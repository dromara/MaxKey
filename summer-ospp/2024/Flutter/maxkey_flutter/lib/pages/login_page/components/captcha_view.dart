part of 'package:maxkey_flutter/pages/login_page/page.dart';

class _CaptchaViewController extends ChangeNotifier {
  final String loginState;
  late Future<Uint8List?> captcha;

  _CaptchaViewController(this.loginState) {
    captcha = MaxKey.instance.imageCaptchaService.captcha(state: loginState);
  }

  void getNewCaptcha() {
    captcha = MaxKey.instance.imageCaptchaService.captcha(state: loginState);
    notifyListeners();
  }
}

class _CaptchaView extends StatelessWidget {
  const _CaptchaView({super.key, required this.controller});

  final _CaptchaViewController controller;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: controller.getNewCaptcha,
      child: ListenableBuilder(
        listenable: controller,
        builder: (context, _) {
          return FutureBuilder(
            future: controller.captcha,
            builder: (context, snapshot) {
              if (snapshot.data == null) {
                return const Center(
                  child: SizedBox(
                    width: 16,
                    height: 16,
                    child: CircularProgressIndicator(),
                  ),
                );
              }

              return Image.memory(snapshot.data!, width: 80, height: 25);
            },
          );
        },
      ),
    );
  }
}
