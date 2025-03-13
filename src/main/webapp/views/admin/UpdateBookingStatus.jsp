<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    int bookingId = Integer.parseInt(request.getParameter("bookingId"));
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Update Booking Status</title>
</head>
<body>
<h2>Update Status for Booking #<%= bookingId %></h2>
<form action="<%=request.getContextPath()%>/api/admin/management/update-booking-status" method="post">
    <input type="hidden" name="bookingId" value="<%= bookingId %>">
    <label>Select Status:</label>
    <select name="status">
        <option value="PENDING">Pending</option>
        <option value="APPROVED">Approved</option>
        <option value="COMPLETED">Completed</option>
        <option value="CANCELLED">Cancelled</option>
    </select>
    <button type="submit">Update</button>
</form>
</body>
</html>

