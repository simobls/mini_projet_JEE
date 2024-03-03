<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import= "models.*" %>
<%@ page import= "metier.SingletonConnection" %>
<%@ page import= "java.util.*" %>
<%@ page import= "java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<%
    String carId = request.getParameter("id");
	Connection connection = SingletonConnection.getConnection();
	String query = "SELECT * FROM car WHERE id = ?";
	PreparedStatement statement = connection.prepareStatement(query);
	statement.setString(1, carId);
	ResultSet resultSet = statement.executeQuery();
	String loc = null;
	Car car = new Car(carId);
	if (resultSet.next()) {
        car.setType(resultSet.getString("type"));
        car.setModel(resultSet.getString("model"));
        car.setYear(resultSet.getInt("year"));
        car.setAvailability_status(resultSet.getString("availability_status"));
        car.setPrice_per_day(resultSet.getDouble("price_per_day"));
        car.setImage_url(resultSet.getString("image_url"));
        car.setLocation_id(resultSet.getString("location_id"));
        loc = Location.getNameById(car.getLocation_id());
    }
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reservation</title>
    <link rel="stylesheet" href="../css/reservation.css">
</head>
<body>
     <nav>
             <a href="home.jsp"><img src="<%=request.getContextPath()%>/assets/Picsart_24-02-22_14-56-36-631.png" alt=""></a>
            <ul>
                <a href="car_listing.jsp"><li class="elem">Car listing</li></a>
                <% User user = (User)request.getSession().getAttribute("client");
                if(user != null) { %>
                <a href="historique.jsp"><li class="elem">Reservations</li></a>
                <% }else { %>
                <a href="LogIn.jsp"><li class="elem">Reservations</li></a>
                <% } %>
                <li class="elem">About us</li>
            </ul>
            <div>
                 <% 
            
            if(user != null) { %>
                <p><%= user.getPrenom() %></p>
                <form action="../logout" method="post" class="logout">
                    <button class="sign">Log Out</button>
                </form>
            <% } else { %>
                <a href="LogIn.jsp"><button class="log">Log In</button></a>
                <a href="SignUp.jsp"><button class="sign">Sign Up</button></a>
            <% } %>
            </div>
        </nav>
    <section class="car_listing">
            <div class="car">
                <div class="pic">
                    <img src="../assets/<%= car.getImage_url() %>" alt="">
                </div>
                <h2><%= car.getModel() %></h2>
                <div class="info">
                    <p><svg xmlns="http://www.w3.org/2000/svg" id="Layer_1" data-name="Layer 1" viewBox="0 0 24 24" width="15" height="15"><path d="M12,0A12,12,0,0,0,0,12v0c.59,15.905,23.416,15.89,24,0v0A12,12,0,0,0,12,0Zm0,2a10.01,10.01,0,0,1,8.878,5.4l-7.049,1.41a9.64,9.64,0,0,1-3.8,0L3.108,7.428A10.01,10.01,0,0,1,12,2ZM9,21.54a10.027,10.027,0,0,1-6.935-8.4l3.9.78a2.994,2.994,0,0,1,2.05,1.515l.624,1.153A3,3,0,0,1,9,18.014Zm6,0V18.014a3,3,0,0,1,.362-1.428l.624-1.154a3,3,0,0,1,2.05-1.514l3.9-.78A10.027,10.027,0,0,1,15,21.54Zm2.644-9.583a4.987,4.987,0,0,0-3.416,2.522L13.6,15.633a5.009,5.009,0,0,0-.6,2.381V21.95a10.126,10.126,0,0,1-2,0V18.014a5.009,5.009,0,0,0-.6-2.381L9.772,14.48a4.985,4.985,0,0,0-3.416-2.523l-4.314-.863a9.82,9.82,0,0,1,.324-1.775l7.272,1.454a11.629,11.629,0,0,0,4.583,0l7.406-1.481a9.845,9.845,0,0,1,.331,1.8Z"/></svg>
                        <%= car.getType() %>
                    </p>
                    <p>
                        <svg xmlns="http://www.w3.org/2000/svg" id="Outline" viewBox="0 0 24 24" width="15" height="15"><path d="M12,6a4,4,0,1,0,4,4A4,4,0,0,0,12,6Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,12Z"/><path d="M12,24a5.271,5.271,0,0,1-4.311-2.2c-3.811-5.257-5.744-9.209-5.744-11.747a10.055,10.055,0,0,1,20.11,0c0,2.538-1.933,6.49-5.744,11.747A5.271,5.271,0,0,1,12,24ZM12,2.181a7.883,7.883,0,0,0-7.874,7.874c0,2.01,1.893,5.727,5.329,10.466a3.145,3.145,0,0,0,5.09,0c3.436-4.739,5.329-8.456,5.329-10.466A7.883,7.883,0,0,0,12,2.181Z"/></svg>
                       <%= loc%>
                    </p>
                </div>
                <p class="price">Price: $<%= car.getPrice_per_day() %>/day</p>
            </div>
            <div class="form_res">
                <h2>Fill the reservation form</h2>
                <form action="../addReserv">
                    <div>
                    	<input type="text" value="<%= car.getId() %>" class="hide" name="carId">
                    	<input type="text" value="<%= car.getPrice_per_day() %>" class="hide" name="price">
                        <div class="input">
                            <p>Pickup date</p>
                            <input type="date" id="startDate" onchange="calculateDateDifference() updateDropoffMinDate()" name="startDate" required>
                            <script>
					    	var inputDate = document.getElementById("startDate");
					    	var currentDate = new Date().toISOString().split('T')[0];
					    	inputDate.min = currentDate;
					    </script>
                        </div>
                        <div class="input">
                            <p>Dropoff date</p>
                            <input type="date" id="endDate" onchange="calculateDateDifference(<%= car.getPrice_per_day() %>) " name="endDate" required> 
                        </div>
                    </div>
                    <p id="dateDifference">the reservation price will be : <span id="differenceValue"> 0$</span></p>
                    <% String erreur = (String) session.getAttribute("errorMessage");
			       if (erreur != null && !erreur.isEmpty()) { %>
			       <div class="erreur-message">
			           <%= erreur %>
			       </div>
			    <% }
       				session.removeAttribute("errorMessage"); // Effacer le message d'erreur pour qu'il ne soit affiché qu'une fois %>
                    <button class="res">Reserve</button>
                </form>
                
            </div>
    </section>
    <script>
            function calculateDateDifference(price) {
            // Get the input elements
            var startDateInput = document.getElementById("startDate");
            var endDateInput = document.getElementById("endDate");

            // Get the selected date values
            var startDate = new Date(startDateInput.value);
            var endDate = new Date(endDateInput.value);

            // Calculate the difference in milliseconds
            var difference = Math.abs(endDate - startDate);

            // Convert the difference to days
            var daysDifference = difference / (1000 * 60 * 60 * 24) * price;

            // Display the difference
            var differenceValue = document.getElementById("differenceValue");
            differenceValue.textContent = daysDifference + "$";
            
            
            }
      </script>
</body>
</html>