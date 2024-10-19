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
 

package me.zhyd.oauth.request;
import me.zhyd.oauth.cache.AuthStateCache;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;

public class AuthMaxkeyRequest extends AuthDefaultRequest {

    public static final String KEY = "maxkey";
    public AuthMaxkeyRequest(AuthConfig config) {
        super(config, WeLinkAuthDefaultSource.HUAWEI_WELINK);
    }

    public AuthMaxkeyRequest(AuthConfig config, AuthStateCache authStateCache) {
        super(config, MaxkeyAuthDefaultSource.MAXKEY, authStateCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        return null;
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        return null;
    }
}
