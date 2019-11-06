package org.maxkey.dao.service;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.maxkey.dao.persistence.AppsSaml20DetailsMapper;
import org.maxkey.domain.apps.AppsSAML20Details;
import org.springframework.stereotype.Service;

@Service
public class AppsSaml20DetailsService  extends JpaBaseService<AppsSAML20Details>{

	public AppsSaml20DetailsService() {
		super(AppsSaml20DetailsMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public AppsSaml20DetailsMapper getMapper() {
		// TODO Auto-generated method stub
		return (AppsSaml20DetailsMapper)super.getMapper();
	}
	
	public  AppsSAML20Details  getAppDetails(String id){
		return  getMapper().getAppDetails(id);
	}
}
