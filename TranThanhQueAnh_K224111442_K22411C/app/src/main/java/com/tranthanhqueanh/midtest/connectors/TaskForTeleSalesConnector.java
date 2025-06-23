package com.tranthanhqueanh.midtest.connectors;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tranthanhqueanh.midtest.models.TaskForTeleSales;
import com.tranthanhqueanh.midtest.models.TaskForTeleSalesDetails;
import com.tranthanhqueanh.midtest.models.Customer;
import com.tranthanhqueanh.midtest.models.Account; // Needed to get employee name if displaying it with task

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

// Handles database operations for TaskForTeleSales and TaskForTeleSalesDetails tables.
public class TaskForTeleSalesConnector {
    private SQLiteDatabase database;
    private SQLiteConnector dbConnector;
    private Activity context; // Keep context for Account/Customer connectors if needed

    public TaskForTeleSalesConnector(Activity context) {
        this.context = context;
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

    // Inserts a new task into the TaskForTeleSales table.
    // Returns the ID of the newly inserted row, or -1 on error.
    public long insertTask(TaskForTeleSales task) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put("AccountID", task.getAccountId());
        values.put("TaskTitle", task.getTaskTitle());
        values.put("DateAssigned", task.getDateAssigned());
        values.put("IsCompleted", task.getIsCompleted());

        long newRowId = -1;
        try {
            newRowId = database.insert("TaskForTeleSales", null, values);
        } catch (Exception e) {
            Log.e("TaskConnector", "Error inserting task: " + e.getMessage());
        } finally {
            // closeDatabase(); // Keep database open if multiple operations follow (e.g., details)
        }
        return newRowId;
    }

    // Inserts a new task detail into the TaskForTeleSalesDetails table.
    public long insertTaskDetail(TaskForTeleSalesDetails detail) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put("TaskForTeleSalesID", detail.getTaskForTeleSalesId());
        values.put("CustomerID", detail.getCustomerId());
        values.put("IsCalled", detail.getIsCalled());

