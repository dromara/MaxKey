package org.maxkey.web.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.maxkey.web.WebContext;
import org.maxkey.web.message.Message;
import org.maxkey.web.message.MessageScope;

/**
 * message提示标签
 * 需要和后台联合使用
 * @author Crystal.Sea
 *
 */
public class MessageTag extends BodyTagSupport
{
	private static final long serialVersionUID = 4494502315875570815L;
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
			Message message = WebContext.getMessage();
			if(message!=null){
				printErrorMessage(message.getMessage());
				if(message.getMessageScope()==MessageScope.CLIENT||message.getMessageScope()==MessageScope.DB_CLIENT){
					WebContext.clearMessage();
				}
			}
		}catch(Exception e){
			throw new JspException("exception="+e.getMessage());
		}
		return tagReturn;
		
	}
	
	private void printErrorMessage(String message) throws IOException{
		if(message!=null&&!message.equals("")){
			StringBuffer htmlBuffer=new StringBuffer();
			htmlBuffer.append("<script language='javascript'>\n");
			htmlBuffer.append("	$(function(){\n");
			htmlBuffer.append("		$.alert({content:'"+message+"',type:'error'});\n");
			//$.alert({content:data.message,type:"error"});
			htmlBuffer.append("	});\n");
			htmlBuffer.append("</script>\n");
			
			pageContext.getOut().println(htmlBuffer.toString());
			pageContext.getOut().flush();
		}
	}
	public void realse(){
		pageContext=null;
	}	
}
