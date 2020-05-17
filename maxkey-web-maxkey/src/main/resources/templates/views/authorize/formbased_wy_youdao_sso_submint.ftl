<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<#include  "formbased_common.ftl">
  	
  	<script type="text/javascript">
			$(function(){
				window.top.location.href ="https://note.youdao.com/login/acc/login?username=${username}&password=${password}&app=web&product=YNOTE&tp=urstoken&cf=2&fr=1&systemName=&deviceType=&ru=http://note.youdao.com/web/&er=http://note.youdao.com/web/?&systemName=Windows&deviceType=WindowsPC&timestamp=${currentTime}";
			});
		</script>
</head>

<body style="display:none">
	<form class="bd" name="frmLogin" method="get" id="loginForm"  target="_top"
		action="https://note.youdao.com/login/acc/login?username=${username}&password=${password}&app=web&product=YNOTE&tp=urstoken&cf=2&fr=1&systemName=&deviceType=&ru=http://note.youdao.com/web/&er=http://note.youdao.com/web/?&systemName=Windows&deviceType=WindowsPC&timestamp=${currentTime}">
		<table>
			<tr>
				<td colspan="2"><input id="formbasedsubmitbutton" type="submit" value="submit"/></td>
			</tr>
		</table>
	</form>
</body>
</html>
