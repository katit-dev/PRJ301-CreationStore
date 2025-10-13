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
public class CartDAO {

    public int getOrCreatePendingCartId(int userId) throws SQLException, ClassNotFoundException {
        String selectSql = "SELECT CartId FROM Cart WHERE UserId = ? AND Status = 'pending'";
        Connection conn = DbUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(selectSql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("CartId");
        } else {
            String insertSql = "INSERT INTO Cart (UserId, CreatedAt, Status) OUTPUT INSERTED.CartId VALUES (?, GETDATE(), 'pending')";
            ps = conn.prepareStatement(insertSql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1; // Error
    }

    public void clearCartByUserId(int userId) throws SQLException, ClassNotFoundException {
        String getCartIdSql = "SELECT CartId FROM Cart WHERE UserId = ? AND Status = 'Pending'";
        String deleteDetailSql = "DELETE FROM CartDetail WHERE CartId = ?";
        String updateCartSql = "UPDATE Cart SET Status = 'ordered' WHERE CartId = ?";

        try ( Connection conn = DbUtils.getConnection();  PreparedStatement psGet = conn.prepareStatement(getCartIdSql)) {

            psGet.setInt(1, userId);
            try ( ResultSet rs = psGet.executeQuery()) {
                if (rs.next()) {
                    int cartId = rs.getInt("CartId");

                    // Xoá chi tiết sản phẩm
                    try ( PreparedStatement psDel = conn.prepareStatement(deleteDetailSql)) {
                        psDel.setInt(1, cartId);
                        psDel.executeUpdate();
                    }

                    // Cập nhật trạng thái giỏ hàng
                    try ( PreparedStatement psUpdate = conn.prepareStatement(updateCartSql)) {
                        psUpdate.setInt(1, cartId);
                        psUpdate.executeUpdate();
                    }
                }
            }
        }
    }

    public List<CartDTO> getAllCarts() throws SQLException, ClassNotFoundException {
        List<CartDTO> list = new ArrayList<>();
        String sql = "SELECT CartId, UserId, Status, CreatedAt FROM Cart";
        try ( Connection con = DbUtils.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CartDTO cart = new CartDTO(
                        rs.getInt("CartId"),
                        rs.getInt("UserId"),
                        rs.getTimestamp("CreatedAt"),
                        rs.getString("Status")
                );
                list.add(cart);
            }
        }
        return list;
    }

}
