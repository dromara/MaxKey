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
 

package org.maxkey.connector.activedirectory;

import java.io.UnsupportedEncodingException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.maxkey.connector.PasswordConnector;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.ldap.ActiveDirectoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "passwordConnector")
public class Password2Activedirectory  extends PasswordConnector{
	private final static Logger logger = LoggerFactory.getLogger(Password2Activedirectory.class);
	ActiveDirectoryUtils  ldapUtils;
	/**
	 * userAccountControl鍊煎緱璇存槑
	 * http://support.microsoft.com/zh-cn/kb/305144
	 *  灞炴�ф爣蹇�	鍗佸叚杩涘埗鍊�	鍗佽繘鍒跺��
		SCRIPT	0x0001	1
		ACCOUNTDISABLE	0x0002	2
		HOMEDIR_REQUIRED	0x0008	8
		LOCKOUT	0x0010	16
		PASSWD_NOTREQD	0x0020	32
		PASSWD_CANT_CHANGE	0x0040	64
		ENCRYPTED_TEXT_PWD_ALLOWED	0x0080	128
		TEMP_DUPLICATE_ACCOUNT	0x0100	256
		NORMAL_ACCOUNT	0x0200	512
		INTERDOMAIN_TRUST_ACCOUNT	0x0800	2048
		WORKSTATION_TRUST_ACCOUNT	0x1000	4096
		SERVER_TRUST_ACCOUNT	0x2000	8192
		DONT_EXPIRE_PASSWORD	0x10000	65536
		MNS_LOGON_ACCOUNT	0x20000	131072
		SMARTCARD_REQUIRED	0x40000	262144
		TRUSTED_FOR_DELEGATION	0x80000	524288
		NOT_DELEGATED	0x100000	1048576
		USE_DES_KEY_ONLY	0x200000	2097152
		DONT_REQ_PREAUTH	0x400000	4194304
		PASSWORD_EXPIRED	0x800000	8388608
		TRUSTED_TO_AUTH_FOR_DELEGATION	0x1000000	16777216
	 */
	public Password2Activedirectory() {
		
	}
	
	@Override
	public boolean sync(UserInfo userInfo) throws Exception{
		try {
			String dn=null;
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(ldapUtils.getSearchScope());
			NamingEnumeration<SearchResult> results = ldapUtils.getConnection()
					.search(ldapUtils.getBaseDN(), "(sAMAccountName="+userInfo.getUsername()+")", searchControls);
			if (results == null || !results.hasMore()) {
				
			}else{
				SearchResult sr = (SearchResult) results.next();
				dn =sr.getNameInNamespace();
				ModificationItem[] modificationItems = new ModificationItem[1];
				logger.info("decipherable : "+userInfo.getDecipherable());
				String password=ReciprocalUtils.decoder(userInfo.getDecipherable());
				//modificationItems[0]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("userPassword",password));
				modificationItems[0]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,new BasicAttribute("unicodePwd",("\"" + password + "\"").getBytes("UTF-16LE")));
				
				ldapUtils.getCtx().modifyAttributes(dn, modificationItems);
			}
			
			ldapUtils.close();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return true;
	}

	
}
