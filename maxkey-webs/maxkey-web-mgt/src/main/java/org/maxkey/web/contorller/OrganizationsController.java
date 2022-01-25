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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.maxkey.constants.ConstsOperateMessage;
import org.maxkey.entity.ExcelImport;
import org.maxkey.entity.Organizations;
import org.maxkey.persistence.service.OrganizationsService;
import org.maxkey.util.ExcelUtils;
import org.maxkey.web.WebContext;
import org.maxkey.web.component.TreeNode;
import org.maxkey.web.component.TreeNodeList;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageScope;
import org.maxkey.web.message.MessageType;
import org.maxkey.web.message.OperateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;


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
    queryOrg.setInstId(WebContext.getUserInfo().getInstId());
    List<Organizations> organizationsList = this.organizationsService.queryOrgs(queryOrg);
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
		orgs.setInstId(WebContext.getUserInfo().getInstId());
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
    
    org.setInstId(WebContext.getUserInfo().getInstId());
    if (this.organizationsService.insert(org)) {
      return new Message(WebContext.getI18nValue("message.action.insert.success"), MessageType.success);
    }
    
    return new Message(WebContext.getI18nValue("message.action.insert.success"), MessageType.error);
  }

	@ResponseBody
	@RequestMapping({"/query"})
	public Message query(@ModelAttribute("org") Organizations org) {
		_logger.debug("-query  :" + org);
		org.setInstId(WebContext.getUserInfo().getInstId());
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
		org.setInstId(WebContext.getUserInfo().getInstId());
    	if (this.organizationsService.update(org)) {
    		return new Message(WebContext.getI18nValue("message.action.update.success"), MessageType.success);
    	}
    
    	return new Message(WebContext.getI18nValue("message.action.update.error"), MessageType.error);
	}

	@ResponseBody
	@RequestMapping({"/delete"})
	public Message delete(@ModelAttribute("org") Organizations org) {
		_logger.debug("-delete  organization :" + org);
    	if (this.organizationsService.deleteBatch(org.getId())) {
    		return new Message(WebContext.getI18nValue("message.action.delete.success"), MessageType.success);
    	}
    
    	return new Message(WebContext.getI18nValue("message.action.delete.success"), MessageType.error);
	}

  @RequestMapping({"/orgUsersList"})
  public ModelAndView orgUsersList() { return new ModelAndView("orgs/orgUsersList"); }

  @RequestMapping(value = "/import")
  public ModelAndView importing(@ModelAttribute("excelImportFile")ExcelImport excelImportFile)  {
      if (excelImportFile.isExcelNotEmpty() ) {
        try {
            List<Organizations> orgsList = Lists.newArrayList();
            Workbook workbook = excelImportFile.biuldWorkbook();
            int sheetSize = workbook.getNumberOfSheets();
            //遍历sheet页
            for (int i = 0; i < sheetSize; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                int rowSize = sheet.getLastRowNum() + 1;
                for (int j = 1; j < rowSize; j++) {//遍历行
                	Row row = sheet.getRow(j);
                    if (row == null || j <3 ) {//略过空行和前3行
                        continue;
                    } else {//其他行是数据行
                        orgsList.add(buildOrganizationsFromSheetRow(row));
                    }
                }
            }
            // 数据去重
            if(!CollectionUtils.isEmpty(orgsList)){
                orgsList = orgsList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))), ArrayList::new));
                if(organizationsService.insertBatch(orgsList)) {
		        	new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_SUCCESS), null, MessageType.success, OperateType.add, MessageScope.DB);
		        }else {
		        	new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_ERROR), MessageType.error);
		        }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	excelImportFile.closeWorkbook();
        }
	}else {
		new Message(WebContext.getI18nValue(ConstsOperateMessage.INSERT_ERROR), MessageType.error);
	}
      
	return new ModelAndView("/orgs/orgsImport");
  }

  public Organizations buildOrganizationsFromSheetRow(Row row) {
		Organizations organization = new Organizations();
		// 上级编码
		organization.setParentId(ExcelUtils.getValue(row, 0));
		// 上级名称
		organization.setParentName(ExcelUtils.getValue(row, 1));
		// 组织编码
		organization.setId(ExcelUtils.getValue(row, 2));
		// 组织名称
		organization.setName(ExcelUtils.getValue(row, 3));
		// 组织全称
		organization.setFullName(ExcelUtils.getValue(row, 4));
		// 编码路径
		organization.setCodePath(ExcelUtils.getValue(row, 5));
		// 名称路径
		organization.setNamePath(ExcelUtils.getValue(row, 6));
		// 组织类型
		organization.setType(ExcelUtils.getValue(row, 7));
		// 所属分支机构
		organization.setDivision(ExcelUtils.getValue(row, 8));
		// 级别
		String level = ExcelUtils.getValue(row, 9);
		organization.setLevel(level.equals("") ? 1 : Integer.parseInt(level));
		// 排序
		String sortIndex = ExcelUtils.getValue(row, 10);
		organization.setSortIndex(sortIndex.equals("") ? 1 : Integer.parseInt(sortIndex));
		// 联系人
		organization.setContact(ExcelUtils.getValue(row, 11));
		// 联系电话
		organization.setPhone(ExcelUtils.getValue(row, 12));
		// 邮箱
		organization.setEmail(ExcelUtils.getValue(row, 13));
		// 传真
		organization.setFax(ExcelUtils.getValue(row, 14));
		// 工作-国家
		organization.setCountry(ExcelUtils.getValue(row, 15));
		// 工作-省
		organization.setRegion(ExcelUtils.getValue(row, 16));
		// 工作-城市
		organization.setLocality(ExcelUtils.getValue(row, 17));
		// 工作-地址
		organization.setLocality(ExcelUtils.getValue(row, 18));
		// 邮编
		organization.setPostalCode(ExcelUtils.getValue(row, 19));
		// 详细描述
		organization.setDescription(ExcelUtils.getValue(row, 20));
		organization.setStatus(1);
		
		organization.setInstId(WebContext.getUserInfo().getInstId());
      return organization;
  }
}
