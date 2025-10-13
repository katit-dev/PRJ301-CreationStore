/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.pkcs11.Secmod;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class ProductDAO {

    /**
     * Get all products
     */
    public List<ProductDTO> getAllProducts() {

        List<ProductDTO> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));
                product.setStatus(rs.getBoolean("Status"));
                product.setImage(rs.getString("Image"));
                product.setValidity(rs.getInt("Validity"));
                product.setCategoryId(rs.getInt("CategoryId"));

                products.add(product);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Get all ACTIVE products
     */
    public List<ProductDTO> getAllActiveProducts() {

        List<ProductDTO> products = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE Status = 1";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));
                product.setStatus(rs.getBoolean("Status"));
                product.setImage(rs.getString("Image"));
                product.setValidity(rs.getInt("Validity"));
                product.setCategoryId(rs.getInt("CategoryId"));

                products.add(product);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * get product by category_id
     */
    public List<ProductDTO> getProductByCategoryId(int categoryId) {
        List<ProductDTO> products = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE CategoryId = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));
                product.setStatus(rs.getBoolean("Status"));
                product.setImage(rs.getString("Image"));
                product.setValidity(rs.getInt("Validity"));
                product.setCategoryId(rs.getInt("CategoryId"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * List by name
     */
    public List<ProductDTO> listProductByName(String productname) {
        List<ProductDTO> products = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE  Status = 1 AND ProductName LIKE ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + productname + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));
                product.setStatus(rs.getBoolean("Status"));
                product.setImage(rs.getString("Image"));
                product.setValidity(rs.getInt("Validity"));
                product.setCategoryId(rs.getInt("CategoryId"));

                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    
    /**
     * List by name (active and inactive product)
     */
    public List<ProductDTO> listAllProductByName(String productname) {
        List<ProductDTO> products = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE ProductName LIKE ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + productname + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));
                product.setStatus(rs.getBoolean("Status"));
                product.setImage(rs.getString("Image"));
                product.setValidity(rs.getInt("Validity"));
                product.setCategoryId(rs.getInt("CategoryId"));

                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Filter products
     */
    public List<ProductDTO> filterProducts(int categoryId, double priceFrom, double priceTo) {
        List<ProductDTO> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Products WHERE Status = 1");

        if (categoryId > 0) {
            sql.append(" AND CategoryId = ").append(categoryId);
        }
        if (priceFrom >= 0) {
            sql.append(" AND Price >= ").append(priceFrom);
        }
        if (priceTo > 0) {
            sql.append(" AND Price <= ").append(priceTo);
        }

        try ( Connection conn = DbUtils.getConnection();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql.toString())) {

            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));
                product.setStatus(rs.getBoolean("Status"));
                product.setImage(rs.getString("Image"));
                product.setValidity(rs.getInt("Validity"));
                product.setCategoryId(rs.getInt("CategoryId"));
                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    /**
     * GEt product by product_id
     */
    public ProductDTO getProductById(int product_id) {
        ProductDTO product = null;
        String sql = "  SELECT productId, productName, description, price, image, validity, status, CategoryId FROM Products WHERE productId = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, product_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new ProductDTO();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));
                product.setImage(rs.getString("Image"));
                product.setValidity(rs.getInt("Validity"));
                product.setStatus(rs.getBoolean("Status"));
                product.setCategoryId(rs.getInt("CategoryId"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }
    
    /**
     * GEt product by product_name
     */
    public ProductDTO getProductByName(String product_name) {
        ProductDTO product = null;
        String sql = "  SELECT productId, productName, description, price, image, validity, status, CategoryId FROM Products WHERE ProductName = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, product_name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new ProductDTO();
                product.setProductId(rs.getInt("ProductId"));
                product.setProductName(rs.getString("ProductName"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));
                product.setImage(rs.getString("Image"));
                product.setValidity(rs.getInt("Validity"));
                product.setStatus(rs.getBoolean("Status"));
                product.setCategoryId(rs.getInt("CategoryId"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }
    
    public boolean isExistProductByName(String product_name){
        return (getProductByName(product_name) != null);
    }

    /**
     * Update product
     */
   private static final String UPDATE_PRODUCT = "UPDATE Products SET ProductName = ?, Description = ?, Price = ?, Validity = ?, Status = ?, CategoryId = ? WHERE ProductId = ?";

    public boolean update(ProductDTO product) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_PRODUCT);

            ps.setString(1, product.getProductName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getValidity());
            ps.setBoolean(5, product.isStatus());
            ps.setInt(6, product.getCategoryId());
            ps.setInt(7, product.getProductId()); // WHERE ProductId = ?

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
            e.printStackTrace();
        }

        return success;
    }

    /**
     * update product status
     */
    public boolean updateProductStatus(int productId, boolean status){
        ProductDTO product = getProductById(productId);
        if(product!=null){
            product.setStatus(status);
             return update(product);
        }else{
            return false;
        }     
    }
    
    /**
     * create 
     */
    public boolean create(ProductDTO product) {
    boolean success = false;
    Connection conn = null;
    PreparedStatement ps = null;

    String sql = "INSERT INTO Products (ProductName, Description, Price, Validity, CategoryId) VALUES (?, ?, ?, ?, ?)";

    try {
        conn = DbUtils.getConnection();
        ps = conn.prepareStatement(sql);

        ps.setString(1, product.getProductName());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());
        ps.setInt(4, product.getValidity());
        ps.setInt(5, product.getCategoryId());

        int rowsAffected = ps.executeUpdate();
        success = (rowsAffected > 0);
    } catch (Exception e) {
        System.err.println("Error in create(): " + e.getMessage());
        e.printStackTrace();
    }

    return success;
}

    
}
