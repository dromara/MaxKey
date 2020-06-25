package org.maxkey.identity.rest;

import java.io.IOException;

import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value={"/identity/api/userinfo"})
public class RestApiUserInfoController {

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserInfo getUser(
                                       @PathVariable String id,
                                       @RequestParam(required = false) String attributes) {
        
        UserInfo loadUserInfo = userInfoService.get(id);
        loadUserInfo.setDecipherable(null);
        return loadUserInfo;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public UserInfo create(@RequestBody  UserInfo userInfo,
                                                      @RequestParam(required = false) String attributes,
                                                      UriComponentsBuilder builder) throws IOException {
        UserInfo loadUserInfo = userInfoService.loadByUsername(userInfo.getUsername());
        if(loadUserInfo != null) {
            userInfoService.update(userInfo);
        }else {
            userInfoService.insert(userInfo);
        }
        return userInfo;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public UserInfo replace(@PathVariable String id,
                                                       @RequestBody UserInfo userInfo,
                                                       @RequestParam(required = false) String attributes)
            throws IOException {
        UserInfo loadUserInfo = userInfoService.loadByUsername(userInfo.getUsername());
        if(loadUserInfo != null) {
            userInfoService.update(userInfo);
        }else {
            userInfoService.insert(userInfo);
        }
        return userInfo;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final String id) {
        userInfoService.logisticDeleteAllByCid(id);
       
    }
}
