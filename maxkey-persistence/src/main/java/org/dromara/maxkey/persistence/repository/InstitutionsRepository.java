/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.persistence.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.dromara.maxkey.entity.Institutions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class InstitutionsRepository {
    private static Logger _logger = LoggerFactory.getLogger(InstitutionsRepository.class);
    
    private static final String SELECT_STATEMENT = 
    						"select * from  mxk_institutions where id = ? or domain = ? or consoledomain = ?" ;
    
    private static final String DEFAULT_INSTID = "1";

    protected static final Cache<String, Institutions> institutionsStore = 
            Caffeine.newBuilder()
                	.expireAfterWrite(60, TimeUnit.MINUTES)
                	.build();
    
    //id domain mapping
    protected static final  ConcurrentHashMap<String,String> mapper = new ConcurrentHashMap<String,String>();
    
    protected JdbcTemplate jdbcTemplate;
    
    public InstitutionsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public Institutions get(String instIdOrDomain) {
        _logger.trace(" instId {}" , instIdOrDomain);
        Institutions inst = getByInstIdOrDomain(instIdOrDomain);
        if(inst == null) {//use default inst
        	inst = getByInstIdOrDomain(DEFAULT_INSTID);
        	institutionsStore.put(instIdOrDomain, inst);
        }
        return inst;
    }
    
    private Institutions getByInstIdOrDomain(String instIdOrDomain) {
        _logger.trace(" instId {}" , instIdOrDomain);
        Institutions inst = institutionsStore.getIfPresent(mapper.get(instIdOrDomain)==null ? DEFAULT_INSTID : mapper.get(instIdOrDomain) );
        if(inst == null) {
	        List<Institutions> institutions = 
	        		jdbcTemplate.query(SELECT_STATEMENT,new InstitutionsRowMapper(),instIdOrDomain,instIdOrDomain,instIdOrDomain);
	        
	        if (institutions != null && institutions.size() > 0) {
	        	inst = institutions.get(0);
	        }
	        if(inst != null ) {
		        institutionsStore.put(inst.getDomain(), inst);
		        institutionsStore.put(inst.getConsoleDomain(), inst);
		        mapper.put(inst.getId(), inst.getDomain());
	        }
        }
        
        return inst;
    }
    
    public class InstitutionsRowMapper implements RowMapper<Institutions> {
        @Override
        public Institutions mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Institutions institution = new Institutions();
        	institution.setId(rs.getString("id"));
        	institution.setName(rs.getString("name"));
        	institution.setFullName(rs.getString("fullname"));
        	institution.setLogo(rs.getString("logo"));
        	institution.setDomain(rs.getString("domain"));
        	institution.setFrontTitle(rs.getString("fronttitle"));
        	institution.setConsoleDomain(rs.getString("consoledomain"));
        	institution.setConsoleTitle(rs.getString("consoletitle"));
        	institution.setCaptcha(rs.getString("captcha"));
        	institution.setDefaultUri(rs.getString("defaultUri"));
            return institution;
        }
    }
}
