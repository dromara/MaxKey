<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn"     	 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring"  	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" 			uri="http://www.connsec.com/tags" %> 

<div class="container">	
	<table border="0"  style="width:100%;">
		<tr>
			<td width="630px">
				
			</td>
			<td>
				<form action="<s:Base/>/forgotpassword/email" method="post">
					<table  class="datatable">
						<tr>
							<td><s:Locale code="forgotpwd.email"/></td>
							<td><input type="email" id="email" name="email" class="int required" title="" value=""/></td>
						</tr>
						
						<tr>
							<td  colspan="2"><input id="forgotpwdBtn" class="button" type="submit" value="<s:Locale code="button.text.enable" />"/></td>
						</tr>
						
					</table>
				</form>
			</td>
		</tr>
	</table>
</div>