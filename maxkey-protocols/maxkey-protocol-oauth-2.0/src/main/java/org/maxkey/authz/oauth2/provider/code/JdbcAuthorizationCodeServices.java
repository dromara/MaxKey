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
 

package org.maxkey.authz.oauth2.provider.code;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.maxkey.util.SerializationUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.util.Assert;

/**
 * Implementation of authorization code services that stores the codes and
 * authentication in a database.
 * 
 * @author Crystal.Sea
 */
public class JdbcAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private static final String DEFAULT_SELECT_STATEMENT = "select code, authentication from oauth_code where code = ?";
    private static final String DEFAULT_INSERT_STATEMENT = "insert into oauth_code (code, authentication) values (?, ?)";
    private static final String DEFAULT_DELETE_STATEMENT = "delete from oauth_code where code = ?";

    private String selectAuthenticationSql = DEFAULT_SELECT_STATEMENT;
    private String insertAuthenticationSql = DEFAULT_INSERT_STATEMENT;
    private String deleteAuthenticationSql = DEFAULT_DELETE_STATEMENT;

    private final JdbcTemplate jdbcTemplate;

    public JdbcAuthorizationCodeServices(DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcAuthorizationCodeServices(JdbcTemplate jdbcTemplate) {
        Assert.notNull(jdbcTemplate, "jdbcTemplate required");
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        jdbcTemplate.update(insertAuthenticationSql,
                new Object[] { code, new SqlLobValue(SerializationUtils.serialize(authentication)) },
                new int[] { Types.VARCHAR, Types.BLOB });
    }

    public OAuth2Authentication remove(String code) {
        OAuth2Authentication authentication;

        try {
            authentication = jdbcTemplate.queryForObject(selectAuthenticationSql,
                    new RowMapper<OAuth2Authentication>() {
                        public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return SerializationUtils.deserialize(rs.getBytes("authentication"));
                        }
                    }, code);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        if (authentication != null) {
            jdbcTemplate.update(deleteAuthenticationSql, code);
        }

        return authentication;
    }

    public void setSelectAuthenticationSql(String selectAuthenticationSql) {
        this.selectAuthenticationSql = selectAuthenticationSql;
    }

    public void setInsertAuthenticationSql(String insertAuthenticationSql) {
        this.insertAuthenticationSql = insertAuthenticationSql;
    }

    public void setDeleteAuthenticationSql(String deleteAuthenticationSql) {
        this.deleteAuthenticationSql = deleteAuthenticationSql;
    }
}
