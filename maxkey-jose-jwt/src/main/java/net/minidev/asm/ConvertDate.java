package net.minidev.asm;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class ConvertDate {
	static TreeMap<String, Integer> monthsTable = new TreeMap<String, Integer>(new StringCmpNS()); // StringCmpNS.COMP
	static TreeMap<String, Integer> daysTable = new TreeMap<String, Integer>(new StringCmpNS()); // StringCmpNS.COMP
	private static HashSet<String> voidData = new HashSet<String>();

	public static class StringCmpNS implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return o1.compareToIgnoreCase(o2);
		}
	}

	public static Integer getMonth(String month) {
		return monthsTable.get(month);
	}

	private static Integer parseMonth(String s1) {
		if (Character.isDigit(s1.charAt(0))) {
			return Integer.parseInt(s1) - 1;
		} else {
			Integer month = monthsTable.get(s1);
			if (month == null)
				throw new NullPointerException("can not parse " + s1 + " as month");
			return month.intValue();
		}
	}

	static {
		voidData.add("CET");
		voidData.add("MEZ");
		voidData.add("Uhr");
		voidData.add("h");
		voidData.add("pm");
		voidData.add("PM");
		voidData.add("o'clock");

		// for (int c = 1; c <= 31; c++) {
		// String s = Integer.toString(c);
		// if (c < 10)
		// daysTable.put("0".concat(s), c - 1);
		// daysTable.put(s, c - 1);
		// }

		// for (int c = 1; c <= 12; c++) {
		// String s = Integer.toString(c);
		// if (c < 10)
		// monthsTable.put("0".concat(s), c - 1);
		// monthsTable.put(s, c - 1);
		// }

		for (Locale locale : DateFormatSymbols.getAvailableLocales()) {
			if ("ja".equals(locale.getLanguage()))
				continue;
			if ("ko".equals(locale.getLanguage()))
				continue;
			if ("zh".equals(locale.getLanguage()))
				continue;
			DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
			String[] keys = dfs.getMonths();
			for (int i = 0; i < keys.length; i++) {
				if (keys[i].length() == 0)
					continue;
				fillMap(monthsTable, keys[i], Integer.valueOf(i));
			}
			keys = dfs.getShortMonths();
			for (int i = 0; i < keys.length; i++) {
				String s = keys[i];
				if (s.length() == 0)
					continue;
				if (Character.isDigit(s.charAt(s.length() - 1)))
					continue;
				fillMap(monthsTable, keys[i], Integer.valueOf(i));
				fillMap(monthsTable, keys[i].replace(".", ""), Integer.valueOf(i));
			}
			keys = dfs.getWeekdays();
			for (int i = 0; i < keys.length; i++) {
				String s = keys[i];
				if (s.length() == 0)
					continue;
				fillMap(daysTable, s, Integer.valueOf(i));
				fillMap(daysTable, s.replace(".", ""), Integer.valueOf(i));
			}
			keys = dfs.getShortWeekdays();
			for (int i = 0; i < keys.length; i++) {
				String s = keys[i];
				if (s.length() == 0)
					continue;
				fillMap(daysTable, s, Integer.valueOf(i));
				fillMap(daysTable, s.replace(".", ""), Integer.valueOf(i));
			}
		}
	}

	private static void fillMap(TreeMap<String, Integer> map, String key, Integer value) {
		map.put(key, value);
		key = key.replace("é", "e");
		key = key.replace("û", "u");
		map.put(key, value);
	}

	/**
	 * try read a Date from a Object
	 */
	public static Date convertToDate(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Date)
			return (Date) obj;
		if (obj instanceof String) {
			StringTokenizer st = new StringTokenizer((String) obj, " -/:,.+");
			String s1 = "";
			if (!st.hasMoreTokens())
				return null;
			s1 = st.nextToken();
			if (s1.length() == 4 && Character.isDigit(s1.charAt(0)))
				return getYYYYMMDD(st, s1);
			// skip Day if present.
			if (daysTable.containsKey(s1)) {
				if (!st.hasMoreTokens())
					return null;
				s1 = st.nextToken();
			}
			if (monthsTable.containsKey(s1))
				return getMMDDYYYY(st, s1);

			if (Character.isDigit(s1.charAt(0)))
				return getDDMMYYYY(st, s1);
			return null;
		}
		throw new RuntimeException("Primitive: Can not convert " + obj.getClass().getName() + " to int");
	}

	private static Date getYYYYMMDD(StringTokenizer st, String s1) {
		GregorianCalendar cal = new GregorianCalendar(2000, 0, 0, 0, 0, 0);
		cal.setTimeInMillis(0);

		int year = Integer.parseInt(s1);
		cal.set(Calendar.YEAR, year);
		if (!st.hasMoreTokens())
			return cal.getTime();
		s1 = st.nextToken();

		cal.set(Calendar.MONTH, parseMonth(s1));
		if (!st.hasMoreTokens())
			return cal.getTime();

		s1 = st.nextToken();
		if (Character.isDigit(s1.charAt(0))) {
			if (s1.length()==5 && s1.charAt(2) == 'T') {
				// TIME + TIMEZONE
				int day = Integer.parseInt(s1.substring(0,2));
				cal.set(Calendar.DAY_OF_MONTH, day);
				return addHour(st, cal, s1.substring(3));
			}
			int day = Integer.parseInt(s1);
			cal.set(Calendar.DAY_OF_MONTH, day);
			return addHour(st, cal, null);
		}
		return cal.getTime();
	}

	private static int getYear(String s1) {
		int year = Integer.parseInt(s1);
		// CET ?
		if (year < 100) {
			if (year > 23)
				year += 2000;
			else
				year += 1900;
		}
		return year;
	}

	private static Date getMMDDYYYY(StringTokenizer st, String s1) {
		GregorianCalendar cal = new GregorianCalendar(2000, 0, 0, 0, 0, 0);
		Integer month = monthsTable.get(s1);
		if (month == null)
			throw new NullPointerException("can not parse " + s1 + " as month");
		cal.set(Calendar.MONTH, month);
		if (!st.hasMoreTokens())
			return null;
		s1 = st.nextToken();
		// DAY
		int day = Integer.parseInt(s1);
		cal.set(Calendar.DAY_OF_MONTH, day);

		if (!st.hasMoreTokens())
			return null;
		s1 = st.nextToken();
		if (Character.isLetter(s1.charAt(0))) {
			if (!st.hasMoreTokens())
				return null;
			s1 = st.nextToken();
		}
		cal.set(Calendar.YEAR, getYear(s1));

		// /if (st.hasMoreTokens())
		// return null;
		// s1 = st.nextToken();
		return addHour(st, cal, null);
		// return cal.getTime();
	}

	private static Date getDDMMYYYY(StringTokenizer st, String s1) {
		GregorianCalendar cal = new GregorianCalendar(2000, 0, 0, 0, 0, 0);
		int day = Integer.parseInt(s1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		if (!st.hasMoreTokens())
			return null;
		s1 = st.nextToken();
		cal.set(Calendar.MONTH, parseMonth(s1));

		if (!st.hasMoreTokens())
			return null;
		s1 = st.nextToken();
		cal.set(Calendar.YEAR, getYear(s1));
		return addHour(st, cal, null);
	}

	private static Date addHour(StringTokenizer st, Calendar cal, String s1) {
		// String s1;
		if (s1 == null) {
			if (!st.hasMoreTokens())
				return cal.getTime();
			s1 = st.nextToken();
		}
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s1));

		if (!st.hasMoreTokens())
			return cal.getTime();
		s1 = st.nextToken();

		s1 = trySkip(st, s1, cal);
		if (s1 == null)
			return cal.getTime();

		// if (s1.equalsIgnoreCase("h")) {
		// if (!st.hasMoreTokens())
		// return cal.getTime();
		// s1 = st.nextToken();
		// }
		cal.set(Calendar.MINUTE, Integer.parseInt(s1));

		if (!st.hasMoreTokens())
			return cal.getTime();
		s1 = st.nextToken();

		s1 = trySkip(st, s1, cal);
		if (s1 == null)
			return cal.getTime();

		cal.set(Calendar.SECOND, Integer.parseInt(s1));
		if (!st.hasMoreTokens())
			return cal.getTime();
		s1 = st.nextToken();

		s1 = trySkip(st, s1, cal);
		if (s1 == null)
			return cal.getTime();
		// TODO ADD TIME ZONE
		s1 = trySkip(st, s1, cal);
		// if (s1.equalsIgnoreCase("pm"))
		// cal.add(Calendar.HOUR_OF_DAY, 12);
		return cal.getTime();
	}

	private static String trySkip(StringTokenizer st, String s1, Calendar cal) {
		while (voidData.contains(s1)) {
			if (s1.equalsIgnoreCase("pm"))
				cal.add(Calendar.HOUR_OF_DAY, 12);
			if (!st.hasMoreTokens())
				return null;
			s1 = st.nextToken();
		}
		return s1;
	}

}
