<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Error</title>
        <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <style>
            body {
                background-color: #f8d7da;
            }
            .error-box {
                background-color: white;
                padding: 40px;
                border-radius: 12px;
                box-shadow: 0 0 20px rgba(0,0,0,0.1);
                margin-top: 100px;
            }
        </style>
    </head>
    <body>
        <div class="container d-flex justify-content-center align-items-center min-vh-100">
            <div class="error-box text-center border border-danger">
                <h2 class="text-danger">⚠ Oops! Something went wrong.</h2>
                <p class="mt-3 text-muted">
                    <c:out value="${requestScope.errorMessage != null ? requestScope.errorMessage : 'An unexpected error occurred. Please try again later.'}" />
                </p>
                <button onclick="history.back()" class="btn btn-secondary me-2">⬅ Back</button>
        </div>
    </body>
</html>
