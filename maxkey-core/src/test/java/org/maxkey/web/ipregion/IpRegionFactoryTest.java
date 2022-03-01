package org.maxkey.web.ipregion;

import org.junit.Test;

public class IpRegionFactoryTest {

	@Test
	public void test(){
		System.out.println(IpRegionFactory.getFactory().getLocation(
				IpRegionFactory.getFactory().region("127.0.0.1")
				));
		
		System.out.println(IpRegionFactory.getFactory().getLocation(
				IpRegionFactory.getFactory().region("117.155.70.59")
				));
	}
}
