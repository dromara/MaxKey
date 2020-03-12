<h2>CAS应用集成</h2>
本文介绍CAS应用如何与MaxKey进行集成。

<h2>应用注册</h2>

应用在MaxKey管理系统进行注册，注册的配置信息如下

<img src="{{ "/images/sso/sso_cas_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>


<h2>CAS客户端配置</h2>

本文使用JAVA WEB程序为例

jar包依赖如下

cas-client-core-3.2.1.jar

commons-codec-1.4.jar

commons-logging-1.1.1.jar

slf4j-api-1.5.11.jar

web.xml配置

<pre><code class="xml hljs">  
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
	version="2.5"&gt;
	&lt;display-name&gt;&lt;/display-name&gt;
	&lt;listener&gt;
		&lt;listener-class&gt;org.jasig.cas.client.session.SingleSignOutHttpSessionListener&lt;/listener-class&gt;
	&lt;/listener&gt;
	&lt;filter&gt;
		&lt;filter-name&gt;CAS Single Sign Out Filter&lt;/filter-name&gt;
		&lt;filter-class&gt;org.jasig.cas.client.session.SingleSignOutFilter&lt;/filter-class&gt;
	&lt;/filter&gt;
	&lt;filter-mapping&gt;
		&lt;filter-name&gt;CAS Single Sign Out Filter&lt;/filter-name&gt;
		&lt;url-pattern&gt;/index.jsp&lt;/url-pattern&gt;
	&lt;/filter-mapping&gt;
	&lt;filter&gt;
		&lt;filter-name&gt;CAS Filter&lt;/filter-name&gt;
		&lt;filter-class&gt;org.jasig.cas.client.authentication.AuthenticationFilter&lt;/filter-class&gt;
		&lt;!-- cas server login url --&gt;
		&lt;init-param&gt;
			&lt;param-name&gt;casServerLoginUrl&lt;/param-name&gt;
			&lt;param-value&gt;&gt;https://sso.maxkey.org/maxkey/authz/cas/&lt;/param-value&gt;
		&lt;/init-param&gt;
		&lt;!-- cas client url, in end of url / is required --&gt;
		&lt;init-param&gt;
			&lt;param-name&gt;serverName&lt;/param-name&gt;
			&lt;param-value&gt;http://cas.demo.maxkey.org:8080/&lt;/param-value&gt;
		&lt;/init-param&gt;
	&lt;/filter&gt;
	&lt;filter-mapping&gt;
		&lt;filter-name&gt;CAS Filter&lt;/filter-name&gt;
		&lt;url-pattern&gt;/index.jsp&lt;/url-pattern&gt;
	&lt;/filter-mapping&gt;

	&lt;!-- Cas10TicketValidationFilter Cas20ProxyReceivingTicketValidationFilter --&gt;
	&lt;filter&gt;
		&lt;filter-name&gt;CAS Validation Filter&lt;/filter-name&gt;
		&lt;filter-class&gt;org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter&lt;/filter-class&gt;
		&lt;!-- cas server login url --&gt;
		&lt;init-param&gt;
			&lt;param-name&gt;casServerUrlPrefix&lt;/param-name&gt;
			&lt;param-value&gt;https://sso.maxkey.org/maxkey/authz/cas/&lt;/param-value&gt;
		&lt;/init-param&gt;
		&lt;!-- cas client url --&gt;
		&lt;init-param&gt;
			&lt;param-name&gt;serverName&lt;/param-name&gt;
			&lt;param-value&gt;http://cas.demo.maxkey.org:8080/&lt;/param-value&gt;
		&lt;/init-param&gt;
	&lt;/filter&gt;
	&lt;filter-mapping&gt;
		&lt;filter-name&gt;CAS Validation Filter&lt;/filter-name&gt;
		&lt;url-pattern&gt;/index.jsp&lt;/url-pattern&gt;
	&lt;/filter-mapping&gt;
	&lt;filter&gt;
		&lt;filter-name&gt;CAS HttpServletRequest Wrapper Filter&lt;/filter-name&gt;
		&lt;filter-class&gt;
			org.jasig.cas.client.util.HttpServletRequestWrapperFilter
		&lt;/filter-class&gt;
	&lt;/filter&gt;
	&lt;filter-mapping&gt;
		&lt;filter-name&gt;CAS HttpServletRequest Wrapper Filter&lt;/filter-name&gt;
		&lt;url-pattern&gt;/index.jsp&lt;/url-pattern&gt;
	&lt;/filter-mapping&gt;
	&lt;filter&gt;
		&lt;filter-name&gt;CAS Assertion Thread Local Filter&lt;/filter-name&gt;
		&lt;filter-class&gt;org.jasig.cas.client.util.AssertionThreadLocalFilter&lt;/filter-class&gt;
	&lt;/filter&gt;
	&lt;filter-mapping&gt;
		&lt;filter-name&gt;CAS Assertion Thread Local Filter&lt;/filter-name&gt;
		&lt;url-pattern&gt;/index.jsp&lt;/url-pattern&gt;
	&lt;/filter-mapping&gt;
	&lt;welcome-file-list&gt;
		&lt;welcome-file&gt;index.jsp&lt;/welcome-file&gt;
	&lt;/welcome-file-list&gt;
