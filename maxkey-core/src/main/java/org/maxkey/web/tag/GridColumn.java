package org.maxkey.web.tag;

public class GridColumn {
	
	private String field;
	
	private String title;
	
	private int width;
	
	private boolean hidden;
	
	private boolean sortable;
	
	private String formatter;

	
	
	/**
	 * 
	 */
	public GridColumn() {
		super();
	}
	
	

	/**
	 * @param field
	 * @param title
	 * @param width
	 * @param hidden
	 * @param sortable
	 * @param formatter
	 */
	public GridColumn(String field, String title, int width, boolean hidden,
			boolean sortable, String formatter) {
		this.field = field;
		this.title = title;
		this.width = width;
		this.hidden = hidden;
		this.sortable = sortable;
		this.formatter = formatter;
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

	public String getJson(){
		StringBuffer sb=new StringBuffer("{");
		sb.append("name :\"").append(this.field).append("\", ");
		sb.append("index :\"").append(this.field).append("\", ");
		sb.append("width :").append(this.width).append(", ");
		sb.append("hidden :").append(this.hidden).append(", ");
		if(this.formatter==null){
			sb.append("sortable :").append(this.sortable).append(" ");
		}else{
			sb.append("sortable :").append(this.sortable).append(", ");
			sb.append("formatter :").append(this.formatter).append("");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public String toString() {
		return "GridColumn [field=" + field + ", title=" + title + ", width="
				+ width + ", hidden=" + hidden + ", sortable=" + sortable
				+ ", formatter=" + formatter + "]";
	}
}
