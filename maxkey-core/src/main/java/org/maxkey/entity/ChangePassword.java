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
 

package org.maxkey.entity;

import org.apache.mybatis.jpa.persistence.JpaBaseEntity;

public class ChangePassword extends JpaBaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2362608803392892403L;

	private String id;
	private String userId;
	private String username;
	private String email;
	private String mobile;
	private String windowsAccount;
	private String employeeNumber;
	private String displayName;
	private String oldPassword;
	private String password;
	private String confirmpassword;
	private String decipherable;
	private String instId;
	
	/**
	 * 
	 */
	public ChangePassword() {

	}

	
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


	/**
	 * @return the uid
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param uid the uid to set
	 */
	public void setUserId(String uid) {
		this.userId = uid;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}


	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the confirmpassword
	 */
	public String getConfirmpassword() {
		return confirmpassword;
	}


	/**
	 * @param confirmpassword the confirmpassword to set
	 */
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}


	/**
	 * @return the decipherable
	 */
	public String getDecipherable() {
		return decipherable;
	}


	/**
	 * @param decipherable the decipherable to set
	 */
	public void setDecipherable(String decipherable) {
		this.decipherable = decipherable;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getWindowsAccount() {
		return windowsAccount;
	}


	public void setWindowsAccount(String windowsAccount) {
		this.windowsAccount = windowsAccount;
	}


	public String getEmployeeNumber() {
		return employeeNumber;
	}


	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
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
        builder.append("ChangePassword [id=");
        builder.append(id);
        builder.append(", uid=");
        builder.append(userId);
        builder.append(", username=");
        builder.append(username);
        builder.append(", displayName=");
        builder.append(displayName);
        builder.append(", oldPassword=");
        builder.append(oldPassword);
        builder.append(", password=");
        builder.append(password);
        builder.append(", confirmpassword=");
        builder.append(confirmpassword);
        builder.append(", decipherable=");
        builder.append(decipherable);
        builder.append("]");
        return builder.toString();
    }
	
}
