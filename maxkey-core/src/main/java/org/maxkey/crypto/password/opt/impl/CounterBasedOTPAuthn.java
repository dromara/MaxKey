package org.maxkey.crypto.password.opt.impl;

import org.apache.commons.codec.binary.Hex;
import org.maxkey.crypto.Base32Utils;
import org.maxkey.crypto.password.opt.AbstractOTPAuthn;
import org.maxkey.crypto.password.opt.algorithm.TimeBasedOTP;
import org.maxkey.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;


public class CounterBasedOTPAuthn  extends AbstractOTPAuthn {
	private final static Logger _logger = LoggerFactory.getLogger(CounterBasedOTPAuthn.class);
	
	public CounterBasedOTPAuthn(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	public boolean produce(UserInfo userInfo) {
		return true;
	}

	@Override
	public boolean validate(UserInfo userInfo,String token) {
		_logger.debug("SharedCounter : "+userInfo.getSharedCounter());
		byte[]byteSharedSecret= Base32Utils.decode(userInfo.getSharedSecret());
        String hexSharedSecret=Hex.encodeHexString(byteSharedSecret);
        String counterBasedToken="";
        if(crypto.equalsIgnoreCase("HmacSHA1")){
        	counterBasedToken=TimeBasedOTP.genOTP(hexSharedSecret,userInfo.getSharedCounter(),""+digits);
        }else  if(crypto.equalsIgnoreCase("HmacSHA256")){
        	counterBasedToken=TimeBasedOTP.genOTPHmacSHA256(hexSharedSecret,userInfo.getSharedCounter(),""+digits);
        }else  if(crypto.equalsIgnoreCase("HmacSHA512")){
        	counterBasedToken=TimeBasedOTP.genOTPHmacSHA512(hexSharedSecret,userInfo.getSharedCounter(),""+digits);
        }
        
        _logger.debug("token : "+token);
        _logger.debug("counterBasedToken : "+counterBasedToken);
        if(token.equalsIgnoreCase(counterBasedToken)){
        	return true;
        }
       	return false;
	}
	
}
