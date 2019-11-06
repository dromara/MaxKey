package org.maxkey.web.apps.contorller;


import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.apps.Apps;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/apps"})
public class ApplicationsController extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(ApplicationsController.class);
	
	@RequestMapping(value={"/list"})
	public ModelAndView applicationsList(){
		return new ModelAndView("apps/appsList");
	}
	
	@RequestMapping(value={"/select"})
	public ModelAndView select(){
		return new ModelAndView("apps/selectAppsList");
	}
	
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<Apps> queryDataGrid(@ModelAttribute("applications") Apps applications) {
		JpaPageResults<Apps> jqGridApp=appsService.queryPageResults(applications);
		if(jqGridApp!=null&&jqGridApp.getRows()!=null){
			for (Apps app : jqGridApp.getRows()){
				WebContext.setAttribute(app.getId(), app.getIcon());
			}
		}
		return jqGridApp;
	}
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("apps/appAdd");
	}
	
	
	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("application") Apps application) {
		_logger.debug("-Add  :" + application);
		
		transform(application);
		
		if (appsService.insert(application)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.error);
		}
		
	}
	
	/**
	 * query
	 * @param application
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/query"}) 
	public Message query(@ModelAttribute("application") Apps application) {
		_logger.debug("-query  :" + application);
		if (appsService.load(application)!=null) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_ERROR),MessageType.error);
		}
		
	}
	
	/**
	 * modify
	 * @param application
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("application") Apps application) {
		_logger.debug("-update  application :" + application);
		if (appsService.update(application)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("application") Apps application) {
		_logger.debug("-delete  application :" + application);
		if (appsService.delete(application)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.error);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = { "/generate/secret/{type}" })
	public String generateSecret(@PathVariable("type") String type) {
		String secret="";
		type=type.toLowerCase();
		if(type.equals("des")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.DES);
		}else if(type.equals("desede")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.DESede);
		}else if(type.equals("aes")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.AES);
		}else if(type.equals("blowfish")){
			secret=ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.Blowfish);
		}else{
			secret=ReciprocalUtils.generateKey("");
		}
		
		return secret;
	}
	
	
}
