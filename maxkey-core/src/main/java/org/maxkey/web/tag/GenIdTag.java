package org.maxkey.web.tag;

import java.util.UUID;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * 主键或者唯一编号生成标签
 * @author Crystal.Sea
 *
 */
public class GenIdTag extends BodyTagSupport
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
		try{
			pageContext.getOut().print(UUID.randomUUID().toString().toLowerCase());
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
