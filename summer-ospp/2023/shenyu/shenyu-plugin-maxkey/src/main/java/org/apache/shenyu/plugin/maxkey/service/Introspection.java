/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.plugin.maxkey.service;

public class Introspection {

    private String token;

    private boolean active;

    private String sub;

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * Gets active.
     *
     * @return is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(final boolean active) {
        this.active = active;
    }

    /**
     * Gets sub.
     *
     * @return the sub
     */
    public String getSub() {
        return sub;
    }

    /**
     * Sets sub.
     *
     * @param sub the sub
     */
    public void setSub(final String sub) {
        this.sub = sub;
    }
}
