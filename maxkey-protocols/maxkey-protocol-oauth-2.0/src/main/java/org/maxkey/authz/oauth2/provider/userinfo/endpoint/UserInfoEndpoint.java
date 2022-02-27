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
 

package org.maxkey.authz.oauth2.provider.userinfo.endpoint;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.maxkey.authz.oauth2.common.OAuth2Constants;
import org.maxkey.authz.oauth2.common.exceptions.OAuth2Exception;
import org.maxkey.authz.oauth2.provider.ClientDetailsService;
import org.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.maxkey.constants.ConstsBoolean;
import org.maxkey.entity.UserInfo;
import org.maxkey.entity.apps.Apps;
import org.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.maxkey.persistence.service.AppsService;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.AuthorizationHeaderUtils;
import org.maxkey.util.Instance;
import org.maxkey.util.JsonUtils;
import org.maxkey.util.StringGenerator;
import org.maxkey.web.HttpResponseAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "2-1-OAuth v2.0 API文档模块")
@Controller
public class UserInfoEndpoint {
	final static Logger _logger = LoggerFactory.getLogger(UserInfoEndpoint.class);	
	@Autowired
	@Qualifier("oauth20JdbcClientDetailsService")
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	@Qualifier("oauth20TokenServices")
	private DefaultTokenServices oauth20tokenServices;
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;
	
	@Autowired
	@Qualifier("appsService")
	protected AppsService appsService;

    @Autowired
    protected HttpResponseAdapter httpResponseAdapter;
	
    @Operation(summary = "OAuth 2.0 用户信息接口", description = "传递参数access_token",method="GET")
	@RequestMapping(value=OAuth2Constants.ENDPOINT.ENDPOINT_USERINFO, method={RequestMethod.POST, RequestMethod.GET}) 
	public void apiV20UserInfo(
			@RequestParam(value = "access_token", required = false) String access_token,
            HttpServletRequest request, 
            HttpServletResponse response) {	        
	        if(StringUtils.isBlank(access_token)) {
	        	//for header authorization bearer
	        	access_token = AuthorizationHeaderUtils.resolveBearer(request);
	        }
	        
			if (!StringGenerator.uuidMatches(access_token)) {
				httpResponseAdapter.write(response,JsonUtils.gson2Json(accessTokenFormatError(access_token)),"json"); 
			}
			
			String principal="";
			OAuth2Authentication oAuth2Authentication =null;
			try{
				 oAuth2Authentication = oauth20tokenServices.loadAuthentication(access_token);
				 
				 principal=((SigninPrincipal)oAuth2Authentication.getUserAuthentication().getPrincipal()).getUsername();
				 
				 String client_id= oAuth2Authentication.getOAuth2Request().getClientId();
				 ClientDetails clientDetails = 
						 clientDetailsService.loadClientByClientId(client_id,true);
				 
				 UserInfo userInfo=queryUserInfo(principal);
				 Apps app = appsService.get(client_id);
				 
				 AbstractAuthorizeAdapter adapter;
				 if(ConstsBoolean.isTrue(app.getIsAdapter())){
					adapter =(AbstractAuthorizeAdapter)Instance.newInstance(app.getAdapter());
					try {
						BeanUtils.setProperty(adapter, "clientDetails", clientDetails);
					} catch (IllegalAccessException | InvocationTargetException e) {
						_logger.error("setProperty error . ", e);
					}
				 }else{
					adapter =(AbstractAuthorizeAdapter)new OAuthDefaultUserInfoAdapter(clientDetails);
				 }
				 adapter.setAuthentication((SigninPrincipal)oAuth2Authentication.getUserAuthentication().getPrincipal());
				 adapter.setUserInfo(userInfo);
				 adapter.setApp(app);
				 
				Object jsonData = adapter.generateInfo();
				httpResponseAdapter.write(response,jsonData.toString(),"json"); 
			}catch(OAuth2Exception e){
				HashMap<String,Object>authzException=new HashMap<String,Object>();
				authzException.put(OAuth2Exception.ERROR, e.getOAuth2ErrorCode());
				authzException.put(OAuth2Exception.DESCRIPTION,e.getMessage());
				httpResponseAdapter.write(response,JsonUtils.gson2Json(authzException),"json"); 
			}
	}
	
	public HashMap<String,Object> accessTokenFormatError(String access_token){
		HashMap<String,Object>atfe=new HashMap<String,Object>();
		atfe.put(OAuth2Exception.ERROR, "token Format Invalid");
		atfe.put(OAuth2Exception.DESCRIPTION, "access Token Format Invalid , access_token : "+access_token);
		
		return atfe;
	}

	public  UserInfo queryUserInfo(String userId){
		_logger.debug("userId : "+userId);
		UserInfo userInfo = (UserInfo) userInfoService.findByUsername(userId);
		return userInfo;
	}


	public void setOauth20tokenServices(DefaultTokenServices oauth20tokenServices) {
		this.oauth20tokenServices = oauth20tokenServices;
	}
	


	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

}
