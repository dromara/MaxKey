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

import org.dromara.maxkey.crypto.password.PasswordGen;

public class PasswordGenTest {

	public PasswordGenTest() {
	}

	public static void main(String[] args) {
		PasswordGen gen=new PasswordGen();
		System.out.println(gen.gen(2,2,2,1));
		for(int i=1;i<100;i++){
			//System.out.println(gen.gen());
			//System.out.println(gen.gen(6));
			//System.out.println(gen.gen(2,2,2,0));
		}
		
	}

}
