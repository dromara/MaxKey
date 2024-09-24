part of 'package:maxkey_flutter/pages/home_page/page.dart';

enum _TimeOfDay {
  morning,
  noon,
  afternoon,
  evening;

  const _TimeOfDay();

  String greeting(BuildContext context) => switch (this) {
        _TimeOfDay.morning =>
          AppLocalizations.of(context)!.homePageUserCardGreetingMorning,
        _TimeOfDay.noon =>
          AppLocalizations.of(context)!.homePageUserCardGreetingNoon,
        _TimeOfDay.afternoon =>
          AppLocalizations.of(context)!.homePageUserCardGreetingAfternoon,
        _TimeOfDay.evening =>
          AppLocalizations.of(context)!.homePageUserCardGreetingEvening,
      };

  static const _table = [
    evening,
    evening,
    evening,
    evening,
    evening,
    morning,
    morning,
    morning,
    morning,
    morning,
    morning,
    noon,
    noon,
    afternoon,
    afternoon,
    afternoon,
    afternoon,
    afternoon,
    afternoon,
    evening,
    evening,
    evening,
    evening,
    evening,
  ];

  factory _TimeOfDay.fromDateTime(DateTime dateTime) {
    return _table[dateTime.hour];
  }
}

class _UserCard extends StatelessWidget {
  const _UserCard({super.key, required this.user});

  final MaxKeyUser user;

  @override
  Widget build(BuildContext context) {
    final scheme = Theme.of(context).colorScheme;

    return InkWell(
      borderRadius: BorderRadius.circular(8.0),
      onTap: () {
        context.push(RoutePath.userPage, extra: user);
      },
      child: Ink(
        padding: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 16.0),
        decoration: BoxDecoration(
          color: scheme.surfaceContainer,
          borderRadius: BorderRadius.circular(8.0),
        ),
        child: Row(
          children: [
            ClipOval(
              child: user.picture == null
                  ? Image.asset(
                      "assets/logo.jpg",
                      width: 64,
                      height: 64,
                    )
                  : Image.memory(
                      user.picture!,
                      width: 64,
                      height: 64,
                    ),
            ),
            const SizedBox(width: 8),
            Expanded(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(_TimeOfDay.fromDateTime(DateTime.now())
                      .greeting(context)),
                  Text(
                    user.displayName,
                    style: const TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(width: 8),
            IconButton(
              icon: const Icon(Icons.qr_code_scanner),
              onPressed: () async {
                LOGGER.i("_UserCard: ");
                final qrCodeValue = await context.push<String?>(
                  RoutePath.scanPage,
                  extra: AppLocalizations.of(context)!.homePageUserCardScanPage,
                );

                if (qrCodeValue == null) {
                  LOGGER.w("No QR code.");
                  return;
                }

                await MaxKey.instance.authnService.scanCode(
                  expectedErrorHandler: (msg) {
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(content: Text(msg)),
                    );
                  },
                  code: qrCodeValue,
                );
                if (context.mounted) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(content: Text(AppLocalizations.of(context)!.homePageUserCardScanSucceed)),
                  );
                }
              },
            ),
            const SizedBox(width: 8),
            const SizedBox(
              height: 40,
              width: 40,
              child: Icon(Icons.keyboard_arrow_right),
            ),
          ],
        ),
      ),
    );
  }
}

/// 加载时 [_UserCard] 的替代组件，头像、问候语和用户名用透明度渐变的圆角矩形代替。
class _LoadingUserCard extends StatelessWidget {
  const _LoadingUserCard({super.key});

  @override
  Widget build(BuildContext context) {
    final scheme = Theme.of(context).colorScheme;

    return InkWell(
      borderRadius: BorderRadius.circular(8.0),
      onTap: () {
        context.push(RoutePath.userPage);
      },
      child: Ink(
        padding: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 16.0),
        decoration: BoxDecoration(
          color: scheme.surfaceContainer,
          borderRadius: BorderRadius.circular(8.0),
        ),
        child: RepeatTweenAnimationBuilder(
          tween: Tween(begin: 0.3, end: 0.7),
          duration: const Duration(seconds: 3),
          builder: (context, value, _) => Row(
            children: [
              Container(
                width: 64,
                height: 64,
                decoration: BoxDecoration(
                  color: scheme.outline.withOpacity(value),
                  borderRadius: BorderRadius.circular(32),
                ),
              ),
              const SizedBox(width: 8),
              Expanded(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Container(
                      height: 14,
                      width: 56,
                      decoration: BoxDecoration(
                        color: scheme.outline.withOpacity(value),
                        borderRadius: BorderRadius.circular(4.0),
                      ),
                    ),
                    const SizedBox(height: 4.0),
                    Container(
                      height: 18,
                      width: 72,
                      decoration: BoxDecoration(
                        color: scheme.outline.withOpacity(value),
                        borderRadius: BorderRadius.circular(4.0),
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(width: 8),
              const SizedBox(
                height: 40,
                width: 40,
                child: Icon(Icons.keyboard_arrow_right),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
