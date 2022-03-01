package org.maxkey.web.ipregion;

import org.maxkey.util.JsonUtils;
import org.maxkey.web.HttpRequestAdapter;

public class IpRegionPconline  extends AbstractIpRegion implements IpRegion{
	
	public static final String REGION_URL = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=%s";
	
	@Override
	public String region(String ipAddress) {
		String responseJson  = 
				new HttpRequestAdapter(HttpRequestAdapter.MediaType.JSON)
								.get(String.format(REGION_URL,ipAddress),null);
		return JsonUtils.json2Object(responseJson, IpRegionPconlineResponse.class).getAddr().trim();
	}
	
}
