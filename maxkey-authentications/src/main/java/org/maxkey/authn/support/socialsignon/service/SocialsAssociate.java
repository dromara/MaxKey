/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.maxkey.authn.support.socialsignon.service;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;

/**
 * 
 * @author Crystal.Sea
 */
public class SocialsAssociate extends JpaBaseDomain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2151179554190800162L;
	private String id;
	private String provider;
	private String uid;
	private String username;
	private String socialuid;
	private String socialUserInfo;
	private String accessToken;
	private String exAttribute; 
	
	public SocialsAssociate() {}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSocialuid() {
		return socialuid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSocialuid(String socialuid) {
		this.socialuid = socialuid;
	}

	public String getSocialUserInfo() {
		return socialUserInfo;
	}

	public void setSocialUserInfo(String socialUserInfo) {
		this.socialUserInfo = socialUserInfo;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExAttribute() {
		return exAttribute;
	}

	public void setExAttribute(String exAttribute) {
		this.exAttribute = exAttribute;
	}

	@Override
	public String toString() {
		return "SocialSignOnUserToken [provider=" + provider + ", uid=" + uid
				+ ", socialuid=" + socialuid + ", socialUserInfo="
				+ socialUserInfo + ", accessToken=" + accessToken
				+ ", exAttribute=" + exAttribute + "]";
	}
}
