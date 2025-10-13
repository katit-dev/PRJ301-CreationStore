<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${requestScope.isEdit ? "Edit Category" : "Add Category"}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/css/form.css" rel="stylesheet">
    </head>
    <body class="bg-form">
        <%@ include file="header.jsp" %>
        <div class="form-container">
            <h1>${requestScope.isEdit ? "Edit Category" : "Add Category"}</h1>

            <form action="MainController">
                <input type="hidden" name="action" value="${requestScope.isEdit ? "UpdateCategory" : "addCategory"}" />

                <c:if test="${requestScope.isEdit}">
                    <label for="id">Category ID</label>
                    <input type="text" name="id" id="id" value="${category.categoryId}" readonly />
                </c:if>

                <label for="name">Category Name</label>
                <input type="text" name="name" id="name" value="${not empty category ? category.categoryName : (not empty categoryAdd ? categoryAdd.categoryName : "")}" required />
                <div class="error-message">${requestScope.nameError}</div>

                <label for="description">Description</label>
                <input type="text" name="description" id="description" value="${not empty category ? category.description : (not empty categoryAdd ? categoryAdd.description : "")}" required />

                <input type="submit" value="${requestScope.isEdit ? "Save to database" : "Add Category"}" />
            </form>

            <c:if test="${not empty requestScope.checkError}">
                <div class="error-message">${requestScope.checkError}</div>
            </c:if>
            <c:if test="${not empty requestScope.message}">
                <div class="success-message">${requestScope.message}</div>
            </c:if>

            <a href="MainController?action=viewAllCategories" class="back-link">← Back to Categories</a>
        </div>
             <%@ include file="footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
