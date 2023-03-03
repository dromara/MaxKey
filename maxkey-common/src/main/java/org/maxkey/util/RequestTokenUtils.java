package org.maxkey.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

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
	
	/**
	 * resolveAccessToken
	 * 
	 * @param request
	 * @return access_token
	 */
	public static String resolveAccessToken(HttpServletRequest request) {
		String access_token = request.getParameter("access_token");
		
		if(StringUtils.isBlank(access_token)) {
	    	//for header authorization bearer
	    	access_token = AuthorizationHeaderUtils.resolveBearer(request);
	    }
		
		if(StringUtils.isBlank(access_token)) {
			access_token = request.getParameter("token");
		}
		return access_token;
	}

}
