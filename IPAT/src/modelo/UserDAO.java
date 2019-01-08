package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bs.Pool;
import bs.Struct;

import com.opencsv.CSVReader;

import modelo.UserBean;

public class UserDAO {

	public static int a1 = 0;
	public static int a2 = 0;
	public static int a3 = 0;
	public static int a4 = 0;
	public static int a5 = 0;
	public static int a6 = 0;
	public static int a7 = 0;
	public static int a8 = 0;
	public static int a9 = 0;
	public static int a10 = 0;
	public static int a11 = 0;
	public static int a12 = 0;
	public static int a13 = 0;

	public static String errores = new String();
	public static String cadenaErrores = new String();
	// cliente no válido
	public static List<Struct> listD3 = new ArrayList<Struct>();
	// fecha no valida
	public static List<Struct> listF3 = new ArrayList<Struct>();
	// cobrador
	public static List<Struct> listN3 = new ArrayList<Struct>();
	// vendedor
	public static List<Struct> listO3 = new ArrayList<Struct>();
	// observaciones
	public static List<Struct> listP3 = new ArrayList<Struct>();
	// Condicion Pago
	public static List<Struct> listQ3 = new ArrayList<Struct>();
	// artículo
	public static List<Struct> listB4 = new ArrayList<Struct>();
	// cantidad artículos
	public static List<Struct> listC1 = new ArrayList<Struct>();
	// precio
	public static List<Struct> listD4 = new ArrayList<Struct>();
	// IVA
	public static List<Struct> listF4 = new ArrayList<Struct>();
	// ASIGNA
	public static List<Struct> listB3 = new ArrayList<Struct>();
	// Moneda
	public static List<Struct> listE3 = new ArrayList<Struct>();
	// ADDENDA Catalogo (Rubro 5)
	public static List<Struct> listK3 = new ArrayList<Struct>();
	// Lista de clientes con su nombre y dirección
	public static List<Cliente> listClientes = new ArrayList<Cliente>();
	// Datos que se van a necesitar del layout
	public static List<Pedido> listPedidos = new ArrayList<Pedido>();
	// total Mercadería de todos los pedidos
	public static List<Float> listTotalMercaderías = new ArrayList<Float>();
	static RoundingMode RM = RoundingMode.HALF_EVEN;
	
	public static String excepcionPRECIO = new String();
	public static String excepcionCANTIDAD = new String();
	//Extra No. 6
	public static String excepcionRUBRO5 = new String();
	public static String excepcionCOBRADOR = new String();
	public static String excepcionVENDEDOR = new String();
	public static String excepcionOBSERVACIONES = new String();
	public static String excepcionCONDICION = new String();
	
	public static String errorJSP = new String();
	

	static Connection currentCon = null;
	static Statement stmt = null;
	static Statement stmt1 = null;
	static Statement stmtP = null;
	static ResultSet rs = null;
	static ResultSet rs1 = null;
	static ResultSet rsP = null;

	static String sql;

	public static UserBean login(UserBean bean) {

		if (Pool.conecta(bean.getUsername(), bean.getPassword())) {
			bean.setValid(true);
			System.out.println("Bienvenido " + bean.getUsername());
		} else {
			System.out.println("USUARIO FALSO!!!!!");
			bean.setValid(false);
		}
		
		return bean;
	}
	
	public static UserBean loginOracle(UserBean beanOracle) 
	{
		if (Pool.conectaOracle(beanOracle.getUsernameOracle(), beanOracle.getPasswordOracle())) 
		{
			beanOracle.setValidOracle(true);
			System.out.println("Oracle: " + beanOracle.getUsernameOracle());
			System.out.println("Pass: " + beanOracle.getPasswordOracle());
		} 
		else 
		{
			System.out.println("USUARIO FALSO!!!!!");
			beanOracle.setValidOracle(false);
		}

		return beanOracle;
	}

