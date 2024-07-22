/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 


package org.dromara.maxkey.authz.saml20.provider.xml;


import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.authz.saml.service.TimeService;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.web.WebContext;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml2.core.impl.NameIDBuilder;
import org.opensaml.saml2.core.impl.SubjectBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationDataBuilder;

public class SubjectGenerator {

	//private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
	private final TimeService timeService;
		
	public SubjectGenerator(TimeService timeService) {
		super();
		this.timeService = timeService;
	}

	public Subject generateSubject( AppsSAML20Details saml20Details,
							String assertionConsumerURL, 
							String inResponseTo, 
							int validInSeconds,
							UserInfo userInfo) {
		String nameIdValue = userInfo.getUsername();
		String nameIDType = NameIDType.UNSPECIFIED;
		if(saml20Details.getNameidFormat().equalsIgnoreCase("persistent")) {
			nameIDType = NameIDType.PERSISTENT;
		}else if(saml20Details.getNameidFormat().equalsIgnoreCase("transient")) {
			nameIDType = NameIDType.TRANSIENT;
        }else if(saml20Details.getNameidFormat().equalsIgnoreCase("unspecified")) {
        	nameIDType = NameIDType.UNSPECIFIED;
        }else if(saml20Details.getNameidFormat().equalsIgnoreCase("emailAddress")) {
            if(userInfo.getEmail()!=null && !userInfo.getEmail().equals("")) {
                nameIdValue = userInfo.getEmail();
            }
            nameIDType = NameIDType.EMAIL;
        }else if(saml20Details.getNameidFormat().equalsIgnoreCase("X509SubjectName")) {
        	nameIDType = NameIDType.X509_SUBJECT;
        }else if(saml20Details.getNameidFormat().equalsIgnoreCase("WindowsDomainQualifiedName")) {
            if(userInfo.getWindowsAccount()!=null && !userInfo.getWindowsAccount().equals("")) {
                nameIdValue = userInfo.getWindowsAccount();
            }
            nameIDType = NameIDType.WIN_DOMAIN_QUALIFIED;
        }else if(saml20Details.getNameidFormat().equalsIgnoreCase("entity")) {
        	nameIDType = NameIDType.ENTITY;
        }else if(saml20Details.getNameidFormat().equalsIgnoreCase("custom")) {
            
        }else if(saml20Details.getNameidFormat().equalsIgnoreCase("Mobile")) {
            if(userInfo.getMobile()!=null && !userInfo.getMobile().equals("")) {
                nameIdValue = userInfo.getMobile();
            }
        }else if(saml20Details.getNameidFormat().equalsIgnoreCase("EmployeeNumber")) {
            if(userInfo.getEmployeeNumber()!=null && !userInfo.getEmployeeNumber().equals("")) {
                nameIdValue = userInfo.getEmployeeNumber();
            }
        }
		
		if(!StringUtils.isEmpty(saml20Details.getNameIdSuffix())) {
		    nameIdValue = nameIdValue + saml20Details.getNameIdSuffix();
		}
		
		if(saml20Details.getNameIdConvert().equalsIgnoreCase("uppercase")) {
		    nameIdValue = nameIdValue.toUpperCase();
        }else if(saml20Details.getNameIdConvert().equalsIgnoreCase("lowercase")) {
            nameIdValue = nameIdValue.toLowerCase();
        }else {
        	//do nothing
        }
		
		NameID nameID = builderNameID(nameIdValue,assertionConsumerURL,nameIDType);
		Subject subject =builderSubject(nameID);
		
		String clientAddress=WebContext.getRequestIpAddress(WebContext.getRequest());
		SubjectConfirmation subjectConfirmation =builderSubjectConfirmation(
								assertionConsumerURL,
								inResponseTo,
								validInSeconds,
								clientAddress);

		subject.getSubjectConfirmations().add(subjectConfirmation);
		
		return subject;
	}
	
	public NameID builderNameID(String value,String strSPNameQualifier,String nameIDType){
		//Response/Assertion/Subject/NameID	
		NameID nameID = new NameIDBuilder().buildObject();
		nameID.setValue(value);
		//nameID.setFormat(NameIDType.PERSISTENT);
		nameID.setFormat(nameIDType);
		//nameID.setSPNameQualifier(strSPNameQualifier);
		
		return nameID;
	}
	
	public Subject builderSubject (NameID nameID){
		//Response/Assertion/Subject
		Subject subject = new SubjectBuilder().buildObject();
		subject.setNameID(nameID);
		return subject;
	}
	
	public SubjectConfirmation builderSubjectConfirmation(String recipient,String inResponseTo,int validInSeconds,String clientAddress){
		//SubjectConfirmationBuilder subjectConfirmationBuilder = (SubjectConfirmationBuilder)builderFactory.getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
		SubjectConfirmation subjectConfirmation = new SubjectConfirmationBuilder().buildObject();
		subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);
		
		//SubjectConfirmationDataBuilder subjectConfirmationDataBuilder = (SubjectConfirmationDataBuilder)builderFactory.getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
		SubjectConfirmationData subjectConfirmationData = new SubjectConfirmationDataBuilder().buildObject();
		
		subjectConfirmationData.setRecipient(recipient);
		//if idp-init not need inResponseTo
		if(null!=inResponseTo){
			subjectConfirmationData.setInResponseTo(inResponseTo);
		}
		subjectConfirmationData.setNotOnOrAfter(timeService.getCurrentDateTime().plusSeconds(validInSeconds));
		subjectConfirmationData.setAddress(clientAddress);
		
		subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);
		
		return subjectConfirmation;
	}
	
}
