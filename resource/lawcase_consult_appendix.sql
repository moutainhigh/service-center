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
  `uploader_user_id` varchar(50) DEFAULT '' COMMENT '用户id',
  `full_name` varchar(200) DEFAULT '',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `appendix_type` varchar(100) DEFAULT '' COMMENT '附件类型',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=utf8mb4;
