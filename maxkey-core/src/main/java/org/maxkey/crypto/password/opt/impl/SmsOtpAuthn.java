package org.maxkey.crypto.password.opt.impl;

import org.maxkey.crypto.password.opt.AbstractOptAuthn;
import org.maxkey.domain.UserInfo;
import org.springframework.jdbc.core.JdbcTemplate;

public class SmsOtpAuthn extends AbstractOptAuthn {

    public SmsOtpAuthn(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public boolean produce(UserInfo userInfo) {
        String token = this.genToken(userInfo);
        // TODO:You must add send sms code here

        this.insertDataBase(userInfo, token, userInfo.getUsername(), OptTypes.SMS);
        return true;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        return this.validateDataBase(userInfo, token, OptTypes.SMS);
    }

}
