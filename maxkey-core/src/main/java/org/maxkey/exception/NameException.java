package org.maxkey.exception;

import org.maxkey.web.WebContext;
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