<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <base href="<@base/>">
    
    <title>My JSP 'register.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">


  </head>
  
  <body>
<div class="container">	
	<table border="0"  style="width:100%;">
		<tr>
			<td width="630px">
				
			</td>
			<td>
				<form action="<@base />/registration/register" method="post">
					<table  class="datatable">
						<tr>
							<td><@locale code="register.lastname"/></td>
							<td><input type='text' id="lastName" name="lastName" value="" /></td>
						</tr>
						<tr>
							<td><@locale code="register.firstname"/></td>
							<td><input type='text' id="firstName" name="firstName" value="" /></td>
						</tr>
						<tr>
							<td><@locale code="register.workemail"/></td>
							<td><input type='text' id="workEmail" name="workEmail" value="" /></td>
						</tr>
						<tr>
							<td><@locale code="register.company"/></td>
							<td><input type='text' id="company" name="company" value="" /></td>
						</tr>
						<tr>
							<td><@locale code="register.workphone"/></td>
							<td><input type='text' id="workPhone" name="workPhone" value="" /></td>
						</tr>
						
						<tr style="display:none">
							<td><@locale code="register.users"/></td>
							<td><input type='text' id="users" name="users" value="0" /></td>
						</tr>
						<tr>
							<td  colspan="2"><input id="registerBtn" class="button" type="submit" value="<@locale code="register.button.register" />"/></td>
						</tr>
						
					</table>
				</form>
			</td>
		</tr>
	</table>
</div>
  </body>
</html>
