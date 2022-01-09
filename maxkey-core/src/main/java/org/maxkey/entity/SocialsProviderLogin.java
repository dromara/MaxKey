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
 

package org.maxkey.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Crystal.Sea
 *
 */

public class SocialsProviderLogin implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2672107566766342357L;
	
	List<SocialsProvider> socialSignOnProviders = new ArrayList<SocialsProvider>();
	
	String dingTalkLogin 		= "none";
	
	String workWeixinLogin 		= "none";
	
	String feiShuLogin 			= "none";
	
	String weLinkLogin 			= "none";

	public SocialsProviderLogin(List<SocialsProvider> socialSignOnProviders) {
		super();
		this.socialSignOnProviders = socialSignOnProviders;
	}

	public String getDingTalkLogin() {
		return dingTalkLogin;
	}

	public void setDingTalkLogin(String dingTalkLogin) {
		this.dingTalkLogin = dingTalkLogin;
	}

	public String getWorkWeixinLogin() {
		return workWeixinLogin;
	}

	public void setWorkWeixinLogin(String workWeixinLogin) {
		this.workWeixinLogin = workWeixinLogin;
	}

	public String getFeiShuLogin() {
		return feiShuLogin;
	}

	public void setFeiShuLogin(String feiShuLogin) {
		this.feiShuLogin = feiShuLogin;
	}

	public String getWeLinkLogin() {
		return weLinkLogin;
	}

	public void setWeLinkLogin(String weLinkLogin) {
		this.weLinkLogin = weLinkLogin;
	}

	public List<SocialsProvider> getSocialSignOnProviders() {
		return socialSignOnProviders;
	}

	
}
