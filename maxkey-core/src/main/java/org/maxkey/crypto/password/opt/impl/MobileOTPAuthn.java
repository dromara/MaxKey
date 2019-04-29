package org.maxkey.crypto.password.opt.impl;

import org.maxkey.crypto.password.opt.AbstractOTPAuthn;
import org.maxkey.domain.UserInfo;
import org.springframework.jdbc.core.JdbcTemplate;


public class MobileOTPAuthn  extends AbstractOTPAuthn {

	public MobileOTPAuthn(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	public boolean produce(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validate(UserInfo userInfo,String token) {
		// TODO Auto-generated method stub
		return false;
	}
	

	
}
