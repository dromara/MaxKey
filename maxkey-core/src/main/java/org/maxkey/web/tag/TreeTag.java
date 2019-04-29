package org.maxkey.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TreeTag extends TagSupport {
	final static Logger _logger = LoggerFactory.getLogger(TreeTag.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1243669057069893859L;
	
	
	private String id;
	
	private String url;
	
	private String  rootId;
	
	private boolean checkbox;
	
	private int width=300;
	
	private int height;
	
	private String onClick="null";
	
	private String onDblClick="null";
	
	private PageContext pageContext;


	
	
	@Override
	public int doEndTag() throws JspException {

		return super.doEndTag();
	}


	@Override
	
	public int doStartTag() throws JspException {
		try{	
			StringBuffer sb=new StringBuffer("");
			sb.append("\n<script type=\"text/javascript\">\n<!--");
			sb.append("\nvar "+id+"_treeSettings={");
			sb.append("\n\t element  :  \""+id+"\",");
			sb.append("\n\t rootId  :  \""+rootId+"\",");
			sb.append("\n\t checkbox  :  "+checkbox+",");
			sb.append("\n\t onClick  :  "+onClick+",");
			sb.append("\n\t onDblClick  :  "+onDblClick+",");
			sb.append("\n\t url  :  '").append(pageContext.getServletContext().getContextPath()).append(url).append("',");
			
			sb.append("\n};");
			sb.append("\n$(function () {");
			sb.append("\n\t $.tree(").append(id).append("_treeSettings);");
			sb.append("\n});");
			
			sb.append("\n-->\n</script>");
			
			sb.append("\n<div id=\"").append(id).append("\" class=\"ztree\"") ;
			sb.append(" style=\"height:"+(height == 0? "100%"  : (height+"px"))+";width:"+width+"px\"").append("></div>");
			
			pageContext.getOut().println(sb);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return EVAL_BODY_INCLUDE;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getRootId() {
		return rootId;
	}


	public void setRootId(String rootId) {
		this.rootId = rootId;
	}


	public boolean isCheckbox() {
		return checkbox;
	}


	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public String getOnClick() {
		return onClick;
	}


	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}


	public String getOnDblClick() {
		return onDblClick;
	}


	public void setOnDblClick(String onDblClick) {
		this.onDblClick = onDblClick;
	}


	public PageContext getPageContext() {
		return pageContext;
	}
	
}
