<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Register - CreationStore</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link href="assets/css/style.css" rel="stylesheet"/>
        <link href="assets/css/login.css" rel="stylesheet"/>
    </head>
    <body class="bg-light-green d-block justify-content-center align-items-center" style="height: 100vh;">

        <c:if test="${not empty sessionScope.user}">
            <%@include file="header.jsp" %>
        </c:if>

     
            <div class="container mt-5">
                <div class="card shadow-sm rounded-4 p-4 mx-auto" style="max-width: 500px;">
                    <h3 class="text-center text-success mb-3">${not empty sessionScope.user? "My Profile" : "Create your account"}</h3>

                    <form action="MainController" method="POST">
                        <input type="hidden" name="action" value="${not empty sessionScope.user? "updateProfile" : "register"}" />
                        <input type="hidden" name="userIdstr" value="${sessionScope.user.userId}" />


                        <div class="mb-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" name="username" required value="${not empty sessionScope.user? sessionScope.user.username : requestScope.username}">
                            <c:if test="${not empty requestScope.usernameError}">
                                <small class="text-danger">${requestScope.usernameError}</small>
                            </c:if>
                        </div>

                        <c:if test="${ empty sessionScope.user}">
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control" name="password" required value="${not empty requestScope.password ? requestScope.password : ""}">
                            </div>
                        </c:if>


                        <div class="mb-3">
                            <label for="fullName" class="form-label">Full Name</label>
                            <input type="text" class="form-control" name="fullName" required value="${not empty sessionScope.user? sessionScope.user.fullName : requestScope.fullName}">
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" name="email" required value="${not empty sessionScope.user? sessionScope.user.getEmail() : requestScope.email}">
                            <c:if test="${not empty requestScope.emailError}">
                                <small class="text-danger">${requestScope.emailError}</small>
                            </c:if>
                        </div>

                        <button type="submit" class="btn btn-success w-100">${not empty sessionScope.user? "Update Profile" : "Register"}</button>
                    </form>

                    <c:if test="${not empty requestScope.checkError}">
                        <div class="alert alert-warning mt-3 text-center">${requestScope.checkError}</div>
                    </c:if>
                    <c:if test="${not empty requestScope.message}">
                        <div class="alert alert-success mt-3 text-center">${requestScope.message}</div>
                    </c:if>

                    <div class="mt-3 text-center">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <a href="verifyAcount.jsp" class="text-decoration-none">Change Password</a><br/>
                                <a href="MainController?action=ShowAllActiveProducts" class="text-decoration-none">Back To Dashboard</a>
                            </c:when>
                            <c:otherwise>
                                <a href="login.jsp" class="text-decoration-none">Already have an account? Login</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
     

        <c:if test="${not empty sessionScope.user}">
            <%@ include file="footer.jsp" %>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </c:if>

    </body>
</html>
