package org.maxkey.web.contorller;

import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.dao.service.Saml20MetadataService;
import org.maxkey.domain.Saml20Metadata;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/config/saml20/metadata"})
public class Saml20MetadataController{
	final static Logger _logger = LoggerFactory.getLogger(Saml20MetadataController.class);
	
	@Autowired
	Saml20MetadataService saml20MetadataService;
	
	@RequestMapping(value = { "/forward" })
	public ModelAndView forwardUpdate(@RequestParam(value="id",required=false) String id) {
		ModelAndView modelAndView=new ModelAndView("config/saml20/metadata/metadata");
		Saml20Metadata saml20Metadata=saml20MetadataService.get(null);
		if(saml20Metadata==null){
			saml20Metadata=new Saml20Metadata();
			modelAndView.addObject("modaction","add");
		}else{
			modelAndView.addObject("modaction","update");
		}
		modelAndView.addObject("model",saml20Metadata);
		return modelAndView;
	}
	/**
	 * 修改
	 * @param application
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("saml20Metadata") Saml20Metadata saml20Metadata,@RequestParam("modaction") String modaction) {
		//
		_logger.debug("-update  application :" + saml20Metadata);

		if(modaction.equals("add")){
			if (saml20MetadataService.insert(saml20Metadata)) {
				return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
				
			} else {
				return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_ERROR),MessageType.error);
			}
		}else{
			if (saml20MetadataService.update(saml20Metadata)) {
				return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
				
			} else {
				return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
			}
		}
		
	}
	
}
