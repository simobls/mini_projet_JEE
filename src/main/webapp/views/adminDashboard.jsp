<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="metier.SingletonConnection" %>
<%@ page import="models.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="../css/dashboard.css">
    <link rel="stylesheet" href="../css/car.css">
</head>
<body>
    <section>
        <div class="tools">
            <a href="home.jsp"><img src="../assets/Picsart_24-02-22_14-56-36-631.png" alt="" class="logo"></a>
            <div>
                <a href="adminDashboard.jsp"><img src="../assets/sports-car.png" alt="" class="logo2 main"></a>
                <a href="reservation_page.jsp"><img src="../assets/icons8-reservation-30.png" alt="" class="logo2"></a>
                <a href="location_page.jsp"><img src="../assets/icons8-location-50.png" alt="" class="logo2"></a>
            </div>
            <form action="../logout">
            <button class="but"><img src="../assets/icons8-logout-64.png" alt="" class="logo3"></button>
            </form>
        </div>
        <div class="cont">
            <div class="add_form">
                <form action="<%=request.getContextPath()%>/addCar" class="form" method = "post">
                <div>
                    <div class="in_name">
                        <div>
                            <div class="inputForm1">
                                <input type="text" class="input" placeholder="Model" required name="model">
                            </div>
                        </div>
                        <div>
                            <div class="inputForm1">
                                <select class="input" placeholder="Type" required name="type">
                                    <option value="default">Type</option>
                                    <option value="Automatic">Automatic</option>
                                    <option value="Manual">Manual</option>
                                </select>
                            </div>
                        </div>
                        <div>
                            <div class="inputForm1">
                                <input type="number" min="2000" max="2024" class="input" placeholder="Year" required name="year">
                            </div>
                        </div>
                    </div>
                    <div class="in_name">
                        <div>
                            <div class="inputForm1">
                                <select class="input" placeholder="Lacation" required name="location">
                                 <option value="default">Location</option>
                                <% 
								Connection connection = SingletonConnection.getConnection();
								List<Location> locations = Location.getAllLocations();
								for (Location location : locations) { 
								%>
			                <option value="<%= location.getId() %>"><%= location.getName() %></option>
			               		<% } %>
                                </select>
                            </div>
                        </div>
                        <div>
                            <div class="inputForm1">
                                <input type="text" class="input" placeholder="Price" required name="price">
                            </div>
                        </div>
                        <div>
                            <div class="inputForm1">
                                <label class="custom-file-upload">
                                    <input type="file"  name="image" accept=".png, .jpg, .jpeg">
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                    <div class="in_name">
                        <button class="addbtn">Add</button>
                    </div>
                </form>
                <% String valide = (String) session.getAttribute("valide");
			       if (valide!= null && !valide.isEmpty()) { %>
			       <div class="valide-message">
			           <%= valide %>
			       </div>
			      <% }
			       String updated = (String) session.getAttribute("updated");
			       if (updated!= null && !updated.isEmpty()) { %>
			       <div class="valide-message">
			           <%= updated %>
			       </div>
			       <% }
			       String error = (String) session.getAttribute("errormMssage");
			       if (error!= null && !error.isEmpty()) { %>
			       <div class="valide-message">
			           <%= error %>
			       </div>
			       <% }
			       String deleted = (String) session.getAttribute("deleted");
			       if (deleted!= null && !deleted.isEmpty()) { %>
			       <div class="valide-message">
			           <%= deleted %>
			       </div>
			   <% }
       session.removeAttribute("valide");
       session.removeAttribute("updated");
       session.removeAttribute("deleted");
       session.removeAttribute("errorMessage");// Effacer le message de valide pour qu'il ne soit affiché qu'une fois %>
            </div>
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
                                <th></th>
                                <th>model</th>
                                <th>year</th>
                                <th>type</th>
                                <th>Location</th>
                                <th>Status</th>
                                <th>price</th>
                                <th>Action</th>
                            </form>
                        </tr>
                        <%
                        	List<Car> Cars = Car.getAllCars();
                        	for (Car car : Cars){ 
                        		Location location = Location.getLocationById(car.getLocation_id());
                        	%>
                        <tr>
                            <td><img src="../assets/<%= car.getImage_url() %>" alt="" width="150vh" height="auto"></td>
                            <td><%= car.getModel() %></td>
                            <td><%= car.getYear() %></td>
                            <td><%= car.getType() %></td>
                            <td><%= location.getName() %></td>
                            <td><%= car.getAvailability_status() %></td>
                            <td><%= car.getPrice_per_day() %></td>
                            <td class='opt'>
                                <form id='deleteForm_<%= car.getId() %>' method='get' action="../deleteCar">
							        <button name='modify' id='update' class='update-btn'><img src='../assets/icons8-edit-property-30.png' class='img1'></button>
									<button name='delete_id' type='submit' value="<%= car.getId() %>"
									    onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette voiture ?');">
									    <img src='../assets/icons8-delete-30.png' class='img2'>
									</button>							    
								</form>
                            </td>
                        </tr>
                        <tr>
                            <td colspan='100%' id='update-form1'>
                                <form method='post' action='../updateCar' class='update form' >
                                    <div>
                                        <div class="in_name">
                                            <div class="flex-divs">
                                                <div class="inputForm1">
                                                    <input type="text" class="input" placeholder="Car model" required value="<%= car.getModel() %>" name="model">
                                                </div>
                                            </div>
                                            <div class="flex-divs">
                                                <div class="inputForm1">
                                                    <select class="input" placeholder="Car model" required name="type">
                                                        <option value="<%= car.getType() %>"><%= car.getType() %></option>
                                                        <% if (car.getType().equals("Automatic")) {%>
                                                        <option value="Manual">Manual</option>
                                                        <% } else { %>
                                                        <option value="Automatic">Automatic</option>
                                                        <%} %>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="flex-divs">
                                                <div class="inputForm1">
                                                    <input type="number" class="input" placeholder="Year" required value="<%= car.getYear() %>" name="year">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="in_name">
                                            <div class="flex-divs">
                                                <div class="inputForm1">
                                                    <select class="input" placeholder="Car location" required name="location">
					                                <% 
													for (Location location1 : locations) { 
													%>
								                		<option value="<%= location1.getId() %>"><%= location1.getName() %></option>
								               		<% } %>
					                                </select>
                                                </div>
                                            </div>
                                            <div class="flex-divs">
                                                <div class="inputForm1">
                                                    <input type="text" class="input" placeholder="Price" required value="<%= car.getPrice_per_day() %>" name="price">
                                                </div>
                                            </div>
                                            <div class="flex-divs">
                                                <div class="inputForm1">
                                                    <label class="custom-file-upload">
                                                        <input type="file" value="../assets/<%= car.getImage_url() %>" name="image" accept=".png, .jpg, .jpeg"/>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                        <div class="in_name">
                                            <button class="addbtn" value="<%= car.getId() %>" name="id">Update</button>
                                        </div>
                                </form>
                            </td>
                        </tr>
                        <%
                        }
                        %>
                        
                        </tbody>
                </table>
            </div>
        </div>
    </section>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Get all the update buttons and update forms
            var updateBtns = document.querySelectorAll('.update-btn');
            var updateForms = document.querySelectorAll('.update');
    
            // Hide all update forms on page load
            updateForms.forEach(function (form) {
                form.style.display = 'none';
            });
    
            // Add click event listener to each update button
            updateBtns.forEach(function (updateBtn, index) {
                updateBtn.addEventListener('click', function (event) {
                    // Prevent the default form submission
                    event.preventDefault();
    
                    // Toggle the visibility of the corresponding update form
                    updateForms[index].style.display = (updateForms[index].style.display === 'none' || updateForms[index].style.display === '') ? 'flex' : 'none';
    
                    // Hide other update forms
                    updateForms.forEach(function (form, formIndex) {
                        if (formIndex !== index) {
                            form.style.display = 'none';
                        }
                    });
                });
            });
        });
    </script>
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