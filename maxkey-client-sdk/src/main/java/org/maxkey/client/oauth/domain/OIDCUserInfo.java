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
package org.maxkey.client.oauth.domain;

/**
 * @author Administrator
 *
 */
public class OIDCUserInfo {
	
	private String sub;
	private String name;
 	private String preferred_username;
 	private String given_name;
 	private String family_name;
 	private String middle_name;
 	private String nickname;
 	private String profile;
 	private String picture;
 	private String website;
 	private String gender;
 	private String zoneinfo;
 	private String locale;
 	private String updated_time;
 	private String birthdate;
 	
 	
 	private String email;
	private boolean email_verified;
	
	private String phone_number;
	private boolean phone_number_verified;
	private OIDCAddress address;
	
	
	protected String error;
	protected String error_description;
	
	protected String responseString;
	
	
	/**
	 * 
	 */
	public OIDCUserInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreferred_username() {
		return preferred_username;
	}

	public void setPreferred_username(String preferred_username) {
		this.preferred_username = preferred_username;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getZoneinfo() {
		return zoneinfo;
	}

	public void setZoneinfo(String zoneinfo) {
		this.zoneinfo = zoneinfo;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmail_verified() {
		return email_verified;
	}

	public void setEmail_verified(boolean email_verified) {
		this.email_verified = email_verified;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public boolean isPhone_number_verified() {
		return phone_number_verified;
	}

	public void setPhone_number_verified(boolean phone_number_verified) {
		this.phone_number_verified = phone_number_verified;
	}

	public OIDCAddress getAddress() {
		return address;
	}

	public void setAddress(OIDCAddress address) {
		this.address = address;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	
}
