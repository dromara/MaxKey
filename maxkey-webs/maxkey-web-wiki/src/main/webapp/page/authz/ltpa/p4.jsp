<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/samlv20/saml4_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-ltpa-ltpa4" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
    <h1>SAML安全认证配置</h1>
     <table class="basisTable" cellspacing="1">
    <thead>
      <th>SAML配置</th><th>URL</th>
    </thead>
    <tbody>
      <tr>
    	<td>SAML SSO认证地址</td>
        <td>https://login.connsec.com/sec/saml/authorize/appid</td>
    </tr>
     <tr>
    	<td>SAML IDP metadata XML 地址</td>
        <td>https://login.connsec.com/sec/saml/metadata/appid</td>
    </tr>
     <tr>
        <td colspan="2">注:可以通过导入metadata实现SP和IDP的互信</td>
    </tr>
    </tbody>
    </table>
    
    <div id="chapters">
   ConnSec SAML与自开发应用认证配置
  	<ol>
    	<li><a href="#001">生成证书</a></li>
        <li><a href="#002">注册SAML应用</a></li>
        <li><a href="#003">实现证书互信</a></li>
    </ol>
    
    <div id="chapters">
 		<a href="#004">ConnSec SAML与Saas应用认证配置</a>
  	<!-- 
  	<ol>
    	<li><a href="">ConnSec SAML与Saas应用认证配置</a></li>
    </ol>
     -->
  </div>

    <!-- 所有图片加文字描述的样式 div=imgtxt -->
    <div class="imgtxt">
    	<div class="imgtxt-txt">
		<h4><a name="001">1.生成证书</a></h4>
        <h5>SAML IDP与SP之间的信任需要配置证书，通过证书对认证的信息进行加密和解密，证书格式为X.509证书，下面介绍如何使用JDK keytool工具生成证书。</h5>
       <pre>
		        cmd进入jdk中keytool的安装目录，输入命令：keytool -genkey -alias 证书别名 -keypass 别名密码 -keyalg RSA -keysize 1024 -validity 365 -keystore 证书存放的路径及名称 -storepass 证书的秘钥。
       </pre>
        <div class="imgtxt-img">
        <h5>其步骤如下图所示:</h5>
        <img src="../../images/saml/saml4_1.png" alt="JDK keytool工具生成证书">
        </div>
        
        
        <div class="imgtxt-img">
      <pre>在cmd.exe中输入命令：keytool -list -v -keystore d:\ConnSecsp.keystore -storepass secret，若结果如下图所示，则表明证书生成成功</pre>
        <img src="../../images/saml/saml4_2.png" alt="JDK keytool工具生成证书">
        </div>
    </div>    
    </div>
    <!--  // end 所有图片加文字描述的样式 div=imgtxt -->
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
   

    <div class="imgtxt">
    	<div class="imgtxt-txt">
		<h4><a name="002">2.注册SAML应用</a></h4>
        <ul>
        <li>
		       <h5>
		       使用SAML时，在线服务供应商可以联系一个独立的网络身份认证提供者，对试图访问受保护内容的用户进行身份验证。此过程的实现需要把服务供应商相关的SAML信息注册到身份认证提供者中，以便身份提供者对请求进行判断和处理，如验证请求是否由合法的服务供应商发出等，其配置如下图所示：
		       </h5>
        </li>
        </ul>
        <div class="imgtxt-img">
        <h5>其步骤如下图所示:</h5>
        <img src="../../images/saml/saml4_3.png" alt="生成配置">
        </div>
        
<table class="basisTable" cellspacing="1">
    <thead>
      <th>内容</th><th>作用</th>
    </thead>
    <tbody>
	    <tr>
	    	<td>服务供应商证书</td>
	        <td>SP应用的证书，目的是为了让IDP信任此证书</td>
	    </tr>
	    <tr>
	    	<td>认证回调地址</td>
	        <td>IDP认证成功后，回跳到SP的地址.</td>
	    </tr>
	    <tr>
	    	<td>启用SAML认证</td>
	        <td>是否启用SAML认证，若启用，则使用SAML协议进行认证</td>
	    </tr>
    </tbody>
</table>
    </div>    
    </div>
 <div id="chapters">
 		<a name="003">3.实现证书互信</a>
  </div>
	<div class="text-section">
		SAML认证时，IDP和SP均需要判断请求方或相应方是否受信任，为此，在使用SAML协议实现SSO时，自	的证书为信任证书，其实现步骤有：
        <ol>
        <li>下载IDP的证书</li>
        <li>将IDP的证书添加到SP证书中，使SP的证书信任IDP的证书。</li>
        </ol>
          <pre>cmd进入jdk中keytool的安装目录，输入命令：keytool -import -alias 证书别名(别名唯一，否则导入出错) -file d:\ConnSecidp.cer -keystore d:\ConnSecsp.keystore -storepass 证书的密钥，此步骤就是将ConnSecidp证书导入ConnSecsp.keystore中，使ConnSecsp.keystore信任ConnSecidp.cer证书。</pre>
	</div>




   <div class="imgtxt">
    	<div class="imgtxt-txt">
		<h4><a name="004">ConnSec SAML与Saas应用认证配置</a></h4>
		 <ol>
        <li>下载Saas应用的证书。</li>
        <li>注册SAML应用，同时将下载的Saas证书上传到信息配置中。</li>
        <li>将Saas应用的证书添加到ConnSec SAML的证书中，使Saas的证书受信于ConnSec SAML自建的证书。</li>
        </ol>
		</div>
   </div>		



 </div>
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
