package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.TokenBasedDetailsMapper;
import org.maxkey.domain.apps.TokenBasedDetails;
import org.springframework.stereotype.Service;

@Service
public class TokenBasedDetailsService  extends JpaBaseService<TokenBasedDetails>{

	public TokenBasedDetailsService() {
		super(TokenBasedDetailsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public TokenBasedDetailsMapper getMapper() {
		// TODO Auto-generated method stub
		return (TokenBasedDetailsMapper)super.getMapper();
	}
	
}
