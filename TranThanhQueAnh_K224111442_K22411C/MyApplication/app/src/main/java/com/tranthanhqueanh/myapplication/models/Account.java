package com.tranthanhqueanh.myapplication.models;

public class Account {
    private int ID;
    private String Username;
    private String Password;

    public Account(int ID, String Username, String Password) {
        this.ID = ID;
        this.Username = Username;
        this.Password = Password;
    }

    // Getters và Setters
    public int getID() { return ID; }
    public String getUsername() { return Username; }
    public String getPassword() { return Password; }
}
