package org.maxkey.web.apps.contorller;

import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.constants.PROTOCOLS;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.dao.service.AppsCasDetailsService;
import org.maxkey.domain.apps.AppsCasDetails;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/apps/cas"})
public class CasDetailsController  extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(CasDetailsController.class);
	
	@Autowired
	AppsCasDetailsService casDetailsService;
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/cas/appAdd");
		AppsCasDetails casDetails =new AppsCasDetails();
		casDetails.setId(casDetails.generateId());
		casDetails.setProtocol(PROTOCOLS.CAS);
		casDetails.setSecret(ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.DES));
		modelAndView.addObject("model",casDetails);
		return modelAndView;
	}
	
	
	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("casDetails") AppsCasDetails casDetails) {
		_logger.debug("-Add  :" + casDetails);

		transform(casDetails);
		
		if (casDetailsService.insert(casDetails)&&appsService.insertApp(casDetails)) {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+casDetails.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/cas/appUpdate");
		AppsCasDetails casDetails=casDetailsService.getAppDetails(id);
		super.decoderSecret(casDetails);
		WebContext.setAttribute(casDetails.getId(), casDetails.getIcon());

		modelAndView.addObject("model",casDetails);
		return modelAndView;
	}
	
	/**
	 * modify
	 * @param application
	 * @return
	 */
	@RequestMapping(value={"/update"})  
	public ModelAndView update(@ModelAttribute("casDetails") AppsCasDetails casDetails) {
		//
		_logger.debug("-update  application :" + casDetails);
		transform(casDetails);

		if (casDetailsService.update(casDetails)&&appsService.updateApp(casDetails)) {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+casDetails.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		if (casDetailsService.remove(id)&&appsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.error);
		}
	}
	
	
}
