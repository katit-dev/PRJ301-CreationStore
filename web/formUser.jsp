<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${requestScope.isEdit ? "Edit User" : "Add User"} - CreationStore</title>

        <!-- Bootstrap + Custom CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/css/form.css" rel="stylesheet">
    </head>
    <body class="bg-form">
        <%@ include file="header.jsp" %>

        <div class="form-container">
            <h1>${requestScope.isEdit ? "Edit User" : "Add User"}</h1>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="${requestScope.isEdit ? "UpdateUser" : "addUser"}" />
                <input type="hidden" name="keyword" value="${requestScope.keyword}" />

                <c:if test="${requestScope.isEdit}">
                    <label for="id">User ID</label>
                    <input type="text" id="id" name="id" readonly
                           value="${not empty userEdit ? userEdit.userId : ""}" />
                </c:if>

                <label for="name">User Name</label>
                <input type="text" id="name" name="name"
                       value="${not empty userEdit ? userEdit.username : (not empty userAdd ? userAdd.username : "")}" required />
                <div class="error-message">${requestScope.nameError}${requestScope.usernameError}</div>

                <label for="password">Password</label>
                <input type="text" id="password" name="password"
                       value="${not empty userEdit ? userEdit.password : (not empty userAdd ? userAdd.password : "")}" required />


                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName"
                       value="${not empty userEdit ? userEdit.fullName : (not empty userAdd ? userAdd.fullName : "")}" required />


                <label for="email">Email</label>
                <input type="text" id="email" name="email"
                       value="${not empty userEdit ? userEdit.email : (not empty userAdd ? userAdd.email : "")}" required />

                <div class="error-message">${requestScope.emailError}</div>

                <c:if test="${requestScope.isEdit}">
                    <label for="role">Role</label>
                    <select name="role" id="role" required>
                        <option value="Admin" ${user.role eq 'Admin' ? 'selected' : ''}>Admin</option>
                        <option value="Member" ${user.role eq 'Member' ? 'selected' : ''}>Member</option>
                    </select>

                    <label for="createdAt">Created At</label>
                    <input type="text" id="createdAt" name="createdAt" readonly
                           value="<fmt:formatDate value='${user.createdAt}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
                </c:if>

                <input type="submit" value="${requestScope.isEdit ? "Save to database" : "Add User"}" />

                <c:if test="${not empty requestScope.checkError}">
                    <div class="error-message">${requestScope.checkError}</div>
                </c:if>

                <c:if test="${not empty requestScope.message}">
                    <div class="success-message">${requestScope.message}</div>
                </c:if>
            </form>

            <a href="MainController?action=searchUsername&keyword=${requestScope.keyword}" class="back-link">← Back to Users</a>
        </div>

        <%@ include file="footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
