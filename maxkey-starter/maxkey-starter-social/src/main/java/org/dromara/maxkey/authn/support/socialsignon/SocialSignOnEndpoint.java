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
 

/**
 * 
 */
package org.dromara.maxkey.authn.support.socialsignon;

import me.zhyd.oauth.request.AuthMaxkeyRequest;
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.authn.jwt.AuthJwt;
import org.dromara.maxkey.constants.ConstsLoginType;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.SocialsAssociate;
import org.dromara.maxkey.entity.SocialsProvider;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.uuid.UUID;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import me.zhyd.oauth.request.AuthRequest;

import java.util.Map;

/**
 * @author Crystal.Sea
 *
 */
@RestController
@RequestMapping(value = "/logon/oauth20")
public class SocialSignOnEndpoint  extends AbstractSocialSignOnEndpoint{
	static final  Logger _logger = LoggerFactory.getLogger(SocialSignOnEndpoint.class);

	@GetMapping("/authorize/{provider}")
	public Message<Object> authorize( HttpServletRequest request,@PathVariable("provider") String provider) {
		_logger.trace("SocialSignOn provider : {}" , provider);
		String instId = WebContext.getInst().getId();
		String originURL =WebContext.getContextPath(request,false);
    	String authorizationUrl =
				buildAuthRequest(
						instId,
						provider,
						originURL + applicationConfig.getFrontendUri()
				).authorize(authTokenService.genRandomJwt());

		_logger.trace("authorize SocialSignOn : {}" , authorizationUrl);
		return new Message<Object>(authorizationUrl);
	}

	@GetMapping("/scanqrcode/{provider}")
	public Message<SocialsProvider> scanQRCode(HttpServletRequest request,@PathVariable("provider") String provider) {
		String instId = WebContext.getInst().getId();
		String originURL =WebContext.getContextPath(request,false);
	    AuthRequest authRequest = 
	    		buildAuthRequest(
						instId,
						provider,
						originURL + applicationConfig.getFrontendUri());
	    SocialsProvider scanQrProvider = null;
	    if(authRequest != null ) {
	        String state = UUID.generate().toString();
		    //String state = authTokenService.genRandomJwt();
		    authRequest.authorize(state);
		    
			SocialsProvider socialSignOnProvider = socialSignOnProviderService.get(instId,provider);
			scanQrProvider = new SocialsProvider(socialSignOnProvider);
			scanQrProvider.setState(state);
			scanQrProvider.setRedirectUri(
					socialSignOnProviderService.getRedirectUri(
							originURL + applicationConfig.getFrontendUri(), provider));
			//缓存state票据在缓存或者是redis中五分钟过期
			if (provider.equalsIgnoreCase(AuthMaxkeyRequest.KEY)) {
				socialSignOnProviderService.setToken(state);
			}
	    }else {
	    	 _logger.error("build authRequest fail .");
	    }
		
		return new Message<>(scanQrProvider);
	}

	@GetMapping("/bind/{provider}")
	public Message<AuthJwt> bind(@PathVariable("provider") String provider,
								  @CurrentUser UserInfo userInfo,
								  HttpServletRequest request) {
		 //auth call back may exception 
	    try {
	    	String originURL = WebContext.getContextPath(request,false);
	    	SocialsAssociate socialsAssociate = 
	    			this.authCallback(userInfo.getInstId(),provider,originURL + applicationConfig.getFrontendUri());
		    socialsAssociate.setSocialUserInfo(accountJsonString);
		    socialsAssociate.setUserId(userInfo.getId());
			socialsAssociate.setUsername(userInfo.getUsername());
			socialsAssociate.setInstId(userInfo.getInstId());
			_logger.debug("Social Bind : {}",socialsAssociate);
			this.socialsAssociateService.delete(socialsAssociate);
			this.socialsAssociateService.insert(socialsAssociate);
			return new Message<>();
	    }catch(Exception e) {
	        _logger.error("callback Exception  ",e);
	    }
	    return new Message<>(Message.ERROR);
	}

