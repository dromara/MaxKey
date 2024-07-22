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
 

package org.dromara.maxkey.authn.web;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.web.WebConstants;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    	Authentication  authentication  = 
    			(Authentication ) webRequest.getAttribute(
    					WebConstants.AUTHENTICATION, RequestAttributes.SCOPE_SESSION);
    	UserInfo userInfo  = AuthorizationUtils.getUserInfo(authentication);
    	if (userInfo != null) {
            return userInfo;
        }
        throw new MissingServletRequestPartException("currentUser");
    }
    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserInfo.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }
    
}
