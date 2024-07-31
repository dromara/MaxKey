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

import org.joda.time.DateTime;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.impl.AudienceBuilder;
import org.opensaml.saml2.core.impl.AudienceRestrictionBuilder;
import org.opensaml.saml2.core.impl.ConditionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionsGenerator {
	private static final  Logger logger = LoggerFactory.getLogger(ConditionsGenerator.class);
	
	public Conditions generateConditions(String audienceUrl,int validInSeconds) {
		Conditions conditions = new ConditionsBuilder().buildObject();
		conditions.setNotBefore(new DateTime());
		conditions.setNotOnOrAfter(new DateTime().plus(validInSeconds*1000));
		
		AudienceRestriction audienceRestriction=builderAudienceRestriction(audienceUrl);
		conditions.getAudienceRestrictions().add(audienceRestriction);
		
		return conditions;
	}
	
	public AudienceRestriction builderAudienceRestriction(String audienceUrl){
		AudienceRestriction audienceRestriction = new AudienceRestrictionBuilder().buildObject();
		
		Audience audience = new AudienceBuilder().buildObject();
		audience.setAudienceURI(audienceUrl);
		
		audienceRestriction.getAudiences().add(audience);
		logger.debug("Audience URL "+audienceUrl);
		return audienceRestriction;
		
	}
}
