/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.authn.support.socialsignon.service;

import java.util.HashMap;
import java.util.List;

import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.*;



public class SocialSignOnProviderService{
	private static Logger _logger = LoggerFactory.getLogger(SocialSignOnProviderService.class);
	
	List<SocialSignOnProvider> socialSignOnProviders;
	
	HashMap<String ,SocialSignOnProvider>socialSignOnProviderMaps=new HashMap<String ,SocialSignOnProvider>();
	
	
	public SocialSignOnProvider get(String provider){
		return socialSignOnProviderMaps.get(provider);
	}
	
	public AuthRequest  getAuthRequest(String provider) {
		AuthRequest authRequest = null;
		AuthConfig authConfig = AuthConfig.builder()
				.clientId(this.get(provider).getClientId())
				.clientSecret(this.get(provider).getClientSecret())
				.redirectUri(WebContext.getHttpContextPath()+ "/logon/oauth20/callback/"+provider)
				.build();
		
		if(provider.equalsIgnoreCase("WeChatOpen")) {
			authRequest = new AuthWeChatOpenRequest(authConfig);
		}else if(provider.equalsIgnoreCase("sinaweibo")) {
			authRequest = new AuthWeiboRequest(authConfig);
		}else if(provider.equalsIgnoreCase("qq")) {
			authRequest = new AuthQqRequest(authConfig);
		}else if(provider.equalsIgnoreCase("Alipay")) {
			authRequest = new AuthAlipayRequest(authConfig);
		}else if(provider.equalsIgnoreCase("Twitter")) {
			authRequest = new AuthTwitterRequest(authConfig);
		}else if(provider.equalsIgnoreCase("google")) {
			authRequest = new AuthGoogleRequest(authConfig);
		}else if(provider.equalsIgnoreCase("microsoft")) {
			authRequest = new AuthMicrosoftRequest(authConfig);
		}else if(provider.equalsIgnoreCase("Linkedin")) {
			authRequest = new AuthLinkedinRequest(authConfig);
		}else if(provider.equalsIgnoreCase("DingTalk")) {
			authRequest = new AuthDingTalkRequest(authConfig);
		}
		
		
		
		return authRequest;
	}
	
	public String getAccountId(String provider,AuthResponse<?> authResponse) {
		if(provider.equalsIgnoreCase("WeChatOpen")) {
			return ((AuthUser)authResponse.getData()).getUuid();
		}else if(provider.equalsIgnoreCase("sinaweibo")) {
			return ((AuthUser)authResponse.getData()).getUuid();
		}else if(provider.equalsIgnoreCase("qq")) {
			return ((AuthUser)authResponse.getData()).getUuid();
		}else if(provider.equalsIgnoreCase("Alipay")) {
			return ((AuthUser)authResponse.getData()).getUuid();
		}else if(provider.equalsIgnoreCase("Twitter")) {
			return ((AuthUser)authResponse.getData()).getUuid();
		}else if(provider.equalsIgnoreCase("google")) {
			return ((AuthUser)authResponse.getData()).getUuid();
		}else if(provider.equalsIgnoreCase("microsoft")) {
			return ((AuthUser)authResponse.getData()).getUuid();
		}else if(provider.equalsIgnoreCase("Linkedin")) {
			return ((AuthUser)authResponse.getData()).getUuid();
		}else if(provider.equalsIgnoreCase("DingTalk")) {
			return ((AuthUser)authResponse.getData()).getUuid();
		}else {
		    return ((AuthUser)authResponse.getData()).getUuid();
		}
	}
	public List<SocialSignOnProvider> getSocialSignOnProviders() {
		return socialSignOnProviders;
	}

	public void setSocialSignOnProviders(
			List<SocialSignOnProvider> socialSignOnProviders) {
		
		this.socialSignOnProviders = socialSignOnProviders;
		
		for(SocialSignOnProvider socialSignOnProvider : socialSignOnProviders){
			socialSignOnProviderMaps.put(socialSignOnProvider.getProvider(), socialSignOnProvider);
		}
		
		_logger.debug(""+socialSignOnProviders);
	}
	
}
