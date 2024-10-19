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
 

package org.dromara.maxkey.web.historys.contorller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.history.HistoryLogin;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.HistoryLoginService;
import org.dromara.maxkey.util.DateUtils;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录日志查询
 * 
 * @author Crystal.sea
 *
 */

@Controller
@RequestMapping(value={"/historys"})
public class LoginHistoryController {
	static final  Logger logger = LoggerFactory.getLogger(LoginHistoryController.class);
	
	@Autowired
	HistoryLoginService loginHistoryService;
	
	/**
	 * @param HistoryLogin
	 * @return
	 */
	@RequestMapping(value={"/loginHistory/fetch"})
	@ResponseBody
	public Message<?> fetch(
				@ModelAttribute("historyLogin") HistoryLogin historyLogin,
				@CurrentUser UserInfo currentUser
			){
		logger.debug("historys/loginHistory/fetch/ {}",historyLogin);
		historyLogin.setInstId(currentUser.getInstId());
		historyLogin.setUserId(currentUser.getId());
		return new Message<JpaPageResults<HistoryLogin>>(
					loginHistoryService.fetchPageResults(historyLogin)
				);
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_DATE_HH_MM_SS);
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
