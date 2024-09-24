import 'dart:typed_data';

import 'package:dio/dio.dart';
import 'package:maxkey_flutter/utils.dart';

class MaxKeyUser {
  final String displayName;
  final Uint8List? picture;

  MaxKeyUser(this.displayName, this.picture);

  factory MaxKeyUser.fromMap(Map map) {
    final String displayName = map["data"]["displayName"];
    final String? pictureBase64 = map["data"]["pictureBase64"];
    final picture = pictureBase64?.split(",")[1].base64ToBuf();
    return MaxKeyUser(displayName, picture);
  }
}

class MaxKeyUserInfo {
  MaxKeyUserInfo({
    /// 姓名
    required this.displayName,

    /// 登录账号
    required this.username,

    /// 性别
    required this.gender,

    /// 员工编号
    required this.employeeNumber,

    /// 手机号码
    required this.mobile,

    /// 邮箱
    required this.email,

    /// 用户类型
    required this.userType,

    /// 用户状态
    required this.userState,

    /// 状态
    required this.status,

    /// 证件类型
    required this.idType,

    /// 证件号码
    required this.idCardNo,

    /// 婚姻状态
    required this.married,

    /// 出生日期
    required this.birthDate,

    /// 所属组织
    required this.organization,

    /// 分支机构
    required this.division,

    /// 部门编号
    required this.departmentId,

    /// 部门名称
    required this.department,

    /// 职位
    required this.jobTitle,

    /// 级别
    required this.jobLevel,

    /// 上级经理
    required this.manager,
  });

  static MaxKeyUserInfo? fromMap(Map map) {
    return MaxKeyUserInfo(
      displayName: map["displayName"].toString(),
      username: map["username"].toString(),
      gender: switch (map["gender"]) { 1 => "女", 2 => "男", _ => _unknwon },
      employeeNumber: map["employeeNumber"]?.toString() ?? _unknwon,
      mobile: map["mobile"]?.toString() ?? _unknwon,
      email: map["email"]?.toString() ?? _unknwon,
      userType: switch (map["userType"]) {
        "EMPLOYEE" => "内部员工",
        "CONTRACTOR" => "承包商",
        "CUSTOMER" => "客户",
        "SUPPLIER" => "供应商",
        "PARTNER" => "合作伙伴",
        "EXTERNAL" => "外部用户",
        "INTERN" => "实习生",
        "TEMP" => "临时用户",
        "DEALER" => "经销商",
        _ => _unknwon,
      },
      userState: switch (map["userState"]) {
        "RESIDENT" => "在职",
        "WITHDRAWN" => "离职",
        "INACTIVE" => "停薪留职",
        "RETIREE" => "退休",
        _ => _unknwon,
      },
      status: switch (map["status"]) {
        1 => "活动",
        2 => "不活动",
        4 => "禁用",
        5 => "锁定",
        9 => "已删除",
        _ => _unknwon,
      },
      idType: switch (map["idType"]) {
        0 => "未知",
        1 => "身份证",
        2 => "护照",
        3 => "学生证",
        4 => "军人证",
        _ => _unknwon,
      },
      idCardNo: map["idCardNo"]?.toString() ?? _unknwon,
      married: switch (map["married"]) {
        0 => "未知",
        1 => "单身",
        2 => "已婚",
        3 => "离异",
        4 => "丧偶",
        _ => _unknwon,
      },
      birthDate: map["birthDate"]?.toString() ?? _unknwon,
      organization: map["organization"]?.toString() ?? _unknwon,
      division: map["division"]?.toString() ?? _unknwon,
      departmentId: map["departmentId"]?.toString() ?? _unknwon,
      department: map["deparment"]?.toString() ?? _unknwon,
      jobTitle: map["jobTitle"]?.toString() ?? _unknwon,
      jobLevel: map["jobLevel"]?.toString() ?? _unknwon,
      manager: map["manager"]?.toString() ?? _unknwon,
    );
  }

  /// 姓名
  final String displayName;

  /// 登录账号
  final String username;

  /// 性别
  final String gender;

  /// 员工编号
  final String employeeNumber;

  /// 手机号码
  final String mobile;

  /// 邮箱
  final String email;

  /// 用户类型
  final String userType;

  /// 用户状态
  final String userState;

  /// 状态
  final String status;

  /// 证件类型
  final String idType;

  /// 证件号码
  final String idCardNo;

  /// 婚姻状态
  final String married;

  /// 出生日期
  final String birthDate;

  /// 所属组织
  final String organization;

  /// 分支机构
  final String division;

  /// 部门编号
  final String departmentId;

  /// 部门名称
  final String department;

  /// 职位
  final String jobTitle;

  /// 级别
  final String jobLevel;

  /// 上级经理
  final String manager;

  static const String _unknwon = "未知";
}

class UsersService {
  final Dio _dio;
  UsersService(this._dio);

  Future<MaxKeyUser?> getBasicUserInfo() async {
    try {
      LOGGER.i("UsersService.getBasicUserInfo(): ");
      LOGGER.i("GET: /users/profile/get");

      final res = await _dio.get("/users/profile/get");
      if (res.data["code"] != 0) {
        LOGGER.w(res.data["message"]);
        return null;
      }
      return MaxKeyUser.fromMap(res.data);
    } catch (err) {
      LOGGER.i("UsersService.getBasicUserInfo(): ");
      LOGGER.e(err);
    }
    return null;
  }

  Future<MaxKeyUserInfo?> getFullUserInfo() async {
    try {
      LOGGER.i("UsersService.getFullUserInfo(): ");
      LOGGER.i("GET: /users/profile/get");

      final res = await _dio.get("/users/profile/get");
      if (res.data["code"] != 0) {
        LOGGER.w(res.data["message"]);
        return null;
      }

      return MaxKeyUserInfo.fromMap(res.data["data"]);
    } catch (err) {
      LOGGER.e("UsersService.getFullUserInfo(): ");
      LOGGER.e(err);
    }
    return null;
  }
}
