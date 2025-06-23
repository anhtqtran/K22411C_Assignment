package com.tranthanhqueanh.midtest.connectors;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tranthanhqueanh.midtest.models.Account;

import java.util.ArrayList;

// Handles database operations for the Account table.
public class AccountConnector {
    private SQLiteDatabase database;
    private SQLiteConnector dbConnector;

    public AccountConnector(Activity context) {
        dbConnector = new SQLiteConnector(context);
        // Database will be opened later, or assumed opened by LoginActivity
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

    // Authenticates a user based on username, password, and account type.
    public Account authenticateUser(String username, String password, int typeOfAccount) {
        openDatabase();
        Account account = null;
        Cursor cursor = null;
        try {
            // Raw query to prevent SQL injection for now (in real app, use parameterized queries)
            String query = "SELECT ID, Username, Password, TypeOfAccount FROM Account WHERE Username = ? AND Password = ? AND TypeOfAccount = ?";
            cursor = database.rawQuery(query, new String[]{username, password, String.valueOf(typeOfAccount)});

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String dbUsername = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
                String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow("Password"));
                int dbTypeOfAccount = cursor.getInt(cursor.getColumnIndexOrThrow("TypeOfAccount"));
                account = new Account(id, dbUsername, dbPassword, dbTypeOfAccount);
            }
        } catch (Exception e) {
            Log.e("AccountConnector", "Error authenticating user: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // Do not close database here as it might be used by other connectors in the same session
            // closeDatabase();
        }
        return account;
    }

    // Retrieves all employees (TypeOfAccount = 2).
    public ArrayList<Account> getAllEmployees() {
        openDatabase();
        ArrayList<Account> employees = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT ID, Username FROM Account WHERE TypeOfAccount = 2";
            cursor = database.rawQuery(query, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                    String username = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
                    // Password is not needed for display, setting it to empty for security
                    employees.add(new Account(id, username, "", 2));
                }
            }
        } catch (Exception e) {
            Log.e("AccountConnector", "Error getting all employees: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // closeDatabase(); // Keep database open if multiple operations follow
        }
        return employees;
    }

    // Retrieves an account by ID.
    public Account getAccountById(int accountId) {
        openDatabase();
        Account account = null;
        Cursor cursor = null;
        try {
            String query = "SELECT ID, Username, TypeOfAccount FROM Account WHERE ID = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(accountId)});

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("Username"));
                int type = cursor.getInt(cursor.getColumnIndexOrThrow("TypeOfAccount"));
                account = new Account(id, username, "", type); // Password not needed
            }
        } catch (Exception e) {
            Log.e("AccountConnector", "Error getting account by ID: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return account;
    }
}
