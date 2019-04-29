<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<script type="text/javascript">
<!--
$(function(){	

});
//-->
</script>
<form id="actionForm"  method="post" type="label"  action="<s:Base/>/config/saml20/metadata/update">		 
  	        <!-- content -->    
  	      	<!--table-->
			   <table width="960"  class="datatable" >
				<tbody>
				<tr style="display:none">
					<th>app id：</th>
					<td>
						<input type="text" id="id" name="id"  title="" value="${model.id}"/>
						<input type="hidden" id="modaction" name="modaction"  title="" value="${modaction}"/>
						<b class="orange">*</b><label for="id"></label>
					</td>
					
					
				</tr>
				<tr>
					<td colspan=4><s:Locale code="apps.saml.metadata.info" /></td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.metadata.orgName" />：</th>
					<td>
						<input type="text" id="orgName" name="orgName"  title="" value="${model.orgName}"/>
						<b class="orange">*</b><label for="entityId"></label>
					</td>
					<th><s:Locale code="apps.saml.metadata.orgDisplayName" />：</th>
					<td>
						<input type="text" id="orgDisplayName" name="orgDisplayName"  title="" value="${model.orgDisplayName}"/>
						<b class="orange">*</b><label for="issuer"></label>
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="apps.saml.metadata.orgURL" />：</th>
					<td colspan="3">
						<input type="text" id="orgURL" name="orgURL"  title="" value="${model.orgURL}"/>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.metadata.contactType" />：</th>
					<td>
						<select  id="contactType" name="contactType" >
							<option value="technical"  <c:if test="${'technical'==model.contactType}">selected</c:if>>technical</option>
							<option value="support" <c:if test="${'support'==model.contactType}">selected</c:if>>support</option>
							<option value="administrative" <c:if test="${'administrative'==model.contactType}">selected</c:if>>administrative</option>
							<option value="billing" <c:if test="${'billing'==model.contactType}">selected</c:if>>billing</option>
							<option value="other"  <c:if test="${'other'==model.contactType}">selected</c:if>>other</option>
						</select>
						<b class="orange">*</b><label for="contactType"></label>
					</td>
					<th><s:Locale code="apps.saml.metadata.company" />：</th>
					<td><input type="text" id="company" name="company"  title="" value="${model.company}"/>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.metadata.givenName" />：</th>
					<td>
						<input type="text" id="givenName" name="givenName"  title="" value="${model.givenName}"/>
						<b class="orange">*</b><label for="givenName"></label>
					</td>
					<th><s:Locale code="apps.saml.metadata.surName" />：</th>
					<td>
						<input type="text" id="surName" name="surName"  title="" value="${model.surName}"/>
						<b class="orange">*</b><label for="surName"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="apps.saml.metadata.emailAddress" />：</th>
					<td>
						<input type="text" id="emailAddress" name="emailAddress"  title="" value="${model.emailAddress}"/>
						<b class="orange">*</b><label for="entityId"></label>
					</td>
					<th><s:Locale code="apps.saml.metadata.telephoneNumber" />：</th>
					<td>
						<input type="text" id="telephoneNumber" name="telephoneNumber"  title="" value="${model.telephoneNumber}"/>
						<b class="orange">*</b><label for="telephoneNumber"></label>
					</td>
				</tr>
				</tbody>
			  </table>
  	      
    		<input class="button" id="submitBtn" type="button" value="<s:Locale code="button.text.save" />"/>
</form>