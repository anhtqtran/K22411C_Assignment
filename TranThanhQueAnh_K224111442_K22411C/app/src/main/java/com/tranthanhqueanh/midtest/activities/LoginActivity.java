package com.tranthanhqueanh.midtest.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranthanhqueanh.midtest.R;
import com.tranthanhqueanh.midtest.connectors.AccountConnector;
import com.tranthanhqueanh.midtest.connectors.SQLiteConnector;
import com.tranthanhqueanh.midtest.models.Account;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername;
    EditText edtPassword;
    RadioGroup rgRole;
    RadioButton rbAdmin, rbEmployee;
    Button btnLogin;

    String DATABASE_NAME = "SalesDatabase.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";

    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ROLE = "role"; // 1 for Admin, 2 for Employee

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login); // Ensure this points to your new login layout

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addViews();
        addEvents();

        // Check and copy database on first run
        processCopyDatabase();
        // Load saved login info
        loadLoginInfo();
    }

    private void addViews() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        rgRole = findViewById(R.id.rgRole);
        rbAdmin = findViewById(R.id.rbAdmin);
        rbEmployee = findViewById(R.id.rbEmployee);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        int selectedRoleId = rgRole.getCheckedRadioButtonId();
        int typeOfAccount = -1; // Default invalid type

        if (selectedRoleId == R.id.rbAdmin) {
            typeOfAccount = 1; // Admin
        } else if (selectedRoleId == R.id.rbEmployee) {
            typeOfAccount = 2; // Employee
        }

        if (username.isEmpty() || password.isEmpty() || typeOfAccount == -1) {
            Toast.makeText(this, "Please enter username, password, and select a role.", Toast.LENGTH_SHORT).show();
            return;
        }

        AccountConnector accountConnector = new AccountConnector(this);
        Account authenticatedAccount = accountConnector.authenticateUser(username, password, typeOfAccount);

        if (authenticatedAccount != null) {
            saveLoginInfo(username, password, typeOfAccount); // Save login info on successful login

            if (authenticatedAccount.getTypeOfAccount() == 1) { // Admin
                Toast.makeText(this, getString(R.string.login_success_admin), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, AdminTaskListActivity.class);
                intent.putExtra("account", authenticatedAccount); // Pass the entire account object
                startActivity(intent);
                finish(); // Close login activity
            } else { // Employee
                Toast.makeText(this, getString(R.string.login_success_employee), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, EmployeeTaskActivity.class);
                intent.putExtra("account", authenticatedAccount); // Pass the entire account object
                startActivity(intent);
                finish(); // Close login activity
            }
        } else {
            Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLoginInfo(String username, String password, int role) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putInt(KEY_ROLE, role);
        editor.apply();
    }

    private void loadLoginInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_USERNAME, "");
        String password = sharedPreferences.getString(KEY_PASSWORD, "");
        int role = sharedPreferences.getInt(KEY_ROLE, 1); // Default to admin

        edtUsername.setText(username);
        edtPassword.setText(password);
        if (role == 1) {
            rbAdmin.setChecked(true);
        } else {
            rbEmployee.setChecked(true);
        }
    }


    // --- Database Copy Logic (from your original file, slightly adjusted) ---
    private void processCopyDatabase() {
        File dbFile = getApplicationContext().getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Database copied successfully from Assets.", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error copying database: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("LoginActivity", "Error copying database: " + e.getMessage());
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset() throws IOException {
        InputStream myInput = getAssets().open(DATABASE_NAME);
        String outFileName = getDatabasePath();

        // Create the /databases/ directory if it doesn't exist
        File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists()) {
            f.mkdir();
        }

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    // --- Menu for "About" (Q7) ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            Intent intent = new Intent(LoginActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
