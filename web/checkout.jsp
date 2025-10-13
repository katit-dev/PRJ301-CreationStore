<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Checkout</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/header.css">
        <link rel="stylesheet" href="assets/css/checkout.css">
    </head>
    <body class="bg-light">
        <%@ include file="header.jsp" %>

        <div class="container mt-5 mb-5">
            <h2 class="mb-4">🧾 Checkout</h2>

            <table class="table table-bordered table-striped">
                <thead class="table-light">
                    <tr>
                        <th>Product</th>
                        <th>Quantity</th>
                        <th>Price (each)</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="grandTotal" value="0" />
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>${item.productName}</td>
                            <td>${item.quantity}</td>
                            <td><fmt:formatNumber value="${item.priceAtTime}" type="currency" currencySymbol="₫"/></td>
                            <td>
                                <fmt:formatNumber value="${item.quantity * item.priceAtTime}" type="currency" currencySymbol="₫"/>
                                <c:set var="grandTotal" value="${grandTotal + (item.quantity * item.priceAtTime)}" />
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="3" class="text-end fw-bold">Grand Total:</td>
                        <td><fmt:formatNumber value="${grandTotal}" type="currency" currencySymbol="₫" /></td>
                    </tr>
                </tbody>
            </table>

            <form method="post" action="MainController">
                <input type="hidden" name="action" value="PlaceOrder" />

                <!--  -->
                <div class="mb-3">
                    <label for="note" class="form-label">Order Note (optional)</label>
                    <textarea name="note" class="form-control" rows="3" placeholder="Any special requests or notes..."></textarea>
                </div>

                <!-- Mã QR -->
                <div class="qr-box text-center mb-4">
                    <p>📱 Scan QR to Pay:</p>
                    <img src="assets/images/myQR.jpg" alt="QR Code" style="max-width: 200px;">
                </div>

                <div class="text-end">
                    <button type="submit" class="btn btn-success">Confirm Order</button>
                </div>
            </form>

            <!--  -->
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success success-message mt-4">
                    ✅ ${successMessage}
                </div>
            </c:if>
        </div>

        <%@ include file="footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
