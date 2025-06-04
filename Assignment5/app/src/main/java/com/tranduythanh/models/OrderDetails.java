package com.tranduythanh.models;

public class OrderDetails {
    private int Id;
    private int OrderId;
    private int ProductId;
    private double Quantity;
    private double Price;
    private double Discount;
    private double VAT;
    private double TotalValue;

    public OrderDetails() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
    }

    public double getTotalValue() {
        // Calculate total value: (Quantity * Price - Discount) * (1 + VAT/100)
        TotalValue = (Quantity * Price - Discount) * (1 + VAT / 100);
        return TotalValue;
    }

    public void setTotalValue(double totalValue) {
        TotalValue = totalValue;
    }
}
