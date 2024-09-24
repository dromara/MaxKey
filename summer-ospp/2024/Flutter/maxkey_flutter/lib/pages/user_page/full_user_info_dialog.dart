part of 'package:maxkey_flutter/pages/user_page/page.dart';

class _FullUserInfoDialog extends StatelessWidget {
  const _FullUserInfoDialog({super.key, required this.userInfo});

  final MaxKeyUserInfo userInfo;

  @override
  Widget build(BuildContext context) {
    return SimpleDialog(
      title: Text(AppLocalizations.of(context)!.userPageFullUserInfoDialogTitle),
      children: [
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogDisplayName, infoValue: userInfo.displayName),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogUsername, infoValue: userInfo.username),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogGender, infoValue: userInfo.gender),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogEmployeeNumber, infoValue: userInfo.employeeNumber),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogMobile, infoValue: userInfo.mobile),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogEmail, infoValue: userInfo.email),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogUserType, infoValue: userInfo.userType),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogUserState, infoValue: userInfo.userState),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogIdType, infoValue: userInfo.idType),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogIdCardNo, infoValue: userInfo.idCardNo),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogMarried, infoValue: userInfo.married),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogBirth, infoValue: userInfo.birthDate),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogOrganization, infoValue: userInfo.organization),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogDivision, infoValue: userInfo.division),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogDepartmentId, infoValue: userInfo.departmentId),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogDepartment, infoValue: userInfo.department),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogJobTitle, infoValue: userInfo.jobTitle),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogJobLevel, infoValue: userInfo.jobLevel),
        _UserInfoTile(infoDes: AppLocalizations.of(context)!.userPageFullUserInfoDialogManager, infoValue: userInfo.manager),
      ],
    );
  }
}

class _UserInfoTile extends StatelessWidget {
  const _UserInfoTile({
    super.key,
    required this.infoDes,
    required this.infoValue,
  });

  final String infoDes;
  final String infoValue;

  @override
  Widget build(BuildContext context) {
    return ListTile(title: Text(infoDes), subtitle: Text(infoValue));
  }
}
