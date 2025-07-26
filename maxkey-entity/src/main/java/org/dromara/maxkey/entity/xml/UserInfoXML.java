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
 

package org.dromara.maxkey.entity.xml;

import org.dromara.mybatis.jpa.entity.JpaEntity;

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * xml can not include array , MultipartFile
 * @author Crystal.Sea
 *
 */
@XmlRootElement
public class UserInfoXML extends JpaEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6942731467730249291L;

	/**
	 * 
	 */
	public UserInfoXML() {
		super();
	}



}
