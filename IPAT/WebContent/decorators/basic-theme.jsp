<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@page import="java.sql.Statement"%>
<%@page import="bs.ConnectionManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@ page import="modelo.UserBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="image/ico" href="../images/Logo.png" rel="shortcut icon">
<title>IPAT</title>

<style type="text/css"> 
	@import "../css/estilos.css";
</style>
<style type="text/css">
	@import "../css/bootstrap.min.css";
</style>
<style type="text/css">
	@import "../css/jquery-ui.css";
</style>

<jsp:text>
	<![CDATA[
                <script tyoe="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
                <script tyoe="text/javascript" src="../js/jquery-ui.min.js"></script>
                <script tyoe="text/javascript" src="../js/bootstrap.min.js"></script>
                <script tyoe="text/javascript" src="../js/scripts.js"></script>
            ]]>
</jsp:text>

<decorator:head />
<body>

	<%
		UserBean usuario = new UserBean();
		usuario = (UserBean) request.getSession().getAttribute("currentSessionUser");
		if(usuario==null)
		{
			response.sendRedirect("/IPAT");
		}
	
	%>

	<table id="container" width="100%" height="100%">
		<!-- HEADER -->
		<tr style="height: 10%;">
			<td id="pageHeader" width="100%" colspan="3">
				<div id="header" style="padding-left: 15px; padding-top: 15px;">
					<img src="../images/GrupoBMV.jpg" border="0" height="50"
						width="200" />
					<h3>Interfaces de Procesamiento de Archivos para
						Tesorer&iacute;a</h3>
				</div>

			</td>
		</tr>

		<tr style="height: 10%;">
			<td id="pageMenu" width="100%" colspan="3">
				<form action="../NavegacionServlet" method="post">
					<div id="navigation">
						<div class="collapse navbar-collapse navbar-ex1-collapse pad">
							<table style="width:100%">
								<tr>
									<td style="width:10%">
										<ul class="nav navbar-nav">
		<!-- 									<li id="btnInicio" class="dropdown btnImg"><a href="/IPAT" -->
		<!-- 										class="dropdown-toggle" data-toggle="dropdown"></a>									 -->
		<!-- 									</li> -->
											<li id="btnOperaciones" class="dropdown btnImg">
												<a href="#" class="dropdown-toggle" data-toggle="dropdown">
													<b class="caret"></b>
												</a>
												<ul class="dropdown-menu">
													<!--	<li id="liImportar"><a href="../pages/Importar.jsp">Importar archivo</a></li> -->
																<!-- <li id="liImportar"><a href="../pages/Reporte_Iva.jsp">Reporte de IVA pagado</a></li> -->
												
													<%											
														UserBean userR = new UserBean();
														userR = (UserBean) request.getSession().getAttribute("currentSessionUser");
								
														Connection currentCon = ConnectionManager.getConnection(userR.getUsername(), userR.getPassword());
														ResultSet rs = null;
														Statement st = currentCon.createStatement();
								
														String sql = ("EXEC bmv_sp_menu_ipat_opciones @usuario='"+userR.getUsername()+"'");
								
														try 
														{
															rs = st.executeQuery(sql);
															while (rs.next()) 
															{
																out.println("<li id=\"" + rs.getString("operacion")+ "\"><a href=\""+ rs.getString("ruta")+"\">"+ rs.getString("descripcion_ope") + "</a></li>");
															}
														} 
														catch (Exception e) 
														{
															e.printStackTrace();
														}
														finally {}
														st.close();
														rs.close();
													%>
												</ul>
											</li>
										</ul>
									</td>
									<td style="width:10%">
										<ul class="nav navbar-nav">
											<li id="btnReportes" class="dropdown btnImg">
												<a href="#" class="dropdown-toggle" data-toggle="dropdown">
													<b class="caret"></b>
												</a>
												<ul class="dropdown-menu">
												
													<%
								
														Connection currentConRP = ConnectionManager.getConnection(userR.getUsername(), userR.getPassword());
								
														ResultSet rsRP = null;
														Statement stRP = currentConRP.createStatement();
								
														String sqlRP = ("EXEC bmv_sp_menu_ipat_reportes @usuario='"+userR.getUsername()+"'");
								
														try 
														{
															rsRP = stRP.executeQuery(sqlRP);
															while (rsRP.next()) 
															{
																out.println("<li id=\"" + rsRP.getString("operacion")+ "\"><a href=\""+ rsRP.getString("ruta")+"\">"+ rsRP.getString("descripcion_ope") + "</a></li>");
															}
														} 
														catch (Exception e) 
														{
															e.printStackTrace();
														} 
														finally {}
														stRP.close();
														rsRP.close();
													%>
												</ul>
											</li>
										</ul>
									</td>
									<td style="width:70%"></td>	
									<td style="width:10%">
										<button name="btnSalir" id="btnSalir" type="submit"></button>
	<!-- 								<input name="btnSalir" class="btn btn-danger" type="submit" value="Cerrar sesi&oacute;n"> -->
	<!-- 								<input type="submit" class="exit-btn exit-btn-1" name="btnSalir" value="Cerrar sesi&oacute;n"/> -->
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
			<td width="2%"></td>
			<td id="pageMain" width="90%">
				<div id="pageContent">
					<decorator:body />
				</div></td>
			<td width="5%"></td>
		</tr>



	</table>

		<script type="text/javascript" src="../js/scripts.js"></script>

</body>
</html>