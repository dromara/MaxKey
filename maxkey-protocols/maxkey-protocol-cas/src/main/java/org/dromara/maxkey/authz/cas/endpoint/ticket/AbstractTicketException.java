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
 

package org.dromara.maxkey.authz.cas.endpoint.ticket;


/**
 * Generic ticket exception. Top of the AbstractTicketException hierarchy.
 *
 * @author Scott Battaglia
 * @since 4.2.0
 */
public abstract class AbstractTicketException{
    String code;
    String msg;
    Throwable throwable;
    /**
     * Instantiates a new ticket exception.
     *
     * @param code the code
     * @param throwable the throwable
     */
    public AbstractTicketException(final String code, final Throwable throwable) {
    	this.code=code;
    	this.throwable=throwable;
    }

    /**
     * Instantiates a new ticket exception.
     *
     * @param code the code
     */
    public AbstractTicketException(final String code) {
    	this.code=code;
    }

    /**
     * Instantiates a new ticket exception.
     *
     * @param code the code
     * @param msg the msg
     */
    public AbstractTicketException(final String code, final String msg) {
    	this.code=code;
    	this.msg=msg;
    }
}
