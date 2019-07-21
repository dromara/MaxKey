package org.maxkey.web.contorller;

import javax.validation.Valid;

import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.dao.service.MyProfileService;
import org.maxkey.dao.service.UserInfoService;
import org.maxkey.domain.UserInfo;
import org.maxkey.util.StringUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageScope;
import org.maxkey.web.message.MessageType;
import org.maxkey.web.message.OperateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/profile"})
public class ProfileController {
	final static Logger _logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MyProfileService myProfileService;
	
	
	@RequestMapping(value={"/myProfile"})
	public ModelAndView forwardBasic(){
		ModelAndView modelAndView=new ModelAndView("profile/myProfile");
		UserInfo userInfo=new UserInfo();
		userInfo.setId(WebContext.getUserInfo().getId());
		userInfo=userInfoService.load(userInfo);
		WebContext.getSession().setAttribute(userInfo.getId(), userInfo.getPicture());
		
//		HashMap<String,Object>extraAttributeMap=new HashMap<String,Object>();
//		extraAttributeMap=(HashMap<String,Object>)JsonUtils.json2Object(userInfo.getExtraAttribute(),extraAttributeMap);
//		modelAndView.addObject("extraAttributeMap", extraAttributeMap);
//		_logger.info("extraAttributeMap : "+extraAttributeMap);
		
		modelAndView.addObject("model", userInfo);
		return modelAndView;
	}
	
	
	/**
	 * 修改用户
	 * @param userInfo
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/update/myProfile") 
	public ModelAndView updatebasic(@Valid  @ModelAttribute("userInfo")UserInfo userInfo,BindingResult result) {
		_logger.debug(userInfo.toString());
		userInfo.setNameZHShortSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), true));
		userInfo.setNameZHSpell(StringUtils.hanYu2Pinyin(userInfo.getDisplayName(), false));
		
//		if(userInfo.getExtraAttributeValue()!=null){
//			String []extraAttributeLabel=userInfo.getExtraAttributeName().split(",");
//			String []extraAttributeValue=userInfo.getExtraAttributeValue().split(",");
//			Map<String,String> extraAttributeMap=new HashMap<String,String> ();
//			for(int i=0;i<extraAttributeLabel.length;i++){
//				extraAttributeMap.put(extraAttributeLabel[i], extraAttributeValue[i]);
//			}
//			String extraAttribute=JsonUtils.object2Json(extraAttributeMap);
//			userInfo.setExtraAttribute(extraAttribute);
//		}
		
		if(myProfileService.updateProfile(userInfo)>0) {
			new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),userInfo,MessageType.success,OperateType.add,MessageScope.DB);
			
		}else{
			new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		}
		
		return   WebContext.forward("forwardMyProfile");
		
	}

}
