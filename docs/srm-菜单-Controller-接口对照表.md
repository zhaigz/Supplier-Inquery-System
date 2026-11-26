# SRM 菜单 / Controller / 接口 对照表

> 菜单层级取自需求说明书《供应商查询系统需求及建设说明书 V2.1》页面标题与当前系统菜单树；
> 接口为 `技术方案/api_manifest_v1.0.json` 的 134 条（不增删），方法/路径/权限串/功能与外调编号逐字对应；
> 「外调」列出现的编号即该接口会经唯一出口 `TycGateway.call(...)` 请求天眼查，
> 源码中以 `// TYC-CALL 外调天眼查 <编号>` 与 `// TODO[天眼查接入]` 标注。

## 一、菜单 → Controller → 接口数 概览

| 菜单一级 | 菜单项（页面） | Controller | base_path | 接口数 | 外调编号 |
|---|---|---|---|---:|---|
| 查询与档案 | 页面1 供应商列表 | `SrmSupplierController` | `/srm/supplier` | 8 | 818 |
| 查询与档案 | 页面2 查询首页 | `SrmQueryHomeController` | `/srm/query` | 5 | — |
| 查询与档案 | 页面3 供应商基本信息 | `SrmSupplierDetailController` | `/srm/supplier/detail` | 9 | 458、818、819、1046 |
| 查询与档案 | 页面4 供应商风险信息 | `SrmSupplierRiskController` | `/srm/supplier/risk` | 8 | 839、846、848、851、871、872、1002 |
| 图谱与项目 | 页面5 股权穿透图谱 | `SrmEquityGraphController` | `/srm/graph/equity` | 6 | 821、1123、1160 |
| 图谱与项目 | 页面6 幕后关系图谱 | `SrmGraphBackstageController` | `/srm/graph/backstage` | 6 | 1123、1161 |
| 图谱与项目 | 页面7 项目关系网 | `SrmProjectController` | `/srm/project` | 13 | 1123、1156、1160、1161 |
| 监控与复核 | 页面8 变更监控清单 | `SrmChangeEventController` | `/srm/change-event` | 5 | — |
| 成本与审计 | 页面9 接口调用信息 | `SrmApiCallLogController` | `/srm/api-log` | 5 | — |
| 图谱与项目 | 页面10 关联关系配置 | `SrmRelRuleController` | `/srm/rel-rule` | 9 | — |
| 成本与审计 | 页面11 字段映射配置 | `SrmFieldMappingController` | `/srm/field-mapping` | 8 | — |
| 监控与复核 | 页面12 人工复核 | `SrmClueReviewController` | `/srm/clue-review` | 6 | — |
| 监控与复核 | 页面13 报告版本 | `SrmReportVersionController` | `/srm/report-version` | 7 | — |
| 查询与档案 | 页面14 批量导入 | `SrmImportBatchController` | `/srm/supplier-import` | 7 | — |
| 监控与复核 | 页面15 监控范围与频率 | `SrmMonitorPolicyController` | `/srm/monitor-policy` | 7 | — |
| 成本与审计 | 页面16 预算与触发规则 | `SrmBudgetPolicyController`（5 条，`/srm/budget-policy`） + `SrmTriggerRuleController`（2 条，`/srm/trigger-rule`） + `SrmApiDictController`（2 条，`/srm/api-dict`） | 3 个 base_path | 9 | — |
| 成本与审计 | 页面17 操作审计 | `SrmAuditLogController` | `/srm/audit-log` | 4 | — |
| 监控与复核 | 页面18 数据总览 | `SrmOverviewController` | `/srm/overview` | 4 | — |
| 成本与审计 | 页面19 数据中心 | `SrmDataCenterController` | `/srm/data-center` | 8 | — |

> 说明：菜单与 Controller 默认一对一；**页面16 为一对多**（预算 5 条 + 触发规则 2 条 + 接口字典 2 条），已在明细中按接口归属标注。源码可控器总数 21 个（另含 `SrmApiDictController`、`SrmTriggerRuleController` 两个从属控制器），与本表 19 个菜单项一一覆盖 134 条接口。

