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
	import java.io.FileInputStream;
	import java.io.ObjectInputStream;
	import java.security.Key;
	import java.security.PrivateKey;
	import java.security.PublicKey;
	import java.security.Signature;
	import java.security.interfaces.RSAPrivateKey;
	import java.security.interfaces.RSAPublicKey;

	import javax.crypto.Cipher;
	
	
	/**
	* RSA�ӽ���,RSAǩ��ǩ����֤��
	*
	* @author Administrator
	*
	*/
	public class RsaMessage {




	public static void main(String[] args) throws Exception {
	String str = "hello,�����ĵ����";
	System.out.println("ԭ�ģ�" + str);

	RsaMessage rsa = new RsaMessage();
	RSAPrivateKey privateKey = (RSAPrivateKey) rsa.readFromFile("sk.dat");
	RSAPublicKey publickKey = (RSAPublicKey) rsa.readFromFile("pk.dat");

	byte[] encbyte = rsa.encrypt(str, privateKey);
	System.out.println("˽Կ���ܺ�");
	String encStr = toHexString(encbyte);
	System.out.println(encStr);

	byte[] signBytes = rsa.sign(str, privateKey);
	System.out.println("ǩ��ֵ��");
	String signStr = toHexString(signBytes);
	System.out.println(signStr);

	byte[] decByte = rsa.decrypt(encStr, publickKey);
	System.out.println("��Կ���ܺ�");
	String decStr = new String(decByte);
	System.out.println(decStr);

	if (rsa.verifySign(str, signStr, publickKey)) {
	System.out.println("rsa sign check success");
	} else {
	System.out.println("rsa sign check failure");
	}
	}

	/**
	* ����,key�����ǹ�Կ��Ҳ������˽Կ
	*
	* @param message
	* @return
	* @throws Exception
	*/
	public byte[] encrypt(String message, Key key) throws Exception {
	Cipher cipher = Cipher.getInstance("RSA");
	cipher.init(Cipher.ENCRYPT_MODE, key);
	return cipher.doFinal(message.getBytes());
	}

	/**
	* ���ܣ�key�����ǹ�Կ��Ҳ������˽Կ������ǹ�Կ���ܾ���˽Կ���ܣ���֮��Ȼ
	*
	* @param message
	* @return
	* @throws Exception
	*/
	public byte[] decrypt(String message, Key key) throws Exception {
	Cipher cipher = Cipher.getInstance("RSA");
	cipher.init(Cipher.DECRYPT_MODE, key);
	return cipher.doFinal(toBytes(message));
	}

	/**
	* ��˽Կǩ��
	*
	* @param message
	* @param key
	* @return
	* @throws Exception
	*/
	public byte[] sign(String message, PrivateKey key) throws Exception {
	Signature signetcheck = Signature.getInstance("MD5withRSA");
	signetcheck.initSign(key);
	signetcheck.update(message.getBytes("ISO-8859-1"));
	return signetcheck.sign();
	}

	/**
	* �ù�Կ��֤ǩ�����ȷ��
	*
	* @param message
	* @param signStr
	* @return
	* @throws Exception
	*/
	public boolean verifySign(String message, String signStr, PublicKey key)
	throws Exception {
	if (message == null || signStr == null || key == null) {
	return false;
	}
	Signature signetcheck = Signature.getInstance("MD5withRSA");
	signetcheck.initVerify(key);
	signetcheck.update(message.getBytes("ISO-8859-1"));
	return signetcheck.verify(toBytes(signStr));
	}

	/**
	* ���ļ���ȡobject
	*
	* @param fileName
	* @return
	* @throws Exception
	*/
	private Object readFromFile(String fileName) throws Exception {
	ObjectInputStream input = new ObjectInputStream(new FileInputStream(
	fileName));
	Object obj = input.readObject();
	input.close();
	return obj;
	}

	public static String toHexString(byte[] b) {
	StringBuilder sb = new StringBuilder(b.length * 2);
	for (int i = 0; i < b.length; i++) {
	sb.append(HEXCHAR[(b[i] & 0xf0) >>> 4]);
	sb.append(HEXCHAR[b[i] & 0x0f]);
	}
	return sb.toString();
	}

	public static final byte[] toBytes(String s) {
	byte[] bytes;
	bytes = new byte[s.length() / 2];
	for (int i = 0; i < bytes.length; i++) {
	bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
	16);
	}
	return bytes;
	}

	private static char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7',
	'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	}
