package com.tranthanhqueanh.myapplication.models;

public class Products {
    private int ID;
    private String ProductCode;
    private String ProductName;
    private double UnitPrice;
    private String ImageLink;

    public Products(int ID, String ProductCode, String ProductName, double UnitPrice, String ImageLink) {
        this.ID = ID;
        this.ProductCode = ProductCode;
        this.ProductName = ProductName;
        this.UnitPrice = UnitPrice;
        this.ImageLink = ImageLink;
    }

    // Getters và Setters
    public int getID() { return ID; }
    public String getProductCode() { return ProductCode; }
    public String getProductName() { return ProductName; }
    public double getUnitPrice() { return UnitPrice; }
    public String getImageLink() { return ImageLink; }
}
