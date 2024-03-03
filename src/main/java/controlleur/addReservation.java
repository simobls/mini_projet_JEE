package controlleur;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.*;

/**
 * Servlet implementation class addReservation
 */
@WebServlet("/addReservation")
public class addReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addReservation() {
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
		
        double price = Double.parseDouble(request.getParameter("price"));
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        // Convertir les dates en objets Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            if (startDateStr != null && endDateStr != null) {
                startDate = sdf.parse(startDateStr);
                endDate = sdf.parse(endDateStr);
            } else {
                // Handle missing or invalid dates
                // For example, you could redirect the user to an error page
                response.sendRedirect(request.getContextPath() + "/error.jsp");
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Gérer l'erreur de conversion de la date
            // For example, you could redirect the user to an error page
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return;
        }
        String carId = request.getParameter("carId");
        String email = (String)request.getSession().getAttribute("email");
        String userId = User.getIDbyEmail(email);
        System.out.println(email+" "+userId);
        // Créer un nouvel utilisateur
        Booking newBooking = new Booking(UUID.randomUUID().toString());
        newBooking.setStart_date(startDate);
        newBooking.setEnd_date(endDate);
        newBooking.setCar_id(carId);
        newBooking.setUser_id(userId);
        newBooking.setBooking_status("Actived");
        Double price1=newBooking.getTotalPriceForBooking(price);
        newBooking.setTotal_price(price1);
        
        try {
            OracleDatabaseHandler dbHandler = new OracleDatabaseHandler("jdbc:mysql://localhost:3306/miniprojetJEE", "root", "");
            if (dbHandler.isCarAlreadyBooked(carId, startDate, endDate)) {
                request.getSession().setAttribute("errorMessage", "La voiture est déjà réservée pour cette période.");
                response.sendRedirect(request.getContextPath()+"/views/reservations.jsp?id="+ carId);
            }else if (dbHandler.hasBookingInDateRange(userId, startDate, endDate)) {
                // Le client a déjà une réservation dans cet intervalle, afficher un message d'erreur
            	request.getSession().setAttribute("errorMessage", "Vous avez déjà une réservation dans cet intervalle de temps.");
            	response.sendRedirect(request.getContextPath()+"/views/reservations.jsp?id="+ carId);
            }
            else {dbHandler.insertObject(newBooking, "Booking");
            request.getSession().setAttribute("valide", "La reservation a ete validee");
            // Rediriger vers une page de succès ou une autre page appropriée
            response.sendRedirect(request.getContextPath()+"/views/historique.jsp");
            }
        } catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            // Gérer les erreurs (par exemple afficher un message d'erreur sur la page de signup)
            request.setAttribute("errorMessage", "Une erreur s'est produite lors de la reservation. Veuillez réessayer.");
            request.getRequestDispatcher("/views/reservations.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
//        User user = (User) request.getSession().getAttribute("client");
//        if (user != null) {
//            // Rediriger vers la page d'erreur
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        } else {
//            // Rediriger vers la page de connexion
//            response.sendRedirect(request.getContextPath() + "/LogIn.jsp");
//        }
	}

}
