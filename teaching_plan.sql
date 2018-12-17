/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : teaching_plan

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-03-02 15:20:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account_role_t`
-- ----------------------------
DROP TABLE IF EXISTS `account_role_t`;
CREATE TABLE `account_role_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `role_id` int(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `account_role_t_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account_t` (`id`),
  CONSTRAINT `account_role_t_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role_t` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_role_t
-- ----------------------------
INSERT INTO `account_role_t` VALUES ('1', '1', '0');
INSERT INTO `account_role_t` VALUES ('3', '31', '2');
INSERT INTO `account_role_t` VALUES ('4', '8', '1');
INSERT INTO `account_role_t` VALUES ('5', '32', '1');
INSERT INTO `account_role_t` VALUES ('6', '33', '3');
INSERT INTO `account_role_t` VALUES ('7', '34', '3');
INSERT INTO `account_role_t` VALUES ('8', '35', '1');
INSERT INTO `account_role_t` VALUES ('9', '36', '1');

-- ----------------------------
-- Table structure for `account_t`
-- ----------------------------
DROP TABLE IF EXISTS `account_t`;
CREATE TABLE `account_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(16) NOT NULL COMMENT '账号',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态：0-关闭，1-正常，2-待审批，3-审批驳回',
  `type` int(1) NOT NULL DEFAULT '3' COMMENT '0-管理员，1-学校，2-教师，3-学员',
  `mod_default` int(1) NOT NULL DEFAULT '0' COMMENT '0-初始密码没被修改，1-初始密码已修改',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='账号表';

-- ----------------------------
-- Records of account_t
-- ----------------------------
INSERT INTO `account_t` VALUES ('1', 'admin', 'ISMvKXpXpadDiUoOSoAfww==', '1', '0', '1');
INSERT INTO `account_t` VALUES ('2', 'jiaoshi', 'UdmqM37ofye5NcAnln6xqA==', '0', '2', '1');
INSERT INTO `account_t` VALUES ('8', 'test', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '1', '1');
INSERT INTO `account_t` VALUES ('9', 'jjjj', '4QrcOUm6Wau+VuBX8g+IPg==', '3', '1', '1');
INSERT INTO `account_t` VALUES ('10', '1111', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '1', '1');
INSERT INTO `account_t` VALUES ('11', '31231', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '1', '1');
INSERT INTO `account_t` VALUES ('12', '12312', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '1', '1');
INSERT INTO `account_t` VALUES ('14', '111', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '1', '1');
INSERT INTO `account_t` VALUES ('15', '222', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '1', '1');
INSERT INTO `account_t` VALUES ('16', '333', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '1', '1');
INSERT INTO `account_t` VALUES ('18', 'jiaoshi1', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '2', '1');
INSERT INTO `account_t` VALUES ('21', '444', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '2', '1');
INSERT INTO `account_t` VALUES ('22', '1', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '2', '1');
INSERT INTO `account_t` VALUES ('23', '2', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '2', '1');
INSERT INTO `account_t` VALUES ('24', '3', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '2', '1');
INSERT INTO `account_t` VALUES ('25', 'jiaoshi添加', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '2', '1');
INSERT INTO `account_t` VALUES ('26', '123456', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '2', '1');
INSERT INTO `account_t` VALUES ('28', 'xuesheng', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '3', '1');
INSERT INTO `account_t` VALUES ('29', 'xuesheng2', '4QrcOUm6Wau+VuBX8g+IPg==', '0', '3', '1');
INSERT INTO `account_t` VALUES ('30', '123', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '3', '1');
INSERT INTO `account_t` VALUES ('31', '4', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '2', '1');
INSERT INTO `account_t` VALUES ('32', 'test2', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '1', '1');
INSERT INTO `account_t` VALUES ('33', '5', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '3', '1');
INSERT INTO `account_t` VALUES ('34', 'rest', '4QrcOUm6Wau+VuBX8g+IPg==', '1', '3', '1');
INSERT INTO `account_t` VALUES ('35', 'aaa', 'R7zlx09Yn0hn29V+nKn4CA==', '1', '1', '1');
INSERT INTO `account_t` VALUES ('36', 'a', 'DMF1ucDxtqgxw5niaXcmYQ==', '1', '1', '1');

-- ----------------------------
-- Table structure for `category_t`
-- ----------------------------
DROP TABLE IF EXISTS `category_t`;
CREATE TABLE `category_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `text` varchar(16) NOT NULL COMMENT '类型名称',
  `type` int(1) DEFAULT NULL COMMENT '类型：1-教案，2-文件',
  `sub_type` int(1) DEFAULT NULL COMMENT '1-基础课程，2-特色课程，3-场景，4-主题/1-招生，2-教学，3-管理',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类id',
  `cover` varchar(64) DEFAULT NULL COMMENT '封面名称',
  `file_path` varchar(255) DEFAULT NULL COMMENT '封面路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category_t
-- ----------------------------
INSERT INTO `category_t` VALUES ('1', '课程', '1', null, null, null, null);
INSERT INTO `category_t` VALUES ('32', '场景', '1', '3', null, null, null);
INSERT INTO `category_t` VALUES ('33', '主题', '1', '4', null, null, null);
INSERT INTO `category_t` VALUES ('34', '基础课程', null, '1', '1', null, null);
INSERT INTO `category_t` VALUES ('35', '特色课程', null, '2', '1', null, null);
INSERT INTO `category_t` VALUES ('36', '招生', '2', '1', null, null, null);
INSERT INTO `category_t` VALUES ('37', '教学', '2', '2', null, null, null);
INSERT INTO `category_t` VALUES ('38', '管理', '2', '3', null, null, null);
INSERT INTO `category_t` VALUES ('39', '大学', null, null, '34', 'QQ图片20171227150705.jpg', '/file/cover/b1b771a8-8f56-4b46-99a0-da022a9c3347.jpg');
INSERT INTO `category_t` VALUES ('40', '招生子类别', null, null, '36', null, null);
INSERT INTO `category_t` VALUES ('42', '室内', null, null, '32', '', '');
INSERT INTO `category_t` VALUES ('43', '室外', null, null, '32', null, null);
INSERT INTO `category_t` VALUES ('44', '健身', null, null, '33', null, null);
INSERT INTO `category_t` VALUES ('45', '游泳', null, null, '33', null, null);
INSERT INTO `category_t` VALUES ('46', '特色大学', null, null, '35', 'cas流程图.png', '/file/cover/32fd7446-e846-4d01-bf28-831247f00702.png');
INSERT INTO `category_t` VALUES ('47', '中学', null, null, '34', 'RTX截图未命名.png', '/file/cover/b1c4e988-3447-40ea-949e-feacfcf365c2.png');
INSERT INTO `category_t` VALUES ('48', '小学', null, null, '34', '胡夏合照.jpg', '/file/cover/d9a628a4-7d3f-4e02-acd5-7ea0a09d2b0f.jpg');
INSERT INTO `category_t` VALUES ('49', '幼儿园', null, null, '34', '胡夏在玩游戏.jpg', '/file/cover/38524a4c-6b93-405b-a8fa-d9828081f499.jpg');
INSERT INTO `category_t` VALUES ('50', '特色中学', null, null, '35', '胡夏和腾讯老总.jpg', '/file/cover/617ca339-424a-46d7-a500-7bf61cc4b67e.jpg');
INSERT INTO `category_t` VALUES ('51', '教学文件', null, null, '37', null, null);

-- ----------------------------
-- Table structure for `classes_t`
-- ----------------------------
DROP TABLE IF EXISTS `classes_t`;
CREATE TABLE `classes_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '班级名称',
  `school_id` int(11) NOT NULL COMMENT '绑定学校id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of classes_t
-- ----------------------------
INSERT INTO `classes_t` VALUES ('1', '一班', '1', '测试22222');
INSERT INTO `classes_t` VALUES ('2', '二班', '2', '222');
INSERT INTO `classes_t` VALUES ('3', '三班', '2', 'ddd');
INSERT INTO `classes_t` VALUES ('4', '二班', '1', 'q');
INSERT INTO `classes_t` VALUES ('5', '一班', '6', '222');
INSERT INTO `classes_t` VALUES ('6', '111', '0', '111');
INSERT INTO `classes_t` VALUES ('7', '1112', '0', '1111');
INSERT INTO `classes_t` VALUES ('8', '22222', '10', '11111');
INSERT INTO `classes_t` VALUES ('9', '2222', '10', '1111');
INSERT INTO `classes_t` VALUES ('10', '三班', '1', '333');

-- ----------------------------
-- Table structure for `code_t`
-- ----------------------------
DROP TABLE IF EXISTS `code_t`;
CREATE TABLE `code_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(16) NOT NULL,
  `code` int(6) NOT NULL,
  `expire_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of code_t
-- ----------------------------
INSERT INTO `code_t` VALUES ('1', '111', '197552', '2018-02-12 15:46:12');
INSERT INTO `code_t` VALUES ('2', 'rest', '164962', '2018-01-01 00:00:00');
INSERT INTO `code_t` VALUES ('3', 'rest', '737216', '2018-01-01 00:00:00');
INSERT INTO `code_t` VALUES ('4', 'rest', '513065', '2018-01-01 00:00:00');
INSERT INTO `code_t` VALUES ('5', '13049492251', '885459', '2018-02-26 11:34:04');
INSERT INTO `code_t` VALUES ('6', '13049492251', '920242', '2018-02-26 11:39:41');
INSERT INTO `code_t` VALUES ('7', '13049492251', '373796', '2018-02-26 12:21:33');
INSERT INTO `code_t` VALUES ('8', '13049492251', '507632', '2018-02-26 13:09:34');
INSERT INTO `code_t` VALUES ('9', '13049492251', '310031', '2018-02-26 13:14:21');
INSERT INTO `code_t` VALUES ('10', '13049492251', '750836', '2018-02-26 13:15:06');
INSERT INTO `code_t` VALUES ('11', '13049492251', '191640', '2018-02-26 13:38:31');
INSERT INTO `code_t` VALUES ('12', '13049492251', '884303', '2018-02-26 13:41:51');
INSERT INTO `code_t` VALUES ('13', '13049492251', '77662', '2018-02-26 13:44:51');
INSERT INTO `code_t` VALUES ('14', '13049492251', '214779', '2018-02-26 13:46:11');
INSERT INTO `code_t` VALUES ('15', '13049492251', '711421', '2018-02-26 13:48:01');
INSERT INTO `code_t` VALUES ('16', '13049492251', '519812', '2018-02-26 14:00:08');
INSERT INTO `code_t` VALUES ('17', '13049492251', '463569', '2018-02-26 14:02:08');
INSERT INTO `code_t` VALUES ('18', '13049492251', '990642', '2018-02-26 14:08:36');
INSERT INTO `code_t` VALUES ('19', '13049492251', '591908', '2018-02-26 14:13:21');
INSERT INTO `code_t` VALUES ('20', '13049492251', '783370', '2018-02-26 14:15:15');
INSERT INTO `code_t` VALUES ('21', '13049492251', '895773', '2018-02-26 14:22:39');
INSERT INTO `code_t` VALUES ('22', '13049492251', '863865', '2018-02-26 14:23:23');
INSERT INTO `code_t` VALUES ('23', '13049492251', '960307', '2018-02-26 14:32:29');
INSERT INTO `code_t` VALUES ('24', '13049492251', '290537', '2018-02-26 14:33:14');
INSERT INTO `code_t` VALUES ('25', '13049492251', '889312', '2018-02-26 14:34:13');
INSERT INTO `code_t` VALUES ('26', '13049492251', '671412', '2018-02-26 14:34:47');
INSERT INTO `code_t` VALUES ('27', '13049492251', '82842', '2018-02-26 14:35:34');
INSERT INTO `code_t` VALUES ('28', '13049492251', '701311', '2018-02-26 14:37:57');

-- ----------------------------
-- Table structure for `file_download_t`
-- ----------------------------
DROP TABLE IF EXISTS `file_download_t`;
CREATE TABLE `file_download_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '文件显示名称',
  `type` int(1) NOT NULL COMMENT '类别:1-招生，2-教学，3-管理',
  `sub_type_id` int(11) NOT NULL COMMENT '子类别',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `file_name` varchar(64) NOT NULL COMMENT '文件名称',
  `file_path` varchar(255) NOT NULL COMMENT '文件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_download_t
-- ----------------------------
INSERT INTO `file_download_t` VALUES ('1', '1', '1', '40', '1', 'aaa.sql', '/file/fileDownload/b47fc926-32a0-4423-a148-2ba1edb2fe3d.sql');
INSERT INTO `file_download_t` VALUES ('2', '5', '2', '51', '5', 'myoa配置.xlsx', '/file/fileDownload/c90601cb-82dc-43a6-8cb8-df03d82c6bb8.xlsx');
INSERT INTO `file_download_t` VALUES ('3', '2222', '1', '40', '22222', 'cas流程图.png', '/file/fileDownload/70186df9-c5d7-458d-a3b0-926240d54145.png');
INSERT INTO `file_download_t` VALUES ('4', '6', '1', '40', '6', '~$CAS.docx', '/file/fileDownload/7bfb6eea-87d2-49b9-8e77-7a5dc5ab53bb.docx');

-- ----------------------------
-- Table structure for `menu_t`
-- ----------------------------
DROP TABLE IF EXISTS `menu_t`;
CREATE TABLE `menu_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(16) NOT NULL,
  `address` varchar(32) DEFAULT NULL COMMENT '地址url',
  `role_ids` varchar(8) DEFAULT NULL COMMENT '角色id集合',
  `parent_id` int(11) DEFAULT NULL COMMENT '父ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of menu_t
-- ----------------------------
INSERT INTO `menu_t` VALUES ('1', '账号管理', null, '0', '0');
INSERT INTO `menu_t` VALUES ('2', '类别管理', null, '0', '0');
INSERT INTO `menu_t` VALUES ('3', '教案管理', null, '0,1,2', '0');
INSERT INTO `menu_t` VALUES ('4', '班级管理', null, '0,1,2', '0');
INSERT INTO `menu_t` VALUES ('5', '教师管理', '/user/teacher/teacherList', '0,1', '0');
INSERT INTO `menu_t` VALUES ('6', '学员管理', null, '0,1,2', '0');
INSERT INTO `menu_t` VALUES ('7', '文件下载', '/fileDownload/fileDownloadList', '0,1,2', '0');
INSERT INTO `menu_t` VALUES ('8', '视频库', '/video/videoList', '0,1,2', '0');
INSERT INTO `menu_t` VALUES ('9', '查看账号列表', '/account/accountList', '0', '1');
INSERT INTO `menu_t` VALUES ('10', '账号审批', '/account/approveList', '0', '1');
INSERT INTO `menu_t` VALUES ('11', '查看学员信息', '/user/student/studentList', '0,1,2', '6');
INSERT INTO `menu_t` VALUES ('12', '查看班级列表', '/classes/classesList', '0,1,2', '4');
INSERT INTO `menu_t` VALUES ('13', '查看班级成员', '/classes/classesInfo', '0,1,2', '4');
INSERT INTO `menu_t` VALUES ('14', '教案管理', '/category/teachPlan', '0', '2');
INSERT INTO `menu_t` VALUES ('15', '文件管理', '/category/file', '0', '2');
INSERT INTO `menu_t` VALUES ('16', '查看教案', '/teachPlan/teachPlanList', '0,1,2', '3');
INSERT INTO `menu_t` VALUES ('17', '添加教案', '/teachPlan/addTeachPlan', '0', '3');
INSERT INTO `menu_t` VALUES ('18', '查看考勤', '/user/student/signInList', '0,1,2', '6');
INSERT INTO `menu_t` VALUES ('19', '查看教案分配详情', '/teachPlan/detailList', '0,1', '3');

-- ----------------------------
-- Table structure for `message_t`
-- ----------------------------
DROP TABLE IF EXISTS `message_t`;
CREATE TABLE `message_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `content` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message_t
-- ----------------------------
INSERT INTO `message_t` VALUES ('1', '12', '1', '<p>大苏打似的<img src=\"/file/message/8165b00f-6fa5-45a7-be04-8c89de150159.jpg\"></p><p><br></p>');

-- ----------------------------
-- Table structure for `permission_t`
-- ----------------------------
DROP TABLE IF EXISTS `permission_t`;
CREATE TABLE `permission_t` (
  `id` int(3) NOT NULL,
  `name` varchar(24) NOT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission_t
-- ----------------------------
INSERT INTO `permission_t` VALUES ('1', 'queryTeachPlan');
INSERT INTO `permission_t` VALUES ('2', 'addTeachPlan');
INSERT INTO `permission_t` VALUES ('3', 'modTeachPlan');
INSERT INTO `permission_t` VALUES ('4', 'delTeachPlan');
INSERT INTO `permission_t` VALUES ('5', 'allotTeachPlan');
INSERT INTO `permission_t` VALUES ('6', 'addClasses');
INSERT INTO `permission_t` VALUES ('7', 'modClasses');
INSERT INTO `permission_t` VALUES ('8', 'delClassess');
INSERT INTO `permission_t` VALUES ('9', 'addFileDownload');
INSERT INTO `permission_t` VALUES ('10', 'modFileDownload');
INSERT INTO `permission_t` VALUES ('11', 'delFileDownload');
INSERT INTO `permission_t` VALUES ('12', 'queryFileDownload');
INSERT INTO `permission_t` VALUES ('13', 'allotFileDownload');
INSERT INTO `permission_t` VALUES ('14', 'sendMessage');

-- ----------------------------
-- Table structure for `role_permission_t`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission_t`;
CREATE TABLE `role_permission_t` (
  `id` int(11) NOT NULL,
  `permission_id` int(3) NOT NULL,
  `role_id` int(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `permission_id` (`permission_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `role_permission_t_ibfk_1` FOREIGN KEY (`permission_id`) REFERENCES `permission_t` (`id`),
  CONSTRAINT `role_permission_t_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role_t` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission_t
-- ----------------------------
INSERT INTO `role_permission_t` VALUES ('1', '1', '0');
INSERT INTO `role_permission_t` VALUES ('2', '2', '0');
INSERT INTO `role_permission_t` VALUES ('3', '3', '0');
INSERT INTO `role_permission_t` VALUES ('4', '4', '0');
INSERT INTO `role_permission_t` VALUES ('5', '1', '1');
INSERT INTO `role_permission_t` VALUES ('6', '1', '2');
INSERT INTO `role_permission_t` VALUES ('7', '5', '0');
INSERT INTO `role_permission_t` VALUES ('8', '5', '1');
INSERT INTO `role_permission_t` VALUES ('9', '6', '0');
INSERT INTO `role_permission_t` VALUES ('10', '7', '0');
INSERT INTO `role_permission_t` VALUES ('11', '8', '0');
INSERT INTO `role_permission_t` VALUES ('12', '6', '1');
INSERT INTO `role_permission_t` VALUES ('13', '7', '1');
INSERT INTO `role_permission_t` VALUES ('14', '8', '1');
INSERT INTO `role_permission_t` VALUES ('15', '9', '0');
INSERT INTO `role_permission_t` VALUES ('16', '10', '0');
INSERT INTO `role_permission_t` VALUES ('17', '11', '0');
INSERT INTO `role_permission_t` VALUES ('18', '12', '0');
INSERT INTO `role_permission_t` VALUES ('19', '13', '0');
INSERT INTO `role_permission_t` VALUES ('20', '12', '1');
INSERT INTO `role_permission_t` VALUES ('21', '13', '1');
INSERT INTO `role_permission_t` VALUES ('22', '12', '2');
INSERT INTO `role_permission_t` VALUES ('23', '14', '2');

-- ----------------------------
-- Table structure for `role_t`
-- ----------------------------
DROP TABLE IF EXISTS `role_t`;
CREATE TABLE `role_t` (
  `id` int(2) NOT NULL COMMENT '角色ID',
  `name` varchar(16) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role_t
-- ----------------------------
INSERT INTO `role_t` VALUES ('0', 'admin');
INSERT INTO `role_t` VALUES ('1', 'school');
INSERT INTO `role_t` VALUES ('2', 'teacher');
INSERT INTO `role_t` VALUES ('3', 'student');

-- ----------------------------
-- Table structure for `school_t`
-- ----------------------------
DROP TABLE IF EXISTS `school_t`;
CREATE TABLE `school_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `school_id` int(11) DEFAULT NULL,
  `name` varchar(16) NOT NULL COMMENT '学校名称',
  `account_id` int(11) NOT NULL COMMENT '账号ID',
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `school_t_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account_t` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of school_t
-- ----------------------------
INSERT INTO `school_t` VALUES ('1', null, '清华', '8');
INSERT INTO `school_t` VALUES ('2', null, '北大', '9');
INSERT INTO `school_t` VALUES ('3', null, '南开', '10');
INSERT INTO `school_t` VALUES ('4', null, '武汉大学', '11');
INSERT INTO `school_t` VALUES ('5', null, '技校', '12');
INSERT INTO `school_t` VALUES ('6', null, '大学', '14');
INSERT INTO `school_t` VALUES ('7', '6', '大学', '15');
INSERT INTO `school_t` VALUES ('8', '6', '大学', '16');
INSERT INTO `school_t` VALUES ('9', '1', '清华', '32');
INSERT INTO `school_t` VALUES ('10', null, 'aaa', '35');
INSERT INTO `school_t` VALUES ('11', null, 'a', '36');

-- ----------------------------
-- Table structure for `sign_in_t`
-- ----------------------------
DROP TABLE IF EXISTS `sign_in_t`;
CREATE TABLE `sign_in_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `student_id` int(11) NOT NULL COMMENT '学生id(student表的id)',
  `student_account` varchar(16) NOT NULL,
  `student_name` varchar(16) NOT NULL,
  `operater_id` int(11) NOT NULL COMMENT '操作人员id(account表的id)',
  `operater_name` varchar(16) NOT NULL,
  `sign_in_date` datetime NOT NULL COMMENT '签到时间',
  `school_id` int(11) NOT NULL,
  `school_name` varchar(16) NOT NULL,
  `classes_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sign_in_t
-- ----------------------------
INSERT INTO `sign_in_t` VALUES ('5', '1', 'xuesheng', '小明', '1', 'admin', '2018-01-04 19:25:35', '1', '清华', '二班,一班');
INSERT INTO `sign_in_t` VALUES ('6', '4', '5', '学生小明', '1', 'admin', '2018-02-11 19:27:11', '2', '北大', null);
INSERT INTO `sign_in_t` VALUES ('7', '3', '123', '小赵', '1', 'admin', '2018-02-11 19:27:14', '1', '清华', '一班,二班');

-- ----------------------------
-- Table structure for `student_t`
-- ----------------------------
DROP TABLE IF EXISTS `student_t`;
CREATE TABLE `student_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(16) NOT NULL COMMENT '姓名',
  `parent_phone` varchar(16) DEFAULT NULL COMMENT '家长电话',
  `birthday` date DEFAULT NULL,
  `school_id` int(11) NOT NULL COMMENT '学校ID',
  `account_id` int(11) NOT NULL COMMENT '账号id',
  `sign` int(1) NOT NULL DEFAULT '0' COMMENT '签到标志：0-未签到，1-已签到',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_t
-- ----------------------------
INSERT INTO `student_t` VALUES ('1', '小明', '11111', '2018-01-10', '1', '28', '0');
INSERT INTO `student_t` VALUES ('3', '小赵', '123', '2018-01-02', '1', '30', '0');
INSERT INTO `student_t` VALUES ('4', '学生小明', '1', '2018-03-08', '2', '33', '0');
INSERT INTO `student_t` VALUES ('5', 'lalalal', null, null, '1', '34', '0');

-- ----------------------------
-- Table structure for `teacher_t`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_t`;
CREATE TABLE `teacher_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(16) NOT NULL COMMENT '姓名',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `school_id` int(11) NOT NULL COMMENT '学校id',
  `account_id` int(11) NOT NULL COMMENT '账号id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of teacher_t
-- ----------------------------
INSERT INTO `teacher_t` VALUES ('1', 'eee', '111', '1', '2');
INSERT INTO `teacher_t` VALUES ('3', '张老师1222', '111', '1', '18');
INSERT INTO `teacher_t` VALUES ('6', '4444', null, '1', '21');
INSERT INTO `teacher_t` VALUES ('7', '1', '', '1', '22');
INSERT INTO `teacher_t` VALUES ('8', '2', '', '2', '23');
INSERT INTO `teacher_t` VALUES ('9', '3', null, '2', '24');
INSERT INTO `teacher_t` VALUES ('10', '2321', '12121', '2', '25');
INSERT INTO `teacher_t` VALUES ('11', '654321', '2', '1', '26');
INSERT INTO `teacher_t` VALUES ('12', '啦啦啦', null, '1', '31');

-- ----------------------------
-- Table structure for `teach_plan_t`
-- ----------------------------
DROP TABLE IF EXISTS `teach_plan_t`;
CREATE TABLE `teach_plan_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '教案名称',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `type` int(1) NOT NULL COMMENT '1-基础课程，2-特色课程',
  `course_id` int(11) NOT NULL COMMENT '课程类别ID',
  `scene_id` int(11) NOT NULL COMMENT '场景类别ID',
  `theme_id` int(11) NOT NULL COMMENT '主题类别ID',
  `summary` varchar(1024) DEFAULT NULL COMMENT '课程概述',
  `prepare` varchar(1024) DEFAULT NULL COMMENT '课前准备内容',
  `prepare_file_path` varchar(255) DEFAULT NULL COMMENT '课前准备文件存放路径',
  `prepare_file_name` varchar(64) DEFAULT NULL COMMENT '课前准备文件名',
  `content` varchar(255) DEFAULT NULL COMMENT '课程内容文件存放路径',
  `ppt_file_path` varchar(255) DEFAULT NULL COMMENT 'PPT文件存放路径',
  `ppt_file_name` varchar(64) DEFAULT NULL COMMENT 'PPT文件名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teach_plan_t
-- ----------------------------
INSERT INTO `teach_plan_t` VALUES ('1', '1111', 'hhhhhhh', '2', '50', '42', '45', '11111', '11111', '/file/prepare/bee20968-5989-4983-82b0-a7f289ebbb2e.jpg', 'J8006557346.jpg', '/file/html/96c07340-cbc4-49f5-bb63-53c9343f55cb.html', '', '1 - 副本.ppt');
INSERT INTO `teach_plan_t` VALUES ('4', '22222222', '1', '1', '39', '42', '44', '1111111111111\n222222222222\n33333333333', null, null, null, '/file/html/8f9e9284-fd65-4a6a-9024-297be8300a06.html', null, null);
INSERT INTO `teach_plan_t` VALUES ('5', '999', null, '1', '39', '42', '44', '0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000888888', null, null, null, '/file/html/da7e181c-114b-4571-a0cf-eca23f4f3c9d.html', null, null);
INSERT INTO `teach_plan_t` VALUES ('6', '1', null, '1', '39', '42', '44', null, null, null, null, '/file/html/aa797dc8-2f1e-44a4-9215-8170bc965dd6.html', null, null);
INSERT INTO `teach_plan_t` VALUES ('7', '2222', null, '1', '48', '42', '45', '11111111111111', '2222222222222222', '/file/prepare/9a35834e-958a-4be8-a9d7-3dc5702feda3.jpg', '胡夏和腾讯老总.jpg', '/file/html/cc068b6a-5288-4f90-9ddf-bedf222ad876.html', null, null);
INSERT INTO `teach_plan_t` VALUES ('8', 'PPT', null, '1', '49', '43', '44', 'ppt', 'ppt', '/file/prepare/b9deb442-8ce6-4dfd-a96a-0e26fafe26ca.jpg', '胡夏在玩游戏.jpg', '/file/html/eb444094-c76a-449e-b6d9-5f691867f1e2.html', '/file/ppt/a83ec0c9-20b7-456e-94dd-1188fd0b2abd.pptx', 'Java培训.pptx');
INSERT INTO `teach_plan_t` VALUES ('9', 'pptText', null, '1', '47', '42', '44', null, null, null, null, '/file/html/6991836e-c201-4610-8198-7250e8b38e23.html', '/file/ppt/6f450aca-277d-4869-9f24-1a0efd69f531.pptx', 'Java培训.pptx');
INSERT INTO `teach_plan_t` VALUES ('10', 'ssss', null, '1', '39', '42', '44', '', '', '', '', '/file/html/23f52a71-f8a4-4f5e-bcd4-d85273ceba3a.html', '/file/ppt/05aeb4f8-81a8-44c8-82f0-cde94dec9ed7.pptx', 'Java培训.pptx');

-- ----------------------------
-- Table structure for `user_classes_t`
-- ----------------------------
DROP TABLE IF EXISTS `user_classes_t`;
CREATE TABLE `user_classes_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` int(11) NOT NULL COMMENT '学生/教师的账号id',
  `classes_id` int(11) NOT NULL COMMENT '班级id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of user_classes_t
-- ----------------------------
INSERT INTO `user_classes_t` VALUES ('5', '18', '1');
INSERT INTO `user_classes_t` VALUES ('6', '18', '4');
INSERT INTO `user_classes_t` VALUES ('7', '22', '1');
INSERT INTO `user_classes_t` VALUES ('8', '22', '4');
INSERT INTO `user_classes_t` VALUES ('9', '23', '3');
INSERT INTO `user_classes_t` VALUES ('10', '25', '2');
INSERT INTO `user_classes_t` VALUES ('22', '31', '4');
INSERT INTO `user_classes_t` VALUES ('23', '28', '1');
INSERT INTO `user_classes_t` VALUES ('24', '28', '4');
INSERT INTO `user_classes_t` VALUES ('25', '30', '1');
INSERT INTO `user_classes_t` VALUES ('32', '33', '2');
INSERT INTO `user_classes_t` VALUES ('33', '34', '1');

-- ----------------------------
-- Table structure for `user_file_download_t`
-- ----------------------------
DROP TABLE IF EXISTS `user_file_download_t`;
CREATE TABLE `user_file_download_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` int(11) NOT NULL COMMENT '学生/教师的账号id',
  `file_download_id` int(11) NOT NULL COMMENT '文件下载的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_file_download_t
-- ----------------------------
INSERT INTO `user_file_download_t` VALUES ('1', '8', '1');
INSERT INTO `user_file_download_t` VALUES ('8', '31', '1');
INSERT INTO `user_file_download_t` VALUES ('9', '8', '3');
INSERT INTO `user_file_download_t` VALUES ('10', '31', '3');
INSERT INTO `user_file_download_t` VALUES ('11', '23', '3');
INSERT INTO `user_file_download_t` VALUES ('12', '23', '1');
INSERT INTO `user_file_download_t` VALUES ('13', '14', '3');

-- ----------------------------
-- Table structure for `user_teach_plan_t`
-- ----------------------------
DROP TABLE IF EXISTS `user_teach_plan_t`;
CREATE TABLE `user_teach_plan_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` int(11) NOT NULL COMMENT '学生/教师的账号id',
  `teach_plan_id` int(11) NOT NULL COMMENT '教案ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_teach_plan_t
-- ----------------------------
INSERT INTO `user_teach_plan_t` VALUES ('1', '8', '10');
INSERT INTO `user_teach_plan_t` VALUES ('2', '31', '10');
INSERT INTO `user_teach_plan_t` VALUES ('4', '8', '4');
INSERT INTO `user_teach_plan_t` VALUES ('5', '8', '5');
INSERT INTO `user_teach_plan_t` VALUES ('7', '31', '4');
INSERT INTO `user_teach_plan_t` VALUES ('8', '31', '5');
INSERT INTO `user_teach_plan_t` VALUES ('9', '10', '10');
INSERT INTO `user_teach_plan_t` VALUES ('10', '11', '6');

-- ----------------------------
-- Table structure for `video_t`
-- ----------------------------
DROP TABLE IF EXISTS `video_t`;
CREATE TABLE `video_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '视频名称',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `file_name` varchar(64) NOT NULL COMMENT '视频文件名称',
  `file_path` varchar(255) NOT NULL COMMENT '视频文件路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video_t
-- ----------------------------
INSERT INTO `video_t` VALUES ('1', '2222', '222', 'mov_bbb.mp4', '/file/video/ad4598fe-c695-42e2-aa6e-18f206eac551.mp4');
INSERT INTO `video_t` VALUES ('3', '6666', '6666', '8fe72d1f-0321-4e89-9efd-fa5e3349f3fc.mp4', '/file/video/e6c9278a-2363-4946-9ba4-f9954f873ff6.mp4');
INSERT INTO `video_t` VALUES ('4', '1', '1', '6c0872ca-704a-4bf8-80bc-755ee66c37ab.mp4', '/file/video/df308a21-971f-4f97-bcb3-9c5cd9d02c5d.mp4');

-- ----------------------------
-- Table structure for version_t
-- ----------------------------
DROP TABLE IF EXISTS `version_t`;
CREATE TABLE `version_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_version` varchar(16) NOT NULL,
  `app_package` varchar(255) NOT NULL,
  `hardware_version` varchar(16) NOT NULL,
  `hardware_package` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;

-- ----------------------------
-- View structure for `account_sub_v`
-- ----------------------------
DROP VIEW IF EXISTS `account_sub_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `account_sub_v` AS select `a`.`id` AS `id`,`a`.`account` AS `account`,`a`.`password` AS `password`,`a`.`status` AS `status`,`a`.`type` AS `type`,`s`.`id` AS `schoolId`,`s`.`name` AS `name` from ((`account_t` `a` left join `teacher_t` `t` on((`t`.`account_id` = `a`.`id`))) left join `school_t` `s` on((`s`.`id` = `t`.`school_id`))) where (`a`.`type` = 2) union select `a`.`id` AS `id`,`a`.`account` AS `account`,`a`.`password` AS `password`,`a`.`status` AS `status`,`a`.`type` AS `type`,`s`.`id` AS `schoolId`,`s`.`name` AS `name` from (`account_t` `a` left join `school_t` `s` on((`s`.`account_id` = `a`.`id`))) where (`a`.`type` = 1) union select `a`.`id` AS `id`,`a`.`account` AS `account`,`a`.`password` AS `password`,`a`.`status` AS `status`,`a`.`type` AS `type`,`s`.`id` AS `schoolId`,`s`.`name` AS `name` from ((`account_t` `a` left join `student_t` `stu` on((`stu`.`account_id` = `a`.`id`))) left join `school_t` `s` on((`s`.`id` = `stu`.`school_id`))) where (`a`.`type` = 3) ;

-- ----------------------------
-- View structure for `account_v`
-- ----------------------------
DROP VIEW IF EXISTS `account_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `account_v` AS select `v`.`id` AS `id`,`v`.`account` AS `account`,`v`.`password` AS `password`,`v`.`status` AS `status`,`v`.`type` AS `type`,`v`.`schoolId` AS `schoolId`,`v`.`name` AS `name` from `account_sub_v` `v` order by `v`.`id` ;

-- ----------------------------
-- View structure for `classes_user_v`
-- ----------------------------
DROP VIEW IF EXISTS `classes_user_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `classes_user_v` AS select `a`.`id` AS `id`,`c`.`id` AS `classesId`,`c`.`name` AS `classes`,`c`.`school_id` AS `schoolId`,`a`.`account` AS `account`,`a`.`type` AS `type`,`s`.`name` AS `name`,`sch`.`name` AS `schoolName` from ((((`classes_t` `c` left join `user_classes_t` `uc` on((`c`.`id` = `uc`.`classes_id`))) left join `account_t` `a` on((`a`.`id` = `uc`.`account_id`))) left join `student_t` `s` on((`s`.`account_id` = `uc`.`account_id`))) left join `school_t` `sch` on((`c`.`school_id` = `sch`.`id`))) where (`a`.`type` = 3) union select `a`.`id` AS `id`,`c`.`id` AS `id`,`c`.`name` AS `classes`,`c`.`school_id` AS `schoolId`,`a`.`account` AS `account`,`a`.`type` AS `type`,`t`.`name` AS `name`,`sch`.`name` AS `schoolName` from ((((`classes_t` `c` left join `user_classes_t` `uc` on((`c`.`id` = `uc`.`classes_id`))) left join `account_t` `a` on((`a`.`id` = `uc`.`account_id`))) left join `teacher_t` `t` on((`t`.`account_id` = `uc`.`account_id`))) left join `school_t` `sch` on((`c`.`school_id` = `sch`.`id`))) where (`a`.`type` = 2) ;

-- ----------------------------
-- View structure for `classes_v`
-- ----------------------------
DROP VIEW IF EXISTS `classes_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `classes_v` AS select `c`.`id` AS `id`,`c`.`name` AS `name`,`c`.`school_id` AS `schoolId`,`c`.`remark` AS `remark`,(select count(0) from `user_classes_t` `uc` where (`uc`.`classes_id` = `c`.`id`)) AS `count`,`s`.`name` AS `schoolName` from (`classes_t` `c` left join `school_t` `s` on((`c`.`school_id` = `s`.`id`))) ;

-- ----------------------------
-- View structure for `file_download_v`
-- ----------------------------
DROP VIEW IF EXISTS `file_download_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `file_download_v` AS select `f`.`id` AS `id`,`f`.`name` AS `name`,`f`.`type` AS `type`,`f`.`sub_type_id` AS `subTypeId`,`f`.`description` AS `description`,`f`.`file_name` AS `fileName`,`f`.`file_path` AS `filePath`,`c`.`text` AS `subTypeName` from (`file_download_t` `f` left join `category_t` `c` on((`f`.`sub_type_id` = `c`.`id`))) ;

-- ----------------------------
-- View structure for `student_v`
-- ----------------------------
DROP VIEW IF EXISTS `student_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `student_v` AS select `s`.`id` AS `id`,`s`.`name` AS `name`,`s`.`parent_phone` AS `parentPhone`,`s`.`birthday` AS `birthday`,`s`.`school_id` AS `school_id`,`s`.`account_id` AS `accountId`,`s`.`sign` AS `sign`,`a`.`account` AS `account`,`a`.`status` AS `status`,`sch`.`name` AS `schoolName`,group_concat(`c`.`name` separator ',') AS `classes`,group_concat(`c`.`id` separator ',') AS `classesId` from ((((`student_t` `s` left join `account_t` `a` on((`s`.`account_id` = `a`.`id`))) left join `school_t` `sch` on((`s`.`school_id` = `sch`.`id`))) left join `user_classes_t` `us` on((`s`.`account_id` = `us`.`account_id`))) left join `classes_t` `c` on((`us`.`classes_id` = `c`.`id`))) where (`a`.`type` = 3) group by `s`.`id` ;

-- ----------------------------
-- View structure for `teacher_classes_v`
-- ----------------------------
DROP VIEW IF EXISTS `teacher_classes_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `teacher_classes_v` AS select `c`.`id` AS `id`,`u_classes`.`account_id` AS `accountId`,`c`.`name` AS `name`,`c`.`school_id` AS `schoolId`,`c`.`remark` AS `remark`,(select count(0) from `user_classes_t` `uc` where (`uc`.`classes_id` = `c`.`id`)) AS `count`,`s`.`name` AS `schoolName` from ((`classes_t` `c` left join `school_t` `s` on((`c`.`school_id` = `s`.`id`))) left join `user_classes_t` `u_classes` on((`c`.`id` = `u_classes`.`classes_id`))) ;

-- ----------------------------
-- View structure for `teacher_v`
-- ----------------------------
DROP VIEW IF EXISTS `teacher_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `teacher_v` AS select `t`.`id` AS `id`,`t`.`name` AS `name`,group_concat(`c`.`name` separator ',') AS `classes`,group_concat(`c`.`id` separator ',') AS `classesId`,`t`.`remark` AS `remark`,`t`.`school_id` AS `school_id`,`t`.`account_id` AS `account_id`,`a`.`account` AS `account`,`s`.`name` AS `schoolName`,`a`.`status` AS `status` from ((((`teacher_t` `t` left join `account_t` `a` on((`t`.`account_id` = `a`.`id`))) left join `school_t` `s` on((`t`.`school_id` = `s`.`id`))) left join `user_classes_t` `us` on((`t`.`account_id` = `us`.`account_id`))) left join `classes_t` `c` on((`us`.`classes_id` = `c`.`id`))) where (`a`.`type` = 2) group by `t`.`id` ;

-- ----------------------------
-- View structure for `teach_plan_v`
-- ----------------------------
DROP VIEW IF EXISTS `teach_plan_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `teach_plan_v` AS select `t`.`id` AS `id`,`t`.`name` AS `name`,`t`.`description` AS `description`,`t`.`type` AS `type`,`t`.`course_id` AS `courseId`,`t`.`scene_id` AS `sceneId`,`t`.`theme_id` AS `themeId`,`t`.`summary` AS `summary`,`t`.`prepare` AS `prepare`,`t`.`prepare_file_path` AS `prepareFilePath`,`t`.`prepare_file_name` AS `prepareFileName`,`t`.`content` AS `content`,`t`.`ppt_file_path` AS `pptFilePath`,`t`.`ppt_file_name` AS `pptFileName`,`c1`.`text` AS `courseName`,`c2`.`text` AS `sceneName`,`c3`.`text` AS `themeName` from (((`teach_plan_t` `t` left join `category_t` `c1` on((`t`.`course_id` = `c1`.`id`))) left join `category_t` `c2` on((`t`.`scene_id` = `c2`.`id`))) left join `category_t` `c3` on((`t`.`theme_id` = `c3`.`id`))) ;

-- ----------------------------
-- View structure for `user_file_download_v`
-- ----------------------------
DROP VIEW IF EXISTS `user_file_download_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_file_download_v` AS select distinct `f`.`id` AS `id`,`ufd`.`account_id` AS `accountId`,`f`.`name` AS `name`,`f`.`type` AS `type`,`f`.`sub_type_id` AS `subTypeId`,`f`.`description` AS `description`,`f`.`file_name` AS `fileName`,`f`.`file_path` AS `filePath`,`c`.`text` AS `subTypeName` from ((`file_download_t` `f` left join `category_t` `c` on((`f`.`sub_type_id` = `c`.`id`))) left join `user_file_download_t` `ufd` on((`f`.`id` = `ufd`.`file_download_id`))) ;

-- ----------------------------
-- View structure for `user_teach_plan_v`
-- ----------------------------
DROP VIEW IF EXISTS `user_teach_plan_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_teach_plan_v` AS select distinct `t`.`id` AS `id`,`utp`.`account_id` AS `accountId`,`t`.`name` AS `name`,`t`.`description` AS `description`,`t`.`type` AS `type`,`t`.`course_id` AS `courseId`,`t`.`scene_id` AS `sceneId`,`t`.`theme_id` AS `themeId`,`t`.`summary` AS `summary`,`t`.`prepare` AS `prepare`,`t`.`prepare_file_path` AS `prepareFilePath`,`t`.`prepare_file_name` AS `prepareFileName`,`t`.`content` AS `content`,`t`.`ppt_file_path` AS `pptFilePath`,`t`.`ppt_file_name` AS `pptFileName`,`c1`.`text` AS `courseName`,`c2`.`text` AS `sceneName`,`c3`.`text` AS `themeName` from ((((`teach_plan_t` `t` left join `category_t` `c1` on((`t`.`course_id` = `c1`.`id`))) left join `category_t` `c2` on((`t`.`scene_id` = `c2`.`id`))) left join `category_t` `c3` on((`t`.`theme_id` = `c3`.`id`))) left join `user_teach_plan_t` `utp` on((`t`.`id` = `utp`.`teach_plan_id`))) ;

-- ----------------------------
-- View structure for `video_v`
-- ----------------------------
DROP VIEW IF EXISTS `video_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `video_v` AS select `t`.`id` AS `id`,`t`.`name` AS `name`,`t`.`description` AS `description`,`t`.`file_name` AS `fileName`,`t`.`file_path` AS `filePath` from `video_t` `t` ;
