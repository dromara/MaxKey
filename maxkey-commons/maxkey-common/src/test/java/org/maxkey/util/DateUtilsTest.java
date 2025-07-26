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
 

package org.maxkey.util;

import java.util.Date;

import org.dromara.maxkey.util.DateUtils;

public class DateUtilsTest {
	
	/**
	 * Main method for test.
	 * 
	 * @param args
	 * @throws EncryptException 
	 */
	public static void main(String[] args) throws Exception {
		String stringValue = "20110610090519";
		System.out.println(stringValue);
//		System.out.println("Parse \"" + stringValue
//				+ "\" using format pattern \"" + DateUtils.FORMAT_DATE_DEFAULT
//				+ "\" with method \"DateUtils.parse()\", result: "
//				+ DateUtils.parse(stringValue));
//		stringValue = "20080506";
//		System.out.println("Parse \"" + stringValue
//				+ "\" using method \"DateUtils.tryParse()\", result: "
//				+ DateUtils.tryParse(stringValue));
//		String s = DateUtils.getExchangeFormat(stringValue,FORMAT_DATE_YYYYMMDDHHMMSS,DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
//		System.out.print("--->>>"+s);
		
//		String str = "2011-08-09";
//		System.out.println(UserPasswordUtil.decrypt("PVuyeIHtXnXv5oSPwPUug66w=="));
//		System.out.println(DateUtils.getFormtPattern1ToPattern2(str, DateUtils.FORMAT_DATE_YYYY_MM_DD, DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));
//		str = "aaa\r\nbbb";
//		List<String> list = StringUtil.strToList(str, "\r\n");
//		System.out.println(list.size());
//		System.out.println(StringUtil.listToStr(null, ","));
		
//		String value = "a,b,,c,,";
//		System.out.println(value.split("\\,").length);
//		System.out.println(StringUtil.removeSplit(value, ",")); 
		
//		Class clazz = TmEmployeeUserInfo.class;
//		Field field = clazz.getDeclaredField("spellName");
//		System.out.println(field.getName());
		
//		System.out.println(UserPasswordUtil.encrypt("oscwebadmin@163.com"));
		//System.out.println(JCEnDecrypt.randomDecrypt("2AF5022B2E78478A9761FD3381BB"));
//		System.out.println(JCEnDecrypt.randomEncrypt("aaa")); 41l2Iw4V 
//		String regEx="[1]{1}[3,5,8,6]{1}[0-9]{9}"; //��ʾa��f
//		System.out.println(Pattern.compile(regEx).matcher("18258842633").find());
//		Date lockoutDate = DateUtils.addDate(new Date(), 0, 30, 0); //解锁时间
//		System.out.println(DateUtils.format(lockoutDate, DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));
		Date date = new Date();
		System.out.println(DateUtils.format(DateUtils.addDate(date, 0, 0, 1, 0, 0, 0),DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS));
		
		System.out.println(DateUtils.format(DateUtils.addMinutes(new Date(), Integer.parseInt("2")*1000),DateUtils.FORMAT_DATE_ISO_TIMESTAMP));
		System.out.println(DateUtils.toUtc(date));
		
		System.out.println(DateUtils.toUtcLocal("2015-11-04T16:00:22.875Z"));
		System.out.println(DateUtils.toUtcLocal("2015-11-04T23:58:14.286+08:00"));
		
		System.out.println(DateUtils.formatDateTime(new Date()));
	
	}
}
