# -*- coding: utf-8 -*-
"""生成 SRM 模拟（演示）数据并可直接灌入 srm 库。

重点加厚：
  页面5 股权穿透图   srm_analysis_task / srm_graph_node / srm_graph_edge
  页面6 幕后关系图   同上（task_type=backstage）
  页面7 项目管理     srm_project / srm_project_member / srm_rel_clue / srm_report_version

同时补齐主档与其余本地库表，保证页面1—13 均有数据可查。

用法：
  python tools/gen_srm_mock_data.py              # 只生成 sql/srm_mock_data_v1.0.sql
  python tools/gen_srm_mock_data.py --execute    # 生成并灌入 MySQL(srm)

幂等：演示数据 id 统一落在 1001..1999，重跑先删该区间再写入。
数据性质：演示数据（非生产数据）。
"""
import io
import json
import os
import subprocess
import sys

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

HERE = os.path.dirname(os.path.abspath(__file__))
PROJECT = os.path.dirname(HERE)
OUT_SQL = os.path.join(PROJECT, 'sql', 'srm_mock_data_v1.0.sql')

DB = dict(host='127.0.0.1', port='3306', user='root', pwd='root', schema='srm')
ID_LO, ID_HI = 1001, 1999

TABLES = []          # 生成顺序（= 删除顺序，先删子表）
ROWS = {}            # table -> (columns, rows)


def table(name, columns, rows):
    TABLES.append(name)
    ROWS[name] = (columns, rows)
    return rows


def sql_literal(v):
    if v is None:
        return 'NULL'
    if isinstance(v, bool):
        return '1' if v else '0'
    if isinstance(v, (int, float)):
        return str(v)
    if isinstance(v, (dict, list)):
        v = json.dumps(v, ensure_ascii=False, separators=(',', ':'))
    s = str(v).replace('\\', '\\\\').replace("'", "''")
    return "'" + s + "'"


def validate():
    bad = []
    for name in TABLES:
        cols, rows = ROWS[name]
        n = len(cols)
        if len(set(cols)) != n:
            bad.append('%s: 列名重复' % name)
        for i, r in enumerate(rows):
            if len(r) != n:
                bad.append('%s: 第 %d 行列数 %d != 列定义 %d' % (name, i + 1, len(r), n))
    if bad:
        raise SystemExit('数据列数校验失败:\n  ' + '\n  '.join(bad))


def build_sql():
    out = []
    out.append('-- ============================================================================')
    out.append('-- SRM 演示（模拟）数据 v1.0  —— 由 tools/gen_srm_mock_data.py 生成，请勿手工修改')
    out.append('-- 覆盖页面：1 供应商列表 / 2 查询首页 / 3 详情 / 4 风险 / 5 股权穿透 /')
    out.append('--           6 幕后关系 / 7 项目管理 / 8 监控 / 9 调用日志 / 12 复核 / 13 报告')
    out.append('-- 幂等：演示数据 id 落在 1001..1999，重跑先删区间。数据性质：演示数据。')
    out.append('-- ============================================================================')
    out.append('SET NAMES utf8mb4;')
    out.append('')
    out.append('-- 先清理演示数据区间（子表 -> 父表）')
    for name in reversed(TABLES):
        cols, rows = ROWS[name]
        if not rows:
            continue
        # 区间删除：演示数据 id 统一 >= ID_LO，重跑先整段清空，彻底幂等，
        # 不因新增供应商使自增 _seq 偏移而残留旧行、命中唯一键冲突。
        out.append('DELETE FROM `%s` WHERE id >= %d;' % (name, ID_LO))
    out.append('')
    for name in TABLES:
        cols, rows = ROWS[name]
        if not rows:
            continue
        out.append('-- %s : %d 行' % (name, len(rows)))
        collist = ', '.join('`%s`' % c for c in cols)
        for r in rows:
            vals = ', '.join(sql_literal(v) for v in r)
            out.append('INSERT INTO `%s` (%s) VALUES (%s);' % (name, collist, vals))
        out.append('')
    return '\n'.join(out) + '\n'


def mysql_run(sql):
    env = dict(os.environ, MYSQL_PWD=DB['pwd'])
    return subprocess.run(
        ['mysql', '-h', DB['host'], '-P', DB['port'], '-u', DB['user'],
         '--default-character-set=utf8mb4', DB['schema']],
        input=sql.encode('utf-8'), capture_output=True, env=env)


def main():
    # 脚本模式下 __main__ 即 gen_srm_mock_data，先登记别名，
    # 保证 data_srm_mock 里 `from gen_srm_mock_data import table` 复用同一注册表。
    sys.modules.setdefault('gen_srm_mock_data', sys.modules['__main__'])
    import data_srm_mock  # noqa: F401  (由同目录模块注册数据)
    validate()
    sql = build_sql()
    os.makedirs(os.path.dirname(OUT_SQL), exist_ok=True)
    io.open(OUT_SQL, 'w', encoding='utf-8', newline='\n').write(sql)

    total = sum(len(r) for _, r in ROWS.values())
    print('已生成 %s' % OUT_SQL)
    print('表 %d 张 / 数据 %d 行 / %d 字节' % (len(TABLES), total, len(sql.encode('utf-8'))))
    for name in TABLES:
        print('  %-30s %d' % (name, len(ROWS[name][1])))

    if '--execute' in sys.argv:
        r = mysql_run(sql)
        if r.returncode != 0:
            sys.stderr.write(r.stderr.decode('utf-8', 'replace'))
            print('灌库失败')
            return 1
        print('\n已灌入 MySQL %s@%s:%s/%s' % (DB['user'], DB['host'], DB['port'], DB['schema']))
    return 0


if __name__ == '__main__':
    sys.exit(main())