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

/**
 * Servlet implementation class Actualizar
 */
@WebServlet("/Actualizar")
public class Actualizar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Actualizar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Boolean correcto = true;
		Boolean exito = true;

		String archivo = request.getParameter("fName");
		System.out.println("archivo: " + archivo);
		// Google Chrome lo detecta como null si no se seleccionó ninguna opción en cambio Firefox lo detecta como vacío ("")
		if (archivo == null || archivo.equals("")) {
			System.out.println("Archivo no seleccionado");

			PrintWriter outArch = response.getWriter();
			outArch.println("<script type=\"text/javascript\">"	+ "alert('Archivo no seleccionado');"+ "location='/GDI/Actualizar.jsp';</script>");
			correcto = false;
		}
		else if(!archivo.contains(".csv"))
		{
			System.out.println("Formato Incorrecto");

			PrintWriter outArch = response.getWriter();
			outArch.println("<script type=\"text/javascript\">"	+ "alert('Formato Incorrecto');"+ "location='/GDI/Actualizar.jsp';</script>");
			correcto = false;
		}

		if (correcto) 
		{
			request.setAttribute("archivo", archivo);
			ArrayList<String> arreglo = UserDAO.leeCSV(archivo);

			if (arreglo == null) 
			{
				UserDAO.errorJSP = "No se pudo leer el archivo CSV";
				response.sendRedirect("Error.jsp");
			} 
			else 
			{
				exito = Pool.actualizarRegistros(arreglo);
				if (exito)
				{
					response.sendRedirect("Success.jsp");
				}
				else
				{
					UserDAO.errorJSP = "Registros duplicados o informacion incorrecta";
					response.sendRedirect("Error.jsp");
				}
			}
		}

	}//doPost()

}

