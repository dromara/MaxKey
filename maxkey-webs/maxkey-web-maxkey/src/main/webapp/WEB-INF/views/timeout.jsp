<%@page session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" 		uri="http://sso.maxkey.org/tags" %> 
<!DOCTYPE HTML >
<html>
  <head>
  		<jsp:include page="layout/header.jsp"></jsp:include>
		<jsp:include page="layout/common.css.jsp"></jsp:include>
		<jsp:include page="layout/common.js.jsp"></jsp:include>
      	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
      	<title><s:Locale code="login.session.timeout.tip"/></title>
  </head>
<body>
<div id="top">
	<jsp:include page="layout/nologintop.jsp"></jsp:include>
</div>
<div id="content">
	<h2><s:Locale code="login.session.timeout.tip"/></h2>

	<p>
		<s:Locale code="login.session.timeout.prefix"/>
		<a href="${reloginUrl}">
			<s:Locale code="login.session.timeout.suffix"/>
		</a>.
	</p>
</div>
<div id="footer">
	<jsp:include page="layout/footer.jsp"></jsp:include>
</div>
</body>
</html>
