package org.maxkey.crypto.password.opt.impl;

import org.maxkey.crypto.password.opt.AbstractOptAuthn;
import org.maxkey.domain.UserInfo;

public class SmsOtpAuthn extends AbstractOptAuthn {

    @Override
    public boolean produce(UserInfo userInfo) {
        String token = this.genToken(userInfo);
        // TODO:You must add send sms code here

        return true;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        return true;
    }

}
