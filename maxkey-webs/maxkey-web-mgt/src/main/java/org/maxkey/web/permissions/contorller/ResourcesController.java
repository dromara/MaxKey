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
 

package org.maxkey.web.permissions.contorller;

import java.util.List;

import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.maxkey.authn.annotation.CurrentUser;
import org.maxkey.entity.Message;
import org.maxkey.entity.Resources;
import org.maxkey.entity.UserInfo;
import org.maxkey.persistence.service.ResourcesService;
import org.maxkey.web.component.TreeAttributes;
import org.maxkey.web.component.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value={"/permissions/resources"})
public class ResourcesController {
	final static Logger _logger = LoggerFactory.getLogger(ResourcesController.class);
	
	@Autowired
	ResourcesService resourcesService;

	@RequestMapping(value = { "/fetch" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<?> fetch(@ModelAttribute Resources resource,@CurrentUser UserInfo currentUser) {
		_logger.debug("fetch {}" , resource);
		resource.setInstId(currentUser.getInstId());
		return new Message<JpaPageResults<Resources>>(
				resourcesService.queryPageResults(resource)).buildResponse();
	}

	@ResponseBody
	@RequestMapping(value={"/query"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> query(@ModelAttribute Resources resource,@CurrentUser UserInfo currentUser) {
		_logger.debug("-query  {}" , resource);
		resource.setInstId(currentUser.getInstId());
		List<Resources>  resourceList = resourcesService.query(resource);
		if (resourceList != null) {
			 return new Message<List<Resources>>(Message.SUCCESS,resourceList).buildResponse();
		} else {
			 return new Message<List<Resources>>(Message.FAIL).buildResponse();
		}
	}
	
	@RequestMapping(value = { "/get/{id}" }, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		Resources resource=resourcesService.get(id);
		return new Message<Resources>(resource).buildResponse();
	}
	
	@ResponseBody
	@RequestMapping(value={"/add"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> insert(@RequestBody Resources resource,@CurrentUser UserInfo currentUser) {
		_logger.debug("-Add  :" + resource);
		resource.setInstId(currentUser.getInstId());
		if (resourcesService.insert(resource)) {
			return new Message<Resources>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Resources>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/update"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> update(@RequestBody  Resources resource,@CurrentUser UserInfo currentUser) {
		_logger.debug("-update  :" + resource);
		resource.setInstId(currentUser.getInstId());
		if (resourcesService.update(resource)) {
		    return new Message<Resources>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Resources>(Message.FAIL).buildResponse();
		}
	}
	
	@ResponseBody
	@RequestMapping(value={"/delete"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> delete(@RequestParam("ids") String ids,@CurrentUser UserInfo currentUser) {
		_logger.debug("-delete  ids : {} " , ids);
		if (resourcesService.deleteBatch(ids)) {
			 return new Message<Resources>(Message.SUCCESS).buildResponse();
		} else {
			return new Message<Resources>(Message.FAIL).buildResponse();
		}
	}
  
  
	@ResponseBody
	@RequestMapping(value={"/tree"}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> tree(@ModelAttribute Resources resource,@CurrentUser UserInfo currentUser) {
		_logger.debug("-query  {}" , resource);
		resource.setInstId(currentUser.getInstId());
		List<Resources>  resourceList = resourcesService.query(resource);
		if (resourceList != null) {
			TreeAttributes treeAttributes = new TreeAttributes();
			int nodeCount = 0;
			for (Resources r : resourceList) {
				TreeNode treeNode = new TreeNode(r.getId(),r.getName());
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
			 return new Message<TreeAttributes>(Message.SUCCESS,treeAttributes).buildResponse();
		} else {
			 return new Message<TreeAttributes>(Message.FAIL).buildResponse();
		}
	}
	

}
