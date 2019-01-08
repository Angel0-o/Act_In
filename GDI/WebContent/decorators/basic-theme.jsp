<?xml version="1.0" encoding="UTF-8" ?>	
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page import="modelo.UserBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="image/ico" href="images/Logo.png" rel="shortcut icon">
<title>GDI</title>

<style type="text/css">
@import "css/bootstrap.min.css";
@import "css/bootstrap-theme.min.css";
@import "css/estilos.css";
</style>

<jsp:text>
	<![CDATA[
                <script tyoe="text/javascript" src="js/jquery-1.11.3.min.js"></script>
                <script tyoe="text/javascript" src="js/jquery-ui.min.js"></script>
                <script tyoe="text/javascript" src="js/bootstrap.min.js"></script>
                <script tyoe="text/javascript" src="js/scripts.js"></script>
            ]]>
</jsp:text>

<decorator:head />
<body>

	<%
		UserBean usuario = new UserBean();
		usuario = (UserBean) request.getSession().getAttribute(
				"currentSessionUser");
		if(usuario==null){
			response.sendRedirect("/GDI");
		}
	%>

	<table id="container" width="100%" height="100%">
		<!-- HEADER -->

		<tr style="height: 10%;">

			<td id="pageHeader" width="100%" colspan="3">

				<div id="header" style="padding-left: 15px; padding-top: 15px;">
					<img src="images/GrupoBMV.jpg" border="0" height="50"
						width="200" />
					<h3>Gestion Directorio Intranet</h3>
				</div>

			</td>
		</tr>

		<tr style="height: 10%;">

			<td id="pageMenu" width="100%" colspan="3">

				<form action="NavegacionServlet" method="post">

					<div id="navigation">
						<div class="collapse navbar-collapse navbar-ex1-collapse pad">
						
							<table style="width:100%">
							
								<tr>
						
								<td style="width:10%">
								<ul class="nav navbar-nav">
<!-- 									<li id="btnInicio" class="dropdown btnImg"><a href="/IPAT" -->
<!-- 										class="dropdown-toggle" data-toggle="dropdown"></a>									 -->
<!-- 									</li> -->
									<li id="btnOperaciones" class="dropdown btnImg"><a href="#"
										class="dropdown-toggle" data-toggle="dropdown"><b
											class="caret"></b></a>
										<ul class="dropdown-menu">
											<li id="liImportar"><a href="Actualizar.jsp">Dar de alta</a></li>
											<li id="liImportar"><a href="Eliminar.jsp">Dar de baja</a></li>
										</ul>
									</li>
								</ul>
								</td>
								
								<td style="width:80%"></td>
								
								<td style="width:10%">
								<button name="btnSalir" id="btnSalir" type="submit"></button>
<!-- 								<input name="btnSalir" class="btn btn-danger" type="submit" value="Cerrar sesi&oacute;n"> -->
<!-- 								<input type="submit" class="exit-btn exit-btn-1" name="btnSalir" value="Cerrar sesi&oacute;n"/> --><!-- 									<button class="exit-btn exit-btn-1">HOLA</button> -->
	<!-- 								style="background: url(../images/cerrar.png) no-repeat;" /> -->
								</td>
								
								</tr>
							</table>

						</div>

					</div>

				</form> <!-- Fin barra de navegación -->

			</td>

		</tr>


		<!-- CONTENT -->
		<tr style="height: 80%;">
			<td width="5%"></td>
			<td id="pageMain" width="90%"><div id="pageContent">
					<decorator:body />
				</div></td>
			<td width="5%"></td>
		</tr>



	</table>


	<!-- 	<script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script> -->
	<!-- 	<script type="text/javascript" src="../js/bootstrap.min.js"></script> -->
		<script type="text/javascript" src="js/scripts.js"></script>

</body>
</html>