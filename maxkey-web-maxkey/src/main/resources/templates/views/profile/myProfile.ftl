<!DOCTYPE HTML >
<html>
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
	<script type="text/javascript">
	   <!--
	      $(function(){	
	      	$("#picture").on("click",function(){
	      		$("#pictureFile").click();
	      	});
	      });
	      //-->
	</script>
</head>
<body>
<#include  "../layout/top.ftl"/>
<#include  "../layout/nav_primary.ftl"/>
<div class="container">


<form id="actionFormProfile"  
	method="post" 
	type="alert" 
	forward="<@base/>/profile/myProfile"
	action="<@base/>/profile/update/myProfile" 
	enctype="multipart/form-data"
	class="needs-validation" novalidate>
	<input  class="form-control"  type="hidden" id="id" name="id" value="${model.id!}"/>  
	
<div class="row">
<div class="col-md-6">
 	      	<!--table-->
			  <table class="table table-bordered">
				<tbody>				
				<tr>
					<th style="width:15%;"><@locale code="userinfo.username" />：</th>
					<td style="width:35%;">
						<input  class="form-control"  type="text" readonly id="username" name="username"  title="" value="${model.username!}"/>

					</td>
				</tr>
				<tr>
					<th style="width:15%;"><@locale code="userinfo.userType" />：</th>
					<td  style="width:35%;">
						<input  class="form-control"  readonly type="text" id="userType" name="userType"  title="" value="${model.userType !}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.displayName" />：</th>
					<td>
						<input class="form-control"  type="text" id="displayName" name="displayName"  title="" value="${model.displayName!}"  required="" />
					</td>
					
				</tr>
				
				<tr>
					<th><@locale code="userinfo.familyName" />：</th>
					<td>
						<input class="form-control"  type="text" id="familyName" name="familyName"  title="" value="${model.familyName!}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.givenName" />：</th>
					<td>
						<input class="form-control"  type="text" id="givenName" name="givenName"  title="" value="${model.givenName!}"/>
					</td>
					
					
				</tr>
				<tr>
					<th><@locale code="userinfo.middleName" />：</th>
					<td>
						<input class="form-control"  type="text" id="middleName" name="middleName"  title="" value="${model.middleName!}"/>
					</td>
					
					
				</tr>
				<tr>
					
					<th><@locale code="userinfo.nickName" />：</th>
					<td>
						<input class="form-control"  type="text" id="nickName" name="nickName"  title="" value="${model.nickName!}"/>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="userinfo.gender" />：</th>
					<td>
						<select class="form-control" name="gender"  class="gender">
								<option value="1"  <#if 1==model.gender>selected</#if> ><@locale code="userinfo.gender.female" /></option>
								<option value="2"  <#if 2==model.gender>selected</#if> ><@locale code="userinfo.gender.male" /></option>
						</select>
						
					</td>
				</tr>
				<tr>
					
					<th><@locale code="userinfo.married" />：</th>
					<td>
						<select class="form-control"  name="married"  class="select_t">
							<option value="0"  <#if 0==model.married>selected</#if> ><@locale code="userinfo.married.unknown" /></option>
							<option value="1"  <#if 1==model.married>selected</#if> ><@locale code="userinfo.married.single" /></option>
							<option value="2"  <#if 2==model.married>selected</#if> ><@locale code="userinfo.married.married" /></option>
							<option value="3"  <#if 3==model.married>selected</#if> ><@locale code="userinfo.married.divorce" /></option>
							<option value="4"  <#if 4==model.married>selected</#if> ><@locale code="userinfo.married.widowed" /></option>
						</select>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="userinfo.website" />：</th>
					<td>
						<input class="form-control"  type="text" id="webSite" name="webSite"  title="" value="${model.webSite!}"/>

						
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.idtype" />：</th>
					<td>
						<select class="form-control"  name="idType"  class="select_t">
							<option value="0"  <#if 0==model.idType>selected</#if> ><@locale code="userinfo.idtype.unknown" /></option>
							<option value="1"  <#if 1==model.idType>selected</#if> ><@locale code="userinfo.idtype.idcard" /></option>
							<option value="2"  <#if 2==model.idType>selected</#if> ><@locale code="userinfo.idtype.passport" /></option>
							<option value="3"  <#if 3==model.idType>selected</#if> ><@locale code="userinfo.idtype.studentcard" /></option>
							<option value="4"  <#if 4==model.idType>selected</#if> ><@locale code="userinfo.idtype.militarycard" /></option>
						</select>
					</td>
					
				</tr>
				<tr>					
					<th><@locale code="userinfo.idCardNo" />：</th>
					<td>
						<input class="form-control"  type="text" id="idCardNo" name="idCardNo"  title="" value="${model.idCardNo!}"/>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.startWorkDate" />：</th>
					<td>
						<input class="form-control"  type="text"  class="datepicker"  id="startWorkDate" name="startWorkDate"  title="" value="${model.startWorkDate!}"/>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="userinfo.preferredLanguage" />：</th>
					<td>
						<select class="form-control"  name="preferredLanguage" id="preferredLanguage">
							
							<option value="en_US"  <#if 'en_US'==model.preferredLanguage>selected</#if>  >English</option>
							<option value="nl_NL"  <#if 'nl_NL'==model.preferredLanguage>selected</#if>  >Dutch</option>
							<option value="fr"     <#if 'fr'==model.preferredLanguage>selected</#if>  >French</option>
							<option value="de"     <#if 'de'==model.preferredLanguage>selected</#if>  >German</option>
							<option value="it"     <#if 'it'==model.preferredLanguage>selected</#if>  >Italian</option>
							<option value="es"     <#if 'es'==model.preferredLanguage>selected</#if>  >Spanish</option>
							<option value="sv"     <#if 'sv'==model.preferredLanguage>selected</#if>  >Swedish</option>
							<option value="pt_BR"  <#if 'pt_BR'==model.preferredLanguage>selected</#if>  >Portuguese  (Brazilian)</option>
							<option value="ja"     <#if 'ja'==model.preferredLanguage>selected</#if>  >Japanese</option>
							<option value="zh_CN"  <#if 'zh_CN'==model.preferredLanguage>selected</#if>  >Chinese (Simplified)</option>
							<option value="zh_TW"  <#if 'zh_TW'==model.preferredLanguage>selected</#if>  >Chinese (Traditional)</option>
							<option value="ko"     <#if 'ko'==model.preferredLanguage>selected</#if>  >Korean</option>
							<option value="th"     <#if 'th'==model.preferredLanguage>selected</#if>  >Thai</option>
							<option value="fi"     <#if 'fi'==model.preferredLanguage>selected</#if>  >Finnish</option>
							<option value="ru"     <#if 'ru'==model.preferredLanguage>selected</#if>  >Russian</option>
						</select>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.timeZone" />：</th>
					<td>
						<select class="form-control"  id="timeZone" name="timeZone" tabindex="61">
							<option value="Pacific/Kiritimati"   <#if 'Pacific/Kiritimati'==model.timeZone>selected</#if>>(GMT+14:00) Line Islands Time (Pacific/Kiritimati)</option>
							<option value="Pacific/Chatham"      <#if 'Pacific/Chatham'==model.timeZone>selected</#if>>(GMT+13:45) Chatham Daylight Time (Pacific/Chatham)</option>
							<option value="Pacific/Auckland"     <#if 'Pacific/Auckland'==model.timeZone>selected</#if>>(GMT+13:00) New Zealand Daylight Time (Pacific/Auckland)</option>
							<option value="Pacific/Enderbury"    <#if 'Pacific/Enderbury'==model.timeZone>selected</#if>>(GMT+13:00) Phoenix Islands Time (Pacific/Enderbury)</option>
							<option value="Pacific/Tongatapu"    <#if 'Pacific/Tongatapu'==model.timeZone>selected</#if>>(GMT+13:00) Tonga Time (Pacific/Tongatapu)</option>
							<option value="Asia/Kamchatka"       <#if 'Asia/Kamchatkai'==model.timeZone>selected</#if>>(GMT+12:00) Magadan Time (Asia/Kamchatka)</option>
							<option value="Pacific/Fiji"         <#if 'Pacific/Fiji'==model.timeZone>selected</#if>>(GMT+12:00) Fiji Time (Pacific/Fiji)</option>
							<option value="Pacific/Norfolk"      <#if 'Pacific/Norfolk'==model.timeZone>selected</#if>>(GMT+11:30) Norfolk Islands Time (Pacific/Norfolk)</option>
							<option value="Australia/Lord_Howe"  <#if 'Australia/Lord_Howe'==model.timeZone>selected</#if>>(GMT+11:00) Lord Howe Daylight Time (Australia/Lord_Howe)</option>
							<option value="Australia/Sydney"     <#if 'Australia/Sydney'==model.timeZone>selected</#if>>(GMT+11:00) Australian Eastern Daylight Time (Australia/Sydney)</option>
							<option value="Pacific/Guadalcanal"  <#if 'Australia/Sydney'==model.timeZone>selected</#if> >(GMT+11:00) Solomon Islands Time (Pacific/Guadalcanal)</option>
							<option value="Australia/Adelaide"   <#if 'Australia/Adelaide'==model.timeZone>selected</#if>>(GMT+10:30) Australian Central Daylight Time (Australia/Adelaide)</option>
							<option value="Australia/Brisbane"   <#if 'Australia/Brisbane'==model.timeZone>selected</#if>>(GMT+10:00) Australian Eastern Standard Time (Australia/Brisbane)</option>
							<option value="Australia/Darwin"     <#if 'Australia/Darwin'==model.timeZone>selected</#if>>(GMT+09:30) Australian Central Standard Time (Australia/Darwin)</option>
							<option value="Asia/Seoul"           <#if 'Asia/Seoul'==model.timeZone>selected</#if>>(GMT+09:00) Korean Standard Time (Asia/Seoul)</option>
							<option value="Asia/Tokyo"           <#if 'Asia/Tokyo'==model.timeZone>selected</#if>>(GMT+09:00) Japan Standard Time (Asia/Tokyo)</option>
							<option value="Asia/Hong_Kong"       <#if 'Asia/Hong_Kong'==model.timeZone>selected</#if>>(GMT+08:00) Hong Kong Time (Asia/Hong_Kong)</option>
							<option value="Asia/Kuala_Lumpur"    <#if 'Asia/Kuala_Lumpur'==model.timeZone>selected</#if>>(GMT+08:00) Malaysia Time (Asia/Kuala_Lumpur)</option>
							<option value="Asia/Manila"          <#if 'Asia/Manila'==model.timeZone>selected</#if>>(GMT+08:00) Philippine Time (Asia/Manila)</option>
							<option value="Asia/Shanghai"        <#if 'Asia/Shanghai'==model.timeZone>selected</#if>>(GMT+08:00) China Standard Time (Asia/Shanghai)</option>
							<option value="Asia/Singapore"       <#if 'Asia/Singapore'==model.timeZone>selected</#if>>(GMT+08:00) Singapore Standard Time (Asia/Singapore)</option>
							<option value="Asia/Taipei"          <#if 'Asia/Taipei'==model.timeZone>selected</#if> >(GMT+08:00) Taipei Standard Time (Asia/Taipei)</option>
							<option value="Australia/Perth"      <#if 'Australia/Perth'==model.timeZone>selected</#if>>(GMT+08:00) Australian Western Standard Time (Australia/Perth)</option>
							<option value="Asia/Bangkok"         <#if 'Asia/Bangkok'==model.timeZone>selected</#if>>(GMT+07:00) Indochina Time (Asia/Bangkok)</option>
							<option value="Asia/Ho_Chi_Minh"     <#if 'Asia/Ho_Chi_Minh'==model.timeZone>selected</#if>>(GMT+07:00) Indochina Time (Asia/Ho_Chi_Minh)</option>
							<option value="Asia/Jakarta"         <#if 'Asia/Jakarta'==model.timeZone>selected</#if>>(GMT+07:00) Western Indonesia Time (Asia/Jakarta)</option>
							<option value="Asia/Rangoon"         <#if 'Asia/Rangoon'==model.timeZone>selected</#if>>(GMT+06:30) Myanmar Time (Asia/Rangoon)</option>
							<option value="Asia/Dhaka"           <#if 'Asia/Dhaka'==model.timeZone>selected</#if>>(GMT+06:00) Bangladesh Time (Asia/Dhaka)</option>
							<option value="Asia/Yekaterinburg"   <#if 'Asia/Yekaterinburg'==model.timeZone>selected</#if>>(GMT+06:00) Yekaterinburg Time (Asia/Yekaterinburg)</option>
							<option value="Asia/Kathmandu"       <#if 'Asia/Kathmandu'==model.timeZone>selected</#if>>(GMT+05:45) Nepal Time (Asia/Kathmandu)</option>
							<option value="Asia/Colombo"         <#if 'Asia/Colombo'==model.timeZone>selected</#if>>(GMT+05:30) India Standard Time (Asia/Colombo)</option>
							<option value="Asia/Kolkata"         <#if 'Asia/Kolkata'==model.timeZone>selected</#if>>(GMT+05:30) India Standard Time (Asia/Kolkata)</option>
							<option value="Asia/Baku"            <#if 'Asia/Baku'==model.timeZone>selected</#if>>(GMT+05:00) Azerbaijan Summer Time (Asia/Baku)</option>
							<option value="Asia/Karachi"         <#if 'Asia/Karachi'==model.timeZone>selected</#if>>(GMT+05:00) Pakistan Time (Asia/Karachi)</option>
							<option value="Asia/Tashkent"        <#if 'Asia/Tashkent'==model.timeZone>selected</#if>>(GMT+05:00) Uzbekistan Time (Asia/Tashkent)</option>
							<option value="Asia/Kabul"           <#if 'Asia/Kabul'==model.timeZone>selected</#if>>(GMT+04:30) Afghanistan Time (Asia/Kabul)</option>
							<option value="Asia/Dubai"           <#if 'Asia/Dubai'==model.timeZone>selected</#if>>(GMT+04:00) Gulf Standard Time (Asia/Dubai)</option>
							<option value="Asia/Tbilisi"         <#if 'Asia/Tbilisi'==model.timeZone>selected</#if>>(GMT+04:00) Georgia Time (Asia/Tbilisi)</option>
							<option value="Asia/Yerevan"         <#if 'Asia/Yerevan'==model.timeZone>selected</#if>>(GMT+04:00) Armenia Time (Asia/Yerevan)</option>
							<option value="Europe/Moscow"        <#if 'Europe/Moscow'==model.timeZone>selected</#if>>(GMT+04:00) Moscow Standard Time (Europe/Moscow)</option>
							<option value="Asia/Tehran"          <#if 'Asia/Tehran'==model.timeZone>selected</#if>>(GMT+03:30) Iran Standard Time (Asia/Tehran)</option>
							<option value="Africa/Nairobi"       <#if 'Africa/Nairobi'==model.timeZone>selected</#if>>(GMT+03:00) East Africa Time (Africa/Nairobi)</option>
							<option value="Asia/Baghdad"         <#if 'Asia/Baghdad'==model.timeZone>selected</#if>>(GMT+03:00) Arabian Standard Time (Asia/Baghdad)</option>
							<option value="Asia/Beirut"          <#if 'Asia/Beirut'==model.timeZone>selected</#if>>(GMT+03:00) Eastern European Summer Time (Asia/Beirut)</option>
							<option value="Asia/Jerusalem"       <#if 'Asia/Jerusalem'==model.timeZone>selected</#if>>(GMT+03:00) Israel Daylight Time (Asia/Jerusalem)</option>
							<option value="Asia/Kuwait"          <#if 'Asia/Kuwait'==model.timeZone>selected</#if>>(GMT+03:00) Arabian Standard Time (Asia/Kuwait)</option>
							<option value="Asia/Riyadh"          <#if 'Asia/Riyadh'==model.timeZone>selected</#if>>(GMT+03:00) Arabian Standard Time (Asia/Riyadh)</option>
							<option value="Europe/Athens"        <#if 'Europe/Athens'==model.timeZone>selected</#if>>(GMT+03:00) Eastern European Summer Time (Europe/Athens)</option>
							<option value="Europe/Bucharest"     <#if 'Europe/Bucharest'==model.timeZone>selected</#if>>(GMT+03:00) Eastern European Summer Time (Europe/Bucharest)</option>
							<option value="Europe/Helsinki"      <#if 'Europe/Helsinki'==model.timeZone>selected</#if>>(GMT+03:00) Eastern European Summer Time (Europe/Helsinki)</option>
							<option value="Europe/Istanbul"      <#if 'Europe/Istanbul'==model.timeZone>selected</#if>>(GMT+03:00) Eastern European Summer Time (Europe/Istanbul)</option>
							<option value="Europe/Minsk"         <#if 'Europe/Minsk'==model.timeZone>selected</#if>>(GMT+03:00) Further-eastern European Time (Europe/Minsk)</option>
							<option value="Africa/Cairo"         <#if 'Africa/Cairo'==model.timeZone>selected</#if>>(GMT+02:00) Eastern European Time (Africa/Cairo)</option>
							<option value="Africa/Johannesburg"  <#if 'Africa/Johannesburg'==model.timeZone>selected</#if>>(GMT+02:00) South Africa Standard Time (Africa/Johannesburg)</option>
							<option value="Europe/Amsterdam"     <#if 'Europe/Amsterdam'==model.timeZone>selected</#if>>(GMT+02:00) Central European Summer Time (Europe/Amsterdam)</option>
							<option value="Europe/Berlin"        <#if 'Europe/Berlin'==model.timeZone>selected</#if>>(GMT+02:00) Central European Summer Time (Europe/Berlin)</option>
							<option value="Europe/Brussels"      <#if 'Europe/Brussels'==model.timeZone>selected</#if>>(GMT+02:00) Central European Summer Time (Europe/Brussels)</option>
							<option value="Europe/Paris"         <#if 'Europe/Paris'==model.timeZone>selected</#if>>(GMT+02:00) Central European Summer Time (Europe/Paris)</option>
							<option value="Europe/Prague"        <#if 'Europe/Prague'==model.timeZone>selected</#if>>(GMT+02:00) Central European Summer Time (Europe/Prague)</option>
							<option value="Europe/Rome"          <#if 'Europe/Rome'==model.timeZone>selected</#if>>(GMT+02:00) Central European Summer Time (Europe/Rome)</option>
							<option value="Africa/Algiers"       <#if 'Africa/Algiers'==model.timeZone>selected</#if>>(GMT+01:00) Central European Time (Africa/Algiers)</option>
							<option value="Europe/Dublin"        <#if 'Europe/Dublin'==model.timeZone>selected</#if>>(GMT+01:00) Irish Summer Time (Europe/Dublin)</option>
							<option value="Europe/Lisbon"        <#if 'Europe/Lisbon'==model.timeZone>selected</#if>>(GMT+01:00) Western European Summer Time (Europe/Lisbon)</option>
							<option value="Europe/London"        <#if 'Europe/London'==model.timeZone>selected</#if>>(GMT+01:00) British Summer Time (Europe/London)</option>
							<option value="Africa/Casablanca"    <#if 'Africa/Casablanca'==model.timeZone>selected</#if>>(GMT+00:00) Western European Time (Africa/Casablanca)</option>
							<option value="America/Scoresbysund" <#if 'America/Scoresbysund'==model.timeZone>selected</#if>>(GMT+00:00) East Greenland Summer Time (America/Scoresbysund)</option>
							<option value="Atlantic/Azores"      <#if 'Atlantic/Azores'==model.timeZone>selected</#if>>(GMT+00:00) Azores Summer Time (Atlantic/Azores)</option>
							<option value="GMT"                  <#if 'GMT'==model.timeZone>selected</#if>>(GMT+00:00) Greenwich Mean Time (GMT)</option>
							<option value="Atlantic/Cape_Verde"  <#if 'Atlantic/Cape_Verde'==model.timeZone>selected</#if>>(GMT-01:00) Cape Verde Time (Atlantic/Cape_Verde)</option>
							<option value="Atlantic/South_Georgia" <#if 'Atlantic/South_Georgia'==model.timeZone>selected</#if>>(GMT-02:00) South Georgia Time (Atlantic/South_Georgia)</option>
							<option value="America/St_Johns"      <#if 'America/St_Johns'==model.timeZone>selected</#if>>(GMT-02:30) Newfoundland Daylight Time (America/St_Johns)</option>
							<option value="America/Argentina/Buenos_Aires" <#if 'America/Argentina/Buenos_Aires'==model.timeZone>selected</#if>>(GMT-03:00) Argentina Time (America/Argentina/Buenos_Aires)</option>
							<option value="America/Halifax"      <#if 'America/Halifax'==model.timeZone>selected</#if>>(GMT-03:00) Atlantic Daylight Time (America/Halifax)</option>
							<option value="America/Santiago"     <#if 'America/Santiago'==model.timeZone>selected</#if>>(GMT-03:00) Chile Summer Time (America/Santiago)</option>
							<option value="America/Sao_Paulo"    <#if 'America/Sao_Paulo'==model.timeZone>selected</#if>>(GMT-03:00) Brasilia Time (America/Sao_Paulo)</option>
							<option value="Atlantic/Bermuda"     <#if 'Atlantic/Bermuda'==model.timeZone>selected</#if>>(GMT-03:00) Atlantic Daylight Time (Atlantic/Bermuda)</option>
							<option value="America/Indiana/Indianapolis" <#if 'America/Indiana/Indianapolis'==model.timeZone>selected</#if>>(GMT-04:00) Eastern Daylight Time (America/Indiana/Indianapolis)</option>
							<option value="America/New_York"     <#if 'America/New_York'==model.timeZone>selected</#if>>(GMT-04:00) Eastern Daylight Time (America/New_York)</option>
							<option value="America/Puerto_Rico"  <#if 'America/Puerto_Rico'==model.timeZone>selected</#if>>(GMT-04:00) Atlantic Standard Time (America/Puerto_Rico)</option>
							<option value="America/Caracas"      <#if 'America/Caracas'==model.timeZone>selected</#if>>(GMT-04:30) Venezuela Time (America/Caracas)</option>
							<option value="America/Bogota"       <#if 'America/Bogota'==model.timeZone>selected</#if>>(GMT-05:00) Colombia Time (America/Bogota)</option>
							<option value="America/Chicago"      <#if 'America/Chicago'==model.timeZone>selected</#if>>(GMT-05:00) Central Daylight Time (America/Chicago)</option>
							<option value="America/Lima"         <#if 'America/Lima'==model.timeZone>selected</#if>>(GMT-05:00) Peru Time (America/Lima)</option>
							<option value="America/Mexico_City"  <#if 'America/Mexico_City'==model.timeZone>selected</#if>>(GMT-05:00) Central Daylight Time (America/Mexico_City)</option>
							<option value="America/Panama"       <#if 'America/Panama'==model.timeZone>selected</#if>>(GMT-05:00) Eastern Standard Time (America/Panama)</option>
							<option value="America/Denver"       <#if 'America/Denver'==model.timeZone>selected</#if>>(GMT-06:00) Mountain Daylight Time (America/Denver)</option>
							<option value="America/El_Salvador"  <#if 'America/El_Salvador'==model.timeZone>selected</#if>>(GMT-06:00) Central Standard Time (America/El_Salvador)</option>
							<option value="America/Mazatlan"     <#if 'America/Mazatlan'==model.timeZone>selected</#if>>(GMT-06:00) Mountain Daylight Time (America/Mazatlan)</option>
							<option value="America/Los_Angeles"  <#if 'America/Los_Angeles'==model.timeZone>selected</#if>>(GMT-07:00) Pacific Daylight Time (America/Los_Angeles)</option>
							<option value="America/Phoenix"      <#if 'America/Phoenix'==model.timeZone>selected</#if>>(GMT-07:00) Mountain Standard Time (America/Phoenix)</option>
							<option value="America/Tijuana"      <#if 'America/Tijuana'==model.timeZone>selected</#if>>(GMT-07:00) Pacific Daylight Time (America/Tijuana)</option>
							<option value="America/Anchorage"    <#if 'America/Anchorage'==model.timeZone>selected</#if>>(GMT-08:00) Alaska Daylight Time (America/Anchorage)</option>
							<option value="Pacific/Pitcairn"     <#if 'Pacific/Pitcairn'==model.timeZone>selected</#if>>(GMT-08:00) Pitcairn Time (Pacific/Pitcairn)</option>
							<option value="America/Adak"         <#if 'America/Adak'==model.timeZone>selected</#if>>(GMT-09:00) Hawaii-Aleutian Standard Time (America/Adak)</option>
							<option value="Pacific/Gambier"      <#if 'Pacific/Gambier'==model.timeZone>selected</#if>>(GMT-09:00) Gambier Time (Pacific/Gambier)</option>
							<option value="Pacific/Marquesas"    <#if 'Pacific/Marquesas'==model.timeZone>selected</#if>>(GMT-09:30) Marquesas Time (Pacific/Marquesas)</option>
							<option value="Pacific/Honolulu"     <#if 'Pacific/Honolulu'==model.timeZone>selected</#if>>(GMT-10:00) Hawaii-Aleutian Standard Time (Pacific/Honolulu)</option>
							<option value="Pacific/Niue"         <#if 'Pacific/Niue'==model.timeZone>selected</#if>>(GMT-11:00) Niue Time (Pacific/Niue)</option>
							<option value="Pacific/Pago_Pago"    <#if 'Pacific/Pago_Pago'==model.timeZone>selected</#if>>(GMT-11:00) Samoa Standard Time (Pacific/Pago_Pago)</option>
							</select>

					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.locale" />：</th>
					<td>
						<select class="form-control"  name="locale" id="locale">
							
							<option value="en_US"  <#if 'en_US'==model.locale>selected</#if>  >English</option>
							<option value="nl_NL"  <#if 'nl_NL'==model.locale>selected</#if>  >Dutch</option>
							<option value="fr"     <#if 'fr'==model.locale>selected</#if>  >French</option>
							<option value="de"     <#if 'de'==model.locale>selected</#if>  >German</option>
							<option value="it"     <#if 'it'==model.locale>selected</#if>  >Italian</option>
							<option value="es"     <#if 'es'==model.locale>selected</#if>  >Spanish</option>
							<option value="sv"     <#if 'sv'==model.locale>selected</#if>  >Swedish</option>
							<option value="pt_BR"  <#if 'pt_BR'==model.locale>selected</#if>  >Portuguese  (Brazilian)</option>
							<option value="ja"     <#if 'ja'==model.locale>selected</#if>  >Japanese</option>
							<option value="zh_CN"  <#if 'zh_CN'==model.locale>selected</#if>  >Chinese (Simplified)</option>
							<option value="zh_TW"  <#if 'zh_TW'==model.locale>selected</#if>  >Chinese (Traditional)</option>
							<option value="ko"     <#if 'ko'==model.locale>selected</#if>  >Korean</option>
							<option value="th"     <#if 'th'==model.locale>selected</#if>  >Thai</option>
							<option value="fi"     <#if 'fi'==model.locale>selected</#if>  >Finnish</option>
							<option value="ru"     <#if 'ru'==model.locale>selected</#if>  >Russian</option>
						</select>
					</td>
				</tr>
				</tbody>
			  </table>
