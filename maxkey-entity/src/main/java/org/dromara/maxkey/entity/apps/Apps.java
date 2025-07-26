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
 

package org.dromara.maxkey.entity.apps;

import java.io.Serializable;
import java.util.Date;

import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.crypto.Base64Utils;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "MXK_APPS")
public class Apps extends JpaEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6264641546959620712L;

    public static final class CREDENTIALS {
        public static final String USER_DEFINED = "user_defined";
        public static final String SHARED 		= "shared";
        public static final String SYSTEM 		= "system";
        public static final String NONE 		= "none";
    }

    public static final class VISIBLE {
        public static final int HIDDEN = 0;
        public static final int ALL = 1;
        public static final int INTERNET = 2;
        public static final int INTRANET = 3;
    }

    @Id
    @Column
    @GeneratedValue
    protected String id;
    /**
     * 
     */
    @Column
    private String appName;
    /*
     * Login url
     */
    @Column
    private String loginUrl;
    @Column
    private String category;
    @Column
    private String protocol;
    @Column
    private String secret;
    /*
     * icon and icon upload field iconField
     */
    @Column
    private byte[] icon;
    private String iconBase64;
    String iconId;
    
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private int visible;
    
    //引导方式 IDP OR SP,default is IDP
    private String inducer;
    /*
     * vendor
     */
    @Column
    private String vendor;
    @Column
    private String vendorUrl;

    /*
     * CREDENTIAL VALUES USER-DEFINED SYSTEM SHARED NONE
     */
    @Column
    private String credential;
    @Column
    private String sharedUsername;
    @Column
    private String sharedPassword;
    @Column
    private String systemUserAttr;

    // 获取第三方token凭证
    @Column
    private String principal;
    @Column
    private String credentials;
    @Column
    private String logoutUrl;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private int logoutType;
    /*
     * extendAttr
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private int isExtendAttr;
    private String extendAttr;
    
    private String userPropertys;

    /**
     * Signature for client verify create by SignaturePublicKey &
     * SignaturePrivateKey issuer is domain name subject is app id append domain
     * name
     */
    @Column
    private int isSignature;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private int isAdapter;
    @Column
    private String adapterId;
    
    @Column
    private String adapterName;
    
    @Column
    private String adapter;

	@Column
	private String frequently;
	
    @Column
    protected int sortIndex;
    @Column
    protected int status;
    @Column
    protected String createdBy;
    @Column
    protected Date createdDate;
    @Column
    protected String modifiedBy;
    @Column
    protected Date modifiedDate;
    @Column
    protected String description;
	@Column
	private String instId;
	
	private String instName;
    
    protected String loginDateTime;
    
    public Apps() {
        super();
        isSignature = ConstsBoolean.FALSE;
    }

  

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
		return appName;
	}



	public void setAppName(String appName) {
		this.appName = appName;
	}



	/**
     * @return the loginUrl
     */
    public String getLoginUrl() {
        return loginUrl;
    }

    /**
     * @param loginUrl the loginUrl to set
     */
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    
    public String getFrequently() {
		return frequently;
	}

	public void setFrequently(String frequently) {
		this.frequently = frequently;
	}

	/**
     * @return the icon
     */
    public byte[] getIcon() {
        return icon;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconBase64() {
		return iconBase64;
	}

	public void setIconBase64(String iconBase64) {
		this.iconBase64 = iconBase64;
	}
	
	public void transIconBase64() {
		if(icon !=null) {
			this.iconBase64 = Base64Utils.encodeImage(icon);
		}
	}

    public String getIconId() {
		return iconId;
	}

	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	/**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

 

    /**
     * @return the vendor
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * @param vendor the vendor to set
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * @return the vendorUrl
     */
    public String getVendorUrl() {
        return vendorUrl;
    }

    /**
     * @param vendorUrl the vendorUrl to set
     */
    public void setVendorUrl(String vendorUrl) {
        this.vendorUrl = vendorUrl;
    }

    /**
     * @return the credential
     */
    public String getCredential() {
        return credential;
    }

    /**
     * @param credential the credential to set
     */
    public void setCredential(String credential) {
        this.credential = credential;
    }

    /**
     * @return the sharedUsername
     */
    public String getSharedUsername() {
        return sharedUsername;
    }

    /**
     * @param sharedUsername the sharedUsername to set
     */
    public void setSharedUsername(String sharedUsername) {
        this.sharedUsername = sharedUsername;
    }

    /**
     * @return the sharedPassword
     */
    public String getSharedPassword() {
        return sharedPassword;
    }

    /**
     * @param sharedPassword the sharedPassword to set
     */
    public void setSharedPassword(String sharedPassword) {
        this.sharedPassword = sharedPassword;
    }

    /**
     * @return the systemUserAttr
     */
    public String getSystemUserAttr() {
        return systemUserAttr;
    }

    /**
     * @param systemUserAttr the systemUserAttr to set
     */
    public void setSystemUserAttr(String systemUserAttr) {
        this.systemUserAttr = systemUserAttr;
    }

    /**
     * @return the isExtendAttr
     */
    public int getIsExtendAttr() {
        return isExtendAttr;
    }

    /**
     * @param isExtendAttr the isExtendAttr to set
     */
    public void setIsExtendAttr(int isExtendAttr) {
        this.isExtendAttr = isExtendAttr;
    }

    /**
     * @return the extendAttr
     */
    public String getExtendAttr() {
        return extendAttr;
    }

    /**
     * @param extendAttr the extendAttr to set
     */
    public void setExtendAttr(String extendAttr) {
        this.extendAttr = extendAttr;
    }

    public String getUserPropertys() {
        return userPropertys;
    }

    public void setUserPropertys(String userPropertys) {
        this.userPropertys = userPropertys;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getIsSignature() {
        return isSignature;
    }

    public void setIsSignature(int isSignature) {
        this.isSignature = isSignature;
    }

    /**
     * @return the isAdapter
     */
    public int getIsAdapter() {
        return isAdapter;
    }

    /**
     * @param isAdapter the isAdapter to set
     */
    public void setIsAdapter(int isAdapter) {
        this.isAdapter = isAdapter;
    }

    /**
     * @return the adapter
     */
    public String getAdapter() {
        return adapter;
    }

    /**
     * @param adapter the adapter to set
     */
    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
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


    /**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}



	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}



	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getInducer() {
        return inducer;
    }

    public void setInducer(String inducer) {
        this.inducer = inducer;
    }
    

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public int getLogoutType() {
        return logoutType;
    }

    public void setLogoutType(int logoutType) {
        this.logoutType = logoutType;
    }

    
    public String getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(String loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public String getAdapterId() {
		return adapterId;
	}

	public void setAdapterId(String adapterId) {
		this.adapterId = adapterId;
	}

	public String getAdapterName() {
		return adapterName;
	}

	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
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
		StringBuilder builder = new StringBuilder();
		builder.append("Apps [id=");
		builder.append(id);
		builder.append(", appName=");
		builder.append(appName);
		builder.append(", loginUrl=");
		builder.append(loginUrl);
		builder.append(", category=");
		builder.append(category);
		builder.append(", protocol=");
		builder.append(protocol);
		builder.append(", secret=");
		builder.append(secret);
		builder.append(", iconId=");
		builder.append(iconId);
		builder.append(", visible=");
		builder.append(visible);
		builder.append(", inducer=");
		builder.append(inducer);
		builder.append(", vendor=");
		builder.append(vendor);
		builder.append(", vendorUrl=");
		builder.append(vendorUrl);
		builder.append(", credential=");
		builder.append(credential);
		builder.append(", sharedUsername=");
		builder.append(sharedUsername);
		builder.append(", sharedPassword=");
		builder.append(sharedPassword);
		builder.append(", systemUserAttr=");
		builder.append(systemUserAttr);
		builder.append(", principal=");
		builder.append(principal);
		builder.append(", credentials=");
		builder.append(credentials);
		builder.append(", logoutUrl=");
		builder.append(logoutUrl);
		builder.append(", logoutType=");
		builder.append(logoutType);
		builder.append(", isExtendAttr=");
		builder.append(isExtendAttr);
		builder.append(", extendAttr=");
		builder.append(extendAttr);
		builder.append(", userPropertys=");
		builder.append(userPropertys);
		builder.append(", isSignature=");
		builder.append(isSignature);
		builder.append(", isAdapter=");
		builder.append(isAdapter);
		builder.append(", adapterId=");
		builder.append(adapterId);
		builder.append(", adapterName=");
		builder.append(adapterName);
		builder.append(", adapter=");
		builder.append(adapter);
		builder.append(", sortIndex=");
		builder.append(sortIndex);
		builder.append(", status=");
		builder.append(status);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append(", modifiedDate=");
		builder.append(modifiedDate);
		builder.append(", description=");
		builder.append(description);
		builder.append(", loginDateTime=");
		builder.append(loginDateTime);
		builder.append("]");
		return builder.toString();
	}

}
