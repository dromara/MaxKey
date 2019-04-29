<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/samlv20/saml1_en.jsp?language=en_US");	
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
 	<jsp:param value="authz-custom" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>定制认证集成</h1>
    <div class="text-section">
     <p class="section">定制的认证集成是为了用户能够更好的对认证系统进行扩展，对系统定义了对应的扩展接口。
     <br>
     	1、基础的协议已经提供了默认的实现方法，用户仅需配置就可以完成单点登录的功能；<br>
     	2、部分应用比较特殊，使用当前的协议无法满足其认证的功能，需要开发人员进行扩展。
     	<br>
     	备注 ：实现基于JAVA平台</p>
    </div><!-- 一段描述结束 -->

    <h3>接口说明</h3>
    <div class="text-section">
    <p class="section">
     <table class="basisTable" cellspacing="1">
    	<thead>
      		<th style="width:50px" >序号</th><th>项目</th><th>类型或者方法(英文)</th><th>备注</th>
    	</thead>
	    <tbody>
		    <tr>
		        <td>1</td>
		        <td>类名</td>
		        <td>com.connsec.web.authorize.endpoint.adapter.AbstractAuthorizeAdapter</td>
		        <td>实现类必须继承</td>
		    </tr>
		    <tr>
		        <td>2</td>
		        <td>人员信息处理方法</td>
		        <td>public abstract String generateInfo(UserInfo userInfo,Object app);</td>
		        <td>默认未实现</td>
		    </tr>
		     <tr>
		        <td>3</td>
		        <td>加密处理方法</td>
		        <td>public  String encrypt(String data,String algorithmKey,String algorithm);</td>
		        <td>默认实现</td>
		    </tr>
		    <tr>
		        <td>4</td>
		        <td>签名处理方法</td>
		        <td>public String  sign(String data,Applications app);</td>
		        <td>默认实现</td>
		    </tr>
		    <tr>
		        <td>5</td>
		        <td>认证实现处理方法</td>
		        <td>public abstract ModelAndView authorize(UserInfo userInfo,Object app,String data,ModelAndView modelAndView);</td>
		        <td>默认未实现</td>
		    </tr>
	</tbody>
	</table>
	</p>
	</div>
<h3>默认加密实现</h3>
<pre  class="brush: java;">
/**
 *data 			由generateInfo处理得到的数据
 *algorithmKey 	加密的密钥
 *algorithm    	加密的方法
 */
public  String encrypt(String data,String algorithmKey,String algorithm){
	//解密得到应有配置的加密密钥
	algorithmKey=passwordReciprocal.decoder(algorithmKey);
	log.debug("algorithm : "+algorithm);
	log.debug("algorithmKey : "+algorithmKey);
	//解决中文乱码问题，把数据转换成HEX，编码是UTF-8
	//Chinese , encode data to HEX
	try {
		data = new String(Hex.encodeHex(data.getBytes("UTF-8")));
	} catch (UnsupportedEncodingException e) { 
		e.printStackTrace();
	}
	//对data数据进行加密     
	byte[] encodeData=ReciprocalUtils.encode(data, algorithmKey, algorithm);
	//对加密完成的数据进行BASE64URL编码
	String tokenString=Base64Utils.base64UrlEncode(encodeData);
	log.trace("Reciprocal then HEX  Token : "+tokenString);
	//返回加密数据
	return tokenString;
}
</pre>
<h3>默认签名实现</h3>
<pre  class="brush: java;">
/**
 *data 			由encrypt处理得到的数据
 *app 			应用配置信息
 */
