package controlleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.OracleDatabaseHandler;
import models.User;

/**
 * Servlet implementation class signup
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
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
		
		
		        // R�cup�rer les donn�es du formulaire
	        String firstName = request.getParameter("firstName");
	        String lastName = request.getParameter("lastName");
	        String email = request.getParameter("email");
	        String address = request.getParameter("address"); 
	        String phoneNumber = request.getParameter("phoneNumber");
	        String password = request.getParameter("password");

	        // Cr�er un nouvel utilisateur
	        User newUser = new User(UUID.randomUUID().toString());
	        newUser.setNom(firstName);
	        newUser.setPrenom(lastName);
	        newUser.setEmail(email);
	        newUser.setPhone_number(phoneNumber);
	        newUser.setPassword(password);
	        newUser.setAddress(address);
	        newUser.setType("client");

	        // Ins�rer le nouvel utilisateur dans la base de donn�es
	        try {
	            OracleDatabaseHandler dbHandler = new OracleDatabaseHandler("jdbc:mysql://localhost:3306/miniprojetJEE", "root", "");
	            dbHandler.insertObject(newUser, "User");
	            // Rediriger vers une page de succ�s ou une autre page appropri�e
	            response.sendRedirect("../views/LogIn.jsp");
	        } catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
	            e.printStackTrace();
	            // G�rer les erreurs (par exemple afficher un message d'erreur sur la page de signup)
	            request.setAttribute("errorMessage", "Une erreur s'est produite lors de la cr�ation de votre compte. Veuillez r�essayer.");
	            request.getRequestDispatcher("/views/LogIn.jsp").forward(request, response);
	        } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		

	}

}
