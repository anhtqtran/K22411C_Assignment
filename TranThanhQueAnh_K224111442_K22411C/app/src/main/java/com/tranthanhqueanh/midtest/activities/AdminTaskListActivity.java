package com.tranthanhqueanh.midtest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranthanhqueanh.midtest.R;
import com.tranthanhqueanh.midtest.adapters.TaskAdapter;
import com.tranthanhqueanh.midtest.connectors.TaskForTeleSalesConnector;
import com.tranthanhqueanh.midtest.models.Account;
import com.tranthanhqueanh.midtest.models.TaskForTeleSales;

import java.util.ArrayList;

public class AdminTaskListActivity extends AppCompatActivity {

    private TextView txtAdminDashboardTitle;
    private ListView lvAdminTasks;
    private Button btnCreateNewTask;

    private TaskAdapter taskAdapter;
    private ArrayList<TaskForTeleSales> taskList;
    private TaskForTeleSalesConnector taskConnector;
    private Account loggedInAccount; // To store the logged-in admin's account

    // Request code for CreateTaskActivity
    private static final int CREATE_TASK_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_task_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the logged-in account from the intent
        loggedInAccount = (Account) getIntent().getSerializableExtra("account");
        if (loggedInAccount == null || loggedInAccount.getTypeOfAccount() != 1) {
            Toast.makeText(this, "Unauthorized access. Please login as Admin.", Toast.LENGTH_LONG).show();
            finish(); // Close activity if not admin or account not passed
            return;
        }

        addViews();
        initData();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh tasks whenever the activity resumes (e.g., after creating a new task)
        loadTasks();
    }

    private void addViews() {
        txtAdminDashboardTitle = findViewById(R.id.txtAdminDashboardTitle);
        lvAdminTasks = findViewById(R.id.lvAdminTasks);
        btnCreateNewTask = findViewById(R.id.btnCreateNewTask);
    }

    private void initData() {
        taskConnector = new TaskForTeleSalesConnector(this);
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, R.layout.item_admin_task, taskList);
        lvAdminTasks.setAdapter(taskAdapter);

        loadTasks(); // Initial load
    }

    private void loadTasks() {
        taskList.clear();
        ArrayList<TaskForTeleSales> fetchedTasks = taskConnector.getAllTasks();
        if (fetchedTasks != null) {
            taskList.addAll(fetchedTasks);
        }
        taskAdapter.notifyDataSetChanged();
        if (taskList.isEmpty()) {
            Toast.makeText(this, "No tasks available. Create a new one!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addEvents() {
        btnCreateNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminTaskListActivity.this, CreateTaskActivity.class);
                // Pass the admin's account ID if needed in CreateTaskActivity for logging
                intent.putExtra("adminAccountId", loggedInAccount.getId());
                startActivityForResult(intent, CREATE_TASK_REQUEST_CODE);
            }
        });

        // Optional: Handle item clicks for detailed view or editing tasks (not explicitly required by Q4)
        lvAdminTasks.setOnItemClickListener((parent, view, position, id) -> {
            TaskForTeleSales selectedTask = taskList.get(position);
            Toast.makeText(AdminTaskListActivity.this, "Task selected: " + selectedTask.getTaskTitle(), Toast.LENGTH_SHORT).show();
            // Implement navigation to task detail screen if needed
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            // Task was created successfully, refresh the list
            Toast.makeText(this, getString(R.string.task_creation_success), Toast.LENGTH_SHORT).show();
            loadTasks();
        }
    }
}
