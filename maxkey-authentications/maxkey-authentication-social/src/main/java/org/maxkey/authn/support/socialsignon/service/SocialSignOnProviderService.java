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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsTimeInterval;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.SocialsProvider;
import org.maxkey.entity.SocialsProviderLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.*;

public class SocialSignOnProviderService{
	private static Logger _logger = LoggerFactory.getLogger(SocialSignOnProviderService.class);
	
	private static final String DEFAULT_SELECT_STATEMENT = "select * from mxk_socials_provider where instid = ? and status = 1  order by sortindex";
	
	protected static final Cache<String, SocialsProviderLogin> socialSignOnProvidersStore = 
            Caffeine.newBuilder()
                .expireAfterWrite(ConstsTimeInterval.ONE_HOUR, TimeUnit.MINUTES)
                .build();
	
	HashMap<String ,SocialsProvider>socialSignOnProviderMaps=new HashMap<String ,SocialsProvider>();
	
	private final JdbcTemplate jdbcTemplate;
	
	public SocialSignOnProviderService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate; 
    }

	public SocialsProvider get(String provider){
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
            //authRequest = new AuthFeishuRequest(authConfig);
        	authRequest = new AuthFeishu2Request(authConfig);
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
        }else if(provider.equalsIgnoreCase("welink")) {
            authRequest = new AuthHuaweiWeLinkRequest(authConfig);
        }
		
		
		
		return authRequest;
	}
	
	public String getAccountId(String provider,AuthResponse<?> authResponse)  throws Exception {
	    if(authResponse.getData() != null) {
	        AuthUser authUser = (AuthUser)authResponse.getData();
	        _logger.debug("AuthUser[{},{},{},{},{},{},{},{},{},{},{},{}]",
	                authUser.getUuid(),
	                authUser.getUsername(),
	                authUser.getNickname(),
	                authUser.getGender(),
	                authUser.getEmail(),
	                authUser.getCompany(),
	                authUser.getBlog(),
	                authUser.getLocation(),
	                authUser.getRemark(),
	                authUser.getSource(),
	                authUser.getBlog(),
	                authUser.getAvatar());
	        _logger.debug("RawUserInfo {}",authUser.getRawUserInfo());
    		if(provider.equalsIgnoreCase("WeChatOpen")) {
    			return authUser.getUuid();
    		}else if(provider.equalsIgnoreCase("sinaweibo")) {
    			return authUser.getUuid();
    		}else if(provider.equalsIgnoreCase("qq")) {
    			return authUser.getUuid();
    		}else if(provider.equalsIgnoreCase("Alipay")) {
    			return authUser.getUuid();
    		}else if(provider.equalsIgnoreCase("Twitter")) {
    			return authUser.getUuid();
    		}else if(provider.equalsIgnoreCase("google")) {
    			return authUser.getUuid();
    		}else if(provider.equalsIgnoreCase("microsoft")) {
    			return authUser.getUuid();
    		}else if(provider.equalsIgnoreCase("Linkedin")) {
    			return authUser.getUuid();
    		}else if(provider.equalsIgnoreCase("DingTalk")) {
    			return authUser.getUuid();
    		}else {
    		    return authUser.getUuid();
    		}
	    }
	    return null;
	}
	
	public SocialsProviderLogin loadSocialsProviders(String instId) {
		SocialsProviderLogin ssl = socialSignOnProvidersStore.getIfPresent(instId);
		if(ssl == null) {
		    List<SocialsProvider> listSocialsProvider=jdbcTemplate.query(
		            DEFAULT_SELECT_STATEMENT,
	                new SocialsProviderRowMapper(),instId);
	        _logger.trace("query SocialsProvider " + listSocialsProvider);
	        
	        
	        List<SocialsProvider> socialSignOnProviders = new ArrayList<SocialsProvider>();
	        ssl = new SocialsProviderLogin(socialSignOnProviders);
	        
	        for(SocialsProvider socialsProvider : listSocialsProvider){
	            socialSignOnProviderMaps.put(socialsProvider.getProvider(), socialsProvider);
	            _logger.debug("Social Provider " + socialsProvider.getProvider() 
	                                             + "(" + socialsProvider.getProviderName()+")");
	            if(!socialsProvider.getHidden().equals("true")) {
	                socialSignOnProviders.add(socialsProvider);
	            }
	            
	            if(socialsProvider.getProvider().equalsIgnoreCase("workweixin")) {
	            	ssl.setWorkWeixinLogin(socialsProvider.getScanCode());
	            }else if(socialsProvider.getProvider().equalsIgnoreCase("dingtalk")) {
	            	ssl.setDingTalkLogin(socialsProvider.getScanCode());
	            }else if(socialsProvider.getProvider().equalsIgnoreCase("feishu")) {
	            	ssl.setFeiShuLogin(socialsProvider.getScanCode());
	            }else if(socialsProvider.getProvider().equalsIgnoreCase("welink")) {
	            	ssl.setWeLinkLogin(socialsProvider.getScanCode());
	            }
	        }
	        
	        _logger.debug("social SignOn Providers Login {}" , ssl);
	       
	        socialSignOnProvidersStore.put(instId, ssl);
		}
        return ssl;
	}
	
	
	private final class SocialsProviderRowMapper  implements RowMapper<SocialsProvider> {
        @Override
        public SocialsProvider mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            SocialsProvider socialsProvider=new SocialsProvider();
            socialsProvider.setId(rs.getString("id"));
            socialsProvider.setProvider(rs.getString("provider"));
            socialsProvider.setProviderName(rs.getString("providername"));
            socialsProvider.setIcon(rs.getString("icon"));
            socialsProvider.setClientId(rs.getString("clientid"));
            String clientSecret= rs.getString("clientsecret");
            clientSecret = PasswordReciprocal.getInstance().decoder(clientSecret);
            socialsProvider.setClientSecret(clientSecret);
            socialsProvider.setAgentId(rs.getString("agentId"));
            socialsProvider.setHidden(rs.getString("hidden"));
            socialsProvider.setSortIndex(rs.getInt("sortindex"));
            socialsProvider.setScanCode(rs.getString("scancode"));
            socialsProvider.setStatus(rs.getInt("status"));
            return socialsProvider;
        }
    }
}
