<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome | UserInWeb</title>

    <!-- Bootstrap -->
    <c:url var="bootstrapCss" value="/resources/core/css/bootstrap.min.css"/>
    <link href="${bootstrapCss}" rel="stylesheet"/>

    <style>
        body {
            background: linear-gradient(135deg, #89f7fe, #66a6ff); /* світло-блакитний градієнт */
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
            font-family: "Segoe UI", sans-serif;
        }

        .welcome-box {
            background: rgba(255, 255, 255, 0.15);
            border-radius: 20px;
            padding: 40px 60px;
            text-align: center;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
        }

        h1 {
            font-size: 2.5rem;
            margin-bottom: 20px;
            font-weight: 600;
        }

        .btn-login {
            background-color: #ffffff;
            color: #007bff;
            border-radius: 50px;
            padding: 12px 30px;
            font-weight: bold;
            transition: all 0.3s ease;
        }

        .btn-login:hover {
            background-color: #007bff;
            color: white;
            transform: scale(1.05);
        }
    </style>
</head>
<body>

<div class="welcome-box">
    <h1>Welcome to UserInWeb</h1>
    <p>Manage your users easily and securely.</p>
    <a href="${pageContext.request.contextPath}/login" class="btn btn-login btn-lg">Go to Login</a>
</div>

</body>
</html>
