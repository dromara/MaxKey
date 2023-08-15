/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

/**
 * 
 */
package org.dromara.maxkey.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.springframework.core.io.ClassPathResource;

/**
 * 类似linux或Unix上，md5sum是用来计算和校验文件报文摘要的工具程序
 * @author Crystal.Sea
 *
 */
public class Md5Sum {
	
	static String passSum ="$2a$10$Yju1npqje5sMN/CYhXjogO4e707d7318e6ba7b763098f03779fd47877a7bf4780c1c219be9c280646eace0f44dc4d426be8fa50415e507786424e887c2b266add267cea005a0daf9f019a152f16b30a8631e4872def2e9a9872d44";
	
	/**
	 * 
	 */
	public Md5Sum() {

	}

	public static String produce(File file) {
		String md5value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(byteBuffer);
			byte[] bCipher=messageDigest.digest();
			md5value=HexUtils.bytes2HexString(bCipher);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		md5value += " *"+file.getName();
		return md5value;
	}
	
	public static String produce(InputStream is,String fileName) {
		String md5value = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(BytesUtils.toByteArray(is));
			byte[] bCipher=messageDigest.digest();
			md5value=HexUtils.bytes2HexString(bCipher);
			md5value += " *"+fileName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			
		return md5value;
	}
	
	public static boolean check(File file,String md5String) {
		String md5value	=	produce(file);
		
		return md5value.equals(md5String)?true:false;
	}
	
	public static boolean check(InputStream is,String md5String) {
		String fileName = md5String.split("\\*")[1];
		String md5value	=	produce(is,fileName);
		
		return md5value.equals(md5String)?true:false;
	}
	
	public static boolean checkVersion() {
		boolean checkResult = false;
		try {
			ClassPathResource classFile = 
	                new ClassPathResource(
	                		PasswordReciprocal.getInstance().decoder(
	                	"$2a$10$XqRN8D5dWhArSVmzNi67GO5a5ced4bc39f6c73962d2faad399e6dd41d7e3d92b4dcd3b4f4be5229b41dd61d405803fb22d449a791da786e9e651444ba8149108c592663ae5fc32f88157ddfa4a06bea7803b8c"
	               ));
			checkResult = check(classFile.getInputStream(),PasswordReciprocal.getInstance().decoder(passSum));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if( !checkResult ) {
			System.exit(0);
		}
		
		return checkResult;
	}

}
