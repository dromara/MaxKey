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
 

package org.maxkey.web.contorller;

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

import javax.validation.Valid;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.constants.ConstsPasswordSetType;
import org.maxkey.crypto.password.PasswordReciprocal;
import org.maxkey.entity.ExcelImport;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.UserInfoService;
import org.maxkey.util.DateUtils;
import org.maxkey.util.ExcelUtils;
import org.maxkey.util.JsonUtils;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageScope;
import org.maxkey.web.message.MessageType;
import org.maxkey.web.message.OperateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

/**
 * @author Crystal.Sea
 *
 */
@Controller
@RequestMapping(value = { "/userinfo" })
public class UserInfoController {
	final static Logger _logger = LoggerFactory.getLogger(UserInfoController.class);
	
	@Autowired
	@Qualifier("userInfoService")
	private UserInfoService userInfoService;

	
	/**
	 * 查询用户列表
	 * @param user
	 * @return
	 */
	@RequestMapping(value={"/grid"})
	@ResponseBody
	public JpaPageResults<UserInfo> forwardUsersList(@ModelAttribute("userInfo") UserInfo userInfo){
		userInfo.setInstId(WebContext.getUserInfo().getInstId());
		return userInfoService.queryPageResults(userInfo);
		
	}
	
	@RequestMapping(value={"/forwardAdd"})
	public ModelAndView forwardSelectUserType(){
		ModelAndView modelAndView=new ModelAndView("/userinfo/userAdd");
		//List<UserType> userTypeList=userTypeService.query(null);
		//modelAndView.addObject("userTypeList", userTypeList);
		return modelAndView;
	}
	
	@RequestMapping(value={"/list"})
	public ModelAndView usersList(){
		return new ModelAndView("/userinfo/usersList");
	}
	
	@RequestMapping(value={"/select"})
	public ModelAndView usersSelect(){
		ModelAndView modelAndView= new ModelAndView("/userinfo/userinfoSelect");
		return modelAndView;
	}
	
	/**
	 * 新增
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/add") 
	public ModelAndView addUsers(@Valid  @ModelAttribute("userInfo")UserInfo userInfo,BindingResult result) {
		_logger.debug(userInfo.toString());
		if(result.hasErrors()){
			// new Message(WebContext.getValidErrorText(),result);
		}
		
		userInfo.setId(WebContext.genId());
		userInfo.setInstId(WebContext.getUserInfo().getInstId());
		//userInfo.setNameZHShortSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), true));
		//userInfo.setNameZHSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), false));
		if( userInfoService.insert(userInfo)) {
			  new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS),userInfo,MessageType.success,OperateType.add,MessageScope.DB);
		}
		
		 new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_ERROR),MessageType.error);
		return   WebContext.forward("forwardUpdate/"+userInfo.getId());
	}
	
	@RequestMapping(value={"/forwardUpdate/{id}"})
	public ModelAndView forwardUpdateUsers(@PathVariable("id")String id){
		ModelAndView modelAndView=new ModelAndView("/userinfo/userUpdate");
		UserInfo userInfo=userInfoService.get(id);
		if(userInfo.getPicture()!=null){
			userInfo.transPictureBase64();
		}
		
		modelAndView.addObject("model", userInfo);
		return modelAndView;
	}

	/**
	 * 查询用户，根据id
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getUsers/{id}") 
	public UserInfo getUserInfo(@PathVariable("id")String id) {
		_logger.debug(id);
		UserInfo userInfo = userInfoService.get(id);
		if(userInfo!=null&&userInfo.getDecipherable()!=null){
			try{
				userInfo.setPassword(PasswordReciprocal.getInstance().decoder(userInfo.getDecipherable()));
			}catch (Exception e) {
			}
			userInfo.setDecipherable(userInfo.getPassword());
		}
		return userInfo;
	}
	
	
    @ResponseBody
    @RequestMapping(value = "/randomPassword")
    public String randomPassword() {
        return userInfoService.randomPassword();
    }
	   
	/**
	 * 修改用户
	 * @param userInfo
	 * @param result
	 * @return
	 */

