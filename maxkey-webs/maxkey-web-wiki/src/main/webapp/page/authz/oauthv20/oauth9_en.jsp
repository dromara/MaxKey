<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("zh_CN")){
	response.sendRedirect(path+"/page/oauthv20/oauth9.jsp?language=zh_CN");	
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
 	<jsp:param value="oauth_9" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  <div id="chapters">
 <div class="text-section">
  	<h1>SDK Application Integration Guide Example</h1>
	<h5>Only JEE SDK and example offered, Refer to the explanation of other sections for the system SSO Integration of other language.</h5>
</div>	
</div>

  <div id="chapters">
  	<ol>
  		<li><a href="#001">JEE SDK application integration guide example</a>
  				<ol>
			    	<li><a href="#002"> Request OAuth authentication</a></li>
			    	<li><a href="#003">Obtain OAuth2.0 Access Token</a></li>
			        <li><a href="#004">Obtain user information</a></li>
			    </ol>
  		
  		</li>
  		<li><a href="#005">SDK application integration guide example</a></li>
  		<li><a href="#006">SDK & DEMO download</a></li>
  	</ol>
  	
  
  </div>

    <div id="chapters">
    	<h5><a name="002"></a>1.1 Request OAuth authentication</h5>
    	<div>
	    	<p>
	    		If the SSO authentication address of application skips to https://login.connsec.com, then it is to login through LandingPage, click on the application icon of the LandingPage, the system will automatically request OAuth to login application; otherwise authentication completes the direct application login.
	    	 </p>
	    	 <pre>
    	the direct login shall display the code as follows:
		String clientId = yourClientId;
		String clientSerect = yourClientSercet;
		String callback = yourAppCallBackUrl;
	
		//the object of parameter required for the storage of OAuth authentication
		OAuthService oAuthService = new OAuthService(clientId,clientSerect,callback);
		
		//OAuth authentication request address
		String authorizationUrl = oAuthService.getAuthorizationUrl();
    	</pre>
    	<p>
    		In which, the value of authorizationUrl is:<br>
	https://login.connsec.com/sec/oauth/authorize?client_id=yourClientId&grant_type=authorization_code&redirect_uri=yourAppCallBackUrl
    	</p>
	    </div>
    </div>
    
    <div id="chapters">
    	<h5><a name="003"></a>1.2 Obtain OAuth2.0 access token</h5>
    	<div>
    	<p>
    	As OAuth authentication succeeds, OAuth will redirect ser request to the token url provided by APP, and attach a parameter in the token url as well. The parameter name is code. Then, the APP application system can accept the value of code, which will send the request for obtaining Access Token to the OAuth Server, the request address is https://login.connsec.com/sec/oauth/access_token?client_id=yourClientId&client_secret=yourClientSercet&grant_type=authorization_code&redirect_uri=yourAppCallBackUrl&code=CODE, OAuth server will judge the validation of code sent. One Access Token will return to the application system if valid, in which, Access Token contains the attribute value: value of token, valid remaining time of token
    	</p>
    	</div>
    	<pre>
    	value of refresh_token, the example code as follows:
		//obtain OAuthService target
		OAuthService oAuthService = session.getAttribute(“oAuthService”);
		//obtain server returned code
		String code = request.getParameter("code");		
		//exchange for token through code
		Token accessToken = oAuthService.getAccessToken(code);
    	</pre>
    </div>
    
      <div id="chapters">
      	<h5><a name="004"></a>1.3 Obtain user information</h5>
      	<pre>
		// obtain the basic information of the login user	    
		UserInfo  userInfo=null;
		try {
		//oauth/userinfo interface
		userInfo = new UserInfo (accessToken);
		userInfo.requestUserInfo();
		} catch(OAuthApiException) {
		// exception handling
		}
      	</pre>
      
      </div>
    
    <div id="chapters">
     <h5><a name="006"></a>3.SDK & Demo Download</h5>
     	
  		<div>JAVA</div>
       <a href="<%=path%>/sdk/java/oauth2-sdk.zip">JAVA SDK</a><BR>	
	   <a href="<%=path%>/sdk/java/oauth2-demo.war"> JEE OAuth DEMO</a><BR>	
  		<hr>	
	  
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
