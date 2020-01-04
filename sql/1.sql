/*
SQLyog Ultimate v11.13 (64 bit)
MySQL - 5.7.28-log : Database - rdyl_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rdyl_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `rdyl_db`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `availbalance` decimal(16,8) NOT NULL COMMENT '可用余额',
  `frozenblance` decimal(16,8) NOT NULL COMMENT '冻结余额',
  `account_type` tinyint(2) NOT NULL COMMENT '账户类型',
  `coin_type` tinyint(2) NOT NULL COMMENT '币种',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `accountTypePk` (`account_type`),
  KEY `cointypePk` (`coin_type`),
  KEY `useridPk` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `account` */

insert  into `account`(`id`,`user_id`,`availbalance`,`frozenblance`,`account_type`,`coin_type`,`createtime`,`updatetime`) values (1,3,0.00000000,0.00000000,0,0,'2019-12-21 20:00:33','2019-12-21 20:00:33'),(2,3,0.00000000,0.00000000,0,1,'2019-12-21 20:00:33','2019-12-21 20:00:33'),(3,4,0.00000000,0.00000000,0,0,'2019-12-21 20:02:12','2019-12-21 20:02:12'),(4,4,0.00000000,0.00000000,0,1,'2019-12-21 20:02:12','2019-12-21 20:02:12'),(5,5,0.00000000,0.00000000,0,0,'2019-12-21 20:06:08','2019-12-21 20:06:08'),(6,5,0.00000000,0.00000000,0,1,'2019-12-21 20:06:08','2019-12-21 20:06:08'),(7,6,0.00000000,0.00000000,0,0,'2019-12-21 20:07:15','2019-12-21 20:07:15'),(8,6,0.00000000,0.00000000,0,1,'2019-12-21 20:07:15','2019-12-21 20:07:15'),(9,7,0.00000000,0.00000000,0,0,'2019-12-21 20:13:45','2019-12-21 20:13:45'),(10,7,0.00000000,0.00000000,0,1,'2019-12-21 20:13:45','2019-12-21 20:13:45'),(11,8,0.00000000,0.00000000,0,0,'2019-12-21 20:43:19','2019-12-21 20:43:19'),(12,8,0.00000000,0.00000000,0,1,'2019-12-21 20:43:19','2019-12-21 20:43:19'),(13,1,40.00000000,2.00000000,0,1,'2019-12-21 20:43:19','2019-12-21 20:43:19');

/*Table structure for table `account_transfer` */

DROP TABLE IF EXISTS `account_transfer`;

CREATE TABLE `account_transfer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `to_user_id` int(11) NOT NULL COMMENT '目标用户id',
  `coin_type` tinyint(2) NOT NULL COMMENT '币种类型',
  `account_type` tinyint(2) NOT NULL COMMENT '账户类型',
  `amount` decimal(16,8) NOT NULL COMMENT '转帐金额',
  `relatedid` int(11) NOT NULL COMMENT '相关id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `account_transfer` */

/*Table structure for table `appointment_record` */

DROP TABLE IF EXISTS `appointment_record`;

CREATE TABLE `appointment_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `spend` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '花费',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `appointment_record` */

insert  into `appointment_record`(`id`,`user_id`,`name`,`spend`,`create_time`,`update_time`) values (2,1,'狗狗',10.00,'2019-12-30 19:35:54','2019-12-30 19:35:54'),(3,1,'狗狗1',5.00,'2019-12-30 19:35:56','2019-12-30 19:35:57'),(4,2,'狗狗2',51.00,'2019-12-30 19:36:06','2019-12-30 19:36:06');

/*Table structure for table `banner` */

DROP TABLE IF EXISTS `banner`;

