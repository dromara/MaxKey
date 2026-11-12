package org.dromara.maxkey.authn;

/** 签发请求 . */
public class CongressRequest {
    // MaxKey 中已存在的用户名
    String username;
    // 可选 , 目标单点登录应用ID , 传入则直接返回可302的完整跳转地址
    String appId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
