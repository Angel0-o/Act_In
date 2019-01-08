package bs;

import java.util.ArrayList;

import modelo.UserDAO;

public class Utilidades {

	public static String construirHTML(ArrayList<String> arreglo) {
		String tabla = null;

		int filas = arreglo.size();

		tabla = "<table class=\"table table-hover\"  style=\"vertical-align: middle; text-align: center; font-size:10px;\"><thead>"
				+ "<tr class=\"bg-primary info\"><th style=\"vertical-align: middle; text-align: center;\">1</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Cabecero H</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Unidad Negocio</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Tipo Factura</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Cliente</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Moneda</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Fecha</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Condicion de Pago</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Rubro1</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Rubro2</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Rubro3</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Rubro4</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Rubro5</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Cobrador</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Vendedor</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Observaciones</th></tr></thead>";
		
		tabla += "<thead><tr class=\"sub\"><th style=\"vertical-align: middle; text-align: center;\">2</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Cabecero D</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Producto</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Cantidad</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Precio</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">Notas</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">IVA</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\">ComDescripcion</th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\"></th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\"></th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\"></th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\"></th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\"></th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\"></th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\"></th>"
				+ "<th style=\"vertical-align: middle; text-align: center;\"></th></tr><thead>";

		int i, j, z = 0, y = 0, x = 0, w = 0, v = 0, u = 0, t = 0, s = 0, r = 0, q = 0, p = 0, o = 0, n = 0;
		// Bandera para quitarle el ] a los números impresos vía web
		int band;

		String filaTemp;

		String[] arregloSeparated;
		int longitud;
		// Bandera para identificar las filas título
		int bandera = 0;
//		int rutaInvalida = 0;

		for (i = 0; i < filas; i++) {
			bandera = 0;
			filaTemp = arreglo.get(i);
			arregloSeparated = filaTemp.split(",");

			if (arregloSeparated[0].equals("[H")) {
				tabla += "<thead><tr class=\"bg-primary\">";
				bandera = 1;
			} else {
				tabla += "<tr>";
			}

			tabla += "<td>" + (i + 3) + "</td>";

			
			//DEBE SER 15
			longitud = arregloSeparated.length;
			for (j = 0; j < longitud; j++) {
				
				if(j==15){
					j=0;
					break;
				}
					

				band = 0;

				if (j == 0) {// Primera columna para quitar el [
					tabla += "<td>" + arregloSeparated[j].substring(1)	+ "</td>";
				} else if (j == arregloSeparated.length - 1) {// Última columna para quitar el ]
					
					// Observaciones inválido
					if (UserDAO.listP3.size() > 0 && p < UserDAO.listP3.size()) {
						if (UserDAO.listP3.get(p).getFila() == i && UserDAO.listP3.get(p).getColumna() == j	&& UserDAO.listP3.get(p).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>"+ arregloSeparated[j].substring(0,arregloSeparated[j].length() - 1) + "</b></span></td>";
							p++;
							band = 1;
							continue;
						}
					}
					
					
					if(band != 1)
					tabla += "<td>" + arregloSeparated[j].substring(0,arregloSeparated[j].length() - 1) + "</td>";
					
					

				} else {// Columnas intermedias
					
					// cliente no válido
					if (UserDAO.listD3.size() > 0 && x < UserDAO.listD3.size()) {
						if (UserDAO.listD3.get(x).getFila() == i && UserDAO.listD3.get(x).getColumna() == j	&& UserDAO.listD3.get(x).getRojo()) {
							tabla += "<td><span style=\"color:#A00000 !important\"><b>" + arregloSeparated[j] + "</b></span></td>";
							x++;
							band = 1;
						}
					}

					// fecha no valida
					if (UserDAO.listF3.size() > 0 && y < UserDAO.listF3.size()) {
						if (UserDAO.listF3.get(y).getFila() == i && UserDAO.listF3.get(y).getColumna() == j && UserDAO.listF3.get(y).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>" + arregloSeparated[j] + "</b></span></td>";
							y++;
							band = 1;
						}
					}

					// articulo no existente
					if (UserDAO.listB4.size() > 0 && z < UserDAO.listB4.size()) {
						if (UserDAO.listB4.get(z).getFila() == i && UserDAO.listB4.get(z).getColumna() == j && UserDAO.listB4.get(z).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>" + arregloSeparated[j] + "</b></span></td>";
							z++;
							band = 1;
						}
					}

					// cantidad inválida
					if (UserDAO.listC1.size() > 0 && w < UserDAO.listC1.size()) {
						if (UserDAO.listC1.get(w).getFila() == i && UserDAO.listC1.get(w).getColumna() == j && UserDAO.listC1.get(w).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>" + arregloSeparated[j] + "</b></span></td>";
							w++;
							band = 1;
						}
					}

					// precio inválido
					if (UserDAO.listD4.size() > 0 && v < UserDAO.listD4.size()) {
						if (UserDAO.listD4.get(v).getFila() == i && UserDAO.listD4.get(v).getColumna() == j && UserDAO.listD4.get(v).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>" + arregloSeparated[j] + "</b></span></td>";
							v++;
							band = 1;
						}
					}

					// IVA inválido
					if (UserDAO.listF4.size() > 0 && u < UserDAO.listF4.size()) {
						if (UserDAO.listF4.get(u).getFila() == i
								&& UserDAO.listF4.get(u).getColumna() == j
								&& UserDAO.listF4.get(u).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>"+ arregloSeparated[j] + "</b></span></td>";
							u++;
							band = 1;
						}
					}

					// Compania: ASIGNA
					if (UserDAO.listB3.size() > 0 && t < UserDAO.listB3.size()) {
						if (UserDAO.listB3.get(t).getFila() == i && UserDAO.listB3.get(t).getColumna() == j && UserDAO.listB3.get(t).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>" + arregloSeparated[j] + "</b></span></td>";
							t++;
							band = 1;
						}
					}

					// Moneda: L o D
					if (UserDAO.listE3.size() > 0 && s < UserDAO.listE3.size()) {
						if (UserDAO.listE3.get(s).getFila() == i && UserDAO.listE3.get(s).getColumna() == j && UserDAO.listE3.get(s).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>" + arregloSeparated[j] + "</b></span></td>";
							s++;
							band = 1;
						}
					}
					
					// Condicion de Pago
					if(UserDAO.listQ3.size() > 0 && n < UserDAO.listQ3.size()){
						if(UserDAO.listQ3.get(n).getFila() == i && UserDAO.listQ3.get(n).getColumna() == j && UserDAO.listQ3.get(n).getRojo()){
							tabla += "<td><span style=\"color:#A00000\"><b>" + arregloSeparated[j] + "</b></span></td>";
							n++;
							band = 1;
						}
					}
					
					// Rubro5 inválido
					if (UserDAO.listK3.size() > 0 && o < UserDAO.listK3.size()) {
						if (UserDAO.listK3.get(o).getFila() == i && UserDAO.listK3.get(o).getColumna() == j && UserDAO.listK3.get(o).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>" + arregloSeparated[j] + "</b></span></td>";
							o++;
							band = 1;
						}
					}
					
					// Cobrador inválido
					if (UserDAO.listN3.size() > 0 && r < UserDAO.listN3.size()) {
						if (UserDAO.listN3.get(r).getFila() == i && UserDAO.listN3.get(r).getColumna() == j && UserDAO.listN3.get(r).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>" + arregloSeparated[j] + "</b></span></td>";
							r++;
							band = 1;
						}
					}
					
					// Vendedor inválido
					if (UserDAO.listO3.size() > 0 && q < UserDAO.listO3.size()) {
						if (UserDAO.listO3.get(q).getFila() == i && UserDAO.listO3.get(q).getColumna() == j && UserDAO.listO3.get(q).getRojo()) {
							tabla += "<td><span style=\"color:#A00000\"><b>" + arregloSeparated[j] + "</b></span></td>";
							q++;
							band = 1;
						}
					}
					

				}// columnas intermedias FIN

				// última columna para quitarle el ]
				if (band != 1 && j != 0 && j != (arregloSeparated.length - 1 ))
					tabla += "<td>" + arregloSeparated[j] + "</td>";

			}// for columnas

			if (bandera == 1) {
				tabla += "</tr></thead>";
			} else {
				tabla += "</tr>";
			}

		}

		tabla += "</table>";

		return tabla;
	}// construirHTML

	public static String construirErrores() {
		String errores = "";

		errores = "<textarea class=\"textA\" readonly>" + UserDAO.cadenaErrores + "</textarea>";

		return errores;
	}
	
	
	public static String idAmib(String idAInsertar){
		String idA = "";
		String numero = idAInsertar.substring(2,idAInsertar.length());
		String parte1 = idAInsertar.substring(0,2); 
		String parte2 = String.valueOf(Integer.valueOf(numero) + 1); 
		
		if (parte2.length() < idAInsertar.length()-2) {
			for (int ex = parte2.length(); parte2.length() < idAInsertar.length()-2; ex++) {
				parte2 = "0" + parte2;
			}
		}
		
		idA = parte1 + parte2;
		
		System.out.println("consecutivo Aumentado: " + idA);
		
		return idA;
	}
	
}
