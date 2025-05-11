package com.pricecomparator.model;

import java.util.Date;

public class Discount {
    private String productId;
    private String productName;
    private String brand;
    private int packageQuantity;
    private String packageUnit;
    private String productCategory;
    private Date fromDate;
    private Date toDate;
    private double discountPercent;

    // Constructor
    public Discount(String productId, String productName, String brand, int packageQuantity,
                    String packageUnit, String productCategory, Date fromDate, Date toDate, double discountPercent) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.packageQuantity = packageQuantity;
        this.packageUnit = packageUnit;
        this.productCategory = productCategory;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.discountPercent = discountPercent;
    }

    // Getters and Setters
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(int packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }
}
