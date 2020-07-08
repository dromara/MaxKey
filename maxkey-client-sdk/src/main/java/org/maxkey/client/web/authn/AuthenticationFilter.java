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
 

package org.maxkey.client.web.authn;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.maxkey.client.oauth.OAuthClient;
import org.maxkey.client.oauth.builder.ServiceBuilder;
import org.maxkey.client.oauth.builder.api.MaxkeyApi20;
import org.maxkey.client.oauth.domain.UserInfo;
import org.maxkey.client.oauth.model.Token;
import org.maxkey.client.oauth.model.Verifier;
import org.maxkey.client.oauth.oauth.OAuthService;



/**
 * 
 * @author Crystal.Sea
 */
public class AuthenticationFilter implements Filter {
	
	private   static  Log log  =  LogFactory.getLog(AuthenticationFilter. class );
	
	private static final String UUID_REGEX =  "^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$";
	
	public  static final String CONST_MAXKEY_USERINFO="CONST_MAXKEY_USERINFO";
	
	private String clientId;
	private String clientSecret;
	private String callBackUri;
	private boolean enable;
	private OAuthService service ;
	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession();
		
		if(enable){
			Token EMPTY_TOKEN = null;
			String code=request.getParameter("code");
			
			if(code!=null&&uuidMatches(code)){
				Verifier verifier = new Verifier(code);
				Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
				log.debug(" access token is "+accessToken);
				
				OAuthClient restClient=new OAuthClient(OAuthClient.OAUTH_V20_USERINFO_URI);
				 
				UserInfo userInfo=restClient.getUserInfo(accessToken.getToken());

				session.setAttribute(CONST_MAXKEY_USERINFO, userInfo);
				
			}else if(session.getAttribute(CONST_MAXKEY_USERINFO)==null){
				String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
				log.debug("Redirect to authorization Url : "+authorizationUrl);
				httpServletResponse.sendRedirect(authorizationUrl);
			}
		}
		
		chain.doFilter(request, response);
	}
		
	public void destroy() {
		this.destroy();
	}

	public void init(FilterConfig config) throws ServletException {
		this.clientId=config.getInitParameter("clientId");
		this.clientSecret=config.getInitParameter("clientSecret");
		this.callBackUri=config.getInitParameter("callBackUri");
		this.enable=config.getInitParameter("enable").equalsIgnoreCase("true");
		
		log.debug("client_id : "+clientId);
		log.debug("client_secret : "+clientSecret);
		log.debug("callBack Uri : "+callBackUri);
		log.debug("enable : "+enable);
		
		service = new ServiceBuilder()
	        .provider(MaxkeyApi20.class)
	        .apiKey(this.clientId)
	        .apiSecret(this.clientSecret)
	        .callback(this.callBackUri)
	        .build();
		log.debug(" init.");
	}

    public static boolean uuidMatches(String uuidString) {
		return uuidString.matches(UUID_REGEX);
	}
}
