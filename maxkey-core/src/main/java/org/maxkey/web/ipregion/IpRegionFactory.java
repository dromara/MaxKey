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
