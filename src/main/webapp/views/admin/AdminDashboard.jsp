<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="model.Admin" %>
<%
  Admin admin = (Admin) session.getAttribute("admin");
  if (admin == null) {
    response.sendRedirect(request.getContextPath() + "/views/admin/login.jsp");
    return;
  }
%>

<html>
<head>
  <title>Admin Dashboard</title>
</head>
<body>
<h2>Welcome, Admin</h2>
<nav>
  <a href="<%=request.getContextPath()%>/views/admin/AddVehicle.jsp">Add Vehicle</a> |
  <a href="<%=request.getContextPath()%>/views/admin/ViewVehicles.jsp">View Vehicles</a> |
  <a href="<%=request.getContextPath()%>/views/admin/ViewDrivers.jsp">View Drivers</a> |
  <a href="<%=request.getContextPath()%>/views/admin/ViewBookings.jsp">View Bookings</a> |
  <a href="<%=request.getContextPath()%>/api/admin/logout">Logout</a>
</nav>
</body>
</html>

