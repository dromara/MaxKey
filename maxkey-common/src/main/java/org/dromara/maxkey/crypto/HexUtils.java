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
 

/**
 * 
 */
package org.dromara.maxkey.crypto;

import java.io.UnsupportedEncodingException;

/**
 * @author Crystal.Sea
 *
 */
public final class HexUtils {

	static final byte[] HEX_CHAR_TABLE = { 
		(byte) '0', (byte) '1', (byte) '2',(byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
		(byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c',(byte) 'd', (byte) 'e', (byte) 'f' 
	};
	
	
	public static String hex2String(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public static String bytes2HexString(byte[] raw) {
		byte[] hex = new byte[2 * raw.length];
		int index = 0;

		for (byte b : raw) {
			int v = b & 0xFF;
			hex[index++] = HEX_CHAR_TABLE[v >>> 4];
			hex[index++] = HEX_CHAR_TABLE[v & 0xF];
		}
		String s = "";
		try {
			s = new String(hex, "ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	 public static byte[] hex2Bytes(char[] hex) {
		    int length = hex.length / 2;
		    byte[] raw = new byte[length];
		    for (int i = 0; i < length; i++) {
		      int high = Character.digit(hex[i * 2], 16);
		      int low = Character.digit(hex[i * 2 + 1], 16);
		      int value = (high << 4) | low;
		      if (value > 127) {
		        value -= 256;
		      }
		      raw[i] = (byte) value;
		    }
		    return raw;
		  }
	 
	 public static byte[] hex2Bytes(String hex) {
		    return hex2Bytes(hex.toCharArray());
	}
}
