/*
 * Copyright [2023] [MaxKey of copyright http://www.maxkey.top]
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

package org.dromara.maxkey.ip2location;

 
import org.dromara.maxkey.ip2location.IpLocation;
import org.dromara.maxkey.ip2location.online.Ip138;
import org.junit.Test;

public class IpRegionIp138Test {
	
	@Test
	public void test(){
		IpLocation ipRegion = new Ip138();
		System.out.println(ipRegion.region("117.155.70.59"));
	}
	
}
