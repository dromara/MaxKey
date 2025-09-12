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

package org.dromara.maxkey.passkey.endpoint;

import org.dromara.maxkey.passkey.manager.PasskeyManager;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.authn.web.AuthorizationUtils;
import org.dromara.maxkey.authn.session.Session;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.maxkey.authn.realm.AbstractAuthenticationRealm;
import org.dromara.maxkey.authn.session.SessionManager;
import org.dromara.maxkey.authn.jwt.AuthJwt;
import org.dromara.maxkey.authn.jwt.AuthTokenService;
import org.dromara.maxkey.authn.LoginCredential;
import org.dromara.maxkey.util.IdGenerator;
import org.dromara.maxkey.web.WebContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Passkey认证端点
 * 提供Passkey认证相关的REST API
 */
@RestController
@RequestMapping("/passkey/authentication")
public class PasskeyAuthenticationEndpoint {
    private static final Logger _logger = LoggerFactory.getLogger(PasskeyAuthenticationEndpoint.class);
    
    @Autowired
    private PasskeyManager passkeyManager;
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
    private AbstractAuthenticationRealm authenticationRealm;
    
    @Autowired
    private SessionManager sessionManager;
    
    @Autowired
    private AuthTokenService authTokenService;
    
    // 管理员权限列表
    public static ArrayList<GrantedAuthority> grantedAdministratorsAuthoritys = new ArrayList<GrantedAuthority>();
    
