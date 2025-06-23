package com.tranthanhqueanh.midtest.connectors;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tranthanhqueanh.midtest.models.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

// Handles database operations for the Customer table.
public class CustomerConnector {
    private SQLiteDatabase database;
    private SQLiteConnector dbConnector;

    public CustomerConnector(Activity context) {
        dbConnector = new SQLiteConnector(context);
    }

    // Opens the database connection.
    private void openDatabase() {
        if (database == null || !database.isOpen()) {
            database = dbConnector.openDatabase();
        }
    }

    // Closes the database connection.
    private void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    // Retrieves a random set of customers for task assignment.
    public ArrayList<Customer> getRandomCustomers(int count) {
        openDatabase();
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ArrayList<Customer> randomCustomers = new ArrayList<>();
        Cursor cursor = null;

        try {
            String query = "SELECT ID, Name, Phone FROM Customer";
            cursor = database.rawQuery(query, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow("Phone"));
                    allCustomers.add(new Customer(id, name, phone));
                }
            }

            // Shuffle and pick 'count' unique customers
            if (allCustomers.size() > 0) {
                Collections.shuffle(allCustomers, new Random());
                for (int i = 0; i < Math.min(count, allCustomers.size()); i++) {
                    randomCustomers.add(allCustomers.get(i));
                }
            }

        } catch (Exception e) {
            Log.e("CustomerConnector", "Error getting random customers: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // closeDatabase(); // Keep database open if multiple operations follow
        }
        return randomCustomers;
    }

    // Retrieves a customer by ID.
    public Customer getCustomerById(int customerId) {
        openDatabase();
        Customer customer = null;
        Cursor cursor = null;
        try {
            String query = "SELECT ID, Name, Phone FROM Customer WHERE ID = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(customerId)});

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("Phone"));
                customer = new Customer(id, name, phone);
            }
        } catch (Exception e) {
            Log.e("CustomerConnector", "Error getting customer by ID: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return customer;
    }

    // Retrieves customers associated with a specific TaskForTeleSales ID.
    public ArrayList<Customer> getCustomersByTaskId(int taskId) {
        openDatabase();
        ArrayList<Customer> customers = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT C.ID, C.Name, C.Phone " +
                    "FROM Customer C " +
                    "JOIN TaskForTeleSalesDetails TFD ON C.ID = TFD.CustomerID " +
                    "WHERE TFD.TaskForTeleSalesID = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(taskId)});

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow("Phone"));
                    customers.add(new Customer(id, name, phone));
                }
            }
        } catch (Exception e) {
            Log.e("CustomerConnector", "Error getting customers by task ID: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return customers;
    }
}
