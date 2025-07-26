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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SCryptPasswordEncoderTest {

	public SCryptPasswordEncoderTest() {
	}

	public static void main(String[] args) {
		BCryptPasswordEncoder pe=new BCryptPasswordEncoder();
		//String c="$e0801$7Holo9EgzBeg5xf/WLZu3/5IQwOyEPDLJPgMXkF9jnekBrbQUMt4CF9O2trkz3zBCnCLpUMR437q/AjQ5TTToA==$oYB8KRSxAsxkKkt5r79W6r6P0wTUcKwGye1ivXRN0Ts="
		//;
		System.out.println(pe.encode("admin"));
			//	System.out.println(pe.encode("shimingxy")+"_password");
				//System.out.println(pe.matches("shimingxy"+"_password", c));
	}

}
