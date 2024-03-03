package controlleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.*;
import models.OracleDatabaseHandler;

/**
 * Servlet implementation class addLocation
 */
@WebServlet("/addLocation")
public class addLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addLocation() {
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
		String name = request.getParameter("name");
        String address = request.getParameter("address");

        // Créer un nouvel utilisateur
        Location newLocation = new Location(UUID.randomUUID().toString());
        newLocation.setName(name);
        newLocation.setAddress(address);
        
        
        try {
            OracleDatabaseHandler dbHandler = new OracleDatabaseHandler("jdbc:mysql://localhost:3306/miniprojetJEE", "root", "");
            dbHandler.insertObject(newLocation, "Location");
            request.getSession().setAttribute("valide", "La location a ete bien ajoutee");
            // Rediriger vers une page de succès ou une autre page appropriée
            response.sendRedirect(request.getContextPath()+"/views/location_page.jsp");
        } catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            // Gérer les erreurs (par exemple afficher un message d'erreur sur la page de signup)
            request.setAttribute("errorMessage", "Une erreur s'est produite lors de la création de location. Veuillez réessayer.");
            request.getRequestDispatcher("/views/location_page.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
