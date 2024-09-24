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

import java.io.IOException;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.FileUpload;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FileUploadEndpoint {
	
	private static Logger _logger = LoggerFactory.getLogger(FileUploadEndpoint.class);
	
	@Autowired
	FileUploadService fileUploadService;
	
 	@PostMapping({"/file/upload/"})
 	@ResponseBody
 	public Message<Object> upload( HttpServletRequest request, 
 	                            HttpServletResponse response,
 	                           @ModelAttribute FileUpload fileUpload,
 	                           @CurrentUser UserInfo currentUser){
 		_logger.debug("FileUpload");
 		fileUpload.setId(fileUpload.generateId());
 		fileUpload.setContentType(fileUpload.getUploadFile().getContentType());
 		fileUpload.setFileName(fileUpload.getUploadFile().getOriginalFilename());
 		fileUpload.setContentSize(fileUpload.getUploadFile().getSize());
 		fileUpload.setCreatedBy(currentUser.getUsername());
 		/*
		 * upload UploadFile MultipartFile  to Uploaded Bytes
		 */
		if(null!=fileUpload.getUploadFile()&&!fileUpload.getUploadFile().isEmpty()){
			try {
				fileUpload.setUploaded(fileUpload.getUploadFile().getBytes());
				fileUploadService.insert(fileUpload);
				_logger.trace("FileUpload SUCCESS");
			} catch (IOException e) {
				_logger.error("FileUpload IOException",e);
			}
		}
 		return new Message<Object>(Message.SUCCESS,(Object)fileUpload.getId());
 	}
 	
}
