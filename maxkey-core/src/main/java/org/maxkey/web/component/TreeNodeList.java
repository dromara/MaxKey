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
