<%@ page   language="java"  import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" 		uri="http://sso.maxkey.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"  %>

<table width="100%">
  <tr>
    <td>
	  <table  class="datatable" >
			<tbody>
			<tr>
				<td colspan="2">CounterBased One Time Password</td>
			</tr>
			<tr>
				<td width="50%"> <img id="captchaimg" src="<c:url value="/image/${id}"/>" /></td>
				<td  width="50%">
					<table  class="datatable">
						<tr>
							<th width="120px"><s:Locale code="userinfo.displayName" /> :</th>
							<td>
								<input readonly type="text" class="required" title="" value="${userInfo.displayName}"/>
								
							</td>
						</tr>
						<tr>
							<th><s:Locale code="userinfo.username" /> :</th>
							<td>
								<input readonly type="text" class="required" title="" value="${userInfo.username}"/>
							</td>
						</tr>
						<tr>
							<th><s:Locale code="access.security.otp.sharedSecret" />(BASE32) :</th>
							<td>
							<input readonly type="text" class="required" title="" value="${sharedSecret}"/>
							</td>
						</tr>
						<tr>
							<th><s:Locale code="access.security.otp.sharedSecret" />(HEX) :</th>
							<td>
								<input readonly type="text" class="required" title="" value="${hexSharedSecret}"/>
							</td>
						</tr>
						<tr>
							<th><s:Locale code="access.security.otp.period" />:</th>
							<td>
								<input readonly type="text" class="required" title="" value="${format.period}"/>
							</td>
						</tr>
						<tr>
							<th><s:Locale code="access.security.otp.digits" />:</th>
							<td>
								<input readonly type="text" class="required" title="" value="${format.digits}"/>
							</td>
						</tr>
						<tr>
							<th><s:Locale code="access.security.otp.crypto" />:</th>
							<td>
								<input readonly type="text" class="required" title="" value="${format.crypto}"/>
							</td>
						</tr>
						<tr>
							<th><s:Locale code="access.security.otp.counter" />:</th>
							<td>
								<input readonly type="text" class="required" title="" value="${format.counter}"/>
							</td>
						</tr>
						<tr>
							<td colspan="2">
					    		<input class="button forward" style="width:100px" wurl="<c:url value="/otp/gen/counterbased"/>"  type="button"    id="forward" value="<s:Locale code="button.text.save" />"/>
								
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
		</tbody>
	  </table>
</td>
  </tr>
</table>