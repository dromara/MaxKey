package org.maxkey.web.filter.ipaddress;

import java.util.HashMap;
import java.util.List;

import org.maxkey.cache.AbstractCache;
import org.maxkey.domain.IpAddrFilter;

public class IpAddressCache  extends AbstractCache{

	JdbcIpAddressService jdbcIpAddressService;
	
	public static HashMap<String,IpAddrFilter> ipAddressBlackListMap=new HashMap<String,IpAddrFilter>();
	
	public static HashMap<String,IpAddrFilter> ipAddressWhiteListMap=new HashMap<String,IpAddrFilter>();
	
	@Override
	public void business() {
		List<IpAddrFilter> ipAddrFilterList=jdbcIpAddressService.queryAll();
		if(ipAddrFilterList!=null){
			ipAddressBlackListMap.clear();
			ipAddressWhiteListMap.clear();
			for(IpAddrFilter ipAddrFilter :ipAddrFilterList){
				if(ipAddrFilter.getFilter()==IpAddrFilter.FILTER.BLACKLIST){
					ipAddressBlackListMap.put(ipAddrFilter.getIpAddr(), ipAddrFilter);
				}else{
					ipAddressWhiteListMap.put(ipAddrFilter.getIpAddr(), ipAddrFilter);
				}
			}
		}
	}

	public void setJdbcIpAddressService(JdbcIpAddressService jdbcIpAddressService) {
		this.jdbcIpAddressService = jdbcIpAddressService;
	}

}
