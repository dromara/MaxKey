/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
 * @author Crystal.Sea
 *
 */
@Entity
@Table(name = "MXK_SOCIALS_PROVIDER")
public class SocialsProvider extends JpaEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1636727203025187769L;
    @Id
    @Column
    @GeneratedValue
    private String id;
    @Column
	private String provider;
    @Column
	private String providerName;
    @Column
	private String icon;
    @Column
	private String clientId;
    @Column
	private String clientSecret;
    @Column
    private String agentId;
    @Column
    private String display;
    @Column
    private long sortIndex;
    @Column
    private String scanCode;
    @Column
    private int status;
    @Column
	private String instId;
    @Column
    String createdBy;
    @Column
    Date createdDate;
    @Column
    String modifiedBy;
    @Column
    Date modifiedDate;
    
	private String redirectUri;
	
	private String accountId;
	private String bindTime;
	private String unBindTime;
	private String lastLoginTime;
	private String state;
	
	
	private boolean userBind;
	
	/**
	 * 
	 */
	public SocialsProvider() {

	}
	
	public SocialsProvider(SocialsProvider copy) {
		this.clientId = copy.getClientId();
		this.id = copy.getId();
		this.provider = copy.getProvider();
		this.providerName = copy.getProviderName();
		this.agentId = copy.getAgentId();
		this.icon = copy.getIcon();
		this.scanCode = copy.getScanCode();
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public boolean isUserBind() {
		return userBind;
	}

	public void setUserBind(boolean userBind) {
		this.userBind = userBind;
	}

	
    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public String getUnBindTime() {
        return unBindTime;
    }

    public void setUnBindTime(String unBindTime) {
        this.unBindTime = unBindTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public long getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(long sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getScanCode() {
		return scanCode;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SocialsProvider [id=");
		builder.append(id);
		builder.append(", provider=");
		builder.append(provider);
		builder.append(", providerName=");
		builder.append(providerName);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", clientId=");
		builder.append(clientId);
		builder.append(", clientSecret=");
		builder.append(clientSecret);
		builder.append(", agentId=");
		builder.append(agentId);
		builder.append(", display=");
		builder.append(display);
		builder.append(", sortIndex=");
		builder.append(sortIndex);
		builder.append(", scanCode=");
		builder.append(scanCode);
		builder.append(", status=");
		builder.append(status);
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append(", modifiedDate=");
		builder.append(modifiedDate);
		builder.append(", redirectUri=");
		builder.append(redirectUri);
		builder.append(", accountId=");
		builder.append(accountId);
		builder.append(", bindTime=");
		builder.append(bindTime);
		builder.append(", unBindTime=");
		builder.append(unBindTime);
		builder.append(", lastLoginTime=");
		builder.append(lastLoginTime);
		builder.append(", state=");
		builder.append(state);
		builder.append(", userBind=");
		builder.append(userBind);
		builder.append("]");
		return builder.toString();
	}
	
	

}
