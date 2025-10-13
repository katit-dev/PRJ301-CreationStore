<%-- 
    Document   : myOrders
    Created on : 24 Jul 2025, 13:24:31
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <title>My Orders</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="bg-light">
        <%@ include file="header.jsp" %>

        <div class="container mt-5">
            <h2>📦 My Orders</h2>

            <c:choose>
                <c:when test="${empty orders}">
                    <p>You haven't placed any orders yet.</p>
                </c:when>
                <c:otherwise>
                    <table class="table table-bordered mt-3">
                        <thead class="table-light">
                            <tr>
                                <th>Order ID</th>
                                <th>Date</th>
                                <th>Status</th>
                                <th>Total</th>
                                <th>Note</th>
                                <th>Actions</th> 
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${orders}">
                                <tr>
                                    <td>#${order.orderId}</td>
                                    <td><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td>${order.status}</td>
                                    <td><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫"/></td>
                                    <td>${order.note}</td>
                                    <td>
                                        <a class="btn btn-sm btn-primary"
                                           href="MainController?action=OrderDetails&orderId=${order.orderId}">
                                            View Details
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </c:otherwise>
            </c:choose>
        </div>

        <%@ include file="footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

