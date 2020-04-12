package org.maxkey.crypto.password.opt.impl;

import org.maxkey.crypto.password.opt.AbstractOptAuthn;
import org.maxkey.domain.UserInfo;

public class MobileOtpAuthn extends AbstractOptAuthn {

    
    
    public MobileOtpAuthn() {
        optType = OptTypes.SMS;
    }

    @Override
    public boolean produce(UserInfo userInfo) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        // TODO Auto-generated method stub
        return false;
    }

}