## 二、逐菜单接口明细（134 条）

### 查询与档案 · 页面1 供应商列表

- Controller：`SrmSupplierController`，base_path `/srm/supplier`，共 8 条接口
- 本菜单调用的天眼查编号：818（企业基本信息（含企业联系方式））

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/supplier/list` | `srm:supplier:list` | 分页列表（八列） | — |
| 2 | GET | `/srm/supplier/{id}` | `srm:supplier:query` | 编辑回显（主档+行业+曾用名） | — |
| 3 | POST | `/srm/supplier` | `srm:supplier:add` | 新增主档（本地） | — |
| 4 | PUT | `/srm/supplier` | `srm:supplier:edit` | 修改主档 | — |
| 5 | PUT | `/srm/supplier/monitor` | `srm:supplier:edit` | 勾选/取消重点监控 | — |
| 6 | DELETE | `/srm/supplier/{ids}` | `srm:supplier:remove` | 批量逻辑删除 | — |
| 7 | POST | `/srm/supplier/enrich` | `srm:supplier:edit` | 工商补全（按需一次 818） | 818 |
| 8 | POST | `/srm/supplier/export` | `srm:supplier:export` | 导出当前筛选结果 | — |

### 查询与档案 · 页面2 查询首页

- Controller：`SrmQueryHomeController`，base_path `/srm/query`，共 5 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/query/match` | `srm:query:list` | 本地包含匹配检索 | — |
| 2 | GET | `/srm/query/history` | `srm:query:list` | 最近查询/常用入口 | — |
| 3 | POST | `/srm/query/history` | `srm:query:add` | 记录本次检索 | — |
| 4 | DELETE | `/srm/query/history/{ids}` | `srm:query:remove` | 清空/删除最近查询 | — |
| 5 | GET | `/srm/query/card/{supplierId}` | `srm:query:query` | 命中项工商简卡 | — |

### 查询与档案 · 页面3 供应商基本信息

- Controller：`SrmSupplierDetailController`，base_path `/srm/supplier/detail`，共 9 条接口
- 本菜单调用的天眼查编号：458（企业曾用名）、818（企业基本信息（含企业联系方式））、819（企业基本信息（含主要人员））、1046（企业联系方式）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/supplier/detail/{supplierId}` | `srm:detail:query` | 主档 13 项+行业四级 | — |
| 2 | POST | `/srm/supplier/detail/enrich` | `srm:detail:edit` | 工商信息补全（818+819） | 818、819 |
| 3 | GET | `/srm/supplier/detail/contact/{supplierId}` | `srm:detail:list` | 联系方式区 | 1046 |
| 4 | GET | `/srm/supplier/detail/person/{supplierId}` | `srm:detail:list` | 主要人员（819 沉淀） | 819 |
| 5 | GET | `/srm/supplier/detail/shareholder/{supplierId}` | `srm:detail:list` | 股东与占比 | — |
| 6 | GET | `/srm/supplier/detail/former-name/{supplierId}` | `srm:detail:list` | 曾用名时间轴 | 458 |
| 7 | GET | `/srm/supplier/detail/change/{supplierId}` | `srm:detail:list` | 工商变更记录 | — |
| 8 | GET | `/srm/supplier/detail/ext/{supplierId}` | `srm:detail:list` | 自定义字段值 | — |
| 9 | PUT | `/srm/supplier/detail/local` | `srm:detail:edit` | 编辑本地维护项 | — |

### 查询与档案 · 页面4 供应商风险信息

- Controller：`SrmSupplierRiskController`，base_path `/srm/supplier/risk`，共 8 条接口
- 本菜单调用的天眼查编号：839（被执行人）、846（严重违法）、848（经营异常）、851（欠税公告）、871（历史被执行人）、872（历史失信人）、1002（司法风险）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/supplier/risk/summary/{supplierId}` | `srm:risk:query` | 七维度汇总卡 | 1002 |
| 2 | GET | `/srm/supplier/risk/enforce` | `srm:risk:list` | 被执行/历史被执行 | 839、871 |
| 3 | GET | `/srm/supplier/risk/dishonest` | `srm:risk:list` | 失信人明细 | 872 |
| 4 | GET | `/srm/supplier/risk/punish` | `srm:risk:list` | 严重违法/经营异常 | 846、848 |
| 5 | GET | `/srm/supplier/risk/tax` | `srm:risk:list` | 欠税公告 | 851 |
| 6 | POST | `/srm/supplier/risk/refresh` | `srm:risk:edit` | 刷新全量风险（六段） | 839、846、848、851、871、872、1002 |
| 7 | GET | `/srm/supplier/risk/meta` | `srm:risk:list` | 接口元数据/分页上限 | — |
| 8 | POST | `/srm/supplier/risk/export` | `srm:risk:export` | 导出风险清单 | — |

