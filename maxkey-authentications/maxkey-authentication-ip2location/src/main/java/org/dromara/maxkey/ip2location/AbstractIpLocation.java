package org.dromara.maxkey.ip2location;
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
 


/**
 * IpRegion转换抽象类，获取地址Location
 * 
 * @author Crystal.Sea
 *
 */
public abstract class AbstractIpLocation implements IpLocation{

	int failCount = 0;
	
	@Override
	public int getFailCount() {
		return failCount;
	};
	
	@Override
	public int plusFailCount() {
		return failCount++;
	};
	
	@Override
	public String getLocation(String region) {
		if(region.endsWith("电信") || region.endsWith("移动") || region.endsWith("联通")) {
			region.substring(0, region.length() - 2).trim();
		}
		
		if(region.indexOf(" ") > 0) {
			return region.split(" ")[0];
		}
		
		return region;
	}
}
