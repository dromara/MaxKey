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
 


package org.dromara.maxkey.util;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;



public class DateUtils {
	public static final  String FORMAT_DATE_DEFAULT = "yyyy-MM-dd";

	public static final  String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";

	public static final  String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
	
	public static final  String FORMAT_DATE_PATTERN_1="yyyy/MM/dd";
	public static final  String FORMAT_DATE_PATTERN_2="yyyy/M/dd";
	public static final  String FORMAT_DATE_PATTERN_3="yyyy/MM/d";
	public static final  String FORMAT_DATE_PATTERN_4="yyyy/M/d";
	public static final  String FORMAT_DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	public static final  String FORMAT_DATE_ISO_TIMESTAMP="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static final  String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final  String FORMAT_DATE_YYYY_MM_DD_HHMM = "yyyy-MM-dd HHmm";

	public static final  String FORMAT_DATE_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final  String FORMAT_DATE_HH_MM = "HH:mm";

	public static final  String FORMAT_DATE_HH_MM_SS = "HH:mm:ss";

	public static final  String FORMAT_DATE_HHMM = "HHmm";

	public static final  String FORMAT_DATE_HHMMSS = "HHmmss";

	public static final String FORMAT_WORK_TIME = "yyyy-MM-dd HHmmss";
	/**
	 * Compares two Dates from their string value.
	 * 
	 * @param stringValue1
	 *            Date 1 as string value.
	 * @param stringValue2
	 *            Date 2 as string value.
	 * 
	 * @return the value <code>0</code> if the argument stringValue1 is equal
	 *         to stringValue2; a value less than <code>0</code> if this
	 *         stringValue1 is before the stringValue2 as Date; and a value
	 *         greater than <code>0</code> if this stringValue1 is after the
	 *         stringValue2.
	 * @since 1.2
	 */
	public static final  int compareDate(String stringValue1, String stringValue2)
			throws ParseException {
		Date date1 = tryParse(stringValue1);
		if (date1 == null) {
			throw new ParseException("Can not parse " + stringValue1+ " to Date.", 0);
		}
		Date date2 = tryParse(stringValue2);
		if (date2 == null) {
			throw new ParseException("Can not parse " + stringValue1+ " to Date.", 0);
		}
		return date1.compareTo(date2);
	}

	/**
	 * Returns current system date as formatted string value with default format
	 * pattern.
	 * 
	 * @return current system date.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public static final  String getCurrentDateAsString() {
		return getCurrentDateAsString(FORMAT_DATE_DEFAULT);
	}
	
	public static final  String getCurrentDateTimeAsString() {
		return getCurrentDateAsString(FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * Returns current system date as formatted string value with given format
	 * pattern.
	 * 
	 * @param formatPattern
	 *            format pattern.
	 * @return current system date.
	 * 
	 */
	public static final  String getCurrentDateAsString(String formatPattern) {
		Date date = new Date();
		return format(date, formatPattern);
	}
	
	public static final  String getFormtPattern1ToPattern2(String stringValue,String formatPattern1,String formatPattern2){
		Date date = parse(stringValue, formatPattern1);
		return format(date, formatPattern2);
	}

	/**
	 * Returns current system date.
	 * 
	 * @return current system date.
	 */
	public static final  Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 0������, 1����һ, 2���ڶ�, 3������, 4������, 5������,6������
	 * @return
	 */
	public static final  String getTodayOfWeek(){
		Calendar calendar = Calendar.getInstance();   
		Date date = new Date();   
		calendar.setTime(date);   
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;  
		return dayOfWeek+"";
	}
	
	/**
	 * �Ƚ�ʱ���ǰ���� 6:00-12:00
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static final  boolean compareTime(String startTime,String endTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			Date start = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			if(start.before(end)){
				return true;
			}
			return false;
	}
	
	/**
	 * 判断value时间值是否在date时间之前
	 * @param value
	 * @param date
	 * @param datePattern
	 * @return
	 */
	public static boolean compareTime(String endDate,Date startDate,String datePattern) {
		Date valueDate = DateUtils.parse(endDate, datePattern);
		return valueDate.before(startDate);
	}
	
