package org.maxkey.authn;

import org.maxkey.entity.UserInfo;
import org.maxkey.web.WebConstants;
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
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserInfo.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }
    
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    	UserInfo userInfo = null;
    	Authentication  authentication  = (Authentication ) webRequest.getAttribute(WebConstants.AUTHENTICATION, RequestAttributes.SCOPE_SESSION);
        if(authentication.getPrincipal() instanceof SigninPrincipal) {
        	SigninPrincipal signinPrincipal = ((SigninPrincipal) authentication.getPrincipal());
        	userInfo = signinPrincipal.getUserInfo();
        	if (userInfo != null) {
                return userInfo;
            }
        }
        throw new MissingServletRequestPartException("currentUser");
    }
}