<%@ page   language="java"    import="java.util.*"   pageEncoding="UTF-8"%>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="s"  		uri="http://sso.maxkey.org/tags" %>
<%@ taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"		uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"	 	uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML >
<html>
<head>
	<jsp:include page="../layout/header.jsp"></jsp:include>
	<jsp:include page="../layout/common.cssjs.jsp"></jsp:include>
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
<jsp:include page="../layout/top.jsp"></jsp:include>
<jsp:include page="../layout/nav_primary.jsp"></jsp:include>
<div class="container">
<form id="actionForm"  
	method="post" 
	type="alert" 
	forward="<s:Base/>/profile/myProfile"
	action="<s:Base/>/profile/update/myProfile" 
	enctype="multipart/form-data">
	 <div class="" style="width:100%;">
	    <div class="main">
	    <div class="mainin">			 
  	        <!-- content -->    
  	      	<!--table-->
			  <table class="table table-bordered">
				<tbody>				
				<tr>
					<th style="width:15%;"><s:Locale code="userinfo.username" />：</th>
					<td style="width:35%;">
					<input  class="form-control"  type="hidden" id="id" name="id" value="${model.id}"/>
						<input  class="form-control"  type="text" readonly id="username" name="username"  title="" value="${model.username}"/>
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
						<input class="form-control"  type="text" id="displayName" name="displayName"  title="" value="${model.displayName}"/>
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
						<input  class="form-control"  type="file" id="pictureFile" name="pictureFile" style="display:none" />
						<b class="orange">*</b><label for="picture"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.familyName" />：</th>
					<td>
						<input class="form-control"  type="text" id="familyName" name="familyName"  title="" value="${model.familyName}"/>
						<b class="orange">*</b><label for="familyName"></label>
					</td>
					
					
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.givenName" />：</th>
					<td>
						<input class="form-control"  type="text" id="givenName" name="givenName"  title="" value="${model.givenName}"/>
						<b class="orange">*</b><label for="givenName"></label>
					</td>
					
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.middleName" />：</th>
					<td>
						<input class="form-control"  type="text" id="middleName" name="middleName"  title="" value="${model.middleName}"/>
						<label for="middleName"></label>
					</td>
					
					
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.nickName" />：</th>
					<td>
						<input class="form-control"  type="text" id="nickName" name="nickName"  title="" value="${model.nickName}"/>
						<label for="nickName"></label>
					</td>
					<th><s:Locale code="userinfo.gender" />：</th>
					<td>
						<select class="form-control" name="gender"  class="gender">
								<option value="1"  <c:if test="${1==model.gender}">selected</c:if> ><s:Locale code="userinfo.gender.female" /></option>
								<option value="2"  <c:if test="${2==model.gender}">selected</c:if> ><s:Locale code="userinfo.gender.male" /></option>
						</select>
						<label for="gender"></label>
						
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.married" />：</th>
					<td>
						<select class="form-control"  name="married"  class="select_t">
							<option value="0"  <c:if test="${0==model.married}">selected</c:if> ><s:Locale code="userinfo.married.unknown" /></option>
							<option value="1"  <c:if test="${1==model.married}">selected</c:if> ><s:Locale code="userinfo.married.single" /></option>
							<option value="2"  <c:if test="${2==model.married}">selected</c:if> ><s:Locale code="userinfo.married.married" /></option>
							<option value="3"  <c:if test="${3==model.married}">selected</c:if> ><s:Locale code="userinfo.married.divorce" /></option>
							<option value="4"  <c:if test="${4==model.married}">selected</c:if> ><s:Locale code="userinfo.married.widowed" /></option>
						</select>
					</td>
					<th><s:Locale code="userinfo.website" />：</th>
					<td>
						<input class="form-control"  type="text" id="webSite" name="webSite"  title="" value="${model.webSite}"/>
						<label for="webSite"></label>
						
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.idtype" />：</th>
					<td>
						<select class="form-control"  name="idType"  class="select_t">
							<option value="0"  <c:if test="${0==model.idType}">selected</c:if> ><s:Locale code="userinfo.idtype.unknown" /></option>
							<option value="1"  <c:if test="${1==model.idType}">selected</c:if> ><s:Locale code="userinfo.idtype.idcard" /></option>
							<option value="2"  <c:if test="${2==model.idType}">selected</c:if> ><s:Locale code="userinfo.idtype.passport" /></option>
							<option value="3"  <c:if test="${3==model.idType}">selected</c:if> ><s:Locale code="userinfo.idtype.studentcard" /></option>
							<option value="4"  <c:if test="${4==model.idType}">selected</c:if> ><s:Locale code="userinfo.idtype.militarycard" /></option>
						</select>
					</td>
					
					<th><s:Locale code="userinfo.idCardNo" />：</th>
					<td>
						<input class="form-control"  type="text" id="idCardNo" name="idCardNo"  title="" value="${model.idCardNo}"/>
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
						<input class="form-control"  type="text"  class="datepicker"  id="startWorkDate" name="startWorkDate"  title="" value="${model.startWorkDate}"/>
						<label for="startWorkDate"></label>
					</td>
					<th><s:Locale code="userinfo.preferredLanguage" />：</th>
					<td>
						<select class="form-control"  name="preferredLanguage" id="preferredLanguage">
							
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
						<select class="form-control"  id="timeZone" name="timeZone" tabindex="61">
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
						<select class="form-control"  name="locale" id="locale">
							
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
				<tr>
					<td colspan="4">&nbsp;
					</td>
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.employeeNumber" />：</th>
					<td>
						<input class="form-control"  readonly type="text" id="employeeNumber" name="employeeNumber"  title="" value="${model.employeeNumber}"/>
						<label for="username"></label>
					</td>
					<th><s:Locale code="userinfo.windowsAccount" />：</th>
					<td>
						<input class="form-control"  type="text" id="windowsAccount" name="windowsAccount"  title="" value="${model.windowsAccount}"/>
						<label for="windowsAccount"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.organization" />：</th>
					<td>
						<input class="form-control"  type="text" id="organization" name="organization"  title="" value="${model.organization}"/>
						<label for="organization"></label>
					</td>
					<th><s:Locale code="userinfo.division" />：</th>
					<td>
						<input class="form-control"  type="text" id="division" name="division"  title="" value="${model.division}"/>
						<label for="division"></label>
					</td>
					
				</tr>
				
				<tr>
					<th><s:Locale code="userinfo.department" />：</th>
					<td>
						<input class="form-control"  type="hidden" id="departmentId" name="departmentId"  title="" value="${model.departmentId}"/>
						<input class="form-control"  type="text" id="department" name="department"  title="" value="${model.department}"/>
						<label for="department"></label>
					</td>
					<th><s:Locale code="userinfo.costCenter" />：</th>
					<td>
						<input class="form-control"  type="text" id="costCenter" name="costCenter"  title="" value="${model.costCenter}"/>
						<label for="costCenter"></label>
					</td>
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.jobTitle" />：</th>
					<td>
						<input class="form-control"  type="text" id="jobTitle" name="jobTitle"  title="" value="${model.jobTitle}"/>
						<label for="jobTitle"></label>
					</td>
					<th><s:Locale code="userinfo.jobLevel" />：</th>
					<td>
						<input class="form-control"  type="text" id="jobLevel" name="jobLevel"  title="" value="${model.jobLevel}"/>
						<label for="jobLevel"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.manager" />：</th>
					<td>
						<input class="form-control"  type="hidden" id="managerId" name="managerId"  title="" value="${model.managerId}"/>
						<input class="form-control"  type="text" id="manager" name="manager"  title="" value="${model.manager}"/>
						<label for="manager"></label>
					</td>
					<th><s:Locale code="userinfo.assistant" />：</th>
					<td>
						<input class="form-control"  type="hidden" id="assistantId" name="assistantId"  title="" value="${model.assistantId}"/>
						<input class="form-control"   type="text" id="assistant" name="assistant"  title="" value="${model.assistant}"/>
						<label for="delegatedApprover"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.entryDate" />：</th>
					<td>
						<input class="form-control"  type="text" class="datepicker"  id="entryDate" name="entryDate"  title="" value="${model.entryDate}"/>
						<label for="entryDate"></label>
					</td>
					<th><s:Locale code="userinfo.quitDate" />：</th>
					<td>
						<input class="form-control"   type="text" class="datepicker"  id="quitDate" name="quitDate"  title="" value="${model.quitDate}"/>
						<label for="delegatedApprover"></label>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;
					</td>
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.workCountry" />：</th>
					<td>
						<select  class="form-control"  id="workCountry" name="workCountry">
							<option value="AFG"  <c:if test="${'AFG'==model.workCountry}">selected</c:if>>Afghanistan</option>
							<option value="ALA"  <c:if test="${'ALA'==model.workCountry}">selected</c:if>>Åland Islands</option>
							<option value="ALB"  <c:if test="${'ALB'==model.workCountry}">selected</c:if>>Albania</option>
							<option value="DZA"  <c:if test="${'DZA'==model.workCountry}">selected</c:if>>Algeria</option>
							<option value="ASM"  <c:if test="${'ASM'==model.workCountry}">selected</c:if>>American Samoa</option>
							<option value="AND"  <c:if test="${'AND'==model.workCountry}">selected</c:if>>Andorra</option>
							<option value="AGO"  <c:if test="${'AGO'==model.workCountry}">selected</c:if>>Angola</option>
							<option value="AIA"  <c:if test="${'AIA'==model.workCountry}">selected</c:if>>Anguilla</option>
							<option value="ATA"  <c:if test="${'ATA'==model.workCountry}">selected</c:if>>Antarctica</option>
							<option value="ATG"  <c:if test="${'ATG'==model.workCountry}">selected</c:if>>Antigua and Barbuda</option>
							<option value="ARG"  <c:if test="${'ARG'==model.workCountry}">selected</c:if>>Argentina</option>
							<option value="ARM"  <c:if test="${'ARM'==model.workCountry}">selected</c:if>>Armenia</option>
							<option value="ABW"  <c:if test="${'ABW'==model.workCountry}">selected</c:if>>Aruba</option>
							<option value="AUS"  <c:if test="${'AUS'==model.workCountry}">selected</c:if>>Australia</option>
							<option value="AUT"  <c:if test="${'AUT'==model.workCountry}">selected</c:if>>Austria</option>
							<option value="AZE"  <c:if test="${'AZE'==model.workCountry}">selected</c:if>>Azerbaijan</option>
							<option value="BHS"  <c:if test="${'BHS'==model.workCountry}">selected</c:if>>Bahamas</option>
							<option value="BHR"  <c:if test="${'BHR'==model.workCountry}">selected</c:if>>Bahrain</option>
							<option value="BGD"  <c:if test="${'BGD'==model.workCountry}">selected</c:if>>Bangladesh</option>
							<option value="BRB"  <c:if test="${'BRB'==model.workCountry}">selected</c:if>>Barbados</option>
							<option value="BLR"  <c:if test="${'BLR'==model.workCountry}">selected</c:if>>Belarus</option>
							<option value="BEL"  <c:if test="${'BEL'==model.workCountry}">selected</c:if>>Belgium</option>
							<option value="BLZ"  <c:if test="${'BLZ'==model.workCountry}">selected</c:if>>Belize</option>
							<option value="BEN"  <c:if test="${'BEN'==model.workCountry}">selected</c:if>>Benin</option>
							<option value="BMU"  <c:if test="${'BMU'==model.workCountry}">selected</c:if>>Bermuda</option>
							<option value="BTN"  <c:if test="${'BTN'==model.workCountry}">selected</c:if>>Bhutan</option>
							<option value="BOL"  <c:if test="${'BOL'==model.workCountry}">selected</c:if>>Bolivia, Plurinational State of</option>
							<option value="BES"  <c:if test="${'BES'==model.workCountry}">selected</c:if>>Bonaire, Sint Eustatius and Saba</option>
							<option value="BIH"  <c:if test="${'BIH'==model.workCountry}">selected</c:if>>Bosnia and Herzegovina</option>
							<option value="BWA"  <c:if test="${'BWA'==model.workCountry}">selected</c:if>>Botswana</option>
							<option value="BVT"  <c:if test="${'BVT'==model.workCountry}">selected</c:if>>Bouvet Island</option>
							<option value="BRA"  <c:if test="${'BRA'==model.workCountry}">selected</c:if>>Brazil</option>
							<option value="IOT"  <c:if test="${'IOT'==model.workCountry}">selected</c:if>>British Indian Ocean Territory</option>
							<option value="BRN"  <c:if test="${'BRN'==model.workCountry}">selected</c:if>>Brunei Darussalam</option>
							<option value="BGR"  <c:if test="${'BGR'==model.workCountry}">selected</c:if>>Bulgaria</option>
							<option value="BFA"  <c:if test="${'BFA'==model.workCountry}">selected</c:if>>Burkina Faso</option>
							<option value="BDI"  <c:if test="${'BDI'==model.workCountry}">selected</c:if>>Burundi</option>
							<option value="KHM"  <c:if test="${'KHM'==model.workCountry}">selected</c:if>>Cambodia</option>
							<option value="CMR"  <c:if test="${'CMR'==model.workCountry}">selected</c:if>>Cameroon</option>
							<option value="CAN"  <c:if test="${'CAN'==model.workCountry}">selected</c:if>>Canada</option>
							<option value="CPV"  <c:if test="${'CPV'==model.workCountry}">selected</c:if>>Cape Verde</option>
							<option value="CYM"  <c:if test="${'CYM'==model.workCountry}">selected</c:if>>Cayman Islands</option>
							<option value="CAF"  <c:if test="${'CAF'==model.workCountry}">selected</c:if>>Central African Republic</option>
							<option value="TCD"  <c:if test="${'TCD'==model.workCountry}">selected</c:if>>Chad</option>
							<option value="CHL"  <c:if test="${'CHL'==model.workCountry}">selected</c:if>>Chile</option>
							<option value="CHN"  <c:if test="${'CHN'==model.workCountry}">selected</c:if>>China</option>
							<option value="CXR"  <c:if test="${'CXR'==model.workCountry}">selected</c:if>>Christmas Island</option>
							<option value="CCK"  <c:if test="${'CCK'==model.workCountry}">selected</c:if>>Cocos (Keeling) Islands</option>
							<option value="COL"  <c:if test="${'COL'==model.workCountry}">selected</c:if>>Colombia</option>
							<option value="COM"  <c:if test="${'COM'==model.workCountry}">selected</c:if>>Comoros</option>
							<option value="COG"  <c:if test="${'COG'==model.workCountry}">selected</c:if>>Congo</option>
							<option value="COD"  <c:if test="${'COD'==model.workCountry}">selected</c:if>>Congo, the Democratic Republic of the</option>
							<option value="COK"  <c:if test="${'COK'==model.workCountry}">selected</c:if>>Cook Islands</option>
							<option value="CRI"  <c:if test="${'CRI'==model.workCountry}">selected</c:if>>Costa Rica</option>
							<option value="CIV"  <c:if test="${'CIV'==model.workCountry}">selected</c:if>>Côte d'Ivoire</option>
							<option value="HRV"  <c:if test="${'HRV'==model.workCountry}">selected</c:if>>Croatia</option>
							<option value="CUB"  <c:if test="${'CUB'==model.workCountry}">selected</c:if>>Cuba</option>
							<option value="CUW"  <c:if test="${'CUW'==model.workCountry}">selected</c:if>>Curaçao</option>
							<option value="CYP"  <c:if test="${'CYP'==model.workCountry}">selected</c:if>>Cyprus</option>
							<option value="CZE"  <c:if test="${'CZE'==model.workCountry}">selected</c:if>>Czech Republic</option>
							<option value="DNK"  <c:if test="${'DNK'==model.workCountry}">selected</c:if>>Denmark</option>
							<option value="DJI"  <c:if test="${'DJI'==model.workCountry}">selected</c:if>>Djibouti</option>
							<option value="DMA"  <c:if test="${'DMA'==model.workCountry}">selected</c:if>>Dominica</option>
							<option value="DOM"  <c:if test="${'DOM'==model.workCountry}">selected</c:if>>Dominican Republic</option>
							<option value="ECU"  <c:if test="${'ECU'==model.workCountry}">selected</c:if>>Ecuador</option>
							<option value="EGY"  <c:if test="${'EGY'==model.workCountry}">selected</c:if>>Egypt</option>
							<option value="SLV"  <c:if test="${'SLV'==model.workCountry}">selected</c:if>>El Salvador</option>
							<option value="GNQ"  <c:if test="${'GNQ'==model.workCountry}">selected</c:if>>Equatorial Guinea</option>
							<option value="ERI"  <c:if test="${'ERI'==model.workCountry}">selected</c:if>>Eritrea</option>
							<option value="EST"  <c:if test="${'EST'==model.workCountry}">selected</c:if>>Estonia</option>
							<option value="ETH"  <c:if test="${'ETH'==model.workCountry}">selected</c:if>>Ethiopia</option>
							<option value="FLK"  <c:if test="${'FLK'==model.workCountry}">selected</c:if>>Falkland Islands (Malvinas)</option>
							<option value="FRO"  <c:if test="${'FRO'==model.workCountry}">selected</c:if>>Faroe Islands</option>
							<option value="FJI"  <c:if test="${'FJI'==model.workCountry}">selected</c:if>>Fiji</option>
							<option value="FIN"  <c:if test="${'FIN'==model.workCountry}">selected</c:if>>Finland</option>
							<option value="FRA"  <c:if test="${'FRA'==model.workCountry}">selected</c:if>>France</option>
							<option value="GUF"  <c:if test="${'GUF'==model.workCountry}">selected</c:if>>French Guiana</option>
							<option value="PYF"  <c:if test="${'PYF'==model.workCountry}">selected</c:if>>French Polynesia</option>
							<option value="ATF"  <c:if test="${'ATF'==model.workCountry}">selected</c:if>>French Southern Territories</option>
							<option value="GAB"  <c:if test="${'GAB'==model.workCountry}">selected</c:if>>Gabon</option>
							<option value="GMB"  <c:if test="${'GMB'==model.workCountry}">selected</c:if>>Gambia</option>
							<option value="GEO"  <c:if test="${'GEO'==model.workCountry}">selected</c:if>>Georgia</option>
							<option value="DEU"  <c:if test="${'DEU'==model.workCountry}">selected</c:if>>Germany</option>
							<option value="GHA"  <c:if test="${'GHA'==model.workCountry}">selected</c:if>>Ghana</option>
							<option value="GIB"  <c:if test="${'GIB'==model.workCountry}">selected</c:if>>Gibraltar</option>
							<option value="GRC"  <c:if test="${'GRC'==model.workCountry}">selected</c:if>>Greece</option>
							<option value="GRL"  <c:if test="${'GRL'==model.workCountry}">selected</c:if>>Greenland</option>
							<option value="GRD"  <c:if test="${'GRD'==model.workCountry}">selected</c:if>>Grenada</option>
							<option value="GLP"  <c:if test="${'GLP'==model.workCountry}">selected</c:if>>Guadeloupe</option>
							<option value="GUM"  <c:if test="${'GUM'==model.workCountry}">selected</c:if>>Guam</option>
							<option value="GTM"  <c:if test="${'GTM'==model.workCountry}">selected</c:if>>Guatemala</option>
							<option value="GGY"  <c:if test="${'GGY'==model.workCountry}">selected</c:if>>Guernsey</option>
							<option value="GIN"  <c:if test="${'GIN'==model.workCountry}">selected</c:if>>Guinea</option>
							<option value="GNB"  <c:if test="${'GNB'==model.workCountry}">selected</c:if>>Guinea-Bissau</option>
							<option value="GUY"  <c:if test="${'GUY'==model.workCountry}">selected</c:if>>Guyana</option>
							<option value="HTI"  <c:if test="${'HTI'==model.workCountry}">selected</c:if>>Haiti</option>
							<option value="HMD"  <c:if test="${'HMD'==model.workCountry}">selected</c:if>>Heard Island and McDonald Islands</option>
							<option value="VAT"  <c:if test="${'VAT'==model.workCountry}">selected</c:if>>Holy See (Vatican City State)</option>
							<option value="HND"  <c:if test="${'HND'==model.workCountry}">selected</c:if>>Honduras</option>
							<option value="HKG"  <c:if test="${'HKG'==model.workCountry}">selected</c:if>>Hong Kong</option>
							<option value="HUN"  <c:if test="${'HUN'==model.workCountry}">selected</c:if>>Hungary</option>
							<option value="ISL"  <c:if test="${'ISL'==model.workCountry}">selected</c:if>>Iceland</option>
							<option value="IND"  <c:if test="${'IND'==model.workCountry}">selected</c:if>>India</option>
							<option value="IDN"  <c:if test="${'IDN'==model.workCountry}">selected</c:if>>Indonesia</option>
							<option value="IRN"  <c:if test="${'IRN'==model.workCountry}">selected</c:if>>Iran, Islamic Republic of</option>
							<option value="IRQ"  <c:if test="${'IRQ'==model.workCountry}">selected</c:if>>Iraq</option>
							<option value="IRL"  <c:if test="${'IRL'==model.workCountry}">selected</c:if>>Ireland</option>
							<option value="IMN"  <c:if test="${'IMN'==model.workCountry}">selected</c:if>>Isle of Man</option>
							<option value="ISR"  <c:if test="${'ISR'==model.workCountry}">selected</c:if>>Israel</option>
							<option value="ITA"  <c:if test="${'ITA'==model.workCountry}">selected</c:if>>Italy</option>
							<option value="JAM"  <c:if test="${'JAM'==model.workCountry}">selected</c:if>>Jamaica</option>
							<option value="JPN"  <c:if test="${'JPN'==model.workCountry}">selected</c:if>>Japan</option>
							<option value="JEY"  <c:if test="${'JEY'==model.workCountry}">selected</c:if>>Jersey</option>
							<option value="JOR"  <c:if test="${'JOR'==model.workCountry}">selected</c:if>>Jordan</option>
							<option value="KAZ"  <c:if test="${'KAZ'==model.workCountry}">selected</c:if>>Kazakhstan</option>
							<option value="KEN"  <c:if test="${'KEN'==model.workCountry}">selected</c:if>>Kenya</option>
							<option value="KIR"  <c:if test="${'KIR'==model.workCountry}">selected</c:if>>Kiribati</option>
							<option value="PRK"  <c:if test="${'PRK'==model.workCountry}">selected</c:if>>Korea, Democratic People's Republic of</option>
							<option value="KOR"  <c:if test="${'KOR'==model.workCountry}">selected</c:if>>Korea, Republic of</option>
							<option value="KWT"  <c:if test="${'KWT'==model.workCountry}">selected</c:if>>Kuwait</option>
							<option value="KGZ"  <c:if test="${'KGZ'==model.workCountry}">selected</c:if>>Kyrgyzstan</option>
							<option value="LAO"  <c:if test="${'LAO'==model.workCountry}">selected</c:if>>Lao People's Democratic Republic</option>
							<option value="LVA"  <c:if test="${'LVA'==model.workCountry}">selected</c:if>>Latvia</option>
							<option value="LBN"  <c:if test="${'LBN'==model.workCountry}">selected</c:if>>Lebanon</option>
							<option value="LSO"  <c:if test="${'LSO'==model.workCountry}">selected</c:if>>Lesotho</option>
							<option value="LBR"  <c:if test="${'LBR'==model.workCountry}">selected</c:if>>Liberia</option>
							<option value="LBY"  <c:if test="${'LBY'==model.workCountry}">selected</c:if>>Libya</option>
							<option value="LIE"  <c:if test="${'LIE'==model.workCountry}">selected</c:if>>Liechtenstein</option>
							<option value="LTU"  <c:if test="${'LTU'==model.workCountry}">selected</c:if>>Lithuania</option>
							<option value="LUX"  <c:if test="${'LUX'==model.workCountry}">selected</c:if>>Luxembourg</option>
							<option value="MAC"  <c:if test="${'MAC'==model.workCountry}">selected</c:if>>Macao</option>
							<option value="MKD"  <c:if test="${'MKD'==model.workCountry}">selected</c:if>>Macedonia, the former Yugoslav Republic of</option>
							<option value="MDG"  <c:if test="${'MDG'==model.workCountry}">selected</c:if>>Madagascar</option>
							<option value="MWI"  <c:if test="${'MWI'==model.workCountry}">selected</c:if>>Malawi</option>
							<option value="MYS"  <c:if test="${'MYS'==model.workCountry}">selected</c:if>>Malaysia</option>
							<option value="MDV"  <c:if test="${'MDV'==model.workCountry}">selected</c:if>>Maldives</option>
							<option value="MLI"  <c:if test="${'MLI'==model.workCountry}">selected</c:if>>Mali</option>
							<option value="MLT"  <c:if test="${'MLT'==model.workCountry}">selected</c:if>>Malta</option>
							<option value="MHL"  <c:if test="${'MHL'==model.workCountry}">selected</c:if>>Marshall Islands</option>
							<option value="MTQ"  <c:if test="${'MTQ'==model.workCountry}">selected</c:if>>Martinique</option>
							<option value="MRT"  <c:if test="${'MRT'==model.workCountry}">selected</c:if>>Mauritania</option>
							<option value="MUS"  <c:if test="${'MUS'==model.workCountry}">selected</c:if>>Mauritius</option>
							<option value="MYT"  <c:if test="${'MYT'==model.workCountry}">selected</c:if>>Mayotte</option>
							<option value="MEX"  <c:if test="${'MEX'==model.workCountry}">selected</c:if>>Mexico</option>
							<option value="FSM"  <c:if test="${'FSM'==model.workCountry}">selected</c:if>>Micronesia, Federated States of</option>
							<option value="MDA"  <c:if test="${'MDA'==model.workCountry}">selected</c:if>>Moldova, Republic of</option>
							<option value="MCO"  <c:if test="${'MCO'==model.workCountry}">selected</c:if>>Monaco</option>
							<option value="MNG"  <c:if test="${'MNG'==model.workCountry}">selected</c:if>>Mongolia</option>
							<option value="MNE"  <c:if test="${'MNE'==model.workCountry}">selected</c:if>>Montenegro</option>
							<option value="MSR"  <c:if test="${'MSR'==model.workCountry}">selected</c:if>>Montserrat</option>
							<option value="MAR"  <c:if test="${'MAR'==model.workCountry}">selected</c:if>>Morocco</option>
							<option value="MOZ"  <c:if test="${'MOZ'==model.workCountry}">selected</c:if>>Mozambique</option>
							<option value="MMR"  <c:if test="${'MMR'==model.workCountry}">selected</c:if>>Myanmar</option>
							<option value="NAM"  <c:if test="${'NAM'==model.workCountry}">selected</c:if>>Namibia</option>
							<option value="NRU"  <c:if test="${'NRU'==model.workCountry}">selected</c:if>>Nauru</option>
							<option value="NPL"  <c:if test="${'NPL'==model.workCountry}">selected</c:if>>Nepal</option>
							<option value="NLD"  <c:if test="${'NLD'==model.workCountry}">selected</c:if>>Netherlands</option>
							<option value="NCL"  <c:if test="${'NCL'==model.workCountry}">selected</c:if>>New Caledonia</option>
							<option value="NZL"  <c:if test="${'NZL'==model.workCountry}">selected</c:if>>New Zealand</option>
							<option value="NIC"  <c:if test="${'NIC'==model.workCountry}">selected</c:if>>Nicaragua</option>
							<option value="NER"  <c:if test="${'NER'==model.workCountry}">selected</c:if>>Niger</option>
							<option value="NGA"  <c:if test="${'NGA'==model.workCountry}">selected</c:if>>Nigeria</option>
							<option value="NIU"  <c:if test="${'NIU'==model.workCountry}">selected</c:if>>Niue</option>
							<option value="NFK"  <c:if test="${'NFK'==model.workCountry}">selected</c:if>>Norfolk Island</option>
							<option value="MNP"  <c:if test="${'MNP'==model.workCountry}">selected</c:if>>Northern Mariana Islands</option>
							<option value="NOR"  <c:if test="${'NOR'==model.workCountry}">selected</c:if>>Norway</option>
							<option value="OMN"  <c:if test="${'OMN'==model.workCountry}">selected</c:if>>Oman</option>
							<option value="PAK"  <c:if test="${'PAK'==model.workCountry}">selected</c:if>>Pakistan</option>
							<option value="PLW"  <c:if test="${'PLW'==model.workCountry}">selected</c:if>>Palau</option>
							<option value="PSE"  <c:if test="${'PSE'==model.workCountry}">selected</c:if>>Palestinian Territory, Occupied</option>
							<option value="PAN"  <c:if test="${'PAN'==model.workCountry}">selected</c:if>>Panama</option>
							<option value="PNG"  <c:if test="${'PNG'==model.workCountry}">selected</c:if>>Papua New Guinea</option>
							<option value="PRY"  <c:if test="${'PRY'==model.workCountry}">selected</c:if>>Paraguay</option>
							<option value="PER"  <c:if test="${'PER'==model.workCountry}">selected</c:if>>Peru</option>
							<option value="PHL"  <c:if test="${'PHL'==model.workCountry}">selected</c:if>>Philippines</option>
							<option value="PCN"  <c:if test="${'PCN'==model.workCountry}">selected</c:if>>Pitcairn</option>
							<option value="POL"  <c:if test="${'POL'==model.workCountry}">selected</c:if>>Poland</option>
							<option value="PRT"  <c:if test="${'PRT'==model.workCountry}">selected</c:if>>Portugal</option>
							<option value="PRI"  <c:if test="${'PRI'==model.workCountry}">selected</c:if>>Puerto Rico</option>
							<option value="QAT"  <c:if test="${'QAT'==model.workCountry}">selected</c:if>>Qatar</option>
							<option value="REU"  <c:if test="${'REU'==model.workCountry}">selected</c:if>>Réunion</option>
							<option value="ROU"  <c:if test="${'ROU'==model.workCountry}">selected</c:if>>Romania</option>
							<option value="RUS"  <c:if test="${'RUS'==model.workCountry}">selected</c:if>>Russian Federation</option>
							<option value="RWA"  <c:if test="${'RWA'==model.workCountry}">selected</c:if>>Rwanda</option>
							<option value="BLM"  <c:if test="${'BLM'==model.workCountry}">selected</c:if>>Saint Barthélemy</option>
							<option value="SHN"  <c:if test="${'SHN'==model.workCountry}">selected</c:if>>Saint Helena, Ascension and Tristan da Cunha</option>
							<option value="KNA"  <c:if test="${'KNA'==model.workCountry}">selected</c:if>>Saint Kitts and Nevis</option>
							<option value="LCA"  <c:if test="${'LCA'==model.workCountry}">selected</c:if>>Saint Lucia</option>
							<option value="MAF"  <c:if test="${'MAF'==model.workCountry}">selected</c:if>>Saint Martin (French part)</option>
							<option value="SPM"  <c:if test="${'SPM'==model.workCountry}">selected</c:if>>Saint Pierre and Miquelon</option>
							<option value="VCT"  <c:if test="${'VCT'==model.workCountry}">selected</c:if>>Saint Vincent and the Grenadines</option>
							<option value="WSM"  <c:if test="${'WSM'==model.workCountry}">selected</c:if>>Samoa</option>
							<option value="SMR"  <c:if test="${'SMR'==model.workCountry}">selected</c:if>>San Marino</option>
							<option value="STP"  <c:if test="${'STP'==model.workCountry}">selected</c:if>>Sao Tome and Principe</option>
							<option value="SAU"  <c:if test="${'SAU'==model.workCountry}">selected</c:if>>Saudi Arabia</option>
							<option value="SEN"  <c:if test="${'SEN'==model.workCountry}">selected</c:if>>Senegal</option>
							<option value="SRB"  <c:if test="${'SRB'==model.workCountry}">selected</c:if>>Serbia</option>
							<option value="SYC"  <c:if test="${'SYC'==model.workCountry}">selected</c:if>>Seychelles</option>
							<option value="SLE"  <c:if test="${'SLE'==model.workCountry}">selected</c:if>>Sierra Leone</option>
							<option value="SGP"  <c:if test="${'SGP'==model.workCountry}">selected</c:if>>Singapore</option>
							<option value="SXM"  <c:if test="${'SXM'==model.workCountry}">selected</c:if>>Sint Maarten (Dutch part)</option>
							<option value="SVK"  <c:if test="${'SVK'==model.workCountry}">selected</c:if>>Slovakia</option>
							<option value="SVN"  <c:if test="${'SVN'==model.workCountry}">selected</c:if>>Slovenia</option>
							<option value="SLB"  <c:if test="${'SLB'==model.workCountry}">selected</c:if>>Solomon Islands</option>
							<option value="SOM"  <c:if test="${'SOM'==model.workCountry}">selected</c:if>>Somalia</option>
							<option value="ZAF"  <c:if test="${'ZAF'==model.workCountry}">selected</c:if>>South Africa</option>
							<option value="SGS"  <c:if test="${'SGS'==model.workCountry}">selected</c:if>>South Georgia and the South Sandwich Islands</option>
							<option value="SSD"  <c:if test="${'SSD'==model.workCountry}">selected</c:if>>South Sudan</option>
							<option value="ESP"  <c:if test="${'ESP'==model.workCountry}">selected</c:if>>Spain</option>
							<option value="LKA"  <c:if test="${'LKA'==model.workCountry}">selected</c:if>>Sri Lanka</option>
							<option value="SDN"  <c:if test="${'SDN'==model.workCountry}">selected</c:if>>Sudan</option>
							<option value="SUR"  <c:if test="${'SUR'==model.workCountry}">selected</c:if>>Suriname</option>
							<option value="SJM"  <c:if test="${'SJM'==model.workCountry}">selected</c:if>>Svalbard and Jan Mayen</option>
							<option value="SWZ"  <c:if test="${'SWZ'==model.workCountry}">selected</c:if>>Swaziland</option>
							<option value="SWE"  <c:if test="${'SWE'==model.workCountry}">selected</c:if>>Sweden</option>
							<option value="CHE"  <c:if test="${'CHE'==model.workCountry}">selected</c:if>>Switzerland</option>
							<option value="SYR"  <c:if test="${'SYR'==model.workCountry}">selected</c:if>>Syrian Arab Republic</option>
							<option value="TWN"  <c:if test="${'TWN'==model.workCountry}">selected</c:if>>Taiwan, Province of China</option>
							<option value="TJK"  <c:if test="${'TJK'==model.workCountry}">selected</c:if>>Tajikistan</option>
							<option value="TZA"  <c:if test="${'TZA'==model.workCountry}">selected</c:if>>Tanzania, United Republic of</option>
							<option value="THA"  <c:if test="${'THA'==model.workCountry}">selected</c:if>>Thailand</option>
							<option value="TLS"  <c:if test="${'TLS'==model.workCountry}">selected</c:if>>Timor-Leste</option>
							<option value="TGO"  <c:if test="${'TGO'==model.workCountry}">selected</c:if>>Togo</option>
							<option value="TKL"  <c:if test="${'TKL'==model.workCountry}">selected</c:if>>Tokelau</option>
							<option value="TON"  <c:if test="${'TON'==model.workCountry}">selected</c:if>>Tonga</option>
							<option value="TTO"  <c:if test="${'TTO'==model.workCountry}">selected</c:if>>Trinidad and Tobago</option>
							<option value="TUN"  <c:if test="${'TUN'==model.workCountry}">selected</c:if>>Tunisia</option>
							<option value="TUR"  <c:if test="${'TUR'==model.workCountry}">selected</c:if>>Turkey</option>
							<option value="TKM"  <c:if test="${'TKM'==model.workCountry}">selected</c:if>>Turkmenistan</option>
							<option value="TCA"  <c:if test="${'TCA'==model.workCountry}">selected</c:if>>Turks and Caicos Islands</option>
							<option value="TUV"  <c:if test="${'TUV'==model.workCountry}">selected</c:if>>Tuvalu</option>
							<option value="UGA"  <c:if test="${'UGA'==model.workCountry}">selected</c:if>>Uganda</option>
							<option value="UKR"  <c:if test="${'UKR'==model.workCountry}">selected</c:if>>Ukraine</option>
							<option value="ARE"  <c:if test="${'ARE'==model.workCountry}">selected</c:if>>United Arab Emirates</option>
							<option value="GBR"  <c:if test="${'GBR'==model.workCountry}">selected</c:if>>United Kingdom</option>
							<option value="USA"  <c:if test="${'USA'==model.workCountry}">selected</c:if>>United States</option>
							<option value="UMI"  <c:if test="${'UMI'==model.workCountry}">selected</c:if>>United States Minor Outlying Islands</option>
							<option value="URY"  <c:if test="${'URY'==model.workCountry}">selected</c:if>>Uruguay</option>
							<option value="UZB"  <c:if test="${'UZB'==model.workCountry}">selected</c:if>>Uzbekistan</option>
							<option value="VUT"  <c:if test="${'VUT'==model.workCountry}">selected</c:if>>Vanuatu</option>
							<option value="VEN"  <c:if test="${'VEN'==model.workCountry}">selected</c:if>>Venezuela, Bolivarian Republic of</option>
							<option value="VNM"  <c:if test="${'VNM'==model.workCountry}">selected</c:if>>Viet Nam</option>
							<option value="VGB"  <c:if test="${'VGB'==model.workCountry}">selected</c:if>>Virgin Islands, British</option>
							<option value="VIR"  <c:if test="${'VIR'==model.workCountry}">selected</c:if>>Virgin Islands, U.S.</option>
							<option value="WLF"  <c:if test="${'WLF'==model.workCountry}">selected</c:if>>Wallis and Futuna</option>
							<option value="ESH"  <c:if test="${'ESH'==model.workCountry}">selected</c:if>>Western Sahara</option>
							<option value="YEM"  <c:if test="${'YEM'==model.workCountry}">selected</c:if>>Yemen</option>
							<option value="ZMB"  <c:if test="${'ZMB'==model.workCountry}">selected</c:if>>Zambia</option>
							<option value="ZWE"  <c:if test="${'ZWE'==model.workCountry}">selected</c:if>>Zimbabwe</option>
						</select>
						<label for="workCountry"></label>
					</td>
					<th><s:Locale code="userinfo.workRegion" />：</th>
					<td>
						<input class="form-control"  type="text" id="workRegion" name="workRegion"  title="" value="${model.workRegion}"/>
						<label for="province"></label>
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.workLocality" />：</th>
					<td>
						<input class="form-control"  type="text" id="workLocality" name="workLocality"  title="" value="${model.workLocality}"/>
						<label for="workLocality"></label>
					</td>
					<th><s:Locale code="userinfo.workStreetAddress" />：</th>
					<td>
						<input class="form-control"  type="text" id="workStreetAddress" name="workStreetAddress"  title="" value="${model.workStreetAddress}"/>
						<label for="street"></label>
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.workPostalCode" />：</th>
					<td>
						<input class="form-control"  type="text" id="workPostalCode" name="workPostalCode"  title="" value="${model.workPostalCode}"/>
						<label for="workPostalCode"></label>
					</td>
					<th><s:Locale code="userinfo.workFax" />：</th>
					<td>
						<input class="form-control"  type="text" id="workFax" name="workFax"  title="" value="${model.workFax}"/>
						<label for="workFax"></label>
					</td>
				</tr>

				<tr>
					<th><s:Locale code="userinfo.workPhoneNumber" />：</th>
					<td>
						<input class="form-control"  type="text" id="workPhoneNumber" name="workPhoneNumber"  title="" value="${model.workPhoneNumber}"/>
						<label for="workPhoneNumber"></label>
					</td>
					<th><s:Locale code="userinfo.workEmail" />：</th>
					<td>
						<input class="form-control"  type="text" id="workEmail" name="workEmail"  title="" value="${model.workEmail}"/>
						<label for="workEmail"></label>
					</td>
					
				</tr>
				<tr>
					<td colspan="4">&nbsp;
					</td>
					
				</tr>
				
				<tr>
					<th><s:Locale code="userinfo.homeCountry" />：</th>
					<td>
						<select  class="form-control"  id="homeCountry" name="homeCountry">
							<option value="AFG"  <c:if test="${'AFG'==model.homeCountry}">selected</c:if>>Afghanistan</option>
							<option value="ALA"  <c:if test="${'ALA'==model.homeCountry}">selected</c:if>>Åland Islands</option>
							<option value="ALB"  <c:if test="${'ALB'==model.homeCountry}">selected</c:if>>Albania</option>
							<option value="DZA"  <c:if test="${'DZA'==model.homeCountry}">selected</c:if>>Algeria</option>
							<option value="ASM"  <c:if test="${'ASM'==model.homeCountry}">selected</c:if>>American Samoa</option>
							<option value="AND"  <c:if test="${'AND'==model.homeCountry}">selected</c:if>>Andorra</option>
							<option value="AGO"  <c:if test="${'AGO'==model.homeCountry}">selected</c:if>>Angola</option>
							<option value="AIA"  <c:if test="${'AIA'==model.homeCountry}">selected</c:if>>Anguilla</option>
							<option value="ATA"  <c:if test="${'ATA'==model.homeCountry}">selected</c:if>>Antarctica</option>
							<option value="ATG"  <c:if test="${'ATG'==model.homeCountry}">selected</c:if>>Antigua and Barbuda</option>
							<option value="ARG"  <c:if test="${'ARG'==model.homeCountry}">selected</c:if>>Argentina</option>
							<option value="ARM"  <c:if test="${'ARM'==model.homeCountry}">selected</c:if>>Armenia</option>
							<option value="ABW"  <c:if test="${'ABW'==model.homeCountry}">selected</c:if>>Aruba</option>
							<option value="AUS"  <c:if test="${'AUS'==model.homeCountry}">selected</c:if>>Australia</option>
							<option value="AUT"  <c:if test="${'AUT'==model.homeCountry}">selected</c:if>>Austria</option>
							<option value="AZE"  <c:if test="${'AZE'==model.homeCountry}">selected</c:if>>Azerbaijan</option>
							<option value="BHS"  <c:if test="${'BHS'==model.homeCountry}">selected</c:if>>Bahamas</option>
							<option value="BHR"  <c:if test="${'BHR'==model.homeCountry}">selected</c:if>>Bahrain</option>
							<option value="BGD"  <c:if test="${'BGD'==model.homeCountry}">selected</c:if>>Bangladesh</option>
							<option value="BRB"  <c:if test="${'BRB'==model.homeCountry}">selected</c:if>>Barbados</option>
							<option value="BLR"  <c:if test="${'BLR'==model.homeCountry}">selected</c:if>>Belarus</option>
							<option value="BEL"  <c:if test="${'BEL'==model.homeCountry}">selected</c:if>>Belgium</option>
							<option value="BLZ"  <c:if test="${'BLZ'==model.homeCountry}">selected</c:if>>Belize</option>
							<option value="BEN"  <c:if test="${'BEN'==model.homeCountry}">selected</c:if>>Benin</option>
							<option value="BMU"  <c:if test="${'BMU'==model.homeCountry}">selected</c:if>>Bermuda</option>
							<option value="BTN"  <c:if test="${'BTN'==model.homeCountry}">selected</c:if>>Bhutan</option>
							<option value="BOL"  <c:if test="${'BOL'==model.homeCountry}">selected</c:if>>Bolivia, Plurinational State of</option>
							<option value="BES"  <c:if test="${'BES'==model.homeCountry}">selected</c:if>>Bonaire, Sint Eustatius and Saba</option>
							<option value="BIH"  <c:if test="${'BIH'==model.homeCountry}">selected</c:if>>Bosnia and Herzegovina</option>
							<option value="BWA"  <c:if test="${'BWA'==model.homeCountry}">selected</c:if>>Botswana</option>
							<option value="BVT"  <c:if test="${'BVT'==model.homeCountry}">selected</c:if>>Bouvet Island</option>
							<option value="BRA"  <c:if test="${'BRA'==model.homeCountry}">selected</c:if>>Brazil</option>
							<option value="IOT"  <c:if test="${'IOT'==model.homeCountry}">selected</c:if>>British Indian Ocean Territory</option>
							<option value="BRN"  <c:if test="${'BRN'==model.homeCountry}">selected</c:if>>Brunei Darussalam</option>
							<option value="BGR"  <c:if test="${'BGR'==model.homeCountry}">selected</c:if>>Bulgaria</option>
							<option value="BFA"  <c:if test="${'BFA'==model.homeCountry}">selected</c:if>>Burkina Faso</option>
							<option value="BDI"  <c:if test="${'BDI'==model.homeCountry}">selected</c:if>>Burundi</option>
							<option value="KHM"  <c:if test="${'KHM'==model.homeCountry}">selected</c:if>>Cambodia</option>
							<option value="CMR"  <c:if test="${'CMR'==model.homeCountry}">selected</c:if>>Cameroon</option>
							<option value="CAN"  <c:if test="${'CAN'==model.homeCountry}">selected</c:if>>Canada</option>
							<option value="CPV"  <c:if test="${'CPV'==model.homeCountry}">selected</c:if>>Cape Verde</option>
							<option value="CYM"  <c:if test="${'CYM'==model.homeCountry}">selected</c:if>>Cayman Islands</option>
							<option value="CAF"  <c:if test="${'CAF'==model.homeCountry}">selected</c:if>>Central African Republic</option>
							<option value="TCD"  <c:if test="${'TCD'==model.homeCountry}">selected</c:if>>Chad</option>
							<option value="CHL"  <c:if test="${'CHL'==model.homeCountry}">selected</c:if>>Chile</option>
							<option value="CHN"  <c:if test="${'CHN'==model.homeCountry}">selected</c:if>>China</option>
							<option value="CXR"  <c:if test="${'CXR'==model.homeCountry}">selected</c:if>>Christmas Island</option>
							<option value="CCK"  <c:if test="${'CCK'==model.homeCountry}">selected</c:if>>Cocos (Keeling) Islands</option>
							<option value="COL"  <c:if test="${'COL'==model.homeCountry}">selected</c:if>>Colombia</option>
							<option value="COM"  <c:if test="${'COM'==model.homeCountry}">selected</c:if>>Comoros</option>
							<option value="COG"  <c:if test="${'COG'==model.homeCountry}">selected</c:if>>Congo</option>
							<option value="COD"  <c:if test="${'COD'==model.homeCountry}">selected</c:if>>Congo, the Democratic Republic of the</option>
							<option value="COK"  <c:if test="${'COK'==model.homeCountry}">selected</c:if>>Cook Islands</option>
							<option value="CRI"  <c:if test="${'CRI'==model.homeCountry}">selected</c:if>>Costa Rica</option>
							<option value="CIV"  <c:if test="${'CIV'==model.homeCountry}">selected</c:if>>Côte d'Ivoire</option>
							<option value="HRV"  <c:if test="${'HRV'==model.homeCountry}">selected</c:if>>Croatia</option>
							<option value="CUB"  <c:if test="${'CUB'==model.homeCountry}">selected</c:if>>Cuba</option>
							<option value="CUW"  <c:if test="${'CUW'==model.homeCountry}">selected</c:if>>Curaçao</option>
							<option value="CYP"  <c:if test="${'CYP'==model.homeCountry}">selected</c:if>>Cyprus</option>
							<option value="CZE"  <c:if test="${'CZE'==model.homeCountry}">selected</c:if>>Czech Republic</option>
							<option value="DNK"  <c:if test="${'DNK'==model.homeCountry}">selected</c:if>>Denmark</option>
							<option value="DJI"  <c:if test="${'DJI'==model.homeCountry}">selected</c:if>>Djibouti</option>
							<option value="DMA"  <c:if test="${'DMA'==model.homeCountry}">selected</c:if>>Dominica</option>
							<option value="DOM"  <c:if test="${'DOM'==model.homeCountry}">selected</c:if>>Dominican Republic</option>
							<option value="ECU"  <c:if test="${'ECU'==model.homeCountry}">selected</c:if>>Ecuador</option>
							<option value="EGY"  <c:if test="${'EGY'==model.homeCountry}">selected</c:if>>Egypt</option>
							<option value="SLV"  <c:if test="${'SLV'==model.homeCountry}">selected</c:if>>El Salvador</option>
							<option value="GNQ"  <c:if test="${'GNQ'==model.homeCountry}">selected</c:if>>Equatorial Guinea</option>
							<option value="ERI"  <c:if test="${'ERI'==model.homeCountry}">selected</c:if>>Eritrea</option>
							<option value="EST"  <c:if test="${'EST'==model.homeCountry}">selected</c:if>>Estonia</option>
							<option value="ETH"  <c:if test="${'ETH'==model.homeCountry}">selected</c:if>>Ethiopia</option>
							<option value="FLK"  <c:if test="${'FLK'==model.homeCountry}">selected</c:if>>Falkland Islands (Malvinas)</option>
							<option value="FRO"  <c:if test="${'FRO'==model.homeCountry}">selected</c:if>>Faroe Islands</option>
							<option value="FJI"  <c:if test="${'FJI'==model.homeCountry}">selected</c:if>>Fiji</option>
							<option value="FIN"  <c:if test="${'FIN'==model.homeCountry}">selected</c:if>>Finland</option>
							<option value="FRA"  <c:if test="${'FRA'==model.homeCountry}">selected</c:if>>France</option>
							<option value="GUF"  <c:if test="${'GUF'==model.homeCountry}">selected</c:if>>French Guiana</option>
							<option value="PYF"  <c:if test="${'PYF'==model.homeCountry}">selected</c:if>>French Polynesia</option>
							<option value="ATF"  <c:if test="${'ATF'==model.homeCountry}">selected</c:if>>French Southern Territories</option>
							<option value="GAB"  <c:if test="${'GAB'==model.homeCountry}">selected</c:if>>Gabon</option>
							<option value="GMB"  <c:if test="${'GMB'==model.homeCountry}">selected</c:if>>Gambia</option>
							<option value="GEO"  <c:if test="${'GEO'==model.homeCountry}">selected</c:if>>Georgia</option>
							<option value="DEU"  <c:if test="${'DEU'==model.homeCountry}">selected</c:if>>Germany</option>
							<option value="GHA"  <c:if test="${'GHA'==model.homeCountry}">selected</c:if>>Ghana</option>
							<option value="GIB"  <c:if test="${'GIB'==model.homeCountry}">selected</c:if>>Gibraltar</option>
							<option value="GRC"  <c:if test="${'GRC'==model.homeCountry}">selected</c:if>>Greece</option>
							<option value="GRL"  <c:if test="${'GRL'==model.homeCountry}">selected</c:if>>Greenland</option>
							<option value="GRD"  <c:if test="${'GRD'==model.homeCountry}">selected</c:if>>Grenada</option>
							<option value="GLP"  <c:if test="${'GLP'==model.homeCountry}">selected</c:if>>Guadeloupe</option>
							<option value="GUM"  <c:if test="${'GUM'==model.homeCountry}">selected</c:if>>Guam</option>
							<option value="GTM"  <c:if test="${'GTM'==model.homeCountry}">selected</c:if>>Guatemala</option>
							<option value="GGY"  <c:if test="${'GGY'==model.homeCountry}">selected</c:if>>Guernsey</option>
							<option value="GIN"  <c:if test="${'GIN'==model.homeCountry}">selected</c:if>>Guinea</option>
							<option value="GNB"  <c:if test="${'GNB'==model.homeCountry}">selected</c:if>>Guinea-Bissau</option>
							<option value="GUY"  <c:if test="${'GUY'==model.homeCountry}">selected</c:if>>Guyana</option>
							<option value="HTI"  <c:if test="${'HTI'==model.homeCountry}">selected</c:if>>Haiti</option>
							<option value="HMD"  <c:if test="${'HMD'==model.homeCountry}">selected</c:if>>Heard Island and McDonald Islands</option>
							<option value="VAT"  <c:if test="${'VAT'==model.homeCountry}">selected</c:if>>Holy See (Vatican City State)</option>
							<option value="HND"  <c:if test="${'HND'==model.homeCountry}">selected</c:if>>Honduras</option>
							<option value="HKG"  <c:if test="${'HKG'==model.homeCountry}">selected</c:if>>Hong Kong</option>
							<option value="HUN"  <c:if test="${'HUN'==model.homeCountry}">selected</c:if>>Hungary</option>
							<option value="ISL"  <c:if test="${'ISL'==model.homeCountry}">selected</c:if>>Iceland</option>
							<option value="IND"  <c:if test="${'IND'==model.homeCountry}">selected</c:if>>India</option>
							<option value="IDN"  <c:if test="${'IDN'==model.homeCountry}">selected</c:if>>Indonesia</option>
							<option value="IRN"  <c:if test="${'IRN'==model.homeCountry}">selected</c:if>>Iran, Islamic Republic of</option>
							<option value="IRQ"  <c:if test="${'IRQ'==model.homeCountry}">selected</c:if>>Iraq</option>
							<option value="IRL"  <c:if test="${'IRL'==model.homeCountry}">selected</c:if>>Ireland</option>
							<option value="IMN"  <c:if test="${'IMN'==model.homeCountry}">selected</c:if>>Isle of Man</option>
							<option value="ISR"  <c:if test="${'ISR'==model.homeCountry}">selected</c:if>>Israel</option>
							<option value="ITA"  <c:if test="${'ITA'==model.homeCountry}">selected</c:if>>Italy</option>
							<option value="JAM"  <c:if test="${'JAM'==model.homeCountry}">selected</c:if>>Jamaica</option>
							<option value="JPN"  <c:if test="${'JPN'==model.homeCountry}">selected</c:if>>Japan</option>
							<option value="JEY"  <c:if test="${'JEY'==model.homeCountry}">selected</c:if>>Jersey</option>
							<option value="JOR"  <c:if test="${'JOR'==model.homeCountry}">selected</c:if>>Jordan</option>
							<option value="KAZ"  <c:if test="${'KAZ'==model.homeCountry}">selected</c:if>>Kazakhstan</option>
							<option value="KEN"  <c:if test="${'KEN'==model.homeCountry}">selected</c:if>>Kenya</option>
							<option value="KIR"  <c:if test="${'KIR'==model.homeCountry}">selected</c:if>>Kiribati</option>
							<option value="PRK"  <c:if test="${'PRK'==model.homeCountry}">selected</c:if>>Korea, Democratic People's Republic of</option>
							<option value="KOR"  <c:if test="${'KOR'==model.homeCountry}">selected</c:if>>Korea, Republic of</option>
							<option value="KWT"  <c:if test="${'KWT'==model.homeCountry}">selected</c:if>>Kuwait</option>
							<option value="KGZ"  <c:if test="${'KGZ'==model.homeCountry}">selected</c:if>>Kyrgyzstan</option>
							<option value="LAO"  <c:if test="${'LAO'==model.homeCountry}">selected</c:if>>Lao People's Democratic Republic</option>
							<option value="LVA"  <c:if test="${'LVA'==model.homeCountry}">selected</c:if>>Latvia</option>
							<option value="LBN"  <c:if test="${'LBN'==model.homeCountry}">selected</c:if>>Lebanon</option>
							<option value="LSO"  <c:if test="${'LSO'==model.homeCountry}">selected</c:if>>Lesotho</option>
							<option value="LBR"  <c:if test="${'LBR'==model.homeCountry}">selected</c:if>>Liberia</option>
							<option value="LBY"  <c:if test="${'LBY'==model.homeCountry}">selected</c:if>>Libya</option>
							<option value="LIE"  <c:if test="${'LIE'==model.homeCountry}">selected</c:if>>Liechtenstein</option>
							<option value="LTU"  <c:if test="${'LTU'==model.homeCountry}">selected</c:if>>Lithuania</option>
							<option value="LUX"  <c:if test="${'LUX'==model.homeCountry}">selected</c:if>>Luxembourg</option>
							<option value="MAC"  <c:if test="${'MAC'==model.homeCountry}">selected</c:if>>Macao</option>
							<option value="MKD"  <c:if test="${'MKD'==model.homeCountry}">selected</c:if>>Macedonia, the former Yugoslav Republic of</option>
							<option value="MDG"  <c:if test="${'MDG'==model.homeCountry}">selected</c:if>>Madagascar</option>
							<option value="MWI"  <c:if test="${'MWI'==model.homeCountry}">selected</c:if>>Malawi</option>
							<option value="MYS"  <c:if test="${'MYS'==model.homeCountry}">selected</c:if>>Malaysia</option>
							<option value="MDV"  <c:if test="${'MDV'==model.homeCountry}">selected</c:if>>Maldives</option>
							<option value="MLI"  <c:if test="${'MLI'==model.homeCountry}">selected</c:if>>Mali</option>
							<option value="MLT"  <c:if test="${'MLT'==model.homeCountry}">selected</c:if>>Malta</option>
							<option value="MHL"  <c:if test="${'MHL'==model.homeCountry}">selected</c:if>>Marshall Islands</option>
							<option value="MTQ"  <c:if test="${'MTQ'==model.homeCountry}">selected</c:if>>Martinique</option>
							<option value="MRT"  <c:if test="${'MRT'==model.homeCountry}">selected</c:if>>Mauritania</option>
							<option value="MUS"  <c:if test="${'MUS'==model.homeCountry}">selected</c:if>>Mauritius</option>
							<option value="MYT"  <c:if test="${'MYT'==model.homeCountry}">selected</c:if>>Mayotte</option>
							<option value="MEX"  <c:if test="${'MEX'==model.homeCountry}">selected</c:if>>Mexico</option>
							<option value="FSM"  <c:if test="${'FSM'==model.homeCountry}">selected</c:if>>Micronesia, Federated States of</option>
							<option value="MDA"  <c:if test="${'MDA'==model.homeCountry}">selected</c:if>>Moldova, Republic of</option>
							<option value="MCO"  <c:if test="${'MCO'==model.homeCountry}">selected</c:if>>Monaco</option>
							<option value="MNG"  <c:if test="${'MNG'==model.homeCountry}">selected</c:if>>Mongolia</option>
							<option value="MNE"  <c:if test="${'MNE'==model.homeCountry}">selected</c:if>>Montenegro</option>
							<option value="MSR"  <c:if test="${'MSR'==model.homeCountry}">selected</c:if>>Montserrat</option>
							<option value="MAR"  <c:if test="${'MAR'==model.homeCountry}">selected</c:if>>Morocco</option>
							<option value="MOZ"  <c:if test="${'MOZ'==model.homeCountry}">selected</c:if>>Mozambique</option>
							<option value="MMR"  <c:if test="${'MMR'==model.homeCountry}">selected</c:if>>Myanmar</option>
							<option value="NAM"  <c:if test="${'NAM'==model.homeCountry}">selected</c:if>>Namibia</option>
							<option value="NRU"  <c:if test="${'NRU'==model.homeCountry}">selected</c:if>>Nauru</option>
							<option value="NPL"  <c:if test="${'NPL'==model.homeCountry}">selected</c:if>>Nepal</option>
							<option value="NLD"  <c:if test="${'NLD'==model.homeCountry}">selected</c:if>>Netherlands</option>
							<option value="NCL"  <c:if test="${'NCL'==model.homeCountry}">selected</c:if>>New Caledonia</option>
							<option value="NZL"  <c:if test="${'NZL'==model.homeCountry}">selected</c:if>>New Zealand</option>
							<option value="NIC"  <c:if test="${'NIC'==model.homeCountry}">selected</c:if>>Nicaragua</option>
							<option value="NER"  <c:if test="${'NER'==model.homeCountry}">selected</c:if>>Niger</option>
							<option value="NGA"  <c:if test="${'NGA'==model.homeCountry}">selected</c:if>>Nigeria</option>
							<option value="NIU"  <c:if test="${'NIU'==model.homeCountry}">selected</c:if>>Niue</option>
							<option value="NFK"  <c:if test="${'NFK'==model.homeCountry}">selected</c:if>>Norfolk Island</option>
							<option value="MNP"  <c:if test="${'MNP'==model.homeCountry}">selected</c:if>>Northern Mariana Islands</option>
							<option value="NOR"  <c:if test="${'NOR'==model.homeCountry}">selected</c:if>>Norway</option>
							<option value="OMN"  <c:if test="${'OMN'==model.homeCountry}">selected</c:if>>Oman</option>
							<option value="PAK"  <c:if test="${'PAK'==model.homeCountry}">selected</c:if>>Pakistan</option>
							<option value="PLW"  <c:if test="${'PLW'==model.homeCountry}">selected</c:if>>Palau</option>
							<option value="PSE"  <c:if test="${'PSE'==model.homeCountry}">selected</c:if>>Palestinian Territory, Occupied</option>
							<option value="PAN"  <c:if test="${'PAN'==model.homeCountry}">selected</c:if>>Panama</option>
							<option value="PNG"  <c:if test="${'PNG'==model.homeCountry}">selected</c:if>>Papua New Guinea</option>
							<option value="PRY"  <c:if test="${'PRY'==model.homeCountry}">selected</c:if>>Paraguay</option>
							<option value="PER"  <c:if test="${'PER'==model.homeCountry}">selected</c:if>>Peru</option>
							<option value="PHL"  <c:if test="${'PHL'==model.homeCountry}">selected</c:if>>Philippines</option>
							<option value="PCN"  <c:if test="${'PCN'==model.homeCountry}">selected</c:if>>Pitcairn</option>
							<option value="POL"  <c:if test="${'POL'==model.homeCountry}">selected</c:if>>Poland</option>
							<option value="PRT"  <c:if test="${'PRT'==model.homeCountry}">selected</c:if>>Portugal</option>
							<option value="PRI"  <c:if test="${'PRI'==model.homeCountry}">selected</c:if>>Puerto Rico</option>
							<option value="QAT"  <c:if test="${'QAT'==model.homeCountry}">selected</c:if>>Qatar</option>
							<option value="REU"  <c:if test="${'REU'==model.homeCountry}">selected</c:if>>Réunion</option>
							<option value="ROU"  <c:if test="${'ROU'==model.homeCountry}">selected</c:if>>Romania</option>
							<option value="RUS"  <c:if test="${'RUS'==model.homeCountry}">selected</c:if>>Russian Federation</option>
							<option value="RWA"  <c:if test="${'RWA'==model.homeCountry}">selected</c:if>>Rwanda</option>
							<option value="BLM"  <c:if test="${'BLM'==model.homeCountry}">selected</c:if>>Saint Barthélemy</option>
							<option value="SHN"  <c:if test="${'SHN'==model.homeCountry}">selected</c:if>>Saint Helena, Ascension and Tristan da Cunha</option>
							<option value="KNA"  <c:if test="${'KNA'==model.homeCountry}">selected</c:if>>Saint Kitts and Nevis</option>
							<option value="LCA"  <c:if test="${'LCA'==model.homeCountry}">selected</c:if>>Saint Lucia</option>
							<option value="MAF"  <c:if test="${'MAF'==model.homeCountry}">selected</c:if>>Saint Martin (French part)</option>
							<option value="SPM"  <c:if test="${'SPM'==model.homeCountry}">selected</c:if>>Saint Pierre and Miquelon</option>
							<option value="VCT"  <c:if test="${'VCT'==model.homeCountry}">selected</c:if>>Saint Vincent and the Grenadines</option>
							<option value="WSM"  <c:if test="${'WSM'==model.homeCountry}">selected</c:if>>Samoa</option>
							<option value="SMR"  <c:if test="${'SMR'==model.homeCountry}">selected</c:if>>San Marino</option>
							<option value="STP"  <c:if test="${'STP'==model.homeCountry}">selected</c:if>>Sao Tome and Principe</option>
							<option value="SAU"  <c:if test="${'SAU'==model.homeCountry}">selected</c:if>>Saudi Arabia</option>
							<option value="SEN"  <c:if test="${'SEN'==model.homeCountry}">selected</c:if>>Senegal</option>
							<option value="SRB"  <c:if test="${'SRB'==model.homeCountry}">selected</c:if>>Serbia</option>
							<option value="SYC"  <c:if test="${'SYC'==model.homeCountry}">selected</c:if>>Seychelles</option>
							<option value="SLE"  <c:if test="${'SLE'==model.homeCountry}">selected</c:if>>Sierra Leone</option>
							<option value="SGP"  <c:if test="${'SGP'==model.homeCountry}">selected</c:if>>Singapore</option>
							<option value="SXM"  <c:if test="${'SXM'==model.homeCountry}">selected</c:if>>Sint Maarten (Dutch part)</option>
							<option value="SVK"  <c:if test="${'SVK'==model.homeCountry}">selected</c:if>>Slovakia</option>
							<option value="SVN"  <c:if test="${'SVN'==model.homeCountry}">selected</c:if>>Slovenia</option>
							<option value="SLB"  <c:if test="${'SLB'==model.homeCountry}">selected</c:if>>Solomon Islands</option>
							<option value="SOM"  <c:if test="${'SOM'==model.homeCountry}">selected</c:if>>Somalia</option>
							<option value="ZAF"  <c:if test="${'ZAF'==model.homeCountry}">selected</c:if>>South Africa</option>
							<option value="SGS"  <c:if test="${'SGS'==model.homeCountry}">selected</c:if>>South Georgia and the South Sandwich Islands</option>
							<option value="SSD"  <c:if test="${'SSD'==model.homeCountry}">selected</c:if>>South Sudan</option>
							<option value="ESP"  <c:if test="${'ESP'==model.homeCountry}">selected</c:if>>Spain</option>
							<option value="LKA"  <c:if test="${'LKA'==model.homeCountry}">selected</c:if>>Sri Lanka</option>
							<option value="SDN"  <c:if test="${'SDN'==model.homeCountry}">selected</c:if>>Sudan</option>
							<option value="SUR"  <c:if test="${'SUR'==model.homeCountry}">selected</c:if>>Suriname</option>
							<option value="SJM"  <c:if test="${'SJM'==model.homeCountry}">selected</c:if>>Svalbard and Jan Mayen</option>
							<option value="SWZ"  <c:if test="${'SWZ'==model.homeCountry}">selected</c:if>>Swaziland</option>
							<option value="SWE"  <c:if test="${'SWE'==model.homeCountry}">selected</c:if>>Sweden</option>
							<option value="CHE"  <c:if test="${'CHE'==model.homeCountry}">selected</c:if>>Switzerland</option>
							<option value="SYR"  <c:if test="${'SYR'==model.homeCountry}">selected</c:if>>Syrian Arab Republic</option>
							<option value="TWN"  <c:if test="${'TWN'==model.homeCountry}">selected</c:if>>Taiwan, Province of China</option>
							<option value="TJK"  <c:if test="${'TJK'==model.homeCountry}">selected</c:if>>Tajikistan</option>
							<option value="TZA"  <c:if test="${'TZA'==model.homeCountry}">selected</c:if>>Tanzania, United Republic of</option>
							<option value="THA"  <c:if test="${'THA'==model.homeCountry}">selected</c:if>>Thailand</option>
							<option value="TLS"  <c:if test="${'TLS'==model.homeCountry}">selected</c:if>>Timor-Leste</option>
							<option value="TGO"  <c:if test="${'TGO'==model.homeCountry}">selected</c:if>>Togo</option>
							<option value="TKL"  <c:if test="${'TKL'==model.homeCountry}">selected</c:if>>Tokelau</option>
							<option value="TON"  <c:if test="${'TON'==model.homeCountry}">selected</c:if>>Tonga</option>
							<option value="TTO"  <c:if test="${'TTO'==model.homeCountry}">selected</c:if>>Trinidad and Tobago</option>
							<option value="TUN"  <c:if test="${'TUN'==model.homeCountry}">selected</c:if>>Tunisia</option>
							<option value="TUR"  <c:if test="${'TUR'==model.homeCountry}">selected</c:if>>Turkey</option>
							<option value="TKM"  <c:if test="${'TKM'==model.homeCountry}">selected</c:if>>Turkmenistan</option>
							<option value="TCA"  <c:if test="${'TCA'==model.homeCountry}">selected</c:if>>Turks and Caicos Islands</option>
							<option value="TUV"  <c:if test="${'TUV'==model.homeCountry}">selected</c:if>>Tuvalu</option>
							<option value="UGA"  <c:if test="${'UGA'==model.homeCountry}">selected</c:if>>Uganda</option>
							<option value="UKR"  <c:if test="${'UKR'==model.homeCountry}">selected</c:if>>Ukraine</option>
							<option value="ARE"  <c:if test="${'ARE'==model.homeCountry}">selected</c:if>>United Arab Emirates</option>
							<option value="GBR"  <c:if test="${'GBR'==model.homeCountry}">selected</c:if>>United Kingdom</option>
							<option value="USA"  <c:if test="${'USA'==model.homeCountry}">selected</c:if>>United States</option>
							<option value="UMI"  <c:if test="${'UMI'==model.homeCountry}">selected</c:if>>United States Minor Outlying Islands</option>
							<option value="URY"  <c:if test="${'URY'==model.homeCountry}">selected</c:if>>Uruguay</option>
							<option value="UZB"  <c:if test="${'UZB'==model.homeCountry}">selected</c:if>>Uzbekistan</option>
							<option value="VUT"  <c:if test="${'VUT'==model.homeCountry}">selected</c:if>>Vanuatu</option>
							<option value="VEN"  <c:if test="${'VEN'==model.homeCountry}">selected</c:if>>Venezuela, Bolivarian Republic of</option>
							<option value="VNM"  <c:if test="${'VNM'==model.homeCountry}">selected</c:if>>Viet Nam</option>
							<option value="VGB"  <c:if test="${'VGB'==model.homeCountry}">selected</c:if>>Virgin Islands, British</option>
							<option value="VIR"  <c:if test="${'VIR'==model.homeCountry}">selected</c:if>>Virgin Islands, U.S.</option>
							<option value="WLF"  <c:if test="${'WLF'==model.homeCountry}">selected</c:if>>Wallis and Futuna</option>
							<option value="ESH"  <c:if test="${'ESH'==model.homeCountry}">selected</c:if>>Western Sahara</option>
							<option value="YEM"  <c:if test="${'YEM'==model.homeCountry}">selected</c:if>>Yemen</option>
							<option value="ZMB"  <c:if test="${'ZMB'==model.homeCountry}">selected</c:if>>Zambia</option>
							<option value="ZWE"  <c:if test="${'ZWE'==model.homeCountry}">selected</c:if>>Zimbabwe</option>
						</select>
						<label for="homeCountry"></label>
					</td>
					<th><s:Locale code="userinfo.homeRegion" />：</th>
					<td>
						<input class="form-control"  type="text" id="homeRegion" name="homeRegion"  title="" value="${model.homeRegion}"/>
						<label for="homeRegion"></label>
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.homeLocality" />：</th>
					<td>
						<input class="form-control"  type="text" id="homeLocality" name="homeLocality"  title="" value="${model.homeLocality}"/>
						<label for="homeLocality"></label>
					</td>
					<th><s:Locale code="userinfo.homeStreetAddress" />：</th>
					<td>
						<input class="form-control"  type="text" id="homeStreetAddress" name="homeStreetAddress"  title="" value="${model.homeStreetAddress}"/>
						<label for="homeStreetAddress"></label>
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.homePostalCode" />：</th>
					<td>
						<input class="form-control"  type="text" id="homePostalCode" name="homePostalCode"  title="" value="${model.homePostalCode}"/>
						<label for="homePostalCode"></label>
					</td>
					<th><s:Locale code="userinfo.homeFax" />：</th>
					<td>
						<input class="form-control"  type="text" id="homeFax" name="homeFax"  title="" value="${model.homeFax}"/>
						<label for="homeFax"></label>
					</td>
				</tr>
				
				<tr>
					<th><s:Locale code="userinfo.homePhoneNumber" />：</th>
					<td>
						<input class="form-control"  type="text" id="homePhoneNumber" name="homePhoneNumber"  title="" value="${model.homePhoneNumber}"/>
						<label for="homePhoneNumber"></label>
					</td>
					<th><s:Locale code="userinfo.homeEmail" />：</th>
					<td >
						<input class="form-control"  type="text" id="homeEmail" name="homeEmail"  title="" value="${model.homeEmail}"/>
						<label for="homeEmail"></label>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;
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
					<input id="submitBtn" class="button btn btn-primary mr-3" type="button" value="<s:Locale code="button.text.save" />"/>
				</div>
			</div>
	 </div> 
</form>
</div>
<div id="footer">
	<jsp:include page="../layout/footer.jsp"></jsp:include>
</div>
<body>
</html>