CREATE TABLE `banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `imgpath` varchar(255) CHARACTER SET latin1 NOT NULL COMMENT '图片地址',
  `title` varchar(255) CHARACTER SET latin1 NOT NULL COMMENT '标题',
  `state` int(2) NOT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `banner` */

insert  into `banner`(`id`,`imgpath`,`title`,`state`,`create_time`,`update_time`) values (1,'https://avatars0.githubusercontent.com/u/30070855?s=460&v=4','test',1,'2019-12-25 10:22:29','2019-12-25 10:22:29'),(2,'https://csdnimg.cn/cdn/content-toolbar/csdnlogo-christmas.gif','test1',1,'2019-12-25 10:23:02','2019-12-25 10:23:02');

/*Table structure for table `bind_info` */

DROP TABLE IF EXISTS `bind_info`;

CREATE TABLE `bind_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '类型',
  `account` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '账户',
  `name` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '名称',
  `img_url` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '图片地址',
  `bank_name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '银行名称',
  `branch_name` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '开户行',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `bind_info` */

insert  into `bind_info`(`id`,`user_id`,`type`,`account`,`name`,`img_url`,`bank_name`,`branch_name`,`state`,`create_time`,`update_time`) values (6,1,0,'zhifubao','','','','',1,'2019-12-25 20:59:41','2019-12-25 21:00:19'),(7,1,1,'weixin','','','','',1,'2019-12-25 20:59:48','2019-12-25 21:00:21'),(8,1,2,'yinhangka','cccc','cccc','cccccc','cccccc',1,'2019-12-25 20:59:55','2019-12-25 21:00:24'),(9,0,3,'token','','','','',1,'2019-12-25 21:00:00','2019-12-25 21:00:26');

/*Table structure for table `coin_manager` */

DROP TABLE IF EXISTS `coin_manager`;

CREATE TABLE `coin_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '币种名称',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '充值地址',
  `recharge_url` varchar(255) NOT NULL DEFAULT '' COMMENT '充值二维码地址',
  `withdraw_fee` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '提现手续费',
  `recharge_fee` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '充值手续费',
  `recharge_amount_min` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '充值最小值',
  `withdraw_amount_min` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '提现最小值',
  `recharge_doc` varchar(1023) NOT NULL DEFAULT '' COMMENT '充值注意事项',
  `withdraw_doc` varchar(1023) NOT NULL DEFAULT '' COMMENT '提现注意事项',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `coin_manager` */

insert  into `coin_manager`(`id`,`name`,`address`,`recharge_url`,`withdraw_fee`,`recharge_fee`,`recharge_amount_min`,`withdraw_amount_min`,`recharge_doc`,`withdraw_doc`,`create_time`,`update_time`) values (1,'OS','SDFGDSGDSG','https://lanhuapp.com/',1.00,1.00,10.00,10.00,'https://lanhuapp.com/','https://lanhuapp.com/','2019-12-31 16:37:16','2019-12-31 16:40:59');

/*Table structure for table `flow` */

DROP TABLE IF EXISTS `flow`;

CREATE TABLE `flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `oper_type` varchar(255) NOT NULL COMMENT '操作类型',
  `relate_id` int(11) NOT NULL COMMENT '关联id',
  `account_type` int(11) NOT NULL COMMENT '账户类型',
  `coin_type` int(11) NOT NULL COMMENT '币种',
  `oper_id` int(11) NOT NULL COMMENT '操作人id',
  `amount` decimal(16,8) NOT NULL COMMENT '金额',
  `result_amount` varchar(255) DEFAULT NULL COMMENT '操作后金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `flow` */

insert  into `flow`(`id`,`user_id`,`oper_type`,`relate_id`,`account_type`,`coin_type`,`oper_id`,`amount`,`result_amount`,`create_time`,`update_time`) values (1,1,'预约消耗',3,0,1,1,2.00000000,'2','2019-12-25 18:32:38','2019-12-29 19:30:49');

/*Table structure for table `idcard_validate` */

DROP TABLE IF EXISTS `idcard_validate`;

CREATE TABLE `idcard_validate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `identificationnumber` varchar(255) NOT NULL COMMENT '身份证号',
  `idcardtype` varchar(255) NOT NULL COMMENT '身份证类型',
  `idcardexpiry` varchar(255) NOT NULL COMMENT '有效期',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `sex` varchar(20) NOT NULL COMMENT '性别',
  `idcardfrontpic` varchar(255) NOT NULL COMMENT '正面照片',
  `idcardbackpic` varchar(255) NOT NULL COMMENT '反面照片',
  `facepic` varchar(20) NOT NULL COMMENT '脸部照片',
  `state` int(2) NOT NULL COMMENT '状态',
  `taskid` varchar(255) NOT NULL COMMENT 'taskid',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `useridPk` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `idcard_validate` */

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标题',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT '内容',
  `type` int(2) NOT NULL DEFAULT '0' COMMENT '类型 0弹窗 1公告 2帮助',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `notice` */

insert  into `notice`(`id`,`title`,`content`,`type`,`create_time`,`update_time`) values (6,'测试1','士大夫大师傅士大夫',0,'2019-12-25 12:07:21','2019-12-25 12:07:21'),(7,'测试1','士大夫大师傅士大夫',1,'2019-12-25 12:07:21','2019-12-25 12:07:21'),(8,'测试1','士大夫大师傅士大夫',2,'2019-12-25 12:07:21','2019-12-25 12:07:21'),(9,'测试2','士大夫大师傅士大夫111',1,'2019-12-25 12:07:21','2019-12-25 12:07:21');

