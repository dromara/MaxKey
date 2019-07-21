<%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       	uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn"     	 	uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring"  	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" 			uri="http://sso.maxkey.org/tags" %> 

<div class="container">	
  <c:if test="${0 == pwdreseted}">
  	password not eq the confirm password,<br>
  	<input type="button"  class="button"  value="后退"  onclick="javascript:history.go(-1);"> 
  </c:if>
  <c:if test="${1 == pwdreseted}">
  	Reset Password successful,<a href="<s:Base/>/login">click here</a> login.
  </c:if>
  <c:if test="${2 == pwdreseted}">
  	url expired.
  </c:if>
</div>