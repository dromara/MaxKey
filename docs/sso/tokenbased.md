<h2>TokenBased应用集成</h2>
本文介绍TokenBased应用如何与MaxKey进行集成。

<h2>应用注册</h2>

应用在MaxKey管理系统进行注册，注册的配置信息如下

<img src="{{ "/images/sso/sso_token_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

LTPA使用Cookie传输令牌

<img src="{{ "/images/sso/sso_token_ltpa_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>


<h2>TokenBased客户端集成</h2>

本文使用JAVA WEB程序为例

jar包依赖如下

commons-codec-1.9.jar

commons-io-2.2.jar

commons-logging-1.1.1.jar

gson-2.2.4.jar

log4j-1.2.17.jar

maxkey-client-sdk.jar


JSP实现Code

<h4>简单令牌</h4>

<pre><code class="jsp hljs"> 
&lt;%@ page language="java" import="java.util.*" pageEncoding="utf-8"%&gt;
&lt;%@ page language="java" import="org.maxkey.client.tokenbase.*"%&gt;
&lt;%@ page language="java" import="org.maxkey.client.crypto.*"%&gt;
&lt;%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String token =request.getParameter("token");
System.out.println("token : "+token);
String tokenString=TokenUtils.decode(token, "x8zPbCya", ReciprocalUtils.Algorithm.DES);
String parseToken[]=TokenUtils.parseSimpleBasedToken(tokenString);
%&gt;
&lt;!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"&gt;
&lt;html&gt;
  &lt;head&gt;
    &lt;base href="&lt;%=basePath%&gt;"&gt;
    
   &lt;title&gt;SimpleBasedToken Demo&lt;/title&gt;
	&lt;meta http-equiv="pragma" content="no-cache"&gt;
	&lt;meta http-equiv="cache-control" content="no-cache"&gt;
	&lt;meta http-equiv="expires" content="0"&gt;    
	&lt;meta http-equiv="keywords" content="keyword1,keyword2,keyword3"&gt;
	&lt;meta http-equiv="description" content="SimpleBasedToken Demo"&gt;
	&lt;link rel="shortcut icon" type="image/x-icon" href="&lt;%=basePath %&gt;/images/favicon.ico"/&gt;
	
	&lt;style type="text/css"&gt;
		body{
			margin: 0;
			margin-top: 0px;
			margin-left: auto;
			margin-right: auto;
			padding: 0 0 0 0px;
			font-size: 12px;
			text-align:center;
			float:center;
			font-family: "Arial", "Helvetica", "Verdana", "sans-serif";
		}
		.container {
			width: 990px;
			margin-left: auto;
			margin-right: auto;
			padding: 0 10px
		}
		table.datatable {
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			width: 100%;
		}
		
		table.datatable th{
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			height: 40px;
		}
		
		
		table.datatable td{
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			height: 40px;
		}
		
		table.datatable td.title{
			text-align: center;
			font-size: 20px;
			font-weight: bold;
		}
	&lt;/style&gt;
  &lt;/head&gt;
  
  &lt;body&gt;
  		&lt;div class="container"&gt;
	  		&lt;table class="datatable"&gt;
	  			&lt;tr&gt;
	  				
	  				&lt;td colspan="2" class="title"&gt;SimpleBasedToken Demo&lt;/td&gt;
	  			&lt;/tr&gt;
	  			
	  			&lt;tr&gt;
	  				&lt;td&gt;SimpleBasedToken Logo&lt;/td&gt;
	  				&lt;td&gt; &lt;img src="&lt;%=basePath %&gt;images/simple.png" width="124px" height="124px"/&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;UserName&lt;/td&gt;
	  				&lt;td&gt;&lt;%=parseToken[0]%&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;Authentication at Time&lt;/td&gt;
	  				&lt;td&gt;&lt;%=parseToken[1]%&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  		&lt;/table&gt;
  		&lt;/div&gt;
  &lt;/body&gt;
&lt;/html&gt;
</code></pre>


<h4>基于JSON令牌</h4>

<pre><code class="jsp hljs">
&lt;%@ page language="java" import="java.util.*" pageEncoding="utf-8"%&gt;
&lt;%@ page language="java" import="org.maxkey.client.tokenbase.*"%&gt;
&lt;%@ page language="java" import="org.maxkey.client.crypto.*"%&gt;
&lt;%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String token =request.getParameter("token");
System.out.println("token : "+token);
String tokenString=TokenUtils.decode(token, "lEWhDLTo", ReciprocalUtils.Algorithm.DES);
Map tokenMap=TokenUtils.parseJsonBasedToken(tokenString);

%&gt;
&lt;!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"&gt;
&lt;html&gt;
  &lt;head&gt;
    &lt;base href="&lt;%=basePath%&gt;"&gt;
    
   &lt;title&gt;JsonBasedToken Demo&lt;/title&gt;
	&lt;meta http-equiv="pragma" content="no-cache"&gt;
	&lt;meta http-equiv="cache-control" content="no-cache"&gt;
	&lt;meta http-equiv="expires" content="0"&gt;    
	&lt;meta http-equiv="keywords" content="keyword1,keyword2,keyword3"&gt;
	&lt;meta http-equiv="description" content="JsonBasedToken Demo"&gt;
	&lt;link rel="shortcut icon" type="image/x-icon" href="&lt;%=basePath %&gt;/images/favicon.ico"/&gt;
	
	&lt;style type="text/css"&gt;
		body{
			margin: 0;
			margin-top: 0px;
			margin-left: auto;
			margin-right: auto;
			padding: 0 0 0 0px;
			font-size: 12px;
			text-align:center;
			float:center;
			font-family: "Arial", "Helvetica", "Verdana", "sans-serif";
		}
		.container {
			width: 990px;
			margin-left: auto;
			margin-right: auto;
			padding: 0 10px
		}
		table.datatable {
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			width: 100%;
		}
		
		table.datatable th{
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			height: 40px;
		}
		
		
		table.datatable td{
			border: 1px solid #d8dcdf;
			border-collapse:collapse;
			border-spacing:0;
			height: 40px;
		}
		
		table.datatable td.title{
			text-align: center;
			font-size: 20px;
			font-weight: bold;
		}
	&lt;/style&gt;
  &lt;/head&gt;
  
  &lt;body&gt;
  		&lt;div class="container"&gt;
	  		&lt;table class="datatable"&gt;
	  			&lt;tr&gt;
	  				
	  				&lt;td colspan="2" class="title"&gt;JsonBasedToken Demo&lt;/td&gt;
	  			&lt;/tr&gt;
	  			
	  			&lt;tr&gt;
	  				&lt;td&gt;JsonBasedToken Logo&lt;/td&gt;
	  				&lt;td&gt; &lt;img src="&lt;%=basePath %&gt;images/json.png" width="124px" height="124px"/&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;UID&lt;/td&gt;
	  				&lt;td&gt;&lt;%=tokenMap.get("uid") %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;UserName&lt;/td&gt;
	  				&lt;td&gt;&lt;%=tokenMap.get("username") %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;Department&lt;/td&gt;
	  				&lt;td&gt;&lt;%=tokenMap.get("department") %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;Email&lt;/td&gt;
	  				&lt;td&gt;&lt;%=tokenMap.get("email") %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;Authentication at Time&lt;/td&gt;
	  				&lt;td&gt;&lt;%=tokenMap.get("at")%&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;Expires&lt;/td&gt;
	  				&lt;td&gt;&lt;%=tokenMap.get("expires")%&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  		&lt;/table&gt;
  		&lt;/div&gt;
  &lt;/body&gt;
&lt;/html&gt;
</code></pre>