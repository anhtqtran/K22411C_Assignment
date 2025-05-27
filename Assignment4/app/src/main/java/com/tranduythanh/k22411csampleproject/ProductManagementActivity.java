package com.tranduythanh.k22411csampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranduythanh.models.Category;
import com.tranduythanh.models.ListCategory;
import com.tranduythanh.models.Product;

public class ProductManagementActivity extends AppCompatActivity {
    Spinner spinnerCategory;
    ArrayAdapter<Category> adapterCategory;
    ListCategory listCategory;

    ListView lvProduct;
    ArrayAdapter<Product> adapterProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvents();
    }

    private void addEvents() {
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category c = adapterCategory.getItem(i);
                displayProductsByCategory(c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = adapterProduct.getItem(i);
                displayProductDetailActivity(product);
            }
        });
    }

    private void displayProductDetailActivity(Product product) {
        Intent intent = new Intent(ProductManagementActivity.this, ProductDetailActivity.class);
        if (product != null) {
            intent.putExtra("SELECTED_PRODUCT", product);
        }
        startActivity(intent);
    }

    private void displayProductsByCategory(Category c) {
        adapterProduct.clear();
        adapterProduct.addAll(c.getProducts());
    }

    private void addViews() {
        spinnerCategory = findViewById(R.id.spinnerCategory);
        adapterCategory = new ArrayAdapter<>(
                ProductManagementActivity.this,
                android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);

        listCategory = new ListCategory();
        listCategory.generate_sample_product_dataset();
        adapterCategory.addAll(listCategory.getCategories());

        lvProduct = findViewById(R.id.lvProduct);
        adapterProduct = new ArrayAdapter<>(
                ProductManagementActivity.this,
                android.R.layout.simple_list_item_1);
        lvProduct.setAdapter(adapterProduct);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();

        if (itemId == R.id.menu_new_product) {
            Toast.makeText(this, "Open screen to add new product", Toast.LENGTH_LONG).show();
            displayProductDetailActivity(null);
            return true;
        } else if (itemId == R.id.menu_sort_by_id) {
            Toast.makeText(this, "Sorting products by ID", Toast.LENGTH_LONG).show();
            if (selectedCategory != null) {
                selectedCategory.getProducts().sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
                adapterProduct.clear();
                adapterProduct.addAll(selectedCategory.getProducts());
                adapterProduct.notifyDataSetChanged();
            }
            return true;
        } else if (itemId == R.id.menu_sort_by_name) {
            Toast.makeText(this, "Sorting products by name", Toast.LENGTH_LONG).show();
            if (selectedCategory != null) {
                selectedCategory.getProducts().sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
                adapterProduct.clear();
                adapterProduct.addAll(selectedCategory.getProducts());
                adapterProduct.notifyDataSetChanged();
            }
            return true;
        } else if (itemId == R.id.menu_sort_by_price) {
            Toast.makeText(this, "Sorting products by price", Toast.LENGTH_LONG).show();
            if (selectedCategory != null) {
                selectedCategory.getProducts().sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
                adapterProduct.clear();
                adapterProduct.addAll(selectedCategory.getProducts());
                adapterProduct.notifyDataSetChanged();
            }
            return true;
        } else if (itemId == R.id.menu_sort_by_quantity) {
            Toast.makeText(this, "Sorting products by quantity", Toast.LENGTH_LONG).show();
            if (selectedCategory != null) {
                selectedCategory.getProducts().sort((p1, p2) -> Integer.compare(p1.getQuantity(), p2.getQuantity()));
                adapterProduct.clear();
                adapterProduct.addAll(selectedCategory.getProducts());
                adapterProduct.notifyDataSetChanged();
            }
            return true;
        } else if (itemId == R.id.menu_filter_by_category) {
            Toast.makeText(this, "Filtering products by category", Toast.LENGTH_LONG).show();
            // Already handled by spinnerCategory, could prompt for category selection
            return true;
        } else if (itemId == R.id.menu_broadcast_advertising) {
            Toast.makeText(this, "Sending bulk advertising to customers", Toast.LENGTH_LONG).show();
            return true;
        } else if (itemId == R.id.menu_help) {
            Toast.makeText(this, "Opening help website", Toast.LENGTH_LONG).show();
            // Could open a browser with a help URL
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}