<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="bs.Conexion"%>
<%@page import="java.sql.ResultSet"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String usr,pass;
	if (session.getAttribute("user") == null) 
	{
		usr = request.getParameter("user");
		pass = request.getParameter("pass");
		session.setAttribute( "user", usr );
		session.setAttribute( "pass", pass );
	}
	else
	{
		usr = (String)session.getAttribute("user");
		pass = (String)session.getAttribute("pass");
	}
	ResultSet rs;
	Conexion con = new Conexion();
	if(!con.connect(usr, pass))
		response.sendRedirect("index.jsp");
	String sql = "select * from dbo.bmv_correo_clave;";
	if(con.execute(sql))
		rs = con.getValues();
	else
		rs = null;
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
	function select_all()
	{
		for(i = 0;i<document.formulario.elements.length;++i)
		{
			if(document.formulario.elements[i].type == "checkbox")	
		         document.formulario.elements[i].checked=1;
		}
	}
	
	function diselect_all()
	{
		for(i = 0;i<document.formulario.elements.length;++i)
		{
			if(document.formulario.elements[i].type == "checkbox")	
		         document.formulario.elements[i].checked=0;
		}
	}
</script>
<title>Insert title here</title>
</head>
<body>
	<div id ="capa_valores">
		<form action="Envio_Correos.jsp" method="POST" name="formulario">
			<table>
				<tr>
					<td></td>
					<th>Usuario</th>
					<th>Correo</th>
				</tr>
				<%
					int total = 0;
					while(rs.next())
					{
						String usuario = rs.getString("trabajador");
						String correo = rs.getString("correo");
						out.println("<tr>");
						out.println("<td class='td_check'><input type='checkbox' name='usuarios' value='"+usuario+"'></td>");
						out.println("<td class='td_user'>"+usuario+"</td>");
						out.println("<td class='td_correo'>"+correo+"</td>");
						out.println("</tr>");
						total++;
					}
					con.close();
					out.println("<input type='hidden' name='total' value ='"+total+"'>");
					out.println("<input type='hidden' name='user' value ='"+usr+"'>");
					out.println("<input type='hidden' name='pass' value ='"+pass+"'>");
				%>
				<tr></tr><tr></tr>
				<tr>
					<td></td><td><input type = "submit" value="Enviar" class="botones"/></td>
					<td>
						<input type = "button" value="Activar Todos" class ="botones" id="botones_select" onClick="select_all()"></td>
					<td>	<input type = "button" value="Desactivar" class ="botones" id="botones_des" onClick="diselect_all()">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<br>
	<br>
	<div id = "capa_claves">
		<form action ="Generar_Clave.jsp">
			<input type ="submit" value="Generar Claves" class = "botones" id = "botones_generar"> 
		</form>
	</div>
</body>
</html>