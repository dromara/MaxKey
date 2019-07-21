<!DOCTYPE html>
<%@ page 	import="org.springframework.security.core.AuthenticationException" %>
<%@ page 	import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page 	import="org.maxkey.authz.oauth2.common.exceptions.UnapprovedClientAuthenticationException" %>
<%@ page    import="org.springframework.security.web.WebAttributes" %>
<%@ taglib 	prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="s"  uri="http://sso.maxkey.org/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../layout/header.jsp"></jsp:include>
<jsp:include page="../layout/common.css.jsp"></jsp:include>
<jsp:include page="../layout/common.js.jsp"></jsp:include>
</head>

<body>
	<div id="top">
		<jsp:include page="../layout/nologintop.jsp"></jsp:include>
	</div>
	<div class="container">	
		<c:if test="${'oauth 2.0'==model.oauth_version}">
			<!-- oauth 2.0 -->
		    <% if (session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null && !(session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) instanceof UnapprovedClientAuthenticationException)) { %>
		      <div class="error">
		        <h2> Woops!</h2>
		
		        <p>Access could not be granted. (<%= ((AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).getMessage() %>)</p>
		      </div>
		    <% } %>
		    <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
		    
		      <h2>Please Confirm OAuth 2.0</h2>
		
		      <p>You hereby authorize "${client.clientId}" to access your protected resources.</p>
		      <form id="confirmationForm" name="confirmationForm" action="<%=request.getContextPath()%>/oauth/v20/authorize" method="post">
		        <input name="user_oauth_approval" value="true" type="hidden"/>
		        	
			        <ul>
						<c:forEach items="${model.scopes}" var="scope">
							<c:set var="approved">
								<c:if test="${scope.value}"> checked</c:if>
							</c:set>
							<c:set var="denied">
								<c:if test="${!scope.value}"> checked</c:if>
							</c:set>
					        <li>
								${scope.key}: 
								<input type="radio" name="${scope.key}" value="true"${approved}>Approve</input>
								<input type="radio" name="${scope.key}" value="false"${denied}>Deny</input>
							</li> 
		        		</c:forEach>
		       		 </ul>
		        <label><input name="authorize" value="Authorize" type="submit"/></label>
		      </form>
	    </c:if>
    </div>
    <div id="footer">
		<jsp:include page="../layout/footer.jsp"></jsp:include>
	</div>
</body>
</html>
