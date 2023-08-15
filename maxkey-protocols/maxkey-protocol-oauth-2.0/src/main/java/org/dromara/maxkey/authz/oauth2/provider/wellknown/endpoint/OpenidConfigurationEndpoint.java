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

import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.authz.oauth2.provider.endpoint.AbstractEndpoint;
import org.dromara.maxkey.authz.oauth2.provider.wellknown.OpenidConfiguration;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.dromara.maxkey.pretty.impl.JsonPretty;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.cloud.commons.lang.StringUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "2-1-OAuth v2.0 API文档模块")
@Controller
public class OpenidConfigurationEndpoint extends AbstractEndpoint {
	final static Logger _logger = LoggerFactory.getLogger(OpenidConfigurationEndpoint.class);
	

	@Operation(summary = "OpenID Connect metadata 元数据接口", description = "参数client_id",method="GET,POST")
	@RequestMapping(
			value = {
					OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/.well-known/openid-configuration"},
			produces = "application/json",
			method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String  configuration(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "client_id", required = false) String client_id) {
		return configurationMetadata(request,response, null,client_id);
	}
	
	@Operation(summary = "OpenID Connect metadata 元数据接口", description = "参数client_id",method="GET,POST")
	@RequestMapping(
			value = {
					OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/{instId}/.well-known/openid-configuration"},
			produces = "application/json",
			method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String  configurationMetadata(
			HttpServletRequest request,
			HttpServletResponse response, 
			@PathVariable("instId") String instId,
			@RequestParam(value = "client_id", required = false) String client_id) {
		_logger.debug("instId {} , client_id {}" , instId ,client_id);
		
		String baseUrl = WebContext.getContextPath(true);
		
		ClientDetails  clientDetails = null;
		
		if(StringUtils.isNotBlank(client_id)) {
			try {
				clientDetails = getClientDetailsService().loadClientByClientId(client_id,true);
			}catch(Exception e) {
				_logger.error("getClientDetailsService", e);
			}
		}
		
		OpenidConfiguration openidConfig = new OpenidConfiguration();
		openidConfig.setRequest_parameter_supported(true);
		openidConfig.setAuthorization_endpoint(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/authorize");
		openidConfig.setToken_endpoint(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/token");
		openidConfig.setIntrospection_endpoint(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/introspect");
		openidConfig.setUserinfo_endpoint(baseUrl + "/api/connect/v10/userinfo");
		openidConfig.setEnd_session_endpoint(baseUrl + "/force/logout");
		
		if(clientDetails != null) {
			openidConfig.setClient_id(client_id);
			openidConfig.setJwks_uri(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/jwks?client_id=" + clientDetails.getClientId());
	
			Set<String>  introspection_endpoint_auth_methods_supported = new HashSet<String>();
			introspection_endpoint_auth_methods_supported.add("client_secret_basic");
			openidConfig.setIntrospection_endpoint_auth_methods_supported(introspection_endpoint_auth_methods_supported);                                                  
			                                                  
			openidConfig.setIssuer(clientDetails.getIssuer());
			openidConfig.setResponse_types_supported(clientDetails.getAuthorizedGrantTypes());
			
			Set<String>  response_modes_supported = new HashSet<String>();
			response_modes_supported.add("query");
			response_modes_supported.add("form_post");
			openidConfig.setResponse_modes_supported(response_modes_supported);
			
			openidConfig.setGrant_types_supported(clientDetails.getAuthorizedGrantTypes());
			openidConfig.setClaims_supported(clientDetails.getScope());
			
			
			Set<String>  id_token_signing_alg_values_supported = new HashSet<String>();
			id_token_signing_alg_values_supported.add(clientDetails.getSignature().toUpperCase());
			openidConfig.setId_token_signing_alg_values_supported(id_token_signing_alg_values_supported);
			
			openidConfig.setScopes_supported(clientDetails.getScope());
			
			Set<String>  token_endpoint_auth_methods_supported = new HashSet<String>();
			token_endpoint_auth_methods_supported.add("client_secret_basic");
			token_endpoint_auth_methods_supported.add("client_secret_post");
			token_endpoint_auth_methods_supported.add("none");
			openidConfig.setToken_endpoint_auth_methods_supported(token_endpoint_auth_methods_supported);
			
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
			
			openidConfig.setClaims_supported(claims_supported);
		}else {
			openidConfig.setClient_id(client_id);
			openidConfig.setJwks_uri(baseUrl + OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/jwks");
			
			Set<String>  introspection_endpoint_auth_methods_supported = new HashSet<String>();
			introspection_endpoint_auth_methods_supported.add("client_secret_basic");
			openidConfig.setIntrospection_endpoint_auth_methods_supported(introspection_endpoint_auth_methods_supported);                                                  
			                                                  
			openidConfig.setIssuer(baseUrl + "/maxkey");
			Set<String>  response_types_supported = new HashSet<String>();
			response_types_supported.add("code");
			response_types_supported.add("code id_token");
			response_types_supported.add("id_token");
			openidConfig.setResponse_types_supported(response_types_supported);
			
			Set<String>  response_modes_supported = new HashSet<String>();
			response_modes_supported.add("query");
			response_modes_supported.add("form_post");
			openidConfig.setResponse_modes_supported(response_modes_supported);
			
			Set<String>  grant_types_supported = new HashSet<String>();
			grant_types_supported.add("authorization_code");
			grant_types_supported.add("refresh_token");
			grant_types_supported.add("password");
			grant_types_supported.add("client_credentials");
			openidConfig.setGrant_types_supported(grant_types_supported);
			
			Set<String>  id_token_signing_alg_values_supported = new HashSet<String>();
			id_token_signing_alg_values_supported.add("RS256");
			openidConfig.setId_token_signing_alg_values_supported(id_token_signing_alg_values_supported);
			
			Set<String>  scopes_supported = new HashSet<String>();
			scopes_supported.add("openid");
			scopes_supported.add("email");
			scopes_supported.add("profile");
			scopes_supported.add("address");
			scopes_supported.add("phone");
			openidConfig.setScopes_supported(scopes_supported);
			
			Set<String>  token_endpoint_auth_methods_supported = new HashSet<String>();
			token_endpoint_auth_methods_supported.add("client_secret_basic");
			token_endpoint_auth_methods_supported.add("client_secret_post");
			token_endpoint_auth_methods_supported.add("none");
			openidConfig.setToken_endpoint_auth_methods_supported(token_endpoint_auth_methods_supported);
			
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
			
			openidConfig.setClaims_supported(claims_supported);
		}
		
		return JsonPretty.getInstance().format(openidConfig,true);
	}
}