### 图谱与项目 · 页面5 股权穿透图谱

- Controller：`SrmEquityGraphController`，base_path `/srm/graph/equity`，共 6 条接口
- 本菜单调用的天眼查编号：821（企业股东）、1123（疑似实际控制人）、1160（股权穿透（10层））

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | POST | `/srm/graph/equity/init` | `srm:graphEquity:add` | 发起穿透任务 | 821、1123、1160 |
| 2 | GET | `/srm/graph/equity/progress/{taskNo}` | `srm:graphEquity:query` | 轮询进度（不计费） | — |
| 3 | GET | `/srm/graph/equity/result/{taskNo}` | `srm:graphEquity:query` | 取图谱结果 | — |
| 4 | POST | `/srm/graph/equity/expand` | `srm:graphEquity:edit` | 节点续穿 | 1160 |
| 5 | GET | `/srm/graph/equity/node/{nodeId}` | `srm:graphEquity:query` | 节点详情 | — |
| 6 | POST | `/srm/graph/equity/export` | `srm:graphEquity:export` | 导出节点/连线 | — |

### 图谱与项目 · 页面6 幕后关系图谱

- Controller：`SrmGraphBackstageController`，base_path `/srm/graph/backstage`，共 6 条接口
- 本菜单调用的天眼查编号：1123（疑似实际控制人）、1161（幕后关系（10层））

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | POST | `/srm/graph/backstage/init` | `srm:graphBackstage:add` | 发起幕后关系任务 | 1123、1161 |
| 2 | GET | `/srm/graph/backstage/progress/{taskNo}` | `srm:graphBackstage:query` | 轮询进度 | — |
| 3 | GET | `/srm/graph/backstage/result/{taskNo}` | `srm:graphBackstage:query` | 取节点/连线/共同指向 | — |
| 4 | GET | `/srm/graph/backstage/align/{taskNo}` | `srm:graphBackstage:query` | 与本地人员/股东对齐 | — |
| 5 | GET | `/srm/graph/backstage/task/list` | `srm:graphBackstage:list` | 历史任务清单 | — |
| 6 | POST | `/srm/graph/backstage/export` | `srm:graphBackstage:export` | 导出幕后关系 | — |

### 图谱与项目 · 页面7 项目关系网

