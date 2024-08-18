package org.dromara.maxkey.authn;

import jakarta.validation.constraints.NotEmpty;

/**
 * @description:
 * @author: orangeBabu
 * @time: 16/8/2024 PM4:28
 */
public class ScanCode {

    @NotEmpty(message = "二维码内容不能为空")
    String code;

    @NotEmpty(message = "登录方式不能为空")
    String authType;

    public @NotEmpty(message = "二维码内容不能为空") String getCode() {
        return code;
    }

    public void setCode(@NotEmpty(message = "二维码内容不能为空") String code) {
        this.code = code;
    }

    public @NotEmpty(message = "登录方式不能为空") String getAuthType() {
        return authType;
    }

    public void setAuthType(@NotEmpty(message = "登录方式不能为空") String authType) {
        this.authType = authType;
    }
}
