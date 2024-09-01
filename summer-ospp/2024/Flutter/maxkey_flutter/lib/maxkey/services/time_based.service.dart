import 'package:dio/dio.dart';
import 'package:maxkey_flutter/utils.dart';

class TimeBasedService {
  final Dio _dio;
  TimeBasedService(this._dio);

  Future<bool> verify({required String totpCode}) async {
    try {
      LOGGER.i("GET: /config/verify?otp=$totpCode");

      final res = await _dio.get(
        "/config/verify",
        queryParameters: {"otp": totpCode},
      );

      if (res.data["code"] != 0) {
        LOGGER.w("验证失败：${res.data["message"]}");
        return false;
      }

      LOGGER.i("验证成功");
      return true;
    } catch (err) {
      LOGGER.e(err);
    }
    return false;
  }
}
