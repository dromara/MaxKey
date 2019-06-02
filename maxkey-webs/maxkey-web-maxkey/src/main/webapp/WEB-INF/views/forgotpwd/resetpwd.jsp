<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn"     	 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring"  	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" 			uri="http://sso.maxkey.org/tags" %> 

<div class="container">	
  <c:if test="${null != model}">
	<table border="0"  style="width:100%;">
		<tr>
			<td width="630px">
				
			</td>
			<td>
				<form action="<s:Base/>/forgotpassword/setpassword/${model.id}" method="post">
					<table  class="datatable">
						<tr>
							<td><s:Locale code="register.workemail"/></td>
							<td>${model.email}</td>
						</tr>
						<tr>
							<td><s:Locale code="register.password"/></td>
							<td><input type='password' id="password" name="password" value="" /></td>
						</tr>
						<tr>
							<td><s:Locale code="register.confirmpassword"/></td>
							<td><input type='password' id="confirmpassword" name="confirmpassword" value="" /></td>
						</tr>
						<tr>
							<td  colspan="2"><input id="registerBtn" class="button" type="submit" value="<s:Locale code="button.text.enable" />"/></td>
						</tr>
						
					</table>
				</form>
			</td>
		</tr>
	</table>
	  </c:if>
    <c:if test="${null == model}">
    	url expired.
    </c:if>  
</div>