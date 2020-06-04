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
