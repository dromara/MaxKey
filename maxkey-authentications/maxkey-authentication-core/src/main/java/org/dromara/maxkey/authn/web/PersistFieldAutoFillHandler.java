/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.authn.web;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.dromara.maxkey.authn.SignPrincipal;
import org.dromara.mybatis.jpa.handler.FieldAutoFillHandler;
import org.springframework.stereotype.Component;

@Component
public class PersistFieldAutoFillHandler   extends FieldAutoFillHandler{

	@Override
	public void insertFill(MetaObject metaObject) {
		
		SignPrincipal principal = getPrincipal();
		if(principal != null) {
			this.setFieldValue(metaObject , "instId", principal.getInstId());
			this.setFieldValue(metaObject , "createdBy", principal.getUserId());
		}
		this.setFieldValue(metaObject , "createdDate", new Date());
		
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		SignPrincipal principal = getPrincipal();
		if(principal != null) {
			this.setFieldValue(metaObject , "modifiedBy", principal.getUserId());
		}
		this.setFieldValue(metaObject , "modifiedDate", new Date());
	}
	
	/**
	 * 获取principal , 忽略异常情况
	 * @return
	 */
	SignPrincipal getPrincipal() {
		SignPrincipal principal = null;
		try {
			principal = AuthorizationUtils.getPrincipal();
		}catch(Exception e) {
			//
		}
		return principal;
	}

}