- Controller：`SrmProjectController`，base_path `/srm/project`，共 13 条接口
- 本菜单调用的天眼查编号：1123（疑似实际控制人）、1156（疑似关系发现）、1160（股权穿透（10层））、1161（幕后关系（10层））

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/project/list` | `srm:project:list` | 项目分页清单 | — |
| 2 | GET | `/srm/project/{id}` | `srm:project:query` | 项目详情+成员+参数 | — |
| 3 | POST | `/srm/project` | `srm:project:add` | 新建项目 | — |
| 4 | PUT | `/srm/project` | `srm:project:edit` | 修改项目与穿透参数 | — |
| 5 | DELETE | `/srm/project/{ids}` | `srm:project:remove` | 删除项目（有版本禁删） | — |
| 6 | POST | `/srm/project/member` | `srm:project:add` | 加入供应商成员 | — |
| 7 | DELETE | `/srm/project/member/{ids}` | `srm:project:remove` | 移出成员 | — |
| 8 | POST | `/srm/project/analysis/init` | `srm:project:add` | 执行关联分析 | 1123、1156、1160、1161 |
| 9 | GET | `/srm/project/analysis/progress/{taskNo}` | `srm:project:query` | 分析进度 | — |
| 10 | GET | `/srm/project/clue/list` | `srm:project:list` | 命中线索明细 | — |
| 11 | GET | `/srm/project/graph/{taskNo}` | `srm:project:query` | 关系网图谱回放 | — |
| 12 | POST | `/srm/project/report/save` | `srm:project:add` | 保存报告版本 | — |
| 13 | POST | `/srm/project/export` | `srm:project:export` | 导出报告 | — |

### 监控与复核 · 页面8 变更监控清单

- Controller：`SrmChangeEventController`，base_path `/srm/change-event`，共 5 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/change-event/list` | `srm:changeEvent:list` | 变更清单 13 列 | — |
| 2 | GET | `/srm/change-event/{id}` | `srm:changeEvent:query` | 事件详情+前后快照 | — |
| 3 | PUT | `/srm/change-event/handle` | `srm:changeEvent:edit` | 处置（改状态） | — |
| 4 | GET | `/srm/change-event/stat` | `srm:changeEvent:list` | 维度/企业统计+成本 | — |
| 5 | POST | `/srm/change-event/export` | `srm:changeEvent:export` | 导出变更清单 | — |

### 成本与审计 · 页面9 接口调用信息

- Controller：`SrmApiCallLogController`，base_path `/srm/api-log`，共 5 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/api-log/list` | `srm:apiLog:list` | 调用明细 | — |
| 2 | GET | `/srm/api-log/{id}` | `srm:apiLog:query` | 单次调用详情 | — |
| 3 | GET | `/srm/api-log/raw/{id}` | `srm:apiLog:query` | 原始响应（仅管理员） | — |
| 4 | GET | `/srm/api-log/stat` | `srm:apiLog:list` | 区间汇总+预算对照 | — |
| 5 | POST | `/srm/api-log/export` | `srm:apiLog:export` | 导出对账表 | — |

### 图谱与项目 · 页面10 关联关系配置

- Controller：`SrmRelRuleController`，base_path `/srm/rel-rule`，共 9 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/rel-rule/list` | `srm:relRule:list` | 规则清单 | — |
| 2 | GET | `/srm/rel-rule/{id}` | `srm:relRule:query` | 规则详情 | — |
| 3 | POST | `/srm/rel-rule` | `srm:relRule:add` | 新增自定义维度 | — |
| 4 | PUT | `/srm/rel-rule` | `srm:relRule:edit` | 编辑（追加版本快照） | — |
| 5 | PUT | `/srm/rel-rule/toggle` | `srm:relRule:edit` | 启用/停用 | — |
| 6 | DELETE | `/srm/rel-rule/{ids}` | `srm:relRule:remove` | 软删（保留版本流水） | — |
| 7 | POST | `/srm/rel-rule/restore` | `srm:relRule:add` | 一键恢复推荐规则 | — |
| 8 | GET | `/srm/rel-rule/version/list` | `srm:relRule:list` | 版本流水 | — |
| 9 | GET | `/srm/rel-rule/refCount/{id}` | `srm:relRule:query` | 被线索引用数 | — |

### 成本与审计 · 页面11 字段映射配置

