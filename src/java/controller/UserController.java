package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.UserDAO;
import model.UserDTO;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import model.CategoryDAO;
import model.CategoryDTO;
import model.ProductDAO;
import model.ProductDTO;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String WELCOME_PAGE = "welcome.jsp";

    UserDAO userDAO = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = LOGIN_PAGE;
        try {
            if ("login".equals(action)) {
                url = handleLogin(request, response);
            } else if ("logout".equals(action)) {
                url = handleLogout(request, response);
            } else if ("register".equals(action)) {
                url = handleRegister(request, response);
            } else if ("updateProfile".equals(action)) {
                url = handleupdateProfile(request, response);
            } else if ("verifyPassword".equals(action)) {
                url = handleVerifyPassword(request, response);
            } else if ("changePassword".equals(action)) {
                url = handleChangePassword(request, response);
            } else if ("viewAllUsers".equals(action)) { // admin action
                url = handleviewAllUsers(request, response);
            } else if ("searchUsername".equals(action)) { // admin action
                url = handleSearchUsername(request, response);
            } else if ("editUser".equals(action)) { // admin action
                url = handleEditUser(request, response);
            } else if ("UpdateUser".equals(action)) { // admin action
                url = handleUpdateUser(request, response);
            } else if ("showFormUser".equals(action)) { // admin action
                url = handleShowFormUser(request, response);
            } else if ("addUser".equals(action)) { // admin action
                url = handleAddUser(request, response);
            } else {
                request.setAttribute("message", "Invalid action: " + action);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "UserController error: " + e.getMessage());
            url = "error.jsp";
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

    private String handleLogin(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN_PAGE;
        HttpSession session = request.getSession();

        String username = request.getParameter("strUsername");
        String password = request.getParameter("strPassword");

        System.out.println("HANDLE LOGIN: username = " + username + ", password = " + password);

        if (userDAO.login(username, password)) {
            UserDTO user = userDAO.getUserByUserName(username);
            System.out.println("Login success. Set user to session: " + user.getUsername());
            session.setAttribute("user", user);
            // hanh dong gui list cac product di kem
            ProductDAO productDAO = new ProductDAO();
            List<ProductDTO> productList = productDAO.getAllActiveProducts();
            request.setAttribute("productList", productList);
            // hanh dong gui listCategory di kem
            CategoryDAO categoryDAO = new CategoryDAO();
            List<CategoryDTO> listCategory = categoryDAO.getAllCategories();
            request.setAttribute("listCategory", listCategory);

            url = WELCOME_PAGE;
        } else {
            System.out.println("Login failed.");
            request.setAttribute("message", "Username or Password incorrect!");
        }
        return url;
    }

    private String handleLogout(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN_PAGE;
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                Object object = session.getAttribute("user");
                UserDTO user = object != null ? (UserDTO) object : null;
                if (user != null) {
                    session.invalidate();
                }
            }
        } catch (Exception e) {
        }
        return url;
    }

    private String handleRegister(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        String usernameError = "";
        String emailError = "";

        // lay du lieu tu form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);

        //-- tryCatch --
        // xu ly loi
        if (userDAO.isUserExistingByUserName(username)) {
            request.setAttribute("usernameError", "This user name already exists!");
        }
        if (userDAO.isUserExistingByEmail(email)) {
            request.setAttribute("emailError", "This email is already registered");
        }

        // create userDTO
        UserDTO user = new UserDTO(username, password, fullName, email);
        if (!userDAO.register(user)) {
            checkError += "<br/> Can not register!";
        }
        // gui user len request
        request.setAttribute("user", user);

        // message handling
        if (checkError.isEmpty()) {
            message = "Register Successfully!";
        }

        // gui message va checkError di
        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        return "register.jsp";
    }

    private String handleupdateProfile(HttpServletRequest request, HttpServletResponse response) {
        String userIdstr = (request.getParameter("userIdstr"));

        if (userIdstr == null || userIdstr.trim().isEmpty()) {
            request.setAttribute("checkError", "User ID is missing!");
            return "register.jsp";
        }
        int userId = Integer.parseInt(userIdstr);
        if (userId == 0) {
            request.setAttribute("checkError", "User ID is 0!");
            return "register.jsp";
        }
        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");

        if (userDAO.updateProfile(userId, username, fullName, email)) {
            //  cap nhat lai user len session sau khi update
            UserDTO updatedUser = userDAO.getUserByUserId(userId);
            request.getSession().setAttribute("user", updatedUser);

            request.setAttribute("message", "Update profile successfully!");
        } else {
            request.setAttribute("checkError", "Failed to update profile!.");
        }
        return "register.jsp";
    }

    private String handleVerifyPassword(HttpServletRequest request, HttpServletResponse response) {
        String passwordVerify = request.getParameter("passwordVerify");
        request.setAttribute("passwordVerify", passwordVerify);

        HttpSession session = request.getSession();
        UserDTO sessionUser = (UserDTO) session.getAttribute("user");
        // kt session
        if (sessionUser == null) {
            request.setAttribute("checkError", "Session expired. Please login again.");
            return "login.jsp";
        }

        // verify password
        UserDTO userFromDb = userDAO.getUserByUserId(sessionUser.getUserId());

        if (userFromDb != null && userFromDb.getPassword().equals(passwordVerify)) {
            return "newPassword.jsp";
        } else {
            request.setAttribute("checkError", "Incorrect password. Please try again.");
            return "verifyAcount.jsp";
        }

    }

    private String handleChangePassword(HttpServletRequest request, HttpServletResponse response) {
        String newPassword = request.getParameter("newPassword");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String userIdstr = (request.getParameter("userIdstr"));

        request.setAttribute("newPassword", newPassword);
        request.setAttribute("passwordConfirm", passwordConfirm);

        // xu li id
        if (userIdstr == null || userIdstr.trim().isEmpty()) {
            request.setAttribute("checkError", "User ID is missing!");
            return "newPassword.jsp";
        }
        int userId = Integer.parseInt(userIdstr);

        // kiem tra password
        UserDTO user = userDAO.getUserByUserId(userId);
        if (user.getPassword().equals(newPassword)) {
            request.setAttribute("checkError", "The new Password can not be the same the current Password!");
            return "newPassword.jsp";
        }

        if (!passwordConfirm.equals(newPassword)) {
            request.setAttribute("checkError", "Password and Confirm Password are not the same!");
            return "newPassword.jsp";
        }

        // cap nhat mk moi
        boolean success = userDAO.updatePassword(userId, newPassword);
        if (success) {
            request.setAttribute("message", "Password updated successfully!");
        } else {
            request.setAttribute("checkError", "Failed to update password. Please try again.");
        }

        return "newPassword.jsp";
    }

    private String handleviewAllUsers(HttpServletRequest request, HttpServletResponse response) {
        List<UserDTO> allUserList = userDAO.getAllUser();
        request.setAttribute("allUserList", allUserList);
        return "Table.jsp";
    }

    private String handleSearchUsername(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");
        List<UserDTO> userList = userDAO.listUserByName(keyword);
        request.setAttribute("keyword", keyword);
        request.setAttribute("userList", userList);

        List<UserDTO> allUserList = userDAO.getAllUser();
        request.setAttribute("allUserList", allUserList);
        return "Table.jsp";
    }

    private String handleEditUser(HttpServletRequest request, HttpServletResponse response) {
        String strUserId = request.getParameter("strUserId");
        String keyword = request.getParameter("keyword");
        int userID = Integer.parseInt(strUserId);

        UserDTO userEdit = userDAO.getUserByUserId(userID);
        if (userEdit != null) {
            request.setAttribute("userEdit", userEdit);
            request.setAttribute("keyword", keyword);
            request.setAttribute("isEdit", true);
            return "formUser.jsp";
        } else {
            request.setAttribute("checkError", "User not found!");
        }

        return handleSearchUsername(request, response);
    }

    private String handleUpdateUser(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        String keyword = request.getParameter("keyword");

        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("name");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String role = request.getParameter("role");
            String createdAtStr = request.getParameter("createdAt");

            Timestamp createdAt = Timestamp.valueOf(createdAtStr); // yyyy-MM-dd

            UserDTO user = new UserDTO(userId, username, password, fullName, email, role, createdAt);
            boolean success = userDAO.updateUser(user);
            if (success) {
                message = "Update successful!";

                HttpSession session = request.getSession();
                UserDTO currentUser = (UserDTO) session.getAttribute("user");
                if (currentUser != null && currentUser.getUserId() == userId) {
                    UserDTO updatedUser = userDAO.getUserByUserId(userId);
                    session.setAttribute("user", updatedUser);
                }

            } else {
                checkError = "Failed to update user.";
            }

            request.setAttribute("userEdit", user);
        } catch (Exception e) {
            checkError = "Invalid input: " + e.getMessage();
            e.printStackTrace();
        }

        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        request.setAttribute("keyword", keyword);
        request.setAttribute("isEdit", true);

        return "formUser.jsp";
    }

    private String handleShowFormUser(HttpServletRequest request, HttpServletResponse response) {
        return "formUser.jsp";
    }

    private String handleAddUser(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        String usernameError = "";
        String emailError = "";

        // lay du lieu tu form
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);

        //-- tryCatch --
        // xu ly loi
        if (userDAO.isUserExistingByUserName(username)) {
            request.setAttribute("usernameError", "This user name already exists!");
        }
        if (userDAO.isUserExistingByEmail(email)) {
            request.setAttribute("emailError", "This email is already registered");
        }

        // create userDTO
        UserDTO user = new UserDTO(username, password, fullName, email);
        if (!userDAO.register(user)) {
            checkError += "<br/> Can not Add!";
        }
        // gui user len request
        request.setAttribute("userAdd", user);

        // message handling
        if (checkError.isEmpty()) {
            message = "Add Successfully!";
        }

        // gui message va checkError di
        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        return "formUser.jsp";
    }
}
