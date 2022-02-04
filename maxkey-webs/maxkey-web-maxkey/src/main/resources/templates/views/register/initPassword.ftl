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
  <#if null != model>
	<table border="0"  style="width:100%;">
		<tr>
			<td width="630px">
				
			</td>
			<td>
				<form action="<@base />/signup/activate/${model.id}" method="post">
					<table  class="datatable">
						<tr>
							<td><@locale code="register.lastname"/></td>
							<td>${model.lastName}</td>
						</tr>
						<tr>
							<td><@locale code="register.firstname"/></td>
							<td>${model.firstName}</td>
						</tr>
						<tr>
							<td><@locale code="register.workemail"/></td>
							<td>${model.workEmail}</td>
						</tr>
						<tr>
							<td><@locale code="register.company"/></td>
							<td>${model.company}</td>
						</tr>
						<tr>
							<td><@locale code="register.workphone"/></td>
							<td>${model.workPhone}</td>
						</tr>
						<tr>
							<td><@locale code="register.password"/></td>
							<td><input type='password' id="password" name="password" value="" /></td>
						</tr>
						<tr>
							<td><@locale code="register.confirmpassword"/></td>
							<td><input type='password' id="confirmpassword" name="confirmpassword" value="" /></td>
						</tr>
						<tr style="display:none">
							<td><@locale code="register.users"/></td>
							<td><input type='text' id="users" name="users" value="0" /></td>
						</tr>
						<tr>
							<td  colspan="2"><input id="registerBtn" class="button" type="submit" value="<@locale code="button.text.enable" />"/></td>
						</tr>
						
					</table>
				</form>
			</td>
		</tr>
	</table>
	  </#if>
    <#if null == model>
    	url expired.
    </#if>  
</div>
  </body>
</html>
