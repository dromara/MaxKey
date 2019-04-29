<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" 	uri="http://www.connsec.com/tags" %>


<script type="text/javascript">


</script>
	 
	     <!-- content -->  
	<form id="actionForm" name="credentialsubmit" action="<s:Base/>/appUserConfig" method="post">
		<input type="hidden" id="protocol" name="protocol" value="${protocol}" />
		<input type="hidden" id="credential" name="credential" value="${credential}" />
		<table width="420px">
			
			<tr <c:if test="${false==username}">style="display:none"</c:if>>
				<td><s:Locale code="userinfo.appaccouts.relatedUsername" /></td>
				<td><input type="text" id="identity_username" name="identity_username" value="${identity_username}" /></td>
			</tr>
			<tr <c:if test="${false==password}"> style="display:none"</c:if> >
				<td><s:Locale code="userinfo.appaccouts.relatedPassword" /></td>
				<td><input type="password" id="identity_password" name="identity_password" value="${identity_password}" /></td>
			</tr>
			
			<tr style="display:none">
				<td>uid</td>
				<td><input type="text" id="uid" name="uid" value="${uid}" /></td>
			</tr>
			<tr style="display:none">
				<td>appId</td>
				<td><input type="text" id="appId" name="appId" value="${appId}" /></td>
			</tr>
			<tr>
				<td colspan="2"><input class="button" type="submit" style="width: 400px" id="credentialsubmitbutton"value="<s:Locale code="button.text.save" />"/></td>
			</tr>
		</table>
	</form> 
