<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/zalo-button.css">


<header class="header-custom">
    <div class="container-fluid d-flex justify-content-between align-items-center py-2 px-4">
        <div class="logo-section">
            <h2 class="m-0 text-white">CreationStore</h2>
            <span class="slogan">The plug for everything</span>
        </div>

        <form class="d-flex search-form" action="MainController" method="GET">
            <input type="hidden" name="action" value="searchProduct" />
            <input 
                class="form-control me-2" 
                type="search" 
                name="strKeyword" 
                placeholder="Search..." 
                aria-label="Search"
                value="${not empty requestScope.keyword ? requestScope.keyword : ''}" 
                />
            <button class="btn btn-light" type="submit">Search</button>
        </form>


        <nav class="nav-links d-flex align-items-center">
            <a class="nav-item nav-link " href="MainController?action=ShowAllActiveProducts">Home</a>
            <a class="nav-item nav-link " href="MainController?action=ViewCart">Cart</a>

            <c:if test="${not empty sessionScope.user}">
                <div class="dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
                        ${sessionScope.user.fullName}
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="register.jsp">My Account</a></li>
                        <li><a class="dropdown-item" href="MainController?action=MyOrders">My Orders</a></li>
                        <li><a class="dropdown-item" href="#" onclick="confirmLogout(event)">Logout</a></li>                    </ul>
                </div>
            </c:if>

            <c:if test="${sessionScope.user != null and sessionScope.user.role == 'Admin'}">
                <a class="nav-item nav-link text-warning" href="adminPanel.jsp">Admin Panel</a>
            </c:if>    
        </nav>
    </div>
</header>
<script>
    function confirmLogout(event) {
        event.preventDefault(); 
        if (confirm("Are you sure you want to log out?")) {
            window.location.href = "MainController?action=logout";
        }
    }
</script>
