<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import= "models.*" %>
<%@ page import= "metier.SingletonConnection" %>
<%@ page import= "java.util.*" %>
<%@ page import= "java.sql.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>North Star</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css">
</head>
<body>
    <section class="hero">
        <nav>
             <a href="home.jsp"><img src="<%=request.getContextPath()%>/assets/Picsart_24-02-22_14-56-36-631.png" alt=""></a>
            <ul>
                <a href="car_listing.jsp"><li class="elem">Car listing</li></a>
                <li class="elem">Reservations</li>
                <li class="elem">About us</li>
            </ul>
            <div>
                 <% 
            User user = (User)request.getSession().getAttribute("client");
            if(user != null) { %>
                <p>Welcome, <%= user.getPrenom() %></p>
                <form action="LogoutServlet" method="post">
                    <button class="sign">Log Out</button>
                </form>
            <% } else { %>
                <a href="LogIn.jsp"><button class="log">Log In</button></a>
                <a href="SignUp.jsp"><button class="sign">Sign Up</button></a>
            <% } %>
            </div>
        </nav>
        <div class="content">
            
            <form method="post" action="" method="post">
                <h1>Rent a car for every journey</h1>
                <div class="form">
                    <div class="input">
                          <p>Pickup location</p>
                        <select name="pickupLocation" placeholder="Enter your location">
						    <option value="default">Pickup City</option>
								<% 
								Connection connection = SingletonConnection.getConnection();
								List<Location> locations = Location.getAllLocations();
								for (Location location : locations) { 
								%>
			                <option value="<%= location.getId() %>"><%= location.getName() %></option>
			               		<% } %>
						</select>
                    </div>
                    <div class="input">
					    <p>Pickup date</p>
					    <input id="pickupDate" type="date"  onchange="updateDropoffMinDate()" name="dateDebut">
					    <script>
					    	var inputDate = document.getElementById("pickupDate");
					    	var currentDate = new Date().toISOString().split('T')[0];
					    	inputDate.min = currentDate;
					    </script>
					</div>
					<div class="input">
					    <p>Dropoff date</p>
					    <input id="dropoffDate" type="date" name="dateFin">
					</div>
                </div>
                <input class="Search_btn" type="submit" value="Search">
            </form>
        </div>
    </section>
    <section class="car_listing">
        <div class="text">
            <h2>Our Collection</h1>
            <p>Experience an amazing journey with your choice.</p>
        </div>
    <%
    List<Car> cars = Car.getAllCars();
    //Map<String, String> locationNames = new HashMap<String, String>();
    //for (Car car : cars) {
     //   if (!locationNames.containsKey(car.getLocation_id())) {
    //        locationNames.put(car.getLocation_id(), car.getLocationName(car.getLocation_id()));
    //    }
    //}
%>

<div class="cars">
    <% int i = 0;
    for (Car car : cars) {
        if (i < 6) {
            %>
            <div class="car">
                <div class="pic">
                    <img src="../assets/<%= car.getImage_url() %>" alt="Car Image">
                </div>
                <h2><%= car.getModel() %></h2>
                <div class="info">
                    <p><svg xmlns="http://www.w3.org/2000/svg" id="Layer_1" data-name="Layer 1" viewBox="0 0 24 24" width="15" height="15">
                    	<path d="M12,0A12,12,0,0,0,0,12v0c.59,15.905,23.416,15.89,24,0v0A12,12,0,0,0,12,0Zm0,2a10.01,10.01,0,0,1,8.878,5.4l-7.049,1.41a9.64,9.64,0,0,1-3.8,0L3.108,7.428A10.01,10.01,0,0,1,12,2ZM9,21.54a10.027,10.027,0,0,1-6.935-8.4l3.9.78a2.994,2.994,0,0,1,2.05,1.515l.624,1.153A3,3,0,0,1,9,18.014Zm6,0V18.014a3,3,0,0,1,.362-1.428l.624-1.154a3,3,0,0,1,2.05-1.514l3.9-.78A10.027,10.027,0,0,1,15,21.54Zm2.644-9.583a4.987,4.987,0,0,0-3.416,2.522L13.6,15.633a5.009,5.009,0,0,0-.6,2.381V21.95a10.126,10.126,0,0,1-2,0V18.014a5.009,5.009,0,0,0-.6-2.381L9.772,14.48a4.985,4.985,0,0,0-3.416-2.523l-4.314-.863a9.82,9.82,0,0,1,.324-1.775l7.272,1.454a11.629,11.629,0,0,0,4.583,0l7.406-1.481a9.845,9.845,0,0,1,.331,1.8Z"/></svg>
                        <%= car.getType() %></p>
                    <p>
                        <svg xmlns="http://www.w3.org/2000/svg" id="Outline" viewBox="0 0 24 24" width="15" height="15">
                        <path d="M12,6a4,4,0,1,0,4,4A4,4,0,0,0,12,6Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,12Z"/><path d="M12,24a5.271,5.271,0,0,1-4.311-2.2c-3.811-5.257-5.744-9.209-5.744-11.747a10.055,10.055,0,0,1,20.11,0c0,2.538-1.933,6.49-5.744,11.747A5.271,5.271,0,0,1,12,24ZM12,2.181a7.883,7.883,0,0,0-7.874,7.874c0,2.01,1.893,5.727,5.329,10.466a3.145,3.145,0,0,0,5.09,0c3.436-4.739,5.329-8.456,5.329-10.466A7.883,7.883,0,0,0,12,2.181Z"/></svg>
						<%=
                        Location.getLocationById(car.getLocation_id()).getName()
                        %>                        
                    </p>
                </div>
                <p class="price">Price: $<%= car.getPrice_per_day() %>/day</p>
            </div>
            <% i++;
        }
    }
