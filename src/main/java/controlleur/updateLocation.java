package controlleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.*;

/**
 * Servlet implementation class updateLocation
 */
@WebServlet("/updateLocation")
public class updateLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateLocation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String id = request.getParameter("id");
	    String name = request.getParameter("name");
	    String address = request.getParameter("address");

	    Location location = new Location(id);
	    location.setName(name);
	    location.setAddress(address);

	    location.updateLocation(location);
	    try {
	        request.getSession().setAttribute("updated", "La location a été modifiée avec succès");
	        // Rediriger vers une page de succès ou une autre page appropriée
	        response.sendRedirect(request.getContextPath() + "/views/location_page.jsp");
	    } catch (IllegalArgumentException e) {
	        e.printStackTrace();
	        // Gérer les erreurs (par exemple afficher un message d'erreur sur la page de signup)
	        request.setAttribute("errorMessage", "Une erreur s'est produite lors de la modification de la location. Veuillez réessayer.");
	        request.getRequestDispatcher("/views/location.jsp").forward(request, response);
	    }
	}

}
