<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Login</title>
    <c:url var="bootstrapCss" value="/resources/core/css/bootstrap.min.css" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <style>
        body {
            background-color: #e0f0ff; /* світло-блакитний фон для всієї сторінки */
        }
        .container { margin-top: 30px; max-width: 500px; }
        .text-danger { margin-top: 5px; display: block; }
    </style>
</head>
<body>

<div class="container">

    <h2>Login</h2>
    <hr/>

    <!-- Виводимо повідомлення про успіх -->
    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>

    <!-- Виводимо повідомлення про помилку -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- Форма логіну -->
    <form:form method="POST" modelAttribute="user" cssClass="form-horizontal" action="${pageContext.request.contextPath}/login">

        <div class="form-group">
            <label for="email">Email</label>
            <form:input path="email" cssClass="form-control" id="email" placeholder="you@example.com" />
            <form:errors path="email" cssClass="text-danger" />
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <form:password path="password" cssClass="form-control" id="password" placeholder="Enter password" />
            <form:errors path="password" cssClass="text-danger" />
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary">Login</button>
            <a href="<c:url value='/user/create' />" class="btn btn-warning">Register</a>
        </div>

    </form:form>
</div>

</body>
</html>
