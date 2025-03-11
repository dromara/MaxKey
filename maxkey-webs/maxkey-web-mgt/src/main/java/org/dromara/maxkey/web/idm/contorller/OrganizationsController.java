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
 

package org.dromara.maxkey.web.idm.contorller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsEntryType;
import org.dromara.maxkey.constants.ConstsAct;
import org.dromara.maxkey.constants.ConstsActResult;
import org.dromara.maxkey.entity.ExcelImport;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.TreeAttributes;
import org.dromara.maxkey.entity.TreeNode;
import org.dromara.maxkey.entity.idm.Organizations;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.OrganizationsService;
import org.dromara.maxkey.util.ExcelUtils;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.dromara.mybatis.jpa.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;


@RestController
@RequestMapping({"/orgs"})
public class OrganizationsController {
  static final Logger logger = LoggerFactory.getLogger(OrganizationsController.class);

	@Autowired
	OrganizationsService organizationsService;
	
	@Autowired
	HistorySystemLogsService systemLog;

	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Message<?> fetch(@ModelAttribute Organizations org,@CurrentUser UserInfo currentUser) {
		logger.debug("fetch {}" , org);
		org.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Organizations>>(
				organizationsService.fetchPageResults(org));
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> query(@ModelAttribute Organizations org,@CurrentUser UserInfo currentUser) {
		logger.debug("-query  {}" , org);
		org.setInstId(currentUser.getInstId());
		List<Organizations>  orgList = organizationsService.query(org);
		if (orgList != null) {
			 return new Message<List<Organizations>>(Message.SUCCESS,orgList);
		} else {
			 return new Message<List<Organizations>>(Message.FAIL);
		}
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> get(@PathVariable("id") String id) {
		Organizations org=organizationsService.get(id);
		return new Message<Organizations>(org);
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> insert(@RequestBody Organizations org,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  : {}" , org);
		org.setInstId(currentUser.getInstId());
		if (organizationsService.insert(org)) {
			systemLog.insert(
					ConstsEntryType.ORGANIZATION, 
					org, 
					ConstsAct.CREATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
			return new Message<Organizations>(Message.SUCCESS);
		} else {
			return new Message<Organizations>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> update(@RequestBody  Organizations org,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  :{}" , org);
		org.setInstId(currentUser.getInstId());
		if (organizationsService.update(org)) {
			systemLog.insert(
					ConstsEntryType.ORGANIZATION, 
					org, 
					ConstsAct.UPDATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
		    return new Message<Organizations>(Message.SUCCESS);
		} else {
			return new Message<Organizations>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (organizationsService.deleteBatch(ids)) {
			systemLog.insert(
					ConstsEntryType.ORGANIZATION, 
					ids, 
					ConstsAct.DELETE, 
					ConstsActResult.SUCCESS, 
					currentUser);
			 return new Message<Organizations>(Message.SUCCESS);
		} else {
			return new Message<Organizations>(Message.FAIL);
		}
	}
  
  
	@ResponseBody
	@RequestMapping(value={"/tree"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> tree(@ModelAttribute Organizations organization,@CurrentUser UserInfo currentUser) {
		logger.debug("-tree  {}" , organization);
		List<Organizations>  orgList = organizationsService.query(
				Query.builder().eq("instid", currentUser.getInstId()));
		if (orgList != null) {
			TreeAttributes treeAttributes = new TreeAttributes();
			int nodeCount = 0;
			for (Organizations org : orgList) {
				TreeNode treeNode = new TreeNode(org.getId(),org.getOrgName());
				treeNode.setCode(org.getOrgCode());
				treeNode.setCodePath(org.getCodePath());
				treeNode.setNamePath(org.getNamePath());
				treeNode.setParentKey(org.getParentId());
				treeNode.setParentTitle(org.getParentName());
				treeNode.setParentCode(org.getParentCode());
				treeNode.setAttrs(org);
				treeNode.setLeaf(true);
				treeAttributes.addNode(treeNode);
				nodeCount ++;
				//root organization node,parentId is null or parentId = -1 or parentId = 0 or  id = instId or id = parentId 
				if(org.getParentId() == null 
						||org.getParentId().equalsIgnoreCase("0")
						||org.getParentId().equalsIgnoreCase("-1")
						||org.getId().equalsIgnoreCase(currentUser.getInstId())
						||org.getId().equalsIgnoreCase(org.getParentId())
						) {
					treeNode.setExpanded(true);
					treeNode.setLeaf(false);
					treeAttributes.setRootNode(treeNode);
				}
			}
			treeAttributes.setNodeCount(nodeCount);
			 return new Message<TreeAttributes>(Message.SUCCESS,treeAttributes);
		} else {
			 return new Message<TreeAttributes>(Message.FAIL);
		}
	}

  @RequestMapping(value = "/import")
  public Message<?> importingOrganizations(
		  @ModelAttribute("excelImportFile")ExcelImport excelImportFile,
		  @CurrentUser UserInfo currentUser)  {
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
                        orgsList.add(buildOrganizationsFromSheetRow(row,currentUser));
                    }
                }
            }
            // 数据去重
            if(!CollectionUtils.isEmpty(orgsList)){
                orgsList = orgsList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))), ArrayList::new));
                if(organizationsService.insertBatch(orgsList)) {
                	return new Message<Organizations>(Message.SUCCESS);
		        }else {
		        	return new Message<Organizations>(Message.FAIL);
		        }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	excelImportFile.closeWorkbook();
        }
	}
      
	return new Message<Organizations>(Message.FAIL);
      
  }

  public Organizations buildOrganizationsFromSheetRow(Row row,UserInfo currentUser) {
		Organizations organization = new Organizations();
		// 上级编码
		organization.setParentId(ExcelUtils.getValue(row, 0));
		// 上级名称
		organization.setParentName(ExcelUtils.getValue(row, 1));
		// 组织编码
		organization.setId(ExcelUtils.getValue(row, 2));
		// 组织名称
		organization.setOrgName(ExcelUtils.getValue(row, 3));
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
		
		organization.setInstId(currentUser.getInstId());
      return organization;
  }
}
