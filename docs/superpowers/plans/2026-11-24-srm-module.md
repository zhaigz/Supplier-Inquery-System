# SRM Module Skeleton Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Add a JDK 21 `ruoyi-srm` module with DDL-derived persistence scaffolding for all 40 SRM tables and source-backed configuration inventories.

**Architecture:** A small Python generator parses the approved SRM DDL and emits Java domain/Mapper/Service files plus MyBatis XML. The module depends on `ruoyi-common`; the root reactor and admin dependency are updated so the module participates in the build while no business Controller is created.

**Tech Stack:** Java 21, Spring Boot 3.9.2 project conventions, MyBatis, Maven, Python 3 for deterministic generation.

**Spec:** `docs/superpowers/specs/2026-11-24-srm-module-design.md`

## Global Constraints

- Use only the supplied SRM schema, seed, page/API/table mapping, and requirements document.
- Use exact DDL table/column names and comments; do not invent fields, APIs, or API meanings.
- Entities extend `com.ruoyi.common.core.domain.BaseEntity`.
- No business Controller implementation is included in this scope.
- Existing user modifications in tracked files must remain intact.

## Review Focus

- DDL parser must identify exactly 40 `srm_` tables and preserve every column; validate with a generated manifest.
- BaseEntity columns must not be duplicated as entity fields while remaining present in XML result maps and SQL.
- XML namespaces and type aliases must match `com.ruoyi.srm` classes; validate XML count and namespace count.
- Seed-backed trigger, budget, cache and API values must be copied into documentation without speculative defaults.
- The four scheduled jobs must be listed with exact source-backed target and cron values, and no `sys_job` rows are inserted.

### Task 1: Add deterministic DDL generator and module wiring

**Files:**
- Create: `tools/generate_srm.py`
- Create: `ruoyi-srm/pom.xml`
- Modify: `pom.xml` (Java 21 and reactor module)
- Modify: `ruoyi-admin/pom.xml` (module dependency)

- [ ] Parse the supplied DDL into ordered table/column metadata and fail unless exactly 40 `srm_` tables are found.
- [ ] Emit module POM, domain, Mapper, IService, ServiceImpl and XML files using the fixed mappings in the design.
- [ ] Add the module to the Maven reactor and admin dependency list without changing existing unrelated edits.
- [ ] Run the generator and verify the expected file counts.

### Task 2: Add SRM constants and enums

**Files:**
- Create: `ruoyi-srm/src/main/java/com/ruoyi/srm/constant/**`
- Create: `ruoyi-srm/src/main/java/com/ruoyi/srm/enums/**`

- [ ] Define task status values, error codes 60001 through 60012, `call_result`, and cache `env` values only from the input material.
- [ ] Add a compact source note in each class or package documentation where a value is specified by seed/documentation.

### Task 3: Add source-backed system inventories

**Files:**
- Create: `docs/srm-menu-permissions.md`
- Create: `docs/srm-system-parameters.md`

- [ ] Copy the 19 menu routes and permission prefixes from the page/API/table mapping.
- [ ] Record the available `sys_dict_*` and `sys_config` parameter names/defaults from the seed and specified chapters, marking unspecified values as pending rather than inventing them.
- [ ] Record all four `sys_job` invoke targets and cron expressions from the supplied material; do not insert rows.

### Task 4: Verify generated source and build

**Files:**
- Modify only generated files if verification exposes a generator defect.

- [ ] Run a manifest checker for table count, field count, Java property coverage, Mapper namespace coverage and XML result-map coverage.
- [ ] Run Maven compilation for `ruoyi-srm` and the root reactor with tests skipped.
- [ ] Inspect `git diff` and confirm no business Controller exists and no unrelated user changes were overwritten.
