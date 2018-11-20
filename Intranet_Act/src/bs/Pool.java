package bs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;


public class Pool {

	public static DataSource dataSource;
	public static DataSource dataSourceCASPER;

	public static String url = "jdbc:sqlserver://BMVPAFINAN01:1433;databaseName=ADAM";

	public static String usuarioConectado = new String();

	// public String user = "root";
	// public String pass = "123"; 

	public Pool() {
		// inicializaDataSource();
	}

	public static Boolean conecta(String user, String pass) {

		Boolean estado = false;

		java.sql.Connection cn = null;

		try {
			inicializaDataSource(user, pass);
			cn = Pool.dataSource.getConnection();

			if (cn != null) {
				System.out.println("CONECTADO POR POOL-----ADAM-------!!!!!!!!!!!!!!!!!!!");
				estado = true;
				usuarioConectado = user;
			}

		} catch (SQLException e) {
			estado = false;
			System.out.println(e);
		} finally {
			try {
				cn.close();
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}

		return estado;
	}
	
	private static void inicializaDataSource(String user, String pass) {

		BasicDataSource basicDataSource = new BasicDataSource();

		basicDataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		basicDataSource.setUsername(user);
		basicDataSource.setPassword(pass);
		basicDataSource.setUrl(url);
		basicDataSource.setMaxActive(50);

		dataSource = basicDataSource;

	}

	public static Boolean existeValor(String tabla, String columna,
			String valorAbuscar) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		Boolean existe = null;

		String sql = "";

		sql = "SELECT " + columna + " FROM " + tabla + " WHERE " + columna
				+ "='" + valorAbuscar + "'";

		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement
					(
							ResultSet.TYPE_SCROLL_INSENSITIVE, 
							ResultSet.CONCUR_READ_ONLY
					);
			rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				// System.out.println("NO SE ENCONTRÓ el valor: " + columna +
				// "="
				// + valorAbuscar);
				existe = false;
			} else {
				// System.out.println("existe: " + columna + "=" +
				// valorAbuscar);
				existe = true;
			}
		} catch (SQLException e) {
			String elements = e.getMessage();
			System.out.println("SQLException: " + elements);
			// e.printStackTrace();
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

	public static Boolean actualizarRegistros(ArrayList<String> filas) {

		Boolean correcto = false;
		String[] fila = null;
		String auxiliar = "";
		String sqlInsert = "";
		
		for(int x=0;x<filas.size();x++) 
		{
			auxiliar = filas.get(x);
			fila = auxiliar.split(",");
			System.out.println(filas.get(x));
			
			sqlInsert = "insert into bmv_trabajadores_intranet values('" + fila[0]
					+ "''" + fila[1]
					+ "''" + fila[2]
					+ "''" + fila[3]
					+ "')";
			System.out.println("SQL: "+sqlInsert);
		}
		// Datos para la conexión
		
		executeUID(sqlInsert);

		return correcto;
	}

	// -------------------------------------INSERT/UPDATE/DELETE-------------------------------------------------
	public static void executeUID(String sql) {

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			// System.out.println("ÉXITO INSERT/UPDATE/DELETE SQL: " + sql);

		} catch (SQLException e) {
			String elements = e.getMessage();
			System.out.println("SQLException: " + elements);
			// e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		}

	}// executeUID
		// -------------------------------------INSERT/UPDATE/DELETE-------------------------------------------------

	public static void desconectar() {

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			conn.close();
			if (conn.isClosed())
				System.out.println("Connection closed.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}// class
