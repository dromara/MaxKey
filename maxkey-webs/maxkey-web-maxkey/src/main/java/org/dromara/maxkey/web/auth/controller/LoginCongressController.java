/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
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

package org.dromara.maxkey.web.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authn.CongressRequest;
import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.provider.AbstractAuthenticationProvider;
import org.dromara.maxkey.configuration.ApplicationConfig;
import org.dromara.maxkey.constants.ConstsLoginType;
import org.dromara.maxkey.crypto.Base64Utils;
import org.dromara.maxkey.crypto.password.PasswordReciprocal;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.apps.Apps;
import org.dromara.maxkey.persistence.service.AppsService;
import org.dromara.maxkey.http.AuthorizationHeader;
import org.dromara.maxkey.http.AuthorizationHeaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * congress 免登签发接口 .
 * 受信上游系统服务端调用 , 为已在本系统登录的用户签发 MaxKey 一次性登录令牌(congress) ,
 * 浏览器访问返回的 redirect 地址即可静默登录 MaxKey 并继续单点登录到目标应用 ,
 * 全程无需在 MaxKey 登录页输入账号密码 .
 *
 * @author limingxin
 */
@Tag(name = "1-5-congress免登签发接口模块")
@RestController
@RequestMapping(value = "/login/congress")
public class LoginCongressController {
    static final Logger logger = LoggerFactory.getLogger(LoginCongressController.class);

    @Autowired
    AuthTokenService authTokenService;

    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    AbstractAuthenticationProvider authenticationProvider;

    @Autowired
    AppsService appsService;

    @Operation(summary = "congress令牌签发接口",
                description = "受信应用服务端调用 , Basic(appId:secret)认证 , 为指定用户签发一次性登录令牌",
                method = "POST")
    @PostMapping(value = {"/request"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<HashMap<String, String>> request(
            HttpServletRequest httpRequest,
            @RequestBody CongressRequest congressRequest) {
        // 1.应用 Basic 认证 , 凭据为 MaxKey 应用的 appId 和 secret
        AuthorizationHeader headerCredential = AuthorizationHeaderUtils.resolve(httpRequest);
        if (headerCredential == null || !headerCredential.isBasic()
                || StringUtils.isBlank(headerCredential.getUsername())
                || StringUtils.isBlank(headerCredential.getCredential())) {
            return new Message<>(Message.FAIL, "应用认证失败 , 需要 Basic appId:secret 认证");
        }
        Apps app = appsService.get(headerCredential.getUsername(), false);
        //secret 校验必须实时查询 , 避免缓存导致密钥轮换后旧secret仍可通过
        boolean secretMatched = false;
        if (app != null && StringUtils.isNotBlank(app.getSecret())) {
            try {
                secretMatched = PasswordReciprocal.getInstance().matches(
                        headerCredential.getCredential(), app.getSecret());
            } catch (RuntimeException e) {
                logger.warn("app {} secret verify failed : {}",
                        headerCredential.getUsername(), e.getMessage());
            }
        }
        if (!secretMatched) {
            return new Message<>(Message.FAIL, "应用ID或Secret不正确");
        }

        // 2.参数校验
        if (congressRequest == null || StringUtils.isBlank(congressRequest.getUsername())) {
            return new Message<>(Message.FAIL, "username不能为空");
        }
        Apps targetApp = null;
        if (StringUtils.isNotBlank(congressRequest.getAppId())) {
            targetApp = appsService.get(congressRequest.getAppId(), false);
            if (targetApp == null) {
                return new Message<>(Message.FAIL, "目标应用不存在");
            }
            if (StringUtils.isBlank(targetApp.getLoginUrl())) {
                return new Message<>(Message.FAIL, "目标应用未配置登录地址");
            }
        }

        // 3.trusted 登录 , 仅凭用户名建立会话 , 与 RememberMe 相同的内部受信通道
        Authentication authentication = null;
        try {
            LoginCredential credential = new LoginCredential(
            		congressRequest.getUsername().trim(), "", ConstsLoginType.CONGRESS);
            authentication = authenticationProvider.authenticate(credential, true);
        } catch (AuthenticationException e) {
            logger.error("congress create for {} failed : {}",
            		congressRequest.getUsername(), e.getMessage());
            return new Message<>(Message.FAIL, "用户认证失败 , " + e.getMessage());
        }
        if (authentication == null || !authentication.isAuthenticated()) {
            return new Message<>(Message.FAIL, "用户认证失败");
        }

        // 4.签发一次性 congress 令牌
        String congress = authTokenService.createCongress(authentication);

        HashMap<String, String> data = new HashMap<>();
        data.put("congress", congress);
        if (targetApp != null) {
            // 登录成功后跳转目标应用的登录地址 , 由其自行生成state并发起 OAuth 2.0 authorize 请求 .
            // 不能跳 /authz/{appId} : IdP 发起式流程不经过目标应用生成state , 回调会被拒绝
            String frontendUri = applicationConfig.getFrontendUri();
            if (!frontendUri.startsWith("http")) {
                // frontend.uri 为相对路径前缀(如 /maxkey)时 , 补上当前请求的对外地址
                frontendUri = buildRequestOrigin(httpRequest) + frontendUri;
            }
            String redirect = frontendUri
                    + "/#/passport/login?congress=" + congress
                    + "&redirect_uri=" + Base64Utils.base64UrlEncode(
                            targetApp.getLoginUrl().getBytes(StandardCharsets.UTF_8));
            data.put("redirect", redirect);
        }

        logger.info("app {} create congress for user {}",
                headerCredential.getUsername(), congressRequest.getUsername());
        return new Message<>(data);
    }

    /** 当前请求对外可达的 origin , 如 http://sso.corp.com:21717 . */
    protected String buildRequestOrigin(HttpServletRequest httpRequest) {
        String scheme = httpRequest.getScheme();
        StringBuilder origin = new StringBuilder(scheme).append("://").append(httpRequest.getServerName());
        int port = httpRequest.getServerPort();
        if (("http".equals(scheme) && port == 80) || ("https".equals(scheme) && port == 443)) {
            return origin.toString();
        }
        return origin.append(":").append(port).toString();
    }
}
