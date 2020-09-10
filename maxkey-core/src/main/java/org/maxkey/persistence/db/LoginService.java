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
 

package org.maxkey.persistence.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.maxkey.constants.ConstantsStatus;
import org.maxkey.domain.Groups;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class LoginService {
    private static Logger _logger = LoggerFactory.getLogger(LoginService.class);

    private static final String LOCK_USER_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET ISLOCKED = ?  , UNLOCKTIME = ? WHERE ID = ?";

    private static final String UNLOCK_USER_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET ISLOCKED = ? , UNLOCKTIME = ? WHERE ID = ?";

    private static final String BADPASSWORDCOUNT_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET BADPASSWORDCOUNT = ? , BADPASSWORDTIME = ?  WHERE ID = ?";

    private static final String BADPASSWORDCOUNT_RESET_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET BADPASSWORDCOUNT = ? , ISLOCKED = ? ,UNLOCKTIME = ?  WHERE ID = ?";

    private static final String LOGIN_USERINFO_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET LASTLOGINTIME = ?  , LASTLOGINIP = ? , LOGINCOUNT = ?, ONLINE = "
            + UserInfo.ONLINE.ONLINE + "  WHERE ID = ?";

    private static final String LOGOUT_USERINFO_UPDATE_STATEMENT = "UPDATE MXK_USERINFO SET LASTLOGOFFTIME = ? , ONLINE = "
            + UserInfo.ONLINE.OFFLINE + "  WHERE ID = ?";

    private static final String GROUPS_SELECT_STATEMENT = "SELECT DISTINCT G.ID,G.NAME FROM MXK_USERINFO U,`MXK_GROUPS` G,MXK_GROUP_MEMBER GM WHERE U.ID = ?  AND U.ID=GM.MEMBERID AND GM.GROUPID=G.ID ";

    private static final String DEFAULT_USERINFO_SELECT_STATEMENT = "SELECT * FROM  MXK_USERINFO WHERE USERNAME = ?";
    
    protected JdbcTemplate jdbcTemplate;
    
    public LoginService(){
        
    }
    
    public LoginService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
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
    * reset BadPasswordCount And Lockout
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
     * if login password is error ,BadPasswordCount++ and set bad date
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
    
    
    public void setLastLoginInfo(UserInfo userInfo) {
        jdbcTemplate.update(LOGIN_USERINFO_UPDATE_STATEMENT,
                new Object[] { userInfo.getLastLoginTime(), userInfo.getLastLoginIp(), userInfo.getLoginCount() + 1, userInfo.getId() },
                new int[] { Types.TIMESTAMP, Types.VARCHAR, Types.INTEGER, Types.VARCHAR });
    }
    
    
    public void setLastLogoffInfo(UserInfo userInfo) {
        jdbcTemplate.update(LOGOUT_USERINFO_UPDATE_STATEMENT, new Object[] { userInfo.getLastLogoffTime(), userInfo.getId() },
                new int[] { Types.TIMESTAMP, Types.VARCHAR });
   
    }
    
    public class UserInfoRowMapper implements RowMapper<UserInfo> {

        @Override
        public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

            UserInfo userInfo = new UserInfo();
            userInfo.setId(rs.getString("ID"));
            userInfo.setUsername(rs.getString("USERNAME"));
            userInfo.setPassword(rs.getString("PASSWORD"));
            userInfo.setSharedSecret(rs.getString("SHAREDSECRET"));
            userInfo.setSharedCounter(rs.getString("SHAREDCOUNTER"));
            userInfo.setDecipherable(rs.getString("DECIPHERABLE"));
            userInfo.setWindowsAccount(rs.getString("WINDOWSACCOUNT"));
            userInfo.setUserType(rs.getString("USERTYPE"));

            userInfo.setDisplayName(rs.getString("DISPLAYNAME"));
            userInfo.setNickName(rs.getString("NICKNAME"));
            userInfo.setNameZhSpell(rs.getString("NAMEZHSPELL"));// nameZHSpell
            userInfo.setNameZhShortSpell(rs.getString("NAMEZHSHORTSPELL"));// nameZHSpell
            userInfo.setGivenName(rs.getString("GIVENNAME"));
            userInfo.setMiddleName(rs.getString("MIDDLENAME"));
            userInfo.setFamilyName(rs.getString("FAMILYNAME"));
            userInfo.setHonorificPrefix(rs.getString("HONORIFICPREFIX"));
            userInfo.setHonorificSuffix(rs.getString("HONORIFICSUFFIX"));
            userInfo.setFormattedName(rs.getString("FORMATTEDNAME"));

            userInfo.setGender(rs.getInt("GENDER"));
            userInfo.setBirthDate(rs.getString("BIRTHDATE"));
            userInfo.setPicture(rs.getBytes("PICTURE"));
            userInfo.setMarried(rs.getInt("MARRIED"));
            userInfo.setIdType(rs.getInt("IDTYPE"));
            userInfo.setIdCardNo(rs.getString("IDCARDNO"));
            userInfo.setWebSite(rs.getString("WEBSITE"));

            userInfo.setAuthnType(rs.getInt("AUTHNTYPE"));
            userInfo.setMobile(rs.getString("MOBILE"));
            userInfo.setMobileVerified(rs.getInt("MOBILEVERIFIED"));
            userInfo.setEmail(rs.getString("EMAIL"));
            userInfo.setEmailVerified(rs.getInt("EMAILVERIFIED"));
            userInfo.setPasswordQuestion(rs.getString("PASSWORDQUESTION"));
            userInfo.setPasswordAnswer(rs.getString("PASSWORDANSWER"));

            userInfo.setAppLoginAuthnType(rs.getInt("APPLOGINAUTHNTYPE"));
            userInfo.setAppLoginPassword(rs.getString("APPLOGINPASSWORD"));
            userInfo.setProtectedApps(rs.getString("PROTECTEDAPPS"));

            userInfo.setPasswordLastSetTime(rs.getString("PASSWORDLASTSETTIME"));
            userInfo.setPasswordSetType(rs.getInt("PASSWORDSETTYPE"));
            userInfo.setBadPasswordCount(rs.getInt("BADPASSWORDCOUNT"));
            userInfo.setBadPasswordTime(rs.getString("BADPASSWORDTIME"));
            userInfo.setUnLockTime(rs.getString("UNLOCKTIME"));
            userInfo.setIsLocked(rs.getInt("ISLOCKED"));
            userInfo.setLastLoginTime(rs.getString("LASTLOGINTIME"));
            userInfo.setLastLoginIp(rs.getString("LASTLOGINIP"));
            userInfo.setLastLogoffTime(rs.getString("LASTLOGOFFTIME"));
            userInfo.setLoginCount(rs.getInt("LOGINCOUNT"));

            userInfo.setTimeZone(rs.getString("TIMEZONE"));
            userInfo.setLocale(rs.getString("LOCALE"));
            userInfo.setPreferredLanguage(rs.getString("PREFERREDLANGUAGE"));

            userInfo.setWorkEmail(rs.getString("WORKEMAIL"));
            userInfo.setWorkPhoneNumber(rs.getString("WORKPHONENUMBER"));
            userInfo.setWorkCountry(rs.getString("WORKCOUNTRY"));
            userInfo.setWorkRegion(rs.getString("WORKREGION"));
            userInfo.setWorkLocality(rs.getString("WORKLOCALITY"));
            userInfo.setWorkStreetAddress(rs.getString("WORKSTREETADDRESS"));
            userInfo.setWorkAddressFormatted(rs.getString("WORKADDRESSFORMATTED"));
            userInfo.setWorkPostalCode(rs.getString("WORKPOSTALCODE"));
            userInfo.setWorkFax(rs.getString("WORKFAX"));

            userInfo.setHomeEmail(rs.getString("HOMEEMAIL"));
            userInfo.setHomePhoneNumber(rs.getString("HOMEPHONENUMBER"));
            userInfo.setHomeCountry(rs.getString("HOMECOUNTRY"));
            userInfo.setHomeRegion(rs.getString("HOMEREGION"));
            userInfo.setHomeLocality(rs.getString("HOMELOCALITY"));
            userInfo.setHomeStreetAddress(rs.getString("HOMESTREETADDRESS"));
            userInfo.setHomeAddressFormatted(rs.getString("HOMEADDRESSFORMATTED"));
            userInfo.setHomePostalCode(rs.getString("HOMEPOSTALCODE"));
            userInfo.setHomeFax(rs.getString("HOMEFAX"));

            userInfo.setEmployeeNumber(rs.getString("EMPLOYEENUMBER"));
            userInfo.setDivision(rs.getString("DIVISION"));
            userInfo.setCostCenter(rs.getString("COSTCENTER"));
            userInfo.setOrganization(rs.getString("ORGANIZATION"));
            userInfo.setDepartmentId(rs.getString("DEPARTMENTID"));
            userInfo.setDepartment(rs.getString("DEPARTMENT"));
            userInfo.setJobTitle(rs.getString("JOBTITLE"));
            userInfo.setJobLevel(rs.getString("JOBLEVEL"));
            userInfo.setManagerId(rs.getString("MANAGERID"));
            userInfo.setManager(rs.getString("MANAGER"));
            userInfo.setAssistantId(rs.getString("ASSISTANTID"));
            userInfo.setAssistant(rs.getString("ASSISTANT"));
            userInfo.setEntryDate(rs.getString("ENTRYDATE"));//
            userInfo.setQuitDate(rs.getString("QUITDATE"));
            userInfo.setStartWorkDate(rs.getString("STARTWORKDATE"));// STARTWORKDATE

            userInfo.setExtraAttribute(rs.getString("EXTRAATTRIBUTE"));

            userInfo.setCreatedBy(rs.getString("CREATEDBY"));
            userInfo.setCreatedDate(rs.getString("CREATEDDATE"));
            userInfo.setModifiedBy(rs.getString("MODIFIEDBY"));
            userInfo.setModifiedDate(rs.getString("MODIFIEDDATE"));

            userInfo.setStatus(rs.getInt("STATUS"));
            userInfo.setGridList(rs.getInt("GRIDLIST"));
            userInfo.setDescription(rs.getString("DESCRIPTION"));
            userInfo.setTheme(rs.getString("THEME"));
            if (userInfo.getTheme() == null || userInfo.getTheme().equalsIgnoreCase("")) {
                userInfo.setTheme("default");
            }
            
            return userInfo;
        }
    }
}


