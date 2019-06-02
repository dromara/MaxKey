<%@ page   language="java"  import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib 	prefix="s" 		uri="http://sso.maxkey.org/tags" %>

<table width="100%">
  <tr>
    <td>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<s:Base/>/safe/mobile"> 

	  <table  class="datatable" >
			<tbody>
			<tr>
				<th  colspan="2"><s:Locale code="access.security.mobile.setting" /></th>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.displayName" /> :</th>
				<td>
					<input readonly type="text" id="displayName" name="displayName" class="required" title="" value="${model.displayName}"/>
					
				</td>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.username" /> :</th>
				<td>
					<input readonly type="text" id="username" name="username" class="required" title="" value="${model.username}"/>
					
				</td>
			</tr>
			
			<tr>
				<th><s:Locale code="userinfo.mobile" /> :</th>
				<td>
					<input type="text" id="mobile" name="mobile" class=" required" title="" value="${model.mobile}"/>
					<label for="mobile"></label>
				</td>
			</tr>
			<tr style="display:none;">
				<th>Verify Code :</th>
				<td>
					<input type="text" id="verify" name="verify" class="required" title="" value="1"  style="width:200px" /><input class="button" style="width:100px"  type="button"    id="getVerifyBtn" value="get Verify"/>
					<label for="verify"></label>
				</td>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button" style="width:100px"  type="button"    id="submitBtn" value="<s:Locale code="button.text.save" />"/>
					
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</td>
  </tr>
</table>