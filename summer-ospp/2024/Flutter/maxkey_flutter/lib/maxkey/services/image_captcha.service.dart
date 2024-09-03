import 'dart:convert';
import 'dart:typed_data';

import 'package:dio/dio.dart';
import 'package:maxkey_flutter/utils.dart';

class ImageCaptchaService {
  final Dio _dio;
  ImageCaptchaService(this._dio);

  Future<Uint8List?> captcha({required String state}) async {
    try {
      LOGGER.i("ImageCaptchaService.captcha(): ");
      LOGGER.i("GET: /captcha?_allow_anonymous=true");

      final res = await _dio.get(
        "/captcha?_allow_anonymous=true",
        queryParameters: {"state": state},
      );

      final String base64Image = res.data["data"]["image"];
      return base64.decode(base64Image.split(",")[1]);
    } catch (err) {
      LOGGER.e("ImageCaptchaService.captcha(): ");
      LOGGER.e(err);
    }
    return null;
  }
}
