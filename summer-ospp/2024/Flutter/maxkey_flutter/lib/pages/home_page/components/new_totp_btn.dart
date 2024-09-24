part of 'package:maxkey_flutter/pages/home_page/page.dart';

class _NewTotpBtn extends StatelessWidget {
  const _NewTotpBtn({super.key, required this.controller});

  final _TotpListViewController controller;

  @override
  Widget build(BuildContext context) {
    final scheme = Theme.of(context).colorScheme;

    return Ink(
      decoration: BoxDecoration(
        color: scheme.surfaceContainer,
        borderRadius: BorderRadius.circular(8.0),
      ),
      child: InkWell(
        borderRadius: BorderRadius.circular(8.0),
        onTap: () async {
          LOGGER.i("_NewTotpBtn: ");
          final qrCodeValue = await context.push<String?>(
            RoutePath.scanPage,
            extra: AppLocalizations.of(context)!.homePageNewTotpBtnScanPage,
          );

          if (qrCodeValue == null) {
            LOGGER.w("No QR code.");
            return;
          }

          final newTotp = Totp.fromUri(qrCodeValue);
          if (newTotp == null) {
            LOGGER.w("Unsupported QR code.");
            if (context.mounted) {
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text(AppLocalizations.of(context)!.homePagenewTotpBtnErr)),
              );
            }
            return;
          }

          final totps = MaxKeyPersistent.instance.totps;
          totps.add(newTotp);
          await MaxKeyPersistent.instance.saveTotps(totps);

          controller.update();
          LOGGER.i("TOTP added.");
        },
        child: Padding(
          padding: const EdgeInsets.symmetric(vertical: 16.0),
          child: Center(
            child: Text(
              AppLocalizations.of(context)!.homePagenewTotpBtn,
              style: const TextStyle(
                fontSize: 16,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ),
      ),
    );
  }
}
