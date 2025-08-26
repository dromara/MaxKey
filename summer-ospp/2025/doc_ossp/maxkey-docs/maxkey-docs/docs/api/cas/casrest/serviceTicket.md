---
title: ServiceTicket接口
---

## CAS REST认证接口


**接口地址**:`/sign/authz/cas/v1/tickets/{ticketGrantingTicket}`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>通过TGT获取ST</p>



**请求参数**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|ticketGrantingTicket|ticketGrantingTicket|path|true|string||
|password||formData|false|string||
|service||formData|false|string||
|renew||formData|false|string||
|username||formData|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||
|201|Created||
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


暂无


**响应示例**:
```text
string
```