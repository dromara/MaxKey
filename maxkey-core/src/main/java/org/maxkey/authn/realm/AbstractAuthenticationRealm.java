package org.maxkey.authn.realm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.maxkey.authn.support.rememberme.AbstractRemeberMeService;
import org.maxkey.constants.ConstantsLoginType;
import org.maxkey.constants.ConstantsPasswordSetType;
import org.maxkey.constants.ConstantsStatus;
import org.maxkey.domain.Groups;
import org.maxkey.domain.PasswordPolicy;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.db.PasswordPolicyRowMapper;
import org.maxkey.persistence.db.UserInfoRowMapper;
import org.maxkey.util.DateUtils;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebConstants;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * AbstractAuthenticationRealm.
 * @author Crystal.Sea
 *
 */
public abstract class AbstractAuthenticationRealm {
    private static Logger _logger = LoggerFactory.getLogger(AbstractAuthenticationRealm.class);

    private static final String LOCK_USER_UPDATE_STATEMENT = "UPDATE USERINFO SET ISLOCKED = ?  , UNLOCKTIME = ? WHERE ID = ?";

    private static final String UNLOCK_USER_UPDATE_STATEMENT = "UPDATE USERINFO SET ISLOCKED = ? , UNLOCKTIME = ? WHERE ID = ?";

    private static final String BADPASSWORDCOUNT_UPDATE_STATEMENT = "UPDATE USERINFO SET BADPASSWORDCOUNT = ? , BADPASSWORDTIME = ?  WHERE ID = ?";

    private static final String BADPASSWORDCOUNT_RESET_UPDATE_STATEMENT = "UPDATE USERINFO SET BADPASSWORDCOUNT = ? , ISLOCKED = ? ,UNLOCKTIME = ?  WHERE ID = ?";

    private static final String HISTORY_LOGIN_INSERT_STATEMENT = "INSERT INTO HISTORY_LOGIN (ID , SESSIONID , UID , USERNAME , DISPLAYNAME , LOGINTYPE , MESSAGE , CODE , PROVIDER , SOURCEIP , BROWSER , PLATFORM , APPLICATION , LOGINURL )VALUES( ? , ? , ? , ? , ?, ? , ? , ?, ? , ? , ?, ? , ? , ?)";

    private static final String LOGIN_USERINFO_UPDATE_STATEMENT = "UPDATE USERINFO SET LASTLOGINTIME = ?  , LASTLOGINIP = ? , LOGINCOUNT = ?, ONLINE = "
            + UserInfo.ONLINE.ONLINE + "  WHERE ID = ?";

    private static final String LOGOUT_USERINFO_UPDATE_STATEMENT = "UPDATE USERINFO SET LASTLOGOFFTIME = ? , ONLINE = "
            + UserInfo.ONLINE.OFFLINE + "  WHERE ID = ?";

    private static final String HISTORY_LOGOUT_UPDATE_STATEMENT = "UPDATE HISTORY_LOGIN SET LOGOUTTIME = ?  WHERE  SESSIONID = ?";

    private static final String GROUPS_SELECT_STATEMENT = "SELECT DISTINCT G.ID,G.NAME FROM USERINFO U,GROUPS G,GROUP_MEMBER GM WHERE U.ID = ?  AND U.ID=GM.MEMBERID AND GM.GROUPID=G.ID ";

    private static final String DEFAULT_USERINFO_SELECT_STATEMENT = "SELECT * FROM	USERINFO WHERE USERNAME = ?";

    private static final String PASSWORD_POLICY_SELECT_STATEMENT = "SELECT ID,MINLENGTH,MAXLENGTH,LOWERCASE,UPPERCASE,DIGITS,SPECIALCHAR,ATTEMPTS,DURATION,EXPIRATION,USERNAME,SIMPLEPASSWORDS FROM PASSWORD_POLICY ";

    protected PasswordPolicy passwordPolicy;

    protected JdbcTemplate jdbcTemplate;

    protected boolean provisioning;

    @Autowired
    @Qualifier("remeberMeService")
    protected AbstractRemeberMeService remeberMeService;

    /**
     * 
     */
    public AbstractAuthenticationRealm() {

    }

    public AbstractAuthenticationRealm(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PasswordPolicy getPasswordPolicy() {
        if (passwordPolicy == null) {
            passwordPolicy = jdbcTemplate.queryForObject(PASSWORD_POLICY_SELECT_STATEMENT,
                    new PasswordPolicyRowMapper());
            _logger.debug("query PasswordPolicy : " + passwordPolicy);
        }
        return passwordPolicy;
    }

    public boolean passwordPolicyValid(UserInfo userInfo) {
        /*
         * check login attempts fail times
         */
        if (userInfo.getBadPasswordCount() >= getPasswordPolicy().getAttempts()) {
            _logger.debug("PasswordPolicy : " + passwordPolicy);
            _logger.debug("login Attempts is " + userInfo.getBadPasswordCount());
            lockUser(userInfo);

            throw new BadCredentialsException(
                    WebContext.getI18nValue("login.error.attempts") + " " + userInfo.getBadPasswordCount());
        }

        if (userInfo.getPasswordSetType() != ConstantsPasswordSetType.PASSWORD_NORMAL) {
            WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,
                    userInfo.getPasswordSetType());
            return true;
        } else {
            WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,
                    ConstantsPasswordSetType.PASSWORD_NORMAL);
        }

