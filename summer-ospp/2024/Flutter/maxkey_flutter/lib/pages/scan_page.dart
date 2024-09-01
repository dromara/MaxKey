import 'dart:async';

import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:mobile_scanner/mobile_scanner.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class ScanPage extends StatelessWidget {
  const ScanPage({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(title)),
      body: ScanView(
        resultHandler: (capture, resumeScanner) {
          context.pop(capture.barcodes.first.rawValue);
        },
      ),
    );
  }
}

typedef ScanViewResultHandler = void Function(
  BarcodeCapture capture,
  void Function() resumeScanner,
);

class ScanView extends StatefulWidget {
  const ScanView({super.key, required this.resultHandler});

  /// handle the QR code result.
  ///
  /// The scanner will stop each time the result is complete.
  /// Process the result here and then resume the scanner by calling `resumeScanner`.
  final ScanViewResultHandler resultHandler;

  @override
  State<ScanView> createState() => _ScanViewState();
}

class _ScanViewState extends State<ScanView> with WidgetsBindingObserver {
  final scannerController = MobileScannerController(
    formats: [BarcodeFormat.qrCode],
  );

  StreamSubscription? subscription;

  bool hasResult = false;

  void resumeScanner() {
    hasResult = false;
    scannerController.start();
  }

  void handleQRcode(BarcodeCapture capture) {
    if (hasResult) return;
    hasResult = true;

    scannerController.stop().then((_) {
      widget.resultHandler(capture, resumeScanner);
    });
  }

  @override
  void initState() {
    super.initState();
    // Start listening to lifecycle changes.
    WidgetsBinding.instance.addObserver(this);

    // Start listening to the barcode events.
    subscription = scannerController.barcodes.listen(handleQRcode);

    // Finally, start the scanner itself.
    scannerController.start();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    // If the controller is not ready, do not try to start or stop it.
    // Permission dialogs can trigger lifecycle changes before the controller is ready.
    if (!scannerController.value.isInitialized) return;

    switch (state) {
      case AppLifecycleState.detached:
      case AppLifecycleState.hidden:
      case AppLifecycleState.paused:
        return;
      case AppLifecycleState.resumed:
        // Restart the scanner when the app is resumed.
        // Don't forget to resume listening to the barcode events.
        subscription = scannerController.barcodes.listen(handleQRcode);
        scannerController.start();
        break;
      case AppLifecycleState.inactive:
        // Stop the scanner when the app is paused.
        // Also stop the barcode events subscription.
        subscription?.cancel();
        subscription = null;
        scannerController.stop();
        break;
    }
  }

  @override
  Widget build(BuildContext context) {
    return MobileScanner(
      controller: scannerController,
      errorBuilder: (context, error, _) {
        final scheme = Theme.of(context).colorScheme;
        return ColoredBox(
          color: scheme.surface,
          child: Center(
            child: switch (error.errorCode) {
              MobileScannerErrorCode.controllerAlreadyInitialized ||
              MobileScannerErrorCode.controllerDisposed ||
              MobileScannerErrorCode.controllerUninitialized ||
              MobileScannerErrorCode.genericError ||
              MobileScannerErrorCode.unsupported =>
                Icon(Icons.error, color: scheme.error),
              MobileScannerErrorCode.permissionDenied => Text(
                  AppLocalizations.of(context)!.scanPagePermissionDeniedMsg,
                  textAlign: TextAlign.center,
                ),
            },
          ),
        );
      },
    );
  }

  @override
  void dispose() async {
    // Stop listening to lifecycle changes.
    WidgetsBinding.instance.removeObserver(this);
    // Stop listening to the barcode events.
    subscription?.cancel();
    subscription = null;
    super.dispose();
    // Finally, dispose the controller.
    scannerController.dispose();
  }
}
