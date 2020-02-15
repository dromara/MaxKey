package org.maxkey.util;

import org.maxkey.pretty.impl.SqlPretty;

public class SqlPrettyTest {

	public SqlPrettyTest() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SqlPretty sf=new SqlPretty();
		String sqlString="select * from userinfo where t='111' order by  t,s,t";
		System.out.println(sf.format(sqlString));
	}

}
