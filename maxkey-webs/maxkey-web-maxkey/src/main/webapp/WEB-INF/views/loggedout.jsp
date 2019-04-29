<%@page session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" 			uri="http://www.connsec.com/tags" %> 

<!DOCTYPE HTML>

<html>
  <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8">
      <title><s:Locale code="global.logout.tip"/></title>
	  <script type="text/javascript">
	  	$(function(){
	  		$.cookie("JSESSIONID","JSESSIONID", { expires: -1 });
	  	});
	  </script>
  </head>
  
<body>
<div id="content">
	<h2><s:Locale code="global.logout.tip"/></h2>

	<p>
		<s:Locale code="global.logout.text.prefix"/>
		<a href="<c:url value='/'/>">
			<s:Locale code="global.logout.text.suffix"/>
		</a>.
	</p>
</div>
</body>
</html>
