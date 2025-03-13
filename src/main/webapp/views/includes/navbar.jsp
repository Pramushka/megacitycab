<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="model.User" %>
<%
    HttpSession sessionObj = request.getSession(false);
    User user = (sessionObj != null) ? (User) sessionObj.getAttribute("user") : null;
%>
<nav>
    <div class="nav-container">
        <a href="<%= request.getContextPath() %>/index.jsp" class="logo">MegaCityCab</a>

        <div class="nav-links">
            <a href="<%= request.getContextPath() %>/index.jsp">Home</a>
            <a href="<%= request.getContextPath() %>/views/customer/landing.jsp">Book Now</a>

            <% if (user != null) { %>
            <span>Welcome, <%= user.getEmail() %></span>
            <a href="<%= request.getContextPath() %>/api/auth/logout">Logout</a>
            <% } else { %>
            <a href="<%= request.getContextPath() %>/views/auth/login.jsp">Login</a>
            <% } %>
        </div>
    </div>
</nav>

<style>
    nav {
        background: #007bff;
        padding: 10px 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .logo {
        font-size: 20px;
        color: white;
        text-decoration: none;
        font-weight: bold;
    }
    .nav-links a {
        margin-left: 15px;
        color: white;
        text-decoration: none;
    }
    .nav-links span {
        color: white;
        font-weight: bold;
    }
</style>
