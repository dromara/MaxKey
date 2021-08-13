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
 

package org.maxkey.persistence.service;


import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.mybatis.jpa.persistence.JpaBaseService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.maxkey.constants.ConstantsStatus;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.ChangePassword;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.db.PasswordPolicyValidator;
import org.maxkey.persistence.kafka.KafkaIdentityAction;
import org.maxkey.persistence.kafka.KafkaIdentityTopic;
import org.maxkey.persistence.kafka.KafkaPersistService;
import org.maxkey.persistence.mapper.UserInfoMapper;
import org.maxkey.util.DateUtils;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;


/**
 * @author Crystal.Sea
 *
 */
@Repository
public class UserInfoService extends JpaBaseService<UserInfo> {
	final static Logger _logger = LoggerFactory.getLogger(UserInfoService.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	PasswordPolicyValidator passwordPolicyValidator;
	
	@Autowired
	KafkaPersistService kafkaPersistService;
	
	 @Autowired
	 protected JdbcTemplate jdbcTemplate;
	
	public UserInfoService() {
		super(UserInfoMapper.class);
	}

	/* (non-Javadoc)
	 * @see com.connsec.db.service.BaseService#getMapper()
	 */
	@Override
	public UserInfoMapper getMapper() {
		return (UserInfoMapper)super.getMapper();
	}
	
    public boolean insert(UserInfo userInfo) {
        userInfo = passwordEncoder(userInfo);
        if (super.insert(userInfo)) {
            if(kafkaPersistService.getApplicationConfig().isKafkaSupport()) {
                UserInfo loadUserInfo = loadUserRelated(userInfo.getId());
                kafkaPersistService.send(
                        KafkaIdentityTopic.USERINFO_TOPIC, 
                        loadUserInfo,
                        KafkaIdentityAction.CREATE_ACTION);
            }
            
            return true;
        }

        return false;
    }
	
    public boolean update(UserInfo userInfo) {
        userInfo = passwordEncoder(userInfo);
        if (super.update(userInfo)) {
            if(kafkaPersistService.getApplicationConfig().isKafkaSupport()) {
                UserInfo loadUserInfo = loadUserRelated(userInfo.getId());
                kafkaPersistService.send(
                        KafkaIdentityTopic.USERINFO_TOPIC, 
                        loadUserInfo,
                        KafkaIdentityAction.UPDATE_ACTION);
            }
            changePasswordProvisioning(userInfo);
            return true;
        }
        return false;
    }
	
	public boolean delete(UserInfo userInfo) {
	    UserInfo loadUserInfo = null;
	    if(kafkaPersistService.getApplicationConfig().isKafkaSupport()) {
	        loadUserInfo = loadUserRelated(userInfo.getId());
	    }
	    
		if( super.delete(userInfo)){
			kafkaPersistService.send(
		            KafkaIdentityTopic.USERINFO_TOPIC, 
		            loadUserInfo, 
		            KafkaIdentityAction.DELETE_ACTION);
			 return true;
		}
		return false;
	}

	public UserInfo loadUserRelated(String userId) {
	    UserInfo loadUserInfo =this.get(userId);
	    loadUserInfo.setDepts(getMapper().loadDeptsByUserId(userId));
	    loadUserInfo.setAdjoints(getMapper().loadAdjointsByUserId(userId));
	    return loadUserInfo;
	}
	
	public boolean updateGridList(String gridList) {
	    try {
    	    if (gridList != null && !gridList.equals("")) {
                WebContext.getUserInfo().setGridList(Integer.parseInt(gridList));
                getMapper().updateGridList(WebContext.getUserInfo());
            }
	    }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
	    return true;
	}
	
	
	public boolean updateProtectedApps(UserInfo userinfo) {
		try {
			if(WebContext.getUserInfo() != null) {
				userinfo.setModifiedBy(WebContext.getUserInfo().getId());
			}
			userinfo.setModifiedDate(DateUtils.getCurrentDateTimeAsString());
			return getMapper().updateProtectedApps(userinfo) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public UserInfo loadByUsername(String username) {
		return getMapper().loadByUsername(username);
	}
	
	public UserInfo loadByAppIdAndUsername(String appId,String username){
		try {
			UserInfo userinfo = new UserInfo();
			userinfo.setUsername(username);
			return getMapper().loadByAppIdAndUsername(userinfo) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public void logisticDeleteAllByCid(String cid){
		try {
			 getMapper().logisticDeleteAllByCid(cid);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserInfo passwordEncoder(UserInfo userInfo) {
	    //密码不为空，则需要进行加密处理
	    if(userInfo.getPassword()!=null && !userInfo.getPassword().equals("")) {
    	    String password = passwordEncoder.encode(userInfo.getPassword());
            userInfo.setDecipherable(ReciprocalUtils.encode(PasswordReciprocal.getInstance().rawPassword(userInfo.getUsername(), userInfo.getPassword())));
            _logger.debug("decipherable : "+userInfo.getDecipherable());
            userInfo.setPassword(password);
            userInfo.setPasswordLastSetTime(DateUtils.getCurrentDateTimeAsString());
            
            userInfo.setModifiedDate(DateUtils.getCurrentDateTimeAsString());
	    }
        return userInfo;
	}
	
	
	public boolean changePassword(  String oldPassword,
                                    String newPassword,
                                    String confirmPassword,
                                    int passwordSetType) {
		try {
		    WebContext.setAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT, "");
	        UserInfo userInfo = WebContext.getUserInfo();
	        UserInfo changeUserInfo = new UserInfo();
	        changeUserInfo.setUsername(userInfo.getUsername());
	        changeUserInfo.setPassword(newPassword);
	        changeUserInfo.setId(userInfo.getId());
	        changeUserInfo.setDecipherable(userInfo.getDecipherable());
	        changeUserInfo.setPasswordSetType(passwordSetType);
	        
	        if(newPassword.equals(confirmPassword)){
	            if(oldPassword==null || 
	                    passwordEncoder.matches(oldPassword, userInfo.getPassword())){
	                if(changePassword(changeUserInfo,true) ){
	                    userInfo.setPassword(changeUserInfo.getPassword());
                        userInfo.setDecipherable(changeUserInfo.getDecipherable());
	                    return true;
	                }
	                return false;	               
	            }else {
	                if(oldPassword!=null &&
	                        passwordEncoder.matches(newPassword, userInfo.getPassword())) {
	                    WebContext.setAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT, 
	                            WebContext.getI18nValue("PasswordPolicy.OLD_PASSWORD_MATCH"));
	                }else {
	                    WebContext.setAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT, 
	                        WebContext.getI18nValue("PasswordPolicy.OLD_PASSWORD_NOT_MATCH"));
	                }
	            }
	        }else {
	            WebContext.setAttribute(PasswordPolicyValidator.PASSWORD_POLICY_VALIDATE_RESULT, 
	                    WebContext.getI18nValue("PasswordPolicy.CONFIRMPASSWORD_NOT_MATCH"));
	        }
		 } catch (Exception e) {
             e.printStackTrace();
         }    
		    
		return false;
	}
	
    public boolean changePassword(UserInfo changeUserInfo,boolean passwordPolicy) {
        try {
            _logger.debug("decipherable old : " + changeUserInfo.getDecipherable());
            _logger.debug("decipherable new : " + ReciprocalUtils.encode(PasswordReciprocal.getInstance()
                    .rawPassword(changeUserInfo.getUsername(), changeUserInfo.getPassword())));

            if (passwordPolicy && passwordPolicyValidator.validator(changeUserInfo) == false) {
                return false;
            }

            if (WebContext.getUserInfo() != null) {
                changeUserInfo.setModifiedBy(WebContext.getUserInfo().getId());
            }

            changeUserInfo = passwordEncoder(changeUserInfo);

            if (getMapper().changePassword(changeUserInfo) > 0) {
                changePasswordProvisioning(changeUserInfo);
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
	
	public String randomPassword() {
	    return passwordPolicyValidator.generateRandomPassword();
	}
	
	public void changePasswordProvisioning(UserInfo userInfo) {
	    if(userInfo.getPassword()!=null && !userInfo.getPassword().equals("")) {
    	    ChangePassword changePassword=new ChangePassword();
            changePassword.setId(userInfo.getId());
            changePassword.setUid(userInfo.getId());
            changePassword.setUsername(userInfo.getUsername());
            changePassword.setDecipherable(userInfo.getDecipherable());
            changePassword.setPassword(userInfo.getPassword());
            kafkaPersistService.send(
                    KafkaIdentityTopic.PASSWORD_TOPIC, 
                    changePassword, 
                    KafkaIdentityAction.PASSWORD_ACTION);
	    }
	}
	
	public boolean changeAppLoginPassword(UserInfo userinfo) {
		try {
			if(WebContext.getUserInfo() != null) {
				userinfo.setModifiedBy(WebContext.getUserInfo().getId());
			}
			userinfo.setModifiedDate(DateUtils.getCurrentDateTimeAsString());
			return getMapper().changeAppLoginPassword(userinfo) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 锁定用户：islock：1 用户解锁 2 用户锁定
	 * @param userInfo
	 */
	public void locked(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				userInfo.setIsLocked(ConstantsStatus.STOP);
				getMapper().locked(userInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户登录成功后，重置错误密码次数和解锁用户
	 * @param userInfo
	 */
	public void unlock(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				userInfo.setIsLocked(ConstantsStatus.START);
				userInfo.setBadPasswordCount(0);
				getMapper().unlock(userInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新错误密码次数
	 * @param userInfo
	 */
	public void updateBadPasswordCount(UserInfo userInfo) {
		try {
			if(userInfo != null && StringUtils.isNotEmpty(userInfo.getId())) {
				int updateBadPWDCount = userInfo.getBadPasswordCount() + 1;
				userInfo.setBadPasswordCount(updateBadPWDCount);
				getMapper().updateBadPWDCount(userInfo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    public boolean importing(MultipartFile file) {
        if(file ==null){
            return false;
        }
        InputStream is = null;
        Workbook wb = null;
        List<UserInfo> userInfoList = null;
        try {
            is = file.getInputStream();
            
            String xls = ".xls";
            String xlsx = ".xlsx";
            int columnSize = 46;
            userInfoList = Lists.newArrayList();
            if (file.getOriginalFilename().toLowerCase().endsWith(xls)) {
                wb = new HSSFWorkbook(is);
            } else if (file.getOriginalFilename().toLowerCase().endsWith(xlsx)) {
                wb = new XSSFWorkbook(is);
            } else {
                throw new RuntimeException("maxKey用户导入没有Excel类型");
            }

            int sheetSize = wb.getNumberOfSheets();
            //遍历sheet页
            for (int i = 0; i < sheetSize; i++) {
                Sheet sheet = wb.getSheetAt(i);

                int rowSize = sheet.getLastRowNum() + 1;
                //遍历行
                for (int j = 1; j < rowSize; j++) {
                    Row row = sheet.getRow(j);
                    //略过空行和前3行
                    if (row == null || j <3 ) {
                        continue;
                    } else {
                        //其他行是数据行
                        UserInfo userInfo = new UserInfo();
                        userInfo.setCreatedDate(DateUtils.formatDateTime(new Date()));

                        for (int k = 0; k < columnSize; k++) {
                            if (k == 0) {
                                // 登录账号
                                Cell cell = row.getCell(k);
                                userInfo.setUsername(getValue(cell));
                            } else if (k == 1) {
                                // 密码
                                Cell cell = row.getCell(k);
                                userInfo.setPassword(getValue(cell));
                            } else if (k == 2) {
                                // 用户显示
                                Cell cell = row.getCell(k);
                                userInfo.setDisplayName(getValue(cell));
                            } else if (k == 3) {
                                // 姓
                                Cell cell = row.getCell(k);
                                userInfo.setFamilyName(getValue(cell));
                            } else if (k == 4) {
                                // 名
                                Cell cell = row.getCell(k);
                                userInfo.setGivenName(getValue(cell));
                            } else if (k == 5) {
                                // 中间名
                                Cell cell = row.getCell(k);
                                userInfo.setMiddleName(getValue(cell));
                            } else if (k == 6) {
                                // 昵称
                                Cell cell = row.getCell(k);
                                userInfo.setNickName(getValue(cell));
                            } else if (k == 7) {
                                // 性别
                                Cell cell = row.getCell(k);
                                String gender = getValue(cell);
                                userInfo.setGender(gender.equals("")? 1 : Integer.valueOf(getValue(cell)));
                            } else if (k == 8) {
                                // 语言偏好
                                Cell cell = row.getCell(k);
                                userInfo.setPreferredLanguage(getValue(cell));
                            } else if (k == 9) {
                                // 时区
                                Cell cell = row.getCell(k);
                                userInfo.setTimeZone(getValue(cell));
                            } else if (k == 10) {
                                // 用户类型
                                Cell cell = row.getCell(k);
                                userInfo.setUserType(getValue(cell));
                            } else if (k == 11) {
                                // 员工编码
                                Cell cell = row.getCell(k);
                                userInfo.setEmployeeNumber(getValue(cell));
                            } else if (k == 12) {
                                // AD域账号
                                Cell cell = row.getCell(k);
                                userInfo.setWindowsAccount(getValue(cell));
                            }else if (k == 13) {
                                // 所属机构
                                Cell cell = row.getCell(k);
                                userInfo.setOrganization(getValue(cell));
                            }else if (k == 14) {
                                // 分支机构
                                Cell cell = row.getCell(k);
                                userInfo.setDivision(getValue(cell));
                            }else if (k == 15) {
                                // 部门编号
                                Cell cell = row.getCell(k);
                                userInfo.setDepartmentId(getValue(cell));
                            }else if (k == 16) {
                                // 部门名称
                                Cell cell = row.getCell(k);
                                userInfo.setDepartment(getValue(cell));
                            }else if (k == 17) {
                                // 成本中心
                                Cell cell = row.getCell(k);
                                userInfo.setCostCenter(getValue(cell));
                            }else if (k == 18) {
                                // 职位
                                Cell cell = row.getCell(k);
                                userInfo.setJobTitle(getValue(cell));
                            }else if (k == 19) {
                                // 级别
                                Cell cell = row.getCell(k);
                                userInfo.setJobLevel(getValue(cell));
                            }else if (k == 20) {
                                // 上级经理
                                Cell cell = row.getCell(k);
                                userInfo.setManager(getValue(cell));
                            }else if (k == 21) {
                                // 助理
                                Cell cell = row.getCell(k);
                                userInfo.setAssistant(getValue(cell));
                            }else if (k == 22) {
                                // 入职时间
                                Cell cell = row.getCell(k);
                                userInfo.setEntryDate(getValue(cell));
                            }else if (k == 23) {
                                // 离职时间
                                Cell cell = row.getCell(k);
                                userInfo.setQuitDate(getValue(cell));
                            }else if (k == 24) {
                                // 工作-国家
                                Cell cell = row.getCell(k);
                                userInfo.setWorkCountry(getValue(cell));
                            }else if (k == 25) {
                                // 工作-省
                                Cell cell = row.getCell(k);
                                userInfo.setWorkRegion(getValue(cell));
                            }else if (k == 26) {
                                // 工作-城市
                                Cell cell = row.getCell(k);
                                userInfo.setTimeZone(getValue(cell));
                            }else if (k == 27) {
                                // 工作-地址
                                Cell cell = row.getCell(k);
                                userInfo.setWorkLocality(getValue(cell));
                            }else if (k == 28) {
                                // 邮编
                                Cell cell = row.getCell(k);
                                userInfo.setWorkPostalCode(getValue(cell));
                            }else if (k == 29) {
                                // 传真
                                Cell cell = row.getCell(k);
                                userInfo.setWorkFax(getValue(cell));
                            }else if (k == 30) {
                                // 工作电话
                                Cell cell = row.getCell(k);
                                userInfo.setWorkPhoneNumber(getValue(cell));
                            }else if (k == 31) {
                                // 工作邮件
                                Cell cell = row.getCell(k);
                                userInfo.setWorkEmail(getValue(cell));
                            }else if (k == 32) {
                                // 证件类型 todo 现在数据库中存储的是tinyint
//                                Cell cell = row.getCell(k);
//                                userInfo.setIdType(getValue(cell));
                            }else if (k == 33) {
                                // 证件号码
                                Cell cell = row.getCell(k);
                                userInfo.setIdCardNo(getValue(cell));
                            } else if (k == 34) {
                                // 出生日期
                                Cell cell = row.getCell(k);
                                userInfo.setBirthDate(getValue(cell));
                            }else if (k == 35) {
                                // 婚姻状态 todo 现在数据字段类型是 tinyint
//                                Cell cell = row.getCell(k);
//                                userInfo.setMarried(getValue(cell));
                            }else if (k == 36) {
                                // 开始工作时间
                                Cell cell = row.getCell(k);
                                userInfo.setStartWorkDate(getValue(cell));
                            }else if (k == 37) {
                                // 个人主页
                                Cell cell = row.getCell(k);
                                userInfo.setWebSite(getValue(cell));
                            }else if (k == 38) {
                                // 即时通讯
                                Cell cell = row.getCell(k);
                                userInfo.setDefineIm(getValue(cell));
                            }else if (k == 39) {
                                // 国家
                                Cell cell = row.getCell(k);
                                userInfo.setHomeCountry(getValue(cell));
                            }else if (k == 40) {
                                // 省
                                Cell cell = row.getCell(k);
                                userInfo.setHomeRegion(getValue(cell));
                            }else if (k == 41) {
                                // 城市
                                Cell cell = row.getCell(k);
                                userInfo.setHomeLocality(getValue(cell));
                            }else if (k == 42) {
                                // 家庭地址
                                Cell cell = row.getCell(k);
                                userInfo.setHomeStreetAddress(getValue(cell));
                            }else if (k == 43) {
                                // 家庭邮编
                                Cell cell = row.getCell(k);
                                userInfo.setHomePostalCode(getValue(cell));
                            }else if (k == 44) {
                                // 家庭传真
                                Cell cell = row.getCell(k);
                                userInfo.setHomeFax(getValue(cell));
                            }else if (k == 45) {
                                // 家庭电话
                                Cell cell = row.getCell(k);
                                userInfo.setHomePhoneNumber(getValue(cell));
                            }else if (k == 46) {
                                // 家庭邮箱
                                Cell cell = row.getCell(k);
                                userInfo.setHomeEmail(getValue(cell));
                            }
                        }
                        userInfo.setStatus(1);
                        userInfoList.add(passwordEncoder(userInfo));
                    }
                }
            }
            // 数据去重
            if(CollectionUtils.isEmpty(userInfoList)){
                userInfoList = userInfoList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getUsername()))), ArrayList::new));
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       
        return batchInsert(userInfoList);
    }


    /**
             *       根据数据格式返回数据
     *
     * @param cell
     * @return
     */
    public static String getValue(Cell cell) {
        
        if (cell == null) {
            return "";
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            if("General".equals(cell.getCellStyle().getDataFormatString())){
                return new DecimalFormat("0").format(cell.getNumericCellValue());
            }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){
               return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
            }else{
                return new DecimalFormat("0").format(cell.getNumericCellValue());
            }
        } else {
            return String.valueOf(cell.getStringCellValue().trim());
        }
    }
	
	public boolean changeSharedSecret(UserInfo userInfo){
		return getMapper().changeSharedSecret(userInfo)>0;
	}
	
	public boolean changePasswordQuestion(UserInfo userInfo){
		return getMapper().changePasswordQuestion(userInfo)>0;
	}
	
	public boolean changeAuthnType(UserInfo userInfo){
		return getMapper().changeAuthnType(userInfo)>0;
	}
	
	public boolean changeEmail(UserInfo userInfo){
		return getMapper().changeEmail(userInfo)>0;
	}
	
	public boolean changeMobile(UserInfo userInfo){
		return getMapper().changeMobile(userInfo)>0;
	}
	
    public UserInfo queryUserInfoByEmailMobile(String emailMobile) {
        return getMapper().queryUserInfoByEmailMobile(emailMobile);
    }
    
    public int updateProfile(UserInfo userInfo){
        
        return getMapper().updateProfile(userInfo);
    }

    public void setPasswordPolicyValidator(PasswordPolicyValidator passwordPolicyValidator) {
        this.passwordPolicyValidator = passwordPolicyValidator;
    }

}
