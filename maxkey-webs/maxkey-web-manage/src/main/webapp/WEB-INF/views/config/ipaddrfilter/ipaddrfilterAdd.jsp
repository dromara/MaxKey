<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib 	prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>
<%@ taglib 	prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<s:Base/>/config/ipaddrfilter/add">
	<table border="0" cellpadding="0" cellspacing="0" class="datatable" >
		<tbody>
			<tr>
				<th><s:Locale code="ipaddrfilter.ipAddr" />：</th>
				<td nowrap>
					<span class="intspan"><input type="text" id="ipAddr" name="ipAddr" class="int required" title="" value=""/></span>
					<b class="orange">*</b><label for="ipAddr"></label>
				</td>
			</tr>
			<tr>
			<th><s:Locale code="ipaddrfilter.filter" />：</th>
			<td nowrap>
				<span class="intspan">
				<select  id="filter" name="filter" >
					<option value="1"  selected><s:Locale code="ipaddrfilter.filter.whitelist"/></option>
					<option value="2" ><s:Locale code="ipaddrfilter.filter.blacklist"/></option>
				</select>
				</span>
				<b class="orange">*</b><label for="filter"></label>
			</td>
		</tr>
			<tr>
				<td nowrap colspan="2" class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input  id="status" type="hidden" name="status"  value="1"/>
		    		<input class="button"  id="submitBtn" type="button" value="<s:Locale code="button.text.save" />">
	  				<input class="button"  id="closeBtn"   type="button" value="<s:Locale code="button.text.cancel" />"> 
				</td>
			</tr>
		</tbody>
	</table>
</form>