/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.util;


import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 从请求中解析token令牌信息,优先级顺序如下  
 * <p>
 *  1) 参数 access_token <br/>
 *  2) header 的Authorization或者authorization <br/>
 *  3) 参数 token <br/>
 * </p>
 * 
 * @author crystal.sea
 *
 */

public class RequestTokenUtils {
	
	public static String TOKEN 			= "token";
	
	public static String ACCESS_TOKEN 	= "access_token";
	/**
	 * 从请求中获取token令牌信息,优先级顺序如下  
	 * <p>
	 *  1) 参数 access_token <br/>
	 *  2) header 的Authorization或者authorization <br/>
	 *  3) 参数 token <br/>
	 * </p>
	 * 
	 * @param request
	 * @return access_token
	 */
	public static String resolveAccessToken(HttpServletRequest request) {
		String accessToken = request.getParameter(ACCESS_TOKEN);
		
		if(StringUtils.isBlank(accessToken)) {
			accessToken = request.getParameter(TOKEN);
		}
		
		if(StringUtils.isBlank(accessToken)) {
	    	//for header authorization bearer
			accessToken = AuthorizationHeaderUtils.resolveBearer(request);
	    }
		
		return accessToken;
	}

}
