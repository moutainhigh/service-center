-- 1.增加记录极光推送记录数据表
DROP TABLE IF EXISTS `jpush_schedual_record`;
CREATE TABLE `jpush_schedual_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(255)  DEFAULT NULL COMMENT 'RocketMQ消息ID',
  `schedule_id` varchar(255) DEFAULT NULL COMMENT '极光推送ID',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;