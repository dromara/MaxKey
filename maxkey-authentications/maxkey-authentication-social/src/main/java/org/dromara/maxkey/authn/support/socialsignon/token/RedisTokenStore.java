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
 

package org.dromara.maxkey.authn.support.socialsignon.token;

import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsTimeInterval;
import java.util.concurrent.ConcurrentHashMap;

public class RedisTokenStore {
    
    protected int validitySeconds = ConstsTimeInterval.ONE_MINUTE * 2;


    private final ConcurrentHashMap<String, String> tokenStore = new ConcurrentHashMap<String, String>();

    public RedisTokenStore() {
        super();
    }

    public static String PREFIX = "REDIS_QRSCRAN_SERVICE_";
    

    public void store(String token) {
        tokenStore.put(PREFIX + token,"-1");
       /* DateTime currentDateTime = new DateTime();
        RedisConnection conn = connectionFactory.getConnection();
        conn.getConn().setex(PREFIX + token, validitySeconds, "-1");
        conn.close();*/
    }

    public boolean bindtoken(String token,String loginname) {
        boolean flag = false;
        try {
           /* DateTime currentDateTime = new DateTime();
            RedisConnection conn = connectionFactory.getConnection();
            conn.getConn().setex(PREFIX + token, validitySeconds, loginname);
            //conn.setexObject(PREFIX + token, validitySeconds, loginname);
            conn.close();*/
            tokenStore.put(PREFIX + token,loginname);
            return true;
        }catch (Exception e) {

        }
        return flag;
    }

    public String get(String token) {
      /*  RedisConnection conn = connectionFactory.getConnection();
        String value = conn.get(PREFIX + token);
        if(StringUtils.isNotEmpty(value) && !"-1".equalsIgnoreCase(value)) {
            conn.delete(PREFIX + token);
            return value;
        }*/

        String value = tokenStore.get(PREFIX + token);
        if(StringUtils.isNotEmpty(value) && !"-1".equalsIgnoreCase(value)) {
            tokenStore.remove(PREFIX + token);
            return value;
        }
        return value;
    }

}
