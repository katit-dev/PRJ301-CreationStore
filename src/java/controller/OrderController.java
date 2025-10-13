package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.OrderDAO;
import model.OrderDTO;
import model.OrderDetailDAO;
import model.OrderDetailDTO;
import model.UserDTO;

@WebServlet(name = "OrderController", urlPatterns = {"/OrderController"})
public class OrderController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "error.jsp";
        try {
            if ("MyOrders".equals(action)) {
                url = handleMyOrders(request, response);
            } else if ("OrderDetails".equals(action)) {
                url = handleOrderDetails(request, response);
            } else if ("DisplayAllOrders".equals(action)) {
                url = handleDisplayAllOrders(request, response);
            } else if ("updateOrderStatus".equals(action)) {
                url = handleUpdateOrderStatus(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "OrderController error: " + e.getMessage());
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

    private String handleMyOrders(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException {

        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "login.jsp";
        }

        OrderDAO orderDAO = new OrderDAO();
        List<OrderDTO> orders = orderDAO.getOrdersByUserId(user.getUserId());

        request.setAttribute("orders", orders);
        return "myOrders.jsp";
    }

    private String handleOrderDetails(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        OrderDetailDAO detailDAO = new OrderDetailDAO();
        List<OrderDetailDTO> details = detailDAO.getDetailsByOrderId(orderId);

        request.setAttribute("orderDetails", details);
        request.setAttribute("orderId", orderId);
        return "orderDetails.jsp";
    }

    private String handleDisplayAllOrders(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        List<OrderDTO> orders = new OrderDAO().getAllOrders();
        request.setAttribute("orders", orders);
        return "Table.jsp";
    }

    private String handleUpdateOrderStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String newStatus = request.getParameter("newStatus");

        OrderDAO dao = new OrderDAO();
        boolean updated = dao.updateOrderStatus(orderId, newStatus);

        if (updated) {
            request.setAttribute("message", "Order status updated successfully.");
        } else {
            request.setAttribute("message", "Failed to update order status.");
        }

        // Load lại danh sách
       List<OrderDTO> orders = new OrderDAO().getAllOrders();
        request.setAttribute("orders", orders);
        return "Table.jsp";
    }

}
