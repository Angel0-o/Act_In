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

<title>Intranet_Act</title>

</head>
<body>

	<%
		UserBean usuario = new UserBean();
		usuario = (UserBean) request.getSession().getAttribute(
				"currentSessionUser");
		// 		System.out.println("Usuario: " + usuario.getUsername());
		// 		if(usuario.getUsername()==null){
		if (usuario == null) {
			response.sendRedirect("/Intranet_Act");
		}
	%>

	<%
		int timeout = session.getMaxInactiveInterval();
		response.setHeader("Refresh", timeout
				+ "; URL = SessionExpired.jsp");
	%>

	<h3>Operación realizada con éxito</h3>

</body>
</html>