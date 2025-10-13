<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Product Detail - CreationStore</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/css/detail.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <%@include file="header.jsp" %>

        <div class="container detail-wrapper">
            <div class="detail-image">
                <img src="${pageContext.request.contextPath}/assets/images/${requestScope.product.image}" alt="${requestScope.product.productName}">
            </div>

            <div class="detail-info">
                <div>
                    <h2>${requestScope.product.productName}</h2>
                    <p class="detail-price">
                        <fmt:formatNumber value="${requestScope.product.price}" type="currency" currencySymbol="₫"/>
                    </p>
                    <p class="detail-description">${requestScope.product.description}</p>
                    <p class="detail-description">
                        <c:choose>
                            <c:when test="${requestScope.product.validity == 0}">
                                <strong>Usage Duration:</strong> permanent
                            </c:when>
                            <c:otherwise>
                                <strong>Usage Duration:</strong>
                                ${requestScope.product.validity} days
                            </c:otherwise>
                        </c:choose>

                    </p>

                </div>

                <div class="btn-group-custom">
                    <form action="MainController" method="POST">
                        <input type="hidden" name="action" value="AddToCart"/>
                        <input type="hidden" name="strId" value="${requestScope.product.productId}"/>
                        <button type="submit" class="btn-add-cart">Add to Cart</button>
                    </form>

                    <a href="MainController?action=ShowAllActiveProducts" class="btn btn-outline-secondary">Back to Products</a>
                </div>
            </div>
        </div>

        <%@include file="footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