/*Table structure for table `pets` */

DROP TABLE IF EXISTS `pets`;

CREATE TABLE `pets` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '名称',
  `seque` tinyint(2) NOT NULL DEFAULT '0' COMMENT '排序',
  `level` tinyint(2) NOT NULL DEFAULT '0' COMMENT '级别',
  `price_min` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '价格区间-小',
  `price_mix` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '价格区间-大',
  `appointment_amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '预约消耗',
  `pay_amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '直购消耗',
  `profit_days` int(11) NOT NULL DEFAULT '0' COMMENT '收益天数',
  `profit_rate` decimal(8,6) NOT NULL DEFAULT '0.000000' COMMENT '收益率',
  `profit_coin` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '收益币种',
  `profit_coin_rate` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '收益币种对应利率',
  `img_url` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '图片地址',
  `upgrade_id` tinyint(2) NOT NULL DEFAULT '0' COMMENT '升级对应编号',
  `start_time` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '领养开始时间',
  `end_time` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '领养结束时间',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态 0停用 1启用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `pets` */

insert  into `pets`(`id`,`name`,`seque`,`level`,`price_min`,`price_mix`,`appointment_amount`,`pay_amount`,`profit_days`,`profit_rate`,`profit_coin`,`profit_coin_rate`,`img_url`,`upgrade_id`,`start_time`,`end_time`,`state`,`create_time`,`update_time`) values (1,'狗狗',1,1,10.00,30.00,2.00,4.00,2,0.100000,'0','1','https://dss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=517735934,3607000238&fm=5',2,'16:00','16:30',1,'2019-12-23 21:16:15','2019-12-25 10:39:20'),(2,'猫猫',2,2,31.00,70.00,2.00,4.00,3,0.150000,'0','1','https://dss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=517735934,3607000238&fm=5',3,'17:50','21:50',1,'2019-12-23 21:16:15','2019-12-25 21:04:34'),(3,'花花',3,3,71.00,100.00,3.00,6.00,5,0.200000,'0','1','https://dss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=517735934,3607000238&fm=5',4,'12:00','12:40',1,'2019-12-23 21:16:15','2019-12-25 10:39:32'),(4,'荣荣',4,4,101.00,150.00,2.00,4.00,2,0.200000,'0','1','https://dss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=517735934,3607000238&fm=5',5,'9:00','9:20',1,'2019-12-23 21:16:15','2019-12-25 10:39:38'),(5,'查查',5,5,151.00,200.00,5.00,10.00,3,0.350000,'0','1','https://dss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=517735934,3607000238&fm=5',1,'10:00','11:30',1,'2019-12-23 21:16:15','2019-12-25 11:05:56');

/*Table structure for table `pets_list` */

DROP TABLE IF EXISTS `pets_list`;

