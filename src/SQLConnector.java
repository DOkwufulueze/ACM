/*
* Automated Counselling Machine
* Author: Daniel Okwufulueze
*/

import java.sql.*;

public class SQLConnector{
	public static Connection getConnection()
	{
		Connection con=null;
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:ACM","root","");	
		}
		catch(java.lang.Exception e)
		{
			System.out.println(e);
		}
		return con;
	}	
	
	public static ResultSet executeQuery(String stSQL) throws Exception
	{
		Connection cn = getConnection();
		Statement  st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet res = st.executeQuery(stSQL);
		return res;		
	}
	
	public static int executeUpdate(String stSQL) throws Exception
	{
		Connection cn = getConnection();
		Statement  st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		int  n = st.executeUpdate(stSQL);
		return n;		
	}	
}
