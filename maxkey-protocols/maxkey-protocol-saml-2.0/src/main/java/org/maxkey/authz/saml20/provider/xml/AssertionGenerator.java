package org.maxkey.authz.saml20.provider.xml;

import java.util.ArrayList;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.maxkey.authz.saml.service.IDService;
import org.maxkey.authz.saml.service.TimeService;
import org.maxkey.authz.saml20.binding.BindingAdapter;
import org.maxkey.authz.saml20.xml.IssuerGenerator;
import org.maxkey.domain.apps.AppsSAML20Details;
import org.maxkey.web.WebContext;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.xml.security.BasicSecurityConfiguration;
import org.opensaml.xml.security.credential.BasicCredential;
import org.opensaml.xml.security.keyinfo.KeyInfoGeneratorFactory;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureConstants;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.signature.impl.SignatureBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AssertionGenerator {
	private final static Logger logger = LoggerFactory.getLogger(AssertionGenerator.class);

	private final IssuerGenerator issuerGenerator;
	private final SubjectGenerator subjectGenerator;
	private final IDService idService;
	private final TimeService timeService;
	private final AuthnStatementGenerator authnStatementGenerator ;
	private final AttributeStatementGenerator attributeStatementGenerator;
	private final ConditionsGenerator conditionsGenerator;

	public AssertionGenerator(
							String issuerName,
							TimeService timeService, 
							IDService idService) {
		this.timeService = timeService;
		this.idService = idService;
		issuerGenerator = new IssuerGenerator(issuerName);
		subjectGenerator = new SubjectGenerator(timeService);
		authnStatementGenerator = new AuthnStatementGenerator();
		attributeStatementGenerator = new AttributeStatementGenerator();
		conditionsGenerator = new ConditionsGenerator();
	}

	public Assertion generateAssertion(
							AppsSAML20Details saml20Details,
							BindingAdapter bindingAdapter,
							String assertionConsumerURL, 
							String inResponseTo, 
							String audienceUrl,
							int validInSeconds,
							HashMap<String,String>attributeMap
							) {

		Assertion assertion = new AssertionBuilder().buildObject();;
		//Subject
		Subject subject = subjectGenerator.generateSubject(
						assertionConsumerURL,
						inResponseTo,
						validInSeconds);
		assertion.setSubject(subject);
		//issuer
		Issuer issuer = issuerGenerator.generateIssuer();
		assertion.setIssuer(issuer);
		//AuthnStatements
		DateTime authnInstant = new DateTime(WebContext.getSession().getCreationTime());
		AuthnStatement authnStatement = authnStatementGenerator.generateAuthnStatement(authnInstant);
		assertion.getAuthnStatements().add(authnStatement);
		//AttributeStatements
		ArrayList<GrantedAuthority> grantedAuthoritys = new ArrayList<GrantedAuthority>();
		grantedAuthoritys.add(new SimpleGrantedAuthority("ROLE_USER"));
		for(GrantedAuthority anthGrantedAuthority:  ((UsernamePasswordAuthenticationToken)WebContext.getAuthentication()).getAuthorities()){
			grantedAuthoritys.add(anthGrantedAuthority);
		}
		AttributeStatement attributeStatement =attributeStatementGenerator.generateAttributeStatement(
									saml20Details, grantedAuthoritys,attributeMap);
		assertion.getAttributeStatements().add(attributeStatement);
		//ID
		assertion.setID(idService.generateID());
		//IssueInstant
		assertion.setIssueInstant(timeService.getCurrentDateTime());
		//Conditions
		Conditions conditions = conditionsGenerator.generateConditions(audienceUrl,validInSeconds);
		assertion.setConditions(conditions);
		//sign Assertion
		try{
			
	        BasicCredential basicCredential = new BasicCredential();
	        basicCredential.setPrivateKey(bindingAdapter.getSigningCredential().getPrivateKey());
	        
	        Signature signature = new SignatureBuilder().buildObject();
	        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
	        signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
	        
	        signature.setSigningCredential(basicCredential);
	        KeyInfoGeneratorFactory keyInfoGeneratorFactory = Configuration
					.getGlobalSecurityConfiguration()
					.getKeyInfoGeneratorManager().getDefaultManager()
					.getFactory(bindingAdapter.getSigningCredential());
	        
	        signature.setKeyInfo(keyInfoGeneratorFactory.newInstance().generate(bindingAdapter.getSigningCredential()));
	        BasicSecurityConfiguration config = (BasicSecurityConfiguration) Configuration.getGlobalSecurityConfiguration();
	        config.registerSignatureAlgorithmURI("RSA", SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
	        config.setSignatureReferenceDigestMethod(SignatureConstants.ALGO_ID_DIGEST_SHA256);
			assertion.setSignature(signature);

			Configuration.getMarshallerFactory().getMarshaller(assertion).marshall(assertion);
            Signer.signObject(signature);
            
			logger.debug("assertion.isSigned "+assertion.isSigned());
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("Unable to Signer assertion ",e);
		}

		return assertion;
	}
}
