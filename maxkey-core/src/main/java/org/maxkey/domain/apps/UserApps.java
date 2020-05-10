package org.maxkey.domain.apps;

/**
 * UserApps .
 * @author Crystal.Sea
 *
 */
public class UserApps extends Apps {
    private static final long serialVersionUID = 3186085827268041549L;

    private String username;

    private String userId;

    private String displayName;

    public UserApps() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "UserApplications [username=" + username 
                + ", userId=" + userId + ", displayName=" + displayName + "]";
    }

}
