/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.persistence.derby;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DerbyTest {
/**
 * 1.
 * first SET JAVA_HOME,DERBY_HOME,PATH
 * set PATH=%DERBY_HOME%\bin;%PATH%
 * 2.
 * startNetworkServer Start Derby Database
 * 3.
 * create db seconddb1 , user is tquist
 * CONNECT 'jdbc:derby://localhost:1527/seconddb1;create=true;user=tquist';
 * 4.
 * Configuring NATIVE authentication
 * call SYSCS_UTIL.SYSCS_CREATE_USER( 'tquist', 'tquist' );
 * 5.
 * then restart derby database
 */
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		String nsURL="jdbc:derby://localhost:1527/seconddb1";  
		java.util.Properties props = new java.util.Properties();
		props.setProperty("user","tquist");
		props.setProperty("password","tquist");

		Connection conn = DriverManager.getConnection(nsURL, props);

		/*interact with Derby*/
		Statement s = conn.createStatement();

		ResultSet rs = s.executeQuery("select * from secondtable");
		
		while(rs.next()){
			System.out.println("key : "+rs.getInt("ID")+" ,name : "+rs.getString("NAME"));
		}
	}

}
