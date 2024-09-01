// ignore_for_file: constant_identifier_names, non_constant_identifier_names

import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:logger/logger.dart';

final LOGGER_MEMORY = MemoryOutput();
final LOGGER = Logger(
  filter: ProductionFilter(),
  printer: SimplePrinter(),
  output: kDebugMode
      ? MultiOutput([LOGGER_MEMORY, ConsoleOutput()])
      : LOGGER_MEMORY,
  level: Level.all,
);

final SCAFFOLD_MESSENGER_KEY = GlobalKey<ScaffoldMessengerState>();
final NAVIGATOR_KEY = GlobalKey<NavigatorState>();

class RoutePath {
  static const loginPage = "/login";
  static const homePage = "/home";
  static const scanPage = "/scan";
  static const userPage = "/user";
  static const settingsPage = "/settings";
}

extension Base64 on String {
  Uint8List base64ToBuf() {
    return base64.decode(this);
  }
}
