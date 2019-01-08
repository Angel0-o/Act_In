package controlador;

import java.sql.ResultSet;
import java.util.HashMap;

import bs.Conexion;

public class GenerarClave 
{
	public static HashMap<String,String> getClaves(ResultSet rs) throws Exception
	{
		HashMap<String,String> claves = new HashMap<>();
		while(rs.next())
		{
			String clave =  rs.getString("registro_fiscal");
			claves.put(rs.getString("trabajador"),clave);
		}
		return claves;
	}
	
	public static void registrarClave(String usr, String pass,String usuario,String clave) throws Exception
	{
		Conexion con = new Conexion();
		con.connect(usr, pass);
		String sql = "update bmv_correo_clave set clave = '"+clave+"' where trabajador = '" + usuario + "';";
		con.execute(sql);
		con.close();
	}
}