public String  sign(String data,Applications app){
    //判断应用是否配置签名
	if(BOOLEAN.isTrue(app.getIsSignature())){
	    //判断是否生成了数字证书
		if(app.getSignatureKeyStore()!=null){	
		    //对data数据进行签名			
			byte[] signature=app.sign(data);
			log.debug("signed Token : "+data);
			log.debug("signature : "+signature);
			//对数据和签名结果进行处理,格式BASE64URL(UTF8(data)).BASE64URL(signature)
			try {
				data=Base64Utils.base64UrlEncode(data.getBytes("UTF-8"))+"."+Base64Utils.base64UrlEncode(signature);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			log.debug("Token : "+data);
		}
		
	}else{
		log.debug("data not need sign .");
	}
	//返回数据
	return data;
}
</pre>
<h3>简单令牌(TokenBasedSimpleAdapter)代码实现</h3>
<pre  class="brush: java;">
package com.connsec.web.authorize.endpoint.adapter;

import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

import com.connsec.constants.BOOLEAN;
import com.connsec.domain.UserInfo;
import com.connsec.domain.apps.TokenBasedDetails;
import com.connsec.util.DateUtils;
/*
 *认证用户名@@认证时间(UTC时间),例如：testUser@2010-01-01T01:01:01.001Z
 *加密结果Y00jv2TCCuk365uB2-nDCUdboygeYFoUfETC7BNXr73dQWwFNRrfYltczDQ5iWg8NTO-GsP--VlR6L-JyNhZSg
 *签名结果eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ.dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
 */
public class TokenBasedSimpleAdapter extends AbstractAuthorizeAdapter {//继承AbstractAuthorizeAdapter
	 
	 //用户信息处理
	@Override
	public String generateInfo(UserInfo userInfo,Object app) {
		TokenBasedDetails details=(TokenBasedDetails)app;
	
		String tokenUsername="";
		
		if(BOOLEAN.isTrue(details.getUid())){
			tokenUsername=userInfo.getId();
		}else if(BOOLEAN.isTrue(details.getUsername())){
			tokenUsername= userInfo.getUsername();	
		}else if(BOOLEAN.isTrue(details.getEmail())){
			tokenUsername=userInfo.getEmail();
		}else if(BOOLEAN.isTrue(details.getWindowsAccount())){
			tokenUsername= userInfo.getWindowsAccount();
		}else if(BOOLEAN.isTrue(details.getEmployeeNumber())){
			tokenUsername=userInfo.getEmployeeNumber();
		}else if(BOOLEAN.isTrue(details.getDepartmentId())){
			tokenUsername= userInfo.getDepartmentId();
		}
		
		// use UTC date time format
		Date currentDate=new Date();
		log.debug("UTC Local current date : "+DateUtils.toUtcLocal(currentDate));
		log.debug("UTC  current Date : "+DateUtils.toUtc(currentDate));
		
		//组装数据信息，例如username= testUser,组装成testUser@2010-01-01T01:01:01.001Z
		String tokenString=tokenUsername+"@@"+DateUtils.toUtc(currentDate);
		log.debug("Token : "+tokenString);
		
		return tokenString;
	}
	
	 //使用默认加密
	@Override
	public String encrypt(String data, String algorithmKey, String algorithm) {
		return super.encrypt(data, algorithmKey, algorithm);
	}

	 //认证处理
	@Override
	public ModelAndView authorize(UserInfo userInfo, Object app, String data,ModelAndView modelAndView) {
		//jsp认证实现地址
		modelAndView.setViewName("authorize/tokenbased_sso_submint");
		//获取应用配置信息
		TokenBasedDetails details=(TokenBasedDetails)app;
		//认证提交地址
		modelAndView.addObject("action", details.getRedirectUri());
		//认证提交数据
		modelAndView.addObject("token",data);
		
		return modelAndView;
	}

}

</pre>
实现类需要打包成***.jar,部署到{app}/WEB-INF/lib下<br/>
JSP认证实现部署到{app}/WEB-INF/jsp/authorize/tokenbased_sso_submint.jsp
<h3>JSP认证代码实现</h3>
<pre  class="brush: html;">
&lt;!DOCTYPE html&gt;
&lt;%@ page 	language="java"   import="java.util.*" 	pageEncoding="UTF-8"%&gt;
&lt;%@ taglib 	prefix="authz" 	uri="http://www.springframework.org/security/tags" %&gt;
&lt;%@ taglib 	prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%&gt;
&lt;%@ taglib  prefix="s"  uri="http://www.connsec.com/tags" %&gt;
&lt;html xmlns="http://www.w3.org/1999/xhtml"&gt;
&lt;head&gt;
  &lt;meta http-equiv="Content-Type" content="text/html; charset=utf-8"/&gt;
  &lt;title&gt;Token-Based SSO Submit&lt;/title&gt;
  &lt;link rel="shortcut icon" type="image/x-icon" href="&lt;s:Base /&gt;/images/favicon.ico"/&gt;
  &lt;link type="text/css" rel="stylesheet" href="&lt;s:Base /&gt;/css/base.css"/&gt;
&lt;/head&gt;

&lt;body  onload="document.forms[0].submit()"  style="display:none"&gt;
&lt;form id="tokenbasedsubmit" name="tokenbasedsubmit" action="${action}" method="post"&gt;
		&lt;table style="width:100%"&gt;
			&lt;tr&gt;
				&lt;td&gt;token&lt;/td&gt;
				&lt;td&gt;&lt;input type="text" id="tokenbased_token" name="token" value="${token}" /&gt;&lt;/td&gt;
			&lt;/tr&gt;
			&lt;tr&gt;
				&lt;td colspan="2"&gt;&lt;input type="submit"  name="submitBtn" value="Continue..." /&gt;&lt;/td&gt;
			&lt;/tr&gt;
		&lt;/table&gt;
	&lt;/form&gt;
&lt;/body&gt;
&lt;/html&gt;
</pre>
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
