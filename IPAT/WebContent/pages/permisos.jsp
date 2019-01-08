<%@page import="java.sql.Statement"%>
<%@page import="bs.ConnectionManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="modelo.UserBean"%>
<%@ page import="modelo.PermisosBean"%>
<%@ page import="bs.Pool"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../css/estilos2.css">
<title>Insert title here</title>
</head>
<body>
<%
		UserBean usuarioC = new UserBean();
		usuarioC = (UserBean) request.getSession().getAttribute(
				"currentSessionUser");
		if (usuarioC == null) {
			response.sendRedirect("/IPAT");
		}
	%>
<div class="row">
          <div class="col s12">
            <p Style="font-size: 14px;">Lista de usuarios con permisos para ingresar al sitio</p>
            
  <!--            <form name="frm1" method="post" action=""><input type="hidden" name="hdnbt" /><input type="button" name="bt" value="rcontrerasc" onclick="{document.frm1.hdnbt.value=this.value;document.frm1.submit();}" /></form>
    -->        
            
            <div class="col span_1_of_3">
							<a class="waves-effect waves-light btn modal-trigger" style="font-size: 20px;" href="#modal3">Nuevo usuario</a>
  							
					</div>
					
					
					
						<!-- MODAL -->
	
	<div id="modal3" class="modal modal-fixed-footer" style="  max-height: 35%; width: 35%;">
	

	
	<div class="modal-header" style="border-bottom: 1px solid rgba(0, 0, 0, 0.1); height: 50px; position:absolute; margin-bottom:50px;">
	<h4 style="padding: 5px 0 0 24px; font: 100% Calibri; font-weight: bold;">ALTA DE NUEVO USUARIO</h4>
             
           </div>
	
          
           
           
           <div class="modal-content" style="max-height: 100%; margin: 50px 0px 0px 0px;">
           
           <div class="row">
				    
				    
				    
				    <form class="col s12">
				      <div class="row" style="margin:20px 0px 0px 0px; ">
				        <div class="input-field col s8" style="float:left;">
				        <label for="nombreEm" style="float:left; font-size: 14px;">Usuario:  </label>
				          <input id="nombreEm" name="nombreEm" type="text" class="validate" style="width:200px; margin-left:10px;">
				        </div>
				       
				        
				        <div class="input-field col s4" style="float:left;" >
						<input type="submit" name="btnBuscar" id="btnBuscar"
							value="Agregar" title="Agregar" style="float:left;" class="btn waves-effect waves-light blue grupobmv-2">	
					</div>
				        
				      </div>
				    </form>
					
		              
		                   
		                    <% 
		                    PermisosBean username = new PermisosBean();
		                  
		                    String NombreEmp = request.getParameter("nombreEm");
		                    
		                    UserBean userZ = new UserBean();
							userZ = (UserBean) request.getSession().getAttribute(
									"currentSessionUser");
		                    Connection currentConnection1C = ConnectionManager.getConnection(
									userZ.getUsername(), userZ.getPassword());

		   				 username.setUsuarioC(NombreEmp);
		   				ResultSet rrsC = null;

						Statement ssC = currentConnection1C.createStatement();
		                    
						try {
							
		               
		                
		                if (username.getUsuarioC() == "") {
		                	out.println("<script type=\"text/javascript\">"
									+"alert('No ingresó ningun nombre. Para realizar la operación es necesario ingresar el usuario. ');"
									+"</script>");
		                }
		                
		                else  if (username.getUsuarioC() != null && username.getUsuarioC() != ""){ 

			                String nombreU = username.getUsuarioC();
		         
							String queryN = "EXEC bmv_nuevo_usuario @usuario='"
									+ nombreU
									+ "';";
									System.out.println("sqlNuevo: " + queryN);
							rrsC = ssC.executeQuery(queryN);
							if (!rrsC.next() ) {
								out.println("<script type=\"text/javascript\">"
										+"alert('No se agregó el usuario');"
										+"</script>");
								username.setUsuarioC("");
							 //   System.out.println("no data");
							} else {

							    do {
							
							    	
									out.println("<script type=\"text/javascript\">"
										+"alert('"+ rrsC.getString("mensaje")+"'); location.href = 'permisos.jsp';"
										+"</script>");
								username.setUsuarioC("");
									username.setUsuarioC("");
									
									
									
							    } while (rrsC.next());
							    
							}
							
						} 
						
		                
						} catch (Exception e) {
							e.printStackTrace();
						}
		                
		                %>
		                       
				    
				    
  			</div>
           
           </div>
           
          <div class="modal-footer">
            <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat ">Cerrar</a>
          </div>
        </div>
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
            
            
            <table class="highlight">
              <thead>
                <tr>
                    <th data-field="usuario" style="font-weight: bold; font-size: 14px;">USUARIO</th>
                    <th data-field="ver" style="font-weight: bold; font-size: 14px;">PERMISOS</th>
                    <th data-field="eliminar" style="font-weight: bold; font-size: 14px;">ELIMINAR</th>
                </tr>
              </thead>
              <tbody>
              
              
		
                
                <%
					

						UserBean userR = new UserBean();
						userR = (UserBean) request.getSession().getAttribute(
								"currentSessionUser");

						Connection currentCon = ConnectionManager.getConnection(
								userR.getUsername(), userR.getPassword());

						ResultSet rs = null;
						Statement st = currentCon.createStatement();

						String sql = ("SELECT usuario FROM bmv_usuarios_ipat WHERE nivel=1 ORDER BY usuario;");

						try {
							rs = st.executeQuery(sql);
							while (rs.next()) {
								out.println("<tr><td value='" + rs.getString("usuario")
										+ "' style=\"font-size: 14px;\">" + rs.getString("usuario") + "</td>");
							

						out.println("<td><form name=\"frm"+ rs.getString("usuario") +"\" method=\"post\" action=\"\"><input type=\"hidden\" name=\"hdnbt\" /><button type=\"button\" name=\"bt\" value=\""+ rs.getString("usuario") +"\" class=\"btn-floating blue grupobmv-2\" onclick=\"{document.frm"+ rs.getString("usuario") +".hdnbt.value=this.value;document.frm"+ rs.getString("usuario") +".submit();}\" /><i class=\"material-icons\">lock</i></button></form></td>");	
							//out.println("<td><form name=\"frm\" method=\"post\" action=\"\"><input type=\"hidden\" name=\"hdnbt\" /><input type=\"button\" name=\"bt\" value=\"sa\" onclick=\"{document.frm.hdnbt.value=this.value;document.frm.submit();}\"  /></form></td>");
							//	out.println("<td id=\"usuarioDelete\" name=\"usuarioDelete\" value='" + rs.getString("usuario")+ "'>Eliminar</td></tr>");
								out.println("<td><form name=\"frmD"+ rs.getString("usuario") +"\" method=\"post\" action=\"\"><input type=\"hidden\" name=\"hdnbtD\" /><button type=\"button\" name=\"btd\" value=\""+ rs.getString("usuario") +"\" class=\"btn-floating red\" onclick=\"if (confirm('De click en Aceptar para continuar con la eliminación de "+ rs.getString("usuario")+"')) {document.frmD"+ rs.getString("usuario") +".hdnbtD.value=this.value;document.frmD"+ rs.getString("usuario") +".submit();} else {return false;}\" /><i class=\"material-icons\">delete</i></button></form></td></tr>");	
								
				                    
							}
							PermisosBean usuario = new PermisosBean();
			                  
	                		  String UsuarioEmp =request.getParameter("hdnbt");
	                		  String UsuarioDel =request.getParameter("hdnbtD");
	                		  
	                		  System.out.println("usuario-seleccionado: " + UsuarioEmp);
			                    usuario.setUsuarioP(UsuarioEmp);
			                   
			                    System.out.println("usuario a eliminar: " + UsuarioDel);
			                    usuario.setUsuarioD(UsuarioDel);
							

							out.println("</tbody>");
							out.println("</table>");
							out.println("</div>");
							out.println("</div>");
							
							out.println("<div id=\"modal2\" class=\"modal modal-fixed-footer\" style=\"max-height: 60%; width: 55%;\">");  
							out.println("<div class=\"modal-header\" style=\"border-bottom: 1px solid rgba(0, 0, 0, 0.1); height: 50px; position:absolute; margin-bottom:50px;\">");  
							out.println("<h4 style=\"padding: 5px 0 0 24px; font: 100% Calibri; font-weight: bold;\">ADMINISTRACIÓN DE PERMISOS PARA USUARIOS</h4> ");  
							//out.println("<form class=\"col s12\">");  
							//out.println("<div class=\"row\">");  		        
							//out.println("<div class=\"input-field col s4\" style=\"float:left;\" >");  
							//out.println("<input type=\"submit\" name=\"btnBuscar\" id=\"btnBuscar\" value=\"Guardar\" title=\"Guardar\" style=\"float:left;\" class=\"btn waves-effect waves-light blue grupobmv-2\">	");  
							//out.println("</div>	");  		     
							//out.println("</div>");  
							//out.println(" </form> ");   
							out.println(" </div>");  
							
							out.println("<div class=\"modal-content\" style=\"max-height: 100%; margin: 50px 0px 50px 0px;\">");  
							out.println(" <div class=\"row\">");    
							out.println("<table class=\"highlight\">");
							out.println(" <thead>");
							out.println("<tr>");
							out.println("<th data-field=\"operacion\" style=\"font-weight: bold; font-size: 14px;\">OPERACIÓN</th>");
							out.println("<th data-field=\"permiso\" style=\"font-weight: bold; font-size: 14px;\">PERMISO</th>   ");             
							out.println(" </tr>");
							out.println("</thead>");
							out.println("<tbody>");
							
							
							 
							
			                  
	                		 
	                		  
	                		  UserBean userY = new UserBean();
								userY = (UserBean) request.getSession().getAttribute(
										"currentSessionUser");
			                    Connection currentConnection1 = ConnectionManager.getConnection(
										userY.getUsername(), userY.getPassword());
			                    
			                   
				   				ResultSet rrs = null;

								Statement ss = currentConnection1.createStatement();
								
						//AQUI LO DEJÉ		
								try {
									//usuario = Pool.verPermisos(usuario);
				                if (usuario.getUsuarioP() == null) {
				                	out.println("<tr><td></td><td></td></tr>");
										//	+ "<td>" + rrs.getString("sit_trabajador")+"</td></tr>"
				                }
				                
				   //             if (usuario.getUsuarioP() == "") {
				   //             	out.println("<script type=\"text/javascript\">"
					//						+"alert('No ingresó ningun nombre');"
				//							+"</script>");
				 //               }
				                
				                else  if (usuario.getUsuarioD() != null && usuario.getUsuarioD() != ""){ 
				                	
				                	
				                	String Dusuario = usuario.getUsuarioD();
				                	
				                	 System.out.println("Operaciones Seleccionadas: ");
								        String query1D = "DELETE FROM bmv_aut_operaciones WHERE usuario='"+Dusuario+"';";
										String query2D = "EXEC bmv_sp_quitar_permisos @usuario='"+Dusuario+"'";
										String query3D = "DELETE FROM bmv_usuarios_ipat WHERE usuario='"+Dusuario+"';";
								        
								        Pool.executeUID(query1D); 
								        Pool.executeUID(query2D);
								        Pool.executeUID(query3D); 
								        
								        System.out.println(query1D);
								        System.out.println(query2D);
								        System.out.println(query3D);
								        
								        out.println("<script type=\"text/javascript\">"
												+"alert('El usuario se elimino con éxito.'); location.href = 'permisos.jsp';"
												+"</script>");
				                }
				                
				                else  if (usuario.getUsuarioP() != null && usuario.getUsuarioP() != ""){ 			                
					                String Nusuario = usuario.getUsuarioP();
				         
									String query = "EXEC bmv_permisos_usuario @usuario='"
											+ Nusuario
											+ "';";
											
											System.out.println("sql3: " + query);
											out.println("<form name=\"formc\" onsubmit=\"checkBoxValidation()\"");
											rrs = ss.executeQuery(query);
									if (!rrs.next() ) {
										out.println("<script type=\"text/javascript\">"
												+"alert('Este usuario no tiene ningun permiso');"
												+"</script>");
										usuario.setUsuarioP("");
									 //   System.out.println("no data");
									} else {

									    do {
									    //	System.out.println("Si hay datos");
									    	out.println("<tr><td style=\"font-size: 14px;\">" + rrs.getString("descripcion_ope")
												//	+ "</td>" +"<td>"+ rrs.getString("estatus")+"</td>"
													+"</td><td><p> <input type=\"checkbox\" name=\"operacion\" id=\"c-"+ rrs.getString("operacion")+"\" "+ rrs.getString("estatus")+" value=\""+ rrs.getString("operacion")+"\"/><label for=\"c-"+ rrs.getString("operacion")+"\"></label></p></td></tr>"
													+"<input type=\"hidden\" name=\"usuarioC\" value=\""+Nusuario+"\">"
												//	+ "<td>" + rrs.getString("sit_trabajador")+"</td></tr>"
													+"<script> $(document).ready(function(){$('#modal2').openModal();  });</script>");
											//usuario.setUsuarioP("");
									    } while (rrs.next());
									}
								} 
								
			
				                out.println("</tbody>");
								out.println("</table>");
							    
							    
								out.println("</div>");
			           
								out.println("</div>");
								out.println("<div class=\"modal-footer\">");
								
								out.println("<div class=\"input-field col s4\" style=\"float:right;\" > <input type=\"submit\" value=\"Aplicar\" name=\"btnGr\" id=\"btnGr\" class=\"btn waves-light blue grupobmv-2\" style=\"padding: 0 5px 0 5px;\" /></div>");
								out.println("</form>");
								out.println(" <a href=\"#!\" class=\"modal-action modal-close waves-effect waves-green btn-flat \" style=\"padding:0 30px 0 0;\">Cancelar</a>");
								out.println("</div>");
								out.println("</div>");
								
								//String botonG = request.getParameter("btnGr");
								//System.out.println("Boton: "+botonG);
								
								 if (request.getParameter("btnGr") != null) {
								String Nusuario2 = request.getParameter("usuarioC");
								String operacion[]= request.getParameterValues("operacion");
						        
								if(operacion != null){
							        System.out.println("Operaciones Seleccionadas: ");
							        String query1 = "DELETE FROM bmv_aut_operaciones WHERE usuario='"+Nusuario2+"';";
									String query3 = "EXEC bmv_sp_quitar_permisos @usuario='"+Nusuario2+"'";
							        
							        Pool.executeUID(query1); 
							        Pool.executeUID(query3); 
									//System.out.println("SQL exitoso: " + query3);
									
							        	for(int i=0; i<operacion.length; i++){
							        		//System.out.println("Usuario: "+Nusuario2);
							        		// System.out.println("Operaciones Seleccionadas: "+operacion[i]);
							        		 
							        		 
							        	
							        		String query2 = "INSERT INTO bmv_aut_operaciones VALUES ('"+Nusuario2+"','"+operacion[i]+"',1);";
											String query4 = "EXEC bmv_sp_otorgar_permisos @usuario='"+Nusuario2+"', @operacion='"+operacion[i]+"'";	
												
												//System.out.println("sql: " + query4);
												//ss.executeQuery(query2);
												Pool.executeUID(query2);
												Pool.executeUID(query4); 
							        		 
							        	}
						        	
							        	response.sendRedirect("permisos.jsp");
						        	
						        }
						        
						       
						//        if(request.getParameter("operacion") == null && request.getParameter("operacion") == null) {
						//        	out.println("<script type=\"text/javascript\">"
						//					+"alert('Debe seleccionar algo');"
						//					+"</script>");
				        //        }
								
						        }
				                
				                
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								
						            
						            
							
						} catch (Exception e) {
							e.printStackTrace();
						} finally {

						}
						st.close();
						rs.close();
				
					%>
              
        
        
       
        
        
        
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
		<script src="../js/materialize.js"></script>
		<script>
	  	  $(document).ready(function(){
		    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
		  $('.modal-trigger').leanModal();
		  });
		</script>
	
</body>
</html>