package me.zhyd.oauth.config;

import me.zhyd.oauth.request.AuthDefaultRequest;
import me.zhyd.oauth.request.AuthFeishu2Request;

public enum AuthMxkDefaultSource implements AuthSource {
	 FEISHU2 {
	        @Override
	        public String authorize() {
	            return "https://passport.feishu.cn/suite/passport/oauth/authorize";
	        }

	        @Override
	        public String accessToken() {
	            return "https://passport.feishu.cn/suite/passport/oauth/token";
	        }

	        @Override
	        public String userInfo() {
	            return "https://passport.feishu.cn/suite/passport/oauth/userinfo";
	        }

	        @Override
	        public String refresh() {
	            return "https://passport.feishu.cn/suite/passport/oauth/token";
	        }

	        @Override
	        public Class<? extends AuthDefaultRequest> getTargetClass() {
	            return AuthFeishu2Request.class;
	        }
	    }

}
