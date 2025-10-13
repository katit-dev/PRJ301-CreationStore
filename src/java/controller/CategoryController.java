package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.CategoryDAO;
import model.CategoryDTO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryController", urlPatterns = {"/CategoryController"})
public class CategoryController extends HttpServlet {

    CategoryDAO catDAO = new CategoryDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "error.jsp";
        try {
            if ("viewAllCategories".equals(action)) {
                url = handleViewAllCategories(request, response);
            } else if ("editAction".equals(action)) {
                url = handleEditAction(request, response);
            } else if ("UpdateCategory".equals(action)) {
                url = handleUpdateCategory(request, response);
            } else if ("showFormCat".equals(action)) {
                url = handleShowFormCat(request);
            } else if ("addCategory".equals(action)) {
                url = handleAddCategory(request);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "CategoryController error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String handleViewAllCategories(HttpServletRequest request, HttpServletResponse response) {
        List<CategoryDTO> allCategorieslist = new CategoryDAO().getAllCategories();
        request.setAttribute("allCategorieslist", allCategorieslist);
        return "Table.jsp";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private String handleEditAction(HttpServletRequest request, HttpServletResponse response) {
        String strCategoryId = request.getParameter("strCategoryId");

        int categoryId = Integer.parseInt(strCategoryId);

        CategoryDTO category = catDAO.getCategoryById(categoryId);
        if (category != null) {
            request.setAttribute("category", category);

            CategoryDAO categoryDAO = new CategoryDAO();
            List<CategoryDTO> listCategory = categoryDAO.getAllCategories();
            request.setAttribute("listCategory", listCategory);
            request.setAttribute("isEdit", true);
            return "formCategory.jsp";
        } else {
            request.setAttribute("checkError", "category not found!");
        }

        return handleViewAllCategories(request, response);
    }

    private String handleUpdateCategory(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";

        try {
            int categoryId = Integer.parseInt(request.getParameter("id"));
            String categoryName = request.getParameter("name");
            String description = request.getParameter("description");

            CategoryDTO category = new CategoryDTO();
            category.setCategoryId(categoryId);
            category.setCategoryName(categoryName);
            category.setDescription(description);

            CategoryDAO categoryDAO = new CategoryDAO();
            boolean success = categoryDAO.updateCategory(category);

            if (success) {
                message = "Update successful!";
            } else {
                checkError = "Update failed!";
            }

            request.setAttribute("category", category);
            request.setAttribute("message", message);
            request.setAttribute("checkError", checkError);
            request.setAttribute("isEdit", true);
        } catch (Exception e) {
            checkError = "Error: " + e.getMessage();
            request.setAttribute("checkError", checkError);
        }

        return "formCategory.jsp";
    }

    private String handleShowFormCat(HttpServletRequest request) {
        return "formCategory.jsp";
    }

    private String handleAddCategory(HttpServletRequest request) {
        String checkError = "";
        String message = "";

        try {
             String categoryName = request.getParameter("name");
            String description = request.getParameter("description");

            CategoryDTO category = new CategoryDTO();
            category.setCategoryName(categoryName);
            category.setDescription(description);

            CategoryDAO categoryDAO = new CategoryDAO();
            boolean success = categoryDAO.createCategory(category);

            if (success) {
                message = "Category added successfully!";
            } else {
                checkError = "Failed to add category.";
            }

            request.setAttribute("categoryAdd", category);
            request.setAttribute("message", message);
            request.setAttribute("checkError", checkError);
        } catch (Exception e) {
            checkError = "Error: " + e.getMessage();
            request.setAttribute("checkError", checkError);
        }

        return "formCategory.jsp";
    }

}
