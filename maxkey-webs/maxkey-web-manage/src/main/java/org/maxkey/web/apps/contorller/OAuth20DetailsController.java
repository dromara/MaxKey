package org.maxkey.web.apps.contorller;

import java.util.List;

import org.maxkey.authz.oauth2.provider.client.JdbcClientDetailsService;
import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.constants.PROTOCOLS;
import org.maxkey.crypto.ReciprocalUtils;
import org.maxkey.domain.apps.Applications;
import org.maxkey.domain.apps.OAuth20Details;
import org.maxkey.domain.apps.oauth2.provider.client.BaseClientDetails;
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
@RequestMapping(value={"/apps/oauth20"})
public class OAuth20DetailsController  extends BaseAppContorller {
	final static Logger _logger = LoggerFactory.getLogger(OAuth20DetailsController.class);
	
	@Autowired
	JdbcClientDetailsService oauth20JdbcClientDetailsService;

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		ModelAndView modelAndView=new ModelAndView("apps/oauth20/appAdd");
		OAuth20Details oauth20Details=new OAuth20Details();
		oauth20Details.setId(oauth20Details.generateId());
		oauth20Details.setSecret(ReciprocalUtils.generateKey(""));
		oauth20Details.setClientId(oauth20Details.getId());
		oauth20Details.setClientSecret(oauth20Details.getSecret());
		oauth20Details.setProtocol(PROTOCOLS.OAUTH20);
		modelAndView.addObject("model",oauth20Details);
		return modelAndView;
	}
	
	
	@RequestMapping(value={"/add"})
	public ModelAndView insert(@ModelAttribute("oauth20Details") OAuth20Details oauth20Details) {
		_logger.debug("-Add  :" + oauth20Details);
		
		transform(oauth20Details);

		oauth20Details.setClientSecret(oauth20Details.getSecret());
		
		oauth20JdbcClientDetailsService.addClientDetails(oauth20Details.clientDetailsRowMapper());
		if (applicationsService.insert(oauth20Details)) {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
			
		} else {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+oauth20Details.getId());
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("apps/oauth20/appUpdate");
		BaseClientDetails baseClientDetails=(BaseClientDetails)oauth20JdbcClientDetailsService.loadClientByClientId(id);
		Applications application=applicationsService.get(id);//
		decoderSecret(application);
		OAuth20Details oauth20Details=new OAuth20Details(application,baseClientDetails);
		oauth20Details.setSecret(application.getSecret());
		oauth20Details.setClientSecret(application.getSecret());
		_logger.debug("forwardUpdate "+oauth20Details);
		WebContext.setAttribute(oauth20Details.getId(), oauth20Details.getIcon());
		modelAndView.addObject("model",oauth20Details);
		return modelAndView;
	}
	/**
	 * modify
	 * @param application
	 * @return
	 */
	@RequestMapping(value={"/update"})  
	public ModelAndView update(@ModelAttribute("oauth20Details") OAuth20Details oauth20Details) {
		//
		_logger.debug("-update  application :" + oauth20Details);
		_logger.debug("-update  oauth20Details use oauth20JdbcClientDetails" );
		transform(oauth20Details);
		
		oauth20Details.setClientSecret(oauth20Details.getSecret());
		oauth20JdbcClientDetailsService.updateClientDetails(oauth20Details.clientDetailsRowMapper());
		oauth20JdbcClientDetailsService.updateClientSecret(oauth20Details.getClientId(), oauth20Details.getClientSecret());
		if (applicationsService.update(oauth20Details)) {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
		} else {
			  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		}
		return   WebContext.forward("forwardUpdate/"+oauth20Details.getId());
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete/{id}"})
	public Message delete(@PathVariable("id") String id) {
		_logger.debug("-delete  application :" + id);
		oauth20JdbcClientDetailsService.removeClientDetails(id);
		if (applicationsService.remove(id)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.error);
		}
	}
	
	
	
	
	
}