	@SuppressWarnings("resource")
	public static ArrayList<String> importaCSV(String archivo, String empresa) 
	{

		String idLista="000000";
		String ultimoRubro = null;
		//Limpiamos variables para evitar bugs
		cadenaErrores = "";
		a1 = 0;	a2 = 0;	a3 = 0;	a4 = 0;	a5 = 0;	a6 = 0;	a7 = 0;	a8 = 0;	a9 = 0;	a10 = 0; a11 = 0; a12 = 0; a13 = 0;

		ArrayList<String> ar = new ArrayList<String>();

		try 
		{
			//CSVReader reader = new CSVReader(new FileReader("o:\\ArchIPAT\\"+ archivo + ""), ',' , '"', 2);
			CSVReader reader = new CSVReader(new FileReader(archivo + ""), ',' , '"', 2);
			//CSVReader reader = new CSVReader(new FileReader(archivo), ',' , '"', 2);
			System.out.println("Se leyó el archivo CSV correctamente");
			errorJSP = null;
			
			String[] nextLine;
			int countF = 0;
			int countH = 0;
			int no6 = 0; //COMPROBACIÓN no. 6
			int intDetalle = 0;

			String lineS;
			String[] lineSeparated;

			// Limpiando el arreglo
			ar.clear();
			listF3.clear();
			listD3.clear();
			listB4.clear();
			listC1.clear();
			listD4.clear();
			listF4.clear();
			listB3.clear();
			listE3.clear();
			listK3.clear();
			listQ3.clear();
			listO3.clear();
			listN3.clear();
			listP3.clear();
			listClientes.clear();
			listPedidos.clear();
			
			excepcionRUBRO5 = "";
			excepcionCOBRADOR = "";
			excepcionVENDEDOR = "";
			excepcionCONDICION = "";
			
			excepcionPRECIO = "";
			excepcionCANTIDAD = "";
			Pool.excepcionCLIENTE = "";
			Pool.excepcionDescripcionArt = "";
			Pool.excepcionEXISTEVALOR = "";
			Pool.excepcionFECHA = "";
			Pool.excepcionID = "";
			Pool.excepcionIVA = "";
			Pool.excepcionUID = "";
			Pool.excepcionOBSERVACIONES = "";
			BigDecimal totalMercaderia = new BigDecimal("0.0");
			BigDecimal mercaderiaPorLinea = new BigDecimal("0.0");
			BigDecimal totalUnidades = new BigDecimal("0.0");
			BigDecimal unidadesPorLinea = new BigDecimal("0.0");
			BigDecimal totalIVAs = new BigDecimal("0.0");
			String moneda = "";
			
			//Pedido Linea
			PedidoLinea pedidoLinea = new PedidoLinea();
			List<PedidoLinea> listPedidosLinea = new ArrayList<PedidoLinea>();
			
			//String pedido;
			Integer pedido_linea = 0;
			String articulo = null;
			BigDecimal precio_unitario = null;
			BigDecimal cantidad_linea = null;
			String descripcion;
			String notas="";
			Integer header = 0;
			
			//No. 6
			String rubro5 = "";
			String cobrador = "";
			String vendedor = "";
			String observaciones = "";
			String condicionPago = "";
			
			//Bandera para saber si hay un Header antes de un Detalle
			Boolean existeHeader = false;
			errores = "";
			cadenaErrores = "";
			while ((nextLine = reader.readNext()) != null) {
				lineS = "";
				

				if (nextLine != null) {
					/*
					 * Aquí se realizará la discriminación entre Headers y
					 * Details, así como las reglas de negocio
					 */
					
					lineS = Arrays.toString(nextLine);
					System.out.println("lineS = " + lineS);
					lineSeparated = lineS.split(",");

					if (nextLine[0].equals("H")) {
						
						existeHeader = true;						

						// totalMercadería, totalUnidades, totalIVAs, moneda
						if (totalMercaderia.compareTo(BigDecimal.ZERO) != 0	|| totalUnidades.compareTo(BigDecimal.ZERO) != 0 || totalIVAs.compareTo(BigDecimal.ZERO) != 0 || moneda != "") {
							listPedidos.get(countH).setTotalUnidades(totalUnidades);
							totalUnidades = new BigDecimal("0.0");

							listPedidos.get(countH).setTotalMercaderia(totalMercaderia);
							totalMercaderia = new BigDecimal("0.0");

							listPedidos.get(countH).setTotalIVAs(totalIVAs);
							totalIVAs = new BigDecimal("0.0");

							mercaderiaPorLinea = new BigDecimal("0.0");
							countH++;
							
						}
						
						// Comprobación No. 6: Valores de precio en Detalle pueden ser 0
						intDetalle = 0;
						if(listPedidos.size() > 1){						
							for (no6 = 0; no6 < listPedidos.get(countH-2).getListPedidosLinea().size() ; no6++){							
								if(listPedidos.get(countH-2).getListPedidosLinea().size() != 0){
									if( listPedidos.get(countH-2).getListPedidosLinea().get(no6).getPrecio_unitario().compareTo(BigDecimal.ZERO) == 0 ){
										//Precio unitario = 0
										listPedidos.get(countH-2).getListPedidosLinea().get(no6).setPedidoLineaValido(false);
										intDetalle++;
									}
								}
								
							}
							if(intDetalle == listPedidos.get(countH-2).getListPedidosLinea().size()){
								System.out.println("PEDIDO... todo en CEROS");
								listPedidos.get(countH-2).setPedidoValido(false);
							}
						}
									

						// COLUMNA B: EMPRESA
						if (lineSeparated[1].trim().equals(Pool.empresa)) {
							// System.out.println("ASIGNA CORRECTO");
						} else {
							System.out.println("VALIDACIÓN 0: La compañía no es correcta");
							addToList(listB3, new Struct(countF, 1, true), countF, 1);
							if (a1 < listB3.size()) {
								errores += countF + 3 + ", Unidad Negocio : Compa&ntilde;&iacute;a incorrecta = " + lineSeparated[1].trim() + "\n";
								a1++;
							}
						}
						
						
						/*-----------------------------ultimoID----------------------------*/
						idLista = String.valueOf(Integer.valueOf(idLista) + 1);

						System.out.println("ultimoID + 1 = " + idLista);
						if (idLista.length() < 6) {
							for (int ex = idLista.length(); ex < 6; ex++) {
								idLista = "0" + idLista;
							}
						}

						// COLUMNA D: CLIENTE
						if (Pool.existeValor(Pool.empresa + ".CLIENTE", "CLIENTE", lineSeparated[3].trim())) {
							System.out.println("Sí existe cliente");
							Pool.addCliente(lineSeparated[3].trim(), idLista, empresa);
						} else {
							System.out.println("VALIDACIÓN 1: No existe el cliente");
							addToList(listD3, new Struct(countF, 3, true), countF, 3);
							if (a2 < listD3.size()) {
								errores += countF + 3 + ", Cliente : Cliente inexistente = " + lineSeparated[3].trim() + "\n";
								a2++;
							}
						}

						// COLUMNA E: MONEDA (TABLA)
						if (monedaValida(lineSeparated[4].trim())) {
							System.out.println("Tipo de Moneda correcto");
							moneda = lineSeparated[4].trim();

						} else {
							System.out.println("VALIDACIÓN 7: Moneda no válida");
							addToList(listE3, new Struct(countF, 4, true), countF, 4);
							if (a3 < listE3.size()) {
								errores += countF + 3 + ", Moneda : Moneda inv&aacute;lida = "+ lineSeparated[4].trim() + "\n";
								a3++;
							}
						}

						// COLUMNA F: FECHA (TABLA * GLOBALES)
						String fechaE = Pool.fechaValida(lineSeparated[5].trim(),empresa); 
						if ( fechaE != null ) {
							System.out.println("fechaE: " + fechaE);
						} else {
							System.out.println("VALIDACIÓN 2: Fecha no válida");
							addToList(listF3, new Struct(countF, 5, true), countF, 5);
							if (a4 < listF3.size()) {
								errores += countF + 3 + ", Fecha : Fecha inv&aacute;lida = "+ lineSeparated[5].trim() + "\n";
								a4++;
							}
						}
						
						//COLUMNA G: CONDICION PAGO
						if(Pool.existeValor(Pool.empresa + ".CONDICION_PAGO", "CONDICION_PAGO", lineSeparated[6].trim())){
							String condicion = lineSeparated[6].trim();
							if(condicion.equals("001") || condicion.equals("002") || condicion.equals("003") 
									|| condicion.equals("15") || condicion.equals("CON")){
								condicionPago = condicion;
							}else{
								excepcionCONDICION =  "Condicion de pago invalida" + "</br>"; 
								System.out.println("VALIDACIÓN 6: Condicion no valida");
								addToList(listQ3, new Struct(countF, 6, true), countF, 6);
								System.out.println(a5 + " < " + listQ3.size());
								if (a5 < listQ3.size()) {
									errores += countF + 3 + ",Condicion de Pago : Condicion inv&aacute;lida = " + lineSeparated[6].trim() + "\n";
									a5++;
								}
							}
						}else{
							excepcionCONDICION =  "Condicion de pago invalida" + "</br>"; 
							System.out.println("VALIDACIÓN 6: Condicion no valida");
							addToList(listQ3, new Struct(countF, 6, true), countF, 6);
							System.out.println(a5 + " < " + listQ3.size());
							if (a5 < listQ3.size()) {
								errores += countF + 3 + ",Condicion de Pago : Condicion inv&aacute;lida = " + lineSeparated[6].trim() + "\n";
								a5++;
							}
						}
											

						//COLUMNAS H-K (Rubros 1-4) NO SE VALIDAN (7,8,9,10)
						System.out.println("lineSeparated = " + Arrays.toString(nextLine));
						
						//COLUMNA L (Rubro 5) ADDENDA CATALOGO TIPOS
						if(Pool.existeValor(Pool.empresa + ".ADDENDA_TIPOS", "CATALOGO", lineSeparated[11].trim()))
						{
							rubro5 = lineSeparated[11].trim();
							System.out.println("EXISTE TIPO DE ADDENDA: "+ rubro5);
						}
						else
						{
							excepcionRUBRO5 =  "Tipo de Catalogo invalido" + "</br>"; 
							System.out.println("VALIDACIÓN 6: Catalogo no valido");
							addToList(listK3, new Struct(countF, 11, true), countF, 11);
							System.out.println(a6 + " < " + listK3.size());
							if (a6 < listK3.size()) 
							{
								errores += countF + 3 + ",Catalogo Addenda: Catalogo inv&aacute;lido = " + lineSeparated[11].trim() + "\n";
								a6++;
							}							
						}
												
						//COLUMNA M (Cobrador)
						if (lineSeparated[12].trim().equals("")){
							cobrador = "ND";
						} else if (Pool.existeValor(Pool.empresa + ".COBRADOR", "COBRADOR", lineSeparated[12].trim())) {
							 cobrador = lineSeparated[12].trim();
							 System.out.println("EXISTE COBRADOR: "+ cobrador);
						} else {	
							excepcionCOBRADOR = "Cobrador inválido" + "<br/>";
							System.out.println("VALIDACIÓN 3: Cobrador no válido");
							addToList(listN3, new Struct(countF, 12, true), countF, 12);
							if (a7 < listN3.size()) {
								errores += countF + 3 + ",Cobrador: Cobrador inv&aacute;lido = " + lineSeparated[12].trim() + "\n";
								a7++;
							}
						}
						
						// COLUMNA N (Vendedor)
						if(lineSeparated[13].trim().equals("")){
							vendedor = "ND";
						}
						else if (Pool.existeValor(Pool.empresa + ".VENDEDOR", "VENDEDOR", lineSeparated[13].trim())) {
							 vendedor = lineSeparated[13].trim();
							 System.out.println("EXISTE VENDEDOR: " + vendedor);
						} else {
							excepcionVENDEDOR = "Vendedor inválido" + "<br/>";
							System.out.println("VALIDACIÓN 4: Vendedor no válido");
							addToList(listO3, new Struct(countF, 13, true),	countF, 13);
							if (a8 < listO3.size()) {
								errores += countF + 3 + ",Vendedor: Vendedor inv&aacute;lido = " + lineSeparated[13].trim() + "\n";
								a8++;
							}
						}
						
						//COLUMNA O (Observaciones)
						ultimoRubro = lineSeparated[14].trim();
						if(ultimoRubro.equals("") || ultimoRubro.equals("]")){
							observaciones = "";
							System.out.println("NO HAY CORREOS ASIGNADOS A OBSERVACIONES: " + observaciones);
						}
						else if (Pool.validaObservaciones(ultimoRubro)) {
							String observacionesEmails = ultimoRubro.replace("]", ""); 
							observaciones = observacionesEmails;
							System.out.println("CORREOS: " + observaciones);
						} else {
							excepcionOBSERVACIONES = "Observaciones inválidas" + "<br/>";
							System.out.println("VALIDACIÓN 5: Existen correos no válidos");
							addToList(listP3, new Struct(countF, 14, true),	countF, 14);
							if (a9 < listP3.size()) {
								errores += countF + 3 + ",Correos: Correos inv&aacute;lidos = " + Pool.excepcionOBSERVACIONES + "\n";
								System.out.println(errores);
								a9++;
							}
						}

						addToListPedidos(listPedidos, new Pedido(countF, idLista, condicionPago, lineSeparated[7].trim(),
							lineSeparated[8].trim(), lineSeparated[9].trim(), lineSeparated[10].trim(), 
							rubro5, moneda, cobrador, vendedor, observaciones, fechaE));

						
						/*-----------Asignación y Reseteo de variables de pedido_linea----------*/
						//ASIGNACIÓN
						if(listPedidos.size()!=1){
							listPedidos.get(header-1).setListPedidosLinea(listPedidosLinea);
							pedido_linea = 0;
							listPedidosLinea = new ArrayList<PedidoLinea>();
						}
						header++;
						
						//RESETEO
						/* ------DETALLES------- */

						}else if (nextLine[0].equals("D")) {
							if(!existeHeader){
								errorJSP="Falta Header";
								return null;
							}
							
						
						/* ---- REGLAS DE NEGOCIO---- */
						
						//Add Detalle
						pedidoLinea = new PedidoLinea();
						pedido_linea++;
						
						// COLUMNA B Producto(Artículo)
						if (Pool.existeValor(Pool.empresa + ".ARTICULO", "ARTICULO",lineSeparated[1].trim())) {
								System.out.println("EXISTE ARTICULO: " + lineSeparated[1].trim());
								articulo = lineSeparated[1].trim();
						} else {
							System.out.println("VALIDACIÓN 3: Artículo no válido");
							addToList(listB4, new Struct(countF, 1, true),countF, 1);
							if (a10 < listB4.size()) {
								errores += countF + 3 + ", Producto: Art&iacuteculo inv&aacute;lido = "	+ lineSeparated[1].trim() + "\n";
								a10++;
							}
						}

						// COLUMNA C (Cantidad de Articulos)
						if (Integer.valueOf(lineSeparated[2].trim()) >= 1) {
							cantidad_linea = BigDecimal.valueOf(Double.valueOf(lineSeparated[2].trim()));
							totalUnidades = totalUnidades.add(BigDecimal.valueOf(Double.valueOf(lineSeparated[2].trim()))).setScale(2, RM);
							unidadesPorLinea = BigDecimal.valueOf(Double.valueOf(lineSeparated[2].trim()));
							System.out.println("Cantidad de artículos correcta: CANTIDAD LINEA= "+cantidad_linea +", TOTAL UNIDADES = " + totalUnidades + "UNIDADES POR LINEA = "+ unidadesPorLinea);
						} else {
							excepcionCANTIDAD = "Cantidad inválida" + "<br/>";
							unidadesPorLinea = new BigDecimal("0.0");
							System.out.println("VALIDACIÓN 4: Cantidad de artículos menor que 1");
							addToList(listC1, new Struct(countF, 2, true),countF, 2);
							if (a11 < listC1.size()) {
								errores += countF + 3 + ", Cantidad : Cantidad de art&iacute;culo inv&aacute;lido = " + lineSeparated[2].trim() + "\n";
								a11++;
							}
						}

						// COLUMNA D (Precio) DEBE SER MAYOR A 0
						if (Float.valueOf(lineSeparated[3].trim()) > 0.0) {
							precio_unitario = BigDecimal.valueOf(Double.valueOf(lineSeparated[3].trim())).setScale(2, RM);
							totalMercaderia = totalMercaderia.add(BigDecimal.valueOf(Double.valueOf(lineSeparated[3].trim())).multiply(unidadesPorLinea)).setScale(2, RM);
							mercaderiaPorLinea = BigDecimal.valueOf(Double.valueOf(lineSeparated[3].trim())).multiply(unidadesPorLinea).setScale(2, RM);
							System.out.println("PRECIO MAYOR A 0, PRECIO UNITARIO= " + precio_unitario +", TOTAL MERCADERIA= " + totalMercaderia + ", MERCADERIA POR LINEA = "+ mercaderiaPorLinea);
						} else {
							precio_unitario = new BigDecimal("0.0");
							excepcionPRECIO = "Precio inválido" + "<br/>";
							mercaderiaPorLinea = new BigDecimal("0.0");
							System.out.println("VALIDACIÓN 5: Precio menor o igual que 0");
							addToList(listD4, new Struct(countF, 3, true), countF, 3);
							if (a12 < listD4.size()) {
								errores += countF + 3 + ", Precio : Precio inv&aacute;lido = "+ lineSeparated[3].trim() + "\n";
								a12++;
							}
						}

						//COLUMNA E Notas
						if (pedido_linea == 1){
							if (lineSeparated[4].trim().equals("")){
								notas = "";
							} else if(lineSeparated[4].trim().length() <= 40){
								notas = lineSeparated[4].trim();
							} else {
								
							}
						}
						
						
						//CORREGIDO: Descripcion de asigna.ARTICULO
						descripcion = Pool.rescatarDescripcion(lineSeparated[1].trim(), empresa);
												
						// COLUMNA F IVA's
						if (Pool.existeValorIVA(Pool.empresa+ ".IMPUESTO", "IMPUESTO","IVA", "IMPUESTO1", "IMPUESTO2",lineSeparated[5].trim())) {
							System.out.println("EXISTE IVA: " + lineSeparated[1].trim());
							System.out.println("IVA: " + BigDecimal.valueOf(Double.valueOf(lineSeparated[5].trim())/100) + " mXlinea: " + mercaderiaPorLinea + " IVA_aplicado: " + BigDecimal.valueOf(Double.valueOf(lineSeparated[5].trim())/100).multiply(mercaderiaPorLinea));
							totalIVAs = totalIVAs.add(BigDecimal.valueOf(Double.valueOf(lineSeparated[5].trim()) / 100).multiply(mercaderiaPorLinea)).setScale(2, RM);
						} else {
							System.out.println("VALIDACIÓN 6: IVA no válido");
							addToList(listF4, new Struct(countF, 5, true), countF, 5);
							if (a13 < listF4.size()) {
								errores += countF + 5 + ", IVA : IVA inv&aacute;lido = " + lineSeparated[5].trim() + "\n";
								a13++;
							}
						}
						
						
						// COLUMNA G ComDescripcion
						descripcion += lineSeparated[6];
						System.out.println("Descripción completa = " + descripcion);
												
						//add to List pedidosLinea
						pedidoLinea.setPedido(idLista);
						pedidoLinea.setPedido_linea(pedido_linea);
						pedidoLinea.setArticulo(articulo);
						pedidoLinea.setCantidadL(cantidad_linea);
						pedidoLinea.setPrecio_unitario(precio_unitario);
						pedidoLinea.setDescripcion(descripcion);
						pedidoLinea.setNotas(notas);
						listPedidos.get(countH).setNotas(notas);
						addToListPedidoLinea(listPedidosLinea, pedidoLinea, idLista, pedido_linea);
						
						cadenaErrores= errores;
						System.out.println(cadenaErrores);
						

					} else {
						System.out.println("Letra incorrecta (Debe de ser D o H");
						return null;
					}

					/*
					 * Aquí se realizará la discriminación entre Headers y
					 * Details, así como las reglas de negocio
					 */

					ar.add(Arrays.toString(nextLine));
					countF++;
				}
				System.out.println(errores);

			}

			// totalMercadería, totalUnidades
			if (totalMercaderia.compareTo(BigDecimal.ZERO) != 0	|| totalUnidades.compareTo(BigDecimal.ZERO) != 0 || totalIVAs.compareTo(BigDecimal.ZERO) != 0 || moneda != "") {
				listPedidos.get(countH).setTotalUnidades(totalUnidades);
				totalUnidades = new BigDecimal("0.0");

				listPedidos.get(countH).setTotalMercaderia(totalMercaderia);
				totalMercaderia = new BigDecimal("0.0");

				listPedidos.get(countH).setTotalIVAs(totalIVAs);
				totalIVAs = new BigDecimal("0.0");

				mercaderiaPorLinea = new BigDecimal("0.0");
				countH++;
			}
			
			listPedidos.get(header-1).setListPedidosLinea(listPedidosLinea);
			
			// Comprobación No. 6
			// Valores de precio en Detalle pueden ser 0
			intDetalle = 0;
			if(listPedidos.size() >= 1){
				for (no6 = 0; no6 < listPedidos.get(countH-1).getListPedidosLinea().size() ; no6++){							
					if(listPedidos.get(countH-1).getListPedidosLinea().size() != 0){
						if( listPedidos.get(countH-1).getListPedidosLinea().get(no6).getPrecio_unitario().compareTo(BigDecimal.ZERO) == 0 ){
							System.out.println("no tomar en cuenta detalle");
							listPedidos.get(countH-1).getListPedidosLinea().get(no6).setPedidoLineaValido(false);
							intDetalle++;
						}
					}
					
				}
				if(intDetalle == listPedidos.get(countH-1).getListPedidosLinea().size()){
					System.out.println("PEDIDO... todo en CEROS");
					listPedidos.get(countH-1).setPedidoValido(false);
				}
			}

			reader.close();
			return ar;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException eio) {
			eio.printStackTrace();
		}

		return null;

	}// importaCSV()

	//VALIDA MONEDA
	public static Boolean monedaValida(String moneda) {
		Boolean existe = null;

		if (moneda.equals("L") || moneda.equals("D")) {
			existe = true;
		} else {
			existe = false;
		}

		return existe;
	}

	// addToList
	public static void addToList(List<Struct> lista, Struct item, int fila,	int columna) {
		for (Struct p : lista)
			if (p.getFila() == fila && p.getColumna() == columna)
				return;
		lista.add(item);
	}

	// addToListPEDIDOS
	public static void addToListPedidos(List<Pedido> lista, Pedido item) {
		System.out.println("item a añadir: " + item.getPedido());
		if (lista.size() != 0)
			for (Pedido r : lista)
				if (r.getPedido().equals(item.getPedido()))
					return;
			lista.add(item);
	}
	
	// addToListPEDIDOLINEA
	public static void addToListPedidoLinea(List<PedidoLinea> lista, PedidoLinea pedidoObj, String pedido, Integer pedido_linea) {
		for (PedidoLinea p : lista)
			if (p.getPedido().equals(pedidoObj.getPedido()) && p.getPedido_linea() == pedidoObj.getPedido_linea())
				return;
		lista.add(pedidoObj);
	}

		
}
