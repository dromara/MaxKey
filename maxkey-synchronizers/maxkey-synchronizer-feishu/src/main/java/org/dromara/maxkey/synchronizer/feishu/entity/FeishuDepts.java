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

public class FeishuDepts {
	
	String department_id;
	String parent_department_id;
	String open_department_id;
	String name;
	FeishuI18nName i18n_name;
	String leader_user_id;
	String chat_id;
	String order;
	int member_count;
	FeishuDeptStatus status;
	String is_deleted;
	String create_group_chat;

	public FeishuDepts() {
		super();
	}

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public String getParent_department_id() {
		return parent_department_id;
	}

	public void setParent_department_id(String parent_department_id) {
		this.parent_department_id = parent_department_id;
	}

	public String getOpen_department_id() {
		return open_department_id;
	}

	public void setOpen_department_id(String open_department_id) {
		this.open_department_id = open_department_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FeishuI18nName getI18n_name() {
		return i18n_name;
	}

	public void setI18n_name(FeishuI18nName i18n_name) {
		this.i18n_name = i18n_name;
	}

	public String getLeader_user_id() {
		return leader_user_id;
	}

	public void setLeader_user_id(String leader_user_id) {
		this.leader_user_id = leader_user_id;
	}

	public String getChat_id() {
		return chat_id;
	}

	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getMember_count() {
		return member_count;
	}

	public void setMember_count(int member_count) {
		this.member_count = member_count;
	}

	public FeishuDeptStatus getStatus() {
		return status;
	}

	public void setStatus(FeishuDeptStatus status) {
		this.status = status;
	}

	public String getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}

	public String getCreate_group_chat() {
		return create_group_chat;
	}

	public void setCreate_group_chat(String create_group_chat) {
		this.create_group_chat = create_group_chat;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeishuDepts [department_id=");
		builder.append(department_id);
		builder.append(", parent_department_id=");
		builder.append(parent_department_id);
		builder.append(", open_department_id=");
		builder.append(open_department_id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", i18n_name=");
		builder.append(i18n_name);
		builder.append(", leader_user_id=");
		builder.append(leader_user_id);
		builder.append(", chat_id=");
		builder.append(chat_id);
		builder.append(", order=");
		builder.append(order);
		builder.append(", member_count=");
		builder.append(member_count);
		builder.append(", status=");
		builder.append(status);
		builder.append(", is_deleted=");
		builder.append(is_deleted);
		builder.append(", create_group_chat=");
		builder.append(create_group_chat);
		builder.append("]");
		return builder.toString();
	}


}
