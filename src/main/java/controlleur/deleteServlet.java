package controlleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Car;

/**
 * Servlet implementation class deleteServlet
 */
@WebServlet("/deleteServlet")
public class deleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteServlet() {
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
		// TODO Auto-generated method stub
		
		String delete_id = request.getParameter("delete_id");
	    Car car = new Car(delete_id);
		car .supprimerCar(delete_id);
	    try {
	        request.getSession().setAttribute("deleted", "La voiture a été supprimée avec succès");
	        // Rediriger vers une page de succès ou une autre page appropriée
	        response.sendRedirect(request.getContextPath() + "/views/adminDashboard.jsp");
	    } catch (IllegalArgumentException e) {
	        e.printStackTrace();
	        // Gérer les erreurs (par exemple afficher un message d'erreur sur la page de signup)
	        request.setAttribute("errorMessage", "Une erreur s'est produite lors de la modification de la voiture. Veuillez réessayer.");
	        request.getRequestDispatcher("/views/adminDashboard.jsp").forward(request, response);
	    }
	}

}
