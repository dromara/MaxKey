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
 

package org.maxkey.authz.cas.endpoint.ticket.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.maxkey.authz.cas.endpoint.ticket.Ticket;
import org.maxkey.util.SerializationUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.util.Assert;


public class JdbcTicketServices extends RandomServiceTicketServices {

	private static final String DEFAULT_SELECT_STATEMENT = "select id, ticket from cas_ticket where id = ?";
	private static final String DEFAULT_INSERT_STATEMENT = "insert into cas_ticket (id, ticket) values (?, ?)";
	private static final String DEFAULT_DELETE_STATEMENT = "delete from cas_ticket where id = ?";

	private String selectAuthenticationSql = DEFAULT_SELECT_STATEMENT;
	private String insertAuthenticationSql = DEFAULT_INSERT_STATEMENT;
	private String deleteAuthenticationSql = DEFAULT_DELETE_STATEMENT;

	private final JdbcTemplate jdbcTemplate;

	public JdbcTicketServices(JdbcTemplate jdbcTemplate) {
		Assert.notNull(jdbcTemplate, "jdbcTemplate required");
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void store(String ticketId, Ticket ticket) {
		jdbcTemplate.update(insertAuthenticationSql,
				new Object[] { ticket, new SqlLobValue(SerializationUtils.serialize(ticket)) }, new int[] {
						Types.VARCHAR, Types.BLOB });
	}

	public Ticket  remove(String ticketId) {
		Ticket ticket;

		try {
			ticket = jdbcTemplate.queryForObject(selectAuthenticationSql,
					new RowMapper<Ticket>() {
						public Ticket mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return SerializationUtils.deserialize(rs.getBytes("ticket"));
						}
					}, ticketId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		if (ticket != null) {
			jdbcTemplate.update(deleteAuthenticationSql, ticket);
		}

		return ticket;
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

    @Override
    public Ticket get(String ticketId) {
        // TODO Auto-generated method stub
        return null;
    }
}
