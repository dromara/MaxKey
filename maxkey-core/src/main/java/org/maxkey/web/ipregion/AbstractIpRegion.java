package org.maxkey.web.ipregion;

public abstract class AbstractIpRegion implements IpRegion{

	int failCount = 0;
	
	public String getLocation(String region) {
		return region;
	}
	
	public int getFailCount() {
		return failCount;
	};
	
	public int plusFailCount() {
		return failCount++;
	};
}
