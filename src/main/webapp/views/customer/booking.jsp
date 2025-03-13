<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Vehicle" %>
<%@ page import="dao.VehicleDAO" %>
<%@ page import="config.DatabaseConfig" %>

<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj == null || sessionObj.getAttribute("userEmail") == null) {
        response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
        return;
    }
%>

<%@ include file="../includes/navbar.jsp" %>


<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book a Ride | MegaCityCab</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }
        .container { width: 50%; margin: auto; padding: 20px; background: white; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; }
        label, input, select { display: block; width: 100%; margin-bottom: 10px; }
        button { background-color: #007bff; color: white; padding: 10px; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
    </style>
</head>
<body>

<div class="container">
    <h2>Book Your Ride</h2>

    <form id="bookingForm">
        <label for="vehicle">Select Vehicle:</label>
        <select id="vehicle" required>
            <% for (Vehicle vehicle : availableVehicles) { %>
            <option value="<%= vehicle.getVehicleId() %>"><%= vehicle.getModel() %> - <%= vehicle.getVehicleNumber() %></option>
            <% } %>
        </select>

        <label for="pickup">Pickup Location:</label>
        <input type="text" id="pickup" placeholder="Enter pickup location" required>

        <label for="dropoff">Drop-off Location:</label>
        <input type="text" id="dropoff" placeholder="Enter drop-off location" required>

        <button type="submit">Book Now</button>
    </form>
</div>

<script>
    document.getElementById("bookingForm").addEventListener("submit", function(event) {
        event.preventDefault();

        const vehicleId = document.getElementById("vehicle").value;
        const pickupLocation = document.getElementById("pickup").value;
        const dropoffLocation = document.getElementById("dropoff").value;
        const customerId = "<%= session.getAttribute("customerId") %>";

        const bookingData = { customerId, vehicleId, pickupLocation, dropoffLocation, fare: 0, status: "REQUESTED" };

        fetch("/api/booking/create", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(bookingData)
        })
            .then(response => response.json())
            .then(data => {
                alert("Booking successful! Redirecting...");
                window.location.href = "/views/customer/bookingConfirmation.jsp";
            })
            .catch(error => console.error("Error:", error));
    });
</script>

</body>
</html>

