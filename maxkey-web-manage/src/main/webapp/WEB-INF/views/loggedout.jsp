<%@page session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" 			uri="http://sso.maxkey.org/tags" %> 

<!DOCTYPE HTML>

<html>
  <head>
  	<jsp:include page="layout/header.jsp"></jsp:include>
	<jsp:include page="layout/common.cssjs.jsp"></jsp:include>
      <title><s:Locale code="global.logout.tip"/></title>
	  <script type="text/javascript">
	  	$(function(){
	  		$.cookie("JSESSIONID","JSESSIONID", { expires: -1 });
	  	});
	  </script>
  </head>
  
<body>
<div id="top">
	<jsp:include page="layout/nologintop.jsp"></jsp:include>
</div>
<div id="content">
	<h2><s:Locale code="global.logout.tip"/></h2>

	<p>
		<spring:message code="global.logout.text.prefix"/>
		<a href="${reloginUrl}">
			<spring:message code="global.logout.text.suffix"/>
		</a>
	</p>
</div>
<div id="footer">
	<jsp:include page="layout/footer.jsp"></jsp:include>
</div>
</body>
</html>
