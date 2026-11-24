# SRM 模块骨架与 DDL 驱动持久层设计

## 目标
在 RuoYi-Vue Spring Boot 3.9.2 工程中新增 `ruoyi-srm` Maven 模块，包根为 `com.ruoyi.srm`，面向已存在的 `srm` 数据库生成 40 张 `srm_` 表的领域对象、Mapper、XML 和基础 Service 接口/实现，供后续业务接口开发使用。

## 范围
- 新增 `ruoyi-srm` 模块及 `com.ruoyi.srm.controller/service/impl/mapper/domain` 包结构。
- 依据 `srm_schema_v1.0.sql` 生成 40 个 domain、40 个 Mapper、40 个 XML；列名、DDL 注释、SQL 类型以 DDL 为唯一来源。
- 实体继承 `BaseEntity`；DDL 中的 RuoYi 基础审计列映射到父类，XML 保留完整列映射。
- 为每张表生成基础 CRUD Mapper、IService、ServiceImpl；不生成业务 Controller。
- 生成任务状态、错误码 60001—60012、`call_result`、`cache env` 常量/枚举。
- 输出菜单权限、`sys_dict_*`、`sys_config`、4 个 `sys_job` 任务的 `invoke_target` 与 cron 清单。

## 非范围
- 不实现 134 个业务接口。
- 不修改数据库，不写入 `sys_menu`、`sys_dict_*`、`sys_config`、`sys_job`。
- 不根据记忆或未列入输入切片的资料增加字段、接口或接口编号含义。

## 约束与实现决定
- Java 编译目标使用 JDK 21；RuoYi 既有模块保持原结构，仅把父工程 Java 版本设为 21。
- 生成器使用 DDL 解析结果，避免手工复制造成字段漂移；生成后用独立校验脚本核对 40 张表、字段和 XML 映射。
- Java 类型映射固定为：整数使用 `Long`/`Integer`，`decimal` 使用 `BigDecimal`，日期时间使用 `Date`，字符/文本/JSON 使用 `String`；XML 的 `jdbcType` 使用 DDL 基础 SQL 类型。
- Service 只提供通用 CRUD 契约，业务查询条件和接口签名留到后续业务实现。

## 产物
- `ruoyi-srm/pom.xml`
- `ruoyi-srm/src/main/java/com/ruoyi/srm/domain/**`
- `ruoyi-srm/src/main/java/com/ruoyi/srm/mapper/**`
- `ruoyi-srm/src/main/java/com/ruoyi/srm/service/**`
- `ruoyi-srm/src/main/resources/mapper/srm/**`
- `ruoyi-srm/src/main/java/com/ruoyi/srm/constant/**`
- `ruoyi-srm/src/main/java/com/ruoyi/srm/enums/**`
- `docs/srm-menu-permissions.md`
- `docs/srm-system-parameters.md`
- `tools/generate_srm.py`（可重复生成与校验）
