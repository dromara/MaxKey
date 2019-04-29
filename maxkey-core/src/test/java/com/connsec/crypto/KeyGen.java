package com.connsec.crypto;
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



