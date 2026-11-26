# -*- coding: utf-8 -*-
"""SRM 134 接口对账闸门（工程内版本）。

在外部 _analyze_tmp/check_controllers.py 的「方法 + 路径 + 权限串」对账基础上，
新增本轮唯一范围定义中"外调编号"的逐字对账（用户决策 1）：

  A. SrmApiId 常量集合 == api_manifest 中 134 条接口"外调"列去重后的编号集合（不增不减）。
  B. 每个 Controller 方法内出现的 SrmApiId 常量，其编号必须落在该接口清单行的外调集合内（不得多写）。
  C. 清单标注了外调、且不是"沉淀"读法的接口，方法内必须引用与其外调集合完全一致的常量（不得少写）。

用法：python tools/check_srm_controllers.py [工程根目录]
"""
import io
import json
import os
import re
import sys

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

HERE = os.path.dirname(os.path.abspath(__file__))
DEFAULT_PROJECT = os.path.dirname(HERE)
DEFAULT_MANIFEST = (r'D:\zhaigz\work\01项目资料\01集团\04采购\供应商系统管理平台'
                    r'\技术方案\api_manifest_v1.0.json')

MAPPING = re.compile(r'@(Get|Post|Put|Delete|Request)Mapping\b')
VALUE = re.compile(r'"([^"]+)"')
PERMI = re.compile(r"hasPermi\(\s*'([^']+)'\s*\)")
REQUEST_METHOD = re.compile(r'RequestMethod\.(GET|POST|PUT|DELETE)')
SIG = re.compile(r'^\s*(?:public|protected)\s+[\w<>\[\], ?]+\s+(\w+)\s*\(')
CLASS_SIG = re.compile(r'^\s*public\s+(?:final\s+)?class\s+(\w+)')
API_ID_REF = re.compile(r'SrmApiId\.([A-Z][A-Z0-9_]*)\b')
CONST_DEF = re.compile(r'public\s+static\s+final\s+int\s+([A-Z][A-Z0-9_]*)\s*=\s*(\d+)\s*;')


def norm(path):
    p = path.strip()
    if not p.startswith('/'):
        p = '/' + p
    return re.sub(r'/+', '/', p).rstrip('/') or '/'


def collapse(path):
    return re.sub(r'\{[^}/]*\}', '{}', path)


def declared_api_ids(text, filename):
    """返回 {常量名: 编号}，来自 SrmApiId.java。"""
    out = {}
    for name, value in CONST_DEF.findall(text):
        if name != 'IN_SCOPE':
            out[name] = int(value)
    return out



def javadoc_title(lines, sig_idx):
    """返回方法签名上方 Javadoc 的首行正文（Apifox 接口显示名），缺失返回 None。"""
    j = sig_idx - 1
    while j >= 0 and lines[j].strip().startswith('@'):
        j -= 1
    if j < 0 or not lines[j].strip().endswith('*/'):
        return None
    k = j
    while k >= 0 and '/**' not in lines[k]:
        k -= 1
    if k < 0:
        return None
    for ln in lines[k + 1:j]:
        body = ln.strip().lstrip('*').strip()
        if body:
            return body
    return ''


