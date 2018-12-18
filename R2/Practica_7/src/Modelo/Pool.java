package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 *
 * @author angel
 */
public class Pool 
{
    public static String url = "jdbc:mysql://localhost:3306/registro?autoReconnect=true&useSSL=false";
    public static String usuarioConectado = new String();
    
    public Pool(){}
    
    public static Boolean actualiza(String user, String pass, String query) 
    {
	Boolean estado = false;
	try 
        {
            Connection conexion = DriverManager.getConnection(url,user,pass);
            Statement stateregistro = conexion.createStatement();
            System.out.println("CONECTADO POR POOL-----root-------!!!!!!!!!!!!!!!!!!!");
            estado = true;
            usuarioConectado = user;
            
            //ResultSet consulta =  stateregistro.executeQuery("select * from carta");
            stateregistro.executeUpdate(query);
            stateregistro.close();
            conexion.close();
	} catch (SQLException e) {
            estado = false;
            System.out.println(e);
	}

	return estado;
    }
    
    public static ArrayList<String> consulta(String user, String pass, String query) 
    {
	Boolean estado = false;
        String lineS;
	ArrayList<String> filas = new ArrayList<String>();
	try 
        {
            Connection conexion = DriverManager.getConnection(url,user,pass);
            Statement stateregistro = conexion.createStatement();
            System.out.println("CONECTADO POR POOL-----root-------!!!!!!!!!!!!!!!!!!!");
            estado = true;
            usuarioConectado = user;
            
            ResultSet consulta =  stateregistro.executeQuery(query);
            if (consulta.next())
            {
                filas.add(consulta.getString("valor"));
                filas.add(consulta.getString("imagen"));
                filas.add(consulta.getString("disponible"));
            }
            
            stateregistro.close();
            conexion.close();
            consulta.close();
	} catch (SQLException e) {
            estado = false;
            System.out.println(e);
	}
	return filas;
    }
}
