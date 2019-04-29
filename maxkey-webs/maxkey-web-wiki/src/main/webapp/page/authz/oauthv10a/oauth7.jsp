<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/oauthv20/oauth7_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-oauth10a-oauth7" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
   
    <h1>API接口标准</h1>
     <div id="chapters">
    	 <table class="basisTable" cellspacing="1">
    	 		 <tr>
				    <th> <strong>接口 </strong> </th>
				    <th> <strong>说明 </strong> </th>
				    <th> <strong>详细说明 </strong> </th>
				    <th> <strong>调用方法 </strong> </th>
				  </tr>
				  <tr>
				    <td> oauth/authorize </td>
				    <td> 请求用户授权Token </td>
				    <td> https://login.connsec.com/sec接收app sso认证请求,<br>client_id为需要认证的应用的id;</td>
				    <td> APP </td>
				  </tr>
				  <tr>
				    <td> oauth/token </td>
				    <td> 获取授权过的 Access Token </td>
				    <td> 后台应用获取 tokencode ，调用接口进行 tokencode 校验；<br>校验成功获取访问 token </td>
				    <td> APP </td>
				  </tr>
				  <tr>
				    <td> oauth/userinfo </td>
				    <td> 授权用户信息查询接口 </td>
				    <td> 通过访问 token 获取登录用户信息 </td>
				    <td> APP </td>
				  </tr>
    	 </table>
    
    
    </div>
    <div id="chapters">
	  	<ol>
	    	<li><a href="#001">oauth/authorize接口</a></li>
	    	<li><a href="#002">oauth /token接口</a></li>
	        <li><a href="#003">oauth/userinfo接口</a>
	        	<ol>
	        	<li>
	        		<a href="#003.1">OAuth认证接口属性列表</a>
	        	</li>
	        	</ol>
	        </li>
	    </ol>
    </div>
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
  
        
        
  
    <!--  // end 所有图片加文字描述的样式 div=imgtxt -->
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
 <div id="chapters">
 	<h1><a name="001">1.oauth/authorize接口：</a></h1>
 	<h5>请求用户授权Token</h5>
 	 <table class="basisTable" cellspacing="1">
 	   <tr>
	    <th> 接口名称 </th>
	    <th> 请求用户授权Token </th>
  	  </tr>
	  <tr>
	    <td> url </td>
	    <td> https://login.connsec.com/sec/oauth/authorize </td>
	  </tr>
	  <tr>
	    <td> 请求方式 </td>
	    <td> http get/post </td>
	  </tr>
 	 </table>
 	 
 	 <h5>请求参数</h5>
 	 <table class="basisTable" cellspacing="1">
 	   <tr>
	    <th>参数 </th>
	    <th> 说明 </th>
  	  </tr>
	  <tr>
	    <td> client_id </td>
	    <td> 注册应用时分配的client_id。 </td>
	  </tr>

	   <tr>
	    <td> redirect_uri </td>
	    <td>应用回调地址，注册时需要配置</td>
	  </tr>
	  <tr>
	  	<td>grant_type</td>
	  	<td>授权类型。</td>
	  </tr>
	   <tr>
	  	<td>etc param</td>
	  	<td>其他参数。</td>
	  </tr>
	  
	  <tr>
	  		<td colspan="2" align="left">
	  		响应返回app应用程序，包含请求参数如下：
	  		</td>
	  </tr>
	  <tr>
 			<td colspan="2" align="left">
 			http://app.ConnSec.com/app/callback?tokencode =PQ7q7W91a-oMsCeLvIaQm6bTrgtp7
 			</td>
	  
	  </tr>
	  <tr>
	  		<td>tokencode</td>
	  		<td>用于调用oauth/token，接口获取授权后的访问token。</td>
	  </tr>
 	 </table> 	
</div>

