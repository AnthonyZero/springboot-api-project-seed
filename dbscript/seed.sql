CREATE DATABASE `seed` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE `seed`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sm_file
-- ----------------------------
DROP TABLE IF EXISTS `sm_file`;
CREATE TABLE `sm_file` (
  `file_id` bigint(20) NOT NULL COMMENT '文件ID',
  `file_name` varchar(250) NOT NULL COMMENT '文件名称',
  `file_sign` varchar(64) DEFAULT NULL COMMENT '文件签名',
  `file_type` int(11) NOT NULL COMMENT '文件类型:1 图片2 文档3 其他',
  `url_type` int(11) DEFAULT NULL COMMENT 'URL类型 1本地 2*云',
  `file_url` varchar(250) NOT NULL COMMENT '文件路径',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件管理表';

-- ----------------------------
-- Records of sm_file
-- ----------------------------

-- ----------------------------
-- Table structure for sm_user
-- ----------------------------
DROP TABLE IF EXISTS `sm_user`;
CREATE TABLE `sm_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_name` varchar(128) NOT NULL COMMENT '用户名称',
  `user_code` varchar(128) NOT NULL COMMENT '登录账号',
  `password` varchar(128) NOT NULL COMMENT '用户密码',
  `depart_id` bigint(20) NOT NULL COMMENT '部门ID',
  `salt` varchar(64) DEFAULT NULL COMMENT '盐',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像图片',
  `user_type` int(11) DEFAULT '1' COMMENT '用户类型 1系统用户',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `user_desc` varchar(500) DEFAULT NULL,
  `state` int(11) NOT NULL COMMENT '状态 0 无效1 正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_sm_user_login_code` (`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sm_user
-- ----------------------------
INSERT INTO `sm_user` VALUES ('455991757574144', 'username', 'test', 'feaeb4d7ccf8ff83f6d1371554d70eaabb1d9ca9c7427f18e1182173672fbc20', '0', 'UREb44orHf1zXrKRlv7f', null, null, null, null, null, '1', '2018-07-12 11:55:13');

-- ----------------------------
-- Table structure for sm_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sm_user_token`;
CREATE TABLE `sm_user_token` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `token` varchar(128) NOT NULL COMMENT 'token',
  `expire_time` datetime NOT NULL COMMENT '失效时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户TOKEN';

-- ----------------------------
-- Records of sm_user_token
-- ----------------------------
INSERT INTO `sm_user_token` VALUES ('455991757574144', '28d56b5b4da464477d53db69f0e0e172', '2018-07-13 04:54:00', '2018-07-12 16:54:00');
