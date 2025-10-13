package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.CartDAO;
import model.CartDTO;
import model.UserDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.CartDetailDAO;
import model.CartDetailDTO;
import model.OrderDAO;
import model.OrderDetailDAO;
import model.ProductDAO;
import model.ProductDTO;

@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {

    CartDAO cartDAO = new CartDAO();
    CartDetailDAO cartDetailDAO = new CartDetailDAO();
    ProductDAO productDAO = new ProductDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "error.jsp";
        try {
            if ("AddToCart".equals(action)) {
                url = handleAddToCart(request, response);
            } else if ("ViewCart".equals(action)) {
                url = handleViewCart(request, response);
            } else if ("RemoveFromCart".equals(action)) {
                url = handleRemoveFromCart(request, response);
            } else if ("UpdateCartQuantity".equals(action)) {
                url = handleUpdateCartQuantity(request, response);
            } else if ("GoToCheckout".equals(action)) {
                url = handleGoToCheckout(request, response);
            } else if ("PlaceOrder".equals(action)) {
                url = handlePlaceOrder(request, response);
            } else if ("DisplayAllCarts".equals(action)) {
                url = handleDisplayAllCarts(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "CartController error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private String handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        int userId = ((UserDTO) request.getSession().getAttribute("user")).getUserId();
        int productId = Integer.parseInt(request.getParameter("strId"));
        String message = "";

        ProductDTO product = productDAO.getProductById(productId);
        int cartId = cartDAO.getOrCreatePendingCartId(userId);

        if (!cartDetailDAO.addProductToCart(cartId, productId, 1, product.getPrice())) {
            message = "Fail to add";
        } else {
            message = "Added to cart successfully!";
        }

        request.setAttribute("message", message);
        return handleViewCart(request, response);
    }

    private String handleViewCart(HttpServletRequest request, HttpServletResponse response) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if (user == null) {
            return "login.jsp"; // Nếu chưa đăng nhập, chuyển hướng đến trang login
        }

        try {
            int userId = user.getUserId();
            int cartId = cartDAO.getOrCreatePendingCartId(userId);
            List<CartDetailDTO> cartDetails = cartDetailDAO.getCartDetails(cartId);

            request.setAttribute("cartItems", cartDetails);
            request.setAttribute("totalPrice", calculateTotal(cartDetails));
        } catch (Exception e) {
            request.setAttribute("checkError", "Error loading cart: " + e.getMessage());
        }

        return "viewCart.jsp";
    }

    private double calculateTotal(List<CartDetailDTO> items) {
        double total = 0;
        for (CartDetailDTO item : items) {
            total += item.getPriceAtTime() * item.getQuantity();
        }
        return total;
    }

    private String handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            int productId = Integer.parseInt(request.getParameter("productId"));

            CartDetailDAO cartDetailDAO = new CartDetailDAO();
            boolean removed = cartDetailDAO.removeProductFromCart(cartId, productId);

            if (removed) {
                request.setAttribute("message", "Item removed successfully.");
            } else {
                request.setAttribute("checkError", "Failed to remove item.");
            }
        } catch (Exception e) {
            request.setAttribute("checkError", "Error: " + e.getMessage());
        }

        return handleViewCart(request, response);
    }

    private String handleUpdateCartQuantity(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException {

        int cartId = Integer.parseInt(request.getParameter("cartId"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        int delta = Integer.parseInt(request.getParameter("delta")); // +1 hoặc -1

        cartDetailDAO.updateQuantity(cartId, productId, delta);
        return handleViewCart(request, response);
    }

    private String handleGoToCheckout(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if (user == null) {
            return "login.jsp"; // Nếu chưa đăng nhập, chuyển hướng đến trang login
        }

        int userId = user.getUserId();
        int cartId = cartDAO.getOrCreatePendingCartId(userId);
        List<CartDetailDTO> cartItems = cartDetailDAO.getCartDetails(cartId);

        HttpSession session = request.getSession();
        request.setAttribute("cartItems", cartItems);
        session.setAttribute("cartItems", cartItems);

        return "checkout.jsp";
    }

    private String handlePlaceOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("user");

        // 1. Kiểm tra đăng nhập
        if (loginUser == null) {
            return "login.jsp";
        }

        // 2. Lấy giỏ hàng từ session
        List<CartDetailDTO> cartItems = (List<CartDetailDTO>) session.getAttribute("cartItems");
        if (cartItems == null || cartItems.isEmpty()) {
            request.setAttribute("errorMessage", "Your cart is empty.");
        }

        // 3. Tính tổng tiền
        double totalAmount = 0;
        for (CartDetailDTO item : cartItems) {
            totalAmount += item.getQuantity() * item.getPriceAtTime();
        }

        // 4. Ghi vào bảng Orders
        String note = request.getParameter("note"); // có thể null nếu không có form nhập
        OrderDAO orderDAO = new OrderDAO();
        int orderId = orderDAO.insertOrder(loginUser.getUserId(), totalAmount, note);

        if (orderId > 0) {
            // 5. Ghi vào bảng OrderDetail
            OrderDetailDAO detailDAO = new OrderDetailDAO();
            for (CartDetailDTO item : cartItems) {
                detailDAO.insertOrderDetail(orderId, item.getProductId(), item.getQuantity(), item.getPriceAtTime());
            }

            // 6. Xóa cart sau khi đặt hàng thành công
            CartDAO cartDAO = new CartDAO();
            cartDAO.clearCartByUserId(loginUser.getUserId());

            // 7. Xóa cartItems khỏi session
            session.removeAttribute("cartItems");

            // 8. Gửi dữ liệu tới trang orderSuccess.jsp
            request.setAttribute("orderId", orderId);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("note", note);
            return "orderSuccess.jsp";
        } else {
            request.setAttribute("errorMessage", "Failed to place order.");
            return "checkout.jsp";
        }
    }

    private String handleDisplayAllCarts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        List<CartDTO> carts = new CartDAO().getAllCarts();
        request.setAttribute("carts", carts);
        return "Table.jsp";
    }

}
