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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;


/**
 * Deflate (RFC 1951) utilities.
 *
 * @author Vladimir Dzhuvinov
 * @version 2013-04-16
 */
public class DeflateUtils {


	/**
	 * Omit headers and CRC fields from output, as specified by RFC 1950.
	 * Note that the Deflater JavaDocs are incorrect, see
	 * http://stackoverflow.com/questions/11076060/decompressing-gzipped-data-with-inflater-in-java
	 */
	private static final boolean NOWRAP = true;


	/**
	 * Compresses the specified byte array according to the DEFLATE 
	 * specification (RFC 1951).
	 *
	 * @param bytes The byte array to compress. Must not be {@code null}.
	 *
	 * @return The compressed bytes.
	 *
	 * @throws IOException If compression failed.
	 */
	public static byte[] compress(final byte[] bytes)
		throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();


		Deflater deflater = null;
		DeflaterOutputStream def = null;
		try {
			deflater = new Deflater(Deflater.DEFLATED, NOWRAP);
			def = new DeflaterOutputStream(out, deflater);
			def.write(bytes);
		} finally {
			if(def != null) {
				def.close();
			}
			if(deflater != null) {
				deflater.end();
			}
		}

		return out.toByteArray();
	}


	/**
	 * Decompresses the specified byte array according to the DEFLATE
	 * specification (RFC 1951).
	 *
	 * @param bytes The byte array to decompress. Must not be {@code null}.
	 *
	 * @return The decompressed bytes.
	 *
	 * @throws IOException If decompression failed.
	 */
	public static byte[] decompress(final byte[] bytes)
			throws IOException {

		Inflater inflater = null;
		InflaterInputStream inf = null;
		try {
			inflater = new Inflater(NOWRAP);
			inf = new InflaterInputStream(new ByteArrayInputStream(bytes), inflater);

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			// Transfer bytes from the compressed array to the output
			byte[] buf = new byte[1024];

			int len;

			while ((len = inf.read(buf)) > 0) {

				out.write(buf, 0, len);
			}

			return out.toByteArray();
		} finally {
			if(inf != null) {
				inf.close();
			}
			if(inflater != null) {
				inflater.end();
			}
		}
	}


	/**
	 * Prevents public instantiation.
	 */
	private DeflateUtils() {

	}
}
