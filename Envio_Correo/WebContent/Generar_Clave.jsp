<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="bs.Conexion"%>
<%@page import="controlador.GenerarClave"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.HashMap"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	if (session.getAttribute("user") == null) 
	{
		response.sendRedirect("index.jsp");
	}
	String usr = (String)session.getAttribute("user");
	String pass = (String)session.getAttribute("pass");
	
		
		
			  
	String sql = "select cc.trabajador, LEFT(t.registro_fiscal,2) + SUBSTRING(t.registro_fiscal,5,2) + ";
		   sql +="SUBSTRING(t.registro_fiscal,3,1) +  SUBSTRING(t.registro_fiscal,7,2) + SUBSTRING(t.registro_fiscal,4,1) +";
		   sql +="SUBSTRING(t.registro_fiscal,9,2) as registro_fiscal from bmv_correo_clave as cc , trabajadores as t";
		   sql +=" where cc.trabajador = RIGHT(t.trabajador,6);";
;
	Conexion con = new Conexion();
	if(!con.connect(usr, pass))
		response.sendRedirect("index.jsp");
	con.execute(sql);
	HashMap<String,String> claves = GenerarClave.getClaves(con.getValues());
	con.close();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Insert title here</title>
</head>
<body>
	<table style="margin:30px;">
	<tr>
		<th>Usuario</th>
		<th>Clave</th>
	</tr>
	<%
	for ( String key : claves.keySet() ) 
	{
	    out.println("<tr><td>"+key+"</td><td>"+claves.get(key)+"</td></tr>");
	    GenerarClave.registrarClave(usr, pass, key, claves.get(key));
	}
	%>
	</table>
	<p>
		<form action="Ver_Correos.jsp">
			<input type="submit" value = "Regresar" class="botones">
		</form>
	</p>
</body>
</html>