<div id="chapters">
 	<h1><a name="002">2.oauth /token接口：</a></h1>
 	<h5>通过oauth/token用tokencode换取访问token</h5>
 	 <table class="basisTable" cellspacing="1">
 	   <tr>
	    <th> 接口名称 </th>
	    <th> token 接口 </th>
  	  </tr>
	  <tr>
	    <td> url </td>
	    <td> https://login.connsec.com/sec/oauth/token </td>
	  </tr>
	  <tr>
	    <td> 请求方式 </td>
	    <td> http get/post </td>
	  </tr>
 	 </table>
 	 
 	 <h5>请求参数</h5>
 	 <table class="basisTable" cellspacing="1">
 	   <tr>
	    <th>参数 </th>
	    <th> 说明 </th>
  	  </tr>
	  <tr>
	    <td> client_id </td>
	    <td> 注册应用时分配的client_id。 </td>
	  </tr>
	  <tr>
	    <td> client_secret </td>
	    <td> 注册应用时分配的client_secret</td>
	  </tr>
	   <tr>
	    <td> redirect_uri </td>
	    <td>应用回调地址，注册时需要配置</td>
	  </tr>
	  <tr>
	  	<td>tokencode</td>
	  	<td>调用oauth/authorize获得的tokencode值。</td>
	  </tr>
	  <tr>
	  	<td>grant_type</td>
	  	<td>授权类型。Grant type</td>
	  </tr>
	  <tr>
	  	<td>username</td>
	  	<td>当grant_type=password时，此参数表示直接认证用户名。</td>
	  </tr>
	  <tr>
	  	<td>password</td>
	  	<td>当grant_type=password时，此参数表示直接认证用户密码。</td>
	  </tr>
	  <tr>
	  	<td>etc param</td>
	  	<td>其他参数</td>
	  </tr>
	  <tr align="left">
	  	<td colspan="2" align="left">
	  				实际请求如下：
					<p>	The actual request might look like:</p>
					<p>	POST /oauth/ token HTTP/1.1</p>
					<p>	Host: login.connsec.com/openapi</p>
					<p>	Content-Type: application/x-www-form-urlencoded</p>
					<p>	tokencode= PQ7q7W91a-oMsCeLvIaQm6bTrgtp7&</p>
					<p>	client_id=QPKKKSADFUP876&</p>
					<p>	client_secret={client_secret}&</p>
					<p>	redirect_uri=http://app.ConnSec.com/app/callback</p>
	  	</td>
	  </tr>
	  <tr>
	  		<td colspan="2" align="left">
	  		返回数据
	  		</td>
	  </tr>
	  <tr>
 			<td colspan="2" align="left">
 			A successful response to this request contains the following fields:
 			</td>
	  
	  </tr>
	  <tr>
	  		<td>access_token</td>
	  		<td>用该token能调用SSO的API</td>
	  </tr>
	  <tr>
	  		<td colspan="2">
	  		<p>成功返回JSON数据，如下：</p>
			<p>	{</p>
			<p>	access_token  :  “token_id”</p>
			<p>	…</p>
			<p>	}</p>
	  		</td>
	  </tr>
 	 </table> 	
</div>

<div id="chapters">
 	<h1><a name="003">3.oauth/userinfo接口</a></h1>
 	 <table class="basisTable" cellspacing="1">
 	   <tr bgcolor="#CCCCCC">
	    <th> 接口名称 </th>
	    <th> token 接口 </th>
  	  </tr>
	  <tr>
	    <td> url </td>
	    <td>https://login.connsec.com/sec/oauth/userinfo</td>
	  </tr>
	  <tr>
	    <td> 请求方式 </td>
	    <td> http get/post </td>
	  </tr>
 	 </table>
 	 
 	 <h5>请求参数</h5>
 	 <table class="basisTable" cellspacing="1">
 	   <tr>
	    <th>参数 </th>
	    <th> 说明 </th>
  	  </tr>
	  <tr>
	    <td> access_token </td>
	    <td> 调用sso/ token获得的token值。 </td>
	  </tr>
	  <tr align="left">
	  	<td colspan="2" align="left">
	  				实际请求如下：
					<p>	POST /oauth/ userinfo HTTP/1.1</p>
					<p>	Host: login.connsec.com/openapi</p>
					<p>Content-Type: application/x-www-form-urlencoded</p>
					<p>	access_token= PQ7q7W91a-oMsCeLvIaQm6bTrgtp7</p>
	  	</td>
	  </tr>
	  <tr>
	  		<td colspan="2" align="left">
	  		返回数据/ response data
	  		</td>
	  </tr>
	  <tr>
	  		<td colspan="2">
	  		<p>成功返回JSON数据，如下：</p>
			<p>	{</p>
			<p>	userid     :  “zhangs”,</p>
			<p>	…</p>
			<p>	}</p>
			<p>zhangs是认证的用户ID</p>
	  		</td>
	  </tr>
 	 </table> 	
</div>



<div id="chapters">
 	<h1><a name="003.1">3.1 	OAuth认证接口属性列表</a></h1>
 	 <table class="basisTable" cellspacing="1">
 	   <tr bgcolor="#CCCCCC">
	    <th> 属性名(Attribute) </th>
	    <th> 描述 </th>
	    <th>数据类型</th>
  	  </tr>
  	  <tr>
  	  	<td>uid</td>
  	  	<td>uid</td>
  	  	<td>字符串</td>
  	  </tr>
 	 </table> 	
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
