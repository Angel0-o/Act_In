<%@page import="bs.Pool"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="modelo.UserBean"%>
<%@ page import="modelo.UserDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta content="width=device-width, initial-scale=1" name="viewport">
		<!-- para móviles -->

		<title>GDI</title>
</head>
<body>

	<%
		UserBean usuario = new UserBean();
		usuario = (UserBean) request.getSession().getAttribute(
				"currentSessionUser");
		if (usuario == null) {
			response.sendRedirect("/GDI");
		}
	%>

	<%
		int timeout = session.getMaxInactiveInterval();
		response.setHeader("Refresh", timeout
				+ "; URL = SessionExpired.jsp");
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
	
	<form action="Eliminar" method="post" name="formEliminar" autocomplete="off" id="formEliminar" class="cascadeTest">


		<div id="dImportar" class="content50IZQ">

			<table cellpadding="10">

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