<!DOCTYPE html>
<html >
<head>
    <title>Central Authentication Service Single Sign-On</title>
    <#include  "authorize_common.ftl">
    <script type="text/javascript">
        function redirectToLogin(){
            var srcUrl = window.top.location.href;
            srcUrl = srcUrl.substring(srcUrl.indexOf("#"));
            var callbackUrl = "${callbackUrl}";
            if(srcUrl.indexOf("#") >-1 ){
                callbackUrl =callbackUrl + srcUrl;
            }
            window.top.location.href = callbackUrl;
       }
    </script>
</head>

<body onload="redirectToLogin()"  style="display:none">
    <form id="cas_sso_form" name="cas_sso_form" action="${callbackUrl}" method="get">
		<table style="width:100%">
			<tr>
				<td colspan="2"><input type="submit"  name="submitBtn" value="Continue..." /></td>
			</tr>
		</table>
	</form>
</body>
</html>
