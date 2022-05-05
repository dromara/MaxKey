<!DOCTYPE html>
<html>
<head>
  <#include  "authorize_common.ftl">
  <title>JWT Single Sign-On</title>

</head>

<body  onload="document.forms[0].submit()"  style="display:none">
<#if 'post'==tokenType>
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
</#if>

<#if 'get'==tokenType>
    <form id="jwt_sso_form" name="jwt_sso_form" action="${action}?${jwtName}=${token}" method="${tokenType}">
        <table style="width:100%">
            <tr>
                <td colspan="2"><input type="submit"  name="submitBtn" value="Continue..." /></td>
            </tr>
        </table>
    </form>
</#if>

</body>
</html>
