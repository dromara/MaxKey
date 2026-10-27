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
 

package org.dromara.anduin.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dromara.anduin.config.AuthConfig;
import org.dromara.anduin.config.AuthMxkDefaultSource;
import org.dromara.anduin.cache.AuthStateCache;
import org.dromara.anduin.enums.AuthResponseStatus;
import org.dromara.anduin.enums.AuthUserGender;
import org.dromara.anduin.exception.AuthException;
import org.dromara.anduin.http.HttpHeader;
import org.dromara.anduin.http.UnifiedHttpClient;
import org.dromara.anduin.http.UrlBuilder;
import org.dromara.anduin.model.AuthCallback;
import org.dromara.anduin.model.AuthResponse;
import org.dromara.anduin.model.AuthToken;
import org.dromara.anduin.model.AuthUser;
import org.dromara.anduin.utils.GlobalAuthUtils;
import org.dromara.anduin.utils.JsonUtils;
import tools.jackson.databind.JsonNode;

/**
 * 飞书平台，企业自建应用授权登录，
 * https://open.feishu.cn/document/common-capabilities/sso/web-application-sso/web-app-overview
 * <p>
 * 所以，最终修改该平台的实际发布版本为 支持扫码登录
 *
 * @author beacon
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com) 重构业务逻辑 20210101
 * @author maxkey 重构业务逻辑 20220216
 * @since 1.15.9
 */
public class AuthFeishu2Request extends AuthDefaultRequest {

	static final String APP_ACCESS_TOKEN_URL = "https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal/";
	
    public AuthFeishu2Request(AuthConfig config) {
        super(config, AuthMxkDefaultSource.FEISHU2);
    }

    public AuthFeishu2Request(AuthConfig config, AuthStateCache authStateCache) {
        super(config, AuthMxkDefaultSource.FEISHU2, authStateCache);
    }

    /**
     * 获取 app_access_token（企业自建应用）
     * <p>
     * Token 有效期为 2 小时，在此期间调用该接口 token 不会改变。当 token 有效期小于 30 分的时候，再次请求获取 token 的时候，
     * 会生成一个新的 token，与此同时老的 token 依然有效。
     *
     * @return appAccessToken
     */
    private String getAppAccessToken() {
        String cacheKey = this.source.getName().concat(":app_access_token:").concat(config.getClientId());
        String cacheAppAccessToken = this.authStateCache.get(cacheKey);
        if (StringUtils.isNotEmpty(cacheAppAccessToken)) {
            return cacheAppAccessToken;
        }
        Map<String, String> requestObject = new HashMap<>();
        requestObject.put("app_id", config.getClientId());
        requestObject.put("app_secret", config.getClientSecret());
        String response = UnifiedHttpClient.getAdapter().post(APP_ACCESS_TOKEN_URL, JsonUtils.toJson(requestObject), new HttpHeader()
            .add("Content-Type", "application/json")).getBody();
        JsonNode jsonObject = toNode(response);
        this.checkResponse(jsonObject);
        String appAccessToken = jsonObject.get("app_access_token").asString();
        // 缓存 app access token
        this.authStateCache.cache(cacheKey, appAccessToken);
        return appAccessToken;
    }

    @Override
    public AuthToken getAccessToken(AuthCallback authCallback) {
    	Map<String, String> requestObject = new HashMap<>();
        requestObject.put("app_access_token", this.getAppAccessToken());
        requestObject.put("grant_type", "authorization_code");
        requestObject.put("client_id", config.getClientId());
        requestObject.put("client_secret", config.getClientSecret());
        requestObject.put("redirect_uri", config.getRedirectUri());
        requestObject.put("code", authCallback.getCode());
        return getToken(requestObject, this.source.accessToken());

    }

    @Override
    public AuthUser getUserInfo(AuthToken authToken) {
        String accessToken = authToken.getAccessToken();
        String response = UnifiedHttpClient.getAdapter().get(source.userInfo(), null, new HttpHeader()
            .add("Content-Type", "application/json")
            .add("Authorization", "Bearer " + accessToken), false).getBody();

        JsonNode userInfoObject = toNode(response);
        
        this.checkResponse(userInfoObject);
        
        return AuthUser.builder()
            .rawUserInfo(userInfoObject)
            .uuid(userInfoObject.get("union_id").asString())
            .username(userInfoObject.get("name").asString())
            .nickname(userInfoObject.get("name").asString())
            .avatar(userInfoObject.get("avatar_url").asString())
            .email(userInfoObject.get("email").asString())
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
            .build();
    }

    @Override
    public AuthResponse<AuthToken> refresh(AuthToken authToken) {
    	Map<String, String> requestObject = new HashMap<>();
        requestObject.put("app_access_token", this.getAppAccessToken());
        requestObject.put("grant_type", "refresh_token");
        requestObject.put("refresh_token", authToken.getRefreshToken());
        return AuthResponse.<AuthToken>builder()
            .code(AuthResponseStatus.SUCCESS.getCode())
            .data(getToken(requestObject, this.source.refresh()))
            .build();

    }

    private AuthToken getToken(Map<String, String>  params, String url) {
        String response = UnifiedHttpClient.getAdapter().post(url, JsonUtils.toJson(params), new HttpHeader()
            .add("Content-Type", "application/json")).getBody();
        JsonNode jsonObject = toNode(response);
        this.checkResponse(jsonObject);
        return AuthToken.builder()
            .accessToken(jsonObject.get("access_token").asString())
            .refreshToken(jsonObject.get("refresh_token").asString())
            .expireIn(jsonObject.get("expires_in").asInt())
            .tokenType(jsonObject.get("token_type").asString())
            .openId(jsonObject.get("open_id").asString())
            .build();
    }

    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(source.authorize())
            .queryParam("client_id", config.getClientId())
            .queryParam("redirect_uri", GlobalAuthUtils.urlEncode(config.getRedirectUri()))
            .queryParam("response_type", "code")
            .queryParam("state", getRealState(state))
            .build();
    }


    /**
     * 校验响应内容是否正确
     *
     * @param jsonObject 响应内容
     */
    private void checkResponse(JsonNode jsonNode) {
        if (jsonNode.get("code").asInt() != 0) {
            throw new AuthException(jsonNode.get("message").asString());
        }
    }

}
