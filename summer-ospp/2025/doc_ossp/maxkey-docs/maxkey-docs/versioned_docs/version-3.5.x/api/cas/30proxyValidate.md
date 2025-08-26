---
title: CAS 3.0 ticket代理验证接口
---

## CAS 3.0 ticket代理验证接口


**接口地址**:`/sign/authz/cas/p3/proxyValidate`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>通过ProxyTicket获取当前登录用户信息</p>



**请求参数**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|service|service|query|true|string||
|ticket|ticket|query|true|string||
|format|format|query|false|string||
|pgtUrl|pgtUrl|query|false|string||
|renew|renew|query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||
|204|No Content||
|401|Unauthorized||
|403|Forbidden||


**响应参数**:


暂无


**响应示例**:
```javascript

```