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
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rdyl_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `rdyl_db`;

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL DEFAULT '' COMMENT '账号',
  `phone` varchar(255) NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(255) NOT NULL DEFAULT '' COMMENT '昵称',
  `order_pwd` varchar(255) NOT NULL DEFAULT '' COMMENT '交易密码',
  `contribution` int(11) NOT NULL DEFAULT '0' COMMENT '贡献值',
  `level` tinyint(2) NOT NULL DEFAULT '0' COMMENT '等级',
  `uuid` tinyint(2) NOT NULL DEFAULT '0' COMMENT '邀请码',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`id`,`account`,`phone`,`password`,`nick_name`,`order_pwd`,`contribution`,`level`,`uuid`,`state`,`create_time`,`update_time`) values (2,'1','1','c4ca4238a0b923820dcc509a6f75849b','1','c4ca4238a0b923820dcc509a6f75849b',0,0,0,0,'2019-12-19 18:32:46','2019-12-19 19:01:41');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
