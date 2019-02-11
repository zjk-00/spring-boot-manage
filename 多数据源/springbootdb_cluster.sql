/*
Navicat MySQL Data Transfer

Source Server         : MySQL1
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : springbootdb_cluster

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-10-18 13:27:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `city`
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '城市编号',
  `province_id` int(10) unsigned NOT NULL COMMENT '省份编号',
  `city_name` varchar(25) DEFAULT NULL COMMENT '城市名称',
  `description` varchar(25) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('1', '1', '温岭市', 'BYSocket 的家在温岭。');
INSERT INTO `city` VALUES ('2', '2', '大连市', '梦在大连');
INSERT INTO `city` VALUES ('3', '3', '三亚', '梦在三亚');
INSERT INTO `city` VALUES ('4', '4', '成都', '梦在成都');
INSERT INTO `city` VALUES ('6', '6', '成都', '梦在成都');
INSERT INTO `city` VALUES ('10', '5', '三亚', '梦在三亚');
INSERT INTO `city` VALUES ('11', '5', '三亚', '梦在三亚');
INSERT INTO `city` VALUES ('12', '5', '三亚', '梦在三亚');
INSERT INTO `city` VALUES ('13', '5', '三亚', '梦在三亚');
INSERT INTO `city` VALUES ('14', '5', '三亚', '梦在三亚');
INSERT INTO `city` VALUES ('15', '5', '三亚', '梦在三亚');
INSERT INTO `city` VALUES ('16', '5', '三亚', '梦在三亚');
INSERT INTO `city` VALUES ('17', '5', '三亚', '梦在三亚');
INSERT INTO `city` VALUES ('18', '5', '三亚', '梦在三亚');
INSERT INTO `city` VALUES ('19', '12', '郑州市', 'zhengzhou');
INSERT INTO `city` VALUES ('20', '11', '唐山市', 'tangshan');
INSERT INTO `city` VALUES ('21', '11', '唐山市', 'tangshan');
INSERT INTO `city` VALUES ('22', '11', '唐山市', 'tangshan');
INSERT INTO `city` VALUES ('23', '11', '唐山市', 'tangshan');
INSERT INTO `city` VALUES ('24', '11', '唐山市', 'tangshan');
INSERT INTO `city` VALUES ('25', '11', '唐山市', 'tangshan');
INSERT INTO `city` VALUES ('26', '11', '唐山市', 'tangshan');
INSERT INTO `city` VALUES ('27', '11', '唐山市', 'tangshan');
INSERT INTO `city` VALUES ('28', '11', '唐山市', 'tangshan');
INSERT INTO `city` VALUES ('29', '11', '唐山市', 'tangshan');
INSERT INTO `city` VALUES ('30', '11', '唐山市', 'tangshan');
