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

import java.util.Date;
//import java.util.UUID;

import org.dromara.maxkey.util.UUIDGenerator;
import org.dromara.maxkey.uuid.UUID;
import org.junit.Test;

public class UUIDGeneratorTest {
	
	@Test
	public void test()  {
    	Date sd=new Date();
    	
    	//for(int i=0;i<100000;i++){
	    	UUIDGenerator generated=new UUIDGenerator();
	    	generated.toString();
	        //System.out.println(generated.toString());
	        
    	//}
    	Date ed=new Date();
        System.out.println("usertime "+(ed.getTime()-sd.getTime()));
        
       // UUIDGenerator.version(generated);
        
        
        System.out.println("JDK UUID");
        Date ssd=new Date();
       // for(int i=0;i<100000;i++){
        	//UUID.randomUUID().toString();
        	UUID.generate().toString();
	       // System.out.println(UUID.randomUUID().toString());
        //}
        Date sed=new Date();
        System.out.println("usertime "+(sed.getTime()-ssd.getTime()));
        
        UUIDGenerator.version(new UUIDGenerator(UUID.generate().toString()));
 
        
    }
	
}
