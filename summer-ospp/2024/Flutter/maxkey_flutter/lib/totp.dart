import 'package:auth_totp/auth_totp.dart';

/// otpauth://totp/{}?secret={}&issuer={}
class Totp {
  /// for persistent
  final String uri;
  final String issuer;

  final String secret;
  final int interval;

  Totp({
    required this.uri,
    required this.issuer,
    required this.secret,
    required this.interval,
  });

  String get now => AuthTOTP.generateTOTPCode(
        secretKey: secret,
        interval: interval,
      );

  static Totp? fromUri(String uri) {
    final totpUri = Uri.tryParse(uri);
    if (totpUri == null) return null;

    final query = totpUri.queryParameters;
    final secret = query["secret"];
    if (secret == null) return null;

    // final digit = query["digits"];
    final period = query["period"];
    final issuerQuery = query["issuer"];

    final lastPathSeg = totpUri.pathSegments.lastOrNull;
    final colonPos = lastPathSeg?.indexOf(":");
    final account = colonPos == null
        ? null
        : colonPos == -1
            ? lastPathSeg!
            : lastPathSeg!.substring(colonPos + 1);

    final issuer = issuerQuery == null
        ? lastPathSeg ?? "UNKNOWN"
        : "$issuerQuery${account == null ? "" : ":$account"}";

    return Totp(
      uri: uri,
      issuer: issuer,
      secret: secret,
      interval: period == null ? 30 : int.tryParse(period) ?? 30,
    );
  }
}
