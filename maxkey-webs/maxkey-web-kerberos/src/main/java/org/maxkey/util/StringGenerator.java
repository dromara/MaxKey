package org.maxkey.util;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.maxkey.crypto.Base64Utils;

public class StringGenerator {

	private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
			.toCharArray();

	private Random random = new SecureRandom();

	private int length;

	/**
	 * Create a generator with the default length (6).
	 */
	public StringGenerator() {
		this(6);
	}

	/**
	 * Create a generator of random strings of the length provided
	 * 
	 * @param length the length of the strings generated
	 */
	public StringGenerator(int length) {
		this.length = length;
	}

	public String randomGenerate() {
		byte[] verifierBytes = new byte[length];
		random.nextBytes(verifierBytes);
		return getString(verifierBytes);
	}
	
	public String uuidGenerate() {
		UUIDGenerator generated=new UUIDGenerator();
		return generated.toString().replace("-", "").toLowerCase();
	}
	
	public String uniqueGenerate() {
		 StringBuffer uniqueString=new StringBuffer("");
		 this.length=9;
		 String randomString =randomGenerate();
		 uniqueString.append(randomString.subSequence(0, 4));
		 
		 Date currentDate=new Date();
		 DateFormat dateFormat = new SimpleDateFormat("ddMMSSSyyyyHHmmss");
		 String dateString=Base64Utils.encodeBase64(dateFormat.format(currentDate).getBytes());
		 dateString=dateString.substring(0, dateString.length()-1);
		 uniqueString.append(dateString);
		 
		 uniqueString.append(randomString.subSequence(5, 8));
		 
		 return uniqueString.toString();
	}

	/**
	 * Convert these random bytes to a verifier string. The length of the byte array can be
	 * {@link #setLength(int) configured}. The default implementation mods the bytes to fit into the
	 * ASCII letters 1-9, A-Z, a-z .
	 * 
	 * @param verifierBytes The bytes.
	 * @return The string.
	 */
	protected String getString(byte[] verifierBytes) {
		char[] chars = new char[verifierBytes.length];
		for (int i = 0; i < verifierBytes.length; i++) {
			chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
		}
		return new String(chars);
	}

	/**
	 * The random value generator used to create token secrets.
	 * 
	 * @param random The random value generator used to create token secrets.
	 */
	public void setRandom(Random random) {
		this.random = random;
	}
	
	/**
	 * The length of string to generate.
	 * 
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	public static void main(String[] args) {
		StringGenerator stringGenerator=new StringGenerator();
		System.out.println(stringGenerator.uuidGenerate()); 
		System.out.println(stringGenerator.uuidGenerate().length());  
        System.out.println(stringGenerator.uniqueGenerate());  
        System.out.println(stringGenerator.uniqueGenerate().length());  

	}
}
