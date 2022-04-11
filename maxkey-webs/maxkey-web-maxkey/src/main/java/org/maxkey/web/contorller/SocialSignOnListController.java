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
 

package org.maxkey.web.contorller;

import java.util.List;

import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.entity.Message;
import org.maxkey.entity.SocialsAssociate;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.SocialsAssociatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/config/socialsignon"})
public class SocialSignOnListController {
	final static Logger _logger = LoggerFactory.getLogger(SocialSignOnListController.class);
	
	@Autowired
	protected SocialsAssociatesService socialsAssociatesService;
	
	
	@RequestMapping(value={"/fetch"})
	@ResponseBody
	public ResponseEntity<?> fetch(@CurrentUser UserInfo currentUser){
		
		List<SocialsAssociate>  listSocialsAssociate= 
				socialsAssociatesService.queryByUser(currentUser);
		
		return new Message<List<SocialsAssociate>>(listSocialsAssociate).buildResponse();
	}
	
}
