package com.tranthanhqueanh.k22411csampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView imgProduct;
    TextView txtProduct;

    ImageView imgCategory;
    TextView txtCategory;


    ImageView imgCustomer;
    TextView txtCustomer;
    ImageView imgEmployee;
    TextView txtEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        addViews();
        addEvents();

        // Handling window insets to adjust for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        imgEmployee.setOnClickListener(view -> openEmployeeManagementActivity());
        txtEmployee.setOnClickListener(view -> openEmployeeManagementActivity());

        imgCustomer.setOnClickListener(view -> openCustomerManagementActivity());
        txtCustomer.setOnClickListener(view -> openCustomerManagementActivity());

        imgProduct.setOnClickListener(view -> openProductManagementActivity());
        txtProduct.setOnClickListener(view -> openProductManagementActivity());

        imgCategory.setOnClickListener(view -> openCategoryManagementActivity());
        txtCategory.setOnClickListener(view -> openCategoryManagementActivity());
    }

    private void openEmployeeManagementActivity() {
        Intent intent = new Intent(MainActivity.this, EmployeeManagementActivity.class);
        startActivity(intent);
    }

    private void openCustomerManagementActivity() {
        Intent intent = new Intent(MainActivity.this, CustomerManagementActivity.class);
        startActivity(intent);
    }
    private void openProductManagementActivity() {
        Intent intent = new Intent(MainActivity.this, ProductManagementActivity.class);
        startActivity(intent);
    }

    private void openCategoryManagementActivity() {
        Intent intent = new Intent(MainActivity.this, CategoryManagementActivity.class);
        startActivity(intent);
    }


    private void addViews() {
        imgEmployee = findViewById(R.id.imgEmployee);
        txtEmployee = findViewById(R.id.txtEmployee);
        imgCustomer = findViewById(R.id.imgCustomer);
        txtCustomer = findViewById(R.id.txtCustomer);
        imgProduct = findViewById(R.id.imgProduct);
        txtProduct = findViewById(R.id.txtProduct);
        imgCategory = findViewById(R.id.imgCategory);
        txtCategory = findViewById(R.id.txtCategory);
    }
}
