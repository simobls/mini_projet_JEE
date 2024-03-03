package controlleur;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;
import java.sql.*;
/**
 * Servlet implementation class updateServlet
 */
@WebServlet("/updateServlet")
public class updateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateServlet() {
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
	    String id = request.getParameter("id");
	    String model = request.getParameter("model");
	    int year = Integer.parseInt(request.getParameter("year"));
	    String type = request.getParameter("type");
	    double price = Double.parseDouble(request.getParameter("price"));
	    String image = request.getParameter("image");
	    String location = request.getParameter("location");

	    Car car = new Car(id);
	    car.setModel(model);
	    car.setType(type);
	    car.setYear(year);
	    car.setImage_url(image);
	    car.setPrice_per_day(price);
	    car.setLocation_id(location);

	    car.updateCar(car);
	    try {
	        request.getSession().setAttribute("updated", "La voiture a �t� modifi�e avec succ�s");
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
