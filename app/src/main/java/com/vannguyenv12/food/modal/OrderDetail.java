package com.vannguyenv12.food.modal;

public class OrderDetail {
    private int id;
    private int productId;
    private String productName;
    private int orderId;
    private double price;
    private int quantity;

    public OrderDetail(int productId, String productName, int orderId, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.orderId = orderId;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderDetail() {
    }

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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
