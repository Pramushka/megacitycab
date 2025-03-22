<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="model.User" %>

<%
    User user = (User) session.getAttribute("user");
%>

<nav>
    <div class="nav-container">
        <!-- Left: Logo -->
        <div class="nav-left">
            <a href="<%= request.getContextPath() %>/index.jsp" class="logo">MegaCityCab</a>
        </div>

        <!-- Center: Welcome -->
        <div class="nav-center">
            <% if (user != null) { %>
            <span class="welcome-text">Welcome, <%= user.getEmail() %></span>
            <% } %>
        </div>

        <!-- Right: Navigation Links -->
        <div class="nav-right">
            <a href="<%= request.getContextPath() %>/index.jsp">Home</a>
            <a href="<%= request.getContextPath() %>/views/customer/book-ride.jsp">Book Now</a>

            <% if (user != null) { %>
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
        max-width: 1400px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        position: relative;
    }

    .nav-left .logo {
        font-size: 22px;
        color: white;
        text-decoration: none;
        font-weight: bold;
        padding-left: 20px;
    }

    .nav-center {
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
    }

    .welcome-text {
        color: white;
        font-size: 16px;
        font-weight: bold;
    }

    .nav-right {
        display: flex;
        align-items: center;
        gap: 20px;
        padding-right: 20px;
    }

    .nav-right a {
        color: white;
        text-decoration: none;
        font-size: 16px;
    }

    .admin-icon {
        font-weight: bold;
        color: yellow;
        font-size: 16px;
    }

    .admin-icon:hover {
        color: orange;
    }
</style>
