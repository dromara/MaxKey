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
 

package org.dromara.maxkey.web.contorller;

import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authn.session.VisitedDto;
import org.dromara.maxkey.authz.oauth2.provider.token.DefaultTokenServices;
import org.dromara.maxkey.authz.singlelogout.DefaultSingleLogout;
import org.dromara.maxkey.authz.singlelogout.LogoutType;
import org.dromara.maxkey.authz.singlelogout.SamlSingleLogout;
import org.dromara.maxkey.authz.singlelogout.SingleLogout;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.constants.ConstsProtocols;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "1-3-单点注销接口文档模块")
@Controller
public class LogoutEndpoint {
	private static Logger logger = LoggerFactory.getLogger(LogoutEndpoint.class);

	@Autowired 
    ApplicationConfig applicationConfig;
	
	@Autowired
    SessionManager sessionManager;
	
	@Autowired
	DefaultTokenServices oauth20TokenServices;
	
	/**
	 * for front end
	 * @param currentUser
	 * @return ResponseEntity
	 */
	@Operation(summary = "前端注销接口", description = "前端注销接口",method="GET")
	@GetMapping(value={"/logout"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
 	public  Message<String> logout(@CurrentUser UserInfo currentUser){
		//if logined in have onlineTicket ,need remove or logout back
		String sessionId = currentUser.getSessionId();
 		Session session = sessionManager.get(sessionId);
 		if(session != null) {
 			logger.debug("/logout frontend clean Session id {}",session.getId());
	 		Set<Entry<String, VisitedDto>> entrySet = session.getVisited().entrySet();
	 
	        Iterator<Entry<String, VisitedDto>> iterator = entrySet.iterator();
	        while (iterator.hasNext()) {
	            Entry<String, VisitedDto> mapEntry = iterator.next();
	            VisitedDto visited = mapEntry.getValue();
	            logger.debug("App Id : {} , {} " ,  mapEntry.getKey() ,mapEntry.getValue());
	            if( mapEntry.getValue().getLogoutType() == LogoutType.BACK_CHANNEL){
	                SingleLogout singleLogout;
	                if(mapEntry.getValue().getProtocol().equalsIgnoreCase(ConstsProtocols.CAS)) {
	                    singleLogout =new SamlSingleLogout();
	                }else {
	                    singleLogout = new DefaultSingleLogout();
	                }
	                singleLogout.sendRequest(session.getAuthentication(), visited);
	            }
	            //oauth , oidc revoke token
	            if(visited.getProtocol().equalsIgnoreCase(ConstsProtocols.OAUTH20)
	            		||visited.getProtocol().equalsIgnoreCase(ConstsProtocols.OAUTH21)
	            		||visited.getProtocol().equalsIgnoreCase(ConstsProtocols.OPEN_ID_CONNECT10)) {
	            	oauth20TokenServices.revokeToken(visited.getToken());
	            	logger.debug("revoke token");
	            }
	        }
	        //terminate session
	        sessionManager.terminate(
	        		session.getId(), 
	        		currentUser.getId(),
	        		currentUser.getUsername());
 		}
 		return new Message<String>();
 	}
	
	@Operation(summary = "单点注销接口", description = "redirect_uri跳转地址",method="GET")
	@GetMapping({"/force/logout"})
 	public ModelAndView forceLogout(HttpServletRequest request,
 				@RequestParam(value = "redirect_uri",required = false) String redirect_uri){
		//invalidate http session
		logger.debug("/force/logout http Session id {}",request.getSession().getId());
		request.getSession().invalidate();
		StringBuffer logoutUrl = new StringBuffer("");
		logoutUrl.append(applicationConfig.getFrontendUri()).append("/#/passport/logout");
		if(StringUtils.isNotBlank(redirect_uri)) {
			logoutUrl.append("?")
				.append("redirect_uri=").append(redirect_uri);
		}
		ModelAndView modelAndView=new ModelAndView("redirect");
		modelAndView.addObject("redirect_uri", logoutUrl);
		return modelAndView;
 	}
}