def parse_endpoints(text, filename):
    lines = text.split('\n')
    class_base = ''
    start = 0
    for i, ln in enumerate(lines, 1):
        if CLASS_SIG.match(ln):
            for j in range(max(0, i - 12), i - 1):
                pre = lines[j].strip()
                mm = MAPPING.search(pre)
                if mm and mm.group(1) == 'Request':
                    vm = VALUE.search(pre)
                    if vm:
                        class_base = norm(vm.group(1))
                    break
            start = i
            break

    endpoints = []
    pending = []
    i = start
    while i < len(lines):
        s = lines[i].strip()
        lineno = i + 1
        if not s or s.startswith('//') or s.startswith('*') or s.startswith('/*'):
            i += 1
            continue
        if s.startswith('@'):
            pending.append((lineno, s))
            i += 1
            continue
        m = SIG.match(s)
        if not m:
            if s.startswith('private') or s.startswith('protected') or s.startswith('public static'):
                pending = []
            i += 1
            continue
        java_method = m.group(1)
        segs = []
        http_method = None
        perm = None
        for _, a in pending:
            mm = MAPPING.search(a)
            if mm:
                verb = mm.group(1)
                vm = VALUE.search(a)
                segs.append(norm(vm.group(1)) if vm else '')
                if verb == 'Request':
                    rm = REQUEST_METHOD.search(a)
                    http_method = rm.group(1) if rm else http_method
                else:
                    http_method = verb.upper()
            pm = PERMI.search(a)
            if pm:
                perm = pm.group(1)
        pending = []
        # 收集方法体（花括号配对），用于外调编号对账
        body = []
        depth = 0
        j = i
        started = False
        while j < len(lines):
            line = lines[j]
            body.append(line)
            depth += line.count('{') - line.count('}')
            if '{' in line:
                started = True
            if started and depth <= 0:
                break
            j += 1
        refs = sorted(set(API_ID_REF.findall('\n'.join(body))))
        i = j + 1
        if http_method is None or not segs:
            continue
        full = norm(class_base + segs[0]) if len(segs) == 1 else norm(class_base + ''.join(segs))
        endpoints.append({
            'method': http_method,
            'path': full,
            'match_path': collapse(full),
            'permission': perm,
            'java_method': java_method,
            'refs': refs,
            'doc_title': javadoc_title(lines, lineno - 1),
            'line': lineno,
            'file': filename,
        })
    return endpoints


def expected_ids(external):
    if not external or external == '—':
        return set()
    return {int(x) for x in re.findall(r'\d{3,4}', external)}


def is_sediment(external):
    """标注"沉淀"表示只读本地已落库数据，不直接发起外调。"""
    return '沉淀' in (external or '')


