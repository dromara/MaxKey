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
package org.dromara.maxkey.authz.cas.endpoint;


import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authz.cas.endpoint.ticket.CasConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Crystal.Sea
 * https://apereo.github.io/cas/6.2.x/protocol/CAS-Protocol.html
 */
@Tag(name = "2-3-CAS API文档模块")
@Controller
public class CasLogoutEndpoint  extends CasBaseAuthorizeEndpoint{

	static final  Logger _logger = LoggerFactory.getLogger(CasLogoutEndpoint.class);

	/**
	 * for cas logout then redirect to logout
	 * @param request
	 * @param response
	 * @param casService
	 * @return
	 */
	@Operation(summary = "CAS注销接口", description = "CAS注销接口",method="GET")
	@GetMapping(CasConstants.ENDPOINT.ENDPOINT_LOGOUT)
	public ModelAndView logout(HttpServletRequest request , HttpServletResponse response,
			@RequestParam(value = CasConstants.PARAMETER.SERVICE , required = false) String casService){
		StringBuffer logoutUrl = new StringBuffer("/force/logout");
		if(StringUtils.isNotBlank(casService)){
			logoutUrl.append("?").append("redirect_uri=").append(casService);
		}
		return WebContext.forward(logoutUrl.toString());
	}
}
