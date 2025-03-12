<%--
  Created by IntelliJ IDEA.
  User: Ninu
  Date: 12/03/2025
  Time: 00:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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

<div class="container">
    <div class="login-container">
        <h3 class="text-center">MegaCityCab Login</h3>

        <!-- Role Toggle -->
        <div class="form-group mt-3">
            <label>Select Role:</label>
            <select class="form-select" id="userRole">
                <option value="CUSTOMER">Customer</option>
                <option value="DRIVER">Driver</option>
            </select>
        </div>

        <!-- Email Field -->
        <div class="form-group mt-3">
            <label>Email</label>
            <input type="email" class="form-control" id="email" placeholder="Enter your email">
        </div>

        <!-- Password Field -->
        <div class="form-group mt-3">
            <label>Password</label>
            <input type="password" class="form-control" id="password" placeholder="Enter your password">
        </div>

        <!-- Remember Me & Forgot Password -->
        <div class="d-flex justify-content-between align-items-center mt-3">
            <div>
                <input type="checkbox" id="rememberMe"> <label for="rememberMe">Remember Me</label>
            </div>
            <a href="#" class="text-decoration-none">Forgot Password?</a>
        </div>

        <!-- Login Button -->
        <button class="btn btn-primary w-100 mt-3" onclick="login()">Login</button>

        <!-- Register Link -->
        <div class="text-center mt-3">
            <a href="register.jsp" class="text-decoration-none">Don't have an account? Register</a>
        </div>
    </div>
</div>

<!-- JavaScript (AJAX Call for Login) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function login() {
        let email = $("#email").val();
        let password = $("#password").val();
        let role = $("#userRole").val();

        if (email === "" || password === "") {
            alert("Please fill all fields!");
            return;
        }

        $.ajax({
            url: "/api/auth/login",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ email: email, password: password, role: role }),
            success: function(response) {
                alert("Login successful!");
                window.location.href = (role === "CUSTOMER") ? "customer-dashboard.jsp" : "driver-dashboard.jsp";
            },
            error: function() {
                alert("Invalid credentials. Try again!");
            }
        });
    }
</script>

</body>
</html>
