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
 

package org.maxkey.persistence.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.maxkey.domain.PasswordPolicy;
import org.springframework.jdbc.core.RowMapper;

public class PasswordPolicyRowMapper implements RowMapper<PasswordPolicy> {

    @Override
    public PasswordPolicy mapRow(ResultSet rs, int rowNum) throws SQLException {
        PasswordPolicy passwordPolicy = new PasswordPolicy();
        passwordPolicy.setId(rs.getString("ID"));
        passwordPolicy.setMinLength(rs.getInt("MINLENGTH"));
        passwordPolicy.setMaxLength(rs.getInt("MAXLENGTH"));
        passwordPolicy.setLowerCase(rs.getInt("LOWERCASE"));
        passwordPolicy.setUpperCase(rs.getInt("UPPERCASE"));
        passwordPolicy.setDigits(rs.getInt("DIGITS"));
        passwordPolicy.setSpecialChar(rs.getInt("SPECIALCHAR"));
        passwordPolicy.setAttempts(rs.getInt("ATTEMPTS"));
        passwordPolicy.setDuration(rs.getInt("DURATION"));
        passwordPolicy.setExpiration(rs.getInt("EXPIRATION"));
        passwordPolicy.setUsername(rs.getInt("USERNAME"));
        passwordPolicy.setSimplePasswords(rs.getString("SIMPLEPASSWORDS"));
        return passwordPolicy;
    }

}
