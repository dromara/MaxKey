package org.maxkey.web.ipregion;

import org.junit.Test;

public class IpRegionIp360Test {
	
	@Test
	public void test(){
		IpRegion ipRegion = new IpRegionIp360();
		System.out.println(ipRegion.region("117.155.70.59"));
	}
	
}
