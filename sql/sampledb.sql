/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : sampledb

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 25/06/2019 21:40:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_function
-- ----------------------------
DROP TABLE IF EXISTS `tbl_function`;
CREATE TABLE `tbl_function` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `func_name` varchar(255) DEFAULT NULL,
  `func_url` varchar(255) DEFAULT NULL,
  `parent_id` int(10) DEFAULT NULL,
  `func_level` int(10) DEFAULT NULL,
  `show_order` int(10) DEFAULT NULL,
  `enable` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_function
-- ----------------------------
BEGIN;
INSERT INTO `tbl_function` VALUES (1, '记事管理', 'note', NULL, 1, 0, 0);
INSERT INTO `tbl_function` VALUES (2, '备忘', 'memo', 1, 2, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for tbl_memo
-- ----------------------------
DROP TABLE IF EXISTS `tbl_memo`;
CREATE TABLE `tbl_memo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `memo_end_time` datetime(6) DEFAULT NULL COMMENT '备忘截止时间',
  `priority` int(10) DEFAULT NULL COMMENT '优先级',
  `matter` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '事项',
  `memo_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备忘内容',
  `input_persion` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '录入人',
  `input_time` datetime(6) DEFAULT NULL COMMENT '录入时间',
  `remind` int(20) DEFAULT '0' COMMENT '提前提醒时间，单位小时',
  `status` int(10) DEFAULT '1' COMMENT '1是未完成\n2是完成\n0是已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_memo
-- ----------------------------
BEGIN;
INSERT INTO `tbl_memo` VALUES (2, '2019-06-24 00:00:00.000000', 2, '生活', '铲屎', '韩可', '2019-06-22 00:00:00.000000', 0, 0);
INSERT INTO `tbl_memo` VALUES (3, '2019-06-30 00:00:00.000000', 2, '生活', '吃屎', '韩可', '2019-06-22 00:00:00.000000', 0, 0);
INSERT INTO `tbl_memo` VALUES (4, '2019-06-30 00:00:00.000000', 1, '学习', '搞事情', '帆爸爸', '2019-06-22 00:00:00.000000', 0, 0);
INSERT INTO `tbl_memo` VALUES (5, '2019-06-24 00:00:00.000000', 1, '工作', '测试', '杨帆', '2019-06-22 00:00:00.000000', 0, 2);
INSERT INTO `tbl_memo` VALUES (7, '2019-06-24 20:06:53.000000', 1, '工作', '抓捕外星人 野兽鲍里斯', '史密斯', '2019-06-22 20:36:04.000000', 0, 2);
INSERT INTO `tbl_memo` VALUES (8, '2019-06-23 08:06:43.000000', 3, '生活', '剪头', '杨帆', '2019-06-23 08:49:10.000000', 0, 2);
INSERT INTO `tbl_memo` VALUES (16, '2019-06-23 14:06:05.000000', 1, '学习', '测试', '杨帆', '2019-06-23 14:03:29.000000', 0, 2);
INSERT INTO `tbl_memo` VALUES (17, '2019-06-24 14:06:16.000000', 2, '工作', '测试提醒时间', '杨安', '2019-06-23 14:45:29.000000', 6, 2);
INSERT INTO `tbl_memo` VALUES (18, '2019-06-23 23:06:20.000000', 3, '生活', '吃药', '杨帆', '2019-06-23 15:34:25.000000', 1, 2);
INSERT INTO `tbl_memo` VALUES (19, '2019-06-30 22:06:13.000000', 1, '学习', '完成到期提醒功能', '杨帆', '2019-06-24 22:54:59.000000', 24, 1);
COMMIT;

-- ----------------------------
-- Table structure for tbl_permission
-- ----------------------------
DROP TABLE IF EXISTS `tbl_permission`;
CREATE TABLE `tbl_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `func_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `perm_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_permission
-- ----------------------------
BEGIN;
INSERT INTO `tbl_permission` VALUES (1, 1, 1, NULL);
INSERT INTO `tbl_permission` VALUES (2, 2, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tbl_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role`;
CREATE TABLE `tbl_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_role
-- ----------------------------
BEGIN;
INSERT INTO `tbl_role` VALUES (1, 1, '超级管理员', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户登陆id',
  `user_pass` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登陆密码',
  `user_role` int(10) DEFAULT NULL COMMENT '角色，关联tbl_role表',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户昵称',
  `rge_date` datetime DEFAULT NULL COMMENT '注册日期',
  `last_login` datetime DEFAULT NULL COMMENT '上次登录日期',
  `login_times` int(10) DEFAULT NULL COMMENT '登陆次数',
  `login_status` int(2) DEFAULT NULL COMMENT '登陆状态',
  `login_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登陆ip',
  `enabled` int(2) DEFAULT NULL COMMENT '可用状态',
  `idx_url` varchar(255) DEFAULT NULL COMMENT '主页面',
  `email_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `family_id` int(10) DEFAULT NULL COMMENT '家庭id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
BEGIN;
INSERT INTO `tbl_user` VALUES (1, 'yf', '123456', 1, '杨帆', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '18701474327@163.com', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tbl_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_roles`;
CREATE TABLE `tbl_user_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '用户id，对应用户登陆id',
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user_roles
-- ----------------------------
BEGIN;
INSERT INTO `tbl_user_roles` VALUES (1, 'yf', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
