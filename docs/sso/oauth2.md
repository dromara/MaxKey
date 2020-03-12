<h2>OAuth2应用集成</h2>
本文介绍OAuth2应用如何与MaxKey进行集成。

<h2>认证流程</h2>
采用Authorization Code获取Access Token的授权验证流程又被称为Web Server Flow，适用于所有Server端的应用。其调用流程示意图如下：

<img src="{{ "/images/sso/sso_oauth.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

对于应用而言，其流程由获取Authorization Code和通过Authorization Code获取Access Token这2步组成。

1.引导需要授权的用户到如下地址：
<pre class="prettyprint">
https://sso.maxkey.org/maxkey/oauth/v20/authorize?client_id=YOUR_CLIENT_ID&response_type=code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI 
</pre>

2.页面跳转至 
<pre class="prettyprint">
YOUR_REGISTERED_REDIRECT_URI/?code=CODEsss
</pre>

3.换取Access Token
<pre class="prettyprint">
https://sso.maxkey.org/maxkey/oauth/v20/token?client_id=YOUR_CLIENT_ID&client_secret=YOUR _SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE
</pre>

返回值
<pre><code class="json hljs">        
{ "access_token":"SlAV32hkKG", "remind_in ":3600, "expires_in":3600 }
</code></pre>

<h2>应用注册</h2>
应用在MaxKey管理系统进行注册，注册的配置信息如下

<img src="{{ "/images/sso/sso_oauth_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h2>API接口标准</h2>
   
 <table border="0" class="table table-striped table-bordered ">
		 <tr>
			<th> <strong>接口 </strong> </th>
			<th> <strong>说明 </strong> </th>
			<th> <strong>详细说明 </strong> </th>
			<th> <strong>调用方法 </strong> </th>
		  </tr>
		  <tr>
			<td> /oauth/v20/authorize </td>
			<td> 请求用户授权Token </td>
			<td> https://sso.maxkey.org/maxkey接收app sso认证请求,<br>client_id为需要认证的应用的id;</td>
			<td> APP </td>
		  </tr>
		  <tr>
			<td> /oauth/v20/token </td>
			<td> 获取授权过的 Access Token </td>
			<td> 后台应用获取 tokencode ，调用接口进行 tokencode 校验；<br>校验成功获取访问 token </td>
			<td> APP </td>
		  </tr>
		  <tr>
			<td> /api/oauth/v20/me </td>
			<td> 授权用户信息查询接口 </td>
			<td> 通过访问 token 获取登录用户信息 </td>
			<td> APP </td>
		  </tr>		  
 </table>
 
<h4>1)/oauth/v20/authorize接口</h4>
请求用户授权Token

<table border="0" class="table table-striped table-bordered ">
   <tr>
	<th> 接口名称 </th>
	<th> 请求用户授权Token </th>
  </tr>
  <tr>
	<td> url </td>
	<td> https://sso.maxkey.org/maxkey/oauth/v20/authorize</td>
  </tr>
  <tr>
	<td> 请求方式 </td>
	<td> http get/post </td>
  </tr>
 </table>
 	 
<h5>请求参数</h5>

 <table border="0" class="table table-striped table-bordered ">
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
		http://app.maxkey.org/app/callback?tokencode =PQ7q7W91a-oMsCeLvIaQm6bTrgtp7
		</td>
  
  </tr>
  <tr>
		<td>tokencode</td>
		<td>用于调用oauth/token，接口获取授权后的访问token。</td>
  </tr>
 </table> 	

<h4>2 /oauth/v20/token接口</h4>

通过/oauth/v20/token用tokencode换取访问token

 <table border="0" class="table table-striped table-bordered ">
   <tr>
	<th> 接口名称 </th>
	<th> token 接口 </th>
  </tr>
  <tr>
	<td> url </td>
	<td> https://sso.maxkey.org/maxkey/oauth/v20/token </td>
  </tr>
  <tr>
	<td> 请求方式 </td>
	<td> http get/post </td>
  </tr>
 </table>
 	 
 <h5>请求参数</h5>
 <table border="0" class="table table-striped table-bordered ">
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
	<td>调用/oauth/v20/authorize获得的tokencode值。</td>
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
<pre><code class="http hljs"> 
The actual request might look like:
POST /oauth/v20/token token HTTP/1.1
Host: sso.maxkey.org/openapi
Content-Type: application/x-www-form-urlencoded
tokencode= PQ7q7W91a-oMsCeLvIaQm6bTrgtp7&
client_id=QPKKKSADFUP876&
client_secret={client_secret}&
redirect_uri=http://app.maxkey.org/app/callback
</code></pre>
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
		成功返回JSON数据，如下：
<pre><code class="json hljs">		
{
access_token  :  "token_id",
id_token      :  "id_token"
…
}
</code></pre>
		</td>
  </tr>
 </table> 	

<h4>3)用户属性接口/api/oauth/v20/me</h4>

<table  border="0" class="table table-striped table-bordered ">
 	   <tr>
	    <th> 接口名称 </th>
	    <th> token 接口 </th>
  	  </tr>
	  <tr>
	    <td> url </td>
	    <td>https://sso.maxkey.org/maxkey/api/oauth/v20/me</td>
	  </tr>
	  <tr>
	    <td> 请求方式 </td>
	    <td> http get/post </td>
	  </tr>
</table>
 	 
<h5>请求参数</h5>

<table  border="0" class="table table-striped table-bordered ">
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
<pre><code class="http hljs"> 
POST /oauth/ userinfo HTTP/1.1
Host: sso.maxkey.org/openapi
Content-Type: application/x-www-form-urlencoded
access_token= PQ7q7W91a-oMsCeLvIaQm6bTrgtp7
</code></pre>
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
<pre><code class="json hljs"> 
{
userid     :  “zhangs”,
				…
}</code></pre>
<br/>
zhangs是认证的用户ID
	  		</td>
	  </tr>
 	 </table> 	



OAuth认证接口属性列表

<table   border="0" class="table table-striped table-bordered ">
   <tr >
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


<h2>OAuth2.0 错误码</h2>

MaxKey OAuth2.0实现中，授权服务器在接收到验证授权请求时，会按照OAuth2.0协议对本请求的请求头部、请求参数进行检验，若请求不合法或验证未通过，授权服务器会返回相应的错误信息，包含以下几个参数：

error: 错误码

error_description: 错误的描述信息



错误信息的返回方式有两种：

当请求授权Endpoint：https://sso.maxkey.org/maxkey/oauth/v20/authorize 时出现错误，返回方式是：跳转到redirect_uri,并在uri 的query parameter中附带错误的描述信息。

当请求access token endpoint:https://sso.maxkey.org/maxkey/oauth/v20/token 时出现错误，返回方式：返回JSON文本。

例如：
<pre><code class="json hljs"> 
{
	"error":"unsupported_response_type",
	"error_description":"不支持的 ResponseType."
}
</code></pre>

OAuth2.0错误响应中的错误码定义如下表所示：

 <table  border="0" class="table table-striped table-bordered ">
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
 
<h2>OAuth2客户端集成</h2>

本文使用JAVA WEB程序为例

jar包依赖如下

https://github.com/shimingxy/MaxKey-Demo/tree/master/maxkey-demo-oauth/lib

认证跳转

https://github.com/shimingxy/MaxKey-Demo/blob/master/maxkey-demo-oauth/src/main/webapp/oauth20index.jsp

获取令牌及用户信息

https://github.com/shimingxy/MaxKey-Demo/blob/master/maxkey-demo-oauth/src/main/webapp/oauth20callback.jsp