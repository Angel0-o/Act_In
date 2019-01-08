package bs;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion 
{
	private Connection con;
	private String user;
	private String pass;
	private ResultSet rs;
	public boolean connect(String userS,String passS)
	{
		 try
	     {
		      //String url ="jdbc:sqlserver://BMVPAFINAN01:1433;databaseName=ADAM";
		      String url ="jdbc:sqlserver://BMVKIFINANZAS:1433;databaseName=ADAM";
		      user = userS;
		      pass = passS;
		      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");             
		      con = DriverManager.getConnection(url,user,pass);
		      return true;
	     }
	     catch(Exception e)
	     {
	        System.out.println(e);
	        return false;
	     }
	}
	
	public boolean execute(String sql)
	{
		Statement stmt = null;
		rs = null;

		try 
		{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		} 
	}
	
	public ResultSet getValues()
	{
		return rs;
	}
	
	public void close()
	{
		try
		{
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
