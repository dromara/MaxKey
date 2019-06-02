<%@ page   language="java"    import="java.util.*"   pageEncoding="UTF-8"%>
<%@ page   import="org.maxkey.web.*"%>
<%@ taglib prefix="s"  		uri="http://sso.maxkey.org/tags" %>
<%@ taglib prefix="spring"	  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"		  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"		  uri="http://java.sun.com/jsp/jstl/core" %>

<form id="actionForm"  method="post" type="alert"  action="<s:Base/>/profile/update/home">
	 <div class="" style="width:100%;">
	    <div class="main">
	    <div class="mainin">			 
  	        <!-- content -->    
  	      	<!--table-->
			  <table  class="datatable">
				<tbody>				
				<tr>
					<th style="width:15%;"><s:Locale code="userinfo.username" />：</th>
					<td style="width:35%;">
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
					<th><s:Locale code="userinfo.homeCountry" />：</th>
					<td>
						<select  id="homeCountry" name="homeCountry">
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
						<input type="text" id="homeRegion" name="homeRegion"  title="" value="${model.homeRegion}"/>
						<label for="homeRegion"></label>
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.homeLocality" />：</th>
					<td>
						<input type="text" id="homeLocality" name="homeLocality"  title="" value="${model.homeLocality}"/>
						<label for="homeLocality"></label>
					</td>
					<th><s:Locale code="userinfo.homeStreetAddress" />：</th>
					<td>
						<input type="text" id="homeStreetAddress" name="homeStreetAddress"  title="" value="${model.homeStreetAddress}"/>
						<label for="homeStreetAddress"></label>
					</td>
				</tr>
				<tr>
					
					<th><s:Locale code="userinfo.homePostalCode" />：</th>
					<td>
						<input type="text" id="homePostalCode" name="homePostalCode"  title="" value="${model.homePostalCode}"/>
						<label for="homePostalCode"></label>
					</td>
					<th><s:Locale code="userinfo.homeFax" />：</th>
					<td>
						<input type="text" id="homeFax" name="homeFax"  title="" value="${model.homeFax}"/>
						<label for="homeFax"></label>
					</td>
				</tr>
				
				<tr>
					<th><s:Locale code="userinfo.homePhoneNumber" />：</th>
					<td>
						<input type="text" id="homePhoneNumber" name="homePhoneNumber"  title="" value="${model.homePhoneNumber}"/>
						<label for="homePhoneNumber"></label>
					</td>
					<th><s:Locale code="userinfo.homeEmail" />：</th>
					<td >
						<input type="text" id="homeEmail" name="homeEmail"  title="" value="${model.homeEmail}"/>
						<label for="homeEmail"></label>
					</td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;
					</td>
					
				</tr>
				<tr>
					<th><s:Locale code="QQ" />：</th>
					<td>
						<input type="text" id="qq" name="qq"  title="" value="${model.qq}"/>
						<label for="qq"></label>
					</td>
					<th><s:Locale code="userinfo.ims.weixin" />：</th>
					<td >
						<input type="text" id="weixin" name="weixin"  title="" value="${model.weixin}"/>
						<label for="weixin"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.ims.sinaweibo" />：</th>
					<td>
						<input type="text" id="sinaweibo" name="sinaweibo"  title="" value="${model.sinaweibo}"/>
						<label for="sinaweibo"></label>
					</td>
					<th><s:Locale code="userinfo.ims.yixin" />：</th>
					<td >
						<input type="text" id="yixin" name="yixin"  title="" value="${model.yixin}"/>
						<label for="weixin"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="Facebook" />：</th>
					<td>
						<input type="text" id="facebook" name="facebook"  title="" value="${model.facebook}"/>
						<label for="facebook"></label>
					</td>
					<th><s:Locale code="Skype" />：</th>
					<td >
						<input type="text" id="skype" name="skype"  title="" value="${model.skype}"/>
						<label for="weixin"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="MSN" />：</th>
					<td>
						<input type="text" id="msn" name="msn"  title="" value="${model.msn}"/>
						<label for="msn"></label>
					</td>
					<th><s:Locale code="GTalk" />：</th>
					<td >
						<input type="text" id="gtalk" name="gtalk"  title="" value="${model.gtalk}"/>
						<label for="gtalk"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="YAHOO" />：</th>
					<td>
						<input type="text" id="yahoo" name="yahoo"  title="" value="${model.yahoo}"/>
						<label for="yahoo"></label>
					</td>
					<th><s:Locale code="LINE" />：</th>
					<td >
						<input type="text" id="line" name="line"  title="" value="${model.line}"/>
						<label for="line"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="AIM" />：</th>
					<td>
						<input type="text" id="aim" name="aim"  title="" value="${model.aim}"/>
						<label for="aim"></label>
					</td>
					<th><s:Locale code="userinfo.ims.define" />：</th>
					<td >
						<input type="text" id="defineIm" name="defineIm"  title="" value="${model.defineIm}"/>
						<label for="defineIm"></label>
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
