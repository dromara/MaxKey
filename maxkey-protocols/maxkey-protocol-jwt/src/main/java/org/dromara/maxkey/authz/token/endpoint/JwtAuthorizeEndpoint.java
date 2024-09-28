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
package org.dromara.maxkey.authz.token.endpoint;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authz.endpoint.AuthorizeBaseEndpoint;
import org.dromara.maxkey.authz.endpoint.adapter.AbstractAuthorizeAdapter;
import org.dromara.maxkey.authz.jwt.endpoint.adapter.JwtAdapter;
import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.constants.ContentType;
import org.dromara.maxkey.crypto.jose.keystore.JWKSetKeyStore;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.apps.AppsJwtDetails;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AppsJwtDetailsService;
import org.dromara.maxkey.util.Instance;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Crystal.Sea
 *
 */
@Tag(name = "2-5-JWT令牌接口")
@Controller
public class JwtAuthorizeEndpoint  extends AuthorizeBaseEndpoint{

	static final  Logger _logger = LoggerFactory.getLogger(JwtAuthorizeEndpoint.class);
	
	@Autowired
	AppsJwtDetailsService jwtDetailsService;
	
	@Operation(summary = "JWT应用ID认证接口", description = "应用ID")
	@GetMapping("/authz/jwt/{id}")
	public ModelAndView authorize(
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("id") String id,
			@CurrentUser UserInfo currentUser){
		ModelAndView modelAndView=new ModelAndView();
		Apps  application = getApp(id);
		AppsJwtDetails jwtDetails = jwtDetailsService.getAppDetails(application.getId() , true);
		_logger.debug("jwtDetails {}",jwtDetails);
		jwtDetails.setAdapter(application.getAdapter());
		jwtDetails.setIsAdapter(application.getIsAdapter());
		
		AbstractAuthorizeAdapter adapter;
		if(ConstsBoolean.isTrue(jwtDetails.getIsAdapter())){
			Object jwtAdapter = Instance.newInstance(jwtDetails.getAdapter());
			try {
				BeanUtils.setProperty(jwtAdapter, "jwtDetails", jwtDetails);
			} catch (IllegalAccessException | InvocationTargetException e) {
				_logger.error("setProperty error . ", e);
			}
			adapter = (AbstractAuthorizeAdapter)jwtAdapter;
		}else{
			adapter =new JwtAdapter(jwtDetails);
		}
		
		adapter.setPrincipal(AuthorizationUtils.getPrincipal());
		
		adapter.generateInfo();
		//sign
		adapter.sign(null,jwtDetails.getSignatureKey(), jwtDetails.getSignature());
		//encrypt
		adapter.encrypt(null, jwtDetails.getAlgorithmKey(), jwtDetails.getAlgorithm());
		
		return adapter.authorize(modelAndView);
	}

	@Operation(summary = "JWT JWK元数据接口", description = "参数mxk_metadata_APPID")
	@GetMapping(value = "/metadata/jwt/" + WebConstants.MXK_METADATA_PREFIX + "{appid}.{mediaType}")
	@ResponseBody
	public String  metadata(HttpServletRequest request,
			HttpServletResponse response, 
			@PathVariable("appid") String appId, 
			@PathVariable("mediaType") String mediaType) {
		AppsJwtDetails jwtDetails = jwtDetailsService.getAppDetails(appId , true);
		if(jwtDetails != null) {
			String jwkSetString = "";
			if(!jwtDetails.getSignature().equalsIgnoreCase("none")) {
				jwkSetString = jwtDetails.getSignatureKey();
			}
			if(!jwtDetails.getAlgorithm().equalsIgnoreCase("none")) {
				if(StringUtils.isBlank(jwkSetString)) {
					jwkSetString = jwtDetails.getAlgorithmKey();
				}else {
					jwkSetString = jwkSetString + "," +jwtDetails.getAlgorithmKey();
				}
			}
			 
			JWKSetKeyStore jwkSetKeyStore = new JWKSetKeyStore("{\"keys\": [" + jwkSetString + "]}");
			if(StringUtils.isNotBlank(mediaType) 
					&& mediaType.equalsIgnoreCase("xml")) {
				response.setContentType(ContentType.APPLICATION_XML_UTF8);
			}else {
				response.setContentType(ContentType.APPLICATION_JSON_UTF8);
			}
			return jwkSetKeyStore.toString(mediaType);
			
		}
		return appId + " not exist. \n" + WebContext.version();
	}
}
