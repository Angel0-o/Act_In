package bs;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionManagerOracle {
	
	static Connection con;
	static String url;
	static PreparedStatement stmt = null;

	         
	   public static Connection getConnection(String userS, String passS)
	   {
	     
	      try
	      {

	    	  //String url ="jdbc:oracle:thin:@10.100.130.54:1521:probmv89";  //ORACLE PRODUCCION
		  	  String url ="jdbc:oracle:thin:@10.100.225.54:1521:DEVBMV89";   //ORACLE DESARROLLO
		      String user = userS;
		      String pass = passS;
		      

	         Class.forName("oracle.jdbc.driver.OracleDriver");
	         
	         try
	         {               
	            con = DriverManager.getConnection(url,user,pass);
	                                     
	         }
	         
	         catch (SQLException ex)
	         {
	        	 System.out.println("Log In failed: An Exception has occurred! USUARIO NO ENCONTRADO EN SQL " + ex);
	        	 ex.printStackTrace();
	        	 return con=null;
	         }
	      }

	      catch(ClassNotFoundException e)
	      {
	         System.out.println(e);
	      }

	   return con;
	}
	   
	// Closing the connection
	    public static Connection disconnect() 
	    {
	    	try 
	    	{
				con.close();
				if (con.isClosed()) 
			          System.out.println("Connection Oracle closed.");
			}
	    	catch (SQLException e) 
	    	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    	return con;
	    	
	    }

}


