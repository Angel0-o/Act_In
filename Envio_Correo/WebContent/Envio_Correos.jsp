<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="bs.Conexion"%>
<%@page import="controlador.Correo"%>
<%@page import="java.sql.ResultSet"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	if (session.getAttribute("user") == null) 
	{
		response.sendRedirect("index.jsp");
	}
	String usr = (String)session.getAttribute("user");
	String pass = (String)session.getAttribute("pass");
	String valores[] = request.getParameterValues("usuarios");
	int total = Integer.parseInt(request.getParameter("total"));
	String sql = "select * from dbo.bmv_correo_clave ";
	if(valores.length != total)
	{
		sql += "where trabajador in (";
		for(int i=0;i<valores.length;++i)
		{
			sql+= "'"+valores[i]+"'";
			if(i<valores.length-1)
				sql+=",";
		}
		sql+=")";
	}
	sql+=";";
	ResultSet rs;
	int correos_enviados = 0;
	Conexion con = new Conexion();
	if(!con.connect(usr, pass))
		response.sendRedirect("index.jsp");
	if(con.execute(sql))
		rs = con.getValues();
	else
		rs = null;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		while(rs.next())
		{
			String correo = rs.getString("Correo");
			String user = rs.getString("trabajador");
			String clave = rs.getString("clave");
			if(Correo.send(correo, user, clave))
				out.println("Correo enviado al usuario: " + user + " con cuenta de correo: " + correo + "\n");
			else
				out.println("<p style='color:#ff0000'> Error de envio a : " + user + " con cuenta de correo: " + correo+"</p>");
		}
	%>
	<br>
	<p><a href="Ver_Correos.jsp" >Regresar</a></p>
</body>
</html>