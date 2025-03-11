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

package org.dromara.maxkey.entity;

import java.util.ArrayList;

/**
 * 数控件节点列表 列表的元素为TreeNode
 * 
 * @author Crystal.Sea
 *
 */
public class TreeAttributes {

	TreeNode rootNode;

	int nodeCount;

	ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();

	public ArrayList<TreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<TreeNode> nodes) {
		this.nodes = nodes;
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}

	/**
	 * 新增节点到列表
	 * 
	 * @param treeNode
	 */
	public void addNode(TreeNode treeNode) {
		this.nodes.add(treeNode);
	}

}
