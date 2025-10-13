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
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class CartDetailDAO {

    public boolean addProductToCart(int cartId, int productId, int quantity, double price) throws SQLException, ClassNotFoundException {
        boolean success = false;
        String checkSql = "SELECT Quantity FROM CartDetail WHERE CartId = ? AND ProductId = ?";
        Connection conn = DbUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(checkSql);
        ps.setInt(1, cartId);
        ps.setInt(2, productId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int oldQuantity = rs.getInt("Quantity");
            String updateSql = "UPDATE CartDetail SET Quantity = ? WHERE CartId = ? AND ProductId = ?";
            ps = conn.prepareStatement(updateSql);
            ps.setInt(1, oldQuantity + quantity);
            ps.setInt(2, cartId);
            ps.setInt(3, productId);
            int rowAffected = ps.executeUpdate();
            success = (rowAffected > 0);
        } else {
            String insertSql = "INSERT INTO CartDetail (CartId, ProductId, Quantity, PriceAtTime) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(insertSql);
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);
            int rowAffected = ps.executeUpdate();
            success = (rowAffected > 0);
        }
        return success;
    }

    public List<CartDetailDTO> getCartDetails(int cartId) throws SQLException, ClassNotFoundException {
        List<CartDetailDTO> list = new ArrayList<>();
        String sql = "SELECT cd.ProductId, p.ProductName, cd.Quantity, cd.PriceAtTime "
                + "FROM CartDetail cd JOIN Products p ON cd.ProductId = p.ProductId "
                + "WHERE cd.CartId = ?";

        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartDetailDTO item = new CartDetailDTO();
                item.setCartId(cartId);
                item.setProductId(rs.getInt("ProductId"));
                item.setProductName(rs.getString("ProductName"));
                item.setQuantity(rs.getInt("Quantity"));
                item.setPriceAtTime(rs.getDouble("PriceAtTime"));
                list.add(item);
            }
        }
        return list;
    }

    public boolean removeProductFromCart(int cartId, int productId) throws SQLException, ClassNotFoundException {
        boolean success = false;
        String sql = "DELETE FROM CartDetail WHERE CartId = ? AND ProductId = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            success = (ps.executeUpdate() > 0);
        }
        return success;
    }
    
    public void updateQuantity(int cartId, int productId, int delta) throws SQLException, ClassNotFoundException {
    String sql = "UPDATE CartDetail SET Quantity = Quantity + ? WHERE CartId = ? AND ProductId = ? AND Quantity + ? > 0";
    try (Connection conn = DbUtils.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, delta);
        ps.setInt(2, cartId);
        ps.setInt(3, productId);
        ps.setInt(4, delta); // tránh giảm xuống âm
        ps.executeUpdate();
    }
}


}
