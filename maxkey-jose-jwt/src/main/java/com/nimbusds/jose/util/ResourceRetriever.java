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
import java.net.URL;


/**
 * Retriever of resources specified by URL.
 */
public interface ResourceRetriever {


	/**
	 * Retrieves the resource from the specified HTTP(S) URL.
	 *
	 * @param url The URL of the resource. Its scheme must be HTTP or
	 *            HTTPS. Must not be {@code null}.
	 *
	 * @return The retrieved resource.
	 *
	 * @throws IOException If the HTTP connection to the specified URL
	 *                     failed or the resource couldn't be retrieved.
	 */
	Resource retrieveResource(final URL url)
		throws IOException;
}