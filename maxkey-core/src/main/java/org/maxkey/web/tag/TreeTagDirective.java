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
 

package org.maxkey.web.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
    *   获取应用上下文标签
 *   <@base/>
 * @author Crystal.Sea
 *
 */

@FreemarkerTag("tree")
public class TreeTagDirective implements TemplateDirectiveModel {
	@Autowired
    private HttpServletRequest request;
	
	private String id;
	
	private String url;
	
	private String  rootId;
	
	private String checkbox;
	
	private int width=300;
	
	private int height;
	
	private String onClick="null";
	
	private String onDblClick="null";
	

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		 id = params.get("id").toString();
		 url = params.get("url").toString();
		 rootId = params.get("rootId").toString();
		 checkbox = params.get("checkbox").toString();
		 if(params.get("width")!=null) {
				width=Integer.parseInt(params.get("width").toString());
			}
		 if(params.get("height")!=null) {
			height=Integer.parseInt(params.get("height").toString());
		 }
		 if(params.get("onClick")!=null) {
			 onClick = params.get("onClick").toString();
		 }
		 if(params.get("onDblClick")!=null) {
			 onDblClick = params.get("onDblClick").toString();
		 }
		
		try{	
			StringBuffer sb=new StringBuffer("");
			sb.append("\n<script type=\"text/javascript\">\n<!--");
			sb.append("\nvar "+id+"_treeSettings={");
			sb.append("\n\t element  :  \""+id+"\",");
			sb.append("\n\t rootId  :  \""+rootId+"\",");
			sb.append("\n\t checkbox  :  "+checkbox+",");
			sb.append("\n\t onClick  :  "+onClick+",");
			sb.append("\n\t onDblClick  :  "+onDblClick+",");
			sb.append("\n\t url  :  '").append(request.getContextPath()).append(url).append("',");
			
			sb.append("\n};");
			sb.append("\n$(function () {");
			sb.append("\n\t $.tree(").append(id).append("_treeSettings);");
			sb.append("\n});");
			
			sb.append("\n-->\n</script>");
			
			sb.append("\n<div id=\"").append(id).append("\" class=\"ztree\"") ;
			sb.append(" style=\"height:"+(height == 0? "100%"  : (height+"px"))+";width:"+width+"px\"").append("></div>");
			
			env.getOut().append(sb);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

}
