<%@ page   language="java"  import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" 		uri="http://sso.maxkey.org/tags" %>
<%@ taglib prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE HTML >
<html>
<head>
	<jsp:include page="../layout/header.jsp"></jsp:include>
	<jsp:include page="../layout/common.css.jsp"></jsp:include>
	<jsp:include page="../layout/common.js.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../layout/top.jsp"></jsp:include>
<jsp:include page="../layout/nav_primary.jsp"></jsp:include>

<table width="100%">
  <tr>
    <td>
	  <table  class="datatable" >
			<tbody>
			<tr>
				<td colspan="2"><s:Locale code="login.totp.title" /></td>
			</tr>
			<tr>
				<td> <img id="captchaimg" src="<c:url value="/image/${id}"/>" /></td>
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
							<th><s:Locale code="login.totp.sharedSecret" />(BASE32) :</th>
							<td>
							<input readonly type="text" class="required" title="" value="${sharedSecret}"/>
							</td>
						</tr>
						<tr>
							<th><s:Locale code="login.totp.sharedSecret" />(HEX) :</th>
							<td>
								<input readonly type="text" class="required" title="" value="${hexSharedSecret}"/>
							</td>
						</tr>
						<tr>
							<th><s:Locale code="login.totp.period" />:</th>
							<td>
								<input readonly type="text" class="required" title="" value="${format.period}"/>
							</td>
						</tr>
						<tr>
							<th><s:Locale code="login.totp.digits" />:</th>
							<td>
								<input readonly type="text" class="required" title="" value="${format.digits}"/>
							</td>
						</tr>
						<tr>
							<th><s:Locale code="login.totp.crypto" />:</th>
							<td>
								<input readonly type="text" class="required" title="" value="${format.crypto}"/>
							</td>
						</tr>
						
						<tr>
							<td colspan="2"  class="center">
					    		<input class="button forward" style="width:100px" wurl="<c:url value="/safe/otp/gen/timebased"/>"  type="button"    id="forward" value="<s:Locale code="login.totp.generate" />"/>
								
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
<div id="footer">
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</div>
<body>
</html>