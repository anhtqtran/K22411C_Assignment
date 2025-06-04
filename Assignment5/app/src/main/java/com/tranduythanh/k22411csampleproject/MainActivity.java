package com.tranduythanh.k22411csampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView imgEmployee, imgCustomer, imgProduct, imgOrder, imgPaymentMethod, imgVoucher, imgBlog, imgTransport, imgReport, imgAdvancedProduct;
    TextView txtEmployee, txtCustomer, txtProduct, txtOrder, txtPaymentMethod, txtVoucher, txtBlog, txtTransport, txtReport, txtAdvancedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addView();
        addEvents();
        handleSystemBars();
    }

    private void addView() {
        imgEmployee = findViewById(R.id.imgEmployee);
        txtEmployee = findViewById(R.id.txtEmployee);
        imgCustomer = findViewById(R.id.imgCustomer);
        txtCustomer = findViewById(R.id.txtCustomer);
        imgProduct = findViewById(R.id.imgProduct);
        txtProduct = findViewById(R.id.txtProduct);
        imgOrder = findViewById(R.id.imgOrder);
        txtOrder = findViewById(R.id.txtOrder);
        imgPaymentMethod = findViewById(R.id.imgPaymentMethod);
        txtPaymentMethod = findViewById(R.id.txtPaymentMethod);
        imgAdvancedProduct = findViewById(R.id.imgAdvancedProduct);
        txtAdvancedProduct = findViewById(R.id.txtAdvancedProduct);
    }

    private void addEvents() {
        setClickListener(imgAdvancedProduct, txtAdvancedProduct, this::openAdvancedProductManagementActivity);
        setClickListener(imgEmployee, txtEmployee, this::openEmployeeManagementActivity);
        setClickListener(imgCustomer, txtCustomer, this::openCustomerManagementActivity);
        setClickListener(imgProduct, txtProduct, this::openProductManagementActivity);
        setClickListener(imgPaymentMethod, txtPaymentMethod, this::openPaymentMethodActivity);
    }

    private void setClickListener(View imageView, View textView, Runnable action) {
        imageView.setOnClickListener(v -> action.run());
        textView.setOnClickListener(v -> action.run());
    }

    private void handleSystemBars() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void openAdvancedProductManagementActivity() {
        startActivity(new Intent(this, AdvancedProductManagementActivity.class));
    }

    private void openEmployeeManagementActivity() {
        startActivity(new Intent(this, EmployeeManagementActivity.class));
    }

    private void openCustomerManagementActivity() {
        startActivity(new Intent(this, CustomerManagementActivity.class));
    }

    private void openProductManagementActivity() {
        startActivity(new Intent(this, ProductManagementActivity.class));
    }

    private void openPaymentMethodActivity() {
        startActivity(new Intent(this, PaymentMethodActivity.class));
    }
}