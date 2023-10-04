/*
 * Copyright [2023] [MaxKey of copyright http://www.maxkey.top]
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
package org.dromara.maxkey.ip2location.online;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Ip138返回结果
 * 
 * @author Crystal.Sea
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ip138Response {

	String ct;
	String prov;
	String city;
	String area;
	String yunyin;
	
	public Ip138Response() {
	}
	public String getCt() {
		return ct;
	}
	public void setCt(String ct) {
		this.ct = ct;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getYunyin() {
		return yunyin;
	}
	public void setYunyin(String yunyin) {
		this.yunyin = yunyin;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(ct)
			   .append(prov)
			   .append(city)
			   .append(area)
			   .append(" ")
			   .append(yunyin);
		return builder.toString();
	}
	
	
}
