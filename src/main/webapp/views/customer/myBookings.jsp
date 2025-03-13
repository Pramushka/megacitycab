<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Booking" %>
<%@ page import="dao.BookingDAO" %>
<%@ page import="config.DatabaseConfig" %>

<%
    // Ensure customer is logged in
    Integer customerId = (Integer) session.getAttribute("customerId");
    if (customerId == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        return;
    }

    BookingDAO bookingDAO = new BookingDAO(DatabaseConfig.getConnection());
    List<Booking> bookings = bookingDAO.getBookingsByCustomerId(customerId);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Bookings | MegaCityCab</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        button {
            padding: 5px 10px;
            color: white;
            background-color: red;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>

<h2>My Bookings</h2>
<table>
    <tr>
        <th>Booking ID</th>
        <th>Pickup Location</th>
        <th>Drop-off Location</th>
        <th>Fare</th>
        <th>Driver</th>
        <th>Vehicle</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    <% for (Booking booking : bookings) { %>
    <tr>
        <td><%= booking.getBookingId() %></td>
        <td><%= booking.getPickupLocation() %></td>
        <td><%= booking.getDropoffLocation() %></td>
        <td>$<%= booking.getFare() %></td>
        <td><%= booking.getDriverName() %> (License: <%= booking.getLicenseNumber() %>)</td>
        <td><%= booking.getVehicleModel() %> (<%= booking.getVehicleNumber() %>)</td>
        <td><%= booking.getStatus() %></td>
        <td>
            <% if ("REQUESTED".equals(booking.getStatus())) { %>
            <button onclick="cancelBooking(<%= booking.getBookingId() %>)">Cancel</button>
            <% } %>
        </td>
    </tr>
    <% } %>
</table>

<script>
    function cancelBooking(bookingId) {
        fetch("/api/bookings/cancel?bookingId=" + bookingId, { method: "POST" })
            .then(response => response.json())
            .then(data => alert(data.message))
            .catch(error => console.error("Error:", error));
    }
</script>

</body>
</html>

