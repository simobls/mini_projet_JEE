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
	        request.getSession().setAttribute("deleted", "La voiture a �t� supprim�e avec succ�s");
	        // Rediriger vers une page de succ�s ou une autre page appropri�e
	        response.sendRedirect(request.getContextPath() + "/views/adminDashboard.jsp");
	    } catch (IllegalArgumentException e) {
	        e.printStackTrace();
	        // G�rer les erreurs (par exemple afficher un message d'erreur sur la page de signup)
	        request.setAttribute("errorMessage", "Une erreur s'est produite lors de la modification de la voiture. Veuillez r�essayer.");
	        request.getRequestDispatcher("/views/adminDashboard.jsp").forward(request, response);
	    }
	}

}
