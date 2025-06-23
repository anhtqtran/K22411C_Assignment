package com.tranthanhqueanh.midtest.models;

import java.io.Serializable;

// Represents a Customer who needs to be contacted by telesales.
public class Customer implements Serializable {
    private int id;
    private String name;
    private String phone;

    public Customer() {
    }

    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        // Useful for displaying in list views
        return name + " - " + phone;
    }
}
