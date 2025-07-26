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
 

package org.dromara.maxkey.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.dromara.maxkey.entity.DbTableColumn;
import org.dromara.maxkey.entity.DbTableMetaData;


public class JdbcUtils {

	public static Connection connect(String url, String user, String pwd, String driverClass) {
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn = java.sql.DriverManager.getConnection(url, user, pwd);
			return conn;
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
			return null;
		} catch (SQLException e) {
			System.out.println("SQLException");
		}
		return null;
	}

	public void release(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("SQLException");
			}
		}
	}

	public static void release(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				System.out.println("SQLException");
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				System.out.println("SQLException");
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				System.out.println("SQLException");
			}
		}
	}
	
	public static void release(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				System.out.println("ResultSet Close Exception");
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				System.out.println("Statement Close Exception");
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
				pstmt = null;
			} catch (SQLException e) {
				System.out.println("PreparedStatement Close Exception");
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				System.out.println("Connection Close Exception");
			}
		}
	}
	
	public static DbTableMetaData getMetaData(ResultSet rs) {
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			DbTableMetaData meta = new DbTableMetaData(metaData.getTableName(1));
			int count = metaData.getColumnCount();
			for (int i = 1; i <= count; i++) {
				DbTableColumn column = new DbTableColumn(
						metaData.getColumnName(i).toLowerCase(),
						metaData.getColumnTypeName(i),
						metaData.getPrecision(i),
						metaData.getScale(i)
						);
				meta.getColumns().add(column);
				meta.getColumnsMap().put(column.getColumn(), column);
			}
			return meta;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
