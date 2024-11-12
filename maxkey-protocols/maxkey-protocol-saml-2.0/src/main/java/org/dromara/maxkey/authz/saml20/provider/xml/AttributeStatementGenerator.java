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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.dromara.maxkey.constants.ConstsBoolean;
import org.dromara.maxkey.entity.ExtraAttr;
import org.dromara.maxkey.entity.ExtraAttrs;
import org.dromara.maxkey.entity.apps.AppsSAML20Details;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.ldap.activedirectory.constants.ActiveDirectoryUser;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.impl.AttributeBuilder;
import org.opensaml.saml2.core.impl.AttributeStatementBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.schema.impl.XSStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

public class AttributeStatementGenerator {
	private static final  Logger logger = LoggerFactory.getLogger(AttributeStatementGenerator.class);
	
	private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

	public static String COMMA 				= ",";
	public static String COMMA_ISO8859_1 	= "#44;"; //#44; ->,
	
	public AttributeStatement generateAttributeStatement(
			AppsSAML20Details saml20Details,
			ArrayList<GrantedAuthority> grantedAuthoritys,
			UserInfo userInfo) {
		return generateAttributeStatement(
				saml20Details, grantedAuthoritys,null,userInfo);

	}

	public AttributeStatement generateAttributeStatement(
					AppsSAML20Details saml20Details,
					ArrayList<GrantedAuthority> grantedAuthoritys,
					HashMap<String,String>attributeMap,
					UserInfo userInfo) {

		AttributeStatementBuilder attributeStatementBuilder = (AttributeStatementBuilder) builderFactory.getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
		AttributeStatement attributeStatement = attributeStatementBuilder.buildObject();
		
		Attribute attributeGrantedAuthority=builderGrantedAuthority(grantedAuthoritys);
		attributeStatement.getAttributes().add(attributeGrantedAuthority);
		
		putUserAttributes(attributeMap,userInfo);
		
		if(null!=attributeMap){
			Iterator<Entry<String, String>> iterator = attributeMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
				String key = entry.getKey();
				String value = entry.getValue();
				Attribute attribute=builderAttribute(key,value,Attribute.BASIC);
				attributeStatement.getAttributes().add(attribute);
			}
		}
		
		logger.debug("ExtendAttr "+saml20Details.getExtendAttr());
		if(ConstsBoolean.isTrue(saml20Details.getIsExtendAttr()) && saml20Details.getExtendAttr() != null) {
			ExtraAttrs extraAttrs=new ExtraAttrs(saml20Details.getExtendAttr());
			for(ExtraAttr extraAttr : extraAttrs.getExtraAttrs()) {
				extraAttr.setValue(extraAttr.getValue().replaceAll(COMMA_ISO8859_1, COMMA));
				logger.debug("Attribute : {} , Vale : {} , Type : {}",
								extraAttr.getAttr(),extraAttr.getValue(),extraAttr.getType());
				
				attributeStatement.getAttributes().add(builderAttribute(
																extraAttr.getAttr(),
																extraAttr.getValue(),
																extraAttr.getType()
															)
														);
			}
		}
		
		return attributeStatement;
	}
	
	public Attribute builderAttribute(String attributeName,String value ,String nameFormat){
		AttributeBuilder attributeBuilder = (AttributeBuilder) builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
		Attribute attribute = attributeBuilder.buildObject();
		attribute.setName(attributeName);

		// urn:oasis:names:tc:SAML:2.0:attrname-format:basic
		if(nameFormat==null || nameFormat.equals("")) {
			nameFormat=Attribute.BASIC;
		}
		
		attribute.setNameFormat(nameFormat);
		if(value!=null) {	
			attribute.getAttributeValues().add(builderAttributeValue(value));
		}
		
		return attribute;
	}
	
	public Attribute   builderGrantedAuthority(Collection<GrantedAuthority> authorities){
		// Response/Assertion/AttributeStatement/Attribute
		Attribute attribute = builderAttribute("GrantedAuthority",null,null);
		for (GrantedAuthority grantedAuthority : authorities) {
			// this was convoluted to figure out
			// Response/Assertion/AttributeStatement/Attribute/AttributeValue
			attribute.getAttributeValues().add(builderAttributeValue(grantedAuthority.getAuthority()));

		}
		return attribute;
	}
	
	public  XSString builderAttributeValue(String value) {
		XSString xsStringValue = new XSStringBuilder().buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
		xsStringValue.setValue(value);
		return xsStringValue;
	}
	
	public HashMap <String,String> putUserAttributes(
			HashMap <String,String> attributeMap,
			UserInfo userInfo){
        attributeMap.put(ActiveDirectoryUser.USERNAME, userInfo.getUsername());
        attributeMap.put(ActiveDirectoryUser.UID, userInfo.getUsername());
        
        attributeMap.put(ActiveDirectoryUser.EMPLOYEENUMBER, userInfo.getEmployeeNumber());
        attributeMap.put(ActiveDirectoryUser.DEPARTMENTNUMBER, userInfo.getDepartmentId());
        attributeMap.put(ActiveDirectoryUser.DEPARTMENT, userInfo.getDepartment());
        attributeMap.put(ActiveDirectoryUser.COMPANY, userInfo.getDivision());
        attributeMap.put(ActiveDirectoryUser.TITLE, userInfo.getJobTitle());
        attributeMap.put(ActiveDirectoryUser.MANAGER, userInfo.getManagerId());
        attributeMap.put(ActiveDirectoryUser.MANAGERNAME, userInfo.getManager());
        
        attributeMap.put(ActiveDirectoryUser.DISPLAYNAME, userInfo.getDisplayName());
        
        attributeMap.put(ActiveDirectoryUser.FIRSTNAME, userInfo.getGivenName());
        attributeMap.put(ActiveDirectoryUser.LASTNAME, userInfo.getFamilyName());
        
        attributeMap.put(ActiveDirectoryUser.GIVENNAME, userInfo.getGivenName());
        attributeMap.put(ActiveDirectoryUser.SN, userInfo.getFamilyName());
        
        attributeMap.put(ActiveDirectoryUser.GENDER, userInfo.getGender() + "");
        attributeMap.put(ActiveDirectoryUser.MOBILE, userInfo.getMobile());
        
        attributeMap.put(ActiveDirectoryUser.MAIL, userInfo.getEmail());
        attributeMap.put(ActiveDirectoryUser.EMAIL, userInfo.getEmail());
        
        attributeMap.put("institution", userInfo.getInstId());
        
        attributeMap.put(ActiveDirectoryUser.USERSTATUS, userInfo.getStatus() + "");
        
        return attributeMap;
    }

}
