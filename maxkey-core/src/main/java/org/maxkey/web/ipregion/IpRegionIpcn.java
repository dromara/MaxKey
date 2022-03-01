package org.maxkey.web.ipregion;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class IpRegionIpcn extends AbstractIpRegion implements IpRegion{
	
	public static final String REGION_URL = "https://ip.cn/ip/%s.html";
	
	@Override
	public String region(String ipAddress) {
		try {
			Document doc;
			doc = Jsoup.connect(String.format(REGION_URL, ipAddress))
					.timeout(TIMEOUT)
					.userAgent(USERAGENT)
					.header("referer","https://ip.cn/")
					.header("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"98\", \"Google Chrome\";v=\"98\"")
					.get();
			Elements address = doc.select("#tab0_address");
			return address.text().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
