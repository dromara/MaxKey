package org.maxkey.domain.apps;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.maxkey.constants.BOOLEAN;
import org.maxkey.domain.Accounts;
import org.springframework.web.multipart.MultipartFile;

@Table(name = "APPLICATIONS")  
public class Applications extends JpaBaseDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6264641546959620712L;
	
	public static  final class CREDENTIALS{
		public static final int USER_DEFINED=3;
		public static final int SHARED=2;
		public static final int SYSTEM=1;
		public static final int NONE=0;
	}
	
	public static final class VISIBLE{
		public static final int HIDDEN=0;
		public static final int ALL=1;
		public static final int INTERNET=2;
		public static final int INTRANET=3;
	}
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO,generator="uuid")
	protected String id;
	/**
	 * 
	 */
	private String name;
	/*
	 * Login url
	 */
	private String loginUrl;
	
	private String category;
	
	private String protocol;
	
	private String secret;
	/*
	 * icon and icon upload field iconField
	 */
	private byte[] icon;
	private MultipartFile iconFile;
	
	private int visible;
	/*
	 * vendor
	 */
	private String vendor;
	private String vendorUrl;

	/*
	 * CREDENTIAL VALUES
	 * 		USER-DEFINED
	 * 		SYSTEM
	 *		SHARED
	 *		NONE  
	 */
	private int credential;
	private String sharedUsername;
	private String sharedPassword;
	private String systemUserAttr;
	
	//获取第三方token凭证
	private String principal;
	private String credentials;
	
	/*
	 * extendAttr
	 */
	private int isExtendAttr;
	private String extendAttr;
	private String attribute;
	private String attributeValue;
	
	/**
	 * Signature
	 * for client verify
	 * create by SignaturePublicKey &  SignaturePrivateKey
	 * issuer is domain name
	 * subject is app id append domain name
	 */
	private int isSignature;
	
	private int isAdapter;
	
	private String adapter;

	protected Accounts appUser;
	
	protected int sortIndex;
	
	protected String description;
	
	public Applications() {
		super();
		isSignature=BOOLEAN.FALSE;
		credential=CREDENTIALS.NONE;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}





	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}





	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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





	/**
	 * @return the iconFile
	 */
	public MultipartFile getIconFile() {
		return iconFile;
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
	 * @param iconFile the iconFile to set
	 */
	public void setIconFile(MultipartFile iconFile) {
		this.iconFile = iconFile;
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
	public int getCredential() {
		return credential;
	}





	/**
	 * @param credential the credential to set
	 */
	public void setCredential(int credential) {
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





	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}





	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}





	/**
	 * @return the attributeValue
	 */
	public String getAttributeValue() {
		return attributeValue;
	}





	/**
	 * @param attributeValue the attributeValue to set
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
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


	public Accounts getAppUser() {
		return appUser;
	}


	public void setAppUser(Accounts appUser) {
		this.appUser = appUser;
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

	@Override
	public String toString() {
		return "Applications [name=" + name + ", loginUrl=" + loginUrl
				+ ", category=" + category + ", protocol=" + protocol
				+ ", secret=" + secret + ", icon=" + Arrays.toString(icon)
				+ ", iconFile=" + iconFile + ", visible=" + visible
				+ ", vendor=" + vendor + ", vendorUrl=" + vendorUrl
				+ ", credential=" + credential + ", sharedUsername="
				+ sharedUsername + ", sharedPassword=" + sharedPassword
				+ ", systemUserAttr=" + systemUserAttr + ", isExtendAttr="
				+ isExtendAttr + ", extendAttr=" + extendAttr + ", attribute="
				+ attribute + ", attributeValue=" + attributeValue
				+ ", isSignature=" + isSignature
				+ "]";
	}

}
