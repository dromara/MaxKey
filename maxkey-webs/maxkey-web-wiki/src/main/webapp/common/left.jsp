<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- treeView Start -->
<%
String path = request.getContextPath();
String lang = "zh_CN";
if(request.getParameter("language")!=null){
	lang = request.getParameter("language");
}
%>
<script type="text/javascript">
$(document).ready(function() {
	$('#menu ul').hide();
	//显示展开节点
	var currId='${param.pageType}';
	var showId=currId.split("-");
	$("#"+showId[0]+"").show();
	if(showId.length>=2){
		$("#"+showId[0]+"-"+showId[1]+"").show();
	}
	
	//click event
	$('#menu li a,#menu li ul li a').click(
		function() {
			var checkElement = $(this).next();
			if((checkElement.is('ul')) && (checkElement.is(':visible'))) {
				checkElement.slideUp('normal');
			  	return false;
			}
			if((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
			  	checkElement.slideDown('normal');
			  	return false;
			  }
		  }
	);
});
</script>
<% if(lang.equals("zh_CN")){%>
	<jsp:include page="/common/left_zh.jsp">
		<jsp:param value="${param.pageType}" name="pageType" />
	</jsp:include>
<%} else { %>
	<jsp:include page="/common/left_en.jsp">
		<jsp:param value="${param.pageType}" name="pageType" />
	</jsp:include>
<%} %>
<!-- treeView End -->
