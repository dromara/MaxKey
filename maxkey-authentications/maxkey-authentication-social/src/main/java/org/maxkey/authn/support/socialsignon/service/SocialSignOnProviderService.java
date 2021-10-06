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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.maxkey.configuration.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.*;



public class SocialSignOnProviderService{
	private static Logger _logger = LoggerFactory.getLogger(SocialSignOnProviderService.class);
	
	List<SocialSignOnProvider> socialSignOnProviders = new ArrayList<SocialSignOnProvider>();
	
	HashMap<String ,SocialSignOnProvider>socialSignOnProviderMaps=new HashMap<String ,SocialSignOnProvider>();
	
	
	public SocialSignOnProvider get(String provider){
		return socialSignOnProviderMaps.get(provider);
	}
	
	public AuthRequest  getAuthRequest(String provider,ApplicationConfig applicationConfig)  throws Exception {
		AuthRequest authRequest = null;
		AuthConfig authConfig = AuthConfig.builder()
				.clientId(this.get(provider).getClientId())
				.clientSecret(this.get(provider).getClientSecret())
				.redirectUri(applicationConfig.getServerPrefix()+ "/logon/oauth20/callback/"+provider)
				.build();
		
		if(provider.equalsIgnoreCase("WeChatOpen")) {
			authRequest = new AuthWeChatOpenRequest(authConfig);
		}else if(provider.equalsIgnoreCase("sinaweibo")) {
			authRequest = new AuthWeiboRequest(authConfig);
		}else if(provider.equalsIgnoreCase("qq")) {
			authRequest = new AuthQqRequest(authConfig);
		}else if(provider.equalsIgnoreCase("Alipay")) {
		    String alipayPublicKey = "";
			authRequest = new AuthAlipayRequest(authConfig,alipayPublicKey);
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
		}else if(provider.equalsIgnoreCase("gitee")) {
            authRequest = new AuthGiteeRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Baidu")) {
            authRequest = new AuthBaiduRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Douyin")) {
            authRequest = new AuthDouyinRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Eleme")) {
            authRequest = new AuthElemeRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Feishu")) {
            authRequest = new AuthFeishuRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Github")) {
            authRequest = new AuthGithubRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Gitlab")) {
            authRequest = new AuthGitlabRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Huawei")) {
            authRequest = new AuthHuaweiRequest(authConfig);
        }else if(provider.equalsIgnoreCase("jd")) {
            authRequest = new AuthJdRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Meituan")) {
            authRequest = new AuthMeituanRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Mi")) {
            authRequest = new AuthMiRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Oschina")) {
            authRequest = new AuthOschinaRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Taobao")) {
            authRequest = new AuthTaobaoRequest(authConfig);
        }else if(provider.equalsIgnoreCase("Toutiao")) {
            authRequest = new AuthToutiaoRequest(authConfig);
        }else if(provider.equalsIgnoreCase("WeChatQyQrcode")) {
            authRequest = new AuthWeChatEnterpriseQrcodeRequest(authConfig);
        }else if(provider.equalsIgnoreCase("workweixin")) {
            authRequest = new AuthWeChatEnterpriseWebRequest(authConfig);
        }
		
		return authRequest;
	}
	
	public String getAccountId(String provider,AuthResponse<?> authResponse)  throws Exception {
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
	    
		for(SocialSignOnProvider socialSignOnProvider : socialSignOnProviders){
		    socialSignOnProviderMaps.put(socialSignOnProvider.getProvider(), socialSignOnProvider);
		    
		    if(!socialSignOnProvider.isHidden()) {
		        this.socialSignOnProviders.add(socialSignOnProvider);
		    }
		}
		
		_logger.debug("social SignOn Providers {}" , this.socialSignOnProviders);
	}
	
}
