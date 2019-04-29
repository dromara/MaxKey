<%@page session="false" %>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" 		uri="http://www.connsec.com/tags" %> 
<!DOCTYPE HTML >
<html>
  <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8">
      <title><s:Locale code="login.password.expired" /></title>
  </head>
<body>
<div class="container">
<table width="100%">
  <tr>
    <td>
	<div>
<form  method="post" type="label" autoclose="true"  action="<s:Base/>/safe/changeExpiredPassword"> 
	
	  <table   class="datatable" >
			<tbody>
			<tr>
				<th  colspan="2"><s:Locale code="login.password.expired.tip" /></th>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.displayName" /> :</th>
				<td>
					<input readonly type="text" id="displayName" name="displayName" class="required" title="" value="${model.displayName}"/>
					
				</td>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.username" /> :</th>
				<td>
					<input readonly type="text" id="username" name="username" class="required" title="" value="${model.username}"/>
					
				</td>
			</tr>
			<tr>
				<th><s:Locale code="access.security.newPassword" />:</th>
				<td>
					<input type="password" id="newPassword" name="newPassword" class=" required" title="" value=""/>
					<b class="orange">*</b>
					<label for="newPassword"></label>
				</td>
			</tr>
			<tr>
				<th><s:Locale code="access.security.confirmPassword" />:</th>
				<td nowrap>
					<input type="password" id="confirmPassword" name="confirmPassword" class="{ required: true, equalTo: '#newPassword' }" title="" value=""/>
					<b class="orange">*</b>
					<label for="confirmPassword"></label>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button" style="width:100px"  type="submit"    id="submitBtn" value="<s:Locale code="button.text.save" />"/>
					
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</div>
</td>
  </tr>
</table>
</div>
</body>
</html>
