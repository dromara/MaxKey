<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<s:Base/>/groups/update">
	 <table  border="0" cellpadding="0" cellspacing="0" class="datatable">
		<tbody>
		<tr>
			<th><s:Locale code="group.id" />：</th>
			<td nowrap>
				<input id="id" type="text" readonly name="id"  value="${model.id}"/>
			</td>
		</tr>
		<tr>
			<th><s:Locale code="group.name" />：</th>
			<td nowrap>
				<span class="intspan"><input type="text" id="name" name="name" class="int required" title="" value="${model.name}"/></span>
				<b class="orange">*</b><label for="name"></label>
			</td>
		</tr>
		<tr>
			<td nowrap colspan="2"  class="center">
				<input id="_method" type="hidden" name="_method"  value="post"/>
				
				
				<input id="status" type="hidden" name="status"  value="1"/>
	    		<input class="button"  id="submitBtn" type="button" value="<s:Locale code="button.text.save" />">
  				<input class="button"  id="closeBtn"   type="button" value="<s:Locale code="button.text.cancel" />">	 
			</td>
		</tr>
		</tbody>
	  </table>
</form>