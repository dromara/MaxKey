<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="c"		  uri="http://java.sun.com/jsp/jstl/core" %>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<s:Base/>/usertype/update">
	 		<table border="0" cellpadding="0" cellspacing="0" class="datatable">
				<tbody>
				<tr>
					<th><s:Locale code="userinfo.userType.name" />：</th>
					<td nowrap>
						<span class="intspan"><input type="text" id="name" name="name" class="int required" title="" value="${model.name}"/></span>
						<b class="orange">*</b><label for="name"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="common.text.description" />：</th>
					<td nowrap>
						<span class="intspan"><input type="text" id="description" name="description" class="int required" title="" value="${model.description}"/></span>
						<b class="orange">*</b><label for="name"></label>
					</td>
				</tr>
				<tr>
					 <th><s:Locale code="common.text.sortorder"/>：</th>
			         <td  nowrap>
			         	<input  type="text" id="sortOrder" name="sortOrder"  title="" value="${model.sortOrder}"/>
			            <b class="orange">*</b><label for="sortOrder"></label>
			         </td>
		         </tr>
				<tr>
					<th><s:Locale code="common.text.status" />：</th>
					<td nowrap>
						<select id="status" name="status">
							<option value="3"  <c:if test="${3==model.status}">selected</c:if> ><s:Locale code="common.text.status.3" /></option>
							<option value="4"  <c:if test="${4==model.status}">selected</c:if>><s:Locale code="common.text.status.4" /></option>
						</select>
					</td>
				</tr>
				<tr>
				<td nowrap colspan="2" class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input id="id" type="hidden" name="id"  value="${model.id}"/>
		    		<input class="button"  id="submitBtn" type="button" value="<s:Locale code="button.text.save" />">
	  				<input class="button"  id="closeBtn"   type="button" value="<s:Locale code="button.text.cancel" />">	 
				</td>
			</tr>
				</tbody>
			  </table>
</form>