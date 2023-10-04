/*
 * Copyright [2023] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dromara.maxkey.ip2location.online;

import java.io.IOException;

import org.dromara.maxkey.ip2location.AbstractIpLocation;
import org.dromara.maxkey.ip2location.IpLocation;
import org.dromara.maxkey.ip2location.Region;
import org.dromara.maxkey.util.JsonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ip138查询ip地址
 * 
 * @author Crystal.Sea
 *
 */
public class Ip138 extends AbstractIpLocation implements IpLocation{
	static final  Logger _logger = LoggerFactory.getLogger(Ip138.class);
	
	public static final String REGION_URL = "https://www.ip138.com/iplookup.asp?ip=%s&action=2";
	
	public static final String BEGIN 	= "\"ip_c_list\":[";
	public static final String END 		= "], \"zg\":0};";
	
	@Override
	public Region region(String ipAddress) {
		try {
			Document doc;
			doc = Jsoup.connect(String.format(REGION_URL, ipAddress))
					.timeout(TIMEOUT)
					.userAgent(USERAGENT)
					.header("Host", "www.ip138.com")
					.header("Referer", "https://www.ip138.com/")
					.header("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"98\", \"Google Chrome\";v=\"98\"")
					.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
					.get();
			String htmlData = doc.toString();
			_logger.trace("html {}",htmlData);
			String jsonData = htmlData.substring(htmlData.indexOf(BEGIN) + BEGIN.length() , htmlData.indexOf(END));
			Ip138Response response = JsonUtils.stringToObject(jsonData, Ip138Response.class);
			return response == null ? null : new Region(response.getCt(),response.getProv(),response.getCity(),getLocation(response.toString()));
		} catch (Exception e) {
			_logger.error("Exception ",e);
		}
		return null;
	}
}
