# -*- coding: utf-8 -*-
"""生成《天眼查外调接口对照表》，并校验每个外调调用点都有 TYC-CALL 注释。

数据来源（三同源）：
  - 技术方案/api_manifest_v1.0.json            接口清单（方法/路径/权限/功能/外调编号）
  - 天眼查API接口.xlsx                         外调编号 -> 接口名/分类/单价/文档URL
  - ruoyi-srm/src/main/java/.../SrmApiId.java  外调编号常量与元数据

产出：
  - docs/srm-天眼查外调接口对照表.md            （编号 -> 名称/分类/单价/文档URL/调用页面）
  - docs/srm-天眼查外调调用点清单.md            （Controller 方法 -> 实际调用的编号，逐条带行号）

用法：
    python tools/gen_srm_tyc_doc.py            # 生成文档 + 校验
    python tools/gen_srm_tyc_doc.py --check    # 只校验，不落盘
"""
import io
import json
import os
import re
import sys

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

HERE = os.path.dirname(os.path.abspath(__file__))
PROJECT = os.path.dirname(HERE)
MANIFEST = (r'D:\zhaigz\work\01项目资料\01集团\04采购\供应商系统管理平台'
            r'\技术方案\api_manifest_v1.0.json')
XLSX = (r'D:\zhaigz\work\01项目资料\01集团\04采购\供应商系统管理平台'
        r'\天眼查API接口.xlsx')
CTRL_DIR = os.path.join(PROJECT, 'ruoyi-srm', 'src', 'main', 'java',
                        'com', 'ruoyi', 'srm', 'controller')
API_ID_FILE = os.path.join(PROJECT, 'ruoyi-srm', 'src', 'main', 'java',
                           'com', 'ruoyi', 'srm', 'constant', 'SrmApiId.java')
OUT_DIR = os.path.join(PROJECT, 'docs')
APP_YML = os.path.join(PROJECT, 'ruoyi-admin', 'src', 'main', 'resources', 'application.yml')

CONST_DEF = re.compile(r'public\s+static\s+final\s+int\s+([A-Z][A-Z0-9_]*)\s*=\s*(\d+)\s*;')
TYC_CALL = re.compile(r'//\s*TYC-CALL\s*外调天眼查\s*(\d{3,4})')
CALL_EXPR = re.compile(r'tycGateway\.call\(')
METHOD_SIG = re.compile(r'^\s*(?:public|protected|private)\s+[\w<>\[\],\.\? ]+\s+(\w+)\s*\(')
MAPPING = re.compile(r'@(Get|Post|Put|Delete|Request)Mapping\b')


def read_xlsx():
    """返回 {api_id: dict(name, category, price, doc_url, remark)}。"""
    import openpyxl
    wb = openpyxl.load_workbook(XLSX, data_only=True)
    ws = wb.worksheets[0]
    out = {}
    for row in ws.iter_rows(min_row=2, values_only=True):
        if not row or row[4] is None:
            continue
        api_id = str(row[4]).strip()
        if not api_id.isdigit():
            continue
        out[int(api_id)] = {
            'name': (row[3] or '').strip(),
            'category': (row[2] or '').strip(),
            'price': (row[8] or '').strip(),
            'doc_url': (row[7] or '').strip(),
            'remark': (row[9] or '').strip() if len(row) > 9 else '',
            'input': (row[11] or '').strip() if len(row) > 11 else '',
        }
    return out


def read_yaml_api_ids():
    """读取 application.yml 中 srm.tyc.apis 下的编号键集合。"""
    text = io.open(APP_YML, encoding='utf-8').read()
    i = text.find('\n    apis:')
    if i < 0:
        return set()
    j = text.find('\n  data-center:', i)
    block = text[i:j if j > 0 else len(text)]
    return set(int(x) for x in re.findall(r'^\s+"(\d{3,4})"\s*:\s*$', block, re.M))


def read_constants():
    text = io.open(API_ID_FILE, encoding='utf-8').read()
    out = {}
    for name, value in CONST_DEF.findall(text):
        if name != 'IN_SCOPE':
            out[int(value)] = name
    return out


