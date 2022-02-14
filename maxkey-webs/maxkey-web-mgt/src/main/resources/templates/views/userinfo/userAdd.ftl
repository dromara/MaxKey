<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "../layout/header.ftl"/>
	<#include  "../layout/common.cssjs.ftl"/>
<script type="text/javascript">
   <!--
      $(function(){	
      	$("#departmentId").val($.cookie("select_org_id"));
		$("#department").val($.cookie("select_org_name"));
		
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
	action="<@base/>/userinfo/add" 
	autoclose="true" 
	enctype="multipart/form-data"
	class="needs-validation" novalidate>
	 <div class="" style="width:100%;">
		<div class="top">
			<ul class="switch_tab"  style="width:100%" >
				<li id="table_switch_basic"    style="width:30%" class="switch_tab_class switch_tab_current"><a href="javascript:void(0);"><@locale code="userinfo.tab.basic" /></a></li>
				<li id="table_switch_company"  style="width:30%" class="switch_tab_class"><a href="javascript:void(0);"><@locale code="userinfo.tab.business" /></a></li>
				<li id="table_switch_home"     style="width:30%" class="switch_tab_class"><a href="javascript:void(0);"><@locale code="userinfo.tab.personal" /></a></li>
				
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
			<input type="hidden" id="id" name="id" value=""/>
			<input type="hidden" id="status" name="status" value="1"/>
			<input  class="form-control"  type="text" required="" id="username" name="username"  title="" value=""/>
		</td>
		<th style="width:15%;"><@locale code="login.text.password" /></th>
		<th style="width:35%;">
		  <div class="input-group" style="vertical-align middle;">
			<input  class="form-control"  type="password" required="" id="password" name="password"  title="" value=""/>
			<i class="passwdeye fa fa-eye-slash fa-2" style="left: 290px; color: gainsboro;" refid="password" ></i>
		  </div>
		</th>
	</tr>
	<tr>
			<td style="width:15%;"><@locale code="userinfo.employeeNumber" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="employeeNumber" name="employeeNumber"  title="" value=""/>
			</td>
			<td style="width:15%;"><@locale code="userinfo.windowsAccount" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="windowsAccount" name="windowsAccount"  title="" value=""/>
			</td>
		</tr>
		
	<tr>
		<td><@locale code="userinfo.userType" /></td>
		<td style="width:35%;">

			<select name="userType"   class="form-control  form-select" >
					<option value="EMPLOYEE"  selected><@locale code="userinfo.userType.employee" /></option>
					<option value="CONTRACTOR"  ><@locale code="userinfo.userType.contractor" /></option>
					<option value="CUSTOMER"  ><@locale code="userinfo.userType.customer" /></option>
					<option value="DEALER"  ><@locale code="userinfo.userType.dealer" /></option>
					<option value="SUPPLIER"  ><@locale code="userinfo.userType.supplier" /></option>
					<option value="PARTNER"  ><@locale code="userinfo.userType.partner" /></option>
					<option value="EXTERNAL"  ><@locale code="userinfo.userType.external" /></option>
					<option value="INTERN"  ><@locale code="userinfo.userType.intern" /></option>
					<option value="TEMP"  ><@locale code="userinfo.userType.temp" /></option>
			</select>
		</td>
		<td><@locale code="userinfo.userstate" /></td>
		<td style="width:35%;">
			<select name="userState"   class="form-control  form-select" >
					<option value="RESIDENT"  selected ><@locale code="userinfo.userstate.resident" /></option>
					<option value="WITHDRAWN"  	><@locale code="userinfo.userstate.withdrawn" /></option>
					<option value="RETIREE"  	><@locale code="userinfo.userstate.retiree" /></option>
					<option value="INACTIVE"  	><@locale code="userinfo.userstate.inactive" /></option>
			</select>
		</td>
		
	</tr>
	<tr>
		<td colspan="4">&nbsp;
		</td>
		
	</tr>
	<tr>
		<th><@locale code="userinfo.displayName" /></th>
		<td>
			<input class="form-control"  type="text" required="" id="displayName" name="displayName"  title="" value=""/>
		</td>
		<td rowspan="3"><@locale code="userinfo.picture" /></td>
		<td rowspan="3">
			<img id="picture" width="150px" height="150px" src="<@base/>/static/images/uploadimage.jpg" />
			<input type="file" id="pictureFile" name="pictureFile" style="display:none" />
					</td>
	</tr>
	<tr>
		<td><@locale code="userinfo.familyName" /></td>
		<td>
			<input class="form-control"  type="text" id="familyName" name="familyName"  title="" value=""/>
		</td>
	</tr>
	<tr>
		<td><@locale code="userinfo.givenName" /></td>
		<td>
			<input class="form-control"  type="text" id="givenName" name="givenName"  title="" value=""/>
		</td>
	</tr>
	<tr>
		<td><@locale code="userinfo.middleName" /></td>
		<td>
			<input class="form-control"  type="text" id="middleName" name="middleName"  title="" value=""/>
		</td>
		<td><@locale code="userinfo.nickName" /></td>
		<td>
			<input class="form-control"  type="text" id="nickName" name="nickName"  title="" value=""/>
		</td>
		
	</tr>
	<tr>
		<td><@locale code="userinfo.gender" /></td>
		<td>
			<select name="gender"   class="form-control" >
					<option value="1"  selected><@locale code="userinfo.gender.female" /></option>
					<option value="2"  ><@locale code="userinfo.gender.male" /></option>
			</select>
		</td>
		<td><@locale code="userinfo.birthDate" /></td>
		<td>
			<input class="form-control"  type="text" id="birthDate" name="birthDate"  title="" value=""/>
		</td>
	</tr>
	<tr>
		<td><@locale code="userinfo.preferredLanguage" /></td>
		<td>
			<select class="form-control  form-select"  name="preferredLanguage" id="preferredLanguage">
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
		<td><@locale code="userinfo.timeZone" /></td>
		<td nowrap >
			<select class="form-control  form-select"  id="timeZone" name="timeZone" tabindex="61">
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
	</tr>
	</tbody>
 </table>
 <table  class="table table-bordered"  style="display:none" id="div_table_switch_company" width="980">
	<tbody>				
		<tr>
			<td style="width:15%;"><@locale code="userinfo.organization" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="organization" name="organization"  title="" value=""/>
			</td>
			<td style="width:15%;"><@locale code="userinfo.division" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="division" name="division"  title="" value=""/>
			</td>
			
		</tr>
		
		<tr>
			<td><@locale code="userinfo.department" /></td>
			<td>
				<input class="form-control"  type="hidden" id="departmentId" name="departmentId"  title="" value=""/>
				<input class="form-control"  type="text" style="width:70%"  id="department" name="department"  title="" value=""/>
				<s:Dialog text="button.text.select" title="department" url="/orgs/orgsSelect/deptId/department" width="300" height="400" />
			</td>
			<td><@locale code="userinfo.costCenter" /></td>
			<td>
				<input class="form-control"  type="text" id="costCenter" name="costCenter"  title="" value=""/>
			</td>
			
		</tr>
		<tr>
			<td><@locale code="userinfo.jobTitle" /></td>
			<td>
				<input class="form-control"  type="text" id="jobTitle" name="jobTitle"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.jobLevel" /></td>
			<td>
				<input class="form-control"  type="text" id="jobLevel" name="jobLevel"  title="" value=""/>
			</td>
		</tr>
		<tr>
			<td><@locale code="userinfo.manager" /></td>
			<td>
				<input class="form-control"  type="hidden" id="managerId" name="managerId"  title="" value=""/>
				<input class="form-control"  type="text" id="manager" name="manager"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.assistant" /></td>
			<td>
				<input class="form-control"  type="hidden" id="assistantId" name="assistantId"  title="" value=""/>
				<input class="form-control"   type="text" id="assistant" name="assistant"  title="" value=""/>
			</td>
		</tr>
		<tr>
			<td><@locale code="userinfo.entryDate" /></td>
			<td>
				<input class="form-control"  type="text" id="entryDate" name="entryDate"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.quitDate" /></td>
			<td>
				<input class="form-control"   type="text" id="quitDate" name="quitDate"  title="" value=""/>
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;
			</td>
			
		</tr>
		<tr>
			<td><@locale code="userinfo.workCountry" /></td>
			<td nowrap >
				<input class="form-control"   type="text" id="workCountry" name="workCountry"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.workRegion" /></td>
			<td>
				<input class="form-control"  type="text" id="workRegion" name="workRegion"  title="" value=""/>
			</td>
		</tr>
		<tr>
			<td><@locale code="userinfo.workLocality" /></td>
			<td>
				<input class="form-control"  type="text" id="workLocality" name="workLocality"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.workStreetAddress" /></td>
			<td>
				<input class="form-control"  type="text" id="workStreetAddress" name="workStreetAddress"  title="" value=""/>
			</td>
		</tr>
		<tr>
			
			<td><@locale code="userinfo.workPostalCode" /></td>
			<td>
				<input class="form-control"  type="text" id="workPostalCode" name="workPostalCode"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.workFax" /></td>
			<td>
				<input class="form-control"  type="text" id="workFax" name="workFax"  title="" value=""/>
			</td>
		</tr>

		<tr>
			<td><@locale code="userinfo.workPhoneNumber" /></td>
			<td>
				<input class="form-control"  type="text" id="workPhoneNumber" name="workPhoneNumber"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.workEmail" /></td>
			<td>
				<input class="form-control"  type="text" id="workEmail" name="workEmail"  title="" value=""/>
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
					<option value="UNKNOWN" selected ><@locale code="userinfo.idtype.unknown" /></option>
					<option value="IDCARD"  ><@locale code="userinfo.idtype.idcard" /></option>
					<option value="PASSPORT"  ><@locale code="userinfo.idtype.passport" /></option>
					<option value="STUDENTCARD"  ><@locale code="userinfo.idtype.studentcard" /></option>
					<option value="MILITARYCARD"  ><@locale code="userinfo.idtype.militarycard" /></option>
				</select>
			</td>
			
			<td style="width:15%;"><@locale code="userinfo.idCardNo" /></td>
			<td style="width:35%;">
				<input class="form-control"  type="text" id="idCardNo" name="idCardNo"  title="" value=""/>

			</td>
		</tr>
		<tr>
			
			<td><@locale code="userinfo.married" /></td>
			<td>
				<select name="married"  class="form-control  form-select" >
					<option value="UNKNOWN" selected><@locale code="userinfo.married.unknown" /></option>
					<option value="SINGLE" ><@locale code="userinfo.married.single" /></option>
					<option value="MARRIED" ><@locale code="userinfo.married.married" /></option>
					<option value="DIVORCE" ><@locale code="userinfo.married.divorce" /></option>
					<option value="WIDOWED" ><@locale code="userinfo.married.widowed" /></option>
				</select>
			</td>
			<td><@locale code="userinfo.startWorkDate" /></td>
			<td>
				<input class="form-control"  type="text" id="startWorkDate" name="startWorkDate"  title="" value=""/>
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;
			</td>
			
		</tr>
		
		<tr>
			<td><@locale code="userinfo.homeCountry" /></td>
			<td>
				<input class="form-control"  type="text" id="homeCountry" name="homeCountry"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.homeRegion" /></td>
			<td>
				<input class="form-control"  type="text" id="homeRegion" name="homeRegion"  title="" value=""/>
			</td>
		</tr>
		<tr>
			
			<td><@locale code="userinfo.homeLocality" /></td>
			<td>
				<input class="form-control"  type="text" id="homeLocality" name="homeLocality"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.homeStreetAddress" /></td>
			<td>
				<input class="form-control"  type="text" id="homeStreetAddress" name="homeStreetAddress"  title="" value=""/>
			</td>
		</tr>
		<tr>
			
			<td><@locale code="userinfo.homePostalCode" /></td>
			<td>
				<input class="form-control"  type="text" id="homePostalCode" name="homePostalCode"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.homeFax" /></td>
			<td>
				<input class="form-control"  type="text" id="homeFax" name="homeFax"  title="" value=""/>
			</td>
		</tr>
		
		<tr>
			<td><@locale code="userinfo.homePhoneNumber" /></td>
			<td>
				<input class="form-control"  type="text" id="homePhoneNumber" name="homePhoneNumber"  title="" value=""/>
			</td>
			<td><@locale code="userinfo.homeEmail" /></td>
			<td >
				<input class="form-control"  type="text" id="homeEmail" name="homeEmail"  title="" value=""/>
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;
			</td>
			
		</tr>
				<tr>
					<td><@locale code="userinfo.website" /></td>
					<td>
						<input  class="form-control"   type="text" id="webSite" name="webSite"  title="" value=""/>
					</td>
					<td><@locale code="userinfo.ims" /></td>
					<td >
						<input class="form-control"  type="text" id="defineIm" name="defineIm"  title="" value=""/>
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