package org.maxkey.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * 获取请求地址及应用上下文标签
 * get Http Context full Path,if port equals 80 is omitted
 * @return String
 * eg:http://192.168.1.20:9080/webcontext or http://www.website.com/webcontext
 * @author Crystal.Sea
 *
 */
public class BasePathTag extends BodyTagSupport{
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
		String basePath="";
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		basePath = request.getScheme()+"://"+request.getServerName();
		int port=request.getServerPort();
		if(port==443 && request.getScheme().equalsIgnoreCase("https")){
			
		}else if(port==80 && request.getScheme().equalsIgnoreCase("http")){
			
		}else{
			basePath	+=	":"+port;
		}
		basePath += request.getContextPath()+"";

		try{
			pageContext.getOut().print(basePath);
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
