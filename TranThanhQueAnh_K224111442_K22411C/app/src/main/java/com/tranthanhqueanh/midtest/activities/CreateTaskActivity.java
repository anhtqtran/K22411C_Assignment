package com.tranthanhqueanh.midtest.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranthanhqueanh.midtest.R;
import com.tranthanhqueanh.midtest.connectors.AccountConnector;
import com.tranthanhqueanh.midtest.connectors.CustomerConnector;
import com.tranthanhqueanh.midtest.connectors.TaskForTeleSalesConnector;
import com.tranthanhqueanh.midtest.models.Account;
import com.tranthanhqueanh.midtest.models.Customer;
import com.tranthanhqueanh.midtest.models.TaskForTeleSales;
import com.tranthanhqueanh.midtest.models.TaskForTeleSalesDetails;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CreateTaskActivity extends AppCompatActivity {

    private EditText edtTaskTitle;
    private Spinner spinnerAssignTo;
    private Button btnSelectCustomers;
    private TextView txtSelectedCustomers;
    private Button btnCreateTask;

    private AccountConnector accountConnector;
    private CustomerConnector customerConnector;
    private TaskForTeleSalesConnector taskConnector;

    private ArrayAdapter<Account> employeeAdapter;
    private ArrayList<Account> employees;
    private Account selectedEmployee; // Store the selected employee from spinner
    private ArrayList<Customer> selectedCustomers; // Store the 5 random customers

    private int adminAccountId; // ID of the admin creating the task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_task);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adminAccountId = getIntent().getIntExtra("adminAccountId", -1);
        if (adminAccountId == -1) {
            Toast.makeText(this, "Admin account not found. Cannot create task.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        addViews();
        initData();
        addEvents();
    }

    private void addViews() {
        edtTaskTitle = findViewById(R.id.edtTaskTitle);
        spinnerAssignTo = findViewById(R.id.spinnerAssignTo);
        btnSelectCustomers = findViewById(R.id.btnSelectCustomers);
        txtSelectedCustomers = findViewById(R.id.txtSelectedCustomers);
        btnCreateTask = findViewById(R.id.btnCreateTask);
    }

    private void initData() {
        accountConnector = new AccountConnector(this);
        customerConnector = new CustomerConnector(this);
        taskConnector = new TaskForTeleSalesConnector(this);

        // Populate employee spinner
        employees = accountConnector.getAllEmployees();
        employeeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, employees);
        employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAssignTo.setAdapter(employeeAdapter);

        // Set initial spinner selection (if any employees exist)
        if (!employees.isEmpty()) {
            selectedEmployee = employees.get(0); // Select first employee by default
        } else {
            Toast.makeText(this, "No employees found. Please add employees first.", Toast.LENGTH_LONG).show();
            // Optionally disable create task button
        }
    }

    private void addEvents() {
        spinnerAssignTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEmployee = (Account) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedEmployee = null;
            }
        });

        btnSelectCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRandomCustomers();
            }
        });

        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTask();
            }
        });
    }

    private void selectRandomCustomers() {
        selectedCustomers = customerConnector.getRandomCustomers(5); // Get 5 random customers
        if (selectedCustomers.isEmpty()) {
            txtSelectedCustomers.setText("No customers found to select.");
            Toast.makeText(this, "No customers available in database.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder customerListText = new StringBuilder();
        customerListText.append("Selected Customers:\n");
        for (int i = 0; i < selectedCustomers.size(); i++) {
            Customer c = selectedCustomers.get(i);
            customerListText.append(i + 1).append(". ").append(c.getName()).append(" (").append(c.getPhone()).append(")\n");
        }
        txtSelectedCustomers.setText(customerListText.toString());
        Toast.makeText(this, selectedCustomers.size() + " customers selected.", Toast.LENGTH_SHORT).show();
    }

    private void createNewTask() {
        String taskTitle = edtTaskTitle.getText().toString().trim();

        if (taskTitle.isEmpty()) {
            Toast.makeText(this, "Please enter a task title.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedEmployee == null) {
            Toast.makeText(this, "Please select an employee to assign the task to.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedCustomers == null || selectedCustomers.isEmpty()) {
            Toast.makeText(this, getString(R.string.no_customers_selected), Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current system date
        String currentDate = taskConnector.getCurrentDate();

        // Create TaskForTeleSales object
        TaskForTeleSales newTask = new TaskForTeleSales();
        newTask.setAccountId(selectedEmployee.getId());
        newTask.setTaskTitle(taskTitle);
        newTask.setDateAssigned(currentDate);
        newTask.setIsCompleted(0); // New task is not completed by default

        // Insert task into database
        long taskId = taskConnector.insertTask(newTask);

        if (taskId != -1) {
            // Insert task details for each selected customer
            boolean allDetailsInserted = true;
            for (Customer customer : selectedCustomers) {
                TaskForTeleSalesDetails detail = new TaskForTeleSalesDetails();
                detail.setTaskForTeleSalesId((int) taskId); // Cast long to int, assuming ID fits
                detail.setCustomerId(customer.getId());
                detail.setIsCalled(0); // Not called by default

                long detailId = taskConnector.insertTaskDetail(detail);
                if (detailId == -1) {
                    allDetailsInserted = false;
                    Log.e("CreateTaskActivity", "Failed to insert detail for customer ID: " + customer.getId());
                }
            }

            if (allDetailsInserted) {
                setResult(Activity.RESULT_OK); // Indicate success
                finish(); // Go back to AdminTaskListActivity
            } else {
                Toast.makeText(this, getString(R.string.task_creation_failure) + " (details issue)", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.task_creation_failure), Toast.LENGTH_LONG).show();
        }
    }
}
