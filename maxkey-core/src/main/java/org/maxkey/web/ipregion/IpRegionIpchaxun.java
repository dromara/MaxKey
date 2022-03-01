package org.maxkey.web.ipregion;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class IpRegionIpchaxun extends AbstractIpRegion implements IpRegion{
	
	public static final String REGION_URL = "https://ipchaxun.com/%s/";
	
	@Override
	public String region(String ipAddress) {
		try {
			Document doc;
			doc = Jsoup.connect(String.format(REGION_URL, ipAddress))
					.timeout(TIMEOUT)
					.userAgent(USERAGENT)
					.header("Host","ipchaxun.com")
					.header("Referer","https://ipchaxun.com/")
					.header("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"98\", \"Google Chrome\";v=\"98\"")
					.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
					.get();
			
			Elements address = doc.select(".info label span.value");
			return address.get(1).text().trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
