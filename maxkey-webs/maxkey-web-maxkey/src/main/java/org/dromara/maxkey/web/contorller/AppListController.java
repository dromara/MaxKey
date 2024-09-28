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
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Accounts;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.entity.apps.UserApps;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.AccountsService;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.mybatis.jpa.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AppListController.
 * 
 * @author Administrator
 *
 */
@RestController
public class AppListController {
    static final Logger logger = LoggerFactory.getLogger(AppListController.class);
    
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    AccountsService accountsService;

    @Autowired
    AppsService appsService;

    /**
     * gridList.
     * @param gridList 类型
     * @return
     */
    @GetMapping(value = { "/appList" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<List<UserApps>> appList(
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
        return new Message<>(appList);
    }
 
    
    @GetMapping(value = { "/account/get" })
	public Message<Accounts> getAccount(
    		@RequestParam("credential") String credential,
    		@RequestParam("appId") String appId,
    		@CurrentUser UserInfo currentUser) {
        Accounts account = null ;
        
        if (credential.equalsIgnoreCase(Apps.CREDENTIALS.USER_DEFINED)) {
        	account = accountsService.get(Query.builder().eq("appId", appId).eq("userid", currentUser.getId()));
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
        return new Message<>(account);

    }

    @PutMapping(value = { "/account/update" })
	public Message<Accounts> updateAccount(
    		@RequestParam("credential") String credential,
    		@ModelAttribute Accounts account,
            @CurrentUser UserInfo currentUser) {
        Accounts appUsers = new Accounts();
        if (credential.equalsIgnoreCase(Apps.CREDENTIALS.USER_DEFINED)) {
            appUsers = accountsService.get(Query.builder().eq("appId", account.getAppId()).eq("userid", currentUser.getId()));
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

        return new Message<>();
    }
}
