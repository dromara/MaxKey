package org.maxkey.web.apps.contorller;

import java.util.List;

import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.constants.PROTOCOLS;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.apps.Applications;
import org.maxkey.domain.apps.ExtendApiDetails;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/apps/extendapi"})
public class ExtendApiDetailsController  extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(ExtendApiDetailsController.class);

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/extendapi/appAdd");
		ExtendApiDetails extendApiDetails=new ExtendApiDetails();
		extendApiDetails.setId(extendApiDetails.generateId());
		extendApiDetails.setProtocol(PROTOCOLS.EXTEND_API);
		extendApiDetails.setSecret(ReciprocalUtils.generateKey(""));

		modelAndView.addObject("model",extendApiDetails);
		return modelAndView;
	}
	
	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("extendApiDetails") ExtendApiDetails extendApiDetails) {
		_logger.debug("-Add  :" + extendApiDetails);
		
		transform(extendApiDetails);
		
		if (applicationsService.insert(extendApiDetails)) {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+extendApiDetails.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/extendapi/appUpdate");
		Applications application= applicationsService.get(id);
		super.decoderSecret(application);
		ExtendApiDetails extendApiDetails=new ExtendApiDetails();
		BeanUtils.copyProperties(application, extendApiDetails);
		
		WebContext.setAttribute(extendApiDetails.getId(), extendApiDetails.getIcon());

		modelAndView.addObject("model",extendApiDetails);
		return modelAndView;
	}
	
	/**
	 * modify
	 * @param extendApiDetails
	 * @return
	 */
	@RequestMapping(value={"/update"})  
	public ModelAndView update(@ModelAttribute("extendApiDetails") ExtendApiDetails extendApiDetails) {
		_logger.debug("-update  extendApiDetails :" + extendApiDetails);
		transform(extendApiDetails);
		
		if (applicationsService.update(extendApiDetails)) {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+extendApiDetails.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		if (applicationsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.error);
		}
	}

}
