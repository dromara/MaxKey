package org.maxkey.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 获取请求参数标签
 * @author Crystal.Sea
 *
 */
public class ParameterTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9094347470856820500L;
	
	private PageContext pageContext;
	
	private String name;
	



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	public final int doStartTag() throws JspException{
		return SKIP_BODY;
	}
	
	public final int doEndTag() throws JspException{		
		int tagReturn=EVAL_PAGE;		
		try{
			if(this.getName()!=null&&!this.getName().equals("")){
				pageContext.getOut().print(this.pageContext.getRequest().getParameter(this.getName()));
				pageContext.getOut().flush();
			}
		} catch (IOException e){
			throw new JspException("exception="+e.getMessage());
		}
		return tagReturn;
		
	}

}
