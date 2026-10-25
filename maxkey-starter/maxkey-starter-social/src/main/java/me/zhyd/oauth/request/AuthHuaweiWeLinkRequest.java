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
 

package me.zhyd.oauth.request;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.enums.scope.AuthHuaweiScope;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.http.UnifiedHttpClient;
import me.zhyd.oauth.http.UrlBuilder;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.utils.AuthScopeUtils;
import tools.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

import static me.zhyd.oauth.enums.AuthResponseStatus.SUCCESS;

public class AuthHuaweiWeLinkRequest  extends AuthDefaultRequest {

    public AuthHuaweiWeLinkRequest(AuthConfig config) {
        super(config, WeLinkAuthDefaultSource.HUAWEI_WELINK);
    }

    public AuthHuaweiWeLinkRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, WeLinkAuthDefaultSource.HUAWEI_WELINK, authStateCache);
    }

    /**
     * 获取access token
     *
     * @param authCallback 授权成功后的回调参数
     * @return token
     * @see AuthDefaultRequest#authorize()
     * @see AuthDefaultRequest#authorize(String)
     */
    @Override
    public AuthToken getAccessToken(AuthCallback authCallback) {
        Map<String, String> form = new HashMap<>(8);
        form.put("grant_type", "authorization_code");
        form.put("code", authCallback.getAuthorization_code());
        form.put("client_id", config.getClientId());
        form.put("client_secret", config.getClientSecret());
        form.put("redirect_uri", config.getRedirectUri());

        String response = UnifiedHttpClient.getAdapter().post(source.accessToken(), form, false).getBody();
        return getAuthToken(response);
    }

    /**
     * 使用token换取用户信息
     *
     * @param authToken token信息
     * @return 用户信息
     * @see AuthDefaultRequest#getAccessToken(AuthCallback)
     */
    @Override
    public AuthUser getUserInfo(AuthToken authToken) {
        Map<String, String> form = new HashMap<>(7);
        form.put("nsp_ts", System.currentTimeMillis() + "");
        form.put("access_token", authToken.getAccessToken());
        form.put("nsp_fmt", "JS");
        form.put("nsp_svc", "OpenUP.User.getInfo");

        String response = UnifiedHttpClient.getAdapter().post(source.userInfo(), form, false).getBody();
        JsonNode object = toNode(response);

        this.checkResponse(object);

        AuthUserGender gender = getRealGender(object);

        return AuthUser.builder()
            .rawUserInfo(object)
            .uuid(object.get("userId").asString(""))
            .username(object.get("userNameCn").asString(""))
            .nickname(object.get("userNameCn").asString(""))
            .gender(gender)
            //.avatar(object.getString("headPictureURL"))
            .token(authToken)
            .source(source.toString())
            .build();
    }

    /**
     * 刷新access token （续期）
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    @Override
    public AuthResponse<AuthToken> refresh(AuthToken authToken) {
        Map<String, String> form = new HashMap<>(7);
        form.put("client_id", config.getClientId());
        form.put("client_secret", config.getClientSecret());
        form.put("refresh_token", authToken.getRefreshToken());
        form.put("grant_type", "refresh_token");

        String response = UnifiedHttpClient.getAdapter().post(source.refresh(), form, false).getBody();
        return AuthResponse.<AuthToken>builder().code(SUCCESS.getCode()).data(getAuthToken(response)).build();
    }

    private AuthToken getAuthToken(String response) {
    	JsonNode object = toNode(response);

        this.checkResponse(object);

        return AuthToken.builder()
            .accessToken(object.get("access_token").asString(""))
            .expireIn(object.get("expires_in").asInt(0))
            .refreshToken(object.get("refresh_token").asString(""))
            .build();
    }

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     * @since 1.9.3
     */
    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(super.authorize(state))
            .queryParam("access_type", "offline")
            .queryParam("scope", this.getScopes(" ", true, AuthScopeUtils.getDefaultScopes(AuthHuaweiScope.values())))
            .build();
    }

    /**
     * 返回获取userInfo的url
     *
     * @param authToken token
     * @return 返回获取userInfo的url
     */
    @Override
    protected String userInfoUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(source.userInfo())
            .queryParam("nsp_ts", System.currentTimeMillis())
            .queryParam("access_token", authToken.getAccessToken())
            .queryParam("nsp_fmt", "JS")
            .queryParam("nsp_svc", "OpenUP.User.getInfo")
            .build();
    }

    /**
     * 获取用户的实际性别。华为系统中，用户的性别：1表示女，0表示男
     *
     * @param object obj
     * @return AuthUserGender
     */
    private AuthUserGender getRealGender(JsonNode object) {
        int genderCodeInt = object.get("gender").asInt(0);
        String genderCode = genderCodeInt == 1 ? "0" : (genderCodeInt == 0) ? "1" : genderCodeInt + "";
        return AuthUserGender.getRealGender(genderCode);
    }

    /**
     * 校验响应结果
     *
     * @param object 接口返回的结果
     */
    private void checkResponse(JsonNode object) {
        if (object.has("NSP_STATUS")) {
            throw new AuthException(object.get("error").asString(""));
        }
        if (object.has("error")) {
            throw new AuthException(object.get("sub_error").asString("") + ":" + object.get("error_description").asString(""));
        }
    }
}