- Controller：`SrmFieldMappingController`，base_path `/srm/field-mapping`，共 8 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/field-mapping/list` | `srm:fieldMapping:list` | 映射清单 | — |
| 2 | POST | `/srm/field-mapping` | `srm:fieldMapping:add` | 新增映射 | — |
| 3 | PUT | `/srm/field-mapping` | `srm:fieldMapping:edit` | 修改映射 | — |
| 4 | PUT | `/srm/field-mapping/toggle` | `srm:fieldMapping:edit` | 启用/停用 | — |
| 5 | DELETE | `/srm/field-mapping/{ids}` | `srm:fieldMapping:remove` | 删除（内置禁删） | — |
| 6 | POST | `/srm/field-mapping/check` | `srm:fieldMapping:list` | 接入检查 | — |
| 7 | GET | `/srm/field-mapping/ext/list` | `srm:fieldMapping:list` | 自定义字段定义清单 | — |
| 8 | POST | `/srm/field-mapping/ext` | `srm:fieldMapping:add` | 新增自定义字段定义 | — |

### 监控与复核 · 页面12 人工复核

- Controller：`SrmClueReviewController`，base_path `/srm/clue-review`，共 6 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/clue-review/list` | `srm:clueReview:list` | 待复核线索 | — |
| 2 | GET | `/srm/clue-review/evidence/{clueId}` | `srm:clueReview:query` | 命中依据回放 | — |
| 3 | POST | `/srm/clue-review` | `srm:clueReview:add` | 提交复核结论 | — |
| 4 | PUT | `/srm/clue-review` | `srm:clueReview:edit` | 改判（追加） | — |
| 5 | GET | `/srm/clue-review/stat` | `srm:clueReview:list` | 复核进度统计 | — |
| 6 | POST | `/srm/clue-review/export` | `srm:clueReview:export` | 导出复核清单 | — |

### 监控与复核 · 页面13 报告版本

- Controller：`SrmReportVersionController`，base_path `/srm/report-version`，共 7 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/report-version/list` | `srm:reportVersion:list` | 版本清单 | — |
| 2 | GET | `/srm/report-version/{id}` | `srm:reportVersion:query` | 版本详情 | — |
| 3 | GET | `/srm/report-version/clue/{id}` | `srm:reportVersion:list` | 版本内线索（只读快照） | — |
| 4 | GET | `/srm/report-version/graph/{id}` | `srm:reportVersion:query` | 图谱回放 | — |
| 5 | PUT | `/srm/report-version/lock/{id}` | `srm:reportVersion:edit` | 锁定/解锁 | — |
| 6 | POST | `/srm/report-version/export/{id}` | `srm:reportVersion:export` | 导出快照 | — |
| 7 | GET | `/srm/report-version/diff/{idA}/{idB}` | `srm:reportVersion:query` | 两版本差异 | — |

### 查询与档案 · 页面14 批量导入

- Controller：`SrmImportBatchController`，base_path `/srm/supplier-import`，共 7 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | POST | `/srm/supplier-import/parse` | `srm:supplierImport:add` | 解析+三类校验 | — |
| 2 | GET | `/srm/supplier-import/list` | `srm:supplierImport:list` | 批次清单 | — |
| 3 | GET | `/srm/supplier-import/row/list` | `srm:supplierImport:list` | 行级校验结果 | — |
| 4 | POST | `/srm/supplier-import/confirm/{batchId}` | `srm:supplierImport:add` | 确认入库 | — |
| 5 | PUT | `/srm/supplier-import/row` | `srm:supplierImport:edit` | 单行修正/忽略 | — |
| 6 | POST | `/srm/supplier-import/exportError` | `srm:supplierImport:export` | 导出错误行 | — |
| 7 | GET | `/srm/supplier-import/{id}` | `srm:supplierImport:query` | 批次详情 | — |

### 监控与复核 · 页面15 监控范围与频率

- Controller：`SrmMonitorPolicyController`，base_path `/srm/monitor-policy`，共 7 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/monitor-policy/list` | `srm:monitorPolicy:list` | 策略清单 | — |
| 2 | GET | `/srm/monitor-policy/{id}` | `srm:monitorPolicy:query` | 策略详情 | — |
| 3 | POST | `/srm/monitor-policy` | `srm:monitorPolicy:add` | 新增（保存即注册调度） | — |
| 4 | PUT | `/srm/monitor-policy` | `srm:monitorPolicy:edit` | 修改（同步 sys_job cron） | — |
| 5 | PUT | `/srm/monitor-policy/toggle` | `srm:monitorPolicy:edit` | 启停（停用即暂停 job） | — |
| 6 | DELETE | `/srm/monitor-policy/{ids}` | `srm:monitorPolicy:remove` | 删除并移除调度 | — |
| 7 | GET | `/srm/monitor-policy/lastRun/{id}` | `srm:monitorPolicy:query` | 最近采集时间/快照数 | — |

