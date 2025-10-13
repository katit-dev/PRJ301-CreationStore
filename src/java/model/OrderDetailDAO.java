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

public class OrderDetailDAO {

    public void insertOrderDetail(int orderId, int productId, int quantity, double price) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO OrderDetails (OrderId, ProductId, Quantity, PriceAtTime) VALUES (?, ?, ?, ?)";

        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);

            ps.executeUpdate();
        }
    }

    public List<OrderDetailDTO> getDetailsByOrderId(int orderId) throws SQLException, ClassNotFoundException {
    List<OrderDetailDTO> list = new ArrayList<>();
    String sql = "SELECT od.OrderId, od.ProductId, od.Quantity, od.PriceAtTime, p.ProductName " +
                 "FROM OrderDetails od JOIN Products p ON od.ProductId = p.ProductId " +
                 "WHERE od.OrderId = ?";

    try (Connection conn = DbUtils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, orderId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrderDetailDTO dto = new OrderDetailDTO(
                    rs.getInt("OrderId"),
                    rs.getInt("ProductId"),
                    rs.getInt("Quantity"),
                    rs.getDouble("PriceAtTime")
                );
                dto.setProductName(rs.getString("ProductName")); 
                list.add(dto);
            }
        }
    }
    return list;
}


}
