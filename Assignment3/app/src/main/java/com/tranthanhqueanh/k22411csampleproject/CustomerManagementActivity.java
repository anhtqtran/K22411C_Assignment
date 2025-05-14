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

import com.tranthanhqueanh.connectors.CustomerConnector;
import com.tranthanhqueanh.models.Customer;

public class CustomerManagementActivity extends AppCompatActivity {

    ListView lvCustomer;
    ArrayAdapter<Customer> adapter;
    CustomerConnector connector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvents();
    }

    private void addEvents() {
        lvCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                Customer selected = adapter.getItem(i);
                adapter.remove(selected);
                return false;
            }
        });
    }

    private void addViews() {
        lvCustomer=findViewById(R.id.lvCustomer);
        adapter = new ArrayAdapter<>(CustomerManagementActivity.this, android.R.layout.simple_list_item_1);
        connector=new CustomerConnector();
        adapter.addAll(connector.get_all_customers());
        lvCustomer.setAdapter(adapter);
    }
}