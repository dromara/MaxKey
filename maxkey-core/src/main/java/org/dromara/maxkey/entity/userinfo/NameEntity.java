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
 

package org.dromara.maxkey.entity.userinfo;

public class NameEntity{
	private String givenName;
	private String middleName;
	private String familyName;
	private String honorificPrefix;
	private String honorificSuffix;
	private String formattedName;
	/**
	 * 
	 */
	public NameEntity() {
		
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getHonorificPrefix() {
		return honorificPrefix;
	}
	public void setHonorificPrefix(String honorificPrefix) {
		this.honorificPrefix = honorificPrefix;
	}
	public String getHonorificSuffix() {
		return honorificSuffix;
	}
	public void setHonorificSuffix(String honorificSuffix) {
		this.honorificSuffix = honorificSuffix;
	}
	public String getFormattedName() {
		return formattedName;
	}
	public void setFormattedName(String formattedName) {
		this.formattedName = formattedName;
	}
	@Override
	public String toString() {
		return "NameEntity [givenName=" + givenName + ", middleName="
				+ middleName + ", familyName=" + familyName
				+ ", honorificPrefix=" + honorificPrefix
				+ ", honorificSuffix=" + honorificSuffix
				+ ", formattedName=" + formattedName + "]";
	}
}
