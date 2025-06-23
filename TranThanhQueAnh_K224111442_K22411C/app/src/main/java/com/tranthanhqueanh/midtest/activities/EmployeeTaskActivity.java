package com.tranthanhqueanh.midtest.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranthanhqueanh.midtest.R;
import com.tranthanhqueanh.midtest.adapters.CustomerTaskDetailAdapter;
import com.tranthanhqueanh.midtest.connectors.TaskForTeleSalesConnector;
import com.tranthanhqueanh.midtest.models.Account;
import com.tranthanhqueanh.midtest.models.TaskForTeleSales;
import com.tranthanhqueanh.midtest.models.TaskForTeleSalesDetails;

import java.util.ArrayList;

public class EmployeeTaskActivity extends AppCompatActivity {

    private TextView txtEmployeeDashboardTitle;
    private TextView txtCurrentTaskTitle;
    private ListView lvCustomersAssigned;

    private CustomerTaskDetailAdapter customerTaskDetailAdapter;
    private ArrayList<TaskForTeleSalesDetails> currentTaskDetailsList;
    private TaskForTeleSalesConnector taskConnector;
    private Account loggedInEmployeeAccount;
    private TaskForTeleSales currentAssignedTask; // The single task assigned to the employee for the day

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_task);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the logged-in account from the intent
        loggedInEmployeeAccount = (Account) getIntent().getSerializableExtra("account");
        if (loggedInEmployeeAccount == null || loggedInEmployeeAccount.getTypeOfAccount() != 2) {
            Toast.makeText(this, "Unauthorized access. Please login as Employee.", Toast.LENGTH_LONG).show();
            finish(); // Close activity if not employee or account not passed
            return;
        }

        addViews();
        initData();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh task details whenever the activity resumes
        loadEmployeeTaskAndDetails();
    }

    private void addViews() {
        txtEmployeeDashboardTitle = findViewById(R.id.txtEmployeeDashboardTitle);
        txtCurrentTaskTitle = findViewById(R.id.txtCurrentTaskTitle);
        lvCustomersAssigned = findViewById(R.id.lvCustomersAssigned);
    }

    private void initData() {
        taskConnector = new TaskForTeleSalesConnector(this);
        currentTaskDetailsList = new ArrayList<>();
        customerTaskDetailAdapter = new CustomerTaskDetailAdapter(this, R.layout.item_employee_customer_detail, currentTaskDetailsList);
        lvCustomersAssigned.setAdapter(customerTaskDetailAdapter);

        loadEmployeeTaskAndDetails(); // Initial load
    }

    private void loadEmployeeTaskAndDetails() {
        // Get today's date in YYYY-MM-DD format
        String todayDate = taskConnector.getCurrentDate();

        // Get the most recent task for this employee for today
        currentAssignedTask = taskConnector.getMostRecentTaskForEmployeeByDate(loggedInEmployeeAccount.getId(), todayDate);

        if (currentAssignedTask != null) {
            txtCurrentTaskTitle.setText("Current Task: " + currentAssignedTask.getTaskTitle());
            // Load details for this task
            currentTaskDetailsList.clear();
            ArrayList<TaskForTeleSalesDetails> fetchedDetails = taskConnector.getTaskDetailsByTaskId(currentAssignedTask.getId());
            if (fetchedDetails != null) {
                currentTaskDetailsList.addAll(fetchedDetails);
            }
            customerTaskDetailAdapter.notifyDataSetChanged();

            if (currentTaskDetailsList.isEmpty()) {
                Toast.makeText(this, "No customers assigned to this task.", Toast.LENGTH_SHORT).show();
            }
        } else {
            txtCurrentTaskTitle.setText("No task assigned for today.");
            currentTaskDetailsList.clear();
            customerTaskDetailAdapter.notifyDataSetChanged();
            Toast.makeText(this, "No tasks assigned to you for today.", Toast.LENGTH_LONG).show();
        }
    }

    private void addEvents() {
        lvCustomersAssigned.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskForTeleSalesDetails clickedDetail = currentTaskDetailsList.get(position);

                if (clickedDetail.getIsCalled() == 0) { // Only allow calling if not already called
                    // Simulate making a call
                    String customerPhone = clickedDetail.getCustomer().getPhone();
                    Toast.makeText(EmployeeTaskActivity.this, getString(R.string.calling_customer, customerPhone), Toast.LENGTH_SHORT).show();

                    // Update IsCalled status to 1
                    boolean updated = taskConnector.updateTaskDetailIsCalled(clickedDetail.getId(), 1);
                    if (updated) {
                        clickedDetail.setIsCalled(1); // Update the model in memory
                        customerTaskDetailAdapter.notifyDataSetChanged(); // Refresh ListView

                        // Check if all customers for the current task are called
                        if (currentAssignedTask != null && taskConnector.areAllTaskDetailsCalled(currentAssignedTask.getId())) {
                            // If all are called, update the parent task's IsCompleted status
                            boolean taskCompleted = taskConnector.updateTaskIsCompleted(currentAssignedTask.getId(), 1);
                            if (taskCompleted) {
                                Toast.makeText(EmployeeTaskActivity.this, getString(R.string.task_completed_message), Toast.LENGTH_LONG).show();
                                currentAssignedTask.setIsCompleted(1); // Update task in memory
                                txtCurrentTaskTitle.setText("Current Task: " + currentAssignedTask.getTaskTitle() + " (COMPLETED)");
                            }
                        }
                    } else {
                        Toast.makeText(EmployeeTaskActivity.this, "Failed to mark customer as called.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EmployeeTaskActivity.this, "Customer already called.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
