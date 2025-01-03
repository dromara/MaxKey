/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
 


package org.maxkey;

import java.util.Date;

import org.dromara.maxkey.util.DateUtils;
import org.joda.time.DateTime;

public class DateExpirationTest {

	public static void main(String[] args) {
		// 当前时间
		Date now = DateTime.now().toDate();
		// 用户时间
		Date uDate = DateUtils.parse("2025-01-04 16:59:53",DateUtils.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS);
		
		System.out.println("user time " + DateUtils.formatDateTime(uDate));
		System.out.println("now  time " + DateUtils.formatDateTime(now));
		
		//用户时间 > 当前时间 
		System.out.println("after "+uDate.after(now));
		//用户时间 < 当前时间
		System.out.println("before "+uDate.before(now));
	}

}
