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
 

/**
 * 
 */
package org.maxkey.authn.support.socialsignon;

import javax.servlet.http.HttpServletRequest;

import org.maxkey.authn.LoginCredential;
import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.authn.jwt.AuthJwt;
import org.maxkey.authn.web.AuthorizationUtils;
import org.maxkey.constants.ConstsLoginType;
import org.maxkey.entity.Message;
import org.maxkey.entity.SocialsAssociate;
import org.maxkey.entity.SocialsProvider;
import org.maxkey.entity.UserInfo;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import me.zhyd.oauth.request.AuthRequest;

/**
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value = "/logon/oauth20")
public class SocialSignOnEndpoint  extends AbstractSocialSignOnEndpoint{
	final static Logger _logger = LoggerFactory.getLogger(SocialSignOnEndpoint.class);
    
	@RequestMapping(value={"/authorize/{provider}"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> authorize(HttpServletRequest request,
									@PathVariable String provider
									) {
		_logger.trace("SocialSignOn provider : " + provider);
		String instId = WebContext.getInst().getId();
    	String authorizationUrl = buildAuthRequest(instId,provider).authorize(authJwtService.genJwt());
		_logger.trace("authorize SocialSignOn : " + authorizationUrl);
		return new Message<Object>((Object)authorizationUrl).buildResponse();
	}

	@RequestMapping(value={"/scanqrcode/{provider}"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> scanQRCode(
							HttpServletRequest request,
							@PathVariable("provider") String provider) {
		String instId = WebContext.getInst().getId();
	    AuthRequest authRequest = buildAuthRequest(instId,provider);
	   
	    if(authRequest == null ) {
	        _logger.error("build authRequest fail .");
	    }
	    String state = authJwtService.genJwt();
	    authRequest.authorize(state);
	    
		SocialsProvider socialSignOnProvider = socialSignOnProviderService.get(instId,provider);
		SocialsProvider scanQrProvider = new SocialsProvider(socialSignOnProvider);
		scanQrProvider.setState(state);
		scanQrProvider.setRedirectUri(
				socialSignOnProviderService.getRedirectUri(WebContext.getBaseUri(), provider));
		
		return new Message<SocialsProvider>(scanQrProvider).buildResponse();
	}	
	
	
	@RequestMapping(value={"/bind/{provider}"}, method = RequestMethod.GET)
	public ResponseEntity<?> bind(@PathVariable String provider,@CurrentUser UserInfo userInfo) {
		 //auth call back may exception 
	    try {
	    	SocialsAssociate socialsAssociate = this.authCallback(userInfo.getInstId(),provider);
		    socialsAssociate.setSocialUserInfo(accountJsonString);
		    socialsAssociate.setUserId(userInfo.getId());
			socialsAssociate.setUsername(userInfo.getUsername());
			socialsAssociate.setInstId(userInfo.getInstId());
			//socialsAssociate.setAccessToken(JsonUtils.object2Json(accessToken));
			//socialsAssociate.setExAttribute(JsonUtils.object2Json(accessToken.getResponseObject()));
			_logger.debug("Social Bind : "+socialsAssociate);
			this.socialsAssociateService.delete(socialsAssociate);
			this.socialsAssociateService.insert(socialsAssociate);
			return new Message<AuthJwt>().buildResponse();
	    }catch(Exception e) {
	        _logger.error("callback Exception  ",e);
	    }
	    
	    return new Message<AuthJwt>(Message.ERROR).buildResponse();
	}

	@RequestMapping(value={"/callback/{provider}"}, method = RequestMethod.GET)
	public ResponseEntity<?> callback(@PathVariable String provider) {
		 //auth call back may exception 
	    try {
	    	String instId = WebContext.getInst().getId();
	    	SocialsAssociate socialsAssociate = this.authCallback(instId,provider);
		
	    	socialsAssociate=this.socialsAssociateService.get(socialsAssociate);
		
	    	_logger.debug("Loaded SocialSignOn Socials Associate : "+socialsAssociate);
		
	    	if(null == socialsAssociate) {
	    		return new Message<AuthJwt>(Message.ERROR).buildResponse();
	    	}
		
	    	_logger.debug("Social Sign On from {} mapping to user {}",
		                socialsAssociate.getProvider(),socialsAssociate.getUsername());
		
	    	LoginCredential loginCredential =new LoginCredential(
	    			socialsAssociate.getUsername(),"",ConstsLoginType.SOCIALSIGNON);
	    	SocialsProvider socialSignOnProvider = socialSignOnProviderService.get(instId,provider);
	    	loginCredential.setProvider(socialSignOnProvider.getProviderName());
	    	
	    	Authentication  authentication = authenticationProvider.authenticate(loginCredential,true);
	    	//socialsAssociate.setAccessToken(JsonUtils.object2Json(this.accessToken));
	    	socialsAssociate.setSocialUserInfo(accountJsonString);
	    	//socialsAssociate.setExAttribute(JsonUtils.object2Json(accessToken.getResponseObject()));
		
	    	this.socialsAssociateService.update(socialsAssociate);
	    	return new Message<AuthJwt>(authJwtService.genAuthJwt(authentication)).buildResponse();
	    }catch(Exception e) {
	    	 _logger.error("callback Exception  ",e);
	    	 return new Message<AuthJwt>(Message.ERROR).buildResponse();
	    }
	}
}
