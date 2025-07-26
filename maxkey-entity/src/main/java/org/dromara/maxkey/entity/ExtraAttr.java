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
package org.dromara.maxkey.entity;

/**
 * @author Administrator
 *
 */
public class ExtraAttr {
	
	String attr;
	String type;
	String value;
	
	public ExtraAttr() {
		super();
	}
	
	public ExtraAttr(String attr, String value) {
		super();
		this.attr = attr;
		this.value = value;
	}
	
	/**
	 * @param attr
	 * @param value
	 */
	public ExtraAttr(String attr, String type,String value) {
		super();
		this.attr = attr;
		this.type=type;
		this.value = value;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ExtraAttr [attr=");
        builder.append(attr);
        builder.append(", type=");
        builder.append(type);
        builder.append(", value=");
        builder.append(value);
        builder.append("]");
        return builder.toString();
    }
	
}
