<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 13/03/2025
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="dao.DriverDAO" %>
<%@ page import="config.DatabaseConfig" %>
<%
    int driverId = (int) session.getAttribute("driverId");
    String contact = request.getParameter("contact");
    String referral = request.getParameter("referral");

    DriverDAO driverDAO = new DriverDAO(DatabaseConfig.getConnection());
    boolean success = driverDAO.submitVerification(driverId, contact, referral);

    if (success) {
        out.println("<p>Verification details submitted. Waiting for admin approval.</p>");
    } else {
        out.println("<p>Error submitting details. Try again.</p>");
    }
%>

