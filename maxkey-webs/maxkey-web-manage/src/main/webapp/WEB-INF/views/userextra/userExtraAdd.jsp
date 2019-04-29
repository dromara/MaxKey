<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<s:Base/>/userextra/add">

	  <table border="0" cellpadding="0" cellspacing="0" class="datatable">
		<tbody>
		<tr>
			<th><s:Locale code="usertype.id" />：</th>
			<td nowrap>
				<span class="intspan"><input type="text" id="userTypeId" name="userTypeId" class="int required" title="" value="${model.id}"/></span>
				<b class="orange">*</b><label for="userTypeId"></label>
			</td>
		</tr>
		<tr>
			<th><s:Locale code="userinfo.userType.name" />：</th>
			<td nowrap>
				<span class="intspan"><input type="text" id="userTypeName" name="userTypeName" class="int required" title="" value="${model.name}"/></span>
				<b class="orange">*</b><label for="userTypeName"></label>
			</td>
		</tr>
		<tr>
			<th><s:Locale code="userinfo.userType.extra.attributeName" />：</th>
			<td nowrap>
				<span class="intspan"><input type="text" id="attributeName" name="attributeName" class="int required" title="" value=""/></span>
				<b class="orange">*</b><label for="attribute"></label>
			</td>
		</tr>
		<tr>
			<th><s:Locale code="userinfo.userType.extra.attribute" />：</th>
			<td nowrap>
				<span class="intspan"><input type="text" id="attribute" name="attribute" class="int required" title="" value=""/></span>
				<b class="orange">*</b><label for="attribute"></label>
			</td>
		</tr>
		<tr>
			<th><s:Locale code="userinfo.userType.extra.attributeType" />：</th>
			<td nowrap>
				<span class="intspan"><input type="text" id="attributeType" name="attributeType" class="int required" title="" value=""/></span>
				<b class="orange">*</b><label for="attributeType"></label>
			</td>
		</tr>
		<tr>
			<th><s:Locale code="userinfo.userType.extra.defaultValue" />：</th>
			<td nowrap>
				<span class="intspan"><input type="text" id="defaultValue" name="defaultValue" class="int required" title="" value=""/></span>
				<b class="orange">*</b><label for="defaultValue"></label>
			</td>
		</tr>
		<tr>
			 <th><s:Locale code="common.text.sortorder"/>：</th>
	         <td  nowrap>
	         	<input  type="text" id="sortOrder" name="sortOrder"  title="" value="0"/>
	            <b class="orange">*</b><label for="sortOrder"></label>
	         </td>
         </tr>
		<tr>
			<td colspan="2" class="center">
				<input id="_method" type="hidden" name="_method"  value="post"/>
				<input id="status" type="hidden" name="status"  value="1"/>
	    		<input class="button"  type="button"    id="submitBtn" value="<s:Locale code="button.text.save" />"/>
  				<input class="button"  type="button"    id="closeBtn" value="<s:Locale code="button.text.cancel" /> "/>	
				
			</td>
		</tr>
		</tbody>
	  </table>
</form>