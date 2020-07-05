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
 

package org.maxkey.client.http;

import java.io.*;
import java.net.*;
import java.util.*;

import org.maxkey.client.oauth.exceptions.*;
import org.maxkey.client.utils.StreamUtils;

/**
 * Represents an HTTP Response.
 * 
 * @author Pablo Fernandez
 */
public class Response {

    private int code;
    private String message;
    private String body;
    private InputStream stream;
    private Map<String, String> headers;

    public Response(int code, String message, Map<String, String> headers, String body, InputStream stream) {
        this.code = code;
        this.headers = headers;
        this.body = body;
        this.message = message;
        this.stream = stream;
    }

    Response(HttpURLConnection connection) throws IOException {
        try {
            connection.connect();
            code = connection.getResponseCode();
            message = connection.getResponseMessage();
            headers = parseHeaders(connection);
            stream = isSuccessful() ? connection.getInputStream() : connection.getErrorStream();
        } catch (UnknownHostException e) {
            throw new OAuthException("The IP address of a host could not be determined.", e);
        }
    }

    private String parseBodyContents() throws IOException {
        if ("gzip".equals(getHeader("Content-Encoding"))) {
            body = StreamUtils.getGzipStreamContents(getStream());
        } else {
            body = StreamUtils.getStreamContents(getStream());
        }
        return body;
    }

    private Map<String, String> parseHeaders(HttpURLConnection conn) {
        final Map<String, String> headers = new HashMap<>();
        for (String key : conn.getHeaderFields().keySet()) {
            headers.put(key, conn.getHeaderFields().get(key).get(0));
        }
        return headers;
    }

    public final boolean isSuccessful() {
        return getCode() >= 200 && getCode() < 400;
    }

    public String getBody()  {
        try {
			return body == null ? parseBodyContents() : body;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "";
    }

    /**
     * Obtains the meaningful stream of the HttpUrlConnection, either inputStream or errorInputStream, depending on the
     * status code
     *
     * @return input stream / error stream
     */
    public InputStream getStream() {
        return stream;
    }

    /**
     * Obtains the HTTP status code
     *
     * @return the status code
     */
    public final int getCode() {
        return code;
    }

    /**
     * Obtains the HTTP status message. Returns <code>null</code> if the message can not be discerned from the response
     * (not valid HTTP)
     *
     * @return the status message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Obtains a {@link Map} containing the HTTP Response Headers
     *
     * @return headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Obtains a single HTTP Header value, or null if undefined
     *
     * @param name the header name.
     *
     * @return header value or null.
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public String toString() {
        return "Response{" +
            "code=" + code +
            ", message='" + message + '\'' +
            ", body='" + body + '\'' +
            ", headers=" + headers +
            '}';
    }
}
