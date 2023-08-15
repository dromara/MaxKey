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
 

/**
 * 
 */
package org.maxkey.web.authentication.kerberos;

import java.util.Date;
import java.util.regex.Pattern;

import org.dromara.maxkey.authn.support.kerberos.KerberosToken;
import org.dromara.maxkey.util.DateUtils;
import org.dromara.maxkey.util.JsonUtils;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;

/**
 * @author Crystal.Sea
 *
 */
public class KerberosPrincipal {

	/**
	 * 
	 */
	public KerberosPrincipal() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String kerberosPrincipal="Administrator@CONNSEC.COM";
		kerberosPrincipal=kerberosPrincipal.substring(0, kerberosPrincipal.indexOf("@"));
		System.out.println(kerberosPrincipal);

		if (Pattern.matches("[0-9]+", "TWO_WEEK")){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		
		DateTime datetime=new DateTime(new Date(), ISOChronology.getInstanceUTC());
		System.out.println(DateUtils.toUtc(datetime));
		
		datetime=datetime.plus(10*1000);
		
		System.out.println(DateUtils.toUtc(datetime));
		String json="{\"fullPrincipal\":\"Administrator@CONNSEC.COM\",\"principal\":\"Administrator\",\"userDomain\":\"CONNSEC\",\"notOnOrAfter\":\"2014-01-18T07:10:16.624Z\"}";
		KerberosToken kerberosToken=new KerberosToken();
		kerberosToken=(KerberosToken)JsonUtils.stringToObject(json, kerberosToken);
		
		System.out.println(kerberosToken);
		
		System.out.println(DateUtils.toUtcDate(kerberosToken.getNotOnOrAfter()));
		
	}

}
