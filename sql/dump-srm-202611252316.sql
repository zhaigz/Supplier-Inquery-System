-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: srm
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `form_col_num` int DEFAULT '1' COMMENT '表单布局（单列 双列 三列）',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Blob类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日历信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Cron类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint NOT NULL COMMENT '触发的时间',
  `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
  `priority` int NOT NULL COMMENT '优先级',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='已触发的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组名',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储的悲观锁信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='暂停的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调度器状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简单触发器的信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='同步机制的行锁表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='触发器详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_analysis_task`
--

DROP TABLE IF EXISTS `srm_analysis_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_analysis_task` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `task_no` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务号（前端轮询凭据）',
  `trace_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '同一次业务动作下的多任务归组',
  `task_type` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'equity/backstage/shortest/project/monitor',
  `biz_scene` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'page5/page6/page7/page8/page13',
  `project_id` bigint unsigned DEFAULT NULL,
  `report_version_id` bigint unsigned DEFAULT NULL,
  `subject_key` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '目标主体',
  `source_subject` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `target_subject` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `rel_scope` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关系口径：选中维度集合',
  `direction` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '穿透方向 up/down',
  `depth` tinyint DEFAULT NULL COMMENT '深度/层数 1-10',
  `ratio_min` decimal(9,4) DEFAULT NULL,
  `ratio_max` decimal(9,4) DEFAULT NULL,
  `rule_version_no` int DEFAULT NULL,
  `min_weight` tinyint DEFAULT NULL,
  `include_bookkeeping` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '1 包含 / 2 不包含代记账公司电话关系',
  `task_status` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'QUEUED/RUNNING/SUCCESS/FAIL/CANCELED',
  `poll_count` int DEFAULT NULL,
  `first_trigger_at` datetime DEFAULT NULL,
  `billed_at` datetime DEFAULT NULL,
  `bill_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `unit_consumed` decimal(10,2) DEFAULT NULL,
  `est_unit` decimal(10,2) DEFAULT NULL,
  `progress_step` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `err_code` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `err_msg` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `result_count` int DEFAULT NULL COMMENT '结果规模：节点数/线索数',
  `start_time` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `cost_ms` int DEFAULT NULL,
  `env` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_task_no` (`task_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1505 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='分析任务（穿透/幕后/项目/监控）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_analysis_task`
--

LOCK TABLES `srm_analysis_task` WRITE;
/*!40000 ALTER TABLE `srm_analysis_task` DISABLE KEYS */;
INSERT INTO `srm_analysis_task` VALUES (1101,'MOCK-EQ-20261120-001','MOCKTRACE-EQ-001','equity','page5',NULL,NULL,'91310115MA1K10001A','华晟控股集团有限公司','华晟控股集团有限公司','ALL','both',5,0.0000,100.0000,1,1,'1','SUCCESS',3,'2026-11-20 10:00:00','2026-11-20 10:00:36','1',12.00,15.00,'DONE',NULL,NULL,23,'2026-11-20 10:00:00','2026-11-20 10:00:36',36210,'prod','admin','2026-11-20 10:00:00','2026-11-20 10:00:36'),(1102,'MOCK-BS-20261120-001','MOCKTRACE-BS-001','backstage','page6',NULL,NULL,'91310115MA1K10001A','华晟控股集团有限公司','华晟控股集团有限公司','ALL','both',3,0.0000,100.0000,1,3,'1','SUCCESS',2,'2026-11-20 11:20:00','2026-11-20 11:20:21','1',8.50,10.00,'DONE',NULL,NULL,16,'2026-11-20 11:20:00','2026-11-20 11:20:21',21180,'prod','admin','2026-11-20 11:20:00','2026-11-20 11:20:21'),(1103,'MOCK-BS-20261120-002','MOCKTRACE-BS-002','backstage','page6',NULL,NULL,'91310104MA1K10004D','恒信供应链管理有限公司','恒信供应链管理有限公司','ALL','both',3,0.0000,100.0000,1,3,'1','SUCCESS',1,'2026-11-20 13:05:00','2026-11-20 13:05:13','1',6.00,10.00,'DONE',NULL,NULL,10,'2026-11-20 13:05:00','2026-11-20 13:05:13',13400,'prod','admin','2026-11-20 13:05:00','2026-11-20 13:05:13'),(1201,'MOCK-PRJ-20261121-001','MOCKTRACE-PRJ-001','project','page7',1001,1001,'PRJ-2026-001','华晟控股集团有限公司',NULL,'ALL','both',5,0.0000,100.0000,1,1,'1','SUCCESS',4,'2026-11-21 09:00:00','2026-11-21 09:02:10','1',24.00,30.00,'DONE',NULL,NULL,6,'2026-11-21 09:00:00','2026-11-21 09:02:10',130500,'prod','admin','2026-11-21 09:00:00','2026-11-21 09:02:10'),(1202,'MOCK-PRJ-20261121-002','MOCKTRACE-PRJ-002','project','page7',1002,1002,'PRJ-2026-002','恒信供应链管理有限公司',NULL,'ALL','both',4,0.0000,100.0000,1,3,'1','SUCCESS',2,'2026-11-21 14:10:00','2026-11-21 14:11:32','1',18.00,30.00,'DONE',NULL,NULL,4,'2026-11-21 14:10:00','2026-11-21 14:11:32',92100,'prod','admin','2026-11-21 14:10:00','2026-11-21 14:11:32'),(1203,'MOCK-PRJ-20261122-001','MOCKTRACE-PRJ-003','project','page7',1003,1003,'PRJ-2026-003','华晟控股集团有限公司',NULL,'ALL','both',6,0.0000,100.0000,1,1,'1','SUCCESS',5,'2026-11-22 08:30:00','2026-11-22 08:33:05','1',36.00,40.00,'DONE',NULL,NULL,5,'2026-11-22 08:30:00','2026-11-22 08:33:05',185600,'prod','admin','2026-11-22 08:30:00','2026-11-22 08:33:05'),(1301,'MOCK-EQ-20261118-001','MOCKTRACE-1301','equity','page5',NULL,NULL,'91310115MA1K10001A','华晟控股集团有限公司','华晟控股集团有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',13.00,10.00,'DONE',NULL,NULL,11,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1302,'MOCK-EQ-20261118-002','MOCKTRACE-1302','equity','page5',NULL,NULL,'91310115MA1K10002B','华晟建筑工程有限公司','华晟建筑工程有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',14.00,10.00,'DONE',NULL,NULL,6,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1303,'MOCK-EQ-20261118-003','MOCKTRACE-1303','equity','page5',NULL,NULL,'91310116MA1K10003C','华晟智能装备制造有限公司','华晟智能装备制造有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',15.00,10.00,'DONE',NULL,NULL,8,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1304,'MOCK-EQ-20261118-004','MOCKTRACE-1304','equity','page5',NULL,NULL,'91310104MA1K10004D','恒信供应链管理有限公司','恒信供应链管理有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',16.00,10.00,'DONE',NULL,NULL,7,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1305,'MOCK-EQ-20261118-005','MOCKTRACE-1305','equity','page5',NULL,NULL,'91310117MA1K10005E','鑫达新材料科技有限公司','鑫达新材料科技有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',17.00,10.00,'DONE',NULL,NULL,5,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1306,'MOCK-EQ-20261118-006','MOCKTRACE-1306','equity','page5',NULL,NULL,'91310105MA1K10006F','鸿远物流服务有限公司','鸿远物流服务有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',18.00,10.00,'DONE',NULL,NULL,3,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1307,'MOCK-EQ-20261118-007','MOCKTRACE-1307','equity','page5',NULL,NULL,'91310113MA1K10007G','海通机电设备有限公司','海通机电设备有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',19.00,10.00,'DONE',NULL,NULL,5,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1308,'MOCK-EQ-20261118-008','MOCKTRACE-1308','equity','page5',NULL,NULL,'91310118MA1K10008H','中启环保工程有限公司','中启环保工程有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',20.00,10.00,'DONE',NULL,NULL,6,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1309,'MOCK-EQ-20261118-009','MOCKTRACE-1309','equity','page5',NULL,NULL,'91310114MA1K10009J','联志电子科技有限公司','联志电子科技有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',21.00,10.00,'DONE',NULL,NULL,5,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1310,'MOCK-EQ-20261118-010','MOCKTRACE-1310','equity','page5',NULL,NULL,'91310120MA1K10010K','广盛建设劳务有限公司','广盛建设劳务有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',12.00,10.00,'DONE',NULL,NULL,3,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1311,'MOCK-EQ-20261118-011','MOCKTRACE-1311','equity','page5',NULL,NULL,'91310109MA1K10011L','瑞昇信息技术有限公司','瑞昇信息技术有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',13.00,10.00,'DONE',NULL,NULL,6,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1312,'MOCK-EQ-20261118-012','MOCKTRACE-1312','equity','page5',NULL,NULL,'91310110MA1K10012M','天成检测认证有限公司','天成检测认证有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',14.00,10.00,'DONE',NULL,NULL,3,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1313,'MOCK-EQ-20261118-013','MOCKTRACE-1313','equity','page5',NULL,NULL,'91310115MA1K10013N','蓝湾海洋工程有限公司','蓝湾海洋工程有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',15.00,10.00,'DONE',NULL,NULL,6,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1314,'MOCK-EQ-20261118-014','MOCKTRACE-1314','equity','page5',NULL,NULL,'91310116MA1K10014P','凯越精密机械有限公司','凯越精密机械有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',16.00,10.00,'DONE',NULL,NULL,7,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1315,'MOCK-EQ-20261118-015','MOCKTRACE-1315','equity','page5',NULL,NULL,'91310104MA1K10015Q','云通数据科技有限公司','云通数据科技有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',17.00,10.00,'DONE',NULL,NULL,7,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1316,'MOCK-EQ-20261118-016','MOCKTRACE-1316','equity','page5',NULL,NULL,'91310115MA1K10016R','瑞泽实业集团有限公司','瑞泽实业集团有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',18.00,10.00,'DONE',NULL,NULL,12,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1317,'MOCK-EQ-20261118-017','MOCKTRACE-1317','equity','page5',NULL,NULL,'91310115MA1K10017S','瑞泽建筑工程有限公司','瑞泽建筑工程有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',19.00,10.00,'DONE',NULL,NULL,7,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1318,'MOCK-EQ-20261118-018','MOCKTRACE-1318','equity','page5',NULL,NULL,'91310117MA1K10018T','瑞泽新能源科技有限公司','瑞泽新能源科技有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',20.00,10.00,'DONE',NULL,NULL,7,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1319,'MOCK-EQ-20261118-019','MOCKTRACE-1319','equity','page5',NULL,NULL,'91310105MA1K10019U','上海泽信国际贸易有限公司','上海泽信国际贸易有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',21.00,10.00,'DONE',NULL,NULL,6,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1320,'MOCK-EQ-20261118-020','MOCKTRACE-1320','equity','page5',NULL,NULL,'91310110MA1K10020V','瑞泽人力资源有限公司','瑞泽人力资源有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',12.00,10.00,'DONE',NULL,NULL,6,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1321,'MOCK-EQ-20261118-021','MOCKTRACE-1321','equity','page5',NULL,NULL,'91310114MA1K10021W','中泰智能仓储有限公司','中泰智能仓储有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',13.00,10.00,'DONE',NULL,NULL,10,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1322,'MOCK-EQ-20261118-022','MOCKTRACE-1322','equity','page5',NULL,NULL,'91310117MA1K10022X','华辰智造科技有限公司','华辰智造科技有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',14.00,10.00,'DONE',NULL,NULL,8,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1401,'MOCK-BS-20261118-001','MOCKTRACE-1401','backstage','page6',NULL,NULL,'91310115MA1K10001A','华晟控股集团有限公司','华晟控股集团有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',10.00,10.00,'DONE',NULL,NULL,11,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1402,'MOCK-BS-20261118-002','MOCKTRACE-1402','backstage','page6',NULL,NULL,'91310115MA1K10002B','华晟建筑工程有限公司','华晟建筑工程有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',11.00,10.00,'DONE',NULL,NULL,7,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1403,'MOCK-BS-20261118-003','MOCKTRACE-1403','backstage','page6',NULL,NULL,'91310116MA1K10003C','华晟智能装备制造有限公司','华晟智能装备制造有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',12.00,10.00,'DONE',NULL,NULL,10,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1404,'MOCK-BS-20261118-004','MOCKTRACE-1404','backstage','page6',NULL,NULL,'91310104MA1K10004D','恒信供应链管理有限公司','恒信供应链管理有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',13.00,10.00,'DONE',NULL,NULL,9,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1405,'MOCK-BS-20261118-005','MOCKTRACE-1405','backstage','page6',NULL,NULL,'91310117MA1K10005E','鑫达新材料科技有限公司','鑫达新材料科技有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',14.00,10.00,'DONE',NULL,NULL,6,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1406,'MOCK-BS-20261118-006','MOCKTRACE-1406','backstage','page6',NULL,NULL,'91310105MA1K10006F','鸿远物流服务有限公司','鸿远物流服务有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',15.00,10.00,'DONE',NULL,NULL,5,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1407,'MOCK-BS-20261118-007','MOCKTRACE-1407','backstage','page6',NULL,NULL,'91310113MA1K10007G','海通机电设备有限公司','海通机电设备有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',16.00,10.00,'DONE',NULL,NULL,5,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1408,'MOCK-BS-20261118-008','MOCKTRACE-1408','backstage','page6',NULL,NULL,'91310118MA1K10008H','中启环保工程有限公司','中启环保工程有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',10.00,10.00,'DONE',NULL,NULL,5,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1409,'MOCK-BS-20261118-009','MOCKTRACE-1409','backstage','page6',NULL,NULL,'91310114MA1K10009J','联志电子科技有限公司','联志电子科技有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',11.00,10.00,'DONE',NULL,NULL,6,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1410,'MOCK-BS-20261118-010','MOCKTRACE-1410','backstage','page6',NULL,NULL,'91310120MA1K10010K','广盛建设劳务有限公司','广盛建设劳务有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',12.00,10.00,'DONE',NULL,NULL,4,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1411,'MOCK-BS-20261118-011','MOCKTRACE-1411','backstage','page6',NULL,NULL,'91310109MA1K10011L','瑞昇信息技术有限公司','瑞昇信息技术有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',13.00,10.00,'DONE',NULL,NULL,5,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1412,'MOCK-BS-20261118-012','MOCKTRACE-1412','backstage','page6',NULL,NULL,'91310110MA1K10012M','天成检测认证有限公司','天成检测认证有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',14.00,10.00,'DONE',NULL,NULL,4,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1413,'MOCK-BS-20261118-013','MOCKTRACE-1413','backstage','page6',NULL,NULL,'91310115MA1K10013N','蓝湾海洋工程有限公司','蓝湾海洋工程有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',15.00,10.00,'DONE',NULL,NULL,8,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1414,'MOCK-BS-20261118-014','MOCKTRACE-1414','backstage','page6',NULL,NULL,'91310116MA1K10014P','凯越精密机械有限公司','凯越精密机械有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',16.00,10.00,'DONE',NULL,NULL,6,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1415,'MOCK-BS-20261118-015','MOCKTRACE-1415','backstage','page6',NULL,NULL,'91310104MA1K10015Q','云通数据科技有限公司','云通数据科技有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',10.00,10.00,'DONE',NULL,NULL,7,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1416,'MOCK-BS-20261118-016','MOCKTRACE-1416','backstage','page6',NULL,NULL,'91310115MA1K10016R','瑞泽实业集团有限公司','瑞泽实业集团有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',11.00,10.00,'DONE',NULL,NULL,12,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1417,'MOCK-BS-20261118-017','MOCKTRACE-1417','backstage','page6',NULL,NULL,'91310115MA1K10017S','瑞泽建筑工程有限公司','瑞泽建筑工程有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',12.00,10.00,'DONE',NULL,NULL,8,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1418,'MOCK-BS-20261118-018','MOCKTRACE-1418','backstage','page6',NULL,NULL,'91310117MA1K10018T','瑞泽新能源科技有限公司','瑞泽新能源科技有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',13.00,10.00,'DONE',NULL,NULL,8,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1419,'MOCK-BS-20261118-019','MOCKTRACE-1419','backstage','page6',NULL,NULL,'91310105MA1K10019U','上海泽信国际贸易有限公司','上海泽信国际贸易有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',14.00,10.00,'DONE',NULL,NULL,8,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1420,'MOCK-BS-20261118-020','MOCKTRACE-1420','backstage','page6',NULL,NULL,'91310110MA1K10020V','瑞泽人力资源有限公司','瑞泽人力资源有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',15.00,10.00,'DONE',NULL,NULL,8,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1421,'MOCK-BS-20261118-021','MOCKTRACE-1421','backstage','page6',NULL,NULL,'91310114MA1K10021W','中泰智能仓储有限公司','中泰智能仓储有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',16.00,10.00,'DONE',NULL,NULL,11,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1422,'MOCK-BS-20261118-022','MOCKTRACE-1422','backstage','page6',NULL,NULL,'91310117MA1K10022X','华辰智造科技有限公司','华辰智造科技有限公司','ALL','both',4,0.0000,100.0000,1,1,'1','SUCCESS',2,'2026-11-18 15:00:00','2026-11-18 15:00:00','1',10.00,10.00,'DONE',NULL,NULL,7,'2026-11-18 15:00:00','2026-11-18 15:00:00',18600,'prod','admin','2026-11-18 15:00:00','2026-11-18 15:00:00'),(1501,'MOCK-PRJ-20261122-004','MOCKTRACE-PRJ-004','project','page7',1004,1004,'PRJ-2026-004','华晟控股集团有限公司',NULL,'ALL','both',5,0.0000,100.0000,1,1,'1','SUCCESS',6,'2026-11-22 10:00:00','2026-11-22 10:14:20','1',45.00,60.00,'DONE',NULL,NULL,10,'2026-11-22 10:00:00','2026-11-22 10:14:20',862100,'prod','admin','2026-11-22 10:00:00','2026-11-22 10:14:20'),(1502,'MOCK-PRJ-20261122-005','MOCKTRACE-PRJ-005','project','page7',1005,1005,'PRJ-2026-005','瑞昇信息技术有限公司',NULL,'ALL','both',6,0.0000,100.0000,1,3,'1','SUCCESS',4,'2026-11-22 11:00:00','2026-11-22 11:04:10','1',30.00,45.00,'DONE',NULL,NULL,6,'2026-11-22 11:00:00','2026-11-22 11:04:10',402300,'prod','admin','2026-11-22 11:00:00','2026-11-22 11:04:10'),(1503,'MOCK-PRJ-20261123-006','MOCKTRACE-PRJ-006','project','page7',1006,1006,'PRJ-2026-006','瑞泽实业集团有限公司',NULL,'ALL','both',6,0.0000,100.0000,1,1,'1','SUCCESS',3,'2026-11-23 09:00:00','2026-11-23 09:03:40','1',42.00,55.00,'DONE',NULL,NULL,9,'2026-11-23 09:00:00','2026-11-23 09:03:40',220400,'prod','admin','2026-11-23 09:00:00','2026-11-23 09:03:40'),(1504,'PR20261125223352341',NULL,'project','page7',1001,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'SUCCESS',NULL,NULL,NULL,'0',0.00,NULL,'DONE',NULL,NULL,0,NULL,'2026-11-25 22:33:53',NULL,'prod','admin','2026-11-25 22:33:52','2026-11-25 22:33:53');
/*!40000 ALTER TABLE `srm_analysis_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_api_cache_key`
--

DROP TABLE IF EXISTS `srm_api_cache_key`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_api_cache_key` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `cache_key` char(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '缓存键 md5(api_id + 标准化主体标识 + 参数指纹)，页面5/6/7 有方向/层数/比例/口径入参，只按信用代码缓存会错命中',
  `api_id` int NOT NULL COMMENT '接口编号',
  `credit_code` varchar(18) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '统一社会信用代码（页面5/6 逐字要求按信用代码缓存，本字段保留该口径）',
  `subject_key` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标准化主体标识：信用代码优先，缺失时用规范化企业名或 tyc 公司id',
  `param_json` json DEFAULT NULL COMMENT '参数指纹原文（穿透方向/层数/比例上下限/关系口径/深度/源与目标主体）',
  `raw_id` bigint unsigned NOT NULL COMMENT '结果指向 srm_api_raw.id',
  `hit_count` int NOT NULL DEFAULT '0' COMMENT '命中次数（缓存节省量统计，页面16 缓存命中不重复计量）',
  `expire_at` datetime NOT NULL COMMENT '过期时间 = fetched_at + 有效时长',
  `fetched_at` datetime NOT NULL,
  `env` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'prod' COMMENT 'prod/test：测试额度与正式额度的结果分桶，避免测试数据污染正式结论',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_cache_key` (`cache_key`),
  KEY `idx_subject` (`api_id`,`subject_key`,`expire_at`),
  KEY `idx_raw` (`raw_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='外部接口结果缓存索引（省钱主路径）：调用前先查本表，未过期直接读 raw，不新发请求；空结果同样落键（负缓存），避免反复计费；页面2/3/4/8 的"缓存命中直接读本地库"由本表判定';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_api_cache_key`
--

LOCK TABLES `srm_api_cache_key` WRITE;
/*!40000 ALTER TABLE `srm_api_cache_key` DISABLE KEYS */;
INSERT INTO `srm_api_cache_key` VALUES (1001,'a1b2c3d4e5f60001000000000000c001',821,'91310115MA1K10001A','91310115MA1K10001A:depth3','{\"depth\": 3}',1001,12,'2026-12-20 10:00:12','2026-11-20 10:00:12','prod'),(1002,'a1b2c3d4e5f60002000000000000c002',1160,'91310104MA1K10004D','91310104MA1K10004D:up','{\"direction\": \"up\"}',1002,9,'2026-12-20 10:00:18','2026-11-20 10:00:18','prod'),(1003,'a1b2c3d4e5f60003000000000000c003',1156,'91310104MA1K10004D','91310104MA1K10004D:contact','{\"type\": \"contact\"}',1003,21,'2026-12-20 11:20:06','2026-11-20 11:20:06','prod'),(1004,'a1b2c3d4e5f60004000000000000c004',1123,'91310115MA1K10001A','91310115MA1K10001A:controller','{\"type\": \"actualController\"}',1004,7,'2026-12-20 10:00:24','2026-11-20 10:00:24','prod'),(1005,'a1b2c3d4e5f60005000000000000c005',1158,'91310118MA1K10008H','91310118MA1K10008H:staff','{\"type\": \"staff\"}',1005,15,'2026-12-22 08:31:02','2026-11-22 08:31:02','prod'),(1006,'a1b2c3d4e5f60006000000000000c006',1161,'91310105MA1K10006F','91310105MA1K10006F:backstage','{\"type\": \"backstage\"}',1006,5,'2026-12-20 11:20:11','2026-11-20 11:20:11','prod');
/*!40000 ALTER TABLE `srm_api_cache_key` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_api_call_log`
--

DROP TABLE IF EXISTS `srm_api_call_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_api_call_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `trace_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '全链路号：一次项目分析会产生多条调用，用同一个 trace 串起来',
  `task_id` bigint unsigned DEFAULT NULL COMMENT '关联 srm_analysis_task.id（异步任务发起的调用）',
  `call_time` datetime NOT NULL COMMENT '调用时间',
  `user_id` bigint DEFAULT NULL COMMENT '调用人（系统账号）',
  `user_name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '调用人名称冗余，避免账号变更导致日志不可读',
  `dept_id` bigint DEFAULT NULL COMMENT '所属部门（来自系统侧账号信息）',
  `dept_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `source_ip` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '来源 IP（需求新增维度）',
  `terminal_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '终端标识：区分同一账号多终端',
  `api_id` int DEFAULT NULL COMMENT '接口编号',
  `api_name` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接口名称',
  `subject_key` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '调用范围/入参摘要（企业主体）',
  `param_digest` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '入参摘要，不含敏感值',
  `page_no` int DEFAULT NULL COMMENT '分页接口当前页',
  `call_result` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用结果：成功 / 失败 / 被拦截',
  `block_reason` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '被拦截原因：日预算超限/月预算超限/并发超限/套餐未含该接口/主体不存在',
  `resp_msg` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cost_ms` int DEFAULT NULL COMMENT '响应耗时',
  `unit_consumed` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '消耗调用单元（本条请求实际计费次数，缓存命中与拦截记 0）',
  `cache_hit` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否缓存命中：命中则不计量、不新发请求',
  `env` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'prod' COMMENT 'prod/test：测试额度调用单独统计，不占正式额度',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_time` (`call_time`),
  KEY `idx_api_time` (`api_id`,`call_time`),
  KEY `idx_user_time` (`user_id`,`call_time`),
  KEY `idx_ip` (`source_ip`,`call_time`),
  KEY `idx_result` (`call_result`,`call_time`),
  KEY `idx_trace` (`trace_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1068 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='接口后台调用信息（页面9，11 项逐字建模）。APPEND-ONLY，与页面17 业务操作审计严格分列；只记录调用事实，不落接口业务字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_api_call_log`
--

LOCK TABLES `srm_api_call_log` WRITE;
/*!40000 ALTER TABLE `srm_api_call_log` DISABLE KEYS */;
INSERT INTO `srm_api_call_log` VALUES (1001,'MOCKTRACE-EQ-001',1101,'2026-11-20 10:00:12',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',821,'股权穿透（向上/向下）','91310115MA1K10001A','sha256:mock-1001',1,'成功',NULL,'OK',486,1.00,'N','prod','2026-11-20 10:00:12'),(1002,'MOCKTRACE-EQ-001',1101,'2026-11-20 10:00:18',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1160,'企业关系（对外投资）','91310104MA1K10004D','sha256:mock-1002',1,'成功',NULL,'OK',512,1.00,'N','prod','2026-11-20 10:00:18'),(1003,'MOCKTRACE-EQ-001',1101,'2026-11-20 10:00:24',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1123,'疑似实际控制人','91310115MA1K10001A','sha256:mock-1003',1,'成功',NULL,'OK',690,1.00,'N','prod','2026-11-20 10:00:24'),(1004,'MOCKTRACE-BS-001',1102,'2026-11-20 11:20:06',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1156,'联系方式/账簿信息','91310104MA1K10004D','sha256:mock-1004',1,'成功',NULL,'OK',233,1.00,'Y','prod','2026-11-20 11:20:06'),(1005,'MOCKTRACE-BS-001',1102,'2026-11-20 11:20:11',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1161,'幕后关系','91310105MA1K10006F','sha256:mock-1005',1,'成功',NULL,'OK',744,1.00,'N','prod','2026-11-20 11:20:11'),(1006,'MOCKTRACE-BS-001',1102,'2026-11-20 11:20:16',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1158,'主要人员','91310105MA1K10006F','sha256:mock-1006',1,'成功',NULL,'OK',251,1.00,'Y','prod','2026-11-20 11:20:16'),(1007,'MOCKTRACE-PRJ-001',1201,'2026-11-21 09:01:02',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',821,'股权穿透（向上/向下）','91310115MA1K10001A','sha256:mock-1007',1,'成功',NULL,'OK',470,1.00,'Y','prod','2026-11-21 09:01:02'),(1008,'MOCKTRACE-PRJ-001',1201,'2026-11-21 09:01:20',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1160,'企业关系（对外投资）','91310104MA1K10004D','sha256:mock-1008',1,'成功',NULL,'OK',498,1.00,'Y','prod','2026-11-21 09:01:20'),(1009,'MOCKTRACE-PRJ-002',1202,'2026-11-21 14:10:30',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1156,'联系方式/账簿信息','91310104MA1K10004D','sha256:mock-1009',1,'成功',NULL,'OK',210,1.00,'Y','prod','2026-11-21 14:10:30'),(1010,'MOCKTRACE-PRJ-002',1202,'2026-11-21 14:10:52',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1161,'幕后关系','91310104MA1K10004D','sha256:mock-1010',1,'成功',NULL,'OK',701,1.00,'N','prod','2026-11-21 14:10:52'),(1011,'MOCKTRACE-PRJ-003',1203,'2026-11-22 08:31:02',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1158,'主要人员','91310118MA1K10008H','sha256:mock-1011',1,'成功',NULL,'OK',178,1.00,'Y','prod','2026-11-22 08:31:02'),(1012,'MOCKTRACE-PRJ-003',1203,'2026-11-22 08:31:40',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1123,'疑似实际控制人','91310115MA1K10001A','sha256:mock-1012',1,'成功',NULL,'OK',655,1.00,'Y','prod','2026-11-22 08:31:40'),(1013,'MOCKTRACE-PRJ-003',1203,'2026-11-22 08:32:10',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',821,'股权穿透（向上/向下）','91310109MA1K10011L','sha256:mock-1013',1,'被拦截','日预算超限','日预算超限',0,0.00,'Y','prod','2026-11-22 08:32:10'),(1014,'MOCKTRACE-PRJ-003',1203,'2026-11-22 08:32:44',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1161,'幕后关系','91310109MA1K10011L','sha256:mock-1014',1,'成功',NULL,'OK',733,1.00,'N','prod','2026-11-22 08:32:44'),(1015,'MOCKTRACE-BS-002',1103,'2026-11-20 13:05:02',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1156,'联系方式/账簿信息','91310104MA1K10004D','sha256:mock-1015',1,'成功',NULL,'OK',226,1.00,'Y','prod','2026-11-20 13:05:02'),(1016,'MOCKTRACE-BS-002',1103,'2026-11-20 13:05:09',1,'admin',100,'若依科技','127.0.0.1','MOCK-TERM-01',1161,'幕后关系','91310104MA1K10004D','sha256:mock-1016',1,'成功',NULL,'OK',718,1.00,'N','prod','2026-11-20 13:05:09');
/*!40000 ALTER TABLE `srm_api_call_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_api_dict`
--

DROP TABLE IF EXISTS `srm_api_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_api_dict` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `api_id` int NOT NULL COMMENT '接口编号：818/819/821/846/848/851/871/872/839/1002/1046/1047/1123/1141/1156/1160/1161/1168/458',
  `api_name` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口名称',
  `api_category` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据分类：工商信息/司法风险/经营风险/关系发现/组合接口/特色接口/增值服务',
  `doc_url` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '详细 URL',
  `unit_price` decimal(8,2) NOT NULL COMMENT '单价（元/次），逐字取自接口清单',
  `price_unit` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '次' COMMENT '计价单位（次/家）',
  `is_page_query` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否分页接口：1 分页每页 20 条 / 0 非分页',
  `page_size_max` int NOT NULL DEFAULT '0' COMMENT '每页条数上限，非分页为 0',
  `billing_mode` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'unknown' COMMENT '计费口径：per_call 每次计 / per_page 每页计 / async_poll 二次拉取才计 / unknown 待实测',
  `async_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否异步任务型接口（1161/1141 需 Init-Poll）',
  `show_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '是否在呈现清单（接口表 是否呈现=是 的 19 项）',
  `status` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0 可用 / 1 未购买或停用（停用接口前端不出现调用入口）',
  `cache_ttl_sec` int NOT NULL DEFAULT '86400' COMMENT '本接口默认缓存有效期（秒），页面16 缓存有效期可全局覆盖',
  `depth_max` int DEFAULT NULL COMMENT '接口深度上限：1160=10、1161=10（区间外返回无数据）、4=默认层数',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_api_id` (`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='天眼查接口字典：页面9 接口多选、页面16 单价与额度口径、调用前套餐校验全部读这张表。billing_mode 初始为 unknown，必须在 500 次/周的测试额度内实测后固化';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_api_dict`
--

LOCK TABLES `srm_api_dict` WRITE;
/*!40000 ALTER TABLE `srm_api_dict` DISABLE KEYS */;
/*!40000 ALTER TABLE `srm_api_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_api_raw`
--

DROP TABLE IF EXISTS `srm_api_raw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_api_raw` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `call_log_id` bigint unsigned DEFAULT NULL COMMENT '对应一次调用 srm_api_call_log.id',
  `api_id` int NOT NULL,
  `subject_key` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '查询主体',
  `request_json` json DEFAULT NULL COMMENT '请求参数（不含密钥/令牌，任何情况下不入库）',
  `response_json` json NOT NULL COMMENT '原始响应（HTTP 层原样），结构化表可由本字段重建',
  `resp_status` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接口业务返回码/错误描述',
  `http_status` int DEFAULT NULL,
  `cost_ms` int DEFAULT NULL COMMENT '响应耗时',
  `storage_type` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'db' COMMENT 'db / file（超大响应落对象存储或本地磁盘，path 记于 storage_path）',
  `storage_path` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `byte_size` int DEFAULT NULL COMMENT '响应字节数，供清理策略使用',
  `fetched_at` datetime NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_api_time` (`api_id`,`fetched_at`),
  KEY `idx_call` (`call_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='原始数据留痕（需求逐字要求"服务端完成签名、鉴权、缓存、限流和原始数据留痕"）。APPEND-ONLY：结构化表可重建，本表不可改';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_api_raw`
--

LOCK TABLES `srm_api_raw` WRITE;
/*!40000 ALTER TABLE `srm_api_raw` DISABLE KEYS */;
INSERT INTO `srm_api_raw` VALUES (1001,1001,821,'91310115MA1K10001A','{\"depth\": 3, \"keyword\": \"华晟控股集团有限公司\"}','{\"result\": {\"items\": [{\"name\": \"华晟建筑工程有限公司\", \"percent\": \"100%\"}, {\"name\": \"华晟智能装备制造有限公司\", \"percent\": \"70%\"}]}}','SUCCESS',200,486,'db',NULL,20480,'2026-11-20 10:00:12','2026-11-20 10:00:00'),(1002,1002,1160,'91310104MA1K10004D','{\"keyword\": \"恒信供应链管理有限公司\", \"direction\": \"up\"}','{\"result\": {\"items\": [{\"name\": \"华晟控股集团有限公司\", \"percent\": \"45%\"}]}}','SUCCESS',200,512,'db',NULL,18432,'2026-11-20 10:00:18','2026-11-20 10:00:00'),(1003,1003,1156,'91310104MA1K10004D','{\"keyword\": \"恒信供应链管理有限公司\"}','{\"result\": {\"email\": \"service@hengxin.example\", \"phone\": \"021-58881004\", \"address\": \"上海市徐汇区宜山路900号\"}}','SUCCESS',200,233,'db',NULL,8192,'2026-11-20 11:20:06','2026-11-20 10:00:00'),(1004,1004,1123,'91310115MA1K10001A','{\"keyword\": \"华晟控股集团有限公司\"}','{\"result\": {\"path\": [\"陈国华\", \"华晟控股集团有限公司\"], \"percent\": \"60%\", \"controller\": \"陈国华\"}}','SUCCESS',200,690,'db',NULL,20480,'2026-11-20 10:00:24','2026-11-20 10:00:00'),(1005,1005,1158,'91310118MA1K10008H','{\"keyword\": \"中启环保工程有限公司\"}','{\"result\": {\"staff\": [{\"name\": \"王建军\", \"position\": \"法定代表人\"}]}}','SUCCESS',200,178,'db',NULL,6144,'2026-11-22 08:31:02','2026-11-20 10:00:00'),(1006,1006,1161,'91310105MA1K10006F','{\"keyword\": \"鸿远物流服务有限公司\"}','{\"result\": {\"bookkeeping\": [{\"type\": \"财务联系人\", \"subject\": \"刘志强\"}]}}','SUCCESS',200,744,'db',NULL,16384,'2026-11-20 11:20:11','2026-11-20 10:00:00');
/*!40000 ALTER TABLE `srm_api_raw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_audit_log`
--

DROP TABLE IF EXISTS `srm_audit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_audit_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `audit_time` datetime NOT NULL COMMENT '时间（必填）',
  `module_name` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '模块（必填）：供应商主档/项目管理/项目分析/接口预算/关联规则/字段映射/复核处置/备份恢复…',
  `action_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '动作（必填）：新增 / 修改 / 导入 / 导出 / 启停 / 移出',
  `object_name` varchar(300) COLLATE utf8mb4_general_ci NOT NULL COMMENT '对象（必填）：供应商编码、项目单据号、规则名、报告版本…',
  `detail` varchar(2000) COLLATE utf8mb4_general_ci NOT NULL COMMENT '详情（必填）：变更前后值或动作说明',
  `scope_type` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '记录范围：主档增改/监控变更/项目名单与参数/规则变更/报告导出/字段映射变更/备份与恢复/预算拦截',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人',
  `operator_name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dept_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `source_ip` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `terminal_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `trace_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '与接口调用日志串联（预算拦截场景）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_time` (`audit_time`),
  KEY `idx_module_action` (`module_name`,`action_name`,`audit_time`),
  KEY `idx_operator` (`operator_id`,`audit_time`),
  KEY `idx_object` (`object_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1022 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='业务操作审计（页面17，5 列均不得为空）。APPEND-ONLY：正式环境由服务端持久化，只追加不可篡改；与页面9 调用日志分别落表，互不混用';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_audit_log`
--

LOCK TABLES `srm_audit_log` WRITE;
/*!40000 ALTER TABLE `srm_audit_log` DISABLE KEYS */;
INSERT INTO `srm_audit_log` VALUES (1001,'2026-11-20 10:00:00','股权穿透','发起','华晟控股集团有限公司','发起股权穿透查询：深度 5，比例区间 0%-100%，范围 ALL','项目分析',1,'admin','若依科技','127.0.0.1','MOCK-TERM-01','MOCKTRACE-EQ-001','2026-11-20 10:00:00'),(1002,'2026-11-20 11:20:00','幕后关系','发起','华晟控股集团有限公司','发起幕后关系查询：账簿信息包含，最小权重 3','项目分析',1,'admin','若依科技','127.0.0.1','MOCK-TERM-01','MOCKTRACE-BS-001','2026-11-20 11:20:00'),(1003,'2026-11-21 09:02:10','项目关系网','分析','PRJ-2026-001','完成项目分析，生成报告版本 V1，命中线索 6 条','项目名单与参数',1,'admin','若依科技','127.0.0.1','MOCK-TERM-01','MOCKTRACE-PRJ-001','2026-11-21 09:02:10'),(1004,'2026-11-21 10:20:00','人工复核','复核','CLUE-1001','确认存在账簿共用关系，结论 CONFIRMED','复核处置',1,'张审核','若依科技','127.0.0.1','MOCK-TERM-02','MOCKTRACE-PRJ-001','2026-11-21 10:20:00'),(1005,'2026-11-21 16:02:00','人工复核','复核','CLUE-1008','关联投标证据不足，结论 REJECTED','复核处置',1,'李复核','若依科技','127.0.0.1','MOCK-TERM-02','MOCKTRACE-PRJ-002','2026-11-21 16:02:00'),(1006,'2026-11-22 08:33:05','报告版本','导出','PRJ-2026-003 / V1','导出年度重点供应商核查报告（Excel/PDF）','项目名单与参数',1,'admin','若依科技','127.0.0.1','MOCK-TERM-01','MOCKTRACE-PRJ-003','2026-11-22 08:33:05'),(1007,'2026-11-22 09:12:00','人工复核','复核','CLUE-1011','确认共同董监高关系，结论 CONFIRMED','复核处置',1,'张审核','若依科技','127.0.0.1','MOCK-TERM-02','MOCKTRACE-PRJ-003','2026-11-22 09:12:00'),(1019,'2026-11-25 22:32:23','股权穿透','发起','init','发起穿透任务',NULL,1,'admin',NULL,'127.0.0.1',NULL,'78c3a31629c1b4cc2d1c3726f23e4898',NULL),(1020,'2026-11-25 22:33:25','幕后关系','发起','init','发起幕后关系任务',NULL,1,'admin',NULL,'127.0.0.1',NULL,'4b35c1567c0721d9b59c41697b2b22bc',NULL),(1021,'2026-11-25 22:33:53','项目关系网','分析','analysisInit','执行关联分析',NULL,1,'admin',NULL,'127.0.0.1',NULL,'08e5b6db87bc17b6a704108b6275fadb',NULL);
/*!40000 ALTER TABLE `srm_audit_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_backup_record`
--

DROP TABLE IF EXISTS `srm_backup_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_backup_record` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `backup_no` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '备份号',
  `backup_type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'full_json 完整备份 / csv_suppliers 供应商清单 / csv_projects 项目清单 / csv_reviews 复核清单 / csv_audit 审计日志',
  `trigger_type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'manual' COMMENT 'manual 手动 / import 批量导入后 / analysis 项目批量分析后 / rule 重大规则调整后 / schedule 计划任务',
  `cover_scope` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '覆盖内容：主档、项目、规则、监控、复核、审计（页面19 六项）',
  `stat_supplier` int DEFAULT NULL COMMENT '统计：供应商数',
  `stat_monitor` int DEFAULT NULL COMMENT '统计：重点监控企业',
  `stat_version` int DEFAULT NULL COMMENT '统计：累计分析版本',
  `stat_review` int DEFAULT NULL COMMENT '统计：待复核线索',
  `storage_path` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备份文件路径（服务端存储，非浏览器）',
  `byte_size` bigint DEFAULT NULL,
  `sha256` char(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '摘要，恢复前校验',
  `restore_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '本文件是否已被用于恢复',
  `restore_at` datetime DEFAULT NULL COMMENT '恢复时间',
  `danger_confirm` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '危险操作二次确认：清除本机存档并恢复初始示例须先下载 JSON 备份',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_backup_no` (`backup_no`),
  KEY `idx_type_time` (`backup_type`,`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据备份与恢复流水（页面19）。正式版落服务端存储：刷新页面不丢数据，恢复动作与危险操作写入审计';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_backup_record`
--

LOCK TABLES `srm_backup_record` WRITE;
/*!40000 ALTER TABLE `srm_backup_record` DISABLE KEYS */;
INSERT INTO `srm_backup_record` VALUES (1001,'BAK-20261120-001','full_json','manual','供应商主档+监控+线索+报告版本',12,7,3,12,'/data/srm/backup/mock_full_20261120.sql',5242880,'3b1f2c4d5e6a7b8c9d0e1f2a3b4c5d6e7f8091a2b3c4d5e6f708192a3b4c5d6e','0',NULL,'0','admin','2026-11-20 23:30:00'),(1002,'BAK-20261122-002','csv_suppliers','schedule','全量演示数据（增量差集）',12,7,3,12,'/data/srm/backup/mock_incr_20261122.sql',1048576,'7c8d9e0f1a2b3c4d5e6f708192a3b4c5d6e3b1f2c4d5e6a7b8c9d0e1f2a3b4c5','0',NULL,'0','admin','2026-11-22 23:30:00');
/*!40000 ALTER TABLE `srm_backup_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_budget_policy`
--

DROP TABLE IF EXISTS `srm_budget_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_budget_policy` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `policy_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '默认策略' COMMENT '策略名称（预留多策略，按环境/部门）',
  `daily_limit` int NOT NULL DEFAULT '0' COMMENT '每日调用预算上限（次），0 表示不限制；数值由采购方给定',
  `monthly_limit` int NOT NULL DEFAULT '0' COMMENT '月度调用预算上限（次）',
  `warn_ratios` varchar(60) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '60' COMMENT '预算预警阈值档位，多选逗号分隔，取值 60/70/80/90',
  `max_concurrency` int NOT NULL DEFAULT '2' COMMENT '并发数（同一时刻允许的外部请求条数），以采购合同为准，先给保守值',
  `auto_full_scan` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '自动全量查询：0 关闭（需求默认，且不允许改 1）/ 1 开启',
  `cache_ttl_sec` int NOT NULL DEFAULT '86400' COMMENT '缓存有效期（秒），页面3 缓存有效期对应项',
  `monitor_frequency` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'manual' COMMENT '重点供应商监控频率：daily/weekly/manual/push（默认人工触发）',
  `monitor_max_ent` int NOT NULL DEFAULT '0' COMMENT '监控企业上限（家），0 表示不额外限制，以合同为准',
  `status` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0 启用 / 1 停用',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_policy` (`policy_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='接口调用策略与预算配置（页面16）。日/月上限按"次"计不按金额；正式版必须按实际请求数、供应商数、接口项与批量接口计量';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_budget_policy`
--

LOCK TABLES `srm_budget_policy` WRITE;
/*!40000 ALTER TABLE `srm_budget_policy` DISABLE KEYS */;
/*!40000 ALTER TABLE `srm_budget_policy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_change_event`
--

DROP TABLE IF EXISTS `srm_change_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_change_event` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `supplier_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '供应商（页面8 表 8-2 列）',
  `credit_code` varchar(18) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '统一社会信用代码',
  `legal_person` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法定代表人',
  `last_industry_update` datetime DEFAULT NULL COMMENT '最近工商更新',
  `monitor_status` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '监控状态：重点监控 / 普通供应商',
  `change_at` datetime NOT NULL COMMENT '变更时间',
  `change_item` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '变更事项',
  `dimension` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属监控维度（srm_monitor_policy.policy_code）',
  `level_tag` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '等级（本地配置规则标记，非接口字段）',
  `before_value` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '变更前',
  `after_value` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '变更后',
  `handle_status` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '待处理' COMMENT '处理状态：待处理 / 已确认（页面8 标记已处理）',
  `high_risk_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '高风险事件：按配置规则标记，页面8 指标卡计数',
  `prev_snapshot_id` bigint unsigned DEFAULT NULL COMMENT '比对基准快照',
  `curr_snapshot_id` bigint unsigned DEFAULT NULL COMMENT '本次快照',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `handle_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `handle_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sup_time` (`supplier_id`,`change_at`),
  KEY `idx_status` (`handle_status`,`change_at`),
  KEY `idx_dimension` (`dimension`,`change_at`)
) ENGINE=InnoDB AUTO_INCREMENT=1008 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='变更事件清单（页面8 表 8-2 十三列，逐列建模）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_change_event`
--

LOCK TABLES `srm_change_event` WRITE;
/*!40000 ALTER TABLE `srm_change_event` DISABLE KEYS */;
INSERT INTO `srm_change_event` VALUES (1001,1004,'恒信供应链管理有限公司','91310104MA1K10004D','刘志强','2026-11-19 12:00:00','重点监控','2026-11-19 12:00:00','新增股东','shareholder','高','刘志强 55%、赵文博 45%','刘志强 55%、赵文博 45%、华晟控股集团有限公司 10%','待处理','1',1003,1004,'2026-11-19 12:05:00','2026-11-19 12:05:00',NULL,NULL),(1002,1001,'华晟控股集团有限公司','91310115MA1K10001A','陈国华','2026-11-18 09:00:00','重点监控','2026-11-18 09:00:00','法定代表人变更','legal_person','高','陈建国','陈国华','已确认','1',1001,1002,'2026-11-19 12:05:00','2026-11-19 12:05:00',NULL,NULL),(1003,1008,'中启环保工程有限公司','91310118MA1K10008H','王建军','2026-11-15 08:30:00','重点监控','2026-11-15 08:30:00','新增被执行人','judicial','高','无被执行人记录','(2021)沪0118执3302号 26.8万元','待处理','1',NULL,NULL,'2026-11-19 12:05:00','2026-11-19 12:05:00',NULL,NULL),(1004,1005,'鑫达新材料科技有限公司','91310117MA1K10005E','孙立群','2026-11-12 14:20:00','普通供应商','2026-11-12 14:20:00','新增欠税公告','judicial','中','无欠税记录','增值税欠税 18.65万元','已确认','0',NULL,NULL,'2026-11-19 12:05:00','2026-11-19 12:05:00',NULL,NULL),(1005,1002,'华晟建筑工程有限公司','91310115MA1K10002B','李明远','2026-11-05 10:00:00','重点监控','2026-11-05 10:00:00','注册资本变更','biz_info','低','3000万元人民币','5000万元人民币','已确认','0',NULL,NULL,'2026-11-19 12:05:00','2026-11-19 12:05:00',NULL,NULL),(1006,1016,'瑞泽实业集团有限公司','91310115MA1K10016R','沈文彬','2026-11-19 12:00:00','重点监控','2026-11-19 12:00:00','新增股东','shareholder','高','沈文彬 55%、上海瑞泽投资中心(有限合伙) 20%','沈文彬 55%、华晟控股集团有限公司 25%、上海瑞泽投资中心(有限合伙) 20%','待处理','1',1005,1006,'2026-11-19 12:05:00','2026-11-19 12:05:00',NULL,NULL),(1007,1021,'中泰智能仓储有限公司','91310114MA1K10021W','罗伯涛','2026-11-17 09:20:00','重点监控','2026-11-17 09:20:00','新增被执行人','judicial','高','无被执行人记录','(2025)沪0114执3307号 96万元','待处理','1',NULL,NULL,'2026-11-19 12:05:00','2026-11-19 12:05:00',NULL,NULL);
/*!40000 ALTER TABLE `srm_change_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_clue_review`
--

DROP TABLE IF EXISTS `srm_clue_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_clue_review` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `clue_id` bigint unsigned NOT NULL,
  `project_id` bigint unsigned DEFAULT NULL,
  `report_version_id` bigint unsigned DEFAULT NULL COMMENT '项目/版本（页面12 清单列）',
  `review_status` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '复核状态：待复核 / 已确认有关联 / 排除线索 / 需补充材料',
  `conclusion` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL COMMENT '复核结论（必填）',
  `review_note` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '复核说明：排除线索与需补充材料时不得为空',
  `evidence_ref` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '证据依据（多行文本 / 附件索引，附件本体走文件存储）',
  `reviewer_id` bigint NOT NULL COMMENT '复核人',
  `reviewer_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `reviewed_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_clue` (`clue_id`,`reviewed_at`) COMMENT '同一线索多次复核为附加保存，按 reviewed_at 取最新',
  KEY `idx_reviewer` (`reviewer_id`,`reviewed_at`)
) ENGINE=InnoDB AUTO_INCREMENT=1035 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='人工复核流水（页面12）。APPEND-ONLY：复核是人工处置记录，不回写原始关系链路';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_clue_review`
--

LOCK TABLES `srm_clue_review` WRITE;
/*!40000 ALTER TABLE `srm_clue_review` DISABLE KEYS */;
INSERT INTO `srm_clue_review` VALUES (1001,1001,1001,1001,'已确认有关联','确认存在账簿共用关系','经天眼查工商登记与年报信息比对，电话/邮箱/地址三项一致，判定为同一控制下关联方。','TYC:1156:hash-mock-001',1,'张审核','2026-11-21 10:20:00','2026-11-21 10:20:00'),(1002,1004,1001,1001,'已确认有关联','确认同邮箱且存在参股关系','联志电子持有瑞昇信息 10% 股权，登记邮箱一致，维持关联判定。','TYC:1156:hash-mock-002',1,'张审核','2026-11-21 10:26:00','2026-11-21 10:26:00'),(1003,1005,1001,1001,'已确认有关联','确认间接控股路径','恒信供应链持股 30% 链路成立，穿透至华晟控股集团，判定为疑似同一实际控制人。','TYC:1160:hash-mock-003',1,'张审核','2026-11-21 10:31:00','2026-11-21 10:31:00'),(1004,1003,1001,1001,'待复核','待补充共同董监高任职证明','电话与董监高重合已确认，任职证明需业务部门补充后复核。','TYC:1156:hash-mock-004',1,'李复核','2026-11-21 15:40:00','2026-11-21 15:40:00'),(1005,1007,1002,1002,'已确认有关联','确认账簿共用','恒信供应链与鸿远物流登记信息完全一致，判定为强关联。','TYC:1156:hash-mock-005',1,'张审核','2026-11-21 15:10:00','2026-11-21 15:10:00'),(1006,1009,1002,1002,'已确认有关联','确认向上穿透一级路径','持股 30% 关系经股权穿透接口校验通过。','TYC:1160:hash-mock-006',1,'张审核','2026-11-21 15:16:00','2026-11-21 15:16:00'),(1007,1008,1002,1002,'排除线索','关联投标证据不足','共同投标记录未能在本轮缓存数据中复现，暂不判定为关联关系。','TYC:1161:hash-mock-007',1,'李复核','2026-11-21 16:02:00','2026-11-21 16:02:00'),(1008,1011,1003,1003,'已确认有关联','确认共同董监高关系','两家企业董监高重合且共用电话，判定为关联方。','TYC:1156:hash-mock-008',1,'张审核','2026-11-22 09:12:00','2026-11-22 09:12:00'),(1009,1012,1003,1003,'已确认有关联','确认同邮箱','登记邮箱一致，判定为关联线索，建议纳入重点监控。','TYC:1156:hash-mock-009',1,'张审核','2026-11-22 09:18:00','2026-11-22 09:18:00'),(1010,1013,1003,1003,'已确认有关联','确认向下穿透控股链','华晟智能装备持股 35%，股权链路校验通过。','TYC:1160:hash-mock-010',1,'张审核','2026-11-22 09:25:00','2026-11-22 09:25:00'),(1011,1014,1003,1003,'已确认有关联','确认全资控股','100% 持股关系明确，属同一集团内成员单位。','TYC:1160:hash-mock-011',1,'张审核','2026-11-22 09:30:00','2026-11-22 09:30:00'),(1012,1015,1003,1003,'待复核','疑似实际控制人待业务确认','股权链路成立，需业务部门确认是否认定为实际控制人。','TYC:1123:hash-mock-012',1,'李复核','2026-11-22 09:41:00','2026-11-22 09:41:00'),(1013,1016,1004,1004,'已确认有关联','确认 55% 控股链路','蓝湾海洋工程由华晟控股集团持股 55%，股权链路成立，属集团控股成员单位。','TYC:1160:hash-mock-013',1,'张审核','2026-11-22 10:40:00','2026-11-22 10:40:00'),(1014,1017,1004,1004,'已确认有关联','确认二级控股链路','凯越精密机械由华晟智能装备持股 60%，向上穿透至华晟控股集团。','TYC:1160:hash-mock-014',1,'张审核','2026-11-22 10:44:00','2026-11-22 10:44:00'),(1015,1018,1004,1004,'已确认有关联','确认同电话与参股','云通数据与瑞昇信息共用登记电话，且瑞昇信息持股 30%，判定为关联方。','TYC:1156:hash-mock-015',1,'张审核','2026-11-22 10:48:00','2026-11-22 10:48:00'),(1016,1019,1004,1004,'待复核','共同董监高待任职证明','两企业董监高重合已确认，任职证明需业务部门补充。','TYC:1156:hash-mock-016',1,'李复核','2026-11-22 10:52:00','2026-11-22 10:52:00'),(1017,1020,1004,1004,'已确认有关联','确认向下穿透链路','华晟控股集团向下穿透至蓝湾海洋工程，链路校验通过。','TYC:1123:hash-mock-017',1,'张审核','2026-11-22 10:56:00','2026-11-22 10:56:00'),(1018,1021,1004,1004,'待复核','同电话线索待复核','凯越精密机械与海通机电登记电话一致，待确认是否同一经营团队。','TYC:1156:hash-mock-018',1,'李复核','2026-11-22 11:00:00','2026-11-22 11:00:00'),(1019,1022,1004,1004,'已确认有关联','确认同邮箱','云通数据与联志电子登记邮箱一致，判定为关联线索。','TYC:1156:hash-mock-019',1,'张审核','2026-11-22 11:04:00','2026-11-22 11:04:00'),(1020,1023,1004,1004,'待复核','疑似实际控制人待确认','顾伟明持股 45%，是否与集团构成共同实际控制需业务确认。','TYC:1123:hash-mock-020',1,'李复核','2026-11-22 11:08:00','2026-11-22 11:08:00'),(1021,1024,1005,1005,'已确认有关联','确认 30% 参股链路','瑞昇信息持有云通数据 30% 股权，向上穿透至许文娟。','TYC:1160:hash-mock-021',1,'张审核','2026-11-22 11:20:00','2026-11-22 11:20:00'),(1022,1025,1005,1005,'已确认有关联','确认共同董监高与同电话','云通数据与瑞昇信息董监高交叉且电话一致，判定为关联方。','TYC:1156:hash-mock-022',1,'张审核','2026-11-22 11:24:00','2026-11-22 11:24:00'),(1023,1026,1005,1005,'已确认有关联','确认 60% 控股链路','凯越精密机械由华晟智能装备持股 60%，复核链路成立。','TYC:1160:hash-mock-023',1,'张审核','2026-11-22 11:28:00','2026-11-22 11:28:00'),(1024,1027,1005,1005,'已确认有关联','确认 55% 控股链路','蓝湾海洋工程由华晟控股集团持股 55%，复核链路成立。','TYC:1160:hash-mock-024',1,'张审核','2026-11-22 11:32:00','2026-11-22 11:32:00'),(1025,1028,1005,1005,'待复核','同注册地址线索待复核','天成检测与云通数据注册地址一致，待确认账簿共用情况。','TYC:1156:hash-mock-025',1,'李复核','2026-11-22 11:36:00','2026-11-22 11:36:00'),(1026,1029,1005,1005,'待复核','共同董监高待任职证明','蓝湾海洋工程与凯越精密机械董监高重合，任职证明待补充。','TYC:1156:hash-mock-026',1,'李复核','2026-11-22 11:40:00','2026-11-22 11:40:00'),(1027,1030,1006,1006,'已确认有关联','确认 40% 控股链路','瑞泽实业集团为中泰智仓第一大股东，工商公示与缓存接口结果一致，判定为控股成员单位。','TYC:1160:hash-mock-027',1,'张审核','2026-11-23 09:40:00','2026-11-23 09:40:00'),(1028,1031,1006,1006,'已确认有关联','确认跨集团持股','华晟控股集团持股 25% 已写入瑞泽实业集团工商登记，两集团构成股权层面关联。','TYC:1160:hash-mock-028',1,'张审核','2026-11-23 09:44:00','2026-11-23 09:44:00'),(1029,1032,1006,1006,'待复核','跨集团参股待补充出资证明','恒信供应链 35% 持股链路成立，但出资时间需与业务合同交叉验证。','TYC:1160:hash-mock-029',1,'李复核','2026-11-23 09:48:00','2026-11-23 09:48:00'),(1030,1033,1006,1006,'已确认有关联','确认账簿三同','电话/邮箱/注册地址三项完全一致，判定为同一控制下关联方。','TYC:1156:hash-mock-030',1,'张审核','2026-11-23 09:52:00','2026-11-23 09:52:00'),(1031,1034,1006,1006,'待复核','同电话待确认是否代理登记','仅电话一致，需确认是否为代理机构统一留号。','TYC:1156:hash-mock-031',1,'李复核','2026-11-23 09:56:00','2026-11-23 09:56:00'),(1032,1035,1006,1006,'已确认有关联','确认跨集团人员交叉','徐海燕在两集团均担任监事，hcgid 一致，判定为共同董监高。','TYC:1156:hash-mock-032',1,'张审核','2026-11-23 10:00:00','2026-11-23 10:00:00'),(1033,1037,1006,1006,'已确认有关联','确认关键自然人跨集团任职','刘志强同时为三家企业股东或董监高，建议纳入重点监控。','TYC:1156:hash-mock-033',1,'张审核','2026-11-23 10:04:00','2026-11-23 10:04:00'),(1034,1038,1006,1006,'已确认有关联','确认疑似实际控制人','沈文彬直接 40% 叠加通过集团间接 60%，合计控制 76%，认定为疑似实际控制人。','TYC:1123:hash-mock-034',1,'张审核','2026-11-23 10:08:00','2026-11-23 10:08:00');
/*!40000 ALTER TABLE `srm_clue_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_data_snapshot`
--

DROP TABLE IF EXISTS `srm_data_snapshot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_data_snapshot` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `credit_code` varchar(18) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `snapshot_type` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '快照对象类型：industry_info/shareholder/person/former_name/risk_illegal/risk_abnormal/judicial/equity/backstage',
  `snapshot_key` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '快照键：企业 + 关系口径/深度 或 穿透方向/层数/比例（页面5/6 快照键口径）',
  `snapshot_hash` char(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '参与比对字段的规范化摘要，相等即无变更（空值不参与比对）',
  `payload_json` json NOT NULL COMMENT '参与比对的字段子集（只存字段映射表 monitor_flag=1 的字段）',
  `api_ids` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '本快照由哪些接口取回',
  `raw_id` bigint unsigned DEFAULT NULL,
  `captured_at` datetime NOT NULL COMMENT '采集时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_sup_type` (`supplier_id`,`snapshot_type`,`captured_at`),
  KEY `idx_hash` (`snapshot_hash`)
) ENGINE=InnoDB AUTO_INCREMENT=1007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='监控数据快照（增量比对基线）。相邻两次快照逐字段比对产出 srm_change_event；变更事件由本地比对产生，不由任何接口直接返回';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_data_snapshot`
--

LOCK TABLES `srm_data_snapshot` WRITE;
/*!40000 ALTER TABLE `srm_data_snapshot` DISABLE KEYS */;
INSERT INTO `srm_data_snapshot` VALUES (1001,1001,'91310115MA1K10001A','industry_info','T1001|biz_info','a1b2c3d4e5f60718293a4b5c6d7e8f90','{\"entName\": \"华晟控股集团有限公司\", \"bizStatus\": \"存续\", \"regCapital\": \"10000万元人民币\", \"legalPerson\": \"陈国华\"}','818,819',NULL,'2026-11-19 12:00:00','2026-11-25 22:05:36'),(1002,1001,'91310115MA1K10001A','industry_info','T1001|biz_info','b1c2d3e4f5061728394a5b6c7d8e9f01','{\"entName\": \"华晟控股集团有限公司\", \"bizStatus\": \"存续\", \"regCapital\": \"10000万元人民币\", \"legalPerson\": \"陈国华\"}','818,819',NULL,'2026-11-19 12:00:00','2026-11-25 22:05:36'),(1003,1004,'91310104MA1K10004D','shareholder','T1004|equity_up|3','c1d2e3f405162738495a6b7c8d9e0f12','{\"shareholders\": [{\"name\": \"刘志强\", \"ratio\": 0.55}, {\"name\": \"赵文博\", \"ratio\": 0.45}]}','821',NULL,'2026-11-19 12:00:00','2026-11-25 22:05:36'),(1004,1004,'91310104MA1K10004D','shareholder','T1004|equity_up|3','d1e2f30415263748596a7b8c9d0e1f23','{\"shareholders\": [{\"name\": \"刘志强\", \"ratio\": 0.55}, {\"name\": \"赵文博\", \"ratio\": 0.45}, {\"name\": \"华晟控股集团有限公司\", \"ratio\": 0.1}]}','821',NULL,'2026-11-19 12:00:00','2026-11-25 22:05:36'),(1005,1016,'91310115MA1K10016R','shareholder','T1016|equity_up|3','e1f2031425364758697a8b9c0d1e2f34','{\"shareholders\": [{\"name\": \"沈文彬\", \"ratio\": 0.55}, {\"name\": \"上海瑞泽投资中心(有限合伙)\", \"ratio\": 0.2}]}','821',NULL,'2026-11-19 12:00:00','2026-11-25 22:05:36'),(1006,1016,'91310115MA1K10016R','shareholder','T1016|equity_up|3','f102132435465768798a9b0c1d2e3f45','{\"shareholders\": [{\"name\": \"沈文彬\", \"ratio\": 0.55}, {\"name\": \"华晟控股集团有限公司\", \"ratio\": 0.25}, {\"name\": \"上海瑞泽投资中心(有限合伙)\", \"ratio\": 0.2}]}','821',NULL,'2026-11-19 12:00:00','2026-11-25 22:05:36');
/*!40000 ALTER TABLE `srm_data_snapshot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_erp_export`
--

DROP TABLE IF EXISTS `srm_erp_export`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_erp_export` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `export_no` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '导出批次号',
  `export_mode` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'file' COMMENT 'file 文件交换 / api 接口推送（以 ERP 侧能力为准）',
  `target_system` varchar(40) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'ERP' COMMENT '目标系统',
  `scope_desc` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '本次输出范围说明（最终合格供应商结果）',
  `supplier_count` int NOT NULL DEFAULT '0' COMMENT '输出供应商数',
  `export_status` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '待执行' COMMENT '待执行 / 成功 / 部分失败 / 失败',
  `file_path` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `err_msg` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_export_no` (`export_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='向 ERP 输出最终合格供应商结果的流水（纪要：只单向输出，不反向从 ERP 拉全量业务数据；本期不做与用友主数据实时对接）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_erp_export`
--

LOCK TABLES `srm_erp_export` WRITE;
/*!40000 ALTER TABLE `srm_erp_export` DISABLE KEYS */;
INSERT INTO `srm_erp_export` VALUES (1001,'EXP-20261121-001','api','ERP','PRJ-2026-001 项目供应商及关联线索',8,'成功','/data/srm/export/mock_erp_prj2026001.json',NULL,'admin','2026-11-21 09:20:00'),(1002,'EXP-20261122-002','file','ERP','PRJ-2026-003 报告版本 V1 全量供应商',12,'成功','/data/srm/export/mock_erp_prj2026003.xlsx',NULL,'admin','2026-11-22 08:45:00');
/*!40000 ALTER TABLE `srm_erp_export` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_field_mapping`
--

DROP TABLE IF EXISTS `srm_field_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_field_mapping` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `biz_field` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务字段（页面11 8 列之一）',
  `data_source` varchar(60) COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据来源：授权外部数据源 / 本地主档（自定义）',
  `api_id` int DEFAULT NULL COMMENT '对应接口编号（本地自定义字段为空）',
  `outer_key` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '外部字段标识：以接口响应文档中的真实键名为准，禁止填样例占位键名；NULL 表示真实键名尚未核对（本地自定义字段恒为 NULL），此时 status 必须为 1 停用，服务层禁止启用',
  `inner_key` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '内部字段标识：内部稳定标识，按内部字段标识规范维护',
  `data_type` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据类型：列表 / 文本',
  `analysis_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '关联分析：1 参与关联分析 / 0 未参与',
  `monitor_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '持续监控：1 参与监控 / 0 未监控',
  `target_table` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结构化落库目标表（raw→结构化投影的可重建依据）',
  `norm_rule` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '规范化规则：电话/地址/人员的去空、全半角、分隔符处理',
  `not_null_check` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '空值不参与关系匹配（需求逐字要求），固定为 1',
  `status` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0 有效 / 1 停用；同一业务字段只允许一条有效映射',
  `active_biz` varchar(100) COLLATE utf8mb4_general_ci GENERATED ALWAYS AS (if((`status` = _utf8mb4'0'),`biz_field`,NULL)) STORED COMMENT '有效映射唯一性承载列：停用的历史映射取 NULL，不参与唯一约束，从而只约束"有效"这一态',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_active_biz` (`active_biz`),
  KEY `idx_biz_status` (`biz_field`,`status`),
  KEY `idx_inner` (`inner_key`),
  KEY `idx_api` (`api_id`,`outer_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='外部数据字段映射（页面11）。接口字段名变化只改本表不改页面逻辑；内部逻辑只认 inner_key，是外部数据与主档之间的唯一翻译层';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_field_mapping`
--

LOCK TABLES `srm_field_mapping` WRITE;
/*!40000 ALTER TABLE `srm_field_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `srm_field_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_graph_edge`
--

DROP TABLE IF EXISTS `srm_graph_edge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_graph_edge` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `task_id` bigint unsigned NOT NULL,
  `edge_key` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '连线唯一标识：1161 关系id，用于去重',
  `from_node_key` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '开始节点 / 起始节点',
  `to_node_key` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '结束节点',
  `from_id_new` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '开始节点（对应节点idNew字段）原文映射',
  `to_id_new` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结束节点（对应节点idNew字段）原文映射',
  `rel_type` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型：SERVE_ALL-董监高 / INVEST-股东 / BRANCH-分支机构 / HISTORICAL_SHAREHOLDER-历史股东 / HISTORICAL_SERVE_ALL-历史董监高 / ALL-所有类型（默认）',
  `ratio` decimal(9,4) DEFAULT NULL COMMENT '占比（股东关系）/ 股份比例 / 占比：按来源接口分列存 source_api_id',
  `ratio_label` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '连线标注原文（不同接口同叫占比，界面须逐字区分）',
  `found_flag` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关系发现标识（1161/1141）',
  `weight` tinyint DEFAULT NULL COMMENT '本地规则权重 1—5（非接口字段，页面7 连线标注组成部分）',
  `rule_id` bigint unsigned DEFAULT NULL COMMENT '命中的本地规则 srm_rel_rule.id',
  `clue_id` bigint unsigned DEFAULT NULL COMMENT '命中的线索 srm_rel_clue.id（连线=启用规则命中的关联线索）',
  `source_api_id` int DEFAULT NULL,
  `raw_id` bigint unsigned DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_task_edge` (`task_id`,`edge_key`,`from_node_key`,`to_node_key`),
  KEY `idx_task` (`task_id`),
  KEY `idx_nodes` (`task_id`,`from_node_key`,`to_node_key`),
  KEY `idx_rule` (`rule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3601 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='图谱连线（1161/1123/1160/1141 成对生成）。APPEND-ONLY';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_graph_edge`
--

LOCK TABLES `srm_graph_edge` WRITE;
/*!40000 ALTER TABLE `srm_graph_edge` DISABLE KEYS */;
INSERT INTO `srm_graph_edge` VALUES (3001,1101,'E-2001-2002','H1001','C1001','H1001','C1001','INVEST',60.0000,'60%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3002,1101,'E-2001-2003','C1001','C1002','C1001','C1002','INVEST',100.0000,'100%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3003,1101,'E-2001-2004','C1001','C1003','C1001','C1003','INVEST',70.0000,'70%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3004,1101,'E-2003-2005','C1002','H1002','C1002','H1002','SERVE_ALL',NULL,'法定代表人','Y',5,3,NULL,1158,NULL,'2026-11-20 10:00:20'),(3005,1101,'E-2003-2006','C1002','C1007','C1002','C1007','INVEST',40.0000,'40%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3006,1101,'E-2004-2007','C1003','C1008','C1003','C1008','INVEST',35.0000,'35%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3007,1101,'E-2001-2008','C1001','C1004','C1001','C1004','INVEST',45.0000,'45%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3008,1101,'E-2008-2009','C1004','C1005','C1004','C1005','INVEST',30.0000,'30%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3009,1101,'E-2008-2010','H1003','C1004','H1003','C1004','INVEST',55.0000,'55%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3010,1101,'E-2008-2011','H1004','C1004','H1004','C1004','INVEST',45.0000,'45%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3011,1101,'E-2004-2012','C1003','C1009','C1003','C1009','INVEST',20.0000,'20%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3012,1101,'E-2012-2013','C1009','C1011','C1009','C1011','INVEST',10.0000,'10%','Y',5,1,NULL,821,NULL,'2026-11-20 10:00:20'),(3013,1101,'E-2012-2014','C1009','H1005','C1009','H1005','SERVE_ALL',NULL,'法定代表人','Y',5,3,NULL,1158,NULL,'2026-11-20 10:00:20'),(3014,1101,'E-2015-2008','C1006','C1004','C1006','C1004','ALL',NULL,'同电话/同邮箱/同地址','Y',3,4,NULL,1156,NULL,'2026-11-20 10:00:20'),(3015,1101,'E-2005-2016','C1005','H1006','C1005','H1006','SERVE_ALL',NULL,'法定代表人','Y',5,3,NULL,1158,NULL,'2026-11-20 10:00:20'),(3016,1101,'E-H1003-2014','H1003','H1005','H1003','H1005','ALL',NULL,'共同董监高/同电话','Y',4,7,NULL,1156,NULL,'2026-11-20 10:00:20'),(3101,1102,'E-2101-2102','C1001','C1004','C1001','C1004','INVEST',45.0000,'45%','Y',5,1,NULL,821,NULL,'2026-11-20 11:20:15'),(3102,1102,'E-2102-2103','C1004','C1006','C1004','C1006','ALL',NULL,'同电话/同邮箱/同地址','Y',3,4,NULL,1156,NULL,'2026-11-20 11:20:15'),(3103,1102,'E-2102-2104','H1003','C1004','H1003','C1004','SERVE_ALL',NULL,'股东/董监高','Y',5,3,NULL,1158,NULL,'2026-11-20 11:20:15'),(3104,1102,'E-2103-2105','H1007','C1006','H1007','C1006','SERVE_ALL',NULL,'财务联系人','Y',4,3,NULL,1158,NULL,'2026-11-20 11:20:15'),(3105,1102,'E-2104-2105','H1003','H1007','H1003','H1007','ALL',NULL,'共账簿/共用联系方式','Y',4,7,NULL,1161,NULL,'2026-11-20 11:20:15'),(3106,1102,'E-2102-2106','C1004','C1009','C1004','C1009','ALL',NULL,'共同供应商/关联投标','Y',3,7,NULL,1161,NULL,'2026-11-20 11:20:15'),(3201,1103,'E-2204-2201','H1003','C1004','H1003','C1004','INVEST',55.0000,'55%','Y',5,1,NULL,821,NULL,'2026-11-20 13:05:10'),(3202,1103,'E-2204-2202','C1004','C1005','C1004','C1005','INVEST',30.0000,'30%','Y',5,1,NULL,821,NULL,'2026-11-20 13:05:10'),(3203,1103,'E-2201-2203','C1004','C1006','C1004','C1006','ALL',NULL,'同电话/同邮箱/同地址','Y',3,4,NULL,1156,NULL,'2026-11-20 13:05:10'),(3204,1103,'E-2203-2205','H1008','C1006','H1008','C1006','SERVE_ALL',NULL,'财务联系人','Y',4,3,NULL,1158,NULL,'2026-11-20 13:05:10'),(3205,1103,'E-2204-2205','H1003','H1008','H1003','H1008','ALL',NULL,'共账簿/共用联系方式','Y',4,7,NULL,1161,NULL,'2026-11-20 13:05:10'),(3301,1301,'E1301-H1001_1-C1001','H1001_1','C1001','H1001_1','C1001','INVEST',0.6000,'60%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3302,1301,'E1301-H1001_2-C1001','H1001_2','C1001','H1001_2','C1001','INVEST',0.2500,'25%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3303,1301,'E1301-H1001_3-C1001','H1001_3','C1001','H1001_3','C1001','INVEST',0.1500,'15%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3304,1301,'E1301-C1001-HPE1001_1001','C1001','HPE1001_1001','C1001','HPE1001_1001','SERVE_ALL',NULL,'执行董事兼总经理','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3305,1301,'E1301-C1001-HPE1001_1016','C1001','HPE1001_1016','C1001','HPE1001_1016','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3306,1301,'E1301-C1001-C1013','C1001','C1013','C1001','C1013','INVEST',0.5500,'55%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3307,1301,'E1301-C1001-C1002','C1001','C1002','C1001','C1002','INVEST',1.0000,'100%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3308,1301,'E1301-C1001-C1003','C1001','C1003','C1001','C1003','INVEST',0.7000,'70%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3309,1301,'E1301-C1001-C1016','C1001','C1016','C1001','C1016','INVEST',0.2500,'25%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3310,1301,'E1301-C1001-C1022','C1001','C1022','C1001','C1022','INVEST',0.5100,'51%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3311,1302,'E1302-C1001-C1002','C1001','C1002','C1001','C1002','INVEST',1.0000,'100%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3312,1302,'E1302-H1001_1-C1001','H1001_1','C1001','H1001_1','C1001','INVEST',0.6000,'60%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3313,1302,'E1302-C1002-HPE1002_1002','C1002','HPE1002_1002','C1002','HPE1002_1002','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3314,1302,'E1302-C1002-HPE1002_1003','C1002','HPE1002_1003','C1002','HPE1002_1003','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3315,1302,'E1302-C1002-C1007','C1002','C1007','C1002','C1007','INVEST',0.4000,'40%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3316,1303,'E1303-C1001-C1003','C1001','C1003','C1001','C1003','INVEST',0.7000,'70%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3317,1303,'E1303-H1003_2-C1003','H1003_2','C1003','H1003_2','C1003','INVEST',0.3000,'30%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3318,1303,'E1303-H1001_1-C1001','H1001_1','C1001','H1001_1','C1001','INVEST',0.6000,'60%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3319,1303,'E1303-C1003-HPE1003_1004','C1003','HPE1003_1004','C1003','HPE1003_1004','SERVE_ALL',NULL,'执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3320,1303,'E1303-C1003-HPE1003_1005','C1003','HPE1003_1005','C1003','HPE1003_1005','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3321,1303,'E1303-C1003-C1014','C1003','C1014','C1003','C1014','INVEST',0.6000,'60%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3322,1303,'E1303-C1003-C1008','C1003','C1008','C1003','C1008','INVEST',0.3500,'35%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3323,1304,'E1304-H1004_1-C1004','H1004_1','C1004','H1004_1','C1004','INVEST',0.5500,'55%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3324,1304,'E1304-H1004_2-C1004','H1004_2','C1004','H1004_2','C1004','INVEST',0.4500,'45%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3325,1304,'E1304-C1004-HPE1004_1006','C1004','HPE1004_1006','C1004','HPE1004_1006','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3326,1304,'E1304-C1004-HPE1004_1008','C1004','HPE1004_1008','C1004','HPE1004_1008','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3327,1304,'E1304-C1004-C1005','C1004','C1005','C1004','C1005','INVEST',0.3000,'30%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3328,1304,'E1304-C1004-C1021','C1004','C1021','C1004','C1021','INVEST',0.3500,'35%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3329,1305,'E1305-H1005_1-C1005','H1005_1','C1005','H1005_1','C1005','INVEST',0.7000,'70%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3330,1305,'E1305-C1004-C1005','C1004','C1005','C1004','C1005','INVEST',0.3000,'30%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3331,1305,'E1305-H1004_1-C1004','H1004_1','C1004','H1004_1','C1004','INVEST',0.5500,'55%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3332,1305,'E1305-C1005-HPE1005_1010','C1005','HPE1005_1010','C1005','HPE1005_1010','SERVE_ALL',NULL,'执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3333,1306,'E1306-H1006_1-C1006','H1006_1','C1006','H1006_1','C1006','INVEST',1.0000,'100%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3334,1306,'E1306-C1006-HPE1006_1007','C1006','HPE1006_1007','C1006','HPE1006_1007','SERVE_ALL',NULL,'执行董事兼总经理','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3335,1307,'E1307-H1007_1-C1007','H1007_1','C1007','H1007_1','C1007','INVEST',0.6000,'60%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3336,1307,'E1307-C1002-C1007','C1002','C1007','C1002','C1007','INVEST',0.4000,'40%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3337,1307,'E1307-C1001-C1002','C1001','C1002','C1001','C1002','INVEST',1.0000,'100%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3338,1307,'E1307-C1007-HPE1007_1009','C1007','HPE1007_1009','C1007','HPE1007_1009','SERVE_ALL',NULL,'执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3339,1308,'E1308-H1008_1-C1008','H1008_1','C1008','H1008_1','C1008','INVEST',0.6500,'65%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3340,1308,'E1308-C1003-C1008','C1003','C1008','C1003','C1008','INVEST',0.3500,'35%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3341,1308,'E1308-C1001-C1003','C1001','C1003','C1001','C1003','INVEST',0.7000,'70%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3342,1308,'E1308-H1003_1-C1003','H1003_1','C1003','H1003_1','C1003','INVEST',0.3000,'30%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3343,1308,'E1308-C1008-HPE1008_1011','C1008','HPE1008_1011','C1008','HPE1008_1011','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3344,1309,'E1309-H1009_1-C1009','H1009_1','C1009','H1009_1','C1009','INVEST',0.8000,'80%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3345,1309,'E1309-H1009_2-C1009','H1009_2','C1009','H1009_2','C1009','INVEST',0.2000,'20%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3346,1309,'E1309-C1009-HPE1009_1013','C1009','HPE1009_1013','C1009','HPE1009_1013','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3347,1309,'E1309-C1009-C1011','C1009','C1011','C1009','C1011','INVEST',0.1000,'10%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3348,1310,'E1310-H1010_1-C1010','H1010_1','C1010','H1010_1','C1010','INVEST',1.0000,'100%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3349,1310,'E1310-C1010-HPE1010_1012','C1010','HPE1010_1012','C1010','HPE1010_1012','SERVE_ALL',NULL,'执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3350,1311,'E1311-H1011_1-C1011','H1011_1','C1011','H1011_1','C1011','INVEST',0.9000,'90%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3351,1311,'E1311-C1009-C1011','C1009','C1011','C1009','C1011','INVEST',0.1000,'10%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3352,1311,'E1311-H1009_1-C1009','H1009_1','C1009','H1009_1','C1009','INVEST',0.8000,'80%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3353,1311,'E1311-C1011-HPE1011_1014','C1011','HPE1011_1014','C1011','HPE1011_1014','SERVE_ALL',NULL,'执行董事兼总经理','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3354,1311,'E1311-C1011-C1015','C1011','C1015','C1011','C1015','INVEST',0.3000,'30%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3355,1312,'E1312-H1012_1-C1012','H1012_1','C1012','H1012_1','C1012','INVEST',1.0000,'100%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3356,1312,'E1312-C1012-HPE1012_1015','C1012','HPE1012_1015','C1012','HPE1012_1015','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3357,1313,'E1313-C1001-C1013','C1001','C1013','C1001','C1013','INVEST',0.5500,'55%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3358,1313,'E1313-H1013_2-C1013','H1013_2','C1013','H1013_2','C1013','INVEST',0.4500,'45%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3359,1313,'E1313-H1001_1-C1001','H1001_1','C1001','H1001_1','C1001','INVEST',0.6000,'60%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3360,1313,'E1313-C1013-HPE1013_1017','C1013','HPE1013_1017','C1013','HPE1013_1017','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3361,1313,'E1313-C1013-HPE1013_1018','C1013','HPE1013_1018','C1013','HPE1013_1018','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3362,1314,'E1314-C1003-C1014','C1003','C1014','C1003','C1014','INVEST',0.6000,'60%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3363,1314,'E1314-H1014_2-C1014','H1014_2','C1014','H1014_2','C1014','INVEST',0.4000,'40%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3364,1314,'E1314-C1001-C1003','C1001','C1003','C1001','C1003','INVEST',0.7000,'70%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3365,1314,'E1314-H1003_1-C1003','H1003_1','C1003','H1003_1','C1003','INVEST',0.3000,'30%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3366,1314,'E1314-C1014-HPE1014_1019','C1014','HPE1014_1019','C1014','HPE1014_1019','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3367,1314,'E1314-C1014-HPE1014_1020','C1014','HPE1014_1020','C1014','HPE1014_1020','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3368,1315,'E1315-H1015_1-C1015','H1015_1','C1015','H1015_1','C1015','INVEST',0.7000,'70%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3369,1315,'E1315-C1011-C1015','C1011','C1015','C1011','C1015','INVEST',0.3000,'30%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3370,1315,'E1315-H1011_1-C1011','H1011_1','C1011','H1011_1','C1011','INVEST',0.9000,'90%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3371,1315,'E1315-C1009-C1011','C1009','C1011','C1009','C1011','INVEST',0.1000,'10%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3372,1315,'E1315-C1015-HPE1015_1021','C1015','HPE1015_1021','C1015','HPE1015_1021','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3373,1315,'E1315-C1015-HPE1015_1022','C1015','HPE1015_1022','C1015','HPE1015_1022','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3374,1316,'E1316-H1016_1-C1016','H1016_1','C1016','H1016_1','C1016','INVEST',0.5500,'55%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3375,1316,'E1316-C1001-C1016','C1001','C1016','C1001','C1016','INVEST',0.2500,'25%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3376,1316,'E1316-H1016_3-C1016','H1016_3','C1016','H1016_3','C1016','INVEST',0.2000,'20%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3377,1316,'E1316-H1001_1-C1001','H1001_1','C1001','H1001_1','C1001','INVEST',0.6000,'60%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3378,1316,'E1316-C1016-HPE1016_1023','C1016','HPE1016_1023','C1016','HPE1016_1023','SERVE_ALL',NULL,'法定代表人兼董事长','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3379,1316,'E1316-C1016-HPE1016_1024','C1016','HPE1016_1024','C1016','HPE1016_1024','SERVE_ALL',NULL,'总经理','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3380,1316,'E1316-C1016-HPE1016_1025','C1016','HPE1016_1025','C1016','HPE1016_1025','SERVE_ALL',NULL,'独立董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3381,1316,'E1316-C1016-C1017','C1016','C1017','C1016','C1017','INVEST',1.0000,'100%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3382,1316,'E1316-C1016-C1018','C1016','C1018','C1016','C1018','INVEST',0.6000,'60%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3383,1316,'E1316-C1016-C1019','C1016','C1019','C1016','C1019','INVEST',0.3000,'30%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3384,1316,'E1316-C1016-C1021','C1016','C1021','C1016','C1021','INVEST',0.4000,'40%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3385,1317,'E1317-C1016-C1017','C1016','C1017','C1016','C1017','INVEST',1.0000,'100%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3386,1317,'E1317-H1016_1-C1016','H1016_1','C1016','H1016_1','C1016','INVEST',0.5500,'55%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3387,1317,'E1317-C1001-C1016','C1001','C1016','C1001','C1016','INVEST',0.2500,'25%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3388,1317,'E1317-C1017-HPE1017_1026','C1017','HPE1017_1026','C1017','HPE1017_1026','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3389,1317,'E1317-C1017-HPE1017_1027','C1017','HPE1017_1027','C1017','HPE1017_1027','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3390,1317,'E1317-C1017-C1020','C1017','C1020','C1017','C1020','INVEST',0.5100,'51%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3391,1318,'E1318-C1016-C1018','C1016','C1018','C1016','C1018','INVEST',0.6000,'60%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3392,1318,'E1318-H1018_2-C1018','H1018_2','C1018','H1018_2','C1018','INVEST',0.4000,'40%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3393,1318,'E1318-H1016_1-C1016','H1016_1','C1016','H1016_1','C1016','INVEST',0.5500,'55%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3394,1318,'E1318-C1001-C1016','C1001','C1016','C1001','C1016','INVEST',0.2500,'25%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3395,1318,'E1318-C1018-HPE1018_1028','C1018','HPE1018_1028','C1018','HPE1018_1028','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3396,1318,'E1318-C1018-HPE1018_1029','C1018','HPE1018_1029','C1018','HPE1018_1029','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3397,1319,'E1319-H1019_1-C1019','H1019_1','C1019','H1019_1','C1019','INVEST',0.7000,'70%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3398,1319,'E1319-C1016-C1019','C1016','C1019','C1016','C1019','INVEST',0.3000,'30%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3399,1319,'E1319-H1016_1-C1016','H1016_1','C1016','H1016_1','C1016','INVEST',0.5500,'55%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3400,1319,'E1319-C1001-C1016','C1001','C1016','C1001','C1016','INVEST',0.2500,'25%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3401,1319,'E1319-C1019-HPE1019_1030','C1019','HPE1019_1030','C1019','HPE1019_1030','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3402,1320,'E1320-C1017-C1020','C1017','C1020','C1017','C1020','INVEST',0.5100,'51%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3403,1320,'E1320-H1020_2-C1020','H1020_2','C1020','H1020_2','C1020','INVEST',0.4900,'49%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3404,1320,'E1320-C1016-C1017','C1016','C1017','C1016','C1017','INVEST',1.0000,'100%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3405,1320,'E1320-C1020-HPE1020_1031','C1020','HPE1020_1031','C1020','HPE1020_1031','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3406,1320,'E1320-C1020-HPE1020_1032','C1020','HPE1020_1032','C1020','HPE1020_1032','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3407,1321,'E1321-C1016-C1021','C1016','C1021','C1016','C1021','INVEST',0.4000,'40%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3408,1321,'E1321-C1004-C1021','C1004','C1021','C1004','C1021','INVEST',0.3500,'35%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3409,1321,'E1321-H1021_3-C1021','H1021_3','C1021','H1021_3','C1021','INVEST',0.2500,'25%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3410,1321,'E1321-H1016_1-C1016','H1016_1','C1016','H1016_1','C1016','INVEST',0.5500,'55%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3411,1321,'E1321-C1001-C1016','C1001','C1016','C1001','C1016','INVEST',0.2500,'25%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3412,1321,'E1321-H1004_1-C1004','H1004_1','C1004','H1004_1','C1004','INVEST',0.5500,'55%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3413,1321,'E1321-C1021-HPE1021_1033','C1021','HPE1021_1033','C1021','HPE1021_1033','SERVE_ALL',NULL,'法定代表人兼经理','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3414,1321,'E1321-C1021-HPE1021_1034','C1021','HPE1021_1034','C1021','HPE1021_1034','SERVE_ALL',NULL,'副董事长','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3415,1321,'E1321-C1021-HPE1021_1035','C1021','HPE1021_1035','C1021','HPE1021_1035','SERVE_ALL',NULL,'董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3416,1322,'E1322-C1001-C1022','C1001','C1022','C1001','C1022','INVEST',0.5100,'51%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3417,1322,'E1322-H1022_2-C1022','H1022_2','C1022','H1022_2','C1022','INVEST',0.3400,'34%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3418,1322,'E1322-H1022_3-C1022','H1022_3','C1022','H1022_3','C1022','INVEST',0.1500,'15%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3419,1322,'E1322-H1001_1-C1001','H1001_1','C1001','H1001_1','C1001','INVEST',0.6000,'60%','Y',5,1,NULL,821,NULL,'2026-11-18 15:00:00'),(3420,1322,'E1322-C1022-HPE1022_1036','C1022','HPE1022_1036','C1022','HPE1022_1036','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3421,1322,'E1322-C1022-HPE1022_1037','C1022','HPE1022_1037','C1022','HPE1022_1037','SERVE_ALL',NULL,'董事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3422,1322,'E1322-C1022-HPE1022_1038','C1022','HPE1022_1038','C1022','HPE1022_1038','SERVE_ALL',NULL,'监事','Y',5,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3423,1401,'E1401-C1001-C1013','C1001','C1013','C1001','C1013','ALL',NULL,'同电话 021-58881001','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3424,1401,'E1401-C1001-C1020','C1001','C1020','C1001','C1020','ALL',NULL,'共同董监高：徐海燕','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3425,1401,'E1401-C1001-C1022','C1001','C1022','C1001','C1022','ALL',NULL,'共同董监高：陈国华','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3427,1401,'E1401-C1001-C1002','C1001','C1002','C1001','C1002','INVEST',1.0000,'100%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3428,1401,'E1401-C1001-C1003','C1001','C1003','C1001','C1003','INVEST',0.7000,'70%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3429,1401,'E1401-C1001-C1016','C1001','C1016','C1001','C1016','INVEST',0.2500,'25%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3431,1401,'E1401-HBS1001_1-C1001','HBS1001_1','C1001','HBS1001_1','C1001','INVEST',0.6000,'60%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3432,1401,'E1401-HBS1001_3-C1001','HBS1001_3','C1001','HBS1001_3','C1001','INVEST',0.1500,'15%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3433,1401,'E1401-C1001-HBS-PE1001_1001','C1001','HBS-PE1001_1001','C1001','HBS-PE1001_1001','SERVE_ALL',NULL,'执行董事兼总经理','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3434,1401,'E1401-C1001-HBS-PE1001_1016','C1001','HBS-PE1001_1016','C1001','HBS-PE1001_1016','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3435,1402,'E1402-C1002-C1013','C1002','C1013','C1002','C1013','ALL',NULL,'共同董监高：周海峰','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3436,1402,'E1402-C1002-C1003','C1002','C1003','C1002','C1003','ALL',NULL,'共同董监高：周海峰','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3437,1402,'E1402-C1001-C1002','C1001','C1002','C1001','C1002','INVEST',1.0000,'100%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3438,1402,'E1402-C1002-C1007','C1002','C1007','C1002','C1007','INVEST',0.4000,'40%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3439,1402,'E1402-C1002-HBS-PE1002_1002','C1002','HBS-PE1002_1002','C1002','HBS-PE1002_1002','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3440,1402,'E1402-C1002-HBS-PE1002_1003','C1002','HBS-PE1002_1003','C1002','HBS-PE1002_1003','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3441,1403,'E1403-C1003-C1014','C1003','C1014','C1003','C1014','ALL',NULL,'同电话 021-58881003','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3442,1403,'E1403-C1003-C1013','C1003','C1013','C1003','C1013','ALL',NULL,'共同董监高：周海峰','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3444,1403,'E1403-C1003-C1002','C1003','C1002','C1003','C1002','ALL',NULL,'共同董监高：周海峰','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3445,1403,'E1403-C1003-C1005','C1003','C1005','C1003','C1005','ALL',NULL,'共同董监高：孙立群','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3446,1403,'E1403-C1001-C1003','C1001','C1003','C1001','C1003','INVEST',0.7000,'70%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3448,1403,'E1403-C1003-C1008','C1003','C1008','C1003','C1008','INVEST',0.3500,'35%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3449,1403,'E1403-HBS1003_2-C1003','HBS1003_2','C1003','HBS1003_2','C1003','INVEST',0.3000,'30%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3450,1403,'E1403-C1003-HBS-PE1003_1004','C1003','HBS-PE1003_1004','C1003','HBS-PE1003_1004','SERVE_ALL',NULL,'执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3451,1403,'E1403-C1003-HBS-PE1003_1005','C1003','HBS-PE1003_1005','C1003','HBS-PE1003_1005','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3452,1404,'E1404-C1004-C1006','C1004','C1006','C1004','C1006','ALL',NULL,'同电话 021-58881004','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3455,1404,'E1404-C1004-C1007','C1004','C1007','C1004','C1007','ALL',NULL,'共同董监高：赵文博','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3456,1404,'E1404-C1004-C1021','C1004','C1021','C1004','C1021','ALL',NULL,'共同董监高：刘志强','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3457,1404,'E1404-C1004-C1005','C1004','C1005','C1004','C1005','INVEST',0.3000,'30%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3459,1404,'E1404-HBS1004_1-C1004','HBS1004_1','C1004','HBS1004_1','C1004','INVEST',0.5500,'55%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3460,1404,'E1404-HBS1004_2-C1004','HBS1004_2','C1004','HBS1004_2','C1004','INVEST',0.4500,'45%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3461,1404,'E1404-C1004-HBS-PE1004_1006','C1004','HBS-PE1004_1006','C1004','HBS-PE1004_1006','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3462,1404,'E1404-C1004-HBS-PE1004_1008','C1004','HBS-PE1004_1008','C1004','HBS-PE1004_1008','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3463,1405,'E1405-C1005-C1014','C1005','C1014','C1005','C1014','ALL',NULL,'共同董监高：孙立群','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3464,1405,'E1405-C1005-C1003','C1005','C1003','C1005','C1003','ALL',NULL,'共同董监高：孙立群','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3465,1405,'E1405-C1004-C1005','C1004','C1005','C1004','C1005','INVEST',0.3000,'30%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3466,1405,'E1405-HBS1005_1-C1005','HBS1005_1','C1005','HBS1005_1','C1005','INVEST',0.7000,'70%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3467,1405,'E1405-C1005-HBS-PE1005_1010','C1005','HBS-PE1005_1010','C1005','HBS-PE1005_1010','SERVE_ALL',NULL,'执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3468,1406,'E1406-C1006-C1004','C1006','C1004','C1006','C1004','ALL',NULL,'同电话 021-58881004','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3471,1406,'E1406-C1006-C1021','C1006','C1021','C1006','C1021','ALL',NULL,'共同董监高：刘志强','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3472,1406,'E1406-HBS1006_1-C1006','HBS1006_1','C1006','HBS1006_1','C1006','INVEST',1.0000,'100%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3473,1406,'E1406-C1006-HBS-PE1006_1007','C1006','HBS-PE1006_1007','C1006','HBS-PE1006_1007','SERVE_ALL',NULL,'执行董事兼总经理','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3474,1407,'E1407-C1007-C1004','C1007','C1004','C1007','C1004','ALL',NULL,'共同董监高：赵文博','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3475,1407,'E1407-C1002-C1007','C1002','C1007','C1002','C1007','INVEST',0.4000,'40%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3476,1407,'E1407-HBS1007_1-C1007','HBS1007_1','C1007','HBS1007_1','C1007','INVEST',0.6000,'60%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3477,1407,'E1407-C1007-HBS-PE1007_1009','C1007','HBS-PE1007_1009','C1007','HBS-PE1007_1009','SERVE_ALL',NULL,'执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3478,1408,'E1408-C1008-C1010','C1008','C1010','C1008','C1010','ALL',NULL,'同电话 021-58881008','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3480,1408,'E1408-C1003-C1008','C1003','C1008','C1003','C1008','INVEST',0.3500,'35%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3481,1408,'E1408-HBS1008_1-C1008','HBS1008_1','C1008','HBS1008_1','C1008','INVEST',0.6500,'65%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3482,1408,'E1408-C1008-HBS-PE1008_1011','C1008','HBS-PE1008_1011','C1008','HBS-PE1008_1011','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3483,1409,'E1409-C1009-C1011','C1009','C1011','C1009','C1011','ALL',NULL,'同邮箱','Y',3,5,NULL,1156,NULL,'2026-11-18 15:00:00'),(3484,1409,'E1409-C1009-C1015','C1009','C1015','C1009','C1015','ALL',NULL,'共同董监高：郑晓岚','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3487,1409,'E1409-HBS1009_1-C1009','HBS1009_1','C1009','HBS1009_1','C1009','INVEST',0.8000,'80%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3488,1409,'E1409-HBS1009_2-C1009','HBS1009_2','C1009','HBS1009_2','C1009','INVEST',0.2000,'20%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3489,1409,'E1409-C1009-HBS-PE1009_1013','C1009','HBS-PE1009_1013','C1009','HBS-PE1009_1013','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3490,1410,'E1410-C1010-C1008','C1010','C1008','C1010','C1008','ALL',NULL,'同电话 021-58881008','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3492,1410,'E1410-HBS1010_1-C1010','HBS1010_1','C1010','HBS1010_1','C1010','INVEST',1.0000,'100%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3493,1410,'E1410-C1010-HBS-PE1010_1012','C1010','HBS-PE1010_1012','C1010','HBS-PE1010_1012','SERVE_ALL',NULL,'执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3494,1411,'E1411-C1011-C1015','C1011','C1015','C1011','C1015','ALL',NULL,'同电话 021-58881011','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3495,1411,'E1411-C1011-C1009','C1011','C1009','C1011','C1009','ALL',NULL,'同邮箱','Y',3,5,NULL,1156,NULL,'2026-11-18 15:00:00'),(3498,1411,'E1411-C1009-C1011','C1009','C1011','C1009','C1011','INVEST',0.1000,'10%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3500,1411,'E1411-HBS1011_1-C1011','HBS1011_1','C1011','HBS1011_1','C1011','INVEST',0.9000,'90%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3501,1411,'E1411-C1011-HBS-PE1011_1014','C1011','HBS-PE1011_1014','C1011','HBS-PE1011_1014','SERVE_ALL',NULL,'执行董事兼总经理','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3502,1412,'E1412-C1012-C1015','C1012','C1015','C1012','C1015','ALL',NULL,'共同董监高：许文娟','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3503,1412,'E1412-HBS1012_1-C1012','HBS1012_1','C1012','HBS1012_1','C1012','INVEST',1.0000,'100%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3504,1412,'E1412-C1012-HBS-PE1012_1015','C1012','HBS-PE1012_1015','C1012','HBS-PE1012_1015','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3505,1413,'E1413-C1013-C1001','C1013','C1001','C1013','C1001','ALL',NULL,'同电话 021-58881001','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3506,1413,'E1413-C1013-C1002','C1013','C1002','C1013','C1002','ALL',NULL,'共同董监高：周海峰','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3507,1413,'E1413-C1013-C1003','C1013','C1003','C1013','C1003','ALL',NULL,'共同董监高：周海峰','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3508,1413,'E1413-C1013-C1016','C1013','C1016','C1013','C1016','ALL',NULL,'共同董监高：顾伟明','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3509,1413,'E1413-C1001-C1013','C1001','C1013','C1001','C1013','INVEST',0.5500,'55%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3510,1413,'E1413-HBS1013_2-C1013','HBS1013_2','C1013','HBS1013_2','C1013','INVEST',0.4500,'45%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3511,1413,'E1413-C1013-HBS-PE1013_1017','C1013','HBS-PE1013_1017','C1013','HBS-PE1013_1017','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3512,1413,'E1413-C1013-HBS-PE1013_1018','C1013','HBS-PE1013_1018','C1013','HBS-PE1013_1018','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3513,1414,'E1414-C1014-C1003','C1014','C1003','C1014','C1003','ALL',NULL,'同电话 021-58881003','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3515,1414,'E1414-C1014-C1005','C1014','C1005','C1014','C1005','ALL',NULL,'共同董监高：孙立群','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3516,1414,'E1414-C1003-C1014','C1003','C1014','C1003','C1014','INVEST',0.6000,'60%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3517,1414,'E1414-HBS1014_2-C1014','HBS1014_2','C1014','HBS1014_2','C1014','INVEST',0.4000,'40%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3518,1414,'E1414-C1014-HBS-PE1014_1019','C1014','HBS-PE1014_1019','C1014','HBS-PE1014_1019','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3519,1414,'E1414-C1014-HBS-PE1014_1020','C1014','HBS-PE1014_1020','C1014','HBS-PE1014_1020','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3520,1415,'E1415-C1015-C1011','C1015','C1011','C1015','C1011','ALL',NULL,'同电话 021-58881011','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3521,1415,'E1415-C1015-C1009','C1015','C1009','C1015','C1009','ALL',NULL,'共同董监高：郑晓岚','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3523,1415,'E1415-C1015-C1012','C1015','C1012','C1015','C1012','ALL',NULL,'共同董监高：许文娟','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3524,1415,'E1415-C1011-C1015','C1011','C1015','C1011','C1015','INVEST',0.3000,'30%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3525,1415,'E1415-HBS1015_1-C1015','HBS1015_1','C1015','HBS1015_1','C1015','INVEST',0.7000,'70%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3526,1415,'E1415-C1015-HBS-PE1015_1021','C1015','HBS-PE1015_1021','C1015','HBS-PE1015_1021','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3527,1415,'E1415-C1015-HBS-PE1015_1022','C1015','HBS-PE1015_1022','C1015','HBS-PE1015_1022','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3528,1416,'E1416-C1016-C1019','C1016','C1019','C1016','C1019','ALL',NULL,'同电话 021-66881016','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3529,1416,'E1416-C1016-C1020','C1016','C1020','C1016','C1020','ALL',NULL,'同电话 021-66881016','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3531,1416,'E1416-C1016-C1013','C1016','C1013','C1016','C1013','ALL',NULL,'共同董监高：顾伟明','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3532,1416,'E1416-C1016-C1017','C1016','C1017','C1016','C1017','ALL',NULL,'共同董监高：潘莉、沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3533,1416,'E1416-C1016-C1018','C1016','C1018','C1016','C1018','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3536,1416,'E1416-C1016-C1021','C1016','C1021','C1016','C1021','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3537,1416,'E1416-C1001-C1016','C1001','C1016','C1001','C1016','INVEST',0.2500,'25%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3542,1416,'E1416-HBS1016_1-C1016','HBS1016_1','C1016','HBS1016_1','C1016','INVEST',0.5500,'55%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3543,1416,'E1416-C1016-HBS-PE1016_1023','C1016','HBS-PE1016_1023','C1016','HBS-PE1016_1023','SERVE_ALL',NULL,'法定代表人兼董事长','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3544,1416,'E1416-C1016-HBS-PE1016_1024','C1016','HBS-PE1016_1024','C1016','HBS-PE1016_1024','SERVE_ALL',NULL,'总经理','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3545,1416,'E1416-C1016-HBS-PE1016_1025','C1016','HBS-PE1016_1025','C1016','HBS-PE1016_1025','SERVE_ALL',NULL,'独立董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3546,1417,'E1417-C1017-C1016','C1017','C1016','C1017','C1016','ALL',NULL,'共同董监高：沈文彬、潘莉','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3547,1417,'E1417-C1017-C1018','C1017','C1018','C1017','C1018','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3548,1417,'E1417-C1017-C1019','C1017','C1019','C1017','C1019','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3549,1417,'E1417-C1017-C1020','C1017','C1020','C1017','C1020','ALL',NULL,'共同董监高：潘莉','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3550,1417,'E1417-C1017-C1021','C1017','C1021','C1017','C1021','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3551,1417,'E1417-C1016-C1017','C1016','C1017','C1016','C1017','INVEST',1.0000,'100%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3553,1417,'E1417-C1017-HBS-PE1017_1026','C1017','HBS-PE1017_1026','C1017','HBS-PE1017_1026','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3554,1417,'E1417-C1017-HBS-PE1017_1027','C1017','HBS-PE1017_1027','C1017','HBS-PE1017_1027','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3555,1418,'E1418-C1018-C1016','C1018','C1016','C1018','C1016','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3556,1418,'E1418-C1018-C1017','C1018','C1017','C1018','C1017','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3557,1418,'E1418-C1018-C1019','C1018','C1019','C1018','C1019','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3558,1418,'E1418-C1018-C1021','C1018','C1021','C1018','C1021','ALL',NULL,'共同董监高：罗伯涛、沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3559,1418,'E1418-C1016-C1018','C1016','C1018','C1016','C1018','INVEST',0.6000,'60%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3560,1418,'E1418-HBS1018_2-C1018','HBS1018_2','C1018','HBS1018_2','C1018','INVEST',0.4000,'40%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3561,1418,'E1418-C1018-HBS-PE1018_1028','C1018','HBS-PE1018_1028','C1018','HBS-PE1018_1028','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3562,1418,'E1418-C1018-HBS-PE1018_1029','C1018','HBS-PE1018_1029','C1018','HBS-PE1018_1029','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3563,1419,'E1419-C1019-C1016','C1019','C1016','C1019','C1016','ALL',NULL,'同电话 021-66881016','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3564,1419,'E1419-C1019-C1020','C1019','C1020','C1019','C1020','ALL',NULL,'同电话 021-66881016','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3567,1419,'E1419-C1019-C1017','C1019','C1017','C1019','C1017','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3568,1419,'E1419-C1019-C1018','C1019','C1018','C1019','C1018','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3569,1419,'E1419-C1019-C1021','C1019','C1021','C1019','C1021','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3570,1419,'E1419-C1016-C1019','C1016','C1019','C1016','C1019','INVEST',0.3000,'30%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3571,1419,'E1419-HBS1019_1-C1019','HBS1019_1','C1019','HBS1019_1','C1019','INVEST',0.7000,'70%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3572,1419,'E1419-C1019-HBS-PE1019_1030','C1019','HBS-PE1019_1030','C1019','HBS-PE1019_1030','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3573,1420,'E1420-C1020-C1016','C1020','C1016','C1020','C1016','ALL',NULL,'同电话 021-66881016','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3574,1420,'E1420-C1020-C1019','C1020','C1019','C1020','C1019','ALL',NULL,'同电话 021-66881016','Y',3,4,NULL,1156,NULL,'2026-11-18 15:00:00'),(3575,1420,'E1420-C1020-C1001','C1020','C1001','C1020','C1001','ALL',NULL,'共同董监高：徐海燕','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3577,1420,'E1420-C1020-C1017','C1020','C1017','C1020','C1017','ALL',NULL,'共同董监高：潘莉','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3578,1420,'E1420-C1017-C1020','C1017','C1020','C1017','C1020','INVEST',0.5100,'51%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3579,1420,'E1420-HBS1020_2-C1020','HBS1020_2','C1020','HBS1020_2','C1020','INVEST',0.4900,'49%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3580,1420,'E1420-C1020-HBS-PE1020_1031','C1020','HBS-PE1020_1031','C1020','HBS-PE1020_1031','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3581,1420,'E1420-C1020-HBS-PE1020_1032','C1020','HBS-PE1020_1032','C1020','HBS-PE1020_1032','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3582,1421,'E1421-C1021-C1004','C1021','C1004','C1021','C1004','ALL',NULL,'共同董监高：刘志强','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3583,1421,'E1421-C1021-C1006','C1021','C1006','C1021','C1006','ALL',NULL,'共同董监高：刘志强','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3584,1421,'E1421-C1021-C1016','C1021','C1016','C1021','C1016','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3585,1421,'E1421-C1021-C1017','C1021','C1017','C1021','C1017','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3586,1421,'E1421-C1021-C1018','C1021','C1018','C1021','C1018','ALL',NULL,'共同董监高：沈文彬、罗伯涛','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3587,1421,'E1421-C1021-C1019','C1021','C1019','C1021','C1019','ALL',NULL,'共同董监高：沈文彬','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3588,1421,'E1421-C1016-C1021','C1016','C1021','C1016','C1021','INVEST',0.4000,'40%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3589,1421,'E1421-C1004-C1021','C1004','C1021','C1004','C1021','INVEST',0.3500,'35%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3590,1421,'E1421-HBS1021_3-C1021','HBS1021_3','C1021','HBS1021_3','C1021','INVEST',0.2500,'25%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3591,1421,'E1421-C1021-HBS-PE1021_1033','C1021','HBS-PE1021_1033','C1021','HBS-PE1021_1033','SERVE_ALL',NULL,'法定代表人兼经理','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3592,1421,'E1421-C1021-HBS-PE1021_1034','C1021','HBS-PE1021_1034','C1021','HBS-PE1021_1034','SERVE_ALL',NULL,'副董事长','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3593,1421,'E1421-C1021-HBS-PE1021_1035','C1021','HBS-PE1021_1035','C1021','HBS-PE1021_1035','SERVE_ALL',NULL,'董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3594,1422,'E1422-C1022-C1001','C1022','C1001','C1022','C1001','ALL',NULL,'共同董监高：陈国华','Y',4,7,NULL,1156,NULL,'2026-11-18 15:00:00'),(3595,1422,'E1422-C1001-C1022','C1001','C1022','C1001','C1022','INVEST',0.5100,'51%','Y',5,1,NULL,1160,NULL,'2026-11-18 15:00:00'),(3596,1422,'E1422-HBS1022_2-C1022','HBS1022_2','C1022','HBS1022_2','C1022','INVEST',0.3400,'34%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3597,1422,'E1422-HBS1022_3-C1022','HBS1022_3','C1022','HBS1022_3','C1022','INVEST',0.1500,'15%','Y',5,1,NULL,1123,NULL,'2026-11-18 15:00:00'),(3598,1422,'E1422-C1022-HBS-PE1022_1036','C1022','HBS-PE1022_1036','C1022','HBS-PE1022_1036','SERVE_ALL',NULL,'法定代表人兼执行董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3599,1422,'E1422-C1022-HBS-PE1022_1037','C1022','HBS-PE1022_1037','C1022','HBS-PE1022_1037','SERVE_ALL',NULL,'董事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00'),(3600,1422,'E1422-C1022-HBS-PE1022_1038','C1022','HBS-PE1022_1038','C1022','HBS-PE1022_1038','SERVE_ALL',NULL,'监事','Y',4,3,NULL,1158,NULL,'2026-11-18 15:00:00');
/*!40000 ALTER TABLE `srm_graph_edge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_graph_node`
--

DROP TABLE IF EXISTS `srm_graph_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_graph_node` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `task_id` bigint unsigned NOT NULL COMMENT '归属任务（图谱结果按任务/版本留档，不同任务节点互不复用 id）',
  `node_key` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '节点唯一标识：接口 节点id/节点图id 或 公司或人id/自然人pid',
  `node_type` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'Company' COMMENT 'Company 公司 / Human 人（1160「对象类型 Company-公司, Human-人」、1161 同名字段）/ Other 其他',
  `node_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '节点名 / 节点名称（两字段来源不同时以返回顺序记录，不擅自合并）',
  `credit_code` varchar(18) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '统一社会信用代码（1160）',
  `reg_status` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册状态 / 企业状态（1160/1161），注销吊销打角标',
  `level_no` tinyint DEFAULT NULL COMMENT '层级，逐层展开定位用',
  `parent_node_key` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上一层节点（1160 子对象递归还原父子链）',
  `can_extend` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'true-可延伸 false-不可延伸（1160 原文字段名逐字保留），仅 true 显示展开入口',
  `red_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否标红（1123），控制人/重点监控节点高亮',
  `is_monitor` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否重点监控供应商（本地主档 join，图例区分）',
  `supplier_id` bigint unsigned DEFAULT NULL COMMENT '命中本地主档时的 id',
  `group_key` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '节点聚合（1161 原文重复出现 2 次，按返回顺序记 ext_json.group_seq）',
  `attrs_json` json DEFAULT NULL COMMENT '属性信息 / 属性（接口原样，详情面板读）',
  `ext_json` json DEFAULT NULL COMMENT '其它未固化字段',
  `source_api_id` int DEFAULT NULL,
  `raw_id` bigint unsigned DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_task_node` (`task_id`,`node_key`),
  KEY `idx_node_name` (`node_name`),
  KEY `idx_supplier` (`supplier_id`),
  KEY `idx_task_level` (`task_id`,`level_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2605 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='图谱节点（页面5/6/7 共用一套，按 task_id 隔离）。APPEND-ONLY：一次任务一份结果集，历史版本不被后续分析覆盖';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_graph_node`
--

LOCK TABLES `srm_graph_node` WRITE;
/*!40000 ALTER TABLE `srm_graph_node` DISABLE KEYS */;
INSERT INTO `srm_graph_node` VALUES (2001,1101,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',0,NULL,'1','0','1',1001,'GRP-HS','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{\"tycId\": \"T1001\"}',821,NULL,'2026-11-20 10:00:20'),(2002,1101,'H1001','Human','陈国华',NULL,NULL,1,'C1001','1','0','0',NULL,'GRP-HS','{\"position\": \"疑似实际控制人\", \"ratioLabel\": \"60%\"}','{\"source\": \"actualController\"}',1123,NULL,'2026-11-20 10:00:20'),(2003,1101,'C1002','Company','华晟建筑工程有限公司','91310115MA1K10002B','存续',1,'C1001','1','0','1',1002,'GRP-HS','{\"position\": \"全资子公司\", \"ratioLabel\": \"100%\"}','{\"tycId\": \"T1002\"}',1160,NULL,'2026-11-20 10:00:20'),(2004,1101,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',1,'C1001','1','0','0',1003,'GRP-HS','{\"position\": \"控股子公司\", \"ratioLabel\": \"70%\"}','{\"tycId\": \"T1003\"}',1160,NULL,'2026-11-20 10:00:20'),(2005,1101,'H1002','Human','李明远',NULL,NULL,2,'C1002','0','0','0',NULL,'GRP-HS','{\"position\": \"法定代表人/执行董事\", \"ratioLabel\": \"任职\"}','{\"source\": \"staff\"}',1158,NULL,'2026-11-20 10:00:20'),(2006,1101,'C1007','Company','海通机电设备有限公司','91310113MA1K10007G','存续',2,'C1002','1','0','0',1007,'GRP-HS','{\"position\": \"参股企业\", \"ratioLabel\": \"40%\"}','{\"tycId\": \"T1007\"}',1160,NULL,'2026-11-20 10:00:20'),(2007,1101,'C1008','Company','中启环保工程有限公司','91310118MA1K10008H','存续',2,'C1003','1','1','1',1008,'GRP-HS','{\"position\": \"控股子公司\", \"ratioLabel\": \"35%\"}','{\"tycId\": \"T1008\"}',1160,NULL,'2026-11-20 10:00:20'),(2008,1101,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',2,'C1001','1','0','1',1004,'GRP-HX','{\"position\": \"联营企业\", \"ratioLabel\": \"45%\"}','{\"tycId\": \"T1004\"}',1160,NULL,'2026-11-20 10:00:20'),(2009,1101,'C1005','Company','鑫达新材料科技有限公司','91310117MA1K10005E','存续',3,'C1004','1','0','0',1005,'GRP-HX','{\"position\": \"参股企业\", \"ratioLabel\": \"30%\"}','{\"tycId\": \"T1005\"}',1160,NULL,'2026-11-20 10:00:20'),(2010,1101,'H1003','Human','刘志强',NULL,NULL,3,'C1004','0','1','1',NULL,'GRP-HX','{\"position\": \"股东/董监高\", \"ratioLabel\": \"55%\"}','{\"source\": \"shareholder+staff\"}',1123,NULL,'2026-11-20 10:00:20'),(2011,1101,'H1004','Human','赵文博',NULL,NULL,3,'C1004','0','0','0',NULL,'GRP-HX','{\"position\": \"股东\", \"ratioLabel\": \"45%\"}','{\"source\": \"shareholder\"}',1160,NULL,'2026-11-20 10:00:20'),(2012,1101,'C1009','Company','联志电子科技有限公司','91310114MA1K10009J','存续',2,'C1003','1','0','0',1009,'GRP-HS','{\"position\": \"参股企业\", \"ratioLabel\": \"20%\"}','{\"tycId\": \"T1009\"}',1160,NULL,'2026-11-20 10:00:20'),(2013,1101,'C1011','Company','瑞昇信息技术有限公司','91310109MA1K10011L','存续',3,'C1009','1','0','0',1011,'GRP-HS','{\"position\": \"参股企业\", \"ratioLabel\": \"10%\"}','{\"tycId\": \"T1011\"}',1160,NULL,'2026-11-20 10:00:20'),(2014,1101,'H1005','Human','郑晓岚',NULL,NULL,2,'C1009','0','0','0',NULL,'GRP-HS','{\"position\": \"法定代表人/董监高\", \"ratioLabel\": \"任职\"}','{\"source\": \"staff\"}',1158,NULL,'2026-11-20 10:00:20'),(2015,1101,'C1006','Company','鸿远物流服务有限公司','91310105MA1K10006F','存续',2,'C1001','1','0','0',1006,'GRP-HX','{\"position\": \"关联企业\", \"ratioLabel\": \"同账簿信息\"}','{\"tycId\": \"T1006\"}',1156,NULL,'2026-11-20 10:00:20'),(2016,1101,'H1006','Human','孙立群',NULL,NULL,1,'C1005','0','0','0',NULL,'GRP-XD','{\"position\": \"法定代表人\", \"ratioLabel\": \"任职\"}','{\"source\": \"staff\"}',1158,NULL,'2026-11-20 10:00:20'),(2101,1102,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',0,NULL,'1','0','1',1001,'GRP-HS','{\"position\": \"中心企业\"}','{\"tycId\": \"T1001\"}',1161,NULL,'2026-11-20 11:20:15'),(2102,1102,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',1,'C1001','1','0','1',1004,'GRP-HX','{\"position\": \"关联方\"}','{\"tycId\": \"T1004\"}',1161,NULL,'2026-11-20 11:20:15'),(2103,1102,'C1006','Company','鸿远物流服务有限公司','91310105MA1K10006F','存续',1,'C1001','1','0','0',1006,'GRP-HX','{\"position\": \"关联方\"}','{\"tycId\": \"T1006\"}',1161,NULL,'2026-11-20 11:20:15'),(2104,1102,'H1003','Human','刘志强',NULL,NULL,2,'C1004','0','1','1',NULL,'GRP-HX','{\"position\": \"账簿共用人/实际经办人\"}','{\"source\": \"bookkeeping\"}',1123,NULL,'2026-11-20 11:20:15'),(2105,1102,'H1007','Human','周晓芸',NULL,NULL,2,'C1006','0','1','0',NULL,'GRP-HX','{\"position\": \"关联财务联系人\"}','{\"source\": \"bookkeeping\"}',1161,NULL,'2026-11-20 11:20:15'),(2106,1102,'C1009','Company','联志电子科技有限公司','91310114MA1K10009J','存续',2,'C1004','1','0','0',1009,'GRP-HS','{\"position\": \"关联方\"}','{\"tycId\": \"T1009\"}',1161,NULL,'2026-11-20 11:20:15'),(2201,1103,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',0,NULL,'1','0','1',1004,'GRP-HX','{\"position\": \"中心企业\"}','{\"tycId\": \"T1004\"}',1161,NULL,'2026-11-20 13:05:10'),(2202,1103,'C1005','Company','鑫达新材料科技有限公司','91310117MA1K10005E','存续',1,'C1004','1','0','0',1005,'GRP-HX','{\"position\": \"关联方（持股30%）\"}','{\"tycId\": \"T1005\"}',1160,NULL,'2026-11-20 13:05:10'),(2203,1103,'C1006','Company','鸿远物流服务有限公司','91310105MA1K10006F','存续',1,'C1004','1','0','0',1006,'GRP-HX','{\"position\": \"关联方（同电话/同邮箱/同地址）\"}','{\"tycId\": \"T1006\"}',1156,NULL,'2026-11-20 13:05:10'),(2204,1103,'H1003','Human','刘志强',NULL,NULL,1,'C1004','0','1','1',NULL,'GRP-HX','{\"position\": \"法定代表人/股东（55%）/账簿共用人\"}','{\"source\": \"shareholder+staff+bookkeeping\"}',1123,NULL,'2026-11-20 13:05:10'),(2205,1103,'H1008','Human','周晓芸',NULL,NULL,2,'C1006','0','0','0',NULL,'GRP-HX','{\"position\": \"关联财务联系人/代记账经办人\"}','{\"source\": \"bookkeeping\"}',1161,NULL,'2026-11-20 13:05:10'),(2301,1301,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',0,NULL,'1','0','1',1001,'GRP-1001','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2302,1301,'H1001_1','Human','陈国华',NULL,NULL,1,'C1001','0','1','0',NULL,'GRP-1001','{\"position\": \"自然人股东\", \"ratioLabel\": \"60%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2303,1301,'H1001_2','Human','上海华晟投资管理合伙企业(有限合伙)',NULL,NULL,1,'C1001','0','0','0',NULL,'GRP-1001','{\"position\": \"自然人股东\", \"ratioLabel\": \"25%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2304,1301,'H1001_3','Human','李明远',NULL,NULL,1,'C1001','0','1','0',NULL,'GRP-1001','{\"position\": \"自然人股东\", \"ratioLabel\": \"15%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2305,1301,'HPE1001_1001','Human','陈国华',NULL,NULL,1,'C1001','0','0','0',NULL,'GRP-1001','{\"position\": \"执行董事兼总经理\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2306,1301,'HPE1001_1016','Human','徐海燕',NULL,NULL,1,'C1001','0','0','0',NULL,'GRP-1001','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2307,1301,'C1013','Company','蓝湾海洋工程有限公司','91310115MA1K10013N','存续',1,'C1001','1','0','1',1013,'GRP-1001','{\"position\": \"被投资企业\", \"ratioLabel\": \"55%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2308,1301,'C1002','Company','华晟建筑工程有限公司','91310115MA1K10002B','存续',1,'C1001','1','0','1',1002,'GRP-1001','{\"position\": \"被投资企业\", \"ratioLabel\": \"100%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2309,1301,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',1,'C1001','1','0','0',1003,'GRP-1001','{\"position\": \"被投资企业\", \"ratioLabel\": \"70%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2310,1301,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1001','1','0','1',1016,'GRP-1001','{\"position\": \"被投资企业\", \"ratioLabel\": \"25%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2311,1301,'C1022','Company','华辰智造科技有限公司','91310117MA1K10022X','存续',1,'C1001','1','0','1',1022,'GRP-1001','{\"position\": \"被投资企业\", \"ratioLabel\": \"51%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2312,1302,'C1002','Company','华晟建筑工程有限公司','91310115MA1K10002B','存续',0,NULL,'1','0','1',1002,'GRP-1002','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2313,1302,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1002','1','0','1',1001,'GRP-1002','{\"position\": \"股东企业\", \"ratioLabel\": \"100%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2314,1302,'H1001_1','Human','陈国华',NULL,NULL,2,'C1001','0','1','0',NULL,'GRP-1002','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"60%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2315,1302,'HPE1002_1002','Human','李明远',NULL,NULL,1,'C1002','0','0','0',NULL,'GRP-1002','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2316,1302,'HPE1002_1003','Human','周海峰',NULL,NULL,1,'C1002','0','0','0',NULL,'GRP-1002','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2317,1302,'C1007','Company','海通机电设备有限公司','91310113MA1K10007G','存续',1,'C1002','1','0','0',1007,'GRP-1002','{\"position\": \"被投资企业\", \"ratioLabel\": \"40%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2318,1303,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',0,NULL,'1','0','0',1003,'GRP-1003','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2319,1303,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1003','1','0','1',1001,'GRP-1003','{\"position\": \"股东企业\", \"ratioLabel\": \"70%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2320,1303,'H1003_2','Human','周海峰',NULL,NULL,1,'C1003','0','1','0',NULL,'GRP-1003','{\"position\": \"自然人股东\", \"ratioLabel\": \"30%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2321,1303,'H1001_1','Human','陈国华',NULL,NULL,2,'C1001','0','1','0',NULL,'GRP-1003','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"60%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2322,1303,'HPE1003_1004','Human','周海峰',NULL,NULL,1,'C1003','0','0','0',NULL,'GRP-1003','{\"position\": \"执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2323,1303,'HPE1003_1005','Human','孙立群',NULL,NULL,1,'C1003','0','0','0',NULL,'GRP-1003','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2324,1303,'C1014','Company','凯越精密机械有限公司','91310116MA1K10014P','存续',1,'C1003','1','0','1',1014,'GRP-1003','{\"position\": \"被投资企业\", \"ratioLabel\": \"60%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2325,1303,'C1008','Company','中启环保工程有限公司','91310118MA1K10008H','存续',1,'C1003','1','0','1',1008,'GRP-1003','{\"position\": \"被投资企业\", \"ratioLabel\": \"35%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2326,1304,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',0,NULL,'1','0','1',1004,'GRP-1004','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2327,1304,'H1004_1','Human','刘志强',NULL,NULL,1,'C1004','0','1','0',NULL,'GRP-1004','{\"position\": \"自然人股东\", \"ratioLabel\": \"55%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2328,1304,'H1004_2','Human','赵文博',NULL,NULL,1,'C1004','0','1','0',NULL,'GRP-1004','{\"position\": \"自然人股东\", \"ratioLabel\": \"45%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2329,1304,'HPE1004_1006','Human','刘志强',NULL,NULL,1,'C1004','0','0','0',NULL,'GRP-1004','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2330,1304,'HPE1004_1008','Human','赵文博',NULL,NULL,1,'C1004','0','0','0',NULL,'GRP-1004','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2331,1304,'C1005','Company','鑫达新材料科技有限公司','91310117MA1K10005E','存续',1,'C1004','1','0','0',1005,'GRP-1004','{\"position\": \"被投资企业\", \"ratioLabel\": \"30%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2332,1304,'C1021','Company','中泰智能仓储有限公司','91310114MA1K10021W','存续',1,'C1004','1','0','1',1021,'GRP-1004','{\"position\": \"被投资企业\", \"ratioLabel\": \"35%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2333,1305,'C1005','Company','鑫达新材料科技有限公司','91310117MA1K10005E','存续',0,NULL,'1','0','0',1005,'GRP-1005','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2334,1305,'H1005_1','Human','孙立群',NULL,NULL,1,'C1005','0','1','0',NULL,'GRP-1005','{\"position\": \"自然人股东\", \"ratioLabel\": \"70%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2335,1305,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',1,'C1005','1','0','1',1004,'GRP-1005','{\"position\": \"股东企业\", \"ratioLabel\": \"30%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2336,1305,'H1004_1','Human','刘志强',NULL,NULL,2,'C1004','0','1','0',NULL,'GRP-1005','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"55%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2337,1305,'HPE1005_1010','Human','孙立群',NULL,NULL,1,'C1005','0','0','0',NULL,'GRP-1005','{\"position\": \"执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2338,1306,'C1006','Company','鸿远物流服务有限公司','91310105MA1K10006F','存续',0,NULL,'1','0','1',1006,'GRP-1006','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2339,1306,'H1006_1','Human','刘志强',NULL,NULL,1,'C1006','0','1','0',NULL,'GRP-1006','{\"position\": \"自然人股东\", \"ratioLabel\": \"100%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2340,1306,'HPE1006_1007','Human','刘志强',NULL,NULL,1,'C1006','0','0','0',NULL,'GRP-1006','{\"position\": \"执行董事兼总经理\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2341,1307,'C1007','Company','海通机电设备有限公司','91310113MA1K10007G','存续',0,NULL,'1','0','0',1007,'GRP-1007','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2342,1307,'H1007_1','Human','赵文博',NULL,NULL,1,'C1007','0','1','0',NULL,'GRP-1007','{\"position\": \"自然人股东\", \"ratioLabel\": \"60%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2343,1307,'C1002','Company','华晟建筑工程有限公司','91310115MA1K10002B','存续',1,'C1007','1','0','1',1002,'GRP-1007','{\"position\": \"股东企业\", \"ratioLabel\": \"40%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2344,1307,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',2,'C1002','1','0','1',1001,'GRP-1007','{\"position\": \"间接股东企业\", \"ratioLabel\": \"100%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2345,1307,'HPE1007_1009','Human','赵文博',NULL,NULL,1,'C1007','0','0','0',NULL,'GRP-1007','{\"position\": \"执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2346,1308,'C1008','Company','中启环保工程有限公司','91310118MA1K10008H','存续',0,NULL,'1','0','1',1008,'GRP-1008','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2347,1308,'H1008_1','Human','王建军',NULL,NULL,1,'C1008','0','1','0',NULL,'GRP-1008','{\"position\": \"自然人股东\", \"ratioLabel\": \"65%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2348,1308,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',1,'C1008','1','0','0',1003,'GRP-1008','{\"position\": \"股东企业\", \"ratioLabel\": \"35%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2349,1308,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',2,'C1003','1','0','1',1001,'GRP-1008','{\"position\": \"间接股东企业\", \"ratioLabel\": \"70%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2350,1308,'H1003_1','Human','周海峰',NULL,NULL,2,'C1003','0','1','0',NULL,'GRP-1008','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"30%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2351,1308,'HPE1008_1011','Human','王建军',NULL,NULL,1,'C1008','0','0','0',NULL,'GRP-1008','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2352,1309,'C1009','Company','联志电子科技有限公司','91310114MA1K10009J','存续',0,NULL,'1','0','0',1009,'GRP-1009','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2353,1309,'H1009_1','Human','郑晓岚',NULL,NULL,1,'C1009','0','1','0',NULL,'GRP-1009','{\"position\": \"自然人股东\", \"ratioLabel\": \"80%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2354,1309,'H1009_2','Human','陈国华',NULL,NULL,1,'C1009','0','1','0',NULL,'GRP-1009','{\"position\": \"自然人股东\", \"ratioLabel\": \"20%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2355,1309,'HPE1009_1013','Human','郑晓岚',NULL,NULL,1,'C1009','0','0','0',NULL,'GRP-1009','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2356,1309,'C1011','Company','瑞昇信息技术有限公司','91310109MA1K10011L','存续',1,'C1009','1','0','0',1011,'GRP-1009','{\"position\": \"被投资企业\", \"ratioLabel\": \"10%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2357,1310,'C1010','Company','广盛建设劳务有限公司','91310120MA1K10010K','存续',0,NULL,'1','0','0',1010,'GRP-1010','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2358,1310,'H1010_1','Human','王建军',NULL,NULL,1,'C1010','0','1','0',NULL,'GRP-1010','{\"position\": \"自然人股东\", \"ratioLabel\": \"100%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2359,1310,'HPE1010_1012','Human','王建军',NULL,NULL,1,'C1010','0','0','0',NULL,'GRP-1010','{\"position\": \"执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2360,1311,'C1011','Company','瑞昇信息技术有限公司','91310109MA1K10011L','存续',0,NULL,'1','0','0',1011,'GRP-1011','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2361,1311,'H1011_1','Human','郑晓岚',NULL,NULL,1,'C1011','0','1','0',NULL,'GRP-1011','{\"position\": \"自然人股东\", \"ratioLabel\": \"90%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2362,1311,'C1009','Company','联志电子科技有限公司','91310114MA1K10009J','存续',1,'C1011','1','0','0',1009,'GRP-1011','{\"position\": \"股东企业\", \"ratioLabel\": \"10%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2363,1311,'H1009_1','Human','郑晓岚',NULL,NULL,2,'C1009','0','1','0',NULL,'GRP-1011','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"80%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2364,1311,'HPE1011_1014','Human','郑晓岚',NULL,NULL,1,'C1011','0','0','0',NULL,'GRP-1011','{\"position\": \"执行董事兼总经理\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2365,1311,'C1015','Company','云通数据科技有限公司','91310104MA1K10015Q','存续',1,'C1011','1','0','0',1015,'GRP-1011','{\"position\": \"被投资企业\", \"ratioLabel\": \"30%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2366,1312,'C1012','Company','天成检测认证有限公司','91310110MA1K10012M','存续',0,NULL,'1','0','0',1012,'GRP-1012','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2367,1312,'H1012_1','Human','许文娟',NULL,NULL,1,'C1012','0','1','0',NULL,'GRP-1012','{\"position\": \"自然人股东\", \"ratioLabel\": \"100%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2368,1312,'HPE1012_1015','Human','许文娟',NULL,NULL,1,'C1012','0','0','0',NULL,'GRP-1012','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2369,1313,'C1013','Company','蓝湾海洋工程有限公司','91310115MA1K10013N','存续',0,NULL,'1','0','1',1013,'GRP-1013','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2370,1313,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1013','1','0','1',1001,'GRP-1013','{\"position\": \"股东企业\", \"ratioLabel\": \"55%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2371,1313,'H1013_2','Human','顾伟明',NULL,NULL,1,'C1013','0','1','0',NULL,'GRP-1013','{\"position\": \"自然人股东\", \"ratioLabel\": \"45%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2372,1313,'H1001_1','Human','陈国华',NULL,NULL,2,'C1001','0','1','0',NULL,'GRP-1013','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"60%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2373,1313,'HPE1013_1017','Human','顾伟明',NULL,NULL,1,'C1013','0','0','0',NULL,'GRP-1013','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2374,1313,'HPE1013_1018','Human','周海峰',NULL,NULL,1,'C1013','0','0','0',NULL,'GRP-1013','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2375,1314,'C1014','Company','凯越精密机械有限公司','91310116MA1K10014P','存续',0,NULL,'1','0','1',1014,'GRP-1014','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2376,1314,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',1,'C1014','1','0','0',1003,'GRP-1014','{\"position\": \"股东企业\", \"ratioLabel\": \"60%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2377,1314,'H1014_2','Human','何静',NULL,NULL,1,'C1014','0','1','0',NULL,'GRP-1014','{\"position\": \"自然人股东\", \"ratioLabel\": \"40%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2378,1314,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',2,'C1003','1','0','1',1001,'GRP-1014','{\"position\": \"间接股东企业\", \"ratioLabel\": \"70%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2379,1314,'H1003_1','Human','周海峰',NULL,NULL,2,'C1003','0','1','0',NULL,'GRP-1014','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"30%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2380,1314,'HPE1014_1019','Human','何静',NULL,NULL,1,'C1014','0','0','0',NULL,'GRP-1014','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2381,1314,'HPE1014_1020','Human','孙立群',NULL,NULL,1,'C1014','0','0','0',NULL,'GRP-1014','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2382,1315,'C1015','Company','云通数据科技有限公司','91310104MA1K10015Q','存续',0,NULL,'1','0','0',1015,'GRP-1015','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2383,1315,'H1015_1','Human','许文娟',NULL,NULL,1,'C1015','0','1','0',NULL,'GRP-1015','{\"position\": \"自然人股东\", \"ratioLabel\": \"70%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2384,1315,'C1011','Company','瑞昇信息技术有限公司','91310109MA1K10011L','存续',1,'C1015','1','0','0',1011,'GRP-1015','{\"position\": \"股东企业\", \"ratioLabel\": \"30%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2385,1315,'H1011_1','Human','郑晓岚',NULL,NULL,2,'C1011','0','1','0',NULL,'GRP-1015','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"90%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2386,1315,'C1009','Company','联志电子科技有限公司','91310114MA1K10009J','存续',2,'C1011','1','0','0',1009,'GRP-1015','{\"position\": \"间接股东企业\", \"ratioLabel\": \"10%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2387,1315,'HPE1015_1021','Human','许文娟',NULL,NULL,1,'C1015','0','0','0',NULL,'GRP-1015','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2388,1315,'HPE1015_1022','Human','郑晓岚',NULL,NULL,1,'C1015','0','0','0',NULL,'GRP-1015','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2389,1316,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',0,NULL,'1','0','1',1016,'GRP-1016','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2390,1316,'H1016_1','Human','沈文彬',NULL,NULL,1,'C1016','0','1','0',NULL,'GRP-1016','{\"position\": \"自然人股东\", \"ratioLabel\": \"55%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2391,1316,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1016','1','0','1',1001,'GRP-1016','{\"position\": \"股东企业\", \"ratioLabel\": \"25%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2392,1316,'H1016_3','Human','上海瑞泽投资中心(有限合伙)',NULL,NULL,1,'C1016','0','0','0',NULL,'GRP-1016','{\"position\": \"自然人股东\", \"ratioLabel\": \"20%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2393,1316,'H1001_1','Human','陈国华',NULL,NULL,2,'C1001','0','1','0',NULL,'GRP-1016','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"60%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2394,1316,'HPE1016_1023','Human','沈文彬',NULL,NULL,1,'C1016','0','0','0',NULL,'GRP-1016','{\"position\": \"法定代表人兼董事长\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2395,1316,'HPE1016_1024','Human','潘莉',NULL,NULL,1,'C1016','0','0','0',NULL,'GRP-1016','{\"position\": \"总经理\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2396,1316,'HPE1016_1025','Human','顾伟明',NULL,NULL,1,'C1016','0','0','0',NULL,'GRP-1016','{\"position\": \"独立董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2397,1316,'C1017','Company','瑞泽建筑工程有限公司','91310115MA1K10017S','存续',1,'C1016','1','0','0',1017,'GRP-1016','{\"position\": \"被投资企业\", \"ratioLabel\": \"100%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2398,1316,'C1018','Company','瑞泽新能源科技有限公司','91310117MA1K10018T','存续',1,'C1016','1','0','1',1018,'GRP-1016','{\"position\": \"被投资企业\", \"ratioLabel\": \"60%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2399,1316,'C1019','Company','上海泽信国际贸易有限公司','91310105MA1K10019U','存续',1,'C1016','1','0','0',1019,'GRP-1016','{\"position\": \"被投资企业\", \"ratioLabel\": \"30%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2400,1316,'C1021','Company','中泰智能仓储有限公司','91310114MA1K10021W','存续',1,'C1016','1','0','1',1021,'GRP-1016','{\"position\": \"被投资企业\", \"ratioLabel\": \"40%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2401,1317,'C1017','Company','瑞泽建筑工程有限公司','91310115MA1K10017S','存续',0,NULL,'1','0','0',1017,'GRP-1017','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2402,1317,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1017','1','0','1',1016,'GRP-1017','{\"position\": \"股东企业\", \"ratioLabel\": \"100%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2403,1317,'H1016_1','Human','沈文彬',NULL,NULL,2,'C1016','0','1','0',NULL,'GRP-1017','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"55%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2404,1317,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',2,'C1016','1','0','1',1001,'GRP-1017','{\"position\": \"间接股东企业\", \"ratioLabel\": \"25%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2405,1317,'HPE1017_1026','Human','潘莉',NULL,NULL,1,'C1017','0','0','0',NULL,'GRP-1017','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2406,1317,'HPE1017_1027','Human','沈文彬',NULL,NULL,1,'C1017','0','0','0',NULL,'GRP-1017','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2407,1317,'C1020','Company','瑞泽人力资源有限公司','91310110MA1K10020V','存续',1,'C1017','1','0','0',1020,'GRP-1017','{\"position\": \"被投资企业\", \"ratioLabel\": \"51%\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2408,1318,'C1018','Company','瑞泽新能源科技有限公司','91310117MA1K10018T','存续',0,NULL,'1','0','1',1018,'GRP-1018','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2409,1318,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1018','1','0','1',1016,'GRP-1018','{\"position\": \"股东企业\", \"ratioLabel\": \"60%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2410,1318,'H1018_2','Human','沈文彬',NULL,NULL,1,'C1018','0','1','0',NULL,'GRP-1018','{\"position\": \"自然人股东\", \"ratioLabel\": \"40%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2411,1318,'H1016_1','Human','沈文彬',NULL,NULL,2,'C1016','0','1','0',NULL,'GRP-1018','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"55%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2412,1318,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',2,'C1016','1','0','1',1001,'GRP-1018','{\"position\": \"间接股东企业\", \"ratioLabel\": \"25%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2413,1318,'HPE1018_1028','Human','沈文彬',NULL,NULL,1,'C1018','0','0','0',NULL,'GRP-1018','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2414,1318,'HPE1018_1029','Human','罗伯涛',NULL,NULL,1,'C1018','0','0','0',NULL,'GRP-1018','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2415,1319,'C1019','Company','上海泽信国际贸易有限公司','91310105MA1K10019U','存续',0,NULL,'1','0','0',1019,'GRP-1019','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2416,1319,'H1019_1','Human','沈文彬',NULL,NULL,1,'C1019','0','1','0',NULL,'GRP-1019','{\"position\": \"自然人股东\", \"ratioLabel\": \"70%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2417,1319,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1019','1','0','1',1016,'GRP-1019','{\"position\": \"股东企业\", \"ratioLabel\": \"30%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2418,1319,'H1016_1','Human','沈文彬',NULL,NULL,2,'C1016','0','1','0',NULL,'GRP-1019','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"55%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2419,1319,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',2,'C1016','1','0','1',1001,'GRP-1019','{\"position\": \"间接股东企业\", \"ratioLabel\": \"25%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2420,1319,'HPE1019_1030','Human','沈文彬',NULL,NULL,1,'C1019','0','0','0',NULL,'GRP-1019','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2421,1320,'C1020','Company','瑞泽人力资源有限公司','91310110MA1K10020V','存续',0,NULL,'1','0','0',1020,'GRP-1020','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2422,1320,'C1017','Company','瑞泽建筑工程有限公司','91310115MA1K10017S','存续',1,'C1020','1','0','0',1017,'GRP-1020','{\"position\": \"股东企业\", \"ratioLabel\": \"51%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2423,1320,'H1020_2','Human','潘莉',NULL,NULL,1,'C1020','0','1','0',NULL,'GRP-1020','{\"position\": \"自然人股东\", \"ratioLabel\": \"49%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2424,1320,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',2,'C1017','1','0','1',1016,'GRP-1020','{\"position\": \"间接股东企业\", \"ratioLabel\": \"100%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2425,1320,'HPE1020_1031','Human','潘莉',NULL,NULL,1,'C1020','0','0','0',NULL,'GRP-1020','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2426,1320,'HPE1020_1032','Human','徐海燕',NULL,NULL,1,'C1020','0','0','0',NULL,'GRP-1020','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2427,1321,'C1021','Company','中泰智能仓储有限公司','91310114MA1K10021W','存续',0,NULL,'1','0','1',1021,'GRP-1021','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2428,1321,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1021','1','0','1',1016,'GRP-1021','{\"position\": \"股东企业\", \"ratioLabel\": \"40%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2429,1321,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',1,'C1021','1','0','1',1004,'GRP-1021','{\"position\": \"股东企业\", \"ratioLabel\": \"35%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2430,1321,'H1021_3','Human','沈文彬',NULL,NULL,1,'C1021','0','1','0',NULL,'GRP-1021','{\"position\": \"自然人股东\", \"ratioLabel\": \"25%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2431,1321,'H1016_1','Human','沈文彬',NULL,NULL,2,'C1016','0','1','0',NULL,'GRP-1021','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"55%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2432,1321,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',2,'C1016','1','0','1',1001,'GRP-1021','{\"position\": \"间接股东企业\", \"ratioLabel\": \"25%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2433,1321,'H1004_1','Human','刘志强',NULL,NULL,2,'C1004','0','1','0',NULL,'GRP-1021','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"55%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2434,1321,'HPE1021_1033','Human','罗伯涛',NULL,NULL,1,'C1021','0','0','0',NULL,'GRP-1021','{\"position\": \"法定代表人兼经理\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2435,1321,'HPE1021_1034','Human','沈文彬',NULL,NULL,1,'C1021','0','0','0',NULL,'GRP-1021','{\"position\": \"副董事长\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2436,1321,'HPE1021_1035','Human','刘志强',NULL,NULL,1,'C1021','0','0','0',NULL,'GRP-1021','{\"position\": \"董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2437,1322,'C1022','Company','华辰智造科技有限公司','91310117MA1K10022X','存续',0,NULL,'1','0','1',1022,'GRP-1022','{\"position\": \"中心企业\", \"ratioLabel\": \"起点\"}','{}',1160,NULL,'2026-11-18 15:00:00'),(2438,1322,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1022','1','0','1',1001,'GRP-1022','{\"position\": \"股东企业\", \"ratioLabel\": \"51%\"}','{}',821,NULL,'2026-11-18 15:00:00'),(2439,1322,'H1022_2','Human','陈建国',NULL,NULL,1,'C1022','0','1','0',NULL,'GRP-1022','{\"position\": \"自然人股东\", \"ratioLabel\": \"34%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2440,1322,'H1022_3','Human','林薇',NULL,NULL,1,'C1022','0','1','0',NULL,'GRP-1022','{\"position\": \"自然人股东\", \"ratioLabel\": \"15%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2441,1322,'H1001_1','Human','陈国华',NULL,NULL,2,'C1001','0','1','0',NULL,'GRP-1022','{\"position\": \"间接自然人股东\", \"ratioLabel\": \"60%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2442,1322,'HPE1022_1036','Human','陈建国',NULL,NULL,1,'C1022','0','0','0',NULL,'GRP-1022','{\"position\": \"法定代表人兼执行董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2443,1322,'HPE1022_1037','Human','陈国华',NULL,NULL,1,'C1022','0','0','0',NULL,'GRP-1022','{\"position\": \"董事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2444,1322,'HPE1022_1038','Human','林薇',NULL,NULL,1,'C1022','0','0','0',NULL,'GRP-1022','{\"position\": \"监事\", \"ratioLabel\": \"任职\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2445,1401,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',0,NULL,'1','0','1',1001,'GRP-1001','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2446,1401,'C1013','Company','蓝湾海洋工程有限公司','91310115MA1K10013N','存续',1,'C1001','1','0','1',1013,'GRP-1001','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2447,1401,'C1020','Company','瑞泽人力资源有限公司','91310110MA1K10020V','存续',1,'C1001','1','0','0',1020,'GRP-1001','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2448,1401,'C1022','Company','华辰智造科技有限公司','91310117MA1K10022X','存续',1,'C1001','1','0','1',1022,'GRP-1001','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2449,1401,'C1002','Company','华晟建筑工程有限公司','91310115MA1K10002B','存续',1,'C1001','1','0','1',1002,'GRP-1001','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2450,1401,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',1,'C1001','1','0','0',1003,'GRP-1001','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2451,1401,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1001','1','0','1',1016,'GRP-1001','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2452,1401,'HBS1001_1','Human','陈国华',NULL,NULL,1,'C1001','0','0','0',NULL,'GRP-1001','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"60%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2453,1401,'HBS1001_3','Human','李明远',NULL,NULL,1,'C1001','0','0','0',NULL,'GRP-1001','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"15%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2454,1401,'HBS-PE1001_1001','Human','陈国华',NULL,NULL,1,'C1001','0','1','0',NULL,'GRP-1001','{\"position\": \"执行董事兼总经理\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2455,1401,'HBS-PE1001_1016','Human','徐海燕',NULL,NULL,1,'C1001','0','0','0',NULL,'GRP-1001','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2456,1402,'C1002','Company','华晟建筑工程有限公司','91310115MA1K10002B','存续',0,NULL,'1','0','1',1002,'GRP-1002','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2457,1402,'C1013','Company','蓝湾海洋工程有限公司','91310115MA1K10013N','存续',1,'C1002','1','0','1',1013,'GRP-1002','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2458,1402,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',1,'C1002','1','0','0',1003,'GRP-1002','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2459,1402,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1002','1','0','1',1001,'GRP-1002','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2460,1402,'C1007','Company','海通机电设备有限公司','91310113MA1K10007G','存续',1,'C1002','1','0','0',1007,'GRP-1002','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2461,1402,'HBS-PE1002_1002','Human','李明远',NULL,NULL,1,'C1002','0','0','0',NULL,'GRP-1002','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2462,1402,'HBS-PE1002_1003','Human','周海峰',NULL,NULL,1,'C1002','0','0','0',NULL,'GRP-1002','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2463,1403,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',0,NULL,'1','0','0',1003,'GRP-1003','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2464,1403,'C1014','Company','凯越精密机械有限公司','91310116MA1K10014P','存续',1,'C1003','1','0','1',1014,'GRP-1003','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2465,1403,'C1013','Company','蓝湾海洋工程有限公司','91310115MA1K10013N','存续',1,'C1003','1','0','1',1013,'GRP-1003','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2466,1403,'C1002','Company','华晟建筑工程有限公司','91310115MA1K10002B','存续',1,'C1003','1','0','1',1002,'GRP-1003','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2467,1403,'C1005','Company','鑫达新材料科技有限公司','91310117MA1K10005E','存续',1,'C1003','1','0','0',1005,'GRP-1003','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2468,1403,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1003','1','0','1',1001,'GRP-1003','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2469,1403,'C1008','Company','中启环保工程有限公司','91310118MA1K10008H','存续',1,'C1003','1','0','1',1008,'GRP-1003','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2470,1403,'HBS1003_2','Human','周海峰',NULL,NULL,1,'C1003','0','0','0',NULL,'GRP-1003','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"30%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2471,1403,'HBS-PE1003_1004','Human','周海峰',NULL,NULL,1,'C1003','0','1','0',NULL,'GRP-1003','{\"position\": \"执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2472,1403,'HBS-PE1003_1005','Human','孙立群',NULL,NULL,1,'C1003','0','0','0',NULL,'GRP-1003','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2473,1404,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',0,NULL,'1','0','1',1004,'GRP-1004','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2474,1404,'C1006','Company','鸿远物流服务有限公司','91310105MA1K10006F','存续',1,'C1004','1','0','1',1006,'GRP-1004','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2475,1404,'C1007','Company','海通机电设备有限公司','91310113MA1K10007G','存续',1,'C1004','1','0','0',1007,'GRP-1004','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2476,1404,'C1021','Company','中泰智能仓储有限公司','91310114MA1K10021W','存续',1,'C1004','1','0','1',1021,'GRP-1004','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2477,1404,'C1005','Company','鑫达新材料科技有限公司','91310117MA1K10005E','存续',1,'C1004','1','0','0',1005,'GRP-1004','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2478,1404,'HBS1004_1','Human','刘志强',NULL,NULL,1,'C1004','0','0','0',NULL,'GRP-1004','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"55%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2479,1404,'HBS1004_2','Human','赵文博',NULL,NULL,1,'C1004','0','0','0',NULL,'GRP-1004','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"45%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2480,1404,'HBS-PE1004_1006','Human','刘志强',NULL,NULL,1,'C1004','0','1','0',NULL,'GRP-1004','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2481,1404,'HBS-PE1004_1008','Human','赵文博',NULL,NULL,1,'C1004','0','0','0',NULL,'GRP-1004','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2482,1405,'C1005','Company','鑫达新材料科技有限公司','91310117MA1K10005E','存续',0,NULL,'1','0','0',1005,'GRP-1005','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2483,1405,'C1014','Company','凯越精密机械有限公司','91310116MA1K10014P','存续',1,'C1005','1','0','1',1014,'GRP-1005','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2484,1405,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',1,'C1005','1','0','0',1003,'GRP-1005','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2485,1405,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',1,'C1005','1','0','1',1004,'GRP-1005','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2486,1405,'HBS1005_1','Human','孙立群',NULL,NULL,1,'C1005','0','0','0',NULL,'GRP-1005','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"70%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2487,1405,'HBS-PE1005_1010','Human','孙立群',NULL,NULL,1,'C1005','0','1','0',NULL,'GRP-1005','{\"position\": \"执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2488,1406,'C1006','Company','鸿远物流服务有限公司','91310105MA1K10006F','存续',0,NULL,'1','0','1',1006,'GRP-1006','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2489,1406,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',1,'C1006','1','0','1',1004,'GRP-1006','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2490,1406,'C1021','Company','中泰智能仓储有限公司','91310114MA1K10021W','存续',1,'C1006','1','0','1',1021,'GRP-1006','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2491,1406,'HBS1006_1','Human','刘志强',NULL,NULL,1,'C1006','0','0','0',NULL,'GRP-1006','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"100%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2492,1406,'HBS-PE1006_1007','Human','刘志强',NULL,NULL,1,'C1006','0','1','0',NULL,'GRP-1006','{\"position\": \"执行董事兼总经理\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2493,1407,'C1007','Company','海通机电设备有限公司','91310113MA1K10007G','存续',0,NULL,'1','0','0',1007,'GRP-1007','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2494,1407,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',1,'C1007','1','0','1',1004,'GRP-1007','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2495,1407,'C1002','Company','华晟建筑工程有限公司','91310115MA1K10002B','存续',1,'C1007','1','0','1',1002,'GRP-1007','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2496,1407,'HBS1007_1','Human','赵文博',NULL,NULL,1,'C1007','0','0','0',NULL,'GRP-1007','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"60%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2497,1407,'HBS-PE1007_1009','Human','赵文博',NULL,NULL,1,'C1007','0','1','0',NULL,'GRP-1007','{\"position\": \"执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2498,1408,'C1008','Company','中启环保工程有限公司','91310118MA1K10008H','存续',0,NULL,'1','0','1',1008,'GRP-1008','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2499,1408,'C1010','Company','广盛建设劳务有限公司','91310120MA1K10010K','存续',1,'C1008','1','0','0',1010,'GRP-1008','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2500,1408,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',1,'C1008','1','0','0',1003,'GRP-1008','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2501,1408,'HBS1008_1','Human','王建军',NULL,NULL,1,'C1008','0','0','0',NULL,'GRP-1008','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"65%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2502,1408,'HBS-PE1008_1011','Human','王建军',NULL,NULL,1,'C1008','0','1','0',NULL,'GRP-1008','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2503,1409,'C1009','Company','联志电子科技有限公司','91310114MA1K10009J','存续',0,NULL,'1','0','0',1009,'GRP-1009','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2504,1409,'C1011','Company','瑞昇信息技术有限公司','91310109MA1K10011L','存续',1,'C1009','1','0','0',1011,'GRP-1009','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2505,1409,'C1015','Company','云通数据科技有限公司','91310104MA1K10015Q','存续',1,'C1009','1','0','0',1015,'GRP-1009','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2506,1409,'HBS1009_1','Human','郑晓岚',NULL,NULL,1,'C1009','0','0','0',NULL,'GRP-1009','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"80%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2507,1409,'HBS1009_2','Human','陈国华',NULL,NULL,1,'C1009','0','0','0',NULL,'GRP-1009','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"20%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2508,1409,'HBS-PE1009_1013','Human','郑晓岚',NULL,NULL,1,'C1009','0','1','0',NULL,'GRP-1009','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2509,1410,'C1010','Company','广盛建设劳务有限公司','91310120MA1K10010K','存续',0,NULL,'1','0','0',1010,'GRP-1010','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2510,1410,'C1008','Company','中启环保工程有限公司','91310118MA1K10008H','存续',1,'C1010','1','0','1',1008,'GRP-1010','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2511,1410,'HBS1010_1','Human','王建军',NULL,NULL,1,'C1010','0','0','0',NULL,'GRP-1010','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"100%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2512,1410,'HBS-PE1010_1012','Human','王建军',NULL,NULL,1,'C1010','0','1','0',NULL,'GRP-1010','{\"position\": \"执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2513,1411,'C1011','Company','瑞昇信息技术有限公司','91310109MA1K10011L','存续',0,NULL,'1','0','0',1011,'GRP-1011','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2514,1411,'C1015','Company','云通数据科技有限公司','91310104MA1K10015Q','存续',1,'C1011','1','0','0',1015,'GRP-1011','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2515,1411,'C1009','Company','联志电子科技有限公司','91310114MA1K10009J','存续',1,'C1011','1','0','0',1009,'GRP-1011','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2516,1411,'HBS1011_1','Human','郑晓岚',NULL,NULL,1,'C1011','0','0','0',NULL,'GRP-1011','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"90%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2517,1411,'HBS-PE1011_1014','Human','郑晓岚',NULL,NULL,1,'C1011','0','1','0',NULL,'GRP-1011','{\"position\": \"执行董事兼总经理\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2518,1412,'C1012','Company','天成检测认证有限公司','91310110MA1K10012M','存续',0,NULL,'1','0','0',1012,'GRP-1012','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2519,1412,'C1015','Company','云通数据科技有限公司','91310104MA1K10015Q','存续',1,'C1012','1','0','0',1015,'GRP-1012','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2520,1412,'HBS1012_1','Human','许文娟',NULL,NULL,1,'C1012','0','0','0',NULL,'GRP-1012','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"100%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2521,1412,'HBS-PE1012_1015','Human','许文娟',NULL,NULL,1,'C1012','0','1','0',NULL,'GRP-1012','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2522,1413,'C1013','Company','蓝湾海洋工程有限公司','91310115MA1K10013N','存续',0,NULL,'1','0','1',1013,'GRP-1013','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2523,1413,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1013','1','0','1',1001,'GRP-1013','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2524,1413,'C1002','Company','华晟建筑工程有限公司','91310115MA1K10002B','存续',1,'C1013','1','0','1',1002,'GRP-1013','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2525,1413,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',1,'C1013','1','0','0',1003,'GRP-1013','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2526,1413,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1013','1','0','1',1016,'GRP-1013','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2527,1413,'HBS1013_2','Human','顾伟明',NULL,NULL,1,'C1013','0','0','0',NULL,'GRP-1013','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"45%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2528,1413,'HBS-PE1013_1017','Human','顾伟明',NULL,NULL,1,'C1013','0','1','0',NULL,'GRP-1013','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2529,1413,'HBS-PE1013_1018','Human','周海峰',NULL,NULL,1,'C1013','0','0','0',NULL,'GRP-1013','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2530,1414,'C1014','Company','凯越精密机械有限公司','91310116MA1K10014P','存续',0,NULL,'1','0','1',1014,'GRP-1014','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2531,1414,'C1003','Company','华晟智能装备制造有限公司','91310116MA1K10003C','存续',1,'C1014','1','0','0',1003,'GRP-1014','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2532,1414,'C1005','Company','鑫达新材料科技有限公司','91310117MA1K10005E','存续',1,'C1014','1','0','0',1005,'GRP-1014','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2533,1414,'HBS1014_2','Human','何静',NULL,NULL,1,'C1014','0','0','0',NULL,'GRP-1014','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"40%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2534,1414,'HBS-PE1014_1019','Human','何静',NULL,NULL,1,'C1014','0','1','0',NULL,'GRP-1014','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2535,1414,'HBS-PE1014_1020','Human','孙立群',NULL,NULL,1,'C1014','0','0','0',NULL,'GRP-1014','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2536,1415,'C1015','Company','云通数据科技有限公司','91310104MA1K10015Q','存续',0,NULL,'1','0','0',1015,'GRP-1015','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2537,1415,'C1011','Company','瑞昇信息技术有限公司','91310109MA1K10011L','存续',1,'C1015','1','0','0',1011,'GRP-1015','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2538,1415,'C1009','Company','联志电子科技有限公司','91310114MA1K10009J','存续',1,'C1015','1','0','0',1009,'GRP-1015','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2539,1415,'C1012','Company','天成检测认证有限公司','91310110MA1K10012M','存续',1,'C1015','1','0','0',1012,'GRP-1015','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2540,1415,'HBS1015_1','Human','许文娟',NULL,NULL,1,'C1015','0','0','0',NULL,'GRP-1015','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"70%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2541,1415,'HBS-PE1015_1021','Human','许文娟',NULL,NULL,1,'C1015','0','1','0',NULL,'GRP-1015','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2542,1415,'HBS-PE1015_1022','Human','郑晓岚',NULL,NULL,1,'C1015','0','0','0',NULL,'GRP-1015','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2543,1416,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',0,NULL,'1','0','1',1016,'GRP-1016','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2544,1416,'C1019','Company','上海泽信国际贸易有限公司','91310105MA1K10019U','存续',1,'C1016','1','0','0',1019,'GRP-1016','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2545,1416,'C1020','Company','瑞泽人力资源有限公司','91310110MA1K10020V','存续',1,'C1016','1','0','0',1020,'GRP-1016','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2546,1416,'C1013','Company','蓝湾海洋工程有限公司','91310115MA1K10013N','存续',1,'C1016','1','0','1',1013,'GRP-1016','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2547,1416,'C1017','Company','瑞泽建筑工程有限公司','91310115MA1K10017S','存续',1,'C1016','1','0','0',1017,'GRP-1016','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2548,1416,'C1018','Company','瑞泽新能源科技有限公司','91310117MA1K10018T','存续',1,'C1016','1','0','1',1018,'GRP-1016','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2549,1416,'C1021','Company','中泰智能仓储有限公司','91310114MA1K10021W','存续',1,'C1016','1','0','1',1021,'GRP-1016','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2550,1416,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1016','1','0','1',1001,'GRP-1016','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2551,1416,'HBS1016_1','Human','沈文彬',NULL,NULL,1,'C1016','0','0','0',NULL,'GRP-1016','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"55%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2552,1416,'HBS-PE1016_1023','Human','沈文彬',NULL,NULL,1,'C1016','0','1','0',NULL,'GRP-1016','{\"position\": \"法定代表人兼董事长\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2553,1416,'HBS-PE1016_1024','Human','潘莉',NULL,NULL,1,'C1016','0','0','0',NULL,'GRP-1016','{\"position\": \"总经理\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2554,1416,'HBS-PE1016_1025','Human','顾伟明',NULL,NULL,1,'C1016','0','0','0',NULL,'GRP-1016','{\"position\": \"独立董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2555,1417,'C1017','Company','瑞泽建筑工程有限公司','91310115MA1K10017S','存续',0,NULL,'1','0','0',1017,'GRP-1017','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2556,1417,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1017','1','0','1',1016,'GRP-1017','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2557,1417,'C1018','Company','瑞泽新能源科技有限公司','91310117MA1K10018T','存续',1,'C1017','1','0','1',1018,'GRP-1017','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2558,1417,'C1019','Company','上海泽信国际贸易有限公司','91310105MA1K10019U','存续',1,'C1017','1','0','0',1019,'GRP-1017','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2559,1417,'C1020','Company','瑞泽人力资源有限公司','91310110MA1K10020V','存续',1,'C1017','1','0','0',1020,'GRP-1017','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2560,1417,'C1021','Company','中泰智能仓储有限公司','91310114MA1K10021W','存续',1,'C1017','1','0','1',1021,'GRP-1017','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2561,1417,'HBS-PE1017_1026','Human','潘莉',NULL,NULL,1,'C1017','0','0','0',NULL,'GRP-1017','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2562,1417,'HBS-PE1017_1027','Human','沈文彬',NULL,NULL,1,'C1017','0','0','0',NULL,'GRP-1017','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2563,1418,'C1018','Company','瑞泽新能源科技有限公司','91310117MA1K10018T','存续',0,NULL,'1','0','1',1018,'GRP-1018','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2564,1418,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1018','1','0','1',1016,'GRP-1018','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2565,1418,'C1017','Company','瑞泽建筑工程有限公司','91310115MA1K10017S','存续',1,'C1018','1','0','0',1017,'GRP-1018','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2566,1418,'C1019','Company','上海泽信国际贸易有限公司','91310105MA1K10019U','存续',1,'C1018','1','0','0',1019,'GRP-1018','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2567,1418,'C1021','Company','中泰智能仓储有限公司','91310114MA1K10021W','存续',1,'C1018','1','0','1',1021,'GRP-1018','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2568,1418,'HBS1018_2','Human','沈文彬',NULL,NULL,1,'C1018','0','0','0',NULL,'GRP-1018','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"40%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2569,1418,'HBS-PE1018_1028','Human','沈文彬',NULL,NULL,1,'C1018','0','1','0',NULL,'GRP-1018','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2570,1418,'HBS-PE1018_1029','Human','罗伯涛',NULL,NULL,1,'C1018','0','0','0',NULL,'GRP-1018','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2571,1419,'C1019','Company','上海泽信国际贸易有限公司','91310105MA1K10019U','存续',0,NULL,'1','0','0',1019,'GRP-1019','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2572,1419,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1019','1','0','1',1016,'GRP-1019','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2573,1419,'C1020','Company','瑞泽人力资源有限公司','91310110MA1K10020V','存续',1,'C1019','1','0','0',1020,'GRP-1019','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2574,1419,'C1017','Company','瑞泽建筑工程有限公司','91310115MA1K10017S','存续',1,'C1019','1','0','0',1017,'GRP-1019','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2575,1419,'C1018','Company','瑞泽新能源科技有限公司','91310117MA1K10018T','存续',1,'C1019','1','0','1',1018,'GRP-1019','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2576,1419,'C1021','Company','中泰智能仓储有限公司','91310114MA1K10021W','存续',1,'C1019','1','0','1',1021,'GRP-1019','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2577,1419,'HBS1019_1','Human','沈文彬',NULL,NULL,1,'C1019','0','0','0',NULL,'GRP-1019','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"70%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2578,1419,'HBS-PE1019_1030','Human','沈文彬',NULL,NULL,1,'C1019','0','1','0',NULL,'GRP-1019','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2579,1420,'C1020','Company','瑞泽人力资源有限公司','91310110MA1K10020V','存续',0,NULL,'1','0','0',1020,'GRP-1020','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2580,1420,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1020','1','0','1',1016,'GRP-1020','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2581,1420,'C1019','Company','上海泽信国际贸易有限公司','91310105MA1K10019U','存续',1,'C1020','1','0','0',1019,'GRP-1020','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2582,1420,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1020','1','0','1',1001,'GRP-1020','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2583,1420,'C1017','Company','瑞泽建筑工程有限公司','91310115MA1K10017S','存续',1,'C1020','1','0','0',1017,'GRP-1020','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2584,1420,'HBS1020_2','Human','潘莉',NULL,NULL,1,'C1020','0','0','0',NULL,'GRP-1020','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"49%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2585,1420,'HBS-PE1020_1031','Human','潘莉',NULL,NULL,1,'C1020','0','1','0',NULL,'GRP-1020','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2586,1420,'HBS-PE1020_1032','Human','徐海燕',NULL,NULL,1,'C1020','0','0','0',NULL,'GRP-1020','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2587,1421,'C1021','Company','中泰智能仓储有限公司','91310114MA1K10021W','存续',0,NULL,'1','0','1',1021,'GRP-1021','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2588,1421,'C1004','Company','恒信供应链管理有限公司','91310104MA1K10004D','存续',1,'C1021','1','0','1',1004,'GRP-1021','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2589,1421,'C1006','Company','鸿远物流服务有限公司','91310105MA1K10006F','存续',1,'C1021','1','0','1',1006,'GRP-1021','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2590,1421,'C1016','Company','瑞泽实业集团有限公司','91310115MA1K10016R','存续',1,'C1021','1','0','1',1016,'GRP-1021','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2591,1421,'C1017','Company','瑞泽建筑工程有限公司','91310115MA1K10017S','存续',1,'C1021','1','0','0',1017,'GRP-1021','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2592,1421,'C1018','Company','瑞泽新能源科技有限公司','91310117MA1K10018T','存续',1,'C1021','1','0','1',1018,'GRP-1021','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2593,1421,'C1019','Company','上海泽信国际贸易有限公司','91310105MA1K10019U','存续',1,'C1021','1','0','0',1019,'GRP-1021','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2594,1421,'HBS1021_3','Human','沈文彬',NULL,NULL,1,'C1021','0','0','0',NULL,'GRP-1021','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"25%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2595,1421,'HBS-PE1021_1033','Human','罗伯涛',NULL,NULL,1,'C1021','0','0','0',NULL,'GRP-1021','{\"position\": \"法定代表人兼经理\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2596,1421,'HBS-PE1021_1034','Human','沈文彬',NULL,NULL,1,'C1021','0','1','0',NULL,'GRP-1021','{\"position\": \"副董事长\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2597,1421,'HBS-PE1021_1035','Human','刘志强',NULL,NULL,1,'C1021','0','0','0',NULL,'GRP-1021','{\"position\": \"董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2598,1422,'C1022','Company','华辰智造科技有限公司','91310117MA1K10022X','存续',0,NULL,'1','0','1',1022,'GRP-1022','{\"position\": \"中心企业\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2599,1422,'C1001','Company','华晟控股集团有限公司','91310115MA1K10001A','存续',1,'C1022','1','0','1',1001,'GRP-1022','{\"position\": \"关联方\"}','{}',1161,NULL,'2026-11-18 15:00:00'),(2600,1422,'HBS1022_2','Human','陈建国',NULL,NULL,1,'C1022','0','0','0',NULL,'GRP-1022','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"34%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2601,1422,'HBS1022_3','Human','林薇',NULL,NULL,1,'C1022','0','0','0',NULL,'GRP-1022','{\"position\": \"自然人股东/疑似实际控制人\", \"ratioLabel\": \"15%\"}','{}',1123,NULL,'2026-11-18 15:00:00'),(2602,1422,'HBS-PE1022_1036','Human','陈建国',NULL,NULL,1,'C1022','0','1','0',NULL,'GRP-1022','{\"position\": \"法定代表人兼执行董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2603,1422,'HBS-PE1022_1037','Human','陈国华',NULL,NULL,1,'C1022','0','0','0',NULL,'GRP-1022','{\"position\": \"董事\"}','{}',1158,NULL,'2026-11-18 15:00:00'),(2604,1422,'HBS-PE1022_1038','Human','林薇',NULL,NULL,1,'C1022','0','0','0',NULL,'GRP-1022','{\"position\": \"监事\"}','{}',1158,NULL,'2026-11-18 15:00:00');
/*!40000 ALTER TABLE `srm_graph_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_import_batch`
--

DROP TABLE IF EXISTS `srm_import_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_import_batch` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `batch_no` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '导入批次号（系统生成，页面14 原文未列，按留痕需要补）',
  `source_type` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'paste' COMMENT 'seed 初始内置库 / paste 文本粘贴 / file 文件上传',
  `separator` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'csv' COMMENT 'csv 逗号分隔 / tsv 制表符分隔；MySQL 8.0 保留字，SQL 与 MyBatis 里引用本列必须带反引号',
  `has_header` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '首行处理：1 含字段名 / 0 不含字段名',
  `total_rows` int NOT NULL DEFAULT '0' COMMENT '预览记录（页面14 指标卡）',
  `ok_rows` int NOT NULL DEFAULT '0' COMMENT '可导入',
  `dup_rows` int NOT NULL DEFAULT '0' COMMENT '重复记录',
  `invalid_rows` int NOT NULL DEFAULT '0' COMMENT '无效记录',
  `import_status` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '待确认' COMMENT '待确认 / 已导入 / 部分导入 / 已取消',
  `dup_rule` varchar(40) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'credit_code,name' COMMENT '重复识别口径：信用代码 / 企业名称（含同批次内重复）',
  `file_path` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '原始文件存档路径（导入留痕，可重放）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `confirm_time` datetime DEFAULT NULL,
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_batch_no` (`batch_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='供应商批量导入批次（页面14）：seed 为初始内置合作供应商库，其余为用友导出等存量导入；均不与用友实时对接';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_import_batch`
--

LOCK TABLES `srm_import_batch` WRITE;
/*!40000 ALTER TABLE `srm_import_batch` DISABLE KEYS */;
INSERT INTO `srm_import_batch` VALUES (1001,'IMP-20261110-001','file','csv','1',12,12,0,0,'已导入','credit_code,name','/data/srm/import/mock_supplier_20261110.csv','admin','2026-11-10 09:00:00','2026-11-10 09:15:00','演示批量导入：12 家供应商全部成功'),(1002,'IMP-20261118-002','file','csv','1',5,3,1,1,'部分导入','credit_code,name','/data/srm/import/mock_supplier_20261118.xlsx','admin','2026-11-18 14:00:00','2026-11-18 14:20:00','演示批量导入：1 条重复、1 条证件号非法');
/*!40000 ALTER TABLE `srm_import_batch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_import_row`
--

DROP TABLE IF EXISTS `srm_import_row`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_import_row` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `batch_id` bigint unsigned NOT NULL,
  `row_no` int NOT NULL COMMENT '行号（页面14 表 14-1 列）',
  `supplier_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业名称',
  `credit_code` varchar(18) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '统一社会信用代码',
  `legal_person` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法人',
  `monitor_flag` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '重点监控：是 / 否',
  `check_result` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '校验结果：可导入 / 重复，跳过 / 缺少名称',
  `dup_of_supplier_id` bigint unsigned DEFAULT NULL COMMENT '重复到的既有主档',
  `supplier_id` bigint unsigned DEFAULT NULL COMMENT '导入后指向主档',
  `err_msg` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_batch_row` (`batch_id`,`row_no`),
  KEY `idx_result` (`batch_id`,`check_result`)
) ENGINE=InnoDB AUTO_INCREMENT=1007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='导入行明细与三类校验结果（页面14）。模板列名：企业名称,统一社会信用代码,法定代表人,联系电话,电子邮箱,注册地址,重点监控';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_import_row`
--

LOCK TABLES `srm_import_row` WRITE;
/*!40000 ALTER TABLE `srm_import_row` DISABLE KEYS */;
INSERT INTO `srm_import_row` VALUES (1001,1001,2,'华晟控股集团有限公司','91310115MA1K10001A','陈国华','1','有效',NULL,1001,NULL,'2026-11-11 14:05:00'),(1002,1001,3,'华晟建筑工程有限公司','91310115MA1K10002B','李明远','1','有效',NULL,1002,NULL,'2026-11-11 14:05:00'),(1003,1001,4,'华晟智能装备制造有限公司','91310116MA1K10003C','周海峰','0','有效',NULL,1003,NULL,'2026-11-11 14:05:00'),(1004,1002,2,'恒信供应链管理有限公司','91310104MA1K10004D','刘志强','1','重复',1004,NULL,'证件号已存在','2026-11-12 14:05:00'),(1005,1002,3,'鑫达新材料科技有限公司','91310117MA1K10005E','孙立群','0','有效',NULL,1005,NULL,'2026-11-12 14:05:00'),(1006,1002,4,'未知企业（测试）','BAD-CREDIT-CODE',NULL,'0','无效',NULL,NULL,'统一社会信用代码格式不合法','2026-11-12 14:05:00');
/*!40000 ALTER TABLE `srm_import_row` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_monitor_policy`
--

DROP TABLE IF EXISTS `srm_monitor_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_monitor_policy` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `policy_code` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略码：biz_info / shareholder / legal_person / person / abnormal_punish / judicial / invest（7 项逐字取自原型 monitorTopics）',
  `policy_name` varchar(60) COLLATE utf8mb4_general_ci NOT NULL COMMENT '监控维度名：工商基本信息变更/股东及股权变更/法定代表人变更/主要人员变更/经营异常及行政处罚/司法风险/对外投资变更',
  `support_api` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支撑接口：822/458/821/819/848/1002 等；本期不接入的维度写 待确认 且默认关闭',
  `frequency` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'manual' COMMENT '刷新频率：daily 每日一次 / weekly 每周一次 / manual 人工触发 / push 实时推送（需服务支持），默认人工触发',
  `cron_expr` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '定时表达式（同步到 sys_job，由若依 Quartz 调度，不引新框架）',
  `enabled` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '启用：本期整体后置，默认 0；纪要口径：不采购付费监控接口，改按 3天/5天/1个月周期调基础接口',
  `scan_scope` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'monitor_flag' COMMENT '范围：monitor_flag 仅已勾选重点监控 / all 全库（禁止，字段保留仅为可观测）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_policy_code` (`policy_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='监控策略（页面15）：决定页面8 的轮询范围与维度，不决定预算上限与单价（后者在页面16）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_monitor_policy`
--

LOCK TABLES `srm_monitor_policy` WRITE;
/*!40000 ALTER TABLE `srm_monitor_policy` DISABLE KEYS */;
/*!40000 ALTER TABLE `srm_monitor_policy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_project`
--

DROP TABLE IF EXISTS `srm_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_project` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `project_no` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目单据号（页面7 列表列、审计留痕必填）',
  `project_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目名称',
  `project_date` date DEFAULT NULL COMMENT '项目日期',
  `dept_id` bigint DEFAULT NULL COMMENT '发起部门（数据权限按部门隔离的落点）',
  `dept_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `project_desc` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '项目说明',
  `analysis_param_json` json DEFAULT NULL COMMENT '穿透参数快照：upDepth/downDepth/ratioMin/ratioMax/minWeight/relScope/includeBookkeeping（页面7 编辑与回显唯一来源）',
  `supplier_count` int NOT NULL DEFAULT '0' COMMENT '供应商数量（冗余统计，成员表为准）',
  `analysis_status` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '未分析' COMMENT '分析状态：未分析/分析中/已完成/部分失败/已失败/已取消（页面7 列表列）',
  `last_task_id` bigint unsigned DEFAULT NULL COMMENT '最近一次分析任务',
  `last_report_version_id` bigint unsigned DEFAULT NULL COMMENT '最新可展示版本（历史版本不被覆盖）',
  `status` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  `del_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_project_no` (`project_no`),
  KEY `idx_dept` (`dept_id`,`project_date`),
  KEY `idx_status` (`analysis_status`)
) ENGINE=InnoDB AUTO_INCREMENT=1007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='项目单据（页面7 项目管理）：围串标核查的分组单位，仅项目成员参与分析，不因主档数量自动全库发起';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_project`
--

LOCK TABLES `srm_project` WRITE;
/*!40000 ALTER TABLE `srm_project` DISABLE KEYS */;
INSERT INTO `srm_project` VALUES (1001,'PRJ-2026-001','华晟控股集团股权穿透专项分析','2026-10-08',100,'若依科技','围绕华晟控股集团及其成员单位开展向上/向下穿透，识别最终受益人、疑似实际控制人及历史股东。','{\"upDepth\": 5, \"relScope\": \"ALL\", \"direction\": \"both\", \"downDepth\": 5, \"minWeight\": 1, \"ruleVersionNo\": 1, \"includeBookkeeping\": \"1\"}',8,'已完成',1504,1001,'0','0','admin','2026-10-08 09:00:00','admin','2026-11-25 22:33:53','演示项目：股权穿透 + 实际控制人识别'),(1002,'PRJ-2026-002','恒信供应链关联方风险排查','2026-10-20',100,'若依科技','对恒信供应链及其关联方开展幕后关系排查，重点核对同电话、同邮箱、同注册地址与共同董监高。','{\"upDepth\": 4, \"relScope\": \"ALL\", \"direction\": \"both\", \"downDepth\": 4, \"minWeight\": 3, \"ruleVersionNo\": 1, \"includeBookkeeping\": \"1\"}',5,'已完成',1202,1002,'0','0','admin','2026-10-20 09:30:00','admin','2026-11-21 14:20:00','演示项目：幕后关系 + 账簿共用信息'),(1003,'PRJ-2026-003','2026 年度重点供应商监控名单核查','2026-11-05',100,'若依科技','对全部在库重点供应商做全量关联复核，输出报告版本并在复核台完成线索结论确认。','{\"upDepth\": 6, \"relScope\": \"ALL\", \"direction\": \"both\", \"downDepth\": 6, \"minWeight\": 1, \"ruleVersionNo\": 1, \"includeBookkeeping\": \"1\"}',12,'已失败',1203,1003,'0','0','admin','2026-11-05 08:00:00','admin','2026-11-22 08:40:00','演示项目：全量复核 + 报告版本'),(1004,'PRJ-2026-004','2026 年度新增供应商准入关联核查','2026-11-12',100,'若依科技','对华晟系、瑞昇系及新增准入供应商共 15 家开展准入前关联关系核查，覆盖股权穿透、幕后关系与账簿共用线索。','{\"upDepth\": 5, \"relScope\": \"ALL\", \"direction\": \"both\", \"downDepth\": 5, \"minWeight\": 1, \"ruleVersionNo\": 1, \"includeBookkeeping\": \"1\"}',15,'已完成',1501,1004,'0','0','admin','2026-11-12 09:00:00','admin','2026-11-22 10:15:00','演示项目：15 家供应商准入关联核查（本地库直接回放）'),(1005,'PRJ-2026-005','重点供应商股权穿透复核（第二批）','2026-11-18',100,'若依科技','对第二批 5 家重点供应商复核向上股东、向下投资与实际控制人链路，核对既有本地沉淀图谱是否仍然成立。','{\"upDepth\": 6, \"relScope\": \"ALL\", \"direction\": \"both\", \"downDepth\": 6, \"minWeight\": 3, \"ruleVersionNo\": 1, \"includeBookkeeping\": \"1\"}',5,'已完成',1502,1005,'0','0','admin','2026-11-18 14:00:00','admin','2026-11-22 11:05:00','演示项目：第二批股权穿透复核（本地库直接回放）'),(1006,'PRJ-2026-006','瑞泽系准入关联与跨集团控制关系核查','2026-11-23',100,'若依科技','以瑞泽实业集团为中心向上/向下穿透，重点核对与华晟系的跨集团持股（25%/35%）、共同董监高与同电话/同邮箱/同注册地址线索。','{\"upDepth\": 6, \"relScope\": \"ALL\", \"direction\": \"both\", \"downDepth\": 6, \"minWeight\": 1, \"ruleVersionNo\": 1, \"includeBookkeeping\": \"1\"}',8,'已完成',1503,1006,'0','0','admin','2026-11-23 09:00:00','admin','2026-11-23 09:05:00','演示项目：瑞泽系 6 家 + 跨集团关联方核查（本地库直接回放）');
/*!40000 ALTER TABLE `srm_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_project_member`
--

DROP TABLE IF EXISTS `srm_project_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_project_member` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `project_id` bigint unsigned NOT NULL,
  `supplier_id` bigint unsigned NOT NULL,
  `supplier_code` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '供应商编码（页面7 成员行列）',
  `supplier_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '企业名称',
  `monitor_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '重点监控状态快照（成员加入时的主档值，图谱图例区分普通/重点）',
  `join_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入项目时间',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_project_sup` (`project_id`,`supplier_id`),
  KEY `idx_project` (`project_id`),
  KEY `idx_supplier` (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1054 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='项目成员（页面7「加入的多家供应商」，纪要口径：单次支持 2—20 家企业）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_project_member`
--

LOCK TABLES `srm_project_member` WRITE;
/*!40000 ALTER TABLE `srm_project_member` DISABLE KEYS */;
INSERT INTO `srm_project_member` VALUES (1001,1001,1001,'MOCK-S001','华晟控股集团有限公司','1','2026-11-03 10:00:00','admin'),(1002,1001,1002,'MOCK-S002','华晟建筑工程有限公司','1','2026-11-04 10:00:00','admin'),(1003,1001,1003,'MOCK-S003','华晟智能装备制造有限公司','0','2026-11-05 10:00:00','admin'),(1004,1001,1004,'MOCK-S004','恒信供应链管理有限公司','1','2026-11-06 10:00:00','admin'),(1005,1001,1005,'MOCK-S005','鑫达新材料科技有限公司','0','2026-11-07 10:00:00','admin'),(1006,1001,1007,'MOCK-S007','海通机电设备有限公司','0','2026-11-08 10:00:00','admin'),(1007,1001,1008,'MOCK-S008','中启环保工程有限公司','1','2026-11-09 10:00:00','admin'),(1008,1001,1009,'MOCK-S009','联志电子科技有限公司','0','2026-11-01 10:00:00','admin'),(1009,1002,1004,'MOCK-S004','恒信供应链管理有限公司','1','2026-11-02 10:00:00','admin'),(1010,1002,1006,'MOCK-S006','鸿远物流服务有限公司','1','2026-11-03 10:00:00','admin'),(1011,1002,1005,'MOCK-S005','鑫达新材料科技有限公司','0','2026-11-04 10:00:00','admin'),(1012,1002,1007,'MOCK-S007','海通机电设备有限公司','0','2026-11-05 10:00:00','admin'),(1013,1002,1009,'MOCK-S009','联志电子科技有限公司','0','2026-11-06 10:00:00','admin'),(1014,1003,1001,'MOCK-S001','华晟控股集团有限公司','1','2026-11-07 10:00:00','admin'),(1015,1003,1002,'MOCK-S002','华晟建筑工程有限公司','1','2026-11-08 10:00:00','admin'),(1016,1003,1003,'MOCK-S003','华晟智能装备制造有限公司','0','2026-11-09 10:00:00','admin'),(1017,1003,1004,'MOCK-S004','恒信供应链管理有限公司','1','2026-11-01 10:00:00','admin'),(1018,1003,1005,'MOCK-S005','鑫达新材料科技有限公司','0','2026-11-02 10:00:00','admin'),(1019,1003,1006,'MOCK-S006','鸿远物流服务有限公司','1','2026-11-03 10:00:00','admin'),(1020,1003,1007,'MOCK-S007','海通机电设备有限公司','0','2026-11-04 10:00:00','admin'),(1021,1003,1008,'MOCK-S008','中启环保工程有限公司','1','2026-11-05 10:00:00','admin'),(1022,1003,1009,'MOCK-S009','联志电子科技有限公司','0','2026-11-06 10:00:00','admin'),(1023,1003,1010,'MOCK-S010','广盛建设劳务有限公司','0','2026-11-07 10:00:00','admin'),(1024,1003,1011,'MOCK-S011','瑞昇信息技术有限公司','0','2026-11-08 10:00:00','admin'),(1025,1003,1012,'MOCK-S012','天成检测认证有限公司','0','2026-11-09 10:00:00','admin'),(1026,1004,1001,'MOCK-S001','华晟控股集团有限公司','1','2026-11-01 10:00:00','admin'),(1027,1004,1002,'MOCK-S002','华晟建筑工程有限公司','1','2026-11-02 10:00:00','admin'),(1028,1004,1003,'MOCK-S003','华晟智能装备制造有限公司','0','2026-11-03 10:00:00','admin'),(1029,1004,1004,'MOCK-S004','恒信供应链管理有限公司','1','2026-11-04 10:00:00','admin'),(1030,1004,1005,'MOCK-S005','鑫达新材料科技有限公司','0','2026-11-05 10:00:00','admin'),(1031,1004,1006,'MOCK-S006','鸿远物流服务有限公司','1','2026-11-06 10:00:00','admin'),(1032,1004,1007,'MOCK-S007','海通机电设备有限公司','0','2026-11-07 10:00:00','admin'),(1033,1004,1008,'MOCK-S008','中启环保工程有限公司','1','2026-11-08 10:00:00','admin'),(1034,1004,1009,'MOCK-S009','联志电子科技有限公司','0','2026-11-09 10:00:00','admin'),(1035,1004,1010,'MOCK-S010','广盛建设劳务有限公司','0','2026-11-01 10:00:00','admin'),(1036,1004,1011,'MOCK-S011','瑞昇信息技术有限公司','0','2026-11-02 10:00:00','admin'),(1037,1004,1012,'MOCK-S012','天成检测认证有限公司','0','2026-11-03 10:00:00','admin'),(1038,1004,1013,'MOCK-S013','蓝湾海洋工程有限公司','1','2026-11-04 10:00:00','admin'),(1039,1004,1014,'MOCK-S014','凯越精密机械有限公司','1','2026-11-05 10:00:00','admin'),(1040,1004,1015,'MOCK-S015','云通数据科技有限公司','0','2026-11-06 10:00:00','admin'),(1041,1005,1011,'MOCK-S011','瑞昇信息技术有限公司','0','2026-11-07 10:00:00','admin'),(1042,1005,1012,'MOCK-S012','天成检测认证有限公司','0','2026-11-08 10:00:00','admin'),(1043,1005,1013,'MOCK-S013','蓝湾海洋工程有限公司','1','2026-11-09 10:00:00','admin'),(1044,1005,1014,'MOCK-S014','凯越精密机械有限公司','1','2026-11-01 10:00:00','admin'),(1045,1005,1015,'MOCK-S015','云通数据科技有限公司','0','2026-11-02 10:00:00','admin'),(1046,1006,1016,'MOCK-S016','瑞泽实业集团有限公司','1','2026-11-03 10:00:00','admin'),(1047,1006,1017,'MOCK-S017','瑞泽建筑工程有限公司','0','2026-11-04 10:00:00','admin'),(1048,1006,1018,'MOCK-S018','瑞泽新能源科技有限公司','1','2026-11-05 10:00:00','admin'),(1049,1006,1019,'MOCK-S019','上海泽信国际贸易有限公司','0','2026-11-06 10:00:00','admin'),(1050,1006,1020,'MOCK-S020','瑞泽人力资源有限公司','0','2026-11-07 10:00:00','admin'),(1051,1006,1021,'MOCK-S021','中泰智能仓储有限公司','1','2026-11-08 10:00:00','admin'),(1052,1006,1001,'MOCK-S001','华晟控股集团有限公司','1','2026-11-09 10:00:00','admin'),(1053,1006,1004,'MOCK-S004','恒信供应链管理有限公司','1','2026-11-01 10:00:00','admin');
/*!40000 ALTER TABLE `srm_project_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_rel_clue`
--

DROP TABLE IF EXISTS `srm_rel_clue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_rel_clue` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `task_id` bigint unsigned NOT NULL COMMENT '产出任务',
  `project_id` bigint unsigned DEFAULT NULL,
  `report_version_id` bigint unsigned DEFAULT NULL COMMENT '所属报告版本（版本内线索只读）',
  `supplier_a` bigint unsigned DEFAULT NULL COMMENT '企业 A（主档 id）',
  `supplier_b` bigint unsigned DEFAULT NULL COMMENT '企业 B',
  `ent_a_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '企业名称（1156 企业名称）',
  `ent_b_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '企业名称',
  `ent_a_tyc_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业ID（1156）',
  `ent_b_tyc_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `legal_type_a` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法人类型：1 人 / 2 公司（1156 原文枚举）',
  `legal_type_b` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `legal_person_a` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法定代表人',
  `legal_person_b` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reg_capital_a` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册资本',
  `reg_capital_b` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `establish_a` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '成立时间',
  `establish_b` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `clue_type` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '疑似关系类型（1156）→ 界面「命中规则」线索内容',
  `clue_detail` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '疑似关系详情（1156）→ 界面「关联线索」',
  `bookkeeping_phone` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '代记账公司电话（1156，受"是否包含代记账公司电话关系"入参控制）',
  `local_rel_type` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '本地命中维度：同法定代表人/共同股东/共同董监高/同电话/同邮箱/同注册地址',
  `rule_id` bigint unsigned DEFAULT NULL COMMENT '命中本地规则',
  `rule_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '命中规则名（本地关系规则，非接口字段）',
  `weight` tinyint DEFAULT NULL COMMENT '权重（本地规则权重 1—5，非接口字段）',
  `rel_path` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联路径（节点名链，页面12/13 展示）',
  `up_depth` tinyint DEFAULT NULL COMMENT '本次命中使用的向上穿透层级',
  `down_depth` tinyint DEFAULT NULL COMMENT '向下穿透层级',
  `data_source` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '数据来源：接口编号 / 本地计算',
  `source_api_id` int DEFAULT NULL,
  `raw_id` bigint unsigned DEFAULT NULL,
  `review_status` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '待复核' COMMENT '复核状态：待复核 / 已确认有关联 / 排除线索 / 需补充材料（页面12）。状态回写本行仅为展示，处置过程走 srm_clue_review 追加',
  `page_no` int DEFAULT NULL COMMENT '命中时的分页页码（1156 每页最大 20 条）',
  `analyzed_at` datetime NOT NULL COMMENT '分析时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_task` (`task_id`),
  KEY `idx_project` (`project_id`,`review_status`),
  KEY `idx_pair` (`supplier_a`,`supplier_b`),
  KEY `idx_version` (`report_version_id`),
  KEY `idx_analyzed` (`analyzed_at`)
) ENGINE=InnoDB AUTO_INCREMENT=1039 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='关联命中线索（页面7 主产出、页面12 复核对象、页面13 报告内容）。所有命中仅作为线索，不构成合规结论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_rel_clue`
--

LOCK TABLES `srm_rel_clue` WRITE;
/*!40000 ALTER TABLE `srm_rel_clue` DISABLE KEYS */;
INSERT INTO `srm_rel_clue` VALUES (1001,1201,1001,1001,1004,1006,'恒信供应链管理有限公司','鸿远物流服务有限公司','T1004','T1006','1','1','刘志强','刘志强','2000万元人民币','800万元人民币','2016-01-11','2018-03-06','同电话+同邮箱+同地址','两家企业工商登记的联系电话、邮箱与注册地址完全一致，账簿共用电话 021-58881004。','021-58881004','同电话',4,'同电话',3,'恒信供应链管理有限公司 -> 鸿远物流服务有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',1,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1002,1201,1001,1001,1006,1001,'鸿远物流服务有限公司','华晟控股集团有限公司','T1006','T1001','1','1','刘志强','陈国华','800万元人民币','10000万元人民币','2018-03-06','2005-03-18','共用联系方式（间接指向控股方）','鸿远物流登记电话与华晟控股集团工商年报联系入口一致，存在账簿共用与统一对外留联系方式特征。','021-58881001','同电话',4,'同电话',3,'鸿远物流服务有限公司 -> 华晟控股集团有限公司',2,1,'本地计算',1156,NULL,'待复核',1,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1003,1201,1001,1001,1008,1010,'中启环保工程有限公司','广盛建设劳务有限公司','T1008','T1010','1','1','王建军','王建军','2600万元人民币','600万元人民币','2012-11-08','2019-08-19','共同董监高+同电话','中启环保与广盛劳务共用联系人电话，且存在同一自然人担任两家企业董监高。','021-58881008','同电话',7,'共同董监高',4,'中启环保工程有限公司 -> 广盛劳务建设有限公司',1,1,'本地计算',1156,NULL,'待复核',2,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1004,1201,1001,1001,1009,1011,'联志电子科技有限公司','瑞昇信息技术有限公司','T1009','T1011','1','1','郑晓岚','郑晓岚','1800万元人民币','1000万元人民币','2015-04-02','2020-02-27','同邮箱','联志电子与瑞昇信息的工商登记邮箱一致，且存在股权参股关系（联志电子持股 10%）。',NULL,'同邮箱',5,'同邮箱',3,'联志电子科技有限公司 -> 瑞昇信息技术有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',2,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1005,1201,1001,1001,1004,1005,'恒信供应链管理有限公司','鑫达新材料科技有限公司','T1004','T1005','1','1','刘志强','孙立群','2000万元人民币','1500万元人民币','2016-01-11','2017-07-20','股权穿透（间接控股 30%）','恒信供应链持有鑫达新材料 30% 股权，向上穿透可追溯至华晟控股集团，疑似同一实际控制人。',NULL,NULL,12,'向上穿透',4,'鑫达新材料科技有限公司 -> 恒信供应链管理有限公司 -> 华晟控股集团有限公司',2,2,'1160',1160,NULL,'已确认有关联',3,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1006,1201,1001,1001,1001,1004,'华晟控股集团有限公司','恒信供应链管理有限公司','T1001','T1004','1','1','陈国华','刘志强','10000万元人民币','2000万元人民币','2005-03-18','2016-01-11','疑似实际控制人','刘志强同时为恒信供应链股东（55%）与其上游控股方的关键经办人，疑似实际控制人。',NULL,NULL,14,'实际控制人',5,'华晟控股集团有限公司 -> 恒信供应链管理有限公司',1,2,'1123',1123,NULL,'待复核',3,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1007,1202,1002,1002,1004,1006,'恒信供应链管理有限公司','鸿远物流服务有限公司','T1004','T1006','1','1','刘志强','刘志强','2000万元人民币','800万元人民币','2016-01-11','2018-03-06','同电话+同邮箱+同地址','恒信供应链与鸿远物流共用同一登记电话 021-58881004、同一邮箱及同一注册地址。','021-58881004','同电话',4,'同电话',3,'恒信供应链管理有限公司 -> 鸿远物流服务有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',1,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1008,1202,1002,1002,1004,1009,'恒信供应链管理有限公司','联志电子科技有限公司','T1004','T1009','1','1','刘志强','郑晓岚','2000万元人民币','1800万元人民币','2016-01-11','2015-04-02','共同供应商/关联投标','恒信供应链与联志电子在多个采购项目中出现共同投标联系人与同一经办电话。',NULL,'共同董监高',7,'共同董监高',4,'恒信供应链管理有限公司 -> 联志电子科技有限公司',1,1,'本地计算',1161,NULL,'待复核',1,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1009,1202,1002,1002,1005,1004,'鑫达新材料科技有限公司','恒信供应链管理有限公司','T1005','T1004','1','1','孙立群','刘志强','1500万元人民币','2000万元人民币','2017-07-20','2016-01-11','股权穿透（30% 参股）','恒信供应链持有鑫达新材料 30% 股权，属向上穿透一级路径。',NULL,NULL,12,'向上穿透',4,'鑫达新材料科技有限公司 -> 恒信供应链管理有限公司',1,1,'1160',1160,NULL,'已确认有关联',2,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1010,1202,1002,1002,1007,1002,'海通机电设备有限公司','华晟建筑工程有限公司','T1007','T1002','1','1','赵文博','李明远','1200万元人民币','5000万元人民币','2013-05-16','2010-06-01','股权穿透（40% 参股）','华晟建筑持有海通机电 40% 股权，与恒信供应链同为集团关联方。',NULL,NULL,12,'向上穿透',4,'海通机电设备有限公司 -> 华晟建筑工程有限公司 -> 华晟控股集团有限公司',2,1,'1160',1160,NULL,'待复核',2,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1011,1203,1003,1003,1008,1010,'中启环保工程有限公司','广盛建设劳务有限公司','T1008','T1010','1','1','王建军','王建军','2600万元人民币','600万元人民币','2012-11-08','2019-08-19','共同董监高+同电话','中启环保与广盛劳务共用联系人电话 021-58881008，并由同一自然人担任董监高。','021-58881008','同电话',7,'共同董监高',4,'中启环保工程有限公司 -> 广盛劳务建设有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',1,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1012,1203,1003,1003,1009,1011,'联志电子科技有限公司','瑞昇信息技术有限公司','T1009','T1011','1','1','郑晓岚','郑晓岚','1800万元人民币','1000万元人民币','2015-04-02','2020-02-27','同邮箱','联志电子与瑞昇信息登记邮箱一致（lz@lianzhi.example），并存在 10% 参股关系。',NULL,'同邮箱',5,'同邮箱',3,'联志电子科技有限公司 -> 瑞昇信息技术有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',1,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1013,1203,1003,1003,1003,1008,'华晟智能装备制造有限公司','中启环保工程有限公司','T1003','T1008','1','1','周海峰','王建军','3000万元人民币','2600万元人民币','2014-09-25','2012-11-08','股权穿透（35% 控股）','华晟智能装备持有中启环保 35% 股权，形成向下穿透控股链。',NULL,NULL,13,'向下穿透',3,'华晟智能装备制造有限公司 -> 中启环保工程有限公司',1,1,'1160',1160,NULL,'已确认有关联',2,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1014,1203,1003,1003,1001,1002,'华晟控股集团有限公司','华晟建筑工程有限公司','T1001','T1002','1','1','陈国华','李明远','10000万元人民币','5000万元人民币','2005-03-18','2010-06-01','股权穿透（100% 全资）','华晟控股集团全资持有华晟建筑，属向下穿透第一层。',NULL,NULL,13,'向下穿透',3,'华晟控股集团有限公司 -> 华晟建筑工程有限公司',0,1,'1160',1160,NULL,'已确认有关联',2,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1015,1203,1003,1003,1001,1004,'华晟控股集团有限公司','恒信供应链管理有限公司','T1001','T1004','1','1','陈国华','刘志强','10000万元人民币','2000万元人民币','2005-03-18','2016-01-11','疑似实际控制人','刘志强（恒信供应链 55% 股东）与赵文博共同出资，疑似通过多层股权实际控制关联企业。',NULL,NULL,14,'实际控制人',5,'华晟控股集团有限公司 -> 恒信供应链管理有限公司',1,2,'1123',1123,NULL,'待复核',3,'2026-11-21 09:01:30','2026-11-21 09:01:30'),(1016,1501,1004,1004,1013,1001,'蓝湾海洋工程有限公司','华晟控股集团有限公司','T1013','T1001','1','1','顾伟明','陈国华','3500万元人民币','10000万元人民币','2016-05-12','2005-03-18','股权穿透（55% 控股）','蓝湾海洋工程由华晟控股集团持股 55%，顾伟明持股 45%，属集团控股成员单位。',NULL,NULL,12,'向上穿透',5,'蓝湾海洋工程有限公司 -> 华晟控股集团有限公司',1,1,'1160',1160,NULL,'已确认有关联',2,'2026-11-22 10:02:10','2026-11-22 10:02:10'),(1017,1501,1004,1004,1014,1003,'凯越精密机械有限公司','华晟智能装备制造有限公司','T1014','T1003','1','1','何静','周海峰','2200万元人民币','3000万元人民币','2018-09-03','2014-09-25','股权穿透（60% 控股）','凯越精密机械由华晟智能装备持股 60%，何静持股 40%，形成集团内二级控股链。',NULL,NULL,12,'向上穿透',5,'凯越精密机械有限公司 -> 华晟智能装备制造有限公司 -> 华晟控股集团有限公司',1,2,'1160',1160,NULL,'已确认有关联',2,'2026-11-22 10:03:40','2026-11-22 10:03:40'),(1018,1501,1004,1004,1015,1011,'云通数据科技有限公司','瑞昇信息技术有限公司','T1015','T1011','1','1','许文娟','郑晓岚','900万元人民币','1000万元人民币','2021-11-22','2020-02-27','同电话+参股（30%）','云通数据与瑞昇信息共用登记电话 021-58881011，且瑞昇信息持股 30%。','021-58881011','同电话',7,'共同董监高',4,'云通数据科技有限公司 -> 瑞昇信息技术有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',1,'2026-11-22 10:05:20','2026-11-22 10:05:20'),(1019,1501,1004,1004,1013,1014,'蓝湾海洋工程有限公司','凯越精密机械有限公司','T1013','T1014','1','1','顾伟明','何静','3500万元人民币','2200万元人民币','2016-05-12','2018-09-03','共同董监高','许文娟在蓝湾海洋工程与凯越精密机械同时担任董监高，存在人员交叉。',NULL,'共同董监高',7,'共同董监高',4,'蓝湾海洋工程有限公司 -> 凯越精密机械有限公司',1,1,'本地计算',1156,NULL,'待复核',1,'2026-11-22 10:06:50','2026-11-22 10:06:50'),(1020,1501,1004,1004,1013,1002,'蓝湾海洋工程有限公司','华晟建筑工程有限公司','T1013','T1002','1','1','顾伟明','李明远','3500万元人民币','5000万元人民币','2016-05-12','2010-06-01','股权穿透（成员单位）','蓝湾海洋工程与华晟建筑同属华晟控股集团体系，向上穿透可追溯至陈国华。',NULL,NULL,13,'向下穿透',4,'华晟控股集团有限公司 -> 蓝湾海洋工程有限公司',1,3,'1123',1123,NULL,'已确认有关联',3,'2026-11-22 10:08:30','2026-11-22 10:08:30'),(1021,1501,1004,1004,1014,1007,'凯越精密机械有限公司','海通机电设备有限公司','T1014','T1007','1','1','何静','赵文博','2200万元人民币','1200万元人民币','2018-09-03','2013-05-16','同电话','凯越精密机械与海通机电设备登记联系电话一致，属同一经营团队对外留号。','021-58881007','同电话',4,'同电话',3,'凯越精密机械有限公司 -> 海通机电设备有限公司',1,1,'本地计算',1156,NULL,'待复核',1,'2026-11-22 10:10:10','2026-11-22 10:10:10'),(1022,1501,1004,1004,1015,1009,'云通数据科技有限公司','联志电子科技有限公司','T1015','T1009','1','1','许文娟','郑晓岚','900万元人民币','1800万元人民币','2021-11-22','2015-04-02','同邮箱','云通数据与联志电子登记邮箱一致，且共用联系人。',NULL,'同邮箱',5,'同邮箱',3,'云通数据科技有限公司 -> 联志电子科技有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',2,'2026-11-22 10:11:40','2026-11-22 10:11:40'),(1023,1501,1004,1004,1001,1013,'华晟控股集团有限公司','蓝湾海洋工程有限公司','T1001','T1013','1','1','陈国华','顾伟明','10000万元人民币','3500万元人民币','2005-03-18','2016-05-12','疑似实际控制人','顾伟明为蓝湾海洋工程 45% 股东，同时与华晟控股集团关键经办人重合，疑似共同实际控制。',NULL,NULL,14,'实际控制人',5,'华晟控股集团有限公司 -> 蓝湾海洋工程有限公司',2,1,'1123',1123,NULL,'待复核',3,'2026-11-22 10:13:00','2026-11-22 10:13:00'),(1024,1502,1005,1005,1011,1015,'瑞昇信息技术有限公司','云通数据科技有限公司','T1011','T1015','1','1','郑晓岚','许文娟','1000万元人民币','900万元人民币','2020-02-27','2021-11-22','股权穿透（30% 参股）','瑞昇信息持有云通数据 30% 股权，向上穿透至许文娟。',NULL,NULL,12,'向上穿透',6,'云通数据科技有限公司 -> 瑞昇信息技术有限公司',1,1,'1160',1160,NULL,'已确认有关联',2,'2026-11-22 11:01:00','2026-11-22 11:01:00'),(1025,1502,1005,1005,1015,1011,'云通数据科技有限公司','瑞昇信息技术有限公司','T1015','T1011','1','1','许文娟','郑晓岚','900万元人民币','1000万元人民币','2021-11-22','2020-02-27','共同董监高+同电话','云通数据与瑞昇信息董监高交叉，登记电话一致 021-58881011。','021-58881011','同电话',7,'共同董监高',4,'云通数据科技有限公司 -> 瑞昇信息技术有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',1,'2026-11-22 11:02:20','2026-11-22 11:02:20'),(1026,1502,1005,1005,1014,1003,'凯越精密机械有限公司','华晟智能装备制造有限公司','T1014','T1003','1','1','何静','周海峰','2200万元人民币','3000万元人民币','2018-09-03','2014-09-25','股权穿透（60% 控股）','凯越精密机械由华晟智能装备持股 60%，穿透链路复核通过。',NULL,NULL,12,'向上穿透',6,'凯越精密机械有限公司 -> 华晟智能装备制造有限公司',1,1,'1160',1160,NULL,'已确认有关联',2,'2026-11-22 11:03:10','2026-11-22 11:03:10'),(1027,1502,1005,1005,1013,1001,'蓝湾海洋工程有限公司','华晟控股集团有限公司','T1013','T1001','1','1','顾伟明','陈国华','3500万元人民币','10000万元人民币','2016-05-12','2005-03-18','股权穿透（55% 控股）','蓝湾海洋工程由华晟控股集团持股 55%，复核链路成立。',NULL,NULL,12,'向上穿透',6,'蓝湾海洋工程有限公司 -> 华晟控股集团有限公司',1,1,'1160',1160,NULL,'已确认有关联',2,'2026-11-22 11:03:50','2026-11-22 11:03:50'),(1028,1502,1005,1005,1012,1015,'天成检测认证有限公司','云通数据科技有限公司','T1012','T1015','1','1','许文娟','许文娟','500万元人民币','900万元人民币','2021-06-15','2021-11-22','同注册地址','天成检测与云通数据注册地址同处虹口区同一楼宇，存在账簿共用特征。',NULL,'同注册地址',4,'同注册地址',3,'天成检测技术有限公司 -> 云通数据科技有限公司',1,1,'本地计算',1156,NULL,'待复核',1,'2026-11-22 11:04:00','2026-11-22 11:04:00'),(1029,1502,1005,1005,1013,1014,'蓝湾海洋工程有限公司','凯越精密机械有限公司','T1013','T1014','1','1','顾伟明','何静','3500万元人民币','2200万元人民币','2016-05-12','2018-09-03','共同董监高','蓝湾海洋工程与凯越精密机械存在共同董监高，判定为关联方。',NULL,'共同董监高',7,'共同董监高',4,'蓝湾海洋工程有限公司 -> 凯越精密机械有限公司',1,1,'本地计算',1156,NULL,'待复核',1,'2026-11-22 11:04:05','2026-11-22 11:04:05'),(1030,1503,1006,1006,1021,1016,'中泰智能仓储有限公司','瑞泽实业集团有限公司','T1021','T1016','1','1','罗伯涛','沈文彬','2500万元人民币','8000万元人民币','2015-10-30','2008-04-16','股权穿透（40% 控股）','中泰智能仓储由瑞泽实业集团持股 40%，为第一大股东，属集团控股成员单位。',NULL,NULL,12,'向上穿透',5,'中泰智能仓储有限公司 -> 瑞泽实业集团有限公司',1,1,'1160',1160,NULL,'已确认有关联',1,'2026-11-23 09:01:10','2026-11-23 09:01:10'),(1031,1503,1006,1006,1016,1001,'瑞泽实业集团有限公司','华晟控股集团有限公司','T1016','T1001','1','1','沈文彬','陈国华','8000万元人民币','10000万元人民币','2008-04-16','2005-03-18','股权穿透（跨集团持股 25%）','华晟控股集团有限公司持有瑞泽实业集团 25% 股权，为第二大股东，两个集团存在股权层面控制关系。',NULL,NULL,12,'向上穿透',6,'瑞泽实业集团有限公司 -> 华晟控股集团有限公司',1,2,'1160',1160,NULL,'已确认有关联',1,'2026-11-23 09:01:35','2026-11-23 09:01:35'),(1032,1503,1006,1006,1021,1004,'中泰智能仓储有限公司','恒信供应链管理有限公司','T1021','T1004','1','1','罗伯涛','刘志强','2500万元人民币','2000万元人民币','2015-10-30','2016-01-11','股权穿透（跨集团参股 35%）','恒信供应链持有中泰智能仓储 35% 股权，与瑞泽实业集团形成共同控制。',NULL,NULL,12,'向上穿透',5,'中泰智能仓储有限公司 -> 恒信供应链管理有限公司',1,1,'1160',1160,NULL,'已确认有关联',2,'2026-11-23 09:01:58','2026-11-23 09:01:58'),(1033,1503,1006,1006,1019,1016,'上海泽信国际贸易有限公司','瑞泽实业集团有限公司','T1019','T1016','1','1','沈文彬','沈文彬','1200万元人民币','8000万元人民币','2019-01-08','2008-04-16','同电话+同邮箱+同注册地址','泽信国贸与瑞泽实业集团登记电话 021-66881016、邮箱与注册地址完全一致，具备账簿共用特征。','021-66881016','同电话',4,'同电话',3,'上海泽信国际贸易有限公司 -> 瑞泽实业集团有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',2,'2026-11-23 09:02:20','2026-11-23 09:02:20'),(1034,1503,1006,1006,1020,1016,'瑞泽人力资源有限公司','瑞泽实业集团有限公司','T1020','T1016','1','1','潘莉','沈文彬','600万元人民币','8000万元人民币','2020-05-19','2008-04-16','同电话','瑞泽人力与瑞泽实业集团共用集团总机 021-66881016，对外联系方式未做区隔。','021-66881016','同电话',4,'同电话',3,'瑞泽人力资源有限公司 -> 瑞泽实业集团有限公司',1,1,'本地计算',1156,NULL,'待复核',3,'2026-11-23 09:02:42','2026-11-23 09:02:42'),(1035,1503,1006,1006,1020,1001,'瑞泽人力资源有限公司','华晟控股集团有限公司','T1020','T1001','1','1','潘莉','陈国华','600万元人民币','10000万元人民币','2020-05-19','2005-03-18','共同董监高（跨集团）','徐海燕同时在瑞泽人力与华晟控股集团担任监事，存在跨集团人员交叉。',NULL,'共同董监高',7,'共同董监高',4,'瑞泽人力资源有限公司 -> 华晟控股集团有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',3,'2026-11-23 09:03:00','2026-11-23 09:03:00'),(1036,1503,1006,1006,1016,1013,'瑞泽实业集团有限公司','蓝湾海洋工程有限公司','T1016','T1013','1','1','沈文彬','顾伟明','8000万元人民币','3500万元人民币','2008-04-16','2016-05-12','共同董监高','顾伟明同时为瑞泽实业集团独立董事与蓝湾海洋工程法定代表人，两集团存在人员连通。',NULL,'共同董监高',7,'共同董监高',4,'瑞泽实业集团有限公司 -> 蓝湾海洋工程有限公司',1,1,'本地计算',1156,NULL,'待复核',4,'2026-11-23 09:03:15','2026-11-23 09:03:15'),(1037,1503,1006,1006,1021,1006,'中泰智能仓储有限公司','鸿远物流服务有限公司','T1021','T1006','1','1','罗伯涛','刘志强','2500万元人民币','800万元人民币','2015-10-30','2018-03-06','共同董监高','刘志强同时为中泰智能仓储董事与恒信供应链/鸿远物流法定代表人，为跨集团关键自然人。',NULL,'共同董监高',7,'共同董监高',5,'中泰智能仓储有限公司 -> 鸿远物流服务有限公司',1,1,'本地计算',1156,NULL,'已确认有关联',4,'2026-11-23 09:03:28','2026-11-23 09:03:28'),(1038,1503,1006,1006,1018,1016,'瑞泽新能源科技有限公司','瑞泽实业集团有限公司','T1018','T1016','1','1','沈文彬','沈文彬','3000万元人民币','8000万元人民币','2016-03-24','2008-04-16','疑似实际控制人','沈文彬直接持有瑞泽新能源 40%，并通过瑞泽实业集团间接控制 60%，为疑似实际控制人。',NULL,NULL,14,'实际控制人',6,'瑞泽新能源科技有限公司 -> 瑞泽实业集团有限公司 -> 沈文彬',2,1,'1123',1123,NULL,'已确认有关联',5,'2026-11-23 09:03:38','2026-11-23 09:03:38');
/*!40000 ALTER TABLE `srm_rel_clue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_rel_rule`
--

DROP TABLE IF EXISTS `srm_rel_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_rel_rule` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `rule_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '关系规则名（页面7 命中明细的"命中规则"取此值）',
  `rel_dimension` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '关系维度编码：SERVE_ALL/INVEST/BRANCH/HISTORICAL_SHAREHOLDER/HISTORICAL_SERVE_ALL/ALL/legal_person/invest/common_hcg/phone/email/address',
  `rel_dimension_name` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '关系维度中文名：董监高/股东/分支机构/历史股东/历史董监高/所有类型（默认）/法定代表人/对外投资/共同董监高/同电话/同邮箱/同注册地址',
  `detect_type` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '识别类型（页面10 列），本地规则维度与接口维度分列',
  `direction` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'both' COMMENT '分析方向：双向 / 向上 / 向下；1160 只有单一方向入参（up/down 各 10 层，不可双向），双向规则在网关侧拆成两次调用、分别计数计费',
  `weight` tinyint NOT NULL DEFAULT '3' COMMENT '线索权重 1—5（页面10；页面7 连线标注）',
  `field_mapping_code` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段映射标识（由页面11 维护，指向 srm_field_mapping.inner_key）',
  `api_id` int DEFAULT NULL COMMENT '承载接口：1161/1156/1141/1123/1160；本地计算维度为 NULL',
  `tyc_rel_type` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接口侧关系类型枚举原值：1-同电话 / 2-同邮箱 / 3-同注册地址 / SERVE_ALL 等',
  `up_depth` tinyint NOT NULL DEFAULT '4' COMMENT '向上穿透层数，1—10（1160 层次，为空默认 4）',
  `down_depth` tinyint NOT NULL DEFAULT '4' COMMENT '向下穿透层数，1—10',
  `ratio_min` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '穿透比例下限（≥，默认 0）',
  `ratio_max` decimal(9,4) NOT NULL DEFAULT '1.0000' COMMENT '穿透比例上限（≤，默认 1）',
  `remark_text` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '识别说明（页面10 第 6 列）',
  `recommend_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否推荐规则：1 系统预置，"恢复推荐规则"只重置本类',
  `default_on` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '默认启用：本期只开 股东 + 法定代表人（纪要结论），其余为 0',
  `enabled` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '启用（页面10 第 1 列）',
  `sort_no` int DEFAULT NULL,
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_enabled` (`enabled`,`del_flag`),
  KEY `idx_api` (`api_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='关联关系配置（页面10 七列 + 入参映射）。配置驱动，取值不写死在程序中；规则变更走版本快照';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_rel_rule`
--

LOCK TABLES `srm_rel_rule` WRITE;
/*!40000 ALTER TABLE `srm_rel_rule` DISABLE KEYS */;
INSERT INTO `srm_rel_rule` VALUES (1,'股东','INVEST','股东','接口维度','both',5,NULL,1160,'INVEST',10,10,0.0000,1.0000,'股权穿透核心维度：投资方持股关系','1','1','1',1,'admin','2026-01-01 00:00:00','admin','2026-11-25 19:55:10','0'),(2,'法定代表人','legal_person','法定代表人','本地+接口','both',5,NULL,1123,NULL,4,4,0.0000,1.0000,'同一法定代表人指向的多主体，幕后强关联','1','1','1',2,'admin','2026-01-01 00:00:00','admin','2026-11-25 19:55:10','0'),(3,'共同董监高','SERVE_ALL','董监高','接口维度','both',4,NULL,1161,'SERVE_ALL',4,4,0.0000,1.0000,'共用董事/监事/高管','1','0','1',3,'admin','2026-01-01 00:00:00','','2026-11-24 23:21:40','0'),(4,'同电话','phone','同电话','接口维度','both',3,NULL,1156,'1',4,4,0.0000,1.0000,'联系电话相同（受代记账口径影响）','1','0','1',4,'admin','2026-01-01 00:00:00','','2026-11-24 23:21:40','0'),(5,'同邮箱','email','同邮箱','接口维度','both',3,NULL,1156,'2',4,4,0.0000,1.0000,'电子邮箱相同','1','0','0',5,'admin','2026-01-01 00:00:00','','2026-11-24 23:21:40','0'),(6,'同注册地址','address','同注册地址','接口维度','both',2,NULL,1156,'3',4,4,0.0000,1.0000,'注册地址相同，弱关联需复核','1','0','1',6,'admin','2026-01-01 00:00:00','','2026-11-24 23:21:40','0');
/*!40000 ALTER TABLE `srm_rel_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_rel_rule_version`
--

DROP TABLE IF EXISTS `srm_rel_rule_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_rel_rule_version` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `version_no` int NOT NULL COMMENT '规则版本号，连续不重复（页面7 审计留痕之"规则版本"）',
  `change_type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '新增/编辑/启停/删除/恢复推荐',
  `snapshot_json` json NOT NULL COMMENT '变更后全量启用规则快照（页面13 规则快照从此引用）',
  `diff_json` json DEFAULT NULL COMMENT '变更前后差异，供配置调整留痕',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '变更人（写入页面17 审计）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_version` (`version_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='规则版本流水（APPEND-ONLY）。分析任务与报告版本引用具体 version_no，保证历史结论可复现';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_rel_rule_version`
--

LOCK TABLES `srm_rel_rule_version` WRITE;
/*!40000 ALTER TABLE `srm_rel_rule_version` DISABLE KEYS */;
INSERT INTO `srm_rel_rule_version` VALUES (2,1,'启停','[{\"id\": 1, \"apiId\": 1160, \"params\": {}, \"sortNo\": 1, \"weight\": 5, \"delFlag\": \"0\", \"enabled\": \"1\", \"upDepth\": 10, \"createBy\": \"admin\", \"ratioMax\": 1.0, \"ratioMin\": 0.0, \"ruleName\": \"股东\", \"updateBy\": \"admin\", \"defaultOn\": \"1\", \"direction\": \"both\", \"downDepth\": 10, \"createTime\": \"2026-01-01 00:00:00\", \"detectType\": \"接口维度\", \"remarkText\": \"股权穿透核心维度：投资方持股关系\", \"tycRelType\": \"INVEST\", \"updateTime\": \"2026-11-25 19:55:10\", \"relDimension\": \"INVEST\", \"recommendFlag\": \"1\", \"relDimensionName\": \"股东\"}, {\"id\": 2, \"apiId\": 1123, \"params\": {}, \"sortNo\": 2, \"weight\": 5, \"delFlag\": \"0\", \"enabled\": \"1\", \"upDepth\": 4, \"createBy\": \"admin\", \"ratioMax\": 1.0, \"ratioMin\": 0.0, \"ruleName\": \"法定代表人\", \"updateBy\": \"admin\", \"defaultOn\": \"1\", \"direction\": \"both\", \"downDepth\": 4, \"createTime\": \"2026-01-01 00:00:00\", \"detectType\": \"本地+接口\", \"remarkText\": \"同一法定代表人指向的多主体，幕后强关联\", \"updateTime\": \"2026-11-25 19:55:10\", \"relDimension\": \"legal_person\", \"recommendFlag\": \"1\", \"relDimensionName\": \"法定代表人\"}, {\"id\": 3, \"apiId\": 1161, \"params\": {}, \"sortNo\": 3, \"weight\": 4, \"delFlag\": \"0\", \"enabled\": \"1\", \"upDepth\": 4, \"createBy\": \"admin\", \"ratioMax\": 1.0, \"ratioMin\": 0.0, \"ruleName\": \"共同董监高\", \"defaultOn\": \"0\", \"direction\": \"both\", \"downDepth\": 4, \"createTime\": \"2026-01-01 00:00:00\", \"detectType\": \"接口维度\", \"remarkText\": \"共用董事/监事/高管\", \"tycRelType\": \"SERVE_ALL\", \"updateTime\": \"2026-11-24 23:21:40\", \"relDimension\": \"SERVE_ALL\", \"recommendFlag\": \"1\", \"relDimensionName\": \"董监高\"}, {\"id\": 4, \"apiId\": 1156, \"params\": {}, \"sortNo\": 4, \"weight\": 3, \"delFlag\": \"0\", \"enabled\": \"1\", \"upDepth\": 4, \"createBy\": \"admin\", \"ratioMax\": 1.0, \"ratioMin\": 0.0, \"ruleName\": \"同电话\", \"defaultOn\": \"0\", \"direction\": \"both\", \"downDepth\": 4, \"createTime\": \"2026-01-01 00:00:00\", \"detectType\": \"接口维度\", \"remarkText\": \"联系电话相同（受代记账口径影响）\", \"tycRelType\": \"1\", \"updateTime\": \"2026-11-24 23:21:40\", \"relDimension\": \"phone\", \"recommendFlag\": \"1\", \"relDimensionName\": \"同电话\"}, {\"id\": 5, \"apiId\": 1156, \"params\": {}, \"sortNo\": 5, \"weight\": 3, \"delFlag\": \"0\", \"enabled\": \"0\", \"upDepth\": 4, \"createBy\": \"admin\", \"ratioMax\": 1.0, \"ratioMin\": 0.0, \"ruleName\": \"同邮箱\", \"defaultOn\": \"0\", \"direction\": \"both\", \"downDepth\": 4, \"createTime\": \"2026-01-01 00:00:00\", \"detectType\": \"接口维度\", \"remarkText\": \"电子邮箱相同\", \"tycRelType\": \"2\", \"updateTime\": \"2026-11-24 23:21:40\", \"relDimension\": \"email\", \"recommendFlag\": \"1\", \"relDimensionName\": \"同邮箱\"}, {\"id\": 6, \"apiId\": 1156, \"params\": {}, \"sortNo\": 6, \"weight\": 2, \"delFlag\": \"0\", \"enabled\": \"1\", \"upDepth\": 4, \"createBy\": \"admin\", \"ratioMax\": 1.0, \"ratioMin\": 0.0, \"ruleName\": \"同注册地址\", \"defaultOn\": \"0\", \"direction\": \"both\", \"downDepth\": 4, \"createTime\": \"2026-01-01 00:00:00\", \"detectType\": \"接口维度\", \"remarkText\": \"注册地址相同，弱关联需复核\", \"tycRelType\": \"3\", \"updateTime\": \"2026-11-24 23:21:40\", \"relDimension\": \"address\", \"recommendFlag\": \"1\", \"relDimensionName\": \"同注册地址\"}]','{\"afterName\": \"法定代表人\", \"beforeName\": \"法定代表人\", \"afterWeight\": 5, \"afterEnabled\": \"1\", \"beforeWeight\": 5, \"beforeEnabled\": \"1\"}','admin','2026-11-25 19:55:10',NULL);
/*!40000 ALTER TABLE `srm_rel_rule_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_report_version`
--

DROP TABLE IF EXISTS `srm_report_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_report_version` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `project_id` bigint unsigned NOT NULL,
  `version_no` int NOT NULL COMMENT '版本号，连续不重复（页面13 表 13-1 第 1 列）',
  `task_id` bigint unsigned NOT NULL COMMENT '来源分析任务',
  `analysis_at` datetime NOT NULL COMMENT '分析时间',
  `supplier_scope` json NOT NULL COMMENT '分析范围/分析供应商清单（固化，含供应商编码与名称快照）',
  `member_count` int NOT NULL DEFAULT '0' COMMENT '分析供应商数',
  `clue_count` int NOT NULL DEFAULT '0' COMMENT '关系线索数',
  `up_depth` tinyint DEFAULT NULL COMMENT '向上穿透层级（固化）',
  `down_depth` tinyint DEFAULT NULL COMMENT '向下穿透层级（固化）',
  `min_weight` tinyint DEFAULT NULL COMMENT '最低权重（固化）',
  `rel_scope` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关系口径（固化）',
  `rule_version_no` int DEFAULT NULL COMMENT '规则快照版本（引用 srm_rel_rule_version）',
  `rule_snapshot` json DEFAULT NULL COMMENT '启用规则快照原文（页面13 规则快照）',
  `hit_rules` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '命中规则清单（固化）',
  `data_source` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '数据来源（接口编号集合）',
  `est_unit` decimal(10,2) DEFAULT NULL COMMENT '预估/估算调用单元（本地估算，非计费字段）',
  `data_nature` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '正式数据' COMMENT '数据性质：正式数据 / 演示数据（原型标注「前端模拟数据」，正式库必须区分）',
  `is_locked` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '定稿锁定：1 后本行禁止任何 UPDATE（页面13 只读快照）',
  `export_count` int NOT NULL DEFAULT '0' COMMENT '导出次数（导出动作明细在审计日志）',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_project_version` (`project_id`,`version_no`),
  KEY `idx_task` (`task_id`),
  KEY `idx_analysis_at` (`analysis_at`)
) ENGINE=InnoDB AUTO_INCREMENT=1007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='项目关联分析报告版本（页面13）。每次分析单独保存、不覆盖历史；固化分析范围/穿透层级/规则快照/供应商名单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_report_version`
--

LOCK TABLES `srm_report_version` WRITE;
/*!40000 ALTER TABLE `srm_report_version` DISABLE KEYS */;
INSERT INTO `srm_report_version` VALUES (1001,1001,1,1201,'2026-11-21 09:02:10','[{\"supplierId\": 1001, \"supplierName\": \"华晟控股集团有限公司\"}, {\"supplierId\": 1002, \"supplierName\": \"华晟建筑工程有限公司\"}, {\"supplierId\": 1003, \"supplierName\": \"华晟智能装备制造有限公司\"}, {\"supplierId\": 1004, \"supplierName\": \"恒信供应链管理有限公司\"}, {\"supplierId\": 1005, \"supplierName\": \"鑫达新材料科技有限公司\"}, {\"supplierId\": 1007, \"supplierName\": \"海通机电设备有限公司\"}, {\"supplierId\": 1008, \"supplierName\": \"中启环保工程有限公司\"}, {\"supplierId\": 1009, \"supplierName\": \"联志电子科技有限公司\"}]',8,6,5,5,1,'ALL',1,'{\"rules\": [{\"id\": 1, \"name\": \"股东关系\", \"type\": \"INVEST\", \"weight\": 5}, {\"id\": 3, \"name\": \"董监高任职\", \"type\": \"SERVE_ALL\", \"weight\": 4}, {\"id\": 4, \"name\": \"同电话\", \"type\": \"ALL\", \"weight\": 3}, {\"id\": 7, \"name\": \"共同董监高\", \"type\": \"ALL\", \"weight\": 4}, {\"id\": 14, \"name\": \"实际控制人\", \"type\": \"INVEST\", \"weight\": 5}], \"versionNo\": 1}','1,3,4,7,14','天眼查开放平台（演示缓存）',30.00,'演示数据','1',2,'admin','2026-11-21 09:02:10'),(1002,1002,1,1202,'2026-11-21 14:11:32','[{\"supplierId\": 1004, \"supplierName\": \"恒信供应链管理有限公司\"}, {\"supplierId\": 1005, \"supplierName\": \"鑫达新材料科技有限公司\"}, {\"supplierId\": 1006, \"supplierName\": \"鸿远物流服务有限公司\"}, {\"supplierId\": 1007, \"supplierName\": \"海通机电设备有限公司\"}, {\"supplierId\": 1009, \"supplierName\": \"联志电子科技有限公司\"}]',5,4,4,4,3,'ALL',1,'{\"rules\": [{\"id\": 1, \"name\": \"股东关系\", \"type\": \"INVEST\", \"weight\": 5}, {\"id\": 3, \"name\": \"董监高任职\", \"type\": \"SERVE_ALL\", \"weight\": 4}, {\"id\": 4, \"name\": \"同电话\", \"type\": \"ALL\", \"weight\": 3}, {\"id\": 7, \"name\": \"共同董监高\", \"type\": \"ALL\", \"weight\": 4}, {\"id\": 14, \"name\": \"实际控制人\", \"type\": \"INVEST\", \"weight\": 5}], \"versionNo\": 1}','3,4,7','天眼查开放平台（演示缓存）',30.00,'演示数据','1',0,'admin','2026-11-21 14:11:32'),(1003,1003,1,1203,'2026-11-22 08:33:05','[{\"supplierId\": 1001}, {\"supplierId\": 1002}, {\"supplierId\": 1003}, {\"supplierId\": 1004}, {\"supplierId\": 1005}, {\"supplierId\": 1006}, {\"supplierId\": 1007}, {\"supplierId\": 1008}, {\"supplierId\": 1009}, {\"supplierId\": 1010}, {\"supplierId\": 1011}, {\"supplierId\": 1012}]',12,5,6,6,1,'ALL',1,'{\"rules\": [{\"id\": 1, \"name\": \"股东关系\", \"type\": \"INVEST\", \"weight\": 5}, {\"id\": 3, \"name\": \"董监高任职\", \"type\": \"SERVE_ALL\", \"weight\": 4}, {\"id\": 4, \"name\": \"同电话\", \"type\": \"ALL\", \"weight\": 3}, {\"id\": 7, \"name\": \"共同董监高\", \"type\": \"ALL\", \"weight\": 4}, {\"id\": 14, \"name\": \"实际控制人\", \"type\": \"INVEST\", \"weight\": 5}], \"versionNo\": 1}','1,3,4,7,14','天眼查开放平台（演示缓存）',40.00,'演示数据','0',0,'admin','2026-11-22 08:33:05'),(1004,1004,1,1501,'2026-11-22 10:14:20','[{\"supplierId\": 1001}, {\"supplierId\": 1002}, {\"supplierId\": 1003}, {\"supplierId\": 1004}, {\"supplierId\": 1005}, {\"supplierId\": 1006}, {\"supplierId\": 1007}, {\"supplierId\": 1008}, {\"supplierId\": 1009}, {\"supplierId\": 1010}, {\"supplierId\": 1011}, {\"supplierId\": 1012}, {\"supplierId\": 1013}, {\"supplierId\": 1014}, {\"supplierId\": 1015}]',15,10,5,5,1,'ALL',1,'{\"rules\": [{\"id\": 1, \"name\": \"股东关系\", \"type\": \"INVEST\", \"weight\": 5}, {\"id\": 3, \"name\": \"董监高任职\", \"type\": \"SERVE_ALL\", \"weight\": 4}, {\"id\": 4, \"name\": \"同电话\", \"type\": \"ALL\", \"weight\": 3}, {\"id\": 7, \"name\": \"共同董监高\", \"type\": \"ALL\", \"weight\": 4}, {\"id\": 14, \"name\": \"实际控制人\", \"type\": \"INVEST\", \"weight\": 5}], \"versionNo\": 1}','1,3,4,7,14','天眼查开放平台（演示缓存）',60.00,'演示数据','1',1,'admin','2026-11-22 10:14:20'),(1005,1005,1,1502,'2026-11-22 11:04:10','[{\"supplierId\": 1011}, {\"supplierId\": 1012}, {\"supplierId\": 1013}, {\"supplierId\": 1014}, {\"supplierId\": 1015}]',5,6,6,6,3,'ALL',1,'{\"rules\": [{\"id\": 1, \"name\": \"股东关系\", \"type\": \"INVEST\", \"weight\": 5}, {\"id\": 3, \"name\": \"董监高任职\", \"type\": \"SERVE_ALL\", \"weight\": 4}, {\"id\": 4, \"name\": \"同电话\", \"type\": \"ALL\", \"weight\": 3}, {\"id\": 7, \"name\": \"共同董监高\", \"type\": \"ALL\", \"weight\": 4}, {\"id\": 14, \"name\": \"实际控制人\", \"type\": \"INVEST\", \"weight\": 5}], \"versionNo\": 1}','1,3,7,14','天眼查开放平台（演示缓存）',45.00,'演示数据','1',0,'admin','2026-11-22 11:04:10'),(1006,1006,1,1503,'2026-11-23 09:03:40','[{\"supplierId\": 1016, \"supplierName\": \"瑞泽实业集团有限公司\"}, {\"supplierId\": 1017, \"supplierName\": \"瑞泽建筑工程有限公司\"}, {\"supplierId\": 1018, \"supplierName\": \"瑞泽新能源科技有限公司\"}, {\"supplierId\": 1019, \"supplierName\": \"上海泽信国际贸易有限公司\"}, {\"supplierId\": 1020, \"supplierName\": \"瑞泽人力资源有限公司\"}, {\"supplierId\": 1021, \"supplierName\": \"中泰智能仓储有限公司\"}, {\"supplierId\": 1001, \"supplierName\": \"华晟控股集团有限公司\"}, {\"supplierId\": 1004, \"supplierName\": \"恒信供应链管理有限公司\"}]',8,9,6,6,1,'ALL',1,'{\"rules\": [{\"id\": 1, \"name\": \"股东关系\", \"type\": \"INVEST\", \"weight\": 5}, {\"id\": 3, \"name\": \"董监高任职\", \"type\": \"SERVE_ALL\", \"weight\": 4}, {\"id\": 4, \"name\": \"同电话\", \"type\": \"ALL\", \"weight\": 3}, {\"id\": 7, \"name\": \"共同董监高\", \"type\": \"ALL\", \"weight\": 4}, {\"id\": 14, \"name\": \"实际控制人\", \"type\": \"INVEST\", \"weight\": 5}], \"versionNo\": 1}','1,3,4,5,7,12,13,14','天眼查开放平台（演示缓存）',55.00,'演示数据','1',1,'admin','2026-11-23 09:03:40');
/*!40000 ALTER TABLE `srm_report_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_risk_admin_punish`
--

DROP TABLE IF EXISTS `srm_risk_admin_punish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_risk_admin_punish` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `risk_type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'illegal 严重违法(846) / abnormal 经营异常(848)',
  `list_reason` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列入原因 / 列入异常名录原因',
  `list_dept` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '决定列入部门（作出决定机关）/ 决定列入异常名录部门',
  `list_date` date DEFAULT NULL COMMENT '列入日期',
  `remove_reason` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '移除原因 / 移除异常名录原因',
  `remove_dept` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '决定移除部门 / 移出部门',
  `remove_date` date DEFAULT NULL COMMENT '移除日期 / 移出日期',
  `total_count` int DEFAULT NULL COMMENT '接口返回总数',
  `raw_id` bigint unsigned DEFAULT NULL,
  `fetched_at` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_risk` (`supplier_id`,`risk_type`,`list_date`,`list_reason`),
  KEY `idx_fetch` (`supplier_id`,`fetched_at`)
) ENGINE=InnoDB AUTO_INCREMENT=1011 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='严重违法 + 经营异常（846/848 各 0.15/次，七项同构合并，risk_type 区分）。行政处罚本期不接入，不留字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_risk_admin_punish`
--

LOCK TABLES `srm_risk_admin_punish` WRITE;
/*!40000 ALTER TABLE `srm_risk_admin_punish` DISABLE KEYS */;
INSERT INTO `srm_risk_admin_punish` VALUES (1001,1008,'illegal','通过登记的住所或者经营场所无法联系','上海市青浦区市场监督管理局','2020-05-12','已办理住所变更登记，重新取得联系','上海市青浦区市场监督管理局','2020-09-18',1,NULL,'2026-11-19 11:10:00','2026-11-25 22:05:35'),(1002,1005,'abnormal','未依照规定期限公示年度报告','上海市松江区市场监督管理局','2021-07-01','已补报年度报告并公示','上海市松江区市场监督管理局','2021-11-05',1,NULL,'2026-11-19 11:10:00','2026-11-25 22:05:35'),(1003,1010,'abnormal','公示企业信息隐瞒真实情况、弄虚作假','上海市奉贤区市场监督管理局','2024-09-10',NULL,NULL,NULL,1,NULL,'2026-11-19 11:10:00','2026-11-25 22:05:35'),(1004,1014,'abnormal','未依照规定期限公示年度报告','上海市金山区市场监督管理局','2022-07-01','已补报年度报告并公示','上海市金山区市场监督管理局','2022-10-20',1,NULL,'2026-11-19 11:10:00','2026-11-25 22:05:35'),(1005,1019,'abnormal','未依照规定期限公示年度报告','上海市浦东新区市场监督管理局','2023-07-01','已补报年度报告并公示','上海市浦东新区市场监督管理局','2023-10-25',1,NULL,'2026-11-19 11:10:00','2026-11-25 22:05:35'),(1006,1021,'illegal','经营异常名录届满未满三年仍未履行公示义务','上海市嘉定区市场监督管理局','2024-03-18',NULL,NULL,NULL,1,NULL,'2026-11-19 11:10:00','2026-11-25 22:05:35'),(1007,1022,'abnormal','未依照规定期限公示年度报告','上海市松江区市场监督管理局','2023-07-01','已补报年度报告并公示','上海市松江区市场监督管理局','2023-10-18',1,NULL,'2026-11-19 11:10:00','2026-11-25 22:05:35'),(1008,1001,'illegal','编造虚假计税依据，少缴应纳税款','上海市浦东新区市场监督管理局','2022-04-15',NULL,NULL,NULL,1,NULL,'2026-11-19 11:10:00','2026-11-25 22:05:35'),(1009,1001,'abnormal','未依照规定期限公示年度报告','上海市浦东新区市场监督管理局','2023-07-01','已补报年度报告并公示','上海市浦东新区市场监督管理局','2023-10-25',1,NULL,'2026-11-19 11:10:00','2026-11-25 22:05:36'),(1010,1022,'illegal','通过登记的住所或者经营场所无法联系','上海市松江区市场监督管理局','2021-05-12','已办理住所变更登记，重新取得联系','上海市松江区市场监督管理局','2021-09-18',1,NULL,'2026-11-19 11:10:00','2026-11-25 22:05:36');
/*!40000 ALTER TABLE `srm_risk_admin_punish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_risk_dishonest`
--

DROP TABLE IF EXISTS `srm_risk_dishonest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_risk_dishonest` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `person_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '失信人名称',
  `legal_person` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法人',
  `manager_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '负责人姓名',
  `basis_no` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行依据文号',
  `province_area` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份地区（本接口唯一行政区划字段）',
  `id_no` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号码/组织机构代码（敏感加密）',
  `court` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法院',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `dishonest_type` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '失信人类型',
  `basis_org` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '做出执行的依据单位',
  `obligation` text COLLATE utf8mb4_general_ci COMMENT '生效法律文书确定的义务',
  `perform_status` text COLLATE utf8mb4_general_ci COMMENT '履行情况',
  `filing_date` date DEFAULT NULL COMMENT '立案时间',
  `case_no` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '案号',
  `conduct_type` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '失信被执行人行为具体情形',
  `amount` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '涉案金额（单位：元）',
  `total_count` int DEFAULT NULL COMMENT '接口返回总数',
  `raw_id` bigint unsigned DEFAULT NULL,
  `fetched_at` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_dishonest` (`supplier_id`,`case_no`,`person_name`),
  KEY `idx_fetch` (`supplier_id`,`fetched_at`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='历史失信人（872 0.10/次，17 项逐字段）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_risk_dishonest`
--

LOCK TABLES `srm_risk_dishonest` WRITE;
/*!40000 ALTER TABLE `srm_risk_dishonest` DISABLE KEYS */;
INSERT INTO `srm_risk_dishonest` VALUES (1001,1010,'广盛建设劳务有限公司','王建军','王建军','(2024)沪奉劳仲字第0417号','上海市','91310120MA1K10010K','上海市奉贤区人民法院','2025-04-02 00:00:00','法人或其他组织','上海市奉贤区劳动人事争议仲裁委员会','支付申请执行人工程款及利息合计 186 万元','全部未履行','2025-03-14','(2025)沪0120执1842号','有履行能力而拒不履行生效法律文书确定义务','1860000',1,NULL,'2026-11-19 11:05:00','2026-11-25 22:05:35'),(1002,1005,'鑫达新材料科技有限公司','孙立群','孙立群','(2022)沪松民初字第1883号','上海市','91310117MA1K10005E','上海市松江区人民法院','2022-12-18 00:00:00','法人或其他组织','上海市松江区人民法院','支付货款及违约金合计 52 万元','部分履行','2022-11-30','(2022)沪0117执5510号','违反财产报告制度','520000',1,NULL,'2026-11-19 11:05:00','2026-11-25 22:05:35'),(1003,1001,'华晟控股集团有限公司','陈国华','陈国华','(2023)沪0115民初8890号','上海市','91310115MA1K10001A','上海市浦东新区人民法院','2024-01-15 00:00:00','法人或其他组织','上海市浦东新区人民法院','支付合同价款及违约金合计 120 万元','全部未履行','2023-12-20','(2024)沪0115执1020号','有履行能力而拒不履行生效法律文书确定义务','1200000',1,NULL,'2026-11-19 11:05:00','2026-11-25 22:05:35'),(1004,1022,'华辰智造科技有限公司','陈建国','陈建国','(2024)沪0117民初3320号','上海市','91310117MA1K10022X','上海市松江区人民法院','2025-02-10 00:00:00','法人或其他组织','上海市松江区人民法院','支付货款合计 86 万元','部分履行','2024-09-12','(2024)沪0117执2088号','违反财产报告制度','860000',1,NULL,'2026-11-19 11:05:00','2026-11-25 22:05:35');
/*!40000 ALTER TABLE `srm_risk_dishonest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_risk_enforce`
--

DROP TABLE IF EXISTS `srm_risk_enforce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_risk_enforce` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `hist_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0 当前被执行人(839) / 1 历史被执行人(871)，同构字段合并一表',
  `case_no` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '案号',
  `court` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行法院',
  `person_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '被执行人名称',
  `id_no` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号／组织机构代码（敏感：入库加密，日志与导出不落全值）',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `target_amount` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行标的（元）',
  `total_count` int DEFAULT NULL COMMENT '接口返回总数（分页统计，仅当页快照）',
  `raw_id` bigint unsigned DEFAULT NULL,
  `fetched_at` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_case` (`supplier_id`,`hist_flag`,`case_no`,`person_name`),
  KEY `idx_fetch` (`supplier_id`,`fetched_at`) COMMENT '页面4 分页 20 条 + 页面8 增量比对'
) ENGINE=InnoDB AUTO_INCREMENT=1013 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='被执行人/历史被执行人（839 0.15/次、871 0.10/次）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_risk_enforce`
--

LOCK TABLES `srm_risk_enforce` WRITE;
/*!40000 ALTER TABLE `srm_risk_enforce` DISABLE KEYS */;
INSERT INTO `srm_risk_enforce` VALUES (1001,1010,'0','(2025)沪0120执1842号','上海市奉贤区人民法院','广盛建设劳务有限公司','91310120MA1K10010K','2025-03-14 00:00:00','1860000',2,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1002,1010,'0','(2025)沪0120执2190号','上海市奉贤区人民法院','广盛建设劳务有限公司','91310120MA1K10010K','2025-05-22 00:00:00','742500',2,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1003,1010,'1','(2023)沪0120执7731号','上海市奉贤区人民法院','广盛建设劳务有限公司','91310120MA1K10010K','2023-08-09 00:00:00','310000',1,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1004,1005,'1','(2022)沪0117执5510号','上海市松江区人民法院','鑫达新材料科技有限公司','91310117MA1K10005E','2022-11-30 00:00:00','520000',1,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1005,1008,'1','(2021)沪0118执3302号','上海市青浦区人民法院','中启环保工程有限公司','91310118MA1K10008H','2021-07-15 00:00:00','268000',1,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1006,1013,'0','(2024)沪0115执4455号','上海市浦东新区人民法院','蓝湾海洋工程有限公司','91310115MA1K10013N','2024-06-18 00:00:00','1240000',1,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1007,1021,'0','(2025)沪0114执3307号','上海市嘉定区人民法院','中泰智能仓储有限公司','91310114MA1K10021W','2025-07-09 00:00:00','960000',1,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1008,1018,'1','(2023)沪0117执4118号','上海市松江区人民法院','瑞泽新能源科技有限公司','91310117MA1K10018T','2023-04-26 00:00:00','415000',1,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1009,1022,'0','(2024)沪0117执2088号','上海市松江区人民法院','华辰智造科技有限公司','91310117MA1K10022X','2024-09-12 00:00:00','860000',1,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1010,1001,'1','(2021)沪0115执6621号','上海市浦东新区人民法院','华晟控股集团有限公司','91310115MA1K10001A','2021-05-20 00:00:00','1200000',1,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1011,1001,'0','(2025)沪0115执3390号','上海市浦东新区人民法院','华晟控股集团有限公司','91310115MA1K10001A','2025-03-18 00:00:00','540000',1,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35'),(1012,1022,'1','(2019)沪0117执1120号','上海市松江区人民法院','华辰智造科技有限公司','91310117MA1K10022X','2019-06-25 00:00:00','230000',1,NULL,'2026-11-19 11:00:00','2026-11-25 22:05:35');
/*!40000 ALTER TABLE `srm_risk_enforce` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_risk_judicial_summary`
--

DROP TABLE IF EXISTS `srm_risk_judicial_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_risk_judicial_summary` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `dimension` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '维度名：开庭公告/被执行人/法院公告/立案信息/失信人/法律诉讼/送达公告（1002 七维度）',
  `item_count` int DEFAULT NULL COMMENT '该维度记录数（接口返回，明细字段以 open.tianyancha.com/open/1002 为准）',
  `detail_json` json DEFAULT NULL COMMENT '该维度明细原样留存，字段名不在库表固化，由页面11 映射解释',
  `raw_id` bigint unsigned DEFAULT NULL,
  `fetched_at` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_dim` (`supplier_id`,`dimension`,`fetched_at`),
  KEY `idx_sup` (`supplier_id`,`dimension`)
) ENGINE=InnoDB AUTO_INCREMENT=1015 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='司法风险组合接口 1002（1.50/次）七维度汇总。纪要口径：组合接口单价≥单独调用 5 倍，先取汇总、明细按需走单独接口，本表就是"按需"的判断依据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_risk_judicial_summary`
--

LOCK TABLES `srm_risk_judicial_summary` WRITE;
/*!40000 ALTER TABLE `srm_risk_judicial_summary` DISABLE KEYS */;
INSERT INTO `srm_risk_judicial_summary` VALUES (1001,1010,'被执行人',2,'{\"items\": [{\"amount\": \"1860000\", \"caseNo\": \"(2025)沪0120执1842号\"}, {\"amount\": \"742500\", \"caseNo\": \"(2025)沪0120执2190号\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1002,1010,'失信人',1,'{\"items\": [{\"caseNo\": \"(2025)沪0120执1842号\", \"publishTime\": \"2025-04-02\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1003,1010,'开庭公告',1,'{\"items\": [{\"caseNo\": \"(2025)沪0120民初2211号\", \"reason\": \"建设工程施工合同纠纷\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1004,1005,'欠税公告',1,'{\"items\": [{\"balance\": \"186500.00\", \"taxType\": \"增值税\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1005,1005,'法律诉讼',2,'{\"items\": [{\"caseNo\": \"(2022)沪0117民初1883号\", \"reason\": \"买卖合同纠纷\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1006,1008,'经营异常',1,'{\"items\": [{\"reason\": \"通过登记的住所无法联系\", \"listDate\": \"2020-05-12\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1007,1013,'开庭公告',1,'{\"items\": [{\"caseNo\": \"(2024)沪0115民初6620号\", \"reason\": \"建设工程施工合同纠纷\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1008,1014,'法律诉讼',1,'{\"items\": [{\"caseNo\": \"(2023)沪0116民初1188号\", \"reason\": \"买卖合同纠纷\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1009,1015,'开庭公告',1,'{\"items\": [{\"caseNo\": \"(2025)沪0109民初0345号\", \"reason\": \"技术服务合同纠纷\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1010,1021,'被执行人',1,'{\"items\": [{\"amount\": \"960000\", \"caseNo\": \"(2025)沪0114执3307号\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1011,1019,'经营异常',1,'{\"items\": [{\"reason\": \"未依照规定期限公示年度报告\", \"listDate\": \"2023-07-01\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1012,1016,'法律诉讼',1,'{\"items\": [{\"caseNo\": \"(2024)沪0115民初8821号\", \"reason\": \"股权转让纠纷\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1013,1022,'被执行人',1,'{\"items\": [{\"amount\": \"860000\", \"caseNo\": \"(2024)沪0117执2088号\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36'),(1014,1001,'法律诉讼',1,'{\"items\": [{\"caseNo\": \"(2024)沪0115民初5210号\", \"reason\": \"合同纠纷\"}]}',NULL,'2026-11-19 11:20:00','2026-11-25 22:05:36');
/*!40000 ALTER TABLE `srm_risk_judicial_summary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_risk_tax_owed`
--

DROP TABLE IF EXISTS `srm_risk_tax_owed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_risk_tax_owed` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `taxpayer_no` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '纳税人识别号',
  `taxpayer_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '纳税人名称',
  `id_no` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '证件号码（敏感加密）',
  `taxpayer_type` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '纳税人类型',
  `reg_type` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册类型',
  `tax_type` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '欠税税种',
  `tax_category` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '税务类型',
  `owed_amount` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '欠税金额',
  `owed_balance` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '欠税余额',
  `new_owed_balance` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '当前新发生欠税余额',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `biz_address` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '经营地点',
  `tax_authority` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '税务机关',
  `legal_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法人或负责人名称',
  `legal_id_type` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法人证件名称',
  `total_count` int DEFAULT NULL COMMENT '接口返回总数',
  `raw_id` bigint unsigned DEFAULT NULL,
  `fetched_at` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_tax` (`supplier_id`,`taxpayer_no`,`tax_type`,`publish_time`),
  KEY `idx_fetch` (`supplier_id`,`fetched_at`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='欠税公告（851 0.15/次，16 项逐字段）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_risk_tax_owed`
--

LOCK TABLES `srm_risk_tax_owed` WRITE;
/*!40000 ALTER TABLE `srm_risk_tax_owed` DISABLE KEYS */;
INSERT INTO `srm_risk_tax_owed` VALUES (1001,1005,'91310117MA1K10005E','鑫达新材料科技有限公司','91310117MA1K10005E','单位纳税人','有限责任公司','增值税','国税','186500.00','186500.00','0.00','2025-02-20 00:00:00','上海市松江区新桥镇新南路88号','国家税务总局上海市松江区税务局','孙立群','统一社会信用代码',1,NULL,'2026-11-19 11:15:00','2026-11-25 22:05:36'),(1002,1010,'91310120MA1K10010K','广盛建设劳务有限公司','91310120MA1K10010K','单位纳税人','有限责任公司','企业所得税','国税','92400.00','92400.00','12000.00','2025-06-11 00:00:00','上海市奉贤区南桥镇环城东路100号','国家税务总局上海市奉贤区税务局','王建军','统一社会信用代码',1,NULL,'2026-11-19 11:15:00','2026-11-25 22:05:36'),(1003,1015,'91310104MA1K10015Q','云通数据科技有限公司','91310104MA1K10015Q','单位纳税人','有限责任公司','增值税','国税','42600.00','42600.00','0.00','2025-08-14 00:00:00','上海市虹口区四平路200号5层','国家税务总局上海市虹口区税务局','许文娟','统一社会信用代码',1,NULL,'2026-11-19 11:15:00','2026-11-25 22:05:36'),(1004,1001,'91310115MA1K10001A','华晟控股集团有限公司','91310115MA1K10001A','单位纳税人','有限责任公司','增值税','国税','268000.00','268000.00','0.00','2024-11-20 00:00:00','上海市浦东新区世纪大道100号','国家税务总局上海市浦东新区税务局','陈国华','统一社会信用代码',1,NULL,'2026-11-19 11:15:00','2026-11-25 22:05:36'),(1005,1022,'91310117MA1K10022X','华辰智造科技有限公司','91310117MA1K10022X','单位纳税人','有限责任公司','企业所得税','国税','96500.00','96500.00','12000.00','2025-03-15 00:00:00','上海市松江区新桥镇新南路66号','国家税务总局上海市松江区税务局','陈建国','统一社会信用代码',1,NULL,'2026-11-19 11:15:00','2026-11-25 22:05:36');
/*!40000 ALTER TABLE `srm_risk_tax_owed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_search_history`
--

DROP TABLE IF EXISTS `srm_search_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_search_history` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '查询人（按人展示，非全库共享）',
  `subject_type` varchar(30) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '公司名称' COMMENT '检索标识类型：公司名称/公司id/注册号/社会统一信用代码',
  `subject_key` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '检索值',
  `supplier_id` bigint unsigned DEFAULT NULL COMMENT '命中主档（未命中为空）',
  `match_mode` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '模糊' COMMENT '匹配方式：模糊（包含匹配）/ 精确',
  `searched_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '检索时间（页面2 最近查询 = 企业标识 + 检索时间）',
  PRIMARY KEY (`id`),
  KEY `idx_user_time` (`user_id`,`searched_at`),
  KEY `idx_subject` (`subject_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1019 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='最近查询记录（页面2）。查看与点击不触发外部调用；保留条数由页面16/参数配置控制，定时任务清理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_search_history`
--

LOCK TABLES `srm_search_history` WRITE;
/*!40000 ALTER TABLE `srm_search_history` DISABLE KEYS */;
INSERT INTO `srm_search_history` VALUES (1001,1,'公司名称','华晟控股集团',1001,'模糊','2026-11-21 09:47:00'),(1002,1,'公司名称','恒信供应链',1004,'模糊','2026-11-22 09:54:00'),(1003,1,'社会统一信用代码','91310116MA1K10003C',1003,'精确','2026-11-23 09:01:00'),(1004,1,'公司名称','鑫达新材料',1005,'模糊','2026-11-24 09:08:00'),(1005,1,'公司名称','联志电子',1009,'模糊','2026-11-25 09:15:00'),(1006,1,'公司名称','中启环保',1008,'模糊','2026-11-26 09:22:00'),(1007,1,'社会统一信用代码','91310109MA1K10011L',1011,'精确','2026-11-27 09:29:00'),(1008,1,'公司名称','华晟建筑',1002,'模糊','2026-11-28 09:36:00'),(1009,1,'公司名称','瑞昇信息',1011,'模糊','2026-11-29 09:43:00'),(1010,1,'公司名称','天成检测',1012,'模糊','2026-11-21 09:50:00'),(1011,1,'公司名称','海通机电',1007,'模糊','2026-11-22 09:57:00'),(1012,1,'公司名称','鸿远物流',1006,'模糊','2026-11-23 09:04:00'),(1013,1,'公司名称','瑞泽实业集团',1016,'模糊','2026-11-24 09:11:00'),(1014,1,'社会统一信用代码','91310114MA1K10021W',1021,'精确','2026-11-25 09:18:00'),(1015,1,'公司名称','中泰智能仓储',1021,'模糊','2026-11-26 09:25:00'),(1016,1,'公司名称','泽信国贸',1019,'模糊','2026-11-27 09:32:00');
/*!40000 ALTER TABLE `srm_search_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_supplier`
--

DROP TABLE IF EXISTS `srm_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_supplier` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `supplier_code` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '供应商编码（系统内部生成，页面1 列表列）',
  `supplier_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '企业名称（本地主档权威值，页面3 主档 13 项）',
  `credit_code` varchar(18) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '统一社会信用代码（缓存与比对的业务主键，可空由补全回填）',
  `reg_no` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册号（818 注册号，检索标识类型之一）',
  `tyc_company_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '天眼查公司id（818 企业id，作为外部主体稳定标识）',
  `legal_person` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法定代表人',
  `reg_capital` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册资本（保留原文含币种的文本形态，页面3）',
  `reg_capital_num` decimal(20,4) DEFAULT NULL COMMENT '注册资本数值（万元，仅用于排序/统计，不参与关系匹配）',
  `establish_date` date DEFAULT NULL COMMENT '成立日期（818 成立日期 / 819 注册日期）',
  `biz_status` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '经营状态（818 企业状态；注销/吊销用于图谱角标）',
  `industry` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所属行业（818 行业 / 国民经济行业分类大类名称）',
  `reg_address` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册地址（规范化后值，页面11 要求电话/地址/人员需规范化）',
  `phone` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话（规范化后主值）',
  `email` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `province` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省（818 省）',
  `city` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '市（818 市）',
  `district` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区（818 区）',
  `area_code` varchar(12) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '行政区划代码（818）',
  `monitor_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '重点监控：1 重点监控 / 0 普通供应商（页面1 监控状态、页面15 监控范围）',
  `data_source` varchar(60) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '人工录入' COMMENT '建档来源：初始内置/批量导入/人工录入/查询补全；补全只递增版本，不改写本列（页面3 主档 13 项之数据来源）',
  `data_version` int NOT NULL DEFAULT '1' COMMENT '主档数据版本号，每次回填递增（页面2 查询时间、数据来源与版本）',
  `last_enrich_at` datetime DEFAULT NULL COMMENT '最近一次工商补全成功时间',
  `last_update_at` datetime DEFAULT NULL COMMENT '最近更新（页面3 主档 13 项之最近更新）',
  `first_import_batch` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '首次入库批次号（页面14 批量导入来源追溯）',
  `status` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '若依状态：0 正常 / 1 停用（停用不参与分析）',
  `del_flag` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标志：0 存在 / 2 已删除',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注（本期不展示采购额、合作范围、综合评分，写在此处即可）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_supplier_code` (`supplier_code`),
  UNIQUE KEY `uk_credit_code` (`credit_code`),
  KEY `idx_name` (`supplier_name`),
  KEY `idx_monitor` (`monitor_flag`,`del_flag`),
  KEY `idx_legal` (`legal_person`),
  KEY `idx_tyc` (`tyc_company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1023 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='供应商主档：本地权威数据源。页面1 列表、页面2 模糊/精确匹配、页面7 项目成员均以此为准；本期不建内部评分';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_supplier`
--

LOCK TABLES `srm_supplier` WRITE;
/*!40000 ALTER TABLE `srm_supplier` DISABLE KEYS */;
INSERT INTO `srm_supplier` VALUES (1001,'MOCK-S001','华晟控股集团有限公司','91310115MA1K10001A','31011510001','T1001','陈国华','10000万元人民币',10000.0000,'2005-03-18','存续','商务服务业','上海市浦东新区世纪大道1000号','021-58881001','contact@huasheng-group.example','上海市','上海市','浦东新区','310115','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1002,'MOCK-S002','华晟建筑工程有限公司','91310115MA1K10002B','31011510002','T1002','李明远','5000万元人民币',5000.0000,'2010-06-01','存续','房屋建筑业','上海市浦东新区世纪大道1000号12层','021-58881002','jz@huasheng-group.example','上海市','上海市','浦东新区','310115','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1003,'MOCK-S003','华晟智能装备制造有限公司','91310116MA1K10003C','31011610003','T1003','周海峰','3000万元人民币',3000.0000,'2014-09-25','存续','专用设备制造业','上海市金山区亭卫公路200号','021-58881003','zn@huasheng-group.example','上海市','上海市','金山区','310116','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1004,'MOCK-S004','恒信供应链管理有限公司','91310104MA1K10004D','31010410004','T1004','刘志强','2000万元人民币',2000.0000,'2016-01-11','存续','商务服务业','上海市徐汇区宜山路500号','021-58881004','service@hengxin.example','上海市','上海市','徐汇区','310104','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1005,'MOCK-S005','鑫达新材料科技有限公司','91310117MA1K10005E','31011710005','T1005','孙立群','1500万元人民币',1500.0000,'2017-07-20','存续','化学原料和化学制品制造业','上海市松江区新桥镇新南路88号','021-58881005','xd@xinda.example','上海市','上海市','松江区','310117','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1006,'MOCK-S006','鸿远物流服务有限公司','91310105MA1K10006F','31010510006','T1006','刘志强','800万元人民币',800.0000,'2018-03-06','存续','道路运输业','上海市徐汇区宜山路500号3层','021-58881004','service@hengxin.example','上海市','上海市','徐汇区','310104','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1007,'MOCK-S007','海通机电设备有限公司','91310113MA1K10007G','31011310007','T1007','赵文博','1200万元人民币',1200.0000,'2013-05-16','存续','批发业','上海市宝山区长江西路900号','021-58881007','ht@haitong.example','上海市','上海市','宝山区','310113','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1008,'MOCK-S008','中启环保工程有限公司','91310118MA1K10008H','31011810008','T1008','王建军','2600万元人民币',2600.0000,'2012-11-08','存续','生态保护和环境治理业','上海市青浦区外青松公路800号','021-58881008','zq@zhongqi.example','上海市','上海市','青浦区','310118','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1009,'MOCK-S009','联志电子科技有限公司','91310114MA1K10009J','31011410009','T1009','郑晓岚','1800万元人民币',1800.0000,'2015-04-02','存续','计算机、通信和其他电子设备制造业','上海市嘉定区城中路600号','021-58881009','lz@lianzhi.example','上海市','上海市','嘉定区','310114','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1010,'MOCK-S010','广盛建设劳务有限公司','91310120MA1K10010K','31012010010','T1010','王建军','600万元人民币',600.0000,'2019-08-19','存续','建筑安装业','上海市奉贤区南桥镇环城东路100号','021-58881008','gs@guangsheng.example','上海市','上海市','奉贤区','310120','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1011,'MOCK-S011','瑞昇信息技术有限公司','91310109MA1K10011L','31010910011','T1011','郑晓岚','1000万元人民币',1000.0000,'2020-02-27','存续','软件和信息技术服务业','上海市虹口区四平路200号','021-58881011','lz@lianzhi.example','上海市','上海市','虹口区','310109','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1012,'MOCK-S012','天成检测认证有限公司','91310110MA1K10012M','31011010012','T1012','许文娟','500万元人民币',500.0000,'2021-06-15','存续','专业技术服务业','上海市杨浦区隆昌路600号','021-58881234','tc@tiancheng.example','上海市','上海市','杨浦区','310110','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1013,'MOCK-S013','蓝湾海洋工程有限公司','91310115MA1K10013N','31011510013','T1013','顾伟明','3500万元人民币',3500.0000,'2016-05-12','存续','土木工程建筑业','上海市浦东新区世纪大道1000号8层','021-58881001','lw@lanwan-ocean.example','上海市','上海市','浦东新区','310115','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1014,'MOCK-S014','凯越精密机械有限公司','91310116MA1K10014P','31011610014','T1014','何静','2200万元人民币',2200.0000,'2018-09-03','存续','通用设备制造业','上海市金山区亭卫公路200号3号厂房','021-58881003','ky@kaiyue-precision.example','上海市','上海市','金山区','310116','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1015,'MOCK-S015','云通数据科技有限公司','91310104MA1K10015Q','31010410015','T1015','许文娟','900万元人民币',900.0000,'2021-11-22','存续','软件和信息技术服务业','上海市虹口区四平路200号5层','021-58881011','yt@yuntong-data.example','上海市','上海市','虹口区','310109','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1016,'MOCK-S016','瑞泽实业集团有限公司','91310115MA1K10016R','31011510016','T1016','沈文彬','8000万元人民币',8000.0000,'2008-04-16','存续','商务服务业','上海市浦东新区金港路588号','021-66881016','contact@ruize-group.example','上海市','上海市','浦东新区','310115','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1017,'MOCK-S017','瑞泽建筑工程有限公司','91310115MA1K10017S','31011510017','T1017','潘莉','4000万元人民币',4000.0000,'2012-07-10','存续','房屋建筑业','上海市浦东新区金港路588号','021-66881017','eng@ruize-group.example','上海市','上海市','浦东新区','310115','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1018,'MOCK-S018','瑞泽新能源科技有限公司','91310117MA1K10018T','31011710018','T1018','沈文彬','3000万元人民币',3000.0000,'2016-03-24','存续','电气机械和器材制造业','上海市松江区新桥镇申港路128号','021-66881018','xny@ruize-group.example','上海市','上海市','松江区','310117','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1019,'MOCK-S019','上海泽信国际贸易有限公司','91310105MA1K10019U','31010510019','T1019','沈文彬','1200万元人民币',1200.0000,'2019-01-08','存续','零售业','上海市浦东新区金港路588号','021-66881016','contact@ruize-group.example','上海市','上海市','浦东新区','310115','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1020,'MOCK-S020','瑞泽人力资源有限公司','91310110MA1K10020V','31011010020','T1020','潘莉','600万元人民币',600.0000,'2020-05-19','存续','人力资源服务','上海市杨浦区控江路1500号6层','021-66881016','hr@ruize-group.example','上海市','上海市','杨浦区','310110','0','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1021,'MOCK-S021','中泰智能仓储有限公司','91310114MA1K10021W','31011410021','T1021','罗伯涛','2500万元人民币',2500.0000,'2015-10-30','存续','仓储业','上海市嘉定区宝安公路2000号','021-66881021','storage@zhongtai-wh.example','上海市','上海市','嘉定区','310114','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL),(1022,'MOCK-S022','华辰智造科技有限公司','91310117MA1K10022X','31011710022','T1022','陈建国','5000万元人民币',5000.0000,'2013-04-28','存续','软件和信息技术服务业','上海市松江区新桥镇新南路66号','021-58881022','contact@huachen-smart.example','上海市','上海市','松江区','310117','1','人工录入',1,'2026-11-10 09:15:00','2026-11-20 16:30:00',NULL,'0','0','admin','2026-11-10 09:15:00','admin','2026-11-20 16:30:00',NULL);
/*!40000 ALTER TABLE `srm_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_supplier_change_record`
--

DROP TABLE IF EXISTS `srm_supplier_change_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_supplier_change_record` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `change_item` varchar(120) COLLATE utf8mb4_general_ci NOT NULL COMMENT '变更事项（822）',
  `before_value` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '变更前',
  `after_value` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '变更后',
  `after_hash` char(32) COLLATE utf8mb4_general_ci GENERATED ALWAYS AS (md5(ifnull(`after_value`,_utf8mb4''))) STORED COMMENT '变更后值 md5，把长文本纳入唯一键以避开索引 3072 字节上限',
  `change_date` date DEFAULT NULL COMMENT '变更时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间（接口值）',
  `raw_id` bigint unsigned DEFAULT NULL,
  `fetched_at` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_change` (`supplier_id`,`change_item`,`change_date`,`after_hash`),
  KEY `idx_change_date` (`change_date`)
) ENGINE=InnoDB AUTO_INCREMENT=1016 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='工商变更记录（822，0.15/次）。采购方确认是否纳入套餐；未纳入时本表留空，工商基本信息变更比对退化为快照差值';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_supplier_change_record`
--

LOCK TABLES `srm_supplier_change_record` WRITE;
/*!40000 ALTER TABLE `srm_supplier_change_record` DISABLE KEYS */;
INSERT INTO `srm_supplier_change_record` (`id`, `supplier_id`, `change_item`, `before_value`, `after_value`, `change_date`, `create_date`, `raw_id`, `fetched_at`, `create_time`) VALUES (1001,1001,'法定代表人','陈建国','陈国华','2015-01-20','2015-01-20 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1002,1001,'注册资本','5000万元人民币','10000万元人民币','2016-09-01','2016-09-01 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1003,1005,'企业名称','上海鑫达化工材料有限公司','鑫达新材料科技有限公司','2019-06-12','2019-06-12 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1004,1009,'企业名称','上海联志电子有限公司','联志电子科技有限公司','2018-01-09','2018-01-09 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1005,1002,'注册资本','3000万元人民币','5000万元人民币','2018-03-11','2018-03-11 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1006,1008,'股东','王建军 100%','王建军 65%、华晟智能装备制造有限公司 35%','2016-07-04','2016-07-04 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1007,1004,'经营范围','供应链管理，仓储服务','供应链管理服务，国内贸易代理，仓储服务，普通货物运输代理','2019-05-23','2019-05-23 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1008,1013,'注册资本','2000万元人民币','3500万元人民币','2019-08-06','2019-08-06 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1009,1014,'经营范围','机械配件加工、销售','精密机械零部件制造，数控机床加工，模具设计与制造','2020-12-16','2020-12-16 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1010,1015,'投资人','许文娟 100%','许文娟 70%、瑞昇信息技术有限公司 30%','2022-06-08','2022-06-08 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1011,1016,'企业名称','上海瑞泽投资有限公司','瑞泽实业集团有限公司','2016-11-02','2016-11-02 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1012,1016,'股东','沈文彬 100%','沈文彬 55%、华晟控股集团有限公司 25%、上海瑞泽投资中心(有限合伙) 20%','2016-11-02','2016-11-02 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1013,1021,'投资人','罗伯涛 60%、沈文彬 40%','瑞泽实业集团有限公司 40%、恒信供应链管理有限公司 35%、沈文彬 25%','2019-06-24','2019-06-24 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1014,1018,'注册资本','1000万元人民币','3000万元人民币','2021-05-11','2021-05-11 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35'),(1015,1022,'企业名称','上海华辰自动化设备有限公司','华辰智造科技有限公司','2016-08-10','2016-08-10 00:00:00',NULL,'2026-11-18 10:45:00','2026-11-25 22:05:35');
/*!40000 ALTER TABLE `srm_supplier_change_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_supplier_contact`
--

DROP TABLE IF EXISTS `srm_supplier_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_supplier_contact` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `contact_type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型：phone 电话 / email 邮箱 / url 网址 / address 注册地址',
  `contact_value` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '规范化后的联系值（页面11：电话、地址、人员需规范化）',
  `raw_value` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接口原值（未规范化，供追溯）',
  `phone_source` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话来源（818 电话来源 / 1046 号码来源），非电话类为空',
  `phone_label` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话标签：正常电话/客服电话/大陆座机/空号（818）；1046 为标签',
  `is_primary` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否主用值：主档 phone/email 取本字段=1 的第一条',
  `source_api_id` int DEFAULT NULL COMMENT '818 / 1046',
  `raw_id` bigint unsigned DEFAULT NULL,
  `fetched_at` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_type_value` (`supplier_id`,`contact_type`,`contact_value`),
  KEY `idx_value` (`contact_type`,`contact_value`) COMMENT '同电话/同邮箱线索反查用，必须走索引'
) ENGINE=InnoDB AUTO_INCREMENT=1050 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='联系方式明细（818 企业联系方式与 1046 企业联系方式分列展示，818/819 独有字段各自标注来源不合并）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_supplier_contact`
--

LOCK TABLES `srm_supplier_contact` WRITE;
/*!40000 ALTER TABLE `srm_supplier_contact` DISABLE KEYS */;
INSERT INTO `srm_supplier_contact` VALUES (1001,1001,'phone','021-58881001','021-58881001','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1002,1002,'phone','021-58881002','021-58881002','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1003,1003,'phone','021-58881003','021-58881003','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1004,1004,'phone','021-58881004','021-58881004','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1005,1005,'phone','021-58881005','021-58881005','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1006,1006,'phone','021-58881004','021-58881004','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1007,1007,'phone','021-58881007','021-58881007','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1008,1008,'phone','021-58881008','021-58881008','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1009,1009,'phone','021-58881009','021-58881009','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1010,1010,'phone','021-58881008','021-58881008','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1011,1011,'phone','021-58881011','021-58881011','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1012,1012,'phone','021-58881234','021-58881234','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1013,1013,'phone','021-58881001','021-58881001','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:34'),(1014,1014,'phone','021-58881003','021-58881003','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1015,1015,'phone','021-58881011','021-58881011','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1016,1016,'phone','021-66881016','021-66881016','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1017,1017,'phone','021-66881017','021-66881017','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1018,1018,'phone','021-66881018','021-66881018','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1019,1019,'phone','021-66881016','021-66881016','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1020,1020,'phone','021-66881016','021-66881016','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1021,1021,'phone','021-66881021','021-66881021','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1022,1022,'phone','021-58881022','021-58881022','工商登记','正常电话','1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1023,1001,'email','contact@huasheng-group.example','contact@huasheng-group.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1024,1002,'email','jz@huasheng-group.example','jz@huasheng-group.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1025,1003,'email','zn@huasheng-group.example','zn@huasheng-group.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1026,1004,'email','service@hengxin.example','service@hengxin.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1027,1005,'email','xd@xinda.example','xd@xinda.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1028,1006,'email','service@hengxin.example','service@hengxin.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1029,1007,'email','ht@haitong.example','ht@haitong.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1030,1008,'email','zq@zhongqi.example','zq@zhongqi.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1031,1009,'email','lz@lianzhi.example','lz@lianzhi.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1032,1010,'email','gs@guangsheng.example','gs@guangsheng.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1033,1011,'email','lz@lianzhi.example','lz@lianzhi.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1034,1012,'email','tc@tiancheng.example','tc@tiancheng.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1035,1013,'email','lw@lanwan-ocean.example','lw@lanwan-ocean.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1036,1014,'email','ky@kaiyue-precision.example','ky@kaiyue-precision.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1037,1015,'email','yt@yuntong-data.example','yt@yuntong-data.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1038,1016,'email','contact@ruize-group.example','contact@ruize-group.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1039,1017,'email','eng@ruize-group.example','eng@ruize-group.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1040,1018,'email','xny@ruize-group.example','xny@ruize-group.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1041,1019,'email','contact@ruize-group.example','contact@ruize-group.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1042,1020,'email','hr@ruize-group.example','hr@ruize-group.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1043,1021,'email','storage@zhongtai-wh.example','storage@zhongtai-wh.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1044,1022,'email','contact@huachen-smart.example','contact@huachen-smart.example',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1045,1004,'address','上海市徐汇区宜山路500号','上海市徐汇区宜山路500号',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1046,1006,'address','上海市徐汇区宜山路500号','上海市徐汇区宜山路500号',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1047,1016,'address','上海市浦东新区金港路588号','上海市浦东新区金港路588号',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1048,1017,'address','上海市浦东新区金港路588号','上海市浦东新区金港路588号',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35'),(1049,1019,'address','上海市浦东新区金港路588号','上海市浦东新区金港路588号',NULL,NULL,'1',818,NULL,'2026-11-18 10:25:00','2026-11-25 22:05:35');
/*!40000 ALTER TABLE `srm_supplier_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_supplier_ext_field`
--

DROP TABLE IF EXISTS `srm_supplier_ext_field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_supplier_ext_field` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `field_code` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '内部字段标识（页面11 内部稳定标识，按内部字段标识规范维护）',
  `field_value` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '自定义字段值（页面11 样例：银行账户标识（自定义））',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_field` (`supplier_id`,`field_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1015 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='主档自定义字段（EAV）。页面11 新增自定义业务字段时不改主档表结构，映射与校验走字段映射表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_supplier_ext_field`
--

LOCK TABLES `srm_supplier_ext_field` WRITE;
/*!40000 ALTER TABLE `srm_supplier_ext_field` DISABLE KEYS */;
INSERT INTO `srm_supplier_ext_field` VALUES (1001,1001,'bank_account_flag','基本户已开立(工行上海分行)','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1002,1001,'internal_grade','A','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1003,1002,'safety_license','沪建安许(2019)第0012号','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1004,1004,'internal_grade','A','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1005,1006,'transport_license','沪交运许字第2018006号','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1006,1008,'env_qualification','环境污染治理设施运营乙级','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1007,1013,'safety_license','沪港航施许(2020)第0033号','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1008,1014,'iso_cert','ISO9001:2015 质量管理体系认证','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1009,1015,'internal_grade','B','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1010,1016,'internal_grade','A','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1011,1018,'iso_cert','ISO14001:2015 环境管理体系认证','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1012,1021,'warehouse_cert','智能仓储三级达标证书(2024)','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1013,1019,'customs_code','海关注册编码 3102910188','admin','2026-11-12 09:00:00','2026-11-12 09:00:00'),(1014,1022,'internal_grade','A','admin','2026-11-12 09:00:00','2026-11-12 09:00:00');
/*!40000 ALTER TABLE `srm_supplier_ext_field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_supplier_former_name`
--

DROP TABLE IF EXISTS `srm_supplier_former_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_supplier_former_name` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `former_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '曾用名（458）',
  `start_time` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '开始时间 yyyy-MM（458）',
  `end_time` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结束时间 yyyy-MM（458）',
  `raw_id` bigint unsigned DEFAULT NULL,
  `fetched_at` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_former` (`supplier_id`,`former_name`,`start_time`),
  KEY `idx_former` (`former_name`) COMMENT '曾用名参与本地主体归一与线索反查'
) ENGINE=InnoDB AUTO_INCREMENT=1010 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='曾用名时间轴（458，0.10/次）：页面3 展示、页面8 名称变更比对基线';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_supplier_former_name`
--

LOCK TABLES `srm_supplier_former_name` WRITE;
/*!40000 ALTER TABLE `srm_supplier_former_name` DISABLE KEYS */;
INSERT INTO `srm_supplier_former_name` VALUES (1001,1005,'上海鑫达化工材料有限公司','2017-07','2019-06',NULL,'2026-11-18 10:40:00','2026-11-25 22:05:35'),(1002,1009,'上海联志电子有限公司','2015-04','2018-01',NULL,'2026-11-18 10:40:00','2026-11-25 22:05:35'),(1003,1004,'恒信贸易(上海)有限公司','2016-01','2017-12',NULL,'2026-11-18 10:40:00','2026-11-25 22:05:35'),(1004,1013,'上海蓝湾港航工程有限公司','2016-05','2019-07',NULL,'2026-11-18 10:40:00','2026-11-25 22:05:35'),(1005,1014,'上海凯越机械配件厂','2018-09','2020-11',NULL,'2026-11-18 10:40:00','2026-11-25 22:05:35'),(1006,1019,'上海泽信商贸有限公司','2019-01','2021-06',NULL,'2026-11-18 10:40:00','2026-11-25 22:05:35'),(1007,1021,'上海中泰仓储设备有限公司','2015-10','2018-04',NULL,'2026-11-18 10:40:00','2026-11-25 22:05:35'),(1008,1022,'上海华辰自动化设备有限公司','2013-04','2016-08',NULL,'2026-11-18 10:40:00','2026-11-25 22:05:35'),(1009,1001,'上海华晟投资控股有限公司','2006-03','2010-06',NULL,'2026-11-18 10:40:00','2026-11-25 22:05:35');
/*!40000 ALTER TABLE `srm_supplier_former_name` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_supplier_industry_info`
--

DROP TABLE IF EXISTS `srm_supplier_industry_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_supplier_industry_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL COMMENT '主档 id',
  `tyc_company_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业id',
  `ent_name` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业名（接口原文名，可能与主档名不同，用于曾用名/别名核对）',
  `ent_type` varchar(80) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业类型（818；1047 企业类型可覆盖）',
  `org_type` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织机构：机构类型/一级机构类型/二级机构类型/经济类型（1168）',
  `scope` text COLLATE utf8mb4_general_ci COMMENT '经营范围（818）',
  `reg_org` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登记机关（818）',
  `approve_date` date DEFAULT NULL COMMENT '核准时间（818）',
  `social_credit_code` varchar(18) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '统一社会信用代码（接口返回值，与主档不一致时以主档为准并记差异）',
  `taxpayer_no` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '纳税人识别号（818）',
  `org_code` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织机构代码（818）',
  `eng_name` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '英文名（818）',
  `short_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '简称（818）',
  `from_time` date DEFAULT NULL COMMENT '经营开始时间（818）',
  `to_time` date DEFAULT NULL COMMENT '经营结束时间（818）',
  `cancel_date` date DEFAULT NULL COMMENT '注销日期（818）',
  `cancel_reason` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注销原因（818）',
  `revoke_date` date DEFAULT NULL COMMENT '吊销日期（818）',
  `revoke_reason` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '吊销原因（818）',
  `reg_capital` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册资本',
  `reg_capita_currency` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册资本币种（人民币/美元/欧元等）',
  `actual_capital` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '实收注册资金',
  `staff_num` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '人员规模（818）',
  `social_person_num` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参保人数（818）',
  `is_micro` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否小微企业：0 不是 / 1 是（818）',
  `ent_label` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业标签（818）',
  `regression_up` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '规上企业（818）',
  `econ_zone_1` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '经济功能区1（818）',
  `econ_zone_2` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '经济功能区2（818）',
  `industry_gate` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国民经济行业分类门类',
  `industry_big` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国民经济行业分类大类',
  `industry_mid` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国民经济行业分类中类',
  `industry_small` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国民经济行业分类小类',
  `industry_gate_code` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '门类行业代码',
  `industry_big_code` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '大类行业代码',
  `industry_mid_code` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '中类行业代码',
  `industry_small_code` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '小类行业代码',
  `stock_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '股票名（818）',
  `stock_code` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '股票号（818）',
  `stock_type` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '股票类型（818）',
  `stock_used_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '股票曾用名（818）',
  `commercial_no` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商业登记号（818）',
  `ent_score` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业评分：外部字段原样留存，本期不作内部评分展示（页面3 表 3-6 口径）',
  `update_from` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接口更新时间（818 更新时间）',
  `source_api_id` int DEFAULT NULL COMMENT '来源接口：818 / 819 / 1047 / 1168',
  `raw_id` bigint unsigned DEFAULT NULL COMMENT '原始响应留痕 srm_api_raw.id',
  `fetched_at` datetime DEFAULT NULL COMMENT '取数时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_supplier_api` (`supplier_id`,`source_api_id`),
  KEY `idx_raw` (`raw_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1023 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='工商基础信息沉淀（页面3 工商基础信息卡）。一企业一行/接口，字段级重复覆盖写；不接入项（行政处罚/股权出质/动产抵押）不留列';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_supplier_industry_info`
--

LOCK TABLES `srm_supplier_industry_info` WRITE;
/*!40000 ALTER TABLE `srm_supplier_industry_info` DISABLE KEYS */;
INSERT INTO `srm_supplier_industry_info` VALUES (1001,1001,'T1001','华晟控股集团有限公司','有限责任公司(自然人投资或控股)',NULL,'投资管理，实业投资，企业管理咨询，商务信息咨询。','上海市浦东新区市场监督管理局','2015-01-20','91310115MA1K10001A','91310115MA1K10001A','MA1K10001','HS HOLDING GROUP CO., LTD.','华晟控股','2005-03-18',NULL,NULL,NULL,NULL,NULL,'10000万元人民币','人民币','10000万元人民币','120人','66人',NULL,NULL,NULL,NULL,NULL,NULL,'商务服务业',NULL,NULL,NULL,'L7201',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1002,1002,'T1002','华晟建筑工程有限公司','有限责任公司(非自然人投资或控股的法人独资)',NULL,'房屋建筑工程施工，市政公用工程施工，建筑劳务分包，建筑材料销售。','上海市浦东新区市场监督管理局','2018-03-11','91310115MA1K10002B','91310115MA1K10002B','MA1K10002','HS CONSTRUCTION ENGINEERING CO., LTD.','华晟建筑','2010-06-01',NULL,NULL,NULL,NULL,NULL,'5000万元人民币','人民币','5000万元人民币','320人','286人',NULL,NULL,NULL,NULL,NULL,NULL,'房屋建筑业',NULL,NULL,NULL,'E4700',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1003,1003,'T1003','华晟智能装备制造有限公司','有限责任公司(自然人投资或控股)',NULL,'智能装备研发、制造与销售，工业机器人系统集成，自动化技术服务。','上海市金山区市场监督管理局','2016-07-04','91310116MA1K10003C','91310116MA1K10003C','MA1K10003','HS INTELLIGENT EQUIPMENT CO., LTD.','华晟智能','2014-09-25',NULL,NULL,NULL,NULL,NULL,'3000万元人民币','人民币','3000万元人民币','180人','142人',NULL,NULL,NULL,NULL,NULL,NULL,'专用设备制造业',NULL,NULL,NULL,'C3500',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1004,1004,'T1004','恒信供应链管理有限公司','有限责任公司(自然人投资或控股)',NULL,'供应链管理服务，国内贸易代理，仓储服务，普通货物运输代理。','上海市徐汇区市场监督管理局','2019-05-23','91310104MA1K10004D','91310104MA1K10004D','MA1K10004','HENGXIN SUPPLY CHAIN CO., LTD.','恒信供应链','2016-01-11',NULL,NULL,NULL,NULL,NULL,'2000万元人民币','人民币','2000万元人民币','95人','71人',NULL,NULL,NULL,NULL,NULL,NULL,'商务服务业',NULL,NULL,NULL,'L7202',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1005,1005,'T1005','鑫达新材料科技有限公司','有限责任公司(自然人投资或控股)',NULL,'高性能纤维及复合材料研发、生产与销售，技术开发与咨询。','上海市松江区市场监督管理局','2020-09-08','91310117MA1K10005E','91310117MA1K10005E','MA1K10005','XINDA NEW MATERIALS CO., LTD.','鑫达新材','2017-07-20',NULL,NULL,NULL,NULL,NULL,'1500万元人民币','人民币','1500万元人民币','76人','52人',NULL,NULL,NULL,NULL,NULL,NULL,'化学原料和化学制品制造业',NULL,NULL,NULL,'C2600',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1006,1006,'T1006','鸿远物流服务有限公司','有限责任公司(自然人投资或控股)',NULL,'道路货物运输，物流配送，货运代理，仓储服务。','上海市长宁区市场监督管理局','2021-01-14','91310105MA1K10006F','91310105MA1K10006F','MA1K10006','HONGYUAN LOGISTICS CO., LTD.','鸿远物流','2018-03-06',NULL,NULL,NULL,NULL,NULL,'800万元人民币','人民币','800万元人民币','64人','38人',NULL,NULL,NULL,NULL,NULL,NULL,'道路运输业',NULL,NULL,NULL,'G5400',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1007,1007,'T1007','海通机电设备有限公司','有限责任公司(自然人投资或控股)',NULL,'机电设备、五金交电批发零售，设备安装与维修，技术服务。','上海市宝山区市场监督管理局','2017-09-21','91310113MA1K10007G','91310113MA1K10007G','MA1K10007','HAITONG ELECTROMECHANICAL CO., LTD.','海通机电','2013-05-16',NULL,NULL,NULL,NULL,NULL,'1200万元人民币','人民币','1200万元人民币','48人','31人',NULL,NULL,NULL,NULL,NULL,NULL,'批发业',NULL,NULL,NULL,'F5100',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1008,1008,'T1008','中启环保工程有限公司','有限责任公司(自然人投资或控股)',NULL,'环保工程施工，污染治理设施运营，环境监测与技术服务。','上海市青浦区市场监督管理局','2015-04-27','91310118MA1K10008H','91310118MA1K10008H','MA1K10008','ZHONGQI ENVIRONMENTAL ENGINEERING CO., LTD.','中启环保','2012-11-08',NULL,NULL,NULL,NULL,NULL,'2600万元人民币','人民币','2600万元人民币','130人','108人',NULL,NULL,NULL,NULL,NULL,NULL,'生态保护和环境治理业',NULL,NULL,NULL,'N7700',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1009,1009,'T1009','联志电子科技有限公司','有限责任公司(自然人投资或控股)',NULL,'电子元器件研发与销售，通信设备制造，软件开发。','上海市嘉定区市场监督管理局','2018-02-05','91310114MA1K10009J','91310114MA1K10009J','MA1K10009','LIANZHI ELECTRONIC TECHNOLOGY CO., LTD.','联志电子','2015-04-02',NULL,NULL,NULL,NULL,NULL,'1800万元人民币','人民币','1800万元人民币','150人','118人',NULL,NULL,NULL,NULL,NULL,NULL,'计算机、通信和其他电子设备制造业',NULL,NULL,NULL,'C3900',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1010,1010,'T1010','广盛建设劳务有限公司','有限责任公司(自然人独资)',NULL,'建筑劳务分包，建筑装饰装修工程，模板脚手架工程。','上海市奉贤区市场监督管理局','2021-11-30','91310120MA1K10010K','91310120MA1K10010K','MA1K10010','GUANGSHENG CONSTRUCTION LABOR CO., LTD.','广盛劳务','2019-08-19',NULL,NULL,NULL,NULL,NULL,'600万元人民币','人民币','600万元人民币','52人','44人',NULL,NULL,NULL,NULL,NULL,NULL,'建筑安装业',NULL,NULL,NULL,'E4900',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1011,1011,'T1011','瑞昇信息技术有限公司','有限责任公司(自然人投资或控股)',NULL,'软件开发，信息系统集成服务，数据处理服务，技术咨询。','上海市虹口区市场监督管理局','2022-03-09','91310109MA1K10011L','91310109MA1K10011L','MA1K10011','RUISHENG INFORMATION TECHNOLOGY CO., LTD.','瑞昇信息','2020-02-27',NULL,NULL,NULL,NULL,NULL,'1000万元人民币','人民币','1000万元人民币','60人','47人',NULL,NULL,NULL,NULL,NULL,NULL,'软件和信息技术服务业',NULL,NULL,NULL,'I6500',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1012,1012,'T1012','天成检测认证有限公司','有限责任公司(自然人投资或控股)',NULL,'产品检测服务，认证服务，计量技术服务，技术咨询。','上海市杨浦区市场监督管理局','2022-08-16','91310110MA1K10012M','91310110MA1K10012M','MA1K10012','TIANCHENG TESTING AND CERTIFICATION CO., LTD.','天成检测','2021-06-15',NULL,NULL,NULL,NULL,NULL,'500万元人民币','人民币','500万元人民币','35人','29人',NULL,NULL,NULL,NULL,NULL,NULL,'专业技术服务业',NULL,NULL,NULL,'M7400',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1013,1013,'T1013','蓝湾海洋工程有限公司','有限责任公司(自然人投资或控股)',NULL,'海洋工程建筑，港口与航道工程施工，水下工程施工，海洋工程技术服务。','上海市浦东新区市场监督管理局','2019-08-06','91310115MA1K10013N','91310115MA1K10013N','MA1K10013','LANWAN MARINE ENGINEERING CO., LTD.','蓝湾海工','2016-05-12',NULL,NULL,NULL,NULL,NULL,'3500万元人民币','人民币','3500万元人民币','210人','168人',NULL,NULL,NULL,NULL,NULL,NULL,'土木工程建筑业',NULL,NULL,NULL,'E4800',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1014,1014,'T1014','凯越精密机械有限公司','有限责任公司(自然人投资或控股)',NULL,'精密机械零部件制造，数控机床加工，模具设计与制造，机械设备维修。','上海市金山区市场监督管理局','2020-12-16','91310116MA1K10014P','91310116MA1K10014P','MA1K10014','KAIYUE PRECISION MACHINERY CO., LTD.','凯越精密','2018-09-03',NULL,NULL,NULL,NULL,NULL,'2200万元人民币','人民币','2200万元人民币','118人','93人',NULL,NULL,NULL,NULL,NULL,NULL,'通用设备制造业',NULL,NULL,NULL,'C3400',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1015,1015,'T1015','云通数据科技有限公司','有限责任公司(自然人投资或控股)',NULL,'软件开发，数据处理和存储服务，云计算装备技术服务，信息系统运行维护服务。','上海市虹口区市场监督管理局','2022-04-11','91310104MA1K10015Q','91310104MA1K10015Q','MA1K10015','YUNTONG DATA TECHNOLOGY CO., LTD.','云通数据','2021-11-22',NULL,NULL,NULL,NULL,NULL,'900万元人民币','人民币','900万元人民币','46人','39人',NULL,NULL,NULL,NULL,NULL,NULL,'软件和信息技术服务业',NULL,NULL,NULL,'I6510',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1016,1016,'T1016','瑞泽实业集团有限公司','有限责任公司(自然人投资或控股)',NULL,'实业投资，投资管理，企业管理咨询，国内贸易，工程技术咨询服务。','上海市浦东新区市场监督管理局','2016-11-02','91310115MA1K10016R','91310115MA1K10016R','MA1K10016','RUIZE INDUSTRIAL GROUP CO., LTD.','瑞泽实业','2008-04-16',NULL,NULL,NULL,NULL,NULL,'8000万元人民币','人民币','8000万元人民币','210人','156人',NULL,NULL,NULL,NULL,NULL,NULL,'商务服务业',NULL,NULL,NULL,'L7201',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1017,1017,'T1017','瑞泽建筑工程有限公司','有限责任公司(自然人投资或控股的法人独资)',NULL,'房屋建筑工程施工，地基与基础工程，建筑装修装饰工程，建筑材料销售。','上海市浦东新区市场监督管理局','2020-06-18','91310115MA1K10017S','91310115MA1K10017S','MA1K10017','RUIZE CONSTRUCTION ENGINEERING CO., LTD.','瑞泽建筑','2012-07-10',NULL,NULL,NULL,NULL,NULL,'4000万元人民币','人民币','4000万元人民币','260人','221人',NULL,NULL,NULL,NULL,NULL,NULL,'房屋建筑业',NULL,NULL,NULL,'E4700',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1018,1018,'T1018','瑞泽新能源科技有限公司','有限责任公司(自然人投资或控股)',NULL,'光伏设备及元器件制造，储能技术服务，新能源技术研发与转让。','上海市松江区市场监督管理局','2021-05-11','91310117MA1K10018T','91310117MA1K10018T','MA1K10018','RUIZE NEW ENERGY TECHNOLOGY CO., LTD.','瑞泽新能源','2016-03-24',NULL,NULL,NULL,NULL,NULL,'3000万元人民币','人民币','3000万元人民币','165人','132人',NULL,NULL,NULL,NULL,NULL,NULL,'电气机械和器材制造业',NULL,NULL,NULL,'C3800',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1019,1019,'T1019','上海泽信国际贸易有限公司','有限责任公司(自然人投资或控股)',NULL,'货物进出口，技术进出口，国内贸易代理，转口贸易。','上海市浦东新区市场监督管理局','2021-07-26','91310105MA1K10019U','91310105MA1K10019U','MA1K10019','SHANGHAI ZEXIN INTERNATIONAL TRADE CO., LTD.','泽信国贸','2019-01-08',NULL,NULL,NULL,NULL,NULL,'1200万元人民币','人民币','1200万元人民币','42人','33人',NULL,NULL,NULL,NULL,NULL,NULL,'零售业',NULL,NULL,NULL,'F5200',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1020,1020,'T1020','瑞泽人力资源有限公司','有限责任公司(自然人投资或控股)',NULL,'人力资源服务，劳务分包，企业管理咨询，职业技能培训。','上海市杨浦区市场监督管理局','2022-09-14','91310110MA1K10020V','91310110MA1K10020V','MA1K10020','RUIZE HUMAN RESOURCES CO., LTD.','瑞泽人力','2020-05-19',NULL,NULL,NULL,NULL,NULL,'600万元人民币','人民币','600万元人民币','28人','22人',NULL,NULL,NULL,NULL,NULL,NULL,'人力资源服务',NULL,NULL,NULL,'O8090',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1021,1021,'T1021','中泰智能仓储有限公司','有限责任公司(自然人投资或控股)',NULL,'智能仓储服务，供应链管理，普通货运代理，仓储设备制造与销售。','上海市嘉定区市场监督管理局','2019-03-21','91310114MA1K10021W','91310114MA1K10021W','MA1K10021','ZHONGTAI INTELLIGENT WAREHOUSING CO., LTD.','中泰智仓','2015-10-30',NULL,NULL,NULL,NULL,NULL,'2500万元人民币','人民币','2500万元人民币','98人','76人',NULL,NULL,NULL,NULL,NULL,NULL,'仓储业',NULL,NULL,NULL,'G5900',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00'),(1022,1022,'T1022','华辰智造科技有限公司','有限责任公司(自然人投资或控股)',NULL,'智能控制系统集成，工业机器人销售，软件开发，自动化技术研发与技术服务，信息系统集成服务。','上海市松江区市场监督管理局','2019-03-15','91310117MA1K10022X','91310117MA1K10022X','MA1K10022','HUACHEN INTELLIGENT MANUFACTURING CO., LTD.','华辰智造','2013-04-28',NULL,NULL,NULL,NULL,NULL,'5000万元人民币','人民币','5000万元人民币','135人','102人',NULL,NULL,NULL,NULL,NULL,NULL,'软件和信息技术服务业',NULL,NULL,NULL,'I6510',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,818,NULL,'2026-11-18 10:20:00','2026-11-18 10:20:00','2026-11-18 10:20:00');
/*!40000 ALTER TABLE `srm_supplier_industry_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_supplier_person`
--

DROP TABLE IF EXISTS `srm_supplier_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_supplier_person` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `person_name` varchar(60) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主要人员名称（819 主要人员名称 / 820 人员）',
  `person_type` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '1 公司 / 2 人（819 主要人员列表 1-公司 2-人）',
  `tyc_person_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '人或公司id（819）',
  `hcgid` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '人员hcgid（820）：自然人跨企业聚合标识，幕后关系比对的常用主键',
  `position` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '职位',
  `logo` varchar(300) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'logo',
  `sort_no` int DEFAULT NULL COMMENT '接口返回顺序（总数由 srm_api_raw 计数，不单独存）',
  `source_api_id` int DEFAULT NULL COMMENT '819 / 820',
  `raw_id` bigint unsigned DEFAULT NULL,
  `fetched_at` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_person` (`supplier_id`,`person_name`,`position`),
  KEY `idx_hcgid` (`hcgid`),
  KEY `idx_name` (`person_name`) COMMENT '同自然人反查（本地关联线索用）'
) ENGINE=InnoDB AUTO_INCREMENT=1039 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='主要人员（页面3 主要人员卡，819 与 820 各自入表，按 source_api_id 区分）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_supplier_person`
--

LOCK TABLES `srm_supplier_person` WRITE;
/*!40000 ALTER TABLE `srm_supplier_person` DISABLE KEYS */;
INSERT INTO `srm_supplier_person` VALUES (1001,1001,'陈国华','2','P2001','HCG-P2001','执行董事兼总经理',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1002,1002,'李明远','2','P2002','HCG-P2002','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1003,1002,'周海峰','2','P2003','HCG-P2003','监事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1004,1003,'周海峰','2','P2003','HCG-P2003','执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1005,1003,'孙立群','2','P2004','HCG-P2004','监事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1006,1004,'刘志强','2','P2005','HCG-P2005','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1007,1006,'刘志强','2','P2005','HCG-P2005','执行董事兼总经理',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1008,1004,'赵文博','2','P2006','HCG-P2006','监事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1009,1007,'赵文博','2','P2006','HCG-P2006','执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1010,1005,'孙立群','2','P2004','HCG-P2004','执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1011,1008,'王建军','2','P2007','HCG-P2007','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1012,1010,'王建军','2','P2007','HCG-P2007','执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1013,1009,'郑晓岚','2','P2008','HCG-P2008','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1014,1011,'郑晓岚','2','P2008','HCG-P2008','执行董事兼总经理',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1015,1012,'许文娟','2','P2009','HCG-P2009','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1016,1001,'徐海燕','2','P2010','HCG-P2010','监事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1017,1013,'顾伟明','2','P2011','HCG-P2011','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1018,1013,'周海峰','2','P2003','HCG-P2003','监事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1019,1014,'何静','2','P2012','HCG-P2012','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1020,1014,'孙立群','2','P2004','HCG-P2004','监事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1021,1015,'许文娟','2','P2009','HCG-P2009','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1022,1015,'郑晓岚','2','P2008','HCG-P2008','监事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1023,1016,'沈文彬','2','P2013','HCG-P2013','法定代表人兼董事长',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1024,1016,'潘莉','2','P2014','HCG-P2014','总经理',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1025,1016,'顾伟明','2','P2011','HCG-P2011','独立董事',NULL,3,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1026,1017,'潘莉','2','P2014','HCG-P2014','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1027,1017,'沈文彬','2','P2013','HCG-P2013','监事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1028,1018,'沈文彬','2','P2013','HCG-P2013','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1029,1018,'罗伯涛','2','P2015','HCG-P2015','监事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1030,1019,'沈文彬','2','P2013','HCG-P2013','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1031,1020,'潘莉','2','P2014','HCG-P2014','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1032,1020,'徐海燕','2','P2010','HCG-P2010','监事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1033,1021,'罗伯涛','2','P2015','HCG-P2015','法定代表人兼经理',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1034,1021,'沈文彬','2','P2013','HCG-P2013','副董事长',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1035,1021,'刘志强','2','P2005','HCG-P2005','董事',NULL,3,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1036,1022,'陈建国','2','P2016','HCG-P2016','法定代表人兼执行董事',NULL,1,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1037,1022,'陈国华','2','P2001','HCG-P2001','董事',NULL,2,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35'),(1038,1022,'林薇','2','P2017','HCG-P2017','监事',NULL,3,819,NULL,'2026-11-18 10:30:00','2026-11-25 22:05:35');
/*!40000 ALTER TABLE `srm_supplier_person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_supplier_shareholder`
--

DROP TABLE IF EXISTS `srm_supplier_shareholder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_supplier_shareholder` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint unsigned NOT NULL,
  `shareholder_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '股东名（821）',
  `shareholder_type` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '股东类型：1 公司 / 2 人 / 3 其它（821）',
  `short_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '简称（821）',
  `tyc_ref_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '对应表id（821）',
  `hcgid` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '人员hcgid（821）',
  `ratio` decimal(9,4) DEFAULT NULL COMMENT '占比（821，小数表示百分比）',
  `hold_ratio` decimal(9,4) DEFAULT NULL COMMENT '持股占比（821，与占比分列，页面10 比例区间用）',
  `hold_num` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '持股数量（821）',
  `sub_amount` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '认缴出资金额（821）',
  `sub_date` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '认缴出资金额对应出资时间 / 认缴方式',
  `sub_way` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '认缴方式（821）',
  `paid_amount` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '实缴出资金额（821）',
  `paid_date` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '实缴出资时间（821）',
  `paid_way` varchar(60) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '实缴方式（821）',
  `first_hold_date` varchar(40) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '首次持股日期（821）',
  `is_hk_enterprise` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '香港企业（821）',
  `data_from` char(1) COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '数据来源枚举：1 工商登记 / 2 最新公示（821 入参，不填默认工商登记）',
  `source_api_id` int DEFAULT '821',
  `raw_id` bigint unsigned DEFAULT NULL,
  `fetched_at` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sup_sh` (`supplier_id`,`shareholder_name`,`data_from`),
  KEY `idx_sh_name` (`shareholder_name`),
  KEY `idx_hcgid` (`hcgid`)
) ENGINE=InnoDB AUTO_INCREMENT=1044 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='企业股东（821）：页面5 向上穿透起点、页面8 股东及股权变更比对的字段来源';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_supplier_shareholder`
--

LOCK TABLES `srm_supplier_shareholder` WRITE;
/*!40000 ALTER TABLE `srm_supplier_shareholder` DISABLE KEYS */;
INSERT INTO `srm_supplier_shareholder` VALUES (1001,1001,'陈国华','2',NULL,'T1001S1','HCG-P2001',0.6000,0.6000,NULL,'6000万元','2005-03-18','货币','6000万元','2005-03-18',NULL,'2005-03-18',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1002,1001,'上海华晟投资管理合伙企业(有限合伙)','1','华晟合伙','T1001S2',NULL,0.2500,0.2500,NULL,'2500万元','2016-09-01','货币','2500万元','2016-09-01',NULL,'2016-09-01',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1003,1001,'李明远','2',NULL,'T1001S3','HCG-P2002',0.1500,0.1500,NULL,'1500万元','2010-06-01','货币','1500万元','2010-06-01',NULL,'2010-06-01',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1004,1002,'华晟控股集团有限公司','1','华晟控股','T1002S1',NULL,1.0000,1.0000,NULL,'5000万元','2010-06-01','货币','5000万元','2010-06-01',NULL,'2010-06-01',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1005,1003,'华晟控股集团有限公司','1','华晟控股','T1003S1',NULL,0.7000,0.7000,NULL,'2100万元','2014-09-25','货币','2100万元','2014-09-25',NULL,'2014-09-25',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1006,1003,'周海峰','2',NULL,'T1003S2','HCG-P2003',0.3000,0.3000,NULL,'900万元','2014-09-25','货币','900万元','2014-09-25',NULL,'2014-09-25',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1007,1004,'刘志强','2',NULL,'T1004S1','HCG-P2005',0.5500,0.5500,NULL,'1100万元','2016-01-11','货币','1100万元','2016-01-11',NULL,'2016-01-11',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1008,1004,'赵文博','2',NULL,'T1004S2','HCG-P2006',0.4500,0.4500,NULL,'900万元','2016-01-11','货币','900万元','2016-01-11',NULL,'2016-01-11',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1009,1005,'孙立群','2',NULL,'T1005S1','HCG-P2004',0.7000,0.7000,NULL,'1050万元','2017-07-20','货币','1050万元','2017-07-20',NULL,'2017-07-20',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1010,1005,'恒信供应链管理有限公司','1','恒信供应链','T1005S2',NULL,0.3000,0.3000,NULL,'450万元','2019-04-10','货币','450万元','2019-04-10',NULL,'2019-04-10',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1011,1006,'刘志强','2',NULL,'T1006S1','HCG-P2005',1.0000,1.0000,NULL,'800万元','2018-03-06','货币','800万元','2018-03-06',NULL,'2018-03-06',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1012,1007,'赵文博','2',NULL,'T1007S1','HCG-P2006',0.6000,0.6000,NULL,'720万元','2013-05-16','货币','720万元','2013-05-16',NULL,'2013-05-16',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1013,1007,'华晟建筑工程有限公司','1','华晟建筑','T1007S2',NULL,0.4000,0.4000,NULL,'480万元','2015-02-09','货币','480万元','2015-02-09',NULL,'2015-02-09',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1014,1008,'王建军','2',NULL,'T1008S1','HCG-P2007',0.6500,0.6500,NULL,'1690万元','2012-11-08','货币','1690万元','2012-11-08',NULL,'2012-11-08',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1015,1008,'华晟智能装备制造有限公司','1','华晟智能','T1008S2',NULL,0.3500,0.3500,NULL,'910万元','2016-07-04','货币','910万元','2016-07-04',NULL,'2016-07-04',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1016,1009,'郑晓岚','2',NULL,'T1009S1','HCG-P2008',0.8000,0.8000,NULL,'1440万元','2015-04-02','货币','1440万元','2015-04-02',NULL,'2015-04-02',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1017,1009,'陈国华','2',NULL,'T1009S2','HCG-P2001',0.2000,0.2000,NULL,'360万元','2017-05-18','货币','360万元','2017-05-18',NULL,'2017-05-18',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1018,1010,'王建军','2',NULL,'T1010S1','HCG-P2007',1.0000,1.0000,NULL,'600万元','2019-08-19','货币','600万元','2019-08-19',NULL,'2019-08-19',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1019,1011,'郑晓岚','2',NULL,'T1011S1','HCG-P2008',0.9000,0.9000,NULL,'900万元','2020-02-27','货币','900万元','2020-02-27',NULL,'2020-02-27',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1020,1011,'联志电子科技有限公司','1','联志电子','T1011S2',NULL,0.1000,0.1000,NULL,'100万元','2021-03-15','货币','100万元','2021-03-15',NULL,'2021-03-15',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1021,1012,'许文娟','2',NULL,'T1012S1','HCG-P2009',1.0000,1.0000,NULL,'500万元','2021-06-15','货币','500万元','2021-06-15',NULL,'2021-06-15',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1022,1013,'华晟控股集团有限公司','1','华晟控股','T1013S1',NULL,0.5500,0.5500,NULL,'1925万元','2016-05-12','货币','1925万元','2016-05-12',NULL,'2016-05-12',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1023,1013,'顾伟明','2',NULL,'T1013S2','HCG-P2011',0.4500,0.4500,NULL,'1575万元','2016-05-12','货币','1575万元','2016-05-12',NULL,'2016-05-12',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1024,1014,'华晟智能装备制造有限公司','1','华晟智能','T1014S1',NULL,0.6000,0.6000,NULL,'1320万元','2018-09-03','货币','1320万元','2018-09-03',NULL,'2018-09-03',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1025,1014,'何静','2',NULL,'T1014S2','HCG-P2012',0.4000,0.4000,NULL,'880万元','2018-09-03','货币','880万元','2018-09-03',NULL,'2018-09-03',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1026,1015,'许文娟','2',NULL,'T1015S1','HCG-P2009',0.7000,0.7000,NULL,'630万元','2021-11-22','货币','630万元','2021-11-22',NULL,'2021-11-22',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1027,1015,'瑞昇信息技术有限公司','1','瑞昇信息','T1015S2',NULL,0.3000,0.3000,NULL,'270万元','2022-06-08','货币','270万元','2022-06-08',NULL,'2022-06-08',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1028,1016,'沈文彬','2',NULL,'T1016S1','HCG-P2013',0.5500,0.5500,NULL,'4400万元','2008-04-16','货币','4400万元','2008-04-16',NULL,'2008-04-16',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1029,1016,'华晟控股集团有限公司','1','华晟控股','T1016S2',NULL,0.2500,0.2500,NULL,'2000万元','2016-11-02','货币','2000万元','2016-11-02',NULL,'2016-11-02',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1030,1016,'上海瑞泽投资中心(有限合伙)','1','瑞泽投资','T1016S3',NULL,0.2000,0.2000,NULL,'1600万元','2008-04-16','货币','1600万元','2008-04-16',NULL,'2008-04-16',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1031,1017,'瑞泽实业集团有限公司','1','瑞泽实业','T1017S1',NULL,1.0000,1.0000,NULL,'4000万元','2012-07-10','货币','4000万元','2012-07-10',NULL,'2012-07-10',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1032,1018,'瑞泽实业集团有限公司','1','瑞泽实业','T1018S1',NULL,0.6000,0.6000,NULL,'1800万元','2016-03-24','货币','1800万元','2016-03-24',NULL,'2016-03-24',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1033,1018,'沈文彬','2',NULL,'T1018S2','HCG-P2013',0.4000,0.4000,NULL,'1200万元','2016-03-24','货币','1200万元','2016-03-24',NULL,'2016-03-24',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1034,1019,'沈文彬','2',NULL,'T1019S1','HCG-P2013',0.7000,0.7000,NULL,'840万元','2019-01-08','货币','840万元','2019-01-08',NULL,'2019-01-08',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1035,1019,'瑞泽实业集团有限公司','1','瑞泽实业','T1019S2',NULL,0.3000,0.3000,NULL,'360万元','2019-01-08','货币','360万元','2019-01-08',NULL,'2019-01-08',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1036,1020,'瑞泽建筑工程有限公司','1','瑞泽建筑','T1020S1',NULL,0.5100,0.5100,NULL,'306万元','2020-05-19','货币','306万元','2020-05-19',NULL,'2020-05-19',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1037,1020,'潘莉','2',NULL,'T1020S2','HCG-P2014',0.4900,0.4900,NULL,'294万元','2020-05-19','货币','294万元','2020-05-19',NULL,'2020-05-19',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1038,1021,'瑞泽实业集团有限公司','1','瑞泽实业','T1021S1',NULL,0.4000,0.4000,NULL,'1000万元','2015-10-30','货币','1000万元','2015-10-30',NULL,'2015-10-30',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1039,1021,'恒信供应链管理有限公司','1','恒信供应链','T1021S2',NULL,0.3500,0.3500,NULL,'875万元','2019-06-24','货币','875万元','2019-06-24',NULL,'2019-06-24',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1040,1021,'沈文彬','2',NULL,'T1021S3','HCG-P2013',0.2500,0.2500,NULL,'625万元','2015-10-30','货币','625万元','2015-10-30',NULL,'2015-10-30',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1041,1022,'华晟控股集团有限公司','1','华晟控股','T1022S1',NULL,0.5100,0.5100,NULL,'2550万元','2013-04-28','货币','2550万元','2013-04-28',NULL,'2013-04-28',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1042,1022,'陈建国','2',NULL,'T1022S2','HCG-P2016',0.3400,0.3400,NULL,'1700万元','2013-04-28','货币','1700万元','2013-04-28',NULL,'2013-04-28',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35'),(1043,1022,'林薇','2',NULL,'T1022S3','HCG-P2017',0.1500,0.1500,NULL,'750万元','2013-04-28','货币','750万元','2013-04-28',NULL,'2013-04-28',NULL,'1',821,NULL,'2026-11-18 10:35:00','2026-11-25 22:05:35','2026-11-25 22:05:35');
/*!40000 ALTER TABLE `srm_supplier_shareholder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_trigger_rule`
--

DROP TABLE IF EXISTS `srm_trigger_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_trigger_rule` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `rule_code` varchar(40) COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则码：supplier_save / single_enrich / monitor_scan / project_analysis / repeat_query',
  `rule_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则名（页面16 五条触发规则原文）',
  `trigger_mode` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'never 不自动调用 / manual 经办人点击 / scoped 仅已勾选 / member 仅项目成员 / cache 缓存控制',
  `api_ids` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '允许调用的接口编号，逗号分隔；空表示由业务页面自带清单',
  `allow_auto` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '是否允许自动发起：0 否（需求默认）/ 1 是',
  `enabled` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '启用状态',
  `sort_no` int DEFAULT NULL,
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rule_code` (`rule_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='触发规则清单（页面16 五条）：哪些业务动作允许发起外部请求，网关按本表放行，新增动作改配置不改代码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_trigger_rule`
--

LOCK TABLES `srm_trigger_rule` WRITE;
/*!40000 ALTER TABLE `srm_trigger_rule` DISABLE KEYS */;
/*!40000 ALTER TABLE `srm_trigger_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `srm_usage_stat`
--

DROP TABLE IF EXISTS `srm_usage_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `srm_usage_stat` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `stat_date` date NOT NULL COMMENT '统计日期（按月统计时 stat_date 取当月 1 日，period_type=month）',
  `period_type` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'day / month',
  `api_id` int NOT NULL DEFAULT '0' COMMENT '接口编号，0 表示全部接口合计',
  `call_count` int NOT NULL DEFAULT '0' COMMENT '发起调用次数',
  `cache_hit_count` int NOT NULL DEFAULT '0' COMMENT '缓存命中次数（节省量）',
  `block_count` int NOT NULL DEFAULT '0' COMMENT '被拦截次数',
  `fail_count` int NOT NULL DEFAULT '0' COMMENT '失败次数',
  `unit_consumed` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '消耗调用单元合计',
  `cost_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '按字典单价折算的金额，供对账，不落前端',
  `avg_cost_ms` int DEFAULT NULL COMMENT '平均响应耗时',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_period_api` (`stat_date`,`period_type`,`api_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1019 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用量计量汇总（由定时任务从 srm_api_call_log 增量聚合）。页面16 阈值判断读 Redis 实时值、本表读历史；页面18 KPI 与供应商对账用本表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `srm_usage_stat`
--

LOCK TABLES `srm_usage_stat` WRITE;
/*!40000 ALTER TABLE `srm_usage_stat` DISABLE KEYS */;
INSERT INTO `srm_usage_stat` VALUES (1001,'2026-11-20','day',821,6,2,1,0,6.00,21.00,450,'2026-11-20 23:00:00','2026-11-20 23:00:00'),(1002,'2026-11-20','day',1123,3,1,0,0,3.00,10.50,480,'2026-11-20 23:00:00','2026-11-20 23:00:00'),(1003,'2026-11-20','day',1156,9,3,0,0,9.00,31.50,510,'2026-11-20 23:00:00','2026-11-20 23:00:00'),(1004,'2026-11-20','day',1158,4,1,0,0,4.00,14.00,540,'2026-11-20 23:00:00','2026-11-20 23:00:00'),(1005,'2026-11-20','day',1160,5,1,0,0,5.00,17.50,420,'2026-11-20 23:00:00','2026-11-20 23:00:00'),(1006,'2026-11-20','day',1161,7,2,0,0,7.00,24.50,450,'2026-11-20 23:00:00','2026-11-20 23:00:00'),(1007,'2026-11-21','day',821,6,2,0,0,6.00,21.00,480,'2026-11-21 23:00:00','2026-11-21 23:00:00'),(1008,'2026-11-21','day',1123,3,1,1,0,3.00,10.50,510,'2026-11-21 23:00:00','2026-11-21 23:00:00'),(1009,'2026-11-21','day',1156,9,3,0,0,9.00,31.50,540,'2026-11-21 23:00:00','2026-11-21 23:00:00'),(1010,'2026-11-21','day',1158,4,1,0,0,4.00,14.00,420,'2026-11-21 23:00:00','2026-11-21 23:00:00'),(1011,'2026-11-21','day',1160,5,1,0,0,5.00,17.50,450,'2026-11-21 23:00:00','2026-11-21 23:00:00'),(1012,'2026-11-21','day',1161,7,2,0,0,7.00,24.50,480,'2026-11-21 23:00:00','2026-11-21 23:00:00'),(1013,'2026-11-22','day',821,6,2,0,0,6.00,21.00,510,'2026-11-22 23:00:00','2026-11-22 23:00:00'),(1014,'2026-11-22','day',1123,3,1,0,0,3.00,10.50,540,'2026-11-22 23:00:00','2026-11-22 23:00:00'),(1015,'2026-11-22','day',1156,9,3,1,0,9.00,31.50,420,'2026-11-22 23:00:00','2026-11-22 23:00:00'),(1016,'2026-11-22','day',1158,4,1,0,0,4.00,14.00,450,'2026-11-22 23:00:00','2026-11-22 23:00:00'),(1017,'2026-11-22','day',1160,5,1,0,0,5.00,17.50,480,'2026-11-22 23:00:00','2026-11-22 23:00:00'),(1018,'2026-11-22','day',1161,7,2,0,0,7.00,24.50,510,'2026-11-22 23:00:00','2026-11-22 23:00:00');
/*!40000 ALTER TABLE `srm_usage_stat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2026-11-24 21:20:23','',NULL,'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2026-11-24 21:20:23','',NULL,'初始化密码 123456'),(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y','admin','2026-11-24 21:20:23','',NULL,'深色主题theme-dark，浅色主题theme-light'),(4,'账号自助-验证码开关','sys.account.captchaEnabled','true','Y','admin','2026-11-24 21:20:23','',NULL,'是否开启验证码功能（true开启，false关闭）'),(5,'账号自助-是否开启用户注册功能','sys.account.registerUser','false','Y','admin','2026-11-24 21:20:23','',NULL,'是否开启注册用户功能（true开启，false关闭）'),(6,'用户登录-黑名单列表','sys.login.blackIPList','','Y','admin','2026-11-24 21:20:23','',NULL,'设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）'),(7,'用户管理-初始密码修改策略','sys.account.initPasswordModify','1','Y','admin','2026-11-24 21:20:23','',NULL,'0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框'),(8,'用户管理-账号密码更新周期','sys.account.passwordValidateDays','0','Y','admin','2026-11-24 21:20:23','',NULL,'密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框'),(9,'用户管理-密码字符范围','sys.account.chrtype','0','Y','admin','2026-11-24 21:20:23','',NULL,'默认任意字符范围，0任意（密码可以输入任意字符），1数字（密码只能为0-9数字），2英文字母（密码只能为a-z和A-Z字母），3字母和数字（密码必须包含字母，数字）,4字母数字和特殊字符（目前支持的特殊字符包括：~!@#$%^&*()-=_+）');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (100,0,'0','若依科技',0,'若依','15888888888','ry@qq.com','0','0','admin','2026-11-24 21:20:21','',NULL),(101,100,'0,100','深圳总公司',1,'若依','15888888888','ry@qq.com','0','0','admin','2026-11-24 21:20:21','',NULL),(102,100,'0,100','长沙分公司',2,'若依','15888888888','ry@qq.com','0','0','admin','2026-11-24 21:20:21','',NULL),(103,101,'0,100,101','研发部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2026-11-24 21:20:21','',NULL),(104,101,'0,100,101','市场部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2026-11-24 21:20:21','',NULL),(105,101,'0,100,101','测试部门',3,'若依','15888888888','ry@qq.com','0','0','admin','2026-11-24 21:20:21','',NULL),(106,101,'0,100,101','财务部门',4,'若依','15888888888','ry@qq.com','0','0','admin','2026-11-24 21:20:21','',NULL),(107,101,'0,100,101','运维部门',5,'若依','15888888888','ry@qq.com','0','0','admin','2026-11-24 21:20:21','',NULL),(108,102,'0,100,102','市场部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2026-11-24 21:20:21','',NULL),(109,102,'0,100,102','财务部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2026-11-24 21:20:21','',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`),
  UNIQUE KEY `uk_dict_type_value` (`dict_type`,`dict_value`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','sys_user_sex','','','Y','0','admin','2026-11-24 21:20:22','',NULL,'性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2026-11-24 21:20:22','',NULL,'性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2026-11-24 21:20:22','',NULL,'性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2026-11-24 21:20:22','',NULL,'显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2026-11-24 21:20:22','',NULL,'隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2026-11-24 21:20:22','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2026-11-24 21:20:22','',NULL,'停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2026-11-24 21:20:22','',NULL,'正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2026-11-24 21:20:22','',NULL,'停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2026-11-24 21:20:22','',NULL,'默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2026-11-24 21:20:22','',NULL,'系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2026-11-24 21:20:22','',NULL,'系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2026-11-24 21:20:22','',NULL,'系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2026-11-24 21:20:22','',NULL,'通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2026-11-24 21:20:22','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2026-11-24 21:20:22','',NULL,'正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2026-11-24 21:20:22','',NULL,'关闭状态'),(18,99,'其他','0','sys_oper_type','','info','N','0','admin','2026-11-24 21:20:22','',NULL,'其他操作'),(19,1,'新增','1','sys_oper_type','','info','N','0','admin','2026-11-24 21:20:22','',NULL,'新增操作'),(20,2,'修改','2','sys_oper_type','','info','N','0','admin','2026-11-24 21:20:22','',NULL,'修改操作'),(21,3,'删除','3','sys_oper_type','','danger','N','0','admin','2026-11-24 21:20:22','',NULL,'删除操作'),(22,4,'授权','4','sys_oper_type','','primary','N','0','admin','2026-11-24 21:20:22','',NULL,'授权操作'),(23,5,'导出','5','sys_oper_type','','warning','N','0','admin','2026-11-24 21:20:22','',NULL,'导出操作'),(24,6,'导入','6','sys_oper_type','','warning','N','0','admin','2026-11-24 21:20:22','',NULL,'导入操作'),(25,7,'强退','7','sys_oper_type','','danger','N','0','admin','2026-11-24 21:20:22','',NULL,'强退操作'),(26,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2026-11-24 21:20:22','',NULL,'生成操作'),(27,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2026-11-24 21:20:22','',NULL,'清空操作'),(28,1,'成功','0','sys_common_status','','primary','N','0','admin','2026-11-24 21:20:22','',NULL,'正常状态'),(29,2,'失败','1','sys_common_status','','danger','N','0','admin','2026-11-24 21:20:22','',NULL,'停用状态');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'用户性别','sys_user_sex','0','admin','2026-11-24 21:20:22','',NULL,'用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2026-11-24 21:20:22','',NULL,'菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2026-11-24 21:20:22','',NULL,'系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2026-11-24 21:20:22','',NULL,'任务状态列表'),(5,'任务分组','sys_job_group','0','admin','2026-11-24 21:20:22','',NULL,'任务分组列表'),(6,'系统是否','sys_yes_no','0','admin','2026-11-24 21:20:22','',NULL,'系统是否列表'),(7,'通知类型','sys_notice_type','0','admin','2026-11-24 21:20:22','',NULL,'通知类型列表'),(8,'通知状态','sys_notice_status','0','admin','2026-11-24 21:20:22','',NULL,'通知状态列表'),(9,'操作类型','sys_oper_type','0','admin','2026-11-24 21:20:22','',NULL,'操作类型列表'),(10,'系统状态','sys_common_status','0','admin','2026-11-24 21:20:22','',NULL,'登录状态列表');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (1,'系统默认（无参）','DEFAULT','ryTask.ryNoParams','0/10 * * * * ?','3','1','1','admin','2026-11-24 21:20:23','',NULL,''),(2,'系统默认（有参）','DEFAULT','ryTask.ryParams(\'ry\')','0/15 * * * * ?','3','1','1','admin','2026-11-24 21:20:23','',NULL,''),(3,'系统默认（多参）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','0/20 * * * * ?','3','1','1','admin','2026-11-24 21:20:23','',NULL,'');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `start_time` datetime DEFAULT NULL COMMENT '执行开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '执行结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务调度日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`),
  KEY `idx_sys_logininfor_s` (`status`),
  KEY `idx_sys_logininfor_lt` (`login_time`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
INSERT INTO `sys_logininfor` VALUES (100,'admin','127.0.0.1','内网IP','Chrome 157','Windows10','0','登录成功','2026-11-24 21:21:01'),(101,'admin','127.0.0.1','内网IP','Chrome 157','Windows10','0','登录成功','2026-11-24 22:37:10'),(102,'admin','127.0.0.1','内网IP','Chrome 157','Windows10','0','登录成功','2026-11-24 23:14:10'),(103,'admin','127.0.0.1','内网IP','QoderCNIDE 1.40.1','Windows 10.0','0','登录成功','2026-11-24 23:20:32'),(104,'admin','127.0.0.1','内网IP','Chrome 157','Windows10','0','登录成功','2026-11-25 19:48:29'),(105,'admin','127.0.0.1','内网IP','Chrome 158','Windows10','0','登录成功','2026-11-25 20:28:21'),(106,'admin','127.0.0.1','内网IP','QoderCNIDE 1.41.1','Windows 10.0','0','登录成功','2026-11-25 20:47:07'),(107,'admin','127.0.0.1','内网IP','Chrome 158','Windows10','0','退出成功','2026-11-25 21:14:49'),(108,'admin','127.0.0.1','内网IP','Chrome 158','Windows10','0','登录成功','2026-11-25 21:14:50'),(109,'admin','127.0.0.1','内网IP','Chrome 158','Windows10','0','登录成功','2026-11-25 22:31:41'),(110,'admin','127.0.0.1','内网IP','Chrome 158','Windows10','0','退出成功','2026-11-25 22:48:07'),(111,'admin','127.0.0.1','内网IP','Chrome 158','Windows10','1','验证码已失效','2026-11-25 22:48:13'),(112,'admin','127.0.0.1','内网IP','Chrome 158','Windows10','0','登录成功','2026-11-25 22:48:13');
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) DEFAULT '' COMMENT '路由名称',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,1,'system',NULL,'','',1,0,'M','0','0','','system','admin','2026-11-24 21:20:21','',NULL,'系统管理目录'),(2,'系统监控',0,2,'monitor',NULL,'','',1,0,'M','0','0','','monitor','admin','2026-11-24 21:20:21','',NULL,'系统监控目录'),(3,'系统工具',0,3,'tool',NULL,'','',1,0,'M','0','0','','tool','admin','2026-11-24 21:20:21','',NULL,'系统工具目录'),(4,'若依官网',0,4,'http://ruoyi.vip',NULL,'','',0,0,'M','0','0','','guide','admin','2026-11-24 21:20:21','',NULL,'若依官网地址'),(100,'用户管理',1,1,'user','system/user/index','','',1,0,'C','0','0','system:user:list','user','admin','2026-11-24 21:20:21','',NULL,'用户管理菜单'),(101,'角色管理',1,2,'role','system/role/index','','',1,0,'C','0','0','system:role:list','peoples','admin','2026-11-24 21:20:21','',NULL,'角色管理菜单'),(102,'菜单管理',1,3,'menu','system/menu/index','','',1,0,'C','0','0','system:menu:list','tree-table','admin','2026-11-24 21:20:21','',NULL,'菜单管理菜单'),(103,'部门管理',1,4,'dept','system/dept/index','','',1,0,'C','0','0','system:dept:list','tree','admin','2026-11-24 21:20:21','',NULL,'部门管理菜单'),(104,'岗位管理',1,5,'post','system/post/index','','',1,0,'C','0','0','system:post:list','post','admin','2026-11-24 21:20:21','',NULL,'岗位管理菜单'),(105,'字典管理',1,6,'dict','system/dict/index','','',1,0,'C','0','0','system:dict:list','dict','admin','2026-11-24 21:20:21','',NULL,'字典管理菜单'),(106,'参数设置',1,7,'config','system/config/index','','',1,0,'C','0','0','system:config:list','edit','admin','2026-11-24 21:20:21','',NULL,'参数设置菜单'),(107,'通知公告',1,8,'notice','system/notice/index','','',1,0,'C','0','0','system:notice:list','message','admin','2026-11-24 21:20:21','',NULL,'通知公告菜单'),(108,'日志管理',1,9,'log','','','',1,0,'M','0','0','','log','admin','2026-11-24 21:20:21','',NULL,'日志管理菜单'),(109,'在线用户',2,1,'online','monitor/online/index','','',1,0,'C','0','0','monitor:online:list','online','admin','2026-11-24 21:20:21','',NULL,'在线用户菜单'),(110,'定时任务',2,2,'job','monitor/job/index','','',1,0,'C','0','0','monitor:job:list','job','admin','2026-11-24 21:20:21','',NULL,'定时任务菜单'),(111,'数据监控',2,3,'druid','monitor/druid/index','','',1,0,'C','0','0','monitor:druid:list','druid','admin','2026-11-24 21:20:21','',NULL,'数据监控菜单'),(112,'服务监控',2,4,'server','monitor/server/index','','',1,0,'C','0','0','monitor:server:list','server','admin','2026-11-24 21:20:21','',NULL,'服务监控菜单'),(113,'缓存监控',2,5,'cache','monitor/cache/index','','',1,0,'C','0','0','monitor:cache:list','redis','admin','2026-11-24 21:20:21','',NULL,'缓存监控菜单'),(114,'缓存列表',2,6,'cacheList','monitor/cache/list','','',1,0,'C','0','0','monitor:cache:list','redis-list','admin','2026-11-24 21:20:21','',NULL,'缓存列表菜单'),(115,'表单构建',3,1,'build','tool/build/index','','',1,0,'C','0','0','tool:build:list','build','admin','2026-11-24 21:20:21','',NULL,'表单构建菜单'),(116,'代码生成',3,2,'gen','tool/gen/index','','',1,0,'C','0','0','tool:gen:list','code','admin','2026-11-24 21:20:21','',NULL,'代码生成菜单'),(117,'系统接口',3,3,'swagger','tool/swagger/index','','',1,0,'C','0','0','tool:swagger:list','swagger','admin','2026-11-24 21:20:21','',NULL,'系统接口菜单'),(500,'操作日志',108,1,'operlog','monitor/operlog/index','','',1,0,'C','0','0','monitor:operlog:list','form','admin','2026-11-24 21:20:21','',NULL,'操作日志菜单'),(501,'登录日志',108,2,'logininfor','monitor/logininfor/index','','',1,0,'C','0','0','monitor:logininfor:list','logininfor','admin','2026-11-24 21:20:21','',NULL,'登录日志菜单'),(1000,'用户查询',100,1,'','','','',1,0,'F','0','0','system:user:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1001,'用户新增',100,2,'','','','',1,0,'F','0','0','system:user:add','#','admin','2026-11-24 21:20:21','',NULL,''),(1002,'用户修改',100,3,'','','','',1,0,'F','0','0','system:user:edit','#','admin','2026-11-24 21:20:21','',NULL,''),(1003,'用户删除',100,4,'','','','',1,0,'F','0','0','system:user:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1004,'用户导出',100,5,'','','','',1,0,'F','0','0','system:user:export','#','admin','2026-11-24 21:20:21','',NULL,''),(1005,'用户导入',100,6,'','','','',1,0,'F','0','0','system:user:import','#','admin','2026-11-24 21:20:21','',NULL,''),(1006,'重置密码',100,7,'','','','',1,0,'F','0','0','system:user:resetPwd','#','admin','2026-11-24 21:20:21','',NULL,''),(1007,'角色查询',101,1,'','','','',1,0,'F','0','0','system:role:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1008,'角色新增',101,2,'','','','',1,0,'F','0','0','system:role:add','#','admin','2026-11-24 21:20:21','',NULL,''),(1009,'角色修改',101,3,'','','','',1,0,'F','0','0','system:role:edit','#','admin','2026-11-24 21:20:21','',NULL,''),(1010,'角色删除',101,4,'','','','',1,0,'F','0','0','system:role:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1011,'角色导出',101,5,'','','','',1,0,'F','0','0','system:role:export','#','admin','2026-11-24 21:20:21','',NULL,''),(1012,'菜单查询',102,1,'','','','',1,0,'F','0','0','system:menu:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1013,'菜单新增',102,2,'','','','',1,0,'F','0','0','system:menu:add','#','admin','2026-11-24 21:20:21','',NULL,''),(1014,'菜单修改',102,3,'','','','',1,0,'F','0','0','system:menu:edit','#','admin','2026-11-24 21:20:21','',NULL,''),(1015,'菜单删除',102,4,'','','','',1,0,'F','0','0','system:menu:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1016,'部门查询',103,1,'','','','',1,0,'F','0','0','system:dept:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1017,'部门新增',103,2,'','','','',1,0,'F','0','0','system:dept:add','#','admin','2026-11-24 21:20:21','',NULL,''),(1018,'部门修改',103,3,'','','','',1,0,'F','0','0','system:dept:edit','#','admin','2026-11-24 21:20:21','',NULL,''),(1019,'部门删除',103,4,'','','','',1,0,'F','0','0','system:dept:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1020,'岗位查询',104,1,'','','','',1,0,'F','0','0','system:post:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1021,'岗位新增',104,2,'','','','',1,0,'F','0','0','system:post:add','#','admin','2026-11-24 21:20:21','',NULL,''),(1022,'岗位修改',104,3,'','','','',1,0,'F','0','0','system:post:edit','#','admin','2026-11-24 21:20:21','',NULL,''),(1023,'岗位删除',104,4,'','','','',1,0,'F','0','0','system:post:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1024,'岗位导出',104,5,'','','','',1,0,'F','0','0','system:post:export','#','admin','2026-11-24 21:20:21','',NULL,''),(1025,'字典查询',105,1,'#','','','',1,0,'F','0','0','system:dict:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1026,'字典新增',105,2,'#','','','',1,0,'F','0','0','system:dict:add','#','admin','2026-11-24 21:20:21','',NULL,''),(1027,'字典修改',105,3,'#','','','',1,0,'F','0','0','system:dict:edit','#','admin','2026-11-24 21:20:21','',NULL,''),(1028,'字典删除',105,4,'#','','','',1,0,'F','0','0','system:dict:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1029,'字典导出',105,5,'#','','','',1,0,'F','0','0','system:dict:export','#','admin','2026-11-24 21:20:21','',NULL,''),(1030,'参数查询',106,1,'#','','','',1,0,'F','0','0','system:config:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1031,'参数新增',106,2,'#','','','',1,0,'F','0','0','system:config:add','#','admin','2026-11-24 21:20:21','',NULL,''),(1032,'参数修改',106,3,'#','','','',1,0,'F','0','0','system:config:edit','#','admin','2026-11-24 21:20:21','',NULL,''),(1033,'参数删除',106,4,'#','','','',1,0,'F','0','0','system:config:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1034,'参数导出',106,5,'#','','','',1,0,'F','0','0','system:config:export','#','admin','2026-11-24 21:20:21','',NULL,''),(1035,'公告查询',107,1,'#','','','',1,0,'F','0','0','system:notice:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1036,'公告新增',107,2,'#','','','',1,0,'F','0','0','system:notice:add','#','admin','2026-11-24 21:20:21','',NULL,''),(1037,'公告修改',107,3,'#','','','',1,0,'F','0','0','system:notice:edit','#','admin','2026-11-24 21:20:21','',NULL,''),(1038,'公告删除',107,4,'#','','','',1,0,'F','0','0','system:notice:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1039,'操作查询',500,1,'#','','','',1,0,'F','0','0','monitor:operlog:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1040,'操作删除',500,2,'#','','','',1,0,'F','0','0','monitor:operlog:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1041,'日志导出',500,3,'#','','','',1,0,'F','0','0','monitor:operlog:export','#','admin','2026-11-24 21:20:21','',NULL,''),(1042,'登录查询',501,1,'#','','','',1,0,'F','0','0','monitor:logininfor:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1043,'登录删除',501,2,'#','','','',1,0,'F','0','0','monitor:logininfor:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1044,'日志导出',501,3,'#','','','',1,0,'F','0','0','monitor:logininfor:export','#','admin','2026-11-24 21:20:21','',NULL,''),(1045,'账户解锁',501,4,'#','','','',1,0,'F','0','0','monitor:logininfor:unlock','#','admin','2026-11-24 21:20:21','',NULL,''),(1046,'在线查询',109,1,'#','','','',1,0,'F','0','0','monitor:online:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1047,'批量强退',109,2,'#','','','',1,0,'F','0','0','monitor:online:batchLogout','#','admin','2026-11-24 21:20:21','',NULL,''),(1048,'单条强退',109,3,'#','','','',1,0,'F','0','0','monitor:online:forceLogout','#','admin','2026-11-24 21:20:21','',NULL,''),(1049,'任务查询',110,1,'#','','','',1,0,'F','0','0','monitor:job:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1050,'任务新增',110,2,'#','','','',1,0,'F','0','0','monitor:job:add','#','admin','2026-11-24 21:20:21','',NULL,''),(1051,'任务修改',110,3,'#','','','',1,0,'F','0','0','monitor:job:edit','#','admin','2026-11-24 21:20:21','',NULL,''),(1052,'任务删除',110,4,'#','','','',1,0,'F','0','0','monitor:job:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1053,'状态修改',110,5,'#','','','',1,0,'F','0','0','monitor:job:changeStatus','#','admin','2026-11-24 21:20:21','',NULL,''),(1054,'任务导出',110,6,'#','','','',1,0,'F','0','0','monitor:job:export','#','admin','2026-11-24 21:20:21','',NULL,''),(1055,'生成查询',116,1,'#','','','',1,0,'F','0','0','tool:gen:query','#','admin','2026-11-24 21:20:21','',NULL,''),(1056,'生成修改',116,2,'#','','','',1,0,'F','0','0','tool:gen:edit','#','admin','2026-11-24 21:20:21','',NULL,''),(1057,'生成删除',116,3,'#','','','',1,0,'F','0','0','tool:gen:remove','#','admin','2026-11-24 21:20:21','',NULL,''),(1058,'导入代码',116,4,'#','','','',1,0,'F','0','0','tool:gen:import','#','admin','2026-11-24 21:20:21','',NULL,''),(1059,'预览代码',116,5,'#','','','',1,0,'F','0','0','tool:gen:preview','#','admin','2026-11-24 21:20:21','',NULL,''),(1060,'生成代码',116,6,'#','','','',1,0,'F','0','0','tool:gen:code','#','admin','2026-11-24 21:20:21','',NULL,'');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` VALUES (1,'温馨提醒：2018-07-01 若依新版本发布啦','2',_binary '新版本内容','0','admin','2026-11-24 21:20:23','',NULL,'管理员'),(2,'维护通知：2018-07-01 若依系统凌晨维护','1',_binary '维护内容','0','admin','2026-11-24 21:20:23','',NULL,'管理员'),(3,'若依开源框架介绍','1',_binary '<p><span style=\"color: rgb(230, 0, 0);\">项目介绍</span></p><p><font color=\"#333333\">RuoYi开源项目是为企业用户定制的后台脚手架框架，为企业打造的一站式解决方案，降低企业开发成本，提升开发效率。主要包括用户管理、角色管理、部门管理、菜单管理、参数管理、字典管理、</font><span style=\"color: rgb(51, 51, 51);\">岗位管理</span><span style=\"color: rgb(51, 51, 51);\">、定时任务</span><span style=\"color: rgb(51, 51, 51);\">、</span><span style=\"color: rgb(51, 51, 51);\">服务监控、登录日志、操作日志、代码生成等功能。其中，还支持多数据源、数据权限、国际化、Redis缓存、Docker部署、滑动验证码、第三方认证登录、分布式事务、</span><font color=\"#333333\">分布式文件存储</font><span style=\"color: rgb(51, 51, 51);\">、分库分表处理等技术特点。</span></p><p><img src=\"https://foruda.gitee.com/images/1773931848342439032/a4d22313_1815095.png\" style=\"width: 64px;\"><br></p><p><span style=\"color: rgb(230, 0, 0);\">官网及演示</span></p><p><span style=\"color: rgb(51, 51, 51);\">若依官网地址：&nbsp;</span><a href=\"http://ruoyi.vip\" target=\"_blank\">http://ruoyi.vip</a><a href=\"http://ruoyi.vip\" target=\"_blank\"></a></p><p><span style=\"color: rgb(51, 51, 51);\">若依文档地址：&nbsp;</span><a href=\"http://doc.ruoyi.vip\" target=\"_blank\">http://doc.ruoyi.vip</a><br></p><p><span style=\"color: rgb(51, 51, 51);\">演示地址【不分离版】：&nbsp;</span><a href=\"http://demo.ruoyi.vip\" target=\"_blank\">http://demo.ruoyi.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">演示地址【分离版本】：&nbsp;</span><a href=\"http://vue.ruoyi.vip\" target=\"_blank\">http://vue.ruoyi.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">演示地址【微服务版】：&nbsp;</span><a href=\"http://cloud.ruoyi.vip\" target=\"_blank\">http://cloud.ruoyi.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">演示地址【移动端版】：&nbsp;</span><a href=\"http://h5.ruoyi.vip\" target=\"_blank\">http://h5.ruoyi.vip</a></p><p><br style=\"color: rgb(48, 49, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 12px;\"></p>','0','admin','2026-11-24 21:20:23','',NULL,'管理员');
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice_read`
--

DROP TABLE IF EXISTS `sys_notice_read`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice_read` (
  `read_id` bigint NOT NULL AUTO_INCREMENT COMMENT '已读主键',
  `notice_id` int NOT NULL COMMENT '公告id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `read_time` datetime NOT NULL COMMENT '阅读时间',
  PRIMARY KEY (`read_id`),
  UNIQUE KEY `uk_user_notice` (`user_id`,`notice_id`) COMMENT '同一用户同一公告只记录一次'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告已读记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice_read`
--

LOCK TABLES `sys_notice_read` WRITE;
/*!40000 ALTER TABLE `sys_notice_read` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice_read` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint DEFAULT '0' COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`),
  KEY `idx_sys_oper_log_bt` (`business_type`),
  KEY `idx_sys_oper_log_s` (`status`),
  KEY `idx_sys_oper_log_ot` (`oper_time`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` VALUES (1,'ceo','董事长',1,'0','admin','2026-11-24 21:20:21','',NULL,''),(2,'se','项目经理',2,'0','admin','2026-11-24 21:20:21','',NULL,''),(3,'hr','人力资源',3,'0','admin','2026-11-24 21:20:21','',NULL,''),(4,'user','普通员工',4,'0','admin','2026-11-24 21:20:21','',NULL,'');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5：仅本人数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'1',1,1,'0','0','admin','2026-11-24 21:20:21','',NULL,'超级管理员'),(2,'普通角色','common',2,'2',1,1,'0','0','admin','2026-11-24 21:20:21','',NULL,'普通角色');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
INSERT INTO `sys_role_dept` VALUES (2,100),(2,101),(2,105);
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (2,1),(2,2),(2,3),(2,4),(2,100),(2,101),(2,102),(2,103),(2,104),(2,105),(2,106),(2,107),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,115),(2,116),(2,117),(2,500),(2,501),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,1007),(2,1008),(2,1009),(2,1010),(2,1011),(2,1012),(2,1013),(2,1014),(2,1015),(2,1016),(2,1017),(2,1018),(2,1019),(2,1020),(2,1021),(2,1022),(2,1023),(2,1024),(2,1025),(2,1026),(2,1027),(2,1028),(2,1029),(2,1030),(2,1031),(2,1032),(2,1033),(2,1034),(2,1035),(2,1036),(2,1037),(2,1038),(2,1039),(2,1040),(2,1041),(2,1042),(2,1043),(2,1044),(2,1045),(2,1046),(2,1047),(2,1048),(2,1049),(2,1050),(2,1051),(2,1052),(2,1053),(2,1054),(2,1055),(2,1056),(2,1057),(2,1058),(2,1059),(2,1060);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,103,'admin','若依','00','ry@163.com','15888888888','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2026-11-25 22:48:14','2026-11-24 21:20:21','admin','2026-11-24 21:20:21','',NULL,'管理员'),(2,105,'ry','若依','00','ry@qq.com','15666666666','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2026-11-24 21:20:21','2026-11-24 21:20:21','admin','2026-11-24 21:20:21','',NULL,'测试员');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
INSERT INTO `sys_user_post` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'srm'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-25 23:16:43
