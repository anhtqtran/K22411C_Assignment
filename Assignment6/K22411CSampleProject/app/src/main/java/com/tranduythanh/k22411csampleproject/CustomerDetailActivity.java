package com.tranduythanh.k22411csampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranduythanh.models.Customer;

public class CustomerDetailActivity extends AppCompatActivity {
    private TextView txt_customer_id;
    private EditText edt_customer_id;
    private EditText edt_customer_name;
    private EditText edt_customer_phone;
    private EditText edt_customer_email;
    private EditText edt_customer_username;
    private EditText edt_customer_password;
    private Button btnNew;
    private Button btnSave;
    private Button btnRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvent();
    }

    private void addViews() {
        txt_customer_id = findViewById(R.id.edt_customer_id);
        edt_customer_id = findViewById(R.id.edt_customer_id);
        edt_customer_name = findViewById(R.id.edt_customer_name);
        edt_customer_phone = findViewById(R.id.edt_customer_phone);
        edt_customer_email = findViewById(R.id.edt_customer_email);
        edt_customer_username = findViewById(R.id.edt_customer_username);
        edt_customer_password = findViewById(R.id.edt_customer_password);
        btnNew = findViewById(R.id.btnNew);
        btnSave = findViewById(R.id.btnSave);
        btnRemove = findViewById(R.id.btnRemove);

        display_infor();
    }

    private void addEvent() {
        btnSave.setOnClickListener(v -> process_save_customer());
        btnRemove.setOnClickListener(v -> process_remove_customer());
        btnNew.setOnClickListener(v -> {
            edt_customer_id.setText("");
            edt_customer_name.setText("");
            edt_customer_phone.setText("");
            edt_customer_email.setText("");
            edt_customer_username.setText("");
            edt_customer_password.setText("");
        });
    }

    private void display_infor() {
        Intent intent = getIntent();
        Customer c = (Customer) intent.getSerializableExtra("SELECTED_CUSTOMER");
        if (c == null) {
            txt_customer_id.setVisibility(View.GONE);
            edt_customer_id.setVisibility(View.GONE);
            return;
        }
        edt_customer_id.setText(String.valueOf(c.getId()));
        edt_customer_name.setText(c.getName());
        edt_customer_phone.setText(c.getPhone());
        edt_customer_email.setText(c.getEmail());
        edt_customer_username.setText(c.getUsername());
        edt_customer_password.setText(c.getPassword());
    }

    private void process_save_customer() {
        // Validate input
        String name = edt_customer_name.getText().toString().trim();
        String phone = edt_customer_phone.getText().toString().trim();
        String email = edt_customer_email.getText().toString().trim();
        String username = edt_customer_username.getText().toString().trim();
        String password = edt_customer_password.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Customer c = new Customer();
        String id = edt_customer_id.getText().toString().trim();
        if (!id.isEmpty()) {
            try {
                c.setId(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "ID không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        c.setName(name);
        c.setPhone(phone);
        c.setEmail(email);
        c.setUsername(username);
        c.setPassword(password);

        Intent intent = new Intent();
        intent.putExtra("NEW_CUSTOMER", c);
        setResult(500, intent);
        finish();
    }

    private void process_remove_customer() {
        String id = edt_customer_id.getText().toString().trim();
        if (id.isEmpty()) {
            Toast.makeText(this, "Không thể xóa: ID trống", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("REMOVE_CUSTOMER_ID", id);
        setResult(600, intent);
        finish();
    }
}