### 成本与审计 · 页面16 预算与触发规则

- 本菜单对应 **3 个 Controller**，共 9 条接口（菜单 → Controller 一对多）：
  - `SrmBudgetPolicyController`，base_path `/srm/budget-policy`，5 条（#1、#2、#3、#6、#9）
  - `SrmTriggerRuleController`，base_path `/srm/trigger-rule`，2 条（#4、#5）
  - `SrmApiDictController`，base_path `/srm/api-dict`，2 条（#7、#8）
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/budget-policy/list` | `srm:budgetPolicy:list` | 预算参数 | — |
| 2 | PUT | `/srm/budget-policy` | `srm:budgetPolicy:edit` | 保存预算（@RepeatSubmit） | — |
| 3 | GET | `/srm/budget-policy/usage` | `srm:budgetPolicy:list` | 已用量/剩余额度 | — |
| 4 | GET | `/srm/trigger-rule/list` | `srm:budgetPolicy:list` | 五条触发规则 | — |
| 5 | PUT | `/srm/trigger-rule` | `srm:budgetPolicy:edit` | 编辑触发规则 | — |
| 6 | PUT | `/srm/budget-policy/cache-ttl` | `srm:budgetPolicy:edit` | 缓存有效期覆盖 | — |
| 7 | GET | `/srm/api-dict/list` | `srm:budgetPolicy:list` | 接口字典 | — |
| 8 | PUT | `/srm/api-dict/{id}` | `srm:budgetPolicy:edit` | 固化单价/计费/启停 | — |
| 9 | POST | `/srm/budget-policy/export` | `srm:budgetPolicy:export` | 导出配置 | — |

### 成本与审计 · 页面17 操作审计

- Controller：`SrmAuditLogController`，base_path `/srm/audit-log`，共 4 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/audit-log/list` | `srm:auditLog:list` | 审计清单 | — |
| 2 | GET | `/srm/audit-log/{id}` | `srm:auditLog:query` | 详情（五列+前后 JSON） | — |
| 3 | GET | `/srm/audit-log/trace/{traceId}` | `srm:auditLog:query` | 按链路聚合 | — |
| 4 | POST | `/srm/audit-log/export` | `srm:auditLog:export` | 导出 | — |

### 监控与复核 · 页面18 数据总览

