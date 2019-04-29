<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<s:Base/>/app/accounts/update">
	<table border="0" cellpadding="0" cellspacing="0" class="datatable">
		<tbody>
			<tr style="display:none">
				<th><s:Locale code="userinfo.id" />：</th>
				<td nowrap>
					<span class="intspan"><input type="text" id="id" name="id" readonly class="int required" title="" value="${model.id}"/></span>
					<b class="orange">*</b><label for="userTypeId"></label>
				</td>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.username" />：</th>
				<td nowrap>
					<span class="intspan"><input readonly type="text" id="username" name="username" class="int required" title="" value="${model.username}"/></span>
					<b class="orange">*</b><label for="username"></label>
				</td>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.displayName" />：</th>
				<td nowrap>
					<span class="intspan"><input readonly  type="text" id="displayName" name="displayName" class="int required" title="" value="${model.displayName}"/></span>
					<b class="orange">*</b><label for="displayName"></label>
				</td>
			</tr>
			<tr>
				<th><s:Locale code="apps.name" />：</th>
				<td nowrap>
					<span class="intspan"><input readonly  type="text" id="appName" name="appName" class="int required" title="" value="${model.appName}"/></span>
					<b class="orange">*</b><label for="appName"></label>
				</td>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.appaccouts.relatedUsername" />：</th>
				<td nowrap>
					<span class="intspan"><input type="text" id="relatedUsername" name="relatedUsername" class="int required" title="" value="${model.relatedUsername}"/></span>
					<b class="orange">*</b><label for="relatedUsername"></label>
				</td>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.appaccouts.relatedPassword" />：</th>
				<td nowrap>
					<span class="intspan"><input type="password" id="relatedPassword" name="relatedPassword" class="int required" title="" value="${model.relatedPassword}"/></span>
					<b class="orange">*</b><label for="relatedPassword"></label>
				</td>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input id="status" type="hidden" name="status"  value="1"/>
					<input type="hidden" id="uid" name="uid" class="int required" title="" value="${model.uid}"/>
					<input type="hidden" id="appId" name="appId" class="int required" title="" value="${model.appId}"/>
			   		<input class="button"  type="button"    id="submitBtn" value="<s:Locale code="button.text.save" />"/>
					<input class="button"  type="button"    id="closeBtn" value="<s:Locale code="button.text.cancel" /> "/>	
					
				</td>
			</tr>
		</tbody>
	</table>
</form>