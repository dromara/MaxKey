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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.ConstantsOperateMessage;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.UserInfo;
import org.maxkey.persistence.service.UserInfoService;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


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
		
		userInfo.setId(userInfo.generateId());
		//userInfo.setNameZHShortSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), true));
		//userInfo.setNameZHSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), false));
		if( userInfoService.insert(userInfo)) {
			  new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_SUCCESS),userInfo,MessageType.success,OperateType.add,MessageScope.DB);
		}
		
		 new Message(WebContext.getI18nValue(ConstantsOperateMessage.INSERT_ERROR),MessageType.error);
		return   WebContext.forward("forwardUpdate/"+userInfo.getId());
	}
	
	@RequestMapping(value={"/forwardUpdate/{id}"})
	public ModelAndView forwardUpdateUsers(@PathVariable("id")String id){
		ModelAndView modelAndView=new ModelAndView("/userinfo/userUpdate");
		UserInfo userInfo=userInfoService.get(id);
		if(userInfo.getPicture()!=null){
			WebContext.getSession().setAttribute(userInfo.getId(), userInfo.getPicture());
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
				userInfo.setPassword(ReciprocalUtils.decoder(userInfo.getDecipherable()));
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
		if(userInfoService.update(userInfo)) {
			new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),userInfo,MessageType.success,OperateType.add,MessageScope.DB);
			
		}
	    new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
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
		if(userInfoService.batchDelete(StringUtils.string2List(id, ","))) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_ERROR),MessageType.error);
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
		if(userInfoService.batchDelete(StringUtils.string2List(id, ","))) {
			//provisioningPrepare.prepare(userInfo, OPERATEACTION.DELETE_ACTION);
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_SUCCESS),MessageType.success);
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.DELETE_ERROR),MessageType.error);
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
	
	@ResponseBody
	@RequestMapping(value="/changePassword")  
	public Message changePassword( @ModelAttribute("userInfo")UserInfo userInfo) {
		_logger.debug(userInfo.getId());
		if(userInfoService.changePassword(userInfo)) {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(ConstantsOperateMessage.UPDATE_ERROR),MessageType.error);
		}
	}
	
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
		    @Override
			public void setAsText(String value) {
		        	if(StringUtils.isNullOrBlank(value)){
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
}
