# SRM 页面 - 天眼查接口 映射与接入配置

> 页面/接口来自 `技术方案/api_manifest_v1.0.json`（134 条，不增删）；
> 编号/接口名/入参来自《天眼查API接口.xlsx》；配置键为 `ruoyi-admin/src/main/resources/application.yml` 的 `srm.tyc.apis.<编号>`。

## 一、按页面（仅列涉及外调的页面）

### 页面 1 · 供应商列表

- `POST /srm/supplier/enrich` — 工商补全（按需一次 818）；外调 818（企业基本信息（含企业联系方式））

| 编号 | 天眼查接口名 | 分类 | 单价 | 入参（名称/是否必填，摘自 xlsx） | application.yml 配置键 |
|---|---|---|---|---|---|
| 818 | 企业基本信息（含企业联系方式） | 工商信息 | 0.20/次 | keyword(必填) | `srm.tyc.apis.818` |

### 页面 3 · 供应商基本信息

- `POST /srm/supplier/detail/enrich` — 工商信息补全（818+819）；外调 818（企业基本信息（含企业联系方式））、819（企业基本信息（含主要人员））
- `GET /srm/supplier/detail/contact/{supplierId}` — 联系方式区；外调 1046（企业联系方式）
- `GET /srm/supplier/detail/person/{supplierId}` — 主要人员（819 沉淀）；外调 819（企业基本信息（含主要人员））
- `GET /srm/supplier/detail/former-name/{supplierId}` — 曾用名时间轴；外调 458（企业曾用名）

| 编号 | 天眼查接口名 | 分类 | 单价 | 入参（名称/是否必填，摘自 xlsx） | application.yml 配置键 |
|---|---|---|---|---|---|
| 818 | 企业基本信息（含企业联系方式） | 工商信息 | 0.20/次 | keyword(必填) | `srm.tyc.apis.818` |
| 819 | 企业基本信息（含主要人员） | 工商信息 | 0.25/次 | keyword(必填) | `srm.tyc.apis.819` |
| 1046 | 企业联系方式 | 工商信息 | 0.10/次 | keyword(必填) | `srm.tyc.apis.1046` |
| 458 | 企业曾用名 | 增值服务 | 0.10/次 | keyword(必填) | `srm.tyc.apis.458` |

### 页面 4 · 司法风险 / 经营风险

- `GET /srm/supplier/risk/summary/{supplierId}` — 七维度汇总卡；外调 1002（司法风险）
- `GET /srm/supplier/risk/enforce` — 被执行/历史被执行；外调 839（被执行人）、871（历史被执行人）
- `GET /srm/supplier/risk/dishonest` — 失信人明细；外调 872（历史失信人）
- `GET /srm/supplier/risk/punish` — 严重违法/经营异常；外调 846（严重违法）、848（经营异常）
- `GET /srm/supplier/risk/tax` — 欠税公告；外调 851（欠税公告）
- `POST /srm/supplier/risk/refresh` — 刷新全量风险（六段）；外调 1002（司法风险）、839（被执行人）、871（历史被执行人）、872（历史失信人）、846（严重违法）、848（经营异常）、851（欠税公告）

| 编号 | 天眼查接口名 | 分类 | 单价 | 入参（名称/是否必填，摘自 xlsx） | application.yml 配置键 |
|---|---|---|---|---|---|
| 1002 | 司法风险 | 组合接口 | 1.50/次 | keyword(必填) | `srm.tyc.apis.1002` |
| 839 | 被执行人 | 司法风险 | 0.15/次 | pageSize(可选)、keyword(必填)、pageNum(可选)、source(可选) | `srm.tyc.apis.839` |
| 871 | 历史被执行人 | 司法风险 | 0.10/次 | pageSize(可选)、keyword(必填)、pageNum(可选)、source(可选) | `srm.tyc.apis.871` |
| 872 | 历史失信人 | 司法风险 | 0.10/次 | pageSize(可选)、keyword(必填)、pageNum(可选)、source(可选) | `srm.tyc.apis.872` |
| 846 | 严重违法 | 经营风险 | 0.15/次 | pageSize(可选)、keyword(必填)、pageNum(可选)、source(可选) | `srm.tyc.apis.846` |
| 848 | 经营异常 | 经营风险 | 0.15/次 | pageSize(可选)、keyword(必填)、pageNum(可选)、source(可选) | `srm.tyc.apis.848` |
| 851 | 欠税公告 | 经营风险 | 0.15/次 | keyword(必填)、pageSize(可选)、pageNum(可选)、isHistory(可选) | `srm.tyc.apis.851` |

### 页面 5 · 股权穿透图谱

- `POST /srm/graph/equity/init` — 发起穿透任务；外调 1160（股权穿透（10层））、821（企业股东）、1123（疑似实际控制人）
- `POST /srm/graph/equity/expand` — 节点续穿；外调 1160（股权穿透（10层））

| 编号 | 天眼查接口名 | 分类 | 单价 | 入参（名称/是否必填，摘自 xlsx） | application.yml 配置键 |
|---|---|---|---|---|---|
| 1160 | 股权穿透（10层） | 特色接口 | 4.00/次 | flag(可选)、dir(可选)、keyword(必填)、minPercent(可选)、maxPercent(可选) | `srm.tyc.apis.1160` |
| 821 | 企业股东 | 工商信息 | 0.15/次 | pageSize(可选)、keyword(必填)、pageNum(可选)、source(可选) | `srm.tyc.apis.821` |
| 1123 | 疑似实际控制人 | 关系发现 | 1.00/次 | keyword(必填) | `srm.tyc.apis.1123` |

