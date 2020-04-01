package org.maxkey.authn.support.rememberme;

import java.io.Serializable;
import java.util.Date;

public class RemeberMe implements Serializable {

    private static final long serialVersionUID = 8010496585233991785L;

    /**
     * The number of seconds in one year (= 60 * 60 * 24 * 365).
     */
    public static final Integer ONE_YEAR = 60 * 60 * 24 * 365;

    public static final Integer ONE_DAY = 60 * 60 * 24; // 1 day

    public static final Integer ONE_WEEK = ONE_DAY * 7; // 1 week

    public static final Integer TWO_WEEK = ONE_DAY * 14; // 2 week

    public static final Integer TWO_MONTH = ONE_DAY * 30; // 1 month

    String id;

    String username;

    String authKey;

    Date lastLogin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "RemeberMe [id=" + id 
                + ", username=" + username 
                + ", authKey=" + authKey + ", lastLogin=" + lastLogin
                + "]";
    }
}
