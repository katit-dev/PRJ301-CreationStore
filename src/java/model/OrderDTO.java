package model;

import java.sql.Timestamp;
import java.util.Date;

public class OrderDTO {
    private int orderId;
    private int userId;
    private double totalAmount;
    private String status;
    private Date orderDate;
    private String note;

    public OrderDTO() {}

    public OrderDTO(int userId, double totalAmount, String status, Date orderDate, String note) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.note = note;
    }

    public OrderDTO(int orderId, int userId, double totalAmount, String status, Date orderDate, String note) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.note = note;
    }


    // Getters & Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

   
}
