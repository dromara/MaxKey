package org.maxkey.util;

import java.util.UUID;

import org.junit.Test;
import org.maxkey.util.StringGenerator;

public class StringGeneratorTest {
	@Test
	public void test()  {
		StringGenerator stringGenerator=new StringGenerator();
		System.out.println(stringGenerator.uuidGenerate()); 
		System.out.println(stringGenerator.uuidGenerate().length());  
        System.out.println(stringGenerator.uniqueGenerate());  
        System.out.println(stringGenerator.uniqueGenerate().length());  
        
        System.out.println(StringGenerator.uuidMatches(stringGenerator.uuidGenerate()));
        System.out.println(StringGenerator.uuidMatches(UUID.randomUUID().toString()));
        System.out.println(StringGenerator.uuidMatches("408192be-cab9-4b5b-8d41-4cd827cc4091"));

        
	}
}
