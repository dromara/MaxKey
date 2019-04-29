
package org.maxkey.authz.saml20.provider.xml;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.impl.AudienceBuilder;
import org.opensaml.saml2.core.impl.AudienceRestrictionBuilder;
import org.opensaml.saml2.core.impl.ConditionsBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;

public class ConditionsGenerator {

	private final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

	public Conditions generateConditions(String audienceUrl,int validInSeconds) {
		ConditionsBuilder conditionsBuilder = (ConditionsBuilder) builderFactory.getBuilder(Conditions.DEFAULT_ELEMENT_NAME);
		
		Conditions conditions = conditionsBuilder.buildObject();
		conditions.setNotBefore(new DateTime());
		conditions.setNotOnOrAfter(new DateTime().plus(validInSeconds*1000));
		
		AudienceRestriction audienceRestriction=builderAudienceRestriction(audienceUrl);
		conditions.getAudienceRestrictions().add(audienceRestriction);
		
		return conditions;
	}
	
	public AudienceRestriction builderAudienceRestriction(String audienceUrl){
		AudienceRestrictionBuilder audienceRestrictionBuilder =  (AudienceRestrictionBuilder) builderFactory.getBuilder(AudienceRestriction.DEFAULT_ELEMENT_NAME);
		AudienceRestriction audienceRestriction = audienceRestrictionBuilder.buildObject();
		
		AudienceBuilder audienceBuilder = (AudienceBuilder) builderFactory.getBuilder(Audience.DEFAULT_ELEMENT_NAME);
		Audience audience = audienceBuilder.buildObject();
		audience.setAudienceURI(audienceUrl);
		
		audienceRestriction.getAudiences().add(audience);
		
		return audienceRestriction;
		
	}
}
