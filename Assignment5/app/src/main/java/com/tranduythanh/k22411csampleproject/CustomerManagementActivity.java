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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranduythanh.connectors.CustomerConnector;
import com.tranduythanh.connectors.SQLiteConnector;
import com.tranduythanh.models.Customer;
import com.tranduythanh.models.ListCustomer;

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
        lvCustomer.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Customer c = adapter.getItem(i);
            displayCustomerDetailActivity(c);
            return false;
        });
    }

    private void displayCustomerDetailActivity(Customer c) {
        Intent intent = new Intent(CustomerManagementActivity.this, CustomerDetailActivity.class);
        intent.putExtra("SELECTED_CUSTOMER", c);
        startActivity(intent);
    }

    private void addViews() {
        lvCustomer = findViewById(R.id.lvCustomer);
        adapter = new ArrayAdapter<>(CustomerManagementActivity.this, android.R.layout.simple_list_item_1);
        connector = new CustomerConnector();

        // Fixing the issue: you need to get customers from the database and update the list
        SQLiteConnector sqLiteConnector = new SQLiteConnector(this);
        sqLiteConnector.openDatabase();

        ListCustomer lc = connector.getAllCustomer(sqLiteConnector.getDatabase()); // Correct method to get customers
        adapter.addAll(lc.getCustomers()); // Add the customers from the list
        lvCustomer.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_customer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_new_customer) {
            Toast.makeText(CustomerManagementActivity.this, "Mở màn hình thêm mới khách hàng", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CustomerManagementActivity.this, CustomerDetailActivity.class);
            startActivityForResult(intent, 300);
        } else if (item.getItemId() == R.id.menu_broadcast_advertising) {
            Toast.makeText(CustomerManagementActivity.this, "Gửi quảng cáo hàng loạt tới khách hàng", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.menu_help) {
            Toast.makeText(CustomerManagementActivity.this, "Mở Website hướng dẫn sử dụng", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == 500) {
            Customer c = (Customer) data.getSerializableExtra("NEW_CUSTOMER");
            process_save_customer(c);
        }
    }

    private void process_save_customer(Customer c) {
        boolean result = connector.isExist(c);
        if (result) {
            // Customer exists, handle update logic here (if needed)
        } else {
            connector.addCustomer(c);
            adapter.clear();  // Clear old data
            adapter.addAll(connector.get_all_customers());  // Add updated list
        }
    }
}
