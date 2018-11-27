<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="modelo.UserBean"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GDI</title>
</head>
<body>


	<div align="center">
		<div id="expired" class="content">

			<div align="center" id="TituloV" align="center">
				<h2 class="titulo1">Sesión caducada</h2>
			</div>


			<div style="padding-top: 50px;">
				<p>La sesión ha caducado, por favor haga clic en Iniciar sesión
					para ingresar al sistema nuevamente.</p>
				<p>
				<div style="padding-top: 20px;"></div>
				<a href=index.jsp>
					<button name="btnIniciarSesion" id="btnIniciarSesion"
						title="Iniciar Sesión">Iniciar sesión</button>
				</a>
				</p>
			</div>

		</div>

	</div>

</body>
</html>