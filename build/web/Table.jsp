<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Management Table - CreationStore</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/css/admin.css" rel="stylesheet">
    </head>
    <body class="bg-light-green">
        <%@ include file="header.jsp" %>
        <div class="container py-4">
            <c:choose>

                <%-- Product List --%>
                <c:when test="${not empty requestScope.allProductList}">
                    <h2 class="mb-4 text-primary">All Products</h2>

                    <form class="d-flex mb-3" action="MainController" method="POST">
                        <input type="hidden" name="action" value="searchProductByAdmin" />
                        <input type="text" name="keyword" class="form-control me-2" placeholder="Search by name" value="${requestScope.keyword}" />
                        <button type="submit" class="btn btn-outline-primary">Search</button>
                    </form>

                    <c:set var="productList" value="${not empty requestScope.listSearchAD ? requestScope.listSearchAD : requestScope.allProductList}" />
                    <div class="table-responsive">
                        <table class="table table-bordered align-middle table-hover bg-white">
                            <thead class="table-success">
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Description</th>
                                    <th>Price</th>
                                    <th>Status</th>
                                    <th>Image</th>
                                    <th>Validity</th>
                                    <th>Category ID</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="product" items="${productList}">
                                    <tr>
                                        <td>${product.productId}</td>
                                        <td>${product.productName}</td>
                                        <td>${product.description}</td>
                                        <td>${product.price}</td>
                                        <td>${product.status ? 'Active' : 'Inactive'}</td>
                                        <td><img src="${pageContext.request.contextPath}/assets/images/${product.image}" width="80"/></td>
                                        <td>${product.validity}</td>
                                        <td>${product.categoryId}</td>
                                        <td>
                                            <form action="MainController" method="GET" class="mb-1">
                                                <input type="hidden" name="action" value="editProduct" />
                                                <input type="hidden" name="strProductId" value="${product.productId}" />
                                                <input type="hidden" name="keyword" value="${requestScope.keyword}" />
                                                <button class="btn btn-sm btn-warning w-100">Edit</button>
                                            </form>
                                            <form action="MainController" method="GET">
                                                <input type="hidden" name="action" value="deleteProduct" />
                                                <input type="hidden" name="strProductId" value="${product.productId}" />
                                                <input type="hidden" name="keyword" value="${requestScope.keyword}" />
                                                <button class="btn btn-sm btn-danger w-100">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>

                <%-- User List --%>
                <c:when test="${not empty requestScope.allUserList}">
                    <h2 class="mb-4 text-primary">All Users</h2>

                    <form class="d-flex mb-3" action="MainController" method="POST">
                        <input type="hidden" name="action" value="searchUsername" />
                        <input type="text" name="keyword" class="form-control me-2" placeholder="Search by username" value="${requestScope.keyword}" />
                        <button type="submit" class="btn btn-outline-primary">Search</button>
                    </form>

                    <c:set var="userList" value="${not empty requestScope.userList ? requestScope.userList : requestScope.allUserList}" />
                    <div class="table-responsive">
                        <table class="table table-bordered align-middle table-hover bg-white">
                            <thead class="table-info">
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Full Name</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>Created At</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${userList}">
                                    <tr>
                                        <td>${user.userId}</td>
                                        <td>${user.username}</td>
                                        <td>${user.fullName}</td>
                                        <td>${user.email}</td>
                                        <td>${user.role}</td>
                                        <td>${user.createdAt}</td>
                                        <td>
                                            <form action="MainController" method="GET">
                                                <input type="hidden" name="action" value="editUser" />
                                                <input type="hidden" name="strUserId" value="${user.userId}" />
                                                <input type="hidden" name="keyword" value="${requestScope.keyword}" />
                                                <button class="btn btn-sm btn-warning">Edit</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>

                <%-- Category List --%>
                <c:when test="${not empty requestScope.allCategorieslist}">
                    <h2 class="mb-4 text-primary">All Categories</h2>
                    <div class="table-responsive">
                        <table class="table table-bordered align-middle table-hover bg-white">
                            <thead class="table-warning">
                                <tr>
                                    <th>ID</th>
                                    <th>Category Name</th>
                                    <th>Description</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="category" items="${requestScope.allCategorieslist}">
                                    <tr>
                                        <td>${category.categoryId}</td>
                                        <td>${category.categoryName}</td>
                                        <td>${category.description}</td>
                                        <td>
                                            <form action="MainController" method="GET">
                                                <input type="hidden" name="action" value="editAction" />
                                                <input type="hidden" name="strCategoryId" value="${category.categoryId}" />
                                                <button class="btn btn-sm btn-warning">Edit</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>

                <%-- Cart List --%>
                <c:when test="${not empty requestScope.carts}">
                    <h2 class="mb-4 text-primary">All Carts</h2>
                    <div class="table-responsive">
                        <table class="table table-bordered align-middle table-hover bg-white">
                            <thead class="table-success">
                                <tr>
                                    <th>Cart ID</th>
                                    <th>User ID</th>
                                    <th>Status</th>
                                    <th>Created At</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cart" items="${requestScope.carts}">
                                    <tr>
                                        <td>${cart.cartId}</td>
                                        <td>${cart.userId}</td>
                                        <td>${cart.status}</td>
                                        <td>${cart.createdAt}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>


                <%-- Order List --%>
                <c:when test="${not empty requestScope.orders}">
                    <h2 class="mb-4 text-primary">All Orders</h2>
                    <div class="table-responsive">
                        <table class="table table-bordered align-middle table-hover bg-white">
                            <thead class="table-warning">
                                <tr>
                                    <th>Order ID</th>
                                    <th>User ID</th>
                                    <th>Date</th>
                                    <th>Total</th>
                                    <th>Status</th>
                                    <th>Note</th>
                                    <th>Update</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="order" items="${requestScope.orders}">
                                    <tr>
                                        <td>${order.orderId}</td>
                                        <td>${order.userId}</td>
                                        <td>${order.orderDate}</td>
                                        <td>${order.totalAmount}</td>
                                        <td>${order.status}</td>
                                        <td>${order.note}</td>
                                        <td>
                                            <c:if test="${order.status eq 'Processing'}">
                                                <form action="MainController" >
                                                    <input type="hidden" name="action" value="updateOrderStatus" />
                                                    <input type="hidden" name="orderId" value="${order.orderId}" />
                                                    <input type="hidden" name="newStatus" value="processed" />
                                                    <button type="submit" class="btn btn-sm btn-success">Mark as Ordered</button>

                                                </form>
                                            </c:if>
                                            <c:if test="${order.status eq 'Ordered'}">
                                                <span class="badge bg-success">✔ Already Ordered</span>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>

            </c:choose>

            <div class="mt-4">
                <a href="adminPanel.jsp" class="btn btn-outline-secondary">← Back to Admin Panel</a>
            </div>
        </div>
        <%@ include file="footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
