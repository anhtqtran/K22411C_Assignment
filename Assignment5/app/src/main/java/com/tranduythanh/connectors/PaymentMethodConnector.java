package com.tranduythanh.connectors;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tranduythanh.models.ListPaymentMethod;
import com.tranduythanh.models.PaymentMethod;
import java.util.ArrayList;

public class PaymentMethodConnector {
    private ListPaymentMethod listPaymentMethod;

    public PaymentMethodConnector() {
        listPaymentMethod = new ListPaymentMethod();
    }

    public ArrayList<PaymentMethod> getAllPaymentMethods() {
        if (listPaymentMethod == null) {
            listPaymentMethod = new ListPaymentMethod();
        }
        return listPaymentMethod.getPaymentMethods();
    }

    public boolean isExist(PaymentMethod pm) {
        return listPaymentMethod.isExist(pm);
    }

    public void addPaymentMethod(PaymentMethod pm) {
        listPaymentMethod.addPaymentMethod(pm);
    }

    /**
     * Truy vấn toàn bộ dữ liệu từ bảng paymentmethod và mô hình hóa thành danh sách PaymentMethod.
     * @param database Cơ sở dữ liệu SQLite
     * @return ListPaymentMethod chứa danh sách các phương thức thanh toán
     */
    public ListPaymentMethod getAllPaymentMethods(SQLiteDatabase database) {
        listPaymentMethod = new ListPaymentMethod();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM paymentmethod", null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
                PaymentMethod pm = new PaymentMethod();
                pm.setId(id);
                pm.setName(name);
                pm.setDescription(description);
                listPaymentMethod.addPaymentMethod(pm);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPaymentMethod;
    }
}