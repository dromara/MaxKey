package org.maxkey.crypto.password.opt.impl;

import org.maxkey.crypto.password.opt.AbstractOTPAuthn;
import org.maxkey.domain.UserInfo;
import org.springframework.jdbc.core.JdbcTemplate;


public class SmsOTPAuthn  extends AbstractOTPAuthn {

	public SmsOTPAuthn(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	public boolean produce(UserInfo userInfo) {
		String token=this.genToken(userInfo);
		//TODO:You must add send sms code here 
		
		this.insertDataBase(userInfo, token, userInfo.getUsername(), OPT_TYPES.SMS);
		return true;
	}

	@Override
	public boolean validate(UserInfo userInfo,String token) {
		return this.validateDataBase(userInfo, token, OPT_TYPES.SMS);
	}
	

	
}
