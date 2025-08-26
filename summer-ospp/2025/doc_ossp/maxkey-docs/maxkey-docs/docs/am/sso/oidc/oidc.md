---
sidebar_position: 1
---
# OpenID Connect协议集成
本文介绍OpenID Connect协议如何与MaxKey进行集成。

## 认证流程

请参照OAuth2认证流程

## 应用注册
应用在MaxKey管理系统进行注册，注册的配置信息如下

![sso_oidc_conf](/images/sso/sso_oidc_conf.png)


## 集成和接口
用户属性接口/api/connect/v10/userinfo

通过访问token 获取登录用户信息及签名信息，在程序中必须验证相关的签名信息。
 
<table  border="0" class="table table-striped table-bordered ">
 	   <tr>
	    <th> 接口名称 </th>
	    <th> OIDC授权用户信息查询接口 </th>
  	  </tr>
	  <tr>
	    <td> url </td>
	    <td>https://sso.maxkey.org/sign/api/connect/v10/userinfo</td>
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

```http
POST /oauth/userinfo HTTP/1.1
Host: sso.maxkey.org/openapi
Content-Type: application/x-www-form-urlencoded
access_token= PQ7q7W91a-oMsCeLvIaQm6bTrgtp7
```
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

```json
{
	userid     :  "zhangs"
}
```

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
