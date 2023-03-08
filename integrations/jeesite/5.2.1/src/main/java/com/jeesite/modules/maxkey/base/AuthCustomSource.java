package com.jeesite.modules.maxkey.base;

import com.jeesite.modules.maxkey.oauth.realm.request.AuthMaxKeyJeeGitRequest;

import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.request.AuthDefaultRequest;

/**
 * Oauth2 默认接口说明
 * 
 * @author 长春叭哥 2023年02月23日
 *
 */
public enum AuthCustomSource implements AuthSource {

    /**
     * 自己搭建的gitlab私服
     */
    MAXKEY {
	/**
	 * 授权的api
	 *
	 * @return url
	 */
	@Override
	public String authorize() {
	    return AuthMaxKeyJeeGitRequest.BASE_HOST + "/sign/authz/oauth/v20/authorize";
	}

	/**
	 * 获取accessToken的api
	 *
	 * @return url
	 */
	@Override
	public String accessToken() {
	    return AuthMaxKeyJeeGitRequest.BASE_HOST + "/sign/authz/oauth/v20/token";
	}

	/**
	 * 获取用户信息的api
	 *
	 * @return url
	 */
	@Override
	public String userInfo() {
	    return AuthMaxKeyJeeGitRequest.BASE_HOST + "/sign/api/oauth/v20/me";
	}

	/**
	 * 平台对应的 AuthRequest 实现类，必须继承自 {@link AuthDefaultRequest}
	 *
	 * @return class
	 */
	@Override
	public Class<? extends AuthDefaultRequest> getTargetClass() {
	    return AuthMaxKeyJeeGitRequest.class;
	}
    }
}