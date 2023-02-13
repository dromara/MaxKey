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

import me.zhyd.oauth.config.AuthSource;

public enum MaxkeyAuthDefaultSource implements AuthSource{


	 MAXKEY {
	        @Override
	        public String authorize() {
	            return "https://login.welink.huaweicloud.com/connect/oauth2/sns_authorize";
	        }

	        @Override
	        public String accessToken() {
	            return "https://open.welink.huaweicloud.com/api/auth/v2/tickets";
	        }

	        @Override
	        public String userInfo() {
	            return "https://open.welink.huaweicloud.com/api/contact/v1/users";
	        }

	        @Override
	        public String refresh() {
	            return "";
	        }

	        @Override
	        public Class<? extends AuthDefaultRequest> getTargetClass() {
	            return AuthHuaweiWeLinkRequest.class;
	        }
	    }
}
