/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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


package org.dromara.maxkey.persistence.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.maxkey.constants.ConstsPasswordSetType;
import org.dromara.maxkey.constants.ConstsRoles;
import org.dromara.maxkey.constants.ConstsStatus;
import org.dromara.maxkey.entity.cnf.CnfPasswordPolicy;
import org.dromara.maxkey.entity.idm.Groups;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.CnfPasswordPolicyService;
import org.dromara.maxkey.persistence.service.LoginService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.web.WebConstants;
import org.dromara.maxkey.web.WebContext;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

@Repository
public class LoginServiceImpl  implements LoginService{
    private static final Logger _logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private static final String LOGIN_USERINFO_UPDATE_STATEMENT = "update mxk_userinfo set badpasswordcount = 0 , logincount = logincount + 1 , lastlogintime = ?  , lastloginip = ? , online = "
            + UserInfo.ONLINE.ONLINE + "  where id = ?";

    private static final String GROUPS_SELECT_STATEMENT = "select distinct g.id,g.groupcode,g.groupname from mxk_userinfo u,mxk_groups g,mxk_group_member gm where u.id = ?  and u.id=gm.memberid and gm.groupid=g.id ";

    private static final String DEFAULT_USERINFO_SELECT_STATEMENT = "select * from  mxk_userinfo where username = ? ";

    private static final String DEFAULT_USERINFO_SELECT_STATEMENT_USERNAME_MOBILE = "select * from  mxk_userinfo where (username = ? or mobile = ?)";

    private static final String DEFAULT_USERINFO_SELECT_STATEMENT_USERNAME_MOBILE_EMAIL = "select * from  mxk_userinfo where (username = ? or mobile = ? or email = ?) ";

    private static final String DEFAULT_MYAPPS_SELECT_STATEMENT = "select distinct app.id,app.appname from mxk_apps app,mxk_access gp,mxk_groups g  where app.id=gp.appid and app.status = 1 and gp.groupid=g.id and g.id in(%s)";

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    UserInfoService userInfoService;
    
    @Autowired
    CnfPasswordPolicyService cnfPasswordPolicyService;

    /**
     * 1 (USERNAME)  2 (USERNAME | MOBILE) 3 (USERNAME | MOBILE | EMAIL)
     */
    public  static  int LOGIN_ATTRIBUTE_TYPE = 2;

    public LoginServiceImpl(){

    }

    public UserInfo find(String username, String password) {
        List<UserInfo> listUserInfo = null ;
        if( LOGIN_ATTRIBUTE_TYPE == 1) {
        	listUserInfo = findByUsername(username,password);
        }else if( LOGIN_ATTRIBUTE_TYPE == 2) {
        	 listUserInfo = findByUsernameOrMobile(username,password);
        }else if( LOGIN_ATTRIBUTE_TYPE == 3) {
        	 listUserInfo = findByUsernameOrMobileOrEmail(username,password);
        }
        _logger.debug("load UserInfo : {}" , listUserInfo);
        return (CollectionUtils.isNotEmpty(listUserInfo) ? listUserInfo.get(0) : null);
    }

    public List<UserInfo> findByUsername(String username, String password) {
    	return jdbcTemplate.query(
    			DEFAULT_USERINFO_SELECT_STATEMENT,
    			new UserInfoRowMapper(),
    			username
    		);
    }

    public List<UserInfo> findByUsernameOrMobile(String username, String password) {
    	return jdbcTemplate.query(
			 	DEFAULT_USERINFO_SELECT_STATEMENT_USERNAME_MOBILE,
    			new UserInfoRowMapper(),
    			username,username
    		);
    }

