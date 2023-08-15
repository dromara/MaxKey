/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

/*
 * Copyright (c) 2022, MaxKey and/or its affiliates. All rights reserved.
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please contact MaxKey, visit www.maxkey.top if you need additional
 * information or have any questions.
 */

package org.dromara.maxkey.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class DbTableMetaData {
	String tableName;
	
	ArrayList<DbTableColumn> columns = new ArrayList<DbTableColumn>();
	
	HashMap<String,DbTableColumn> columnsMap = new HashMap<String,DbTableColumn>();

	public DbTableMetaData(String tableName) {
		super();
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<DbTableColumn> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<DbTableColumn> columns) {
		this.columns = columns;
	}

	public HashMap<String, DbTableColumn> getColumnsMap() {
		return columnsMap;
	}

	public void setColumnsMap(HashMap<String, DbTableColumn> columnsMap) {
		this.columnsMap = columnsMap;
	}
	
	
}
