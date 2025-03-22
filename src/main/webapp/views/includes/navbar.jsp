<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="model.User" %>

<%
    HttpSession sessionObj = request.getSession(false);
    User user = (sessionObj != null) ? (User) sessionObj.getAttribute("user") : null;
%>

<nav>
    <div class="nav-container">
        <div class="nav-left">
            <a href="<%= request.getContextPath() %>/index.jsp" class="logo">MegaCityCab</a>
        </div>

        <div class="nav-right">
            <a href="<%= request.getContextPath() %>/index.jsp">Home</a>
            <a href="<%= request.getContextPath() %>/views/customer/booking.jsp">Book Now</a>
            <a href="<%= request.getContextPath() %>/views/customer/myBookings.jsp">My Bookings</a>

            <% if (user != null) { %>
            <span>Welcome, <%= user.getEmail() %></span>
            <a href="<%= request.getContextPath() %>/api/auth/logout">Logout</a>
            <% } else { %>
            <a href="<%= request.getContextPath() %>/views/auth/login.jsp">Login</a>
            <% } %>

            <!-- Admin Login Button -->
            <a href="<%= request.getContextPath() %>/views/admin/login.jsp" class="admin-icon" title="Admin Login">
                &#128274; Admin
            </a>
        </div>
    </div>
</nav>

<style>
    nav {
        background: #007bff;
        padding: 15px 0;
        display: flex;
        justify-content: center;
    }

    .nav-container {
        width: 100%;
        max-width: 1200px; /* Keeps content centered */
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 500px;
    }

    .nav-left .logo {
        font-size: 22px;
        color: white;
        text-decoration: none;
        font-weight: bold;
    }

    .nav-right {
        display: flex;
        align-items: center;
    }

    .nav-right a {
        margin-left: 20px;
        color: white;
        text-decoration: none;
        font-size: 16px;
    }

    .nav-right span {
        color: white;
        font-weight: bold;
    }

    .admin-icon {
        font-weight: bold;
        color: yellow;
        margin-left: 15px;
        font-size: 16px;
    }

    .admin-icon:hover {
        color: orange;
    }
</style>
