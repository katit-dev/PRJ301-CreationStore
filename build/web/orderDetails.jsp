<%-- 
    Document   : orderDetails
    Created on : 24 Jul 2025, 13:36:20
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Order Details</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <%@ include file="header.jsp" %>

        <div class="container mt-5">
            <h3>🧾 Order #${orderId} Details</h3>
            <table class="table table-bordered mt-3">
                <thead class="table-light">
                    <tr>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Price at Time</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${orderDetails}">
                    <tr>
                        <td>${item.productName}</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.priceAtTime}" type="currency" currencySymbol="₫"/></td>
                    <td><fmt:formatNumber value="${item.quantity * item.priceAtTime}" type="currency" currencySymbol="₫"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <a href="MainController?action=MyOrders" class="btn btn-secondary mt-3">← Back to Orders</a>
        </div>

        <%@ include file="footer.jsp" %>
    </body>
</html>
