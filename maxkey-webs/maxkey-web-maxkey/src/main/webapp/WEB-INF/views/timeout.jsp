<%@page session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" 		uri="http://www.connsec.com/tags" %> 
<!DOCTYPE HTML >
<html>
  <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8">
      <title><s:Locale code="global.session.timeout.tip"/></title>
  </head>
<body>

<div id="content">
	<h2><s:Locale code="global.session.timeout.tip"/></h2>

	<p>
		<s:Locale code="global.session.timeout.prefix"/>
		<a href="${reloginUrl}">
			<s:Locale code="global.session.timeout.suffix"/>
		</a>.
	</p>
</div>
</body>
</html>
