package org.maxkey.web.apps.contorller;

import java.util.List;

import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.constants.PROTOCOLS;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.dao.service.TokenBasedDetailsService;
import org.maxkey.domain.apps.TokenBasedDetails;
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
@RequestMapping(value={"/apps/ltpa"})
public class LtpaDetailsController  extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(TokenBasedDetailsController.class);
	
	@Autowired
	TokenBasedDetailsService tokenBasedDetailsService;
	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/ltpa/appAdd");
		TokenBasedDetails ltpaDetails =new TokenBasedDetails();
		ltpaDetails.setProtocol(PROTOCOLS.LTPA);
		ltpaDetails.setSecret(ReciprocalUtils.generateKey(ReciprocalUtils.Algorithm.DES));
		ltpaDetails.setAlgorithmKey(ltpaDetails.getSecret());
		modelAndView.addObject("model",ltpaDetails);
		return modelAndView;
	}
	
	
	
	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("ltpaDetails") TokenBasedDetails ltpaDetails) {
		_logger.debug("-Add  :" + ltpaDetails);
		transform(ltpaDetails);
		
		if (tokenBasedDetailsService.insert(ltpaDetails)&&applicationsService.insert(ltpaDetails)) {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+ltpaDetails.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/ltpa/appUpdate");
		TokenBasedDetails ltpaDetails=tokenBasedDetailsService.get(id);
		decoderSecret(ltpaDetails);
		String algorithmKey=passwordReciprocal.decoder(ltpaDetails.getAlgorithmKey());
		ltpaDetails.setAlgorithmKey(algorithmKey);
		WebContext.setAttribute(ltpaDetails.getId(), ltpaDetails.getIcon());

		modelAndView.addObject("model",ltpaDetails);
		return modelAndView;
	}
	
	/**
	 * modify
	 * @param application
	 * @return
	 */

	@RequestMapping(value={"/update"})  
	public ModelAndView update(@ModelAttribute("ltpaDetails") TokenBasedDetails ltpaDetails) {
		//
		_logger.debug("-update  application :" + ltpaDetails);
		System.out.println();
		transform(ltpaDetails);
		ltpaDetails.setAlgorithmKey(ltpaDetails.getSecret());
		if (tokenBasedDetailsService.update(ltpaDetails)&&applicationsService.update(ltpaDetails)) {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+ltpaDetails.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		if (tokenBasedDetailsService.remove(id)&&applicationsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_ERROR),MessageType.error);
		}
	}
	
	
}
