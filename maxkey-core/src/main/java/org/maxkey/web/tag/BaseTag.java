package org.maxkey.web.tag;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 *  获取应用上下文标签
 * @author Crystal.Sea
 *
 */
public class BaseTag extends BodyTagSupport
{
	private static final long serialVersionUID = 4494502315876572711L;
	
	private PageContext pageContext;
	
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	
	public final int doStartTag() throws JspException{
		return SKIP_BODY;
	}
	
	public final int doEndTag() throws JspException{
		int tagReturn=EVAL_PAGE;
		String base="";

		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		base=request.getContextPath();
		
		try{
			pageContext.getOut().print(base);
			pageContext.getOut().flush();
		}catch(Exception e){
			throw new JspException("exception="+e.getMessage());
		}
		return tagReturn;
		
	}
	
	public void realse(){
		pageContext=null;
	}	
}
