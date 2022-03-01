package org.maxkey.web.ipregion;

public class IpRegionLocal extends AbstractIpRegion implements IpRegion{
	
	@Override
	public String region(String ipAddress) {
		if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
			return "local";
		}
		return null;
	}
}
