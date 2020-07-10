/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.maxkey.web;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.web.message.Message;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

public interface BasicController  <T extends JpaBaseDomain> {

	public JpaPageResults<T> pageResults(@ModelAttribute("modelAttribute") T modelAttribute);
	
	public ModelAndView forwardAdd(@ModelAttribute("modelAttribute") T modelAttribute);
	
	public Message insert(@ModelAttribute("modelAttribute") T modelAttribute);
	
	public ModelAndView forwardUpdate(@PathVariable("id") String id);
	
	public Message update(@ModelAttribute("modelAttribute") T modelAttribute);
	
	public Message delete(@ModelAttribute("modelAttribute") T modelAttribute) ;
	
	
}
