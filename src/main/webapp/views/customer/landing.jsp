<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="model.User" %>
<%@ include file="../includes/navbar.jsp" %>

<%
    // Check if a user is logged in
    User loggedInUser = (User) session.getAttribute("user");
    boolean isLoggedIn = (loggedInUser != null);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MegaCityCab | Book Your Ride</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 90%;
            margin: auto;
            padding: 20px;
        }
        .banner {
            background: url('<%= request.getContextPath() %>/assets/banner.jpg') no-repeat center center/cover;
            height: 300px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 28px;
            font-weight: bold;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.6);
        }
        .section {
            background: white;
            padding: 20px;
            margin: 20px 0;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
        }
        .vehicle-list {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }
        .vehicle-card {
            background: #fff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0px 0px 5px rgba(0,0,0,0.1);
            width: 30%;
        }
        .book-btn {
            display: inline-block;
            background: #007bff;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
        }
        .book-btn:hover {
            background: #0056b3;
        }
    </style>

    <script>
        const contextPath = '<%= request.getContextPath() %>';
        const isLoggedIn = <%= isLoggedIn %>;
    </script>
</head>
<body>

<div class="banner">
    Welcome to MegaCityCab,
    <% if (isLoggedIn) { %>
    <%= loggedInUser.getFirstName() %>!
    <% } else { %>
    Guest!
    <% } %>
</div>

<div class="container">
    <!-- Available Vehicles Section -->
    <div class="section">
        <h2>Available Vehicles</h2>
        <div class="vehicle-list" id="vehicle-list">
            <p>Loading available vehicles...</p>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $.ajax({
            url: contextPath + '/api/vehicles',
            type: 'GET',
            dataType: 'json',
            success: function (vehicles) {
                let vehicleList = $('#vehicle-list');
                vehicleList.empty();

                if (vehicles.length === 0) {
                    vehicleList.append('<p>No available vehicles at the moment.</p>');
                } else {
                    vehicles.forEach(vehicle => {
                        let vehicleModel = vehicle.model || 'Unknown Model';
                        let vehicleType = vehicle.type || 'Unknown Type';
                        let vehicleCapacity = vehicle.capacity || 'N/A';
                        let vehicleNumber = vehicle.vehicleNumber || 'N/A';

                        let bookLink = `<a href="${contextPath}/book-ride?vehicleId=${vehicle.vehicleId}" class="book-btn">Book Now</a>`;
                        let loginLink = `<a href="${contextPath}/views/auth/login.jsp" class="book-btn">Login to Book</a>`;
                        let actionButton = isLoggedIn ? bookLink : loginLink;

                        let vehicleCard = `
                            <div class="vehicle-card">
                                <h3>${vehicleModel}</h3>
                                <p><strong>Type:</strong> ${vehicleType}</p>
                                <p><strong>Capacity:</strong> ${vehicleCapacity}</p>
                                <p><strong>Vehicle Number:</strong> ${vehicleNumber}</p>
                                ${actionButton}
                            </div>
                        `;
                        vehicleList.append(vehicleCard);
                    });
                }
            },
            error: function () {
                $('#vehicle-list').html('<p>Error fetching vehicle data.</p>');
            }
        });
    });
</script>

</body>
</html>
