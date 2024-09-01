part of 'package:maxkey_flutter/pages/settings_page/page.dart';

class _ThemeModeSwitch extends StatefulWidget {
  const _ThemeModeSwitch({super.key});

  @override
  State<_ThemeModeSwitch> createState() => __ThemeModeSwitchState();
}

class __ThemeModeSwitchState extends State<_ThemeModeSwitch> {
  @override
  Widget build(BuildContext context) {
    return _SettingTile(
      title: AppLocalizations.of(context)!.settingsPageThemeModeSwitchTitle,
      action: SegmentedButton(
        showSelectedIcon: false,
        segments: ThemeMode.values
            .map(
              (item) => ButtonSegment(
                value: item,
                icon: switch (item) {
                  ThemeMode.system => const Icon(Icons.brightness_auto),
                  ThemeMode.light => const Icon(Icons.light_mode),
                  ThemeMode.dark => const Icon(Icons.dark_mode),
                },
              ),
            )
            .toList(),
        selected: {MaxKeyPersistent.instance.themeMode},
        onSelectionChanged: (selected) {
          setState(() {
            MaxKeyPersistent.instance.setThemeMode(selected.first);
          });
        },
      ),
    );
  }
}