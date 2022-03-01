package org.maxkey.web.ipregion;

import org.junit.Test;

public class IpRegionIpcnTest {
	
	@Test
	public void test(){
		IpRegion ipRegion = new IpRegionIpcn();
		System.out.println(ipRegion.region("117.155.70.59"));
	}
	
}
