package com.tranduythanh.k22411csampleproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranduythanh.connectors.EmployeeConnector;
import com.tranduythanh.connectors.SQLiteConnector;
import com.tranduythanh.models.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName;
    EditText edtPassword;
    CheckBox chkSaveLogin;

    String DATABASE_NAME = "SalesDatabase.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;

    private boolean doubleBackToExitPressedOnce = false;
    private static final long DOUBLE_BACK_PRESS_THRESHOLD = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        processCopy();
    }

    private void addViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        chkSaveLogin = findViewById(R.id.chkSaveLoginInfor);
    }

    public void do_login(View view) {
        String usr = edtUserName.getText().toString();
        String pwd = edtPassword.getText().toString();
        EmployeeConnector ec = new EmployeeConnector();

        SQLiteConnector sqLiteConnector = new SQLiteConnector(this);
        sqLiteConnector.openDatabase();
        Employee emp = ec.login(sqLiteConnector.getDatabase(), usr, pwd);

        if (emp != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Login failed - Please check your account again!", Toast.LENGTH_LONG).show();
        }
    }

    public void do_exit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        Resources res = getResources();
        builder.setTitle(res.getText(R.string.confirm_exit_title));
        builder.setMessage(res.getText(R.string.confirm_exit_message));
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton(res.getText(R.string.confirm_exit_yes), (dialog, which) -> finish());
        builder.setNegativeButton(res.getText(R.string.confirm_exit_NO), (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (doubleBackToExitPressedOnce) {
                finish();
                return true;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, DOUBLE_BACK_PRESS_THRESHOLD);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void saveLoginInformation() {
        SharedPreferences preferences = getSharedPreferences("LOGIN_INFORMATION", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String usr = edtUserName.getText().toString();
        String pwd = edtPassword.getText().toString();
        boolean isSave = chkSaveLogin.isChecked();
        editor.putString("USERNAME", usr);
        editor.putString("PASSWORD", pwd);
        editor.putBoolean("SAVED", isSave);
        editor.apply(); // Use apply() instead of commit() for better performance
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLoginInformation();
    }

    public void restoreLoginInformation() {
        SharedPreferences preferences = getSharedPreferences("LOGIN_INFORMATION", MODE_PRIVATE);
        String usr = preferences.getString("USERNAME", "");
        String pwd = preferences.getString("PASSWORD", "");
        boolean isSave = preferences.getBoolean("SAVED", true);
        if (isSave) {
            edtUserName.setText(usr);
            edtPassword.setText(pwd);
            chkSaveLogin.setChecked(isSave);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreLoginInformation();
    }

    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                InputStream inputStream = getAssets().open(DATABASE_NAME);
                File dbDirectory = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
                if (!dbDirectory.exists()) {
                    dbDirectory.mkdirs();
                }

                OutputStream outputStream = new FileOutputStream(dbFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error copying database", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
