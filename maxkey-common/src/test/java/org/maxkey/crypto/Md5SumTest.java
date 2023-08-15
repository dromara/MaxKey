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

import java.io.File;
import java.io.FileNotFoundException;

import org.dromara.maxkey.crypto.Md5Sum;

public class Md5SumTest {

	public Md5SumTest() {
	}

	public static void main(String[] args) throws FileNotFoundException {
		//String md5value=Md5Sum.produce(new File("E:/transwarp-4.3.4-Final-el6/transwarp-4.3.4-Final-26854-zh.el6.x86_64.tar.gz"));
		File f=new File("E:/Soft/Xmanager4_setup.1410342608.exe");
		String md5value=Md5Sum.produce(f);
		
		System.out.println(""+md5value);
		
		System.out.println(Md5Sum.check(f,md5value));
	}

}
