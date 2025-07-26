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

import org.dromara.maxkey.util.SnowFlakeId;
import org.joda.time.DateTime;
import org.junit.Test;

public class SonwFlakeIdTest {

	
	@Test
	public void UidGenerator()  {
	    DateTime d= new DateTime("2020-01-01T01:01:01");
	    System.out.println("time "+d.getMillis());
		SnowFlakeId snowFlake = new SnowFlakeId(1, 1,8,d.getMillis());
		long seq = snowFlake.nextId();
		
		System.out.println(seq);
		System.out.println(snowFlake.parse(seq).getDateTime());
	}
	
	@Test
	public void performance()  {
        SnowFlakeId snowFlake = new SnowFlakeId(1, 1);
    
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            System.out.println(snowFlake.nextId());
        }
    
        System.out.println(System.currentTimeMillis() - start);
	}
}
