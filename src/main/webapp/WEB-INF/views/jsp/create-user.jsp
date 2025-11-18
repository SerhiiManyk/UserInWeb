<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Create User</title>
    <c:url var="bootstrapCss" value="/resources/core/css/bootstrap.min.css" />
    <c:url var="mainCss" value="/resources/core/css/hello.css" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${mainCss}" rel="stylesheet" />
    <style>
            /* фон сторінки */
            body {
                background-color: #ffe6cc; /* світло-оранжевий відтінок */
            }
      /* невелике вирівнювання форми */
      .container { margin-top: 30px; }
      .text-danger { margin-top: 5px; display:block; }
    </style>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="<c:url value='/' />">Spring MVC App</a>
    </div>
  </div>
</nav>

<div class="container" style="max-width: 700px;">

  <h2>Create New User</h2>
  <hr/>

  <c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
  </c:if>

  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <form:form method="POST" modelAttribute="user" cssClass="form-horizontal" action="${pageContext.request.contextPath}/user/save">

      <div class="form-group">
          <label class="col-sm-2 control-label" for="username">Username</label>
          <div class="col-sm-10">
              <form:input path="username" cssClass="form-control" id="username" placeholder="Enter username" />
              <form:errors path="username" cssClass="text-danger" />
          </div>
      </div>

      <div class="form-group">
          <label class="col-sm-2 control-label" for="password">Password</label>
          <div class="col-sm-10">
              <form:password path="password" cssClass="form-control" id="password" placeholder="Enter password" />
              <form:errors path="password" cssClass="text-danger" />
          </div>
      </div>

      <div class="form-group">
          <label class="col-sm-2 control-label" for="email">Email</label>
          <div class="col-sm-10">
              <form:input path="email" cssClass="form-control" id="email" placeholder="you@example.com" />
              <form:errors path="email" cssClass="text-danger" />
          </div>
      </div>

      <div class="form-group">
          <label class="col-sm-2 control-label" for="phone">Phone</label>
          <div class="col-sm-10">
              <form:input path="phone" cssClass="form-control" id="phone" placeholder="012-345-678" />
              <form:errors path="phone" cssClass="text-danger" />
          </div>
      </div>

      <div class="form-group">
          <label class="col-sm-2 control-label" for="address">Address</label>
          <div class="col-sm-10">
              <form:input path="address" cssClass="form-control" id="address" placeholder="Street, City" />
              <form:errors path="address" cssClass="text-danger" />
          </div>
      </div>

      <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-primary">Save</button>

              <a href="<c:url value='/user/create' />" class="btn btn-warning text-white">Clear</a>

              <a href="javascript:history.back()" class="btn btn-danger">Come Back</a>

          </div>
      </div>

  </form:form>

</div>

</body>
</html>
