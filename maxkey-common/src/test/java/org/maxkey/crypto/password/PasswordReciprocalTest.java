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
 

package org.maxkey.crypto.password;

import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordReciprocalTest {

	public PasswordReciprocalTest() {
	
	}

	public static void main(String[] args) {
		BCryptPasswordEncoder spe= new BCryptPasswordEncoder();
		//String pass=PasswordReciprocal.getInstance().rawPassword("admin", "admin");
		String pass ="x8zPbCya";
		String epass=spe.encode(pass);
		System.out.println("PasswordEncoder "+epass); 
		
		String encode = PasswordReciprocal.getInstance().encode(pass);
		System.out.println(encode);
		System.out.println(PasswordReciprocal.getInstance().decoder(encode));
		
		System.out.println(PasswordReciprocal.getInstance().matches(pass,encode));
	}

}
