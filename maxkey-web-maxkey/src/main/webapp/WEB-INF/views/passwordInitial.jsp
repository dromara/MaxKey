<%@page session="false" %>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" 		uri="http://sso.maxkey.org/tags" %> 
<!DOCTYPE HTML >
<html>
	<head>
		<jsp:include page="layout/header.jsp"></jsp:include>
		<jsp:include page="../layout/common.cssjs.jsp"></jsp:include>
      	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
      	<title><s:Locale code="login.password.initial.change.tip" /></title>
  </head>
<body>
<div id="top">
	<jsp:include page="layout/nologintop.jsp"></jsp:include>
</div>
<div id="content">
<div class="container">
<table width="100%">
  <tr>
    <td>

<form  method="post" type="label" autoclose="true"  action="<s:Base/>/safe/changeInitPassword"> 

	  <table   class="datatable" >
			<tbody>
			<tr>
				<th  colspan="2"><s:Locale code="login.password.initial.change.tip" /></th>
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
				<th><s:Locale code="login.password.newPassword" />:</th>
				<td>
					<input type="password" id="newPassword" name="newPassword" class=" required" title="" value=""/>
					<b class="orange">*</b>
					<label for="newPassword"></label>
				</td>
			</tr>
			<tr>
				<th><s:Locale code="login.password.confirmPassword" />:</th>
				<td nowrap>
					<input type="password" id="confirmPassword" name="confirmPassword" class="{ required: true, equalTo: '#newPassword' }" title="" value=""/>
					<b class="orange">*</b>
					<label for="confirmPassword"></label>
				</td>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button" style="width:100px"  type="submit"    id="submitBtn" value="<s:Locale code="button.text.save" />"/>
					
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</td>
  </tr>
</table>
</div>
</div>
<div id="footer">
	<jsp:include page="layout/footer.jsp"></jsp:include>
</div>
</body>
</html>
