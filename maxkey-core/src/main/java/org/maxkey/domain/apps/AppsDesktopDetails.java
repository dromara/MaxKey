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
 

/**
 * 
 */
package org.maxkey.domain.apps;

import javax.persistence.Table;

import org.maxkey.domain.Accounts;


/**
 * @author Crystal.Sea
 *
 */
@Table(name = "MXK_APPS_DESKTOP_DETAILS")  
public class AppsDesktopDetails extends Apps {


	public static final class ParameterType{
		
		public static final String PARAMETER="PARAMETER";
		public static final String SIMULATION="SIMULATION";
		
	};
	/**
	 * 
	 */
	private static final long serialVersionUID = 6691566522839823638L;
	
	private String programPath;
	
	//PARAMETER,SIMULATION
	private String parameter;
	
	private String preUsername;
	
	private String usernameType;//PARAMETER,SIMULATION
	
	private String usernameParameter;
	
	private String prePassword;
	
	private String passwordType;//PARAMETER,SIMULATION
	
	private String passwordParameter;
	
	
	private String preSubmit;
	//Enter or key
	private String submitType;
	
	private String submitKey;

	Accounts appUser;
	/**
	 * 
	 */
	public AppsDesktopDetails() {

	}



	/**
	 * @return the programPath
	 */
	public String getProgramPath() {
		return programPath;
	}



	/**
	 * @param programPath the programPath to set
	 */
	public void setProgramPath(String programPath) {
		this.programPath = programPath;
	}



	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}



	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}



	/**
	 * @return the preUsername
	 */
	public String getPreUsername() {
		return preUsername;
	}



	/**
	 * @param preUsername the preUsername to set
	 */
	public void setPreUsername(String preUsername) {
		this.preUsername = preUsername;
	}



	/**
	 * @return the usernameType
	 */
	public String getUsernameType() {
		return usernameType;
	}



	/**
	 * @param usernameType the usernameType to set
	 */
	public void setUsernameType(String usernameType) {
		this.usernameType = usernameType;
	}



	/**
	 * @return the usernameParameter
	 */
	public String getUsernameParameter() {
		return usernameParameter;
	}



	/**
	 * @param usernameParameter the usernameParameter to set
	 */
	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}



	/**
	 * @return the prePassword
	 */
	public String getPrePassword() {
		return prePassword;
	}



	/**
	 * @param prePassword the prePassword to set
	 */
	public void setPrePassword(String prePassword) {
		this.prePassword = prePassword;
	}



	/**
	 * @return the passwordType
	 */
	public String getPasswordType() {
		return passwordType;
	}



	/**
	 * @param passwordType the passwordType to set
	 */
	public void setPasswordType(String passwordType) {
		this.passwordType = passwordType;
	}



	/**
	 * @return the passwordParameter
	 */
	public String getPasswordParameter() {
		return passwordParameter;
	}



	/**
	 * @param passwordParameter the passwordParameter to set
	 */
	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}



	/**
	 * @return the preSubmit
	 */
	public String getPreSubmit() {
		return preSubmit;
	}



	/**
	 * @param preSubmit the preSubmit to set
	 */
	public void setPreSubmit(String preSubmit) {
		this.preSubmit = preSubmit;
	}



	/**
	 * @return the submitType
	 */
	public String getSubmitType() {
		return submitType;
	}



	/**
	 * @param submitType the submitType to set
	 */
	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}



	/**
	 * @return the submitKey
	 */
	public String getSubmitKey() {
		return submitKey;
	}



	/**
	 * @param submitKey the submitKey to set
	 */
	public void setSubmitKey(String submitKey) {
		this.submitKey = submitKey;
	}



	/**
	 * @return the appUser
	 */
	public Accounts getAppUser() {
		return appUser;
	}



	/**
	 * @param appUser the appUser to set
	 */
	public void setAppUser(Accounts appUser) {
		this.appUser = appUser;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DesktopDetails [programPath=" + programPath + ", parameter="
				+ parameter + ", preUsername=" + preUsername
				+ ", usernameType=" + usernameType + ", usernameParameter="
				+ usernameParameter + ", prePassword=" + prePassword
				+ ", passwordType=" + passwordType + ", passwordParameter="
				+ passwordParameter + ", preSubmit=" + preSubmit
				+ ", submitType=" + submitType + ", submitKey=" + submitKey
				+ "]";
	}

}
