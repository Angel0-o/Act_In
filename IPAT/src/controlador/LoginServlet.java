package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.UserBean;
import modelo.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);


        if (request.getSession().getAttribute("currentSessionUser") == null) {
             // Session not created yet. So we do it now.
        	
        	try {

    			UserBean user = new UserBean();
    			UserBean userOracle = new UserBean();
    			user.setUserName(request.getParameter("inputUsername"));
    			user.setPassword(request.getParameter("inputPassword"));
    			userOracle.setUserNameOracle("MASNGFACE");
    			//userOracle.setPasswordOracle("M45nGbMv0124"); // CONTRASEÑA PRODUCCION
    			userOracle.setPasswordOracle("MASNGFACE01"); //CONTRASEÑA DESARROLLO
    			// Buscará si existe el usuario en la BD CASPER
    			user = UserDAO.login(user);
    			userOracle = UserDAO.loginOracle(userOracle);

    			if (user.isValid() == true) {
    				session = request.getSession(true);
//    				HttpSession session = request.getSession(true);
    				session.setMaxInactiveInterval(720*60);
    				session.setAttribute("currentSessionUser", user);
    				response.sendRedirect("pages/Principal.jsp");
//    				request.getRequestDispatcher("pages/Principal.jsp").forward(request, response);
    			} else {
    				response.sendRedirect(response.encodeRedirectURL("/IPAT"));
    			}
    		} catch (Throwable theException) {
    			System.out.println(theException);
    			response.sendRedirect(response.encodeRedirectURL("/IPAT"));
    		}
        		
        		
       } 

       else {
           // Session is already created.
            response.sendRedirect("/IPAT");
          // So i again redirect them to their home page
       }
		
	}//doPost
}
