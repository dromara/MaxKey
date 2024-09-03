import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:maxkey_flutter/maxkey/maxkey.dart';
import 'package:maxkey_flutter/persistent.dart';
import 'package:maxkey_flutter/utils.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

part 'package:maxkey_flutter/pages/settings_page/components/theme_mode_setting.dart';
part 'package:maxkey_flutter/pages/settings_page/components/host_setting.dart';
part 'package:maxkey_flutter/pages/settings_page/components/show_log_btn.dart';

class SettingsPage extends StatelessWidget {
  const SettingsPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(AppLocalizations.of(context)!.settingsPageTitle)),
      body: const SafeArea(
        child: Padding(
          padding: EdgeInsets.symmetric(horizontal: 16.0),
          child: Column(
            children: [
              _ThemeModeSwitch(),
              SizedBox(height: 8.0),
              _SetHostButton(),
              SizedBox(height: 8.0),
              _ShowLogBtn(),
            ],
          ),
        ),
      ),
    );
  }
}

class _SettingTile extends StatelessWidget {
  const _SettingTile({
    super.key,
    required this.title,
    this.desc,
    required this.action,
    this.onTap,
  });

  final String title;
  final String? desc;
  final Widget action;
  final void Function()? onTap;

  @override
  Widget build(BuildContext context) {
    final scheme = Theme.of(context).colorScheme;
    return ListTile(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(8.0),
      ),
      tileColor: scheme.surfaceContainer,
      title: Text(title, maxLines: 1),
      subtitle: desc == null ? null : Text(desc!, maxLines: 2),
      isThreeLine: desc == null ? false : true,
      trailing: action,
      onTap: onTap,
    );
  }
}
