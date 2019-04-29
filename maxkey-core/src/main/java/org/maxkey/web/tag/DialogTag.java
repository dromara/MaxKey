package org.maxkey.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.maxkey.web.WebContext;

public class DialogTag  extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3872210456717811884L;

	
	private String url;
	
	private String title;
	
	private int width=300;
	
	private int height=400;
	
	private String text;

	private PageContext pageContext;
	
	
	@Override
	public int doStartTag() throws JspException {
		
		String i18nTitle=WebContext.getI18nValue(title);
		if(i18nTitle	!= 	null){
			title	=	i18nTitle;
		}
		
		String i18nText=WebContext.getI18nValue(text);
		if(i18nText	!= 	null){
			text	=	i18nText;
		}
		
		try {
			pageContext.getOut().println("<input  class=\"window button\" type=\"button\"  value=\""+text+"\"  title=\""+title+"\" ");
			pageContext.getOut().println("wurl=\""+pageContext.getServletContext().getContextPath()+url+"\" wwidth=\""+width+"\"  wheight=\""+height+"\" />" );
			return SKIP_BODY;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return super.doStartTag();
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	


}
