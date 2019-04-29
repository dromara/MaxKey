<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/oauthv20/oauth8_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-oidc10-oidc8" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  
 <div class="text-section">
  	<h1>OAuth2.0 错误码</h1>
  	<div id="chapters">
	<div class="text-section">
       <p>ConnSec OAuth2.0实现中，授权服务器在接收到验证授权请求时，会按照OAuth2.0协议对本请求的请求头部、请求参数进行检验，若请求不合法或验证未通过，授权服务器会返回相应的错误信息，包含以下几个参数：</p>
		<ul>
			<li>error: 错误码</li>
			<li>error_description: 错误的描述信息</li>
		</ul>
	</div>
</div>	
</div>

    <div id="chapters">
    	<h5>错误信息的返回方式有两种：</h5>
    	<ol>
    		<li>当请求授权Endpoint：https://login.connsec.com/sec/oauth/authorize 时出现错误，返回方式是：跳转到redirect_uri,并在uri 的query parameter中附带错误的描述信息。</li>
    		<li>当请求access token endpoint:https://login.connsec.com/sec/oauth/token 时出现错误，返回方式：返回JSON文本。</li>
    	</ol>
    	<pre>
    	例如：
{
	"error":"unsupported_response_type",
	"error_description":"不支持的 ResponseType."
}
    	
    	</pre>
    </div>
    
     <div id="chapters">
    	<h5>OAuth2.0错误响应中的错误码定义如下表所示：</h5>
    	 <table class="basisTable" cellspacing="1">
    	    <thead>
		      <th>编号</th><th>错误码(error)</th><th>描述(error_description)</th>
		    </thead>
		    <tbody>
			    <tr>
			    	<td>1</td>
			    	<td>empty_client_id</td>
			    	<td>参数client_id为空</td>
			    </tr>
			    <tr>
			    	<td>2</td>
			    	<td>empty_client_secret</td>
			    	<td>参数client_secret为空</td>
			    </tr>
			     <tr>
			    	<td>3</td>
			    	<td>empty_redirect_uri</td>
			    	<td>参数redirect_uri为空</td>
			    </tr>
			     <tr>
			    	<td>4</td>
			    	<td>empty_response_type</td>
			    	<td>参数response_type为空</td>
			    </tr>
			     <tr>
			    	<td>5</td>
			    	<td>empty_code</td>
			    	<td>code为空</td>
			    </tr>
			     <tr>
			    	<td>6</td>
			    	<td>app_unsupport_sso</td>
			    	<td>应用不支持sso登录</td>
			    </tr>
			     <tr>
			    	<td>7</td>
			    	<td>app_unsupport_oauth</td>
			    	<td>应用不支持OAuth认证</td>
			    </tr>
			     <tr>
			    	<td>8</td>
			    	<td>invalid_client_id</td>
			    	<td>非法的client_id</td>
			    </tr>
			     <tr>
			    	<td>9</td>
			    	<td>invalid_response_type</td>
			    	<td>非法的response_type</td>
			    </tr>
			     <tr>
			    	<td>10</td>
			    	<td>invalid_scope</td>
			    	<td>非法的scope</td>
			    </tr>
			    <tr>
			    	<td>11</td>
			    	<td>invalid_grant_type</td>
			    	<td>非法的grant_type</td>
			    </tr>
			    <tr>
			    	<td>12</td>
			    	<td>redirect_uri_mismatch</td>
			    	<td>非法的redirect_uri</td>
			    </tr>
			    <tr>
			    	<td>13</td>
			    	<td>unsupported_response_type</td>
			    	<td>不支持传递的response_type</td>
			    </tr>
			    
			    <tr>
			    	<td>14</td>
			    	<td>invalid_code</td>
			    	<td>非法的code</td>
			    </tr>
			    <tr>
			    	<td>15</td>
			    	<td>unsupported_refresh_token</td>
			    	<td>不支持refresh_token的方式</td>
			    </tr>
			    <tr>
			    	<td>16</td>
			    	<td>access_token_exprise</td>
			    	<td>access_token过期</td>
			    </tr>
			    <tr>
			    	<td>17</td>
			    	<td>invalid_access_token</td>
			    	<td>非法的access_token</td>
			    </tr>
			    <tr>
			    	<td>18</td>
			    	<td>invalid_refresh_token</td>
			    	<td>非法的refresh_token</td>
			    </tr>
			    <tr>
			    	<td>19</td>
			    	<td>refresh_token_exprise</td>
			    	<td>refresh_token过期</td>
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
