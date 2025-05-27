package com.tranduythanh.k22411csampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranduythanh.models.Product;

public class ProductDetailActivity extends AppCompatActivity {

    EditText edt_product_id;
    EditText edt_product_name;
    EditText edt_product_quantity;
    EditText edt_product_price;
    EditText edt_product_category;
    EditText edt_product_description;
    EditText edt_product_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        displayInfo();
    }

    private void addViews() {
        edt_product_id = findViewById(R.id.edt_product_id);
        edt_product_name = findViewById(R.id.edt_product_name);
        edt_product_quantity = findViewById(R.id.edt_product_quantity);
        edt_product_price = findViewById(R.id.edt_product_price);
        edt_product_category = findViewById(R.id.edt_product_category);
        edt_product_description = findViewById(R.id.edt_product_description);
        edt_product_image = findViewById(R.id.edt_product_image);
    }

    private void displayInfo() {
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("SELECTED_PRODUCT");
        if (product == null) return;

        edt_product_id.setText(String.valueOf(product.getId()));
        edt_product_name.setText(product.getName());
        edt_product_quantity.setText(String.valueOf(product.getQuantity()));
        edt_product_price.setText(String.valueOf(product.getPrice()));
        edt_product_category.setText(String.valueOf(product.getCate_id()));
        edt_product_description.setText(product.getDescription());
        edt_product_image.setText(String.valueOf(product.getImage_id()));
    }
}