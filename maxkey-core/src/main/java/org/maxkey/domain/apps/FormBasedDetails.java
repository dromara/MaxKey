package org.maxkey.domain.apps;


/**
 * @author Crystal.Sea
 *
 */
public class FormBasedDetails  extends Applications {
	/**
	 * 
	 */
	private static final long serialVersionUID = 563313247706861431L;
	
	
	private String redirectUri;
	private String usernameMapping;
	private String passwordMapping;
	private String authorizeView;


	/**
	 * 
	 */
	public FormBasedDetails() {
		
	}


	/**
	 * @return the redirectUri
	 */
	public String getRedirectUri() {
		return redirectUri;
	}


	/**
	 * @param redirectUri the redirectUri to set
	 */
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}


	/**
	 * @return the usernameMapping
	 */
	public String getUsernameMapping() {
		return usernameMapping;
	}

	/**
	 * @param usernameMapping the usernameMapping to set
	 */
	public void setUsernameMapping(String usernameMapping) {
		this.usernameMapping = usernameMapping;
	}

	/**
	 * @return the passwordMapping
	 */
	public String getPasswordMapping() {
		return passwordMapping;
	}

	/**
	 * @param passwordMapping the passwordMapping to set
	 */
	public void setPasswordMapping(String passwordMapping) {
		this.passwordMapping = passwordMapping;
	}


	public String getAuthorizeView() {
		return authorizeView;
	}


	public void setAuthorizeView(String authorizeView) {
		this.authorizeView = authorizeView;
	}


	@Override
	public String toString() {
		return "FormBasedDetails [redirectUri=" + redirectUri
				+ ", usernameMapping=" + usernameMapping + ", passwordMapping="
				+ passwordMapping + ", authorizeView=" + authorizeView + "]";
	}

}
