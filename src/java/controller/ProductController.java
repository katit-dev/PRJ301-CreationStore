package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.ProductDAO;
import model.ProductDTO;

import java.io.IOException;
import java.util.List;
import model.CategoryDAO;
import model.CategoryDTO;

@WebServlet(name = "ProductController", urlPatterns = {"/ProductController"})
public class ProductController extends HttpServlet {

    ProductDAO productDAO = new ProductDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "error.jsp";
        try {
            if ("searchProduct".equals(action)) {
                url = handleSearchProducts(request);
            } else if ("FilterProduct".equals(action)) {
                url = handleFilterProducts(request);
            } else if ("ShowAllActiveProducts".equals(action)) {
                url = handleShowAllActiveProducts(request);
            } else if ("ViewDetail".equals(action)) {
                url = handleViewDetail(request);
            } else if ("viewAllProducts".equals(action)) {
                url = handleViewAllProducts(request);
            } else if ("searchProductByAdmin".equals(action)) {
                url = handleSearchProductByAdmin(request, response);
            } else if ("editProduct".equals(action)) {
                url = handleEditProduct(request, response);
            } else if ("UpdateProduct".equals(action)) {
                url = handleUpdateProduct(request, response);
            } else if ("deleteProduct".equals(action)) {
                url = handleDeleteProduct(request, response);
            } else if ("showFormProduct".equals(action)) {
                url = handleShowFormProduct(request, response);
            } else if ("addProduct".equals(action)) {
                url = handleAddProduct(request, response);
            } else {
                request.setAttribute("message", "Invalid action: " + action);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "ProductController error: " + e.getMessage());
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

    private String handleSearchProducts(HttpServletRequest request) {
        String keyword = request.getParameter("strKeyword");
        List<ProductDTO> listSearch = productDAO.listProductByName(keyword);

        CategoryDAO categoryDAO = new CategoryDAO();
        List<CategoryDTO> listCategory = categoryDAO.getAllCategories();
        request.setAttribute("listCategory", listCategory);

        request.setAttribute("keyword", keyword);
        request.setAttribute("listSearch", listSearch);
        return "welcome.jsp";
    }

    private String handleSearchProductByAdmin(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");
        List<ProductDTO> listSearchAD = productDAO.listAllProductByName(keyword);
        request.setAttribute("keyword", keyword);
        request.setAttribute("listSearchAD", listSearchAD);

        List<ProductDTO> allProductList = productDAO.getAllProducts();
        request.setAttribute("allProductList", allProductList);
        return "Table.jsp";
    }

    private String handleFilterProducts(HttpServletRequest request) {
        String categoryIdStr = request.getParameter("categoryId");
        String priceFromStr = request.getParameter("priceFrom");
        String priceToStr = request.getParameter("priceTo");

        int categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : 0;
        double priceFrom = (priceFromStr != null && !priceFromStr.isEmpty()) ? Double.parseDouble(priceFromStr) : 0;
        double priceTo = (priceToStr != null && !priceToStr.isEmpty()) ? Double.parseDouble(priceToStr) : 0;

        // Hoán đổi nếu priceFrom > priceTo
        if (priceTo > 0 && priceTo < priceFrom) {
            double tmp = priceFrom;
            priceFrom = priceTo;
            priceTo = tmp;
        }
        List<ProductDTO> listFilterProducts = productDAO.filterProducts(categoryId, priceFrom, priceTo);

        CategoryDAO categoryDAO = new CategoryDAO();
        List<CategoryDTO> listCategory = categoryDAO.getAllCategories();

        request.setAttribute("listFilterProducts", listFilterProducts);
        request.setAttribute("listCategory", listCategory);

        // Lưu lại thông tin lọc để giữ trên giao diện
        request.setAttribute("selectedCategoryId", categoryId);
        request.setAttribute("priceFrom", priceFrom);
        request.setAttribute("priceTo", priceTo);

        return "welcome.jsp";
    }

    private String handleShowAllActiveProducts(HttpServletRequest request) {
        List<ProductDTO> productList = productDAO.getAllActiveProducts();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<CategoryDTO> listCategory = categoryDAO.getAllCategories();

        request.setAttribute("productList", productList);
        request.setAttribute("listCategory", listCategory);
        return "welcome.jsp";
    }

    private String handleViewDetail(HttpServletRequest request) {
        String strId = request.getParameter("strId");
        if (strId == null || strId.trim().isEmpty()) {
            request.setAttribute("checkError", "product ID is missing!");
            return "welcome.jsp";
        }
        int productId = Integer.parseInt(strId);

        ProductDTO product = productDAO.getProductById(productId);
        request.setAttribute("product", product);

        return "detailOfProduct.jsp";
    }

    private String handleViewAllProducts(HttpServletRequest request) {
        List<ProductDTO> allProductList = productDAO.getAllProducts();
        request.setAttribute("allProductList", allProductList);
        return "Table.jsp";
    }

    private String handleEditProduct(HttpServletRequest request, HttpServletResponse response) {
        String strProductId = request.getParameter("strProductId");
        String keyword = request.getParameter("keyword");

        int productID = Integer.parseInt(strProductId);

        ProductDTO product = productDAO.getProductById(productID);
        if (product != null) {
            request.setAttribute("productEdit", product);
            request.setAttribute("keyword", keyword);

            CategoryDAO categoryDAO = new CategoryDAO();
            List<CategoryDTO> listCategory = categoryDAO.getAllCategories();
            request.setAttribute("listCategory", listCategory);
            request.setAttribute("isEdit", true);
            return "formProduct.jsp";
        } else {
            request.setAttribute("checkError", "Product not found!");
        }

        return handleSearchProductByAdmin(request, response);
    }

    private String handleUpdateProduct(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        String keyword = request.getParameter("keyword");

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int validity = Integer.parseInt(request.getParameter("validity"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        // status 
        String statusStr = request.getParameter("status");
        boolean status = Boolean.parseBoolean(statusStr);

        ProductDTO existingProduct = productDAO.getProductByName(name);
        if (existingProduct != null && existingProduct.getProductId() != id) {
            request.setAttribute("nameError", "This name already exists!");
        }

        ProductDTO product = new ProductDTO();
        product.setProductId(id);
        product.setProductName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setValidity(validity);
        product.setStatus(status);
        product.setCategoryId(categoryId);

        boolean success = productDAO.update(product);
        if (success) {
            message = "Update successful!";
        } else {
            checkError = "Failed to update product.";
        }

        request.setAttribute("productEdit", product);

        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        request.setAttribute("keyword", keyword);

        List<CategoryDTO> listCategory = new CategoryDAO().getAllCategories();
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("isEdit", true);
        return "formProduct.jsp";
    }

    private String handleShowFormProduct(HttpServletRequest request, HttpServletResponse response) {
        List<CategoryDTO> listCategory = new CategoryDAO().getAllCategories();
        request.setAttribute("listCategory", listCategory);
        return "formProduct.jsp";
    }

    private String handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("strProductId"));
        boolean check = productDAO.updateProductStatus(id, false);
        return handleSearchProductByAdmin(request, response);

    }

    private String handleAddProduct(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        String keyword = request.getParameter("keyword");

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int validity = Integer.parseInt(request.getParameter("validity"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        if (productDAO.isExistProductByName(name)) {
            request.setAttribute("nameError", "This name already exists!");
        }

        ProductDTO product = new ProductDTO();
        product.setProductName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setValidity(validity);
        product.setCategoryId(categoryId);

        boolean success = productDAO.create(product);
        if (success) {
            message = "Add successful!";
        } else {
            checkError = "Failed to add product.";
        }

        request.setAttribute("productAdd", product);

        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        request.setAttribute("keyword", keyword);

        List<CategoryDTO> listCategory = new CategoryDAO().getAllCategories();
        request.setAttribute("listCategory", listCategory);
        return "formProduct.jsp";
    }

}
