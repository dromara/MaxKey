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
 

package org.dromara.maxkey.authn.support.socialsignon.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.authn.support.socialsignon.token.RedisTokenStore;
import org.dromara.maxkey.constants.ConstsTimeInterval;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.SocialsProvider;
import org.dromara.maxkey.entity.SocialsProviderLogin;
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
	private static final Logger _logger = LoggerFactory.getLogger(SocialSignOnProviderService.class);
	
	private static final String DEFAULT_SELECT_STATEMENT = "select * from mxk_socials_provider where instid = ? and status = 1  order by sortindex";
	
	protected static final Cache<String, SocialsProviderLogin> socialsProviderLoginStore = 
            Caffeine.newBuilder()
                .expireAfterWrite(ConstsTimeInterval.ONE_HOUR, TimeUnit.MINUTES)
                .build();
	
	HashMap<String ,SocialsProvider>socialSignOnProviderMaps = new HashMap<>();
	
	private final JdbcTemplate jdbcTemplate;


	RedisTokenStore redisTokenStore;
	
	public SocialSignOnProviderService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate; 
    }

	public SocialsProvider get(String instId,String provider){
		return socialSignOnProviderMaps.get(instId + "_" + provider);
	}
	public void setToken(String token){
		this.redisTokenStore.store(token);
	}

	public boolean bindtoken(String token,String loginName){
		return this.redisTokenStore.bindtoken(token,loginName);
	}

	public String getToken(String token){
		return this.redisTokenStore.get(token);
	}
	
	public String getRedirectUri(String baseUri,String provider) {
		return baseUri + "/passport/callback/"+provider;
	}
	
	public AuthRequest  getAuthRequest(String instId,String provider,String baseUri)  throws Exception {
		AuthRequest authRequest = null;
		AuthConfig authConfig = AuthConfig.builder()
				.clientId(this.get(instId,provider).getClientId())
				.clientSecret(this.get(instId,provider).getClientSecret())
				.redirectUri(getRedirectUri(baseUri , provider))
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
            authRequest = new AuthWeChatEnterpriseWebRequestCost(authConfig);
        }else if(provider.equalsIgnoreCase("welink")) {
            authRequest = new AuthHuaweiWeLinkRequest(authConfig);
        }else if(provider.equalsIgnoreCase("maxkey")) {
			authRequest = new AuthMaxkeyRequest(authConfig);
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
	
	public SocialsProviderLogin loadSocials(String instId) {
		SocialsProviderLogin socialsLogin = socialsProviderLoginStore.getIfPresent(instId);
		if(socialsLogin == null) {
		    List<SocialsProvider> listSocialsProvider = jdbcTemplate.query(
		            DEFAULT_SELECT_STATEMENT,
	                new SocialsProviderRowMapper(),instId);
	        _logger.trace("query SocialsProvider {}" , listSocialsProvider);
	        
	        List<SocialsProvider> socialSignOnProviders = new ArrayList<>();
	        socialsLogin = new SocialsProviderLogin(socialSignOnProviders);
	        for(SocialsProvider socialsProvider : listSocialsProvider){
	            _logger.debug("Social Provider {} ({})" ,
	            		socialsProvider.getProvider()  ,socialsProvider.getProviderName());
	            
	            if(socialsProvider.getDisplay().equals("true")) {
	                socialSignOnProviders.add(new SocialsProvider(socialsProvider));
	            }
	            
	            if(socialsProvider.getScanCode().equalsIgnoreCase("true")) {
	            	socialsLogin.setQrScan(socialsProvider.getProvider());
	            }
	            
	            //add to socialSignOnProviderMaps
	            socialSignOnProviderMaps.put(instId + "_" + socialsProvider.getProvider() , socialsProvider);
	        }
	        
	        _logger.debug("social SignOn Providers Login {}" , socialsLogin);
	       
	        socialsProviderLoginStore.put(instId, socialsLogin);
		}
        return socialsLogin;
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
            socialsProvider.setDisplay(rs.getString("display"));
            socialsProvider.setSortIndex(rs.getInt("sortindex"));
            socialsProvider.setScanCode(rs.getString("scancode"));
            socialsProvider.setStatus(rs.getInt("status"));
            socialsProvider.setInstId(rs.getString("instid"));
            return socialsProvider;
        }
    }


	public void setRedisTokenStore(RedisTokenStore redisTokenStore) {
		this.redisTokenStore = redisTokenStore;
	}
}
