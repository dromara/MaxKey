<!DOCTYPE html>
<html >
<head>
    <title>Redirect Single Sign-On</title>
    <#include  "authorize_common.ftl">
    <script type="text/javascript">
        function redirectToLogin(){
            window.top.location.href ="${redirectUri}&${usernameMapping}=${username}&${passwordMapping}=${password}&timestamp=${timestamp}";
       }
    </script>
</head>

<body onload="redirectToLogin()"  style="display:none">
<form id="redirect_sso_form" name="redirect_sso_form" action="${redirectUri}" method="get">
        <table style="width:100%">
            <tr>
                <td colspan="2"><input type="submit"  name="submitBtn" value="Continue..." /></td>
            </tr>
        </table>
    </form>
</body>
</html>
