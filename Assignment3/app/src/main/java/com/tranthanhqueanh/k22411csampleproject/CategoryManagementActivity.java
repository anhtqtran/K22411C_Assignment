package com.tranthanhqueanh.k22411csampleproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranthanhqueanh.connectors.CategoryConnector;
import com.tranthanhqueanh.models.Category;

public class CategoryManagementActivity extends AppCompatActivity {

    ListView lvCategory;
    ArrayAdapter<Category> adapter;
    CategoryConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvents();
    }

    private void addEvents() {
        lvCategory.setOnItemLongClickListener((parent, view, i, id) -> {
            Category selected = adapter.getItem(i);
            adapter.remove(selected);
            return false;
        });
    }

    private void addViews() {
        lvCategory = findViewById(R.id.lvCategory);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        connector = new CategoryConnector();
        adapter.addAll(connector.get_all_categories());
        lvCategory.setAdapter(adapter);
    }
}