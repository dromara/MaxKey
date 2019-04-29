package org.maxkey.tasks.report;

import java.sql.Types;
import java.util.List;
import java.util.Map;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginDayReport {
	
	final static Logger _logger = LoggerFactory.getLogger(LoginDayReport.class);
	
	JdbcTemplate jdbcTemplate;

	public static final String DAY_REPORT_COUNT_SELECT_SQL="SELECT COUNT(ID) DAYCOUNT FROM SECDB.LOGIN_HISTORY WHERE  LOGINTIME > ? AND LOGINTIME < ?";
	
	public static final String DAY_REPORT_INSERT_SQL="INSERT INTO REPORT_LOGIN_DAY  (REPORTDATE,REPORTYEAR,REPORTMONTH,REPORTDAY,REPORTCOUNT) VALUES (? , ? , ? , ? , ?)";
	
	public static final String MONTH_REPORT_COUNT_SELECT_SQL="SELECT REPORTCOUNT  FROM REPORT_LOGIN_MONTH WHERE  REPORTYEAR = ? AND REPORTMONTH = ?";
	
	public static final String MONTH_REPORT_INSERT_SQL="INSERT INTO REPORT_LOGIN_MONTH  (REPORTDATE,REPORTYEAR,REPORTMONTH,REPORTCOUNT) VALUES (? , ? , ? , ?)";
	
	public static final String MONTH_REPORT_UPDATE_SQL="UPDATE REPORT_LOGIN_MONTH  SET REPORTCOUNT = ? WHERE  REPORTYEAR = ? AND REPORTMONTH = ?";
	
	public void dayReportCount() {
		_logger.info("Day Report Statistical Analysis start . ");
		DateTime currentdateTime = new DateTime();
		_logger.info("current date time : " +currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
		
		String beginDateTime=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00"));
		String endDateTime=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd 23:59:59"));
		String currentDate=currentdateTime.toString( DateTimeFormat.forPattern("yyyy-MM-dd"));
		String currentYear=currentdateTime.toString( DateTimeFormat.forPattern("yyyy"));
		String currentMonth=currentdateTime.toString( DateTimeFormat.forPattern("MM"));
		String currentDay=currentdateTime.toString( DateTimeFormat.forPattern("dd"));
		
		_logger.info("Day Report Statistical Analysis Date : " +currentDate);
		//Analysis Current Day Login Count
		Integer reportCount=jdbcTemplate.queryForObject(DAY_REPORT_COUNT_SELECT_SQL, 
				new Object[] { beginDateTime,endDateTime},
				new int[] {Types.TIMESTAMP,Types.TIMESTAMP}, Integer.class);
		
		_logger.info("Current day Count " + reportCount);
		
		jdbcTemplate.update(DAY_REPORT_INSERT_SQL, currentDate,currentYear,currentMonth,currentDay,reportCount);
		
		/**
		 * if month count not exist,then insert a record 
		 * else add current day count to month count
		 */
		List<Map<String, Object>> listSelectMonthReport=jdbcTemplate.queryForList(MONTH_REPORT_COUNT_SELECT_SQL, 
				new Object[] { currentYear,currentMonth},
				new int[] {Types.INTEGER,Types.INTEGER});
		
		if(listSelectMonthReport.size()<1){
			jdbcTemplate.update(MONTH_REPORT_INSERT_SQL, currentDate,currentYear,currentMonth,reportCount);
			_logger.info("Current Month Count " + reportCount);
		}else{
			Integer selectMonthReportCount=Integer.parseInt(listSelectMonthReport.get(0).get("REPORTCOUNT").toString())+reportCount;
			jdbcTemplate.update(MONTH_REPORT_UPDATE_SQL,selectMonthReportCount, currentYear,currentMonth);
			_logger.info("Current Month Count " + selectMonthReportCount);
		}
		
		_logger.info("Day Report Statistical Analysis Successful");
	}
	 
	 
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
