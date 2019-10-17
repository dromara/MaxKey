package org.maxkey.web;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.web.message.Message;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

public interface BasicController  <T extends JpaBaseDomain> {

	public JpaPageResults<T> pageResults(@ModelAttribute("modelAttr") T modelAttr);
	
	public ModelAndView forwardAdd(@ModelAttribute("modelAttr") T modelAttr);
	
	public Message insert(@ModelAttribute("modelAttr") T modelAttr);
	
	public ModelAndView forwardUpdate(@PathVariable("id") String id);
	
	public Message update(@ModelAttribute("modelAttr") T modelAttr);
	
	public Message delete(@ModelAttribute("modelAttr") T modelAttr) ;
	
	
}
