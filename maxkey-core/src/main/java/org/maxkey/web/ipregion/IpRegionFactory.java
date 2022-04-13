/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.web.ipregion;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class IpRegionFactory extends AbstractIpRegion  implements IpRegion{

	static IpRegionFactory factory = new IpRegionFactory();
	
	static final ArrayList<IpRegion> ipRegionList;
	
	static {
		ipRegionList = new ArrayList<IpRegion>();
		ipRegionList.add(new IpRegionLocal());
		ipRegionList.add(new IpRegionIp138());
		ipRegionList.add(new IpRegionIpchaxun());
		ipRegionList.add(new IpRegionIpcn());
		ipRegionList.add(new IpRegionIp360());
		ipRegionList.add(new IpRegionPconline());
	}
	
	public static IpRegion getFactory() {
		return factory;
	}
	
	@Override
	public String region(String ipAddress) {
		for(int i = 0 ; i<ipRegionList.size() ; i++ ) {
			IpRegion ipRegion = ipRegionList.get(i);
			String region = ipRegion.region(ipAddress);
			if(StringUtils.isNotBlank(region)) {
				return region;
			}else {
				if(ipRegion.getFailCount() > 6) {
					ipRegionList.remove(i);//remove from list
				}
				//fail plus 1
				ipRegion.plusFailCount();
			}
		}
		return "unknown";
	}
	
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