    static {
        grantedAdministratorsAuthoritys.add(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ADMINISTRATORS"));
    }
    
    /**
     * 创建完整的登录会话
     * 参考 AbstractAuthenticationProvider.createOnlineTicket 方法
     * @param userInfo 用户信息
     * @return 认证令牌
     */
    private UsernamePasswordAuthenticationToken createOnlineTicket(UserInfo userInfo) {
        try {
            // 创建登录凭证
            LoginCredential loginCredential = new LoginCredential();
            loginCredential.setUsername(userInfo.getUsername());
            loginCredential.setPassword(""); // Passkey认证不需要密码
            

            
            // 获取用户权限
            List<GrantedAuthority> grantedAuthoritys = authenticationRealm.grantAuthority(userInfo);
            
            // 检查管理员权限
            for(GrantedAuthority administratorsAuthority : grantedAdministratorsAuthoritys) {
                if(grantedAuthoritys.contains(administratorsAuthority)) {
                    grantedAuthoritys.add(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ALL_USER"));
                    break;
                }
            }
            
            // 创建认证主体
            SignPrincipal signPrincipal = new SignPrincipal(userInfo);
            signPrincipal.setAuthenticated(true);
            
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(signPrincipal, "password", grantedAuthoritys);
            authenticationToken.setDetails(new WebAuthenticationDetails(WebContext.getRequest()));
            
            // 创建会话
            IdGenerator idGenerator = new IdGenerator();
            String sessionId = idGenerator.generate();
            Session session = new Session(sessionId, authenticationToken);
            session.setLastAccessTime(LocalDateTime.now());
            
            // 更新SignPrincipal的会话信息
            signPrincipal.setSessionId(session.getId());
            userInfo.setSessionId(session.getId());
            
            // 检查管理员权限
            for (GrantedAuthority administratorsAuthority : grantedAdministratorsAuthoritys) {
                if (grantedAuthoritys.contains(administratorsAuthority)) {
                    signPrincipal.setRoleAdministrators(true);
                    _logger.trace("ROLE ADMINISTRATORS Authentication .");
                }
            }
            _logger.debug("Granted Authority {}", grantedAuthoritys);
            
            // 设置授权应用
            signPrincipal.setGrantedAuthorityApps(authenticationRealm.queryAuthorizedApps(grantedAuthoritys));
            
            // 保存会话
            sessionManager.create(session.getId(), session);
            
            // 将认证信息放入当前会话上下文
            session.setAuthentication(authenticationToken);
            
            // 设置认证信息到 HTTP 会话
            AuthorizationUtils.setAuthentication(authenticationToken);
            
            return authenticationToken;
        } catch (Exception e) {
            _logger.error("创建在线票据失败", e);
            return null;
        }
    }
    
    /**
     * 开始Passkey认证
     * @param request 请求参数
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return 认证选项
     */
    @PostMapping("/begin")
    public ResponseEntity<?> beginAuthentication(
            @RequestBody(required = false) Map<String, Object> request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Begin Passkey authentication request received");
        
        try {
            String userId = null;
            
            // 获取用户ID（可选，支持无用户名登录）
            if (request != null) {
                userId = (String) request.get("userId");
            }
            
            // 对于无用户名登录，先检查系统中是否有任何可用的 Passkey
            if (userId == null || userId.isEmpty()) {
                _logger.debug("Checking for registered passkeys in usernameless authentication");
                boolean hasPasskeys = hasAnyRegisteredPasskeys();
                _logger.debug("Has registered passkeys: {}", hasPasskeys);
                
                // 检查系统中是否有任何注册的 Passkey
                if (!hasPasskeys) {
                    _logger.warn("No Passkeys registered in the system for usernameless authentication");
                    return ResponseEntity.badRequest()
                        .body(new Message<>(Message.ERROR, "系统中还没有注册任何 Passkey，请先注册 Passkey 后再使用此功能"));
                } else {
                    _logger.debug("Found registered passkeys, proceeding with authentication");
                }
            }
            
            // 生成认证选项
            Map<String, Object> options = passkeyManager.beginAuthentication(userId);
            
            // 将认证选项存储到会话中，用于后续验证
            HttpSession session = httpRequest.getSession();
            session.setAttribute("passkey_auth_options", options);
            
            _logger.info("Passkey authentication options generated for user: {}", userId != null ? userId : "anonymous");
            return ResponseEntity.ok(new Message<>(Message.SUCCESS, "认证选项生成成功", options));
            
        } catch (RuntimeException e) {
            // 处理业务逻辑异常（如用户没有 Passkey）
            _logger.warn("Passkey authentication failed: {}", e.getMessage());
            return ResponseEntity.badRequest()
                .body(new Message<>(Message.ERROR, e.getMessage()));
        } catch (Exception e) {
            _logger.error("Error beginning Passkey authentication", e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "生成认证选项失败: " + e.getMessage()));
        }
    }
    
    /**
     * 完成Passkey认证
     * @param request 认证响应数据
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return 认证结果
     */
    @PostMapping("/finish")
    public ResponseEntity<?> finishAuthentication(
            @RequestBody Map<String, Object> request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Finish Passkey authentication request received");
        
        try {
            // 验证认证响应
            Map<String, Object> result = passkeyManager.finishAuthentication(request);
            
            if (result != null && Boolean.TRUE.equals(result.get("success"))) {
                String userId = (String) result.get("userId");
                String credentialId = (String) result.get("credentialId");
                
                // 获取完整的用户信息
                UserInfo userInfo = userInfoService.get(userId);
                if (userInfo == null) {
                    _logger.error("User not found for userId: {}", userId);
                    return ResponseEntity.badRequest()
                        .body(new Message<>(Message.ERROR, "用户信息不存在"));
                }
                
                // 创建完整的登录会话
                UsernamePasswordAuthenticationToken authenticationToken = createOnlineTicket(userInfo);
                
                if (authenticationToken == null) {
                    _logger.error("Failed to create authentication token for user: {}", userId);
                    return ResponseEntity.internalServerError()
                        .body(new Message<>(Message.ERROR, "创建认证会话失败"));
                }
                
                // 获取会话信息
                SignPrincipal principal = (SignPrincipal) authenticationToken.getPrincipal();
                String sessionId = principal.getSessionId();
                
                // 生成认证token
                AuthJwt authJwtObj = authTokenService.genAuthJwt(authenticationToken);
                String authJwt = authJwtObj != null ? authJwtObj.getToken() : null;
                
                // 设置 Passkey 特有的会话信息
                HttpSession httpSession = httpRequest.getSession();
                httpSession.setAttribute("passkey_authenticated", true);
                httpSession.setAttribute("passkey_user_id", userId);
                httpSession.setAttribute("passkey_credential_id", credentialId);
                httpSession.setAttribute("passkey_auth_time", System.currentTimeMillis());
                
                // 清理认证选项
                httpSession.removeAttribute("passkey_auth_options");
                
                _logger.info("Passkey authentication completed successfully for user: {} ({})", userInfo.getUsername(), userId);
                
                // 构建完整的认证结果
                Map<String, Object> responseData = new java.util.HashMap<>();
                responseData.put("userInfo", userInfo);
                responseData.put("onlineTicket", sessionId);
                
                // 关键修改：返回完整的 AuthJwt 对象，而不是只返回 token 字符串
                if (authJwtObj != null) {
                    // 直接返回完整的 AuthJwt 对象，包含所有必要的字段
                    responseData.put("id", authJwtObj.getId());
                    responseData.put("username", authJwtObj.getUsername());
                    responseData.put("displayName", authJwtObj.getDisplayName());
                    responseData.put("email", authJwtObj.getEmail());
                    responseData.put("token", authJwtObj.getToken());
                    responseData.put("ticket", authJwtObj.getTicket());
                    responseData.put("authorities", authJwtObj.getAuthorities()); // 关键：包含权限信息
                    responseData.put("passwordSetType", authJwtObj.getPasswordSetType());
                    responseData.put("remeberMe", authJwtObj.getRemeberMe());
                    responseData.put("expiresIn", authJwtObj.getExpiresIn());
                    responseData.put("refreshToken", authJwtObj.getRefreshToken());
                    responseData.put("instId", authJwtObj.getInstId());
                    responseData.put("instName", authJwtObj.getInstName());
                } else {
                    // 如果 authJwtObj 为空，至少设置基本信息
                    responseData.put("id", userId);
                    responseData.put("username", userInfo.getUsername());
                    responseData.put("displayName", userInfo.getDisplayName());
                    responseData.put("email", userInfo.getEmail());
                    responseData.put("authorities", new ArrayList<>()); // 空权限列表
                }
                responseData.put("userId", userId);
                responseData.put("authTime", System.currentTimeMillis());
                
                // 检查是否有重定向URL
                String redirectUrl = (String) httpSession.getAttribute("redirect_url");
                if (redirectUrl != null) {
                    httpSession.removeAttribute("redirect_url");
                    responseData.put("redirectUrl", redirectUrl);
                    return ResponseEntity.ok(new Message<>(Message.SUCCESS, "Passkey认证成功，即将跳转", responseData));
                } else {
                    responseData.put("redirectUrl", "/index"); // 默认跳转到首页
                    return ResponseEntity.ok(new Message<>(Message.SUCCESS, "Passkey认证成功", responseData));
                }
                
            } else {
                _logger.warn("Passkey authentication failed");
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "Passkey认证失败，请重试"));
            }
            
        } catch (Exception e) {
            _logger.error("Error finishing Passkey authentication", e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "认证验证失败: " + e.getMessage()));
        }
    }
    
    /**
     * 检查系统中是否有任何注册的 Passkey
     * @return 如果系统中有注册的 Passkey 返回 true，否则返回 false
     */
    private boolean hasAnyRegisteredPasskeys() {
        try {
            // 通过 PasskeyManager 检查是否有任何用户注册了 Passkey
            // 这里可以调用一个统计方法或者查询方法
            return passkeyManager.hasAnyRegisteredPasskeys();
        } catch (Exception e) {
            _logger.error("Error checking for registered passkeys", e);
            return false; // 出错时保守返回 false
        }
    }
    
    /**
     * 检查认证状态
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return 认证状态
     */
    @GetMapping("/status")
    public ResponseEntity<?> getAuthenticationStatus(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Get Passkey authentication status request received");
        
        try {
            HttpSession session = httpRequest.getSession(false);
            
            Map<String, Object> status = new java.util.HashMap<>();
            
            if (session != null) {
                Boolean authenticated = (Boolean) session.getAttribute("passkey_authenticated");
                String userId = (String) session.getAttribute("passkey_user_id");
                Long authTime = (Long) session.getAttribute("passkey_auth_time");
                
                status.put("authenticated", authenticated != null && authenticated);
                status.put("userId", userId);
                status.put("authTime", authTime);
                
                // 检查认证是否过期（30分钟）
                if (authTime != null) {
                    long currentTime = System.currentTimeMillis();
                    long authDuration = currentTime - authTime;
                    boolean expired = authDuration > 30 * 60 * 1000; // 30分钟
                    
                    status.put("expired", expired);
                    status.put("remainingTime", expired ? 0 : (30 * 60 * 1000 - authDuration));
                    
                    if (expired) {
                        // 清理过期的认证信息
                        session.removeAttribute("passkey_authenticated");
                        session.removeAttribute("passkey_user_id");
                        session.removeAttribute("passkey_credential_id");
                        session.removeAttribute("passkey_auth_time");
                        
                        status.put("authenticated", false);
                        status.put("userId", null);
                        status.put("authTime", null);
                    }
                } else {
                    status.put("expired", false);
                    status.put("remainingTime", 0);
                }
            } else {
                status.put("authenticated", false);
                status.put("userId", null);
                status.put("authTime", null);
                status.put("expired", false);
                status.put("remainingTime", 0);
            }
            
            _logger.debug("Passkey authentication status: {}", status.get("authenticated"));
            return ResponseEntity.ok(new Message<>(Message.SUCCESS, "获取状态成功", status));
            
        } catch (Exception e) {
            _logger.error("Error getting Passkey authentication status", e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "获取认证状态失败: " + e.getMessage()));
        }
    }
    
    /**
     * 注销Passkey认证
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return 注销结果
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Passkey logout request received");
        
        try {
            HttpSession session = httpRequest.getSession(false);
            
            if (session != null) {
                String userId = (String) session.getAttribute("passkey_user_id");
                
                // 清理所有Passkey相关的会话信息
                session.removeAttribute("passkey_authenticated");
                session.removeAttribute("passkey_user_id");
                session.removeAttribute("passkey_credential_id");
                session.removeAttribute("passkey_auth_time");
                session.removeAttribute("passkey_auth_options");
                
                _logger.info("Passkey logout completed for user: {}", userId);
            }
            
            return ResponseEntity.ok(new Message<>(Message.SUCCESS, "注销成功"));
            
        } catch (Exception e) {
            _logger.error("Error during Passkey logout", e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "注销失败: " + e.getMessage()));
        }
    }
    
    /**
     * 验证当前会话的Passkey认证状态
     * @param httpRequest HTTP请求
     * @param httpResponse HTTP响应
     * @return 验证结果
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyAuthentication(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) {
        
        _logger.debug("Verify Passkey authentication request received");
        
        try {
            HttpSession session = httpRequest.getSession(false);
            
            if (session == null) {
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "会话不存在"));
            }
            
            Boolean authenticated = (Boolean) session.getAttribute("passkey_authenticated");
            String userId = (String) session.getAttribute("passkey_user_id");
            Long authTime = (Long) session.getAttribute("passkey_auth_time");
            
            if (authenticated == null || !authenticated || userId == null || authTime == null) {
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "未认证或认证信息不完整"));
            }
            
            // 检查认证是否过期
            long currentTime = System.currentTimeMillis();
            long authDuration = currentTime - authTime;
            boolean expired = authDuration > 30 * 60 * 1000; // 30分钟
            
            if (expired) {
                // 清理过期的认证信息
                session.removeAttribute("passkey_authenticated");
                session.removeAttribute("passkey_user_id");
                session.removeAttribute("passkey_credential_id");
                session.removeAttribute("passkey_auth_time");
                
                return ResponseEntity.badRequest()
                    .body(new Message<>(Message.ERROR, "认证已过期"));
            }
            
            Map<String, Object> result = new java.util.HashMap<>();
            result.put("valid", true);
            result.put("userId", userId);
            result.put("authTime", authTime);
            result.put("remainingTime", 30 * 60 * 1000 - authDuration);
            
            _logger.debug("Passkey authentication verified for user: {}", userId);
            return ResponseEntity.ok(new Message<>(Message.SUCCESS, "认证有效", result));
            
        } catch (Exception e) {
            _logger.error("Error verifying Passkey authentication", e);
            return ResponseEntity.internalServerError()
                .body(new Message<>(Message.ERROR, "验证认证状态失败: " + e.getMessage()));
        }
    }
}