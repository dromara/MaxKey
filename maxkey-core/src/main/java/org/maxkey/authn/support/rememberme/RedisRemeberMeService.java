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

}