def scan_calls():
    """返回 [(file, line, method, raw_call_line)]，method 为最近的方法名。"""
    hits = []
    for fn in sorted(os.listdir(CTRL_DIR)):
        if not fn.endswith('Controller.java'):
            continue
        path = os.path.join(CTRL_DIR, fn)
        lines = io.open(path, encoding='utf-8').read().split('\n')
        method = ''
        for i, ln in enumerate(lines, 1):
            m = METHOD_SIG.match(ln)
            if m:
                method = m.group(1)
            if CALL_EXPR.search(ln):
                hits.append((fn, i, method, ln.strip()))
    return hits


def doc_comment_lines(lines, call_idx):
    """call_idx 为 1-based；向上取紧邻的 TYC-CALL 注释块内出现的全部编号。"""
    ids = []
    j = call_idx - 1
    while j > 0:
        prev = lines[j - 1].strip()
        if prev.startswith('//'):
            for x in TYC_CALL.findall(prev):
                ids.append(int(x))
            j -= 1
            continue
        break
    return ids


HELPER_SIG = re.compile(r'(?:public|private|protected)\s+[\w<>\[\],\.\? ]+\s+(\w+)\s*\(\s*int\s+apiId\b')


def helper_allowed_ids(lines, call_idx, const_name_to_id):
    """动态 apiId 调用点：扫描同文件内所有 call/xxx(SrmApiId.X, ...) 传参，取并集。"""
    call_line = lines[call_idx - 1]
    m = re.search(r'tycGateway\.call\(', call_line)
    if not m:
        return []
    # 向上找所属方法签名（含 int apiId 的私有 helper）
    helper = None
    for j in range(call_idx - 1, 0, -1):
        sm = HELPER_SIG.search(lines[j - 1])
        if sm:
            helper = sm.group(1)
            break
    if not helper:
        return []
    allowed = set()
    pat = re.compile(r'\b' + re.escape(helper) + r'\(\s*SrmApiId\.([A-Z_]+)')
    for ln in lines:
        for name in pat.findall(ln):
            if name in const_name_to_id:
                allowed.add(const_name_to_id[name])
    return sorted(allowed)


def build():
    manifest = json.load(io.open(MANIFEST, encoding='utf-8'))
    xlsx = read_xlsx()
    consts = read_constants()
    yml_ids = read_yaml_api_ids()

    errors = []
    # A. SrmApiId 常量集合 == manifest 外调编号集合
    manifest_ids = set()
    for it in manifest['interfaces']:
        for n in re.findall(r'\d{3,4}', it.get('external_api') or ''):
            manifest_ids.add(int(n))
    if manifest_ids != set(consts):
        errors.append('外调编号集合不一致: manifest=%s SrmApiId=%s'
                      % (sorted(manifest_ids), sorted(consts)))
    # B. 每个编号都能在 xlsx 中找到接口名
    for i in sorted(consts):
        if i not in xlsx:
            errors.append('编号 %s 在天眼查API接口.xlsx 中缺失' % i)

    # C. 每个 tycGateway.call 调用点必须有 TYC-CALL 注释，且编号属于 SrmApiId 白名单
    # D. 动态 apiId 的 helper：注释编号必须覆盖同文件内所有 SrmApiId.X 实参（不得漏标）
    pages = manifest['page_names']
    const_name_to_id = {v: k for k, v in consts.items()}
    call_rows = []
    for fn, line, method, raw in scan_calls():
        lines = io.open(os.path.join(CTRL_DIR, fn), encoding='utf-8').read().split('\n')
        norm = set(doc_comment_lines(lines, line))
        for n in re.findall(r'SrmApiId\.([A-Z_]+)', raw):
            if n in const_name_to_id:
                norm.add(const_name_to_id[n])
        if not norm:
            errors.append('%s:%d 调用点缺少 TYC-CALL 注释且无法解析编号：%s' % (fn, line, raw))
        for i in norm:
            if i not in consts:
                errors.append('%s:%d 注释编号 %s 不在 SrmApiId 白名单' % (fn, line, i))
        allowed = helper_allowed_ids(lines, line, const_name_to_id)
        if allowed:
            missing = [i for i in allowed if i not in norm]
            if missing:
                errors.append('%s:%d 动态 apiId 调用点注释漏标编号：%s' % (fn, line, missing))
            norm |= set(allowed)
        call_rows.append((fn, line, method, sorted(norm), raw))

    # E. application.yml 中 srm.tyc.apis 的编号键集合与 SrmApiId 一致（不增不减）
    if yml_ids != set(consts):
        errors.append('application.yml srm.tyc.apis 编号集合与 SrmApiId 不一致: yml=%s SrmApiId=%s'
                      % (sorted(yml_ids), sorted(consts)))

    return manifest, xlsx, consts, pages, call_rows, errors


