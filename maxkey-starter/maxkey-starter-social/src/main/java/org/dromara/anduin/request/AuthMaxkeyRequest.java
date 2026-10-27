/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.anduin.request;
import org.dromara.anduin.config.AuthConfig;
import org.dromara.anduin.cache.AuthStateCache;
import org.dromara.anduin.model.AuthCallback;
import org.dromara.anduin.model.AuthToken;
import org.dromara.anduin.model.AuthUser;

public class AuthMaxkeyRequest extends AuthDefaultRequest {

    public static final String KEY = "maxkey";
    public AuthMaxkeyRequest(AuthConfig config) {
        super(config, WeLinkAuthDefaultSource.HUAWEI_WELINK);
    }

    public AuthMaxkeyRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, MaxkeyAuthDefaultSource.MAXKEY, authStateCache);
    }

    @Override
    public AuthToken getAccessToken(AuthCallback authCallback) {
        return null;
    }

    @Override
    public AuthUser getUserInfo(AuthToken authToken) {
        return null;
    }
}
