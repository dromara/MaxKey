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
 

package org.dromara.maxkey.synchronizer.workweixin;

import org.dromara.maxkey.synchronizer.entity.AccessToken;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkweixinAccessTokenService {
	static final  Logger _logger = LoggerFactory.getLogger(WorkweixinAccessTokenService.class);
	
	String corpid;
	
	String corpsecret;
	
	public static String TOKEN_URL="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";


	public WorkweixinAccessTokenService() {}
	
	
	public WorkweixinAccessTokenService(String corpid, String corpsecret) {
		super();
		this.corpid = corpid;
		this.corpsecret = corpsecret;
	}


	public String requestToken() {
		HttpRequestAdapter request =new HttpRequestAdapter();
		String responseBody = request.get(String.format(TOKEN_URL, corpid,corpsecret));
		
		AccessToken accessToken = JsonUtils.gsonStringToObject(responseBody, AccessToken.class);
		_logger.debug("accessToken " + accessToken);
		if(accessToken.getErrcode()== 0){
			return accessToken.getAccess_token();
		}
		return "";
	}
	
	


	public String getCorpid() {
		return corpid;
	}


	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}


	public String getCorpsecret() {
		return corpsecret;
	}


	public void setCorpsecret(String corpsecret) {
		this.corpsecret = corpsecret;
	}


	public static void main(String[] args) {

	}

}