    public List<UserInfo> findByUsernameOrMobileOrEmail(String username, String password) {
    	return jdbcTemplate.query(
			 	DEFAULT_USERINFO_SELECT_STATEMENT_USERNAME_MOBILE_EMAIL,
    			new UserInfoRowMapper(),
    			username,username,username
    		);
    }


    
    /**
     * dynamic passwordPolicy Valid for user login.
     * @param userInfo
     * @return boolean
     */
    public boolean passwordPolicyValid(UserInfo userInfo) {
        
 	   CnfPasswordPolicy passwordPolicy = cnfPasswordPolicyService.getPasswordPolicy();
 	   
        DateTime currentdateTime = new DateTime();
         /*
          * check login attempts fail times
          */
         if (userInfo.getBadPasswordCount() >= passwordPolicy.getAttempts() && userInfo.getBadPasswordTime() != null) {
             _logger.debug("login Attempts is {} , bad Password Time {}" , userInfo.getBadPasswordCount(),userInfo.getBadPasswordTime());
            
             Duration duration = new Duration(new DateTime(userInfo.getBadPasswordTime()), currentdateTime);
             int intDuration = Integer.parseInt(duration.getStandardMinutes() + "");
             _logger.debug("bad Password duration {} , " + 
                           "password policy Duration {} , "+
                           "validate result {}" ,
                           intDuration,
                           passwordPolicy.getDuration(), 
                           (intDuration > passwordPolicy.getDuration())
                     );
             //auto unlock attempts when intDuration >= set Duration
             if(intDuration >= passwordPolicy.getDuration()) {
                 _logger.debug("resetAttempts ...");
                 resetAttempts(userInfo);
             }else {
                 lockUser(userInfo);
                 throw new BadCredentialsException(
                         WebContext.getI18nValue("login.error.attempts",
                                 new Object[]{userInfo.getBadPasswordCount(),passwordPolicy.getDuration()}) 
                         );
             }
         }
         
         //locked
         if(userInfo.getIsLocked()==ConstsStatus.LOCK) {
             throw new BadCredentialsException(
                                 userInfo.getUsername()+ " "+
                                 WebContext.getI18nValue("login.error.locked")
                                 );
         }
         // inactive
         if(userInfo.getStatus()!=ConstsStatus.ACTIVE) {
             throw new BadCredentialsException(
                                 userInfo.getUsername()+ 
                                 WebContext.getI18nValue("login.error.inactive") 
                                 );
         }

         return true;
     }
    
    public void applyPasswordPolicy(UserInfo userInfo) {
 	   CnfPasswordPolicy passwordPolicy = cnfPasswordPolicyService.getPasswordPolicy();
 	   
        DateTime currentdateTime = new DateTime();
        //initial password need change
        if(userInfo.getLoginCount()<=0) {
            WebContext.getSession().setAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE,
                    ConstsPasswordSetType.INITIAL_PASSWORD);
        }
        
