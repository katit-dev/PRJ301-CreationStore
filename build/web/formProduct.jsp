<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${requestScope.isEdit ? "Edit Product" : "Add Product"}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/form.css" rel="stylesheet">
</head>
<body class="bg-form">
    <%@ include file="header.jsp" %>

    <div class="form-container">
        <h1>${requestScope.isEdit ? "Edit Product" : "Add Product"}</h1>

        <form action="MainController">
            <input type="hidden" name="action" value="${requestScope.isEdit ? 'UpdateProduct' : 'addProduct'}" />
            <input type="hidden" name="keyword" value="${requestScope.keyword}" />

            <c:if test="${requestScope.isEdit}">
                <label for="id">Product ID</label>
                <input type="text" name="id" id="id"
                       value="${productEdit.productId}" readonly />
            </c:if>

            <label for="name">Product Name</label>
            <input type="text" name="name" id="name"
                   value="${not empty productEdit ? productEdit.productName : (not empty productAdd ? productAdd.productName : '')}" required />
            <div class="error-message">${requestScope.nameError}</div>

            <label for="description">Description</label>
            <input type="text" name="description" id="description"
                   value="${not empty productEdit ? productEdit.description : (not empty productAdd ? productAdd.description : '')}" required />

            <label for="price">Price</label>
            <input type="number" name="price" id="price"
                   value="${not empty productEdit ? productEdit.price : (not empty productAdd ? productAdd.price : '')}"
                   min="10000" step="10000" required />

            <c:if test="${requestScope.isEdit}">
                <label for="status">Status</label>
                <select name="status" id="status" required>
                    <option value="true" ${productEdit.status ? "selected" : ""}>Active</option>
                    <option value="false" ${!productEdit.status ? "selected" : ""}>Inactive</option>
                </select>
            </c:if>

            <label for="validity">Validity (days)</label>
            <input type="number" name="validity" id="validity"
                   value="${not empty productEdit ? productEdit.validity : (not empty productAdd ? productAdd.validity : '')}"
                   min="0" step="1" required />

            <label for="categoryId">Category</label>
            <select name="categoryId" id="categoryId" required>
                <c:forEach var="cat" items="${requestScope.listCategory}">
                    <c:set var="selectedCatId" value="${not empty productEdit ? productEdit.categoryId : (not empty productAdd ? productAdd.categoryId : '')}" />
                    <option value="${cat.categoryId}" ${cat.categoryId == selectedCatId ? 'selected' : ''}>
                        ${cat.categoryName}
                    </option>
                </c:forEach>
            </select>

            <input type="submit" value="${requestScope.isEdit ? 'Save to database' : 'Add Product'}" />
        </form>

        <c:if test="${not empty requestScope.checkError}">
            <div class="error-message">${requestScope.checkError}</div>
        </c:if>
        <c:if test="${not empty requestScope.message}">
            <div class="success-message">${requestScope.message}</div>
        </c:if>

        <a href="MainController?action=searchProductByAdmin&keyword=${requestScope.keyword}" class="back-link">
            ← Back to Products
        </a>
    </div>

    <%@ include file="footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
