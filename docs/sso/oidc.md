<h2>OpenID Connect应用集成</h2>
本文介绍OpenID Connect应用如何与MaxKey进行集成。

<h2>认证流程</h2>

请参照OAuth2认证流程

<h2>应用注册</h2>
应用在MaxKey管理系统进行注册，注册的配置信息如下

<img src="{{ "/images/sso/sso_oidc_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h2>集成和接口</h2>
<h4>3)用户属性接口/api/connect/v10/userinfo</h4>

通过访问token 获取登录用户信息及签名信息，在程序中必须验证相关的签名信息。
 
<table  border="0" class="table table-striped table-bordered ">
 	   <tr>
	    <th> 接口名称 </th>
	    <th> OIDC授权用户信息查询接口 </th>
  	  </tr>
	  <tr>
	    <td> url </td>
	    <td>https://sso.maxkey.org/maxkey/api/connect/v10/userinfo</td>
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

其他请参照OAuth2


<h2>OAuth2客户端集成</h2>

本文使用JAVA WEB程序为例

jar包依赖如下

https://github.com/shimingxy/MaxKey-Demo/tree/master/maxkey-demo-oauth/lib

认证跳转

https://github.com/shimingxy/MaxKey-Demo/blob/master/maxkey-demo-oauth/src/main/webapp/oidc10index.jsp

获取令牌和用户信息及验证签名 (id_token及用户信息)

https://github.com/shimingxy/MaxKey-Demo/blob/master/maxkey-demo-oauth/src/main/webapp/oidc10callback.jsp