        /*
         * check password is Expired,if Expiration equals 0,not need check
         */
        if (getPasswordPolicy().getExpiration() > 0) {

            String passwordLastSetTimeString = userInfo.getPasswordLastSetTime().substring(0, 19);
            _logger.info("last password set date " + passwordLastSetTimeString);

            DateTime currentdateTime = new DateTime();
            DateTime changePwdDateTime = DateTime.parse(passwordLastSetTimeString,
                    DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            Duration duration = new Duration(changePwdDateTime, currentdateTime);
            int intDuration = Integer.parseInt(duration.getStandardDays() + "");
            _logger.debug("validate duration " + intDuration);
            _logger.debug("validate result " + (intDuration <= getPasswordPolicy().getExpiration()));
            if (intDuration > getPasswordPolicy().getExpiration()) {
                WebContext.getSession().setAttribute(WebConstants.CURRENT_LOGIN_USER_PASSWORD_SET_TYPE,
                        ConstantsPasswordSetType.PASSWORD_EXPIRED);
            }
        }

        return true;
    }

    public UserInfo loadUserInfo(String username, String password) {
        List<UserInfo> listUserInfo = jdbcTemplate.query(DEFAULT_USERINFO_SELECT_STATEMENT, new UserInfoRowMapper(),
                username);
        UserInfo userInfo = null;
        if (listUserInfo != null && listUserInfo.size() > 0) {
            userInfo = listUserInfo.get(0);
        }
        _logger.debug("load UserInfo : " + userInfo);
        return userInfo;
    }

    public abstract boolean passwordMatches(UserInfo userInfo, String password);

