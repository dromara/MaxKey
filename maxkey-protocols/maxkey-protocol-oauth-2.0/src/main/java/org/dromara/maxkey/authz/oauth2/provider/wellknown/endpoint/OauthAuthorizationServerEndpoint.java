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
 

package org.dromara.maxkey.authz.oauth2.provider.wellknown.endpoint;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.provider.endpoint.AbstractEndpoint;
import org.dromara.maxkey.authz.oauth2.provider.wellknown.OauthServerConfiguration;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "2-1-OAuth v2.0 API文档模块")
@Controller
public class OauthAuthorizationServerEndpoint extends AbstractEndpoint {
	static final  Logger _logger = LoggerFactory.getLogger(OauthAuthorizationServerEndpoint.class);
	
	@Operation(summary = "OAuth v2 metadata 元数据接口", description = "参数client_id",method="GET,POST")
	@RequestMapping(value = {OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/.well-known/oauth-authorization-server"},
					produces = "application/json",
					method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public OauthServerConfiguration  configurationByParam(
			@RequestParam(value = "inst_id", required = false) String inst_id,
			@RequestParam(value = "client_id", required = false) String client_id) {
		return configurationMetadata( inst_id,client_id,"RequestParam");
	}
	
	@Operation(summary = "OAuth v2 metadata 元数据接口", description = "参数instId,client_id",method="GET,POST")
	@RequestMapping(value = {OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/{instId}/{clientId}/.well-known/oauth-authorization-server"},
					produces = "application/json",
					method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public OauthServerConfiguration  configurationByPath(
			@PathVariable("instId") String instId,
			@PathVariable(value = "clientId", required = false) String clientId) {
		return configurationMetadata(instId,clientId,"PathVariable");
	}
	
	public OauthServerConfiguration  configurationMetadata(String instId,String clientId,String param) {
		_logger.debug("instId {} , client_id {}" , instId ,clientId);
		
		String baseUrl = WebContext.getContextPath(true);
		
		ClientDetails  clientDetails = null;
		
		if(StringUtils.isNotBlank(clientId)) {
			try {
				clientDetails = getClientDetailsService().loadClientByClientId(clientId,true);
			}catch(Exception e) {
				_logger.error("getClientDetailsService", e);
			}
		}
		
		OauthServerConfiguration oauthConfig = new OauthServerConfiguration();
		oauthConfig.setRequest_parameter_supported(true);
		oauthConfig.setAuthorization_endpoint(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/authorize");
		oauthConfig.setToken_endpoint(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/token");
		oauthConfig.setIntrospection_endpoint(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/introspect");
		oauthConfig.setUserinfo_endpoint(baseUrl + "/api/oauth/v20/me");
		oauthConfig.setEnd_session_endpoint(baseUrl + "/force/logout");
		
		Set<String>  code_challenge_methods_supported = new HashSet<String>();
		code_challenge_methods_supported.add("S256");
		oauthConfig.setCode_challenge_methods_supported(code_challenge_methods_supported);
		
		if(clientDetails != null) {
			oauthConfig.setClient_id(clientId);
			if(param.equals("RequestParam")){
				StringBuffer jwksUri = new StringBuffer(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/jwks");
				jwksUri.append("?");
				jwksUri.append("client_id").append("=").append(clientDetails.getClientId());
				if(StringUtils.isNotBlank(instId)) {
					jwksUri.append("&").append("inst_id").append("=").append(instId);
				}
				oauthConfig.setJwks_uri(jwksUri.toString());
			}else {
				oauthConfig.setJwks_uri(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/"+instId+"/"+clientId+"/jwks");
			}

			Set<String>  introspection_endpoint_auth_methods_supported = new HashSet<String>();
			introspection_endpoint_auth_methods_supported.add("client_secret_basic");
			oauthConfig.setIntrospection_endpoint_auth_methods_supported(introspection_endpoint_auth_methods_supported);                                                  
			                                                  
			oauthConfig.setIssuer(clientDetails.getIssuer());
			Set<String> response_types_supported =clientDetails.getAuthorizedGrantTypes();
			if(response_types_supported.contains("authorization_code")) {
				response_types_supported.add("code");
			}
			oauthConfig.setResponse_types_supported(response_types_supported);
			
			Set<String>  response_modes_supported = new HashSet<String>();
			response_modes_supported.add("query");
			response_modes_supported.add("form_post");
			oauthConfig.setResponse_modes_supported(response_modes_supported);
			
			oauthConfig.setGrant_types_supported(clientDetails.getAuthorizedGrantTypes());
			oauthConfig.setClaims_supported(clientDetails.getScope());
			
			
			Set<String>  id_token_signing_alg_values_supported = new HashSet<String>();
			id_token_signing_alg_values_supported.add(clientDetails.getSignature().toUpperCase());
			oauthConfig.setId_token_signing_alg_values_supported(id_token_signing_alg_values_supported);
			
			oauthConfig.setScopes_supported(clientDetails.getScope());
			
			Set<String>  token_endpoint_auth_methods_supported = new HashSet<String>();
			token_endpoint_auth_methods_supported.add("client_secret_basic");
			token_endpoint_auth_methods_supported.add("client_secret_post");
			token_endpoint_auth_methods_supported.add("none");
			oauthConfig.setToken_endpoint_auth_methods_supported(token_endpoint_auth_methods_supported);
			
			Set<String>  claims_supported = new HashSet<String>();
			claims_supported.add("iss");
			claims_supported.add("sub");
			claims_supported.add("aud");
			claims_supported.add("iat");
			claims_supported.add("exp");
			claims_supported.add("jti");
			claims_supported.add("auth_time");
			
			claims_supported.add("institution");
			claims_supported.add("online_ticket");
			
			claims_supported.add("userId");
			claims_supported.add("user");
			claims_supported.add("name");
			claims_supported.add("preferred_username");
			claims_supported.add("given_name");
			claims_supported.add("family_name");
			claims_supported.add("middle_name");
			claims_supported.add("nickname");
			claims_supported.add("displayName");
			claims_supported.add("departmentId");
			claims_supported.add("department");
			claims_supported.add("gender");
			claims_supported.add("zoneinfo");
			claims_supported.add("locale");
			claims_supported.add("updated_time");
			claims_supported.add("birthdate");
			
			claims_supported.add("email");
			claims_supported.add("email_verified");
			
			claims_supported.add("phone_number");
			claims_supported.add("phone_number_verified");
			
			claims_supported.add("address");
			claims_supported.add("country");
			claims_supported.add("region");
			claims_supported.add("locality");
			claims_supported.add("street_address");
			claims_supported.add("formatted");
			claims_supported.add("postal_code");
			
			oauthConfig.setClaims_supported(claims_supported);
		}else {
			oauthConfig.setClient_id(clientId);
			oauthConfig.setJwks_uri(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/jwks");
			
			Set<String>  introspection_endpoint_auth_methods_supported = new HashSet<String>();
			introspection_endpoint_auth_methods_supported.add("client_secret_basic");
			oauthConfig.setIntrospection_endpoint_auth_methods_supported(introspection_endpoint_auth_methods_supported);                                                  
			                                                  
			oauthConfig.setIssuer(baseUrl + "/maxkey");
			Set<String>  response_types_supported = new HashSet<String>();
			response_types_supported.add("code");
			response_types_supported.add("authorization_code");
			response_types_supported.add("code id_token");
			response_types_supported.add("id_token");
			oauthConfig.setResponse_types_supported(response_types_supported);
			
			Set<String>  response_modes_supported = new HashSet<String>();
			response_modes_supported.add("query");
			response_modes_supported.add("form_post");
			oauthConfig.setResponse_modes_supported(response_modes_supported);
			
			Set<String>  grant_types_supported = new HashSet<String>();
			grant_types_supported.add("authorization_code");
			grant_types_supported.add("refresh_token");
			grant_types_supported.add("password");
			grant_types_supported.add("client_credentials");
			oauthConfig.setGrant_types_supported(grant_types_supported);
			
			Set<String>  id_token_signing_alg_values_supported = new HashSet<String>();
			id_token_signing_alg_values_supported.add("RS256");
			oauthConfig.setId_token_signing_alg_values_supported(id_token_signing_alg_values_supported);
			
			Set<String>  scopes_supported = new HashSet<String>();
			scopes_supported.add("openid");
			scopes_supported.add("email");
			scopes_supported.add("profile");
			scopes_supported.add("address");
			scopes_supported.add("phone");
			oauthConfig.setScopes_supported(scopes_supported);
			
			Set<String>  token_endpoint_auth_methods_supported = new HashSet<String>();
			token_endpoint_auth_methods_supported.add("client_secret_basic");
			token_endpoint_auth_methods_supported.add("client_secret_post");
			token_endpoint_auth_methods_supported.add("none");
			oauthConfig.setToken_endpoint_auth_methods_supported(token_endpoint_auth_methods_supported);
			
			Set<String>  claims_supported = new HashSet<String>();
			claims_supported.add("iss");
			claims_supported.add("sub");
			claims_supported.add("aud");
			claims_supported.add("iat");
			claims_supported.add("exp");
			claims_supported.add("jti");
			claims_supported.add("auth_time");
			
			claims_supported.add("institution");
			claims_supported.add("online_ticket");
			
			claims_supported.add("userId");
			claims_supported.add("user");
			claims_supported.add("name");
			claims_supported.add("preferred_username");
			claims_supported.add("given_name");
			claims_supported.add("family_name");
			claims_supported.add("middle_name");
			claims_supported.add("nickname");
			claims_supported.add("displayName");
			claims_supported.add("departmentId");
			claims_supported.add("department");
			claims_supported.add("gender");
			claims_supported.add("zoneinfo");
			claims_supported.add("locale");
			claims_supported.add("updated_time");
			claims_supported.add("birthdate");
			
			claims_supported.add("email");
			claims_supported.add("email_verified");
			
			claims_supported.add("phone_number");
			claims_supported.add("phone_number_verified");
			
			claims_supported.add("address");
			claims_supported.add("country");
			claims_supported.add("region");
			claims_supported.add("locality");
			claims_supported.add("street_address");
			claims_supported.add("formatted");
			claims_supported.add("postal_code");
			
			oauthConfig.setClaims_supported(claims_supported);
		}
		return oauthConfig;
	}
}
