<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.UserBean"%>
<%@ page import="modelo.UserDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta content="width=device-width, initial-scale=1" name="viewport">
	<title>IPAT</title>
</head>

<body>

	<%
		UserBean usuario = new UserBean();
		usuario = (UserBean) request.getSession().getAttribute("currentSessionUser");
		if(usuario==null)
		{
			response.sendRedirect("/IPAT");
		}
		int timeout = session.getMaxInactiveInterval();
		response.setHeader("Refresh", timeout + "; URL = SessionExpired.jsp");
	%>


	<div id="container1" class="content" style="height: 550px; width:100%; background-repeat: no-repeat; margin-left: 3cm; background-image: url(../images/fondo.png);">
	</div>
</body>
</html>