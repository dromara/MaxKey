/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
