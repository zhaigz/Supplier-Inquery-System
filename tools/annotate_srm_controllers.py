# -*- coding: utf-8 -*-
"""为 ruoyi-srm 的 134 个接口补齐规范注释。

唯一来源：api_manifest_v1.0.json（技术方案/接口清单_全量.md 的机器版）。
每个端点生成：

    功能名称（清单 feature，作为 Apifox / Torna 的接口显示名）
    页面编号 + 页面名
    权限串（与 @PreAuthorize 一致）
    外调编号 + SrmApiId 常量名

用法：
    python tools/annotate_srm_controllers.py            # 预览，不落盘
    python tools/annotate_srm_controllers.py --apply    # 写入源码
"""
import io
import json
import os
import re
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from check_srm_controllers import DEFAULT_MANIFEST, parse_endpoints  # noqa: E402

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

HERE = os.path.dirname(os.path.abspath(__file__))
PROJECT = os.path.dirname(HERE)
CTRL_REL = os.path.join('ruoyi-srm', 'src', 'main', 'java',
                        'com', 'ruoyi', 'srm', 'controller')
API_ID_REL = os.path.join('ruoyi-srm', 'src', 'main', 'java',
                          'com', 'ruoyi', 'srm', 'constant', 'SrmApiId.java')

CONST_DEF = re.compile(
    r'public\s+static\s+final\s+int\s+([A-Z][A-Z0-9_]*)\s*=\s*(\d+)\s*;')

DASHES = ('-', '\u2014', '\u2013', '\u2500')


def constant_names(path):
    text = io.open(path, encoding='utf-8').read()
    return {int(v): n for n, v in CONST_DEF.findall(text)}


def sanitize(text):
    """保证文本能安全放进 Javadoc 单行。"""
    return (text or '').replace('*/', '* /').replace('\r', ' ').replace('\n', ' ').strip()


def render_external(raw, names):
    raw = (raw or '').strip()
    if raw in DASHES or raw == '':
        return '\u65e0\uff08\u7eaf\u672c\u5730\u5e93\uff0c\u4e0d\u53d1\u8d77\u5916\u8c03\uff09'
    ids = sorted({int(x) for x in re.findall(r'\d{3,4}', raw)})
    tags = []
    for i in ids:
        name = names.get(i)
        tags.append('%s（%s）' % (i, name) if name else str(i))
    note = ''
    if '沉淀' in raw:
        note = '\uff08\u8bfb\u53d6\u672c\u5730\u5df2\u6c89\u6dc0\u6570\u636e\uff0c\u4e0d\u76f4\u53d1\u5916\u8c03\uff09'
    elif '异步' in raw:
        note = '\uff08\u5f02\u6b65\u89e6\u53d1\uff09'
    return '\u5929\u773c\u67e5 ' + '\u3001'.join(tags) + note


def build_class_doc(page_no, page_name, count, author='ruoyi'):
    return [
        '/**',
        ' * \u9875\u9762%d\u300c%s\u300d\u63a5\u53e3\uff0c\u5171 %d \u6761\u3002' % (page_no, page_name, count),
        ' *',
        ' * <p>\u8303\u56f4\u6765\u6e90\uff1a\u6280\u672f\u65b9\u6848/\u63a5\u53e3\u6e05\u5355_\u5168\u91cf.md \u4e0e api_manifest_v1.0.json\u3002',
        ' * \u65b9\u6cd5\u3001\u8def\u5f84\u3001\u6743\u9650\u4e32\u3001\u529f\u80fd\u3001\u5916\u8c03\u7f16\u53f7\u9010\u5b57\u5b9e\u73b0\uff0c\u4e0d\u589e\u5220\u6761\u76ee\u3002',
        ' *',
        ' * @author %s' % author,
        ' */',
    ]


def build_method_doc(feature, page_no, page_name, permission, external):
    return [
        '    /**',
        '     * %s' % feature,
        '     *',
        '     * <p>\u9875\u9762 %d\u300c%s\u300d\uff1b\u6743\u9650 %s\uff1b\u5916\u8c03 %s'
        % (page_no, page_name, permission or '\uff08\u672a\u58f0\u660e\uff09', external),
        '     */',
    ]


