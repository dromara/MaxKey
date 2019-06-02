<%@ page   language="java"    import="java.util.*"   pageEncoding="UTF-8"%>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="s"  		uri="http://sso.maxkey.org/tags" %>
<%@ taglib prefix="spring"	  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"		  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"		  uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript"> 

</script>
<form id="actionForm"  method="post" type="alert"  action="<s:Base/>/profile/update/company">
	 <div class="" style="width:100%;">
	    <div class="main">
	    <div class="mainin">			 
  	        <!-- content -->    
  	      	<!--table-->
			  <table  class="datatable">
				<tbody>				
				<tr>
					<th style="width:15%;"><s:Locale code="userinfo.username" />：</th>
					<td  style="width:35%;">
					<input type="hidden" id="id" name="id" value="${model.id}"/>
						<input readonly type="text" id="username" name="username"  title="" value="${model.username}"/>
						<label for="username"></label>
					</td>
					<th style="width:15%;"><s:Locale code="userinfo.displayName" />：</th>
					<td style="width:35%;">
						<input readonly type="text" id="displayName" name="displayName"  title="" value="${model.displayName}"/>
						<label for="displayName"></label>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;
					</td>
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.employeeNumber" />：</th>
					<td>
						<input readonly type="text" id="employeeNumber" name="employeeNumber"  title="" value="${model.employeeNumber}"/>
						<label for="username"></label>
					</td>
					<th><s:Locale code="userinfo.windowsAccount" />：</th>
					<td>
						<input type="text" id="windowsAccount" name="windowsAccount"  title="" value="${model.windowsAccount}"/>
						<label for="windowsAccount"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.organization" />：</th>
					<td>
						<input type="text" id="organization" name="organization"  title="" value="${model.organization}"/>
						<label for="organization"></label>
					</td>
					<th><s:Locale code="userinfo.division" />：</th>
					<td>
						<input type="text" id="division" name="division"  title="" value="${model.division}"/>
						<label for="division"></label>
					</td>
					
				</tr>
				
				<tr>
					<th><s:Locale code="userinfo.department" />：</th>
					<td>
						<input type="hidden" id="departmentId" name="departmentId"  title="" value="${model.departmentId}"/>
						<input type="text" id="department" name="department"  title="" value="${model.department}"/>
						<label for="department"></label>
					</td>
					<th><s:Locale code="userinfo.costCenter" />：</th>
					<td>
						<input type="text" id="costCenter" name="costCenter"  title="" value="${model.costCenter}"/>
						<label for="costCenter"></label>
					</td>
					
				</tr>
				<tr>
					<th><s:Locale code="userinfo.jobTitle" />：</th>
					<td>
						<input type="text" id="jobTitle" name="jobTitle"  title="" value="${model.jobTitle}"/>
						<label for="jobTitle"></label>
					</td>
					<th><s:Locale code="userinfo.jobLevel" />：</th>
					<td>
						<input type="text" id="jobLevel" name="jobLevel"  title="" value="${model.jobLevel}"/>
						<label for="jobLevel"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.manager" />：</th>
					<td>
						<input type="hidden" id="managerId" name="managerId"  title="" value="${model.managerId}"/>
						<input type="text" id="manager" name="manager"  title="" value="${model.manager}"/>
						<label for="manager"></label>
					</td>
					<th><s:Locale code="userinfo.assistant" />：</th>
					<td>
						<input type="hidden" id="assistantId" name="assistantId"  title="" value="${model.assistantId}"/>
						<input  type="text" id="assistant" name="assistant"  title="" value="${model.assistant}"/>
						<label for="delegatedApprover"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.entryDate" />：</th>
					<td>
						<input type="text" class="datepicker"  id="entryDate" name="entryDate"  title="" value="${model.entryDate}"/>
						<label for="entryDate"></label>
					</td>
					<th><s:Locale code="userinfo.quitDate" />：</th>
					<td>
						<input  type="text" class="datepicker"  id="quitDate" name="quitDate"  title="" value="${model.quitDate}"/>
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
						<select  id="workCountry" name="workCountry">
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
						<input type="text" id="workRegion" name="workRegion"  title="" value="${model.workRegion}"/>
						<label for="province"></label>
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.workLocality" />：</th>
					<td>
						<input type="text" id="workLocality" name="workLocality"  title="" value="${model.workLocality}"/>
						<label for="workLocality"></label>
					</td>
					<th><s:Locale code="userinfo.workStreetAddress" />：</th>
					<td>
						<input type="text" id="workStreetAddress" name="workStreetAddress"  title="" value="${model.workStreetAddress}"/>
						<label for="street"></label>
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.workPostalCode" />：</th>
					<td>
						<input type="text" id="workPostalCode" name="workPostalCode"  title="" value="${model.workPostalCode}"/>
						<label for="workPostalCode"></label>
					</td>
					<th><s:Locale code="userinfo.workFax" />：</th>
					<td>
						<input type="text" id="workFax" name="workFax"  title="" value="${model.workFax}"/>
						<label for="workFax"></label>
					</td>
				</tr>

				<tr>
					<th><s:Locale code="userinfo.workPhoneNumber" />：</th>
					<td>
						<input type="text" id="workPhoneNumber" name="workPhoneNumber"  title="" value="${model.workPhoneNumber}"/>
						<label for="workPhoneNumber"></label>
					</td>
					<th><s:Locale code="userinfo.workEmail" />：</th>
					<td>
						<input type="text" id="workEmail" name="workEmail"  title="" value="${model.workEmail}"/>
						<label for="workEmail"></label>
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
					<input id="submitBtn" class="button" type="button" value="<s:Locale code="button.text.save" />"/>
				</div>
			</div>
	 </div> 
</form>
