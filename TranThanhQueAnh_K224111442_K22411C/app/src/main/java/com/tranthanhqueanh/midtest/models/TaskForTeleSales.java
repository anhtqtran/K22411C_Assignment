package com.tranthanhqueanh.midtest.models;

import java.io.Serializable;

// Represents a telesales task assigned to an employee.
public class TaskForTeleSales implements Serializable {
    private int id;
    private int accountId; // Employee's Account ID
    private String taskTitle;
    private String dateAssigned; // Format: YYYY-MM-DD
    private int isCompleted; // 0 for False, 1 for True

    public TaskForTeleSales() {
    }

    public TaskForTeleSales(int id, int accountId, String taskTitle, String dateAssigned, int isCompleted) {
        this.id = id;
        this.accountId = accountId;
        this.taskTitle = taskTitle;
        this.dateAssigned = dateAssigned;
        this.isCompleted = isCompleted;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(String dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return taskTitle + " (Assigned: " + dateAssigned + ")";
    }
}
