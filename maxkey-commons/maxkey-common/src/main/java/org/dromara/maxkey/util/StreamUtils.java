/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 
package org.dromara.maxkey.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

/**
 * Utils to deal with Streams.
 */
public abstract class StreamUtils {

    /**
     * Returns the stream contents as an UTF-8 encoded string
     *
     * @param is input stream
     * @return string contents
     * @throws java.io.IOException in any. SocketTimeout in example
     */
    public static String getStreamContents(InputStream is) throws IOException {
        Preconditions.checkNotNull(is, "Cannot get String from a null object");
        final char[] buffer = new char[0x10000];
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(is, "UTF-8")) {
            int read;
            do {
                read = in.read(buffer, 0, buffer.length);
                if (read > 0) {
                    out.append(buffer, 0, read);
                }
            } while (read >= 0);
        }
        return out.toString();
    }

    /**
     * Return String content from a gzip stream
     *
     * @param is input stream
     * @return string contents
     * @throws java.io.IOException in any. SocketTimeout in example
     */
    public static String getGzipStreamContents(InputStream is) throws IOException {
        Preconditions.checkNotNull(is, "Cannot get String from a null object");
        final GZIPInputStream gis = new GZIPInputStream(is);
        return getStreamContents(gis);
    }
}