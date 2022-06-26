<!DOCTYPE html>
<html>
<head>
  <#include  "authorize_common.ftl">
  <title>JWT Single Sign-On</title>
<#if 'get'==tokenType>
    <script type="text/javascript">
        window.top.location.href ="${action}?${jwtName}=${token}";
    </script>
</#if> 
</head>
<#if 'post'==tokenType>
<body  onload="document.forms[0].submit()"  style="display:none">

    <form id="jwt_sso_form" name="jwt_sso_form" action="${action}" method="${tokenType}">
        <table style="width:100%">
            <tr>
                <td>token</td>
                <td><input type="text" id="tokenbased_token" name="${jwtName}" value="${token}" /></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit"  name="submitBtn" value="Continue..." /></td>
            </tr>
        </table>
    </form>
</body>
</#if>  
</html>