	@RequestMapping(value="/update") 
	public ModelAndView updateUsers(@Valid  @ModelAttribute("userInfo")UserInfo userInfo,BindingResult result) {
		_logger.debug(userInfo.toString());
		if(result.hasErrors()){
			// new Message(WebContext.getValidErrorText(),result);
		}
		_logger.info(userInfo.getExtraAttributeName());
		_logger.info(userInfo.getExtraAttributeValue());
		//userInfo.setNameZHShortSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), true));
		//userInfo.setNameZHSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), false));
		convertExtraAttribute(userInfo) ;
		_logger.info(userInfo.getExtraAttribute());
		userInfo.setInstId(WebContext.getUserInfo().getInstId());
		if(userInfoService.update(userInfo)) {
			new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),userInfo,MessageType.success,OperateType.add,MessageScope.DB);
			
		}
	    new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		return   WebContext.forward("forwardUpdate/"+userInfo.getId());
	}
	
	
	/**
	 * 批量删除用户
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/batchDelete")  
	public Message batchDeleteUsers(@RequestParam("id")String id) {
		_logger.debug(id);
		if(userInfoService.deleteBatch(StringUtils.string2List(id, ","))) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_ERROR),MessageType.error);
		}
	}
	
	/**
	 * 根据用户id删除用户
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete")  
	public Message deleteUsersById(@RequestParam("id") String id) {
		_logger.debug(id);
		if(userInfoService.deleteBatch(id)) {
			//provisioningPrepare.prepare(userInfo, OPERATEACTION.DELETE_ACTION);
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_SUCCESS),MessageType.success);
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.DELETE_ERROR),MessageType.error);
		}
	}
	
	protected void convertExtraAttribute(UserInfo userInfo) {
		if(userInfo.getExtraAttributeValue()!=null){
			String []extraAttributeLabel=userInfo.getExtraAttributeName().split(",");
			String []extraAttributeValue=userInfo.getExtraAttributeValue().split(",");
			Map<String,String> extraAttributeMap=new HashMap<String,String> ();
			for(int i=0;i<extraAttributeLabel.length;i++){
				extraAttributeMap.put(extraAttributeLabel[i], extraAttributeValue[i]);
			}
			String extraAttribute=JsonUtils.object2Json(extraAttributeMap);
			userInfo.setExtraAttribute(extraAttribute);
		}
	}
	
	@RequestMapping(value={"/forwardChangePassword/{id}"})
	public ModelAndView forwardChangePassword(@PathVariable("id")String id){
		ModelAndView modelAndView=new ModelAndView("/userinfo/changePassword");
		UserInfo userInfo=userInfoService.get(id);
		
		modelAndView.addObject("model", userInfo);
		return modelAndView;
	}
	
	@RequestMapping(value={"/forwardChangeUserinfoStatus/{id}"})
	public ModelAndView forwardChangeUserinfoStatus(@PathVariable("id")String id){
		ModelAndView modelAndView=new ModelAndView("/userinfo/changeUserinfoStatus");
		UserInfo userInfo=userInfoService.get(id);
		
		modelAndView.addObject("model", userInfo);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value="/changePassword")  
	public Message changePassword( @ModelAttribute("userInfo")UserInfo userInfo) {
		_logger.debug(userInfo.getId());
		userInfo.setPasswordSetType(ConstsPasswordSetType.PASSWORD_NORMAL);
		if(userInfoService.changePassword(userInfo,true)) {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
	}
	
    @RequestMapping(value = "/import")
    public ModelAndView importing(@ModelAttribute("excelImportFile")ExcelImport excelImportFile)  {
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
                        	UserInfo userInfo = buildUserFromSheetRow(row);
                            userInfoList.add(userInfo);
                            recordCount ++;
                            _logger.debug("record {} user {} account {}",recordCount,userInfo.getDisplayName(),userInfo.getUsername());
                        }
                    }
                }
                // 数据去重
                if(!CollectionUtils.isEmpty(userInfoList)){
                    userInfoList = userInfoList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getUsername()))), ArrayList::new));
                    if( userInfoService.insertBatch(userInfoList)) {
                    	new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS), null, MessageType.success, OperateType.add, MessageScope.DB);
                    }else {
                    	new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_ERROR), MessageType.error);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
            	excelImportFile.closeWorkbook();
            }
        }else {
        	new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_ERROR), MessageType.error);
        }
        
        return new ModelAndView("/userinfo/usersImport");
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
	
	
	public UserInfo buildUserFromSheetRow(Row row) {
		UserInfo userInfo = new UserInfo();
        userInfo.setCreatedDate(DateUtils.formatDateTime(new Date()));
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
        userInfo.setInstId(WebContext.getUserInfo().getInstId());
        return userInfo;
	}

}
