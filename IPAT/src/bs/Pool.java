package bs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.*;

import javax.sql.DataSource;

import modelo.Cliente;
import modelo.PermisosBean;
import modelo.UserDAO;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;


public class Pool {

public static DataSource dataSource;
public static DataSource dataSourceOracle;

	public static String url = "jdbc:sqlserver://BMVPAFINAN01:1433;databaseName=DESARROLLO";//DESARROLLO
	//public static String url = "jdbc:sqlserver://BMVKIFINAN01:1433;databaseName=SOFTLAND";  //PRODUCCION
	//public static String urlO ="jdbc:oracle:thin:@10.100.130.54:1521:probmv89";   //ORACLE PRODUCCION
	public static String urlO ="jdbc:oracle:thin:@10.100.225.54:1521:DEVBMV89";   //ORACLE DESARROLLO
	
	public static String usuarioConectado = new String();
	public static String usuarioConectadoOracle = new String();
	public static String contraseniaOracle = new String();
	
	public static String excepcionIVA = new String();
	public static String excepcionFECHA = new String();
	public static String excepcionCLIENTE = new String();
	public static String excepcionID = new String();
	public static String excepcionDescripcionArt = new String();
	public static String excepcionUID = new String();
	public static String excepcionEXISTEVALOR = new String();
	public static String excepcionOBSERVACIONES = new String();
	
	public static String empresa = new String();
	public static String consecutivo = new String();

	public static Boolean conecta(String user, String pass) 
	{

		Boolean estado = false;

		Pool metodospool = new Pool();
		java.sql.Connection cn = null;

		try 
		{
			inicializaDataSource(user, pass);
			cn = Pool.dataSource.getConnection();

			if (cn != null) 
			{
				System.out.println("CONECTADO POR POOL!!!!!!!!!!!!!!!!!!!");
				estado = true;
				usuarioConectado = user;
			}

		} 
		catch (SQLException e) 
		{
			estado = false;
			System.out.println(e);
		} 
		finally 
		{
			try 
			{
				cn.close();
			} 
			catch (SQLException ex) 
			{
				System.out.println(ex);
			}
		}

		return estado;

	}

	private static void inicializaDataSource(String user, String pass) 
	{

		BasicDataSource basicDataSource = new BasicDataSource();

		basicDataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		basicDataSource.setUsername(user);
		basicDataSource.setPassword(pass);
		basicDataSource.setUrl(url);
		basicDataSource.setMaxActive(50);

		dataSource = basicDataSource;

	}

	public static Boolean conectaOracle(String userOracle, String passOracle) 
	{

		Boolean estadoOracle = false;

		Pool metodospoolOracle = new Pool(); 
		java.sql.Connection cnO = null; 

		try 
		{
			System.out.println("WE ARE HERE");
			inicializaDataSourceOracle(userOracle, passOracle); 
			cnO = Pool.dataSourceOracle.getConnection(); 
			//System.out.println("WE ARE HERE" + userOracle + passOracle);
			if (cnO != null) 
			{
				//System.out.println("NOW HERE");
				System.out.println("CONECTADO A ORACLE!!!!!!!!!!!!!!!!!!!");
				estadoOracle = true;
				System.out.println(userOracle + " " + passOracle);
				usuarioConectadoOracle = userOracle;
				contraseniaOracle = passOracle;
			}

		} 
		catch (SQLException e) 
		{
			estadoOracle = false;
			System.out.println(e);
		} 
		finally 
		{
			try 
			{
				cnO.close();
			} catch (SQLException ex) 
			{
				System.out.println(ex);
			}
		}

		return estadoOracle;

	}
	
	
	private static void inicializaDataSourceOracle(String userOracle, String passOracle) {

		BasicDataSource basicDataSourceOracle = new BasicDataSource();

		basicDataSourceOracle.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		basicDataSourceOracle.setUsername(userOracle);
		basicDataSourceOracle.setPassword(passOracle);
		basicDataSourceOracle.setUrl(urlO);
		basicDataSourceOracle.setMaxActive(50);

		dataSourceOracle = basicDataSourceOracle;

	}
	
