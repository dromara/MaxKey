<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="c"        uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib  prefix="fn" 	  uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
<!--
$(function(){	
	$("#generateSecret").on("click",function(){
		$.post("<s:Base/>/apps/generate/secret/oauth20", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#clientSecret").val(data+"");
			$("#clientSecret_text").html(data+"");
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		}); 
	});
});
//-->
</script>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<s:Base/>/apps/oauth10a/update" 
			forward="<s:Base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			<table width="960"  class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appUpdateCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
			   <table width="960"  class="datatable" >
				<tbody>
				
				<tr>
					<td colspan=4><s:Locale code="apps.oauth.v1.0a.info" /></td>
				</tr>
				<tr>
					<th style="width:15%;"><s:Locale code="apps.oauth.1.0a.clientId" />：</th>
					<td style="width:35%;">${model.clientId}
						<input type="hidden" id="clientId" name="clientId"  title="" value="${model.clientId}"/>
					
					</td>
					<th style="width:15%;"><s:Locale code="apps.oauth.1.0a.clientSecret" />：</th>
					<td style="width:35%;">
						<span id="clientSecret_text">${model.clientSecret}</span>
						<input type="hidden" id="clientSecret" name="clientSecret"  title="" value="${model.clientSecret}"/>
						
					</td>
				</tr>
				<tr style="display:none">
					<th>scope：</th>
					<td>
						read<input type="checkbox" id="scope_trust" name="scope" value="read"  <c:if test="${fn:contains(model.scope, 'read')}">checked</c:if> />
						write<input type="checkbox" id="scope_write" name="scope" value="write" <c:if test="${fn:contains(model.scope, 'write')}">checked</c:if>/>
						trust<input type="checkbox" id="scope_trust" name="scope" value="trust" <c:if test="${fn:contains(model.scope, 'trust')}">checked</c:if>/>
						all<input type="checkbox" id="scope_all" name="scope" value="all" <c:if test="${fn:contains(model.scope, 'all')}">checked</c:if>/>
						<b class="orange">*</b><label for="algorithm"></label>
					</td>
					<th>GrantTypes：</th>
					<td>
						authorization_code<input <c:if test="${fn:contains(model.authorizedGrantTypes, 'authorization_code')}">checked</c:if>  type="checkbox" id="grantTypes_authorization_code" name="authorizedGrantTypes" value="authorization_code"/>
						<b class="orange">*</b><label for="authorizedGrantTypes"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.oauth.registeredRedirectUris" />：</th>
					<td colspan=3>
						<textarea  id="registeredRedirectUris" name="registeredRedirectUris" rows="4" cols="60">${model.registeredRedirectUris}</textarea>
						<b class="orange">*</b><label for="registeredRedirectUris"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.oauth.approvalPrompt" />：</th>
					<td >
						<select  id="approvalPrompt" name="approvalPrompt" >
							<option value="force"  <c:if test="${null==model.approvalPrompt}">selected</c:if>>
								<s:Locale code="apps.oauth.approvalPrompt.force" /></option>
							<option value="auto"  <c:if test="${'auto'==model.approvalPrompt}">selected</c:if>>
								<s:Locale code="apps.oauth.approvalPrompt.auto" /></option>
						</select>
					</td>
					<th><s:Locale code="apps.isAdapter" />：</th>
					<td >
						<select  id="isAdapter" name="isAdapter" >
							<option value="0"  <c:if test="${0==model.isAdapter}">selected</c:if> >
								<s:Locale code="apps.isAdapter.no" /></option>
							<option value="1"  <c:if test="${1==model.isAdapter}">selected</c:if> >
								<s:Locale code="apps.isAdapter.yes" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.adapter" />：</th>
					<td colspan =3>
						<input type="text" id="adapter" name="adapter"  title="" value="${model.adapter}"/>
					</td>
				</tr>
				</tbody>
			  </table>
  	       </td>
				</tr>
				</tbody>
				</table>
    		<input class="button" id="submitBtn" type="submit" value="<s:Locale code="button.text.save" />"/>
			<input class="button" id="backBtn"   type="button" value="<s:Locale code="button.text.cancel" />"/>	  
</form>