        if (userInfo.getPasswordSetType() != ConstsPasswordSetType.PASSWORD_NORMAL) {
            WebContext.getSession().setAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE,
                        userInfo.getPasswordSetType());
            return;
        } else {
            WebContext.getSession().setAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE,
                    ConstsPasswordSetType.PASSWORD_NORMAL);
        }

        /*
         * check password is Expired,Expiration is Expired date ,if Expiration equals 0,not need check
         *
         */
        if (passwordPolicy.getExpiration() > 0 && userInfo.getPasswordLastSetTime() != null) {
            _logger.info("last password set date {}" , userInfo.getPasswordLastSetTime());
            Duration duration = new Duration(new DateTime(userInfo.getPasswordLastSetTime()), currentdateTime);
            int intDuration = Integer.parseInt(duration.getStandardDays() + "");
            _logger.debug("password Last Set duration day {} , " +
                          "password policy Expiration {} , " +
                          "validate result {}", 
                     intDuration,
                     passwordPolicy.getExpiration(),
                     intDuration <= passwordPolicy.getExpiration()
                 );
            if (intDuration > passwordPolicy.getExpiration()) {
                WebContext.getSession().setAttribute(WebConstants.CURRENT_USER_PASSWORD_SET_TYPE,
                        ConstsPasswordSetType.PASSWORD_EXPIRED);
            }
        }
        
        resetBadPasswordCount(userInfo);
    }
    
    /**
     * lockUser
     * 
     * @param userInfo
     */
    public void lockUser(UserInfo userInfo) {
        try {
            if (userInfo != null 
         		   && StringUtils.isNotEmpty(userInfo.getId()) 
         		   && userInfo.getIsLocked() == ConstsStatus.ACTIVE) {
                userInfo.setIsLocked(ConstsStatus.LOCK);
                userInfoService.locked(userInfo);
            }
        } catch (Exception e) {
            _logger.error("lockUser Exception",e);
        }
    }
    

    /**
     * unlockUser
     * 
     * @param userInfo
     */
    public void unlockUser(UserInfo userInfo) {
        try {
            if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
                userInfo.setIsLocked(ConstsStatus.ACTIVE);
                userInfoService.lockout(userInfo);
            }
        } catch (Exception e) {
            _logger.error("unlockUser Exception",e);
        }
    }

    /**
    * reset BadPasswordCount And Lockout
     * 
     * @param userInfo
     */
    public void resetAttempts(UserInfo userInfo) {
        try {
            if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
                userInfo.setIsLocked(ConstsStatus.ACTIVE);
                userInfo.setBadPasswordCount(0);
                userInfoService.badPasswordCountReset(userInfo);
            }
        } catch (Exception e) {
            _logger.error("resetAttempts Exception",e);
        }
    }

    /**
     * if login password is error ,BadPasswordCount++ and set bad date
     * 
     * @param userInfo
     */
    private void setBadPasswordCount(String userId,int badPasswordCount) {
        try {
     	   UserInfo user = new UserInfo();
     	   user.setId(userId);
     	   user.setBadPasswordCount(badPasswordCount);
     	   userInfoService.badPasswordCount(user);
        } catch (Exception e) {
            _logger.error("setBadPasswordCount Exception",e);
        }
    }
    
    public void plusBadPasswordCount(UserInfo userInfo) {
        if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
            setBadPasswordCount(userInfo.getId(),userInfo.getBadPasswordCount());
            CnfPasswordPolicy passwordPolicy = cnfPasswordPolicyService.getPasswordPolicy();
            if(userInfo.getBadPasswordCount() >= passwordPolicy.getAttempts()) {
         	   _logger.debug("Bad Password Count {} , Max Attempts {}",
         			   userInfo.getBadPasswordCount() + 1,passwordPolicy.getAttempts());
         	   this.lockUser(userInfo);
            }
        }
    }
    
    public void resetBadPasswordCount(UserInfo userInfo) {
        if (userInfo != null && StringUtils.isNotEmpty(userInfo.getId()) && userInfo.getBadPasswordCount()>0) {
     	   setBadPasswordCount(userInfo.getId(),0);
        }
    }

    public List<GrantedAuthority> queryAuthorizedApps(List<GrantedAuthority> grantedAuthoritys) {
        String grantedAuthorityString="'ROLE_ALL_USER'";
        for(GrantedAuthority grantedAuthority : grantedAuthoritys) {
            grantedAuthorityString += ",'"+ grantedAuthority.getAuthority()+"'";
        }

        ArrayList<GrantedAuthority> listAuthorizedApps = (ArrayList<GrantedAuthority>) jdbcTemplate.query(
                String.format(DEFAULT_MYAPPS_SELECT_STATEMENT, grantedAuthorityString),
                new RowMapper<GrantedAuthority>() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new SimpleGrantedAuthority(rs.getString("id"));
            }
        });

        _logger.debug("list Authorized Apps  {}" , listAuthorizedApps);
        return listAuthorizedApps;
    }

    public List<Groups> queryGroups(UserInfo userInfo) {
        List<Groups> listRoles = jdbcTemplate.query(GROUPS_SELECT_STATEMENT, new RowMapper<Groups>() {
            public Groups mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Groups(rs.getString("id"), rs.getString("groupcode"),rs.getString("groupname"), 0);
            }
        }, userInfo.getId());

        _logger.debug("list Roles  {}" , listRoles);
        return listRoles;
    }

    /**
     * grant Authority by userinfo
     *
     * @param userInfo
     * @return ArrayList<GrantedAuthority>
     */
    public List<GrantedAuthority> grantAuthority(UserInfo userInfo) {
        // query Groups for user
        List<Groups> listGroups = queryGroups(userInfo);

        //set default groups
        ArrayList<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(ConstsRoles.ROLE_USER);
        grantedAuthority.add(ConstsRoles.ROLE_ALL_USER);
        grantedAuthority.add(ConstsRoles.ROLE_ORDINARY_USER);
        for (Groups group : listGroups) {
            grantedAuthority.add(new SimpleGrantedAuthority(group.getId()));
            if(group.getGroupCode().startsWith("ROLE_")
            		&& !grantedAuthority.contains(new SimpleGrantedAuthority(group.getGroupCode()))) {
            	grantedAuthority.add(new SimpleGrantedAuthority(group.getGroupCode()));
            }
        }
        _logger.debug("Authority : {}" , grantedAuthority);

        return grantedAuthority;
    }


    public void updateLastLogin(UserInfo userInfo) {
        jdbcTemplate.update(LOGIN_USERINFO_UPDATE_STATEMENT,
                new Object[] {
                				userInfo.getLastLoginTime(),
                				userInfo.getLastLoginIp(),
                				userInfo.getId()
                			},
                new int[] { Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR });
    }

    public class UserInfoRowMapper implements RowMapper<UserInfo> {
        @Override
        public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

            UserInfo userInfo = new UserInfo();
            userInfo.setId(rs.getString("id"));
            userInfo.setUsername(rs.getString("username"));
            userInfo.setPassword(rs.getString("password"));
            userInfo.setSharedSecret(rs.getString("sharedsecret"));
            userInfo.setSharedCounter(rs.getString("sharedcounter"));
            userInfo.setDecipherable(rs.getString("decipherable"));
            userInfo.setWindowsAccount(rs.getString("windowsaccount"));
            userInfo.setUserType(rs.getString("usertype"));

            userInfo.setDisplayName(rs.getString("displayname"));
            userInfo.setNickName(rs.getString("nickname"));
            userInfo.setNameZhSpell(rs.getString("namezhspell"));// nameZHSpell
            userInfo.setNameZhShortSpell(rs.getString("namezhshortspell"));// nameZHSpell
            userInfo.setGivenName(rs.getString("givenname"));
            userInfo.setMiddleName(rs.getString("middlename"));
            userInfo.setFamilyName(rs.getString("familyname"));
            userInfo.setHonorificPrefix(rs.getString("honorificprefix"));
            userInfo.setHonorificSuffix(rs.getString("honorificsuffix"));
            userInfo.setFormattedName(rs.getString("formattedname"));

            userInfo.setGender(rs.getInt("gender"));
            userInfo.setBirthDate(rs.getString("birthdate"));
            userInfo.setPicture(rs.getBytes("picture"));
            userInfo.setMarried(rs.getInt("married"));
            userInfo.setIdType(rs.getInt("idtype"));
            userInfo.setIdCardNo(rs.getString("idcardno"));
            userInfo.setWebSite(rs.getString("website"));

            userInfo.setAuthnType(rs.getInt("authntype"));
            userInfo.setMobile(rs.getString("mobile"));
            userInfo.setMobileVerified(rs.getInt("mobileverified"));
            userInfo.setEmail(rs.getString("email"));
            userInfo.setEmailVerified(rs.getInt("emailverified"));
            userInfo.setPasswordQuestion(rs.getString("passwordquestion"));
            userInfo.setPasswordAnswer(rs.getString("passwordanswer"));

            userInfo.setAppLoginAuthnType(rs.getInt("apploginauthntype"));
            userInfo.setAppLoginPassword(rs.getString("apploginpassword"));
            userInfo.setProtectedApps(rs.getString("protectedapps"));

            userInfo.setPasswordLastSetTime(rs.getTimestamp("passwordlastsettime"));
            userInfo.setPasswordSetType(rs.getInt("passwordsettype"));
            userInfo.setBadPasswordCount(rs.getInt("badpasswordcount"));
            userInfo.setBadPasswordTime(rs.getTimestamp("badpasswordtime"));
            userInfo.setUnLockTime(rs.getTimestamp("unlocktime"));
            userInfo.setIsLocked(rs.getInt("islocked"));
            userInfo.setLastLoginTime(rs.getTimestamp("lastlogintime"));
            userInfo.setLastLoginIp(rs.getString("lastloginip"));
            userInfo.setLastLogoffTime(rs.getTimestamp("lastlogofftime"));
            userInfo.setLoginCount(rs.getInt("logincount"));
            userInfo.setRegionHistory(rs.getString("regionhistory"));
            userInfo.setPasswordHistory(rs.getString("passwordhistory"));

            userInfo.setTimeZone(rs.getString("timezone"));
            userInfo.setLocale(rs.getString("locale"));
            userInfo.setPreferredLanguage(rs.getString("preferredlanguage"));

            userInfo.setWorkEmail(rs.getString("workemail"));
            userInfo.setWorkPhoneNumber(rs.getString("workphonenumber"));
            userInfo.setWorkCountry(rs.getString("workcountry"));
            userInfo.setWorkRegion(rs.getString("workregion"));
            userInfo.setWorkLocality(rs.getString("worklocality"));
            userInfo.setWorkStreetAddress(rs.getString("workstreetaddress"));
            userInfo.setWorkAddressFormatted(rs.getString("workaddressformatted"));
            userInfo.setWorkPostalCode(rs.getString("workpostalcode"));
            userInfo.setWorkFax(rs.getString("workfax"));

            userInfo.setHomeEmail(rs.getString("homeemail"));
            userInfo.setHomePhoneNumber(rs.getString("homephonenumber"));
            userInfo.setHomeCountry(rs.getString("homecountry"));
            userInfo.setHomeRegion(rs.getString("homeregion"));
            userInfo.setHomeLocality(rs.getString("homelocality"));
            userInfo.setHomeStreetAddress(rs.getString("homestreetaddress"));
            userInfo.setHomeAddressFormatted(rs.getString("homeaddressformatted"));
            userInfo.setHomePostalCode(rs.getString("homepostalcode"));
            userInfo.setHomeFax(rs.getString("homefax"));

            userInfo.setEmployeeNumber(rs.getString("employeenumber"));
            userInfo.setDivision(rs.getString("division"));
            userInfo.setCostCenter(rs.getString("costcenter"));
            userInfo.setOrganization(rs.getString("organization"));
            userInfo.setDepartmentId(rs.getString("departmentid"));
            userInfo.setDepartment(rs.getString("department"));
            userInfo.setJobTitle(rs.getString("jobtitle"));
            userInfo.setJobLevel(rs.getString("joblevel"));
            userInfo.setManagerId(rs.getString("managerid"));
            userInfo.setManager(rs.getString("manager"));
            userInfo.setAssistantId(rs.getString("assistantid"));
            userInfo.setAssistant(rs.getString("assistant"));
            userInfo.setEntryDate(rs.getString("entrydate"));//
            userInfo.setQuitDate(rs.getString("quitdate"));
            userInfo.setStartWorkDate(rs.getString("startworkdate"));// STARTWORKDATE

            userInfo.setExtraAttribute(rs.getString("extraattribute"));

            userInfo.setCreatedBy(rs.getString("createdby"));
            userInfo.setCreatedDate(rs.getTimestamp("createddate"));
            userInfo.setModifiedBy(rs.getString("modifiedby"));
            userInfo.setModifiedDate(rs.getTimestamp("modifieddate"));

            userInfo.setStatus(rs.getInt("status"));
            userInfo.setGridList(rs.getInt("gridlist"));
            userInfo.setDescription(rs.getString("description"));
            userInfo.setTheme(rs.getString("theme"));
            userInfo.setInstId(rs.getString("instid"));
            if (userInfo.getTheme() == null || userInfo.getTheme().equalsIgnoreCase("")) {
                userInfo.setTheme("default");
            }

            return userInfo;
        }
    }
}


