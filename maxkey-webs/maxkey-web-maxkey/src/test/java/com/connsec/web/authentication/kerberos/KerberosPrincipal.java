/**
 * 
 */
package com.connsec.web.authentication.kerberos;

import java.util.Date;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.maxkey.authn.support.kerberos.KerberosToken;
import org.maxkey.util.DateUtils;
import org.maxkey.util.JsonUtils;

/**
 * @author Crystal.Sea
 *
 */
public class KerberosPrincipal {

	/**
	 * 
	 */
	public KerberosPrincipal() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		kerberosToken=(KerberosToken)JsonUtils.json2Object(json, kerberosToken);
		
		System.out.println(kerberosToken);
		
		System.out.println(DateUtils.toUtcDate(kerberosToken.getNotOnOrAfter()));
		
	}

}
