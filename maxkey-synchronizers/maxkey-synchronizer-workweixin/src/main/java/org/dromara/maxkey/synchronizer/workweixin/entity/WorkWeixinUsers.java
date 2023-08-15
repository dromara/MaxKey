/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.synchronizer.workweixin.entity;

public class WorkWeixinUsers {

	String userid;
	String name;
	String mobile;
	long[] department;
	long[] order;
	String position;
	String gender;
	String email;
	long[] is_leader_in_dept;
	String avatar;
	String thumb_avatar;
	String telephone;
	String alias;
	int status;
	int isleader;
	int enable;
	String address;
	int hide_mobile;
	String english_name;
	String open_userid;
	long main_department;

	String qr_code;
	String external_position;

	public class ExtAttrs {

		String type;
		String name;
		String text;

	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long[] getDepartment() {
		return department;
	}

	public void setDepartment(long[] department) {
		this.department = department;
	}

	public long[] getOrder() {
		return order;
	}

	public void setOrder(long[] order) {
		this.order = order;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public long[] getIs_leader_in_dept() {
		return is_leader_in_dept;
	}

	public void setIs_leader_in_dept(long[] is_leader_in_dept) {
		this.is_leader_in_dept = is_leader_in_dept;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getThumb_avatar() {
		return thumb_avatar;
	}

	public void setThumb_avatar(String thumb_avatar) {
		this.thumb_avatar = thumb_avatar;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getHide_mobile() {
		return hide_mobile;
	}

	public void setHide_mobile(int hide_mobile) {
		this.hide_mobile = hide_mobile;
	}

	public String getEnglish_name() {
		return english_name;
	}

	public void setEnglish_name(String english_name) {
		this.english_name = english_name;
	}

	public String getOpen_userid() {
		return open_userid;
	}

	public void setOpen_userid(String open_userid) {
		this.open_userid = open_userid;
	}

	public long getMain_department() {
		return main_department;
	}

	public void setMain_department(long main_department) {
		this.main_department = main_department;
	}

	public String getQr_code() {
		return qr_code;
	}

	public void setQr_code(String qr_code) {
		this.qr_code = qr_code;
	}

	public String getExternal_position() {
		return external_position;
	}

	public void setExternal_position(String external_position) {
		this.external_position = external_position;
	}

	public int getIsleader() {
		return isleader;
	}

	public void setIsleader(int isleader) {
		this.isleader = isleader;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public WorkWeixinUsers() {
		super();
	}

}
