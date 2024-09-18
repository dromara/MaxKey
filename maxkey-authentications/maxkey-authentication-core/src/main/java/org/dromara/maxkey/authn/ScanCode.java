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

    @NotEmpty(message = "state不能为空")
    String state;

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

    public @NotEmpty(message = "state不能为空") String getState() {
        return state;
    }

    public void setState(@NotEmpty(message = "state不能为空") String state) {
        this.state = state;
    }
}
