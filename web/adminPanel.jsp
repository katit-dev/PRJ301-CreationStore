<%-- 
    Document   : adminPanel
    Created on : 15 Jul 2025, 02:32:00
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Panel - CreationStore</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/css/admin.css" rel="stylesheet">
    </head>
    <body class="bg-light-green">
        <%@ include file="header.jsp" %>

        <div class="container admin-wrapper">
            <h1 class="text-center mb-5">Admin Panel</h1>

            <div class="row row-cols-1 row-cols-md-2 g-4">
                <!-- User Management -->
                <div class="col">
                    <div class="admin-card">
                        <h4>User Management</h4>
                        <a href="MainController?action=viewAllUsers" class="btn btn-outline-primary">Display All Users</a>
                        <a href="MainController?action=showFormUser" class="btn btn-outline-success">Add User</a>
                    </div>
                </div>

                <!-- Category Management -->
                <div class="col">
                    <div class="admin-card">
                        <h4>Category Management</h4>
                        <a href="MainController?action=viewAllCategories" class="btn btn-outline-primary">Display All Categories</a>
                        <a href="MainController?action=showFormCat" class="btn btn-outline-success">Add Category</a>
                    </div>
                </div>

                <!-- Product Management -->
                <div class="col">
                    <div class="admin-card">
                        <h4>Product Management</h4>
                        <a href="MainController?action=viewAllProducts" class="btn btn-outline-primary">Display All Products</a>
                        <a href="MainController?action=showFormProduct" class="btn btn-outline-success">Add Product</a>
                    </div>
                </div>

                <!-- Cart Management -->
                <div class="col">
                    <div class="admin-card">
                        <h4>Cart Management</h4>
                        <a href="MainController?action=DisplayAllCarts" class="btn btn-outline-primary">Display All Carts</a>
                        <a href="#" class="btn btn-outline-success">Add Cart</a>
                    </div>
                </div>

                <!-- Order Management -->
                <div class="col">
                    <div class="admin-card">
                        <h4>Order Management</h4>
                        <a href="MainController?action=DisplayAllOrders" class="btn btn-outline-primary">Display All Orders</a>
                        <a href="#" class="btn btn-outline-success">Add Order</a>
                    </div>
                </div>
                
                <!-- User Management -->
                <div class="col">
                    <div class="admin-card">
                        <h4>User Management</h4>
                        <a href="MainController?action=viewAllUsers" class="btn btn-outline-primary">Display All Users</a>
                        <a href="addUser.jsp" class="btn btn-outline-success">Add User</a>
                    </div>
                </div>
            </div>
        </div>

        <%@ include file="footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

