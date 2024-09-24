import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:maxkey_flutter/app_color_scheme.dart';
import 'package:maxkey_flutter/maxkey/maxkey.dart';
import 'package:maxkey_flutter/maxkey/services/users.service.dart';
import 'package:maxkey_flutter/pages/home_page/page.dart';
import 'package:maxkey_flutter/pages/login_page/page.dart';
import 'package:maxkey_flutter/pages/scan_page.dart';
import 'package:maxkey_flutter/pages/settings_page/page.dart';
import 'package:maxkey_flutter/pages/user_page/page.dart';
import 'package:maxkey_flutter/persistent.dart';
import 'package:maxkey_flutter/utils.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await MaxKeyPersistent.init();
  runApp(const AppEntry());
}

class AppEntry extends StatelessWidget {
  const AppEntry({super.key});

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder(
        valueListenable: MaxKeyPersistent.instance.themeModeListenable,
        builder: (context, value, _) {
          return MaterialApp.router(
            scaffoldMessengerKey: SCAFFOLD_MESSENGER_KEY,
            themeMode: value,
            theme: ThemeData.from(colorScheme: AppColorScheme.lightScheme()),
            darkTheme: ThemeData.from(colorScheme: AppColorScheme.darkScheme()),
            localizationsDelegates: const <LocalizationsDelegate<dynamic>>[
              AppLocalizations.delegate,
              GlobalCupertinoLocalizations.delegate,
              GlobalMaterialLocalizations.delegate,
              GlobalWidgetsLocalizations.delegate,
            ],
            supportedLocales: AppLocalizations.supportedLocales,
            routerConfig: _routerConfig,
          );
        });
  }
}

GoRouter _routerConfig = GoRouter(
  navigatorKey: NAVIGATOR_KEY,
  initialLocation: !MaxKey.instance.authnService.localAuth()
      ? RoutePath.loginPage
      : RoutePath.homePage,
  routes: [
    GoRoute(
      path: RoutePath.loginPage,
      pageBuilder: (context, state) => const CupertinoPage(child: LoginPage()),
    ),
    GoRoute(
      path: RoutePath.homePage,
      pageBuilder: (context, state) => const CupertinoPage(child: HomePage()),
    ),
    GoRoute(
      path: RoutePath.scanPage,
      pageBuilder: (context, state) => CupertinoPage(
        child: ScanPage(title: state.extra as String),
      ),
    ),
    GoRoute(
      path: RoutePath.userPage,
      pageBuilder: (context, state) => CupertinoPage(
        child: UserPage(user: state.extra as MaxKeyUser?),
      ),
    ),
    GoRoute(
      path: RoutePath.settingsPage,
      pageBuilder: (context, state) =>
          const CupertinoPage(child: SettingsPage()),
    ),
  ],
);

// const _supportedLocales = <Locale>[
//   Locale('en', 'US'),
//   // generic Chinese 'zh'
//   Locale.fromSubtags(languageCode: 'zh'),
//   // generic simplified Chinese 'zh_Hans'
//   Locale.fromSubtags(languageCode: 'zh', scriptCode: 'Hans'),
//   // generic traditional Chinese 'zh_Hant'
//   Locale.fromSubtags(languageCode: 'zh', scriptCode: 'Hant'),
//   // 'zh_Hans_CN'
//   Locale.fromSubtags(languageCode: 'zh', scriptCode: 'Hans', countryCode: 'CN'),
//   // 'zh_Hant_TW'
//   Locale.fromSubtags(languageCode: 'zh', scriptCode: 'Hant', countryCode: 'TW'),
//   // 'zh_Hant_HK'
//   Locale.fromSubtags(languageCode: 'zh', scriptCode: 'Hant', countryCode: 'HK'),
// ];
