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
 

package org.maxkey.web.component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 数控件节点列表
 * 列表的元素为HashMap<String,Object>
 * 
 * @author Crystal.Sea
 *
 */
public class TreeNodeList {
	
	ArrayList<HashMap<String,Object>> treeNodeList=new ArrayList<HashMap<String,Object>>();

	/**
	 * 获取列表
	 * @return treeNodeList
	 */
	public ArrayList<HashMap<String, Object>> getTreeNodeList() {
		return treeNodeList;
	}

	/**
	 * 设置节点列表
	 * @param treeNodeList
	 */
	public void setTreeNodeList(ArrayList<HashMap<String, Object>> treeNodeList) {
		this.treeNodeList = treeNodeList;
	}

	/**
	 * 新增节点到列表
	 * @param treeNode
	 */
	public void addTreeNode(HashMap<String, Object> treeNode) {
		this.treeNodeList .add(treeNode);
	}
	
}
