package org.maxkey.crypto;

import java.security.Key;
import java.util.Map;

import org.junit.Test;
import org.maxkey.crypto.Base64Utils;
import org.maxkey.crypto.HexUtils;
import org.maxkey.crypto.RSAUtils;

public class RSAUtilsTest {

	//@Test
	public void test() throws Exception {

		// ˽Կ���ܡ�����Կ����
		// ˽Կǩ����Կ��֤ǩ��
		Map<String, Object> key = RSAUtils.genKeyPair();
		String privateKey = RSAUtils.getPublicKey2Hex(key);
		String publicKey = RSAUtils.getPrivateKey2Hex(key);
		System.out.println("privateKey:" + privateKey);
		System.out.println("publicKey:" + publicKey);
		String signString = "my name is shiming";
		Key keyp = (Key) key.get(RSAUtils.PUBLIC_KEY);
		System.out.println("privateKey:" + Base64Utils.base64UrlEncode(keyp.getEncoded()));

		byte[] encodedData = RSAUtils.encryptByPrivateKey(signString.getBytes(), privateKey);
		System.out.println("���ܺ�\r\n" + new String(encodedData));
		System.out.println("���ܺ�B64��\r\n" + HexUtils.bytes2HexString(encodedData));
		byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
		String target = new String(decodedData);
		System.out.println("target:" + target);

	}

}
