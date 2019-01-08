<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.UserBean"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<meta content="width=device-width, initial-scale=1" name="viewport">
<title>IPAT</title>
<link type="image/ico" href="images/Logo.png" rel="shortcut icon">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/estilos.css">

</head>

<body>

	<%
		UserBean usuario = new UserBean();
		usuario = (UserBean) request.getSession().getAttribute("currentSessionUser");
		if(usuario!=null)
		{
			response.sendRedirect("pages/Principal.jsp");
		}
	%>

	<div style="padding-left: 15px; padding-top: 15px;">
		<img src="images/GrupoBMV.jpg" border="0" height="50" width="200" />
	</div>

	<div align="center">
		<h3>Interface de Procesamiento de Archivos para Tesorería</h3>
	</div>

	<div id="flogin">
	
	<form action="LoginServlet" method="post" autocomplete="off"
		name="formLogin">

			<div id="dusuario" class="pad" align="center">
				<img src="images/usuario.png" width="20px" height="20px" align="bottom">
				<input id="inputUsername" name="inputUsername" class="" placeholder="Usuario">
			</div>

			<div id="dclave" class="pad" align="center">
				<img src="images/candado.png" width="20px" height="20px" align="bottom"> 
				<input id="inputPassword" name="inputPassword" class="" type="password" placeholder="Contraseña">
			</div>

			<div id="dEntrar" class="pad" align="center">
				<input type="submit" id="btnEntrar" value="" class="btnImg">
			</div>
	</form>
	
	</div>
	
	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>

</body>
</html>