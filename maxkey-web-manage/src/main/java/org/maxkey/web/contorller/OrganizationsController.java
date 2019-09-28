package org.maxkey.web.contorller;

import java.util.HashMap;
import java.util.List;

import org.maxkey.dao.service.OrganizationsService;
import org.maxkey.domain.Organizations;
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
public class OrganizationsController{
  static final Logger _logger = LoggerFactory.getLogger(OrganizationsController.class);

  
  @Autowired
  OrganizationsService organizationsService;

  
  @ResponseBody
  @RequestMapping({"/tree"})
  public List<HashMap<String, Object>> organizationsTree(@RequestParam(value = "id", required = false) String id) {
    _logger.debug("organizationsTree id :" + id);
    Organizations org = new Organizations();
    List<Organizations> organizationsList = this.organizationsService.query(org);
    
    Organizations rootOrganization = new Organizations();
    rootOrganization.setId("1");
    rootOrganization.setName("");
    rootOrganization.setFullName("");
    rootOrganization.setpName("Root");
    rootOrganization.setxPath("/1");
    rootOrganization.setxNamePath("/" );
    rootOrganization.setpId("-1");
    
    TreeNodeList treeNodeList = new TreeNodeList();
    TreeNode rootTreeNode = new TreeNode("1", "");
    rootTreeNode.setAttr("data", rootOrganization);
    rootTreeNode.setPId(rootOrganization.getpId());
    rootTreeNode.setAttr("open", Boolean.valueOf(true));
    treeNodeList.addTreeNode(rootTreeNode.getAttr());
    
    for (Organizations organization : organizationsList) {
      TreeNode treeNode = new TreeNode(organization.getId(), organization.getName());
      if (organization.getHasChild() != null && organization.getHasChild().equals(Character.valueOf('Y'))) {
        treeNode.setHasChild();
      }

      
      treeNode.setAttr("data", organization);
      treeNode.setPId(organization.getpId());
      if (organization.getId().equals("1")) {
        treeNode.setAttr("open", Boolean.valueOf(true));
      } else {
        treeNode.setAttr("open", Boolean.valueOf(false));
      } 
      treeNodeList.addTreeNode(treeNode.getAttr());
    } 

    
    return treeNodeList.getTreeNodeList();
  }


  
  @RequestMapping({"/list"})
  public ModelAndView orgsTreeList() { return new ModelAndView("orgs/orgsList"); }



  
  @RequestMapping({"/orgsSelect/{deptId}/{department}"})
  public ModelAndView orgsSelect(@PathVariable("deptId") String deptId, @PathVariable("department") String department) {
    ModelAndView modelAndView = new ModelAndView("orgs/orgsSelect");
    modelAndView.addObject("deptId", deptId);
    modelAndView.addObject("department", department);
    return modelAndView;
  }






  
  @ResponseBody
  @RequestMapping({"/add"})
  public Message insert(@ModelAttribute("organization") Organizations organization) {
    _logger.debug("-Add  :" + organization);
    if (null == organization.getId() || organization.getId().equals("")) {
      organization.generateId();
    }
    
    if (this.organizationsService.insert(organization)) {
      return new Message(WebContext.getI18nValue("message.action.insert.success"), MessageType.success);
    }
    
    return new Message(WebContext.getI18nValue("message.action.insert.success"), MessageType.error);
  }







  
  @ResponseBody
  @RequestMapping({"/query"})
  public Message query(@ModelAttribute("organization") Organizations organization) {
    _logger.debug("-query  :" + organization);
    if (this.organizationsService.load(organization) != null) {
      return new Message(WebContext.getI18nValue("message.action.insert.success"), MessageType.success);
    }
    
    return new Message(WebContext.getI18nValue("message.action.insert.error"), MessageType.error);
  }







  
  @ResponseBody
  @RequestMapping({"/update"})
  public Message update(@ModelAttribute("organization") Organizations organization) {
    _logger.debug("-update  organization :" + organization);
    if (this.organizationsService.update(organization)) {
      return new Message(WebContext.getI18nValue("message.action.update.success"), MessageType.success);
    }
    
    return new Message(WebContext.getI18nValue("message.action.update.error"), MessageType.error);
  }







  
  @ResponseBody
  @RequestMapping({"/delete"})
  public Message delete(@ModelAttribute("organization") Organizations organization) {
    _logger.debug("-delete  organization :" + organization);
    if (this.organizationsService.delete(organization)) {
      return new Message(WebContext.getI18nValue("message.action.delete.success"), MessageType.success);
    }
    
    return new Message(WebContext.getI18nValue("message.action.delete.success"), MessageType.error);
  }



  
  @RequestMapping({"/orgUsersList"})
  public ModelAndView orgUsersList() { return new ModelAndView("orgs/orgUsersList"); }
}
