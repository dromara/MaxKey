package org.maxkey.crypto;

import java.util.Date;

import org.junit.Test;
import org.maxkey.crypto.DigestUtils;

public class DigestUtilsTest {
/*
	@Test
	public void test() {
		
		System.out.println(DigestUtils.shaB64("mytest"));
		
		System.out.println(DigestUtils.sha1B64("e707c852-29a4-bf56-f8b9-014716850d89"));
		
		System.out.println(DigestUtils.sha256B64("mytest"));
		
		System.out.println(DigestUtils.sha384B64("mytest"));
		
		System.out.println(DigestUtils.sha512B64("mytest"));
		
		System.out.println(DigestUtils.md5B64("e707c852-29a4-bf56-f8b9-014716850d89"));
	}
	*/
	@Test
	public void testHex() {
		
		System.out.println(DigestUtils.shaHex("mytest"));
		
		System.out.println(DigestUtils.sha1Hex("mytest"));
		
		System.out.println(DigestUtils.sha256Hex("mytest"));
		
		System.out.println(DigestUtils.sha384Hex("mytest"));
		
		System.out.println(DigestUtils.sha512Hex("mytest"));
		
		System.out.println(DigestUtils.md5Hex("seamingxy99"));
		System.out.println((new Date()).getTime());
	}
}
