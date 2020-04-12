package org.maxkey.crypto.password.opt.impl.sms.netease;

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
import org.maxkey.crypto.password.opt.impl.SmsOtpAuthn;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 网易云信的短信验证.
 * @author shimingxy
 *
 */

public class SmsOtpAuthnYunxin extends SmsOtpAuthn {
    public SmsOtpAuthnYunxin(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        // TODO Auto-generated constructor stub
    }

    //发送验证码的请求路径URL
    private static final String
            SERVER_URL = "https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String
            APP_KEY = "94395d754eb55693043f5d6a2b772ef3";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET = "05d5485357bc";
    // 随机数
    private static final String NONCE = "123456";
    // 短信模板ID
    private static final String TEMPLATEID = "14850150";
    // 手机号
    private static final String MOBILE = "15618726256";
    // 验证码长度，范围4～10，默认为4
    private static final String CODELEN = "6";
    
    /**
     * .
     * @param args String
     * @throws Exception e
     */
    public static void sendSms(String[] args) throws Exception {
        
        HttpPost httpPost = new HttpPost(SERVER_URL);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        /*
         * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
         */
        String checkSum = SmsOtpAuthnYunxinCheckSumBuilder
                .getCheckSum(APP_SECRET, NONCE, curTime);

        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        /*
         * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
         * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
         * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
         */
        //https://api.netease.im/sms/sendcode.action
        nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
        nvps.add(new BasicNameValuePair("mobile", MOBILE));
        nvps.add(new BasicNameValuePair("codeLen", CODELEN));
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
        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //{"code":200,"msg":"1","obj":"740673"}

    }
    
    public static void main(String[] args) throws Exception {
        sendSms(null);
    }
    
}
