package org.maxkey.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 获取访问地址url参数标签
 * @author Crystal.Sea
 *
 */
public class PathVariableTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3906613920420893358L;
	
	private PageContext pageContext;
	
	private int index;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	public final int doStartTag() throws JspException{
		return SKIP_BODY;
	}
	
	public final int doEndTag() throws JspException{		
		int tagReturn=EVAL_PAGE;
		String pathVariable="";
		try
		{
			HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
			String[] pathVariables=request.getAttribute(org.springframework.web.util.WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE).toString().split("/");
			
			if(pathVariables==null){
				pathVariables=request.getRequestURI().split("/");
			}
			
			if(this.getIndex()==0){
				pathVariable=pathVariables[pathVariables.length-1];
			}else{
				pathVariable=pathVariables[this.getIndex()+1];
			}
			
			pageContext.getOut().print(pathVariable);
			pageContext.getOut().flush();
		} catch (IOException e)
		{
			throw new JspException("exception="+e.getMessage());
		}
		return tagReturn;
		
	}

}