</div>
<div class="col-md-6">
		
  	      	<!--table-->
			  <table class="table table-bordered">
				<tbody>				
				<tr>
					<th ><@locale code="userinfo.picture" />：</th>
					<td>
						<img id="picture" width="150px" height="215px" 
						<#if  model.picture? default("")=="" >
							src="<@base/>/static/images/uploadimage.jpg" />
						<#else>
							 src="<@base/>/image/${model.id}" />
						</#if>
						<input  class="form-control"  type="file" id="pictureFile" name="pictureFile" style="display:none" />
						
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.employeeNumber" />：</th>
					<td>
						<input class="form-control"  readonly type="text" id="employeeNumber" name="employeeNumber"  title="" value="${model.employeeNumber!}"/>
					
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.windowsAccount" />：</th>
					<td>
						<input class="form-control"  type="text" id="windowsAccount" name="windowsAccount"  title="" value="${model.windowsAccount!}"/>
						
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.organization" />：</th>
					<td>
						<input class="form-control"  type="text" id="organization" name="organization"  title="" value="${model.organization!}"/>
						
					</td>
					
					
				</tr>
				<tr>
					<th><@locale code="userinfo.division" />：</th>
					<td>
						<input class="form-control"  type="text" id="division" name="division"  title="" value="${model.division!}"/>
						
					</td>
					
				</tr>
				<tr>
					<th><@locale code="userinfo.department" />：</th>
					<td>
						<input class="form-control"  type="hidden" id="departmentId" name="departmentId"  title="" value="${model.departmentId!}"/>
						<input class="form-control"  type="text" id="department" name="department"  title="" value="${model.department!}"/>
						
					</td>
					
				</tr>
				<tr>
					<th><@locale code="userinfo.costCenter" />：</th>
					<td>
						<input class="form-control"  type="text" id="costCenter" name="costCenter"  title="" value="${model.costCenter!}"/>
						
					</td>
					
				</tr>
				<tr>
					<th><@locale code="userinfo.jobTitle" />：</th>
					<td>
						<input class="form-control"  type="text" id="jobTitle" name="jobTitle"  title="" value="${model.jobTitle!}"/>
						
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.jobTitle" />：</th>
					<td>
						<input class="form-control"  type="text" id="jobTitle" name="jobTitle"  title="" value="${model.jobTitle!}"/>
						
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.manager" />：</th>
					<td>
						<input class="form-control"  type="hidden" id="managerId" name="managerId"  title="" value="${model.managerId!}"/>
						<input class="form-control"  type="text" id="manager" name="manager"  title="" value="${model.manager!}"/>
						
					</td>
					
				</tr>
				<tr>
					<th><@locale code="userinfo.manager" />：</th>
					<td>
						<input class="form-control"  type="hidden" id="managerId" name="managerId"  title="" value="${model.managerId!}"/>
						<input class="form-control"  type="text" id="manager" name="manager"  title="" value="${model.manager!}"/>
						
					</td>
					
				</tr>
				<tr>
					<th><@locale code="userinfo.entryDate" />：</th>
					<td>
						<input class="form-control"  type="text" class="datepicker"  id="entryDate" name="entryDate"  title="" value="${model.entryDate!}"/>
						
					</td>
					
				</tr>
				<tr>
					
					<th><@locale code="userinfo.quitDate" />：</th>
					<td>
						<input class="form-control"   type="text" class="datepicker"  id="quitDate" name="quitDate"  title="" value="${model.quitDate!}"/>
						
					</td>
				</tr>
				</tbody>
			  </table>
				
			  
	</div>	
