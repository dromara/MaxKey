<%@ page   language="java"  import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib 	prefix="s" uri="http://www.connsec.com/tags" %>

<table width="100%">
  <tr>
    <td>

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<@base/>/users/changePassword"> 

	  <table   class="datatable" >
			<tbody>
			<tr>
				<th><@locale code="userinfo.displayName" /> :</th>
				<td>
					<input readonly type="hidden" id="id" name="id" class="required" title="" value="${model.id}"/>
					<input readonly type="text" id="displayName" name="displayName" class="required" title="" value="${model.displayName}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="userinfo.username" /> :</th>
				<td>
					<input readonly type="text" id="username" name="username" class="required" title="" value="${model.username}"/>
					
				</td>
			</tr>
			<tr>
				<th><@locale code="login.password.newPassword" />:</th>
				<td>
					<input type="password" id="password" name="password" class="required" title="" value=""/>
					<b class="orange">*</b>
					<label for="oldPassword"></label>
				</td>
			</tr>
			<tr>
				<th><@locale code="login.password.confirmPassword" />:</th>
				<td nowrap>
					<input type="password" id="confirmPassword" name="confirmPassword" class="{ required: true, equalTo: '#newPassword' }" title="" value=""/>
					<b class="orange">*</b>
					<label for="confirmPassword"></label>
				</td>
			</tr>
			<tr>
				<td colspan="2"  class="center">
					<input id="_method" type="hidden" name="_method"  value="post"/>
		    		<input class="button" style="width:100px"  type="button"    id="submitBtn" value="<@locale code="button.text.save" />"/>
					
				</td>
			</tr>
		</tbody>
	  </table>
</form>
</td>
  </tr>
</table>