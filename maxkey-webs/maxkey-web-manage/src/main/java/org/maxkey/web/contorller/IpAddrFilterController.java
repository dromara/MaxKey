package org.maxkey.web.contorller;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.constants.OPERATEMESSAGE;
import org.maxkey.dao.service.IpAddrFilterService;
import org.maxkey.domain.IpAddrFilter;
import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value={"/config/ipaddrfilter"})
public class IpAddrFilterController {
	final static Logger _logger = LoggerFactory.getLogger(IpAddrFilterController.class);
	
	@Autowired
	@Qualifier("ipAddrFilterService")
	IpAddrFilterService ipAddrFilterService;

	
	
	@RequestMapping(value={"/list"})
	public ModelAndView ipAddrFiltersList(){
		return new ModelAndView("config/ipaddrfilter/ipaddrfilterList");
	}
	
	
	@RequestMapping(value = { "/grid" })
	@ResponseBody
	public JpaPageResults<IpAddrFilter> queryDataGrid(@ModelAttribute("ipAddrFilters") IpAddrFilter ipAddrFilters) {
		_logger.debug(""+ipAddrFilters);
		return ipAddrFilterService.queryPageResults(ipAddrFilters);
	}

	
	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd() {
		return new ModelAndView("config/ipaddrfilter/ipaddrfilterAdd");
	}
	
	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("config/ipaddrfilter/ipaddrfilterUpdate");
		IpAddrFilter ipAddrFilter=ipAddrFilterService.get(id);
		modelAndView.addObject("model",ipAddrFilter);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"})
	public Message insert(@ModelAttribute("ipAddrFilter") IpAddrFilter ipAddrFilter) {
		_logger.debug("-Add  :" + ipAddrFilter);
		
		if (ipAddrFilterService.insert(ipAddrFilter)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.error);
		}
		
	}
	
	/**
	 * 查询
	 * @param ipAddrFilter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/query"}) 
	public Message query(@ModelAttribute("ipAddrFilter") IpAddrFilter ipAddrFilter) {
		_logger.debug("-query  :" + ipAddrFilter);
		if (ipAddrFilterService.load(ipAddrFilter)!=null) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.INSERT_ERROR),MessageType.error);
		}
		
	}
	
	/**
	 * 修改
	 * @param ipAddrFilter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/update"})  
	public Message update(@ModelAttribute("ipAddrFilter") IpAddrFilter ipAddrFilter) {
		_logger.debug("-update  ipAddrFilter :" + ipAddrFilter);
		
		if (ipAddrFilterService.update(ipAddrFilter)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.UPDATE_ERROR),MessageType.error);
		}
		
	}
	

	@ResponseBody
	@RequestMapping(value={"/delete"})
	public Message delete(@ModelAttribute("ipAddrFilter") IpAddrFilter ipAddrFilter) {
		_logger.debug("-delete  ipAddrFilter :" + ipAddrFilter);
		
		if (ipAddrFilterService.delete(ipAddrFilter)) {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.success);
			
		} else {
			return  new Message(WebContext.getI18nValue(OPERATEMESSAGE.DELETE_SUCCESS),MessageType.error);
		}
		
	}
}
