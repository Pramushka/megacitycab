<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.VehicleDAO, model.Vehicle, config.DatabaseConfig, java.util.List" %>

<%
  VehicleDAO vehicleDAO = new VehicleDAO(DatabaseConfig.getConnection());
  List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
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
  <title>All Vehicles</title>
</head>
<body>
<h2>List of Vehicles</h2>
<table border="1">
  <tr>
    <th>Model</th>
    <th>Type</th>
    <th>Capacity</th>
    <th>Vehicle Number</th>
  </tr>
  <% for (Vehicle vehicle : vehicles) { %>
  <tr>
    <td><%= vehicle.getModel() %></td>
    <td><%= vehicle.getType() %></td>
    <td><%= vehicle.getCapacity() %></td>
    <td><%= vehicle.getVehicleNumber() %></td>
  </tr>
  <% } %>
</table>
</body>
</html>

