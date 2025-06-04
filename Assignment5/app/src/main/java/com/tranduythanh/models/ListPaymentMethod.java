package com.tranduythanh.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ListPaymentMethod implements Serializable {
    private ArrayList<PaymentMethod> paymentMethods;

    public ListPaymentMethod() {
        paymentMethods = new ArrayList<>();
    }

    public ArrayList<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(ArrayList<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public void addPaymentMethod(PaymentMethod pm) {
        paymentMethods.add(pm);
    }

    public boolean isExist(PaymentMethod pm) {
        for (PaymentMethod method : paymentMethods) {
            if (method.getId() == pm.getId() ||
                    method.getName().equalsIgnoreCase(pm.getName())) {
                return true;
            }
        }
        return false;
    }
}