    public static boolean isAuthenticated() {
        if (WebContext.getUserInfo() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 閿佸畾鐢ㄦ埛锛歩slock锛�1 鐢ㄦ埛瑙ｉ攣 2 鐢ㄦ埛閿佸畾
     * 
     * @param userInfo
     */
    public void lockUser(UserInfo userInfo) {
        try {
            if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
                jdbcTemplate.update(LOCK_USER_UPDATE_STATEMENT,
                        new Object[] { ConstantsStatus.LOCK, new Date(), userInfo.getId() },
                        new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 閿佸畾鐢ㄦ埛锛歩slock锛�1 鐢ㄦ埛瑙ｉ攣 2 鐢ㄦ埛閿佸畾
     * 
     * @param userInfo
     */
    public void unlockUser(UserInfo userInfo) {
        try {
            if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
                jdbcTemplate.update(UNLOCK_USER_UPDATE_STATEMENT,
                        new Object[] { ConstantsStatus.ACTIVE, new Date(), userInfo.getId() },
                        new int[] { Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 閲嶇疆閿欒瀵嗙爜娆℃暟鍜岃В閿佺敤鎴�
     * 
     * @param userInfo
     */
    public void resetBadPasswordCountAndLockout(UserInfo userInfo) {
        try {
            if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
                jdbcTemplate.update(BADPASSWORDCOUNT_RESET_UPDATE_STATEMENT,
                        new Object[] { 0, ConstantsStatus.ACTIVE, new Date(), userInfo.getId() },
                        new int[] { Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR });
            }
        } catch (Exception e) {
            e.printStackTrace();
            _logger.error(e.getMessage());
        }
    }

    /**
     * 鏇存柊閿欒瀵嗙爜娆℃暟
     * 
     * @param userInfo
     */
    public void setBadPasswordCount(UserInfo userInfo) {
        try {
            if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
                int badPasswordCount = userInfo.getBadPasswordCount() + 1;
                userInfo.setBadPasswordCount(badPasswordCount);
                jdbcTemplate.update(BADPASSWORDCOUNT_UPDATE_STATEMENT,
                        new Object[] { badPasswordCount, new Date(), userInfo.getId() },
                        new int[] { Types.INTEGER, Types.TIMESTAMP, Types.VARCHAR });
                insertLoginHistory(userInfo, ConstantsLoginType.LOCAL, "", "xe00000004", "password error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            _logger.error(e.getMessage());
        }
    }

    public List<Groups> queryGroups(UserInfo userInfo) {
        List<Groups> listGroups = jdbcTemplate.query(GROUPS_SELECT_STATEMENT, new RowMapper<Groups>() {
            public Groups mapRow(ResultSet rs, int rowNum) throws SQLException {
                Groups group = new Groups(rs.getString("ID"), rs.getString("NAME"), 0);

                return group;
            }
        }, userInfo.getId());

        _logger.debug("list Groups  " + listGroups);
        return listGroups;
    }

    /**
     * grant Authority by userinfo
     * 
     * @param userInfo
     * @return ArrayList<GrantedAuthority>
     */
    public ArrayList<GrantedAuthority> grantAuthority(UserInfo userInfo) {
        // query roles for user
        List<Groups> listGroups = queryGroups(userInfo);

        // set role for spring security
        ArrayList<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
        grantedAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
        for (Groups group : listGroups) {
            grantedAuthority.add(new SimpleGrantedAuthority(group.getId()));
        }
        _logger.debug("Authority : " + grantedAuthority);

        return grantedAuthority;
    }

    /**
     * login log write to log db
     * 
     * @param uid
     * @param j_username
     * @param type
     * @param code
     * @param message
     */
    public boolean insertLoginHistory(UserInfo userInfo, String type, String provider, String code, String message) {
        Date loginDate = new Date();
        String sessionId = WebContext.genId();
        WebContext.setAttribute(WebConstants.CURRENT_USER_SESSION_ID, sessionId);
        String ipAddress = WebContext.getRequestIpAddress();
        String platform = "";
        String browser = "";
        String userAgent = WebContext.getRequest().getHeader("User-Agent");
        String[] arrayUserAgent = null;
        if (userAgent.indexOf("MSIE") > 0) {
            arrayUserAgent = userAgent.split(";");
            browser = arrayUserAgent[1].trim();
            platform = arrayUserAgent[2].trim();
        } else if (userAgent.indexOf("Trident") > 0) {
            arrayUserAgent = userAgent.split(";");
            browser = "MSIE/" + arrayUserAgent[3].split("\\)")[0];
            ;
            platform = arrayUserAgent[0].split("\\(")[1];
        } else if (userAgent.indexOf("Chrome") > 0) {
            arrayUserAgent = userAgent.split(" ");
            // browser=arrayUserAgent[8].trim();
            for (int i = 0; i < arrayUserAgent.length; i++) {
                if (arrayUserAgent[i].contains("Chrome")) {
                    browser = arrayUserAgent[i].trim();
                    browser = browser.substring(0, browser.indexOf('.'));
                }
            }
            platform = (arrayUserAgent[1].substring(1) + " " + arrayUserAgent[2] + " "
                    + arrayUserAgent[3].substring(0, arrayUserAgent[3].length() - 1)).trim();
        } else if (userAgent.indexOf("Firefox") > 0) {
            arrayUserAgent = userAgent.split(" ");
            for (int i = 0; i < arrayUserAgent.length; i++) {
                if (arrayUserAgent[i].contains("Firefox")) {
                    browser = arrayUserAgent[i].trim();
                    browser = browser.substring(0, browser.indexOf('.'));
                }
            }
            platform = (arrayUserAgent[1].substring(1) + " " + arrayUserAgent[2] + " "
                    + arrayUserAgent[3].substring(0, arrayUserAgent[3].length() - 1)).trim();

        }

        jdbcTemplate.update(HISTORY_LOGIN_INSERT_STATEMENT,
                new Object[] { WebContext.genId(), sessionId, userInfo.getId(), userInfo.getUsername(),
                        userInfo.getDisplayName(), type, message, code, provider, ipAddress, browser, platform,
                        "Browser", loginDate },
                new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.TIMESTAMP });

        userInfo.setLastLoginTime(DateUtils.formatDateTime(loginDate));

        jdbcTemplate.update(LOGIN_USERINFO_UPDATE_STATEMENT,
                new Object[] { loginDate, ipAddress, userInfo.getLoginCount() + 1, userInfo.getId() },
                new int[] { Types.TIMESTAMP, Types.VARCHAR, Types.INTEGER, Types.VARCHAR });

        return true;
    }

    public boolean logout(HttpServletResponse response) {
        if (isAuthenticated()) {
            Object sessionIdAttribute = WebContext.getAttribute(WebConstants.CURRENT_USER_SESSION_ID);
            UserInfo userInfo = WebContext.getUserInfo();
            Date logoutDateTime = new Date();
            if (sessionIdAttribute != null) {
                remeberMeService.removeRemeberMe(response);

                jdbcTemplate.update(HISTORY_LOGOUT_UPDATE_STATEMENT,
                        new Object[] { logoutDateTime, sessionIdAttribute.toString() },
                        new int[] { Types.TIMESTAMP, Types.VARCHAR });
            }

            jdbcTemplate.update(LOGOUT_USERINFO_UPDATE_STATEMENT, new Object[] { logoutDateTime, userInfo.getId() },
                    new int[] { Types.TIMESTAMP, Types.VARCHAR });

            _logger.debug("Session " + WebContext.getAttribute(WebConstants.CURRENT_USER_SESSION_ID) + ", user "
                    + userInfo.getUsername() + " Logout, datetime " + DateUtils.toUtc(logoutDateTime) + " .");
        }
        return true;

    }
}
