<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>
<script type="text/javascript">
<!--
$(function(){	
	$("#generateSecret").on("click",function(){
		$.post("<@base/>/apps/generate/secret/oauth20", {_method:"post",currTime:(new Date()).getTime()}, function(data) {
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
</head>
<body>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<@base/>/apps/saml20/update"
			forward="<@base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			<table width="960"  class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appUpdateCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
			   <table width="960"  class="table table-bordered" >
				<tbody>
				
				<tr>
					<td colspan=4><@locale code="apps.saml.v2.0.info" /></td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.entityId" />：</th>
					<td  colspan =3>
						<input type="text" class="form-control"  id="entityId" name="entityId"  title="" value="${model.entityId}"/>
					</td>
					
					
				</tr>
				<tr>
					<th><@locale code="apps.saml.spAcsUrl" />：</th>
					<td colspan =3>
						<input type="text" class="form-control"  id="spAcsUrl" name="spAcsUrl"  title="" value="${model.spAcsUrl}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.issuer" />：</th>
					<td  colspan =3>
						<input type="text" class="form-control"  id="issuer" name="issuer"  title="" value="${model.issuer}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.audience" />：</th>
					<td  colspan =2>
						<input type="text" class="form-control"  id="audience" name="audience"  title="" value="${model.audience}"/>
					</td>
					<td  >
						<a target="_blank" href="${maxKeyURI}/metadata/saml20/${model.id}.xml"> SAML MetaData</a>
					</td>
				</tr>
				<tr>
					<th style="width:15%;"><@locale code="apps.saml.nameidFormat" />：</th>
					<td style="width:35%;">
						<select  id="nameidFormat" name="nameidFormat"  class="form-control" >
							<option value="persistent"  <#if 'persistent'==model.nameidFormat>selected</#if>>persistent</option>
							<option value="transient" <#if 'transient'==model.nameidFormat>selected</#if>>transient</option>
							<option value="emailAddress" <#if 'emailAddress'==model.nameidFormat>selected</#if>>emailAddress</option>
							<option value="X509SubjectName" <#if 'X509SubjectName'==model.nameidFormat>selected</#if>>X509SubjectName</option>
							<option value="WindowsDomainQualifiedName" <#if 'WindowsDomainQualifiedName'==model.nameidFormat>selected</#if>>WindowsDomainQualifiedName</option>
							<option value="unspecified"  <#if 'unspecified'==model.nameidFormat>selected</#if>>unspecified</option>
							<option value="entity" <#if 'entity'==model.nameidFormat>selected</#if>>entity</option>
							<option value="custom" <#if 'custom'==model.nameidFormat>selected</#if>>user custom persistent </option>
						</select>
					</td>
					<th style="width:15%;"><@locale code="apps.saml.nameIdConvert" />：</th>
					<td style="width:35%;">
						<select  id="nameIdConvert" name="nameIdConvert"  class="form-control" >
							<option value="0"  <#if 0==model.nameIdConvert>selected</#if>>
								<@locale code="apps.saml.nameIdConvert.original" /></option>
							<option value="1"  <#if 1==model.nameIdConvert>selected</#if>>
								<@locale code="apps.saml.nameIdConvert.upperCase" /></option>
							<option value="2"  <#if 2==model.nameIdConvert>selected</#if>>
								<@locale code="apps.saml.nameIdConvert.lowerCase" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.binding" />：</th>
					<td>
						<select  id="binding" name="binding"  class="form-control" >
							<option value="Redirect-Post"  <#if 'Redirect-Post'==model.binding>selected</#if>>Redirect-Post</option>
							<option value="Post-Post" <#if 'Post-Post'==model.binding>selected</#if>>Post-Post</option>
							<option value="IdpInit-Post" <#if 'IdpInit-Post'==model.binding>selected</#if>>IdpInit-Post</option>
							<option value="Redirect-PostSimpleSign" <#if 'Redirect-PostSimpleSign'==model.binding>selected</#if>>Redirect-PostSimpleSign</option>
							<option value="Post-PostSimpleSign" <#if 'Post-PostSimpleSign'==model.binding>selected</#if>>Post-PostSimpleSign</option>
							<option value="IdpInit-PostSimpleSign"  <#if 'IdpInit-PostSimpleSign'==model.binding>selected</#if>>IdpInit-PostSimpleSign</option>
						</select>
					</td>
					
					<th><@locale code="apps.saml.validityInterval" />：</th>
					<td >
						<input type="text" class="form-control"  id="validityInterval" name="validityInterval"  title="" value="${model.validityInterval}"/>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.saml.fileType" />：</th>
					<td>
						<select  id="fileType" name="fileType"  class="form-control" >
							<option value="certificate" selected><@locale code="apps.saml.fileType.certificate" /></option>
							<option value="metadata"><@locale code="apps.saml.fileType.metadata" /></option>
						</select>
					</td>
					<th><@locale code="apps.saml.certMetaFile" />：</th>
					<td>
						<img id="certMetaFileImg"  height="32" alt="upload certificate or metadata file" src="<s:Base/>/images/cert.png">
						<b class="orange">*</b><label for="certMetaFile"></label>
					</td>
				</tr>
				
				<tr>
					<th><@locale code="apps.saml.encrypted" />：</th>
					<td >
						<select  id="encrypted" name="encrypted"  class="form-control" >
							<option value="0"   <#if 0==model.encrypted>selected</#if>>
								<@locale code="apps.saml.encrypted.no" /></option>
							<option value="1"  <#if 1==model.encrypted>selected</#if>>
								<@locale code="apps.saml.encrypted.yes" /></option>
						</select>
					</td>
					<th><@locale code="apps.isAdapter" />：</th>
					<td >
						<select  id="isAdapter" name="isAdapter"  class="form-control" >
							<option value="0"  <#if 0==model.isAdapter>selected</#if> ><@locale code="apps.isAdapter.no" /></option>
							<option value="1"  <#if 1==model.isAdapter>selected</#if> ><@locale code="apps.isAdapter.yes" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.adapter" />：</th>
					<td colspan =3>
						<input type="text" class="form-control"  id="adapter" name="adapter"  title="" value="${model.adapter}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.certIssuer" />：</th>
					<td>${model.certIssuer}
					</td>
					<th><@locale code="apps.saml.certExpiration" />：</th>
					<td>${model.certExpiration}
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.certSubject" />：</th>
					<td  colspan =3>${model.certSubject}
					</td>
				</tr>
				<tr>
					<td colspan =4>
						<input class="button"  id="status" type="hidden" name="status"  value="1"/>
			    		<input class="button btn btn-primary mr-3"  id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
						<input class="button btn btn-secondary mr-3"  id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>	
					</td>
				</tr>
				</tbody>
			  </table>
  	       </td>
				</tr>
				</tbody>
				</table>  
</form>
</body>
</html>