<!DOCTYPE HTML>
<html>
  <head>
  	<#include  "layout/header.ftl"/>
	<#include  "layout/common.cssjs.ftl"/>
      <title><@locale code="global.logout.tip"/></title>
	  <script type="text/javascript">
	  	$(function(){
	  		$.cookie("JSESSIONID","JSESSIONID", { expires: -1 });
	  	});
	  </script>
  </head>
  
<body>
<div id="top">
	<#include "layout/nologintop.ftl"/>
</div>
<div id="content">
	<h2><@locale code="global.logout.tip"/></h2>

	<p>
		<@locale code="global.logout.text.prefix"/>
		<a href="${reloginUrl}">
			<@locale code="global.logout.text.suffix"/>
		</a>
	</p>
</div>
<div id="footer">
	<#include "layout/footer.ftl"/>
</div>
</body>
</html>
