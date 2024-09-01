import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:maxkey_flutter/maxkey/maxkey.dart';
import 'package:maxkey_flutter/utils.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

part 'package:maxkey_flutter/pages/login_page/components/captcha_view.dart';
part 'package:maxkey_flutter/pages/login_page/components/login_view.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  var loginGetFuture = MaxKey.instance.authnService.get();

  void _update() {
    setState(() {
      loginGetFuture = MaxKey.instance.authnService.get();
    });
  }

  @override
  void initState() {
    super.initState();
    MaxKey.instance.addListener(_update);
  }

  @override
  void dispose() {
    super.dispose();
    MaxKey.instance.removeListener(_update);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Image.asset("assets/logo_maxkey.png"),
                const SizedBox(height: 32.0),
                FutureBuilder(
                  future: loginGetFuture,
                  builder: (context, snapshot) {
                    if (snapshot.data == null) {
                      return const SizedBox(
                        width: 24,
                        height: 24,
                        child: CircularProgressIndicator(),
                      );
                    }

                    return _NormalLoginView(loginState: snapshot.data!);
                  },
                ),
                const SizedBox(height: 8.0),
                TextButton(
                  onPressed: () {
                    context.push(RoutePath.settingsPage);
                  },
                  child: Text(AppLocalizations.of(context)!.loginPageSettingBtn),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
