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
 

/**
 * 
 */
package org.maxkey.domain;

import java.util.ArrayList;

import org.apache.mybatis.jpa.persistence.JpaBaseDomain;



/**
 * 
 * @author Crystal.Sea
 *
 */
public class Navigations extends JpaBaseDomain  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9015541177872583924L;
	
	String id;
	private String name;
	private String url;
	private String type;
	private String target;
	private String pId;
	private String pName;
	private String xPath;
	private String hasChild;
	private int  visible;
	
	
	private ArrayList<Navigations> childNavs;
	
	
	public Navigations() {
		
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public Navigations(String id, String name, String url) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getxPath() {
		return xPath;
	}

	public void setxPath(String xPath) {
		this.xPath = xPath;
	}

	public String getHasChild() {
		return hasChild;
	}

	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}


	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}



	public ArrayList<Navigations> getChildNavs() {
		return childNavs;
	}

	public void setChildNavs(ArrayList<Navigations> childNavs) {
		this.childNavs = childNavs;
	}

	@Override
	public String toString() {
		return "Navigations [name=" + name + ", url=" + url + ", type=" + type
				+ ", target=" + target + ", pId=" + pId + ", pName=" + pName
				+ ", xPath=" + xPath + ", hasChild=" + hasChild
				+", visible=" + visible
				+ ", childNavs=" + childNavs + "]";
	}



}
