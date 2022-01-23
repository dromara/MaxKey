package me.zhyd.oauth.request;

import me.zhyd.oauth.config.AuthSource;

public enum WeLinkAuthDefaultSource implements AuthSource{

	 HUAWEI_WELINK {
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
