package com.tranduythanh.connectors;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tranduythanh.models.Customer;
import com.tranduythanh.models.ListCustomer;

public class CustomerConnector {

    public ListCustomer getAllCustomers(SQLiteDatabase database) {
        ListCustomer listCustomer = new ListCustomer();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM Customer", null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String phone = cursor.getString(3);
                String username = cursor.getString(4);
                String password = cursor.getString(5);
                Customer c = new Customer();
                c.setId(id);
                c.setName(name);
                c.setEmail(email);
                c.setPhone(phone);
                c.setUsername(username);
                c.setPassword(password);
                listCustomer.addCustomer(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return listCustomer;
    }

    public long saveNewCustomer(Customer c, SQLiteDatabase database) {
        try {
            ContentValues values = new ContentValues();
            values.put("Name", c.getName());
            values.put("Email", c.getEmail());
            values.put("Phone", c.getPhone());
            values.put("UserName", c.getUsername());
            values.put("Password", c.getPassword());
            return database.insertOrThrow("Customer", null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long saveUpdateCustomer(Customer c, SQLiteDatabase database) {
        try {
            ContentValues values = new ContentValues();
            values.put("Name", c.getName());
            values.put("Email", c.getEmail());
            values.put("Phone", c.getPhone());
            values.put("UserName", c.getUsername());
            values.put("Password", c.getPassword());
            return database.update("Customer", values, "Id = ?", new String[]{String.valueOf(c.getId())});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long removeCustomer(int id, SQLiteDatabase database) {
        try {
            return database.delete("Customer", "Id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean isExist(Customer c, SQLiteDatabase database) {
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT Id FROM Customer WHERE Id = ?", new String[]{String.valueOf(c.getId())});
            return cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}