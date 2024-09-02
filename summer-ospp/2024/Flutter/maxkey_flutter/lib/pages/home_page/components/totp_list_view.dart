part of 'package:maxkey_flutter/pages/home_page/page.dart';

class _TotpListViewController with ChangeNotifier {
  List<Totp> get totps => MaxKeyPersistent.instance.totps;

  bool multiSelect = false;
  final Set<Totp> selected = {};

  void setMultiSelect(bool value) {
    multiSelect = value;
    selected.clear();
    notifyListeners();
  }

  void _select(Totp totp) {
    if (!multiSelect) return;

    selected.add(totp);
  }

  void _unselect(Totp totp) {
    if (!multiSelect) return;

    selected.remove(totp);
  }

  void selectOrNot(Totp totp) {
    if (!multiSelect) return;

    if (selected.contains(totp)) {
      _unselect(totp);
    } else {
      _select(totp);
    }
    notifyListeners();
  }

  bool hasSelected(Totp totp) {
    if (!multiSelect) return false;

    return selected.contains(totp);
  }

  Future<void> deleteSelected() async {
    final modifiedTotps = totps;
    for (var selectedItem in selected) {
      modifiedTotps.remove(selectedItem);
    }
    await MaxKeyPersistent.instance.saveTotps(modifiedTotps);
    setMultiSelect(false);
  }

  void update() => notifyListeners();
}

class _TotpListView extends StatelessWidget {
  const _TotpListView({super.key, required this.controller});

  final _TotpListViewController controller;

  @override
  Widget build(BuildContext context) {
    final scheme = Theme.of(context).colorScheme;
    return Expanded(
      child: ClipRRect(
        borderRadius: BorderRadius.circular(8.0),
        child: Ink(
          decoration: BoxDecoration(
            color: scheme.surfaceContainer,
            borderRadius: BorderRadius.circular(8.0),
          ),
          child: ListenableBuilder(
            listenable: controller,
            builder: (context, _) {
              if (controller.totps.isEmpty) {
                return Center(
                    child: Text(AppLocalizations.of(context)!
                        .homePageTotpListViewNoTOTP));
              }

              return Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Expanded(
                    child: ListView.builder(
                      itemCount: controller.totps.length,
                      itemBuilder: (context, i) => _TotpCodeCard(
                        totp: controller.totps[i],
                        controller: controller,
                      ),
                    ),
                  ),
                  if (controller.multiSelect) ...[
                    Ink(
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(8.0),
                      ),
                      child: InkWell(
                        onTap: () {
                          controller.setMultiSelect(false);
                        },
                        borderRadius: BorderRadius.circular(8.0),
                        child: Center(
                          child: Padding(
                            padding: const EdgeInsets.symmetric(vertical: 16.0),
                            child: Icon(
                              Icons.cancel_outlined,
                              color: scheme.onSurface,
                            ),
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(height: 8.0),
                    Ink(
                      decoration: BoxDecoration(
                        color: scheme.errorContainer,
                        borderRadius: BorderRadius.circular(8.0),
                      ),
                      child: InkWell(
                        onTap: () async {
                          final confirm = await showDialog<bool>(
                            context: context,
                            builder: (context) => AlertDialog(
                              title: Text(AppLocalizations.of(context)!
                                  .homePageTotpListViewConfirmDialog),
                              actions: [
                                TextButton(
                                  onPressed: () {
                                    Navigator.of(context).pop(false);
                                  },
                                  child: Text(AppLocalizations.of(context)!
                                      .homePageTotpListViewConfirmDialogCancelBtn),
                                ),
                                TextButton(
                                  onPressed: () {
                                    Navigator.of(context).pop(true);
                                  },
                                  child: Text(AppLocalizations.of(context)!
                                      .homePageTotpListViewConfirmDialogConfirmBtn),
                                ),
                              ],
                            ),
                          );
                          if (confirm == true) {
                            await controller.deleteSelected();
                          }
                        },
                        borderRadius: BorderRadius.circular(8.0),
                        child: Center(
                          child: Padding(
                            padding: const EdgeInsets.symmetric(vertical: 16.0),
                            child: Icon(Icons.delete, color: scheme.error),
                          ),
                        ),
                      ),
                    ),
                  ]
                ],
              );
            },
          ),
        ),
      ),
    );
  }
}

class _TotpCodeCard extends StatefulWidget {
  const _TotpCodeCard(
      {super.key, required this.totp, required this.controller});

  final Totp totp;
  final _TotpListViewController controller;

  @override
  State<_TotpCodeCard> createState() => _TotpCodeCardState();
}

class _TotpCodeCardState extends State<_TotpCodeCard> {
  late String password;
  late Timer updater;
  int validity = 0;

  int getValidity() {
    final utc = DateTime.timestamp();
    return widget.totp.interval -
        (utc.millisecondsSinceEpoch ~/ 1000 % widget.totp.interval);
  }

  @override
  void initState() {
    super.initState();
    password = widget.totp.now;
    validity = getValidity();

    updater = Timer.periodic(const Duration(seconds: 1), (timer) {
      setState(() {
        validity = getValidity();
        if (validity == widget.totp.interval) {
          LOGGER.i("totp: ${DateTime.now()}");
          password = widget.totp.now;
        }
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return ListTile(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(8.0),
      ),
      onLongPress: widget.controller.multiSelect
          ? null
          : () {
              widget.controller.setMultiSelect(!widget.controller.multiSelect);
            },
      onTap: widget.controller.multiSelect
          ? () {
              widget.controller.selectOrNot(widget.totp);
            }
          : null,
      leading: widget.controller.multiSelect
          ? Checkbox(
              value: widget.controller.hasSelected(widget.totp),
              onChanged: (_) {
                widget.controller.selectOrNot(widget.totp);
              },
            )
          : null,
      title: Text(widget.totp.issuer),
      subtitle: Text(
        password,
        style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
      ),
      trailing: Stack(
        alignment: Alignment.center,
        children: [
          CircularProgressIndicator(
            value: validity / widget.totp.interval,
          ),
          Text("$validity"),
        ],
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
    updater.cancel();
  }
}
