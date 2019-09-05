# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 47.110.43.11 (MySQL 5.6.44)
# Database: kasa
# Generation Time: 2019-09-05 02:00:39 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table cc_resource
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cc_resource`;

CREATE TABLE `cc_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `value` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'url(对应的url)',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态(0:删除，1:正常)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='权限资源表';

LOCK TABLES `cc_resource` WRITE;
/*!40000 ALTER TABLE `cc_resource` DISABLE KEYS */;

INSERT INTO `cc_resource` (`id`, `value`, `parent_id`, `name`, `sort`, `create_time`, `update_time`, `status`)
VALUES
	(1,X'2F73796E632F74657374',0,X'E6B58BE8AF95',0,'2019-08-09 16:37:55','2019-08-12 14:14:42',1),
	(2,X'2F75736572',0,X'E794A8E688B7',0,'2019-08-13 19:44:35','2019-08-13 19:44:35',1),
	(3,X'2F757365722F6C6F67696E',2,X'E794A8E688B7E799BBE5BD95',0,'2019-08-13 20:07:17','2019-08-13 20:07:17',1),
	(4,X'2F757365722F7265676973746572',2,X'E794A8E688B7E6B3A8E5868C',0,'2019-08-13 20:07:17','2019-08-13 20:07:17',1);

/*!40000 ALTER TABLE `cc_resource` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table cc_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cc_role`;

CREATE TABLE `cc_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '角色名',
  `value` int(11) NOT NULL DEFAULT '200' COMMENT '角色值(100管理员,200非管理员)',
  `remark` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色备注',
  `type` int(1) NOT NULL DEFAULT '0' COMMENT '角色类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态(0:删除，1:正常)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=COMPACT COMMENT='角色表';

LOCK TABLES `cc_role` WRITE;
/*!40000 ALTER TABLE `cc_role` DISABLE KEYS */;

INSERT INTO `cc_role` (`id`, `name`, `value`, `remark`, `type`, `create_time`, `update_time`, `status`)
VALUES
	(1,X'E8AEBFE5AEA2',200,X'E4B88DE99C80E8A681E799BBE5BD95',0,'2019-08-09 16:44:06','2019-08-09 16:44:19',1),
	(2,X'E794A8E688B7',200,X'E699AEE9809AE794A8E688B7',0,'2019-08-09 16:21:19','2019-08-09 16:43:59',1);

/*!40000 ALTER TABLE `cc_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table cc_role_resource
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cc_role_resource`;

CREATE TABLE `cc_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) NOT NULL COMMENT 'role_id(角色id)',
  `resource_id` int(11) NOT NULL COMMENT 'resource_id(资源id)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态(0:删除，1:正常)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `Index_roleId` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=COMPACT COMMENT='角色资源关联表';

LOCK TABLES `cc_role_resource` WRITE;
/*!40000 ALTER TABLE `cc_role_resource` DISABLE KEYS */;

INSERT INTO `cc_role_resource` (`id`, `role_id`, `resource_id`, `create_time`, `update_time`, `status`)
VALUES
	(1,2,1,'2019-08-09 16:38:01','2019-08-09 16:44:24',1);

/*!40000 ALTER TABLE `cc_role_resource` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table cc_role_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cc_role_user`;

CREATE TABLE `cc_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT 'user_id(用户id)',
  `role_id` int(11) NOT NULL COMMENT 'role_id(角色id)',
  `parent` int(11) NOT NULL DEFAULT '0' COMMENT '父亲id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态(0:删除，1:正常)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `Unique_userRole` (`user_id`,`role_id`),
  KEY `Index_userId` (`parent`),
  KEY `Index_roleId` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=COMPACT COMMENT='用户角色关联表';

LOCK TABLES `cc_role_user` WRITE;
/*!40000 ALTER TABLE `cc_role_user` DISABLE KEYS */;

INSERT INTO `cc_role_user` (`id`, `user_id`, `role_id`, `parent`, `create_time`, `update_time`, `status`)
VALUES
	(1,1,2,0,'2019-08-09 16:27:51','2019-08-12 10:29:18',1);

/*!40000 ALTER TABLE `cc_role_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table cc_test0
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cc_test0`;

CREATE TABLE `cc_test0` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `qwe` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table cc_test1
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cc_test1`;

CREATE TABLE `cc_test1` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `qwe` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table cc_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `cc_user`;

CREATE TABLE `cc_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键，user_id',
  `mobile` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '手机号(登录账户)',
  `nickname` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别(0:未知/1:女/2:男)',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `email` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` smallint(1) NOT NULL DEFAULT '1' COMMENT '状态(0:已删除/1:正常)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Unique_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

LOCK TABLES `cc_user` WRITE;
/*!40000 ALTER TABLE `cc_user` DISABLE KEYS */;

INSERT INTO `cc_user` (`id`, `mobile`, `nickname`, `password`, `sex`, `birthday`, `email`, `create_time`, `update_time`, `status`)
VALUES
	(1,'15068776210','?????','$2a$10$CMrjnMDjxrc5X4Vl/0wvEeE4SQKVYnx6loYrXAurvgX1UAn2.ccDK',0,NULL,NULL,'2019-08-08 23:50:37','2019-08-09 16:34:40',1);

/*!40000 ALTER TABLE `cc_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
