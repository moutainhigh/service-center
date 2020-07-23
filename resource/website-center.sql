/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50725
Source Host           : rm-bp1o00hl21xvv5eq7no.mysql.rds.aliyuncs.com:3306
Source Database       : website-center

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-03-25 16:47:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for lawcase_consult_appendix
-- ----------------------------
DROP TABLE IF EXISTS `lawcase_consult_appendix`;
CREATE TABLE `lawcase_consult_appendix` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appendix_id` varchar(50) DEFAULT '',
  `ref_id` varchar(50) DEFAULT '' COMMENT '关联id',
  `appendix_name` varchar(50) DEFAULT '',
  `oss_resource_id` varchar(50) DEFAULT '',
  `file_size` double DEFAULT NULL COMMENT '文件大小',
  `full_name` varchar(200) DEFAULT '',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `appendix_type` varchar(100) DEFAULT '' COMMENT '附件类型',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=utf8mb4;
-- ----------------------------
-- Table structure for lawcase_consult
-- ----------------------------
DROP TABLE IF EXISTS `lawcase_consult`;
CREATE TABLE `lawcase_consult` (
  `consult_id` varchar(50) NOT NULL,
  `consult_content` varchar(300) DEFAULT NULL,
  `consultor` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `reply` varchar(200) DEFAULT NULL,
  `lawyer` varchar(50) DEFAULT NULL,
  `contact` varchar(50) DEFAULT NULL,
  `source` varchar(20) DEFAULT NULL,
  `target` varchar(50) DEFAULT NULL,
  `origin` varchar(20) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`consult_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-- 1
-- 律师表结构变更
alter table lawyer
add column introduction varchar(500) default '' comment '简介',
add column overall_evaluation varchar(10) default '' comment '综合评估',
add column professional_degree varchar(10) default '' comment '专业程度',
add column service_efficiency varchar(10) default '' comment '服务效率',
add column service_attitude varchar(10) default '' comment '服务态度'

-- 2
-- 法律文库
CREATE TABLE `law_doc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` varchar(50) DEFAULT '' COMMENT '类目id',
  `doc_type` varchar(50) DEFAULT '' COMMENT '文档类型',
  `doc_subtype` varchar(10) DEFAULT '' COMMENT '文档子类型',
  `doc_name` varchar(200) DEFAULT '' COMMENT '文档名称',
  `full_name` varchar(200) DEFAULT '' COMMENT '全称',
  `oss_resource_id` varchar(50) DEFAULT '' COMMENT '资源url',
  `downloads` bigint(20) DEFAULT '0' COMMENT '下载次数',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
-- 购买记录表
CREATE TABLE `buy_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `record_id` varchar(50) DEFAULT '' COMMENT '购买记录id',
  `wechat_openid` varchar(50) DEFAULT '' COMMENT '微信openid',
  `order_no` varchar(50) DEFAULT '' COMMENT '订单号',
  `buy_type` varchar(50) DEFAULT '' COMMENT '购买类型',
  `buy_time` datetime DEFAULT NULL COMMENT '购买时间',
  `expire_time` datetime DEFAULT NULL COMMENT '到期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

-- 修改表字段
ALTER TABLE lawcase_consult
ADD COLUMN enterprise_name varchar(50) DEFAULT '' COMMENT '企业名称',
ADD COLUMN answer varchar(50) DEFAULT '' COMMENT '答案',
ADD COLUMN score varchar(50) DEFAULT '' COMMENT '分数';

-- 修改表字段
ALTER TABLE question
ADD COLUMN system_tag varchar(50) DEFAULT '' COMMENT '系统标记';
-- 修改表字段
ALTER TABLE law_knowledge_category
ADD COLUMN system_tag varchar(50) DEFAULT '' COMMENT '系统标记';
-- 修改表字段
ALTER TABLE lawyer
ADD COLUMN system_tag varchar(50) DEFAULT '' COMMENT '系统标记';