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
 

package org.dromara.maxkey.authz.saml20.binding.decoder;

import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.binding.decoding.HTTPRedirectDeflateDecoder;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.InTransport;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.UrlUtils;

import jakarta.servlet.http.HttpServletRequest;

public class OpenHTTPRedirectDecoder extends HTTPRedirectDeflateDecoder {
	private final Logger log = LoggerFactory.getLogger(OpenHTTPRedirectDecoder.class);

	private String receiverEndpoint;

	public OpenHTTPRedirectDecoder() {
		super();
	}

	public OpenHTTPRedirectDecoder(ParserPool pool) {
		super(pool);
	}

	/**
	 * Check the validity of the SAML protocol message receiver endpoint against
	 * requirements indicated in the message.
	 * 
	 * @param messageContext
	 *            current message context
	 * 
	 * @throws SecurityException
	 *             thrown if the message Destination attribute is invalid with
	 *             respect to the receiver's endpoint
	 * @throws MessageDecodingException
	 *             thrown if there is a problem decoding and processing the
	 *             message Destination or receiver endpoint information
	 */
	
	@Override
	@SuppressWarnings("rawtypes")
	protected void checkEndpointURI(SAMLMessageContext messageContext)throws SecurityException, MessageDecodingException {

		log.debug("Checking SAML message intended destination endpoint against receiver endpoint");

		String messageDestination = DatatypeHelper
				.safeTrimOrNullString(getIntendedDestinationEndpointURI(messageContext));

		boolean bindingRequires = isIntendedDestinationEndpointURIRequired(messageContext);

		if (messageDestination == null) {
			if (bindingRequires) {
				log.error("SAML message intended destination endpoint URI required by binding was empty");
				throw new SecurityException(
						"SAML message intended destination (required by binding) was not present");
			} else {
				log.debug("SAML message intended destination endpoint in message was empty, not required by binding, skipping");
				return;
			}
		}

		String receiverEndpoint = DatatypeHelper
				.safeTrimOrNullString(getActualReceiverEndpointURI(messageContext));

		log.debug("Intended message destination endpoint: {}",messageDestination);
		log.debug("Actual message receiver endpoint: {}", receiverEndpoint);

		// 协议头统一（http或https，需要和destination统一）
		if (messageDestination.indexOf("/") != -1
				&& receiverEndpoint.indexOf("/") != -1) {
			if (!messageDestination.substring(0,
					messageDestination.indexOf("/"))
					.equalsIgnoreCase(
							receiverEndpoint.substring(0,
									receiverEndpoint.indexOf("/")))) {
				receiverEndpoint = messageDestination.substring(0,
						messageDestination.indexOf("/"))
						+ receiverEndpoint.substring(receiverEndpoint
								.indexOf("/"));
			}
		}
		boolean matched = compareEndpointURIs(messageDestination,
				receiverEndpoint);
		if (!matched) {
			log.error(
					"SAML message intended destination endpoint '{}' did not match the recipient endpoint '{}'",
					messageDestination, receiverEndpoint);
			throw new SecurityException(
					"SAML message intended destination endpoint did not match recipient endpoint");
		} else {
			log.debug("SAML message intended destination endpoint matched recipient endpoint");
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected String getActualReceiverEndpointURI(SAMLMessageContext messageContext) throws MessageDecodingException {
		InTransport inTransport = messageContext.getInboundMessageTransport();
		if (!(inTransport instanceof HttpServletRequestAdapter)) {
			throw new MessageDecodingException(
					"Message context InTransport instance was an unsupported type");
		}
		HttpServletRequest httpRequest = 
				((HttpServletRequestAdapter) inTransport).getWrappedRequest();
		String requestUrl = UrlUtils.buildFullRequestUrl(httpRequest);
		if(requestUrl.indexOf("?") > -1) {
			return requestUrl.substring(0, requestUrl.indexOf("?"));
		}else {
			return requestUrl;
		}
	}

	/**
	 * @param receiverEndpoint
	 *            the receiverEndpoint to set
	 */
	public void setReceiverEndpoint(String receiverEndpoint) {
		this.receiverEndpoint = receiverEndpoint;
	}

	/**
	 * @return the receiverEndpoint
	 */
	public String getReceiverEndpoint() {
		return receiverEndpoint;
	}
}
