package com.tranthanhqueanh.midtest.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranthanhqueanh.midtest.R;

public class AboutActivity extends AppCompatActivity {

    private ImageView imgAvatar;
    private TextView txtStudentId, txtStudentName, txtStudentEmail, txtStudentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addViews();
        displayStudentInfo();
    }

    private void addViews() {
        imgAvatar = findViewById(R.id.imgAvatar);
        txtStudentId = findViewById(R.id.txtStudentId);
        txtStudentName = findViewById(R.id.txtStudentName);
        txtStudentEmail = findViewById(R.id.txtStudentEmail);
        txtStudentClass = findViewById(R.id.txtStudentClass);
    }

    private void displayStudentInfo() {
        // Set avatar image (ensure 'my_avatar' is in your res/drawable folder)
        imgAvatar.setImageResource(R.drawable.my_avatar); // Replace with your image resource ID

        // Set student information from strings.xml
        txtStudentId.setText(getString(R.string.student_id_label) + " " + getString(R.string.student_id_value));
        txtStudentName.setText(getString(R.string.student_name_label) + " " + getString(R.string.student_name_value));
        txtStudentEmail.setText(getString(R.string.student_email_label) + " " + getString(R.string.student_email_value));
        txtStudentClass.setText(getString(R.string.student_class_label) + " " + getString(R.string.student_class_value));
    }
}
