package org.maxkey.authz.saml20.provider.xml;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.maxkey.authz.saml20.binding.BindingAdapter;
import org.maxkey.domain.ExtraAttr;
import org.maxkey.domain.ExtraAttrs;
import org.maxkey.domain.apps.AppsSAML20Details;
import org.maxkey.web.WebContext;
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
	private final static Logger logger = LoggerFactory.getLogger(AttributeStatementGenerator.class);
	
	private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

	public AttributeStatement generateAttributeStatement(Collection<GrantedAuthority> authorities) {
		return generateAttributeStatement(authorities, null);

	}

	public AttributeStatement generateAttributeStatement(
					Collection<GrantedAuthority> authorities, 
					HashMap<String,String>attributeMap) {

		AttributeStatementBuilder attributeStatementBuilder = (AttributeStatementBuilder) builderFactory.getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
		AttributeStatement attributeStatement = attributeStatementBuilder.buildObject();
		if(null!=authorities){
			Attribute attributeGrantedAuthority=builderGrantedAuthority(authorities);
			attributeStatement.getAttributes().add(attributeGrantedAuthority);
		}
		
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
		 BindingAdapter bindingAdapter = (BindingAdapter) WebContext.getSession().getAttribute("samlv20Adapter");
		AppsSAML20Details saml20Details = bindingAdapter.getSaml20Details();
		logger.debug("ExtendAttr "+saml20Details.getExtendAttr());
		ExtraAttrs extraAttrs=new ExtraAttrs(saml20Details.getExtendAttr());
		for(ExtraAttr extraAttr : extraAttrs.getExtraAttrs()) {
			logger.debug("Attribute : "+extraAttr.getAttr()+" , Vale : "+extraAttr.getValue()+" , Type : "+extraAttr.getType());
			attributeStatement.getAttributes().add(builderAttribute(extraAttr.getAttr(),extraAttr.getValue(),extraAttr.getType()));
		}
		//tencent
		//attributeStatement.getAttributes().add(builderAttribute("https://cloud.tencent.com/SAML/Attributes/Role","qcs::cam::uin/100013138092:roleName/cloud.tencent_maxkey,qcs::cam::uin/100013138092:saml-provider/maxkey",Attribute.UNSPECIFIED));
		//attributeStatement.getAttributes().add(builderAttribute("https://cloud.tencent.com/SAML/Attributes/RoleSessionName","maxkey",Attribute.UNSPECIFIED));
		//aliyun
		//attributeStatement.getAttributes().add(builderAttribute("https://www.aliyun.com/SAML-Role/Attributes/RoleSessionName","2037230828547234327",Attribute.UNSPECIFIED));
		//attributeStatement.getAttributes().add(builderAttribute("https://www.aliyun.com/SAML-Role/Attributes/Role","acs:ram::1729982683323703:role/maxkey,acs:ram::1729982683323703:saml-provider/maxkey",Attribute.UNSPECIFIED));
		//attributeStatement.getAttributes().add(builderAttribute("https://www.aliyun.com/SAML-Role/Attributes/SessionDuration","1800",Attribute.UNSPECIFIED));
		
		return attributeStatement;
	}
	
	public Attribute builderAttribute(String attributeName,String value ,String nameFormat){
		AttributeBuilder attributeBuilder = (AttributeBuilder) builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
		Attribute attribute = attributeBuilder.buildObject();
		attribute.setName(attributeName);

		// urn:oasis:names:tc:SAML:2.0:attrname-format:basic
		attribute.setNameFormat(nameFormat);

		// Response/Assertion/AttributeStatement/Attribute/AttributeValue
		XSStringBuilder stringBuilder = (XSStringBuilder) builderFactory.getBuilder(XSString.TYPE_NAME);
		XSString stringValue = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
		stringValue.setValue(value);

		attribute.getAttributeValues().add(stringValue);
		
		return attribute;
	}
	
	public Attribute   builderGrantedAuthority(Collection<GrantedAuthority> authorities){
		// Response/Assertion/AttributeStatement/Attribute
		AttributeBuilder attributeBuilder = (AttributeBuilder) builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
		Attribute attribute = attributeBuilder.buildObject();
		attribute.setName("GrantedAuthority");

		// urn:oasis:names:tc:SAML:2.0:attrname-format:basic
		attribute.setNameFormat(Attribute.BASIC);

		for (GrantedAuthority grantedAuthority : authorities) {
			// this was convoluted to figure out
			// Response/Assertion/AttributeStatement/Attribute/AttributeValue
			XSStringBuilder stringBuilder = (XSStringBuilder) Configuration.getBuilderFactory().getBuilder(XSString.TYPE_NAME);
			XSString stringValue = stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
			stringValue.setValue(grantedAuthority.getAuthority());
			attribute.getAttributeValues().add(stringValue);

		}
		return attribute;
	}
	
	

}
