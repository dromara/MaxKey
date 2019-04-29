package org.maxkey.web.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.maxkey.web.WebContext;

public class ColumnTag  extends TagSupport {

	private String field;
	
	private String title;
	
	private int width;
	
	private boolean hidden;
	
	private boolean sortable;
	
	private String formatter;
	
	private PageContext pageContext;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5581918481537828383L;

	
	

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		Tag  pTag =this.getParent();
		if(pTag.getClass().getSimpleName().equalsIgnoreCase("GridTag")){
			List<GridColumn> listGridColumn=(List<GridColumn>)pageContext.getAttribute(GridTag.GridTagAttribute);
			String i18nTitle=WebContext.getI18nValue(title);
			if(i18nTitle	!= 	null){
				title	=	i18nTitle;
			}
			GridColumn gridColumn=new GridColumn(field,title,width,hidden,sortable,formatter);
			
			if(listGridColumn	==	null){
				listGridColumn=new ArrayList<GridColumn>();
			}
			
			listGridColumn.add(gridColumn);
			pageContext.setAttribute(GridTag.GridTagAttribute, listGridColumn);
			
		}else{
			return SKIP_BODY;
		}
		return super.doStartTag();
	}


	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}


	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
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


	public boolean isHidden() {
		return hidden;
	}


	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}


	public boolean isSortable() {
		return sortable;
	}


	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}


	public String getFormatter() {
		return formatter;
	}


	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	
	
}
