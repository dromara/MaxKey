package com.connsec.otp.algorithm;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.maxkey.crypto.Base32Utils;
import org.maxkey.crypto.password.opt.algorithm.HOTP;
import org.maxkey.crypto.password.opt.algorithm.HmacOTP;


public class HmacOTPTest {
 
 public static void main(String[] args) { 
	 
	 byte[]byteseed= Base32Utils.decode("DCGAGPE2BCDBD6D3FG4NX2QGACVIHXP4");
	 
     System.out.println(HmacOTP.gen(Base32Utils.decode("DCGAGPE2BCDBD6D3FG4NX2QGACVIHXP4"),3,6));
     
     try {
		System.out.println(HOTP.generateOTP(byteseed, 3, 6, false, -1));
	} catch (InvalidKeyException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
}
