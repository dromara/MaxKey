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
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;


public class KeyGen {
	public static void main(String[] args) throws Exception {
		String keyInfo="ASDFSDFNUGD__TYTY";
		KeyGen kg = new KeyGen();
		kg.genKeys(keyInfo);
	}


	public void genKeys(String keyInfo) throws Exception {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = new SecureRandom();
		random.setSeed(keyInfo.getBytes());
		
		keygen.initialize(512, random);
		// ȡ����Կ��
		KeyPair kp = keygen.generateKeyPair();
		// ȡ�ù�Կ
		PublicKey publicKey = kp.getPublic();
		System.out.println(publicKey);
		saveFile(publicKey, "pk.dat");
		// ȡ��˽Կ
		PrivateKey privateKey = kp.getPrivate();
		saveFile(privateKey, "sk.dat");
	}

	private void saveFile(Object obj, String fileName) throws Exception {
		ObjectOutputStream output=new ObjectOutputStream(
		new	FileOutputStream(fileName));
		output.writeObject(obj);
		output.close();
	}
}
//���ù�Կ���ܣ�˽Կ���ܣ�



