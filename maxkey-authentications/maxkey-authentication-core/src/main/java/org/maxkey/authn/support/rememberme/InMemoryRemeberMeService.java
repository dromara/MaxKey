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

import java.time.Duration;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.maxkey.constants.ConstantsTimeInterval;

public class InMemoryRemeberMeService   extends AbstractRemeberMeService {

    protected static final UserManagedCache<String, RemeberMe> remeberMeStore = 
            UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, RemeberMe.class)
                .withExpiry(
                    ExpiryPolicyBuilder.timeToLiveExpiration(
                        Duration.ofMinutes(ConstantsTimeInterval.TWO_WEEK)
                    )
                )
                .build(true);
    
    @Override
    public void save(RemeberMe remeberMe) {
        remeberMeStore.put(remeberMe.getUsername(), remeberMe);
    }

    @Override
    public void update(RemeberMe remeberMe) {
        remeberMeStore.put(remeberMe.getUsername(), remeberMe);
    }

    @Override
    public RemeberMe read(RemeberMe remeberMe) {
        return remeberMeStore.get(remeberMe.getUsername());
    }

    @Override
    public void remove(String username) {
        remeberMeStore.remove(username);
    }

}