        long newRowId = -1;
        try {
            newRowId = database.insert("TaskForTeleSalesDetails", null, values);
        } catch (Exception e) {
            Log.e("TaskConnector", "Error inserting task detail: " + e.getMessage());
        } finally {
            // closeDatabase();
        }
        return newRowId;
    }

    // Retrieves all tasks for the Admin dashboard.
    public ArrayList<TaskForTeleSales> getAllTasks() {
        openDatabase();
        ArrayList<TaskForTeleSales> tasks = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT ID, AccountID, TaskTitle, DateAssigned, IsCompleted FROM TaskForTeleSales ORDER BY DateAssigned DESC";
            cursor = database.rawQuery(query, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                    int accountId = cursor.getInt(cursor.getColumnIndexOrThrow("AccountID"));
                    String taskTitle = cursor.getString(cursor.getColumnIndexOrThrow("TaskTitle"));
                    String dateAssigned = cursor.getString(cursor.getColumnIndexOrThrow("DateAssigned"));
                    int isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("IsCompleted"));

                    tasks.add(new TaskForTeleSales(id, accountId, taskTitle, dateAssigned, isCompleted));
                }
            }
        } catch (Exception e) {
            Log.e("TaskConnector", "Error getting all tasks: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // closeDatabase();
        }
        return tasks;
    }

    // Retrieves the most recent task for a specific employee on a given date.
    public TaskForTeleSales getMostRecentTaskForEmployeeByDate(int employeeAccountId, String date) {
        openDatabase();
        TaskForTeleSales task = null;
        Cursor cursor = null;
        try {
            String query = "SELECT ID, AccountID, TaskTitle, DateAssigned, IsCompleted " +
                    "FROM TaskForTeleSales " +
                    "WHERE AccountID = ? AND DateAssigned = ? " +
                    "ORDER BY ID DESC LIMIT 1"; // Get the most recent by ID if multiple on same date
            cursor = database.rawQuery(query, new String[]{String.valueOf(employeeAccountId), date});

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                int accountId = cursor.getInt(cursor.getColumnIndexOrThrow("AccountID"));
                String taskTitle = cursor.getString(cursor.getColumnIndexOrThrow("TaskTitle"));
                String dateAssigned = cursor.getString(cursor.getColumnIndexOrThrow("DateAssigned"));
                int isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("IsCompleted"));
                task = new TaskForTeleSales(id, accountId, taskTitle, dateAssigned, isCompleted);
            }
        } catch (Exception e) {
            Log.e("TaskConnector", "Error getting most recent task for employee: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // closeDatabase();
        }
        return task;
    }

    // Retrieves all details (customer info and call status) for a given task ID.
    public ArrayList<TaskForTeleSalesDetails> getTaskDetailsByTaskId(int taskId) {
        openDatabase();
        ArrayList<TaskForTeleSalesDetails> details = new ArrayList<>();
        Cursor cursor = null;
        try {
            // Join with Customer table to get customer name and phone
            String query = "SELECT TFD.ID, TFD.TaskForTeleSalesID, TFD.CustomerID, TFD.IsCalled, C.Name, C.Phone " +
                    "FROM TaskForTeleSalesDetails TFD " +
                    "JOIN Customer C ON TFD.CustomerID = C.ID " +
                    "WHERE TFD.TaskForTeleSalesID = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(taskId)});

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int detailId = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                    int taskFTSId = cursor.getInt(cursor.getColumnIndexOrThrow("TaskForTeleSalesID"));
                    int customerId = cursor.getInt(cursor.getColumnIndexOrThrow("CustomerID"));
                    int isCalled = cursor.getInt(cursor.getColumnIndexOrThrow("IsCalled"));
                    String customerName = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                    String customerPhone = cursor.getString(cursor.getColumnIndexOrThrow("Phone"));

                    TaskForTeleSalesDetails detail = new TaskForTeleSalesDetails(detailId, taskFTSId, customerId, isCalled);
                    detail.setCustomer(new Customer(customerId, customerName, customerPhone)); // Set the customer object
                    details.add(detail);
                }
            }
        } catch (Exception e) {
            Log.e("TaskConnector", "Error getting task details by task ID: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            // closeDatabase();
        }
        return details;
    }


    // Updates the IsCalled status for a specific task detail entry.
    public boolean updateTaskDetailIsCalled(int detailId, int isCalled) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put("IsCalled", isCalled);
        try {
            int rowsAffected = database.update("TaskForTeleSalesDetails", values, "ID = ?", new String[]{String.valueOf(detailId)});
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e("TaskConnector", "Error updating task detail IsCalled: " + e.getMessage());
            return false;
        } finally {
            // closeDatabase();
        }
    }

    // Updates the IsCompleted status for a specific task.
    public boolean updateTaskIsCompleted(int taskId, int isCompleted) {
        openDatabase();
        ContentValues values = new ContentValues();
        values.put("IsCompleted", isCompleted);
        try {
            int rowsAffected = database.update("TaskForTeleSales", values, "ID = ?", new String[]{String.valueOf(taskId)});
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e("TaskConnector", "Error updating task IsCompleted: " + e.getMessage());
            return false;
        } finally {
            // closeDatabase();
        }
    }

    // Checks if all details for a given task ID are marked as IsCalled=1.
    public boolean areAllTaskDetailsCalled(int taskId) {
        openDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT COUNT(*) FROM TaskForTeleSalesDetails WHERE TaskForTeleSalesID = ? AND IsCalled = 0";
            cursor = database.rawQuery(query, new String[]{String.valueOf(taskId)});
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(0) == 0; // If count of uncalled is 0, then all are called
            }
        } catch (Exception e) {
            Log.e("TaskConnector", "Error checking if all tasks details are called: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false; // Assume not all called if error or no data
    }

    // Utility method to get current date in YYYY-MM-DD format
    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}
