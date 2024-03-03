<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import= "models.*" %>
<%@ page import= "metier.SingletonConnection" %>
<%@ page import= "java.util.*" %>
<%@ page import= "java.util.Date" %>
<%@ page import= "java.sql.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>North Star</title>
    <link rel="stylesheet" href="../css/historique.css">
</head>
<body>
    <section class="hero">
        <nav>
             <a href="home.jsp"><img src="<%=request.getContextPath()%>/assets/Picsart_24-02-22_14-56-36-631.png" alt=""></a>
            <ul>
                <a href="car_listing.jsp"><li class="elem">Car listing</li></a>
                <% User user = (User)request.getSession().getAttribute("client");
                String user_id = user.getId();
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
    <div class="cont">
     <h2>Your Reservations :</h2>
        <div class="display">
            <table frame=void rules=rows>
                    <tr>
                        <form action="" method="get">
                            <th></th>
                            <th>model</th>
                            <th>pickup date</th>
                            <th>dropoff date</th>
                            <th>Location</th>
                            <th>Status</th>
                            <th>price</th>
                        </form>
                    </tr>
                <tbody id='geeks'>
                				<% 
								Connection connection = SingletonConnection.getConnection();
								List<Booking> bookings = Booking.getBookingsHistorique(user_id);
								for (Booking booking : bookings) { 
									String status = null;
		                    		Date today = new Date();
		                    		if ( (today.before(booking.getEnd_date()) || today.equals(booking.getEnd_date())) && (today.after(booking.getStart_date()) || today.equals(booking.getStart_date())))
		                    			status = "Active";
		                    		else
		                    			status = "Not Active";
								%>
                    <tr>
                        <td><img src="../assets/<%= Car.getImageById(booking.getCar_id()) %>" alt="" width="150vh" height="auto"></td>
                        <td><%= booking.getModelByID(booking.getId()) %></td>
                        <td><%= booking.getStart_date() %></td>
                        <td><%= booking.getEnd_date() %></td>
                        <td><%= Location.getLocationbyID(booking.getLocationByID(booking.getId()))%></td>
                        <td><%= status %></td>
                        <td><%= booking.getTotal_price() %> $</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>