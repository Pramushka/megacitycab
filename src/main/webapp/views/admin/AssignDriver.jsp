<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.DriverDAO, model.Driver, config.DatabaseConfig, java.util.List" %>

<%
  int bookingId = Integer.parseInt(request.getParameter("bookingId"));
  DriverDAO driverDAO = new DriverDAO(DatabaseConfig.getConnection());
  List<Driver> drivers = driverDAO.getAvailableDrivers();
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Assign Driver</title>
</head>
<body>
<h2>Assign Driver to Booking #<%= bookingId %></h2>
<form action="<%=request.getContextPath()%>/api/admin/management/assign-driver" method="post">
  <input type="hidden" name="bookingId" value="<%= bookingId %>">
  <label>Select Driver:</label>
  <select name="driverId">
    <% for (Driver driver : drivers) { %>
    <option value="<%= driver.getDriverId() %>"><%= driver.getFirstName() %> - License: <%= driver.getLicenseNumber() %></option>
    <% } %>
  </select>
  <button type="submit">Assign Driver</button>
</form>
</body>
</html>

