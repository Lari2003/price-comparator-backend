package com.pricecomparator.model;

public class Product {
    private String productId;
    private String productName;
    private String category;
    private String brand;
    private double price;
    private String unit;
    private double quantity;
    private String currency;
    private String storeName;

    // Constructor
    public Product(String productId, String productName, String category, String brand, 
                  double price, String unit,double quantity, String currency, String storeName) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.currency = currency;
        this.storeName = storeName;
        
    }

    // Getters and setters
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    public String getCurrency() { 
        return currency; }

    public void setCurrency(String currency) { 
        this.currency = currency; 
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