	//Check if DATA EXISTS INTO DATABASE AMIB
	public static Boolean existeValor(String tabla, String columna,	String valorAbuscar) 
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		Boolean existe = null;

		String sql = "";

		sql = "SELECT " + columna + " FROM " + tabla + " WHERE " + columna 	+ "='" + valorAbuscar + "'";

		try 
		{
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (!rs.next()) 
			{
				//System.out.println("NO SE ENCONTRÓ " + columna + "=" + valorAbuscar);
				existe = false;
			} 
			else 
			{
				//System.out.println("EXISTE " + columna + " = " + valorAbuscar);
				existe = true;
			}
			
			excepcionEXISTEVALOR = "";			
		} 
		catch (SQLException e) 
		{
			String elements = e.getMessage();
			System.out.println("SQLException VALOR NO ENCONTRADO EN LA BD: " + elements);
			excepcionEXISTEVALOR = elements + "<br/>";
			e.printStackTrace();
			existe = false;
		} 
		finally 
		{
			try 
			{
				conn.close();
			} 
			catch (SQLException ex) 
			{
				System.out.println(ex);
			}
		}

		return existe;
	}

	// Check if IVA exists
	public static boolean existeValorIVA(String tabla, String cIVA, String IVA,	String cIMPUESTO1, String cIMPUESTO2, String valorAbuscar) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "";
		Boolean existe = null;

		sql = "SELECT * FROM " + tabla + " WHERE " + cIVA + "='" + IVA + "'" + " AND (" + cIMPUESTO1 + "='" + valorAbuscar + "'" + " OR " + cIMPUESTO2 + "='" + valorAbuscar + "')";

		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				System.out.println("NO SE ENCONTRÓ el IVA: " + valorAbuscar + "=" + valorAbuscar);
				existe = false;
			} else {
				//System.out.println("existe IVA: " + valorAbuscar + "=" + valorAbuscar);
				existe = true;
			}
			
			excepcionIVA = "";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String elements = e.getMessage();
			System.out.println("SQLException IVA: " + elements);
			excepcionIVA = elements + "<br/>";
			e.printStackTrace();
			
			existe = false;
		} finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}

		return existe;
	}
	
	//VALIDA OBSERVACIONES 
	public static boolean validaObservaciones(String observaciones){
		String obsEmails = observaciones.replace("]", ""); 
		String regEx = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
		String emails = "";
		boolean validEmail = false;
		for(String email: obsEmails.split(";")){
			Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Correo no valido: " + email);
                emails += email + ";";
            }           
		}
		if(emails == ""){
			validEmail = true;
		}else{
			excepcionOBSERVACIONES = "";
	        String elements = emails;
			System.out.println("Exception OBSERVACIONES: " + elements);
			excepcionOBSERVACIONES = elements;
			validEmail = false;
		}		
		return validEmail;
	}

	//Check if FECHA is between INI - FIN
	public static String fechaValida(String fechaExcel, String empresa) 
	{

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "";

		String fechaReturn;
		
		String delimitadorDiagonal = "/";
		String[] fechaSinDiagonales = fechaExcel.split(delimitadorDiagonal);
		String fechaF = fechaSinDiagonales[2] + fechaSinDiagonales[1] + fechaSinDiagonales[0];		
		System.out.println("FECHA CONSTRUIDA: " + fechaF);

		sql = "select FECHA_TRABAJO_INI,FECHA_TRABAJO_FIN from " + empresa + ".GLOBALES_AS";

		Date fechaIni = null;
		Date fechaFin = null;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {

			java.util.Date parsed = format.parse(fechaExcel);
			java.sql.Date date = new java.sql.Date(parsed.getTime());

			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();

			fechaIni = rs.getDate("FECHA_TRABAJO_INI");
			System.out.println("fechaIni = " + fechaIni);

			fechaFin = rs.getDate("FECHA_TRABAJO_FIN");
			System.out.println("fechaFin = " + fechaFin);

			if ((date.equals(fechaIni))	|| (date.after(fechaIni) && (date.before(fechaFin)) || date.equals(fechaFin))) {
				// FECHA PERMITIDA
				fechaReturn = fechaF;
				System.out.println("fechaReturn: " + fechaReturn);
				
			} else {
				System.out.println("NO ENTRA EN EL PROCESO. FECHA FUERA DE LÍMITE");
				fechaReturn = null;
			}
			
			excepcionFECHA = "";

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			String elements = e1.getMessage();
			System.out.println("ParseException in Fecha: " + elements);
			excepcionFECHA = elements + "<br/>";
			fechaReturn = null;
			e1.printStackTrace();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			String elements = e.getMessage();
			System.out.println("SQLException: " + elements);
			excepcionFECHA = elements + "<br/>";
			e.printStackTrace();
			fechaReturn = null;
			
		} finally {
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}
		
		return fechaReturn;
		
	}

	//ADD CLIENTE
	public static void addCliente(String cliente, String pedido, String empresa) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "";

		// Datos para capturar la información del cliente
		String idC = null;
		String nombreC = null;
		String direccionC = null;
		String paisC = null;
		String condicionPago = null;

		sql = "SELECT CLIENTE,NOMBRE,DIRECCION,PAIS,CONDICION_PAGO FROM " + empresa	+ ".CLIENTE WHERE CLIENTE='" + cliente + "'";

		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();

			idC = rs.getString(1);
			nombreC = rs.getString(2);
			direccionC = rs.getString("DIRECCION");
			paisC = rs.getString("PAIS");
			condicionPago = rs.getString("CONDICION_PAGO");
			System.out.println(condicionPago);

			for (Cliente c : UserDAO.listClientes)
				if (c.getPedido().equals(pedido))
					return;
			UserDAO.listClientes.add(new Cliente(pedido, cliente, nombreC, direccionC,paisC));
			System.out.println(idC);
			
			excepcionCLIENTE = "";

		} catch (SQLException e) {
			
			String elements = e.getMessage();
			System.out.println("SQLException ADD CLIENTE: " + elements);
			excepcionCLIENTE = elements + "<br/>";
			e.printStackTrace();
			
		} finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}

	}

	public static String rescatarID() {
		// TODO Auto-generated method stub
		String ultimoId="";
		String sqlMaxId;
		
		//GET THE LAST CONSECUTIVO
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		if(Pool.empresa.equals("AMIB2003")){
			sqlMaxId = "SELECT VALOR_CONSECUTIVO FROM "+ Pool.empresa+ ".CONSECUTIVO_FA WHERE CODIGO_CONSECUTIVO ='"+ Pool.consecutivo+ "';";
		}else{
			return "";
		}
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlMaxId);
			rs.next();
			ultimoId = rs.getString(1);

			excepcionID = "";
			
		} catch (SQLException e) {
			String elements = e.getMessage();
			System.out.println("SQLException: " + elements);
			excepcionID = elements + "<br/>";;
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}	

		System.out.println("ULTIMO VALOR CONSECUTIVO: " + ultimoId);
		return ultimoId;
		
	}
	
	public static String rescatarDescripcion(String articulo, String empresa){
		
		String descripcion=null;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		if (articulo.length() < 4 && empresa.equals("asigna")) {
			for (int ex = articulo.length(); ex < 4; ex++) {
				articulo = "0" + articulo;
			}
		}

		String sqlDescripcion="SELECT NOTAS FROM "+ empresa+ ".ARTICULO WHERE ARTICULO='" + articulo + "';";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlDescripcion);
			rs.next();
			descripcion = rs.getString(1);

			excepcionDescripcionArt = "";
			
		} catch (SQLException e) {
			String elements = e.getMessage();
			System.out.println("SQLException: " + elements);
			excepcionDescripcionArt = elements + "<br/>";
			e.printStackTrace();
			
		} finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}	
		
		return descripcion;
		
	}

	
	// -------------------------------------INSERT/UPDATE/DELETE-------------------------------------------------
	public static String executeUID(String sql){
		
		System.out.println("sql: " + sql);
		
		// vacío si fue correcto
		String mensaje = null;
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("ÉXITO INSERT/UPDATE/DELETE SQL: " + sql);
			excepcionUID = "";
			
		} catch (SQLException e) {
			String elements = e.getMessage();
			System.out.println("SQLException: " + elements);
			excepcionUID = elements + "<br/>";
			e.printStackTrace();
			mensaje = elements;
		} finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}
		
		return mensaje;
		
	}
	
	// ------------------------------------- Resultset -------------------------------------------------
	
	public static ResultSet consulta(String sql){
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			System.out.println("compania... " + rs.getString("compania")); 
			
		} catch (SQLException e) {
			String elements = e.getMessage();
			System.out.println("SQLException: " + elements);
			excepcionDescripcionArt = elements + "<br/>";
			e.printStackTrace();
			
		} finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}
		
		return rs;
		
	}	
	
	
	public static void desconectar(){
		
		Connection conn = null;
		Connection connO = null;
		
		try {
			conn = dataSource.getConnection();
			conn.close();
			connO = dataSourceOracle.getConnection();
			connO.close();
			if (conn.isClosed()||connO.isClosed())
				System.out.println("Connection closed.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static PermisosBean verPermisos(PermisosBean PbeanB) {
		
		String usuario =  PbeanB.getUsuarioP();
		String operacionE;
		String descripcionE;
		String estatusE;

		Connection currentCon = null;
		Statement stmtP = null;
		ResultSet rsP = null;


		try {
			currentCon = dataSource.getConnection();
			stmtP = currentCon.createStatement();
			
			if (PbeanB.getUsuarioP() != null && PbeanB.getUsuarioP() != "") {	
				String query = "EXEC bmv_permisos_usuario @usuario='" + usuario	+ "';";
				rsP = stmtP.executeQuery(query);
				System.out.println("Permisos Usuario : " + query);
				if (!rsP.next() ) {
					System.out.println("NO SE ENCONTRÓ NINGÚN REGISTRO!........");
					PbeanB.setValid(false);
				} else {

				    do {
				    	operacionE = rsP.getString("operacion");
						PbeanB.setOperacion(operacionE);
						descripcionE = rsP.getString("descripcion_ope");
						PbeanB.setDescripcion(descripcionE);
						estatusE = rsP.getString("estatus");
						PbeanB.setEstatus(estatusE);
						PbeanB.setUsuarioP(usuario);
						PbeanB.setValid(true);
						
				    } while (rsP.next());
				}
			}
			
			else{
				System.out.println("NO SE RELIZO CONSULTA DE EMPLEADO");
				String query = "EXEC bmv_permisos_usuario @usuario='"+ usuario+ "';";
				System.out.println("Permisos Usuario: " + query);
			}
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("CATCH!!!!!!");
			PbeanB.setValid(true);
		}

		return PbeanB;

	}
	
public static PermisosBean nuevoUsuario(PermisosBean PbeanB) {
		
		String usuarioC =  PbeanB.getUsuarioC();
		String mensajeC;

		Connection currentCon = null;
		Statement stmtC = null;
		ResultSet rsC = null;


		try {
			currentCon = dataSource.getConnection();
			stmtC = currentCon.createStatement();
			
			if (PbeanB.getUsuarioC() != null && PbeanB.getUsuarioC() != "") {	
				String query = "EXEC bmv_nuevo_usuario @usuario='"+ usuarioC+ "';";
				rsC = stmtC.executeQuery(query);
				System.out.println("NUEVO USER: " + query);
				if (!rsC.next() ) {
					System.out.println("NO SE ENCONTRÓ NINGÚN REGISTRO!........");
					PbeanB.setValid2(false);
				} else {

				    do {
				    	
						PbeanB.setUsuarioC(usuarioC);
						mensajeC = rsC.getString("mensaje");
						System.out.println(mensajeC);
						PbeanB.setValid2(true);
				    } while (rsC.next());
				}
			}else{
				System.out.println("NO SE RELIZO CONSULTA");			
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("CATCH!!!!!!");
			PbeanB.setValid(true);
		}

		return PbeanB;

	}
	
}