</div>	

<div class="row">
	<div class="col-md-6">
		<table class="table table-bordered">
		<tbody>		
		<tr>
					<td colspan="2">单位信息
					</td>
					
				</tr>
				<tr>
					<th><@locale code="userinfo.workCountry" />：</th>
					<td>
						<select  class="form-control"  id="workCountry" name="workCountry">
							<option value="AFG"  <#if 'AFG'==model.workCountry>selected</#if>>Afghanistan</option>
							<option value="ALA"  <#if 'ALA'==model.workCountry>selected</#if>>Åland Islands</option>
							<option value="ALB"  <#if 'ALB'==model.workCountry>selected</#if>>Albania</option>
							<option value="DZA"  <#if 'DZA'==model.workCountry>selected</#if>>Algeria</option>
							<option value="ASM"  <#if 'ASM'==model.workCountry>selected</#if>>American Samoa</option>
							<option value="AND"  <#if 'AND'==model.workCountry>selected</#if>>Andorra</option>
							<option value="AGO"  <#if 'AGO'==model.workCountry>selected</#if>>Angola</option>
							<option value="AIA"  <#if 'AIA'==model.workCountry>selected</#if>>Anguilla</option>
							<option value="ATA"  <#if 'ATA'==model.workCountry>selected</#if>>Antarctica</option>
							<option value="ATG"  <#if 'ATG'==model.workCountry>selected</#if>>Antigua and Barbuda</option>
							<option value="ARG"  <#if 'ARG'==model.workCountry>selected</#if>>Argentina</option>
							<option value="ARM"  <#if 'ARM'==model.workCountry>selected</#if>>Armenia</option>
							<option value="ABW"  <#if 'ABW'==model.workCountry>selected</#if>>Aruba</option>
							<option value="AUS"  <#if 'AUS'==model.workCountry>selected</#if>>Australia</option>
							<option value="AUT"  <#if 'AUT'==model.workCountry>selected</#if>>Austria</option>
							<option value="AZE"  <#if 'AZE'==model.workCountry>selected</#if>>Azerbaijan</option>
							<option value="BHS"  <#if 'BHS'==model.workCountry>selected</#if>>Bahamas</option>
							<option value="BHR"  <#if 'BHR'==model.workCountry>selected</#if>>Bahrain</option>
							<option value="BGD"  <#if 'BGD'==model.workCountry>selected</#if>>Bangladesh</option>
							<option value="BRB"  <#if 'BRB'==model.workCountry>selected</#if>>Barbados</option>
							<option value="BLR"  <#if 'BLR'==model.workCountry>selected</#if>>Belarus</option>
							<option value="BEL"  <#if 'BEL'==model.workCountry>selected</#if>>Belgium</option>
							<option value="BLZ"  <#if 'BLZ'==model.workCountry>selected</#if>>Belize</option>
							<option value="BEN"  <#if 'BEN'==model.workCountry>selected</#if>>Benin</option>
							<option value="BMU"  <#if 'BMU'==model.workCountry>selected</#if>>Bermuda</option>
							<option value="BTN"  <#if 'BTN'==model.workCountry>selected</#if>>Bhutan</option>
							<option value="BOL"  <#if 'BOL'==model.workCountry>selected</#if>>Bolivia, Plurinational State of</option>
							<option value="BES"  <#if 'BES'==model.workCountry>selected</#if>>Bonaire, Sint Eustatius and Saba</option>
							<option value="BIH"  <#if 'BIH'==model.workCountry>selected</#if>>Bosnia and Herzegovina</option>
							<option value="BWA"  <#if 'BWA'==model.workCountry>selected</#if>>Botswana</option>
							<option value="BVT"  <#if 'BVT'==model.workCountry>selected</#if>>Bouvet Island</option>
							<option value="BRA"  <#if 'BRA'==model.workCountry>selected</#if>>Brazil</option>
							<option value="IOT"  <#if 'IOT'==model.workCountry>selected</#if>>British Indian Ocean Territory</option>
							<option value="BRN"  <#if 'BRN'==model.workCountry>selected</#if>>Brunei Darussalam</option>
							<option value="BGR"  <#if 'BGR'==model.workCountry>selected</#if>>Bulgaria</option>
							<option value="BFA"  <#if 'BFA'==model.workCountry>selected</#if>>Burkina Faso</option>
							<option value="BDI"  <#if 'BDI'==model.workCountry>selected</#if>>Burundi</option>
							<option value="KHM"  <#if 'KHM'==model.workCountry>selected</#if>>Cambodia</option>
							<option value="CMR"  <#if 'CMR'==model.workCountry>selected</#if>>Cameroon</option>
							<option value="CAN"  <#if 'CAN'==model.workCountry>selected</#if>>Canada</option>
							<option value="CPV"  <#if 'CPV'==model.workCountry>selected</#if>>Cape Verde</option>
							<option value="CYM"  <#if 'CYM'==model.workCountry>selected</#if>>Cayman Islands</option>
							<option value="CAF"  <#if 'CAF'==model.workCountry>selected</#if>>Central African Republic</option>
							<option value="TCD"  <#if 'TCD'==model.workCountry>selected</#if>>Chad</option>
							<option value="CHL"  <#if 'CHL'==model.workCountry>selected</#if>>Chile</option>
							<option value="CHN"  <#if 'CHN'==model.workCountry>selected</#if>>China</option>
							<option value="CXR"  <#if 'CXR'==model.workCountry>selected</#if>>Christmas Island</option>
							<option value="CCK"  <#if 'CCK'==model.workCountry>selected</#if>>Cocos (Keeling) Islands</option>
							<option value="COL"  <#if 'COL'==model.workCountry>selected</#if>>Colombia</option>
							<option value="COM"  <#if 'COM'==model.workCountry>selected</#if>>Comoros</option>
							<option value="COG"  <#if 'COG'==model.workCountry>selected</#if>>Congo</option>
							<option value="COD"  <#if 'COD'==model.workCountry>selected</#if>>Congo, the Democratic Republic of the</option>
							<option value="COK"  <#if 'COK'==model.workCountry>selected</#if>>Cook Islands</option>
							<option value="CRI"  <#if 'CRI'==model.workCountry>selected</#if>>Costa Rica</option>
							<option value="CIV"  <#if 'CIV'==model.workCountry>selected</#if>>Côte d'Ivoire</option>
							<option value="HRV"  <#if 'HRV'==model.workCountry>selected</#if>>Croatia</option>
							<option value="CUB"  <#if 'CUB'==model.workCountry>selected</#if>>Cuba</option>
							<option value="CUW"  <#if 'CUW'==model.workCountry>selected</#if>>Curaçao</option>
							<option value="CYP"  <#if 'CYP'==model.workCountry>selected</#if>>Cyprus</option>
							<option value="CZE"  <#if 'CZE'==model.workCountry>selected</#if>>Czech Republic</option>
							<option value="DNK"  <#if 'DNK'==model.workCountry>selected</#if>>Denmark</option>
							<option value="DJI"  <#if 'DJI'==model.workCountry>selected</#if>>Djibouti</option>
							<option value="DMA"  <#if 'DMA'==model.workCountry>selected</#if>>Dominica</option>
							<option value="DOM"  <#if 'DOM'==model.workCountry>selected</#if>>Dominican Republic</option>
							<option value="ECU"  <#if 'ECU'==model.workCountry>selected</#if>>Ecuador</option>
							<option value="EGY"  <#if 'EGY'==model.workCountry>selected</#if>>Egypt</option>
							<option value="SLV"  <#if 'SLV'==model.workCountry>selected</#if>>El Salvador</option>
							<option value="GNQ"  <#if 'GNQ'==model.workCountry>selected</#if>>Equatorial Guinea</option>
							<option value="ERI"  <#if 'ERI'==model.workCountry>selected</#if>>Eritrea</option>
							<option value="EST"  <#if 'EST'==model.workCountry>selected</#if>>Estonia</option>
							<option value="ETH"  <#if 'ETH'==model.workCountry>selected</#if>>Ethiopia</option>
							<option value="FLK"  <#if 'FLK'==model.workCountry>selected</#if>>Falkland Islands (Malvinas)</option>
							<option value="FRO"  <#if 'FRO'==model.workCountry>selected</#if>>Faroe Islands</option>
							<option value="FJI"  <#if 'FJI'==model.workCountry>selected</#if>>Fiji</option>
							<option value="FIN"  <#if 'FIN'==model.workCountry>selected</#if>>Finland</option>
							<option value="FRA"  <#if 'FRA'==model.workCountry>selected</#if>>France</option>
							<option value="GUF"  <#if 'GUF'==model.workCountry>selected</#if>>French Guiana</option>
							<option value="PYF"  <#if 'PYF'==model.workCountry>selected</#if>>French Polynesia</option>
							<option value="ATF"  <#if 'ATF'==model.workCountry>selected</#if>>French Southern Territories</option>
							<option value="GAB"  <#if 'GAB'==model.workCountry>selected</#if>>Gabon</option>
							<option value="GMB"  <#if 'GMB'==model.workCountry>selected</#if>>Gambia</option>
							<option value="GEO"  <#if 'GEO'==model.workCountry>selected</#if>>Georgia</option>
							<option value="DEU"  <#if 'DEU'==model.workCountry>selected</#if>>Germany</option>
							<option value="GHA"  <#if 'GHA'==model.workCountry>selected</#if>>Ghana</option>
							<option value="GIB"  <#if 'GIB'==model.workCountry>selected</#if>>Gibraltar</option>
							<option value="GRC"  <#if 'GRC'==model.workCountry>selected</#if>>Greece</option>
							<option value="GRL"  <#if 'GRL'==model.workCountry>selected</#if>>Greenland</option>
							<option value="GRD"  <#if 'GRD'==model.workCountry>selected</#if>>Grenada</option>
							<option value="GLP"  <#if 'GLP'==model.workCountry>selected</#if>>Guadeloupe</option>
							<option value="GUM"  <#if 'GUM'==model.workCountry>selected</#if>>Guam</option>
							<option value="GTM"  <#if 'GTM'==model.workCountry>selected</#if>>Guatemala</option>
							<option value="GGY"  <#if 'GGY'==model.workCountry>selected</#if>>Guernsey</option>
							<option value="GIN"  <#if 'GIN'==model.workCountry>selected</#if>>Guinea</option>
							<option value="GNB"  <#if 'GNB'==model.workCountry>selected</#if>>Guinea-Bissau</option>
							<option value="GUY"  <#if 'GUY'==model.workCountry>selected</#if>>Guyana</option>
							<option value="HTI"  <#if 'HTI'==model.workCountry>selected</#if>>Haiti</option>
							<option value="HMD"  <#if 'HMD'==model.workCountry>selected</#if>>Heard Island and McDonald Islands</option>
							<option value="VAT"  <#if 'VAT'==model.workCountry>selected</#if>>Holy See (Vatican City State)</option>
							<option value="HND"  <#if 'HND'==model.workCountry>selected</#if>>Honduras</option>
							<option value="HKG"  <#if 'HKG'==model.workCountry>selected</#if>>Hong Kong</option>
							<option value="HUN"  <#if 'HUN'==model.workCountry>selected</#if>>Hungary</option>
							<option value="ISL"  <#if 'ISL'==model.workCountry>selected</#if>>Iceland</option>
							<option value="IND"  <#if 'IND'==model.workCountry>selected</#if>>India</option>
							<option value="IDN"  <#if 'IDN'==model.workCountry>selected</#if>>Indonesia</option>
							<option value="IRN"  <#if 'IRN'==model.workCountry>selected</#if>>Iran, Islamic Republic of</option>
							<option value="IRQ"  <#if 'IRQ'==model.workCountry>selected</#if>>Iraq</option>
							<option value="IRL"  <#if 'IRL'==model.workCountry>selected</#if>>Ireland</option>
							<option value="IMN"  <#if 'IMN'==model.workCountry>selected</#if>>Isle of Man</option>
							<option value="ISR"  <#if 'ISR'==model.workCountry>selected</#if>>Israel</option>
							<option value="ITA"  <#if 'ITA'==model.workCountry>selected</#if>>Italy</option>
							<option value="JAM"  <#if 'JAM'==model.workCountry>selected</#if>>Jamaica</option>
							<option value="JPN"  <#if 'JPN'==model.workCountry>selected</#if>>Japan</option>
							<option value="JEY"  <#if 'JEY'==model.workCountry>selected</#if>>Jersey</option>
							<option value="JOR"  <#if 'JOR'==model.workCountry>selected</#if>>Jordan</option>
							<option value="KAZ"  <#if 'KAZ'==model.workCountry>selected</#if>>Kazakhstan</option>
							<option value="KEN"  <#if 'KEN'==model.workCountry>selected</#if>>Kenya</option>
							<option value="KIR"  <#if 'KIR'==model.workCountry>selected</#if>>Kiribati</option>
							<option value="PRK"  <#if 'PRK'==model.workCountry>selected</#if>>Korea, Democratic People's Republic of</option>
							<option value="KOR"  <#if 'KOR'==model.workCountry>selected</#if>>Korea, Republic of</option>
							<option value="KWT"  <#if 'KWT'==model.workCountry>selected</#if>>Kuwait</option>
							<option value="KGZ"  <#if 'KGZ'==model.workCountry>selected</#if>>Kyrgyzstan</option>
							<option value="LAO"  <#if 'LAO'==model.workCountry>selected</#if>>Lao People's Democratic Republic</option>
							<option value="LVA"  <#if 'LVA'==model.workCountry>selected</#if>>Latvia</option>
							<option value="LBN"  <#if 'LBN'==model.workCountry>selected</#if>>Lebanon</option>
							<option value="LSO"  <#if 'LSO'==model.workCountry>selected</#if>>Lesotho</option>
							<option value="LBR"  <#if 'LBR'==model.workCountry>selected</#if>>Liberia</option>
							<option value="LBY"  <#if 'LBY'==model.workCountry>selected</#if>>Libya</option>
							<option value="LIE"  <#if 'LIE'==model.workCountry>selected</#if>>Liechtenstein</option>
							<option value="LTU"  <#if 'LTU'==model.workCountry>selected</#if>>Lithuania</option>
							<option value="LUX"  <#if 'LUX'==model.workCountry>selected</#if>>Luxembourg</option>
							<option value="MAC"  <#if 'MAC'==model.workCountry>selected</#if>>Macao</option>
							<option value="MKD"  <#if 'MKD'==model.workCountry>selected</#if>>Macedonia, the former Yugoslav Republic of</option>
							<option value="MDG"  <#if 'MDG'==model.workCountry>selected</#if>>Madagascar</option>
							<option value="MWI"  <#if 'MWI'==model.workCountry>selected</#if>>Malawi</option>
							<option value="MYS"  <#if 'MYS'==model.workCountry>selected</#if>>Malaysia</option>
							<option value="MDV"  <#if 'MDV'==model.workCountry>selected</#if>>Maldives</option>
							<option value="MLI"  <#if 'MLI'==model.workCountry>selected</#if>>Mali</option>
							<option value="MLT"  <#if 'MLT'==model.workCountry>selected</#if>>Malta</option>
							<option value="MHL"  <#if 'MHL'==model.workCountry>selected</#if>>Marshall Islands</option>
							<option value="MTQ"  <#if 'MTQ'==model.workCountry>selected</#if>>Martinique</option>
							<option value="MRT"  <#if 'MRT'==model.workCountry>selected</#if>>Mauritania</option>
							<option value="MUS"  <#if 'MUS'==model.workCountry>selected</#if>>Mauritius</option>
							<option value="MYT"  <#if 'MYT'==model.workCountry>selected</#if>>Mayotte</option>
							<option value="MEX"  <#if 'MEX'==model.workCountry>selected</#if>>Mexico</option>
							<option value="FSM"  <#if 'FSM'==model.workCountry>selected</#if>>Micronesia, Federated States of</option>
							<option value="MDA"  <#if 'MDA'==model.workCountry>selected</#if>>Moldova, Republic of</option>
							<option value="MCO"  <#if 'MCO'==model.workCountry>selected</#if>>Monaco</option>
							<option value="MNG"  <#if 'MNG'==model.workCountry>selected</#if>>Mongolia</option>
							<option value="MNE"  <#if 'MNE'==model.workCountry>selected</#if>>Montenegro</option>
							<option value="MSR"  <#if 'MSR'==model.workCountry>selected</#if>>Montserrat</option>
							<option value="MAR"  <#if 'MAR'==model.workCountry>selected</#if>>Morocco</option>
							<option value="MOZ"  <#if 'MOZ'==model.workCountry>selected</#if>>Mozambique</option>
							<option value="MMR"  <#if 'MMR'==model.workCountry>selected</#if>>Myanmar</option>
							<option value="NAM"  <#if 'NAM'==model.workCountry>selected</#if>>Namibia</option>
							<option value="NRU"  <#if 'NRU'==model.workCountry>selected</#if>>Nauru</option>
							<option value="NPL"  <#if 'NPL'==model.workCountry>selected</#if>>Nepal</option>
							<option value="NLD"  <#if 'NLD'==model.workCountry>selected</#if>>Netherlands</option>
							<option value="NCL"  <#if 'NCL'==model.workCountry>selected</#if>>New Caledonia</option>
							<option value="NZL"  <#if 'NZL'==model.workCountry>selected</#if>>New Zealand</option>
							<option value="NIC"  <#if 'NIC'==model.workCountry>selected</#if>>Nicaragua</option>
							<option value="NER"  <#if 'NER'==model.workCountry>selected</#if>>Niger</option>
							<option value="NGA"  <#if 'NGA'==model.workCountry>selected</#if>>Nigeria</option>
							<option value="NIU"  <#if 'NIU'==model.workCountry>selected</#if>>Niue</option>
							<option value="NFK"  <#if 'NFK'==model.workCountry>selected</#if>>Norfolk Island</option>
							<option value="MNP"  <#if 'MNP'==model.workCountry>selected</#if>>Northern Mariana Islands</option>
							<option value="NOR"  <#if 'NOR'==model.workCountry>selected</#if>>Norway</option>
							<option value="OMN"  <#if 'OMN'==model.workCountry>selected</#if>>Oman</option>
							<option value="PAK"  <#if 'PAK'==model.workCountry>selected</#if>>Pakistan</option>
							<option value="PLW"  <#if 'PLW'==model.workCountry>selected</#if>>Palau</option>
							<option value="PSE"  <#if 'PSE'==model.workCountry>selected</#if>>Palestinian Territory, Occupied</option>
							<option value="PAN"  <#if 'PAN'==model.workCountry>selected</#if>>Panama</option>
							<option value="PNG"  <#if 'PNG'==model.workCountry>selected</#if>>Papua New Guinea</option>
							<option value="PRY"  <#if 'PRY'==model.workCountry>selected</#if>>Paraguay</option>
							<option value="PER"  <#if 'PER'==model.workCountry>selected</#if>>Peru</option>
							<option value="PHL"  <#if 'PHL'==model.workCountry>selected</#if>>Philippines</option>
							<option value="PCN"  <#if 'PCN'==model.workCountry>selected</#if>>Pitcairn</option>
							<option value="POL"  <#if 'POL'==model.workCountry>selected</#if>>Poland</option>
							<option value="PRT"  <#if 'PRT'==model.workCountry>selected</#if>>Portugal</option>
							<option value="PRI"  <#if 'PRI'==model.workCountry>selected</#if>>Puerto Rico</option>
							<option value="QAT"  <#if 'QAT'==model.workCountry>selected</#if>>Qatar</option>
							<option value="REU"  <#if 'REU'==model.workCountry>selected</#if>>Réunion</option>
							<option value="ROU"  <#if 'ROU'==model.workCountry>selected</#if>>Romania</option>
							<option value="RUS"  <#if 'RUS'==model.workCountry>selected</#if>>Russian Federation</option>
							<option value="RWA"  <#if 'RWA'==model.workCountry>selected</#if>>Rwanda</option>
							<option value="BLM"  <#if 'BLM'==model.workCountry>selected</#if>>Saint Barthélemy</option>
							<option value="SHN"  <#if 'SHN'==model.workCountry>selected</#if>>Saint Helena, Ascension and Tristan da Cunha</option>
							<option value="KNA"  <#if 'KNA'==model.workCountry>selected</#if>>Saint Kitts and Nevis</option>
							<option value="LCA"  <#if 'LCA'==model.workCountry>selected</#if>>Saint Lucia</option>
							<option value="MAF"  <#if 'MAF'==model.workCountry>selected</#if>>Saint Martin (French part)</option>
							<option value="SPM"  <#if 'SPM'==model.workCountry>selected</#if>>Saint Pierre and Miquelon</option>
							<option value="VCT"  <#if 'VCT'==model.workCountry>selected</#if>>Saint Vincent and the Grenadines</option>
							<option value="WSM"  <#if 'WSM'==model.workCountry>selected</#if>>Samoa</option>
							<option value="SMR"  <#if 'SMR'==model.workCountry>selected</#if>>San Marino</option>
							<option value="STP"  <#if 'STP'==model.workCountry>selected</#if>>Sao Tome and Principe</option>
							<option value="SAU"  <#if 'SAU'==model.workCountry>selected</#if>>Saudi Arabia</option>
							<option value="SEN"  <#if 'SEN'==model.workCountry>selected</#if>>Senegal</option>
							<option value="SRB"  <#if 'SRB'==model.workCountry>selected</#if>>Serbia</option>
							<option value="SYC"  <#if 'SYC'==model.workCountry>selected</#if>>Seychelles</option>
							<option value="SLE"  <#if 'SLE'==model.workCountry>selected</#if>>Sierra Leone</option>
							<option value="SGP"  <#if 'SGP'==model.workCountry>selected</#if>>Singapore</option>
							<option value="SXM"  <#if 'SXM'==model.workCountry>selected</#if>>Sint Maarten (Dutch part)</option>
							<option value="SVK"  <#if 'SVK'==model.workCountry>selected</#if>>Slovakia</option>
							<option value="SVN"  <#if 'SVN'==model.workCountry>selected</#if>>Slovenia</option>
							<option value="SLB"  <#if 'SLB'==model.workCountry>selected</#if>>Solomon Islands</option>
							<option value="SOM"  <#if 'SOM'==model.workCountry>selected</#if>>Somalia</option>
							<option value="ZAF"  <#if 'ZAF'==model.workCountry>selected</#if>>South Africa</option>
							<option value="SGS"  <#if 'SGS'==model.workCountry>selected</#if>>South Georgia and the South Sandwich Islands</option>
							<option value="SSD"  <#if 'SSD'==model.workCountry>selected</#if>>South Sudan</option>
							<option value="ESP"  <#if 'ESP'==model.workCountry>selected</#if>>Spain</option>
							<option value="LKA"  <#if 'LKA'==model.workCountry>selected</#if>>Sri Lanka</option>
							<option value="SDN"  <#if 'SDN'==model.workCountry>selected</#if>>Sudan</option>
							<option value="SUR"  <#if 'SUR'==model.workCountry>selected</#if>>Suriname</option>
							<option value="SJM"  <#if 'SJM'==model.workCountry>selected</#if>>Svalbard and Jan Mayen</option>
							<option value="SWZ"  <#if 'SWZ'==model.workCountry>selected</#if>>Swaziland</option>
							<option value="SWE"  <#if 'SWE'==model.workCountry>selected</#if>>Sweden</option>
							<option value="CHE"  <#if 'CHE'==model.workCountry>selected</#if>>Switzerland</option>
							<option value="SYR"  <#if 'SYR'==model.workCountry>selected</#if>>Syrian Arab Republic</option>
							<option value="TWN"  <#if 'TWN'==model.workCountry>selected</#if>>Taiwan, Province of China</option>
							<option value="TJK"  <#if 'TJK'==model.workCountry>selected</#if>>Tajikistan</option>
							<option value="TZA"  <#if 'TZA'==model.workCountry>selected</#if>>Tanzania, United Republic of</option>
							<option value="THA"  <#if 'THA'==model.workCountry>selected</#if>>Thailand</option>
							<option value="TLS"  <#if 'TLS'==model.workCountry>selected</#if>>Timor-Leste</option>
							<option value="TGO"  <#if 'TGO'==model.workCountry>selected</#if>>Togo</option>
							<option value="TKL"  <#if 'TKL'==model.workCountry>selected</#if>>Tokelau</option>
							<option value="TON"  <#if 'TON'==model.workCountry>selected</#if>>Tonga</option>
							<option value="TTO"  <#if 'TTO'==model.workCountry>selected</#if>>Trinidad and Tobago</option>
							<option value="TUN"  <#if 'TUN'==model.workCountry>selected</#if>>Tunisia</option>
							<option value="TUR"  <#if 'TUR'==model.workCountry>selected</#if>>Turkey</option>
							<option value="TKM"  <#if 'TKM'==model.workCountry>selected</#if>>Turkmenistan</option>
							<option value="TCA"  <#if 'TCA'==model.workCountry>selected</#if>>Turks and Caicos Islands</option>
							<option value="TUV"  <#if 'TUV'==model.workCountry>selected</#if>>Tuvalu</option>
							<option value="UGA"  <#if 'UGA'==model.workCountry>selected</#if>>Uganda</option>
							<option value="UKR"  <#if 'UKR'==model.workCountry>selected</#if>>Ukraine</option>
							<option value="ARE"  <#if 'ARE'==model.workCountry>selected</#if>>United Arab Emirates</option>
							<option value="GBR"  <#if 'GBR'==model.workCountry>selected</#if>>United Kingdom</option>
							<option value="USA"  <#if 'USA'==model.workCountry>selected</#if>>United States</option>
							<option value="UMI"  <#if 'UMI'==model.workCountry>selected</#if>>United States Minor Outlying Islands</option>
							<option value="URY"  <#if 'URY'==model.workCountry>selected</#if>>Uruguay</option>
							<option value="UZB"  <#if 'UZB'==model.workCountry>selected</#if>>Uzbekistan</option>
							<option value="VUT"  <#if 'VUT'==model.workCountry>selected</#if>>Vanuatu</option>
							<option value="VEN"  <#if 'VEN'==model.workCountry>selected</#if>>Venezuela, Bolivarian Republic of</option>
							<option value="VNM"  <#if 'VNM'==model.workCountry>selected</#if>>Viet Nam</option>
							<option value="VGB"  <#if 'VGB'==model.workCountry>selected</#if>>Virgin Islands, British</option>
							<option value="VIR"  <#if 'VIR'==model.workCountry>selected</#if>>Virgin Islands, U.S.</option>
							<option value="WLF"  <#if 'WLF'==model.workCountry>selected</#if>>Wallis and Futuna</option>
							<option value="ESH"  <#if 'ESH'==model.workCountry>selected</#if>>Western Sahara</option>
							<option value="YEM"  <#if 'YEM'==model.workCountry>selected</#if>>Yemen</option>
							<option value="ZMB"  <#if 'ZMB'==model.workCountry>selected</#if>>Zambia</option>
							<option value="ZWE"  <#if 'ZWE'==model.workCountry>selected</#if>>Zimbabwe</option>
						</select>
						<label for="workCountry"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.workRegion" />：</th>
					<td>
						<input class="form-control"  type="text" id="workRegion" name="workRegion"  title="" value="${model.workRegion!}"/>
						<label for="province"></label>
					</td>
				</tr>
				<tr>
					
					<th><@locale code="userinfo.workLocality" />：</th>
					<td>
						<input class="form-control"  type="text" id="workLocality" name="workLocality"  title="" value="${model.workLocality!}"/>
						<label for="workLocality"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.workStreetAddress" />：</th>
					<td>
						<input class="form-control"  type="text" id="workStreetAddress" name="workStreetAddress"  title="" value="${model.workStreetAddress!}"/>
						<label for="street"></label>
					</td>
				</tr>
				<tr>
					
					<th><@locale code="userinfo.workPostalCode" />：</th>
					<td>
						<input class="form-control"  type="text" id="workPostalCode" name="workPostalCode"  title="" value="${model.workPostalCode!}"/>
						<label for="workPostalCode"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.workFax" />：</th>
					<td>
						<input class="form-control"  type="text" id="workFax" name="workFax"  title="" value="${model.workFax!}"/>
						<label for="workFax"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.workPhoneNumber" />：</th>
					<td>
						<input class="form-control"  type="text" id="workPhoneNumber" name="workPhoneNumber"  title="" value="${model.workPhoneNumber!}"/>
						<label for="workPhoneNumber"></label>
					</td>
					
				</tr>
				<tr>
					<th><@locale code="userinfo.workEmail" />：</th>
					<td>
						<input class="form-control"  type="text" id="workEmail" name="workEmail"  title="" value="${model.workEmail!}"/>
						<label for="workEmail"></label>
					</td>
					
				</tr>
				</tbody>
			  </table>
	</div>
	<div class="col-md-6">
		<table class="table table-bordered">
		<tbody>		
		<tr>
					<td colspan="2">家庭信息
					</td>
					
				</tr>
				
				<tr>
					<th><@locale code="userinfo.homeCountry" />：</th>
					<td>
						<select  class="form-control"  id="homeCountry" name="homeCountry">
							<option value="AFG"  <#if 'AFG'==model.homeCountry>selected</#if>>Afghanistan</option>
							<option value="ALA"  <#if 'ALA'==model.homeCountry>selected</#if>>Åland Islands</option>
							<option value="ALB"  <#if 'ALB'==model.homeCountry>selected</#if>>Albania</option>
							<option value="DZA"  <#if 'DZA'==model.homeCountry>selected</#if>>Algeria</option>
							<option value="ASM"  <#if 'ASM'==model.homeCountry>selected</#if>>American Samoa</option>
							<option value="AND"  <#if 'AND'==model.homeCountry>selected</#if>>Andorra</option>
							<option value="AGO"  <#if 'AGO'==model.homeCountry>selected</#if>>Angola</option>
							<option value="AIA"  <#if 'AIA'==model.homeCountry>selected</#if>>Anguilla</option>
							<option value="ATA"  <#if 'ATA'==model.homeCountry>selected</#if>>Antarctica</option>
							<option value="ATG"  <#if 'ATG'==model.homeCountry>selected</#if>>Antigua and Barbuda</option>
							<option value="ARG"  <#if 'ARG'==model.homeCountry>selected</#if>>Argentina</option>
							<option value="ARM"  <#if 'ARM'==model.homeCountry>selected</#if>>Armenia</option>
							<option value="ABW"  <#if 'ABW'==model.homeCountry>selected</#if>>Aruba</option>
							<option value="AUS"  <#if 'AUS'==model.homeCountry>selected</#if>>Australia</option>
							<option value="AUT"  <#if 'AUT'==model.homeCountry>selected</#if>>Austria</option>
							<option value="AZE"  <#if 'AZE'==model.homeCountry>selected</#if>>Azerbaijan</option>
							<option value="BHS"  <#if 'BHS'==model.homeCountry>selected</#if>>Bahamas</option>
							<option value="BHR"  <#if 'BHR'==model.homeCountry>selected</#if>>Bahrain</option>
							<option value="BGD"  <#if 'BGD'==model.homeCountry>selected</#if>>Bangladesh</option>
							<option value="BRB"  <#if 'BRB'==model.homeCountry>selected</#if>>Barbados</option>
							<option value="BLR"  <#if 'BLR'==model.homeCountry>selected</#if>>Belarus</option>
							<option value="BEL"  <#if 'BEL'==model.homeCountry>selected</#if>>Belgium</option>
							<option value="BLZ"  <#if 'BLZ'==model.homeCountry>selected</#if>>Belize</option>
							<option value="BEN"  <#if 'BEN'==model.homeCountry>selected</#if>>Benin</option>
							<option value="BMU"  <#if 'BMU'==model.homeCountry>selected</#if>>Bermuda</option>
							<option value="BTN"  <#if 'BTN'==model.homeCountry>selected</#if>>Bhutan</option>
							<option value="BOL"  <#if 'BOL'==model.homeCountry>selected</#if>>Bolivia, Plurinational State of</option>
							<option value="BES"  <#if 'BES'==model.homeCountry>selected</#if>>Bonaire, Sint Eustatius and Saba</option>
							<option value="BIH"  <#if 'BIH'==model.homeCountry>selected</#if>>Bosnia and Herzegovina</option>
							<option value="BWA"  <#if 'BWA'==model.homeCountry>selected</#if>>Botswana</option>
							<option value="BVT"  <#if 'BVT'==model.homeCountry>selected</#if>>Bouvet Island</option>
							<option value="BRA"  <#if 'BRA'==model.homeCountry>selected</#if>>Brazil</option>
							<option value="IOT"  <#if 'IOT'==model.homeCountry>selected</#if>>British Indian Ocean Territory</option>
							<option value="BRN"  <#if 'BRN'==model.homeCountry>selected</#if>>Brunei Darussalam</option>
							<option value="BGR"  <#if 'BGR'==model.homeCountry>selected</#if>>Bulgaria</option>
							<option value="BFA"  <#if 'BFA'==model.homeCountry>selected</#if>>Burkina Faso</option>
							<option value="BDI"  <#if 'BDI'==model.homeCountry>selected</#if>>Burundi</option>
							<option value="KHM"  <#if 'KHM'==model.homeCountry>selected</#if>>Cambodia</option>
							<option value="CMR"  <#if 'CMR'==model.homeCountry>selected</#if>>Cameroon</option>
							<option value="CAN"  <#if 'CAN'==model.homeCountry>selected</#if>>Canada</option>
							<option value="CPV"  <#if 'CPV'==model.homeCountry>selected</#if>>Cape Verde</option>
							<option value="CYM"  <#if 'CYM'==model.homeCountry>selected</#if>>Cayman Islands</option>
							<option value="CAF"  <#if 'CAF'==model.homeCountry>selected</#if>>Central African Republic</option>
							<option value="TCD"  <#if 'TCD'==model.homeCountry>selected</#if>>Chad</option>
							<option value="CHL"  <#if 'CHL'==model.homeCountry>selected</#if>>Chile</option>
							<option value="CHN"  <#if 'CHN'==model.homeCountry>selected</#if>>China</option>
							<option value="CXR"  <#if 'CXR'==model.homeCountry>selected</#if>>Christmas Island</option>
							<option value="CCK"  <#if 'CCK'==model.homeCountry>selected</#if>>Cocos (Keeling) Islands</option>
							<option value="COL"  <#if 'COL'==model.homeCountry>selected</#if>>Colombia</option>
							<option value="COM"  <#if 'COM'==model.homeCountry>selected</#if>>Comoros</option>
							<option value="COG"  <#if 'COG'==model.homeCountry>selected</#if>>Congo</option>
							<option value="COD"  <#if 'COD'==model.homeCountry>selected</#if>>Congo, the Democratic Republic of the</option>
							<option value="COK"  <#if 'COK'==model.homeCountry>selected</#if>>Cook Islands</option>
							<option value="CRI"  <#if 'CRI'==model.homeCountry>selected</#if>>Costa Rica</option>
							<option value="CIV"  <#if 'CIV'==model.homeCountry>selected</#if>>Côte d'Ivoire</option>
							<option value="HRV"  <#if 'HRV'==model.homeCountry>selected</#if>>Croatia</option>
							<option value="CUB"  <#if 'CUB'==model.homeCountry>selected</#if>>Cuba</option>
							<option value="CUW"  <#if 'CUW'==model.homeCountry>selected</#if>>Curaçao</option>
							<option value="CYP"  <#if 'CYP'==model.homeCountry>selected</#if>>Cyprus</option>
							<option value="CZE"  <#if 'CZE'==model.homeCountry>selected</#if>>Czech Republic</option>
							<option value="DNK"  <#if 'DNK'==model.homeCountry>selected</#if>>Denmark</option>
							<option value="DJI"  <#if 'DJI'==model.homeCountry>selected</#if>>Djibouti</option>
							<option value="DMA"  <#if 'DMA'==model.homeCountry>selected</#if>>Dominica</option>
							<option value="DOM"  <#if 'DOM'==model.homeCountry>selected</#if>>Dominican Republic</option>
							<option value="ECU"  <#if 'ECU'==model.homeCountry>selected</#if>>Ecuador</option>
							<option value="EGY"  <#if 'EGY'==model.homeCountry>selected</#if>>Egypt</option>
							<option value="SLV"  <#if 'SLV'==model.homeCountry>selected</#if>>El Salvador</option>
							<option value="GNQ"  <#if 'GNQ'==model.homeCountry>selected</#if>>Equatorial Guinea</option>
							<option value="ERI"  <#if 'ERI'==model.homeCountry>selected</#if>>Eritrea</option>
							<option value="EST"  <#if 'EST'==model.homeCountry>selected</#if>>Estonia</option>
							<option value="ETH"  <#if 'ETH'==model.homeCountry>selected</#if>>Ethiopia</option>
							<option value="FLK"  <#if 'FLK'==model.homeCountry>selected</#if>>Falkland Islands (Malvinas)</option>
							<option value="FRO"  <#if 'FRO'==model.homeCountry>selected</#if>>Faroe Islands</option>
							<option value="FJI"  <#if 'FJI'==model.homeCountry>selected</#if>>Fiji</option>
							<option value="FIN"  <#if 'FIN'==model.homeCountry>selected</#if>>Finland</option>
							<option value="FRA"  <#if 'FRA'==model.homeCountry>selected</#if>>France</option>
							<option value="GUF"  <#if 'GUF'==model.homeCountry>selected</#if>>French Guiana</option>
							<option value="PYF"  <#if 'PYF'==model.homeCountry>selected</#if>>French Polynesia</option>
							<option value="ATF"  <#if 'ATF'==model.homeCountry>selected</#if>>French Southern Territories</option>
							<option value="GAB"  <#if 'GAB'==model.homeCountry>selected</#if>>Gabon</option>
							<option value="GMB"  <#if 'GMB'==model.homeCountry>selected</#if>>Gambia</option>
							<option value="GEO"  <#if 'GEO'==model.homeCountry>selected</#if>>Georgia</option>
							<option value="DEU"  <#if 'DEU'==model.homeCountry>selected</#if>>Germany</option>
							<option value="GHA"  <#if 'GHA'==model.homeCountry>selected</#if>>Ghana</option>
							<option value="GIB"  <#if 'GIB'==model.homeCountry>selected</#if>>Gibraltar</option>
							<option value="GRC"  <#if 'GRC'==model.homeCountry>selected</#if>>Greece</option>
							<option value="GRL"  <#if 'GRL'==model.homeCountry>selected</#if>>Greenland</option>
							<option value="GRD"  <#if 'GRD'==model.homeCountry>selected</#if>>Grenada</option>
							<option value="GLP"  <#if 'GLP'==model.homeCountry>selected</#if>>Guadeloupe</option>
							<option value="GUM"  <#if 'GUM'==model.homeCountry>selected</#if>>Guam</option>
							<option value="GTM"  <#if 'GTM'==model.homeCountry>selected</#if>>Guatemala</option>
							<option value="GGY"  <#if 'GGY'==model.homeCountry>selected</#if>>Guernsey</option>
							<option value="GIN"  <#if 'GIN'==model.homeCountry>selected</#if>>Guinea</option>
							<option value="GNB"  <#if 'GNB'==model.homeCountry>selected</#if>>Guinea-Bissau</option>
							<option value="GUY"  <#if 'GUY'==model.homeCountry>selected</#if>>Guyana</option>
							<option value="HTI"  <#if 'HTI'==model.homeCountry>selected</#if>>Haiti</option>
							<option value="HMD"  <#if 'HMD'==model.homeCountry>selected</#if>>Heard Island and McDonald Islands</option>
							<option value="VAT"  <#if 'VAT'==model.homeCountry>selected</#if>>Holy See (Vatican City State)</option>
							<option value="HND"  <#if 'HND'==model.homeCountry>selected</#if>>Honduras</option>
							<option value="HKG"  <#if 'HKG'==model.homeCountry>selected</#if>>Hong Kong</option>
							<option value="HUN"  <#if 'HUN'==model.homeCountry>selected</#if>>Hungary</option>
							<option value="ISL"  <#if 'ISL'==model.homeCountry>selected</#if>>Iceland</option>
							<option value="IND"  <#if 'IND'==model.homeCountry>selected</#if>>India</option>
							<option value="IDN"  <#if 'IDN'==model.homeCountry>selected</#if>>Indonesia</option>
							<option value="IRN"  <#if 'IRN'==model.homeCountry>selected</#if>>Iran, Islamic Republic of</option>
							<option value="IRQ"  <#if 'IRQ'==model.homeCountry>selected</#if>>Iraq</option>
							<option value="IRL"  <#if 'IRL'==model.homeCountry>selected</#if>>Ireland</option>
							<option value="IMN"  <#if 'IMN'==model.homeCountry>selected</#if>>Isle of Man</option>
							<option value="ISR"  <#if 'ISR'==model.homeCountry>selected</#if>>Israel</option>
							<option value="ITA"  <#if 'ITA'==model.homeCountry>selected</#if>>Italy</option>
							<option value="JAM"  <#if 'JAM'==model.homeCountry>selected</#if>>Jamaica</option>
							<option value="JPN"  <#if 'JPN'==model.homeCountry>selected</#if>>Japan</option>
							<option value="JEY"  <#if 'JEY'==model.homeCountry>selected</#if>>Jersey</option>
							<option value="JOR"  <#if 'JOR'==model.homeCountry>selected</#if>>Jordan</option>
							<option value="KAZ"  <#if 'KAZ'==model.homeCountry>selected</#if>>Kazakhstan</option>
							<option value="KEN"  <#if 'KEN'==model.homeCountry>selected</#if>>Kenya</option>
							<option value="KIR"  <#if 'KIR'==model.homeCountry>selected</#if>>Kiribati</option>
							<option value="PRK"  <#if 'PRK'==model.homeCountry>selected</#if>>Korea, Democratic People's Republic of</option>
							<option value="KOR"  <#if 'KOR'==model.homeCountry>selected</#if>>Korea, Republic of</option>
							<option value="KWT"  <#if 'KWT'==model.homeCountry>selected</#if>>Kuwait</option>
							<option value="KGZ"  <#if 'KGZ'==model.homeCountry>selected</#if>>Kyrgyzstan</option>
							<option value="LAO"  <#if 'LAO'==model.homeCountry>selected</#if>>Lao People's Democratic Republic</option>
							<option value="LVA"  <#if 'LVA'==model.homeCountry>selected</#if>>Latvia</option>
							<option value="LBN"  <#if 'LBN'==model.homeCountry>selected</#if>>Lebanon</option>
							<option value="LSO"  <#if 'LSO'==model.homeCountry>selected</#if>>Lesotho</option>
							<option value="LBR"  <#if 'LBR'==model.homeCountry>selected</#if>>Liberia</option>
							<option value="LBY"  <#if 'LBY'==model.homeCountry>selected</#if>>Libya</option>
							<option value="LIE"  <#if 'LIE'==model.homeCountry>selected</#if>>Liechtenstein</option>
							<option value="LTU"  <#if 'LTU'==model.homeCountry>selected</#if>>Lithuania</option>
							<option value="LUX"  <#if 'LUX'==model.homeCountry>selected</#if>>Luxembourg</option>
							<option value="MAC"  <#if 'MAC'==model.homeCountry>selected</#if>>Macao</option>
							<option value="MKD"  <#if 'MKD'==model.homeCountry>selected</#if>>Macedonia, the former Yugoslav Republic of</option>
							<option value="MDG"  <#if 'MDG'==model.homeCountry>selected</#if>>Madagascar</option>
							<option value="MWI"  <#if 'MWI'==model.homeCountry>selected</#if>>Malawi</option>
							<option value="MYS"  <#if 'MYS'==model.homeCountry>selected</#if>>Malaysia</option>
							<option value="MDV"  <#if 'MDV'==model.homeCountry>selected</#if>>Maldives</option>
							<option value="MLI"  <#if 'MLI'==model.homeCountry>selected</#if>>Mali</option>
							<option value="MLT"  <#if 'MLT'==model.homeCountry>selected</#if>>Malta</option>
							<option value="MHL"  <#if 'MHL'==model.homeCountry>selected</#if>>Marshall Islands</option>
							<option value="MTQ"  <#if 'MTQ'==model.homeCountry>selected</#if>>Martinique</option>
							<option value="MRT"  <#if 'MRT'==model.homeCountry>selected</#if>>Mauritania</option>
							<option value="MUS"  <#if 'MUS'==model.homeCountry>selected</#if>>Mauritius</option>
							<option value="MYT"  <#if 'MYT'==model.homeCountry>selected</#if>>Mayotte</option>
							<option value="MEX"  <#if 'MEX'==model.homeCountry>selected</#if>>Mexico</option>
							<option value="FSM"  <#if 'FSM'==model.homeCountry>selected</#if>>Micronesia, Federated States of</option>
							<option value="MDA"  <#if 'MDA'==model.homeCountry>selected</#if>>Moldova, Republic of</option>
							<option value="MCO"  <#if 'MCO'==model.homeCountry>selected</#if>>Monaco</option>
							<option value="MNG"  <#if 'MNG'==model.homeCountry>selected</#if>>Mongolia</option>
							<option value="MNE"  <#if 'MNE'==model.homeCountry>selected</#if>>Montenegro</option>
							<option value="MSR"  <#if 'MSR'==model.homeCountry>selected</#if>>Montserrat</option>
							<option value="MAR"  <#if 'MAR'==model.homeCountry>selected</#if>>Morocco</option>
							<option value="MOZ"  <#if 'MOZ'==model.homeCountry>selected</#if>>Mozambique</option>
							<option value="MMR"  <#if 'MMR'==model.homeCountry>selected</#if>>Myanmar</option>
							<option value="NAM"  <#if 'NAM'==model.homeCountry>selected</#if>>Namibia</option>
							<option value="NRU"  <#if 'NRU'==model.homeCountry>selected</#if>>Nauru</option>
							<option value="NPL"  <#if 'NPL'==model.homeCountry>selected</#if>>Nepal</option>
							<option value="NLD"  <#if 'NLD'==model.homeCountry>selected</#if>>Netherlands</option>
							<option value="NCL"  <#if 'NCL'==model.homeCountry>selected</#if>>New Caledonia</option>
							<option value="NZL"  <#if 'NZL'==model.homeCountry>selected</#if>>New Zealand</option>
							<option value="NIC"  <#if 'NIC'==model.homeCountry>selected</#if>>Nicaragua</option>
							<option value="NER"  <#if 'NER'==model.homeCountry>selected</#if>>Niger</option>
							<option value="NGA"  <#if 'NGA'==model.homeCountry>selected</#if>>Nigeria</option>
							<option value="NIU"  <#if 'NIU'==model.homeCountry>selected</#if>>Niue</option>
							<option value="NFK"  <#if 'NFK'==model.homeCountry>selected</#if>>Norfolk Island</option>
							<option value="MNP"  <#if 'MNP'==model.homeCountry>selected</#if>>Northern Mariana Islands</option>
							<option value="NOR"  <#if 'NOR'==model.homeCountry>selected</#if>>Norway</option>
							<option value="OMN"  <#if 'OMN'==model.homeCountry>selected</#if>>Oman</option>
							<option value="PAK"  <#if 'PAK'==model.homeCountry>selected</#if>>Pakistan</option>
							<option value="PLW"  <#if 'PLW'==model.homeCountry>selected</#if>>Palau</option>
							<option value="PSE"  <#if 'PSE'==model.homeCountry>selected</#if>>Palestinian Territory, Occupied</option>
							<option value="PAN"  <#if 'PAN'==model.homeCountry>selected</#if>>Panama</option>
							<option value="PNG"  <#if 'PNG'==model.homeCountry>selected</#if>>Papua New Guinea</option>
							<option value="PRY"  <#if 'PRY'==model.homeCountry>selected</#if>>Paraguay</option>
							<option value="PER"  <#if 'PER'==model.homeCountry>selected</#if>>Peru</option>
							<option value="PHL"  <#if 'PHL'==model.homeCountry>selected</#if>>Philippines</option>
							<option value="PCN"  <#if 'PCN'==model.homeCountry>selected</#if>>Pitcairn</option>
							<option value="POL"  <#if 'POL'==model.homeCountry>selected</#if>>Poland</option>
							<option value="PRT"  <#if 'PRT'==model.homeCountry>selected</#if>>Portugal</option>
							<option value="PRI"  <#if 'PRI'==model.homeCountry>selected</#if>>Puerto Rico</option>
							<option value="QAT"  <#if 'QAT'==model.homeCountry>selected</#if>>Qatar</option>
							<option value="REU"  <#if 'REU'==model.homeCountry>selected</#if>>Réunion</option>
							<option value="ROU"  <#if 'ROU'==model.homeCountry>selected</#if>>Romania</option>
							<option value="RUS"  <#if 'RUS'==model.homeCountry>selected</#if>>Russian Federation</option>
							<option value="RWA"  <#if 'RWA'==model.homeCountry>selected</#if>>Rwanda</option>
							<option value="BLM"  <#if 'BLM'==model.homeCountry>selected</#if>>Saint Barthélemy</option>
							<option value="SHN"  <#if 'SHN'==model.homeCountry>selected</#if>>Saint Helena, Ascension and Tristan da Cunha</option>
							<option value="KNA"  <#if 'KNA'==model.homeCountry>selected</#if>>Saint Kitts and Nevis</option>
							<option value="LCA"  <#if 'LCA'==model.homeCountry>selected</#if>>Saint Lucia</option>
							<option value="MAF"  <#if 'MAF'==model.homeCountry>selected</#if>>Saint Martin (French part)</option>
							<option value="SPM"  <#if 'SPM'==model.homeCountry>selected</#if>>Saint Pierre and Miquelon</option>
							<option value="VCT"  <#if 'VCT'==model.homeCountry>selected</#if>>Saint Vincent and the Grenadines</option>
							<option value="WSM"  <#if 'WSM'==model.homeCountry>selected</#if>>Samoa</option>
							<option value="SMR"  <#if 'SMR'==model.homeCountry>selected</#if>>San Marino</option>
							<option value="STP"  <#if 'STP'==model.homeCountry>selected</#if>>Sao Tome and Principe</option>
							<option value="SAU"  <#if 'SAU'==model.homeCountry>selected</#if>>Saudi Arabia</option>
							<option value="SEN"  <#if 'SEN'==model.homeCountry>selected</#if>>Senegal</option>
							<option value="SRB"  <#if 'SRB'==model.homeCountry>selected</#if>>Serbia</option>
							<option value="SYC"  <#if 'SYC'==model.homeCountry>selected</#if>>Seychelles</option>
							<option value="SLE"  <#if 'SLE'==model.homeCountry>selected</#if>>Sierra Leone</option>
							<option value="SGP"  <#if 'SGP'==model.homeCountry>selected</#if>>Singapore</option>
							<option value="SXM"  <#if 'SXM'==model.homeCountry>selected</#if>>Sint Maarten (Dutch part)</option>
							<option value="SVK"  <#if 'SVK'==model.homeCountry>selected</#if>>Slovakia</option>
							<option value="SVN"  <#if 'SVN'==model.homeCountry>selected</#if>>Slovenia</option>
							<option value="SLB"  <#if 'SLB'==model.homeCountry>selected</#if>>Solomon Islands</option>
							<option value="SOM"  <#if 'SOM'==model.homeCountry>selected</#if>>Somalia</option>
							<option value="ZAF"  <#if 'ZAF'==model.homeCountry>selected</#if>>South Africa</option>
							<option value="SGS"  <#if 'SGS'==model.homeCountry>selected</#if>>South Georgia and the South Sandwich Islands</option>
							<option value="SSD"  <#if 'SSD'==model.homeCountry>selected</#if>>South Sudan</option>
							<option value="ESP"  <#if 'ESP'==model.homeCountry>selected</#if>>Spain</option>
							<option value="LKA"  <#if 'LKA'==model.homeCountry>selected</#if>>Sri Lanka</option>
							<option value="SDN"  <#if 'SDN'==model.homeCountry>selected</#if>>Sudan</option>
							<option value="SUR"  <#if 'SUR'==model.homeCountry>selected</#if>>Suriname</option>
							<option value="SJM"  <#if 'SJM'==model.homeCountry>selected</#if>>Svalbard and Jan Mayen</option>
							<option value="SWZ"  <#if 'SWZ'==model.homeCountry>selected</#if>>Swaziland</option>
							<option value="SWE"  <#if 'SWE'==model.homeCountry>selected</#if>>Sweden</option>
							<option value="CHE"  <#if 'CHE'==model.homeCountry>selected</#if>>Switzerland</option>
							<option value="SYR"  <#if 'SYR'==model.homeCountry>selected</#if>>Syrian Arab Republic</option>
							<option value="TWN"  <#if 'TWN'==model.homeCountry>selected</#if>>Taiwan, Province of China</option>
							<option value="TJK"  <#if 'TJK'==model.homeCountry>selected</#if>>Tajikistan</option>
							<option value="TZA"  <#if 'TZA'==model.homeCountry>selected</#if>>Tanzania, United Republic of</option>
							<option value="THA"  <#if 'THA'==model.homeCountry>selected</#if>>Thailand</option>
							<option value="TLS"  <#if 'TLS'==model.homeCountry>selected</#if>>Timor-Leste</option>
							<option value="TGO"  <#if 'TGO'==model.homeCountry>selected</#if>>Togo</option>
							<option value="TKL"  <#if 'TKL'==model.homeCountry>selected</#if>>Tokelau</option>
							<option value="TON"  <#if 'TON'==model.homeCountry>selected</#if>>Tonga</option>
							<option value="TTO"  <#if 'TTO'==model.homeCountry>selected</#if>>Trinidad and Tobago</option>
							<option value="TUN"  <#if 'TUN'==model.homeCountry>selected</#if>>Tunisia</option>
							<option value="TUR"  <#if 'TUR'==model.homeCountry>selected</#if>>Turkey</option>
							<option value="TKM"  <#if 'TKM'==model.homeCountry>selected</#if>>Turkmenistan</option>
							<option value="TCA"  <#if 'TCA'==model.homeCountry>selected</#if>>Turks and Caicos Islands</option>
							<option value="TUV"  <#if 'TUV'==model.homeCountry>selected</#if>>Tuvalu</option>
							<option value="UGA"  <#if 'UGA'==model.homeCountry>selected</#if>>Uganda</option>
							<option value="UKR"  <#if 'UKR'==model.homeCountry>selected</#if>>Ukraine</option>
							<option value="ARE"  <#if 'ARE'==model.homeCountry>selected</#if>>United Arab Emirates</option>
							<option value="GBR"  <#if 'GBR'==model.homeCountry>selected</#if>>United Kingdom</option>
							<option value="USA"  <#if 'USA'==model.homeCountry>selected</#if>>United States</option>
							<option value="UMI"  <#if 'UMI'==model.homeCountry>selected</#if>>United States Minor Outlying Islands</option>
							<option value="URY"  <#if 'URY'==model.homeCountry>selected</#if>>Uruguay</option>
							<option value="UZB"  <#if 'UZB'==model.homeCountry>selected</#if>>Uzbekistan</option>
							<option value="VUT"  <#if 'VUT'==model.homeCountry>selected</#if>>Vanuatu</option>
							<option value="VEN"  <#if 'VEN'==model.homeCountry>selected</#if>>Venezuela, Bolivarian Republic of</option>
							<option value="VNM"  <#if 'VNM'==model.homeCountry>selected</#if>>Viet Nam</option>
							<option value="VGB"  <#if 'VGB'==model.homeCountry>selected</#if>>Virgin Islands, British</option>
							<option value="VIR"  <#if 'VIR'==model.homeCountry>selected</#if>>Virgin Islands, U.S.</option>
							<option value="WLF"  <#if 'WLF'==model.homeCountry>selected</#if>>Wallis and Futuna</option>
							<option value="ESH"  <#if 'ESH'==model.homeCountry>selected</#if>>Western Sahara</option>
							<option value="YEM"  <#if 'YEM'==model.homeCountry>selected</#if>>Yemen</option>
							<option value="ZMB"  <#if 'ZMB'==model.homeCountry>selected</#if>>Zambia</option>
							<option value="ZWE"  <#if 'ZWE'==model.homeCountry>selected</#if>>Zimbabwe</option>
						</select>
						<label for="homeCountry"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.homeRegion" />：</th>
					<td>
						<input class="form-control"  type="text" id="homeRegion" name="homeRegion"  title="" value="${model.homeRegion!}"/>
						<label for="homeRegion"></label>
					</td>
				</tr>
				<tr>
					
					<th><@locale code="userinfo.homeLocality" />：</th>
					<td>
						<input class="form-control"  type="text" id="homeLocality" name="homeLocality"  title="" value="${model.homeLocality!}"/>
						<label for="homeLocality"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.homeStreetAddress" />：</th>
					<td>
						<input class="form-control"  type="text" id="homeStreetAddress" name="homeStreetAddress"  title="" value="${model.homeStreetAddress!}"/>
						<label for="homeStreetAddress"></label>
					</td>
				</tr>
				<tr>
					
					<th><@locale code="userinfo.homePostalCode" />：</th>
					<td>
						<input class="form-control"  type="text" id="homePostalCode" name="homePostalCode"  title="" value="${model.homePostalCode!}"/>
						<label for="homePostalCode"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.homeFax" />：</th>
					<td>
						<input class="form-control"  type="text" id="homeFax" name="homeFax"  title="" value="${model.homeFax!}"/>
						<label for="homeFax"></label>
					</td>
				</tr>
				
				<tr>
					<th><@locale code="userinfo.homePhoneNumber" />：</th>
					<td>
						<input class="form-control"  type="text" id="homePhoneNumber" name="homePhoneNumber"  title="" value="${model.homePhoneNumber!}"/>
						<label for="homePhoneNumber"></label>
					</td>
				</tr>
				<tr>
					<th><@locale code="userinfo.homeEmail" />：</th>
					<td >
						<input class="form-control"  type="text" id="homeEmail" name="homeEmail"  title="" value="${model.homeEmail!}"/>
						<label for="homeEmail"></label>
					</td>
				</tr>
				</tbody>
			  </table>
	</div>
</div>
  	        <div class="clear"></div>
			<div >
				<div >
					<input id="_method" type="hidden" name="_method"  value="post"/>
					<input type="submit" id="submitButton" style="display: none;" />
					<input id="submitBtn" class="button btn btn-primary mr-3" type="button" value="<@locale code="button.text.save" />"/>
				</div>
			</div>
	</div>
</form>
</div>
<div id="footer">
	<#include   "../layout/footer.ftl"/>
</div>
<body>
</html>