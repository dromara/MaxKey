/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.synchronizer.feishu.entity;

import java.util.Arrays;

public class FeishuUsers {

	String union_id;
	String user_id;
	String open_id;
	String name;
	String en_name;
	String nickname;
	String email;
	String mobile;
	boolean mobile_visible;
	int gender;
	String avatar_key;
	FeishuUserStatus status;
	String []department_ids;
	String leader_user_id;
	String city;
	String country;
	String work_station;
	int join_time;
	String is_tenant_manager;
	String employee_no;
	long employee_type;

	String enterprise_email;
	String job_title;
	String is_frozen;

	public class ExtAttrs {

		String type;
		String name;
		String text;

	}

	public String getUnion_id() {
		return union_id;
	}

	public void setUnion_id(String union_id) {
		this.union_id = union_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEn_name() {
		return en_name;
	}

	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isMobile_visible() {
		return mobile_visible;
	}

	public void setMobile_visible(boolean mobile_visible) {
		this.mobile_visible = mobile_visible;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getAvatar_key() {
		return avatar_key;
	}

	public void setAvatar_key(String avatar_key) {
		this.avatar_key = avatar_key;
	}

	public FeishuUserStatus getStatus() {
		return status;
	}

	public void setStatus(FeishuUserStatus status) {
		this.status = status;
	}

	public String[] getDepartment_ids() {
		return department_ids;
	}

	public void setDepartment_ids(String[] department_ids) {
		this.department_ids = department_ids;
	}

	public String getLeader_user_id() {
		return leader_user_id;
	}

	public void setLeader_user_id(String leader_user_id) {
		this.leader_user_id = leader_user_id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getWork_station() {
		return work_station;
	}

	public void setWork_station(String work_station) {
		this.work_station = work_station;
	}

	public int getJoin_time() {
		return join_time;
	}

	public void setJoin_time(int join_time) {
		this.join_time = join_time;
	}

	public String getIs_tenant_manager() {
		return is_tenant_manager;
	}

	public void setIs_tenant_manager(String is_tenant_manager) {
		this.is_tenant_manager = is_tenant_manager;
	}

	public String getEmployee_no() {
		return employee_no;
	}

	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}

	public long getEmployee_type() {
		return employee_type;
	}

	public void setEmployee_type(long employee_type) {
		this.employee_type = employee_type;
	}

	public String getEnterprise_email() {
		return enterprise_email;
	}

	public void setEnterprise_email(String enterprise_email) {
		this.enterprise_email = enterprise_email;
	}

	public String getJob_title() {
		return job_title;
	}

	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}

	public String getIs_frozen() {
		return is_frozen;
	}

	public void setIs_frozen(String is_frozen) {
		this.is_frozen = is_frozen;
	}

	public FeishuUsers() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeishuUsers [union_id=");
		builder.append(union_id);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append(", open_id=");
		builder.append(open_id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", en_name=");
		builder.append(en_name);
		builder.append(", nickname=");
		builder.append(nickname);
		builder.append(", email=");
		builder.append(email);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", mobile_visible=");
		builder.append(mobile_visible);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", avatar_key=");
		builder.append(avatar_key);
		builder.append(", status=");
		builder.append(status);
		builder.append(", department_ids=");
		builder.append(Arrays.toString(department_ids));
		builder.append(", leader_user_id=");
		builder.append(leader_user_id);
		builder.append(", city=");
		builder.append(city);
		builder.append(", country=");
		builder.append(country);
		builder.append(", work_station=");
		builder.append(work_station);
		builder.append(", join_time=");
		builder.append(join_time);
		builder.append(", is_tenant_manager=");
		builder.append(is_tenant_manager);
		builder.append(", employee_no=");
		builder.append(employee_no);
		builder.append(", employee_type=");
		builder.append(employee_type);
		builder.append(", enterprise_email=");
		builder.append(enterprise_email);
		builder.append(", job_title=");
		builder.append(job_title);
		builder.append(", is_frozen=");
		builder.append(is_frozen);
		builder.append("]");
		return builder.toString();
	}

}
