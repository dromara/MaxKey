<!DOCTYPE html>
<html >
<head>
    <title>Redirect Single Sign-On</title>
    <#include  "authorize_common.ftl">
    <script type="text/javascript">
        function redirectToLogin(){
           <#if redirect_uri??>
                window.top.location.href ="${redirect_uri!}";
           </#if>
       }
    </script>
</head>

<body onload="redirectToLogin()"  >
    <form id="redirect_sso_form" name="redirect_sso_form" action="${redirect_uri!}" method="get" style="display:none">
        <table style="width:100%">
            <tr>
                <td colspan="2"><input type="submit"  name="submitBtn" value="Continue..." /></td>
            </tr>
        </table>
    </form>
    <#if errorMessage??>
        ${errorMessage!}
    </#if>
</body>
</html>
