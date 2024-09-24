// ignore_for_file: constant_identifier_names, non_constant_identifier_names

import 'package:flutter/material.dart';
import 'package:maxkey_flutter/totp.dart';
import 'package:shared_preferences/shared_preferences.dart';

class MaxKeyPersistent {
  static const String _CURR_USER_KEY = "CurrUser";

  static const String _THEME_MODE_KEY = "ThemeMode";

  /// 和用户绑定
  String get _TOKEN_KEY => "$_currUser.Token";

  /// 不和用户绑定
  static const String _HOST_KEY = "Host";
  static const String _DEFAULT_HOST = "192.168.1.66";

  /// 和用户绑定
  String get _TOTP_LIST_KEY => "$_currUser.TotpList";

  /// must call [init] first
  static final MaxKeyPersistent instance = MaxKeyPersistent();

  late SharedPreferences _sp;

  static Future<void> init() async {
    instance._sp = await SharedPreferences.getInstance();
    instance._readTotps();
  }

  ThemeMode get themeMode => switch (_sp.getString(_THEME_MODE_KEY)) {
        "light" => ThemeMode.light,
        "dark" => ThemeMode.dark,
        _ => ThemeMode.system,
      };

  late ValueNotifier<ThemeMode> themeModeListenable = ValueNotifier(themeMode);

  String? get _currUser => _sp.getString(_CURR_USER_KEY);

  String? get token => _sp.getString(_TOKEN_KEY);

  /// example: 192.168.220.26:9527
  String get host => _sp.getString(_HOST_KEY) ?? _DEFAULT_HOST;
  String get baseUrl => "http://$host/sign";

  final List<Totp> _totps = [];
  void _readTotps() {
    final totpUris = _sp.getStringList(_TOTP_LIST_KEY);
    if (totpUris == null) return;

    for (final uri in totpUris) {
      final parsed = Totp.fromUri(uri);
      if (parsed == null) continue;
      _totps.add(parsed);
    }
  }

  List<Totp> get totps => _totps;

  Future<bool> setThemeMode(ThemeMode themeMode) {
    themeModeListenable.value = themeMode;
    return _sp.setString(_THEME_MODE_KEY, themeMode.name);
  }

  Future<bool> setUser(String username) async {
    final result = await _sp.setString(_CURR_USER_KEY, username);
    _totps.clear();
    if (result) {
      instance._readTotps();
    }
    return result;
  }

  Future<bool> setToken(String token) => _sp.setString(_TOKEN_KEY, token);

  /// example: 192.168.220.26:9527
  Future<bool> setHost(String host) => _sp.setString(_HOST_KEY, host);

  Future<bool> saveTotps(List<Totp> totps) => _sp.setStringList(
        _TOTP_LIST_KEY,
        List.generate(totps.length, (i) => totps[i].uri),
      );

  Future<bool> clearToken() => _sp.remove(_TOKEN_KEY);
}
