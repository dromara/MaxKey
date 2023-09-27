package com.pig4cloud.pig.common.security.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class CasProperties {

	/**
	 * 秘钥
	 */
	@Value("${cas.key}")
	private String casKey;

	/**
	 * cas服务端地址
	 */
	@Value("${cas.server.host.url}")
	private String casServerUrl;

	/**
	 * cas服务端地址
	 */
	@Value("${cas.server.host.grant_url}")
	private String casGrantingUrl;

	/**
	 * cas服务端登录地址
	 */
	@Value("${cas.server.host.login_url}")
	private String casServerLoginUrl;

	/**
	 * cas服务端登出地址 并回跳到制定页面
	 */
	@Value("${cas.server.host.logout_url}")
	private String casServerLogoutUrl;

	/**
	 * cas客户端地址
	 */
	@Value("${cas.service.host.url}")
	private String casServiceUrl;

	/**
	 * cas客户端地址登录地址
	 */
	@Value("${cas.service.host.login_url}")
	private String casServiceLoginUrl;

	/**
	 * cas客户端地址登出地址
	 */
	@Value("${cas.service.host.logout_url}")
	private String casServiceLogoutUrl;

}