def write_outputs(manifest, xlsx, consts, pages, call_rows):
    # 编号 -> 调用页面集合
    id_pages = {}
    id_calls = {}
    for it in manifest['interfaces']:
        for n in re.findall(r'\d{3,4}', it.get('external_api') or ''):
            id_pages.setdefault(int(n), set()).add('%d. %s' % (it['page_no'], it['page_name']))
    for fn, line, method, ids, raw in call_rows:
        for i in ids:
            id_calls.setdefault(i, []).append((fn, method, line))

    order = sorted(consts, key=lambda x: consts[x] and -len(id_calls.get(x, [])) or x)
    lines = []
    lines.append('# SRM 天眼查外调接口对照表')
    lines.append('')
    lines.append('> 编号 / 接口名 / 分类 / 单价 / 文档URL 取自《天眼查API接口.xlsx》；')
    lines.append('> 调用页面取自 `技术方案/api_manifest_v1.0.json`（134 条，不增删）。')
    lines.append('> 源码常量：`SrmApiId`；唯一外调出口：`TycGateway.call(...)`；调用点统一以 `// TYC-CALL 外调天眼查 <编号>（<常量名> · <接口名>）` 注释。')
    lines.append('')
    lines.append('## 一、外调编号总表（%d 个）' % len(consts))
    lines.append('')
    lines.append('| 编号 | 常量名 | 天眼查接口名 | 分类 | 单价 | 原文档 | 调用点数 | 引用页面 |')
    lines.append('|---|---|---|---|---|---|---:|---|')
    for i in sorted(consts):
        x = xlsx.get(i, {})
        lines.append('| %d | `%s` | %s | %s | %s | [%d](%s) | %d | %s |' % (
            i, consts[i], x.get('name', ''), x.get('category', ''), x.get('price', ''),
            i, x.get('doc_url', ''), len(id_calls.get(i, [])),
            '、'.join(sorted(id_pages.get(i, []), key=lambda s: int(s.split('.')[0]))) or '—'))
    lines.append('')
    lines.append('## 二、外调调用点清单（按页面）')
    lines.append('')
    lines.append('| # | Controller | 方法 | 行号 | 调用编号 | 接口名 |')
    lines.append('|---|---|---|---|---|---|')
    seq = 0
    for fn, line, method, ids, raw in sorted(call_rows, key=lambda r: (r[0], r[1])):
        seq += 1
        names = '、'.join('%d %s' % (i, xlsx.get(i, {}).get('name', '')) for i in ids)
        lines.append('| %d | %s | %s | %d | %s | %s |' % (seq, fn.replace('.java', ''), method, line,
                                                          '、'.join(str(i) for i in ids), names))
    lines.append('')
    lines.append('## 三、备注')
    lines.append('')
    lines.append('- `srm_api_dict` 中另有 3 条停用记录（1047 企业类型 / 1168 组织机构 / 1141 最短路径发现），')
    lines.append('  134 条接口未引用，`SrmApiId` 未定义，`TycGateway` 的 `inScope` 会直接拦截（错误码 60008）。')
    lines.append('- `GET /srm/supplier/detail/contact|former-name`、风险类 `GET ...?refresh=true` 与 `POST .../refresh`')
    lines.append('  才真正发起外调；不传 `refresh` 时只读本地已沉淀数据，不消耗外调单元。')
    lines.append('- 页面 5/6/7 的 `init` 为异步触发语义，实际外调仍在同一次请求内经 `TycGateway` 完成并留痕。')
    lines.append('')
    io.open(os.path.join(OUT_DIR, 'srm-天眼查外调接口对照表.md'), 'w',
            encoding='utf-8', newline='\n').write('\n'.join(lines))

    lines2 = []
    lines2.append('# SRM 天眼查外调调用点清单（源码级）')
    lines2.append('')
    lines2.append('> 由 `tools/gen_srm_tyc_doc.py` 从源码扫描生成，与上表同源；改代码后重新生成即可对账。')
    lines2.append('')
    lines2.append('| # | 文件 | 行号 | 所在方法 | 调用编号 |')
    lines2.append('|---|---|---:|---|---|')
    for n, (fn, line, method, ids, raw) in enumerate(sorted(call_rows, key=lambda r: (r[0], r[1])), 1):
        lines2.append('| %d | `%s` | %d | `%s` | %s |' % (n, fn, line, method,
                                                          '、'.join(str(i) for i in ids)))
    lines2.append('')
    lines2.append('共 %d 个调用点，覆盖 %d 个外调编号。' % (len(call_rows), len(consts)))
    lines2.append('')
    io.open(os.path.join(OUT_DIR, 'srm-天眼查外调调用点清单.md'), 'w',
            encoding='utf-8', newline='\n').write('\n'.join(lines2))


