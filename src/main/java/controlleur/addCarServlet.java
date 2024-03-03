package controlleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Car;
import models.Location;
import models.OracleDatabaseHandler;
import models.User;

/**
 * Servlet implementation class addCarServlet
 */
@WebServlet("/addCarServlet")
public class addCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addCarServlet() {
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
		String model = request.getParameter("model");
        int year = Integer.parseInt(request.getParameter("year"));
        String type = request.getParameter("type");
        double price = Double.parseDouble(request.getParameter("price"));
        String image = request.getParameter("image");
        String location = request.getParameter("location");

        // Créer un nouvel utilisateur
        Car newCar = new Car(UUID.randomUUID().toString());
        newCar.setModel(model);
        newCar.setYear(year);
        if (type != null && !type.isEmpty()) {
            newCar.setType(type);
        }
        newCar.setImage_url(image);
        newCar.setPrice_per_day(price);
        newCar.setLocation_id(location);
        newCar.setAvailability_status("Available");
        
        try {
            OracleDatabaseHandler dbHandler = new OracleDatabaseHandler("jdbc:mysql://localhost:3306/miniprojetJEE", "root", "");
            dbHandler.insertObject(newCar, "Car");
            request.getSession().setAttribute("valide", "La voiture a ete bien ajoutee");
            // Rediriger vers une page de succès ou une autre page appropriée
            response.sendRedirect(request.getContextPath()+"/views/adminDashboard.jsp");
        } catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            // Gérer les erreurs (par exemple afficher un message d'erreur sur la page de signup)
            request.setAttribute("errorMessage", "Une erreur s'est produite lors de la création de car. Veuillez réessayer.");
            request.getRequestDispatcher("/views/adminDahboard.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
