package com.jeesite.modules.maxkey.utils;

import com.alibaba.fastjson.JSONObject;

import me.zhyd.oauth.exception.AuthException;

public class AuthCustomExceptionUtils {

    public static void checkResponse(JSONObject object) {
        // oauth/token 验证异常
        if (object.containsKey("error")) {
            throw new AuthException(object.getString("error_description"));
        }
        // user 验证异常
        if (object.containsKey("message")) {
            throw new AuthException(object.getString("message"));
        }
    }
}
