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
 

package org.maxkey.crypto.password.opt;

import org.maxkey.crypto.password.opt.token.AbstractOptTokenStore;
import org.maxkey.crypto.password.opt.token.InMemoryOptTokenStore;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.StringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractOTPAuthn.
 * @author Administrator
 *
 */
public abstract class AbstractOptAuthn {
    private static final  Logger logger = LoggerFactory.getLogger(AbstractOptAuthn.class);

    protected AbstractOptTokenStore optTokenStore = new InMemoryOptTokenStore();
    
    //验证码有效間隔
    protected int interval = 30;
    
    // 验证码长度，范围4～10，默认为6
    protected int digits = 6;

    protected String crypto = "HmacSHA1";

    StringGenerator stringGenerator;
    
    protected String optType = OptTypes.TIMEBASED_OPT;

    public static final class OptTypes {
        // 手机
        public static String  MOBILE = "MOBILE";
        // 短信
        public static String SMS = "SMS";
        // 邮箱
        public static String EMAIL = "EMAIL";
        //TIMEBASED_OPT
        public static String TIMEBASED_OPT = "TOPT";
        // HmacOTP
        public static String HOTP_OPT = "HOTP";

        public static String RSA_OPT = "RSA";
        
        public static String CAP_OPT = "CAP";

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

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

 
}
