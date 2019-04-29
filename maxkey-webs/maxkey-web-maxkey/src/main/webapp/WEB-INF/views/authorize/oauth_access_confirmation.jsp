<!DOCTYPE html>
<%@ page 	import="org.springframework.security.core.AuthenticationException" %>
<%@ page 	import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page 	import="org.maxkey.authz.oauth2.common.exceptions.UnapprovedClientAuthenticationException" %>
<%@ page    import="org.springframework.security.web.WebAttributes" %>
<%@ taglib 	prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="s"  uri="http://www.connsec.com/tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
  <title>Access Confirmation</title>
  <link rel="shortcut icon" type="image/x-icon" href="<s:Base />/images/favicon.ico"/>
  <link type="text/css" rel="stylesheet" href="<s:Base />/css/base.css"/>
</head>

<body>
	<h1>Access Confirmation ${'oauth 1.0a'==model.oauth_version}</h1>
  		<div id="content">
	
		 <c:if test="${'oauth 1.0a'==model.oauth_version}">
		 	<!-- oauth 1.0a -->
		    <c:if test="${!empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
		      <div class="error">
		        <h2>Woops!</h2>
		
		        <p>Access could not be granted. (<%= ((AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).getMessage() %>)</p>
		      </div>
		    </c:if>
		    <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
		    <authz:authorize ifAllGranted="ROLE_USER">
		      <h2>Please Confirm OAuth 1.0a</h2>
		
		      <p>You hereby authorize "${consumer.consumerName}" to access the following resource:</p>
		
		      <ul>
		          <li>${consumer.resourceName} &mdash; ${consumer.resourceDescription}</li>
		      </ul>
		      
		      <form id="oauth_v10a_form" name="oauth_v10a_form" action="<c:url value="/oauth/v10a/authenticate_token"/>" method="post">
		        <input name="requestToken" value="${model.oauth_token}" type="hidden"/>
		        <c:if test="${!empty model.oauth_callback}">
		        <input name="callbackURL" value="${model.oauth_callback}" type="hidden"/>
		        </c:if>
		        <label><input name="authorize" value="Authorize" type="submit"/></label>
		      </form>
		       <c:if test="${!empty model.approval_prompt&&'auto'== model.approval_prompt}">
		       		<script type="text/javascript">
		       			document.getElementById("oauth_v10a_form").submit();
		       		</script>
			  </c:if>
		    </authz:authorize>
		  
		</c:if>
		
		<c:if test="${'oauth 2.0'==model.oauth_version}">
			<!-- oauth 2.0 -->
		    <% if (session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null && !(session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) instanceof UnapprovedClientAuthenticationException)) { %>
		      <div class="error">
		        <h2> Woops!</h2>
		
		        <p>Access could not be granted. (<%= ((AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).getMessage() %>)</p>
		      </div>
		    <% } %>
		    <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
		    
		       <authz:authorize ifAllGranted="ROLE_USER">
		      <h2>Please Confirm OAuth 2.0</h2>
		
		      <p>You hereby authorize "${client.clientId}" to access your protected resources.</p>
		      <form id="confirmationForm" name="confirmationForm" action="<%=request.getContextPath()%>/oauth/v20/authz" method="post">
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
		    </authz:authorize>
	    </c:if>
    </div>
</body>
</html>
