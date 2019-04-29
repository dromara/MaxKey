<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/oauthv20/oauth9_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-oidc10-oidc9" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  
 <div class="text-section">
  	<h1>SDK应用集成指引示例</h1>
  	<div id="chapters">
	<h5>目前仅提供J2EE SDK及示例，其他语言的系统SSO集成，需参考其他章节说明。</h5>
</div>	
</div>

  <div id="chapters">
  	<ol>
  		<li><a href="#001">J2EE SDK应用集成指引示例</a>
  				<ol>
			    	<li><a href="#002">请求OAuth认证</a></li>
			    	<li><a href="#003">获取OAuth2.0 Access Token</a></li>
			        <li><a href="#004">获取用户信息</a></li>
			    </ol>
  		
  		</li>
  		<li><a href="#005">SDK应用集成指引示例</a></li>
  		<li><a href="#006">SDK & DEMO 下载</a></li>
  	</ol>
  	
  
  </div>

    <div id="chapters">
    	<h5><a name="002"></a>1.1请求OAuth认证</h5>
    	<div>
	    	<p>
	    		 如果应用的SSO认证地址跳转到https://login.connsec.com，则为通过LandingPage登录,点击LandingPage中的应用图标，系统自动请求OAuth登录应用;否则认证完成直接登录应用.
	    	 </p>
	    	 <pre  class="brush: java;">
    	//直接登录应示例代码如下：
		String clientId = yourClientId;
		String clientSerect = yourClientSercet;
		String callback = yourAppCallBackUrl;
	
		//存放OAuth认证所需参数的对象
		OAuthService oAuthService = new OAuthService(clientId,clientSerect,callback);
		
		//OAuth认证请求地址
		String authorizationUrl = oAuthService.getAuthorizationUrl();
    	</pre>
    	<p>
    		其中authorizationUrl的值为：<br>
	https://login.connsec.com/sec/oauth/authorize?client_id=yourClientId&grant_type=authorization_code&redirect_uri=yourAppCallBackUrl
    	</p>
	    </div>
    </div>
    
    <div id="chapters">
    	<h5><a name="003"></a>1.2获取OAuth2.0 Access Token</h5>
    	<div>
    	<p>
    	OAuth认证成功后，OAuth会将用户请求重定向到应用APP提供的回调地址，同时会在回调地址中附加一个参数，参数名为code，之后APP应用系统可接受code的值，并由该值发送获取Access Token的请求至OAuth服务端，请求地址为</p>
    	<p>https://login.connsec.com/sec/oauth/access_token?client_id=yourClientId&client_secret=yourClientSercet&grant_type=authorization_code&redirect_uri=yourAppCallBackUrl&code=CODE，OAuth服务端会判断传递的code是否合法，
    	若合法则会返回一	个Access Token给应用系统，其中Access Token中包含的属性值有：token的值，token有效剩余时间
    	</p>
    	</div>
    	<pre  class="brush: java;">
    	//refresh_token的值，示例代码如下：
		//获取OAuthService对象
		OAuthService oAuthService = session.getAttribute(“oAuthService”);
		//获取服务端返回的code
		String code = request.getParameter("code");		
		//通过code换取token
		Token accessToken = oAuthService.getAccessToken(code);
    	</pre>
    </div>
    
      <div id="chapters">
      	<h5><a name="004"></a>1.3获取用户信息</h5>
      	<pre  class="brush: java;">
		//获取登录用户的基本信息	    
		UserInfo  userInfo=null;
		try {
		//oauth/userinfo接口
		userInfo = new UserInfo (accessToken);
		userInfo.requestUserInfo();
		} catch(OAuthApiException) {
		//异常处理
		}
      	</pre>
      
      </div>
    
    <div id="chapters">
     <h5><a name="006"></a>3.SDK & DEMO 下载</h5>
     	
  		<div>JAVA</div>
       <a href=path+"/sdk/java/oauth2-sdk.zip">JAVA SDK</a><BR>	
	   <a href=path+"/sdk/java/oauth2-demo.war"> JEE OAuth DEMO</a><BR>	
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
