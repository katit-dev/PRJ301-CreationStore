package model;

public class ProductDTO {

    private int productId;
    private String productName;
    private String description;
    private double price;
    private boolean status;
    private String image;
    private int validity;  
    private int categoryId;

    public ProductDTO() {
    }

    public ProductDTO( String productName, String description, double price, boolean status, String image, int validity, int categoryId) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.status = status;
        this.image = image;
        this.validity = validity;
        this.categoryId = categoryId;
    }

    public ProductDTO(String productName, String description, double price, boolean status, int validity, int categoryId) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.status = status;
        this.validity = validity;
        this.categoryId = categoryId;
    }
    

    // Getters & Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
