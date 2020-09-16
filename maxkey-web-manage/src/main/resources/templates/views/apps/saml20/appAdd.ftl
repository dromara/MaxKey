<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../../layout/header.ftl"/>
	<#include  "../../layout/common.cssjs.ftl"/>
	<#include  "../appCommonHead.ftl"/>
</head>
<body>
<form id="actionForm_app"  method="post" type="label" autoclose="true"   closeWindow="true" 
			action="<@base/>/apps/saml20/add"
			forward="<@base/>/apps/list"
			enctype="multipart/form-data"
			class="needs-validation" novalidate>		 
  	        <!-- content -->    
  	      	<!--table-->
			<table   class="table table-bordered" >
				<tbody>
				<tr>
					<td ><#include  "../appAddCommon.ftl"/></td>
				</tr>
				<tr>
					<td>
			   <table   class="table table-bordered" >
				<tbody>
				
				<tr>
					<td colspan=4><@locale code="apps.saml.v2.0.info" /></td>
				</tr>
				<tr>
                    <th><@locale code="apps.saml.spAcsUrl" />：</th>
                    <td colspan =3>
                        <input type="text" class="form-control"   id="spAcsUrl" name="spAcsUrl"  title="" value=""  required="" />
                    </td>
                </tr>
				<tr>
					<th><@locale code="apps.saml.entityId" />：</th>
					<td colspan =3>
						<input type="text" class="form-control"   id="entityId" name="entityId"  title="" value=""  required="" />
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.issuer" />：</th>
					<td>
						<input type="text" class="form-control"   id="issuer" name="issuer"  title="" value=""  required="" />
					</td>
					<th><@locale code="apps.saml.audience" />：</th>
                    <td >
                        <input type="text" class="form-control"   id="audience" name="audience"  title="" value="" required="" />
                    </td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.signature" />：</th>
					<td>
						  <select  id="signature" name="signature"  class="form-control" >
                            <option value="RSAwithSHA1"  selected>RSAwithSHA1</option>
                            <option value="RSAwithSHA256" >RSAwithSHA256</option>
                            <option value="RSAwithSHA384" >RSAwithSHA384</option>
                            <option value="RSAwithSHA512" >RSAwithSHA512</option>
                            <option value="RSAwithMD5" >RSAwithMD5</option>
                            <option value="RSAwithRIPEMD160" >RSAwithRIPEMD160</option>
                            <option value="DSAwithSHA1" >DSAwithSHA1</option>
                            <option value="ECDSAwithSHA1" >ECDSAwithSHA1</option>
                            <option value="ECDSAwithSHA256"  >ECDSAwithSHA256</option>
                            <option value="ECDSAwithSHA384" >ECDSAwithSHA384</option>
                            <option value="ECDSAwithSHA512" >ECDSAwithSHA512</option>
                            <option value="HMAC-MD5"  >HMAC-MD5</option>
                            <option value="HMAC-SHA1"  >HMAC-SHA1</option>
                            <option value="HMAC-SHA256"  >HMAC-SHA256</option>
                            <option value="HMAC-SHA384"  >HMAC-SHA384</option>
                            <option value="HMAC-SHA512"  >HMAC-SHA512</option>
                            <option value="HMAC-RIPEMD160"  >HMAC-RIPEMD160</option>
                          </select>
					</td>
					<th><@locale code="apps.saml.digestMethod" />：</th>
                    <td>
                        <select  id="digestMethod" name="digestMethod"  class="form-control" >
                            <option value="MD5"         >MD5</option>
                            <option value="SHA1"        selected>SHA1</option>
                            <option value="SHA256"      >SHA256</option>
                            <option value="SHA384"      >SHA384</option>
                            <option value="SHA512"      >SHA512</option>
                            <option value="RIPEMD-160"  >RIPEMD-160</option>
                        </select>
                    </td>
				</tr>				
				<tr>
					
					<th><@locale code="apps.saml.nameidFormat" />：</th>
					<td>
						<select  id="nameidFormat" name="nameidFormat"  class="form-control"  >
							<option value="persistent" selected>persistent</option>
							<option value="transient">transient</option>
							<option value="emailAddress">emailAddress</option>
							<option value="X509SubjectName">X509SubjectName</option>
							<option value="WindowsDomainQualifiedName">WindowsDomainQualifiedName</option>
							<option value="unspecified" >unspecified</option>
							<option value="entity">entity</option>
							<option value="custom">user custom persistent </option>
						</select>
						<b class="orange">*</b><label for="fileType"></label>
					</td>
					<th><@locale code="apps.saml.nameIdConvert" />：</th>
					<td>
						<select  id="nameIdConvert" name="nameIdConvert"  class="form-control"  >
							<option value="0" selected>
								<@locale code="apps.saml.nameIdConvert.original" /></option>
							<option value="1">
								<@locale code="apps.saml.nameIdConvert.upperCase" /></option>
							<option value="2">
								<@locale code="apps.saml.nameIdConvert.lowerCase" /></option>
						</select>
						<b class="orange">*</b><label for="issuer"></label>
					</td>
					
				</tr>
				<tr>
					<th style="width:15%;"><@locale code="apps.saml.binding" />：</th>
					<td style="width:35%;">
						<select  id="binding" name="binding" class="form-control"   >
							<option value="Redirect-Post"  selected>Redirect-Post</option>
							<option value="Post-Post" >Post-Post</option>
							<option value="IdpInit-Post" >IdpInit-Post</option>
							<option value="Redirect-PostSimpleSign">Redirect-PostSimpleSign</option>
							<option value="Post-PostSimpleSign" >Post-PostSimpleSign</option>
							<option value="IdpInit-PostSimpleSign" >IdpInit-PostSimpleSign</option>
						</select>
						<b class="orange">*</b><label for="binding"></label>
					</td>
					<th style="width:15%;"><@locale code="apps.saml.validityInterval" />：</th>
					<td style="width:35%;">
						<input type="text" class="form-control"   id="validityInterval" name="validityInterval"  title="" value="15"  required="" />
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.saml.fileType" />：</th>
					<td>
						<select  id="fileType" name="fileType"  class="form-control"  >
							<option value="certificate"><@locale code="apps.saml.fileType.certificate" /></option>
							<option value="metadata"  selected><@locale code="apps.saml.fileType.metadata" /></option>
						</select>
						<b class="orange">*</b><label for="fileType"></label>
					</td>
					<th><@locale code="apps.saml.certMetaFile" />：</th>
					<td nowrap >
						<div style="float: left;">
							<img id="certMetaFileImg"  height="40" width="80" alt="upload certificate or metadata file" src="<@base />/static/images/cert.png">
						</div>
						<div style="float: left; width: 250px;">
							<input class="form-control"   id="certMetaFile" type="file" name="certMetaFile" />
							<b class="orange">*</b><label for="certMetaFile"></label>
						</div>
					</td>
				</tr>
				
				<tr>
					<th><@locale code="apps.saml.encrypted" />：</th>
					<td >
						<select  id="encrypted" name="encrypted"  class="form-control"  >
							<option value="0"  selected>
								<@locale code="apps.saml.encrypted.no" /></option>
							<option value="1">
								<@locale code="apps.saml.encrypted.yes" /></option>
						</select>
					</td>
					<th></th>
					<td>
					</td>
				</tr>
				<tr>
					<td colspan =4>
						<input  id="status" type="hidden" name="status"  value="1"/>
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