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
 

package org.maxkey.domain;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;

public class ChangePassword extends JpaBaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2362608803392892403L;

	private String id;
	private String uid;
	private String username;
	private String displayName;
	private String oldPassword;
	private String password;
	private String confirmpassword;
	private String decipherable;
	
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
	public String getUid() {
		return uid;
	}


	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
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


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChangePassword [uid=" + uid + ", username=" + username
				+ ", password=" + password + ", confirmpassword="
				+ confirmpassword + ", decipherable=" + decipherable + "]";
	}
	
}
