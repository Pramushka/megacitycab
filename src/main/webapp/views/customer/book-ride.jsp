<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="model.Vehicle, model.Booking, java.util.List" %>
<%@ include file="../includes/navbar.jsp" %>

<%
    List<Vehicle> vehicles = (List<Vehicle>) request.getAttribute("vehicles");
    Booking preBooking = (Booking) session.getAttribute("pendingBooking");

    // ✅ Debug info - show this in browser if list is null or empty
    if (vehicles == null || vehicles.isEmpty()) {
%>
<p style="color: red;">DEBUG: No vehicles list received or list is empty.</p>
<%
} else {
%>
<p style="color: green;">DEBUG: Vehicle list received with <%= vehicles.size() %> entries.</p>
<%
    }
%>


<html>
<head>
    <title>Book Ride</title>
    <style>
        body { font-family: Arial; background: #f4f4f4; }
        .container { width: 50%; margin: 30px auto; padding: 20px; background: #fff; border-radius: 8px; }
        label, input, select { width: 100%; margin-bottom: 10px; padding: 8px; }
        .btn-group { display: flex; justify-content: space-between; gap: 10px; }
        .btn { background: #007bff; color: #fff; border: none; padding: 10px; cursor: pointer; border-radius: 5px; }
        .btn:hover { background: #0056b3; }
        .summary { background: #e0f7fa; padding: 10px; border-radius: 6px; margin-top: 20px; }
    </style>
</head>
<body>
<div class="container">
    <h2>Book Your Ride</h2>

    <form method="post" action="<%= request.getContextPath() %>/api/bookings/check">
        <label>Select Vehicle:</label>
        <select name="vehicleId" required>
            <% if (vehicles != null && !vehicles.isEmpty()) {
                for (Vehicle v : vehicles) { %>
            <option value="<%= v.getVehicleId() %>"
                    <%= (preBooking != null && v.getVehicleId() == preBooking.getVehicleId()) ? "selected" : "" %>>
                <%= v.getModel() %> - <%= v.getVehicleNumber() %>
            </option>
            <% } } else { %>
            <option disabled selected>No vehicles available</option>
            <% } %>
        </select>

        <label>Pickup Location:</label>
        <input type="text" name="pickupLocation" value="<%= preBooking != null ? preBooking.getPickupLocation() : "" %>" required>

        <label>Dropoff Location:</label>
        <input type="text" name="dropoffLocation" value="<%= preBooking != null ? preBooking.getDropoffLocation() : "" %>" required>

        <label>Booking Date:</label>
        <input type="date" name="bookingDate"
               value="<%= preBooking != null && preBooking.getBookingDate() != null ? preBooking.getBookingDate().toString() : "" %>" required>

        <div class="btn-group">
            <button type="submit" class="btn">Check Fare</button>
        </div>
    </form>

    <% if (preBooking != null && preBooking.getFare() > 0) { %>
    <div class="summary">
        <p><strong>Estimated Fare:</strong> Rs. <%= preBooking.getFare() %></p>
    </div>

    <form method="post" action="<%= request.getContextPath() %>/api/bookings/create">
        <button type="submit" class="btn">Confirm Booking</button>
    </form>
    <% } %>
</div>
</body>
</html>
