package org.maxkey.crypto.password.opt.impl.sms;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import java.io.IOException;

import org.maxkey.crypto.password.opt.impl.SmsOtpAuthn;
import org.maxkey.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 腾讯云短信验证.
 * @author shimingxy
 *
 */
public class SmsOtpAuthnTencentCloud extends SmsOtpAuthn {
    private static final  Logger logger = LoggerFactory.getLogger(SmsOtpAuthnTencentCloud.class);
 
    //
    String secretId;
    //
    String secretKey;
    //短信SDKAPPID
    String smsSdkAppid;
    //短信模板
    String templateId;
    //签名
    String sign;
    
    public SmsOtpAuthnTencentCloud() {
        optType = OptTypes.SMS;
    }
    

    
    
    @Override
    public boolean produce(UserInfo userInfo) {
        // 手机号
        String mobile = userInfo.getMobile();
        if (mobile != null && !mobile.equals("")) {
            try {
                Credential cred = new Credential(secretId, secretKey);
                
                HttpProfile httpProfile = new HttpProfile();
                httpProfile.setEndpoint("sms.tencentcloudapi.com");

                ClientProfile clientProfile = new ClientProfile();
                clientProfile.setHttpProfile(httpProfile);
                
                SmsClient client = new SmsClient(cred, "ap-beijing", clientProfile);
                String token = this.genToken(userInfo);
                String params = "{\"PhoneNumberSet\":[\"" + mobile + "\"]," 
                        + "\"TemplateID\":\"" + templateId + "\",\"Sign\":\"" + sign + "\","
                        + "\"TemplateParamSet\":[\"" + token + "\",\"" + this.interval + "\"],"
                        + "\"SmsSdkAppid\":\"" + smsSdkAppid + "\"}";
                
                SendSmsRequest req = SendSmsRequest.fromJsonString(params, SendSmsRequest.class);
                
                SendSmsResponse resp = client.SendSms(req);
                
                logger.debug("responseString " + SendSmsRequest.toJsonString(resp));
                if (resp.getSendStatusSet()[0].getCode().equalsIgnoreCase("Ok")) {
                    this.optTokenStore.store(
                            userInfo, 
                            token, 
                            userInfo.getMobile(), 
                            OptTypes.SMS);
                    return true;
                }
               
            } catch  (Exception e) {
                logger.error(" produce code error ", e);
            }
        }
        return false;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        return this.optTokenStore.validate(userInfo, token, OptTypes.SMS, interval);
    }


    public String getSecretId() {
        return secretId;
    }




    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }




    public String getSecretKey() {
        return secretKey;
    }




    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }




    public String getSmsSdkAppid() {
        return smsSdkAppid;
    }




    public void setSmsSdkAppid(String smsSdkAppid) {
        this.smsSdkAppid = smsSdkAppid;
    }




    public String getTemplateId() {
        return templateId;
    }




    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }




    public String getSign() {
        return sign;
    }




    public void setSign(String sign) {
        this.sign = sign;
    }
    
    @Override
    public void initPropertys() {
        try {
            this.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.secretId = this.properties.getProperty("config.otp.sms.tencentcloud.secretid");
        this.secretKey = this.properties.getProperty("config.otp.sms.tencentcloud.secretkey");
        this.smsSdkAppid = this.properties.getProperty("config.otp.sms.tencentcloud.smssdkappid");
        this.templateId = this.properties.getProperty("config.otp.sms.tencentcloud.templateid");
        this.sign = this.properties.getProperty("config.otp.sms.tencentcloud.sign");
    }
    
}
