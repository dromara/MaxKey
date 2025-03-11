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
 

package org.dromara.maxkey.web.permissions.contorller;

import java.util.List;

import org.dromara.maxkey.authn.annotation.CurrentUser;
import org.dromara.maxkey.constants.ConstsEntryType;
import org.dromara.maxkey.constants.ConstsAct;
import org.dromara.maxkey.constants.ConstsActResult;
import org.dromara.maxkey.entity.Message;
import org.dromara.maxkey.entity.TreeAttributes;
import org.dromara.maxkey.entity.TreeNode;
import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.entity.permissions.Resources;
import org.dromara.maxkey.persistence.service.HistorySystemLogsService;
import org.dromara.maxkey.persistence.service.ResourcesService;
import org.dromara.mybatis.jpa.entity.JpaPageResults;
import org.dromara.mybatis.jpa.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value={"/permissions/resources"})
public class ResourcesController {
	static final  Logger logger = LoggerFactory.getLogger(ResourcesController.class);
	
	@Autowired
	ResourcesService resourcesService;
	
	@Autowired
	HistorySystemLogsService systemLog;

	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Message<?> fetch(@ModelAttribute Resources resource,@CurrentUser UserInfo currentUser) {
		logger.debug("fetch {}" , resource);
		resource.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Resources>>(
				resourcesService.fetchPageResults(resource));
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> query(@ModelAttribute Resources resource,@CurrentUser UserInfo currentUser) {
		logger.debug("-query  {}" , resource);
		resource.setInstId(currentUser.getInstId());
		List<Resources>  resourceList = resourcesService.query(resource);
		if (resourceList != null) {
			 return new Message<List<Resources>>(Message.SUCCESS,resourceList);
		} else {
			 return new Message<List<Resources>>(Message.FAIL);
		}
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> get(@PathVariable("id") String id) {
		Resources resource=resourcesService.get(id);
		return new Message<Resources>(resource);
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> insert(@RequestBody Resources resource,@CurrentUser UserInfo currentUser) {
		logger.debug("-Add  :" + resource);
		resource.setInstId(currentUser.getInstId());
		if (resourcesService.insert(resource)) {
			systemLog.insert(
					ConstsEntryType.RESOURCE, 
					resource, 
					ConstsAct.CREATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
			return new Message<Resources>(Message.SUCCESS);
		} else {
			return new Message<Resources>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> update(@RequestBody  Resources resource,@CurrentUser UserInfo currentUser) {
		logger.debug("-update  :" + resource);
		resource.setInstId(currentUser.getInstId());
		if (resourcesService.update(resource)) {
			systemLog.insert(
					ConstsEntryType.RESOURCE, 
					resource, 
					ConstsAct.UPDATE, 
					ConstsActResult.SUCCESS, 
					currentUser);
		    return new Message<Resources>(Message.SUCCESS);
		} else {
			return new Message<Resources>(Message.FAIL);
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> delete(@RequestParam("ids") List<String> ids,@CurrentUser UserInfo currentUser) {
		logger.debug("-delete  ids : {} " , ids);
		if (resourcesService.deleteBatch(ids)) {
			systemLog.insert(
					ConstsEntryType.RESOURCE, 
					ids, 
					ConstsAct.DELETE, 
					ConstsActResult.SUCCESS, 
					currentUser);
			 return new Message<Resources>(Message.SUCCESS);
		} else {
			return new Message<Resources>(Message.FAIL);
		}
	}
  
  
	@ResponseBody
	@RequestMapping(value={"/tree"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message<?> tree(@ModelAttribute Resources resource,@CurrentUser UserInfo currentUser) {
		logger.debug("-tree  {}" , resource);
		List<Resources>  resourceList = resourcesService.query(Query.builder().eq("instid", currentUser.getInstId()));
		if (resourceList != null) {
			TreeAttributes treeAttributes = new TreeAttributes();
			int nodeCount = 0;
			for (Resources r : resourceList) {
				TreeNode treeNode = new TreeNode(r.getId(),r.getResourceName());
				treeNode.setParentKey(r.getParentId());
				treeNode.setParentTitle(r.getParentName());
				treeNode.setAttrs(r);
				treeNode.setLeaf(true);
				treeAttributes.addNode(treeNode);
				nodeCount ++;
				if(r.getId().equalsIgnoreCase(currentUser.getInstId())) {
					treeNode.setExpanded(true);
					treeNode.setLeaf(false);
					treeAttributes.setRootNode(treeNode);
				}
			}
			
			TreeNode rootNode = new TreeNode(resource.getAppId(),resource.getAppName());
			rootNode.setParentKey(resource.getAppId());
			rootNode.setExpanded(true);
			rootNode.setLeaf(false);
			treeAttributes.setRootNode(rootNode);
			
			treeAttributes.setNodeCount(nodeCount);
			 return new Message<TreeAttributes>(Message.SUCCESS,treeAttributes);
		} else {
			 return new Message<TreeAttributes>(Message.FAIL);
		}
	}
	

}
