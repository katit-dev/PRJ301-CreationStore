<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <title>verifyAcount - CreationStore</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

        <link href="assets/css/style.css" rel="stylesheet"/>
        <link href="assets/css/login.css" rel="stylesheet"/>
    </head>
    <body class="bg-light-green d-block justify-content-center align-items-center" style="height: 100vh;">

        <%@include file="header.jsp" %>

        <div class="container mt-5">
            <div class="card shadow-sm rounded-4 p-4 mx-auto" style="max-width: 500px;">
                <h3 class="text-center text-success mb-3">Please Verify Account</h3>

                <form action="MainController" method="POST">
                    <input type="hidden" name="action" value="verifyPassword" />
                    <input type="hidden" name="userIdstr" value="${sessionScope.user.userId}" />

                    <div class="mb-3 position-relative">
                        <input type="password" class="form-control pe-5" id="passwordVerify" name="passwordVerify" required 
                               placeholder="Enter current password to verify" value="${not empty requestScope.passwordVerify? requestScope.passwordVerify : ""}">
                        <i class="fa fa-eye position-absolute top-50 end-0 translate-middle-y me-3 toggle-password"
                           toggle="#passwordVerify" style="cursor: pointer;"></i>
                    </div>


                    <button type="submit" class="btn btn-success w-100">Verify</button>
                </form>


                <c:if test="${not empty requestScope.checkError}">
                    <div class="alert alert-warning mt-3 text-center">${requestScope.checkError}</div>
                </c:if>
                <c:if test="${not empty requestScope.message}">
                    <div class="alert alert-success mt-3 text-center">${requestScope.message}</div>
                </c:if>

                <div class="mt-3 text-center">
                    <a href="register.jsp" class="text-decoration-none">Update Profile</a><br/>
                    <a href="MainController?action=ShowAllActiveProducts" class="text-decoration-none">Back To Dashboard</a>

                </div>
            </div>
        </div>

        <%@ include file="footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.querySelectorAll(".toggle-password").forEach(function (eyeIcon) {
                    eyeIcon.addEventListener("click", function () {
                        const input = document.querySelector(this.getAttribute("toggle"));
                        const isPassword = input.type === "password";
                        input.type = isPassword ? "text" : "password";
                        this.classList.toggle("fa-eye");
                        this.classList.toggle("fa-eye-slash");
                    });
                });
            });
        </script>

    </body>
</html>
