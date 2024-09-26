/*
 Navicat Premium Data Transfer

 Source Server         : mysql80
 Source Server Type    : MySQL
 Source Server Version : 80038
 Source Host           : localhost:3306
 Source Schema         : maxkey

 Target Server Type    : MySQL
 Target Server Version : 80038
 File Encoding         : 65001

 Date: 26/09/2024 10:36:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sync_job_config_field
-- ----------------------------
DROP TABLE IF EXISTS `sync_job_config_field`;
CREATE TABLE `sync_job_config_field`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `jobid` bigint NOT NULL DEFAULT 0 COMMENT '同步任务ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '规则名',
  `objecttype` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '类型',
  `targetfield` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '目标字段',
  `targetfieldname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '目标字段描述',
  `sourcefield` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '来源字段',
  `sourcefieldname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '来源字段描述',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '描述',
  `createuser` bigint NULL DEFAULT 0 COMMENT '创建人',
  `createtime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updateuser` bigint NULL DEFAULT 0 COMMENT '修改人',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_job_id`(`jobid` ASC) USING BTREE COMMENT '同步任务ID索引'
) ENGINE = InnoDB AUTO_INCREMENT = 214 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '同步任务字段映射表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sync_job_config_field
-- ----------------------------
INSERT INTO `sync_job_config_field` VALUES (1, 3, NULL, '1', 'username', NULL, 'userid', NULL, '钉钉用户', NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (2, 3, NULL, '1', 'nickName', NULL, 'name', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (3, 3, NULL, '1', 'displayName', NULL, 'name', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (4, 3, NULL, '1', 'formattedName', NULL, 'name', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (5, 3, NULL, '1', 'email', NULL, 'email', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (6, 3, NULL, '1', 'entryDate', NULL, 'hiredDate', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (7, 3, NULL, '1', 'mobile', NULL, 'mobile', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (8, 3, NULL, '1', 'departmentId', NULL, 'objectId', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (9, 3, NULL, '1', 'department', NULL, 'objectName', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (10, 3, NULL, '1', 'employeeNumber', NULL, 'jobNumber', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (11, 3, NULL, '1', 'jobTitle', NULL, 'title', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (12, 3, NULL, '1', 'workEmail', NULL, 'orgEmail', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (13, 3, NULL, '1', 'workPhoneNumber', NULL, 'telephone', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (14, 3, NULL, '1', 'workOfficeName', NULL, 'workPlace', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (15, 3, NULL, '1', 'status', NULL, 'active', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (16, 3, '', '1', 'description', '', 'remark', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (17, 3, NULL, '2', 'id', NULL, 'deptId', NULL, '钉钉组织', NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (18, 3, NULL, '2', 'orgCode', NULL, 'deptId', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (19, 3, NULL, '2', 'orgName', NULL, 'name', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (20, 3, NULL, '2', 'parentId', NULL, 'objectId', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (21, 3, NULL, '2', 'parentCode', NULL, 'parentId', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (22, 3, NULL, '2', 'parentName', NULL, 'objectName', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sync_job_config_field` VALUES (23, 6, '', '1', 'username', '', 'user_id', '', '飞书用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (24, 6, '', '1', 'nickName', '', 'nickname', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (25, 6, '', '1', 'displayName', '', 'name', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (26, 6, '', '1', 'mobile', '', 'mobile', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (27, 6, '', '1', 'email', '', 'email', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (28, 6, '', '1', 'gender', '', 'gender', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (29, 6, '', '1', 'employeeNumber', '', 'employee_no', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (30, 6, '', '1', 'workPhoneNumber', '', 'mobile', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (31, 6, '', '1', 'department', '', 'objectName', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (32, 6, '', '1', 'jobTitle', '', 'job_title', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (33, 6, '', '1', 'workAddressFormatted', '', 'work_station', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (34, 6, '', '1', 'status', '', 'status', '', '', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (35, 6, '', '2', 'orgCode', '', 'department_id', '', '飞书组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (36, 6, '', '2', 'orgName', '', 'name', '', '飞书组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (37, 6, '', '2', 'fullName', '', 'name', '', '飞书组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (38, 6, '', '2', 'parentId', '', 'objectId', '', '飞书组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (39, 6, '', '2', 'parentName', '', 'objectName', '', '飞书组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (40, 6, '', '2', 'sortIndex', '', 'order', '', '飞书组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (41, 4, '', '1', 'username', '', 'userid', '', '企业微信用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (42, 4, '', '1', 'nickName', '', 'alias', '', '企业微信用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (43, 4, '', '1', 'displayName', '', 'name', '', '企业微信用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (44, 4, '', '1', 'mobile', '', 'mobile', '', '企业微信用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (45, 4, '', '1', 'email', '', 'email', '', '企业微信用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (46, 4, '', '1', 'gender', '', 'gender', '', '企业微信用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (47, 4, '', '1', 'workPhoneNumber', '', 'telephone', '', '企业微信用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (48, 4, '', '1', 'departmentId', '', 'main_department', '', '企业微信用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (49, 4, '', '1', 'jobTitle', '', 'position', '', '企业微信用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (50, 4, '', '1', 'workAddressFormatted', '', 'address', '', '企业微信用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (51, 4, '', '2', 'orgName', '', 'name', '', '企业微信组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (52, 4, '', '2', 'orgCode', '', 'id', '', '企业微信组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (53, 4, '', '2', 'parentId', '', 'objectId', '', '企业微信组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (54, 4, '', '2', 'parentName', '', 'objectName', '', '企业微信组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (55, 4, '', '2', 'sortIndex', '', 'order', '', '企业微信组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (56, 2, '', '1', 'department', '', 'orgName', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (57, 2, '', '1', 'departmentId', '', 'id', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (58, 2, '', '1', 'formattedName', '', 'cn', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (59, 2, '', '1', 'username', '', 'sAMAccountname', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (60, 2, '', '1', 'windowsAccount', '', 'sAMAccountname', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (61, 2, '', '1', 'familyName', '', 'sn', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (62, 2, '', '1', 'givenName', '', 'givenName', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (63, 2, '', '1', 'nameZhShortSpell', '', 'initials', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (64, 2, '', '1', 'nickName', '', 'initials', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (65, 2, '', '1', 'displayName', '', 'displayName', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (66, 2, '', '1', 'description', '', 'description', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (67, 2, '', '1', 'workPhoneNumber', '', 'telephoneNumber', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (68, 2, '', '1', 'workOfficeName', '', 'physicalDeliveryOfficeName', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (69, 2, '', '1', 'workEmail', '', 'mail', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (70, 2, '', '1', 'webSite', '', 'wwwHomePage', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (71, 2, '', '1', 'workCountry', '', 'co', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (72, 2, '', '1', 'workRegion', '', 'st', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (73, 2, '', '1', 'workLocality', '', 'l', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (74, 2, '', '1', 'workStreetAddress', '', 'streetAddress', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (75, 2, '', '1', 'workPostalCode', '', 'postalCode', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (76, 2, '', '1', 'workAddressFormatted', '', 'postOfficeBox', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (77, 2, '', '1', 'mobile', '', 'mobile', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (78, 2, '', '1', 'homePhoneNumber', '', 'homePhone', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (79, 2, '', '1', 'workFax', '', 'facsimileTelephoneNumber', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (80, 2, '', '1', 'homeAddressFormatted', '', 'info', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (81, 2, '', '1', 'division', '', 'company', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (82, 2, '', '1', 'jobTitle', '', 'title', '', 'AD用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (83, 2, '', '2', 'orgCode', '', 'id', '', 'AD组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (84, 2, '', '2', 'orgName', '', 'ou', '', 'AD组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (85, 2, '', '2', 'fullName', '', 'orgName', '', 'AD组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (86, 2, '', '2', 'country', '', 'co', '', 'AD组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (87, 2, '', '2', 'region', '', 'st', '', 'AD组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (88, 2, '', '2', 'locality', '', 'l', '', 'AD组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (89, 2, '', '2', 'street', '', 'street', '', 'AD组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (90, 2, '', '2', 'postalCode', '', 'postalCode', '', 'AD组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (91, 2, '', '2', 'description', '', 'description', '', 'AD组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (92, 1, '', '1', 'department', '', 'orgName', '', 'LDAP用户', 0, NULL, 0, '2024-09-24 04:06:12');
INSERT INTO `sync_job_config_field` VALUES (93, 1, '', '1', 'departmentId', '', 'id', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (94, 1, '', '1', 'formattedName', '', 'givenName', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (95, 1, '', '1', 'username', '', 'uid', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (96, 1, '', '1', 'windowsAccount', '', 'uid', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (97, 1, '', '1', 'familyName', '', 'sn', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (98, 1, '', '1', 'givenName', '', 'givenName', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (99, 1, '', '1', 'nickName', '', 'initials', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (100, 1, '', '1', 'nameZhShortSpell', '', 'initials', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (101, 1, '', '1', 'displayName', '', 'displayName', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (102, 1, '', '1', 'employeeNumber', '', 'employeeNumber', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (103, 1, '', '1', 'jobTitle', '', 'title', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (104, 1, '', '1', 'workOfficeName', '', 'physicalDeliveryOfficeName', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (105, 1, '', '1', 'workEmail', '', 'mail', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (106, 1, '', '1', 'workRegion', '', 'st', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (107, 1, '', '1', 'workLocality', '', 'l', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (108, 1, '', '1', 'workStreetAddress', '', 'street', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (109, 1, '', '1', 'workPostalCode', '', 'postalCode', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (110, 1, '', '1', 'workAddressFormatted', '', 'postOfficeBox', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (111, 1, '', '1', 'workFax', '', 'facsimileTelephoneNumber', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (112, 1, '', '1', 'homePhoneNumber', '', 'homePhone', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (113, 1, '', '1', 'homeAddressFormatted', '', 'homePostalAddress', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (114, 1, '', '1', 'mobile', '', 'mobile', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (115, 1, '', '1', 'preferredLanguage', '', 'preferredLanguage', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (116, 1, '', '1', 'description', '', 'description', '', 'LDAP用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (117, 1, '', '2', 'orgCode', '', 'id', '', 'LDAP组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (118, 1, '', '2', 'orgName', '', 'ou', '', 'LDAP组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (119, 1, '', '2', 'fullName', '', 'orgName', '', 'LDAP组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (120, 1, '', '2', 'region', '', 'st', '', 'LDAP组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (121, 1, '', '2', 'locality', '', 'l', '', 'LDAP组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (122, 1, '', '2', 'street', '', 'street', '', 'LDAP组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (123, 1, '', '2', 'postalCode', '', 'postalCode', '', 'LDAP组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (124, 1, '', '2', 'address', '', 'postalAddress', '', 'LDAP组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (125, 1, '', '2', 'phone', '', 'telephoneNumber', '', 'LDAP组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (126, 1, '', '2', 'fax', '', 'facsimileTelephoneNumber', '', 'LDAP组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (127, 1, '', '2', 'description', '', 'description', '', 'LDAP组织', 0, NULL, 0, '2024-09-24 04:12:57');
INSERT INTO `sync_job_config_field` VALUES (128, 7, '', '1', 'id', '', 'id', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (129, 7, '', '1', 'username', '', 'username', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (130, 7, '', '1', 'picture', '', 'picture', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (131, 7, '', '1', 'displayName', '', 'displayname', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (132, 7, '', '1', 'nickName', '', 'nickname', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (133, 7, '', '1', 'mobile', '', 'mobile', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (134, 7, '', '1', 'email', '', 'email', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (135, 7, '', '1', 'birthDate', '', 'birthdate', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (136, 7, '', '1', 'userType', '', 'usertype', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (137, 7, '', '1', 'userState', '', 'userstate', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (138, 7, '', '1', 'windowsAccount', '', 'windowsaccount', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (139, 7, '', '1', 'givenName', '', 'givenname', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (140, 7, '', '1', 'middleName', '', 'middlename', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (141, 7, '', '1', 'married', '', 'married', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (142, 7, '', '1', 'gender', '', 'gender', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (143, 7, '', '1', 'idType', '', 'idtype', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (144, 7, '', '1', 'idCardNo', '', 'idcardno', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (145, 7, '', '1', 'webSite', '', 'website', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (146, 7, '', '1', 'startWorkDate', '', 'startworkdate', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (147, 7, '', '1', 'workCountry', '', 'workcountry', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (148, 7, '', '1', 'workRegion', '', 'workregion', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (149, 7, '', '1', 'workLocality', '', 'worklocality', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (150, 7, '', '1', 'workStreetAddress', '', 'workstreetaddress', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (151, 7, '', '1', 'workAddressFormatted', '', 'workaddressformatted', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (152, 7, '', '1', 'workEmail', '', 'workemail', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (153, 7, '', '1', 'workPhoneNumber', '', 'workphonenumber', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (154, 7, '', '1', 'workPostalCode', '', 'workpostalcode', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (155, 7, '', '1', 'workFax', '', 'workfax', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (156, 7, '', '1', 'workOfficeName', '', 'workofficename', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (157, 7, '', '1', 'homeCountry', '', 'homecountry', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (158, 7, '', '1', 'homeRegion', '', 'homeregion', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (159, 7, '', '1', 'homeLocality', '', 'homelocality', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (160, 7, '', '1', 'homeStreetAddress', '', 'homestreetaddress', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (161, 7, '', '1', 'homeAddressFormatted', '', 'homeaddressformatted', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (162, 7, '', '1', 'homeEmail', '', 'homeemail', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (163, 7, '', '1', 'homePhoneNumber', '', 'homephonenumber', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (164, 7, '', '1', 'homePostalCode', '', 'homepostalcode', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (165, 7, '', '1', 'homeFax', '', 'homefax', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (166, 7, '', '1', 'employeeNumber', '', 'employeenumber', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (167, 7, '', '1', 'costCenter', '', 'costcenter', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (168, 7, '', '1', 'organization', '', 'organization', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (169, 7, '', '1', 'division', '', 'division', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (170, 7, '', '1', 'departmentId', '', 'departmentid', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (171, 7, '', '1', 'department', '', 'department', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (172, 7, '', '1', 'jobTitle', '', 'jobtitle', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (173, 7, '', '1', 'jobLevel', '', 'joblevel', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (174, 7, '', '1', 'managerId', '', 'managerid', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (175, 7, '', '1', 'manager', '', 'manager', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (176, 7, '', '1', 'assistantId', '', 'assistantid', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (177, 7, '', '1', 'assistant', '', 'assistant', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (178, 7, '', '1', 'entryDate', '', 'entrydate', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (179, 7, '', '1', 'quitDate', '', 'quitdate', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (180, 7, '', '1', 'ldapDn', '', 'ldapdn', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (181, 7, '', '1', 'description', '', 'description', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (182, 7, '', '1', 'status', '', 'status', '', 'jdbc用户', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (183, 7, '', '2', 'id', '', 'id', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (184, 7, '', '2', 'orgCode', '', 'orgcode', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (185, 7, '', '2', 'orgName', '', 'orgname', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (186, 7, '', '2', 'fullName', '', 'fullname', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (187, 7, '', '2', 'parentId', '', 'parentid', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (188, 7, '', '2', 'parentCode', '', 'parentcode', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (189, 7, '', '2', 'type', '', 'type', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (190, 7, '', '2', 'codePath', '', 'codepath', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (191, 7, '', '2', 'namePath', '', 'namepath', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (192, 7, '', '2', 'level', '', 'level', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (193, 7, '', '2', 'hasChild', '', 'haschild', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (194, 7, '', '2', 'division', '', 'division', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (195, 7, '', '2', 'country', '', 'country', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (196, 7, '', '2', 'region', '', 'region', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (197, 7, '', '2', 'locality', '', 'locality', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (198, 7, '', '2', 'street', '', 'street', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (199, 7, '', '2', 'address', '', 'address', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (200, 7, '', '2', 'contact', '', 'contact', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (201, 7, '', '2', 'postalCode', '', 'postalcode', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (202, 7, '', '2', 'phone', '', 'phone', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (203, 7, '', '2', 'fax', '', 'fax', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (204, 7, '', '2', 'email', '', 'email', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (205, 7, '', '2', 'sortIndex', '', 'sortindex', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (206, 7, '', '2', 'ldapDn', '', 'ldapdn', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (207, 7, '', '2', 'description', '', 'description', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (208, 7, '', '2', 'status', '', 'status', '', 'jdbc组织', 0, NULL, 0, NULL);
INSERT INTO `sync_job_config_field` VALUES (209, 6, '', '1', 'departmentId', '', 'objectId', '', '飞书用户', 0, NULL, 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
