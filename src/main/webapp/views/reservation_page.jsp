<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="metier.SingletonConnection" %>
<%@ page import="models.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking</title>
    <link rel="stylesheet" href="../css/dashboard.css">
</head>
<body>
    <section>
        <div class="tools">
            <a href="home.jsp"><img src="../assets/Picsart_24-02-22_14-56-36-631.png" alt="" class="logo"></a>
            <div>
                <a href="adminDashboard.jsp"><img src="../assets/sports-car.png" alt="" class="logo2"></a>
                <a href="reservation_page.jsp"><img src="../assets/icons8-reservation-30.png" alt="" class="logo2 main"></a>
                <a href="location_page.jsp"><img src="../assets/icons8-location-50.png" alt="" class="logo2"></a>
            </div>
            <form action="../logout">
            <button class="but"><img src="../assets/icons8-logout-64.png" alt="" class="logo3"></button>
            </form>
        </div>
        <div class="cont">
            <div class="display">
                <div class="search">
                    <img src="../assets/icons8-search.gif" alt="">
                   <form method="get">
                        <input id="gfg" type="search" placeholder="Search"></input>
                    </form>
                </div>
                <table frame=void rules=rows>
                        <tr>
                            <form action="" method="get">
                                <th>model</th>
                                <th>pickup date</th>
                                <th>dropoff date</th>
                                <th>Location</th>
                                <th>Status</th>
                                <th>price</th>
                                <th>email</th>
                            </form>
                        </tr>
                    <tbody id='geeks'>
                    <% List<Booking> bookings = Booking.getAllBookings();
                    	
                    	for(Booking booking : bookings){
                    		String status = null;
                    		Date today = new Date();
                    		if ( (today.before(booking.getEnd_date()) || today.equals(booking.getEnd_date())) && (today.after(booking.getStart_date()) || today.equals(booking.getStart_date())))
                    			status = "Active";
                    		else
                    			status = "Not Active";
                    	
                    %>
                        <tr>
                            <td><%= booking.getModelByID(booking.getId()) %></td>
                            <td><%= booking.getStart_date() %></td>
                            <td><%= booking.getEnd_date() %></td>
                            <td><%= Location.getLocationbyID(booking.getLocationByID(booking.getId()))%></td>
                            <td><%= status %></td>
                            <td><%= booking.getTotal_price() %></td>
                            <td><%= booking.getuserByID(booking.getId()) %></td>
                        </tr>
                    <% } %>
                                
                    </tbody>
                </table>
            </div>
        </div>
    </section>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Get the input element and attach a keyup event listener
            var searchInput = document.getElementById('gfg');
            searchInput.addEventListener('keyup', function () {
                // Get the value of the input and convert it to lowercase
                var searchTerm = searchInput.value.toLowerCase();
    
                // Get all the rows in the table
                var rows = document.getElementById('geeks').getElementsByTagName('tr');
    
                // Loop through each row and hide/show based on the search term
                for (var i = 0; i < rows.length; i++) {
                    var dataCells = rows[i].getElementsByTagName('td');
                    var rowVisible = false;
    
                    // Loop through each data cell in the row
                    for (var j = 0; j < dataCells.length; j++) {
                        var cellText = dataCells[j].innerText.toLowerCase();
    
                        // If the search term is found in any cell, set rowVisible to true
                        if (cellText.includes(searchTerm)) {
                            rowVisible = true;
                            break;
                        }
                    }
    
                    // Hide or show the row based on rowVisible
                    rows[i].style.display = rowVisible ? '' : 'none';
                }
            });
        });
    </script>    
</body>
</html>
