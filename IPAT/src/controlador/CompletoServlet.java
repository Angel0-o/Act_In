package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.UserDAO;
import bs.Pool;
import bs.Utilidades;

/**
 * Servlet implementation class CompletoServlet
 */
@WebServlet("/CompletoServlet")
public class CompletoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompletoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Pool.empresa = "";
		Pool.consecutivo = "";

		Boolean correcto = true;

		String archivo = request.getParameter("fName");
		System.out.println("archivo: " + archivo);
		// Google Chrome lo detecta como null si no se seleccionó ninguna opción en cambio Firefox lo detecta como vacío ("")
		if (archivo == null || archivo.equals("")) {
			System.out.println("Archivo no seleccionado");

			PrintWriter outArch = response.getWriter();
			outArch.println("<script type=\"text/javascript\">"	+ "alert('Archivo no seleccionado');"+ "location='/IPAT/pages/Importar.jsp';</script>");
			correcto = false;
		}

		String empresaTXT = request.getParameter("empresa");

		if (empresaTXT.equals("-1")) {
			System.out.println("Opción no seleccionada");

			PrintWriter outA = response.getWriter();
			outA.println("<script type=\"text/javascript\">"
					+ "alert('Compañía no seleccionada');"
					+ "location='pages/Importar.jsp';</script>");
			correcto = false;
		}

		String consecutivo = request.getParameter("consecutivo");

		if (consecutivo.equals("-1")) {
			System.out.println("Opción no seleccionada");

			PrintWriter outA = response.getWriter();
			outA.println("<script type=\"text/javascript\">"
					+ "alert('Consecutivo no seleccionado');"
					+ "location='pages/Importar.jsp';</script>");
			correcto = false;
		}

		System.out.println("consecutivo: " + consecutivo);

		if (correcto) 
		{
			request.setAttribute("archivo", archivo);
			request.setAttribute("empresaTXT", empresaTXT);
			Pool.consecutivo = consecutivo; 
			Pool.empresa = empresaTXT;

			ArrayList<String> arreglo = UserDAO.importaCSV(archivo, empresaTXT);

			if (arreglo == null) 
			{
				UserDAO.errorJSP = "No se pudo leer el archivo CSV";
				response.sendRedirect("pages/Error.jsp");
			} else {
				// Archivo leído
				String arregloTabla;
				String arregloErrores;

				arregloTabla = Utilidades.construirHTML(arreglo);

				arregloErrores = Utilidades.construirErrores();
				// Concatenar la exception de permisos de tablas (si es que existe)
				//arregloErrores = "<span style=\"color:#A00000\"><b>" + Pool.excepcionIVA + Pool.excepcionFECHA + Pool.excepcionID + Pool.excepcionDescripcionArt + Pool.excepcionUID + Pool.excepcionEXISTEVALOR + Pool.excepcionCLIENTE + "</b></span>" + "\n\n" + arregloErrores;

				/* ------------------------------------------------------------- */

				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<HTML>");
				out.println("<BODY>");
				out.println("<HEAD><TITLE>Resultado del proceso</TITLE>"
						+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/estilos.css\">"
						+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/jquery.css\">"
						+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap-theme.min.css\">"
						+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap.min.css\">"
						+ "</HEAD>");
				out.println("<table id=\"container2\" width=\"100%\" height=\"100%\">");
				out.println("<tr>");
				out.println("<td style=\"width:2%\"></td>");
				out.println("<td style=\"width:96%\">");
				out.println("<H3>Resultado del proceso</H3>");
				out.println("<form action=\"ConfirmarServlet\" method=\"post\" name=\"formConfirmar\" autocomplete=\"off\" id=\"formConfirmar\">");
				out.println("<input type=\"submit\" id=\"Cancelar\" name=\"Cancelar\" value=\"\" />");
				out.println("<input type=\"submit\" id=\"Actualizar\" name=\"Actualizar\" value=\"\" />");
				out.println("</form>");
				out.println("</td>");
				out.println("<td style=\"width:2%\"></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td style=\"width:2%\"></td>");
				out.println("<td style=\"width:96%\">");
				out.println(arregloErrores);
				out.println(arregloTabla);
				out.println("</td>");
				out.println("<td style=\"width:2%\"></td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</BODY>");
				out.println("</HTML>");

			}
		}

	}// doPost

}
