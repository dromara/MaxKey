<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/inte/inte3_en.jsp?language=en_US");	
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/common/head.jsp"/>
</head>

<body>
<jsp:include page="/common/top.jsp"></jsp:include>
<div id="container">
 <jsp:include page="/common/left.jsp">
 	<jsp:param value="inte-inte3" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
    <h1>注册流程</h1>
    <div id="chapters">
	  	<ol>
	        <li><a href="#002">线下申请流程</a></li>
	    </ol>
    </div>
        
  
    <!--  // end 所有图片加文字描述的样式 div=imgtxt -->
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
   


 <div id="chapters">
 	<h1><a name="002">1.线下申请流程：</a></h1>
        <ol>
        <li>应用集成申请人访问https://login.ConnSec.com/wiki。</li>
        <li>申请人点击”应用集成申请”。应用根据实际的情况选择集成的方式，下载相应的表格文档。</li>
        <li>按照表格文档的示例填入相应的应用信息。</li>
        <li>把表格文档提交给ConnSec SSO的负责人，邮箱Support@ConnSec.com。</li>
        <li>ConnSec应用集成内部审核。</li>
        <li>内部审核完成，SSO的负责人完成应用注册。</li>
        <li>注册的相关信息反馈给应用申请人。</li>
        </ol>
</div>


 </div>
 </div>
 <!-- content end -->
 <!-- //content end -->
<div class="clear"></div>
<div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>
</body>
</html>
