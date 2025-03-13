<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.BookingDAO, model.Booking, config.DatabaseConfig, java.util.List" %>

<%
  BookingDAO bookingDAO = new BookingDAO(DatabaseConfig.getConnection());
  List<Booking> bookings = bookingDAO.getAllBookings();
%>
<%@ page import="model.Admin" %>
<%
  Admin admin = (Admin) session.getAttribute("admin");
  if (admin == null) {
    response.sendRedirect(request.getContextPath() + "/views/admin/login.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>All Bookings</title>
</head>
<body>
<h2>All Bookings</h2>
<table border="1">
  <tr>
    <th>Booking ID</th>
    <th>Customer ID</th>
    <th>Driver ID</th>
    <th>Pickup Location</th>
    <th>Dropoff Location</th>
    <th>Fare</th>
    <th>Status</th>
    <th>Actions</th>
  </tr>
  <% for (Booking booking : bookings) { %>
  <tr>
    <td><%= booking.getBookingId() %></td>
    <td><%= booking.getCustomerId() %></td>
    <td><%= booking.getDriverId() == 0 ? "Not Assigned" : booking.getDriverId() %></td>
    <td><%= booking.getPickupLocation() %></td>
    <td><%= booking.getDropoffLocation() %></td>
    <td>$<%= booking.getFare() %></td>
    <td><%= booking.getStatus() %></td>
    <td>
      <a href="AssignDriver.jsp?bookingId=<%= booking.getBookingId() %>">Assign Driver</a> |
      <a href="UpdateBookingStatus.jsp?bookingId=<%= booking.getBookingId() %>">Update Status</a> |
      <a href="<%=request.getContextPath()%>/api/admin/management/cancel-booking?bookingId=<%= booking.getBookingId() %>">Cancel</a>
    </td>
  </tr>
  <% } %>
</table>
</body>
</html>

