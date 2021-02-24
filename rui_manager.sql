/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : rui_manager

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 24/02/2021 14:03:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `activityid` varchar(20) NOT NULL,
  `activityname` varchar(10) NOT NULL,
  `peopleamount` int(10) NOT NULL,
  `starttime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `overtime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`activityid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `departmentid` varchar(20) NOT NULL,
  `departmentname` varchar(10) NOT NULL,
  `ministerid` varchar(20) NOT NULL,
  `chairmanid` varchar(20) NOT NULL,
  `peoplenumber` int(20) NOT NULL,
  `introduction` varchar(100) NOT NULL,
  `createtime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `deletetime` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`departmentid`),
  KEY `ministerid` (`ministerid`),
  KEY `chairmanid` (`chairmanid`),
  CONSTRAINT `department_ibfk_1` FOREIGN KEY (`ministerid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `department_ibfk_2` FOREIGN KEY (`chairmanid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for judge
-- ----------------------------
DROP TABLE IF EXISTS `judge`;
CREATE TABLE `judge` (
  `judgeid` varchar(20) NOT NULL,
  `judgetype` int(10) NOT NULL,
  `judgecontentid` varchar(10) NOT NULL,
  `isjudge` int(1) DEFAULT NULL,
  PRIMARY KEY (`judgeid`),
  CONSTRAINT `judgeid_jk` FOREIGN KEY (`judgeid`) REFERENCES `activity` (`activityid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `informid` varchar(20) NOT NULL,
  `informerid` varchar(20) NOT NULL,
  `informtype` varchar(20) NOT NULL,
  `userid` varchar(10) NOT NULL,
  `checknum` int(50) NOT NULL,
  PRIMARY KEY (`informid`),
  KEY `informerid` (`informerid`),
  KEY `userid` (`userid`),
  CONSTRAINT `informerid` FOREIGN KEY (`informerid`) REFERENCES `activity` (`activityid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for qingjia
-- ----------------------------
DROP TABLE IF EXISTS `qingjia`;
CREATE TABLE `qingjia` (
  `vacateid` varchar(20) NOT NULL,
  `vacateperson` varchar(19) NOT NULL,
  `persondepartment` varchar(19) NOT NULL,
  `vacatereason` varchar(19) NOT NULL,
  `leavetime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `backtime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `judgeid` varchar(19) NOT NULL,
  PRIMARY KEY (`vacateid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `pwd` varchar(100) NOT NULL,
  `branch` varchar(20) NOT NULL,
  `class` varchar(20) NOT NULL,
  `sex` varchar(5) NOT NULL,
  `phonenumber` int(11) NOT NULL,
  `email` varchar(20) DEFAULT NULL,
  `rank` varchar(5) NOT NULL,
  `departments` varchar(20) NOT NULL,
  `leavetime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `appointment_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `extr` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
