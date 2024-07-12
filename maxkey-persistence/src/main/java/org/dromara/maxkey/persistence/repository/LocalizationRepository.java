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
import java.sql.Types;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.dromara.maxkey.constants.ConstsTimeInterval;
import org.dromara.maxkey.entity.Localization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class LocalizationRepository {
	private static Logger _logger = LoggerFactory.getLogger(LocalizationRepository.class);
	private static final String INSERT_STATEMENT ="insert into mxk_localization (id, property,langzh,langen,status,description,instid)values(?,?,?,?,?,?,?)";
	private static final String UPDATE_STATEMENT ="update mxk_localization set langzh = ? , langen =? where id = ?";
	private static final String DELETE_STATEMENT ="delete from  mxk_localization where id = ?";
	private static final String SELECT_STATEMENT ="select * from  mxk_localization where ( id = ? ) or (property = ? and instid = ?)";
	
	private static final Pattern PATTERN_HTML = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
	 
	protected InstitutionsRepository institutionService;
	
	JdbcTemplate jdbcTemplate;
	
    protected static final Cache<String, String> localizationStore = 
            Caffeine.newBuilder()
                .expireAfterWrite(ConstsTimeInterval.ONE_HOUR, TimeUnit.SECONDS)
                .build();

	public LocalizationRepository() {

	}
	
	public String getLocale(String code,String htmlTag,Locale locale,String inst) {
		String message = "";
		htmlTag = (htmlTag == null ||htmlTag.equalsIgnoreCase("true")) ? "tag" : "rtag";
		
		if(code.equals("global.logo")) {
			message = institutionService.get(inst).getLogo();
		}else if(code.equals("global.title")) {
			message = getFromStore(code, htmlTag, locale, inst);
			if(message == null) {
				message = institutionService.get(inst).getFrontTitle();
			}
		}else if(code.equals("global.consoleTitle")) {
			message = getFromStore(code, htmlTag, locale, inst);
			if(message == null) {
				message = institutionService.get(inst).getConsoleTitle();
			}
		}else {
			message = getFromStore(code, htmlTag, locale, inst);
		}
		if(htmlTag.equalsIgnoreCase("rtag")) {
			message = clearHTMLToString(message);
		}
		_logger.trace("{} = {}" , code , message);
		return message == null ? "" : message;
	}
	
	public String clearHTMLToString(String message) {
		return PATTERN_HTML.matcher(message).replaceAll("");
	}

	public String getFromStore(String code,String htmlTag,Locale locale,String inst) {
		String message = localizationStore.getIfPresent(code+"_"+locale.getLanguage()+"_"+inst);
		if(message != null) return message;
		Localization localization = get(code,inst);
		if(localization != null) {
			localizationStore.put(code+"_en_"+inst, localization.getLangEn());
			localizationStore.put(code+"_zh_"+inst, localization.getLangZh());
			if(locale.getLanguage().equals("en")) {
				message = localization.getLangEn();
			}else {
				message = localization.getLangZh();
			}
			if(message != null) return message;
		}
		return message;
	}
	
	public void setInstitutionService(InstitutionsRepository institutionService) {
		this.institutionService = institutionService;
	}

	public boolean insert(Localization localization) {
		return jdbcTemplate.update(INSERT_STATEMENT,
									new Object[] {localization.getId(),localization.getProperty(),
												  localization.getLangZh(),localization.getLangEn(),
												  localization.getStatus(),localization.getDescription(),
												  localization.getInstId()}, 
									new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
											   Types.VARCHAR, Types.VARCHAR,}) > 0;
	}
	
	public boolean update(Localization localization) {
		jdbcTemplate.update(UPDATE_STATEMENT,localization.getLangZh(),localization.getLangEn(),localization.getId());
		return true;
	}
	
	public boolean remove(String id) {
		return jdbcTemplate.update(DELETE_STATEMENT,id) > 0;
	}
	
	public Localization get(String property,String instId) {
		_logger.debug("load property from database , property {} ,instId {}",property, instId);
		List<Localization>  localizations = 
				jdbcTemplate.query(
							SELECT_STATEMENT,new LocalizationRowMapper(),property,property,instId);
		return (localizations==null || localizations.size()==0) ? null : localizations.get(0);
	}
	
	public LocalizationRepository(JdbcTemplate jdbcTemplate,InstitutionsRepository institutionService) {
		super();
		this.institutionService = institutionService;
		this.jdbcTemplate = jdbcTemplate;
	}
    
	   public class LocalizationRowMapper implements RowMapper<Localization> {
	        @Override
	        public Localization mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	Localization localization = new Localization();
	        	localization.setId(rs.getString("id"));
	        	localization.setProperty(rs.getString("property"));
	        	localization.setLangZh(rs.getString("langzh"));
	        	localization.setLangEn(rs.getString("langen"));
	        	localization.setStatus(rs.getInt("status"));
	        	localization.setDescription(rs.getString("description"));
	        	localization.setInstId(rs.getString("instid"));
	            return localization;
	        }
	    }
}
