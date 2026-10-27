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

package org.dromara.anduin.request;

import org.dromara.anduin.config.AuthConfig;
import org.dromara.anduin.config.AuthDefaultSource;
import org.dromara.anduin.cache.AuthStateCache;
import org.dromara.anduin.enums.AuthResponseStatus;
import org.dromara.anduin.enums.AuthUserGender;
import org.dromara.anduin.enums.scope.AuthWeChatEnterpriseWebScope;
import org.dromara.anduin.exception.AuthException;
import org.dromara.anduin.http.UnifiedHttpClient;
import org.dromara.anduin.http.UrlBuilder;
import org.dromara.anduin.model.AuthToken;
import org.dromara.anduin.model.AuthUser;
import org.dromara.anduin.utils.AuthScopeUtils;
import tools.jackson.databind.JsonNode;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthWeChatEnterpriseWebRequestCost extends AbstractAuthWeChatEnterpriseRequest {
    static final  Logger _logger = LoggerFactory.getLogger(AuthWeChatEnterpriseWebRequestCost.class);
    static final String USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
    
    public AuthWeChatEnterpriseWebRequestCost(AuthConfig config) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE_WEB);
    }

    public AuthWeChatEnterpriseWebRequestCost(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthDefaultSource.WECHAT_ENTERPRISE_WEB, authStateCache);
    }

    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(this.source.authorize()).queryParam("appid", this.config.getClientId()).queryParam("redirect_uri", this.config.getRedirectUri()).queryParam("response_type", "code").queryParam("scope", this.getScopes(",", false, AuthScopeUtils.getDefaultScopes(AuthWeChatEnterpriseWebScope.values()))).queryParam("state", this.getRealState(state).concat("#wechat_redirect")).build();
    }

    @Override
    public AuthUser getUserInfo(AuthToken authToken) {
        String response = this.doGetUserInfo(authToken);
        JsonNode object = this.checkResponse(response);
        if (!object.has("UserId")) {
            throw new AuthException(AuthResponseStatus.UNIDENTIFIED_PLATFORM, this.source);
        } else {
            String userId = object.get("UserId").asString("");
            if (StringUtils.isEmpty(userId)) {
                userId = object.get("userid").asString("");
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
                    JsonNode userDetail = this.checkResponse(userDetailResponse);
                    return AuthUser.builder()
                    		.rawUserInfo(userDetail)
                    		.username(userDetail.get("name").asString(""))
                    		.nickname(userDetail.get("alias").asString(""))
                    		.avatar(userDetail.get("avatar").asString(""))
                    		.location(userDetail.get("address").asString(""))
                    		.email(userDetail.get("email").asString(""))
                    		.uuid(userId)
                    		.gender(AuthUserGender.getWechatRealGender(userDetail.get("gender").asString("")))
                    		.token(authToken)
                    		.source(this.source.toString())
                    		.build();
                }catch (Exception e){
                    _logger.error("get userDetail error:{}",e.getMessage());
                }
            }
            return AuthUser.builder().uuid(userId).build();
        }
    }

    private String getUserDetail(String accessToken, String userId) {
        String userDetailUrl = UrlBuilder.fromBaseUrl(USER_URL).queryParam("access_token", accessToken).queryParam("userid", userId).build();
        return UnifiedHttpClient.getAdapter().get(userDetailUrl).getBody();
    }

    private JsonNode checkResponse(String response) {
    	JsonNode object = toNode(response);
        if (object.has("errcode") && object.get("errcode").asInt() != 0) {
            throw new AuthException(object.get("errmsg").asString(), this.source);
        } else {
            return object;
        }
    }
}
