import 'dart:io';

import 'package:dio/dio.dart';
import 'package:maxkey_flutter/persistent.dart';
import 'package:maxkey_flutter/utils.dart';

typedef ExpectedErrorHandler = void Function(String msg);

class AuthnService {
  final Dio _dio;

  AuthnService(this._dio);

  /// 获取 state 类型
  Future<String?> get() async {
    try {
      LOGGER.i("AuthnService.get(): ");
      LOGGER.i("GET: /login/get?_allow_anonymous=true");

      final res = await _dio.get(
        "/login/get?_allow_anonymous=true",
        queryParameters: {"remember_me": true},
      );
      return res.data["data"]["state"];
    } catch (err) {
      LOGGER.e("AuthnService.get(): ");
      LOGGER.e(err);
    }
    return null;
  }

  /// 短信验证码
  Future<bool> produceOtp({
    required ExpectedErrorHandler expectedErrorHandler,
    required String mobile,
  }) async {
    try {
      LOGGER.i("AuthnService.produceOtp(): ");
      LOGGER.i("GET: /login/sendotp/$mobile?_allow_anonymous=true");

      final res = await _dio.get(
        "/login/sendotp/$mobile?_allow_anonymous=true",
        queryParameters: {"mobile": mobile},
      );

      if (res.data["code"] != 0) {
        final msg = res.data["message"] ?? "验证码发送失败";
        expectedErrorHandler(msg);
        LOGGER.w(msg);
        return false;
      }

      return true;
    } catch (err) {
      LOGGER.e("AuthnService.produceOtp(): ");
      LOGGER.e(err);
    }
    return false;
  }

  /// 账密登录
  Future<bool> loginNormal({
    required ExpectedErrorHandler expectedErrorHandler,
    required String state,
    required String username,
    required String password,
    required String captcha,
  }) async {
    try {
      LOGGER.i("AuthnService.loginNormal(): ");
      LOGGER.i("POST: /login/signin?_allow_anonymous=true");

      final res = await _dio.post(
        "/login/signin?_allow_anonymous=true",
        data: {
          "authType": "app",
          "state": state,
          "username": username,
          "password": password,
          "captcha": captcha,
          "remeberMe": true,
        },
      );

      if (res.data["code"] != 0) {
        final msg = res.data["message"] ?? "登录失败";
        expectedErrorHandler(msg);
        LOGGER.w(msg);
        return false;
      }

      await onlineAuth(
        token: res.data["data"]["token"],
        onlineTicket: res.data["data"]["ticket"],
        username: res.data["data"]["username"],
      );
      return true;
    } catch (err) {
      LOGGER.e("AuthnService.loginNormal(): ");
      LOGGER.e(err);
    }
    return false;
  }

  Future<bool> scanCode({
    required ExpectedErrorHandler expectedErrorHandler,
    required String code,
  }) async {
    LOGGER.i("AuthnService.scanCode(): ");
    final token = MaxKeyPersistent.instance.token;
    if (token == null) {
      LOGGER.i("未登录");
      expectedErrorHandler("未登录");
      return false;
    }

    try {
      LOGGER.i("POST: /login/scanCode");
      final res = await _dio.post(
        "/login/scanCode",
        data: {"jwtToken": token, "code": code},
      );

      if (res.data["code"] != 0) {
        final msg = res.data["message"] ?? "扫码登录失败";
        expectedErrorHandler(msg);
        LOGGER.w(msg);
        return false;
      }

      return true;
    } catch (err) {
      LOGGER.e("AuthnService.scanCode(): ");
      LOGGER.e(err);
    }
    return false;
  }

  Future<void> onlineAuth({
    required String token,
    required String onlineTicket,
    required String username,
  }) async {
    await MaxKeyPersistent.instance.setUser(username);
    await MaxKeyPersistent.instance.setToken(token);
    _dio.options.headers[HttpHeaders.authorizationHeader] = token;
  }

  bool localAuth() {
    final token = MaxKeyPersistent.instance.token;
    if (token == null) return false;

    _dio.options.headers[HttpHeaders.authorizationHeader] = token;
    return true;
  }

  /// 登出并清除本地缓存的 token
  Future<void> logout() async {
    try {
      LOGGER.i("AuthnService.logout(): ");
      LOGGER.i("GET: /logout");
      await _dio.get("/logout").timeout(const Duration(seconds: 5));
    } catch (err) {
      LOGGER.e("AuthnService.logout(): ");
      LOGGER.e(err);
    }

    _dio.options.headers.remove(HttpHeaders.authorizationHeader);

    await MaxKeyPersistent.instance.clearToken();
  }
}
