package com.connsec.rest;

import org.junit.Test;
import org.maxkey.util.AuthorizationHeaderUtils;

public class AuthorizationHeaderTest {
	
	@Test
	public void test()  {
		String basic =AuthorizationHeaderUtils.createBasic("Aladdin", "open sesame");
		System.out.println(basic);
		String []rb=AuthorizationHeaderUtils.resolveBasic(basic);
		System.out.println(rb[0]+":"+rb[1]);
	}
}
