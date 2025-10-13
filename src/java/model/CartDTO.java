package model;

import java.util.Date;

public class CartDTO {
    private int cartId;
    private int userId;
    private Date createdAt;
    private String status;

    public CartDTO() {}

    public CartDTO(int userId, Date createdAt, String status) {
        this.userId = userId;
        this.createdAt = createdAt;
        this.status = status;
    }

    public CartDTO(int cartId, int userId, Date createdAt, String status) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getters & Setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
