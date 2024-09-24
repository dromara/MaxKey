/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey.password.sms.impl;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.sms.SmsOtpAuthn;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.util.StringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网易云信短信验证.
 * @author shimingxy
 *
 */
public class SmsOtpAuthnYunxin extends SmsOtpAuthn {
    private static final  Logger logger = LoggerFactory.getLogger(SmsOtpAuthnYunxin.class);
    
    public SmsOtpAuthnYunxin() {
        otpType = OtpTypes.SMS;
    }

    public SmsOtpAuthnYunxin(String appKey, String appSecret, String templateId) {
    	otpType = OtpTypes.SMS;
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.templateId = templateId;
	}

	//发送验证码的请求路径URL
    private static final String
            SERVER_URL = "https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private   String
            appKey = "94395d754eb55693043f5d6a2b772ef3";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private  String appSecret = "05d5485357bc";
    // 短信模板ID
    private  String templateId = "14860099";
    
    
    @Override
    public boolean produce(UserInfo userInfo) {
        HttpPost httpPost = null;
        // 手机号
        String mobile = userInfo.getMobile();
        if (mobile != null && !mobile.equals("")) {
            try {
                httpPost = new HttpPost(SERVER_URL);
                String curTime = String.valueOf((new Date()).getTime() / 1000L);
                /*
                 * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
                 */
                // 随机数
                String nonce = new StringGenerator(
                        StringGenerator.DEFAULT_CODE_NUMBER,
                        6
                    ).randomGenerate();
                String checkSum = SmsOtpAuthnYunxinCheckSumBuilder
                        .getCheckSum(appSecret, nonce, curTime);
                logger.debug("AppKey " +appKey+" ,Nonce "+nonce+", CurTime "+curTime+" ,checkSum "+checkSum);
                // 设置请求的header
                httpPost.addHeader("AppKey", appKey);
                httpPost.addHeader("Nonce", nonce);
                httpPost.addHeader("CurTime", curTime);
                httpPost.addHeader("CheckSum", checkSum);
                httpPost.addHeader("Content-Type", 
                        "application/x-www-form-urlencoded;charset=utf-8");
        
                // 设置请求的的参数，requestBody参数
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                /*
                 * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
                 * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
                 * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
                 */
                //https://api.netease.im/sms/sendcode.action
                nvps.add(new BasicNameValuePair("templateid", templateId));
                nvps.add(new BasicNameValuePair("mobile", userInfo.getMobile()));
                nvps.add(new BasicNameValuePair("codeLen", digits + ""));
                //authCode 用户自定义验证码
                //nvps.add(new BasicNameValuePair("authCode", ""));
                //https://api.netease.im/sms/verifycode.action
                //nvps.add(new BasicNameValuePair("code", "123456"));
                
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
                HttpClient httpClient = HttpClientBuilder.create().build();
                // 执行请求
                HttpResponse response = httpClient.execute(httpPost);
                /*
                 * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
                 * 2.具体的code有问题的可以参考官网的Code状态表
                 */
                //{"code":200,"msg":"1","obj":"740673"}
                String responseString = EntityUtils.toString(response.getEntity(), "utf-8");
                //String responseString = "{\"code\":200,\"msg\":\"1\",\"obj\":\"740673\"}";
                logger.debug("responseString " + responseString);
                YunxinSms  yunxinSms = 
                        JsonUtils.gsonStringToObject(responseString,YunxinSms.class);
                logger.debug("responseEntity code " + yunxinSms.getObj());
                nonce = yunxinSms.getObj() == null ?nonce:yunxinSms.getObj();
                logger.debug("nonce " + nonce);
                this.optTokenStore.store(
                                        userInfo, 
                                        nonce, 
                                        userInfo.getMobile(), 
                                        OtpTypes.SMS);
                return true;
            } catch  (Exception e) {
                logger.error(" produce code error ", e);
            } finally {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
            }
        }
        return false;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        return this.optTokenStore.validate(userInfo, token, OtpTypes.SMS, interval);
    }

    
    
    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }



    public class YunxinSms {
        int code;
        String msg;
        String obj;
        
        public YunxinSms() {
        }

        public int getCode() {
            return code;
        }
        
        public void setCode(int code) {
            this.code = code;
        }
        
        public String getMsg() {
            return msg;
        }
        
        public void setMsg(String msg) {
            this.msg = msg;
        }
        
        public String getObj() {
            return obj;
        }
        
        public void setObj(String obj) {
            this.obj = obj;
        }

        
    }


    /**
     * main.
     * @param args String
     * @throws Exception throws
     */
    public static void main(String[] args) throws Exception {
        String nonce = new StringGenerator(
                StringGenerator.DEFAULT_CODE_NUMBER,
                6
            ).randomGenerate();
        System.out.println(nonce);
        String mapJson = "{\"code\":200,\"msg\":\"1\",\"obj\":\"740673\"}";
        YunxinSms  yunxinSms = JsonUtils.gsonStringToObject(mapJson,YunxinSms.class);  
        System.out.println("code " + yunxinSms.getObj());
    }
    
}

class SmsOtpAuthnYunxinCheckSumBuilder {
    // 计算并获取CheckSum
    public static String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }

    // 计算并获取md5值
    public static String getMD5(String requestBody) {
        return encode("md5", requestBody);
    }

    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest
                    = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}
