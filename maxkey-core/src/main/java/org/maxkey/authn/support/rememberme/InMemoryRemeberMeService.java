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
