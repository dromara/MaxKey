part of 'package:maxkey_flutter/pages/settings_page/page.dart';

class _SetHostDialog extends StatefulWidget {
  const _SetHostDialog({super.key});

  @override
  State<_SetHostDialog> createState() => _SetHostDialogState();
}

class _SetHostDialogState extends State<_SetHostDialog> {
  final hostEditingController =
      TextEditingController(text: MaxKeyPersistent.instance.host);

  bool isTesting = false;

  /// true: 连接成功；false：链接失败
  bool testResult = true;
  String? testDesc;

  @override
  Widget build(BuildContext context) {
    return SimpleDialog(
      title: Text(AppLocalizations.of(context)!.settingsPageHostSettingDialog),
      contentPadding: const EdgeInsets.fromLTRB(16.0, 12.0, 16.0, 16.0),
      children: [
        TextField(
          controller: hostEditingController,
          decoration: InputDecoration(
            labelText:
                AppLocalizations.of(context)!.settingsPageHostSettingDialogHost,
            border: const OutlineInputBorder(),
            helperText: testResult ? testDesc : null,
            errorText: testResult ? null : testDesc,
          ),
        ),
        const SizedBox(height: 16.0),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            TextButton.icon(
              onPressed: isTesting
                  ? null
                  : () async {
                      setState(() {
                        isTesting = true;
                      });
                      final result = await MaxKey.instance
                          .maxKeyNetworkTest(host: hostEditingController.text);
                      setState(() {
                        isTesting = false;
                        testResult = result;
                        testDesc = result
                            ? AppLocalizations.of(context)!
                                .settingsPageHostSettingDialogTestSucceed
                            : AppLocalizations.of(context)!
                                .settingsPageHostSettingDialogTestFail;
                      });
                    },
              icon: isTesting
                  ? const SizedBox(
                      width: 18,
                      height: 18,
                      child: CircularProgressIndicator(strokeWidth: 2),
                    )
                  : null,
              label: Text(AppLocalizations.of(context)!
                  .settingsPageHostSettingDialogTestBtn),
            ),
            Row(
              children: [
                TextButton(
                  onPressed: Navigator.of(context).pop,
                  child: Text(AppLocalizations.of(context)!
                      .settingsPageHostSettingDialogCancleBtn),
                ),
                const SizedBox(width: 8.0),
                TextButton(
                  onPressed: () async {
                    await MaxKeyPersistent.instance
                        .setHost(hostEditingController.text);
                    MaxKey.instance.updateBaseUrl();
                    if (context.mounted) {
                      Navigator.of(context).pop();
                    }
                  },
                  child: Text(AppLocalizations.of(context)!
                      .settingsPageHostSettingDialogConfirmBtn),
                ),
              ],
            )
          ],
        ),
      ],
    );
  }
}

class _SetHostButton extends StatelessWidget {
  const _SetHostButton({super.key});

  @override
  Widget build(BuildContext context) {
    return _SettingTile(
      title: AppLocalizations.of(context)!.settingsPageHostSettingTitle,
      desc: AppLocalizations.of(context)!.settingsPageHostSettingDesc,
      action: const Icon(Icons.arrow_outward),
      onTap: () {
        showDialog(
          context: context,
          barrierDismissible: false,
          builder: (context) => const _SetHostDialog(),
        );
      },
    );
  }
}
