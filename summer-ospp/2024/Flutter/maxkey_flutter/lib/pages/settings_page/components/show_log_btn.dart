part of 'package:maxkey_flutter/pages/settings_page/page.dart';

class _ShowLogBtn extends StatelessWidget {
  const _ShowLogBtn({super.key});

  @override
  Widget build(BuildContext context) {
    return _SettingTile(
      title: AppLocalizations.of(context)!.settingsPageShowLogBtnTitle,
      action: const Icon(Icons.arrow_outward),
      onTap: () {
        Navigator.of(context).push(
          CupertinoPageRoute(builder: (context) => const _LogDisplayPage()),
        );
      },
    );
  }
}

class _LogDisplayPage extends StatefulWidget {
  const _LogDisplayPage({super.key});

  @override
  State<_LogDisplayPage> createState() => __LogDisplayPageState();
}

class __LogDisplayPageState extends State<_LogDisplayPage> {
  final logEditingController = TextEditingController();

  @override
  void initState() {
    super.initState();
    final strbuf = StringBuffer();
    for (var item in LOGGER_MEMORY.buffer) {
      strbuf.writeAll(item.lines);
      strbuf.writeln();
    }
    logEditingController.text = strbuf.toString();
    strbuf.clear();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(AppLocalizations.of(context)!.settingsPageLogDisplayPageTitle)),
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 8.0),
        child: TextField(
          controller: logEditingController,
          maxLines: null,
          decoration: const InputDecoration(border: InputBorder.none),
        ),
      ),
    );
  }
}
