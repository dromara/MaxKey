package org.maxkey.web.contorller;

import java.util.List;
import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.constants.PROTOCOLS;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.dao.service.AccountsService;
import org.maxkey.dao.service.AppsService;
import org.maxkey.dao.service.MyAppsListService;
import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.Accounts;
import org.maxkey.domain.UserInfo;
import org.maxkey.domain.apps.Apps;
import org.maxkey.domain.apps.UserApps;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    MyAppsListService myAppsListService;

    @Autowired
    AccountsService appUsersService;

    @Autowired
    AppsService appsService;

    /**
     * gridList.
     * @param gridList 类型
     * @return
     */
    @RequestMapping(value = { "/appList" })
    public ModelAndView appList(
            @RequestParam(value = "gridList", required = false) String gridList) {
        ModelAndView modelAndView = new ModelAndView("main/appList");

        if (gridList != null && !gridList.equals("")) {
            int intGridList = Integer.parseInt(gridList);
            jdbcTemplate.update("UPDATE USERINFO SET GRIDLIST = ? WHERE ID = ?", intGridList,
                    WebContext.getUserInfo().getId());
            WebContext.getUserInfo().setGridList(intGridList);
        }
        modelAndView.addObject("appList", queryAccessableApps());
        return modelAndView;
    }

    @RequestMapping(value = { "/appConfigList" })
    public ModelAndView appConfigList() {
        ModelAndView modelAndView = new ModelAndView("main/appConfigList");
        modelAndView.addObject("appList", queryAccessableApps());
        return modelAndView;
    }

    private List<UserApps> queryAccessableApps() {
        UserApps userApplications = new UserApps();
        userApplications.setUsername(WebContext.getUserInfo().getUsername());

        List<UserApps> appList = myAppsListService.queryMyApps(userApplications);
        for (UserApps app : appList) {
            WebContext.setAttribute(app.getId(), app.getIcon());
        }

        return appList;
    }

    /**
     * forwardAppLoginConfig.
     * @param protocol protocol
     * @param credential credential
     * @param appId appId
     * @return
     */
    @RequestMapping(value = { "/forward/appProtectedConfig/{protocol}/{credential}/{appId}" })
    public ModelAndView forwardAppLoginConfig(@PathVariable("protocol") String protocol,
            @PathVariable("credential") int credential, @PathVariable("appId") String appId) {
        ModelAndView modelAndView = new ModelAndView("main/appProtectedConfig");

        UserInfo userInfo = WebContext.getUserInfo();

        if (userInfo.getProtectedAppsMap().get(appId) != null) {
            modelAndView.addObject("protectedappId", true);
        } else {
            modelAndView.addObject("protectedappId", false);
        }
        modelAndView.addObject("uid", userInfo.getId());
        modelAndView.addObject("appId", appId);
        modelAndView.addObject("protocol", protocol);
        modelAndView.addObject("credential", credential);
        return modelAndView;

    }

    /**
     * appLoginConfig.
     * @param protocol protocol
     * @param credential credential
     * @param appId appId
     * @param protectedappId protectedappId
     * @param password password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/appProtectedConfig" })
    public Message appLoginConfig(
                @RequestParam("protocol") String protocol, 
                @RequestParam("credential") int credential,
                @RequestParam("appId") String appId, 
                @RequestParam("protectedappId") String protectedappId,
                @RequestParam("password") String password) {

        UserInfo userInfo = WebContext.getUserInfo();
        String userAppProtectedPassword = ReciprocalUtils.decoder(userInfo.getAppLoginPassword());
        if (userAppProtectedPassword.equals(password)) {

            if (protectedappId.equalsIgnoreCase("YES")) {
                if (userInfo.getProtectedApps() != null 
                        && userInfo.getProtectedApps().indexOf(appId) < 0) {
                    userInfo.setProtectedApps(userInfo.getProtectedApps() + "," + appId);
                    if (userInfo.getProtectedAppsMap() != null) {
                        userInfo.getProtectedAppsMap().put(appId, appId);
                    }
                } else {
                    userInfo.setProtectedApps("," + appId);
                }
            } else {
                if (userInfo.getProtectedApps() != null 
                        && userInfo.getProtectedApps().indexOf(appId) > -1) {
                    // userInfo.setSecondProtectedApps(userInfo.getSecondProtectedApps()+","+appId);
                    String[] protectedApps = userInfo.getProtectedApps().split(",");
                    String protectedAppIds = "";
                    if (userInfo.getProtectedAppsMap() != null) {
                        userInfo.getProtectedAppsMap().remove(appId);
                    }
                    for (String protectedAppId : protectedApps) {
                        if (protectedAppId.equalsIgnoreCase(appId) 
                                || protectedAppId.trim().equals("")) {
                            continue;
                        }
                        protectedAppIds = protectedAppIds + "," + protectedAppId;
                    }
                    userInfo.setProtectedApps(protectedAppIds);
                }
            }

            userInfoService.updateProtectedApps(userInfo);
        } else {
            return new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR), MessageType.error);
        }

        return new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS), MessageType.success);
    }

    @RequestMapping(value = { "/forward/appUserConfig/{protocol}/{credential}/{appId}" })
    public ModelAndView forwardAppUserConfig(@PathVariable("protocol") String protocol,
            @PathVariable("credential") int credential, @PathVariable("appId") String appId) {
        ModelAndView modelAndView = new ModelAndView("main/appUserConfig");
        // modelAndView.addObject("appList",appList);

        Accounts appUsers = new Accounts();
        UserInfo userInfo = WebContext.getUserInfo();
        if (credential == Apps.CREDENTIALS.USER_DEFINED) {
            appUsers = appUsersService.load(new Accounts(userInfo.getId(), appId));
            if (protocol.equalsIgnoreCase(PROTOCOLS.DESKTOP) || protocol.equalsIgnoreCase(PROTOCOLS.FORMBASED)
                    || protocol.equalsIgnoreCase(PROTOCOLS.BASIC) || protocol.equalsIgnoreCase(PROTOCOLS.EXTEND_API)) {

                modelAndView.addObject("username", true);
                modelAndView.addObject("password", true);
            } else if (protocol.equalsIgnoreCase(PROTOCOLS.SAML20)) {
                modelAndView.addObject("username", true);
                modelAndView.addObject("password", false);
            } else {
                modelAndView.addObject("username", false);
                modelAndView.addObject("password", false);
            }
            if (appUsers != null) {
                modelAndView.addObject("identity_username", appUsers.getRelatedUsername());
                modelAndView.addObject("identity_password", ReciprocalUtils.decoder(appUsers.getRelatedPassword()));
            } else {
                modelAndView.addObject("identity_username", "");
                modelAndView.addObject("identity_password", "");
            }
        } else {
            modelAndView.addObject("username", false);
            modelAndView.addObject("password", false);
        }

        modelAndView.addObject("uid", userInfo.getId());
        modelAndView.addObject("appId", appId);
        modelAndView.addObject("protocol", protocol);
        modelAndView.addObject("credential", credential);
        return modelAndView;

    }

    @ResponseBody
    @RequestMapping(value = { "/appUserConfig" })
    public Message appUserConfig(@RequestParam("protocol") String protocol, @RequestParam("credential") int credential,
            @RequestParam("appId") String appId, @RequestParam("identity_username") String identity_username,
            @RequestParam("identity_password") String identity_password) {

        Apps app = appsService.get(appId);
        UserInfo userInfo = WebContext.getUserInfo();

        Accounts appUsers = new Accounts();
        appUsers.setAppId(appId);
        appUsers.setUid(userInfo.getId());

        if (identity_password != null && !identity_password.equals("") && credential == Apps.CREDENTIALS.USER_DEFINED) {
            appUsers = appUsersService.load(new Accounts(userInfo.getId(), appId));
            if (appUsers == null) {
                appUsers = new Accounts();
                appUsers.setId(appUsers.generateId());
                appUsers.setAppId(appId);
                appUsers.setAppName(app.getName());
                appUsers.setUid(userInfo.getId());
                appUsers.setUsername(userInfo.getUsername());
                appUsers.setDisplayName(userInfo.getDisplayName());

                appUsers.setRelatedUsername(identity_username);
                appUsers.setRelatedPassword(ReciprocalUtils.encode(identity_password));
                appUsersService.insert(appUsers);
            } else {
                appUsers.setRelatedUsername(identity_username);
                appUsers.setRelatedPassword(ReciprocalUtils.encode(identity_password));
                appUsersService.update(appUsers);
            }
        }

        return new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS), MessageType.success);
    }
}
