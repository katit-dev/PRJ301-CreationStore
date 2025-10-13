<%-- 
    Document   : viewCart
    Created on : 23 Jul 2025, 11:36:40
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Your Cart</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/header.css">
        <link rel="stylesheet" href="assets/css/cart.css"> 
    </head>
    <body class="bg-light">
        <%@ include file="header.jsp" %>

        <div class="container mt-5">
            <h2 class="mb-4">🛒 Your Shopping Cart</h2>

            <c:if test="${empty cartItems}">
                <div class="alert alert-info">Your cart is currently empty.</div>
            </c:if>

            <c:if test="${not empty cartItems}">
                <table class="table table-bordered table-striped">
                    <thead class="table-light">
                        <tr>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Price (each)</th>
                            <th>Total</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="grandTotal" value="0" />
                        <c:forEach var="item" items="${cartItems}">
                            <tr>
                                <td>${item.productName}</td>
                                
                                <td class="text-center">
                                    <form action="MainController" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="UpdateCartQuantity" />
                                        <input type="hidden" name="productId" value="${item.productId}" />
                                        <input type="hidden" name="cartId" value="${item.cartId}" />
                                        <input type="hidden" name="delta" value="-1" />
                                        <button class="btn btn-sm btn-outline-secondary" ${item.quantity <= 1 ? "disabled" : ""}>-</button>
                                    </form>
                                    <span class="mx-2">${item.quantity}</span>
                                    <form action="MainController" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="UpdateCartQuantity" />
                                        <input type="hidden" name="productId" value="${item.productId}" />
                                        <input type="hidden" name="cartId" value="${item.cartId}" />
                                        <input type="hidden" name="delta" value="1" />
                                        <button class="btn btn-sm btn-outline-secondary">+</button>
                                    </form>
                                </td>

                                <td><fmt:formatNumber value="${item.priceAtTime}" type="currency" currencySymbol="₫"/></td>
                                <td>
                                    <fmt:formatNumber value="${item.quantity * item.priceAtTime}" type="currency" currencySymbol="₫"/>
                                    <c:set var="grandTotal" value="${grandTotal + (item.quantity * item.priceAtTime)}" />
                                </td>
                                <td>
                                    <form action="MainController" class="d-inline">
                                        <input type="hidden" name="action" value="RemoveFromCart" />
                                        <input type="hidden" name="productId" value="${item.productId}" />
                                        <input type="hidden" name="cartId" value="${item.cartId}" />
                                        <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="3" class="text-end fw-bold">Grand Total:</td>
                            <td colspan="2">
                                <fmt:formatNumber value="${grandTotal}" type="currency" currencySymbol="₫" />
                            </td>
                        </tr>
                    </tbody>
                </table>

                <div class="text-end">
                    <a href="MainController?action=GoToCheckout" class="btn btn-success">Proceed to Checkout</a>
                </div>
            </c:if>
        </div>

        <%@ include file="footer.jsp" %>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
