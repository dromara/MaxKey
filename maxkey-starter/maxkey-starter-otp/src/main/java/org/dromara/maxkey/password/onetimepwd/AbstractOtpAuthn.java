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
 

package org.dromara.maxkey.password.onetimepwd;

import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.token.AbstractOtpTokenStore;
import org.dromara.maxkey.password.onetimepwd.token.InMemoryOtpTokenStore;
import org.dromara.maxkey.util.StringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractOTPAuthn.
 * @author Administrator
 *
 */
public abstract class AbstractOtpAuthn {
    private static final  Logger logger = LoggerFactory.getLogger(AbstractOtpAuthn.class);

    protected AbstractOtpTokenStore optTokenStore = new InMemoryOtpTokenStore();
    
    //验证码有效間隔
    protected int interval = 30;
    
    // 验证码长度，范围4～10，默认为6
    protected int digits = 6;

    protected String crypto = "HmacSHA1";

    protected String defaultEncoding ="utf-8";
    
    StringGenerator stringGenerator;
    
    protected String otpType = OtpTypes.TIMEBASED_OTP;

    public static final class OtpTypes {
        // 手机
        public static String  MOBILE = "MOBILE";
        // 短信
        public static String SMS = "SMS";
        // 邮箱
        public static String EMAIL = "EMAIL";
        //TIMEBASED_OPT
        public static String TIMEBASED_OTP = "TOPT";
        // HmacOTP
        public static String HOTP_OTP = "HOTP";

        public static String RSA_OTP = "RSA";
        
        public static String CAP_OTP = "CAP";

    }

    public abstract boolean produce(UserInfo userInfo);

    public abstract boolean validate(UserInfo userInfo, String token);

    protected String defaultProduce(UserInfo userInfo) {
        return genToken(userInfo);
    }

    /**
     * genToken.
     * @param userInfo UserInfo
     * @return
     */
    public String genToken(UserInfo userInfo) {
        if (stringGenerator == null) {
            stringGenerator = new StringGenerator(StringGenerator.DEFAULT_CODE_NUMBER, digits);
        }
        String token = stringGenerator.randomGenerate();
        logger.debug("Generator token " + token);
        return token;
    }

    /**
     *  the interval.
     * @return the interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * interval the interval to set.
     * @param interval the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * digits.
     * @return the digits
     */
    public int getDigits() {
        return digits;
    }

    /**
     * digits the digits to set.
     * @param digits the digits to set
     */
    public void setDigits(int digits) {
        this.digits = digits;
    }

    /**
     * crypto.
     * @return the crypto
     */
    public String getCrypto() {
        return crypto;
    }

    /**
     * crypto the crypto to set.
     * @param crypto the crypto to set
     */
    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }

    public String getOtpType() {
        return otpType;
    }

    public void setOtpType(String optType) {
        this.otpType = optType;
    }

    public void setOptTokenStore(AbstractOtpTokenStore optTokenStore) {
        this.optTokenStore = optTokenStore;
    }

    public void initPropertys() {
        
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }
 
}
