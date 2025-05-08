package com.tranthanhqueanh.k22411csampleproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tranthanhqueanh.models.ListEmployee;

public class EmployeeManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_management);

        // Prepare data from ListEmployee
        ListEmployee listEmployee = new ListEmployee();
        listEmployee.gen_dataset();

        // Find RecyclerView
        RecyclerView recyclerView = findViewById(R.id.employeeRecyclerView);

        // Set up the Adapter with the data
        EmployeeAdapter adapter = new EmployeeAdapter(listEmployee.getEmployees());

        // Set the RecyclerView LayoutManager and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}