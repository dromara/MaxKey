<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
	<script type="text/javascript">
	<!--
	$(function(){	
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
<form id="actionForm_app"  method="post" type="label" autoclose="true"   closeWindow="true" 
			action="<@base/>/apps/saml20/update"
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
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
                    <th><@locale code="apps.saml.spAcsUrl" />：</th>
                    <td colspan =3>
                        <input type="text" class="form-control"  id="spAcsUrl" name="spAcsUrl"  title="" value="${model.spAcsUrl!}"  required="" />
                    </td>
                </tr>
				<tr>
					<th><@locale code="apps.saml.entityId" />：</th>
					<td >
						<input type="text" class="form-control"  id="entityId" name="entityId"  title="" value="${model.entityId!}"  required="" />
					</td>
					<td></td>
                    <td  >
                        <a target="_blank" href="${maxKeyURI}/metadata/saml20/Idp_Metadata_${model.id}.xml"> SAML MetaData</a>
                    </td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.issuer" />：</th>
					<td >
						<input type="text" class="form-control"  id="issuer" name="issuer"  title="" value="${model.issuer!}"  required="" />
					</td>
					<th><@locale code="apps.saml.audience" />：</th>
                    <td  colspan =2>
                        <input type="text" class="form-control"  id="audience" name="audience"  title="" value="${model.audience!}"  required="" />
                    </td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.signature" />：</th>
					<td>
						<select  id="signature" name="signature"  class="form-control" >
                            <option value="RSAwithSHA1"  <#if 'RSAwithSHA1'==model.signature>selected</#if>>RSAwithSHA1</option>
                            <option value="RSAwithSHA256" <#if 'RSAwithSHA256'==model.signature>selected</#if>>RSAwithSHA256</option>
                            <option value="RSAwithSHA384" <#if 'RSAwithSHA384'==model.signature>selected</#if>>RSAwithSHA384</option>
                            <option value="RSAwithSHA512" <#if 'RSAwithSHA512'==model.signature>selected</#if>>RSAwithSHA512</option>
                            <option value="RSAwithMD5" <#if 'RSAwithMD5'==model.signature>selected</#if>>RSAwithMD5</option>
                            <option value="RSAwithRIPEMD160" <#if 'RSAwithRIPEMD160'==model.signature>selected</#if>>RSAwithRIPEMD160</option>
                            <option value="DSAwithSHA1" <#if 'DSAwithSHA1'==model.signature>selected</#if>>DSAwithSHA1</option>
                            <option value="ECDSAwithSHA1" <#if 'ECDSAwithSHA1'==model.signature>selected</#if>>ECDSAwithSHA1</option>
                            <option value="ECDSAwithSHA256"  <#if 'ECDSAwithSHA256'==model.signature>selected</#if>>ECDSAwithSHA256</option>
                            <option value="ECDSAwithSHA384" <#if 'ECDSAwithSHA384'==model.signature>selected</#if>>ECDSAwithSHA384</option>
                            <option value="ECDSAwithSHA512" <#if 'ECDSAwithSHA512'==model.signature>selected</#if>>ECDSAwithSHA512</option>
                            <option value="HMAC-MD5"  <#if 'HMAC-MD5'==model.signature>selected</#if>>HMAC-MD5</option>
                            <option value="HMAC-SHA1"  <#if 'HMAC-SHA1'==model.signature>selected</#if>>HMAC-SHA1</option>
                            <option value="HMAC-SHA256"  <#if 'HMAC-SHA256'==model.signature>selected</#if>>HMAC-SHA256</option>
                            <option value="HMAC-SHA384"  <#if 'HMAC-SHA384'==model.signature>selected</#if>>HMAC-SHA384</option>
                            <option value="HMAC-SHA512"  <#if 'HMAC-SHA512'==model.signature>selected</#if>>HMAC-SHA512</option>
                            <option value="HMAC-RIPEMD160"  <#if 'HMAC-RIPEMD160'==model.signature>selected</#if>>HMAC-RIPEMD160</option>
                          </select>
					</td>
					<th><@locale code="apps.saml.digestMethod" />：</th>
                    <td>
                        <select  id="digestMethod" name="digestMethod"  class="form-control" >
                            <option value="MD5"         <#if 'MD5'==model.digestMethod>selected</#if>>MD5</option>
                            <option value="SHA1"        <#if 'SHA1'==model.digestMethod>selected</#if>>SHA1</option>
                            <option value="SHA256"      <#if 'SHA256'==model.digestMethod>selected</#if>>SHA256</option>
                            <option value="SHA384"      <#if 'SHA384'==model.digestMethod>selected</#if>>SHA384</option>
                            <option value="SHA512"      <#if 'SHA512'==model.digestMethod>selected</#if>>SHA512</option>
                            <option value="RIPEMD-160"  <#if 'RIPEMD-160'==model.digestMethod>selected</#if>>RIPEMD-160</option>
                            
                        </select>
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
						<img id="certMetaFileImg"  height="40" width="80" alt="upload certificate or metadata file" src="<@base />/static/images/cert.png">
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
					<th></th>
					<td >
						
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.certIssuer" />：</th>
					<td>${model.certIssuer!}
					</td>
					<th><@locale code="apps.saml.certExpiration" />：</th>
					<td>${model.certExpiration!}
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.certSubject" />：</th>
					<td  colspan =3>${model.certSubject!}
					</td>
				</tr>
				<tr>
					<td colspan =4>
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