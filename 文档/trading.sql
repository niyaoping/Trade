/*
Navicat MySQL Data Transfer

Source Server         : 39.100.198.249
Source Server Version : 50729
Source Host           : 39.100.198.249:8081
Source Database       : trading

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-09-14 07:20:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `good`
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` varchar(200) NOT NULL COMMENT '商品名',
  `stock` int(11) NOT NULL COMMENT '库存数量',
  `price` decimal(10,2) NOT NULL COMMENT '商品单价',
  `weight` decimal(10,2) NOT NULL COMMENT '商品单位重量',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '记录软删除标志，默认是0，表示未删除',
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录创建日期,使用timestamp主要考虑，如果用于全球各地，那么该数据类型可以根据当地时区自动转换时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf32 COMMENT='商品表';

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES ('1', '多功能电瓶车', '10', '1234.00', '1000.00', '0', '2020-09-11 13:13:28');
INSERT INTO `good` VALUES ('2', '耳机线', '10', '26.00', '1.00', '0', '2020-09-11 13:13:31');

-- ----------------------------
-- Table structure for `infra_info`
-- ----------------------------
DROP TABLE IF EXISTS `infra_info`;
CREATE TABLE `infra_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '交易记录软删除标志，默认是0，表示未删除',
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录创建日期',
  `category` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0表示物流公司名称，1表示运输方式,',
  `name` varchar(100) NOT NULL COMMENT '基础信息名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='基础信息维护表';

-- ----------------------------
-- Records of infra_info
-- ----------------------------
INSERT INTO `infra_info` VALUES ('1', '0', '2020-09-11 09:45:26', '0', '韵达快递');
INSERT INTO `infra_info` VALUES ('2', '0', '2020-09-11 09:45:35', '0', '中通快递');
INSERT INTO `infra_info` VALUES ('3', '0', '2020-09-11 09:45:45', '0', '申通快递');
INSERT INTO `infra_info` VALUES ('4', '0', '2020-09-11 09:45:54', '1', '飞机');
INSERT INTO `infra_info` VALUES ('5', '0', '2020-09-11 09:46:01', '1', '轮船');
INSERT INTO `infra_info` VALUES ('6', '0', '2020-09-11 09:46:08', '1', '火车');
INSERT INTO `infra_info` VALUES ('7', '0', '2020-09-11 09:46:14', '1', '卡车');

-- ----------------------------
-- Table structure for `trade`
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '交易记录id',
  `good_id` int(11) NOT NULL COMMENT '供应商的商品id',
  `total_price` decimal(10,0) NOT NULL COMMENT '总交易金额',
  `total_quantity` int(11) NOT NULL COMMENT '货物总数量',
  `total_weight` decimal(10,0) NOT NULL COMMENT '总运输重量',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '交易记录软删除标志，默认是0，表示未删除',
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录创建日期',
  PRIMARY KEY (`id`),
  KEY `index_good_id` (`good_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='商品交易记录主表';

-- ----------------------------
-- Records of trade
-- ----------------------------
INSERT INTO `trade` VALUES ('8', '1', '1234', '990', '12345', '0', '2020-09-13 23:18:24');

-- ----------------------------
-- Table structure for `transportation`
-- ----------------------------
DROP TABLE IF EXISTS `transportation`;
CREATE TABLE `transportation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '运输记录id',
  `parent_id` int(11) NOT NULL DEFAULT '-1',
  `trade_id` int(11) NOT NULL COMMENT '交易记录表id',
  `transportation_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发货时间，使用timestamp主要考虑，如果用于全球各地，那么该数据类型可以根据当地时区自动转换时间',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '运输状态，比如0表示准备中，1表示发货，2表示运输中等',
  `quantity` int(10) NOT NULL COMMENT '本次运输的数量',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '交易记录软删除标志，默认是0，表示未删除',
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录创建日期',
  `express_id` int(11) NOT NULL DEFAULT '-1' COMMENT '物流公司id',
  `express_no` varchar(30) NOT NULL DEFAULT '''NULL''' COMMENT '物流单号',
  PRIMARY KEY (`id`),
  KEY `index_trade_id` (`trade_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='运输记录表';

-- ----------------------------
-- Records of transportation
-- ----------------------------
INSERT INTO `transportation` VALUES ('31', '-1', '8', '2020-09-13 15:09:28', '0', '400', '1', '2020-09-13 15:09:28', '-1', '\'NULL\'');
INSERT INTO `transportation` VALUES ('32', '31', '8', '2020-09-13 15:10:13', '0', '298', '0', '2020-09-13 15:10:13', '-1', '\'NULL\'');
INSERT INTO `transportation` VALUES ('33', '31', '8', '2020-09-13 15:10:13', '0', '50', '1', '2020-09-13 15:10:13', '-1', '\'NULL\'');
INSERT INTO `transportation` VALUES ('34', '31', '8', '2020-09-13 15:10:13', '0', '496', '0', '2020-09-13 15:10:13', '-1', '\'NULL\'');
INSERT INTO `transportation` VALUES ('35', '33', '8', '2020-09-13 15:11:22', '0', '25', '1', '2020-09-13 16:55:46', '-1', '\'NULL\'');
INSERT INTO `transportation` VALUES ('36', '33', '8', '2020-09-13 15:11:22', '0', '25', '1', '2020-09-13 16:55:46', '-1', '\'NULL\'');
INSERT INTO `transportation` VALUES ('37', '-1', '8', '2020-09-13 16:55:46', '0', '196', '0', '2020-09-13 16:55:46', '-1', '\'NULL\'');
