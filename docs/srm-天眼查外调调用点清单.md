# SRM 天眼查外调调用点清单（源码级）

> 由 `tools/gen_srm_tyc_doc.py` 从源码扫描生成，与上表同源；改代码后重新生成即可对账。

| # | 文件 | 行号 | 所在方法 | 调用编号 |
|---|---|---:|---|---|
| 1 | `SrmEquityGraphController.java` | 181 | `expand` | 1160 |
| 2 | `SrmEquityGraphController.java` | 297 | `call` | 821、1123、1160 |
| 3 | `SrmGraphBackstageController.java` | 301 | `call` | 1123、1161 |
| 4 | `SrmProjectController.java` | 275 | `analysisInit` | 1156 |
| 5 | `SrmProjectController.java` | 277 | `analysisInit` | 1161 |
| 6 | `SrmProjectController.java` | 279 | `analysisInit` | 1123 |
| 7 | `SrmProjectController.java` | 281 | `analysisInit` | 1160 |
| 8 | `SrmSupplierController.java` | 161 | `enrich` | 818 |
| 9 | `SrmSupplierDetailController.java` | 107 | `enrich` | 818 |
| 10 | `SrmSupplierDetailController.java` | 109 | `enrich` | 819 |
| 11 | `SrmSupplierDetailController.java` | 261 | `callSubject` | 458、1046 |
| 12 | `SrmSupplierRiskController.java` | 320 | `call` | 839、846、848、851、871、872、1002 |
| 13 | `SrmSupplierRiskController.java` | 345 | `callSubject` | 839、846、848、851、871、872、1002 |

共 13 个调用点，覆盖 16 个外调编号。
