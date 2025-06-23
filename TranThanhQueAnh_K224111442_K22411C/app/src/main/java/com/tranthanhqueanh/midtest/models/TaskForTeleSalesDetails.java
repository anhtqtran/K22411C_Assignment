package com.tranthanhqueanh.midtest.models;

import java.io.Serializable;

// Represents a detailed entry for a task, linking a task to a specific customer.
public class TaskForTeleSalesDetails implements Serializable {
    private int id;
    private int taskForTeleSalesId;
    private int customerId;
    private int isCalled; // 0 for False, 1 for True

    // Optional: Add Customer and TaskForTeleSales objects for easier data management in UI
    private Customer customer; // To store customer details directly for display
    private TaskForTeleSales task; // To store task details directly for display

    public TaskForTeleSalesDetails() {
    }

    public TaskForTeleSalesDetails(int id, int taskForTeleSalesId, int customerId, int isCalled) {
        this.id = id;
        this.taskForTeleSalesId = taskForTeleSalesId;
        this.customerId = customerId;
        this.isCalled = isCalled;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskForTeleSalesId() {
        return taskForTeleSalesId;
    }

    public void setTaskForTeleSalesId(int taskForTeleSalesId) {
        this.taskForTeleSalesId = taskForTeleSalesId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getIsCalled() {
        return isCalled;
    }

    public void setIsCalled(int isCalled) {
        this.isCalled = isCalled;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TaskForTeleSales getTask() {
        return task;
    }

    public void setTask(TaskForTeleSales task) {
        this.task = task;
    }
}
