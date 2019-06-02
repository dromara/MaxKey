<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="c"		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" 	uri="http://sso.maxkey.org/tags" %>


<script type="text/javascript">


</script>
	 
	     <!-- content -->  
	<form id="actionForm" name="credentialsubmit" action="<s:Base/>/appProtectedConfig" method="post" type="type">
		<input type="hidden" id="protocol" name="protocol" value="${protocol}" />
		<input type="hidden" id="credential" name="credential" value="${credential}" />
		<table width="420px">
			<tr style="display:none">
				<td>uid</td>
				<td><input type="text" id="uid" name="uid" value="${uid}" /></td>
			</tr>
			<tr style="display:none">
				<td>appId</td>
				<td><input type="text" id="appId" name="appId" value="${appId}" /></td>
			</tr>
			<tr>
				<td  width="120px"><s:Locale code="access.security.applogin.protection" /> </td>
				<td><select  id="protectedappId" name="protectedappId" >
						<option value="YES" <c:if test="${true==protectedappId}">selected</c:if> ><s:Locale code="common.text.status.3" /></option>
						<option value="NO"  <c:if test="${false==protectedappId}">selected</c:if>><s:Locale code="common.text.status.4" /></option>
					</select></td>
			</tr>
			<tr >
				<td><s:Locale code="userinfo.password" /></td>
				<td><input type="password" id="password" name="password"  /></td>
			</tr>
			<tr>
				<td colspan="2"><input class="button" type="submit" style="width: 400px" id="credentialsubmitbutton"value="<s:Locale code="button.text.save" />"/></td>
			</tr>
		</table>
	</form>
