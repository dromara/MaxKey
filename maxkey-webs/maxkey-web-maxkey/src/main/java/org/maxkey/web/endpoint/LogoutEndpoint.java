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
 

package org.maxkey.web.endpoint;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.maxkey.authn.SigninPrincipal;
import org.maxkey.authn.online.OnlineTicket;
import org.maxkey.authn.online.OnlineTicketServices;
import org.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.maxkey.authz.singlelogout.SamlSingleLogout;
import org.maxkey.authz.singlelogout.DefaultSingleLogout;
import org.maxkey.authz.singlelogout.LogoutType;
import org.maxkey.authz.singlelogout.SingleLogout;
import org.maxkey.configuration.ApplicationConfig;
import org.maxkey.constants.ConstsProtocols;
import org.maxkey.entity.apps.Apps;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "1-3-单点注销接口文档模块")
@Controller
public class LogoutEndpoint {
	
	private static Logger _logger = LoggerFactory.getLogger(LogoutEndpoint.class);
	
	public static final String RE_LOGIN_URL	=	"reLoginUrl";
	
	@Autowired
	@Qualifier("authenticationRealm")
	AbstractAuthenticationRealm authenticationRealm;
	
	@Autowired
	ApplicationConfig applicationConfig;
	
	@Autowired
    @Qualifier("onlineTicketServices")
    protected OnlineTicketServices onlineTicketServices;
	
	@Operation(summary = "单点注销接口", description = "reLoginUrl跳转地址",method="GET")
 	@RequestMapping(value={"/logout"})
 	public ModelAndView logout(
 					HttpServletRequest request, 
 					HttpServletResponse response,
 					@RequestParam(value=RE_LOGIN_URL,required=false) String reLoginUrl){
 		
 		return logoutModelAndView(request,response,"loggedout",reLoginUrl);
 	}
 	
	@Operation(summary = "登录超时接口", description = "",method="GET")
 	@RequestMapping(value={"/timeout"})
 	public ModelAndView timeout(HttpServletRequest request, HttpServletResponse response){
 		return logoutModelAndView(request,response,"timeout",null);
 	}
 	
 	
 	private ModelAndView logoutModelAndView(
 			HttpServletRequest request,
 			HttpServletResponse response,
 			String viewName,
 			String reLoginUrl){
 		ModelAndView modelAndView = new ModelAndView();
 		authenticationRealm.logout(response);
 		
 		if(reLoginUrl==null ||reLoginUrl.equals("")){
	 		SavedRequest  firstSavedRequest = (SavedRequest)WebContext.getAttribute(WebConstants.FIRST_SAVED_REQUEST_PARAMETER);
	 		reLoginUrl="/login";
	 		if(firstSavedRequest!=null){
	 			reLoginUrl= firstSavedRequest.getRedirectUrl();
	 			WebContext.removeAttribute(WebConstants.FIRST_SAVED_REQUEST_PARAMETER);
	 		}
 		}
 		
 		//not start with http or https
 		if(reLoginUrl!=null && !reLoginUrl.toLowerCase().startsWith("http")) {
 		    if(reLoginUrl.startsWith("/")) {
 		        reLoginUrl=request.getContextPath()+reLoginUrl;
 		    }else {
 		       reLoginUrl=request.getContextPath()+"/"+reLoginUrl;
 		    }
 		}
 		
 		_logger.debug("re Login URL : "+ reLoginUrl);
 		
 		modelAndView.addObject("reloginUrl",reLoginUrl);
 		
 		//if logined in have onlineTicket ,need remove or logout back
 		if(WebContext.getAuthentication() != null) {
 			String onlineTicketId = ((SigninPrincipal)WebContext.getAuthentication().getPrincipal()).getOnlineTicket().getTicketId();
 	 		OnlineTicket onlineTicket = onlineTicketServices.get(onlineTicketId);
 	 		if(onlineTicket != null) {
		 		Set<Entry<String, Apps>> entrySet = onlineTicket.getAuthorizedApps().entrySet();
		 
		        Iterator<Entry<String, Apps>> iterator = entrySet.iterator();
		        while (iterator.hasNext()) {
		            Entry<String, Apps> mapEntry = iterator.next();
		            _logger.debug("App Id : "+ mapEntry.getKey()+ " , " +mapEntry.getValue());
		            if( mapEntry.getValue().getLogoutType() == LogoutType.BACK_CHANNEL){
		                SingleLogout singleLogout;
		                if(mapEntry.getValue().getProtocol().equalsIgnoreCase(ConstsProtocols.CAS)) {
		                    singleLogout =new SamlSingleLogout();
		                }else {
		                    singleLogout = new DefaultSingleLogout();
		                }
		                singleLogout.sendRequest(onlineTicket.getAuthentication(), mapEntry.getValue());
		            }
		        }
		 		onlineTicketServices.remove(onlineTicketId);
 	 		}
 		}
 		//remove ONLINE_TICKET cookie
 		WebContext.expiryCookie(
 					WebContext.getResponse(), 
 					this.applicationConfig.getBaseDomainName(), 
 					WebConstants.ONLINE_TICKET_NAME, 
 					UUID.randomUUID().toString()
 		);
 		request.getSession().invalidate();
 		//for(String removeAttribute : WebContext.logoutAttributeNameList) {
 		//	request.getSession().removeAttribute(removeAttribute);
 		//}
 		SecurityContextHolder.clearContext();
 		
 		modelAndView.setViewName(viewName);
 		return modelAndView;
 	}
}
