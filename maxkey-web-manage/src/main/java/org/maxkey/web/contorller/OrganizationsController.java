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
 

package org.maxkey.web.contorller;

import java.util.HashMap;
import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.domain.Organizations;
import org.maxkey.persistence.service.OrganizationsService;
import org.maxkey.web.WebContext;
import org.maxkey.web.component.TreeNode;
import org.maxkey.web.component.TreeNodeList;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping({"/orgs"})
public class OrganizationsController {
  static final Logger _logger = LoggerFactory.getLogger(OrganizationsController.class);

  
  @Autowired
  OrganizationsService organizationsService;

  
  @ResponseBody
  @RequestMapping({"/tree"})
  public List<HashMap<String, Object>> organizationsTree(@RequestParam(value = "id", required = false) String id) {
    _logger.debug("organizationsTree id :" + id);
    Organizations queryOrg = new Organizations();
    List<Organizations> organizationsList = this.organizationsService.query(queryOrg);
    TreeNodeList treeNodeList = new TreeNodeList();
    
    for (Organizations org : organizationsList) {
      TreeNode treeNode = new TreeNode(org.getId(), org.getName());
      if (org.getHasChild() != null && org.getHasChild().startsWith("Y")) {
        treeNode.setHasChild();
      }
      
      treeNode.setAttr("data", org);
      treeNode.setPId(org.getParentId());
      if (org.getId().equals("1")) {
        treeNode.setAttr("open", Boolean.valueOf(true));
      } else {
        treeNode.setAttr("open", Boolean.valueOf(false));
      } 
      treeNodeList.addTreeNode(treeNode.getAttr());
    } 

    
    return treeNodeList.getTreeNodeList();
  }


  
	@RequestMapping({ "/list" })
	public ModelAndView orgsTreeList() {
		return new ModelAndView("orgs/orgsList");
	}

	@RequestMapping(value = { "/pageresults" })
	@ResponseBody
	public JpaPageResults<Organizations> pageResults(@ModelAttribute("orgs") Organizations orgs) {
		return organizationsService.queryPageResults(orgs);

	}

  
  @RequestMapping({"/orgsSelect/{deptId}/{department}"})
  public ModelAndView orgsSelect(@PathVariable("deptId") String deptId, @PathVariable("department") String department) {
    ModelAndView modelAndView = new ModelAndView("orgs/orgsSelect");
    modelAndView.addObject("deptId", deptId);
    modelAndView.addObject("department", department);
    return modelAndView;
  }


	@RequestMapping(value = { "/forwardAdd" })
	public ModelAndView forwardAdd(@ModelAttribute("org") Organizations org) {
		ModelAndView modelAndView=new ModelAndView("/orgs/orgsAdd");
		org =organizationsService.get(org.getId());
		modelAndView.addObject("model",org);
		return modelAndView;
	}



  
  @ResponseBody
  @RequestMapping({"/add"})
  public Message insert(@ModelAttribute("org") Organizations org) {
    _logger.debug("-Add  :" + org);
    if (null == org.getId() || org.getId().equals("")) {
    	org.generateId();
    }
    
    if (this.organizationsService.insert(org)) {
      return new Message(WebContext.getI18nValue("message.action.insert.success"), MessageType.success);
    }
    
    return new Message(WebContext.getI18nValue("message.action.insert.success"), MessageType.error);
  }







  
  @ResponseBody
  @RequestMapping({"/query"})
  public Message query(@ModelAttribute("org") Organizations org) {
    _logger.debug("-query  :" + org);
    if (this.organizationsService.load(org) != null) {
      return new Message(WebContext.getI18nValue("message.action.insert.success"), MessageType.success);
    }
    
    return new Message(WebContext.getI18nValue("message.action.insert.error"), MessageType.error);
  }




	@RequestMapping(value = { "/forwardUpdate/{id}" })
	public ModelAndView forwardUpdate(@PathVariable("id") String id) {
		ModelAndView modelAndView=new ModelAndView("/orgs/orgsUpdate");
		Organizations org =organizationsService.get(id);
		
		modelAndView.addObject("model",org);
		return modelAndView;
	}


  
  @ResponseBody
  @RequestMapping({"/update"})
  public Message update(@ModelAttribute("org") Organizations org) {
    _logger.debug("-update  organization :" + org);
    if (this.organizationsService.update(org)) {
      return new Message(WebContext.getI18nValue("message.action.update.success"), MessageType.success);
    }
    
    return new Message(WebContext.getI18nValue("message.action.update.error"), MessageType.error);
  }







  @ResponseBody
  @RequestMapping({"/delete"})
  public Message delete(@ModelAttribute("org") Organizations org) {
    _logger.debug("-delete  organization :" + org);
    if (this.organizationsService.remove(org.getId())) {
      return new Message(WebContext.getI18nValue("message.action.delete.success"), MessageType.success);
    }
    
    return new Message(WebContext.getI18nValue("message.action.delete.success"), MessageType.error);
  }



  
  @RequestMapping({"/orgUsersList"})
  public ModelAndView orgUsersList() { return new ModelAndView("orgs/orgUsersList"); }







}
