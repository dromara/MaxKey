<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/inte/inte3.jsp?language=zh_CN");	
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
 	<jsp:param value="inte_3" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
    <h1>Registration Procedure</h1>
    <div id="chapters">
	  	<ol>
	        <li><a href="#002">Offline application procedure</a></li>
	    </ol>
    </div>
        
  
    <!--  // end 所有图片加文字描述的样式 div=imgtxt -->
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
   


 <div id="chapters">
 	<h1><a name="002">1.offline application procedure:</a></h1>
        <ol>
        <li>Application integration applicant accesses https://login.ConnSec.com/wiki。</li>
        <li>Applicant clicks on "application integration application". Application selects the integration approach as the actual situation might be and then downloads the related forms and documents.</li>
        <li>Fill in the related application information in according to the example of the forms and documents.</li>
        <li>Submit the forms and documents to the responsible person of ConnSec SSO, the email:suppert@ConnSec.com。</li>
        <li>Internal review of ConnSec application integration.</li>
        <li> SSO responsible person complete application registration upon the completion of internal review.</li>
        <li>Feedback the related information of registration to the application applicant.</li>
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
