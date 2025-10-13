<%-- 
    Document   : contactSupport
    Created on : 25 Jul 2025, 01:00:12
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Support - CreationStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/contactSupport.css" rel="stylesheet">
</head>
<body class="bg-light">
    <%@ include file="header.jsp" %>

    <div class="container my-5">
        <div class="contact-box">
            <h2>Contact Customer Service</h2>
            <p class="contact-info"><i class="bi bi-telephone-fill"></i> Hotline: 1900 633 305</p>
            <p class="contact-info"><i class="bi bi-clock-fill"></i> Working hours: 08:30 AM – 10:00 PM (daily)</p>
            <p class="contact-info"><i class="bi bi-geo-alt-fill"></i> Address: 123 Green Lane, London, UK</p>

            <img src="assets/images/Zalo_QR.jpg" alt="Zalo QR Code" class="qr-img" />
            <p class="text-center text-muted">Scan the QR code to chat via Zalo on your phone</p>
        </div>

        <div class="text-center mt-4">
            <a href="MainController?action=ShowAllActiveProducts" class="btn btn-outline-primary">← Back to Homepage</a>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

