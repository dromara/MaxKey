<!DOCTYPE html>
<html >
<head>
    <title>Central Authentication Service Single Sign-On</title>
    <#include  "authorize_common.ftl">
    <script type="text/javascript">
        function redirectToLogin(){
          <#if callbackUrl??>
            var srcUrl = window.top.location.href;
            srcUrl = srcUrl.substring(srcUrl.indexOf("#"));
            var callbackUrl = "${callbackUrl!}";
            if(srcUrl.indexOf("#") >-1 ){
                callbackUrl =callbackUrl + srcUrl;
            }
            window.top.location.href = callbackUrl;
          </#if>
       }
    </script>
</head>

<body onload="redirectToLogin()" >
    <form id="cas_sso_form" name="cas_sso_form" action="${callbackUrl!}" method="get"  style="display:none">
        <table style="width:100%">
            <tr>
                <td colspan="2"><input type="submit"  name="submitBtn" value="Continue..." /></td>
            </tr>
        </table>
    </form>
    <#if errorMessage??>
        service ${errorMessage!} not registered .
    </#if>
</body>
</html>
