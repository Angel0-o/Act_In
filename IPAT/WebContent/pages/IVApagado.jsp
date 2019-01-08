<%@page import="java.io.*"%>
<%@page import="java.sql.Statement"%>
<%@page import="bs.ConnectionManager"%>
<%@page import="bs.ConnectionManagerOracle"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="modelo.UserBean"%>
<%@page import="bs.Pool"%>
<%@page import="controlador.Paths"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../css/estilos2.css">
<title>IPAT</title>
</head>
<body>
	<%
		UserBean usuario = new UserBean();
		usuario = (UserBean) request.getSession().getAttribute("currentSessionUser");
		if (usuario == null) {
			response.sendRedirect("/IPAT");
		}
		String rutaPagosIVA = Paths.PATH_PAGOS_IVA;
		String rutaPagosAnuIVA = Paths.PATH_PAGOS_ANUL_IVA;
	%>
	
	<script type="text/javascript">
		function changeFunc()
		{
			var selectBox = document.getElementById("selectBox");
			var selectedValue = selectBox.options[selectBox.selectedIndex].value;
 			window.history.replaceState('Nuevo', 'Title', '/IPAT/pages/IVApagado.jsp?usuario='+ selectedValue);
 			location.reload(true);
 		}
		window.onload = function()
		{
			var getUrlParameter = function getUrlParameter(sParam)
			{
				var sPageURL = decodeURIComponent(window.location.search.substring(1)),	sURLVariables = sPageURL.split('&'), sParameterName, i;
				for (i = 0; i < sURLVariables.length; i++){
					sParameterName = sURLVariables[i].split('=');
					if (sParameterName[0] === sParam){
		            	return sParameterName[1] === undefined ? true : sParameterName[1];
		        	}
		    	}
			};
			var dateValue1 = getUrlParameter('datefrom');
			var dateValue2 = getUrlParameter('dateto');
			if (dateValue1 !== null) {$('#datefrom').val(dateValue1);}
			if (dateValue2 !== null) {$('#dateto').val(dateValue2);}
   		}

	</script>
	<h3>Consulta pagos de IVA AMIB</h3>
	<form>
		<div class="row" style="border-style: solid; border-color: #cccccc; border-radius: 25px; max-width: 1300px; margin:0 20px 30px 0;">
            <div class="col grid-example s12 m12 l4">
	            <br><br>
	            <div class="row">
					<div class="input-field col s12 m6 l6">
						DESDE:<br>
						<input id="datefrom" type="date" class="datepicker" name="datefrom" placeholder="YYYY-MM-DD" style="width:149px">
						<span></span>
			        </div>
			        <div class="input-field col s12 m6 l6" style="float:left">
			        	HASTA:<br>
			        	<input id="dateto" type="date" class="datepicker" name="dateto" placeholder="YYYY-MM-DD" style="width:149px">
			        </div>
				</div>
			</div>	            
            </div>
		 	<div class="row">
		 		<div class="col s12 m6 l3"><p> </p></div>
		 		<div class="col s12 m6 l3"><p> </p></div>
		 		<div class="col s12 m6 l3"><p> </p></div>
		 		<div class="col s12 m6 l3">
		 			<div class="input-field col s12" style="float:left;" >
						<input type="submit" name="btnConsultar" id="btnConsultar" value="Consultar" title="Consultar" style="float:left;" Width=2em class="waves-effect waves-teal yellow blue btn grupobmv-2">
					</div>
    			</div>
  			</div>
  
	</form> 
	
	
	<%
		String msgOrcl;
		boolean errorCheck = false;
		//IVApagadoBean SoftFaceB = new IVApagadoBean();
		
		UserBean userR = new UserBean();
		userR = (UserBean) request.getSession().getAttribute("currentSessionUser");
		Connection currentCon = ConnectionManager.getConnection(userR.getUsername(), userR.getPassword());
		
					
		String fechaDesde = "";
		String fechaHasta = "";
		
		
		if(request.getParameter("btnConsultar") != null){
			fechaDesde = request.getParameter("datefrom");
			fechaHasta = request.getParameter("dateto");
												
			/* IMPRESION DE ERRORES */
			
			
			if(fechaDesde=="" || fechaHasta==""){
				out.println("<script type=\"text/javascript\" class=\"alert-warning\">" + "alert('Selecciona una fecha de inicio y una fecha de fin.');" + "</script>");
				errorCheck = true;
			}
			
			if(!errorCheck){
            	
            	/**************LLENANDO TABLA HTML*****************/
	            	            
            	ResultSet rrFR = null;
            	Statement stFR = currentCon.createStatement();
            	
				
				String sqlTabla = "EXEC bmv_SP_IVA_PAGADO @datefrom='" + fechaDesde + "', @dateto='" + fechaHasta + "';";
				rrFR = stFR.executeQuery(sqlTabla);
				System.out.println(sqlTabla);
				if (!rrFR.next()){
					out.println("<script type=\"text/javascript\" class=\"alert-warning\"> " + "alert('No hay datos para el rango de fechas seleccionado');" + "</script>");
				}
				else{
					
					/* IMPRESION DE DATOS SELECCIONADOS */	        
	            	out.println("<table>");
					out.println("<tbody>");
					out.println("<tr>");
					out.println("<th style=\"font-size:14px\"><b><h3>Tabla de Documentos Pagados</h3></b></th>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td style=\"font-size:14px\"> <b>Rango de fechas seleccionados: </b>" + fechaDesde + " - " + fechaHasta + "</td>");
					out.println("</td>");
					out.println("</tr>");
					out.println("</tbody>");
					out.println("</table>");
												
					/* TABLA-CONSULTA */
					
					out.println("<div class=\"col grid-example s12 m6 l4\" style=\"overflow-x: scroll; overflow-y: scroll; max-width: 1300px; max-height:500px;\">");
					out.println("<iframe id=\"my_iframe\" style=\"display:none;\"></iframe>");
					out.println("<table  id=\"table_wrapper\" class=\"striped\">");
			        out.println("<thead>");
			        out.println("<tr class=\"grey darken-3\" style=\"color: rgba(255, 255, 255, 0.9); \">");
			        out.println("<th data-field=\"id_prov\" style=\"font-weight: bold; text-align: center;\">PROVEEDOR</th>");
			        out.println("<th data-field=\"doc\" style=\"font-weight: bold; text-align: center;\">DOCUMENTO</th>");
			        out.println("<th data-field=\"rfc\" style=\"font-weight: bold; text-align: center;\">CONTRIBUYENTE O RFC</th>");
			        out.println("<th data-field=\"prov\" style=\"font-weight: bold; text-align: center;\">NOMBRE PROVEEDOR</th>");
			        out.println("<th data-field=\"nodocto\" style=\"font-weight: bold; text-align: center;\">NO. DE DOCUMENTO (PAGO)</th>");
			        out.println("<th data-field=\"tipo\" style=\"font-weight: bold; text-align: center;\">TIPO DOC (PAGO)</th>");
			        out.println("<th data-field=\"fechadoc_pago\" style=\"font-weight: bold; text-align: center;\">FECHA DE DOCUMENTO (PAGO)</th>");
			        out.println("<th data-field=\"moneda_pago\" style=\"font-weight: bold; text-align: center;\">MONEDA (PAGO)</th>");
			        out.println("<th data-field=\"montoprov_pago\" style=\"font-weight: bold; text-align: center;\">MONTO PROVEEDOR (PAGO)</th>");
			        out.println("<th data-field=\"montolocal_pago\" style=\"font-weight: bold; text-align: center;\">MONTO LOCAL (PAGO)</th>");
			        out.println("<th data-field=\"asiento_pago\" style=\"font-weight: bold; text-align: center;\">ASIENTO (PAGO)</th>");
			        out.println("<th data-field=\"fecha\" style=\"font-weight: bold; text-align: center;\">FECHA</th>");
			        out.println("<th data-field=\"fechadocto\" style=\"font-weight: bold; text-align: center;\">FECHA DE DOCUMENTO</th>");
			        out.println("<th data-field=\"asiento_xp\" style=\"font-weight: bold; text-align: center;\">ASIENTO XP</th>");
			        out.println("<th data-field=\"montolocal\" style=\"font-weight: bold; text-align: center;\">MONTO LOCAL</th>");
			        out.println("<th data-field=\"asiento_pago\" style=\"font-weight: bold; text-align: center;\">DIFERENCIA ENTRE DOCUMENTO ORIGEN Y PAGO</th>");
			        out.println("<th data-field=\"fecha_xp\" style=\"font-weight: bold; text-align: center;\">FECHA XP</th>");
			        out.println("<th data-field=\"iva\" style=\"font-weight: bold; text-align: center;\">IVA CONTABLE</th>");
			        out.println("<th data-field=\"credito\" style=\"font-weight: bold; text-align: center;\">CREDITO LOCAL</th>");
			        out.println("</tr>");
			        out.println("</thead>");
		            out.println("<tbody>");
					
					do{
						out.println("<tr><td>" + rrFR.getString("PROVEEDOR") + "</td>" + "<td>"+ rrFR.getString("DOCUMENTO") + "</td>");
						out.println("<td>" + rrFR.getString("CONTRIBUYENTE") + "</td>" + "<td>"+ rrFR.getString("NOMBRE_PROVEEDOR") + "</td>");
						out.println("<td>" + rrFR.getString("NO_DOCTO_PAGO") + "</td>" + "<td>"+ rrFR.getString("TIPO_DOCTO_PAGO") + "</td>");
						out.println("<td>"+ rrFR.getString("FECHA_DOCTO_PAGO") + "</td>" + "<td>" + rrFR.getString("MONEDA_PAGO") + "</td>");
						out.println("<td>"+ rrFR.getString("MONTO_PROV_PAGO") + "</td>" + "<td>" + rrFR.getString("MONTO_LOCAL_PAGO") + "</td>");
						out.println("<td>"+ rrFR.getString("ASIENTO_PAGO") + "</td>" + "<td>"+ rrFR.getString("FECHA") + "</td>");
						out.println("<td>" + rrFR.getString("FECHA_DOCUMENTO") + "</td>" + "<td>"+ rrFR.getString("ASIENTO_XP") + "</td>");
						out.println("<td>" + rrFR.getString("MONTO_LOCAL") + "</td>" + "<td>" + rrFR.getString("DIF_DOCORIGEN_PAGO") + "</td>");
						if(rrFR.getString("FECHA_XP") == null){
							out.println("<td> </td>");
						}else
							out.println("<td>"+ rrFR.getString("FECHA_XP") + "</td>");
						
						if(rrFR.getString("IVA_CONTABLE") == null){
							out.println("<td> </td>");
						}else
							out.println("<td>" + rrFR.getString("IVA_CONTABLE") + "</td>");
						
						if(rrFR.getString("CREDITO_LOCAL") == null){
							out.println("<td> </td>");
						}else
							out.println("<td>"+ rrFR.getString("CREDITO_LOCAL") + "</td>");
						
					}while (rrFR.next());
					
					stFR.close();
					rrFR.close();
					
					out.println("</tr>"+"</tbody>" + "</table>" + "</div>");
					
					/***********LLENANDO ARCHIVO CSV ****************/
		            try{
			            /* CREANDO EL ARCHIVO */
			            //La ruta del archivo es la del servidor
			            File archivo =  new File(rutaPagosIVA);
						FileOutputStream file = new FileOutputStream (archivo);
						PrintWriter pw = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(file), "UTF-8"));
				    										
						/* LLENANDO EL ENCABEZADO */ 
						pw.append("PROVEEDOR,DOCUMENTO,CONTRIBUYENTE O RFC,NOMBRE PROVEEDOR,NO. DE DOCUMENTO (PAGO),")
						  .append("TIPO DOC (PAGO),FECHA DE DOCUMENTO (PAGO),MONEDA (PAGO),MONTO PROVEEDOR (PAGO),MONTO LOCAL (PAGO),")
						  .append("ASIENTO (PAGO),FECHA,FECHA DE DOCUMENTO,ASIENTO XP,MONTO LOCAL,DIFERENCIA DOC ORIGEN Y PAGO,FECHA XP,IVA CONTABLE,CREDITO LOCAL").println();
						
						/* LLENANDO DESDE SQL */
											
						ResultSet rrExp = null;
			            Statement stExp = currentCon.createStatement();
						String sqlExport = "EXEC bmv_SP_IVA_PAGADO_EXPORTAR @datefrom='" + fechaDesde + "', @dateto='" + fechaHasta + "';";
						rrExp = stExp.executeQuery(sqlExport);
						System.out.println(sqlExport);
						
						if (!rrExp.next()){
							System.out.println("No hay datos en el resultSet");
						}
						else{
							
							do{
								pw.append(rrExp.getString("PROVEEDOR")).append(',').append(rrExp.getString("DOCUMENTO")).append(',')
								  .append(rrExp.getString("CONTRIBUYENTE")).append(',').append(rrExp.getString("NOMBRE_PROVEEDOR")).append(',')
								  .append(rrExp.getString("NO_DOCTO_PAGO")).append(',').append(rrExp.getString("TIPO_DOCTO_PAGO")).append(',')
								  .append(rrExp.getString("FECHA_DOCTO_PAGO")).append(',').append(rrExp.getString("MONEDA_PAGO")).append(',')
								  .append(rrExp.getString("MONTO_PROV_PAGO")).append(',').append(rrExp.getString("MONTO_LOCAL_PAGO")).append(',')
								  .append(rrExp.getString("ASIENTO_PAGO")).append(',').append(rrExp.getString("FECHA")).append(',')
								  .append(rrExp.getString("FECHA_DOCUMENTO")).append(',').append(rrExp.getString("ASIENTO_XP")).append(',')
								  .append(rrExp.getString("MONTO_LOCAL")).append(',').append(rrExp.getString("DIF_DOCORIGEN_PAGO")).append(',');
								if(rrExp.getString("FECHA_XP") == null){
									pw.append("#N/A").append(',');
								}else
									pw.append(rrExp.getString("FECHA_XP")).append(',');
								
								if(rrExp.getString("IVA_CONTABLE") == null){
									pw.append("#N/A").append(',');
								}else
									pw.append(rrExp.getString("IVA_CONTABLE")).append(',');
								
								if(rrExp.getString("CREDITO_LOCAL") == null){
									pw.append("#N/A").println();
								}else
									pw.append(rrExp.getString("CREDITO_LOCAL")).println();
								
							}while (rrExp.next());
							
							stExp.close();
							rrExp.close();
						}
						pw.close();
						
		            }catch(IOException e){e.printStackTrace();}
					
					/*BOTON DE EXPORTAR PARA PAGOS AMIB*/
					
					%>
				
					<div class="row">
				 		<div class="col s12 m6 l3">
				 			<br>
				 			<br>
				 			<br>
				 			
				 			<div class="input-field col s12" style="float:left;">
				 				<a href="../files/pagosIVA-AMIB.csv" download>
				 					<input type="submit"  name="btnExportar" id="btnExportar" value="Exportar" title="Exportar" style="float:left;" Width=2em class="waves-effect waves-teal yellow blue btn grupobmv-2">
				 				</a>
							</div>
						</div>
					</div>
										
					<%
				}//Fin Facturas Iva Pagado
				
				/*******************TABLA ANULADOS*******************/
		            
	            ResultSet rrAN = null;
	            Statement stAN = currentCon.createStatement();
	            String sqlAnulados = "EXEC bmv_SP_IVA_PAGADO_ANULADOS @datefrom='" + fechaDesde + "', @dateto='" + fechaHasta + "';";
	            
	            rrAN = stAN.executeQuery(sqlAnulados);
				System.out.println(sqlAnulados);
				
				/*LLENANDO TABLA ANULADOS*/
				if (!rrAN.next()){
					out.println("<script type=\"text/javascript\" class=\"alert-warning\"> " + "alert('No hay facturas anuladas para el rango de fechas seleccionado.');" + "</script>");
				}else{
					
					/* IMPRESION DE DATOS SELECCIONADOS */	        
	            	out.println("<table>");
					out.println("<tbody>");
					out.println("<tr>");
					out.println("<th style=\"font-size:14px\"><b><h3>Tabla de Documentos Anulados</h3></b></th>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td style=\"font-size:14px\"><b>Rango de fechas seleccionados: </b>" + fechaDesde + " - " + fechaHasta + "</td>");
					out.println("</td>");
					out.println("</tr>");
					out.println("</tbody>");
					out.println("</table>");
					
					/* ENCABEZADO TABLA ANULADOS */
					out.println("<div class=\"col grid-example s12 m6 l4\" style=\"overflow-x: scroll; overflow-y: scroll; max-width: 1300px; max-height:500px;\">");
					out.println("<iframe id=\"my_iframe2\" style=\"display:none;\"></iframe>");
					out.println("<table  id=\"table2\" class=\"striped\">");
			        out.println("<thead>");
			        out.println("<tr class=\"grey darken-3\" style=\"color: rgba(255, 255, 255, 0.9); \">");
			        out.println("<th data-field=\"id_prov\" style=\"font-weight: bold; text-align: center;\">PROVEEDOR</th>");
			        out.println("<th data-field=\"prov\" style=\"font-weight: bold; text-align: center;\">NOMBRE PROVEEDOR</th>");
			        out.println("<th data-field=\"rfc\" style=\"font-weight: bold; text-align: center;\">CONTRIBUYENTE O RFC</th>");
			        out.println("<th data-field=\"doc\" style=\"font-weight: bold; text-align: center;\">DOCUMENTO</th>");
			        out.println("<th data-field=\"tipo\" style=\"font-weight: bold; text-align: center;\">TIPO DOCUMENTO</th>");
			        out.println("<th data-field=\"fechadoc\" style=\"font-weight: bold; text-align: center;\">FECHA DE DOCUMENTO</th>");
			        out.println("<th data-field=\"fecha\" style=\"font-weight: bold; text-align: center;\">FECHA</th>");
			        out.println("<th data-field=\"app\" style=\"font-weight: bold; text-align: center;\">APLICACION</th>");
			        out.println("<th data-field=\"condpago\" style=\"font-weight: bold; text-align: center;\">CONDICION PAGO</th>");
			        out.println("<th data-field=\"moneda\" style=\"font-weight: bold; text-align: center;\">MONEDA</th>");
			        out.println("<th data-field=\"monto\" style=\"font-weight: bold; text-align: center;\">MONTO</th>");
			        out.println("<th data-field=\"saldo\" style=\"font-weight: bold; text-align: center;\">SALDO</th>");
			        out.println("<th data-field=\"montolocal\" style=\"font-weight: bold; text-align: center;\">MONTO LOCAL (PAGO)</th>");
			        out.println("<th data-field=\"saldolocal\" style=\"font-weight: bold; text-align: center;\">SALDO LOCAL</th>");
			        out.println("<th data-field=\"fecha_ult_cred\" style=\"font-weight: bold; text-align: center;\">FECHA ULTIMA DE CREDITO</th>");
			        out.println("<th data-field=\"chqimp\" style=\"font-weight: bold; text-align: center;\">CHEQUE IMPRESO</th>");
			        out.println("<th data-field=\"chqcta\" style=\"font-weight: bold; text-align: center;\">CHEQUE CUENTA</th>");		        
			        out.println("<th data-field=\"imp1\" style=\"font-weight: bold; text-align: center;\">IMPUESTO 1</th>");
			        out.println("<th data-field=\"asiento\" style=\"font-weight: bold; text-align: center;\">ASIENTO</th>");
			        out.println("<th data-field=\"notas\" style=\"font-weight: bold; text-align: center;\">NOTAS</th>");
			        out.println("</tr>");
			        out.println("</thead>");
		            out.println("<tbody>");
					
					do{
						out.println("<tr><td>" + rrAN.getString("PROVEEDOR") + "</td>" + "<td>"+ rrAN.getString("NOMBRE_PROVEEDOR") + "</td>");
						out.println("<td>" + rrAN.getString("CONTRIBUYENTE") + "</td>" + "<td>"+ rrAN.getString("DOCUMENTO") + "</td>");
						out.println("<td>" + rrAN.getString("TIPO") + "</td>" + "<td>"+ rrAN.getString("FECHA_DOCUMENTO") + "</td>");
						out.println("<td>"+ rrAN.getString("FECHA") + "</td>" + "<td>" + rrAN.getString("APLICACION") + "</td>");
						out.println("<td>"+ rrAN.getString("CONDICION_PAGO") + "</td>" + "<td>" + rrAN.getString("MONEDA") + "</td>");
						out.println("<td>"+ rrAN.getString("MONTO") + "</td>" + "<td>" + rrAN.getString("SALDO") + "</td>");
						out.println("<td>"+ rrAN.getString("MONTO_LOCAL") + "</td>" + "<td>" + rrAN.getString("SALDO_LOCAL") + "</td>");
						out.println("<td>"+ rrAN.getString("FECHA_ULT_CREDITO") + "</td>" + "<td>" + rrAN.getString("CHEQUE_IMPRESO") + "</td>");
						out.println("<td>"+ rrAN.getString("CHEQUE_CUENTA") + "</td>" + "<td>" + rrAN.getString("IMPUESTO1") + "</td>");
						out.println("<td>"+ rrAN.getString("ASIENTO") + "</td>" + "<td>" + rrAN.getString("NOTAS") + "</td>");
						
					}while (rrAN.next());
					stAN.close();
					rrAN.close();
					
					out.println("</tr>"+"</tbody>" + "</table>" + "</div>");
					
					/* DOCUMENTO CSV DE FACTURAS ANULADAS */
					
					try{
			            /* CREANDO EL ARCHIVO */
			            //La ruta del archivo es la del servidor
			            File archivo =  new File(rutaPagosAnuIVA);
						FileOutputStream file = new FileOutputStream (archivo);
						PrintWriter pw = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(file), "UTF-8"));
				    										
						/* LLENANDO EL ENCABEZADO */ 
						pw.append("PROVEEDOR,NOMBRE PROVEEDOR,CONTRIBUYENTE O RFC,DOCUMENTO,TIPO,FECHA DOCUMENTO, FECHA,")
						  .append("APLICACION, CONDICION DE PAGO, MONEDA, MONTO, SALDO, MONTO LOCAL, SALDO LOCAL,")
						  .append("FECHA ULTIMA DE CREDITO, CHEQUE IMPRESO, CHEQUE CUENTA, IMPUESTO 1, ASIENTO, NOTAS").println();
						
						/* LLENANDO DESDE SQL */
												
						ResultSet rrAExp = null;
			            Statement stAExp = currentCon.createStatement();
						String sqlAnExp = "EXEC bmv_SP_IVA_PAGADO_ANULADOS_EXPORTAR @datefrom='" + fechaDesde + "', @dateto='" + fechaHasta + "';";
						rrAExp = stAExp.executeQuery(sqlAnExp);
						System.out.println(sqlAnExp);
						
						if (!rrAExp.next()){
							System.out.println("No hay datos en el resultSet");
						}
						else{
							
							do{
								pw.append(rrAExp.getString("PROVEEDOR")).append(',').append(rrAExp.getString("NOMBRE_PROVEEDOR")).append(',')
								  .append(rrAExp.getString("CONTRIBUYENTE")).append(',').append(rrAExp.getString("DOCUMENTO")).append(',')
								  .append(rrAExp.getString("TIPO")).append(',').append(rrAExp.getString("FECHA_DOCUMENTO")).append(',')
								  .append(rrAExp.getString("FECHA")).append(',').append(rrAExp.getString("APLICACION")).append(',')
								  .append(rrAExp.getString("CONDICION_PAGO")).append(',').append(rrAExp.getString("MONEDA")).append(',')
								  .append(rrAExp.getString("MONTO")).append(',').append(rrAExp.getString("SALDO")).append(',')
								  .append(rrAExp.getString("MONTO_LOCAL")).append(',').append(rrAExp.getString("SALDO_LOCAL")).append(',')
								  .append(rrAExp.getString("FECHA_ULT_CREDITO")).append(',').append(rrAExp.getString("CHEQUE_IMPRESO")).append(',')
								  .append(rrAExp.getString("CHEQUE_CUENTA")).append(',').append(rrAExp.getString("IMPUESTO1")).append(',')
								  .append(rrAExp.getString("ASIENTO")).append(',').append(rrAExp.getString("NOTAS")).println();
								
							}while (rrAExp.next());
							
							stAExp.close();
							rrAExp.close();
						}
						pw.close();
						
		            }catch(IOException e){e.printStackTrace();}
					
					/***********BOTON DE EXPORTAR PARA PAGOS AMIB**********/
					
					%>
				
					<div class="row">
				 		<div class="col s12 m6 l3">
				 			<br>
				 			<br>
				 			<br>
				 			
				 			<div class="input-field col s12" style="float:left;">
				 				<a href="../files/pagos-anulados_IVA-AMIB.csv" download>
				 					<input type="submit"  name="btnExportarAnulados" id="btnExportarAnulados" value="Exportar" title="Exportar" style="float:left;" Width=2em class="waves-effect waves-teal yellow blue btn grupobmv-2">
				 				</a>
							</div>
						</div>
					</div>
										
					<%
					
					
				}//Fin Facturas Anuladas									
				
			}//Fin erroCheck btnConsultar
        }//Fin Logica btnConsultar  
	%>
	
	<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script src="../js/materialize.js"></script>
	<script type="text/javascript">
	
		$('.datepicker').pickadate({
			selectMonths: true, // Creates a dropdown to control month
		    selectYears: 18, // Creates a dropdown of 15 years to control year
		});
		
		$('.datepicker').on('change', function(){
			if ($(this).attr('id') === 'datefrom'){
				//alert($(this).val())
				$('#dateto').pickadate('picker').set('min',$(this).val());
			}
			if ($(this).attr('id') === 'dateto'){
				$('#datefrom').pickadate('picker').set('max',$(this).val());
			}
		});
		
		$(document).ready(function(){
			$('.tooltipped').tooltip({delay: 50});
		}); 
		
	</script>
</body>
</html>
 		