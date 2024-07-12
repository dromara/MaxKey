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
package org.dromara.maxkey.web.apps.contorller;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.persistence.service.FileUploadService;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Crystal.Sea
 *
 */
public class BaseAppContorller {
	static final Logger logger = LoggerFactory.getLogger(BaseAppContorller.class);
	
	@Autowired
	protected AppsService appsService;

	@Autowired
	protected PasswordReciprocal passwordReciprocal;
	
	@Autowired
	protected FileUploadService fileUploadService;
	
	@Autowired
	HistorySystemLogsService systemLog;
	
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
		 * upload icon Bytes
		 */
		if(StringUtils.isNotBlank(application.getIconId())){
			application.setIcon(fileUploadService.get(application.getIconId()).getUploaded());
			fileUploadService.delete(application.getIconId());
		}
		
	}
	
	protected void encodeSharedPassword(Apps application){
		if(StringUtils.isNotBlank(application.getSharedPassword())){
			application.setSharedPassword(
					PasswordReciprocal.getInstance().encode(application.getSharedPassword()));
		}
	}
	
	protected void decoderSharedPassword(Apps application){
		if(StringUtils.isNotBlank(application.getSharedPassword())){
			application.setSharedPassword(
					PasswordReciprocal.getInstance().decoder(application.getSharedPassword()));
		}
	}
	
	protected void encoding(Apps application){
		
	}
	
	protected void encodeSecret(Apps application){
		if(StringUtils.isNotBlank(application.getSecret())){
			String encodeSecret=passwordReciprocal.encode(application.getSecret());
			application.setSecret(encodeSecret);
		}
	}
	
	protected void decoderSecret(Apps application){
		if(StringUtils.isNotBlank(application.getSecret())){
			String decodeSecret=passwordReciprocal.decoder(application.getSecret());
			application.setSecret(decodeSecret);
		}
	}
}