	/**
	 * Format Date value as string value with default format pattern.
	 * 
	 * @param date
	 *            Date value.
	 * @return formatted date as string value.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public static final  String format(Date date) {
		if (date == null) {
			return "";
		}
		return format(date, FORMAT_DATE_DEFAULT);
	}

	/**
	 * Format Date value as string value with default format pattern.
	 * 
	 * @param date
	 *            Date value.
	 * @return formatted date as string value.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public static final  String formatDateTime(Date date) {
		if (date == null) {
			return "";
		}
		return format(date, FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * Format Date value as string value with default format pattern.
	 * 
	 * @param date
	 *            Date value.
	 * @return formatted date as string value.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public static final  String formatTimestamp(Date date) {
		if (date == null) {
			return "";
		}
		return format(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * Format Date value as string value with default format pattern.
	 * 
	 * @param date
	 *            Date value.
	 * @return formatted date as string value.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public static final  Date parseTimestamp(String date) {
		if (date == null) {
			return null;
		}
		return parse(date, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * Format Date value as string value with given format pattern.
	 * 
	 * @param date
	 *            Date value.
	 * @param formatPattern
	 *            format pattern.
	 * @return formatted date as string value.
	 * 
	 * @see #FORMAT_DATE_DEFAULT
	 * @see #FORMAT_DATE_YYYY_MM_DD
	 * @see #FORMAT_DATE_YYYY_MM_DD_HH_MM
	 * @see #FORMAT_DATE_YYYY_MM_DD_HH_MM_SS
	 * @see #FORMAT_DATE_YYYY_MM_DD_HHMMSS
	 */
	public static final  String format(Date date, String formatPattern) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(formatPattern).format(date);
	}

	/**
	 * Parse string value to Date with default format pattern.
	 * 
	 * @param stringValue
	 *            date value as string.
	 * @return Date represents stringValue.
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public static final  Date parse(String stringValue) {
		return parse(stringValue, FORMAT_DATE_DEFAULT);
	}

	/**
	 * Parse string value to Date with given format pattern.
	 * 
	 * @param stringValue
	 *            date value as string.
	 * @param formatPattern
	 *            format pattern.
	 * @return Date represents stringValue, null while parse exception occurred.
	 * @see #FORMAT_DATE_DEFAULT
	 */
	public static final  Date parse(String stringValue, String formatPattern) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		try {
			return format.parse(stringValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * Try to parse string value to date.
	 * 
	 * @param stringValue
	 *            string value.
	 * @return Date represents stringValue, null while parse exception occurred.
	 */
	public static final  Date tryParse(String stringValue) {
		Date date = parse(stringValue, FORMAT_DATE_YYYY_MM_DD);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_YYYYMMDD);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_YYYYMMDDHHMMSS);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
		if (date != null) {
			return date;
		}
		date = parse(stringValue, FORMAT_DATE_YYYY_MM_DD_HHMM);
		if (date != null) {
			return date;
		}
		date = parse(stringValue,FORMAT_DATE_PATTERN_1);
		if (date != null) {
			return date;
		}
		date=parse(stringValue, FORMAT_DATE_PATTERN_2);
		if (date != null) {
			return date;
		}
		date=parse(stringValue, FORMAT_DATE_PATTERN_3);
		if (date != null) {
			return date;
		}
		date=parse(stringValue, FORMAT_DATE_PATTERN_4);
		if (date != null) {
			return date;
		}
		return date;
	}

	/**
	 * get day of week
	 * @param SUN_FST_DAY_OF_WEEK
	 * @return
	 */
	public static int getDayOfWeek(int SUN_FST_DAY_OF_WEEK) {
		if (SUN_FST_DAY_OF_WEEK > 7 || SUN_FST_DAY_OF_WEEK < 1) {
			return 0;
		}
		if (SUN_FST_DAY_OF_WEEK == 1) {
			return 7;
		}
		return SUN_FST_DAY_OF_WEEK - 1;
	}

	public static Timestamp parseTimestamp(String stringValue,
			String formatPattern) {
		return new Timestamp(parse(stringValue, formatPattern).getTime());
	}

	public static Timestamp parseTimestamp(Date d) {
		return new Timestamp(d.getTime());
	}
	   //-----------------------------------------------------------------------
    /**
     * Adds a number of milliseconds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }
    
    //-----------------------------------------------------------------------
    /**
     * Adds a number of minutes to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }
    //-----------------------------------------------------------------------
    /**
     * Adds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date  the date, not null
     * @param calendarField  the calendar field to add to
     * @param amount  the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     * 
     */
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
        
	public static String getExchangeFormat(String dateStr,String sourFormat,String destFormat){
		String dt="";
		try{
			SimpleDateFormat sdf=new SimpleDateFormat(destFormat);
			dt=sdf.format(parse(dateStr,sourFormat));
		}catch(Exception e){
			e.printStackTrace();
		}
		return dt;
	}	
	
	public static Date plugOneDate(Date date) {
		return new Date(date.getTime() + 24*60*60*1000L);
	}
    
	/**
	 * ���date�ĺ�һ�죬��ʱ����ֱ�Ϊ00:00:00
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date) {
		Date nextDay = new Date(date.getTime() + 24*60*60*1000L);
		// Get Calendar object set to the date and time of the given Date object  
		Calendar cal = Calendar.getInstance();  
		cal.setTime(nextDay);  
		// Set time fields to zero  
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		cal.set(Calendar.MILLISECOND, 0);  
		// Put it back in the Date object  
		nextDay = cal.getTime(); 
		return nextDay;
	}
	
	/**
	 * ɾ��date�е�ʱ���֡��롢����
	 * 
	 * @param date
	 * @return
	 */
	public static Date truncateTime(Date date) {
		if (date == null) {
			return null;
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}
	
	/**
	 * �Ը�ʱ��date��������ƶ�(��ǰ�ƶ�)����
	 * 
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @param milliSecond
	 * @return
	 */
	public static Date addDate(Date date, int year, int month, int day, int hour, int minute, int second, int milliSecond) {
		if (date == null) {
			return null;
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.DATE, day);
		c.add(Calendar.HOUR_OF_DAY, hour);
		c.add(Calendar.MINUTE, minute);
		c.add(Calendar.SECOND, second);
		c.add(Calendar.MILLISECOND, milliSecond);
		
		return c.getTime();
	}
	
	public static Date addDate(Date date, int year, int month, int day, int hour, int minute, int second) {
		return addDate(date, year, month, day, hour, minute, second, 0);
	}

	public static Date addDate(Date date, int hour, int minute, int second) {
		return addDate(date, 0, 0, 0, hour, minute, second, 0);
	}
	
	/**
	 * ����day1��day2�������(day1-day2)������
	 * 
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static int getIntervalDays(Date day1, Date day2) {
		if (day1 == null || day2 == null) {
			throw new IllegalArgumentException("Argument day1 or day2 must be not null.");
		}
		
		Date day1ToUse = truncateTime(day1);
		Date day2ToUse = truncateTime(day2);
		long intervalMilliSecond =  getIntervalMilliSeconds(day1ToUse, day2ToUse);
		
		return (int) (intervalMilliSecond / (24 * 60 * 60 * 1000));
	}

	/**
	 * ����day1��day2�������(day1-day2)���·�
	 * 
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static int getIntervalMonths(Date day1, Date day2) {
		if (day1 == null || day2 == null) {
			throw new IllegalArgumentException("Argument day1 or day2 must be not null.");
		}

		Calendar calDay1 = Calendar.getInstance();
		calDay1.setTime(day1);
		Calendar calDay2 = Calendar.getInstance();
		calDay2.setTime(day2);
		
		int yearInterval = calDay1.get(Calendar.YEAR) - calDay2.get(Calendar.YEAR);
		int monthInterval = calDay1.get(Calendar.MONTH) - calDay2.get(Calendar.MONTH);
		
		return yearInterval * 12 + monthInterval;
	}

	/**
	 * ����day1��day2�������(day1-day2)����
	 * 
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static int getIntervalYears(Date day1, Date day2) {
		if (day1 == null || day2 == null) {
			throw new IllegalArgumentException("Argument day1 or day2 must be not null.");
		}

		Calendar calDay1 = Calendar.getInstance();
		calDay1.setTime(day1);
		Calendar calDay2 = Calendar.getInstance();
		calDay2.setTime(day2);
		
		return calDay1.get(Calendar.YEAR) - calDay2.get(Calendar.YEAR);
	}
	
	private static long MILLISECOND_ONE_MINUTE = 60 * 1000;
	private static long MILLISECOND_ONE_HOUR = 3600 * 1000;
	private static long MILLISECOND_ONE_DAY = MILLISECOND_ONE_HOUR * 24;
	
	/**
	 * ��ʽ��(**��**Сʱ**��**��**����)��ʾstart��end��ʱ���(end-start)
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String intervalFormatDisplay(Date start, Date end) {
		long minus = getIntervalMilliSeconds(end, start);
		if (minus < 0) {
			throw new IllegalArgumentException("The date end must great than or equal the date start.");
		}

		StringBuilder interval = new StringBuilder();
		long days = minus / MILLISECOND_ONE_DAY;
		if (days > 0) {
			interval.append(days).append("��");
		}
		
		minus -= days * MILLISECOND_ONE_DAY;
		long hours = minus / MILLISECOND_ONE_HOUR;
		if (hours > 0) {
			interval.append(hours).append("Сʱ");
		}
		
		minus -= hours * MILLISECOND_ONE_HOUR;
		long minutes = minus / MILLISECOND_ONE_MINUTE;
		if (minutes > 0) {
			interval.append(minutes).append("��");
		}
		
		minus -= minutes * MILLISECOND_ONE_MINUTE;
		long seconds = minus / 1000;
		if (seconds > 0) {
			interval.append(seconds).append("��");
		}
		
		long millis = minus - seconds * 1000;
		if (millis > 0) {
			interval.append(millis).append("����");
		}
		
		return interval.toString();
	}

	/**
	 * ����day1��day2ʱ�����ĺ�����(day1-day2)
	 * 
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static long getIntervalMilliSeconds(Date day1, Date day2) {
		if (day1 == null || day2 == null) {
			throw new IllegalArgumentException("Argument day1 or day2 must be not null.");
		}
		
		return day1.getTime()-day2.getTime();
	}
	
	public static String toUtc(java.util.Date date){
		DateTime datetime=new DateTime(date, ISOChronology.getInstanceUTC());
		return datetime.toString();
	}
	
	public static String toUtc(DateTime dateTime){
		return dateTime.toString();
	}
	
	public static String toUtc(String date){
		DateTime datetime=new DateTime(date, ISOChronology.getInstanceUTC());
		return datetime.toString();
	}

	public static DateTime toUtcDate(String date){
		DateTime datetime=new DateTime(date, ISOChronology.getInstanceUTC());
		return datetime;
	}
	
	public static String toUtcLocal(java.util.Date date){
		DateTime datetime=new DateTime(date,ISOChronology.getInstance());
		return datetime.toString();
	}
	
	public static String toUtcLocal(String date){
		DateTime datetime=new DateTime(date,ISOChronology.getInstance());
		return datetime.toString();
	}

}
