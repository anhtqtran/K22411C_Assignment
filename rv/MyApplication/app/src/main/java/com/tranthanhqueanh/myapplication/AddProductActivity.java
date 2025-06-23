package com.tranthanhqueanh.myapplication;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranthanhqueanh.myapplication.models.Products;
import com.tranthanhqueanh.myapplication.models.ProductList;
public class AddProductActivity extends AppCompatActivity {
    private EditText edtProductCode, edtProductName, edtUnitPrice, edtImageLink;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);

        edtProductCode = findViewById(R.id.edtProductCode);
        edtProductName = findViewById(R.id.edtProductName);
        edtUnitPrice = findViewById(R.id.edtUnitPrice);
        edtImageLink = findViewById(R.id.edtImageLink);
        btnSave = findViewById(R.id.btnSave);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productCode = edtProductCode.getText().toString().trim();
                String productName = edtProductName.getText().toString().trim();
                String unitPriceStr = edtUnitPrice.getText().toString().trim();
                String imageLink = edtImageLink.getText().toString().trim();

                // Kiểm tra dữ liệu đầu vào
                if (productCode.isEmpty() || productName.isEmpty() || unitPriceStr.isEmpty() || imageLink.isEmpty()) {
                    Toast.makeText(AddProductActivity.this, R.string.alert, LENGTH_SHORT).show();
                    return;
                }

                try {
                    double unitPrice = Double.parseDouble(unitPriceStr);
                    int id = ProductList.getProducts().size() + 1; // tạo id đơn giản
                    Products newProduct = new Products(id, productCode, productName, unitPrice, imageLink);
                    ProductList.addProduct(newProduct);
                    finish(); // đóng activity trở về màn hình trước
                } catch (NumberFormatException e) {
                    Toast.makeText(AddProductActivity.this, R.string.alprice, LENGTH_SHORT).show();
                }
            }
        });
    }
}