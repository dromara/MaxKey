<%@ page   language="java"    import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"	  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"		  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"		  uri="http://java.sun.com/jsp/jstl/core" %>

			  
 <table id="table_switch_basic" width="980" class="datatable" >
	<tbody>				
	<tr>
		<th style="width:15%;"><s:Locale code="userinfo.username" />：</th>
		<td style="width:35%;">
		<input type="hidden" id="id" name="id" value=""/>
			<input type="text" id="username" name="username"  title="" value=""/>
			<b class="orange">*</b><label for="username"></label>
		</td>
		<th style="width:15%;"><s:Locale code="userinfo.password" />：</th>
		<td style="width:35%;">
			<input type="password" id="password" name="password"  title="" value=""/>
			<b class="orange">*</b><label for="password"></label>
		</td>
	</tr>
	<tr>
		<td colspan="4">&nbsp;
		</td>
		
	</tr>
	<tr>
		<th><s:Locale code="userinfo.displayName" />：</th>
		<td>
			<input type="text" id="displayName" name="displayName"  title="" value=""/>
			<b class="orange">*</b><label for="displayName"></label>
		</td>
		<th rowspan="4"><s:Locale code="userinfo.picture" />：</th>
		<td rowspan="4">
			<img id="picture" width="150px" height="150px" src="<c:url value="/images/uploadimage.jpg"/>" />
			<input type="file" id="pictureFile" name="pictureFile" style="display:none" />
					</td>
	</tr>
	<tr>
		<th><s:Locale code="userinfo.familyName" />：</th>
		<td>
			<input type="text" id="familyName" name="familyName"  title="" value=""/>
			<b class="orange">*</b><label for="familyName"></label>
		</td>
	</tr>
	<tr>
		<th><s:Locale code="userinfo.givenName" />：</th>
		<td>
			<input type="text" id="givenName" name="givenName"  title="" value=""/>
			<b class="orange">*</b><label for="givenName"></label>
		</td>
	</tr>
	<tr>
		<th><s:Locale code="userinfo.middleName" />：</th>
		<td>
			<input type="text" id="middleName" name="middleName"  title="" value=""/><label for="middleName"></label>
		</td>
		
		
	</tr>
	<tr>
		<th><s:Locale code="userinfo.nickName" />：</th>
		<td>
			<input type="text" id="nickName" name="nickName"  title="" value=""/><label for="nickName"></label>
		</td>
		<th><s:Locale code="userinfo.userType" />：</th>
		<td style="width:35%;">
				<c:forEach items="${userTypeList}" var="userType">
					<c:if test="${userTypeId  eq userType.id}">
					<input readonly type="text" id="userType" name="userType"  title="" value="${userType.id}" style="display:none" />
					<input readonly type="text" id="userType" name="userTypeName"  title="" value="${userType.name}"/></c:if>
		      	</c:forEach>
		</td>
	</tr>
	<tr>
		<th><s:Locale code="userinfo.gender" />：</th>
		<td>
			<select name="gender"  class="gender">
					<option value="1"  selected><s:Locale code="userinfo.gender.female" /></option>
					<option value="2"  ><s:Locale code="userinfo.gender.male" /></option>
			</select><label for="gender"></label>
		</td>
		<th><s:Locale code="userinfo.birthDate" />：</th>
		<td>
			<input type="text" id="birthDate" name="birthDate"  title="" value=""/><label for="birthDate"></label>
		</td>
	</tr>

	<tr>
		<td colspan="4">&nbsp;
		</td>
		
	</tr>
	<tr>
		<th><s:Locale code="userinfo.webSite" />：</th>
		<td>
			<input type="text" id="webSite" name="webSite"  title="" value=""/><label for="webSite"></label>
		</td>
		<th><s:Locale code="userinfo.preferredLanguage" />：</th>
		<td>
			<select name="preferredLanguage" id="preferredLanguage">
				<option value="" selected="selected">Language</option>
				<option value="en_US"  >English</option>
				<option value="nl_NL"  >Dutch</option>
				<option value="fr"     >French</option>
				<option value="de"     >German</option>
				<option value="it"     >Italian</option>
				<option value="es"     >Spanish</option>
				<option value="sv"     >Swedish</option>
				<option value="pt_BR"  >Portuguese  (Brazilian)</option>
				<option value="ja"     >Japanese</option>
				<option value="zh_CN" selected >Chinese (Simplified)</option>
				<option value="zh_TW"  >Chinese (Traditional)</option>
				<option value="ko"     >Korean</option>
				<option value="th"     >Thai</option>
				<option value="fi"     >Finnish</option>
				<option value="ru"     >Russian</option>
			</select>
		</td>
	</tr>
	<tr>
		<th><s:Locale code="userinfo.timeZone" />：</th>
		<td nowrap >
			<select id="timeZone" name="timeZone" tabindex="61">
				<option value="Pacific/Kiritimati"   >(GMT+14:00) Line Islands Time (Pacific/Kiritimati)</option>
				<option value="Pacific/Chatham"      >(GMT+13:45) Chatham Daylight Time (Pacific/Chatham)</option>
				<option value="Pacific/Auckland"     >(GMT+13:00) New Zealand Daylight Time (Pacific/Auckland)</option>
				<option value="Pacific/Enderbury"    >(GMT+13:00) Phoenix Islands Time (Pacific/Enderbury)</option>
				<option value="Pacific/Tongatapu"    >(GMT+13:00) Tonga Time (Pacific/Tongatapu)</option>
				<option value="Asia/Kamchatka"       >(GMT+12:00) Magadan Time (Asia/Kamchatka)</option>
				<option value="Pacific/Fiji"         >(GMT+12:00) Fiji Time (Pacific/Fiji)</option>
				<option value="Pacific/Norfolk"      >(GMT+11:30) Norfolk Islands Time (Pacific/Norfolk)</option>
				<option value="Australia/Lord_Howe"  >(GMT+11:00) Lord Howe Daylight Time (Australia/Lord_Howe)</option>
				<option value="Australia/Sydney"     >(GMT+11:00) Australian Eastern Daylight Time (Australia/Sydney)</option>
				<option value="Pacific/Guadalcanal"  >(GMT+11:00) Solomon Islands Time (Pacific/Guadalcanal)</option>
				<option value="Australia/Adelaide"   >(GMT+10:30) Australian Central Daylight Time (Australia/Adelaide)</option>
				<option value="Australia/Brisbane"   >(GMT+10:00) Australian Eastern Standard Time (Australia/Brisbane)</option>
				<option value="Australia/Darwin"     >(GMT+09:30) Australian Central Standard Time (Australia/Darwin)</option>
				<option value="Asia/Seoul"           >(GMT+09:00) Korean Standard Time (Asia/Seoul)</option>
				<option value="Asia/Tokyo"           >(GMT+09:00) Japan Standard Time (Asia/Tokyo)</option>
				<option value="Asia/Hong_Kong"       >(GMT+08:00) Hong Kong Time (Asia/Hong_Kong)</option>
				<option value="Asia/Kuala_Lumpur"    >(GMT+08:00) Malaysia Time (Asia/Kuala_Lumpur)</option>
				<option value="Asia/Manila"          >(GMT+08:00) Philippine Time (Asia/Manila)</option>
				<option value="Asia/Shanghai" selected >(GMT+08:00) China Standard Time (Asia/Shanghai)</option>
				<option value="Asia/Singapore"       >(GMT+08:00) Singapore Standard Time (Asia/Singapore)</option>
				<option value="Asia/Taipei"          >(GMT+08:00) Taipei Standard Time (Asia/Taipei)</option>
				<option value="Australia/Perth"      >(GMT+08:00) Australian Western Standard Time (Australia/Perth)</option>
				<option value="Asia/Bangkok"         >(GMT+07:00) Indochina Time (Asia/Bangkok)</option>
				<option value="Asia/Ho_Chi_Minh"     >(GMT+07:00) Indochina Time (Asia/Ho_Chi_Minh)</option>
				<option value="Asia/Jakarta"         >(GMT+07:00) Western Indonesia Time (Asia/Jakarta)</option>
				<option value="Asia/Rangoon"         >(GMT+06:30) Myanmar Time (Asia/Rangoon)</option>
				<option value="Asia/Dhaka"           >(GMT+06:00) Bangladesh Time (Asia/Dhaka)</option>
				<option value="Asia/Yekaterinburg"   >(GMT+06:00) Yekaterinburg Time (Asia/Yekaterinburg)</option>
				<option value="Asia/Kathmandu"       >(GMT+05:45) Nepal Time (Asia/Kathmandu)</option>
				<option value="Asia/Colombo"         >(GMT+05:30) India Standard Time (Asia/Colombo)</option>
				<option value="Asia/Kolkata"         >(GMT+05:30) India Standard Time (Asia/Kolkata)</option>
				<option value="Asia/Baku"            >(GMT+05:00) Azerbaijan Summer Time (Asia/Baku)</option>
				<option value="Asia/Karachi"         >(GMT+05:00) Pakistan Time (Asia/Karachi)</option>
				<option value="Asia/Tashkent"        >(GMT+05:00) Uzbekistan Time (Asia/Tashkent)</option>
				<option value="Asia/Kabul"           >(GMT+04:30) Afghanistan Time (Asia/Kabul)</option>
				<option value="Asia/Dubai"           >(GMT+04:00) Gulf Standard Time (Asia/Dubai)</option>
				<option value="Asia/Tbilisi"         >(GMT+04:00) Georgia Time (Asia/Tbilisi)</option>
				<option value="Asia/Yerevan"         >(GMT+04:00) Armenia Time (Asia/Yerevan)</option>
				<option value="Europe/Moscow"        >(GMT+04:00) Moscow Standard Time (Europe/Moscow)</option>
				<option value="Asia/Tehran"          >(GMT+03:30) Iran Standard Time (Asia/Tehran)</option>
				<option value="Africa/Nairobi"       >(GMT+03:00) East Africa Time (Africa/Nairobi)</option>
				<option value="Asia/Baghdad"         >(GMT+03:00) Arabian Standard Time (Asia/Baghdad)</option>
				<option value="Asia/Beirut"          >(GMT+03:00) Eastern European Summer Time (Asia/Beirut)</option>
				<option value="Asia/Jerusalem"       >(GMT+03:00) Israel Daylight Time (Asia/Jerusalem)</option>
				<option value="Asia/Kuwait"          >(GMT+03:00) Arabian Standard Time (Asia/Kuwait)</option>
				<option value="Asia/Riyadh"          >(GMT+03:00) Arabian Standard Time (Asia/Riyadh)</option>
				<option value="Europe/Athens"        >(GMT+03:00) Eastern European Summer Time (Europe/Athens)</option>
				<option value="Europe/Bucharest"     >(GMT+03:00) Eastern European Summer Time (Europe/Bucharest)</option>
				<option value="Europe/Helsinki"      >(GMT+03:00) Eastern European Summer Time (Europe/Helsinki)</option>
				<option value="Europe/Istanbul"      >(GMT+03:00) Eastern European Summer Time (Europe/Istanbul)</option>
				<option value="Europe/Minsk"         >(GMT+03:00) Further-eastern European Time (Europe/Minsk)</option>
				<option value="Africa/Cairo"         >(GMT+02:00) Eastern European Time (Africa/Cairo)</option>
				<option value="Africa/Johannesburg"  >(GMT+02:00) South Africa Standard Time (Africa/Johannesburg)</option>
				<option value="Europe/Amsterdam"     >(GMT+02:00) Central European Summer Time (Europe/Amsterdam)</option>
				<option value="Europe/Berlin"        >(GMT+02:00) Central European Summer Time (Europe/Berlin)</option>
				<option value="Europe/Brussels"      >(GMT+02:00) Central European Summer Time (Europe/Brussels)</option>
				<option value="Europe/Paris"         >(GMT+02:00) Central European Summer Time (Europe/Paris)</option>
				<option value="Europe/Prague"        >(GMT+02:00) Central European Summer Time (Europe/Prague)</option>
				<option value="Europe/Rome"          >(GMT+02:00) Central European Summer Time (Europe/Rome)</option>
				<option value="Africa/Algiers"       >(GMT+01:00) Central European Time (Africa/Algiers)</option>
				<option value="Europe/Dublin"        >(GMT+01:00) Irish Summer Time (Europe/Dublin)</option>
				<option value="Europe/Lisbon"        >(GMT+01:00) Western European Summer Time (Europe/Lisbon)</option>
				<option value="Europe/London"        >(GMT+01:00) British Summer Time (Europe/London)</option>
				<option value="Africa/Casablanca"    >(GMT+00:00) Western European Time (Africa/Casablanca)</option>
				<option value="America/Scoresbysund" >(GMT+00:00) East Greenland Summer Time (America/Scoresbysund)</option>
				<option value="Atlantic/Azores"      >(GMT+00:00) Azores Summer Time (Atlantic/Azores)</option>
				<option value="GMT"                  >(GMT+00:00) Greenwich Mean Time (GMT)</option>
				<option value="Atlantic/Cape_Verde"  >(GMT-01:00) Cape Verde Time (Atlantic/Cape_Verde)</option>
				<option value="Atlantic/South_Georgia" >(GMT-02:00) South Georgia Time (Atlantic/South_Georgia)</option>
				<option value="America/St_Johns"      >(GMT-02:30) Newfoundland Daylight Time (America/St_Johns)</option>
				<option value="America/Argentina/Buenos_Aires" >(GMT-03:00) Argentina Time (America/Argentina/Buenos_Aires)</option>
				<option value="America/Halifax"      >(GMT-03:00) Atlantic Daylight Time (America/Halifax)</option>
				<option value="America/Santiago"     >(GMT-03:00) Chile Summer Time (America/Santiago)</option>
				<option value="America/Sao_Paulo"    >(GMT-03:00) Brasilia Time (America/Sao_Paulo)</option>
				<option value="Atlantic/Bermuda"     >(GMT-03:00) Atlantic Daylight Time (Atlantic/Bermuda)</option>
				<option value="America/Indiana/Indianapolis" >(GMT-04:00) Eastern Daylight Time (America/Indiana/Indianapolis)</option>
				<option value="America/New_York"     >(GMT-04:00) Eastern Daylight Time (America/New_York)</option>
				<option value="America/Puerto_Rico"  >(GMT-04:00) Atlantic Standard Time (America/Puerto_Rico)</option>
				<option value="America/Caracas"      >(GMT-04:30) Venezuela Time (America/Caracas)</option>
				<option value="America/Bogota"       >(GMT-05:00) Colombia Time (America/Bogota)</option>
				<option value="America/Chicago"      >(GMT-05:00) Central Daylight Time (America/Chicago)</option>
				<option value="America/Lima"         >(GMT-05:00) Peru Time (America/Lima)</option>
				<option value="America/Mexico_City"  >(GMT-05:00) Central Daylight Time (America/Mexico_City)</option>
				<option value="America/Panama"       >(GMT-05:00) Eastern Standard Time (America/Panama)</option>
				<option value="America/Denver"       >(GMT-06:00) Mountain Daylight Time (America/Denver)</option>
				<option value="America/El_Salvador"  >(GMT-06:00) Central Standard Time (America/El_Salvador)</option>
				<option value="America/Mazatlan"     >(GMT-06:00) Mountain Daylight Time (America/Mazatlan)</option>
				<option value="America/Los_Angeles"  >(GMT-07:00) Pacific Daylight Time (America/Los_Angeles)</option>
				<option value="America/Phoenix"      >(GMT-07:00) Mountain Standard Time (America/Phoenix)</option>
				<option value="America/Tijuana"      >(GMT-07:00) Pacific Daylight Time (America/Tijuana)</option>
				<option value="America/Anchorage"    >(GMT-08:00) Alaska Daylight Time (America/Anchorage)</option>
				<option value="Pacific/Pitcairn"     >(GMT-08:00) Pitcairn Time (Pacific/Pitcairn)</option>
				<option value="America/Adak"         >(GMT-09:00) Hawaii-Aleutian Standard Time (America/Adak)</option>
				<option value="Pacific/Gambier"      >(GMT-09:00) Gambier Time (Pacific/Gambier)</option>
				<option value="Pacific/Marquesas"    >(GMT-09:30) Marquesas Time (Pacific/Marquesas)</option>
				<option value="Pacific/Honolulu"     >(GMT-10:00) Hawaii-Aleutian Standard Time (Pacific/Honolulu)</option>
				<option value="Pacific/Niue"         >(GMT-11:00) Niue Time (Pacific/Niue)</option>
				<option value="Pacific/Pago_Pago"    >(GMT-11:00) Samoa Standard Time (Pacific/Pago_Pago)</option>
			</select>
		</td>
		<th><s:Locale code="userinfo.locale" />：</th>
		<td>
			<select name="locale" id="locale">
				<option value="" selected="selected">Language</option>
				<option value="en_US"  >English</option>
				<option value="nl_NL"  >Dutch</option>
				<option value="fr"     >French</option>
				<option value="de"     >German</option>
				<option value="it"     >Italian</option>
				<option value="es"     >Spanish</option>
				<option value="sv"     >Swedish</option>
				<option value="pt_BR"  >Portuguese  (Brazilian)</option>
				<option value="ja"     >Japanese</option>
				<option value="zh_CN" selected >Chinese (Simplified)</option>
				<option value="zh_TW"  >Chinese (Traditional)</option>
				<option value="ko"     >Korean</option>
				<option value="th"     >Thai</option>
				<option value="fi"     >Finnish</option>
				<option value="ru"     >Russian</option>
			</select>
		</td>
	</tr>
	</tbody>
 </table>
