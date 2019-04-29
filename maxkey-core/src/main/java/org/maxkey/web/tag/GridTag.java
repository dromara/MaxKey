package org.maxkey.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.maxkey.web.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GridTag extends TagSupport {
	final static Logger _logger = LoggerFactory.getLogger(GridTag.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1243669057069893859L;
	
	
	public static final String  GridTagAttribute=GridTag.class.getName()+"_GridTagAttribute";
	
	public static final int MultiSelectColumnWidth=903;
	
	public static final int DefaultColumnWidth=928;
	
	private String id;
	
	private String url;
	
	private boolean multiselect;
	
	private String onSelect;
	
	private PageContext pageContext;

	private boolean resize=true;
	
	private int rowLimit=10;
	
	private String rowList="[10,50,100]";

	
	
	@Override
	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException {
		List<GridColumn> listGridColumn=(List<GridColumn>)pageContext.getAttribute(GridTag.GridTagAttribute);
		
		try {
			if(url	==	null){
				pageContext.getOut().println("<div>Grid URL can not be null . </div>");
				return super.doEndTag();
			}
			_logger.debug(""+listGridColumn);
			
			StringBuffer colNames=new StringBuffer("");
			colNames.append("\t colNames  :  [\n");
			
			StringBuffer colModel=new StringBuffer("");
			colModel.append("\t colModel  :  [\n");
			int visibleColumnWidth=0;
			int visibleColumnCount=0;
			for(int i=0;i<listGridColumn.size();i++){
				GridColumn gridColumn=listGridColumn.get(i);
				String titleText=WebContext.getI18nValue(gridColumn.getTitle());
				if(titleText==null||titleText.equals("")){
					String []localPath=gridColumn.getTitle().split("\\.");
					titleText=localPath[(localPath.length>1?localPath.length-1:0)];
					titleText=titleText.toUpperCase().charAt(0)+titleText.substring(1);
				}
				if(i<listGridColumn.size()-1){
					colNames.append("\t\t\"").append(titleText).append("\",\n");
					colModel.append("\t\t").append(gridColumn.getJson()).append(",\n");
				}else{
					colNames.append("\t\t\"").append(titleText).append("\"");
					colModel.append("\t\t").append(gridColumn.getJson());
				}
				if(!gridColumn.isHidden()){
					visibleColumnWidth+=gridColumn.getWidth();
					visibleColumnCount++;
				}
			}
			colNames.append("\n\t ], ");
			colModel.append("\n\t ] ");
			
			StringBuffer gridsb=new StringBuffer("");
			if(visibleColumnWidth>MultiSelectColumnWidth&&this.isMultiselect()){
				gridsb.append("<div>Grid Column Width "+visibleColumnWidth+" greate than "+MultiSelectColumnWidth+" . </div>");
				pageContext.getOut().println(gridsb);
				return super.doEndTag();
			}else if(visibleColumnWidth>DefaultColumnWidth){
				gridsb.append("<div>Grid Column Width "+visibleColumnWidth+" greate than "+DefaultColumnWidth+" . </div>");
				pageContext.getOut().println(gridsb);
				return super.doEndTag();
			}
			
			int currentRowLimit	=	rowLimit;
			if(WebContext.getAttribute(GridTag.class.getName()+"_PageResults")!=null){
				currentRowLimit=Integer.parseInt(WebContext.getAttribute(GridTag.class.getName()+"_PageResults").toString());
				_logger.debug("current RowLimit "+currentRowLimit);
			}
			
			gridsb.append("\n<script type=\"text/javascript\">\n<!--");
			gridsb.append("\nvar "+id+"_gridSettings={");
			gridsb.append("\n\t element  :  \""+id+"\",");
			gridsb.append("\n\t url  :  '"+pageContext.getServletContext().getContextPath()+url+"',");
			gridsb.append("\n\t visibleColumnWidth  :  "+visibleColumnWidth+",");
			gridsb.append("\n\t visibleColumnCount  :  "+visibleColumnCount+",");
			gridsb.append("\n\t columnWidth  :  "+bulidColumnWidth(visibleColumnCount)+",");
			gridsb.append("\n\t multiselect  :  "+multiselect+",");
			gridsb.append("\n\t resize  :  "+resize+",");
			gridsb.append("\n\t rowNum  :  "+currentRowLimit+",");
			gridsb.append("\n\t rowList  :  "+rowList+",");
			
			if(onSelect	!=	null){
				gridsb.append("\n\t onSelectRow  :  "+onSelect+",");
			}
			
			gridsb.append("\n"+colNames.toString()+"");
			gridsb.append("\n"+colModel.toString()+"");
			
			gridsb.append("\n};");
			gridsb.append("\n$(function () {");
			gridsb.append("\n\t $.grid("+id+"_gridSettings);");
			gridsb.append("\n});");
			
			gridsb.append("\n-->\n</script>");
			
			gridsb.append("\n<table id=\""+id+"\" class=\"scroll\"></table>");
			gridsb.append("\n<div id=\""+id+"_pager"+"\" class=\"scroll\" style=\"text-align: center;\"></div>");
			pageContext.getOut().println(gridsb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pageContext.removeAttribute(GridTag.GridTagAttribute);
		
		return super.doEndTag();
	}

	
	public int bulidColumnWidth(int visibleColumnCount){
		int columnWidth=0;
		if(this.isMultiselect()){
			if(visibleColumnCount>5){
				columnWidth=MultiSelectColumnWidth-5*(visibleColumnCount-6);
			}else if(visibleColumnCount<5){
				columnWidth=MultiSelectColumnWidth+5*(5-visibleColumnCount);
			}else{
				columnWidth=MultiSelectColumnWidth;
			}
		}else{
			if(visibleColumnCount>5){
				columnWidth=DefaultColumnWidth-5*(visibleColumnCount-6);
			}else if(visibleColumnCount<5){
				columnWidth=DefaultColumnWidth+5*(5-visibleColumnCount);
			}else{
				columnWidth=DefaultColumnWidth;
			}
		}
		return columnWidth;
	}
	
	@Override
	
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	

	@Override
	public void setValue(String k, Object o) {
		// TODO Auto-generated method stub
		super.setValue(k, o);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isMultiselect() {
		return multiselect;
	}

	public void setMultiselect(boolean multiselect) {
		this.multiselect = multiselect;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getOnSelect() {
		return onSelect;
	}


	public void setOnSelect(String onSelect) {
		this.onSelect = onSelect;
	}


	public boolean isResize() {
		return resize;
	}


	public void setResize(boolean resize) {
		this.resize = resize;
	}


	/**
	 * @return the rowLimit
	 */
	public int getRowLimit() {
		return rowLimit;
	}


	/**
	 * @param rowLimit the rowLimit to set
	 */
	public void setRowLimit(int rowLimit) {
		this.rowLimit = rowLimit;
	}


	/**
	 * @return the rowList
	 */
	public String getRowList() {
		return rowList;
	}


	/**
	 * @param rowList the rowList to set
	 */
	public void setRowList(String rowList) {
		this.rowList = rowList;
	}
	
}
