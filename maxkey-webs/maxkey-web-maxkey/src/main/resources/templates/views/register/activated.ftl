<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<@base/>">
    
    <title>My JSP 'activated.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
<div  id="main"  class="container">
 	 <#if 0 == activate>
		url expired.
	  </#if>
    <#if 1 == activate>
    	activate success,
    	<a href='<@base url="/"/>'>
			<@locale code="login.button.login"/>
		</a>
    </#if>  
</div>
  </body>
</html>
