---
title: CAS 3.0 ProxyTicket代理验证接口
---

## CAS 3.0 ProxyTicket代理验证接口


**接口地址**:`/sign/authz/cas/p3/proxy`


**请求方式**:`POST`


**请求数据类型**:`application/json`


**响应数据类型**:`*/*`


**接口描述**:<p>通过ProxyGrantingTicket获取ProxyTicket</p>



**请求参数**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|pgt|pgt|query|true|string||
|targetService|targetService|query|true|string||
|format|format|query|false|string||


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