package org.maxkey.crypto.password.opt.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.maxkey.crypto.Base32Utils;
import org.maxkey.crypto.password.opt.AbstractOptAuthn;
import org.maxkey.crypto.password.opt.algorithm.HOTP;
import org.maxkey.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HotpOtpAuthn extends AbstractOptAuthn {
    private static final  Logger _logger = LoggerFactory.getLogger(HotpOtpAuthn.class);

    boolean addChecksum;
    int truncation = -1;

    @Override
    public boolean produce(UserInfo userInfo) {
        return true;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        _logger.debug("SharedCounter : " + userInfo.getSharedCounter());
        byte[] byteSharedSecret = Base32Utils.decode(userInfo.getSharedSecret());
        String hotpToken;
        try {
            hotpToken = HOTP.generateOTP(
                    byteSharedSecret, 
                    Long.parseLong(userInfo.getSharedCounter()), 
                    digits,
                    addChecksum, truncation
                );
            _logger.debug("token : " + token);
            _logger.debug("hotpToken : " + hotpToken);
            if (token.equalsIgnoreCase(hotpToken)) {
                return true;
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *  the addChecksum.
     */
    public boolean isAddChecksum() {
        return addChecksum;
    }

    /**
     * addChecksum the addChecksum to set.
     */
    public void setAddChecksum(boolean addChecksum) {
        this.addChecksum = addChecksum;
    }

    /**
     *  the truncation.
     */
    public int getTruncation() {
        return truncation;
    }

    /**
     * truncation the truncation to set.
     */
    public void setTruncation(int truncation) {
        this.truncation = truncation;
    }

}
