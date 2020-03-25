/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50725
Source Host           : rm-bp1o00hl21xvv5eq7no.mysql.rds.aliyuncs.com:3306
Source Database       : website-center

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-03-25 16:47:09
*/

SET FOREIGN_KEY_CHECKS=0;

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
