<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.Map.Entry" %>
<%@ page language="java" import="org.apache.commons.codec.binary.Base64" %>
<%@ page language="java" import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%@ page language="java" import="org.jasig.cas.client.validation.Assertion" %>
<%@ page language="java" import="org.jasig.cas.client.util.AbstractCasFilter" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	System.out.println("CAS Assertion Success . ");
	Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
	                
	                
	String username=     assertion.getPrincipal().getName();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Demo CAS</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="CAS Demo">
	<link rel="shortcut icon" type="image/x-icon" href="<%=basePath %>/images/favicon.ico"/>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		body{
			margin: 0;
			margin-top: 0px;
			margin-left: auto;
			margin-right: auto;
			padding: 0 0 0 0px;
			font-size: 12px;
			text-align:center;
			float:center;
			font-family: "Arial", "Helvetica", "Verdana", "sans-serif";
		}
		.container {
			width: 990px;
			margin-left: auto;
			margin-right: auto;
			padding: 0 10px
		}
		table.datatable {
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			width: 100%;
		}
		
		table.datatable th{
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			height: 40px;
		}
		
		
		table.datatable td{
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			height: 40px;
		}
		
		table.datatable td.title{
			text-align: center;
			font-size: 20px;
			font-weight: bold;
		}
	</style>
  </head>
  
  <body>
  		<div class="container">
	  		<table class="datatable">
	  			<tr>
	  				<td colspan="2" class="title">CAS Demo for MaxKey</td>
	  			</tr>
	  			<tr>
	  				<td>CAS Logo</td>
	  				<td> <img src="<%=basePath %>/images/cas.png"/></td>
	  			</tr>
	  			<tr>
	  				<td width="50%">CAS Assertion</td>
	  				<td><%=username %></td>
	  			</tr>
	  			<tr>
	  				<td>CAS Has Attributes </td>
	  				<td><%=!assertion.getPrincipal().getAttributes().isEmpty() %> size : <%=assertion.getPrincipal().getAttributes().size() %></td>
	  			</tr>
	  			<%
		  			Map<String, Object> attMap = assertion.getPrincipal().getAttributes();  
		            for (Entry<String, Object> entry : attMap.entrySet()) {   
		            	String attributeValue=entry.getValue()==null?"":entry.getValue().toString();
		            	System.out.println("attributeValue : "+attributeValue);
		            	if(attributeValue.startsWith("base64:")){
		            		attributeValue=new String(Base64.decodeBase64(attributeValue.substring("base64:".length())),"UTF-8");
		            	}
		        %>
	  			<tr>
	  				<td>CAS <%=entry.getKey() %> </td>
	  				<td><%=attributeValue %></td>
	  			</tr>
	  			<%}%>
	  		</table>
  		</div>
  </body>
</html>
