<%-- 
    Document   : orderSuccess
    Created on : 24 Jul 2025, 11:23:09
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Order Success</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <%@ include file="header.jsp" %>

        <div class="container mt-5">
            <div class="alert alert-success">
                <h4 class="alert-heading">✅ Order Successfully!</h4>
                <p>Thank you for your purchase. Below is your order summary:</p>
                <hr>
                <p><strong>Order ID:</strong> ${orderId}</p>
                <p><strong>Total:</strong> <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="₫" /></p>
                <c:if test="${not empty note}">
                    <p><strong>Note:</strong> ${note}</p>
                </c:if>
                <a href="MainController?action=ShowAllActiveProducts" class="btn btn-primary mt-3">Back to Home</a>
            </div>
        </div>

        <%@ include file="footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

