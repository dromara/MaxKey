<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/oauthv20/oauth7.jsp?language=zh_CN");	
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
 	<jsp:param value="oauth_7" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
    <div id="chapters">
    <h1>API Interface Standard</h1>
    	 <table class="basisTable" cellspacing="1">
    	 		 <tr>
				    <th> <strong>Interface</strong> </th>
				    <th> <strong>Description </strong> </th>
				    <th> <strong>Detailed Description</strong> </th>
				    <th style="  width: 70px;"> <strong>Call Method</strong> </th>
				  </tr>
				  <tr>
				    <td> oauth/authorize </td>
				    <td> Request user to authorize Token </td>
				    <td> https://login.connsec.com/sec receives app sso authentication request,</td>
				    <td> APP </td>
				  </tr>
				  <tr>
				    <td> oauth/token </td>
				    <td> Obtain the authorized Access Token</td>
				    <td> Background application acquires the tokencode, call the interface to carry out tokencode validation; validation succeeds to obtain access token</td>
				    <td> APP </td>
				  </tr>
				  <tr>
				    <td> oauth/userinfo </td>
				    <td> Authorize the user information to enquire interface </td>
				    <td> Obtain logined user information through access token </td>
				    <td> APP </td>
				  </tr>
    	 </table>
    
    
    </div>
    <div id="chapters">
	  	<ol>
	    	<li><a href="#001">oauth/authorize interface</a></li>
	    	<li><a href="#002">oauth /token interface</a></li>
	        <li><a href="#003">oauth/userinfo interface</a>
	        	<ol>
	        	<li>
	        		<a href="#003.1">OAuth authentication interface attribute list</a>
	        	</li>
	        	</ol>
	        </li>
	    </ol>
    </div>
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
  
        
        
  
    <!--  // end 所有图片加文字描述的样式 div=imgtxt -->
    <!-- 所有图片加文字描述的样式 div=imgtxt -->
 <div id="chapters">
 	<h1><a name="001">1.oauth/authorize interface:</a></h1>
 	<h5>REQUEST USER TO AUTHORIZE TOKEN</h5>
 	 <table class="basisTable" cellspacing="1">
 	   <tr>
	    <th> Interface Name </th>
	    <th> Request User to Authorize Token </th>
  	  </tr>
	  <tr>
	    <td> url </td>
	    <td> https://login.connsec.com/sec/oauth/authorize </td>
	  </tr>
	  <tr>
	    <td> Request Mode </td>
	    <td> http get/post </td>
	  </tr>
 	 </table>
 	 
 	 <h5>REQUEST PARAMETER</h5>
 	 <table class="basisTable" cellspacing="1">
 	   <tr>
	    <th>Parameter </th>
	    <th> Description </th>
  	  </tr>
	  <tr>
	    <td> client_id </td>
	    <td> Clinet-id allocated when the application registered. </td>
	  </tr>

	   <tr>
	    <td> redirect_uri </td>
	    <td>Application redirect address, configuration required for registration</td>
	  </tr>
	  <tr>
	  	<td>grant_type</td>
	  	<td>Grant type</td>
	  </tr>
	   <tr>
	  	<td>etc param</td>
	  	<td>Other parameters.</td>
	  </tr>
	  
	  <tr>
	  		<td colspan="2" align="left">
	  		Response and return to app application, the request parameter contained as follows:
	  		</td>
	  </tr>
	  <tr>
 			<td colspan="2" align="left">
 			http://app.ConnSec.com/app/callback?tokencode =PQ7q7W91a-oMsCeLvIaQm6bTrgtp7
 			</td>
	  
	  </tr>
	  <tr>
	  		<td>tokencode</td>
	  		<td>Call oauth/token, interface obtains the access token authorized.</td>
	  </tr>
 	 </table> 	
</div>

