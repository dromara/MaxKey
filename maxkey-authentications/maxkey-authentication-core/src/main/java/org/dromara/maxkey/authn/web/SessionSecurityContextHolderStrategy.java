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
 

package org.dromara.maxkey.authn.web;

import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;

/**
 * SecurityContext Session for Request , use SecurityContextHolderAwareRequestFilter
 * @author Crystal.Sea
 *
 */
public class SessionSecurityContextHolderStrategy implements SecurityContextHolderStrategy {
    private static final Logger _logger = 
            LoggerFactory.getLogger(SessionSecurityContextHolderStrategy.class);
    
    @Override
    public void clearContext() {
        WebContext.removeAttribute(WebConstants.AUTHENTICATION);
    }

    @Override
    public SecurityContext getContext() {
        SecurityContext ctx =  createEmptyContext();
        Authentication  authentication = null;
        try {
            authentication = (Authentication)AuthorizationUtils.getAuthentication();
            if (authentication != null) {
                ctx.setAuthentication(authentication);
            }
        }catch(Exception e) {
            _logger.trace("a session ", e);
        }
       
       
        return ctx;
    }

    @Override
    public void setContext(SecurityContext context) {
    	AuthorizationUtils.setAuthentication(context.getAuthentication());
    }

    @Override
    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }

}
