<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<script type="text/javascript">
<!--
$(function(){	
	$("#generateSecret").on("click",function(){
		$.post("<s:Base/>/apps/generate/secret/oauth20", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
			$("#secret").val(data+"");
			$("#secret_text").html(data+"");
		}); 
	});
	
	$("#certMetaFileImg").on("click",function(){
		if(!$("#certMetaFileImg").hasClass("appended")){
			$("#certMetaFileImg").after('<input id="certMetaFile" type="file" name="certMetaFile" />');
			$("#certMetaFileImg").addClass("appended");
		}
		
	});
});
//-->
</script>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<s:Base/>/apps/saml20/update"
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
					<td colspan=4><s:Locale code="apps.saml.v2.0.info" /></td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.entityId" />：</th>
					<td  colspan =3>
						<input type="text" id="entityId" name="entityId"  title="" value="${model.entityId}"/>
						<b class="orange">*</b><label for="entityId"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.spAcsUrl" />：</th>
					<td colspan =3>
						<input type="text" id="spAcsUrl" name="spAcsUrl"  title="" value="${model.spAcsUrl}"/>
						<b class="orange">*</b><label for="spAcsUrl"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.issuer" />：</th>
					<td  colspan =3>
						<input type="text" id="issuer" name="issuer"  title="" value="${model.issuer}"/>
						<b class="orange">*</b><label for="issuer"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.audience" />：</th>
					<td  colspan =3>
						<input type="text" id="audience" name="audience"  title="" value="${model.audience}"/>
						<b class="orange">*</b><label for="audience"></label>
					</td>
				</tr>
				<tr>
					<th style="width:15%;"><s:Locale code="apps.saml.nameidFormat" />：</th>
					<td style="width:35%;">
						<select  id="nameidFormat" name="nameidFormat" >
							<option value="persistent"  <c:if test="${'persistent'==model.nameidFormat}">selected</c:if>>persistent</option>
							<option value="transient" <c:if test="${'transient'==model.nameidFormat}">selected</c:if>>transient</option>
							<option value="emailAddress" <c:if test="${'emailAddress'==model.nameidFormat}">selected</c:if>>emailAddress</option>
							<option value="X509SubjectName" <c:if test="${'X509SubjectName'==model.nameidFormat}">selected</c:if>>X509SubjectName</option>
							<option value="WindowsDomainQualifiedName" <c:if test="${'WindowsDomainQualifiedName'==model.nameidFormat}">selected</c:if>>WindowsDomainQualifiedName</option>
							<option value="unspecified"  <c:if test="${'unspecified'==model.nameidFormat}">selected</c:if>>unspecified</option>
							<option value="entity" <c:if test="${'entity'==model.nameidFormat}">selected</c:if>>entity</option>
							<option value="custom" <c:if test="${'custom'==model.nameidFormat}">selected</c:if>>user custom persistent </option>
						</select>
						<b class="orange">*</b><label for="nameidFormat"></label>
					</td>
					<th style="width:15%;"><s:Locale code="apps.saml.nameIdConvert" />：</th>
					<td style="width:35%;">
						<select  id="nameIdConvert" name="nameIdConvert" >
							<option value="0"  <c:if test="${0==model.nameIdConvert}">selected</c:if>>
								<s:Locale code="apps.saml.nameIdConvert.original" /></option>
							<option value="1"  <c:if test="${1==model.nameIdConvert}">selected</c:if>>
								<s:Locale code="apps.saml.nameIdConvert.upperCase" /></option>
							<option value="2"  <c:if test="${2==model.nameIdConvert}">selected</c:if>>
								<s:Locale code="apps.saml.nameIdConvert.lowerCase" /></option>
						</select>
						<b class="orange">*</b><label for="nameIdConvert"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.binding" />：</th>
					<td>
						<select  id="binding" name="binding" >
							<option value="Redirect-Post"  <c:if test="${'Redirect-Post'==model.binding}">selected</c:if>>Redirect-Post</option>
							<option value="Post-Post" <c:if test="${'Post-Post'==model.binding}">selected</c:if>>Post-Post</option>
							<option value="IdpInit-Post" <c:if test="${'IdpInit-Post'==model.binding}">selected</c:if>>IdpInit-Post</option>
							<option value="Redirect-PostSimpleSign" <c:if test="${'Redirect-PostSimpleSign'==model.binding}">selected</c:if>>Redirect-PostSimpleSign</option>
							<option value="Post-PostSimpleSign" <c:if test="${'Post-PostSimpleSign'==model.binding}">selected</c:if>>Post-PostSimpleSign</option>
							<option value="IdpInit-PostSimpleSign"  <c:if test="${'IdpInit-PostSimpleSign'==model.binding}">selected</c:if>>IdpInit-PostSimpleSign</option>
						</select>
						<b class="orange">*</b><label for="binding"></label>
					</td>
					
					<th><s:Locale code="apps.saml.validityInterval" />：</th>
					<td >
						<input type="text" id="validityInterval" name="validityInterval"  title="" value="${model.validityInterval}"/>
					</td>
					
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.fileType" />：</th>
					<td>
						<select  id="fileType" name="fileType" >
							<option value="certificate" selected><s:Locale code="apps.saml.fileType.certificate" /></option>
							<option value="metadata"><s:Locale code="apps.saml.fileType.metadata" /></option>
						</select>
						<b class="orange">*</b><label for="fileType"></label>
					</td>
					<th><s:Locale code="apps.saml.certMetaFile" />：</th>
					<td>
						<img id="certMetaFileImg"  height="32" alt="upload certificate or metadata file" src="<s:Base/>/images/cert.png">
						<b class="orange">*</b><label for="certMetaFile"></label>
					</td>
				</tr>
				
				<tr>
					<th><s:Locale code="apps.saml.encrypted" />：</th>
					<td >
						<select  id="encrypted" name="encrypted" >
							<option value="0"   <c:if test="${0==model.encrypted}">selected</c:if>>
								<s:Locale code="apps.saml.encrypted.no" /></option>
							<option value="1"  <c:if test="${1==model.encrypted}">selected</c:if>>
								<s:Locale code="apps.saml.encrypted.yes" /></option>
						</select>
					</td>
					<th><s:Locale code="apps.isAdapter" />：</th>
					<td >
						<select  id="isAdapter" name="isAdapter" >
							<option value="0"  <c:if test="${0==model.isAdapter}">selected</c:if> ><s:Locale code="apps.isAdapter.no" /></option>
							<option value="1"  <c:if test="${1==model.isAdapter}">selected</c:if> ><s:Locale code="apps.isAdapter.yes" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.adapter" />：</th>
					<td colspan =3>
						<input type="text" id="adapter" name="adapter"  title="" value="${model.adapter}"/>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.certIssuer" />：</th>
					<td>${model.certIssuer}
					</td>
					<th><s:Locale code="apps.saml.certExpiration" />：</th>
					<td>${model.certExpiration}
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.certSubject" />：</th>
					<td  colspan =3>${model.certSubject}
					</td>
				</tr>
				</tbody>
			  </table>
  	       </td>
				</tr>
				</tbody>
				</table>
				
    		<input class="button" id="submitBtn" type="submit" value="<s:Locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<s:Locale code="button.text.cancel" />"/>	  
</form>