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

import java.util.HashMap;

/**
 * 数控件的节点 使用HashMap<String,Object> attr存储节点数据.
 * 
 * @author Crystal.Sea
 *
 */
public class TreeNode {
    // TreeNode
    HashMap<String, Object> attr = new HashMap<String, Object>();

    public TreeNode() {
        super();
    }

    public TreeNode(String id, String name) {
        attr.put("id", id);
        attr.put("name", name);
    }

    public TreeNode(String id, String name, boolean hasChild) {
        attr.put("id", id);
        attr.put("name", name);
        attr.put("isParent", hasChild);
    }

    public TreeNode(String id, String name, String pId) {
        attr.put("id", id);
        attr.put("name", name);
        attr.put("pId", pId);
    }

    public TreeNode(String id, String name, String pId, String url) {
        attr.put("id", id);
        attr.put("name", name);
        attr.put("pId", pId);
        attr.put("url", url);
    }

    public TreeNode(String id, String name, String pId, String url, String target) {
        attr.put("id", id);
        attr.put("name", name);
        attr.put("pId", pId);
        attr.put("url", url);
        attr.put("target", target);
    }

    public void setChecked() {
        attr.put("checked", true);
    }

    public void setHasChild() {
        attr.put("isParent", true);
    }

    public void setPId(String pId) {
        attr.put("pId", pId);
    }

    public void setIcon(String icon) {
        attr.put("icon", icon);
    }

    public HashMap<String, Object> getAttr() {
        return attr;
    }

    public void setAttr(String attrName, Object value) {
        this.attr.put(attrName, value);
    }

    public void setAttr(HashMap<String, Object> attr) {
        this.attr = attr;
    }

}