- Controller：`SrmOverviewController`，base_path `/srm/overview`，共 4 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/overview/kpi` | `srm:overview:list` | KPI 卡 | — |
| 2 | GET | `/srm/overview/distribution` | `srm:overview:list` | 分布图 | — |
| 3 | GET | `/srm/overview/budget` | `srm:overview:list` | 预算进度（口径=页面16） | — |
| 4 | GET | `/srm/overview/recent` | `srm:overview:list` | 最近动态 | — |

### 成本与审计 · 页面19 数据中心

- Controller：`SrmDataCenterController`，base_path `/srm/data-center`，共 8 条接口
- 本菜单调用的天眼查编号：无（纯本地库）

| # | 方法 | 路径 | 权限串 | 功能 | 外调编号 |
|---:|---|---|---|---|---|
| 1 | GET | `/srm/data-center/backup/list` | `srm:dataCenter:list` | 备份记录 | — |
| 2 | POST | `/srm/data-center/backup` | `srm:dataCenter:add` | 生成完整 JSON 备份 | — |
| 3 | POST | `/srm/data-center/restore/{id}` | `srm:dataCenter:edit` | 按备份恢复（二次确认） | — |
| 4 | GET | `/srm/data-center/backup/download/{id}` | `srm:dataCenter:export` | 下载备份（校验 SHA256） | — |
| 5 | GET | `/srm/data-center/erp/list` | `srm:dataCenter:list` | 用友导出表清单 | — |
| 6 | POST | `/srm/data-center/erp/generate` | `srm:dataCenter:add` | 生成 ERP 入围 CSV | — |
| 7 | POST | `/srm/data-center/export/{type}` | `srm:dataCenter:export` | 分项 CSV 导出 | — |
| 8 | DELETE | `/srm/data-center/backup/{ids}` | `srm:dataCenter:remove` | 清理历史备份 | — |

## 三、天眼查编号 → 菜单 反查

| 编号 | 常量名 | 天眼查接口名 | 菜单（页面） |
|---|---|---|---|
| 458 | `FORMER_NAME` | 企业曾用名 | 查询与档案 / 页面3 供应商基本信息 |
| 818 | `BASIC_INFO` | 企业基本信息（含企业联系方式） | 查询与档案 / 页面1 供应商列表；查询与档案 / 页面3 供应商基本信息 |
| 819 | `BASIC_INFO_PERSON` | 企业基本信息（含主要人员） | 查询与档案 / 页面3 供应商基本信息 |
| 821 | `SHAREHOLDER` | 企业股东 | 图谱与项目 / 页面5 股权穿透图谱 |
| 839 | `ENFORCE` | 被执行人 | 查询与档案 / 页面4 供应商风险信息 |
| 846 | `ADMIN_PUNISH_ILLEGAL` | 严重违法 | 查询与档案 / 页面4 供应商风险信息 |
| 848 | `ADMIN_PUNISH_ABNORMAL` | 经营异常 | 查询与档案 / 页面4 供应商风险信息 |
| 851 | `TAX_OWED` | 欠税公告 | 查询与档案 / 页面4 供应商风险信息 |
| 871 | `ENFORCE_HIST` | 历史被执行人 | 查询与档案 / 页面4 供应商风险信息 |
| 872 | `DISHONEST` | 历史失信人 | 查询与档案 / 页面4 供应商风险信息 |
| 1002 | `JUDICIAL_SUMMARY` | 司法风险 | 查询与档案 / 页面4 供应商风险信息 |
| 1046 | `CONTACT` | 企业联系方式 | 查询与档案 / 页面3 供应商基本信息 |
| 1123 | `ACTUAL_CONTROLLER` | 疑似实际控制人 | 图谱与项目 / 页面5 股权穿透图谱；图谱与项目 / 页面6 幕后关系图谱；图谱与项目 / 页面7 项目关系网 |
| 1156 | `SUSPECT_RELATION` | 疑似关系发现 | 图谱与项目 / 页面7 项目关系网 |
| 1160 | `EQUITY_PENETRATION` | 股权穿透（10层） | 图谱与项目 / 页面5 股权穿透图谱；图谱与项目 / 页面7 项目关系网 |
| 1161 | `BACKSTAGE_RELATION` | 幕后关系（10层） | 图谱与项目 / 页面6 幕后关系图谱；图谱与项目 / 页面7 项目关系网 |

> 反查说明：同一编号可能被多个菜单复用（如 1123 同时服务页面5/6/7，1002 服务页面4/8/15）。
> 页面8「变更监控清单」/ 页面15「监控范围与频率」按需求文档说明为"按监控计划调用"编号 821/458/848/846/1002，
> 但本轮 134 条接口清单中该两页未标注外调（无异步/轮询任务接口），故实现里未直连；
> 待监控轮询任务排期时，按同一 `TycGateway.call(...)` 出口补挂即可，编号已在 `SrmApiId` 白名单内。
