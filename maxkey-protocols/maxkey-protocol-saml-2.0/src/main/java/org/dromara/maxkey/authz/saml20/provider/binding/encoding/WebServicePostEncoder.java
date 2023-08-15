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
 

package org.dromara.maxkey.authz.saml20.provider.binding.encoding;

import java.io.UnsupportedEncodingException;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.binding.encoding.HTTPPostEncoder;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.StatusResponseType;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.SecurityConfiguration;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.SigningUtil;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServicePostEncoder extends HTTPPostEncoder {

	/** Class logger. */
	private final Logger log = LoggerFactory.getLogger(WebServicePostEncoder.class);

	public WebServicePostEncoder(VelocityEngine engine, String templateId) {
		super(engine, templateId);
	}

	@SuppressWarnings("rawtypes")
	public VelocityContext encodeMsgContext(MessageContext messageContext)
			throws MessageEncodingException {


		SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;

		SAMLObject outboundMessage = samlMsgCtx.getOutboundSAMLMessage();
		if (outboundMessage == null) {
			throw new MessageEncodingException(
					"No outbound SAML message contained in message context");
		}

		signMessage(samlMsgCtx);
		samlMsgCtx.setOutboundMessage(outboundMessage);

		return encodeMsgContext(samlMsgCtx);
	}

	/**
	 * Base64 and POST encodes the outbound message and writes it to the
	 * outbound transport.
	 * 
	 * @param messageContext
	 *            current message context
	 * @param endpointURL
	 *            endpoint URL to encode message to
	 * 
	 * @throws MessageEncodingException
	 *             thrown if there is a problem encoding the message
	 */
	@SuppressWarnings("rawtypes")
	protected VelocityContext encodeMsgContext(SAMLMessageContext messageContext)
			throws MessageEncodingException {

		try {
			VelocityContext context = new VelocityContext();

			populateVelocityContext(context, messageContext);

			return context;

		} catch (Exception e) {
			log.error("Error invoking velocity template", e);
			throw new MessageEncodingException(
					"Error creating output document", e);
		}
	}

	@SuppressWarnings("rawtypes")
	protected void populateVelocityContext(VelocityContext velocityContext,
			SAMLMessageContext messageContext) throws MessageEncodingException {

		log.debug("Marshalling and Base64 encoding SAML message");
		if (messageContext.getOutboundSAMLMessage().getDOM() == null) {
			marshallMessage(messageContext.getOutboundSAMLMessage());
		}
		try {
			String messageXML = XMLHelper.nodeToString(messageContext.getOutboundSAMLMessage().getDOM());
			String encodedMessage = Base64.encodeBytes(
					messageXML.getBytes("UTF-8"), Base64.DONT_BREAK_LINES);
			if (messageContext.getOutboundSAMLMessage() instanceof RequestAbstractType) {
				velocityContext.put("SAMLRequest", encodedMessage);
			} else if (messageContext.getOutboundSAMLMessage() instanceof StatusResponseType) {
				velocityContext.put("SAMLResponse", encodedMessage);
			} else {
				throw new MessageEncodingException(
						"SAML message is neither a SAML RequestAbstractType or StatusResponseType");
			}
		} catch (UnsupportedEncodingException e) {
			log.error("UTF-8 encoding is not supported, this VM is not Java compliant.");
			throw new MessageEncodingException(
					"Unable to encode message, UTF-8 encoding is not supported");
		}

		Credential signingCredential = messageContext.getOuboundSAMLMessageSigningCredential();
		if (signingCredential == null) {
			log.debug("No signing credential was supplied, skipping HTTP-Post simple signing");
			return;
		}

		String sigAlgURI = getSignatureAlgorithmURI(signingCredential, null);
		velocityContext.put("SigAlg", sigAlgURI);

		String formControlData = buildFormDataToSign(velocityContext,messageContext, sigAlgURI);
		velocityContext.put("Signature",generateSignature(signingCredential, sigAlgURI,formControlData));

		KeyInfoGenerator kiGenerator = SecurityHelper.getKeyInfoGenerator(signingCredential, null, null);
		
		if (kiGenerator != null) {
			String kiBase64 = buildKeyInfo(signingCredential, kiGenerator);
			if (!DatatypeHelper.isEmpty(kiBase64)) {
				velocityContext.put("KeyInfo", kiBase64);
			}
		}
	}

	/**
	 * Build the {@link KeyInfo} from the signing credential.
	 * 
	 * @param signingCredential
	 *            the credential used for signing
	 * @param kiGenerator
	 *            the generator for the KeyInfo
	 * @throws MessageEncodingException
	 *             thrown if there is an error generating or marshalling the
	 *             KeyInfo
	 * @return the marshalled, serialized and base64-encoded KeyInfo, or null if
	 *         none was generated
	 */
	protected String buildKeyInfo(Credential signingCredential,
			KeyInfoGenerator kiGenerator) throws MessageEncodingException {

		try {
			KeyInfo keyInfo = kiGenerator.generate(signingCredential);
			if (keyInfo != null) {
				Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(keyInfo);
				if (marshaller == null) {
					log.error("No KeyInfo marshaller available from configuration");
					throw new MessageEncodingException("No KeyInfo marshaller was configured");
				}
				String kiXML = XMLHelper.nodeToString(marshaller.marshall(keyInfo));
				String kiBase64 = Base64.encodeBytes(kiXML.getBytes(),Base64.DONT_BREAK_LINES);
				return kiBase64;
			} else {
				return null;
			}
		} catch (SecurityException e) {
			log.error("Error generating KeyInfo from signing credential", e);
			throw new MessageEncodingException(
					"Error generating KeyInfo from signing credential", e);
		} catch (MarshallingException e) {
			log.error("Error marshalling KeyInfo based on signing credential",
					e);
			throw new MessageEncodingException(
					"Error marshalling KeyInfo based on signing credential", e);
		}
	}

	/**
	 * Build the form control data string over which the signature is computed.
	 * 
	 * @param velocityContext
	 *            the Velocity context which is already populated with the
	 *            values for SAML message and relay state
	 * @param messageContext
	 *            the SAML message context being processed
	 * @param sigAlgURI
	 *            the signature algorithm URI
	 * 
	 * @return the form control data string for signature computation
	 */
	@SuppressWarnings("rawtypes")
	protected String buildFormDataToSign(VelocityContext velocityContext,
			SAMLMessageContext messageContext, String sigAlgURI) {
		StringBuilder builder = new StringBuilder();

		boolean isRequest = false;
		if (velocityContext.get("SAMLRequest") != null) {
			isRequest = true;
		}

		String msgB64;
		if (isRequest) {
			msgB64 = (String) velocityContext.get("SAMLRequest");
		} else {
			msgB64 = (String) velocityContext.get("SAMLResponse");
		}

		String msg = null;
		try {
			msg = new String(Base64.decode(msgB64), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// All JVM's required to support UTF-8
		}

		if (isRequest) {
			builder.append("SAMLRequest=" + msg);
		} else {
			builder.append("SAMLResponse=" + msg);
		}

		if (messageContext.getRelayState() != null) {
			builder.append("&RelayState=" + messageContext.getRelayState());
		}

		builder.append("&SigAlg=" + sigAlgURI);

		return builder.toString();
	}

	protected String getSignatureAlgorithmURI(Credential credential,
			SecurityConfiguration config) throws MessageEncodingException {

		SecurityConfiguration secConfig;
		if (config != null) {
			secConfig = config;
		} else {
			secConfig = Configuration.getGlobalSecurityConfiguration();
		}

		String signAlgo = secConfig.getSignatureAlgorithmURI(credential);

		if (signAlgo == null) {
			throw new MessageEncodingException(
					"The signing credential's algorithm URI could not be derived");
		}

		return signAlgo;
	}

	/**
	 * Generates the signature over the string of concatenated form control data
	 * as indicated by the SimpleSign spec.
	 * 
	 * @param signingCredential
	 *            credential that will be used to sign
	 * @param algorithmURI
	 *            algorithm URI of the signing credential
	 * @param formData
	 *            form control data to be signed
	 * 
	 * @return base64 encoded signature of form control data
	 * 
	 * @throws MessageEncodingException
	 *             there is an error computing the signature
	 */
	protected String generateSignature(
					Credential signingCredential,
					String algorithmURI, String formData)
					throws MessageEncodingException {

		log.debug(String
				.format("Generating signature with key type '%s', algorithm URI '%s' over form control string '%s'",
						SecurityHelper.extractSigningKey(signingCredential).getAlgorithm(), algorithmURI, formData));

		String b64Signature = null;
		try {
			byte[] rawSignature = SigningUtil.signWithURI(signingCredential,algorithmURI, formData.getBytes("UTF-8"));
			b64Signature = Base64.encodeBytes(rawSignature,Base64.DONT_BREAK_LINES);
			log.debug("Generated digital signature value (base64-encoded) {}",b64Signature);
		} catch (SecurityException e) {
			log.error("Error during URL signing process", e);
			throw new MessageEncodingException(
					"Unable to sign form control string", e);
		} catch (UnsupportedEncodingException e) {
			// UTF-8 encoding is required to be supported by all JVMs
		}

		return b64Signature;
	}
}
