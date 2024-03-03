package controlleur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metier.SingletonConnection;
import models.*;
/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String locationId = request.getParameter("pickupLocation");
        String startDateStr = request.getParameter("dateDebut");
        String endDateStr = request.getParameter("dateFin");

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
        
        // Encodez les paramètres de recherche dans l'URL de redirection
        String redirectURL = String.format("%s/views/car_listing.jsp?pickupLocation=%s&dateDebut=%s&dateFin=%s",
                request.getContextPath(), locationId, startDateStr, endDateStr);
        
        // Rediriger vers la page de liste des voitures avec les paramètres de recherche
        response.sendRedirect(redirectURL);
    }


}
