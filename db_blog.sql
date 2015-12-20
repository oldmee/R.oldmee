/*
Navicat MySQL Data Transfer

Source Server         : MYSQL
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : db_blog

Target Server Type    : MYSQL
Target Server Version : 50599
File Encoding         : 65001

Date: 2015-12-09 08:33:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
`admin_id`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
`admin_name`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`admin_password`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
PRIMARY KEY (`admin_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=gb2312 COLLATE=gb2312_chinese_ci
AUTO_INCREMENT=2

;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
BEGIN;
INSERT INTO `tb_admin` VALUES ('1', 'admin', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
`article_id`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
`articleType_id`  int(10) UNSIGNED NOT NULL ,
`title`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`content`  varchar(5000) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`sdTime`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`createFrom`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`info`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`count`  int(10) UNSIGNED NOT NULL ,
`master_id`  int(10) UNSIGNED NOT NULL ,
PRIMARY KEY (`article_id`),
FOREIGN KEY (`articleType_id`) REFERENCES `tb_articletype` (`articleType_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
FOREIGN KEY (`master_id`) REFERENCES `tb_master` (`master_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
INDEX `FK_tb_article_1` (`articleType_id`) USING BTREE ,
INDEX `FK_tb_article_2` (`master_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=gb2312 COLLATE=gb2312_chinese_ci
COMMENT='InnoDB free: 4096 kB; (`articleType_id`) REFER `db_blog/tb_articletype`(`article'
AUTO_INCREMENT=15

;

-- ----------------------------
-- Records of tb_article
-- ----------------------------
BEGIN;
INSERT INTO `tb_article` VALUES ('14', '2', '轻博客2.0版本面试啦', '1.实现评论功能，可以有一定的互动了。<br>2.逻辑不合理的地方大部分能改的改，能删则删。<br>3.无聊会发布一些日志，欢迎交流。', '2015年12月09日 00:23:55', '原创', '2.0版本', '6', '4');
COMMIT;

-- ----------------------------
-- Table structure for tb_articletype
-- ----------------------------
DROP TABLE IF EXISTS `tb_articletype`;
CREATE TABLE `tb_articletype` (
`articleType_id`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
`typeName`  varchar(20) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`typeInfo`  varchar(100) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`master_id`  int(10) UNSIGNED NOT NULL ,
PRIMARY KEY (`articleType_id`),
FOREIGN KEY (`master_id`) REFERENCES `tb_master` (`master_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
INDEX `FK_tb_articletype_1` (`master_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=gb2312 COLLATE=gb2312_chinese_ci
COMMENT='InnoDB free: 4096 kB; (`master_id`) REFER `db_blog/tb_master`(`master_id`) ON DE'
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of tb_articletype
-- ----------------------------
BEGIN;
INSERT INTO `tb_articletype` VALUES ('2', '我的电脑', '不是你的', '4');
COMMIT;

-- ----------------------------
-- Table structure for tb_authorreview
-- ----------------------------
DROP TABLE IF EXISTS `tb_authorreview`;
CREATE TABLE `tb_authorreview` (
`authorReview_id`  int(11) NOT NULL AUTO_INCREMENT ,
`review_id`  int(255) NOT NULL ,
`authorReplyContent`  text CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`reSdTime`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`master_id`  int(11) NOT NULL ,
PRIMARY KEY (`authorReview_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=gb2312 COLLATE=gb2312_chinese_ci
AUTO_INCREMENT=14

;

-- ----------------------------
-- Records of tb_authorreview
-- ----------------------------
BEGIN;
INSERT INTO `tb_authorreview` VALUES ('13', '27', '作者回复：笑啥', '2015年12月09日 00:49:05', '20');
COMMIT;

-- ----------------------------
-- Table structure for tb_master
-- ----------------------------
DROP TABLE IF EXISTS `tb_master`;
CREATE TABLE `tb_master` (
`master_id`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
`master_name`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`master_password`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`master_sex`  varchar(2) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`master_oicq`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`master_state`  tinyint(1) NOT NULL ,
PRIMARY KEY (`master_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=gb2312 COLLATE=gb2312_chinese_ci
AUTO_INCREMENT=21

;

-- ----------------------------
-- Records of tb_master
-- ----------------------------
BEGIN;
INSERT INTO `tb_master` VALUES ('4', 'kevin_lee', '123456', '男', '265315956', '1'), ('6', 'tom_cy', '123456', '女', '265232254', '1'), ('7', 'a', 'aaaaaa', '男', 'aaaaa', '0'), ('8', 'b', 'bbbbbb', '男', 'bbbbbb', '0'), ('9', 'c', 'cccccc', '女', 'cccccc', '0'), ('10', 'ff', 'ffffff', '男', 'ffff', '0'), ('11', 'bbbbb', 'bbbbbb', '男', 'bbbbb', '0'), ('12', 'xx', 'xxxxxx', '男', 'xxxx', '0'), ('13', 'vv', 'vvvvvvv', '男', 'vvvvv', '0'), ('14', 'ii', 'ii', '男', 'iiiiii', '0'), ('15', '55', '55', '男', '555', '0'), ('16', '11', '11', '男', '111', '0'), ('17', 'oldmee', '123456', '男', '1', '0'), ('18', 'jack', '123456', '男', '1', '0'), ('19', 'mom', '111111', '男', '1', '0'), ('20', 'AA', '111111', '男', '1', '0');
COMMIT;

-- ----------------------------
-- Table structure for tb_review
-- ----------------------------
DROP TABLE IF EXISTS `tb_review`;
CREATE TABLE `tb_review` (
`review_id`  int(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
`article_id`  int(10) UNSIGNED NOT NULL ,
`reAuthor`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`reContent`  text CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
`reSdTime`  varchar(45) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL ,
PRIMARY KEY (`review_id`),
FOREIGN KEY (`article_id`) REFERENCES `tb_article` (`article_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
INDEX `FK_tb_review_1` (`article_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=gb2312 COLLATE=gb2312_chinese_ci
COMMENT='InnoDB free: 4096 kB; (`article_id`) REFER `db_blog/tb_article`(`article_id`) ON'
AUTO_INCREMENT=30

;

-- ----------------------------
-- Records of tb_review
-- ----------------------------
BEGIN;
INSERT INTO `tb_review` VALUES ('27', '14', 'AA', '哈哈&#128516;', '2015年12月09日 00:43:30'), ('28', '14', 'AA', '哈哈&#128516;', '2015年12月09日 00:43:53'), ('29', '14', 'kevin_lee', '|&#160;哈哈&#128516;&#160;&#160;&#160;&#160;&#160;&#160;from:AA<br><br>作者回复：笑啥', '2015年12月09日 00:49:05');
COMMIT;

-- ----------------------------
-- Auto increment value for tb_admin
-- ----------------------------
ALTER TABLE `tb_admin` AUTO_INCREMENT=2;

-- ----------------------------
-- Auto increment value for tb_article
-- ----------------------------
ALTER TABLE `tb_article` AUTO_INCREMENT=15;

-- ----------------------------
-- Auto increment value for tb_articletype
-- ----------------------------
ALTER TABLE `tb_articletype` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for tb_authorreview
-- ----------------------------
ALTER TABLE `tb_authorreview` AUTO_INCREMENT=14;

-- ----------------------------
-- Auto increment value for tb_master
-- ----------------------------
ALTER TABLE `tb_master` AUTO_INCREMENT=21;

-- ----------------------------
-- Auto increment value for tb_review
-- ----------------------------
ALTER TABLE `tb_review` AUTO_INCREMENT=30;
