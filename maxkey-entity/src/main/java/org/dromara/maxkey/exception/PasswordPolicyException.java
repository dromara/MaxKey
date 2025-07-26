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

public class PasswordPolicyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -253274228039876768L;
	private String errorCode;
	private Object filedValue;
	public PasswordPolicyException(String errorCode,Object filedValue) {
		super();
		this.errorCode = errorCode;
		this.filedValue = filedValue;
	}
	public PasswordPolicyException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}
	public Object getFiledValue() {
		return filedValue;
	}

	public String getKey() {
		return "message.passwordpolicy."+errorCode.toLowerCase();
	}
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		if(filedValue!=null)
			return WebContext.getI18nValue(getKey(), new Object[]{filedValue});
		else
			return  WebContext.getI18nValue(getKey());
	}
	
}
