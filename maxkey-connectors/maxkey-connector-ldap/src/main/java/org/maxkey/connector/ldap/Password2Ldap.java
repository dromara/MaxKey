package org.maxkey.connector.ldap;

import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.maxkey.connector.PasswordConnector;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.ldap.LdapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component(value = "passwordConnector")
public class Password2Ldap  extends PasswordConnector{
	private final static Logger logger = LoggerFactory.getLogger(Password2Ldap.class);

	LdapUtils  ldapUtils;
	
	public Password2Ldap() {
		
	}

	@Override
	public boolean sync(UserInfo userInfo) throws Exception{
		logger.info("changePassword");
		try {
			ModificationItem[] modificationItems = new ModificationItem[1];
			modificationItems[0]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("userPassword",ReciprocalUtils.decoder(userInfo.getDecipherable())));
			
			String dn="uid="+userInfo.getUsername()+",dc=users,"+ldapUtils.getBaseDN();
			
			ldapUtils.getCtx().modifyAttributes(dn, modificationItems);
			ldapUtils.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
}