CREATE TABLE `pets_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '当前所属用户',
  `transfer_user_id` int(11) NOT NULL DEFAULT '-1' COMMENT '过户中用户id',
  `level` tinyint(2) NOT NULL DEFAULT '0' COMMENT '等级',
  `price` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '价格',
  `profit_days` int(11) NOT NULL DEFAULT '0' COMMENT '收益天数',
  `profit_rate` decimal(8,6) NOT NULL DEFAULT '0.000000' COMMENT '收益率',
  `profit_coin` varchar(255) NOT NULL DEFAULT '' COMMENT '收益币种',
  `profit_coin_rate` varchar(255) NOT NULL DEFAULT '' COMMENT '收益币种对应利率',
  `start_time` varchar(255) NOT NULL DEFAULT '' COMMENT '正式领养时间',
  `end_time` varchar(255) NOT NULL DEFAULT '' COMMENT '收益结束时间',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态 0收益中 1待转让 2转让中',
  `source_from` tinyint(2) NOT NULL DEFAULT '0' COMMENT '来源 0用户购买 1系统分配',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `pets_list` */

insert  into `pets_list`(`id`,`user_id`,`transfer_user_id`,`level`,`price`,`profit_days`,`profit_rate`,`profit_coin`,`profit_coin_rate`,`start_time`,`end_time`,`state`,`source_from`,`create_time`,`update_time`) values (1,1,-1,2,50.00,2,0.100000,'0','1','2019-12-29 17:13:26','2019-12-31 17:13:26',2,0,'2019-12-23 21:38:08','2020-01-02 18:16:09');

/*Table structure for table `pets_matching_list` */

DROP TABLE IF EXISTS `pets_matching_list`;

CREATE TABLE `pets_matching_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pet_list_id` tinyint(2) NOT NULL DEFAULT '0' COMMENT '宠物id',
  `level` tinyint(2) NOT NULL DEFAULT '0' COMMENT '等级',
  `buy_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '买用户id',
  `sale_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '卖用户id',
  `amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '消耗金币数量',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态 0预约中 1未付款 2未确认 3已完成 4已取消',
  `pay_time` varchar(255) NOT NULL DEFAULT '' COMMENT '付款时间',
  `inactive_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  `appointment_start_time` varchar(255) NOT NULL DEFAULT '' COMMENT '领养开始时间',
  `appointment_end_time` varchar(255) NOT NULL DEFAULT '' COMMENT '领养结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `pets_matching_list` */

insert  into `pets_matching_list`(`id`,`pet_list_id`,`level`,`buy_user_id`,`sale_user_id`,`amount`,`state`,`pay_time`,`inactive_time`,`appointment_start_time`,`appointment_end_time`,`create_time`,`update_time`) values (3,1,2,1,4,2.00,1,'2019-12-29 14:15:20','2019-12-29 06:45:21','2019-12-29 06:45:21','2019-12-29 06:45:21','2019-12-25 18:32:38','2020-01-02 18:04:35'),(4,1,2,1,4,2.00,3,'2019-12-29 14:15:20','2019-12-29 06:45:21','2019-12-29 06:45:21','2019-12-29 06:45:21','2019-12-25 18:32:38','2020-01-02 17:47:44');

/*Table structure for table `profit_record` */

DROP TABLE IF EXISTS `profit_record`;

CREATE TABLE `profit_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `amount` decimal(8,2) NOT NULL COMMENT '金额',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '类型 0个人收益 1动态收益',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `profit_record` */

insert  into `profit_record`(`id`,`user_id`,`amount`,`type`,`remark`,`create_time`,`update_time`) values (2,1,200.00,0,'个人收益','2019-12-30 18:55:34','2019-12-30 18:55:34'),(3,1,200.00,0,'个人收益','2019-12-30 18:55:37','2019-12-30 18:55:37'),(4,1,100.00,1,'个人收益','2019-12-30 18:55:40','2019-12-30 18:56:51'),(5,2,100.00,0,'个人收益','2019-12-30 18:55:42','2019-12-30 18:55:42');

/*Table structure for table `recharge` */

DROP TABLE IF EXISTS `recharge`;

CREATE TABLE `recharge` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `coin_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '币种',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '用户个人地址',
  `amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '数量',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态 0未处理 1成功 2失败',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `recharge` */

insert  into `recharge`(`id`,`user_id`,`coin_type`,`address`,`amount`,`remark`,`state`,`create_time`,`update_time`) values (2,1,1,'521313',10.00,'',0,'2019-12-31 17:07:24','2019-12-31 17:07:24'),(3,1,1,'521313',10.00,'',0,'2020-01-03 11:53:20','2020-01-03 11:53:20');

/*Table structure for table `sms_record` */

DROP TABLE IF EXISTS `sms_record`;

CREATE TABLE `sms_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `phone` varchar(15) NOT NULL COMMENT '手机号',
  `type` int(2) NOT NULL COMMENT '类型',
  `state` int(2) NOT NULL COMMENT '状态',
  `code` varchar(11) NOT NULL COMMENT '验证码',
  `times` int(11) NOT NULL COMMENT '时间',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `sms_record` */

insert  into `sms_record`(`id`,`phone`,`type`,`state`,`code`,`times`,`createtime`,`updatetime`) values (1,'13165373280',1,200,'303894',1,'2020-01-02 21:50:07','2020-01-02 21:50:07');

/*Table structure for table `sysparams` */

DROP TABLE IF EXISTS `sysparams`;

CREATE TABLE `sysparams` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `keyname` varchar(255) NOT NULL COMMENT 'key',
  `keyval` varchar(255) NOT NULL COMMENT 'value',
  `remark` varchar(255) NOT NULL COMMENT '备注',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `sysparams` */

insert  into `sysparams`(`id`,`keyname`,`keyval`,`remark`,`createtime`,`updatetime`) values (1,'WECHAT_ACCOUNT','aaaaaa','客服微信号','2019-12-25 14:10:52','2019-12-25 14:10:52'),(2,'WECHAT_IMG_URL','http://120.24.58.26/templets/default/images/logo.png','客服微信二维码','2019-12-25 14:11:13','2019-12-25 14:11:13'),(3,'PETS_MATCHING_CANCEL_TIME','30','领取自动撤销','2019-12-28 21:40:13','2019-12-28 21:40:13'),(4,'PETS_MATCHING_NO_PAY_CANCEL_TIME','30','未付款自动取消','2019-12-29 12:01:04','2019-12-29 12:01:04'),(5,'GOLD_HELP_DOC','金币获取说明','金币获取说明','2019-12-29 19:33:56','2019-12-29 19:33:56'),(6,'SMS_ONOFF','1','短信开关','2020-01-02 21:48:13','2020-01-02 21:48:13'),(7,'REGIST_ONOFF','1','注册开关','2020-01-02 21:49:04','2020-01-02 21:49:04');

/*Table structure for table `team_award_record` */

DROP TABLE IF EXISTS `team_award_record`;

CREATE TABLE `team_award_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `team_award_record` */

