<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
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

<style   type="text/css">
  .table th, .table td {
    padding: .2rem;
    vertical-align: middle;
  }
</style>
</head>
<body>
<form  
	method="post"
	type="alert"  
	action="<@base/>/userinfo/update" 
	autoclose="true" 
	enctype="multipart/form-data"
	class="needs-validation" novalidate>
	 <div class="" style="width:100%;">
		<div class="top">
			<ul class="switch_tab"  style="width:100%" >
				<li id="table_switch_basic"   style="width:30%" class="switch_tab_class switch_tab_current"><a href="javascript:void(0);"><@locale code="userinfo.tab.basic" /></a></li>
				<li id="table_switch_company" style="width:30%" class="switch_tab_class"><a href="javascript:void(0);"><@locale code="userinfo.tab.business" /></a></li>
				<li id="table_switch_home"    style="width:30%" class="switch_tab_class"><a href="javascript:void(0);"><@locale code="userinfo.tab.personal" /></a></li>
				
			</ul>
		</div>

	    <div class="main">
	    <div class="mainin">			 
  	        <!-- content -->    
  	      	<!--table-->
			 <table id="div_table_switch_basic" width="980" class="table table-bordered" >
	<tbody>				
	<tr>
		<th style="width:15%;"><@locale code="userinfo.username" /></th>
		<td style="width:35%;">
			<input type="hidden" id="id" name="id" value="${model.id!""}"/>
			<input  class="form-control"  type="text" id="username" name="username"  title="" value="${model.username!""}"/>
		</td>
		<td style="width:15%;"><@locale code="userinfo.status" /></td>
		<td style="width:35%;">
			<select name="status"  id="status" class="form-control  form-select" >
						<option value="1"   <#if 1==model.status>selected</#if>><@locale code="userinfo.status.active" /></option>
						<option value="2"   <#if 2==model.status>selected</#if>><@locale code="userinfo.status.inactive" /></option>
						<option value="5"   <#if 5==model.status>selected</#if>><@locale code="userinfo.status.lock" /></option>
						<option value="9"   <#if 9==model.status>selected</#if>><@locale code="userinfo.status.delete" /></option>
				</select>
		</td>
	</tr>
	<tr>
			<td style="width:15%;"><@locale code="userinfo.userType" /></td>
			<td style="width:35%;">
				<select name="userType"   class="form-control  form-select" >
						<option value="EMPLOYEE"  	<#if 'EMPLOYEE'==model.userType>selected</#if> ><@locale code="userinfo.userType.employee" /></option>
						<option value="CONTRACTOR"  <#if 'CONTRACTOR'==model.userType>selected</#if>><@locale code="userinfo.userType.contractor" /></option>
						<option value="CUSTOMER"  	<#if 'CUSTOMER'==model.userType>selected</#if>><@locale code="userinfo.userType.customer" /></option>
						<option value="DEALER"  	<#if 'DEALER'==model.userType>selected</#if>><@locale code="userinfo.userType.dealer" /></option>
						<option value="SUPPLIER"  	<#if 'SUPPLIER'==model.userType>selected</#if>><@locale code="userinfo.userType.supplier" /></option>
						<option value="PARTNER"  	<#if 'PARTNER'==model.userType>selected</#if>><@locale code="userinfo.userType.partner" /></option>
						<option value="EXTERNAL"  	<#if 'EXTERNAL'==model.userType>selected</#if>><@locale code="userinfo.userType.external" /></option>
						<option value="INTERN"  	<#if 'INTERN'==model.userType>selected</#if>><@locale code="userinfo.userType.intern" /></option>
						<option value="TEMP"  		<#if 'TEMP'==model.userType>selected</#if>><@locale code="userinfo.userType.temp" /></option>
				</select>
			</td>
			<td><@locale code="userinfo.userstate" /></td>
			<td style="width:35%;">
				<select name="userState"   class="form-control  form-select" >
						<option value="RESIDENT"  	<#if 'RESIDENT'==model.userState>selected</#if> ><@locale code="userinfo.userstate.resident" /></option>
						<option value="WITHDRAWN"  	<#if 'WITHDRAWN'==model.userState>selected</#if>><@locale code="userinfo.userstate.withdrawn" /></option>
						<option value="RETIREE"  	<#if 'RETIREE'==model.userState>selected</#if>><@locale code="userinfo.userstate.retiree" /></option>
						<option value="INACTIVE"  	<#if 'INACTIVE'==model.userState>selected</#if>><@locale code="userinfo.userstate.inactive" /></option>
				</select>
			</td>
			
	</tr>
		<tr>
			<td style="width:15%;"><@locale code="userinfo.employeeNumber" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="employeeNumber" name="employeeNumber"  title="" value="${model.employeeNumber!""!""}"/>
			</td>
			<td><@locale code="userinfo.windowsAccount" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="windowsAccount" name="windowsAccount"  title="" value="${model.windowsAccount!""}"/>
			</td>
			
	</tr>
	<tr>
		<td colspan="4">&nbsp;
		</td>
		
	</tr>
	<tr>
		<th><@locale code="userinfo.displayName" /></th>
		<td>
			<input class="form-control"  type="text" id="displayName" name="displayName"  title="" value="${model.displayName!""}"/>
		</td>
		<td rowspan="3"><@locale code="userinfo.picture" /></td>
		<td rowspan="3">
		  <img id="picture" width="150px" height="150px" 
		  <#if  model.pictureBase64 ?? >
                src="${model.pictureBase64}" 
          <#else>
                src="<@base/>/static/images/uploadimage.jpg"
          </#if>            
		  />
		  <input type="file" id="pictureFile" name="pictureFile" style="display:none" />
					</td>
	</tr>
	<tr>
		<td><@locale code="userinfo.familyName" /></td>
		<td>
			<input class="form-control"  type="text" id="familyName" name="familyName"  title="" value="${model.familyName!""}"/>
		</td>
	</tr>
	<tr>
		<td><@locale code="userinfo.givenName" /></td>
		<td>
			<input class="form-control"  type="text" id="givenName" name="givenName"  title="" value="${model.givenName!""}"/>
		</td>
	</tr>
	<tr>
		<td><@locale code="userinfo.middleName" /></td>
		<td>
			<input class="form-control"  type="text" id="middleName" name="middleName"  title="" value="${model.middleName!""}"/>
		</td>
		<td><@locale code="userinfo.nickName" /></td>
		<td>
			<input class="form-control"  type="text" id="nickName" name="nickName"  title="" value="${model.nickName!""}"/>
		</td>
		
	</tr>
	<tr>
		<td><@locale code="userinfo.gender" /></td>
		<td>
			<select name="gender"   class="form-control  form-select" >
					<option value="1"  <#if 1==model.gender>selected</#if> ><@locale code="userinfo.gender.female" /></option>
					<option value="2"  <#if 2==model.gender>selected</#if> ><@locale code="userinfo.gender.male" /></option>
			</select>
		</td>
		<td><@locale code="userinfo.birthDate" /></td>
		<td>
			<input class="form-control"  type="text" id="birthDate" name="birthDate"  title="" value="${model.birthDate!""}"/>
		</td>
	</tr>
	<tr>
		<td><@locale code="userinfo.preferredLanguage" /></td>
		<td>
			<select class="form-control  form-select"  name="preferredLanguage" id="preferredLanguage">
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
		<td><@locale code="userinfo.timeZone" /></td>
		<td nowrap >
			<select class="form-control  form-select"  id="timeZone" name="timeZone" tabindex="61">
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
	</tbody>
 </table>
 <table  class="table table-bordered"  style="display:none" id="div_table_switch_company" width="980">
	<tbody>				
		<tr>
			<td style="width:15%;"><@locale code="userinfo.organization" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="organization" name="organization"  title="" value="${model.organization!""}"/>
			</td>
			<td style="width:15%;"><@locale code="userinfo.division" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="division" name="division"  title="" value="${model.division!""}"/>
			</td>
			
		</tr>
		
		<tr>
			<td><@locale code="userinfo.department" /></td>
			<td>
				<input class="form-control"  type="hidden" id="departmentId" name="departmentId"  title="" value="${model.departmentId!""}"/>
				<input class="form-control"  type="text" style="width:70%"  id="department" name="department"  title="" value="${model.department!""}"/>
				<s:Dialog text="button.text.select" title="department" url="/orgs/orgsSelect/deptId/department" width="300" height="400" />
			</td>
			<td><@locale code="userinfo.costCenter" /></td>
			<td>
				<input class="form-control"  type="text" id="costCenter" name="costCenter"  title="" value="${model.costCenter!""}"/>
			</td>
			
		</tr>
		<tr>
			<td><@locale code="userinfo.jobTitle" /></td>
			<td>
				<input class="form-control"  type="text" id="jobTitle" name="jobTitle"  title="" value="${model.jobTitle!""}"/>
			</td>
			<td><@locale code="userinfo.jobLevel" /></td>
			<td>
				<input class="form-control"  type="text" id="jobLevel" name="jobLevel"  title="" value="${model.jobLevel!""}"/>
			</td>
		</tr>
		<tr>
			<td><@locale code="userinfo.manager" /></td>
			<td>
				<input class="form-control"  type="hidden" id="managerId" name="managerId"  title="" value="${model.managerId!""}"/>
				<input class="form-control"  type="text" id="manager" name="manager"  title="" value="${model.manager!""}"/>
			</td>
			<td><@locale code="userinfo.assistant" /></td>
			<td>
				<input class="form-control"  type="hidden" id="assistantId" name="assistantId"  title="" value="${model.assistantId!""}"/>
				<input class="form-control"   type="text" id="assistant" name="assistant"  title="" value="${model.assistant!""}"/>
			</td>
		</tr>
		<tr>
			<td><@locale code="userinfo.entryDate" /></td>
			<td>
				<input class="form-control"  type="text" id="entryDate" name="entryDate"  title="" value="${model.entryDate!""}"/>
			</td>
			<td><@locale code="userinfo.quitDate" /></td>
			<td>
				<input class="form-control"   type="text" id="quitDate" name="quitDate"  title="" value="${model.quitDate!""}"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;
			</td>
			
		</tr>
		<tr>
			<td><@locale code="userinfo.workCountry" /></td>
			<td nowrap >
				<input class="form-control"   type="text" id="workCountry" name="workCountry"  title="" value="${model.workCountry!""}"/>
			</td>
			<td><@locale code="userinfo.workRegion" /></td>
			<td>
				<input class="form-control"  type="text" id="workRegion" name="workRegion"  title="" value="${model.workRegion!""}"/>
			</td>
		</tr>
		<tr>
			<td><@locale code="userinfo.workLocality" /></td>
			<td>
				<input class="form-control"  type="text" id="workLocality" name="workLocality"  title="" value="${model.workLocality!""}"/>
			</td>
			<td><@locale code="userinfo.workStreetAddress" /></td>
			<td>
				<input class="form-control"  type="text" id="workStreetAddress" name="workStreetAddress"  title="" value="${model.workStreetAddress!""}"/>
			</td>
		</tr>
		<tr>
			
			<td><@locale code="userinfo.workPostalCode" /></td>
			<td>
				<input class="form-control"  type="text" id="workPostalCode" name="workPostalCode"  title="" value="${model.workPostalCode!""}"/>
			</td>
			<td><@locale code="userinfo.workFax" /></td>
			<td>
				<input class="form-control"  type="text" id="workFax" name="workFax"  title="" value="${model.workFax!""}"/>
			</td>
		</tr>

		<tr>
			<td><@locale code="userinfo.workPhoneNumber" /></td>
			<td>
				<input class="form-control"  type="text" id="workPhoneNumber" name="workPhoneNumber"  title="" value="${model.workPhoneNumber!""}"/>
			</td>
			<td><@locale code="userinfo.workEmail" /></td>
			<td>
				<input class="form-control"  type="text" id="workEmail" name="workEmail"  title="" value="${model.workEmail!""}"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;
			</td>
			
		</tr>					
	</tbody>
