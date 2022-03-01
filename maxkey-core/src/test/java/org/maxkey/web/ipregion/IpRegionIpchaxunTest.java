package org.maxkey.web.ipregion;

import org.junit.Test;

public class IpRegionIpchaxunTest {
	
	@Test
	public void test(){
		IpRegion ipRegion = new IpRegionIpchaxun();
		System.out.println(ipRegion.region("117.155.70.59"));
	}
	
}
