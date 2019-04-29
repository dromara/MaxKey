<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/oauthv20/oauth8.jsp?language=zh_CN");	
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
 	<jsp:param value="oauth_8" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  <div id="chapters">
 <div class="text-section">
  	<h1>OAuth2.0 Error Code</h1>
	<div class="text-section">
       <p>In the course of ConnSec OAuth2.0 realization, the authorization server conducts verification on the request head, request parameter of this request in according to the OAuth2.0 protocol when the verification and authorization request received; if the request is Invalidor the verification fails, the authorization server will return to the corresponding error information, which contains the following parameters:</p>
		<ul>
			<li>error: error code</li>
			<li>error_description: description information for error</li>
		</ul>
	</div>
</div>	
</div>

    <div id="chapters">
    	<h5>THE RETURN APPROACHES FOR ERROR INFORMATION CAN BE THE FOLLOWING TWO:</h5>
    	<ol>
    		<li>when error occurs in the course of request authorization Endpoint：https://login.connsec.com/sec/oauth/authorize, the return approach: skip to redirect_uri, and attach the description information of error in the query parameter of uri.</li>
    		<li>when error occurs in the course of request access token endpoint:https://login.connsec.com/sec/oauth/token, the return approach: return to JSON text.</li>
    	</ol>
    	<pre>
    	For Example:
{
	"error":"unsupported_response_type",
	"error_description":"不支持的 ResponseType."
}
    	
    	</pre>
    </div>
    
     <div id="chapters">
    	<h5>THE ERROR CODE DEFINITION IN OAUTH2.0 ERROR RESPONSE SHOWS AS FOLLOWS:</h5>
    	 <table class="basisTable" cellspacing="1">
    	    <thead>
		      <th>No.</th><th>Error code</th><th>error_description</th>
		    </thead>
		    <tbody>
			    <tr>
			    	<td>1</td>
			    	<td>empty_client_id</td>
			    	<td>Parameter client_id is empty</td>
			    </tr>
			    <tr>
			    	<td>2</td>
			    	<td>empty_client_secret</td>
			    	<td>Parameter client_secret is empty</td>
			    </tr>
			     <tr>
			    	<td>3</td>
			    	<td>empty_redirect_uri</td>
			    	<td>Parameter redirect_uri is empty</td>
			    </tr>
			     <tr>
			    	<td>4</td>
			    	<td>empty_response_type</td>
			    	<td>Parameter response_type is empty</td>
			    </tr>
			     <tr>
			    	<td>5</td>
			    	<td>empty_code</td>
			    	<td>code is null</td>
			    </tr>
			     <tr>
			    	<td>6</td>
			    	<td>app_unsupport_sso</td>
			    	<td>APP doesn’t support SSO login</td>
			    </tr>
			     <tr>
			    	<td>7</td>
			    	<td>app_unsupport_oauth</td>
			    	<td>APP doesn’t support OAuth authentication</td>
			    </tr>
			     <tr>
			    	<td>8</td>
			    	<td>invalid_client_id</td>
			    	<td>Invalid client_id</td>
			    </tr>
			     <tr>
			    	<td>9</td>
			    	<td>invalid_response_type</td>
			    	<td>Invalid response_type</td>
			    </tr>
			     <tr>
			    	<td>10</td>
			    	<td>invalid_scope</td>
			    	<td>Invalid scope</td>
			    </tr>
			    <tr>
			    	<td>11</td>
			    	<td>invalid_grant_type</td>
			    	<td>Invalid grant_type</td>
			    </tr>
			    <tr>
			    	<td>12</td>
			    	<td>redirect_uri_mismatch</td>
			    	<td>Invalid redirect_uri</td>
			    </tr>
			    <tr>
			    	<td>13</td>
			    	<td>unsupported_response_type</td>
			    	<td>Unsupported response_type delivered</td>
			    </tr>
			    
			    <tr>
			    	<td>14</td>
			    	<td>invalid_code</td>
			    	<td>Invalid code</td>
			    </tr>
			    <tr>
			    	<td>15</td>
			    	<td>unsupported_refresh_token</td>
			    	<td>Unsupported refresh_token approach</td>
			    </tr>
			    <tr>
			    	<td>16</td>
			    	<td>access_token_exprise</td>
			    	<td>access_token expire</td>
			    </tr>
			    <tr>
			    	<td>17</td>
			    	<td>invalid_access_token</td>
			    	<td>Invalid access_token</td>
			    </tr>
			    <tr>
			    	<td>18</td>
			    	<td>invalid_refresh_token</td>
			    	<td>Invalid refresh_token</td>
			    </tr>
			    <tr>
			    	<td>19</td>
			    	<td>refresh_token_exprise</td>
			    	<td>refresh_token expire</td>
			    </tr>
    	</tbody>
    	 </table>
    </div>
    <!-- // 一段描述结束 -->
 </div>
 <!-- //content end -->
<div class="clear"></div>
</div>
<div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>
</body>
</html>
