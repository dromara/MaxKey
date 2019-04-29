<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page 	import="org.maxkey.constants.*"%>
<script type="text/javascript">
<!--
$(function(){	
	$("#protocol_text").html("<%=PROTOCOLS.SAML11%>");
	$("#protocol").val("<%=PROTOCOLS.SAML11%>");	
});
//-->
</script>
<form id="actionForm_app"  method="post" type="label" autoclose="true"  
			action="<s:Base/>/apps/saml11/add"
			forward="<s:Base/>/apps/list"
			enctype="multipart/form-data">		 
  	        <!-- content -->    
  	      	<!--table-->
			<table width="960"   class="datatable" >
				<tbody>
				<tr>
					<td ><jsp:include page="../appAddCommon.jsp"/></td>
				</tr>
				<tr>
					<td>
			   <table width="960"   class="datatable" >
				<tbody>
				<tr>
					<td colspan=4><s:Locale code="apps.saml.v1.1.info" /></td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.entityId" />：</th>
					<td colspan=3>
						<input type="text" id="entityId" name="entityId"  title="" value=""/>
						<b class="orange">*</b><label for="entityId"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.spAcsUrl" />：</th>
					<td colspan=3>
						<input type="text" id="spAcsUrl" name="spAcsUrl"  title="" value=""/>
						<b class="orange">*</b><label for="spAcsUrl"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.issuer" />：</th>
					<td colspan=3>
						<input type="text" id="issuer" name="issuer"  title="" value=""/>
						<b class="orange">*</b><label for="issuer"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.audience" />：</th>
					<td  colspan=3>
						<input type="text" id="audience" name="audience"  title="" value=""/>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.target" />：</th>
					<td >
						<input type="text" id="target" name="target"  title="" value=""/>
					</td>
					<th><s:Locale code="apps.saml.validityInterval" />：</th>
					<td >
						<input type="text" id="validityInterval" name="validityInterval"  title="" value="15"/>
					</td>
				</tr>
				<tr>
					<th style="width:15%;"><s:Locale code="apps.saml.fileType" />：</th>
					<td style="width:35%;">
						<select  id="fileType" name="fileType" >
							<option value="certificate" selected>
								<s:Locale code="apps.saml.fileType.certificate" /></option>
							<option value="metadata">
								<s:Locale code="apps.saml.fileType.metadata" /></option>
						</select>
						<b class="orange">*</b><label for="fileType"></label>
					</td>
					<th style="width:15%;"><s:Locale code="apps.saml.certMetaFile" />：</th>
					<td style="width:35%;">
						<input id="certMetaFile" type="file" name="certMetaFile" />
						<b class="orange">*</b><label for="certMetaFile"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.nameidFormat" />：</th>
					<td>
						<select  id="nameidFormat" name="nameidFormat" >
							<option value="NameIdentifier" selected>NameIdentifier</option>
							<option value="emailAddress">emailAddress</option>
							<option value="X509SubjectName">X509SubjectName</option>
							<option value="WindowsDomainQualifiedName">WindowsDomainQualifiedName</option>
							<option value="unspecified" >unspecified</option>
							<option value="custom">user custom NameIdentifier </option>
						</select>
						<b class="orange">*</b><label for="fileType"></label>
					</td>
					<th><s:Locale code="apps.isAdapter" />：</th>
					<td >
						<select  id="isAdapter" name="isAdapter" >
							<option value="0"  selected><s:Locale code="apps.isAdapter.no" /></option>
							<option value="1"><s:Locale code="apps.isAdapter.yes" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.adapter" />：</th>
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
    		<input class="button" id="submitBtn" type="submit" value="<s:Locale code="button.text.save" />"/>
			<input class="button" id="backBtn" type="button" value="<s:Locale code="button.text.cancel" />"/>	  
</form>