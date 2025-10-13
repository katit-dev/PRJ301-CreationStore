/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class CategoryDAO {

    /**
     * Get all categories
     */
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> listCategory = new ArrayList<>();
        String sql = "SELECT * FROM Categories";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                CategoryDTO category = new CategoryDTO();
                category.setCategoryId(rs.getInt("CategoryId"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setDescription(rs.getString("Description"));
                listCategory.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCategory;
    }
    
    /**
     * Get category by id
     */
    public CategoryDTO getCategoryById(int categoryId) {
    CategoryDTO category = null;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    String sql = "SELECT CategoryId, CategoryName, Description FROM Categories WHERE CategoryId = ?";

    try {
        conn = DbUtils.getConnection();
        ps = conn.prepareStatement(sql);
        ps.setInt(1, categoryId);
        rs = ps.executeQuery();

        if (rs.next()) {
            category = new CategoryDTO();
            category.setCategoryId(rs.getInt("CategoryId"));
            category.setCategoryName(rs.getString("CategoryName"));
            category.setDescription(rs.getString("Description"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return category;
}

/**
 * update
 */    
    public boolean updateCategory(CategoryDTO category) {
    boolean success = false;
    Connection conn = null;
    PreparedStatement ps = null;

    String sql = "UPDATE Categories SET CategoryName = ?, Description = ? WHERE CategoryId = ?";

    try {
        conn = DbUtils.getConnection();
        ps = conn.prepareStatement(sql);
        ps.setString(1, category.getCategoryName());
        ps.setString(2, category.getDescription());
        ps.setInt(3, category.getCategoryId());

        int rowsAffected = ps.executeUpdate();
        success = rowsAffected > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }

    return success;
}

    public boolean createCategory(CategoryDTO category) {
    boolean success = false;
    Connection conn = null;
    PreparedStatement ps = null;

    String sql = "INSERT INTO Categories (CategoryName, Description) VALUES (?, ?)";

    try {
        conn = DbUtils.getConnection();
        ps = conn.prepareStatement(sql);
        ps.setString(1, category.getCategoryName());
        ps.setString(2, category.getDescription());

        int rowsAffected = ps.executeUpdate();
        success = rowsAffected > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }

    return success;
}


}
