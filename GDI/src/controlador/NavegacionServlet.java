package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bs.Pool;
import modelo.UserBean;

/**
 * Servlet implementation class NavegacionServlet
 */
@WebServlet("/NavegacionServlet")
public class NavegacionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NavegacionServlet() {
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
		
		if (request.getParameter("btnSalir") != null) {
//            UserDAO.desconectar();
			Pool.desconectar();
            request.getSession().invalidate();
            UserBean user = null;
            request.getSession().setAttribute("currentSessionUser", user);
            response.sendRedirect("/GDI");
        }
		
		//Redireccionando al usuario a la página de Importar
//		if (request.getParameter("btnImportar") != null) {
//            request.getRequestDispatcher("pages/Importar.jsp").forward(request,
//					response);
//        }
		
		
	}//doPost

}
