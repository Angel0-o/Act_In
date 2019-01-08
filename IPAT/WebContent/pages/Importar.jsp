<%@page import="java.sql.Statement"%>
<%@page import="bs.ConnectionManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="modelo.UserBean"%>
<%@ page import="bs.Pool"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IPAT</title>
</head>
<body>

	<%
		UserBean usuario = new UserBean();
		usuario = (UserBean) request.getSession().getAttribute(
				"currentSessionUser");
		if (usuario == null) 
		{
			response.sendRedirect("/IPAT");
		}
	%>

	<script type="text/javascript" defer="defer">
		function cascadeSelect(parent, child) 
		{
			var childOptions = child.find('option:not(.static)');
			child.data('options', childOptions);

			parent.change(function() {
				childOptions.remove();
				child.append(child.data('options').filter('.sub_' + this.value)).change();})
				childOptions.not('.static, .sub_' + parent.val()).remove();
			}

		$(function() {
			cascadeForm = $('.cascadeTest');
			orgSelect = cascadeForm.find('.empresa');
			terrSelect = cascadeForm.find('.consecutivo');

			cascadeSelect(orgSelect, terrSelect);
		});
	</script>


	<form action="../CompletoServlet" method="post" name="formInsertar" autocomplete="off" id="formInsertar" class="cascadeTest">


		<div id="dImportar" class="content50IZQ">

			<table cellpadding="10">

				<tr>
					<%
						out.println("<td class=\"pad-form\"><b>Compañía:</b></td>");
						out.println("<td class=\"pad-form\">");
						out.println("<select name=\"empresa\" class=\"empresa\" style=\"width: 350px\">");
						out.println("<option value=\"-1\" selected>Seleccione compañía</option>");

						UserBean userR = new UserBean();
						userR = (UserBean) request.getSession().getAttribute("currentSessionUser");

						Connection currentCon = ConnectionManager.getConnection(userR.getUsername(), userR.getPassword());
						ResultSet rs = null;
						Statement st = currentCon.createStatement();

						String sql = ("select * from dbo.BMV_COMPANIAS_IPAT;");

						try 
						{
							rs = st.executeQuery(sql);
							while (rs.next()) 
							{
								out.println("<option value='" + rs.getString("compania") + "'>" + rs.getString("compania") + "</option>");
							}
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						} 
						finally {}
						
						st.close();
						rs.close();
						out.println("</select></td></tr>");

						ResultSet rs2 = null;
						Statement st2 = currentCon.createStatement();
						out.println("<tr>");
						out.println("<td class=\"pad-form\"><b>Consecutivo:</b></td>");
						out.println("<td class=\"pad-form\">");
						out.println("<select name=\"consecutivo\" class=\"consecutivo\" style=\"width: 350px\">");
						out.println("<option value=\"-1\" class=\"static\">Seleccione el consecutivo</option>");
						String sql2 = ("select COMPANIA,PARAMETRO from bmv_parametros_ipat;");
						try 
						{
							rs2 = st2.executeQuery(sql2);
							while (rs2.next()) 
							{
								out.println("<option value=\""+ rs2.getString("PARAMETRO")+ "\" class=\"sub_" + rs2.getString("COMPANIA") + "\""+ ">" + rs2.getString("PARAMETRO") + "</option>");
							}
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						} 
						finally {}
						
						st2.close();
						rs2.close();
						out.println("</select></td></tr>");
					%>

				<tr>
					<td class="pad-form">
						<b>Archivo:</b>
					</td>
					<td class="pad-form">
						<input type="file" name="fName" id="fName" accept=".csv" />
					</td>
				</tr>

				<tr height="70">
					<td class="pad-form"></td>
					<td class="pad-form">
						<input type="submit" id="Procesar"	name="Procesar" value="" />
					</td>
					<td class="pad-form"></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>