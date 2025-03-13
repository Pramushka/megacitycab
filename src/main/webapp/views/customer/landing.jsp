<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 01:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="model.User, model.Vehicle, dao.VehicleDAO, config.DatabaseConfig, java.util.List" %>
<%@ include file="../includes/navbar.jsp" %>

<%
    User user = (User) session.getAttribute("user");
    Integer customerId = (Integer) session.getAttribute("customerId");

    if (user == null || customerId == null) {
        response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
        return;
    }

    List<Vehicle> availableVehicles = new VehicleDAO(DatabaseConfig.getConnection()).getAllVehicles();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MegaCityCab | Book Your Ride</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 90%;
            margin: auto;
            padding: 20px;
        }
        .banner {
            background: url('${pageContext.request.contextPath}/assets/banner.jpg') no-repeat center center/cover;
            height: 300px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 28px;
            font-weight: bold;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.6);
        }
        .section {
            background: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
        }
        .vehicle-list {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }
        .vehicle-card {
            background: #fff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0px 0px 5px rgba(0,0,0,0.1);
            width: 30%;
        }
        .book-btn {
            display: inline-block;
            background: #007bff;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
        }
        .book-btn:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>

<div class="banner">
    Welcome to MegaCityCab, <%= user.getFirstName() %>!
</div>

<div class="container">
    <!-- Available Vehicles Section -->
    <div class="section">
        <h2>Available Vehicles</h2>
        <div class="vehicle-list">
            <% for (Vehicle vehicle : availableVehicles) { %>
            <div class="vehicle-card">
                <h3><%= vehicle.getModel() %></h3>
                <p><strong>Type:</strong> <%= vehicle.getType() %></p>
                <p><strong>Capacity:</strong> <%= vehicle.getCapacity() %></p>
                <p><strong>Vehicle Number:</strong> <%= vehicle.getVehicleNumber() %></p>
                <a href="<%= request.getContextPath() %>/views/customer/book-vehicle.jsp?vehicleId=<%= vehicle.getVehicleId() %>" class="book-btn">Book Now</a>
            </div>
            <% } %>
        </div>
    </div>
</div>

</body>
</html>
