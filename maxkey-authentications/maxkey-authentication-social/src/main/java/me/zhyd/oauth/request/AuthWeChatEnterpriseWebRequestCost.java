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
 

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.zhyd.oauth.request;

import com.alibaba.fastjson.JSONObject;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthDefaultSource;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.enums.scope.AuthWeChatEnterpriseWebScope;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.utils.AuthScopeUtils;
import me.zhyd.oauth.utils.HttpUtils;
import me.zhyd.oauth.utils.UrlBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthWeChatEnterpriseWebRequestCost extends AbstractAuthWeChatEnterpriseRequest {
    static final  Logger _logger = LoggerFactory.getLogger(AuthWeChatEnterpriseWebRequestCost.class);
    public AuthWeChatEnterpriseWebRequestCost(AuthConfig config) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE_WEB);
    }

    public AuthWeChatEnterpriseWebRequestCost(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE_WEB, authStateCache);
    }

    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(this.source.authorize()).queryParam("appid", this.config.getClientId()).queryParam("redirect_uri", this.config.getRedirectUri()).queryParam("response_type", "code").queryParam("scope", this.getScopes(",", false, AuthScopeUtils.getDefaultScopes(AuthWeChatEnterpriseWebScope.values()))).queryParam("state", this.getRealState(state).concat("#wechat_redirect")).build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String response = this.doGetUserInfo(authToken);
        JSONObject object = this.checkResponse(response);
        if (!object.containsKey("UserId")) {
            throw new AuthException(AuthResponseStatus.UNIDENTIFIED_PLATFORM, this.source);
        } else {
            String userId = object.getString("UserId");
            if (StringUtils.isEmpty(userId)) {
                userId = object.getString("userid");
                if (StringUtils.isEmpty(userId)) {
                    //如果还是空，则异常
                    throw new AuthException(AuthResponseStatus.UNIDENTIFIED_PLATFORM, this.source);
                }
            }
            _logger.debug("get userid:{}",userId);
            //根据userid判断是否是上下游的企业微信扫码，下游企业微信扫码返回userid是企业id/用户id,无法获取用户详情会报错400058
            if (userId.indexOf("/") == -1) {
                try {
                    String userDetailResponse = this.getUserDetail(authToken.getAccessToken(), userId);
                    JSONObject userDetail = this.checkResponse(userDetailResponse);
                    return AuthUser.builder().rawUserInfo(userDetail).username(userDetail.getString("name")).nickname(userDetail.getString("alias")).avatar(userDetail.getString("avatar")).location(userDetail.getString("address")).email(userDetail.getString("email")).uuid(userId).gender(AuthUserGender.getWechatRealGender(userDetail.getString("gender"))).token(authToken).source(this.source.toString()).build();
                }catch (Exception e){
                    _logger.error("get userDetail error:{}",e.getMessage());
                }
            }
            return AuthUser.builder().uuid(userId).build();
        }
    }

    private String getUserDetail(String accessToken, String userId) {
        String userDetailUrl = UrlBuilder.fromBaseUrl("https://qyapi.weixin.qq.com/cgi-bin/user/get").queryParam("access_token", accessToken).queryParam("userid", userId).build();
        return (new HttpUtils(this.config.getHttpConfig())).get(userDetailUrl).getBody();
    }

    private JSONObject checkResponse(String response) {
        JSONObject object = JSONObject.parseObject(response);
        if (object.containsKey("errcode") && object.getIntValue("errcode") != 0) {
            throw new AuthException(object.getString("errmsg"), this.source);
        } else {
            return object;
        }
    }
}
