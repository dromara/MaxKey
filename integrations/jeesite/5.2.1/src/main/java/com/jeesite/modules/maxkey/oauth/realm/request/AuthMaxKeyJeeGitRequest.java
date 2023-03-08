package com.jeesite.modules.maxkey.oauth.realm.request;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.modules.maxkey.base.AuthCustomSource;
import com.jeesite.modules.maxkey.utils.AuthCustomExceptionUtils;

import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthDefaultRequest;

public class AuthMaxKeyJeeGitRequest extends AuthDefaultRequest {

    public static final String BASE_HOST = Global.getProperty("oauth2.maxkey.serverUrl");

    /**
     * 设定归属域
     * 
     * @param config
     */
    public AuthMaxKeyJeeGitRequest(AuthConfig config) {
	super(config, AuthCustomSource.MAXKEY);
    }

    public AuthMaxKeyJeeGitRequest(AuthConfig config, AuthSource source) {
	super(config, source);
    }

    public AuthMaxKeyJeeGitRequest(AuthConfig config, AuthStateCache authStateCache) {
	super(config, AuthCustomSource.MAXKEY, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
	String body = doPostAuthorizationCode(authCallback.getCode());
	JSONObject object = JSONObject.parseObject(body);
	System.out.println("getAccessToken:"+JsonMapper.toJson(object));
	AuthCustomExceptionUtils.checkResponse(object);
	return AuthToken.builder().accessToken(object.getString("access_token")).refreshToken(object.getString("refresh_token")).idToken(object.getString("id_token")).tokenType(object.getString("token_type")).scope(object.getString("scope")).build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
	String body = doGetUserInfo(authToken);
	JSONObject object = JSONObject.parseObject(body);
	AuthCustomExceptionUtils.checkResponse(object);
	return AuthUser.builder().uuid(object.getString("id")).username(object.getString("username")).nickname(object.getString("name")).avatar(object.getString("avatar_url")).blog(object.getString("web_url")).company(object.getString("organization")).location(object.getString("location")).email(object.getString("email")).remark(object.getString("bio")).token(authToken).source(source.toString()).build();
    }

}