<div id="chapters">
 	<h1><a name="002">2.oauth /token Interface:</a></h1>
 	<h5>EXCHANGE FOR ACCESS TOKEN WITH TOKENCODE THROUGH OAUTH/TOKEN</h5>
 	 <table class="basisTable" cellspacing="1">
 	   <tr>
	    <th> Interface Name</th>
	    <th> token interface </th>
  	  </tr>
	  <tr>
	    <td> url </td>
	    <td> https://login.connsec.com/sec/oauth/token </td>
	  </tr>
	  <tr>
	    <td> Request Mode</td>
	    <td> http get/post </td>
	  </tr>
 	 </table>
 	 
 	 <h5>REQUEST PARAMETER</h5>
 	 <table class="basisTable" cellspacing="1">
 	   <tr>
	    <th>Parameter </th>
	    <th> Description </th>
  	  </tr>
	  <tr>
	    <td> client_id </td>
	    <td> Clinet-id allocated when the application registered. </td>
	  </tr>
	  <tr>
	    <td> client_secret </td>
	    <td> Clinet-secret allocated when the application registered.</td>
	  </tr>
	   <tr>
	    <td> redirect_uri </td>
	    <td>Application redirect address, configuration required for registration</td>
	  </tr>
	  <tr>
	  	<td>tokencode</td>
	  	<td>The tokencode value acquired by calling oauth/authorize</td>
	  </tr>
	  <tr>
	  	<td>grant_type</td>
	  	<td>Grant type</td>
	  </tr>
	  <tr>
	  	<td>username</td>
	  	<td>This parameter shows the direct authentication user name as grant_type=password.</td>
	  </tr>
	  <tr>
	  	<td>password</td>
	  	<td>This parameter shows the direct authentication user password as grant_type=password.</td>
	  </tr>
	  <tr>
	  	<td>etc param</td>
	  	<td>Other parameters</td>
	  </tr>
	  <tr align="left">
	  	<td colspan="2" align="left">
	  				The actual request might look like:
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
	  		Response data
	  		</td>
	  </tr>
	  <tr>
 			<td colspan="2" align="left">
 			A successful response to this request contains the following fields:
 			</td>
	  
	  </tr>
	  <tr>
	  		<td>access_token</td>
	  		<td>Use this token can invoke the API of SSO</td>
	  </tr>
	  <tr>
	  		<td colspan="2">
	  		<p>Successful return to JSON data, as follows:</p>
			<p>	{</p>
			<p>	access_token  :  “token_id”</p>
			<p>	…</p>
			<p>	}</p>
	  		</td>
	  </tr>
 	 </table> 	
</div>

<div id="chapters">
 	<h1><a name="003">3.oauth/userinfo interface</a></h1>
 	 <table class="basisTable" cellspacing="1">
 	   <tr bgcolor="#CCCCCC">
	    <th> Interface Name</th>
	    <th> token Interface</th>
  	  </tr>
	  <tr>
	    <td> url </td>
	    <td>https://login.connsec.com/sec/oauth/userinfo</td>
	  </tr>
	  <tr>
	    <td> Request Mode </td>
	    <td> http get/post </td>
	  </tr>
 	 </table>
 	 
 	 <h5>REQUEST PARAMETER</h5>
 	 <table class="basisTable" cellspacing="1">
 	   <tr>
	    <th>Parameter </th>
	    <th> Description </th>
  	  </tr>
	  <tr>
	    <td> access_token </td>
	    <td> The token value obtained by invoking sso/ token </td>
	  </tr>
	  <tr align="left">
	  	<td colspan="2" align="left">
	  				The actual request might look like:
					<p>	POST /oauth/ userinfo HTTP/1.1</p>
					<p>	Host: login.connsec.com/openapi</p>
					<p>Content-Type: application/x-www-form-urlencoded</p>
					<p>	access_token= PQ7q7W91a-oMsCeLvIaQm6bTrgtp7</p>
	  	</td>
	  </tr>
	  <tr>
	  		<td colspan="2" align="left">
	  		response data
	  		</td>
	  </tr>
	  <tr>
	  		<td colspan="2">
	  		<p>Successful return to JSON data, as follows:</p>
			<p>	{</p>
			<p>	userid     :  "zhangs",</p>
			<p>	…</p>
			<p>	}</p>
			<p>zhangs is the user ID authenticated.</p>
	  		</td>
	  </tr>
 	 </table> 	
</div>



<div id="chapters">
 	<h1><a name="003.1">3.1 	OAuth Authentication Interface Attribute List</a></h1>
 	 <table class="basisTable" cellspacing="1">
 	   <tr bgcolor="#CCCCCC">
	    <th> Attribute </th>
	    <th> Description </th>
	    <th>Data Type</th>
  	  </tr>
  	  <tr>
  	  	<td>uid</td>
  	  	<td>uid</td>
  	  	<td>Character String</td>
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