</table>
<table  class="table table-bordered"  style="display:none" id="div_table_switch_home" width="980">
	<tbody>				
		<tr>
			<td style="width:15%;"><@locale code="userinfo.idtype" /></td>
			<td style="width:35%;">
				<select name="idType"   class="form-control  form-select" >
					<option value="0"  <#if 0==model.idType>selected</#if> ><@locale code="userinfo.idtype.unknown" /></option>
					<option value="1"  <#if 1==model.idType>selected</#if> ><@locale code="userinfo.idtype.idcard" /></option>
					<option value="2"  <#if 2==model.idType>selected</#if> ><@locale code="userinfo.idtype.passport" /></option>
					<option value="3"  <#if 3==model.idType>selected</#if> ><@locale code="userinfo.idtype.studentcard" /></option>
					<option value="4"  <#if 4==model.idType>selected</#if> ><@locale code="userinfo.idtype.militarycard" /></option>
				</select>
			</td>
			
			<td style="width:15%;"><@locale code="userinfo.idCardNo" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="idCardNo" name="idCardNo"  title="" value="${model.idCardNo!""}"/>

			</td>
		</tr>
		<tr>
			
			<td><@locale code="userinfo.married" /></td>
			<td>
				<select name="married"  class="form-control  form-select" >
					<option value="0"  <#if 0==model.married>selected</#if> ><@locale code="userinfo.married.unknown" /></option>
					<option value="1"  <#if 1==model.married>selected</#if> ><@locale code="userinfo.married.single" /></option>
					<option value="2"  <#if 2==model.married>selected</#if> ><@locale code="userinfo.married.married" /></option>
					<option value="3"  <#if 3==model.married>selected</#if> ><@locale code="userinfo.married.divorce" /></option>
					<option value="4"  <#if 4==model.married>selected</#if> ><@locale code="userinfo.married.widowed" /></option>
				</select>
			</td>
			<td><@locale code="userinfo.startWorkDate" /></td>
			<td>
				<input class="form-control"  type="text" id="startWorkDate" name="startWorkDate"  title="" value="${model.startWorkDate!""}"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;
			</td>
			
		</tr>
		
		<tr>
			<td><@locale code="userinfo.homeCountry" /></td>
			<td>
				<input class="form-control"  type="text" id="homeCountry" name="homeCountry"  title="" value="${model.homeCountry!""}"/>
			</td>
			<td><@locale code="userinfo.homeRegion" /></td>
			<td>
				<input class="form-control"  type="text" id="homeRegion" name="homeRegion"  title="" value="${model.homeRegion!""}"/>
			</td>
		</tr>
		<tr>
			
			<td><@locale code="userinfo.homeLocality" /></td>
			<td>
				<input class="form-control"  type="text" id="homeLocality" name="homeLocality"  title="" value="${model.homeLocality!""}"/>
			</td>
			<td><@locale code="userinfo.homeStreetAddress" /></td>
			<td>
				<input class="form-control"  type="text" id="homeStreetAddress" name="homeStreetAddress"  title="" value="${model.homeStreetAddress!""}"/>
			</td>
		</tr>
		<tr>
			
			<td><@locale code="userinfo.homePostalCode" /></td>
			<td>
				<input class="form-control"  type="text" id="homePostalCode" name="homePostalCode"  title="" value="${model.homePostalCode!""}"/>
			</td>
			<td><@locale code="userinfo.homeFax" /></td>
			<td>
				<input class="form-control"  type="text" id="homeFax" name="homeFax"  title="" value="${model.homeFax!""}"/>
			</td>
		</tr>
		
		<tr>
			<td><@locale code="userinfo.homePhoneNumber" /></td>
			<td>
				<input class="form-control"  type="text" id="homePhoneNumber" name="homePhoneNumber"  title="" value="${model.homePhoneNumber!""}"/>
			</td>
			<td><@locale code="userinfo.homeEmail" /></td>
			<td >
				<input class="form-control"  type="text" id="homeEmail" name="homeEmail"  title="" value="${model.homeEmail!""}"/>
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;
			</td>
			
		</tr>
				<tr>
					<td><@locale code="userinfo.website" /></td>
					<td>
						<input  class="form-control"   type="text" id="webSite" name="webSite"  title="" value="${model.webSite!""}"/>
					</td>
					<td><@locale code="userinfo.ims" /></td>
					<td >
						<input class="form-control"  type="text" id="defineIm" name="defineIm"  title="" value="${model.defineIm!""}"/>
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
					<input id="submitBtn" class="button btn btn-primary mr-3" type="submit" value="<@locale code="button.text.save" />"/>
				</div>
			</div>
	 </div> 
</form>
</body>
</html>