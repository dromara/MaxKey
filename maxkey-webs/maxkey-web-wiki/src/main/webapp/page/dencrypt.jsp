<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/glossary.jsp");	
}
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/common/head.jsp"/>
</head>

<body>
<jsp:include page="/common/top.jsp"></jsp:include>
<div id="container">
 <jsp:include page="/common/left.jsp">
 	<jsp:param value="dencrypt" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>加密和解密相关技术</h1>
    <div class="text-section">
     <p class="section">介绍身份管理和身份认证中常用到的加密和解密的相关技术。</p>
    </div><!-- 一段描述结束 -->
     <table class="basisTable" cellspacing="1">
    	<thead>
      		<th style="width:50px" >序号</th><th>加密方式</th><th>链接</th>
    	</thead>
	    <tbody>
		    <tr>
		        <td>1</td>
		        <td>基本加密算法(BASE64、MD5、SHA、HMAC)</td>
		        <td><a target="_blank"  href="http://shimingxy.blog.163.com/blog/static/740383420149299562390/">访问</a></td>
		    </tr>
		    <tr>
		        <td>2</td>
		        <td> 对称加密算法(DES&AES)</td>
		        <td><a target="_blank"  href="http://shimingxy.blog.163.com/blog/static/7403834201492910620243/">访问</a></td>
		    </tr>
		    <tr>
		        <td>3</td>
		        <td>非对称加密算法RSA</td>
		        <td><a target="_blank"  href="http://shimingxy.blog.163.com/blog/static/7403834201492910119466/">访问</a></td>
		    </tr>
		    <tr>
		        <td>4</td>
		        <td>数字签名算法DSA</td>
		        <td><a target="_blank"  href="http://shimingxy.blog.163.com/blog/static/74038342014929101756185/">访问</a></td>
		    </tr>
		    <tr>
		        <td>5</td>
		        <td>数字证书</td>
		        <td><a target="_blank"  href="http://shimingxy.blog.163.com/blog/static/74038342014929102042126/">访问</a></td>
		    </tr>
		    <tr>
		        <td>6</td>
		        <td>初探SSL</td>
		        <td><a target="_blank"  href="http://shimingxy.blog.163.com/blog/static/74038342014929102555555/">访问</a></td>
		    </tr>
		    <tr>
		        <td>8</td>
		        <td>单向认证</td>
		        <td><a target="_blank"  href="http://shimingxy.blog.163.com/blog/static/7403834201492910283441/">访问</a></td>
		    </tr>
		    <tr>
		        <td>9</td>
		        <td>基本加密算法(BASE64、MD5、SHA、HMAC)</td>
		        <td><a target="_blank"  href="http://shimingxy.blog.163.com/blog/static/740383420149299562390/">访问</a></td>
		    </tr>
		    <tr>
		        <td>10</td>
		        <td>双向认证</td>
		        <td><a target="_blank"  href="http://shimingxy.blog.163.com/blog/static/74038342014929103010146/">访问</a></td>
		    </tr>
		    <tr>
		        <td>11</td>
		        <td>BASE64URL编码和解码</td>
		        <td><a target="_blank"  href="http://shimingxy.blog.163.com/blog/static/740383420152113532844/">访问</a></td>
		    </tr>
		    
	    </tbody>
    </table>
   
 </div>
 <!-- content end -->
 <!-- //content end -->
<div class="clear"></div>
</div>
<div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>
</body>
</html>
