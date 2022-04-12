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
import org.maxkey.constants.ConstsStatus;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.Accounts;
import org.maxkey.entity.Message;
import org.maxkey.entity.UserInfo;
import org.maxkey.entity.apps.Apps;
import org.maxkey.entity.apps.UserApps;
import org.maxkey.persistence.service.AccountsService;
import org.maxkey.persistence.service.AppsService;
import org.maxkey.persistence.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AppListController.
 * 
 * @author Administrator
 *
 */
@Controller
public class AppListController {
    static final Logger _logger = LoggerFactory.getLogger(AppListController.class);
    
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    AccountsService accountsService;

    @Autowired
    AppsService appsService;

    /**
     * gridList.
     * @param gridList 类型
     * @return
     */
    @RequestMapping(value = { "/appList" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> appList(
            @RequestParam(value = "gridList", required = false) String gridList,
            @CurrentUser UserInfo currentUser) {
        userInfoService.updateGridList(gridList,currentUser);
        UserApps userApps = new UserApps();
        userApps.setUsername(currentUser.getUsername());
        userApps.setInstId(currentUser.getInstId());
        List<UserApps> appList = appsService.queryMyApps(userApps);
        for (UserApps app : appList) {
        	app.transIconBase64();
        }
        return new Message<List<UserApps>>(appList).buildResponse();
    }
 
    
    @RequestMapping(value = { "/account/get" })
    @ResponseBody
	public ResponseEntity<?> getAccount(
    		@RequestParam("credential") int credential,
    		@RequestParam("appId") String appId,
    		@CurrentUser UserInfo currentUser) {
        Accounts account = null ;
        
        if (credential == Apps.CREDENTIALS.USER_DEFINED) {
        	account = accountsService.load(new Accounts(currentUser.getId(), appId));
        	account.setRelatedPassword(
        			PasswordReciprocal.getInstance().decoder(
        					account.getRelatedPassword()));
        }else {
        	account = new Accounts();
        	account.setAppId(appId);
        	account.setUserId(currentUser.getId());
        	account.setUsername(currentUser.getUsername());
        	account.setDisplayName(currentUser.getDisplayName());
        }
        return new Message<Accounts>(account).buildResponse();

    }

    @RequestMapping(value = { "/account/update" })
    @ResponseBody
	public ResponseEntity<?> updateAccount(
    		@RequestParam("credential") int credential,
    		@ModelAttribute Accounts account,
            @CurrentUser UserInfo currentUser) {
        Accounts appUsers = new Accounts();

        if (credential == Apps.CREDENTIALS.USER_DEFINED) {
            appUsers = accountsService.load(new Accounts(currentUser.getId(), account.getAppId()));
            if (appUsers == null) {
                appUsers = new Accounts();
                appUsers.setId(appUsers.generateId());
                appUsers.setUserId(currentUser.getId());
                appUsers.setUsername(currentUser.getUsername());
                appUsers.setDisplayName(currentUser.getDisplayName());

                appUsers.setRelatedPassword(
                		PasswordReciprocal.getInstance().encode(account.getRelatedPassword()));
                appUsers.setInstId(currentUser.getInstId());
                appUsers.setStatus(ConstsStatus.ACTIVE);
                accountsService.insert(appUsers);
            } else {
                appUsers.setRelatedUsername(account.getRelatedUsername());
                appUsers.setRelatedPassword(
                		PasswordReciprocal.getInstance().encode(account.getRelatedPassword()));
                accountsService.update(appUsers);
            }
        }

        return new Message<Accounts>().buildResponse();
    }
}