	@GetMapping("/callback/{provider}")
	public Message<AuthJwt> callback(@PathVariable("provider") String provider,HttpServletRequest request) {
		 //auth call back may exception 
	    try {
	    	String originURL =WebContext.getContextPath(request,false);
	    	String instId = WebContext.getInst().getId();
	    	SocialsAssociate socialsAssociate = 
	    			this.authCallback(instId,provider,originURL + applicationConfig.getFrontendUri());

			SocialsAssociate socialssssociate1 = this.socialsAssociateService.get(socialsAssociate);
		
	    	_logger.debug("Loaded SocialSignOn Socials Associate : {}",socialssssociate1);
		
	    	if (null == socialssssociate1) {
				//如果存在第三方ID并且在数据库无法找到映射关系，则进行绑定逻辑
				if (StringUtils.isNotEmpty(socialsAssociate.getSocialUserId())) {
					//返回message为第三方用户标识
					return new Message<>(Message.PROMPT,socialsAssociate.getSocialUserId());
				}
			}

			socialsAssociate = socialssssociate1;
	    	if(socialsAssociate != null) {
	    		_logger.debug("Social Sign On from {} mapping to user {}",
		                socialsAssociate.getProvider(),socialsAssociate.getUsername());
		    	LoginCredential loginCredential =new LoginCredential(
		    			socialsAssociate.getUsername(),"",ConstsLoginType.SOCIALSIGNON);
		    	SocialsProvider socialSignOnProvider = socialSignOnProviderService.get(instId,provider);
		    	loginCredential.setProvider(socialSignOnProvider.getProviderName());
		    	
		    	Authentication  authentication = authenticationProvider.authenticate(loginCredential,true);
		    	socialsAssociate.setSocialUserInfo(accountJsonString);
			
		    	this.socialsAssociateService.update(socialsAssociate);
		    	return new Message<>(authTokenService.genAuthJwt(authentication));
	    	}else {
	    		
	    	}
	    }catch(Exception e) {
	    	 _logger.error("callback Exception  ",e);
	    	 
	    }
	    return new Message<>(Message.ERROR);
	}


	/**
	 * 提供给第三方应用关联用户接口
	 * @return
	 */
	@PostMapping("/workweixin/qr/auth/login")
	public Message<AuthJwt> qrAuthLogin(
			@RequestParam Map<String, String> param,
			HttpServletRequest request) {

		try {
			if (null == param){
				return new Message<>(Message.ERROR);
			}
			String token = param.get("token");
			String username = param.get("username");
			//判断token是否合法
			String redisusername = this.socialSignOnProviderService.getToken(token);
			if (StringUtils.isNotEmpty(redisusername)){
				//设置token和用户绑定
				boolean flag = this.socialSignOnProviderService.bindtoken(token,username);
				if (flag) {
					return new Message<>();
				}
			} else {
				return new Message<>(Message.WARNING,"Invalid token");
			}
		}catch(Exception e) {
			_logger.error("qrAuthLogin Exception  ",e);
		}
		return new Message<>(Message.ERROR);
	}


	/**
	 * maxkey 监听扫码回调
	 * @param provider
	 * @param state
	 * @param request
	 * @return
	 */
	@PostMapping("/qrcallback/{provider}/{state}")
	public Message<AuthJwt> qrcallback(@PathVariable("provider") String provider,@PathVariable("state") String state,
										HttpServletRequest request) {
		try {
			//判断只有maxkey扫码
			if (!provider.equalsIgnoreCase(AuthMaxkeyRequest.KEY)) {
				return new Message<>(Message.ERROR);
			}

			String loginName = socialSignOnProviderService.getToken(state);
			if (StringUtils.isEmpty(loginName)) {
				//二维码过期
				return new Message<>(Message.PROMPT);
			}
			if("-1".equalsIgnoreCase(loginName)){
				//暂无用户扫码
				return new Message<>(Message.WARNING);
			}
			String instId = WebContext.getInst().getId();

			SocialsAssociate socialsAssociate = new SocialsAssociate();
			socialsAssociate.setProvider(provider);
			socialsAssociate.setSocialUserId(loginName);
			socialsAssociate.setInstId(instId);


			socialsAssociate = this.socialsAssociateService.get(socialsAssociate);

			_logger.debug("qrcallback Loaded SocialSignOn Socials Associate : {}",socialsAssociate);

			if(null == socialsAssociate) {
				return new Message<>(Message.ERROR);
			}

			LoginCredential loginCredential =new LoginCredential(
					socialsAssociate.getUsername(),"",ConstsLoginType.SOCIALSIGNON);
			SocialsProvider socialSignOnProvider = socialSignOnProviderService.get(instId,provider);
			loginCredential.setProvider(socialSignOnProvider.getProviderName());

			Authentication  authentication = authenticationProvider.authenticate(loginCredential,true);
			socialsAssociate.setSocialUserInfo(accountJsonString);

			this.socialsAssociateService.update(socialsAssociate);
			return new Message<>(authTokenService.genAuthJwt(authentication));
		}catch(Exception e) {
			_logger.error("qrcallback Exception  ",e);
			return new Message<>(Message.ERROR);
		}
	}
}