%>

</div>



         <div class="more_div">
            <a href="car_listing.jsp"><button class="more">See more</button></a>
        </div>
     </section>
    <section class="brands">
        <div>Brand</div>
        <div>Brand</div>
        <div>Brand</div>
        <div>Brand</div>
        <div>Brand</div>
        <div>Brand</div>
    </section>
    <section class="infos">
        <div class="info_1">
            <div class="info_1_1"></div>
            <div class="info_1_2"></div>
        </div>
        <div class="info_2"></div>
    </section>
    <footer>
        <div class="footer_1">
            <div class="footer_1_1">
                <img src="<%=request.getContextPath()%>/assets/output-onlinepngtools.png">
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam commodi consectetur, dignissimos doloremque dolores maxime.</p>
            </div>
            <div class="footer_1_2">
                <p class="tt">About</p>
                <div>
                    <p>About us</p>
                    <p>Blog</p>
                    <p>Career</p>
                </div>
            </div>
            <div class="footer_1_2">
                <p class="tt">Support</p>
                <div>
                    <p>Contact us</p>
                    <p>Return</p>
                    <p>FAQ</p>
                </div>
            </div>
            <div class="footer_1_4">
                <p class="tt2">Get updates</p>
                <div class="inputForm1">
                    <input type="text" class="input" placeholder="Enter your Email" required>
                    <button>Subscribe</button>
                </div>
                <div class="icons">
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0,0,256,256" width="4vh" height="4vh"><g fill="#ffffff" fill-rule="nonzero" stroke="none" stroke-width="1" stroke-linecap="butt" stroke-linejoin="miter" stroke-miterlimit="10" stroke-dasharray="" stroke-dashoffset="0" font-family="none" font-weight="none" font-size="none" text-anchor="none" style="mix-blend-mode: normal"><g transform="scale(10.66667,10.66667)"><path d="M12,2c-5.523,0 -10,4.477 -10,10c0,5.013 3.693,9.153 8.505,9.876v-7.226h-2.474v-2.629h2.474v-1.749c0,-2.896 1.411,-4.167 3.818,-4.167c1.153,0 1.762,0.085 2.051,0.124v2.294h-1.642c-1.022,0 -1.379,0.969 -1.379,2.061v1.437h2.995l-0.406,2.629h-2.588v7.247c4.881,-0.661 8.646,-4.835 8.646,-9.897c0,-5.523 -4.477,-10 -10,-10z"></path></g></g></svg>
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0,0,256,256" width="4vh" height="4vh"><g fill="#ffffff" fill-rule="nonzero" stroke="none" stroke-width="1" stroke-linecap="butt" stroke-linejoin="miter" stroke-miterlimit="10" stroke-dasharray="" stroke-dashoffset="0" font-family="none" font-weight="none" font-size="none" text-anchor="none" style="mix-blend-mode: normal"><g transform="scale(10.66667,10.66667)"><path d="M8,3c-2.757,0 -5,2.243 -5,5v8c0,2.757 2.243,5 5,5h8c2.757,0 5,-2.243 5,-5v-8c0,-2.757 -2.243,-5 -5,-5zM8,5h8c1.654,0 3,1.346 3,3v8c0,1.654 -1.346,3 -3,3h-8c-1.654,0 -3,-1.346 -3,-3v-8c0,-1.654 1.346,-3 3,-3zM17,6c-0.55228,0 -1,0.44772 -1,1c0,0.55228 0.44772,1 1,1c0.55228,0 1,-0.44772 1,-1c0,-0.55228 -0.44772,-1 -1,-1zM12,7c-2.757,0 -5,2.243 -5,5c0,2.757 2.243,5 5,5c2.757,0 5,-2.243 5,-5c0,-2.757 -2.243,-5 -5,-5zM12,9c1.654,0 3,1.346 3,3c0,1.654 -1.346,3 -3,3c-1.654,0 -3,-1.346 -3,-3c0,-1.654 1.346,-3 3,-3z"></path></g></g></svg>
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0,0,256,256" width="4vh" height="4vh"><g fill="#ffffff" fill-rule="nonzero" stroke="none" stroke-width="1" stroke-linecap="butt" stroke-linejoin="miter" stroke-miterlimit="10" stroke-dasharray="" stroke-dashoffset="0" font-family="none" font-weight="none" font-size="none" text-anchor="none" style="mix-blend-mode: normal"><g transform="scale(10.66667,10.66667)"><path d="M21.582,6.186c-0.23,-0.86 -0.908,-1.538 -1.768,-1.768c-1.56,-0.418 -7.814,-0.418 -7.814,-0.418c0,0 -6.254,0 -7.814,0.418c-0.86,0.23 -1.538,0.908 -1.768,1.768c-0.418,1.56 -0.418,5.814 -0.418,5.814c0,0 0,4.254 0.418,5.814c0.23,0.86 0.908,1.538 1.768,1.768c1.56,0.418 7.814,0.418 7.814,0.418c0,0 6.254,0 7.814,-0.418c0.861,-0.23 1.538,-0.908 1.768,-1.768c0.418,-1.56 0.418,-5.814 0.418,-5.814c0,0 0,-4.254 -0.418,-5.814zM10,15.464v-6.928l6,3.464z"></path></g></g></svg>
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0,0,256,256" width="4vh" height="4vh"><g fill="#ffffff" fill-rule="nonzero" stroke="none" stroke-width="1" stroke-linecap="butt" stroke-linejoin="miter" stroke-miterlimit="10" stroke-dasharray="" stroke-dashoffset="0" font-family="none" font-weight="none" font-size="none" text-anchor="none" style="mix-blend-mode: normal"><g transform="scale(10.66667,10.66667)"><path d="M19,3h-14c-1.105,0 -2,0.895 -2,2v14c0,1.105 0.895,2 2,2h14c1.105,0 2,-0.895 2,-2v-14c0,-1.105 -0.895,-2 -2,-2zM9,17h-2.523v-7h2.523zM7.694,8.717c-0.771,0 -1.286,-0.514 -1.286,-1.2c0,-0.686 0.514,-1.2 1.371,-1.2c0.771,0 1.286,0.514 1.286,1.2c0,0.686 -0.514,1.2 -1.371,1.2zM18,17h-2.442v-3.826c0,-1.058 -0.651,-1.302 -0.895,-1.302c-0.244,0 -1.058,0.163 -1.058,1.302c0,0.163 0,3.826 0,3.826h-2.523v-7h2.523v0.977c0.325,-0.57 0.976,-0.977 2.197,-0.977c1.221,0 2.198,0.977 2.198,3.174z"></path></g></g></svg>
                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0,0,256,256" width="4vh" height="4vh"><g fill="#ffffff" fill-rule="nonzero" stroke="none" stroke-width="1" stroke-linecap="butt" stroke-linejoin="miter" stroke-miterlimit="10" stroke-dasharray="" stroke-dashoffset="0" font-family="none" font-weight="none" font-size="none" text-anchor="none" style="mix-blend-mode: normal"><g transform="scale(10.66667,10.66667)"><path d="M2.36719,3l7.0957,10.14063l-6.72266,7.85938h2.64063l5.26367,-6.16992l4.31641,6.16992h6.91016l-7.42187,-10.625l6.29102,-7.375h-2.59961l-4.86914,5.6875l-3.97266,-5.6875zM6.20703,5h2.04883l9.77734,14h-2.03125z"></path></g></g></svg>
                </div>
            </div>
        </div>
        <div class="footer_2">
            <p>©2024 NorthStar. All rights reserved</p>
            <div>
                <p>Privacy policy</p>
                <p>Terms of service</p>
            </div>
        </div>
    </footer>
    <script>
    function setMinDateToday() {
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = today.getFullYear();

        today = yyyy + '-' + mm + '-' + dd;
        document.getElementById("pickupDate").min = today;
    }
    function updateDropoffMinDate() {
        var pickupDateInput = document.getElementById('pickupDate');
        var dropoffDateInput = document.getElementById('dropoffDate');

        // Set the minimum date for dropoff date to the selected pickup date
        dropoffDateInput.min = pickupDateInput.value;
    }
</script>
</body>
</html>