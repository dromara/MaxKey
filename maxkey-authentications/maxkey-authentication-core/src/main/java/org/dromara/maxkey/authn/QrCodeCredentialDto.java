package org.dromara.maxkey.authn;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description:
 * @author: orangeBabu
 * @time: 16/8/2024 AM10:54
 */


public class QrCodeCredentialDto {


    @NotEmpty(message = "jwtToken不能为空")
    @Schema(name = "jwtToken", description = "token")
    String jwtToken;

    @NotEmpty(message = "返回码不能为空")
    @Schema(name = "code", description = "返回码")
    String code;

    public @NotEmpty(message = "jwtToken不能为空") String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(@NotEmpty(message = "jwtToken不能为空") String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public @NotEmpty(message = "返回码不能为空") String getCode() {
        return code;
    }

    public void setCode(@NotEmpty(message = "返回码不能为空") String code) {
        this.code = code;
    }
}
