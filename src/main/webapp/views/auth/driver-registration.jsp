<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Driver Registration - MegaCityCab</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .container { background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); width: 350px; }
        h2 { text-align: center; margin-bottom: 20px; }
        input, button { width: 100%; padding: 10px; margin: 5px 0; border: 1px solid #ccc; border-radius: 5px; }
        button { background-color: #007bff; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #0056b3; }
    </style>
</head>
<body>

<div class="container">
    <h2>Driver Registration</h2>

    <form method="post" action="<%= request.getContextPath() %>/api/auth/register-driver">
        <input type="text" name="firstName" placeholder="First Name" required>
        <input type="text" name="lastName" placeholder="Last Name" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="text" name="phone" placeholder="Phone" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="text" name="licenseNumber" placeholder="License Number" required>
        <input type="text" name="nic" placeholder="NIC" required>

        <button type="submit">Register</button>
    </form>

    <p>Already have an account? <a href="login.jsp">Login</a></p>
</div>

</body>
</html>
