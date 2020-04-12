package org.maxkey.crypto.password.opt.impl;

import org.maxkey.crypto.password.opt.AbstractOptAuthn;
import org.maxkey.domain.UserInfo;
import org.springframework.jdbc.core.JdbcTemplate;

public class MobileOtpAuthn extends AbstractOptAuthn {

    public MobileOtpAuthn(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
