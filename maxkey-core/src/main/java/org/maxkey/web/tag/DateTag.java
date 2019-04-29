package org.maxkey.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.maxkey.util.DateUtils;

public class DateTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2223298103020379292L;
	private PageContext pageContext;
	private String format;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	
	public final int doStartTag() throws JspException{
	
		return SKIP_BODY;
	}
	
	
	public final int doEndTag() throws JspException{		
		int tagReturn=EVAL_PAGE;	
		try {
//			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();	
			if (format != null && !format.equals("")) {
				pageContext.getOut().print(DateUtils.getCurrentDateAsString(format));
			}else{
				pageContext.getOut().print(DateUtils.getCurrentDateAsString(DateUtils.FORMAT_DATE_YYYY_MM_DD));
			}
			pageContext.getOut().flush();
		} catch (Exception e) {
			throw new JspException("exception="+e.getMessage());
			// TODO: handle exception
		}
		return tagReturn;
	}
	public void realse(){
		pageContext=null;
	}
}
