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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.codec.binary.Hex;
import org.dromara.maxkey.crypto.Base32Utils;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.dromara.maxkey.password.onetimepwd.algorithm.TimeBasedOTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeBasedOtpAuthn extends AbstractOtpAuthn {
    private static final  Logger _logger = LoggerFactory.getLogger(TimeBasedOtpAuthn.class);
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TimeBasedOtpAuthn() {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public TimeBasedOtpAuthn(int digits , int interval) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        super.digits = digits;
        super.interval = interval;
    }
    
    @Override
    public boolean produce(UserInfo userInfo) {
        return true;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        _logger.debug("utcTime : {}" , dateFormat.format(new Date()));
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        String sharedSecret = PasswordReciprocal.getInstance().decoder(userInfo.getSharedSecret());
        byte[] byteSharedSecret = Base32Utils.decode(sharedSecret);
        String hexSharedSecret = Hex.encodeHexString(byteSharedSecret);
        String timeBasedToken = "";
        if (crypto.equalsIgnoreCase("HmacSHA1")) {
            timeBasedToken = TimeBasedOTP.genOTP(
                    hexSharedSecret,
                    Long.toHexString(currentTimeSeconds / interval).toUpperCase() + "",
                    digits + "");
        } else if (crypto.equalsIgnoreCase("HmacSHA256")) {
            timeBasedToken = TimeBasedOTP.genOTPHmacSHA256(
                    hexSharedSecret,
                    Long.toHexString(currentTimeSeconds / interval).toUpperCase() + "", 
                    digits + "");
        } else if (crypto.equalsIgnoreCase("HmacSHA512")) {
            timeBasedToken = TimeBasedOTP.genOTPHmacSHA512(
                    hexSharedSecret,
                    Long.toHexString(currentTimeSeconds / interval).toUpperCase() + "", 
                    digits + "");
        }
        _logger.debug("token : {}" , token);
        _logger.debug("timeBasedToken {}: " , timeBasedToken);
        if (token.equalsIgnoreCase(timeBasedToken)) {
            return true;
        }
        return false;

    }

}
