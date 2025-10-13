<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <title>Login - CreationStore</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link href="assets/css/style.css" rel="stylesheet"/>
        <link href="assets/css/login.css" rel="stylesheet"/>
    </head>
    <body class="bg-light-green d-flex justify-content-center align-items-center" style="height: 100vh;">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <c:redirect url="MainController?action=ShowAllActiveProducts"/>
            </c:when>
            <c:otherwise>
                <div class="container mt-5">
                    <div class="card shadow-sm rounded-4 p-4 mx-auto" style="max-width: 400px;">
                        <h3 class="text-center text-success mb-3">Login to CreationStore</h3>

                        <form action="MainController" method="POST">
                            <input type="hidden" name="action" value="login" />
                            <div class="mb-3">
                                <label for="strUsername" class="form-label">Username</label>
                                <input type="text" class="form-control" name="strUsername" required>
                            </div>
                            <div class="mb-3">
                                <label for="strPassword" class="form-label">Password</label>
                                <input type="password" class="form-control" name="strPassword" required>
                            </div>
                            <button type="submit" class="btn btn-success w-100">Login</button>
                        </form>

                        <div class="mt-3 text-center">
                            <a href="register.jsp" class="text-decoration-none">Don't have an account? Register</a>
                        </div>

                        <c:if test="${not empty requestScope.message}">
                            <div class="alert alert-danger mt-3 text-center">${requestScope.message}</div>
                        </c:if>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>


    </body>
</html>
