package com.pricecomparator.model;

import java.util.List;

public class ShoppingList {
    private String storeName;
    private List<Product> products;
    private double totalPrice;

    // Constructor
    public ShoppingList(String storeName, List<Product> products, double totalPrice) {
        this.storeName = storeName;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