&lt;/web-app&gt;
</code></pre>

JSP实现Code

<pre><code class="jsp hljs"> 
&lt;%@ page language="java" import="java.util.*" pageEncoding="utf-8"%&gt;
&lt;%@ page language="java" import="java.util.Map.Entry" %&gt;
&lt;%@ page language="java" import="org.apache.commons.codec.binary.Base64" %&gt;
&lt;%@ page language="java" import="org.jasig.cas.client.authentication.AttributePrincipal" %&gt;
&lt;%@ page language="java" import="org.jasig.cas.client.validation.Assertion" %&gt;
&lt;%@ page language="java" import="org.jasig.cas.client.util.AbstractCasFilter" %&gt;
&lt;%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	System.out.println("CAS Assertion Success . ");
	Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
	                
	                
	String username=     assertion.getPrincipal().getName();
%&gt;

&lt;!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"&gt;
&lt;html&gt;
  &lt;head&gt;
    &lt;base href="&lt;%=basePath%&gt;"&gt;
    
    &lt;title&gt;Demo CAS&lt;/title&gt;
	&lt;meta http-equiv="pragma" content="no-cache"&gt;
	&lt;meta http-equiv="cache-control" content="no-cache"&gt;
	&lt;meta http-equiv="expires" content="0"&gt;    
	&lt;meta http-equiv="keywords" content="keyword1,keyword2,keyword3"&gt;
	&lt;meta http-equiv="description" content="CAS Demo"&gt;
	&lt;link rel="shortcut icon" type="image/x-icon" href="&lt;%=basePath %&gt;/images/favicon.ico"/&gt;
	&lt;!--
	&lt;link rel="stylesheet" type="text/css" href="styles.css"&gt;
	--&gt;
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
	  				&lt;td colspan="2" class="title"&gt;CAS Demo for MaxKey&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;CAS Logo&lt;/td&gt;
	  				&lt;td&gt; &lt;img src="&lt;%=basePath %&gt;/images/cas.png"/&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td width="50%"&gt;CAS Assertion&lt;/td&gt;
	  				&lt;td&gt;&lt;%=username %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;CAS Has Attributes &lt;/td&gt;
	  				&lt;td&gt;&lt;%=!assertion.getPrincipal().getAttributes().isEmpty() %&gt; size : &lt;%=assertion.getPrincipal().getAttributes().size() %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;%
		  			Map&lt;String, Object&gt; attMap = assertion.getPrincipal().getAttributes();  
		            for (Entry&lt;String, Object&gt; entry : attMap.entrySet()) {   
		            	String attributeValue=entry.getValue()==null?"":entry.getValue().toString();
		            	System.out.println("attributeValue : "+attributeValue);
		            	if(attributeValue.startsWith("base64:")){
		            		attributeValue=new String(Base64.decodeBase64(attributeValue.substring("base64:".length())),"UTF-8");
		            	}
		        %&gt;
	  			&lt;tr&gt;
	  				&lt;td&gt;CAS &lt;%=entry.getKey() %&gt; &lt;/td&gt;
	  				&lt;td&gt;&lt;%=attributeValue %&gt;&lt;/td&gt;
	  			&lt;/tr&gt;
	  			&lt;%}%&gt;
	  		&lt;/table&gt;
  		&lt;/div&gt;
  &lt;/body&gt;
&lt;/html&gt;
</code></pre>