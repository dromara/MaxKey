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