def find_annotation_block(lines, sig_idx):
    """\u8fd4\u56de\uff08\u7b2c\u4e00\u4e2a\u6ce8\u89e3\u884c, \u65e7 Javadoc \u8d77\u6b62\u884c\u6216 None\uff09\u3002"""
    i = sig_idx - 1
    while i >= 0 and lines[i].strip().startswith('@'):
        i -= 1
    blk = i + 1
    rep = None
    if i >= 0 and lines[i].strip().endswith('*/'):
        k = i
        while k >= 0 and '/**' not in lines[k]:
            k -= 1
        if k >= 0:
            rep = (k, i)
    return blk, rep


def annotate_file(path, endpoints, meta, names, apply_changes):
    text = io.open(path, encoding='utf-8', newline='').read()
    lines = text.split('\n')
    edits = []

    for ep in sorted(endpoints, key=lambda e: e['line'], reverse=True):
        record = meta.get((ep['method'], ep['match_path']))
        if not record:
            print('  SKIP \u672a\u5339\u914d\u6e05\u5355\uff1a%s %s' % (ep['method'], ep['path']))
            continue
        doc = build_method_doc(
            sanitize(record['feature']),
            record['page_no'], sanitize(record['page_name']),
            record['permission'],
            render_external(record.get('external_api'), names))
        sig_idx = ep['line'] - 1
        blk, rep = find_annotation_block(lines, sig_idx)
        old = '\n'.join(lines[rep[0]:rep[1] + 1]) if rep else None
        edits.append((rep[0] if rep else blk, rep[1] + 1 if rep else blk, doc, old,
                      ep['method'], ep['path']))

    if not edits:
        return 0

    # \u7c7b\u7ea7 Javadoc \u7edf\u4e00
    pages = {meta[(e['method'], e['match_path'])]['page_no']
             for e in endpoints if (e['method'], e['match_path']) in meta}
    page_rows = {meta[(e['method'], e['match_path'])]['page_no']:
                 meta[(e['method'], e['match_path'])]['page_name']
                 for e in endpoints if (e['method'], e['match_path']) in meta}
    if len(pages) == 1:
        page_no = pages.pop()
        class_doc = build_class_doc(page_no, sanitize(page_rows[page_no]), len(endpoints))
        cls_idx = next((i for i, ln in enumerate(lines)
                        if re.match(r'^public\s+(?:final\s+)?class\s+\w+', ln)), None)
        if cls_idx is not None:
            i = cls_idx - 1
            while i >= 0 and lines[i].strip().startswith('@'):
                i -= 1
            blk = i + 1
            rep = None
            if i >= 0 and lines[i].strip().endswith('*/'):
                k = i
                while k >= 0 and '/**' not in lines[k]:
                    k -= 1
                if k >= 0:
                    rep = (k, i)
            edits.append((rep[0] if rep else blk, rep[1] + 1 if rep else blk,
                          class_doc, None, 'CLASS', ''))

    for start, end, doc, old, method, p in edits:
        lines[start:end] = doc if old is None else doc

    out = '\n'.join(lines)
    changed = out != text
    if changed and apply_changes:
        io.open(path, 'w', encoding='utf-8', newline='').write(out)
    return len(edits) if changed else 0


def main():
    apply_changes = '--apply' in sys.argv
    manifest_path = os.environ.get('SRM_MANIFEST', DEFAULT_MANIFEST)
    payload = json.load(io.open(manifest_path, encoding='utf-8'))
    meta = {(it['method'], it['match_path']): it for it in payload['interfaces']}
    names = constant_names(os.path.join(PROJECT, API_ID_REL))

    ctrl_dir = os.path.join(PROJECT, CTRL_REL)
    total = 0
    for name in sorted(os.listdir(ctrl_dir)):
        if not name.endswith('.java') or name == 'package-info.java':
            continue
        path = os.path.join(ctrl_dir, name)
        eps = parse_endpoints(io.open(path, encoding='utf-8').read(), name)
        if not eps:
            continue
        n = annotate_file(path, eps, meta, names, apply_changes)
        total += n
        print('%-38s endpoints=%-4d %s' % (name, len(eps),
                                           '\u5df2\u66f4\u65b0' if n else '\u65e0\u53d8\u5316'))
    print('\n%s\uff1a%d \u5904\u6ce8\u91ca\uff08\u65b9\u6cd5\u7ea7 134 + \u7c7b\u7ea7 21\uff09' % ('\u5df2\u5199\u5165' if apply_changes else '\u9884\u89c8', total))


if __name__ == '__main__':
    main()