def main():
    project = sys.argv[1] if len(sys.argv) > 1 else DEFAULT_PROJECT
    manifest_path = os.environ.get('SRM_MANIFEST', DEFAULT_MANIFEST)
    ctrl_dir = os.path.join(project, 'ruoyi-srm', 'src', 'main', 'java',
                            'com', 'ruoyi', 'srm', 'controller')
    api_id_file = os.path.join(project, 'ruoyi-srm', 'src', 'main', 'java',
                               'com', 'ruoyi', 'srm', 'constant', 'SrmApiId.java')

    payload = json.load(io.open(manifest_path, encoding='utf-8'))
    expect = payload['interfaces']
    scope_ids = sorted({i for it in expect for i in expected_ids(it.get('external_api', '—'))})

    errors = 0

    # A. SrmApiId 常量集合逐字对齐
    if not os.path.isfile(api_id_file):
        print('【外调编号】缺少 %s' % api_id_file)
        errors += 1
        class_ids = {}
    else:
        class_ids = declared_api_ids(io.open(api_id_file, encoding='utf-8').read(), 'SrmApiId.java')
        code_ids = sorted(set(class_ids.values()))
        extra_ids = sorted(set(code_ids) - set(scope_ids))
        missing_ids = sorted(set(scope_ids) - set(code_ids))
        print('【外调编号】清单去重 %d 个：%s' % (len(scope_ids), scope_ids))
        print('【外调编号】SrmApiId 常量 %d 个：%s' % (len(code_ids), code_ids))
        if extra_ids:
            print('  ERROR 多写外调编号：%s' % extra_ids)
            errors += 1
        if missing_ids:
            print('  ERROR 缺少外调编号：%s' % missing_ids)
            errors += 1
        if not extra_ids and not missing_ids:
            print('  OK 外调编号集合一一对应，不增不减')

    # B/C. 控制器方法级对账
    if not os.path.isdir(ctrl_dir):
        print('【接口】Controller 目录不存在（尚未生成）：%s' % ctrl_dir)
        print('\nERRORS(%d)' % (1 if errors else 0))
        return 1 if errors else 0

    files = sorted(f for f in os.listdir(ctrl_dir) if f.endswith('.java'))
    actual = []
    for f in files:
        actual.extend(parse_endpoints(io.open(os.path.join(ctrl_dir, f), encoding='utf-8').read(), f))

    print('\n【接口】基线 %d 条，源码解析 %d 条，Controller 文件 %d 个'
          % (len(expect), len(actual), len(files)))
    exp_key = {(it['method'], it['match_path']): it for it in expect}
    act_key = {(it['method'], it['match_path']): it for it in actual}
    missing = [exp_key[k] for k in exp_key if k not in act_key]
    extra = [act_key[k] for k in act_key if k not in exp_key]

    perm_bad = []
    no_perm = []
    for k, it in act_key.items():
        base = exp_key.get((it['method'], it['match_path']))
        if not base:
            continue
        if base['permission'] != it['permission']:
            perm_bad.append((it, base['permission']))
        if it['permission'] is None:
            no_perm.append(it)

    print('缺失 %d 条 / 多写 %d 条 / 权限串不一致 %d 条 / 未挂 @PreAuthorize %d 条'
          % (len(missing), len(extra), len(perm_bad), len(no_perm)))

    ref_bad = []
    for it in actual:
        base = exp_key.get((it['method'], it['match_path']))
        if not base:
            continue
        want = expected_ids(base.get('external_api', '—'))
        got = {class_ids.get(name) for name in it['refs'] if name in class_ids}
        unknown = [name for name in it['refs'] if name not in class_ids]
        if unknown:
            ref_bad.append((it, 'SrmApiId 未定义常量: %s' % ','.join(unknown)))
            continue
        if is_sediment(base.get('external_api', '')):
            if got - want:
                ref_bad.append((it, '沉淀接口多引用外调 %s' % sorted(got - want)))
            continue
        if want and got != want:
            ref_bad.append((it, '外调编号 实际 %s / 清单 %s' % (sorted(got), sorted(want))))
        if not want and got:
            ref_bad.append((it, '清单无外调但代码引用 %s' % sorted(got)))

    doc_bad = []
    for it in actual:
        base = exp_key.get((it['method'], it['match_path']))
        if not base:
            continue
        title = it.get('doc_title')
        if title is None:
            doc_bad.append((it, '缺少方法级 Javadoc（Apifox 将退回裸方法名 %s）' % it['java_method']))
        elif title != base['feature']:
            doc_bad.append((it, '接口名「%s」与清单功能「%s」不一致' % (title, base['feature'])))

    if doc_bad:
        print('\n【接口注释不一致】%d 处' % len(doc_bad))
        for it, msg in doc_bad[:60]:
            print('  %s %s -> %s#%d :: %s' % (it['method'], it['path'], it['file'], it['line'], msg))
        errors += len(doc_bad)
    elif actual:
        print('接口注释对账通过：%d 条功能名与清单逐字一致' % len(actual))

    if ref_bad:
        print('\n【外调编号不一致】')
        for it, msg in ref_bad[:60]:
            print('  %s %s -> %s#%d :: %s' % (it['method'], it['path'], it['file'], it['line'], msg))
        errors += len(ref_bad)
    elif actual:
        print('外调编号方法级对账通过')

    # 外调调用点注释闸门（用户决策 1 的延伸）：
    # 每处 tycGateway.call(...) 必须有 // TYC-CALL 外调天眼查 <编号>（<常量名> · <接口名>）注释，
    # 否则 Apifox/日志侧无法一眼看出调的是哪支天眼查接口。
    import subprocess
    checker = os.path.join(HERE, 'gen_srm_tyc_doc.py')
    rc = subprocess.call([sys.executable, checker, '--check'])
    if rc != 0:
        errors += 1
        print('外调调用点 TYC-CALL 注释闸门不通过')

    def emit_group(title, rows, fmt):
        if not rows:
            return
        print('\n%s' % title)
        for r in rows[:60]:
            print('  ' + fmt(r))

    emit_group('【缺失】基线要求但未实现：', missing,
               lambda r: '页面%d #%d %s %s (%s)' % (r['page_no'], r['idx'], r['method'], r['path'], r['feature']))
    emit_group('【多写】清单外的端点：', extra,
               lambda r: '%s %s -> %s#%d' % (r['method'], r['path'], r['file'], r['line']))
    emit_group('【权限串不一致】：', perm_bad,
               lambda r: '%s %s 实际 %s / 基线 %s' % (r[0]['method'], r[0]['path'], r[0]['permission'], r[1]))

    problems = len(missing) + len(extra) + len(perm_bad) + len(ref_bad) + len(doc_bad)
    print('\nERRORS(%d)' % (1 if problems or errors else 0))
    if problems or errors:
        print('对账不通过：接口缺失/多写/权限/外调编号合计 %d 处' % problems)
        return 1
    print('对账通过：134 条接口一一对应，权限串/外调编号/外调调用点 TYC-CALL 注释全部一致')
    return 0


if __name__ == '__main__':
    sys.exit(main())
