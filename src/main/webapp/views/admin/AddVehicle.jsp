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
  <title>Add Vehicle</title>
</head>
<body>
<h2>Add New Vehicle</h2>
<form action="<%=request.getContextPath()%>/api/admin/management/vehicles" method="post">
  <input type="text" name="model" placeholder="Vehicle Model" required>
  <input type="text" name="type" placeholder="Vehicle Type" required>
  <input type="number" name="capacity" placeholder="Capacity" required>
  <input type="text" name="vehicleNumber" placeholder="Vehicle Number" required>
  <button type="submit">Add Vehicle</button>
</form>
</body>
</html>
