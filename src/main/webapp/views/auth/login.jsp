<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Login | MegaCityCab</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background: #f8f9fa;
        }
        .login-container {
            max-width: 400px;
            margin: auto;
            margin-top: 10%;
            padding: 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .toggle-btn {
            width: 100%;
        }
    </style>
</head>
<body>
<%
    String error = request.getParameter("error");
    if ("invalid".equals(error)) {
%>
<div class="alert alert-danger mt-3">Invalid credentials. Please try again.</div>
<% } %>

<div class="container">
    <div class="login-container">
        <h3 class="text-center">MegaCityCab Login</h3>

        <form method="post" action="<%= request.getContextPath() %>/api/auth/login">
            <select class="form-select" name="userType">
                <option value="CUSTOMER">Customer</option>
                <option value="DRIVER">Driver</option>
            </select>

            <input type="email" name="email" class="form-control" placeholder="Enter your email" required>
            <input type="password" name="password" class="form-control" placeholder="Enter your password" required>

            <button type="submit" class="btn btn-primary w-100 mt-3">Login</button>
        </form>


        <!-- Register Link -->
        <div class="text-center mt-3">
            <a href="register.jsp" class="text-decoration-none">Don't have an account? Register</a>
        </div>
    </div>
</div>

</body>
</html>
