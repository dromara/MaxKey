/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.synchronizer.dingtalk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;

public class DingtalkAccessTokenService {
	static final  Logger _logger = LoggerFactory.getLogger(DingtalkAccessTokenService.class);
	
	String appkey;
	
	String appsecret;
	
	public DingtalkAccessTokenService() {
		
	}
	public DingtalkAccessTokenService(String appkey, String appsecret) {
		super();
		this.appkey = appkey;
		this.appsecret = appsecret;
	}


	public String requestToken() throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setAppkey(appkey);
		request.setAppsecret(appsecret);
		request.setHttpMethod("GET");
		OapiGettokenResponse response = client.execute(request);
		_logger.info("response : " + response.getBody());
		
		if(response.getErrcode()== 0){
			return response.getAccessToken();
		}
		return "";
	}
	
	
	public String getAppkey() {
		return appkey;
	}


	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}


	public String getAppsecret() {
		return appsecret;
	}


	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}


	public static void main(String[] args) {

	}

}
