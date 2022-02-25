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
 

package org.maxkey.persistence.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.maxkey.constants.ConstsStatus;
import org.maxkey.entity.Institutions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class InstitutionsRepository {
    private static Logger _logger = LoggerFactory.getLogger(InstitutionsRepository.class);
    
    private static final String SELECT_STATEMENT = 
    						"select * from  mxk_institutions where domain = ? and status = " + ConstsStatus.ACTIVE;

    private static final String SELECT_STATEMENT_BY_ID = 
    						"select * from  mxk_institutions where id = ? and status = " + ConstsStatus.ACTIVE;

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
        
    public Institutions findByDomain(String domain) {
        _logger.trace(" domain {}" , domain);
        Institutions inst = institutionsStore.getIfPresent(domain);
        if(inst == null) {
	        List<Institutions> institutions = 
	        		jdbcTemplate.query(SELECT_STATEMENT,new InstitutionsRowMapper(),domain);
	        
	        if (institutions != null && institutions.size() > 0) {
	        	inst = institutions.get(0);
	        	institutionsStore.put(domain, inst);
		        mapper.put(inst.getId(), domain);
	        }else {
	        	//default institution
	        	inst = get("1"); 
	        }
        }
        
        return inst;
    }
    
    public Institutions get(String instId) {
        _logger.trace(" instId {}" , instId);
        Institutions inst = institutionsStore.getIfPresent(mapper.get(instId)==null ? "1" : mapper.get(instId) );
        if(inst == null) {
	        List<Institutions> institutions = 
	        		jdbcTemplate.query(SELECT_STATEMENT_BY_ID,new InstitutionsRowMapper(),instId);
	        
	        if (institutions != null && institutions.size() > 0) {
	        	inst = institutions.get(0);
	        }
	        institutionsStore.put(inst.getDomain(), inst);
	        mapper.put(inst.getId(), inst.getDomain());
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
        	institution.setTitle(rs.getString("title"));
        	institution.setConsoleTitle(rs.getString("consoletitle"));
        	institution.setCaptcha(rs.getString("captcha"));
        	institution.setCaptchaSupport(rs.getString("CaptchaSupport"));
        	institution.setDefaultUri(rs.getString("DefaultUri"));
            return institution;
        }
    }
}
