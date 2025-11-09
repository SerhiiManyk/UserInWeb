<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>

    <c:url var="loginUrl" value="/login" />
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
    <spring:url value="/resources/core/css/hello.css" var="coreCss" />
    <spring:url value="/resources/images/fon.jpeg" var="bgImage" />

    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />

    <style>
        body {
            background-image: url('${bgImage}');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            height: 100vh;
            margin: 0;
        }

        .title-container {
            text-align: center;
            padding-top: 150px;
            color: white;
            text-shadow: 2px 2px 4px #000000;
        }

        .login-button {
            margin-top: 30px;
        }
    </style>
</head>
<body>

<div class="title-container">
    <h1>Welcome to User In Web Spring learning site</h1>
    <a href="${loginUrl}" class="btn btn-primary btn-lg login-button">Start with Login page</a>
</div>

</body>
</html>
