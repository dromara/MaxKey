package org.maxkey.persistence.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.domain.Accounts;
import org.maxkey.persistence.mapper.AccountsMapper;
import org.springframework.stereotype.Service;

@Service
public class AccountsService  extends JpaBaseService<Accounts>{

	public AccountsService() {
		super(AccountsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public AccountsMapper getMapper() {
		// TODO Auto-generated method stub
		return (AccountsMapper)super.getMapper();
	}
	
}
