<%@ page   contentType="text/html; charset=UTF-8" import="java.util.Map,java.util.LinkedHashMap" %>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="c"		    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" 	uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"	    uri="http://www.springframework.org/tags" %>
<script type="text/javascript">	
$(function () {
	$("#rqcodescanBtn").on("click",function(){
		 if($("#rqcodescanBtn").hasClass("onscan")){
		 	$("#nameCode").show();
			$("#rqcodePicture").hide();
			$("#rqcodescanBtn").removeClass("onscan");
		 }else{
		 	$("#nameCode").hide();
			$("#rqcodePicture").show();
		 	$("#rqcodescanBtn").addClass("onscan");
		 }
	});
});
</script>	
<table style="width:600px;border:1px solid #e5e5e5;">
	<tr>
	
		<td style="width:200px;">
			<c:if test="${null==model.picture}">
				<img id="picture" width="200px" height="200px" src="<c:url value="/images/uploadimage.jpg"/>" />
			</c:if>
			<c:if test="${null!=model.picture}">
			<img id="picture" width="200px" height="200px" src="<c:url value="/image/${model.id}"/>" />
			</c:if>
			<br>
			<input class="button"   id="rqcodescanBtn" type="button" value="<s:Locale code="access.security.contact.rqcode"/>" />
		</td>
		<td>
			<img style="display:none" id="rqcodePicture" width="300px" height="300px" src="<c:url value="/image/${rqcode}"/>" />
			<table id="nameCode" style="width:400px;">
				<tr>
					<td><s:Locale code="userinfo.displayName"/></td><td><input readonly type="text" title="" value="${model.displayName}"/></td>
				</tr>
				<tr>
					<td><s:Locale code="userinfo.jobTitle"/></td><td><input readonly type="text" title="" value="${model.jobTitle}"/></td>
				</tr>
				<tr>
					<td><s:Locale code="userinfo.department"/></td><td><input readonly type="text" title="" value="${model.department}"/></td>
				</tr>
				<tr>
					<td><s:Locale code="userinfo.mobile"/></td><td><input readonly type="text" title="" value="${model.mobile}"/></td>
				</tr>
				<tr>
					<td><s:Locale code="userinfo.email"/></td><td><input readonly type="text" title="" value="${model.email}"/></td>
				</tr>
				<tr>
					<td><s:Locale code="userinfo.webSite"/></td><td><input readonly type="text"  title="" value="${model.webSite}"/></td>
				</tr>
				<tr>
					<td><s:Locale code="userinfo.organization"/></td><td><input readonly type="text" title="" value="${model.organization}"/></td>
				</tr>
				<tr>
					<td><s:Locale code="userinfo.workStreetAddress"/></td><td><input readonly type="text" title="" value="${model.workStreetAddress}"/></td>
				</tr>
			</table>		
		</td>
	</tr>
	</table>