def _param_list(raw):
    """把 xlsx 的入参文本渲染为 name(必填)/name(可选) 列表。"""
    out = []
    for line in (raw or '').split('\n'):
        line = line.strip()
        m = re.match(r'^(\S+)\s+(true|false)\s+(\S+)', line)
        if not m:
            continue
        out.append('%s(%s)' % (m.group(1), '必填' if m.group(2) == 'true' else '可选'))
    return '、'.join(out) if out else '—'


def write_page_mapping(manifest, xlsx, consts, call_rows):
    """生成《SRM 页面-天眼查接口映射》，逐页列出外调编号、入参要点与接入配置键。"""
    lines = []
    lines.append('# SRM 页面 - 天眼查接口 映射与接入配置')
    lines.append('')
    lines.append('> 页面/接口来自 `技术方案/api_manifest_v1.0.json`（134 条，不增删）；')
    lines.append('> 编号/接口名/入参来自《天眼查API接口.xlsx》；配置键为 `ruoyi-admin/src/main/resources/application.yml` 的 `srm.tyc.apis.<编号>`。')
    lines.append('')

    def ids_of(text):
        return [int(x) for x in re.findall(r'\d{3,4}', text or '')]

    by_page = {}
    for it in manifest['interfaces']:
        ids = ids_of(it.get('external_api'))
        if ids:
            by_page.setdefault(it['page_no'], []).append((it, ids))
    used = set()
    for pl in by_page.values():
        for it, ids in pl:
            used.update(ids)

    lines.append('## 一、按页面（仅列涉及外调的页面）')
    lines.append('')
    for pno in sorted(by_page):
        pl = by_page[pno]
        lines.append('### 页面 %d · %s' % (pno, pl[0][0]['page_name']))
        lines.append('')
        for it, ids in pl:
            lines.append('- `%s %s` — %s；外调 %s' % (
                it['method'], it['path'], it['feature'],
                '、'.join('%d（%s）' % (i, xlsx.get(i, {}).get('name', '')) for i in ids)))
        lines.append('')
        lines.append('| 编号 | 天眼查接口名 | 分类 | 单价 | 入参（名称/是否必填，摘自 xlsx） | application.yml 配置键 |')
        lines.append('|---|---|---|---|---|---|')
        seen = []
        for it, ids in pl:
            for i in ids:
                if i in seen:
                    continue
                seen.append(i)
                x = xlsx.get(i, {})
                lines.append('| %d | %s | %s | %s | %s | `srm.tyc.apis.%d` |' % (
                    i, x.get('name', ''), x.get('category', ''), x.get('price', ''),
                    _param_list(x.get('input', '')), i))
        lines.append('')

    lines.append('## 二、编号 -> 触发方式速查')
    lines.append('')
    lines.append('| 编号 | 接口名 | 触发方式 |')
    lines.append('|---|---|---|')
    trig = {
        818: '页面1 `POST /srm/supplier/enrich`；页面3 `POST /srm/supplier/detail/enrich`（手动）',
        819: '页面3 `POST /srm/supplier/detail/enrich`（手动）；页面3 `person` 读本地沉淀，不直发',
        821: '页面5 `POST /srm/graph/equity/init`（发起任务，异步语义）',
        1046: '页面3 `GET /srm/supplier/detail/contact/{id}?refresh=true`（按需）',
        458: '页面3 `GET /srm/supplier/detail/former-name/{id}?refresh=true`（按需）',
        1002: '页面4 `summary?refresh=true`、`POST /risk/refresh`（按需/手动）',
        839: '页面4 `enforce?refresh=true`、`POST /risk/refresh`（按需/手动）',
        871: '页面4 `enforce?refresh=true`、`POST /risk/refresh`（按需/手动）',
        872: '页面4 `dishonest?refresh=true`、`POST /risk/refresh`（按需/手动）',
        846: '页面4 `punish?refresh=true`、`POST /risk/refresh`（按需/手动）',
        848: '页面4 `punish?refresh=true`、`POST /risk/refresh`（按需/手动）',
        851: '页面4 `tax?refresh=true`、`POST /risk/refresh`（按需/手动）',
        1160: '页面5 `init`/`expand`；页面7 `POST /srm/project/analysis/init`（异步语义）',
        1161: '页面6 `POST /srm/graph/backstage/init`；页面7 `analysis/init`（异步语义）',
        1123: '页面5 `init`、页面6 `init`、页面7 `analysis/init`（异步语义）',
        1156: '页面7 `POST /srm/project/analysis/init`（异步语义）',
    }
    for i in sorted(consts):
        lines.append('| %d | %s | %s |' % (i, xlsx.get(i, {}).get('name', ''), trig.get(i, '—')))
    lines.append('')
    lines.append('## 三、接入步骤（开发阶段 -> 测试接真实接口）')
    lines.append('')
    lines.append('1. 注入令牌：环境变量 `TYC_TOKEN`（或配置中心注入 `srm.tyc.token`），禁止提交明文。')
    lines.append('2. 逐编号把《天眼查API接口.xlsx》"详细URL"的路径段填入 `srm.tyc.apis.<编号>.path`，方法填 `method`。')
    lines.append('3. 把 `srm.tyc.enabled` 置 `true`；`srm.tyc.default-env` 按测试/生产选 `test`/`prod`。')
    lines.append('4. 页面接口、Controller、`TycGateway.call(SrmApiId.X, ...)` 均无需改动；')
    lines.append('   开发阶段 `enabled=false` 或 token 为空时，六段流水线照常执行并留痕，仅第⑥段不发真实 HTTP。')
    lines.append('')
    if used != set(consts):
        lines.append('> 注意：页面引用编号集合与 SrmApiId 不一致，差集 %s' % sorted(used.symmetric_difference(set(consts))))
        lines.append('')
    io.open(os.path.join(OUT_DIR, 'srm-页面天眼查接口映射.md'), 'w',
            encoding='utf-8', newline='\n').write('\n'.join(lines))


def main():
    manifest, xlsx, consts, pages, call_rows, errors = build()
    if '--check' not in sys.argv:
        write_outputs(manifest, xlsx, consts, pages, call_rows)
        write_page_mapping(manifest, xlsx, consts, call_rows)
        print('已生成 docs/srm-天眼查外调接口对照表.md 与 docs/srm-天眼查外调调用点清单.md')
    print('外调编号 %d 个，调用点 %d 处' % (len(consts), len(call_rows)))
    for e in errors:
        print('  ERROR ' + e)
    print('ERRORS(%d)' % len(errors))
    return 1 if errors else 0


if __name__ == '__main__':
    sys.exit(main())
