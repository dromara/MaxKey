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
 

package org.maxkey.otp.algorithm;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.dromara.maxkey.crypto.Base32Utils;
import org.dromara.maxkey.password.onetimepwd.algorithm.HOTP;
import org.dromara.maxkey.password.onetimepwd.algorithm.HmacOTP;


public class HmacOTPTest {
 
 public static void main(String[] args) { 
	 
	 byte[]byteseed= Base32Utils.decode("DCGAGPE2BCDBD6D3FG4NX2QGACVIHXP4");
	 
     System.out.println(HmacOTP.gen(Base32Utils.decode("DCGAGPE2BCDBD6D3FG4NX2QGACVIHXP4"),3,6));
     
     try {
		System.out.println(HOTP.generateOTP(byteseed, 3, 6, false, -1));
	} catch (InvalidKeyException e) {
		e.printStackTrace();
	} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
	}
 }
}