### 页面 6 · 幕后关系图谱

- `POST /srm/graph/backstage/init` — 发起幕后关系任务；外调 1161（幕后关系（10层））、1123（疑似实际控制人）

| 编号 | 天眼查接口名 | 分类 | 单价 | 入参（名称/是否必填，摘自 xlsx） | application.yml 配置键 |
|---|---|---|---|---|---|
| 1161 | 幕后关系（10层） | 特色接口 | 4.00/次 | types(可选)、depth(可选)、idTo(可选)、nameTo(可选)、idFrom(可选)、nameFrom(可选) | `srm.tyc.apis.1161` |
| 1123 | 疑似实际控制人 | 关系发现 | 1.00/次 | keyword(必填) | `srm.tyc.apis.1123` |

### 页面 7 · 项目供应商关系网

- `POST /srm/project/analysis/init` — 执行关联分析；外调 1156（疑似关系发现）、1161（幕后关系（10层））、1123（疑似实际控制人）、1160（股权穿透（10层））

| 编号 | 天眼查接口名 | 分类 | 单价 | 入参（名称/是否必填，摘自 xlsx） | application.yml 配置键 |
|---|---|---|---|---|---|
| 1156 | 疑似关系发现 | 关系发现 | 1.00/次 | keyword(必填)、types(可选)、isBookkeeping(可选)、pageNum(可选)、pageSize(可选) | `srm.tyc.apis.1156` |
| 1161 | 幕后关系（10层） | 特色接口 | 4.00/次 | types(可选)、depth(可选)、idTo(可选)、nameTo(可选)、idFrom(可选)、nameFrom(可选) | `srm.tyc.apis.1161` |
| 1123 | 疑似实际控制人 | 关系发现 | 1.00/次 | keyword(必填) | `srm.tyc.apis.1123` |
| 1160 | 股权穿透（10层） | 特色接口 | 4.00/次 | flag(可选)、dir(可选)、keyword(必填)、minPercent(可选)、maxPercent(可选) | `srm.tyc.apis.1160` |

## 二、编号 -> 触发方式速查

| 编号 | 接口名 | 触发方式 |
|---|---|---|
| 458 | 企业曾用名 | 页面3 `GET /srm/supplier/detail/former-name/{id}?refresh=true`（按需） |
| 818 | 企业基本信息（含企业联系方式） | 页面1 `POST /srm/supplier/enrich`；页面3 `POST /srm/supplier/detail/enrich`（手动） |
| 819 | 企业基本信息（含主要人员） | 页面3 `POST /srm/supplier/detail/enrich`（手动）；页面3 `person` 读本地沉淀，不直发 |
| 821 | 企业股东 | 页面5 `POST /srm/graph/equity/init`（发起任务，异步语义） |
| 839 | 被执行人 | 页面4 `enforce?refresh=true`、`POST /risk/refresh`（按需/手动） |
| 846 | 严重违法 | 页面4 `punish?refresh=true`、`POST /risk/refresh`（按需/手动） |
| 848 | 经营异常 | 页面4 `punish?refresh=true`、`POST /risk/refresh`（按需/手动） |
| 851 | 欠税公告 | 页面4 `tax?refresh=true`、`POST /risk/refresh`（按需/手动） |
| 871 | 历史被执行人 | 页面4 `enforce?refresh=true`、`POST /risk/refresh`（按需/手动） |
| 872 | 历史失信人 | 页面4 `dishonest?refresh=true`、`POST /risk/refresh`（按需/手动） |
| 1002 | 司法风险 | 页面4 `summary?refresh=true`、`POST /risk/refresh`（按需/手动） |
| 1046 | 企业联系方式 | 页面3 `GET /srm/supplier/detail/contact/{id}?refresh=true`（按需） |
| 1123 | 疑似实际控制人 | 页面5 `init`、页面6 `init`、页面7 `analysis/init`（异步语义） |
| 1156 | 疑似关系发现 | 页面7 `POST /srm/project/analysis/init`（异步语义） |
| 1160 | 股权穿透（10层） | 页面5 `init`/`expand`；页面7 `POST /srm/project/analysis/init`（异步语义） |
| 1161 | 幕后关系（10层） | 页面6 `POST /srm/graph/backstage/init`；页面7 `analysis/init`（异步语义） |

## 三、接入步骤（开发阶段 -> 测试接真实接口）

1. 注入令牌：环境变量 `TYC_TOKEN`（或配置中心注入 `srm.tyc.token`），禁止提交明文。
2. 逐编号把《天眼查API接口.xlsx》"详细URL"的路径段填入 `srm.tyc.apis.<编号>.path`，方法填 `method`。
3. 把 `srm.tyc.enabled` 置 `true`；`srm.tyc.default-env` 按测试/生产选 `test`/`prod`。
4. 页面接口、Controller、`TycGateway.call(SrmApiId.X, ...)` 均无需改动；
   开发阶段 `enabled=false` 或 token 为空时，六段流水线照常执行并留痕，仅第⑥段不发真实 HTTP。
