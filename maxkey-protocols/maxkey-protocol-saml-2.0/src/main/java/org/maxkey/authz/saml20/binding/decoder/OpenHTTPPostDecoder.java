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
 

package org.maxkey.authz.saml20.binding.decoder;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.saml.saml2.binding.decoding.impl.HTTPPostDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.shibboleth.utilities.java.support.xml.ParserPool;

public class OpenHTTPPostDecoder extends HTTPPostDecoder {
    private final Logger log = LoggerFactory.getLogger(OpenHTTPPostDecoder.class);

    private String receiverEndpoint;

    public OpenHTTPPostDecoder() {
        super();
    }

    public OpenHTTPPostDecoder(ParserPool pool) {
        super(pool);
    }

    /**
     * Check the validity of the SAML protocol message receiver endpoint against
     * requirements indicated in the message.
     * 
     * @param messageContext current message context
     * 
     * @throws SecurityException        thrown if the message Destination attribute
     *                                  is invalid with respect to the receiver's
     *                                  endpoint
     * @throws MessageDecodingException thrown if there is a problem decoding and
     *                                  processing the message Destination or
     *                                  receiver endpoint information
     */
    protected void checkEndpointURI(MessageContext messageContext)
            throws SecurityException, MessageDecodingException {

        log.debug("Checking SAML message intended destination endpoint against receiver endpoint");

        String messageDestination = DatatypeHelper
                .safeTrimOrNullString(getIntendedDestinationEndpointURI(messageContext));

        boolean bindingRequires = isIntendedDestinationEndpointURIRequired(messageContext);

        if (messageDestination == null) {
            if (bindingRequires) {
                log.error("SAML message intended destination endpoint URI required by binding was empty");
                throw new SecurityException("SAML message intended destination (required by binding) was not present");
            } else {
                log.debug(
                        "SAML message intended destination endpoint in message was empty, not required by binding, skipping");
                return;
            }
        }

        String receiverEndpoint = DatatypeHelper.safeTrimOrNullString(getActualReceiverEndpointURI(messageContext));

        log.debug("Intended message destination endpoint: {}", messageDestination);
        log.debug("Actual message receiver endpoint: {}", receiverEndpoint);

        // 鍗忚澶寸粺涓�锛坔ttp鎴杊ttps锛岄渶瑕佸拰destination缁熶竴锛�
        if (messageDestination.indexOf("/") != -1 && receiverEndpoint.indexOf("/") != -1) {
            if (!messageDestination.substring(0, messageDestination.indexOf("/"))
                    .equalsIgnoreCase(receiverEndpoint.substring(0, receiverEndpoint.indexOf("/")))) {
                receiverEndpoint = messageDestination.substring(0, messageDestination.indexOf("/"))
                        + receiverEndpoint.substring(receiverEndpoint.indexOf("/"));
            }
        }
        boolean matched = compareEndpointURIs(messageDestination, receiverEndpoint);
        if (!matched) {
            log.error("SAML message intended destination endpoint '{}' did not match the recipient endpoint '{}'",
                    messageDestination, receiverEndpoint);
            throw new SecurityException("SAML message intended destination endpoint did not match recipient endpoint");
        } else {
            log.debug("SAML message intended destination endpoint matched recipient endpoint");
        }
    }

    @Override
    protected String getActualReceiverEndpointURI(MessageContext messageContext) throws MessageDecodingException {
        InTransport inTransport = messageContext.getInboundMessageTransport();
        if (!(inTransport instanceof HttpServletRequestAdapter)) {
            throw new MessageDecodingException("Message context InTransport instance was an unsupported type");
        }
        HttpServletRequest httpRequest = ((HttpServletRequestAdapter) inTransport).getWrappedRequest();

        StringBuffer urlBuilder = httpRequest.getRequestURL();

        String tempUrl = urlBuilder.toString();
        // 浠巋ttp鍗忚澶村紑濮嬶紝璺宠繃鍓嶉潰涓や釜鏂滄潬
        tempUrl = tempUrl.substring(tempUrl.indexOf("/", 8) + 1);
        return receiverEndpoint + tempUrl;
    }

    /**
     * @param receiverEndpoint the receiverEndpoint to set
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
