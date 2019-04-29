<%@ page   language="java"    import="java.util.*"   pageEncoding="UTF-8"%>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="s"  uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"	  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"		  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"		  uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
   <!--
      $(function(){	
      	$("#picture").on("click",function(){
      		$("#pictureFile").click();
      	});
      });
      //-->
</script>

<form id="actionForm"  
	method="post" 
	type="alert" 
	forward="<s:Base/>/profile/forwardBasic"
	action="<s:Base/>/profile/update/basic" 
	enctype="multipart/form-data">
	 <div class="" style="width:100%;">
	    <div class="main">
	    <div class="mainin">			 
  	        <!-- content -->    
  	      	<!--table-->
			  <table class="datatable">
				<tbody>				
				<tr>
					<th style="width:15%;"><s:Locale code="userinfo.username" />：</th>
					<td style="width:35%;">
					<input type="hidden" id="id" name="id" value="${model.id}"/>
						<input type="text" readonly id="username" name="username"  title="" value="${model.username}"/>
						<label for="username"></label>
					</td>
					<th style="width:15%;"><s:Locale code="userinfo.userType" />：</th>
					<td  style="width:35%;">
						<c:forEach items="${userTypeList}" var="userType">
							<c:if test="${model.userType  eq userType.id}"><input readonly type="text" id="userType" name="userType"  title="" value="${userType.name}"/></c:if>
				      	</c:forEach>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;
					</td>
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.displayName" />：</th>
					<td>
						<input type="text" id="displayName" name="displayName"  title="" value="${model.displayName}"/>
						<label for="displayName"></label>
					</td>
					<th rowspan="4"><s:Locale code="userinfo.picture" />：</th>
					<td rowspan="4">
						<c:if test="${null==model.picture}">
							<img id="picture" width="150px" height="150px" src="<c:url value="/images/uploadimage.jpg"/>" />
						</c:if>
						<c:if test="${null!=model.picture}">
						<img id="picture" width="150px" height="150px" src="<c:url value="/image/${model.id}"/>" />
						</c:if>
						<input type="file" id="pictureFile" name="pictureFile" style="display:none" />
						<b class="orange">*</b><label for="picture"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.familyName" />：</th>
					<td>
						<input type="text" id="familyName" name="familyName"  title="" value="${model.familyName}"/>
						<b class="orange">*</b><label for="familyName"></label>
					</td>
					
					
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.givenName" />：</th>
					<td>
						<input type="text" id="givenName" name="givenName"  title="" value="${model.givenName}"/>
						<b class="orange">*</b><label for="givenName"></label>
					</td>
					
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.middleName" />：</th>
					<td>
						<input type="text" id="middleName" name="middleName"  title="" value="${model.middleName}"/>
						<label for="middleName"></label>
					</td>
					
					
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.nickName" />：</th>
					<td>
						<input type="text" id="nickName" name="nickName"  title="" value="${model.nickName}"/>
						<label for="nickName"></label>
					</td>
					<th><s:Locale code="userinfo.gender" />：</th>
					<td>
						<select name="gender"  class="gender">
								<option value="1"  <c:if test="${1==model.gender}">selected</c:if> ><s:Locale code="userinfo.gender.female" /></option>
								<option value="2"  <c:if test="${2==model.gender}">selected</c:if> ><s:Locale code="userinfo.gender.male" /></option>
						</select>
						<label for="gender"></label>
						
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.married" />：</th>
					<td>
						<select name="married"  class="select_t">
							<option value="0"  <c:if test="${0==model.married}">selected</c:if> ><s:Locale code="userinfo.usertype.UNKNOWN" /></option>
							<option value="1"  <c:if test="${1==model.married}">selected</c:if> ><s:Locale code="userinfo.usertype.SINGLE" /></option>
							<option value="2"  <c:if test="${2==model.married}">selected</c:if> ><s:Locale code="userinfo.usertype.MARRIED" /></option>
							<option value="3"  <c:if test="${3==model.married}">selected</c:if> ><s:Locale code="userinfo.usertype.DIVORCE" /></option>
							<option value="4"  <c:if test="${4==model.married}">selected</c:if> ><s:Locale code="userinfo.usertype.WIDOWED" /></option>
						</select>
					</td>
					<th><s:Locale code="userinfo.webSite" />：</th>
					<td>
						<input type="text" id="webSite" name="webSite"  title="" value="${model.webSite}"/>
						<label for="webSite"></label>
						
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.idType" />：</th>
					<td>
						<select name="idType"  class="select_t">
							<option value="0"  <c:if test="${0==model.idType}">selected</c:if> ><s:Locale code="userinfo.usertype.UNKNOWN" /></option>
							<option value="1"  <c:if test="${1==model.idType}">selected</c:if> ><s:Locale code="userinfo.usertype.IDCARD" /></option>
							<option value="2"  <c:if test="${2==model.idType}">selected</c:if> ><s:Locale code="userinfo.usertype.PASSPORT" /></option>
							<option value="3"  <c:if test="${3==model.idType}">selected</c:if> ><s:Locale code="userinfo.usertype.STUDENTCARD" /></option>
							<option value="4"  <c:if test="${4==model.idType}">selected</c:if> ><s:Locale code="userinfo.usertype.MILITARYCARD" /></option>
						</select>
					</td>
					
					<th><s:Locale code="userinfo.idCardNo" />：</th>
					<td>
						<input type="text" id="idCardNo" name="idCardNo"  title="" value="${model.idCardNo}"/>
						<label for="idCardNo"></label>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;
					</td>
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.startWorkDate" />：</th>
					<td>
						<input type="text"  class="datepicker"  id="startWorkDate" name="startWorkDate"  title="" value="${model.startWorkDate}"/>
						<label for="startWorkDate"></label>
					</td>
					<th><s:Locale code="userinfo.preferredLanguage" />：</th>
					<td>
						<select name="preferredLanguage" id="preferredLanguage">
							
							<option value="en_US"  <c:if test="${'en_US'==model.preferredLanguage}">selected</c:if>  >English</option>
							<option value="nl_NL"  <c:if test="${'nl_NL'==model.preferredLanguage}">selected</c:if>  >Dutch</option>
							<option value="fr"     <c:if test="${'fr'==model.preferredLanguage}">selected</c:if>  >French</option>
							<option value="de"     <c:if test="${'de'==model.preferredLanguage}">selected</c:if>  >German</option>
							<option value="it"     <c:if test="${'it'==model.preferredLanguage}">selected</c:if>  >Italian</option>
							<option value="es"     <c:if test="${'es'==model.preferredLanguage}">selected</c:if>  >Spanish</option>
							<option value="sv"     <c:if test="${'sv'==model.preferredLanguage}">selected</c:if>  >Swedish</option>
							<option value="pt_BR"  <c:if test="${'pt_BR'==model.preferredLanguage}">selected</c:if>  >Portuguese  (Brazilian)</option>
							<option value="ja"     <c:if test="${'ja'==model.preferredLanguage}">selected</c:if>  >Japanese</option>
							<option value="zh_CN"  <c:if test="${'zh_CN'==model.preferredLanguage}">selected</c:if>  >Chinese (Simplified)</option>
							<option value="zh_TW"  <c:if test="${'zh_TW'==model.preferredLanguage}">selected</c:if>  >Chinese (Traditional)</option>
							<option value="ko"     <c:if test="${'ko'==model.preferredLanguage}">selected</c:if>  >Korean</option>
							<option value="th"     <c:if test="${'th'==model.preferredLanguage}">selected</c:if>  >Thai</option>
							<option value="fi"     <c:if test="${'fi'==model.preferredLanguage}">selected</c:if>  >Finnish</option>
							<option value="ru"     <c:if test="${'ru'==model.preferredLanguage}">selected</c:if>  >Russian</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.timeZone" />：</th>
					<td>
						<select id="timeZone" name="timeZone" tabindex="61">
							<option value="Pacific/Kiritimati"   <c:if test="${'Pacific/Kiritimati'==model.timeZone}">selected</c:if>>(GMT+14:00) Line Islands Time (Pacific/Kiritimati)</option>
							<option value="Pacific/Chatham"      <c:if test="${'Pacific/Chatham'==model.timeZone}">selected</c:if>>(GMT+13:45) Chatham Daylight Time (Pacific/Chatham)</option>
							<option value="Pacific/Auckland"     <c:if test="${'Pacific/Auckland'==model.timeZone}">selected</c:if>>(GMT+13:00) New Zealand Daylight Time (Pacific/Auckland)</option>
							<option value="Pacific/Enderbury"    <c:if test="${'Pacific/Enderbury'==model.timeZone}">selected</c:if>>(GMT+13:00) Phoenix Islands Time (Pacific/Enderbury)</option>
							<option value="Pacific/Tongatapu"    <c:if test="${'Pacific/Tongatapu'==model.timeZone}">selected</c:if>>(GMT+13:00) Tonga Time (Pacific/Tongatapu)</option>
							<option value="Asia/Kamchatka"       <c:if test="${'Asia/Kamchatkai'==model.timeZone}">selected</c:if>>(GMT+12:00) Magadan Time (Asia/Kamchatka)</option>
							<option value="Pacific/Fiji"         <c:if test="${'Pacific/Fiji'==model.timeZone}">selected</c:if>>(GMT+12:00) Fiji Time (Pacific/Fiji)</option>
							<option value="Pacific/Norfolk"      <c:if test="${'Pacific/Norfolk'==model.timeZone}">selected</c:if>>(GMT+11:30) Norfolk Islands Time (Pacific/Norfolk)</option>
							<option value="Australia/Lord_Howe"  <c:if test="${'Australia/Lord_Howe'==model.timeZone}">selected</c:if>>(GMT+11:00) Lord Howe Daylight Time (Australia/Lord_Howe)</option>
							<option value="Australia/Sydney"     <c:if test="${'Australia/Sydney'==model.timeZone}">selected</c:if>>(GMT+11:00) Australian Eastern Daylight Time (Australia/Sydney)</option>
							<option value="Pacific/Guadalcanal"  <c:if test="${'Australia/Sydney'==model.timeZone}">selected</c:if> >(GMT+11:00) Solomon Islands Time (Pacific/Guadalcanal)</option>
							<option value="Australia/Adelaide"   <c:if test="${'Australia/Adelaide'==model.timeZone}">selected</c:if>>(GMT+10:30) Australian Central Daylight Time (Australia/Adelaide)</option>
							<option value="Australia/Brisbane"   <c:if test="${'Australia/Brisbane'==model.timeZone}">selected</c:if>>(GMT+10:00) Australian Eastern Standard Time (Australia/Brisbane)</option>
							<option value="Australia/Darwin"     <c:if test="${'Australia/Darwin'==model.timeZone}">selected</c:if>>(GMT+09:30) Australian Central Standard Time (Australia/Darwin)</option>
							<option value="Asia/Seoul"           <c:if test="${'Asia/Seoul'==model.timeZone}">selected</c:if>>(GMT+09:00) Korean Standard Time (Asia/Seoul)</option>
							<option value="Asia/Tokyo"           <c:if test="${'Asia/Tokyo'==model.timeZone}">selected</c:if>>(GMT+09:00) Japan Standard Time (Asia/Tokyo)</option>
							<option value="Asia/Hong_Kong"       <c:if test="${'Asia/Hong_Kong'==model.timeZone}">selected</c:if>>(GMT+08:00) Hong Kong Time (Asia/Hong_Kong)</option>
							<option value="Asia/Kuala_Lumpur"    <c:if test="${'Asia/Kuala_Lumpur'==model.timeZone}">selected</c:if>>(GMT+08:00) Malaysia Time (Asia/Kuala_Lumpur)</option>
							<option value="Asia/Manila"          <c:if test="${'Asia/Manila'==model.timeZone}">selected</c:if>>(GMT+08:00) Philippine Time (Asia/Manila)</option>
							<option value="Asia/Shanghai"        <c:if test="${'Asia/Shanghai'==model.timeZone}">selected</c:if>>(GMT+08:00) China Standard Time (Asia/Shanghai)</option>
							<option value="Asia/Singapore"       <c:if test="${'Asia/Singapore'==model.timeZone}">selected</c:if>>(GMT+08:00) Singapore Standard Time (Asia/Singapore)</option>
							<option value="Asia/Taipei"          <c:if test="${'Asia/Taipei'==model.timeZone}">selected</c:if> >(GMT+08:00) Taipei Standard Time (Asia/Taipei)</option>
							<option value="Australia/Perth"      <c:if test="${'Australia/Perth'==model.timeZone}">selected</c:if>>(GMT+08:00) Australian Western Standard Time (Australia/Perth)</option>
							<option value="Asia/Bangkok"         <c:if test="${'Asia/Bangkok'==model.timeZone}">selected</c:if>>(GMT+07:00) Indochina Time (Asia/Bangkok)</option>
							<option value="Asia/Ho_Chi_Minh"     <c:if test="${'Asia/Ho_Chi_Minh'==model.timeZone}">selected</c:if>>(GMT+07:00) Indochina Time (Asia/Ho_Chi_Minh)</option>
							<option value="Asia/Jakarta"         <c:if test="${'Asia/Jakarta'==model.timeZone}">selected</c:if>>(GMT+07:00) Western Indonesia Time (Asia/Jakarta)</option>
							<option value="Asia/Rangoon"         <c:if test="${'Asia/Rangoon'==model.timeZone}">selected</c:if>>(GMT+06:30) Myanmar Time (Asia/Rangoon)</option>
							<option value="Asia/Dhaka"           <c:if test="${'Asia/Dhaka'==model.timeZone}">selected</c:if>>(GMT+06:00) Bangladesh Time (Asia/Dhaka)</option>
							<option value="Asia/Yekaterinburg"   <c:if test="${'Asia/Yekaterinburg'==model.timeZone}">selected</c:if>>(GMT+06:00) Yekaterinburg Time (Asia/Yekaterinburg)</option>
							<option value="Asia/Kathmandu"       <c:if test="${'Asia/Kathmandu'==model.timeZone}">selected</c:if>>(GMT+05:45) Nepal Time (Asia/Kathmandu)</option>
							<option value="Asia/Colombo"         <c:if test="${'Asia/Colombo'==model.timeZone}">selected</c:if>>(GMT+05:30) India Standard Time (Asia/Colombo)</option>
							<option value="Asia/Kolkata"         <c:if test="${'Asia/Kolkata'==model.timeZone}">selected</c:if>>(GMT+05:30) India Standard Time (Asia/Kolkata)</option>
							<option value="Asia/Baku"            <c:if test="${'Asia/Baku'==model.timeZone}">selected</c:if>>(GMT+05:00) Azerbaijan Summer Time (Asia/Baku)</option>
							<option value="Asia/Karachi"         <c:if test="${'Asia/Karachi'==model.timeZone}">selected</c:if>>(GMT+05:00) Pakistan Time (Asia/Karachi)</option>
							<option value="Asia/Tashkent"        <c:if test="${'Asia/Tashkent'==model.timeZone}">selected</c:if>>(GMT+05:00) Uzbekistan Time (Asia/Tashkent)</option>
							<option value="Asia/Kabul"           <c:if test="${'Asia/Kabul'==model.timeZone}">selected</c:if>>(GMT+04:30) Afghanistan Time (Asia/Kabul)</option>
							<option value="Asia/Dubai"           <c:if test="${'Asia/Dubai'==model.timeZone}">selected</c:if>>(GMT+04:00) Gulf Standard Time (Asia/Dubai)</option>
							<option value="Asia/Tbilisi"         <c:if test="${'Asia/Tbilisi'==model.timeZone}">selected</c:if>>(GMT+04:00) Georgia Time (Asia/Tbilisi)</option>
							<option value="Asia/Yerevan"         <c:if test="${'Asia/Yerevan'==model.timeZone}">selected</c:if>>(GMT+04:00) Armenia Time (Asia/Yerevan)</option>
							<option value="Europe/Moscow"        <c:if test="${'Europe/Moscow'==model.timeZone}">selected</c:if>>(GMT+04:00) Moscow Standard Time (Europe/Moscow)</option>
							<option value="Asia/Tehran"          <c:if test="${'Asia/Tehran'==model.timeZone}">selected</c:if>>(GMT+03:30) Iran Standard Time (Asia/Tehran)</option>
							<option value="Africa/Nairobi"       <c:if test="${'Africa/Nairobi'==model.timeZone}">selected</c:if>>(GMT+03:00) East Africa Time (Africa/Nairobi)</option>
							<option value="Asia/Baghdad"         <c:if test="${'Asia/Baghdad'==model.timeZone}">selected</c:if>>(GMT+03:00) Arabian Standard Time (Asia/Baghdad)</option>
							<option value="Asia/Beirut"          <c:if test="${'Asia/Beirut'==model.timeZone}">selected</c:if>>(GMT+03:00) Eastern European Summer Time (Asia/Beirut)</option>
							<option value="Asia/Jerusalem"       <c:if test="${'Asia/Jerusalem'==model.timeZone}">selected</c:if>>(GMT+03:00) Israel Daylight Time (Asia/Jerusalem)</option>
							<option value="Asia/Kuwait"          <c:if test="${'Asia/Kuwait'==model.timeZone}">selected</c:if>>(GMT+03:00) Arabian Standard Time (Asia/Kuwait)</option>
							<option value="Asia/Riyadh"          <c:if test="${'Asia/Riyadh'==model.timeZone}">selected</c:if>>(GMT+03:00) Arabian Standard Time (Asia/Riyadh)</option>
							<option value="Europe/Athens"        <c:if test="${'Europe/Athens'==model.timeZone}">selected</c:if>>(GMT+03:00) Eastern European Summer Time (Europe/Athens)</option>
							<option value="Europe/Bucharest"     <c:if test="${'Europe/Bucharest'==model.timeZone}">selected</c:if>>(GMT+03:00) Eastern European Summer Time (Europe/Bucharest)</option>
							<option value="Europe/Helsinki"      <c:if test="${'Europe/Helsinki'==model.timeZone}">selected</c:if>>(GMT+03:00) Eastern European Summer Time (Europe/Helsinki)</option>
							<option value="Europe/Istanbul"      <c:if test="${'Europe/Istanbul'==model.timeZone}">selected</c:if>>(GMT+03:00) Eastern European Summer Time (Europe/Istanbul)</option>
							<option value="Europe/Minsk"         <c:if test="${'Europe/Minsk'==model.timeZone}">selected</c:if>>(GMT+03:00) Further-eastern European Time (Europe/Minsk)</option>
							<option value="Africa/Cairo"         <c:if test="${'Africa/Cairo'==model.timeZone}">selected</c:if>>(GMT+02:00) Eastern European Time (Africa/Cairo)</option>
							<option value="Africa/Johannesburg"  <c:if test="${'Africa/Johannesburg'==model.timeZone}">selected</c:if>>(GMT+02:00) South Africa Standard Time (Africa/Johannesburg)</option>
							<option value="Europe/Amsterdam"     <c:if test="${'Europe/Amsterdam'==model.timeZone}">selected</c:if>>(GMT+02:00) Central European Summer Time (Europe/Amsterdam)</option>
							<option value="Europe/Berlin"        <c:if test="${'Europe/Berlin'==model.timeZone}">selected</c:if>>(GMT+02:00) Central European Summer Time (Europe/Berlin)</option>
							<option value="Europe/Brussels"      <c:if test="${'Europe/Brussels'==model.timeZone}">selected</c:if>>(GMT+02:00) Central European Summer Time (Europe/Brussels)</option>
							<option value="Europe/Paris"         <c:if test="${'Europe/Paris'==model.timeZone}">selected</c:if>>(GMT+02:00) Central European Summer Time (Europe/Paris)</option>
							<option value="Europe/Prague"        <c:if test="${'Europe/Prague'==model.timeZone}">selected</c:if>>(GMT+02:00) Central European Summer Time (Europe/Prague)</option>
							<option value="Europe/Rome"          <c:if test="${'Europe/Rome'==model.timeZone}">selected</c:if>>(GMT+02:00) Central European Summer Time (Europe/Rome)</option>
							<option value="Africa/Algiers"       <c:if test="${'Africa/Algiers'==model.timeZone}">selected</c:if>>(GMT+01:00) Central European Time (Africa/Algiers)</option>
							<option value="Europe/Dublin"        <c:if test="${'Europe/Dublin'==model.timeZone}">selected</c:if>>(GMT+01:00) Irish Summer Time (Europe/Dublin)</option>
							<option value="Europe/Lisbon"        <c:if test="${'Europe/Lisbon'==model.timeZone}">selected</c:if>>(GMT+01:00) Western European Summer Time (Europe/Lisbon)</option>
							<option value="Europe/London"        <c:if test="${'Europe/London'==model.timeZone}">selected</c:if>>(GMT+01:00) British Summer Time (Europe/London)</option>
							<option value="Africa/Casablanca"    <c:if test="${'Africa/Casablanca'==model.timeZone}">selected</c:if>>(GMT+00:00) Western European Time (Africa/Casablanca)</option>
							<option value="America/Scoresbysund" <c:if test="${'America/Scoresbysund'==model.timeZone}">selected</c:if>>(GMT+00:00) East Greenland Summer Time (America/Scoresbysund)</option>
							<option value="Atlantic/Azores"      <c:if test="${'Atlantic/Azores'==model.timeZone}">selected</c:if>>(GMT+00:00) Azores Summer Time (Atlantic/Azores)</option>
							<option value="GMT"                  <c:if test="${'GMT'==model.timeZone}">selected</c:if>>(GMT+00:00) Greenwich Mean Time (GMT)</option>
							<option value="Atlantic/Cape_Verde"  <c:if test="${'Atlantic/Cape_Verde'==model.timeZone}">selected</c:if>>(GMT-01:00) Cape Verde Time (Atlantic/Cape_Verde)</option>
							<option value="Atlantic/South_Georgia" <c:if test="${'Atlantic/South_Georgia'==model.timeZone}">selected</c:if>>(GMT-02:00) South Georgia Time (Atlantic/South_Georgia)</option>
							<option value="America/St_Johns"      <c:if test="${'America/St_Johns'==model.timeZone}">selected</c:if>>(GMT-02:30) Newfoundland Daylight Time (America/St_Johns)</option>
							<option value="America/Argentina/Buenos_Aires" <c:if test="${'America/Argentina/Buenos_Aires'==model.timeZone}">selected</c:if>>(GMT-03:00) Argentina Time (America/Argentina/Buenos_Aires)</option>
							<option value="America/Halifax"      <c:if test="${'America/Halifax'==model.timeZone}">selected</c:if>>(GMT-03:00) Atlantic Daylight Time (America/Halifax)</option>
							<option value="America/Santiago"     <c:if test="${'America/Santiago'==model.timeZone}">selected</c:if>>(GMT-03:00) Chile Summer Time (America/Santiago)</option>
							<option value="America/Sao_Paulo"    <c:if test="${'America/Sao_Paulo'==model.timeZone}">selected</c:if>>(GMT-03:00) Brasilia Time (America/Sao_Paulo)</option>
							<option value="Atlantic/Bermuda"     <c:if test="${'Atlantic/Bermuda'==model.timeZone}">selected</c:if>>(GMT-03:00) Atlantic Daylight Time (Atlantic/Bermuda)</option>
							<option value="America/Indiana/Indianapolis" <c:if test="${'America/Indiana/Indianapolis'==model.timeZone}">selected</c:if>>(GMT-04:00) Eastern Daylight Time (America/Indiana/Indianapolis)</option>
							<option value="America/New_York"     <c:if test="${'America/New_York'==model.timeZone}">selected</c:if>>(GMT-04:00) Eastern Daylight Time (America/New_York)</option>
							<option value="America/Puerto_Rico"  <c:if test="${'America/Puerto_Rico'==model.timeZone}">selected</c:if>>(GMT-04:00) Atlantic Standard Time (America/Puerto_Rico)</option>
							<option value="America/Caracas"      <c:if test="${'America/Caracas'==model.timeZone}">selected</c:if>>(GMT-04:30) Venezuela Time (America/Caracas)</option>
							<option value="America/Bogota"       <c:if test="${'America/Bogota'==model.timeZone}">selected</c:if>>(GMT-05:00) Colombia Time (America/Bogota)</option>
							<option value="America/Chicago"      <c:if test="${'America/Chicago'==model.timeZone}">selected</c:if>>(GMT-05:00) Central Daylight Time (America/Chicago)</option>
							<option value="America/Lima"         <c:if test="${'America/Lima'==model.timeZone}">selected</c:if>>(GMT-05:00) Peru Time (America/Lima)</option>
							<option value="America/Mexico_City"  <c:if test="${'America/Mexico_City'==model.timeZone}">selected</c:if>>(GMT-05:00) Central Daylight Time (America/Mexico_City)</option>
							<option value="America/Panama"       <c:if test="${'America/Panama'==model.timeZone}">selected</c:if>>(GMT-05:00) Eastern Standard Time (America/Panama)</option>
							<option value="America/Denver"       <c:if test="${'America/Denver'==model.timeZone}">selected</c:if>>(GMT-06:00) Mountain Daylight Time (America/Denver)</option>
							<option value="America/El_Salvador"  <c:if test="${'America/El_Salvador'==model.timeZone}">selected</c:if>>(GMT-06:00) Central Standard Time (America/El_Salvador)</option>
							<option value="America/Mazatlan"     <c:if test="${'America/Mazatlan'==model.timeZone}">selected</c:if>>(GMT-06:00) Mountain Daylight Time (America/Mazatlan)</option>
							<option value="America/Los_Angeles"  <c:if test="${'America/Los_Angeles'==model.timeZone}">selected</c:if>>(GMT-07:00) Pacific Daylight Time (America/Los_Angeles)</option>
							<option value="America/Phoenix"      <c:if test="${'America/Phoenix'==model.timeZone}">selected</c:if>>(GMT-07:00) Mountain Standard Time (America/Phoenix)</option>
							<option value="America/Tijuana"      <c:if test="${'America/Tijuana'==model.timeZone}">selected</c:if>>(GMT-07:00) Pacific Daylight Time (America/Tijuana)</option>
							<option value="America/Anchorage"    <c:if test="${'America/Anchorage'==model.timeZone}">selected</c:if>>(GMT-08:00) Alaska Daylight Time (America/Anchorage)</option>
							<option value="Pacific/Pitcairn"     <c:if test="${'Pacific/Pitcairn'==model.timeZone}">selected</c:if>>(GMT-08:00) Pitcairn Time (Pacific/Pitcairn)</option>
							<option value="America/Adak"         <c:if test="${'America/Adak'==model.timeZone}">selected</c:if>>(GMT-09:00) Hawaii-Aleutian Standard Time (America/Adak)</option>
							<option value="Pacific/Gambier"      <c:if test="${'Pacific/Gambier'==model.timeZone}">selected</c:if>>(GMT-09:00) Gambier Time (Pacific/Gambier)</option>
							<option value="Pacific/Marquesas"    <c:if test="${'Pacific/Marquesas'==model.timeZone}">selected</c:if>>(GMT-09:30) Marquesas Time (Pacific/Marquesas)</option>
							<option value="Pacific/Honolulu"     <c:if test="${'Pacific/Honolulu'==model.timeZone}">selected</c:if>>(GMT-10:00) Hawaii-Aleutian Standard Time (Pacific/Honolulu)</option>
							<option value="Pacific/Niue"         <c:if test="${'Pacific/Niue'==model.timeZone}">selected</c:if>>(GMT-11:00) Niue Time (Pacific/Niue)</option>
							<option value="Pacific/Pago_Pago"    <c:if test="${'Pacific/Pago_Pago'==model.timeZone}">selected</c:if>>(GMT-11:00) Samoa Standard Time (Pacific/Pago_Pago)</option>
							</select>

					</td>
					<th><s:Locale code="userinfo.locale" />：</th>
					<td>
						<select name="locale" id="locale">
							
							<option value="en_US"  <c:if test="${'en_US'==model.locale}">selected</c:if>  >English</option>
							<option value="nl_NL"  <c:if test="${'nl_NL'==model.locale}">selected</c:if>  >Dutch</option>
							<option value="fr"     <c:if test="${'fr'==model.locale}">selected</c:if>  >French</option>
							<option value="de"     <c:if test="${'de'==model.locale}">selected</c:if>  >German</option>
							<option value="it"     <c:if test="${'it'==model.locale}">selected</c:if>  >Italian</option>
							<option value="es"     <c:if test="${'es'==model.locale}">selected</c:if>  >Spanish</option>
							<option value="sv"     <c:if test="${'sv'==model.locale}">selected</c:if>  >Swedish</option>
							<option value="pt_BR"  <c:if test="${'pt_BR'==model.locale}">selected</c:if>  >Portuguese  (Brazilian)</option>
							<option value="ja"     <c:if test="${'ja'==model.locale}">selected</c:if>  >Japanese</option>
							<option value="zh_CN"  <c:if test="${'zh_CN'==model.locale}">selected</c:if>  >Chinese (Simplified)</option>
							<option value="zh_TW"  <c:if test="${'zh_TW'==model.locale}">selected</c:if>  >Chinese (Traditional)</option>
							<option value="ko"     <c:if test="${'ko'==model.locale}">selected</c:if>  >Korean</option>
							<option value="th"     <c:if test="${'th'==model.locale}">selected</c:if>  >Thai</option>
							<option value="fi"     <c:if test="${'fi'==model.locale}">selected</c:if>  >Finnish</option>
							<option value="ru"     <c:if test="${'ru'==model.locale}">selected</c:if>  >Russian</option>
						</select>
					</td>
				</tr>
				
				</tbody>
			  </table>
  	        <div class="clear"></div>
		</div>
		</div>
			<div >
				<div >
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input id="submitBtn" class="button" type="button" value="<s:Locale code="button.text.save" />"/>
				</div>
			</div>
	 </div> 
</form>
