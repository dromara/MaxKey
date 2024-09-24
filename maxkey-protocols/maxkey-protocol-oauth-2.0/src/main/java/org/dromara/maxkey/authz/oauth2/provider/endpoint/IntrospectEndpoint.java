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
 

package org.dromara.maxkey.authz.oauth2.provider.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.common.exceptions.OAuth2Exception;
import org.dromara.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.dromara.maxkey.util.AuthorizationHeader;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.util.RequestTokenUtils;
import org.dromara.maxkey.web.HttpResponseAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "2-1-OAuth v2.0 API文档模块")
@Controller
public class IntrospectEndpoint {
	static final  Logger _logger = LoggerFactory.getLogger(IntrospectEndpoint.class);	
	@Autowired
	@Qualifier("oauth20JdbcClientDetailsService")
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	@Qualifier("oauth20TokenServices")
	private DefaultTokenServices oauth20tokenServices;
	
	@Autowired
	ProviderManager oauth20ClientAuthenticationManager;

    @Autowired
    protected HttpResponseAdapter httpResponseAdapter;
	
    @Operation(summary = "OAuth 2.0 令牌验证接口", description = "请求参数access_token , header Authorization , token ",method="POST,GET")
	@RequestMapping(value=OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/introspect", method = {RequestMethod.POST, RequestMethod.GET}) 
	public void introspect(HttpServletRequest request, HttpServletResponse response) {	  
    	String access_token =  RequestTokenUtils.resolveAccessToken(request);
        _logger.debug("access_token {}" , access_token);
	    
		OAuth2Authentication oAuth2Authentication =null;
		Introspection introspection = new Introspection(access_token);
		try{
			 oAuth2Authentication = oauth20tokenServices.loadAuthentication(access_token);
			 if(oAuth2Authentication != null) {   
				 String sub = "";
				//userAuthentication not null , is password or code , 
				 if(oAuth2Authentication.getUserAuthentication() != null) {
					 sub = ((SignPrincipal)oAuth2Authentication.getUserAuthentication().getPrincipal()).getUsername();
				 }else {
					 //client_credentials
					 sub = oAuth2Authentication.getOAuth2Request().getClientId();
				 }
				 if(StringUtils.isNotBlank(sub)) {
					 introspection.setSub(sub,true);
				 }
			 }
		}catch(OAuth2Exception e){
			_logger.error("OAuth2Exception ", e);
		}
		
		httpResponseAdapter.write(response,JsonUtils.gsonToString(introspection),"json"); 
	}
	
    public boolean clientAuthenticate(AuthorizationHeader headerCredential) {
    	if(headerCredential != null){
			UsernamePasswordAuthenticationToken authenticationToken = null;
			if(headerCredential.isBasic()) {
			    if(StringUtils.isNotBlank(headerCredential.getUsername())&&
			    		StringUtils.isNotBlank(headerCredential.getCredential())
			    		) {
			    	UsernamePasswordAuthenticationToken authRequest = 
							new UsernamePasswordAuthenticationToken(
									headerCredential.getUsername(),
									headerCredential.getCredential());
			    	authenticationToken = (UsernamePasswordAuthenticationToken)oauth20ClientAuthenticationManager.authenticate(authRequest);
			    }
			}
			if(authenticationToken != null && authenticationToken.isAuthenticated()) {
				return true;
			}
		}
    	return false;
    }
    
	public void setOauth20tokenServices(DefaultTokenServices oauth20tokenServices) {
		this.oauth20tokenServices = oauth20tokenServices;
	}
	
	public class Introspection {
		
		String token;
		boolean active;
		String sub;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public String getSub() {
			return sub;
		}

		public void setSub(String sub,boolean active) {
			this.sub = sub;
			this.active = active;
		}

		public Introspection(String token) {
			this.token = token;
			this.active = false;
		}

		public Introspection(String token, boolean active, String sub) {
			this.token = token;
			this.active = active;
			this.sub = sub;
		}

	}

}
