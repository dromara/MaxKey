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
 

package org.dromara.maxkey.password.sms;

import java.io.IOException;

import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.StandardEnvironment;

public class SmsOtpAuthn extends AbstractOtpAuthn {
    private static final  Logger logger = LoggerFactory.getLogger(SmsOtpAuthn.class);
    
    protected StandardEnvironment properties;
    
    
    @Override
    public boolean produce(UserInfo userInfo) {
        String token = this.genToken(userInfo);
        // You must add send sms code here
        logger.debug("send sms code" + token);
        return true;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        return true;
    }
    
    public void setProperties(StandardEnvironment properties) {
		this.properties = properties;
	}

	protected void loadProperties() throws IOException {

    }
    
    public void initPropertys() {
        
    }

}
