/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.web.idm.contorller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsEntryType;
import org.dromara.maxkey.constants.ConstsAct;
import org.dromara.maxkey.constants.ConstsActResult;
import org.dromara.maxkey.constants.ConstsPasswordSetType;
import org.dromara.maxkey.entity.ChangePassword;
import org.dromara.maxkey.entity.ExcelImport;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.FileUploadService;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.UserInfoService;
import org.dromara.maxkey.util.ExcelUtils;
import org.dromara.maxkey.util.JsonUtils;
import org.dromara.maxkey.web.WebContext;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

/**
 * @author Crystal.Sea
 *
 */
@RestController
@RequestMapping(value = { "/users" })
public class UserInfoController {
	static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	HistorySystemLogsService systemLog;
	
	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Message<?> fetch(@ModelAttribute UserInfo userInfo,@CurrentUser UserInfo currentUser) {
		logger.debug(""+userInfo);
		userInfo.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<UserInfo>>(
				userInfoService.fetchPageResults(userInfo));
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> query(@ModelAttribute UserInfo userInfo,@CurrentUser UserInfo currentUser) {
		logger.debug("-query  :" + userInfo);
		if (userInfoService.query(userInfo)!=null) {
			 return new Message<UserInfo>(Message.SUCCESS);
		} else {
			 return new Message<UserInfo>(Message.SUCCESS);
		}
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> get(@PathVariable("id") String id) {
		UserInfo userInfo=userInfoService.get(id);
		userInfo.trans();
		return new Message<UserInfo>(userInfo);
	}
	
	@RequestMapping(value = { "/getByUsername/{username}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> getByUsername(@PathVariable("username") String username) {
		UserInfo userInfo=userInfoService.findByUsername(username);
		userInfo.trans();
		return new Message<UserInfo>(userInfo);
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> insert(@RequestBody UserInfo userInfo,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  :" + userInfo);
		userInfo.setId(WebContext.genId());
		userInfo.setInstId(currentUser.getInstId());
		if(StringUtils.isNotBlank(userInfo.getPictureId())) {
			userInfo.setPicture(fileUploadService.get(userInfo.getPictureId()).getUploaded());
			fileUploadService.delete(userInfo.getPictureId());
		}
		if (userInfoService.insert(userInfo)) {
			systemLog.insert(
					ConstsEntryType.USERINFO, 
					userInfo, 
					ConstsAct.CREATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
			return new Message<UserInfo>(Message.SUCCESS);
		} else {
			return new Message<UserInfo>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> update(@RequestBody  UserInfo userInfo,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  :" + userInfo);
		logger.info(userInfo.getExtraAttributeName());
		logger.info(userInfo.getExtraAttributeValue());
		//userInfo.setNameZHShortSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), true));
		//userInfo.setNameZHSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), false));
		convertExtraAttribute(userInfo) ;
		logger.info(userInfo.getExtraAttribute());
		userInfo.setInstId(currentUser.getInstId());
		if(StringUtils.isNotBlank(userInfo.getPictureId())) {
			userInfo.setPicture(fileUploadService.get(userInfo.getPictureId()).getUploaded());
			fileUploadService.delete(userInfo.getPictureId());
		}
		if (userInfoService.update(userInfo)) {
			systemLog.insert(
					ConstsEntryType.USERINFO, 
					userInfo, 
					ConstsAct.UPDATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
		    return new Message<UserInfo>(Message.SUCCESS);
		} else {
			return new Message<UserInfo>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		
		if (userInfoService.deleteBatch(ids)) {
			systemLog.insert(
					ConstsEntryType.USERINFO, 
					ids, 
					ConstsAct.DELETE, 
					ConstsActResult.SUCCESS, 
					currentUser);
			 return new Message<UserInfo>(Message.SUCCESS);
		} else {
			return new Message<UserInfo>(Message.FAIL);
		}
	}

	
    @ResponseBody
    @RequestMapping(value = "/randomPassword", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Message<?> randomPassword() {
        return new Message<Object>(
        		Message.SUCCESS,
        		(Object)userInfoService.randomPassword()
        	);
    }
	   
	
	protected void convertExtraAttribute(UserInfo userInfo) {
		if(userInfo.getExtraAttributeValue()!=null){
			String []extraAttributeLabel=userInfo.getExtraAttributeName().split(",");
			String []extraAttributeValue=userInfo.getExtraAttributeValue().split(",");
			Map<String,String> extraAttributeMap=new HashMap<String,String> ();
			for(int i=0;i<extraAttributeLabel.length;i++){
				extraAttributeMap.put(extraAttributeLabel[i], extraAttributeValue[i]);
			}
			String extraAttribute=JsonUtils.toString(extraAttributeMap);
			userInfo.setExtraAttribute(extraAttribute);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/changePassword", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> changePassword(
			@RequestBody ChangePassword changePassword,
			@CurrentUser UserInfo currentUser) {
		logger.debug("UserId {}",changePassword.getUserId());
		changePassword.setPasswordSetType(ConstsPasswordSetType.PASSWORD_NORMAL);
		if(userInfoService.changePassword(changePassword,true)) {
			systemLog.insert(
					ConstsEntryType.USERINFO, 
					changePassword, 
					ConstsAct.CHANGE_PASSWORD, 
					ConstsActResult.SUCCESS, 
					currentUser);
			return new Message<UserInfo>(Message.SUCCESS);
		} else {
			return new Message<UserInfo>(Message.FAIL);
		}
	}
	
	@RequestMapping(value = { "/updateStatus" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Message<?> updateStatus(@ModelAttribute UserInfo userInfo,@CurrentUser UserInfo currentUser) {
		logger.debug(""+userInfo);
		UserInfo loadUserInfo = userInfoService.get(userInfo.getId());
		userInfo.setInstId(currentUser.getInstId());
		userInfo.setUsername(loadUserInfo.getUsername());
		userInfo.setDisplayName(loadUserInfo.getDisplayName());
		if(userInfoService.updateStatus(userInfo)) {
			systemLog.insert(
					ConstsEntryType.USERINFO, 
					userInfo, 
					ConstsAct.statusActon.get(userInfo.getStatus()), 
					ConstsActResult.SUCCESS, 
					currentUser);
			return new Message<UserInfo>(Message.SUCCESS);
		} else {
			return new Message<UserInfo>(Message.FAIL);
		}
	}
	
    @RequestMapping(value = "/import")
    public Message<?> importingUsers(
    		@ModelAttribute("excelImportFile")ExcelImport excelImportFile,
    		@CurrentUser UserInfo currentUser)  {
        if (excelImportFile.isExcelNotEmpty() ) {
            try {
                List<UserInfo> userInfoList = Lists.newArrayList();
                Workbook workbook = excelImportFile.biuldWorkbook();
                int recordCount = 0;
                int sheetSize = workbook.getNumberOfSheets();
                for (int i = 0; i < sheetSize; i++) {//遍历sheet页
                    Sheet sheet = workbook.getSheetAt(i);
                    int rowSize = sheet.getLastRowNum() + 1;
                    for (int j = 1; j < rowSize; j++) {//遍历行
                        Row row = sheet.getRow(j);
                        if (row == null || j <3 ) {//略过空行和前3行
                            continue;
                        } else {//其他行是数据行
                        	UserInfo userInfo = buildUserFromSheetRow(row,currentUser);
                            userInfoList.add(userInfo);
                            recordCount ++;
                            logger.debug("record {} user {} account {}",recordCount,userInfo.getDisplayName(),userInfo.getUsername());
                        }
                    }
                }
                // 数据去重
                if(!CollectionUtils.isEmpty(userInfoList)){
                    userInfoList = userInfoList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getUsername()))), ArrayList::new));
                    if( userInfoService.insertBatch(userInfoList)) {
                    	return new Message<UserInfo>(Message.SUCCESS);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
            	excelImportFile.closeWorkbook();
            }
        }
        return new Message<UserInfo>(Message.FAIL);
        
    }
    
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
		    @Override
			public void setAsText(String value) {
		        	if(StringUtils.isEmpty(value)){
		        		setValue(null);
		        	}else{
		        		setValue(value);
		        	}
		    }

		    
		});
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        dateFormat.setLenient(false);  
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	
	public UserInfo buildUserFromSheetRow(Row row,UserInfo currentUser) {
		UserInfo userInfo = new UserInfo();
        userInfo.setCreatedDate(new Date());
		// 登录账号
		userInfo.setUsername(ExcelUtils.getValue(row, 0));
		// 密码
		userInfo.setPassword(ExcelUtils.getValue(row, 1));
		// 用户显示
		userInfo.setDisplayName(ExcelUtils.getValue(row, 2));
		// 姓
		userInfo.setFamilyName(ExcelUtils.getValue(row, 3));
		// 名
		userInfo.setGivenName(ExcelUtils.getValue(row, 4));
		// 中间名
		userInfo.setMiddleName(ExcelUtils.getValue(row, 5));
		// 昵称
		userInfo.setNickName(ExcelUtils.getValue(row, 6));
		// 性别
		String gender = ExcelUtils.getValue(row, 7);
		userInfo.setGender(gender.equals("") ? 1 : Integer.valueOf(gender));
		// 语言偏好
		userInfo.setPreferredLanguage(ExcelUtils.getValue(row, 8));
		// 时区
		userInfo.setTimeZone(ExcelUtils.getValue(row, 9));
		// 用户类型
		userInfo.setUserType(ExcelUtils.getValue(row, 10));
		// 员工编码
		userInfo.setEmployeeNumber(ExcelUtils.getValue(row, 11));
		// AD域账号
		userInfo.setWindowsAccount(ExcelUtils.getValue(row, 12));
		// 所属机构
		userInfo.setOrganization(ExcelUtils.getValue(row, 13));
		// 分支机构
		userInfo.setDivision(ExcelUtils.getValue(row, 14));
		// 部门编号
		userInfo.setDepartmentId(ExcelUtils.getValue(row, 15));
		// 部门名称
		userInfo.setDepartment(ExcelUtils.getValue(row, 16));
		// 成本中心
		userInfo.setCostCenter(ExcelUtils.getValue(row, 17));
		// 职位
		userInfo.setJobTitle(ExcelUtils.getValue(row, 18));
		// 级别
		userInfo.setJobLevel(ExcelUtils.getValue(row, 19));
		// 上级经理
		userInfo.setManager(ExcelUtils.getValue(row, 20));
		// 助理
		userInfo.setAssistant(ExcelUtils.getValue(row, 21));
		// 入职时间
		userInfo.setEntryDate(ExcelUtils.getValue(row, 22));
		// 离职时间
		userInfo.setQuitDate(ExcelUtils.getValue(row, 23));
		// 工作-国家
		userInfo.setWorkCountry(ExcelUtils.getValue(row, 24));
		// 工作-省
		userInfo.setWorkRegion(ExcelUtils.getValue(row, 25));
		// 工作-城市
		userInfo.setTimeZone(ExcelUtils.getValue(row, 26));
		// 工作-地址
		userInfo.setWorkLocality(ExcelUtils.getValue(row, 27));
		// 邮编
		userInfo.setWorkPostalCode(ExcelUtils.getValue(row, 28));
		// 传真
		userInfo.setWorkFax(ExcelUtils.getValue(row, 29));
		// 工作电话
		userInfo.setWorkPhoneNumber(ExcelUtils.getValue(row, 30));
		// 工作邮件
		userInfo.setWorkEmail(ExcelUtils.getValue(row, 31));
		// 证件类型 todo 现在数据库中存储的是tinyint
//      userInfo.setIdType(ExcelUtils.getValue(row, 32));
		// 证件号码
		userInfo.setIdCardNo(ExcelUtils.getValue(row, 33));
		// 出生日期
		userInfo.setBirthDate(ExcelUtils.getValue(row, 34));
		// 婚姻状态 todo 现在数据字段类型是 tinyint
//      userInfo.setMarried(ExcelUtils.getValue(row, 35));
		// 开始工作时间
		userInfo.setStartWorkDate(ExcelUtils.getValue(row, 36));
		// 个人主页
		userInfo.setWebSite(ExcelUtils.getValue(row, 37));
		// 即时通讯
		userInfo.setDefineIm(ExcelUtils.getValue(row, 38));
		// 国家
		userInfo.setHomeCountry(ExcelUtils.getValue(row, 39));
		// 省
		userInfo.setHomeRegion(ExcelUtils.getValue(row, 40));
		// 城市
		userInfo.setHomeLocality(ExcelUtils.getValue(row, 41));
		// 家庭地址
		userInfo.setHomeStreetAddress(ExcelUtils.getValue(row, 42));
		// 家庭邮编
		userInfo.setHomePostalCode(ExcelUtils.getValue(row, 43));
		// 家庭传真
		userInfo.setHomeFax(ExcelUtils.getValue(row, 44));
		// 家庭电话
		userInfo.setHomePhoneNumber(ExcelUtils.getValue(row, 45));
		// 家庭邮箱
		userInfo.setHomeEmail(ExcelUtils.getValue(row, 46));
		userInfoService.passwordEncoder(userInfo);
        userInfo.setStatus(1);
        userInfo.setInstId(currentUser.getInstId());
        return userInfo;
	}

}
