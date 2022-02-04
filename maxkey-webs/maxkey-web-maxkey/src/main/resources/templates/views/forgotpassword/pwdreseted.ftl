<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<#include  "../layout/header.ftl">
<#include  "../layout/common.cssjs.ftl">
</head>
<body  >
<div id="top">
	<#include "../layout/nologintop.ftl">
</div>
<div   id="main"  class="container">	
<div class="row">
<div class="col-md-2"></div>
<div class="col-md-8">
  <#if 3 == passwordResetResult>
  	<@locale code="forgotpassword.pwdreseted.password"/>
  	<a href="<@base/>/forgotpassword/forward"><@locale code="forgotpassword.backstep"/></a >
    <br>${validate_result}
  	
  </#if>
  <#if 2 == passwordResetResult>
  	<@locale code="forgotpassword.pwdreseted.captcha"/>
  	<a href="javascript:history.go(-1);"><@locale code="forgotpassword.backstep"/></a >
  	
  </#if>
  <#if 1 == passwordResetResult>
  	<@locale code="forgotpassword.pwdreseted.success.tip"/>
  	<a href="<@base/>/login"><@locale code="forgotpassword.pwdreseted.success.login"/></a> .
  	
  </#if>
</div>
<div class="col-md-2"></div>
</div >
</div>
<div id="footer">
	<#include "../layout/footer.ftl">
</div>
</body>
</html>  