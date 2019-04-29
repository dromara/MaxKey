<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<s:Base/>/company/update">
 
  	        <!-- content -->    
  	      	<!--table-->
			<table width="980"  class="datatable" >
				<tbody>
				<tr>
					<th  width="140px"><s:Locale code="company.shortname" />：</th>
					<td  width="340px">
						<input type="text" id="shortName" name="shortName"  title="" value="${model.shortName}"/>
						<b class="orange">*</b><label for="shortName"></label>
					</td>
					<th  width="140px"><s:Locale code="company.fullname" />：</th>
					<td  width="340px">
						<input type="text" id="fullName" name="fullName"  title="" value="${model.fullName}"/>
						<b class="orange">*</b><label for="fullName"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="company.division" />：</th>
					<td>
						<input type="text" id="division" name="division"  title="" value="${model.division}"/>
						<b class="orange">*</b><label for="division"></label>
					</td>
					<th><s:Locale code="company.category" />：</th>
					<td>
						<input type="text" id="category" name="category"  title="" value="${model.category}"/>
						<b class="orange">*</b><label for="category"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="company.representative" />：</th>
					<td>
						<input type="text" id="representative" name="representative"  title="" value="${model.representative}"/>
						<b class="orange">*</b><label for="representative"></label>
					</td>
					<th><s:Locale code="company.website" />：</th>
					<td>
						<input type="text" id="webSite" name="webSite"  title="" value="${model.webSite}"/>
						<b class="orange">*</b><label for="webSite"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="company.phone" />：</th>
					<td>
						<input type="text" id="phone" name="phone"  title="" value="${model.phone}"/>
						<b class="orange">*</b><label for="phone"></label>
					</td>
					<th><s:Locale code="company.fax" />：</th>
					<td>
						<input type="text" id="fax" name="fax"  title="" value="${model.fax}"/>
						<b class="orange">*</b><label for="fax"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="company.email" />：</th>
					<td>
						<input type="text" id="email" name="email"  title="" value="${model.email}"/>
						<b class="orange">*</b><label for="email"></label>
					</td>
					<th><s:Locale code="company.postalcode" />：</th>
					<td>
						<input type="text" id="postalCode" name="postalCode"  title="" value="${model.postalCode}"/>
						<b class="orange">*</b><label for="postalCode"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="company.country" />：</th>
					<td>
						<input type="text" id="country" name="country"  title="" value="${model.country}"/>
						<b class="orange">*</b><label for="country"></label>
					</td>
					<th><s:Locale code="company.region" />：</th>
					<td>
						<input type="text" id="region" name="region"  title="" value="${model.region}"/>
						<b class="orange">*</b><label for="region"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="company.locality" />：</th>
					<td>
						<input type="text" id="locality" name="locality"  title="" value="${model.locality}"/>
						<b class="orange">*</b><label for="locality"></label>
					</td>
					<th><s:Locale code="company.street" />：</th>
					<td>
						<input type="text" id="street" name="street"  title="" value="${model.street}"/>
						<b class="orange">*</b><label for="street"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="company.license" />：</th>
					<td>
						<input type="text" id="license" name="license"  title="" value="${model.license}"/>
						<b class="orange">*</b><label for="license"></label>
					</td>
					<th><s:Locale code="company.creation.date" />：</th>
					<td>
						<input type="text" id="creationDate" name="creationDate"  title="" value="${model.creationDate}"/>
						<b class="orange">*</b><label for="creationDate"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="common.text.description" />：</th>
					<td colspan="3">
						<input  type="text" id="description" name="description"  title="" value="${model.description}"/>
						<b class="orange">*</b><label for="description"></label>
					</td>
				</tr>
				</tbody>
			  </table>
			<input id="_method" type="hidden" name="_method"  value="post"/>
			<input id="status" type="hidden" name="status"  value="1"/>
    		<input  id="submitBtn" class="button" style="width:100px" type="button" value="<s:Locale code="button.text.save" />"> 
				
</form>