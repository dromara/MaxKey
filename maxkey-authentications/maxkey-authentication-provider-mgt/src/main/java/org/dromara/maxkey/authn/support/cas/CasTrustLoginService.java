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
 

package org.dromara.maxkey.authn.support.cas;

import org.apereo.cas.client.validation.Assertion;
import org.apereo.cas.client.validation.Cas20ServiceTicketValidator;
import org.apereo.cas.client.validation.TicketValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CasTrustLoginService {
    private static final Logger _logger = LoggerFactory.getLogger(CasTrustLoginService.class);


    String service;
    
    Cas20ServiceTicketValidator cas20ServiceTicketValidator;
    
    public CasTrustLoginService(String casServerUrlPrefix,String service) {
    	this.service = service;
        this.cas20ServiceTicketValidator = new Cas20ServiceTicketValidator(casServerUrlPrefix);
    }

    public String buildLoginUser(String ticket) {
    	_logger.debug("build Login User .");
        String user = null;
        Assertion assertion;
		try {
			assertion = cas20ServiceTicketValidator.validate(ticket, service);
			 if(assertion != null) {
		        	user = assertion.getPrincipal().getName();
		     }
		} catch (TicketValidationException e) {
			_logger.error("cas TicketValidationException" , e);
			e.printStackTrace();
		}
       
		_logger.debug("cas user : {}" , user);
        return user;
    }
    
}
