<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || session.getAttribute("driverId") == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Driver Verification</title>
</head>
<body>
<h2>Driver Verification</h2>
<form action="submitVerification.jsp" method="post">
    <label for="contact">Additional Contact Number:</label>
    <input type="text" name="contact" required><br>

    <label for="referral">Referral Code:</label>
    <input type="text" name="referral"><br>

    <button type="submit">Submit for Verification</button>
</form>
</body>
</html>
