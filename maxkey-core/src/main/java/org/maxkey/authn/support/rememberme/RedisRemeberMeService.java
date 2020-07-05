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
 

package org.maxkey.authn.support.rememberme;

import org.maxkey.constants.ConstantsTimeInterval;
import org.maxkey.persistence.redis.RedisConnection;
import org.maxkey.persistence.redis.RedisConnectionFactory;

public class RedisRemeberMeService extends AbstractRemeberMeService {

    protected int serviceTicketValiditySeconds = ConstantsTimeInterval.TWO_WEEK;
    
    RedisConnectionFactory connectionFactory;
    
    public static String PREFIX = "REDIS_REMEBER_ME_SERVICE_";
    
    @Override
    public void save(RemeberMe remeberMe) {
        RedisConnection conn = connectionFactory.getConnection();
        conn.setexObject(PREFIX + remeberMe.getUsername(), serviceTicketValiditySeconds, remeberMe);
        conn.close();
    }

    @Override
    public void update(RemeberMe remeberMe) {
        RedisConnection conn = connectionFactory.getConnection();
        conn.setexObject(PREFIX + remeberMe.getUsername(), serviceTicketValiditySeconds, remeberMe);
        conn.close();
    }

    @Override
    public RemeberMe read(RemeberMe remeberMe) {
        RedisConnection conn = connectionFactory.getConnection();
        RemeberMe readRemeberMe = (RemeberMe)conn.getObject(PREFIX + remeberMe.getUsername());
        conn.close();
        return readRemeberMe;
    }

    @Override
    public void remove(String username) {
        RedisConnection conn = connectionFactory.getConnection();
        conn.delete(PREFIX + username);
        conn.close();
    }

    public RedisRemeberMeService(RedisConnectionFactory connectionFactory) {
        super();
        this.connectionFactory = connectionFactory;
    }

    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    
}
