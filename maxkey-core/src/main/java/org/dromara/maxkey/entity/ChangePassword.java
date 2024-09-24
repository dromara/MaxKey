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

import java.util.Date;

import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.mybatis.jpa.entity.JpaEntity;

public class ChangePassword extends JpaEntity{

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
	private String confirmPassword;
	private String decipherable;
	private String instId;
	private int passwordSetType;
	private Date passwordLastSetTime;
	
	/**
	 * 
	 */
	public ChangePassword() {

	}
	
	public ChangePassword(String username,String password) {
		this.username = username;
		this.password = password;
	}
	
	public ChangePassword(UserInfo userInfo) {
		this.setId(userInfo.getId());
		this.setUserId(userInfo.getId());
		this.setUsername(userInfo.getUsername());
		this.setWindowsAccount(userInfo.getWindowsAccount());
		this.setMobile(userInfo.getMobile());
		this.setEmail(userInfo.getEmail());
		this.setEmployeeNumber(userInfo.getEmployeeNumber());
		this.setDecipherable(userInfo.getDecipherable());
		this.setPassword(userInfo.getPassword());
		this.setInstId(userInfo.getInstId());
	}
	
	public void clearPassword() {
		this.password ="";
		this.decipherable = "";
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	
	public int getPasswordSetType() {
		return passwordSetType;
	}


	public void setPasswordSetType(int passwordSetType) {
		this.passwordSetType = passwordSetType;
	}


	public Date getPasswordLastSetTime() {
		return passwordLastSetTime;
	}

	public void setPasswordLastSetTime(Date passwordLastSetTime) {
		this.passwordLastSetTime = passwordLastSetTime;
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
        builder.append(", confirmPassword=");
        builder.append(confirmPassword);
        builder.append(", decipherable=");
        builder.append(decipherable);
        builder.append("]");
        return builder.toString();
    }
	
}
