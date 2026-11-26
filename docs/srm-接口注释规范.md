# SRM 接口注释规范（ruoyi-srm）

本规范保证 134 条接口在 Apifox / Swagger 等文档工具中显示**中文功能名**，
而不是退化成 `list` / `getInfo` / `add` 这类裸方法名。

## 1. 唯一数据源

方法、路径、权限串、功能、外调编号**逐字**取自：

- `技术方案/接口清单_全量.md`
- `技术方案/api_manifest_v1.0.json`（机器可读版，134 条）

**不得增删条目**。缺失的接口写进 `docs/srm-接口待拍板清单.md`，不自行补。

## 2. 方法级注释格式

每个映射方法（`@GetMapping` / `@PostMapping` / `@PutMapping` / `@DeleteMapping` /
`@RequestMapping`）上方**必须**有 Javadoc，且**首行正文 = 清单 `feature` 字段逐字一致**。
文档工具据此生成接口名。

```java
/**
 * 分页列表（八列）
 *
 * <p>页面 1「供应商列表」；权限 srm:supplier:list；外调 无（纯本地库，不发起外调）
 */
@PreAuthorize("@ss.hasPermi('srm:supplier:list')")
@GetMapping("/list")
public TableDataInfo list(Supplier query)
```

要点：

- 第一行功能名：直接对应清单 `feature`，**逐字一致**，不加前缀、不改写。
- 第二段固定给出：页面编号 + 页面名、权限串、外调编号。
- 外调渲染：`—` → `无（纯本地库，不发起外调）`；否则为
  `天眼查 818（BASIC_INFO）、819（BASIC_INFO_PERSON）`，并按语义追加
  `（异步触发）` 或 `（读取本地沉淀数据，不直发外调）`。

## 3. 类级注释格式

类级 Javadoc 说明该 Controller 覆盖的页面与接口条数：

```java
/**
 * 页面1「供应商列表」接口，共 8 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
```

## 4. 外调调用点注释格式（天眼查）

方法级 Javadoc 说明"这个方法用到哪些外调编号"，但不够——真正发起 HTTP 的是
`TycGateway.call(...)` 这一行。因此**每一个 `tycGateway.call(...)` 调用点上方必须紧贴一行**：

```java
// TYC-CALL 外调天眼查 1160（EQUITY_PENETRATION · 股权穿透（10层））4.00 元/次，发起穿透任务必调
calls.add(call(SrmApiId.EQUITY_PENETRATION, subjectKey, params, ctx));
```

规则：

- 前缀固定为 `// TYC-CALL 外调天眼查 `，后面必须是 `<编号>（<常量名> · <天眼查接口名>）`，
  编号 / 接口名与《天眼查API接口.xlsx》逐字一致。
- 一行只写一个编号；一次调用涉及多个编号时（如刷新全量风险的 7 段），逐行并列。
- 按需触发/异步触发等语义追加在括号后，例如 `refresh=true 才触发`、`（异步触发）`。
- 动态 `apiId` 的私有 helper（`call(int apiId, ...)` / `callSubject(int apiId, ...)`）无法在
  调用行内看出编号，必须把该 helper 全部可能取值的编号逐行列出，对账脚本会反查同文件内的实参并校验是否漏标。
- 网关内部 `TycGateway` 打印 `SrmApiId.label(apiId)`（`编号（常量名 · 接口名）`），日志与
  `srm_api_call_log` 都能定位到具体天眼查接口。

## 5. 生成与校验

```powershell
python tools/annotate_srm_controllers.py           # 预览（幂等，应为 0 处）
python tools/annotate_srm_controllers.py --apply   # 写入源码
python tools/check_srm_controllers.py              # 必须 ERRORS(0)
python tools/gen_srm_tyc_doc.py                    # 生成天眼查外调对照表 + 校验调用点注释
python tools/gen_srm_tyc_doc.py --check            # 只校验，不落盘
```

产出文档：

- `docs/srm-天眼查外调接口对照表.md` —— 编号 / 常量名 / 接口名 / 分类 / 单价 / 文档 URL / 引用页面 / 调用点数。
- `docs/srm-天眼查外调调用点清单.md` —— 源码级调用点清单（文件 + 行号 + 方法 + 编号）。
```

`check_srm_controllers.py` 已把"注释规范"纳入门禁：

- A. `SrmApiId` 常量集合 == 清单外调编号去重集合（不增不减）。
- B. 方法内引用的 `SrmApiId` 常量必须落在该接口清单行的外调集合内。
- C. 外调接口必须引用与外调集合完全一致的常量；沉淀接口不得多引用。
- D. **每条方法级 Javadoc 首行与清单 `feature` 逐字一致**；缺失或改写均报错。

## 5. 编码约定

- 控制器源码统一 **UTF-8 无 BOM**，换行保持 LF。
- 批量改写后需 `mvn -o -q -pl ruoyi-admin -am compile -DskipTests` 通过。
