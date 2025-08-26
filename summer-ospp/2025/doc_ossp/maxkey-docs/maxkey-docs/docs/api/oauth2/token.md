---
title: 获取AccessToken接口
sidebar_position: 3
---

## OAuth 2.0 获取AccessToken接口


**接口地址**:`/sign/authz/oauth/v20/token`


**请求方式**:`GET` `POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>传递参数token等</p>



**请求参数**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|parameters|parameters|query|true||object|


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|OAuth2AccessToken|
|401|Unauthorized||
|403|Forbidden||
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|additionalInformation||object||
|expiration||string(date-time)|string(date-time)|
|expired||boolean||
|expiresIn||integer(int32)|integer(int32)|
|oauth2Exception||OAuth2Exception|OAuth2Exception|
|&emsp;&emsp;additionalInformation||object||
|&emsp;&emsp;cause||Throwable|Throwable|
|&emsp;&emsp;&emsp;&emsp;cause||Throwable|Throwable|
|&emsp;&emsp;&emsp;&emsp;localizedMessage||string||
|&emsp;&emsp;&emsp;&emsp;message||string||
|&emsp;&emsp;&emsp;&emsp;stackTrace||array|StackTraceElement|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;className||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;fileName||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;lineNumber||integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;methodName||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;nativeMethod||boolean||
|&emsp;&emsp;&emsp;&emsp;suppressed||array|Throwable|
|&emsp;&emsp;httpErrorCode||integer(int32)||
|&emsp;&emsp;localizedMessage||string||
|&emsp;&emsp;message||string||
|&emsp;&emsp;oauth2ErrorCode||string||
|&emsp;&emsp;stackTrace||array|StackTraceElement|
|&emsp;&emsp;&emsp;&emsp;className||string||
|&emsp;&emsp;&emsp;&emsp;fileName||string||
|&emsp;&emsp;&emsp;&emsp;lineNumber||integer||
|&emsp;&emsp;&emsp;&emsp;methodName||string||
|&emsp;&emsp;&emsp;&emsp;nativeMethod||boolean||
|&emsp;&emsp;summary||string||
|&emsp;&emsp;suppressed||array|Throwable|
|&emsp;&emsp;&emsp;&emsp;cause||Throwable|Throwable|
|&emsp;&emsp;&emsp;&emsp;localizedMessage||string||
|&emsp;&emsp;&emsp;&emsp;message||string||
|&emsp;&emsp;&emsp;&emsp;stackTrace||array|StackTraceElement|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;className||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;fileName||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;lineNumber||integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;methodName||string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;nativeMethod||boolean||
|&emsp;&emsp;&emsp;&emsp;suppressed||array|Throwable|
|refreshToken||OAuth2RefreshToken|OAuth2RefreshToken|
|scope||array||
|tokenType||string||
|value||string||


**响应示例**:
```javascript
{
	"additionalInformation": {},
	"expiration": "",
	"expired": true,
	"expiresIn": 0,
	"oauth2Exception": {
		"additionalInformation": {},
		"cause": {
			"cause": {
				"cause": {},
				"localizedMessage": "",
				"message": "",
				"stackTrace": [
					{
						"className": "",
						"fileName": "",
						"lineNumber": 0,
						"methodName": "",
						"nativeMethod": true
					}
				],
				"suppressed": [
					{}
				]
			},
			"localizedMessage": "",
			"message": "",
			"stackTrace": [
				{
					"className": "",
					"fileName": "",
					"lineNumber": 0,
					"methodName": "",
					"nativeMethod": true
				}
			],
			"suppressed": [
				{}
			]
		},
		"httpErrorCode": 0,
		"localizedMessage": "",
		"message": "",
		"oauth2ErrorCode": "",
		"stackTrace": [
			{
				"className": "",
				"fileName": "",
				"lineNumber": 0,
				"methodName": "",
				"nativeMethod": true
			}
		],
		"summary": "",
		"suppressed": [
			{}
		]
	},
	"refreshToken": {},
	"scope": [],
	"tokenType": "",
	"value": ""
}
```