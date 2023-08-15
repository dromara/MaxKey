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
 

package org.maxkey.crypto;

import java.time.Instant;

import org.dromara.maxkey.crypto.DigestUtils;
import org.junit.Test;

public class DigestUtilsTest {
/*
	@Test
	public void test() {
		
		System.out.println(DigestUtils.shaB64("mytest"));
		
		System.out.println(DigestUtils.sha1B64("e707c852-29a4-bf56-f8b9-014716850d89"));
		
		System.out.println(DigestUtils.sha256B64("mytest"));
		
		System.out.println(DigestUtils.sha384B64("mytest"));
		
		System.out.println(DigestUtils.sha512B64("mytest"));
		
		System.out.println(DigestUtils.md5B64("e707c852-29a4-bf56-f8b9-014716850d89"));
	}
	*/
	@Test
	public void testHex() {
		/*
		System.out.println(DigestUtils.shaHex("mytest"));
		
		System.out.println(DigestUtils.sha1Hex("mytest"));
		
		System.out.println(DigestUtils.sha256Hex("mytest"));
		
		System.out.println(DigestUtils.sha384Hex("mytest"));
		
		System.out.println(DigestUtils.sha512Hex("mytest"));
		
		System.out.println(DigestUtils.md5Hex("seamingxy99"));
		System.out.println((new Date()).getTime());
		*/
		
		//String zentaoLogin="http://127.0.0.1/biz/api.php?m=user&f=apilogin&account=%s&code=%s&time=%s&token=%s";
		String zentaoLogin="http://127.0.0.1/zentao/api.php?m=user&f=apilogin&account=%s&code=%s&time=%s&token=%s";
		String code = "maxkey";
		//String key   = "430ba509ba95094e580b925fc4839459";
		String key   = "f71792dfebf23d62bc4d65d1513087e3";
		//String time  = ""+System.currentTimeMillis();
		String time  = ""+Instant.now().getEpochSecond();
		//String time = "1615370929";
		//String code  = "myApp";
		//String key   = "427c579384224abf9570779d82969d1e";
		//String time  = "1557034496";
		
		String token =DigestUtils.md5Hex(code+key+time);
		
		System.out.println("currentTimeMillis " + System.currentTimeMillis());
		System.out.println(DigestUtils.md5Hex(code+key+time));
		String account="admin";
		
		String redirec_uri=String.format(zentaoLogin,account,code,time,token);
		System.out.println("redirec_uri : \n"+redirec_uri);
		
		

		
	}
}
