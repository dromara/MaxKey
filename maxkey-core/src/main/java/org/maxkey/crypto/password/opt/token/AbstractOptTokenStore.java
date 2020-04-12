package org.maxkey.crypto.password.opt.token;

import org.maxkey.domain.UserInfo;

public abstract class AbstractOptTokenStore {
    
    public abstract void store(UserInfo userInfo, String token, String receiver, String type);
    
    public abstract boolean validate(UserInfo userInfo, String token, String type,int interval);
}
