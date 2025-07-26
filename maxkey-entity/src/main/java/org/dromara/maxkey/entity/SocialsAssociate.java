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
 

package org.dromara.maxkey.entity;

import java.io.Serializable;
import java.util.Date;

import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 * @author Crystal.Sea
 */
@Entity
@Table(name = "MXK_SOCIALS_ASSOCIATE")
public class SocialsAssociate extends JpaEntity  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2151179554190800162L;
	@Id
    @Column
    @GeneratedValue
	private String id;
	@Column
	private String provider;
	private String providerName;
	private String icon;
	
	@Column
	private String userId;
	@Column
	private String username;
	@Column
	private String socialUserId;
	@Column
	private String socialUserInfo;
	private String accessToken;
	private String exAttribute; 
	@Column
    private Date createdDate;
    @Column
    private Date updatedDate;
    @Column
    private String instId;
	
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String uid) {
		this.userId = uid;
	}

	public String getSocialUserId() {
		return socialUserId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSocialUserId(String socialUserId) {
		this.socialUserId = socialUserId;
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

    public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	
	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SocialsAssociate [id=");
        builder.append(id);
        builder.append(", provider=");
        builder.append(provider);
        builder.append(", uid=");
        builder.append(userId);
        builder.append(", username=");
        builder.append(username);
        builder.append(", socialuid=");
        builder.append(socialUserId);
        builder.append(", socialUserInfo=");
        builder.append(socialUserInfo);
        builder.append(", accessToken=");
        builder.append(accessToken);
        builder.append(", exAttribute=");
        builder.append(exAttribute);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append("]");
        return builder.toString();
    }
}
