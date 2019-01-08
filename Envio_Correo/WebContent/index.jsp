<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Insert title here</title>
</head>
<body>
	<div id = "principal">
		<form action="Ver_Correos.jsp" method = "POST">
			<table>
				<tr>
					<td>Usuario:</td> 
					<td><input type = "text" name = "user"></td>
				</tr>
				<tr>
					<td>Clave:</td> 
					<td><input type = "password" name = "pass"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Entrar" id = "button_enviar"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>