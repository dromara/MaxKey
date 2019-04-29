package org.maxkey.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.maxkey.web.WebContext;


/**
 * 本地语言标签
 * 国际化使用
 * @author Crystal.Sea
 *
 */
public class LocaleTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3906613920420893358L;
	
	private PageContext pageContext;
	
	private String code;
	
	

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
			if(null==code){
				pageContext.getOut().print(WebContext.getRequestLocale());
			}else{
				String localeText=WebContext.getI18nValue(code);
				if(localeText==null||localeText.equals("")){
					String []localPath=code.split("\\.");
					localeText=localPath[(localPath.length>1?localPath.length-1:0)];
					localeText=localeText.toUpperCase().charAt(0)+localeText.substring(1);
				}
				pageContext.getOut().print(localeText);
			}
			
			pageContext.getOut().flush();
		} catch (IOException e){
			throw new JspException("exception="+e.getMessage());
		}
		return tagReturn;
		
	}

}
