package org.maxkey.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 浏览器版本标签
 * 根据浏览器版本包含html
 * @author Crystal.Sea
 *
 */
public class BrowserTag extends BodyTagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2336696641611234784L;
	
	private PageContext pageContext;
	
	String version;
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public int doStartTag() throws JspException {
		
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		String userAgent = request.getHeader("User-Agent");
		try {
			pageContext.getOut().print("<!--<div style='display:none'>"+userAgent+"</div>-->");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(userAgent.indexOf(version)>0){
			return EVAL_BODY_INCLUDE;
		}
		
		return SKIP_BODY;
		
	}
}
