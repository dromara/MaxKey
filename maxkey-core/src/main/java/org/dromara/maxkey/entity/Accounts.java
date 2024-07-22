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
import java.util.HashMap;
import java.util.List;

import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.mybatis.jpa.entity.JpaEntity;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MXK_ACCOUNTS")
public class Accounts extends JpaEntity implements Serializable {
    private static final long serialVersionUID = 6829592256223630307L;
    
    public  static final String DEFAULT_PASSWORD_SUFFIX = UserInfo.DEFAULT_PASSWORD_SUFFIX;
    
    @Id
    @Column
    @GeneratedValue
    private String id;
    @Column
    private String userId;
    @Column
    private String username;
    @Column
    private String displayName;
    @Column
    private String appId;
    @Column
    private String appName;

    @Length(max = 60)
    @Column
    private String relatedUsername;
    @Column
    private String relatedPassword;
    @Column
    private String createType;
    @Column
    private String strategyId;
    @Column
    private String strategyName;
    @Column
    private int status;
    
    @Column
    private String instId;
    
    private String instName;
    
    UserInfo userInfo;
    
    @JsonIgnore
    private HashMap<String,OrganizationsCast> orgCast =new HashMap<>();

    public Accounts() {
        super();
    }

    public Accounts(String id) {
        this.id = id;
    }

    public Accounts(String userId, String appId) {
        this.userId = userId;
        this.appId = appId;
    }

    public Accounts(String userId, String appId, String password) {
        this.userId = userId;
        this.appId = appId;
        this.relatedPassword = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getRelatedUsername() {
        return relatedUsername;
    }

    public void setRelatedUsername(String relatedUsername) {
        this.relatedUsername = relatedUsername;
    }

    public String getRelatedPassword() {
        return relatedPassword;
    }

    public void setRelatedPassword(String relatedPassword) {
        this.relatedPassword = relatedPassword;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    
    public String getCreateType() {
        return createType;
    }

    public void setCreateType(String createType) {
        this.createType = createType;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public HashMap<String, OrganizationsCast> getOrgCast() {
        return orgCast;
    }

    public void setOrgCast(HashMap<String, OrganizationsCast> orgCast) {
        this.orgCast = orgCast;
    }
    
    public void setOrgCast(List <OrganizationsCast> listOrgCast) {
        for(OrganizationsCast cast : listOrgCast) {
            this.orgCast.put(cast.getProvider(), cast);
        }
    }

    public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	@Override
    public String toString() {
        return "AppAccounts [uid=" + userId + ", username=" + username + ", displayName=" + displayName + ", appId="
                + appId + ", appName=" + appName + ", relatedUsername=" + relatedUsername + ", relatedPassword="
                + relatedPassword + "]";
    }

}
