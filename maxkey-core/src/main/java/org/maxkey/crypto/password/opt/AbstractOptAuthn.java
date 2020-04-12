package org.maxkey.crypto.password.opt;

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

    protected int interval = 30;

    protected int digits = 6;

    protected String crypto = "HmacSHA1";

    StringGenerator stringGenerator;

    public static final class OptTypes {
        // 手机
        public static int MOBILE = 2;
        // 短信
        public static int SMS = 3;
        // 邮箱
        public static int EMAIL = 4;

        public static int TIMEBASED_OPT = 5;

        public static int COUNTERBASED_OPT = 6;

        public static int HOTP_OPT = 7;

        public static int RSA_OPT = 8;

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

 
}
