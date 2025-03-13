<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="model.User" %>
<%@ page import="dao.PaymentDAO" %>
<%@ page import="dao.BookingDAO" %>
<%@ page import="config.DatabaseConfig" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Booking" %>
<%
    // Ensure user is a driver
    User user = (User) session.getAttribute("user");
    if (user == null || session.getAttribute("driverId") == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        return;
    }

    int driverId = (int) session.getAttribute("driverId");

    // Fetch total earnings from PaymentDAO
    PaymentDAO paymentDAO = new PaymentDAO(DatabaseConfig.getConnection());
    double totalEarnings = paymentDAO.getDriverEarnings(driverId);

    // Fetch assigned bookings
    BookingDAO bookingDAO = new BookingDAO(DatabaseConfig.getConnection());
    List<Booking> assignedBookings = bookingDAO.getBookingsByDriverId(driverId);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Driver Dashboard</title>
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
        .dashboard-header {
            background-color: #007bff;
            color: white;
            padding: 15px;
            text-align: center;
            font-size: 24px;
        }
        .earnings-section, .ride-section {
            background: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        .earnings-amount {
            font-size: 28px;
            font-weight: bold;
            color: #28a745;
        }
    </style>
</head>
<body>

<div class="dashboard-header">
    Welcome, <%= user.getFirstName() %> (Driver)
</div>

<div class="container">
    <!-- Driver Earnings -->
    <div class="earnings-section">
        <h2>Your Total Earnings</h2>
        <p class="earnings-amount">$<%= totalEarnings %></p>
    </div>

    <!-- Assigned Rides -->
    <div class="ride-section">
        <h2>Your Assigned Rides</h2>
        <table border="1">
            <tr>
                <th>Booking ID</th>
                <th>Pickup</th>
                <th>Dropoff</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            <% for (Booking booking : assignedBookings) { %>
            <tr>
                <td><%= booking.getBookingId() %></td>
                <td><%= booking.getPickupLocation() %></td>
                <td><%= booking.getDropoffLocation() %></td>
                <td><%= booking.getStatus() %></td>
                <td>
                    <% if (booking.getStatus().equals("IN_PROGRESS")) { %>
                    <form action="<%= request.getContextPath() %>/api/driver/complete" method="post">
                        <input type="hidden" name="bookingId" value="<%= booking.getBookingId() %>">
                        <button type="submit">Complete Ride</button>
                    </form>
                    <% } %>
                </td>
            </tr>
            <% } %>
        </table>
    </div>
</div>

</body>
</html>
