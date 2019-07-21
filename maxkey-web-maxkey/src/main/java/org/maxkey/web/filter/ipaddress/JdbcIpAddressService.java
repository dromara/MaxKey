package org.maxkey.web.filter.ipaddress;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.maxkey.domain.IpAddrFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class JdbcIpAddressService  {
	private static final Logger _logger = LoggerFactory.getLogger(JdbcIpAddressService.class);

	private static final String DEFAULT_ALL_SELECT_STATEMENT = "SELECT ID, IPADDR,FILTER,DESCRIPTION  FROM IPADDRFILTER WHERE STATUS = 1";

	private static final String DEFAULT_FILTER_SELECT_STATEMENT = "SELECT ID, IPADDR,FILTER,DESCRIPTION  FROM IPADDRFILTER WHERE FILTER = ?  AND STATUS = 1";

	private final JdbcTemplate jdbcTemplate;
	
	public JdbcIpAddressService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public List<IpAddrFilter> queryAll() {
		List<IpAddrFilter> listIpAddrFilter=jdbcTemplate.query(DEFAULT_ALL_SELECT_STATEMENT, new RowMapper<IpAddrFilter>() {
			public IpAddrFilter mapRow(ResultSet rs, int rowNum) throws SQLException {
				IpAddrFilter ipAddrFilter=new IpAddrFilter();
				ipAddrFilter.setId(rs.getString(1));
				ipAddrFilter.setIpAddr(rs.getString(2));
				ipAddrFilter.setFilter(rs.getInt(3));
				ipAddrFilter.setDescription(rs.getString(4));
				return ipAddrFilter;
			}
		});
		_logger.debug("ListIpAddrFilter "+listIpAddrFilter);
		return (listIpAddrFilter.size()>0)?listIpAddrFilter:null;
	}

}
