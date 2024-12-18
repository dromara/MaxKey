package org.dromara.maxkey.persistence.service;

import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.cnf.CnfPasswordPolicy;

public interface PasswordPolicyValidatorService {
	
	public CnfPasswordPolicy getPasswordPolicy();
	
	public boolean validator(ChangePassword changePassword);
	
	public String generateRandomPassword() ;
}
