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
 

package org.dromara.maxkey.exception;

import org.dromara.maxkey.web.WebContext;
/**
 * 定义自定义异常
 * 异常包括三个属性，分别是对应在异常中，出现异常的属性field，和对于提示错误消息对应的属性文件的KEY。以及属性的错误数据value
 * 主要作用在于验证企业的“简称”和“全称”是否有重复
 * @author Crystal.Sea
 *
 */
public class NameException extends Exception {

		public NameException(String field, String key, String value) {
			super();
			this.field = field;
			this.key = key;
			this.value = value;
		}

		private static final long serialVersionUID = -5425015701816705662L;
		private String field;
		private String key;
		private String value;

		/**
		 * @return 返回异常属性
		 */
		public String getField() {
			return field;
		}

		/**
		 * @return 返回属性文件的key对应值
		 */
		public String getKey() {
			return WebContext
					.getI18nValue("ui.enterprises.enterprises.message."
							+ key);
		}

		/**
		 * @return 错误数据
		 */
		public String getValue() {
			return value;
		}
	}
