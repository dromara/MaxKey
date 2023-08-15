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

public class FeishuI18nName {

	String zh_cn;
	String ja_jp;
	String en_us;

	public FeishuI18nName() {
		super();
	}

	public String getZh_cn() {
		return zh_cn;
	}

	public void setZh_cn(String zh_cn) {
		this.zh_cn = zh_cn;
	}

	public String getJa_jp() {
		return ja_jp;
	}

	public void setJa_jp(String ja_jp) {
		this.ja_jp = ja_jp;
	}

	public String getEn_us() {
		return en_us;
	}

	public void setEn_us(String en_us) {
		this.en_us = en_us;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeishuI18nName [zh_cn=");
		builder.append(zh_cn);
		builder.append(", ja_jp=");
		builder.append(ja_jp);
		builder.append(", en_us=");
		builder.append(en_us);
		builder.append("]");
		return builder.toString();
	}
	
}
