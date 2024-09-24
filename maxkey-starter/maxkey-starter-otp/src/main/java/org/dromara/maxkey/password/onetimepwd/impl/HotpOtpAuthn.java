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
 

package org.dromara.maxkey.password.onetimepwd.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.dromara.maxkey.crypto.Base32Utils;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.algorithm.HOTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HotpOtpAuthn extends AbstractOtpAuthn {
    private static final  Logger _logger = LoggerFactory.getLogger(HotpOtpAuthn.class);

    boolean addChecksum;
    int truncation = -1;

    public HotpOtpAuthn() {
        otpType = OtpTypes.HOTP_OTP;
    }

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
