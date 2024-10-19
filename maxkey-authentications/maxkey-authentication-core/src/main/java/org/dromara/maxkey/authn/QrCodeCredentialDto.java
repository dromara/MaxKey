/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authn;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;


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
