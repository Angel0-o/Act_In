<%@page import="java.io.*"%>
<%@page import="java.sql.Statement"%>
<%@page import="bs.ConnectionManager"%>
<%@page import="bs.ConnectionManagerOracle"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="modelo.UserBean"%>
<%@page import="bs.Pool"%>
<%@page import="controlador.Validation" %>
<%@page import="controlador.Paths" %>


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
		if (usuario == null) 
		{
			response.sendRedirect("/IPAT");
		}
		
		//ruta del archivo CSV a crear
		String rutaFileExcel =//"C:/apache-tomcat-7.0.61-windows-x64/apache-tomcat-7.0.61/webapps/IPAT/files/bitacora-AMIB.csv"; 
							  //"C:/Users/gvazquez/Desktop/PruebaCSV/bitacora-AMIB.csv";
								"O:/Users/gvazquez/Documents/workIPAT/IPAT/WebContent/files/bitacora-AMIB.csv";
		String rutaFileButton = //"http://10.100.229.51:8040/IPAT/files/bitacora-AMIB.xlsx";
								//"http://localhost:8040/IPAT//files/bitacora-AMIB.xlsx";
								"./bitacora-AMIB.csv";
								
		//out.println(rutaFileExcel);
	%>
	
	<script type="text/javascript">
		function changeFunc()
		{
			var selectBox = document.getElementById("selectBox");
			var selectedValue = selectBox.options[selectBox.selectedIndex].value;
 			window.history.replaceState('Nuevo', 'Title', '/IPAT/pages/bitacora.jsp?usuario='+ selectedValue);
 			location.reload(true);
 		}
		window.onload = function()
		{
			var getUrlParameter = function getUrlParameter(sParam)
			{
				var sPageURL = decodeURIComponent(window.location.search.substring(1)),	sURLVariables = sPageURL.split('&'), sParameterName, i;
				for (i = 0; i < sURLVariables.length; i++)
				{
					sParameterName = sURLVariables[i].split('=');
					if (sParameterName[0] === sParam)
					{
		            	return sParameterName[1] === undefined ? true : sParameterName[1];
		        	}
		    	}
			};
			var dateValue1 = getUrlParameter('datefrom');
			var dateValue2 = getUrlParameter('dateto');
			var type = getUrlParameter('type_request');
			if (dateValue1 !== null) {$('#datefrom').val(dateValue1);}
			if (dateValue2 !== null) {$('#dateto').val(dateValue2);}
			if (type != null)
			{
				$('#select_type').val(type);
				$('#select_type').change();
			}
   		}

	</script>
	<h3>Bitácora</h3>
	<form>
		<div class="row" style="border-style: solid; border-color: #cccccc; border-radius: 25px; max-width: 1300px; margin:0 20px 30px 0;">
            <div class="col grid-example s12 m12 l5">
	            <br><br>
	            <div class="row">
					<div class="input-field col s12 m4 l4">
						DESDE:<br>
						<input id="datefrom" type="date" class="datepicker" name="datefrom" placeholder="YYYY-MM-DD" style="width:149px">
						<span></span>
			        </div>
			        <div class="input-field col s12 m4 l4" style="float:left">
			        	HASTA:<br>
			        	<input id="dateto" type="date" class="datepicker" name="dateto" placeholder="YYYY-MM-DD" style="width:149px">
			        </div>
			        <div class="input-field col s12 m4 l4" style="float:left">
			        	<br>
			        	<select class="selectpicker" name='type_request' id='select_type'>
			        		<option value="todo" style="width:149px">TODO</option>
			        		<option value="facturas" style="width:149px">FACTURAS</option>
			        		<option value="complementos" style="width:149px">COMPLEMENTOS DE PAGO</option>
			        		<option value="nominas" style="width:149px">NOTAS DE CRÉDITO</option>
			        	</select> 	
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
		String type = "";
		int ini = 0;//indice de inicio para la consulta
		int fin = 0;//indice final para la consulta
		
		if(request.getParameter("btnConsultar") != null)
		{
			fechaDesde = request.getParameter("datefrom");
			fechaHasta = request.getParameter("dateto");
			type = request.getParameter("type_request");
			//Se hace una transformacion de la peticion
			//1.- Para factura
			//2.- Para complementos
			//3.- Para notas de crédito
			if(type.equals("todo"))
			{
				ini = 1;
				fin = 3;
			}
			else if(type.equals("facturas"))
				ini=fin=1;
			else if (type.equals("complementos"))
				ini=fin=2;
			else
				ini=fin=3;
			/* IMPRESION DE ERRORES */			
			if(fechaDesde=="" || fechaHasta=="")
			{
				out.println("<script type=\"text/javascript\" class=\"alert-warning\">" + "alert('Selecciona una fecha de inicio y una fecha de fin.');" + "</script>");
				errorCheck = true;
			}
			
			if(!errorCheck)
			{
				try
				{
            	/**************LLENANDO TABLA HTML*****************/
	            	int numConsultas = fin-ini+1;
	            	ResultSet[] rrFR = new ResultSet[numConsultas];
	            	Statement[] stFR = new Statement[numConsultas];
	    			
					/* CREANDO EL ARCHIVO */
	
	            	 //La ruta del archivo es la del servidor
		            File archivo =  new File(rutaFileExcel);
					FileOutputStream file = new FileOutputStream (archivo);
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(file), "UTF-8"));
					
					String headers[][] = { {},
											{"FOLIO","TIPO","NUMERO DE CLIENTE","FECHA DE ELABORACION","RAZON SOCIAL",
											"MONTO TOTAL", "MONEDA","CORREOS DE ENTREGA","FECHA DE ENVIO","ENVIADO",
											"TIMBRADO","ERRORES EN TIMBRADO"},
											
											{"FOLIO","FACTURA ASOCIADA","NUMERO DE CLIENTE","FECHA DE ELABORACION","RAZON SOCIAL",
												"MONTO TOTAL", "MONEDA","CORREOS DE ENTREGA","FECHA DE ENVIO","ENVIADO",
												"TIMBRADO","ERRORES EN TIMBRADO"},
												
											{"FOLIO","NUMERO DE CLIENTE","FECHA DE ELABORACION","RAZON SOCIAL",
												"MONTO TOTAL", "MONEDA","CORREOS DE ENTREGA","FECHA DE ENVIO","ENVIADO",
												"TIMBRADO","ERRORES EN TIMBRADO"}
										 };
					boolean crear = true;
					int numSinDatos = 0;
					/* IMPRESION DE DATOS SELECCIONADOS */	        
	            	for(int i=0,opcion=ini;opcion<=fin;++i,++opcion)
	            	{
	            		rrFR[i] = null;
		            	stFR[i] = currentCon.createStatement();
		            	
						String sqlTabla = "EXEC bmv_SP_BITACORA @datefrom='" + fechaDesde + "', @dateto='" + fechaHasta + "',@type_req="+opcion+";";
						rrFR[i] = stFR[i].executeQuery(sqlTabla);
						System.out.println(sqlTabla);
						if (!rrFR[i].next())
						{
							if(numSinDatos == numConsultas-1)
								out.println("<script type=\"text/javascript\" class=\"alert-warning\"> " + "alert('No hay datos para el rango de fechas seleccionado');" + "</script>");
							numSinDatos++;
						}
						else
						{
							if(crear)
							{
								out.println("<table>");
								out.println("<tbody>");
								out.println("<tr>");
								out.println("<td style=\"font-size:14px\"><b>Rango de fechas seleccionados: </b>" + fechaDesde + " - " + fechaHasta + "</td>");
								out.println("</td>");
								out.println("</tr>");
								out.println("</tbody>");
								out.println("</table>");
								crear = false;
							}
							
							if(opcion == 1)
							{
								out.println("<h1>FACTURAS</h1>");
								pw.append("FACTURAS").println();
							}
							else if(opcion == 2)
							{
								out.println("<h1>COMPLEMENTOS DE PAGOS</h1>");
								pw.append("COMPLEMENTOS DE PAGOS").println();
							}
							else 
							{
								out.println("<h1>NOTAS DE CRÉDITO</h1>");
								pw.append("NOTAS DE CREDITO").println();
							}
							
							out.println("<div class=\"col grid-example s12 m6 l4\" style=\"overflow-x: scroll; overflow-y: scroll; max-width: 1300px; max-height:500px;\">");
							out.println("<iframe id=\"my_iframe\" style=\"display:none;\"></iframe>");
							out.println("<table  id=\"table_wrapper\" class=\"striped\">");
							
							out.println("<tr class=\"grey darken-3\" style=\"color: rgba(255, 255, 255, 0.9); \">");
							for(int j=0;j<headers[opcion].length;++j)
					        {
					        	out.println("<th style=\"font-weight: bold; text-align: center;\">"+headers[opcion][j]+"</th>");
					        	pw.append(headers[opcion][j]);
					        	if(j<headers[opcion].length-1)
					        		pw.append(",");
					        }
					        out.println("</tr>");
					        pw.println();
					        out.println("<tbody>");	
					        do
					        {
					        	String estatusEnvio = Validation.getCorrectValue(rrFR[i].getString("ENVIADO"));
								String estatusTimbrado = Validation.getCorrectValue(rrFR[i].getString("ESTADO"));
								String estatusErrores = Validation.getCorrectValue(rrFR[i].getString("CONTIENE_ERRORES"));
								if(estatusEnvio.equalsIgnoreCase("S"))
									estatusEnvio = "SI";
								else
									estatusEnvio = "NO";
								
								if(estatusTimbrado.equalsIgnoreCase("F"))
									estatusTimbrado = "NO";
								else
									estatusTimbrado = "SI";
								
								if(estatusErrores.equalsIgnoreCase(""))
									estatusErrores = "NO";
								else
									estatusErrores = "SI";
					        	if(opcion == 1) //FACTURAS
					        	{
					        		String folio = Validation.getCorrectValue(rrFR[i].getString("FACTURA"));
									String tipo = Validation.getCorrectValue(rrFR[i].getString("RUBRO5"));
									String numCliente = Validation.getCorrectValue(rrFR[i].getString("CLIENTE"));
									String fechaElab = Validation.getDate(rrFR[i].getString("FECHA"));
									String razonSocial = Validation.getCorrectValue(rrFR[i].getString("NOMBRE_CLIENTE"));
									String montoTotal =  Validation.getCorrectMoney((rrFR[i].getString("TOTAL_FACTURA")));
									String moneda = Validation.getCorrectValue(rrFR[i].getString("MONEDA"));
									String correoEntrega = Validation.getCorrectValue(rrFR[i].getString("OBSERVACIONES"));
									String fechaEnvio = Validation.getDate(rrFR[i].getString("FECHA_RECIBIDO"));
									if(Validation.isEmptyField(correoEntrega))
									{
										estatusEnvio = "NO";
										fechaEnvio = "";
									}
	
									out.println("<tr><td class='cell-left' style='width:100px;'>" + folio + "</td>" + "<td class='cell-left' style='width:100px;'>"+ tipo + "</td>");
									out.println("<td class='cell-center' style='width:100px;'>" + numCliente + "</td>" + "<td class='cell-center' style='width:100px;'>"+ fechaElab + "</td>");
									out.println("<td class='cell-left' style='width:250px;'>" + razonSocial + "</td>" + "<td class= 'cell-right' style='width:100px;'>"+ montoTotal + " &nbsp;&nbsp;</td>");
									out.println("<td class='cell-center'style='width:100px;' >"+ moneda + "</td>" + "<td class='cell-left' style='width:350px;'>" + correoEntrega.replaceAll(";", "<br>").replaceAll(",","<br>") + "</td>");
									out.println("<td class='cell-center' style='width:100px;'>"+ fechaEnvio + "</td>" + "<td class='cell-center' style='width:100px;'>" + estatusEnvio + "</td>");
									out.println("<td class='cell-center' style='width:100px;'>"+ estatusTimbrado + "</td>" + "<td class='cell-center' style='width:100px;'>" + estatusErrores + "</td></tr>");
									
									//LLENAR CAMPOS ARCHIVO CSV
									pw.append(folio).append(',').append(tipo).append(',').append(numCliente+"\t").append(',')
									  .append(fechaElab).append(',').append(razonSocial.replaceAll(",","")).append(',').append("=MONEDA("+montoTotal+")").append(',')
									  .append(moneda).append(',').append(correoEntrega.replaceAll(",",";").replaceAll("\n","").replaceAll("\r","").trim()).append(',').append(fechaEnvio).append(',').append(estatusEnvio).append(',')
									  .append(estatusTimbrado).append(',').append(estatusErrores).println();
					        	}
					        	else if(opcion == 3) //Notas de credito
					        	{
					        		String folio = Validation.getCorrectValue(rrFR[i].getString("DOCUMENTO"));
					        		String numCliente = Validation.getCorrectValue(rrFR[i].getString("CLIENTE"));
									String fechaElab = Validation.getDate(rrFR[i].getString("FECHA"));
									String razonSocial = Validation.getCorrectValue(rrFR[i].getString("NOMBRE_CLIENTE"));
									String montoTotal =  Validation.getCorrectMoney((rrFR[i].getString("MONTO")));
									String moneda = Validation.getCorrectValue(rrFR[i].getString("MONEDA"));
									String correoEntrega = Validation.getCorrectValue(rrFR[i].getString("EMAIL_EMISOR"));
									String fechaEnvio = Validation.getDate(rrFR[i].getString("FECHA_HORA"));
									
									if(Validation.isEmptyField(correoEntrega))
									{
										estatusEnvio = "NO";
										fechaEnvio = "";
									}
									
									out.println("<tr><td class='cell-left' style='width:100px;'>" + folio + "</td>");
									out.println("<td class='cell-center' style='width:100px;'>" + numCliente + "</td>" + "<td class='cell-center' style='width:100px;'>"+ fechaElab + "</td>");
									out.println("<td class='cell-left' style='width:250px;'>" + razonSocial + "</td>" + "<td class= 'cell-right' style='width:100px;'>"+ montoTotal + " &nbsp;&nbsp;</td>");
									out.println("<td class='cell-center'style='width:100px;' >"+ moneda + "</td>" + "<td class='cell-left' style='width:350px;'>" + correoEntrega.replaceAll(";", "<br>").replaceAll(",","<br>") + "</td>");
									out.println("<td class='cell-center' style='width:100px;'>"+ fechaEnvio + "</td>" + "<td class='cell-center' style='width:100px;'>" + estatusEnvio + "</td>");
									out.println("<td class='cell-center' style='width:100px;'>"+ estatusTimbrado + "</td>" + "<td class='cell-center' style='width:100px;'>" + estatusErrores + "</td></tr>");
									//LLENAR CAMPOS ARCHIVO CSV
									pw.append(folio).append(',').append(numCliente+"\t").append(',')
									  .append(fechaElab).append(',').append(razonSocial.replaceAll(",","")).append(',').append("=MONEDA("+montoTotal+")").append(',')
									  .append(moneda).append(',').append(correoEntrega.replaceAll(",",";").replaceAll("\n","").replaceAll("\r","").trim()).append(',').append(fechaEnvio).append(',').append(estatusEnvio).append(',')
									  .append(estatusTimbrado).append(',').append(estatusErrores).println();
					        	}
					        	
					        	else if(opcion == 2) //Complementos de Pagos
					        	{
					        		String folio = Validation.getCorrectValue(rrFR[i].getString("DOCUMENTO"));
					        		String factura = Validation.getCorrectValue(rrFR[i].getString("FACTURA_AS"));
					        		String numCliente = Validation.getCorrectValue(rrFR[i].getString("CLIENTE"));
									String fechaElab = Validation.getDate(rrFR[i].getString("FECHA"));
									String razonSocial = Validation.getCorrectValue(rrFR[i].getString("NOMBRE_CLIENTE"));
									String montoTotal =  Validation.getCorrectMoney((rrFR[i].getString("MONTO")));
									String moneda = Validation.getCorrectValue(rrFR[i].getString("MONEDA"));
									String correoEntrega = Validation.getCorrectValue(rrFR[i].getString("EMAIL_EMISOR"));
									String fechaEnvio = Validation.getDate(rrFR[i].getString("FECHA_HORA"));
									
									if(Validation.isEmptyField(correoEntrega))
									{
										estatusEnvio = "NO";
										fechaEnvio = "";
									}
									
									out.println("<tr><td class='cell-left' style='width:100px;'>" + folio + "</td><td class = 'cell-left' style = 'width:100px;'>"+factura+"</td>");
									out.println("<td class='cell-center' style='width:100px;'>" + numCliente + "</td>" + "<td class='cell-center' style='width:100px;'>"+ fechaElab + "</td>");
									out.println("<td class='cell-left' style='width:250px;'>" + razonSocial + "</td>" + "<td class= 'cell-right' style='width:100px;'>"+ montoTotal + " &nbsp;&nbsp;</td>");
									out.println("<td class='cell-center'style='width:100px;' >"+ moneda + "</td>" + "<td class='cell-left' style='width:350px;'>" + correoEntrega.replaceAll(";", "<br>").replaceAll(",","<br>") + "</td>");
									out.println("<td class='cell-center' style='width:100px;'>"+ fechaEnvio + "</td>" + "<td class='cell-center' style='width:100px;'>" + estatusEnvio + "</td>");
									out.println("<td class='cell-center' style='width:100px;'>"+ estatusTimbrado + "</td>" + "<td class='cell-center' style='width:100px;'>" + estatusErrores + "</td></tr>");
									//LLENAR CAMPOS ARCHIVO CSV
									pw.append(folio).append(',').append(factura).append(',').append(numCliente+"\t").append(',')
									  .append(fechaElab).append(',').append(razonSocial.replaceAll(",","")).append(',').append("=MONEDA("+montoTotal+")").append(',')
									  .append(moneda).append(',').append(correoEntrega.replaceAll(",",";").replaceAll("\n","").replaceAll("\r","").trim()).append(',').append(fechaEnvio).append(',').append(estatusEnvio).append(',')
									  .append(estatusTimbrado).append(',').append(estatusErrores).println();
					        	}
					        	//pw.flush();
					        }while (rrFR[i].next());
					        stFR[i].close();
							rrFR[i].close();
							out.println("</tbody>" + "</table>" + "</div><br>");
							pw.println();
							pw.println();
						}//Fin else existen registros
	            	}//Fin for consultas
	            	pw.close();
	      			file.close();
	            	if(numSinDatos!=numConsultas)
	            	{
	            		/*BOTON DE EXPORTAR BITÁCORA*/
						%>
							<div class="row">
						 		<div class="col s12 m6 l3">
						 			<br>
						 			<br>
						 			<br>
						 			
						 			<div class="input-field col s12" style="float:left;">
						 				<a href="../files/bitacora-AMIB.csv" download>
						 					<input type="submit"  name="btnExportar" id="btnExportar" value="Exportar" title="Exportar" style="float:left;" Width=2em class="waves-effect waves-teal yellow blue btn grupobmv-2">
						 				</a>
									</div>
								</div>
							</div>
						<%
	            	}
				}//Fin try
				catch(Exception e)
				{
					e.printStackTrace();
				}
            }//Fin if errorCheck
        }// Fin if request btnConsultar   		
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
