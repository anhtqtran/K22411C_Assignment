package com.tranduythanh.k22411csampleproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.concurrent.Executors;

public class CustomerManagementActivity extends AppCompatActivity {
    private ListView lvCustomer;
    private ArrayAdapter<Customer> adapter;
    private CustomerConnector connector;

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

    private void addViews() {
        lvCustomer = findViewById(R.id.lvCustomer);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvCustomer.setAdapter(adapter);

        Executors.newSingleThreadExecutor().execute(() -> {
            connector = new CustomerConnector();
            SQLiteDatabase database = new SQLiteConnector(this).openDatabase();
            try {
                ListCustomer lc = connector.getAllCustomers(database);
                runOnUiThread(() -> {
                    adapter.addAll(lc.getCustomers());
                    adapter.notifyDataSetChanged();
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Lỗi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show());
            } finally {
                database.close();
            }
        });
    }

    private void addEvents() {
        lvCustomer.setOnItemClickListener((parent, view, position, id) -> {
            Customer c = adapter.getItem(position);
            displayCustomerDetailActivity(c);
        });

        lvCustomer.setOnItemLongClickListener((parent, view, position, id) -> {
            Customer c = adapter.getItem(position);
            displayCustomerDetailActivity(c);
            return true;
        });
    }

    private void displayCustomerDetailActivity(Customer c) {
        Intent intent = new Intent(this, CustomerDetailActivity.class);
        intent.putExtra("SELECTED_CUSTOMER", c);
        startActivityForResult(intent, 400);
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
            Intent intent = new Intent(this, CustomerDetailActivity.class);
            startActivityForResult(intent, 300);
            return true;
        } else if (item.getItemId() == R.id.menu_broadcast_advertising) {
            Toast.makeText(this, "Gửi quảng cáo hàng loạt tới khách hàng", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.menu_help) {
            Toast.makeText(this, "Mở Website hướng dẫn sử dụng", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;

        if (requestCode == 300 && resultCode == 500) {
            Customer c = (Customer) data.getSerializableExtra("NEW_CUSTOMER");
            if (c != null) {
                process_save_new_customer(c);
            }
        } else if (requestCode == 400 && resultCode == 500) {
            Customer c = (Customer) data.getSerializableExtra("NEW_CUSTOMER");
            if (c != null) {
                process_save_update_customer(c);
            }
        } else if (requestCode == 400 && resultCode == 600) {
            String id = data.getStringExtra("REMOVE_CUSTOMER_ID");
            if (id != null) {
                process_remove_customer(Integer.parseInt(id));
            }
        }
    }

    private void process_save_new_customer(Customer c) {
        Executors.newSingleThreadExecutor().execute(() -> {
            SQLiteDatabase database = new SQLiteConnector(this).openDatabase();
            try {
                database.beginTransaction();
                CustomerConnector cc = new CustomerConnector();
                long flag = cc.saveNewCustomer(c, database);
                if (flag > 0) {
                    ListCustomer lc = cc.getAllCustomers(database);
                    runOnUiThread(() -> {
                        adapter.clear();
                        adapter.addAll(lc.getCustomers());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
                    });
                    database.setTransactionSuccessful();
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Thêm khách hàng thất bại", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
            } finally {
                database.endTransaction();
                database.close();
            }
        });
    }

    private void process_save_update_customer(Customer c) {
        Executors.newSingleThreadExecutor().execute(() -> {
            SQLiteDatabase database = new SQLiteConnector(this).openDatabase();
            try {
                database.beginTransaction();
                CustomerConnector cc = new CustomerConnector();
                long flag = cc.saveUpdateCustomer(c, database);
                if (flag > 0) {
                    runOnUiThread(() -> {
                        int index = -1;
                        for (int i = 0; i < adapter.getCount(); i++) {
                            if (adapter.getItem(i).getId() == c.getId()) {
                                index = i;
                                break;
                            }
                        }
                        if (index >= 0) {
                            adapter.remove(adapter.getItem(index));
                            adapter.insert(c, index);
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Cập nhật khách hàng thành công", Toast.LENGTH_SHORT).show();
                    });
                    database.setTransactionSuccessful();
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Cập nhật khách hàng thất bại: ID không tồn tại", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
            } finally {
                database.endTransaction();
                database.close();
            }
        });
    }

    private void process_remove_customer(int id) {
        Executors.newSingleThreadExecutor().execute(() -> {
            SQLiteDatabase database = new SQLiteConnector(this).openDatabase();
            try {
                database.beginTransaction();
                CustomerConnector cc = new CustomerConnector();
                long flag = cc.removeCustomer(id, database);
                if (flag > 0) {
                    ListCustomer lc = cc.getAllCustomers(database);
                    runOnUiThread(() -> {
                        adapter.clear();
                        adapter.addAll(lc.getCustomers());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Xóa khách hàng thành công", Toast.LENGTH_SHORT).show();
                    });
                    database.setTransactionSuccessful();
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Xóa khách hàng thất bại", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show());
            } finally {
                database.endTransaction();
                database.close();
            }
        });
    }
}