/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2016, Connect2id Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.nimbusds.jose.util;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import net.jcip.annotations.ThreadSafe;


/**
 * The default retriever of resources specified by URL. Provides setting of
 * HTTP connect and read timeouts as well as a size limit of the retrieved
 * entity. Caching header directives are not honoured.
 *
 * @author Vladimir Dzhuvinov
 * @version 2018-01-04
 */
@ThreadSafe
public class DefaultResourceRetriever extends AbstractRestrictedResourceRetriever implements RestrictedResourceRetriever {
	
	
	/**
	 * If {@code true} the disconnect method of the underlying
	 * HttpURLConnection is called after a successful or failed retrieval.
	 */
	private boolean disconnectAfterUse;
	
	
	/**
	 * Creates a new resource retriever. The HTTP timeouts and entity size
	 * limit are set to zero (infinite).
	 */
	public DefaultResourceRetriever() {
	
		this(0, 0);	
	}
	
	
	/**
	 * Creates a new resource retriever. The HTTP entity size limit is set
	 * to zero (infinite).
	 *
	 * @param connectTimeout The HTTP connects timeout, in milliseconds, 
	 *                       zero for infinite. Must not be negative.
	 * @param readTimeout    The HTTP read timeout, in milliseconds, zero 
	 *                       for infinite. Must not be negative.
	 */
	public DefaultResourceRetriever(final int connectTimeout, final int readTimeout) {

		this(connectTimeout, readTimeout, 0);
	}


	/**
	 * Creates a new resource retriever.
	 *
	 * @param connectTimeout The HTTP connects timeout, in milliseconds,
	 *                       zero for infinite. Must not be negative.
	 * @param readTimeout    The HTTP read timeout, in milliseconds, zero
	 *                       for infinite. Must not be negative.
	 * @param sizeLimit      The HTTP entity size limit, in bytes, zero for
	 *                       infinite. Must not be negative.
	 */
	public DefaultResourceRetriever(final int connectTimeout, final int readTimeout, final int sizeLimit) {
	
		this(connectTimeout, readTimeout, sizeLimit, true);
	}


	/**
	 * Creates a new resource retriever.
	 *
	 * @param connectTimeout     The HTTP connects timeout, in
	 *                           milliseconds, zero for infinite. Must not
	 *                           be negative.
	 * @param readTimeout        The HTTP read timeout, in milliseconds,
	 *                           zero for infinite. Must not be negative.
	 * @param sizeLimit          The HTTP entity size limit, in bytes, zero
	 *                           for infinite. Must not be negative.
	 * @param disconnectAfterUse If {@code true} the disconnect method of
	 *                           the underlying {@link HttpURLConnection}
	 *                           will be called after trying to retrieve
	 *                           the resource. Whether the TCP socket is
	 *                           actually closed or reused depends on the
	 *                           underlying HTTP implementation and the
	 *                           setting of the {@code keep.alive} system
	 *                           property.
	 */
	public DefaultResourceRetriever(final int connectTimeout,
					final int readTimeout,
					final int sizeLimit,
					final boolean disconnectAfterUse) {
	
		super(connectTimeout, readTimeout, sizeLimit);
		this.disconnectAfterUse = disconnectAfterUse;
	}
	
	
	/**
	 * Returns {@code true} if the disconnect method of the underlying
	 * {@link HttpURLConnection} will be called after trying to retrieve
	 * the resource. Whether the TCP socket is actually closed or reused
	 * depends on the underlying HTTP implementation and the setting of the
	 * {@code keep.alive} system property.
	 *
	 * @return If {@code true} the disconnect method of the underlying
	 *         {@link HttpURLConnection} will be called after trying to
	 *         retrieve the resource.
	 */
	public boolean disconnectsAfterUse() {
		
		return disconnectAfterUse;
	}
	
	
	/**
	 * Controls calling of the disconnect method the underlying
	 * {@link HttpURLConnection} after trying to retrieve the resource.
	 * Whether the TCP socket is actually closed or reused depends on the
	 * underlying HTTP implementation and the setting of the
	 * {@code keep.alive} system property.
	 *
	 * If {@code true} the disconnect method of the underlying
	 * {@link HttpURLConnection} will be called after trying to
	 * retrieve the resource.
	 */
	public void setDisconnectsAfterUse(final boolean disconnectAfterUse) {
		
		this.disconnectAfterUse = disconnectAfterUse;
	}


	@Override
	public Resource retrieveResource(final URL url)
		throws IOException {
		
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection)url.openConnection();
			
			con.setConnectTimeout(getConnectTimeout());
			con.setReadTimeout(getReadTimeout());
			
			final String content;
			
			InputStream inputStream = con.getInputStream();
			try {
				if (getSizeLimit() > 0) {
					inputStream = new BoundedInputStream(inputStream, getSizeLimit());
				}
				
				content = IOUtils.readInputStreamToString(inputStream, Charset.forName("UTF-8"));
				
			} finally {
				inputStream.close();
			}
	
			// Check HTTP code + message
			final int statusCode = con.getResponseCode();
			final String statusMessage = con.getResponseMessage();
	
			// Ensure 2xx status code
			if (statusCode > 299 || statusCode < 200) {
				throw new IOException("HTTP " + statusCode + ": " + statusMessage);
			}
	
			return new Resource(content, con.getContentType());
		
		} catch (ClassCastException e) {
			throw new IOException("Couldn't open HTTP(S) connection: " + e.getMessage(), e);
		} finally {
			if (disconnectAfterUse && con != null) {
				con.disconnect();
			}
		}
	}
}
