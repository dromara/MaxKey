package org.maxkey.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.maxkey.domain.UserInfo;
import org.maxkey.web.WebContext;

/**
 * 获取当前登录用户标签
 * @author Crystal.Sea
 *
 */
public class UsernameTag extends TagSupport
{
	private PageContext pageContext;
	private static final long serialVersionUID = 4494502315876572711L;
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
 
	public final int doStartTag() throws JspException{
		return SKIP_BODY;
	}
	
	public final int doEndTag() throws JspException{		
		int tagReturn=EVAL_PAGE;		
		try
		{
			UserInfo userInfo=WebContext.getUserInfo();
			if(userInfo!=null&&!userInfo.getUsername().equals("")){
				pageContext.getOut().print(userInfo.getUsername());
				pageContext.getOut().flush();
			}
		} catch (IOException e)
		{
			throw new JspException("exception="+e.getMessage());
		}
		return tagReturn;
		
	}
	
}
