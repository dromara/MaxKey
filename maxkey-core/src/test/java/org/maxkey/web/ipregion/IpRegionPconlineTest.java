package org.maxkey.web.ipregion;

import org.junit.Test;

public class IpRegionPconlineTest {
	
	@Test
	public void test(){
		IpRegion ipRegion = new IpRegionPconline();
		System.out.println(ipRegion.region("117.155.70.59"));
	}
	
}
