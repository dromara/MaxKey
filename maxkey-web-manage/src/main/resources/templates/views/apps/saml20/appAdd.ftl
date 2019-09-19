<script type="text/javascript">
<!--
$(function(){	
	$("#protocol_text").html("<%=PROTOCOLS.SAML20%>");
	$("#protocol").val("<%=PROTOCOLS.SAML20%>");	
});
//-->
</script>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<@base/>/apps/saml20/add"
			forward="<@base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			<table width="960"  class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appAddCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
			   <table width="960"  class="datatable" >
				<tbody>
				
				<tr>
					<td colspan=4><@locale code="apps.saml.v2.0.info" /></td>
				</tr>
				<tr>
					<th><@locale code="apps.saml.entityId" />：</th>
					<td colspan =3>
						<input type="text" id="entityId" name="entityId"  title="" value=""/>
						<b class="orange">*</b><label for="entityId"></label>
					</td>
					
					
				</tr>
				
				<tr>
					<th><@locale code="apps.saml.issuer" />：</th>
					<td colspan =3>
						<input type="text" id="issuer" name="issuer"  title="" value=""/>
						<b class="orange">*</b><label for="issuer"></label>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.saml.spAcsUrl" />：</th>
					<td colspan =3>
						<input type="text" id="spAcsUrl" name="spAcsUrl"  title="" value=""/>
						<b class="orange">*</b><label for="spAcsUrl"></label>
					</td>
				</tr>
				
				<tr>
					<th><@locale code="apps.saml.audience" />：</th>
					<td colspan =3>
						<input type="text" id="audience" name="audience"  title="" value=""/>
					</td>
				</tr>
				<tr>
					
					<th><@locale code="apps.saml.nameidFormat" />：</th>
					<td>
						<select  id="nameidFormat" name="nameidFormat" >
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
						<select  id="nameIdConvert" name="nameIdConvert" >
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
						<select  id="binding" name="binding" >
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
						<input type="text" id="validityInterval" name="validityInterval"  title="" value="15"/>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="apps.saml.fileType" />：</th>
					<td>
						<select  id="fileType" name="fileType" >
							<option value="certificate" selected><@locale code="apps.saml.fileType.certificate" /></option>
							<option value="metadata"><@locale code="apps.saml.fileType.metadata" /></option>
						</select>
						<b class="orange">*</b><label for="fileType"></label>
					</td>
					<th><@locale code="apps.saml.certMetaFile" />：</th>
					<td>
						<input id="certMetaFile" type="file" name="certMetaFile" />
						<b class="orange">*</b><label for="certMetaFile"></label>
					</td>
				</tr>
				
				<tr>
					<th><@locale code="apps.saml.encrypted" />：</th>
					<td >
						<select  id="encrypted" name="encrypted" >
							<option value="0"  selected>
								<@locale code="apps.saml.encrypted.no" /></option>
							<option value="1">
								<@locale code="apps.saml.encrypted.yes" /></option>
						</select>
					</td>
					<th><@locale code="apps.isAdapter" />：</th>
					<td>
						<select  id="isAdapter" name="isAdapter" >
							<option value="0"  selected><@locale code="apps.isAdapter.no" /></option>
							<option value="1"><@locale code="apps.isAdapter.yes" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><@locale code="apps.adapter" />：</th>
					<td colspan =3>
						<input type="text" id="adapter" name="adapter"  title="" value=""/>
					</td>
				</tr>
				</tbody>
			  </table>
			</td>
				</tr>
				</tbody>
				</table>
			<input  id="status" type="hidden" name="status"  value="1"/>
    		<input class="button" id="submitBtn" type="submit" value="<@locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<@locale code="button.text.cancel" />"/>	  
</form>