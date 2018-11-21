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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);


        if (request.getSession().getAttribute("currentSessionUser") == null) {
             // Session not created yet. So we do it now.
        	
        	try {

    			UserBean user = new UserBean();
    			user.setUserName(request.getParameter("inputUsername"));
    			user.setPassword(request.getParameter("inputPassword"));
    			// Buscará si existe el usuario en la BD CASPER
    			user = UserDAO.login(user);

    			if (user.isValid() == true) {
    				session = request.getSession(true);
//    				HttpSession session = request.getSession(true);
    				session.setMaxInactiveInterval(720*60);
    				session.setAttribute("currentSessionUser", user);
    				response.sendRedirect("pages/Principal.jsp");
//    				request.getRequestDispatcher("pages/Principal.jsp").forward(request, response);
    			} else {
    				/*PrintWriter out = response.getWriter();
    				out.println("<script type=\"text/javascript\">");
    				out.println("location='index.jsp';");
    				out.println("</script>");*/
    				response.sendRedirect(response.encodeRedirectURL("/Intranet_Act"));
    			}
    		} catch (Throwable theException) {
    			System.out.println(theException);
    			response.sendRedirect(response.encodeRedirectURL("/Intranet_Act"));
    		}
        		
        		
       } 

       else {
           // Session is already created.
            response.sendRedirect("/Intranet_Act");
          // So i again redirect them to their home page
       }
		
	}//doPost
}
