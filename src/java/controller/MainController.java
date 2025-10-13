package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";

    private boolean isUserAction(String action) {
        return "login".equals(action)
                || "logout".equals(action)
                || "register".equals(action)
                || "updateProfile".equals(action)
                || "verifyPassword".equals(action)
                || "changePassword".equals(action)
                || "viewAllUsers".equals(action) // admin action
                || "searchUsername".equals(action) // admin action
                || "editUser".equals(action) // admin action
                || "UpdateUser".equals(action) // admin action
                || "showFormUser".equals(action) // admin action
                || "addUser".equals(action) //admin action
                
                || "deleteUser".equals(action);
    }

    private boolean isProductAction(String action) {
        return "ShowAllActiveProducts".equals(action)
                || "searchProduct".equals(action)
                || "FilterProduct".equals(action)
                || "ViewDetail".equals(action)
                || "viewAllProducts".equals(action) // admin action
                || "searchProductByAdmin".equals(action) // admin action
                || "editProduct".equals(action)// admin action
                || "UpdateProduct".equals(action) // admin action
                || "deleteProduct".equals(action) // admin action
                ||"showFormProduct".equals(action) // admin action                
                || "addProduct".equals(action); // admin action
    }

    private boolean isCategoryAction(String action) {
        return "viewAllCategories".equals(action) // admin action
                || "editAction".equals(action) //admin action
                || "UpdateCategory".equals(action) //admin action                
                || "addCategory".equals(action) //admin action                      
                || "showFormCat".equals(action); //admin action
    }

    private boolean isCartAction(String action) {
        return "AddToCart".equals(action)                
                || "ViewCart".equals(action)                
                || "RemoveFromCart".equals(action)                
                || "UpdateCartQuantity".equals(action)
                || "GoToCheckout".equals(action)
                || "PlaceOrder".equals(action)
                || "DisplayAllCarts".equals(action);// admin action
    }

    private boolean isOrderAction(String action) {
        return "MyOrders".equals(action)
                || "OrderDetails".equals(action)
                || "DisplayAllOrders".equals(action) //admin action
                || "updateOrderStatus".equals(action);// admin action
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = LOGIN_PAGE;

        try {
            String action = request.getParameter("action");
            System.out.println("Action received: " + action); // Debug log

            if (action != null) {
                if (isUserAction(action)) {
                    url = "UserController";
                } else if (isProductAction(action)) {
                    url = "ProductController";
                } else if (isCategoryAction(action)) {
                    url = "CategoryController";
                } else if (isCartAction(action)) {
                    url = "CartController";
                } else if (isOrderAction(action)) {
                    url = "OrderController";
                } else {
                    url = "error.jsp";
                    request.setAttribute("errorMessage", "Invalid action: " + action);
                }
            } else {
                url = "welcome.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "MainController error: " + e.getMessage());
            url = "error.jsp";
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Main controller to dispatch requests to appropriate controllers";
    }

}
