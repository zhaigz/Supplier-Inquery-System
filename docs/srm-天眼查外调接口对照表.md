# SRM 天眼查外调接口对照表

> 编号 / 接口名 / 分类 / 单价 / 文档URL 取自《天眼查API接口.xlsx》；
> 调用页面取自 `技术方案/api_manifest_v1.0.json`（134 条，不增删）。
> 源码常量：`SrmApiId`；唯一外调出口：`TycGateway.call(...)`；调用点统一以 `// TYC-CALL 外调天眼查 <编号>（<常量名> · <接口名>）` 注释。

## 一、外调编号总表（16 个）

| 编号 | 常量名 | 天眼查接口名 | 分类 | 单价 | 原文档 | 调用点数 | 引用页面 |
|---|---|---|---|---|---|---:|---|
| 458 | `FORMER_NAME` | 企业曾用名 | 增值服务 | 0.10/次 | [458](https://www.tianyancha.com/data/api/458) | 1 | 3. 供应商基本信息 |
| 818 | `BASIC_INFO` | 企业基本信息（含企业联系方式） | 工商信息 | 0.20/次 | [818](https://www.tianyancha.com/data/api/818) | 2 | 1. 供应商列表、3. 供应商基本信息 |
| 819 | `BASIC_INFO_PERSON` | 企业基本信息（含主要人员） | 工商信息 | 0.25/次 | [819](https://www.tianyancha.com/data/api/819) | 1 | 3. 供应商基本信息 |
| 821 | `SHAREHOLDER` | 企业股东 | 工商信息 | 0.15/次 | [821](https://www.tianyancha.com/data/api/821) | 1 | 5. 股权穿透图谱 |
| 839 | `ENFORCE` | 被执行人 | 司法风险 | 0.15/次 | [839](https://www.tianyancha.com/data/api/839) | 2 | 4. 司法风险 / 经营风险 |
| 846 | `ADMIN_PUNISH_ILLEGAL` | 严重违法 | 经营风险 | 0.15/次 | [846](https://www.tianyancha.com/data/api/846) | 2 | 4. 司法风险 / 经营风险 |
| 848 | `ADMIN_PUNISH_ABNORMAL` | 经营异常 | 经营风险 | 0.15/次 | [848](https://www.tianyancha.com/data/api/848) | 2 | 4. 司法风险 / 经营风险 |
| 851 | `TAX_OWED` | 欠税公告 | 经营风险 | 0.15/次 | [851](https://www.tianyancha.com/data/api/851) | 2 | 4. 司法风险 / 经营风险 |
| 871 | `ENFORCE_HIST` | 历史被执行人 | 司法风险 | 0.10/次 | [871](https://www.tianyancha.com/data/api/871) | 2 | 4. 司法风险 / 经营风险 |
| 872 | `DISHONEST` | 历史失信人 | 司法风险 | 0.10/次 | [872](https://www.tianyancha.com/data/api/872) | 2 | 4. 司法风险 / 经营风险 |
| 1002 | `JUDICIAL_SUMMARY` | 司法风险 | 组合接口 | 1.50/次 | [1002](https://www.tianyancha.com/data/api/1002) | 2 | 4. 司法风险 / 经营风险 |
| 1046 | `CONTACT` | 企业联系方式 | 工商信息 | 0.10/次 | [1046](https://www.tianyancha.com/data/api/1046) | 1 | 3. 供应商基本信息 |
| 1123 | `ACTUAL_CONTROLLER` | 疑似实际控制人 | 关系发现 | 1.00/次 | [1123](https://www.tianyancha.com/data/api/1123) | 3 | 5. 股权穿透图谱、6. 幕后关系图谱、7. 项目供应商关系网 |
| 1156 | `SUSPECT_RELATION` | 疑似关系发现 | 关系发现 | 1.00/次 | [1156](https://www.tianyancha.com/data/api/1156) | 1 | 7. 项目供应商关系网 |
| 1160 | `EQUITY_PENETRATION` | 股权穿透（10层） | 特色接口 | 4.00/次 | [1160](https://www.tianyancha.com/data/api/1160) | 3 | 5. 股权穿透图谱、7. 项目供应商关系网 |
| 1161 | `BACKSTAGE_RELATION` | 幕后关系（10层） | 特色接口 | 4.00/次 | [1161](https://www.tianyancha.com/data/api/1161) | 2 | 6. 幕后关系图谱、7. 项目供应商关系网 |

## 二、外调调用点清单（按页面）

| # | Controller | 方法 | 行号 | 调用编号 | 接口名 |
|---|---|---|---|---|---|
| 1 | SrmEquityGraphController | expand | 181 | 1160 | 1160 股权穿透（10层） |
| 2 | SrmEquityGraphController | call | 297 | 821、1123、1160 | 821 企业股东、1123 疑似实际控制人、1160 股权穿透（10层） |
| 3 | SrmGraphBackstageController | call | 301 | 1123、1161 | 1123 疑似实际控制人、1161 幕后关系（10层） |
| 4 | SrmProjectController | analysisInit | 275 | 1156 | 1156 疑似关系发现 |
| 5 | SrmProjectController | analysisInit | 277 | 1161 | 1161 幕后关系（10层） |
| 6 | SrmProjectController | analysisInit | 279 | 1123 | 1123 疑似实际控制人 |
| 7 | SrmProjectController | analysisInit | 281 | 1160 | 1160 股权穿透（10层） |
| 8 | SrmSupplierController | enrich | 161 | 818 | 818 企业基本信息（含企业联系方式） |
| 9 | SrmSupplierDetailController | enrich | 107 | 818 | 818 企业基本信息（含企业联系方式） |
| 10 | SrmSupplierDetailController | enrich | 109 | 819 | 819 企业基本信息（含主要人员） |
| 11 | SrmSupplierDetailController | callSubject | 261 | 458、1046 | 458 企业曾用名、1046 企业联系方式 |
| 12 | SrmSupplierRiskController | call | 320 | 839、846、848、851、871、872、1002 | 839 被执行人、846 严重违法、848 经营异常、851 欠税公告、871 历史被执行人、872 历史失信人、1002 司法风险 |
| 13 | SrmSupplierRiskController | callSubject | 345 | 839、846、848、851、871、872、1002 | 839 被执行人、846 严重违法、848 经营异常、851 欠税公告、871 历史被执行人、872 历史失信人、1002 司法风险 |

## 三、备注

- `srm_api_dict` 中另有 3 条停用记录（1047 企业类型 / 1168 组织机构 / 1141 最短路径发现），
  134 条接口未引用，`SrmApiId` 未定义，`TycGateway` 的 `inScope` 会直接拦截（错误码 60008）。
- `GET /srm/supplier/detail/contact|former-name`、风险类 `GET ...?refresh=true` 与 `POST .../refresh`
  才真正发起外调；不传 `refresh` 时只读本地已沉淀数据，不消耗外调单元。
- 页面 5/6/7 的 `init` 为异步触发语义，实际外调仍在同一次请求内经 `TycGateway` 完成并留痕。
