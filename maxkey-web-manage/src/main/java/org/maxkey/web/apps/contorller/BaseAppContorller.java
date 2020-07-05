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
 

/**
 * 
 */
package org.maxkey.web.apps.contorller;

import java.io.IOException;

import org.maxkey.constants.ConstantsProtocols;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.domain.apps.Apps;
import org.maxkey.persistence.service.AppsService;
import org.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author Crystal.Sea
 *
 */
public class BaseAppContorller {

	final static Logger _logger = LoggerFactory.getLogger(BaseAppContorller.class);
	
	@Autowired
	@Qualifier("appsService")
	protected AppsService appsService;

	
	@Autowired
	@Qualifier("passwordReciprocal")
	protected PasswordReciprocal passwordReciprocal;
	
	
	public void setAppsService(AppsService appsService) {
		this.appsService = appsService;
	}

	protected void transform(Apps application) {
		
		encodeSharedPassword(application);
		
		encodeSecret(application);
		
		/*
		 * string field encoding
		 */
		encoding(application);

		/*
		 * upload iconFile MultipartFile  to icon Bytes
		 */
		if(null!=application.getIconFile()&&!application.getIconFile().isEmpty()){
			try {
				application.setIcon(application.getIconFile().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		
	}
	
	protected void encodeSharedPassword(Apps application){
		if(application.getCredential()!=Apps.CREDENTIALS.SHARED){
			if(application.getProtocol().equals(ConstantsProtocols.DESKTOP)||application.getProtocol().equals(ConstantsProtocols.FORMBASED)){
				if(StringUtils.isNotEmpty(application.getSharedPassword())){
					application.setSharedPassword(ReciprocalUtils.encode(application.getSharedPassword()));
				}
			}
		}
	}
	
	protected void decoderSharedPassword(Apps application){
		if(application.getCredential()!=Apps.CREDENTIALS.SHARED){
			if(application.getProtocol().equals(ConstantsProtocols.DESKTOP)||application.getProtocol().equals(ConstantsProtocols.FORMBASED)){
				if(StringUtils.isNotEmpty(application.getSharedPassword())){
					application.setSharedPassword(ReciprocalUtils.decoder(application.getSharedPassword()));
				}
			}
		}
	}
	
	protected void encoding(Apps application){
		
		//application.setName(WebContext.encoding(application.getName()));
		if(null!=application.getDescription()){
		//	application.setDescription(WebContext.encoding(application.getDescription()));
		}
		
	}
	
	
	protected void encodeSecret(Apps application){
		if(application.getSecret()!=null&&!application.getSecret().equals("")){
			//
			String encodeSecret=passwordReciprocal.encode(application.getSecret());
			application.setSecret(encodeSecret);
		}
	}
	
	protected void decoderSecret(Apps application){
		if(application.getSecret()!=null&&!application.getSecret().equals("")){
			String decodeSecret=passwordReciprocal.decoder(application.getSecret());
			application.setSecret(decodeSecret);
		}
	}
}
