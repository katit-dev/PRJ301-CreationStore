<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="assets/css/footer.css" />

<footer class="footer-custom mt-5">
    <div class="container py-4">
        <div class="row">
            <!-- About -->
            <div class="col-md-4 mb-3">
                <h5>About</h5>
                <p>CreationStore is your plug for everything – tech, creativity, and inspiration. <br/>
                    We bring value to every creation. <br/> Your expectations - Our creation</p>
            </div>

            <!-- Quick Links -->
            <div class="col-md-4 mb-3">
                <h5>Quick Links</h5>
                <ul class="list-unstyled">
                    <li><a href="MainController?action=ShowAllActiveProducts">Home</a></li>
                    <li><a href="MainController?action=ViewCart">Cart</a></li>
                    <li><a href="#" onclick="confirmLogout(event)">Logout</a></li>                   

                </ul>
            </div>

            <!-- Contact -->
            <div class="col-md-4 mb-3">
                <h5>Contact With Us</h5>
                <ul class="list-unstyled">
                    <li>Email: support@creationstore.com</li>
                    <li>Phone: +44 123 456 789</li>
                    <li>Address: 123 Green Lane, London, UK</li>
                    <li>
                        <a href="contactSupport.jsp" class="text-decoration-none text-primary">
                            💬 Chat with Customer Service
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="text-center pt-3 border-top mt-4">
            <small>&copy; 2025 CreationStore. All rights reserved.</small>
        </div>
    </div>
</footer>
<script>
    function confirmLogout(event) {
        event.preventDefault();
        if (confirm("Are you sure you want to log out?")) {
            window.location.href = "MainController?action=logout";
        }
    }
</script>