/*
 * Copyright (c) 2022, MaxKey and/or its affiliates. All rights reserved.
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact MaxKey, visit www.maxkey.top if you need additional
 * information or have any questions.
 */

package org.maxkey.entity;

public class DbTableColumn {
	String column;
	String type;
	int precision;
	int scale;

	public DbTableColumn(String column, String type, int precision, int scale) {
		super();
		this.column = column;
		this.type = type;
		this.precision = precision;
		this.scale = scale;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

}
