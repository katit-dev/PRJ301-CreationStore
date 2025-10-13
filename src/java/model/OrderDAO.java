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
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class OrderDAO {

    public int insertOrder(int userId, double totalAmount, String note) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Orders (UserId, OrderDate, TotalAmount, Status, Note) VALUES (?, GETDATE(), ?, ?, ?)";
        int orderId = -1;

        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.setDouble(2, totalAmount);
            ps.setString(3, "Processing"); // Trạng thái mặc định
            ps.setString(4, note);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1); // Lấy ID đơn hàng vừa tạo
                }
            }
        }
        return orderId;
    }

    public List<OrderDTO> getOrdersByUserId(int userId) throws SQLException, ClassNotFoundException {
        List<OrderDTO> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE UserId = ? ORDER BY OrderDate DESC";

        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDTO order = new OrderDTO(
                            rs.getInt("OrderId"),
                            rs.getInt("UserId"),
                            rs.getDouble("TotalAmount"),
                            rs.getString("Status"),
                            rs.getTimestamp("OrderDate"),
                            rs.getString("Note")
                    );
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public List<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        List<OrderDTO> list = new ArrayList<>();
        String sql = "SELECT OrderId, UserId, OrderDate, TotalAmount, Status, Note FROM Orders";
        try ( Connection con = DbUtils.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrderDTO order = new OrderDTO(
                        rs.getInt("OrderId"),
                        rs.getInt("UserId"),
                        rs.getDouble("TotalAmount"),
                        rs.getString("Status"),
                        rs.getTimestamp("OrderDate"),
                        rs.getString("Note")
                );
                list.add(order);
            }
        }
        return list;
    }
    
    public boolean updateOrderStatus(int orderId, String newStatus) {
    String sql = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
    try (Connection conn = DbUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, newStatus);
        ps.setInt(2, orderId);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


}
