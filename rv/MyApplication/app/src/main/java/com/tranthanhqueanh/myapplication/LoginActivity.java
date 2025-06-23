package com.tranthanhqueanh.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranthanhqueanh.myapplication.models.Account;
import com.tranthanhqueanh.myapplication.models.AccountList;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    CheckBox checkBoxRememberMe;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Set the content view first
        EdgeToEdge.enable(this);

        // Ánh xạ các thành phần UI
        editTextUsername = findViewById(R.id.edtUsername);
        editTextPassword = findViewById(R.id.edtPassword);
        checkBoxRememberMe = findViewById(R.id.chkRemember);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Tải thông tin đã lưu từ SharedPreferences (nếu có)
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("USERNAME", null);
        boolean isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false);

        if (savedUsername != null) {
            editTextUsername.setText(savedUsername);
            checkBoxRememberMe.setChecked(true);
        }

        // Xử lý sự kiện nhấn nút Đăng nhập
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Kiểm tra đầu vào không được để trống
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Xác thực thông tin đăng nhập với AccountList
                boolean loginSuccess = false;
                for (Account account : AccountList.getAccounts()) {
                    if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                        loginSuccess = true;
                        break;
                    }
                }

                if (loginSuccess) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    // Lưu thông tin nếu CheckBox được chọn
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (checkBoxRememberMe.isChecked()) {
                        editor.putString("USERNAME", username);
                        editor.putBoolean("IS_LOGGED_IN", true);
                    } else {
                        editor.remove("USERNAME");
                        editor.remove("IS_LOGGED_IN");
                    }
                    editor.apply();

                    // Chuyển sang Screen B (Danh sách sản phẩm)
                    Intent intent = new Intent(LoginActivity.this, ProductListActivity.class);
                    startActivity(intent);
                    finish(); // Đóng màn hình đăng nhập
                } else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
