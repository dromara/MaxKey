package org.maxkey.crypto.password.opt.token;

import org.joda.time.DateTime;
import org.maxkey.constants.ConstantsTimeInterval;
import org.maxkey.crypto.password.opt.OneTimePassword;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.redis.RedisConnection;
import org.maxkey.persistence.redis.RedisConnectionFactory;

public class RedisOptTokenStore  extends AbstractOptTokenStore {
    
    protected int validitySeconds = ConstantsTimeInterval.ONE_MINUTE * 5;
    
    RedisConnectionFactory connectionFactory;
    
    public static String PREFIX = "REDIS_OTP_SERVICE_";
    
    @Override
    public void store(UserInfo userInfo, String token, String receiver, String type) {
        DateTime currentDateTime = new DateTime();
        OneTimePassword otp = new OneTimePassword();
        otp.setId(userInfo.getUsername() + "_" + type + "_" + token);
        otp.setType(type);
        otp.setUsername(userInfo.getUsername());
        otp.setToken(token);
        otp.setReceiver(receiver);
        otp.setCreateTime(currentDateTime.toString("yyyy-MM-dd HH:mm:ss"));
        RedisConnection conn = connectionFactory.getConnection();
        conn.setexObject(PREFIX + otp.getId(), validitySeconds, otp);
        conn.close();
    }

    @Override
    public boolean validate(UserInfo userInfo, String token, String type, int interval) {
        RedisConnection conn = connectionFactory.getConnection();
        OneTimePassword otp = (OneTimePassword)conn.getObject(
                PREFIX + userInfo.getUsername() + "_" + type + "_" + token);
        conn.delete(PREFIX + userInfo.getUsername() + "_" + type + "_" + token);
        conn.close();
        if (otp != null) { 
            return true;
        }
        return false;
    }

}
