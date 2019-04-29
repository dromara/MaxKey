package org.maxkey.web.apps.contorller;

import java.util.List;

import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.constants.PROTOCOLS;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.dao.service.Saml20DetailsService;
import org.maxkey.domain.apps.SAML20Details;
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
@RequestMapping(value={"/apps/saml20"})
public class SAML20DetailsController  extends BaseSAMLAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(SAML20DetailsController.class);
	
	@Autowired
	Saml20DetailsService saml20DetailsService;
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/saml20/appAdd");
		SAML20Details saml20Details=new SAML20Details();
		saml20Details.setSecret(ReciprocalUtils.generateKey(""));
		saml20Details.setProtocol(PROTOCOLS.SAML20);
		modelAndView.addObject("model",saml20Details);
		 
		return modelAndView;
	}
	
	
	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("saml20Details") SAML20Details saml20Details) {
		_logger.debug("-Add  :" + saml20Details);

		try {
			transform(saml20Details);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saml20DetailsService.insert(saml20Details);
		if (applicationsService.insert(saml20Details)) {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+saml20Details.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/saml20/appUpdate");
		SAML20Details saml20Details=saml20DetailsService.get(id);
		decoderSecret(saml20Details);
		WebContext.setAttribute(saml20Details.getId(), saml20Details.getIcon());
		modelAndView.addObject("model",saml20Details);
		return modelAndView;
	}
	/**
	 * modify
	 * @param application
	 * @return
	 */
	@RequestMapping(value={"/update"})  
	public ModelAndView update(@ModelAttribute("saml20Details") SAML20Details saml20Details) {
		//
		_logger.debug("-update  application :" + saml20Details);
	   _logger.debug("");
		try {
			transform(saml20Details);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saml20DetailsService.update(saml20Details);
		if (applicationsService.update(saml20Details)) {
			 new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			 new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+saml20Details.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		if (saml20DetailsService.remove(id)&&applicationsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.error);
		}
	}
	

	
	
}
