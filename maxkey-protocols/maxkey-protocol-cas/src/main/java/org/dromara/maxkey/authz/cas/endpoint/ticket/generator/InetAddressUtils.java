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
 

package org.dromara.maxkey.authz.cas.endpoint.ticket.generator;

import java.net.InetAddress;

/**
 * This is {@link InetAddressUtils}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
public final class InetAddressUtils {
   
    private InetAddressUtils() {
    }

    /**
     * Gets cas server host name.
     *
     * @return the cas server host name
     */
    public static String getCasServerHostName() {
        try {
            final String hostName = InetAddress.getLocalHost().getCanonicalHostName();
            final int index = hostName.indexOf('.');
            if (index > 0) {
                return hostName.substring(0, index);
            }
            return hostName;
        } catch (final Exception e) {
            throw new IllegalArgumentException("Host name could not be determined automatically.", e);
        }
    }
}
