package controlador;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.UserDAO;
import bs.Pool;
import bs.Utilidades;

/**
 * Servlet implementation class ConfirmarServlet
 */
@WebServlet("/ConfirmarServlet")
public class ConfirmarServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
		if (request.getParameter("Actualizar") != null) {
			
			
			int q = 0, r = 0;
			String idAInsertar = "";

			if (Pool.excepcionCLIENTE.length() == 0 && Pool.excepcionDescripcionArt.length() == 0 
					&& Pool.excepcionEXISTEVALOR.length() == 0 && Pool.excepcionFECHA.length() == 0 
					&& Pool.excepcionID.length() == 0 && Pool.excepcionIVA.length() == 0
					&& Pool.excepcionUID.length() == 0 && UserDAO.excepcionPRECIO.length() == 0
					&& UserDAO.excepcionRUBRO5.length() == 0 && UserDAO.excepcionCANTIDAD.length() == 0	
					&& UserDAO.excepcionVENDEDOR.length() == 0 && UserDAO.excepcionCOBRADOR.length() == 0 
					&& Pool.excepcionOBSERVACIONES.length() == 0) {

				System.out.println("NO SE ENCONTRARON EXCEPCIONES AL IMPORTAR DATOS");

				/* ---------------------------------------------------------- */

				String sqlPedido = "";
				String sqlPedido_Linea = "";

				Calendar fecha = Calendar.getInstance();
				java.sql.Date Fecha = new java.sql.Date(fecha.getTime().getTime());

				String delimitadorGuion = "-";
				String[] fechaSinGuiones = Fecha.toString().split(delimitadorGuion);
				String fechaF = fechaSinGuiones[0] + fechaSinGuiones[1]	+ fechaSinGuiones[2];
				Calendar calendar = Calendar.getInstance();
				Timestamp Hora = new java.sql.Timestamp(calendar.getTime().getTime());
				String delimitadorEspacio = " ";
				String[] horaSinEspacios = Hora.toString().split(delimitadorEspacio);
				String horaF = fechaF + " " + horaSinEspacios[1].substring(0, 5);
				System.out.println("FECHA F (SIN GUIONES) = "+fechaF);
				System.out.println("HORA F  = "+horaF);

				String fecha_ult_cancelac = "19800101";
				
				String sqlUpdateID = "";
				
				String errorJSPservlet = null;

				for (q = 0; q < UserDAO.listPedidos.size(); q++) {
					//Verificar si el Pedido es válido, porque puede que TODOS sus detalles tengan 0 en precio
					// Punto No.6 
					if(!UserDAO.listPedidos.get(q).getPedidoValido()){
						System.out.println("PEDIDO VALIDO 1: " + UserDAO.listPedidos.get(q));
						continue;
					}
					
					//verificar si existe valor en el último pedido
					if(UserDAO.listPedidos.get(q).getTotalMercaderia() == null){
						System.out.println("PEDIDO VALIDO 2: " + UserDAO.listPedidos.get(q));
						break;
					}

					/* -----------------rescatar ID---------------------- */
					idAInsertar = Pool.rescatarID();
					
					if(idAInsertar.equals("")){
						UserDAO.errorJSP = "Error en el ID";
						response.sendRedirect("pages/Error.jsp");
					}
					
					if(Pool.empresa.equals("AMIB2003")){
						idAInsertar = Utilidades.idAmib(idAInsertar);
					}else{
						UserDAO.errorJSP = "Error inesperado 2. Contacte a su administrador.\nEmpresa no soportada, se necesita discriminar sus parámetros";
						response.sendRedirect("pages/Error.jsp");
					}
	
					//insertar el ultimoID al pedido actual
					UserDAO.listPedidos.get(q).setPedido(idAInsertar);
					/* -----------------rescatar ID---------------------- */
					
					sqlPedido = "INSERT INTO "
							+ Pool.empresa
							+ ".PEDIDO (PEDIDO, ESTADO, FECHA_PEDIDO, FECHA_PROMETIDA, FECHA_PROX_EMBARQU, FECHA_ULT_EMBARQUE, FECHA_ULT_CANCELAC, FECHA_ORDEN, "
							+ "TARJETA_CREDITO, EMBARCAR_A, DIREC_EMBARQUE, DIRECCION_FACTURA, RUBRO1, RUBRO2, RUBRO3, RUBRO4, RUBRO5, "
							+ "OBSERVACIONES, COMENTARIO_CXC, TOTAL_MERCADERIA, MONTO_ANTICIPO, MONTO_FLETE, MONTO_SEGURO, MONTO_DOCUMENTACIO, "
							+ "TIPO_DESCUENTO1, TIPO_DESCUENTO2, MONTO_DESCUENTO1, MONTO_DESCUENTO2, "
							+ "PORC_DESCUENTO1, PORC_DESCUENTO2, TOTAL_IMPUESTO1, TOTAL_IMPUESTO2, TOTAL_A_FACTURAR, PORC_COMI_VENDEDOR, PORC_COMI_COBRADOR, TOTAL_CANCELADO, "
							+ "TOTAL_UNIDADES, IMPRESO, FECHA_HORA, DESCUENTO_VOLUMEN, TIPO_PEDIDO, MONEDA_PEDIDO, VERSION_NP, AUTORIZADO, "
							+ "DOC_A_GENERAR, CLASE_PEDIDO, MONEDA, NIVEL_PRECIO, COBRADOR, RUTA, USUARIO, CONDICION_PAGO, BODEGA, ZONA, VENDEDOR, "
							+ "CLIENTE, CLIENTE_DIRECCION, CLIENTE_CORPORAC, CLIENTE_ORIGEN, PAIS, SUBTIPO_DOC_CXC, TIPO_DOC_CXC, BACKORDER, CONTRATO, "
							+ "PORC_INTCTE, DESCUENTO_CASCADA, TIPO_CAMBIO, FIJAR_TIPO_CAMBIO, ORIGEN_PEDIDO, DESC_DIREC_EMBARQUE, DIVISION_GEOGRAFICA1, "
							+ "DIVISION_GEOGRAFICA2, BASE_IMPUESTO1, BASE_IMPUESTO2, NOMBRE_CLIENTE, FECHA_PROYECTADA, TIPO_DOCUMENTO, "
							+ "VERSION_COTIZACION, DES_CANCELA_COTI, CAMBIOS_COTI, COTIZACION_PADRE, "
							+ "NoteExistsFlag, RecordDate, CreatedBy, UpdatedBy, CreateDate) values (" 
							+ "'" + UserDAO.listPedidos.get(q).getPedido() // PEDIDO
							+ "', 'N" // ESTADO
							+ "', '"+ UserDAO.listPedidos.get(q).getFecha() // FECHA_PEDIDO - Excel
							+ "', '"+ UserDAO.listPedidos.get(q).getFecha() // FECHA_PROMETIDA - Excel
							+ "', '"+ UserDAO.listPedidos.get(q).getFecha() // FECHA_PROX_EMBARQU - Excel
							+ "', '"+ UserDAO.listPedidos.get(q).getFecha() // FECHA_ULT_EMBARQUE - Excel
							+ "', '"+ fecha_ult_cancelac // FECHA_ULT_CANCELAC
							+ "', '"+ UserDAO.listPedidos.get(q).getFecha() //FECHA_ORDEN - Excel
							+ "', ''"//TCREDITO
							+ ", '"+ UserDAO.listClientes.get(q).getId() // EMBARCAR_A
							+ "', 'ND'" // DIREC_EMBARQUE
							+ ", '"+ UserDAO.listClientes.get(q).getDireccion()
							+ "', '"+ UserDAO.listPedidos.get(q).getRubro1()
							+ "', '"+ UserDAO.listPedidos.get(q).getRubro2()
							+ "', '"+ UserDAO.listPedidos.get(q).getRubro3()
							+ "', '"+ UserDAO.listPedidos.get(q).getRubro4()
							+ "', '"+ UserDAO.listPedidos.get(q).getRubro5() //ADDENDA
							+ "', '"+ UserDAO.listPedidos.get(q).getObservaciones() // OBSERVACIONES
							+ "', '"+ UserDAO.listPedidos.get(q).getNotas() // COMENTARIO CXC
							+ "', "+ UserDAO.listPedidos.get(q).getTotalMercaderia()
							+ ", "+0+", "+0+", "+0+ ", "+ 0	+ ", 'P'," // "TIPO_DESCUENTO1,							
							+ "'P'," // TIPO_DESCUENTO2
							+ 0	+ ", "// MONTO_DESCUENTO1,
							+ 0	+ ", "// MONTO_DESCUENTO2"
							+ 0 + ", "// PORC_DESCUENTO1,
							+ 0 + ", "// PORC_DESCUENTO2,
							+ UserDAO.listPedidos.get(q).getTotalIVAs()	+ ", "// TOTAL_IMPUESTO1,
							+ 0	+ ", "// TOTAL_IMPUESTO2,
							+ (UserDAO.listPedidos.get(q).getTotalMercaderia().add(UserDAO.listPedidos.get(q).getTotalIVAs()))+ ", "// TOTAL_A_FACTURAR,
							+ 0	+ ", "// PORC_COMI_VENDEDOR,
							+ 0	+ ", "// PORC_COMI_COBRADOR,
							+ 0 + ", "// TOTAL_CANCELADO*/
							+ UserDAO.listPedidos.get(q).getTotalUnidades()
							+ ", 'N', '"+ horaF	+ "', "	+ 0	+ ", 'N', '"+ UserDAO.listPedidos.get(q).getMoneda()+ "'"
							+ ", 1, 'N', 'F', 'N', '"+ UserDAO.listPedidos.get(q).getMoneda() + "', ";

					if (UserDAO.listPedidos.get(q).getMoneda().equals("L")) {
						sqlPedido += "'ND-LOCAL', ";
					} else {
						sqlPedido += "'ND-DOLAR', ";
					}

					sqlPedido += "'" + UserDAO.listPedidos.get(q).getCobrador() + "'" + ", " //cobrador
							+ "'ND', " //ruta
							+ "'" + Pool.usuarioConectado+ "', '"
							//+ UserDAO.listClientes.get(q).getCondicionPago() +"', ";//BODEGA
							+ UserDAO.listPedidos.get(q).getCondPago() + "', ";//Condicion Pago desde CSV
							if(Pool.empresa.equals("AMIB2003")) {
								sqlPedido += "'A6VE', ";
							}
					sqlPedido += "'ND', '" + UserDAO.listPedidos.get(q).getVendedor() + "', " //vendedor
							+ "'" + UserDAO.listClientes.get(q).getId() + "', "
							+ "'"+ UserDAO.listClientes.get(q).getId()+ "', "
							+ "'"+ UserDAO.listClientes.get(q).getId()+ "', "
							+ "'"+ UserDAO.listClientes.get(q).getId()+ "', "
							+ "'"+ UserDAO.listClientes.get(q).getPais()+ "', "	+ 0 //SUBTIPO_DOC_CXC, 
							+ ", 'FAC" //TIPO_DOC_CXC
							+ "','N', '', "	+ 0	+ ", 'N', "	+ 0	+ ", 'N', 'F', '', '', '', "+ 0	+ ", "+ 0+ ", "
							+ "'"+ UserDAO.listClientes.get(q).getNombre()+ "', "
							+ "'"+ UserDAO.listPedidos.get(q).getFecha() + "', "// FECHA_PROYECTADA - Excel
							+ "'P', " //TIPO_DOCUMENTO, 
							+ "'', '', '', ''," //VERSION_COTIZACION,DES_CANCELA_COTI, CAMBIOS_COTI, COTIZACION_PADRE
							// NoteExistsFlag, RecordDate, CreatedBy, UpdatedBy,
							// CreateDate"
							+ 0	+ ", ' ', ' ', ' ', ' ');";

					System.out.println("sqlPedido = " + sqlPedido);

					errorJSPservlet = Pool.executeUID(sqlPedido); 
					if(errorJSPservlet != null){
						UserDAO.errorJSP = errorJSPservlet;
						response.sendRedirect("pages/Error.jsp");
					}
						

					for (r = 0; r < UserDAO.listPedidos.get(q)
							.getListPedidosLinea().size(); r++) {
						
						//si existe error en INSERTAR PEDIDO_LINEA terminar proceso
						if(!UserDAO.listPedidos.get(q).getListPedidosLinea().get(r).getPedidoLineaValido()){
							continue;
						}
						
						UserDAO.listPedidos.get(q).getListPedidosLinea().get(r).setPedido(idAInsertar);

						sqlPedido_Linea = "INSERT INTO "+ Pool.empresa + ".PEDIDO_LINEA ("
								+ "PEDIDO, PEDIDO_LINEA, BODEGA, ARTICULO, ESTADO, FECHA_ENTREGA, "
								+ "LINEA_USUARIO, PRECIO_UNITARIO, CANTIDAD_PEDIDA, CANTIDAD_A_FACTURA, CANTIDAD_FACTURADA, "
								+ "CANTIDAD_RESERVADA, CANTIDAD_BONIFICAD, CANTIDAD_CANCELADA, TIPO_DESCUENTO, MONTO_DESCUENTO, "
								+ "PORC_DESCUENTO, DESCRIPCION, FECHA_PROMETIDA, NoteExistsFlag, RecordDate, CreatedBy, "
								+ "UpdatedBy, CreateDate) " + "values (" 
								+ "'"+ UserDAO.listPedidos.get(q).getListPedidosLinea().get(r).getPedido()+"', "
								+ UserDAO.listPedidos.get(q).getListPedidosLinea().get(r).getPedido_linea()	+ ", ";
								//BODEGA
								if(Pool.empresa.equals("AMIB2003")) {
									sqlPedido_Linea += "'A6VE', ";
								}
						sqlPedido_Linea +=	"'"	+ UserDAO.listPedidos.get(q).getListPedidosLinea().get(r).getArticulo()+ "', "
								+ "'N', "// ESTADO
								+ "'"+ UserDAO.listPedidos.get(q).getFecha()+ "', "
								+ 0 + ", "// LINEA_USUARIO
								+ UserDAO.listPedidos.get(q).getListPedidosLinea().get(r).getPrecio_unitario()
								+ ", '"	+ UserDAO.listPedidos.get(q).getListPedidosLinea().get(r).getCantidadL()+ "', "
								+ "'"+ UserDAO.listPedidos.get(q).getListPedidosLinea().get(r).getCantidadL()+ "', "
								+ 0	+ ", "// CANTIDAD_FACTURADA
								+ 0	+ ", "// CANTIDAD_RESERVADA
								+ 0	+ ", "// CANTIDAD_BONIFICAD
								+ 0	+ ", "// CANTIDAD_CANCELADA
								+ "'P', "+ 0 + ", "// MONTO_DESCUENTO
								+ 0	+ ", "// PORC_DESCUENTO
								+ "'" + UserDAO.listPedidos.get(q).getListPedidosLinea().get(r).getDescripcion() + "', "
								+ "'" + UserDAO.listPedidos.get(q).getFecha() + "', "
								+ 0 + ", ' ', ' ', ' ', ' ');";

						System.out.println("sqlPedido_Linea = " + sqlPedido_Linea);

						
						errorJSPservlet = Pool.executeUID(sqlPedido_Linea);
						if(errorJSPservlet != null)
						{
							UserDAO.errorJSP = errorJSPservlet;
							response.sendRedirect("pages/Error.jsp");
						}

					}// for pedido_linea
					
					sqlUpdateID = "UPDATE "	+ Pool.empresa	+ ".CONSECUTIVO_FA SET VALOR_CONSECUTIVO='"
							+ idAInsertar + "' WHERE CODIGO_CONSECUTIVO ='"	+ Pool.consecutivo	+ "'";
					
					System.out.println("sqlUpdateID = " + sqlUpdateID);
					
					errorJSPservlet = Pool.executeUID(sqlUpdateID);
					if(errorJSPservlet != null){
						UserDAO.errorJSP = errorJSPservlet;
						response.sendRedirect("pages/Error.jsp");
					}
					
					
				}// for

				response.sendRedirect("pages/Correcto.jsp");
				
			} else {
//				errorProceso(request, response);		
				UserDAO.errorJSP = "Verifica tus datos, al parecer hay un error.";
				response.sendRedirect("pages/Error.jsp");
			}
			
		} else if (request.getParameter("Cancelar") != null){
			
//			System.out.println("btn CANCELAR");
			response.sendRedirect("pages/Importar.jsp");
			
		}
			
		
	}//doPost

}
