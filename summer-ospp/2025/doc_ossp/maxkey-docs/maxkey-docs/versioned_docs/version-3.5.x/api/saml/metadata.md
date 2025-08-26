---
title: SAML 2.0 元数据接口
sidebar_position: 4
---

## SAML 2.0 元数据接口


**接口地址**:`/sign/metadata/saml20/mxk_metadata_{appid}.xml`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/xml`


**接口描述**:<p>参数Idp_Metadata_应用ID</p>



**请求参数**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|appid|appid|path|true|string||


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
```text
string
```