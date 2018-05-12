/*
 Navicat Premium Data Transfer

 Source Server         : Mysql57_3357
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3357
 Source Schema         : mybatis_demo

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 12/05/2018 14:55:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '昆明王五', NULL, '2', '云南省');
INSERT INTO `user` VALUES (10, '张三', '2014-07-10', '1', '北京市');
INSERT INTO `user` VALUES (16, '张小明', NULL, '1', '云南省昆明');
INSERT INTO `user` VALUES (22, '陈小明', NULL, '1', '云南省昆明');
INSERT INTO `user` VALUES (24, '张三丰', NULL, '1', '四川成都');
INSERT INTO `user` VALUES (25, '陈小明', NULL, '1', '云南省昆明');
INSERT INTO `user` VALUES (26, '深圳王五', NULL, NULL, '广东深圳');

SET FOREIGN_KEY_CHECKS = 1;
