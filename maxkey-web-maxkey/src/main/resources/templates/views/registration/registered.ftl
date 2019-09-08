<!DOCTYPE HTML >
<html>
  <head>
     <base href="<@base/>">
    
    <title>Registered</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="Registered">

  </head>
  
  <body>
  <#if 2==registered>
  		company ${company} registered,please change company name.<br>
  		<input type="button"  class="button"  value="后退"  onclick="javascript:history.go(-1);">  
  </#if>
  
  <#if 1==registered>
  		please activate by you email .
  </#if>
  
  <#if 0==registered>
  		please activate by you email .
  </#if>
  </body>
</html>
