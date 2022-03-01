package org.maxkey.web.ipregion;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.maxkey.util.JsonUtils;

public class IpRegionIp360  extends AbstractIpRegion  implements IpRegion{
	
	public static final String REGION_URL = "http://ip.360.cn/IPQuery/ipquery?ip=%s&verifycode=";
	public static final String BEGIN 	= "<body>";
	public static final String END 		= "</body>";
	
	@Override
	public String region(String ipAddress) {
		try {
			Document doc;
			doc = Jsoup.connect(String.format(REGION_URL, ipAddress))
					.timeout(TIMEOUT)
					.userAgent(USERAGENT)
					.header("Host", "ip.360.cn")
					.header("Origin", "http://ip.360.cn")
					.header("Referer", "http://ip.360.cn/")
					.header("Accept","application/json, text/plain, */*")
					.post();
			
			String htmlData = doc.toString();
			String jsonData = htmlData.substring(htmlData.indexOf(BEGIN) + BEGIN.length() , htmlData.indexOf(END));
			IpRegionIp360Response responseJson = JsonUtils.json2Object(jsonData, IpRegionIp360Response.class);
			return responseJson == null ? null : responseJson.getData().replace("\t", " ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
