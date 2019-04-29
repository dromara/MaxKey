<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
String lang = "zh_CN";
if(request.getParameter("language")!=null){
	lang = request.getParameter("language");
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>
<% if(lang.equals("zh_CN")){%> 应用集成开发指南
<%}else{%>App Integration Develop Wiki<%}%>
</title>
<jsp:include page="css.jsp"/>

<link rel="shortcut icon" href="<%=path%>/images/favicon.ico">

<script src="<%=path %>/js/jquery-1.9.1.js" type="text/javascript"></script>

<script type="text/javascript" src="<%=path%>/js/syntaxhighlighter_3.0.83/scripts/shCore.js"></script>
<script type="text/javascript" src="<%=path%>/js/syntaxhighlighter_3.0.83/scripts/shBrushJava.js"></script>
<script type="text/javascript" src="<%=path%>/js/syntaxhighlighter_3.0.83/scripts/shBrushXml.js"></script>
<script type="text/javascript" src="<%=path%>/js/syntaxhighlighter_3.0.83/scripts/shBrushJScript.js"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/js/syntaxhighlighter_3.0.83/styles/shCoreDefault.css"/>
<script type="text/javascript">
$(document).ready(function() {
	$(".basisTable tr:even").addClass("troddclass");
	SyntaxHighlighter.all();
});
</script>