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
 

package org.dromara.maxkey.web.contorller;

import java.util.List;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.SocialsAssociate;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.SocialsAssociatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/config/socialsignon"})
public class SocialSignOnListController {
	static final Logger logger = LoggerFactory.getLogger(SocialSignOnListController.class);
	
	@Autowired
	SocialsAssociatesService socialsAssociatesService;
	
	@RequestMapping(value={"/fetch"})
	@ResponseBody
	public Message<?> fetch(@CurrentUser UserInfo currentUser){
		
		List<SocialsAssociate>  listSocialsAssociate= 
				socialsAssociatesService.queryByUser(currentUser);
		
		return new Message<List<SocialsAssociate>>(listSocialsAssociate);
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (socialsAssociatesService.deleteBatch(ids)) {
			 return new Message<Apps>(Message.SUCCESS);
		} else {
			return new Message<Apps>(Message.FAIL);
		}
	}
	
}
