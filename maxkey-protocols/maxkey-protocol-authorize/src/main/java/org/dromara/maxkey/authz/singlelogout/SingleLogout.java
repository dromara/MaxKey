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
 

package org.dromara.maxkey.authz.singlelogout;

import java.util.Map;

import org.dromara.maxkey.authn.session.VisitedDto;
import org.dromara.maxkey.web.HttpRequestAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

public abstract class SingleLogout {
    private static final Logger _logger = LoggerFactory.getLogger(SingleLogout.class);
    
    public abstract void sendRequest(Authentication authentication,VisitedDto visited) ;
    
    public void postMessage(String url,Map<String, Object> paramMap) {
    	_logger.debug("post logout message to url {}" , url);
    	(new HttpRequestAdapter()).post(url , paramMap);
    }
}
