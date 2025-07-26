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
 

package org.dromara.maxkey.entity.apps;

import java.io.Serializable;

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
@Table(name = "MXK_APPS_FORM_BASED_DETAILS")  
public class AppsFormBasedDetails  extends Apps  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 563313247706861431L;
	@Id
	@Column
	@GeneratedValue
	protected String id;
	
	@Column
	private String redirectUri;
	@Column
	private String usernameMapping;
	@Column
	private String passwordMapping;
	@Column
	private String passwordAlgorithm;
	@Column
	private String authorizeView;
	@Column
	private String instId;

	private String instName;

	/**
	 * 
	 */
	public AppsFormBasedDetails() {
		
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


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPasswordAlgorithm() {
        return passwordAlgorithm;
    }


    public void setPasswordAlgorithm(String passwordAlgorithm) {
        this.passwordAlgorithm = passwordAlgorithm;
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
		builder.append("AppsFormBasedDetails [id=");
		builder.append(id);
		builder.append(", redirectUri=");
		builder.append(redirectUri);
		builder.append(", usernameMapping=");
		builder.append(usernameMapping);
		builder.append(", passwordMapping=");
		builder.append(passwordMapping);
		builder.append(", passwordAlgorithm=");
		builder.append(passwordAlgorithm);
		builder.append(", authorizeView=");
		builder.append(authorizeView);
		builder.append(", instId=");
		builder.append(instId);
		builder.append(", instName=");
		builder.append(instName);
		builder.append("]");
		return builder.toString();
	}

}
