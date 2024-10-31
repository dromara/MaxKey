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
 

package org.dromara.maxkey.authz.oauth2.provider.endpoint;

import org.dromara.maxkey.authz.oauth2.common.OAuth2Constants;
import org.dromara.maxkey.constants.ContentType;
import org.dromara.maxkey.crypto.jose.keystore.JWKSetKeyStore;
import org.dromara.maxkey.entity.apps.oauth2.provider.ClientDetails;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "2-1-OAuth v2.0 API文档模块")
@Controller
public class OauthJwksEndpoint extends AbstractEndpoint {
	static final  Logger _logger = LoggerFactory.getLogger(OauthJwksEndpoint.class);

	@Operation(summary = "OAuth JWk 元数据接口", description = "参数inst_id , client_id",method="GET")
	@RequestMapping(value = OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/jwks",
					method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String  keysMetadataByParam(HttpServletRequest request , HttpServletResponse response, 
			@RequestParam(value="inst_id",required = false) String inst_id,
			@RequestParam(value="client_id",required = false) String client_id) {
		return buildMetadata(request,response,inst_id,client_id,ContentType.JSON);
	}
	
	@Operation(summary = "OAuth JWk 元数据接口", description = "参数instId , clientId",method="GET")
	@RequestMapping(value = OAuth2Constants.ENDPOINT.ENDPOINT_BASE + "/{instId}/{clientId}/jwks",
					method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String  keysMetadatabyPath(HttpServletRequest request , HttpServletResponse response, 
			@PathVariable(value="instId") String instId,
			@PathVariable(value="clientId") String clientId) {
		return buildMetadata(request,response,instId,clientId,ContentType.JSON);
	}
	
	@Operation(summary = "OAuth JWk 元数据接口", description = "参数mxk_metadata_clientId",method="GET")
	@RequestMapping(
			value = "/metadata/oauth/v20/" + WebConstants.MXK_METADATA_PREFIX + "{clientId}.{mediaType}",
			method={RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String metadata(HttpServletRequest request , HttpServletResponse response,
			@PathVariable(value="clientId") String clientId,
			@PathVariable(value="mediaType") String mediaType) {
		return buildMetadata(request,response,null,clientId,mediaType);
	}
	
	public String  buildMetadata(HttpServletRequest request , HttpServletResponse response,
			String instId,String clientId,String mediaType){
		ClientDetails  clientDetails = null;
		try {
			clientDetails = getClientDetailsService().loadClientByClientId(clientId,true);
		}catch(Exception e) {
			_logger.error("getClientDetailsService", e);
		}
		if(clientDetails != null) {
			String jwkSetString = "";
			if(!clientDetails.getSignature().equalsIgnoreCase("none")) {
				jwkSetString = clientDetails.getSignatureKey();
			}
			if(!clientDetails.getAlgorithm().equalsIgnoreCase("none")) {
				if(!StringUtils.hasText(jwkSetString)) {
					jwkSetString = clientDetails.getAlgorithmKey();
				}else {
					jwkSetString = jwkSetString + "," +clientDetails.getAlgorithmKey();
				}
			}
			JWKSetKeyStore jwkSetKeyStore = new JWKSetKeyStore("{\"keys\": [" + jwkSetString + "]}");
			
			if(StringUtils.hasText(mediaType) && mediaType.equalsIgnoreCase(ContentType.XML)) {
				response.setContentType(ContentType.APPLICATION_XML_UTF8);
			}else {
				response.setContentType(ContentType.APPLICATION_JSON_UTF8);
			}
			return jwkSetKeyStore.toString(mediaType);
		} else {
			return clientId + " not exist . \n" + WebContext.version();
		}
	}

}
