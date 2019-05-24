-- 1.日志表添加系统标签
ALTER TABLE `system_log_business`
  ADD COLUMN `system_tag`  varchar(30) NULL COMMENT '系统标签';
ALTER TABLE `system_log_error`
  ADD COLUMN `system_tag`  varchar(30) NULL COMMENT '系统标签'