package controlleur;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metier.UserDao;
import models.User;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String email = request.getParameter("email");
	    String mdp = request.getParameter("password");

	    UserDao user = new UserDao();
	    User admin = user.getAdmin(email, mdp);
	    User client = user.getUser(email, mdp);
	    if (admin == null && client == null) {
	    	request.getSession().setAttribute("erreur", "Email ou mot de passe incorrect");
	    	response.sendRedirect(request.getContextPath() + "/views/LogIn.jsp");
	    } else if (admin != null) {
	        request.getSession().setAttribute("admin", admin);
	        response.sendRedirect(request.getContextPath() + "/views/adminDashboard.jsp");
	        //RequestDispatcher rq = request.getRequestDispatcher("/views/adminDashboard.jsp");
	        //rq.forward(request, response);
	        return;
	    } else if (client != null) {
	        request.getSession().setAttribute("client", client);
	        response.sendRedirect(request.getContextPath() + "/views/home.jsp");
	        request.getSession().setAttribute("email", email);
	        //RequestDispatcher rq = request.getRequestDispatcher("/views/home.jsp");
	        //rq.forward(request, response);
	        return;
	    }
	}


}
