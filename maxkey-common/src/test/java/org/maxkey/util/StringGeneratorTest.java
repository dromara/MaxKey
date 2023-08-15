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
 

package org.maxkey.util;

import java.util.UUID;

import org.dromara.maxkey.util.StringGenerator;
import org.junit.Test;

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
