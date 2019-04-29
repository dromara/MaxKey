<%@ page   language="java"    import="java.util.*"   pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="http://www.connsec.com/tags" %>
<%@ taglib prefix="spring"	  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt"		  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c"		  uri="http://java.sun.com/jsp/jstl/core" %>
<table  class="datatable"  style="display:none" id="table_switch_home" width="980">
	<tbody>				
		<tr>
			<th style="width:15%;"><s:Locale code="userinfo.idType" />：</th>
			<td style="width:35%;">
				<select name="idType"  class="select_t">
					<option value="0" selected ><s:Locale code="userinfo.usertype.UNKNOWN" /></option>
					<option value="1"  ><s:Locale code="userinfo.usertype.IDCARD" /></option>
					<option value="2"  ><s:Locale code="userinfo.usertype.PASSPORT" /></option>
					<option value="3"  ><s:Locale code="userinfo.usertype.STUDENTCARD" /></option>
					<option value="4"  ><s:Locale code="userinfo.usertype.MILITARYCARD" /></option>
				</select>
			</td>
			
			<th style="width:15%;"><s:Locale code="userinfo.idCardNo" />：</th>
			<td style="width:35%;">
				<input type="text" id="idCardNo" name="idCardNo"  title="" value=""/>
				<label for="idCardNo"></label>
			</td>
		</tr>
		<tr>
			
			<th><s:Locale code="userinfo.married" />：</th>
			<td>
				<select name="married"  class="select_t">
					<option value="0" selected><s:Locale code="userinfo.usertype.UNKNOWN" /></option>
					<option value="1" ><s:Locale code="userinfo.usertype.SINGLE" /></option>
					<option value="2" ><s:Locale code="userinfo.usertype.MARRIED" /></option>
					<option value="3" ><s:Locale code="userinfo.usertype.DIVORCE" /></option>
					<option value="4" ><s:Locale code="userinfo.usertype.WIDOWED" /></option>
				</select>
			</td>
			<th><s:Locale code="userinfo.startWorkDate" />：</th>
			<td>
				<input type="text" id="startWorkDate" name="startWorkDate"  title="" value=""/>
				<label for="startWorkDate"></label>
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
					<option value="AFG" >Afghanistan</option>
					<option value="ALA" >Åland Islands</option>
					<option value="ALB" >Albania</option>
					<option value="DZA" >Algeria</option>
					<option value="ASM" >American Samoa</option>
					<option value="AND" >Andorra</option>
					<option value="AGO" >Angola</option>
					<option value="AIA" >Anguilla</option>
					<option value="ATA" >Antarctica</option>
					<option value="ATG" >Antigua and Barbuda</option>
					<option value="ARG" >Argentina</option>
					<option value="ARM" >Armenia</option>
					<option value="ABW" >Aruba</option>
					<option value="AUS" >Australia</option>
					<option value="AUT" >Austria</option>
					<option value="AZE" >Azerbaijan</option>
					<option value="BHS" >Bahamas</option>
					<option value="BHR" >Bahrain</option>
					<option value="BGD" >Bangladesh</option>
					<option value="BRB" >Barbados</option>
					<option value="BLR" >Belarus</option>
					<option value="BEL" >Belgium</option>
					<option value="BLZ" >Belize</option>
					<option value="BEN" >Benin</option>
					<option value="BMU" >Bermuda</option>
					<option value="BTN" >Bhutan</option>
					<option value="BOL" >Bolivia, Plurinational State of</option>
					<option value="BES" >Bonaire, Sint Eustatius and Saba</option>
					<option value="BIH" >Bosnia and Herzegovina</option>
					<option value="BWA" >Botswana</option>
					<option value="BVT" >Bouvet Island</option>
					<option value="BRA" >Brazil</option>
					<option value="IOT" >British Indian Ocean Territory</option>
					<option value="BRN" >Brunei Darussalam</option>
					<option value="BGR" >Bulgaria</option>
					<option value="BFA" >Burkina Faso</option>
					<option value="BDI" >Burundi</option>
					<option value="KHM" >Cambodia</option>
					<option value="CMR" >Cameroon</option>
					<option value="CAN" >Canada</option>
					<option value="CPV" >Cape Verde</option>
					<option value="CYM" >Cayman Islands</option>
					<option value="CAF" >Central African Republic</option>
					<option value="TCD" >Chad</option>
					<option value="CHL" >Chile</option>
					<option value="CHN" selected >China</option>
					<option value="CXR" >Christmas Island</option>
					<option value="CCK" >Cocos (Keeling) Islands</option>
					<option value="COL" >Colombia</option>
					<option value="COM" >Comoros</option>
					<option value="COG" >Congo</option>
					<option value="COD" >Congo, the Democratic Republic of the</option>
					<option value="COK" >Cook Islands</option>
					<option value="CRI" >Costa Rica</option>
					<option value="CIV" >Côte d'Ivoire</option>
					<option value="HRV" >Croatia</option>
					<option value="CUB" >Cuba</option>
					<option value="CUW" >Curaçao</option>
					<option value="CYP" >Cyprus</option>
					<option value="CZE" >Czech Republic</option>
					<option value="DNK" >Denmark</option>
					<option value="DJI" >Djibouti</option>
					<option value="DMA" >Dominica</option>
					<option value="DOM" >Dominican Republic</option>
					<option value="ECU" >Ecuador</option>
					<option value="EGY" >Egypt</option>
					<option value="SLV" >El Salvador</option>
					<option value="GNQ" >Equatorial Guinea</option>
					<option value="ERI" >Eritrea</option>
					<option value="EST" >Estonia</option>
					<option value="ETH" >Ethiopia</option>
					<option value="FLK" >Falkland Islands (Malvinas)</option>
					<option value="FRO" >Faroe Islands</option>
					<option value="FJI" >Fiji</option>
					<option value="FIN" >Finland</option>
					<option value="FRA" >France</option>
					<option value="GUF" >French Guiana</option>
					<option value="PYF" >French Polynesia</option>
					<option value="ATF" >French Southern Territories</option>
					<option value="GAB" >Gabon</option>
					<option value="GMB" >Gambia</option>
					<option value="GEO" >Georgia</option>
					<option value="DEU" >Germany</option>
					<option value="GHA" >Ghana</option>
					<option value="GIB" >Gibraltar</option>
					<option value="GRC" >Greece</option>
					<option value="GRL" >Greenland</option>
					<option value="GRD" >Grenada</option>
					<option value="GLP" >Guadeloupe</option>
					<option value="GUM" >Guam</option>
					<option value="GTM" >Guatemala</option>
					<option value="GGY" >Guernsey</option>
					<option value="GIN" >Guinea</option>
					<option value="GNB" >Guinea-Bissau</option>
					<option value="GUY" >Guyana</option>
					<option value="HTI" >Haiti</option>
					<option value="HMD" >Heard Island and McDonald Islands</option>
					<option value="VAT" >Holy See (Vatican City State)</option>
					<option value="HND" >Honduras</option>
					<option value="HKG" >Hong Kong</option>
					<option value="HUN" >Hungary</option>
					<option value="ISL" >Iceland</option>
					<option value="IND" >India</option>
					<option value="IDN" >Indonesia</option>
					<option value="IRN" >Iran, Islamic Republic of</option>
					<option value="IRQ" >Iraq</option>
					<option value="IRL" >Ireland</option>
					<option value="IMN" >Isle of Man</option>
					<option value="ISR" >Israel</option>
					<option value="ITA" >Italy</option>
					<option value="JAM" >Jamaica</option>
					<option value="JPN" >Japan</option>
					<option value="JEY" >Jersey</option>
					<option value="JOR" >Jordan</option>
					<option value="KAZ" >Kazakhstan</option>
					<option value="KEN" >Kenya</option>
					<option value="KIR" >Kiribati</option>
					<option value="PRK" >Korea, Democratic People's Republic of</option>
					<option value="KOR" >Korea, Republic of</option>
					<option value="KWT" >Kuwait</option>
					<option value="KGZ" >Kyrgyzstan</option>
					<option value="LAO" >Lao People's Democratic Republic</option>
					<option value="LVA" >Latvia</option>
					<option value="LBN" >Lebanon</option>
					<option value="LSO" >Lesotho</option>
					<option value="LBR" >Liberia</option>
					<option value="LBY" >Libya</option>
					<option value="LIE" >Liechtenstein</option>
					<option value="LTU" >Lithuania</option>
					<option value="LUX" >Luxembourg</option>
					<option value="MAC" >Macao</option>
					<option value="MKD" >Macedonia, the former Yugoslav Republic of</option>
					<option value="MDG" >Madagascar</option>
					<option value="MWI" >Malawi</option>
					<option value="MYS" >Malaysia</option>
					<option value="MDV" >Maldives</option>
					<option value="MLI" >Mali</option>
					<option value="MLT" >Malta</option>
					<option value="MHL" >Marshall Islands</option>
					<option value="MTQ" >Martinique</option>
					<option value="MRT" >Mauritania</option>
					<option value="MUS" >Mauritius</option>
					<option value="MYT" >Mayotte</option>
					<option value="MEX" >Mexico</option>
					<option value="FSM" >Micronesia, Federated States of</option>
					<option value="MDA" >Moldova, Republic of</option>
					<option value="MCO" >Monaco</option>
					<option value="MNG" >Mongolia</option>
					<option value="MNE" >Montenegro</option>
					<option value="MSR" >Montserrat</option>
					<option value="MAR" >Morocco</option>
					<option value="MOZ" >Mozambique</option>
					<option value="MMR" >Myanmar</option>
					<option value="NAM" >Namibia</option>
					<option value="NRU" >Nauru</option>
					<option value="NPL" >Nepal</option>
					<option value="NLD" >Netherlands</option>
					<option value="NCL" >New Caledonia</option>
					<option value="NZL" >New Zealand</option>
					<option value="NIC" >Nicaragua</option>
					<option value="NER" >Niger</option>
					<option value="NGA" >Nigeria</option>
					<option value="NIU" >Niue</option>
					<option value="NFK" >Norfolk Island</option>
					<option value="MNP" >Northern Mariana Islands</option>
					<option value="NOR" >Norway</option>
					<option value="OMN" >Oman</option>
					<option value="PAK" >Pakistan</option>
					<option value="PLW" >Palau</option>
					<option value="PSE" >Palestinian Territory, Occupied</option>
					<option value="PAN" >Panama</option>
					<option value="PNG" >Papua New Guinea</option>
					<option value="PRY" >Paraguay</option>
					<option value="PER" >Peru</option>
					<option value="PHL" >Philippines</option>
					<option value="PCN" >Pitcairn</option>
					<option value="POL" >Poland</option>
					<option value="PRT" >Portugal</option>
					<option value="PRI" >Puerto Rico</option>
					<option value="QAT" >Qatar</option>
					<option value="REU" >Réunion</option>
					<option value="ROU" >Romania</option>
					<option value="RUS" >Russian Federation</option>
					<option value="RWA" >Rwanda</option>
					<option value="BLM" >Saint Barthélemy</option>
					<option value="SHN" >Saint Helena, Ascension and Tristan da Cunha</option>
					<option value="KNA" >Saint Kitts and Nevis</option>
					<option value="LCA" >Saint Lucia</option>
					<option value="MAF" >Saint Martin (French part)</option>
					<option value="SPM" >Saint Pierre and Miquelon</option>
					<option value="VCT" >Saint Vincent and the Grenadines</option>
					<option value="WSM" >Samoa</option>
					<option value="SMR" >San Marino</option>
					<option value="STP" >Sao Tome and Principe</option>
					<option value="SAU" >Saudi Arabia</option>
					<option value="SEN" >Senegal</option>
					<option value="SRB" >Serbia</option>
					<option value="SYC" >Seychelles</option>
					<option value="SLE" >Sierra Leone</option>
					<option value="SGP" >Singapore</option>
					<option value="SXM" >Sint Maarten (Dutch part)</option>
					<option value="SVK" >Slovakia</option>
					<option value="SVN" >Slovenia</option>
					<option value="SLB" >Solomon Islands</option>
					<option value="SOM" >Somalia</option>
					<option value="ZAF" >South Africa</option>
					<option value="SGS" >South Georgia and the South Sandwich Islands</option>
					<option value="SSD" >South Sudan</option>
					<option value="ESP" >Spain</option>
					<option value="LKA" >Sri Lanka</option>
					<option value="SDN" >Sudan</option>
					<option value="SUR" >Suriname</option>
					<option value="SJM" >Svalbard and Jan Mayen</option>
					<option value="SWZ" >Swaziland</option>
					<option value="SWE" >Sweden</option>
					<option value="CHE" >Switzerland</option>
					<option value="SYR" >Syrian Arab Republic</option>
					<option value="TWN" >Taiwan, Province of China</option>
					<option value="TJK" >Tajikistan</option>
					<option value="TZA" >Tanzania, United Republic of</option>
					<option value="THA" >Thailand</option>
					<option value="TLS" >Timor-Leste</option>
					<option value="TGO" >Togo</option>
					<option value="TKL" >Tokelau</option>
					<option value="TON" >Tonga</option>
					<option value="TTO" >Trinidad and Tobago</option>
					<option value="TUN" >Tunisia</option>
					<option value="TUR" >Turkey</option>
					<option value="TKM" >Turkmenistan</option>
					<option value="TCA" >Turks and Caicos Islands</option>
					<option value="TUV" >Tuvalu</option>
					<option value="UGA" >Uganda</option>
					<option value="UKR" >Ukraine</option>
					<option value="ARE" >United Arab Emirates</option>
					<option value="GBR" >United Kingdom</option>
					<option value="USA" >United States</option>
					<option value="UMI" >United States Minor Outlying Islands</option>
					<option value="URY" >Uruguay</option>
					<option value="UZB" >Uzbekistan</option>
					<option value="VUT" >Vanuatu</option>
					<option value="VEN" >Venezuela, Bolivarian Republic of</option>
					<option value="VNM" >Viet Nam</option>
					<option value="VGB" >Virgin Islands, British</option>
					<option value="VIR" >Virgin Islands, U.S.</option>
					<option value="WLF" >Wallis and Futuna</option>
					<option value="ESH" >Western Sahara</option>
					<option value="YEM" >Yemen</option>
					<option value="ZMB" >Zambia</option>
					<option value="ZWE" >Zimbabwe</option>
				</select>
			</td>
			<th><s:Locale code="userinfo.homeRegion" />：</th>
			<td>
				<input type="text" id="homeRegion" name="homeRegion"  title="" value=""/>
				<label for="homeRegion"></label>
			</td>
		</tr>
		<tr>
			
			<th><s:Locale code="userinfo.homeLocality" />：</th>
			<td>
				<input type="text" id="homeLocality" name="homeLocality"  title="" value=""/>
				<label for="homeLocality"></label>
			</td>
			<th><s:Locale code="userinfo.homeStreetAddress" />：</th>
			<td>
				<input type="text" id="homeStreetAddress" name="homeStreetAddress"  title="" value=""/>
				<label for="homeStreetAddress"></label>
			</td>
		</tr>
		<tr>
			
			<th><s:Locale code="userinfo.homePostalCode" />：</th>
			<td>
				<input type="text" id="homePostalCode" name="homePostalCode"  title="" value=""/>
				<label for="homePostalCode"></label>
			</td>
			<th><s:Locale code="userinfo.homeFax" />：</th>
			<td>
				<input type="text" id="homeFax" name="homeFax"  title="" value=""/>
				<label for="homeFax"></label>
			</td>
		</tr>
		
		<tr>
			<th><s:Locale code="userinfo.homePhoneNumber" />：</th>
			<td>
				<input type="text" id="homePhoneNumber" name="homePhoneNumber"  title="" value=""/>
				<label for="homePhoneNumber"></label>
			</td>
			<th><s:Locale code="userinfo.homeEmail" />：</th>
			<td >
				<input type="text" id="homeEmail" name="homeEmail"  title="" value=""/>
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
						<input type="text" id="qq" name="qq"  title="" value=""/>
						<label for="qq"></label>
					</td>
					<th><s:Locale code="userinfo.ims.weixin" />：</th>
					<td >
						<input type="text" id="weixin" name="weixin"  title="" value=""/>
						<label for="weixin"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="userinfo.ims.sinaweibo" />：</th>
					<td>
						<input type="text" id="sinaweibo" name="sinaweibo"  title="" value=""/>
						<label for="sinaweibo"></label>
					</td>
					<th><s:Locale code="userinfo.ims.yixin" />：</th>
					<td >
						<input type="text" id="yixin" name="yixin"  title="" value=""/>
						<label for="weixin"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="Facebook" />：</th>
					<td>
						<input type="text" id="facebook" name="facebook"  title="" value=""/>
						<label for="facebook"></label>
					</td>
					<th><s:Locale code="Skype" />：</th>
					<td >
						<input type="text" id="skype" name="skype"  title="" value=""/>
						<label for="weixin"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="MSN" />：</th>
					<td>
						<input type="text" id="msn" name="msn"  title="" value=""/>
						<label for="msn"></label>
					</td>
					<th><s:Locale code="GTalk" />：</th>
					<td >
						<input type="text" id="gtalk" name="gtalk"  title="" value=""/>
						<label for="gtalk"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="YAHOO" />：</th>
					<td>
						<input type="text" id="yahoo" name="yahoo"  title="" value=""/>
						<label for="yahoo"></label>
					</td>
					<th><s:Locale code="LINE" />：</th>
					<td >
						<input type="text" id="line" name="line"  title="" value=""/>
						<label for="line"></label>
					</td>
				</tr>
				<tr>
					<th><s:Locale code="AIM" />：</th>
					<td>
						<input type="text" id="aim" name="aim"  title="" value=""/>
						<label for="aim"></label>
					</td>
					<th><s:Locale code="userinfo.ims.define" />：</th>
					<td >
						<input type="text" id="defineIm" name="defineIm"  title="" value=""/>
						<label for="defineIm"></label>
					</td>
				</tr>
	</tbody>
</table>