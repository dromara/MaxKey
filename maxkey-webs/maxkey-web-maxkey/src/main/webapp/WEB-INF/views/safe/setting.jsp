<%@ page    language="java"  import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib  prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib  prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib 	prefix="s" 		 uri="http://sso.maxkey.org/tags" %>
<%@ taglib  prefix="c"       uri="http://java.sun.com/jsp/jstl/core"  %>
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

<form id="actionForm"  method="post" type="label" autoclose="true"  action="<s:Base/>/safe/setting"> 

	  <table  class="datatable" >
			<tbody>
			<tr>
				<th  colspan="2">authnSetting</th>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.displayName" /> :</th>
				<td style="width:700px">
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
				<th><s:Locale code="userinfo.authnType" />:</th>
				<td nowrap>
					<select name="authnType" id="authnType">
						<option value="1"  <c:if test="${0==model.authnType}">selected</c:if>  ><s:Locale code="button.text.select" /></option>
						<option value="1"  <c:if test="${1==model.authnType}">selected</c:if>  ><s:Locale code="userinfo.authnType.authnType.1" /></option>
						<!-- 
						<option value="2"  <c:if test="${2==model.authnType}">selected</c:if>  ><s:Locale code="userinfo.authnType.authnType.2" /></option>
						 -->
						<option value="3"  <c:if test="${3==model.authnType}">selected</c:if>  ><s:Locale code="userinfo.authnType.authnType.3" /></option>
						<option value="4"  <c:if test="${4==model.authnType}">selected</c:if>  ><s:Locale code="userinfo.authnType.authnType.4" /></option>
						<option value="5"  <c:if test="${5==model.authnType}">selected</c:if>  ><s:Locale code="userinfo.authnType.authnType.5" /></option>
						<!-- 
						<option value="6"  <c:if test="${6==model.authnType}">selected</c:if>  ><s:Locale code="userinfo.authnType.authnType.6" /></option>
						<option value="7"  <c:if test="${7==model.authnType}">selected</c:if>  ><s:Locale code="userinfo.authnType.authnType.7" /></option>
						
						<option value="8"  <c:if test="${8==model.authnType}">selected</c:if>  ><s:Locale code="userinfo.authnType.authnType.8" /></option>
						<option value="9"  <c:if test="${9==model.authnType}">selected</c:if>  ><s:Locale code="userinfo.authnType.authnType.9" /></option>
						<option value="10"  <c:if test="${10==model.authnType}">selected</c:if>  ><s:Locale code="userinfo.authnType.authnType.10" /></option>
						 -->
					</select>
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
					<input type="text" id="mobileVerify" name="mobileVerify" class="required" title="" value="1"  style="width:200px" /><input class="button" style="width:100px"  type="button"    id="getMobileVerifyBtn" value="get Verify"/>
					<label for="verify"></label>
				</td>
			</tr>
			<tr>
				<th><s:Locale code="userinfo.email" /> :</th>
				<td>
					<input type="text" id="email" name="email" class="required" title="" value="${model.email}"/>
					<label for="eamil"></label>
				</td>
			</tr>
			<tr style="display:none;">
				<th>Verify Code :</th>
				<td>
					<input type="text" id="emailVerify" name="emailVerify" class="required" title="" value="Verify"  style="width:200px" /><input class="button" style="width:100px"  type="button"    id="getEmailVerifyBtn" value="get Verify"/>
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
<div id="footer">
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</div>
<body>
</html>