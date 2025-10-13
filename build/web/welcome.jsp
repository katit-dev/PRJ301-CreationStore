<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Welcome - CreationStore</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/welcome.css">
    </head>
    <body class="bg-light-green">

        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <c:redirect url="login.jsp"/>
            </c:when>
            <c:otherwise>

                <%@include file="header.jsp" %>

                <div class="container my-5">
                    <!-- Search & Filter UI -->
                    <div class="p-4 bg-white rounded-4 shadow-sm mb-5" style="max-width: 1000px; margin: auto;">
                        <h4 class="text-success mb-4">Search and Filter Products</h4>

                        <!-- Search -->
                        <form action="MainController" method="POST" class="mb-4">
                            <input type="hidden" name="action" value="searchProduct" />
                            <div class="input-group">
                                <input type="text" name="strKeyword" class="form-control" placeholder="Search by name"
                                       value="${not empty requestScope.keyword ? requestScope.keyword : ""}" />
                                <button type="submit" class="btn btn-success">Search</button>
                            </div>
                        </form>

                        <!-- Filter -->
                        <form action="MainController" method="POST">
                            <input type="hidden" name="action" value="FilterProduct" />
                            <div class="row g-3 align-items-end">
                                <div class="col-md-4">
                                    <label class="form-label">Category</label>
                                    <select name="categoryId" class="form-select">
                                        <option value="0" <c:if test="${selectedCategoryId == 0}">selected</c:if>>All</option>
                                        <c:forEach var="cat" items="${requestScope.listCategory}">
                                            <option value="${cat.categoryId}" <c:if test="${selectedCategoryId == cat.categoryId}">selected</c:if>>
                                                ${cat.categoryName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col-md-3">
                                    <label class="form-label">Price From</label>
                                    <input type="number" name="priceFrom" class="form-control" placeholder="Min" min="0" step="10000"
                                           value="${not empty requestScope.priceFrom ? requestScope.priceFrom : ""}" >
                                </div>

                                <div class="col-md-3">
                                    <label class="form-label">Price To</label>
                                    <input type="number" name="priceTo" class="form-control" placeholder="Max" min="0" step="10000"
                                           value="${not empty requestScope.priceTo ? requestScope.priceTo : ""}">
                                </div>

                                <div class="col-md-2 d-flex gap-2">
                                    <button type="submit" class="btn btn-primary w-100">Filter</button>
                                    <a href="MainController?action=ShowAllActiveProducts" class="btn btn-outline-secondary w-100">Reset</a>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- Product Cards -->
                    <div class="product-container">
                        <c:if test="${not empty requestScope.listFilterProducts}">
                            <c:forEach var="product" items="${requestScope.listFilterProducts}">
                                <div class="product-card">
                                    <img src="${pageContext.request.contextPath}/assets/images/${product.image}" alt="${product.productName}" class="product-image"/>
                                    <div class="product-info">
                                        <h3 class="product-name">${product.productName}</h3>
                                        <p class="product-price">Price: <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫"/></p>
                                        <div class="product-actions d-flex justify-content-center gap-2 mt-2">
                                            <form action="MainController" >
                                                <input type="hidden" name="action" value="ViewDetail" />
                                                <input type="hidden" name="strId" value="${product.productId}" />
                                                <button type="submit" class="btn btn-outline-primary btn-sm">View Detail</button>
                                                <span>${not empty requestScope.message ? message : ""}</span>
                                            </form>

                                            <form action="MainController" >
                                                <input type="hidden" name="action" value="AddToCart" />
                                                <input type="hidden" name="strId" value="${product.productId}" />
                                                <button type="submit" class="btn btn-warning btn-sm">Add to Cart</button>
                                            </form>
                                        </div>

                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>

                        <c:if test="${not empty requestScope.listSearch}">
                            <c:forEach var="product" items="${requestScope.listSearch}">
                                <div class="product-card">
                                    <img src="${pageContext.request.contextPath}/assets/images/${product.image}" alt="${product.productName}" class="product-image"/>
                                    <div class="product-info">
                                        <h3 class="product-name">${product.productName}</h3>
                                        <p class="product-price">Price: <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫"/></p>
                                        <div class="product-actions d-flex justify-content-center gap-2 mt-2">
                                            <form action="MainController" >
                                                <input type="hidden" name="action" value="ViewDetail" />
                                                <input type="hidden" name="strId" value="${product.productId}" />
                                                <button type="submit" class="btn btn-outline-primary btn-sm">View Detail</button>
                                                <span>${not empty requestScope.message ? message : ""}</span>
                                            </form>

                                            <form action="MainController" >
                                                <input type="hidden" name="action" value="AddToCart" />
                                                <input type="hidden" name="strId" value="${product.productId}" />
                                                <button type="submit" class="btn btn-warning btn-sm">Add to Cart</button>
                                            </form>
                                        </div>

                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>

                        <c:if test="${empty requestScope.listFilterProducts && empty requestScope.listSearch}">
                            <c:forEach var="product" items="${requestScope.productList}">
                                <div class="product-card">
                                    <img src="${pageContext.request.contextPath}/assets/images/${product.image}" alt="${product.productName}" class="product-image"/>
                                    <div class="product-info">
                                        <h3 class="product-name">${product.productName}</h3>
                                        <p class="product-price">Price: <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫"/></p>
                                        <div class="product-actions d-flex justify-content-center gap-2 mt-2">
                                            <form action="MainController" >
                                                <input type="hidden" name="action" value="ViewDetail" />
                                                <input type="hidden" name="strId" value="${product.productId}" />
                                                <button type="submit" class="btn btn-outline-primary btn-sm">View Detail</button>
                                                <span>${not empty requestScope.message ? message : ""}</span>
                                            </form>

                                            <form action="MainController" >
                                                <input type="hidden" name="action" value="AddToCart" />
                                                <input type="hidden" name="strId" value="${product.productId}" />
                                                <button type="submit" class="btn btn-warning btn-sm">Add to Cart</button>
                                            </form>
                                        </div>

                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>

                <%@include file="footer.jsp" %>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

            </c:otherwise>
        </c:choose>

    </body>
</html>
