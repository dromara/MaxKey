part of 'package:maxkey_flutter/pages/login_page/page.dart';

/// login by username and password
class _NormalLoginView extends StatefulWidget {
  const _NormalLoginView({
    super.key,
    required this.loginState,
  });

  final String loginState;

  @override
  State<_NormalLoginView> createState() => _NormalLoginViewState();
}

class _NormalLoginViewState extends State<_NormalLoginView> {
  final usernameEditingController = TextEditingController();
  final passwordEditingController = TextEditingController();
  final captchaEditingController = TextEditingController();
  late final captchaViewController = _CaptchaViewController(widget.loginState);
  final loginBtnController = WidgetStatesController();
  ValueNotifier<bool> obscureText = ValueNotifier(true);

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        TextField(
          controller: usernameEditingController,
          decoration: InputDecoration(
            labelText: AppLocalizations.of(context)!.loginPageLoginViewUsername,
            border: const OutlineInputBorder(),
          ),
        ),
        const SizedBox(height: 16.0),
        ListenableBuilder(
          listenable: obscureText,
          builder: (context, _) {
            return TextField(
              controller: passwordEditingController,
              obscureText: obscureText.value,
              decoration: InputDecoration(
                labelText: AppLocalizations.of(context)!.loginPageLoginViewPassword,
                suffix: IconButton(
                  onPressed: () {
                    obscureText.value = !obscureText.value;
                  },
                  icon: Icon(
                    obscureText.value ? Icons.visibility_off : Icons.visibility,
                  ),
                ),
                border: const OutlineInputBorder(),
              ),
            );
          },
        ),
        const SizedBox(height: 16.0),
        TextField(
          controller: captchaEditingController,
          decoration: InputDecoration(
            labelText: AppLocalizations.of(context)!.loginPageLoginViewCaptcha,
            suffix: _CaptchaView(controller: captchaViewController),
            border: const OutlineInputBorder(),
          ),
        ),
        const SizedBox(height: 16.0),
        FilledButton(
          statesController: loginBtnController,
          onPressed: () async {
            loginBtnController.update(WidgetState.disabled, true);
            final hasSucceed = await MaxKey.instance.authnService.loginNormal(
              expectedErrorHandler: (msg) {
                loginBtnController.update(WidgetState.disabled, false);
                captchaViewController.getNewCaptcha();
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text(msg)),
                );
              },
              state: widget.loginState,
              username: usernameEditingController.text,
              password: passwordEditingController.text,
              captcha: captchaEditingController.text,
            );

            if (hasSucceed && context.mounted) {
              context.pushReplacement(RoutePath.homePage);
            }
          },
          child: Text(AppLocalizations.of(context)!.loginPageLoginViewLoginBtn),
        ),
      ],
    );
  }
}
