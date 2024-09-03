import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:maxkey_flutter/maxkey/services/authn.service.dart';
import 'package:maxkey_flutter/maxkey/services/image_captcha.service.dart';
import 'package:maxkey_flutter/maxkey/services/time_based.service.dart';
import 'package:maxkey_flutter/maxkey/services/users.service.dart';
import 'package:maxkey_flutter/persistent.dart';
import 'package:maxkey_flutter/utils.dart';

/// 会在需要刷新的时候通知
class MaxKey with ChangeNotifier {
  final _dio = Dio(BaseOptions(
    baseUrl: MaxKeyPersistent.instance.baseUrl,
    connectTimeout: const Duration(seconds: 10),
  ));
  late final authnService = AuthnService(_dio);
  late final imageCaptchaService = ImageCaptchaService(_dio);
  late final timeBasedService = TimeBasedService(_dio);
  late final usersService = UsersService(_dio);

  static MaxKey? _instance;
  static MaxKey get instance {
    _instance ??= MaxKey._();
    return _instance!;
  }

  MaxKey._() {
    _dio.interceptors.add(InterceptorsWrapper(
      onResponse: (response, handler) {
        final cookies = response.headers[HttpHeaders.setCookieHeader];
        if (cookies != null) {
          maxKeyCookies = List.from(cookies);
        }
        handler.next(response);
      },
      onError: (error, handler) {
        LOGGER.e("InterceptorsWrapper.onError(): ");
        LOGGER.e(error.type);
        LOGGER.e(error.response);
        if (error.type == DioExceptionType.badResponse) {
          SCAFFOLD_MESSENGER_KEY.currentState?.showSnackBar(
            SnackBar(
              content: const Text("登录状态过期。请重新登录"),
              action: SnackBarAction(
                label: "重新登录",
                onPressed: () async {
                  _dio.options.headers.remove(HttpHeaders.authorizationHeader);
                  await MaxKeyPersistent.instance.clearToken();
                  NAVIGATOR_KEY.currentContext?.pushReplacement(
                    RoutePath.loginPage,
                  );
                },
              ),
            ),
          );
        }
        handler.next(error);
      },
    ));
  }

  void updateBaseUrl() {
    LOGGER.i("MaxKey.updateBaseUrl(): ");
    LOGGER.i("old baseUrl: ${_dio.options.baseUrl}");
    _dio.options.baseUrl = MaxKeyPersistent.instance.baseUrl;
    LOGGER.i("new baseUrl: ${_dio.options.baseUrl}");
    notifyListeners();
  }

  List<String> maxKeyCookies = [];

  Future<bool> maxKeyNetworkTest({String? host}) async {
    try {
      LOGGER.i("MaxKey.maxKeyNetworkTest(): ");
      LOGGER.i(
        "[MaxKeyNetworkTest] GET: ${host == null ? MaxKeyPersistent.instance.baseUrl : "http://$host/sign"}",
      );

      await _dio
          .get(host == null
              ? MaxKeyPersistent.instance.baseUrl
              : "http://$host/sign")
          .timeout(const Duration(seconds: 5));
      LOGGER.i("MaxKeyNetworkTest: true");
      return true;
    } catch (err) {
      LOGGER.e("MaxKey.maxKeyNetworkTest(): ");
      LOGGER.e(err);
    }
    LOGGER.i("MaxKeyNetworkTest: false");
    return false;
  }
}
