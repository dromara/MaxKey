<!DOCTYPE HTML >
<html>
  <head>
  		<#include  "layout/header.ftl"/>
		<#include  "layout/common.cssjs.ftl"/>
      	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
      	<title><@locale code="login.session.timeout.tip"/></title>
  </head>
<body>
<div id="top">
	<#include "layout/nologintop.ftl"/>
</div>
<div id="content">
	<h2><@locale code="login.session.timeout.tip"/></h2>

	<p>
		<@locale code="login.session.timeout.prefix"/>
		<a href="${reloginUrl}">
			<@locale code="login.session.timeout.suffix"/>
		</a>.
	</p>
</div>
<div id="footer">
	<#include "layout/footer.ftl"/>
</div>
</body>
</html>
