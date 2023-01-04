package org.maxkey.synchronizer.jdbc;

import org.maxkey.entity.DbTableColumn;

public class ColumnFieldMapper extends DbTableColumn{
	String field;
	
	public ColumnFieldMapper(String column, String field, String type) {
		super(column, type, 0, 0);
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}