/*Table structure for table `team_record` */

DROP TABLE IF EXISTS `team_record`;

CREATE TABLE `team_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `refer_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人id',
  `amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
  `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '代数 1直推 2二代',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `team_record` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '账号',
  `phone` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '昵称',
  `order_pwd` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '' COMMENT '交易密码',
  `contribution` int(11) NOT NULL DEFAULT '0' COMMENT '贡献值',
  `team_level` tinyint(2) NOT NULL DEFAULT '0' COMMENT '团队等级 0新手 1精灵 2使者3元老',
  `person_level` tinyint(2) NOT NULL DEFAULT '0' COMMENT '个人等级 0未激活 1一级 2二级',
  `uuid` varchar(11) CHARACTER SET latin1 NOT NULL DEFAULT '0' COMMENT '邀请码',
  `refer_id` varchar(11) CHARACTER SET latin1 NOT NULL DEFAULT '0' COMMENT '推荐人邀请码',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态 0未激活 1已激活 2冻结 3拉黑',
  `id_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '实名状态 0未认证 1已通过',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`id`,`account`,`phone`,`password`,`nick_name`,`order_pwd`,`contribution`,`team_level`,`person_level`,`uuid`,`refer_id`,`state`,`id_status`,`create_time`,`update_time`) values (1,'13165373280','13165373280','c8a91fe68aba7605be1ca955f45e5ec0','13165373280','e10adc3949ba59abbe56e057f20f883e',0,0,0,'51839006','0',1,0,'2019-12-21 20:00:33','2019-12-29 13:27:45'),(2,'1','1','c4ca4238a0b923820dcc509a6f75849b','1','c4ca4238a0b923820dcc509a6f75849b',0,0,0,'0','0',0,0,'2019-12-19 18:32:46','2019-12-19 19:01:41'),(4,'13165373281','13165373281','c8a91fe68aba7605be1ca955f45e5ec0','13165373281','',0,0,0,'89810475','0',0,0,'2019-12-21 20:02:12','2019-12-21 20:02:12'),(5,'13165373282','13165373282','c8a91fe68aba7605be1ca955f45e5ec0','13165373282','',0,0,0,'60020974','0',0,0,'2019-12-21 20:06:08','2019-12-21 20:06:08'),(6,'13165373283','13165373283','c8a91fe68aba7605be1ca955f45e5ec0','13165373283','',0,0,0,'19216441','0',0,0,'2019-12-21 20:07:15','2019-12-21 20:07:15'),(7,'13165373284','13165373284','c8a91fe68aba7605be1ca955f45e5ec0','13165373284','',0,0,0,'95167305','19216441',0,0,'2019-12-21 20:13:45','2019-12-21 20:13:45'),(8,'13165373287','13165373287','c8a91fe68aba7605be1ca955f45e5ec0','13165373287','',0,0,0,'75927800','89810475',0,0,'2019-12-21 20:43:19','2019-12-21 20:43:19');

/*Table structure for table `withdraw` */

DROP TABLE IF EXISTS `withdraw`;

CREATE TABLE `withdraw` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `coin_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '币种',
  `amount` decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '提现金额',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态 0未处理 1成功 2失败',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `withdraw` */

insert  into `withdraw`(`id`,`user_id`,`coin_type`,`amount`,`state`,`remark`,`create_time`,`update_time`) values (2,1,1,10.00,0,'提现发起','2019-12-31 15:51:34','2019-12-31 15:51:34');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
