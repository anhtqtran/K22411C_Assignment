package com.tranthanhqueanh.midtest.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.tranthanhqueanh.midtest.R;
import com.tranthanhqueanh.midtest.models.Account;
import com.tranthanhqueanh.midtest.models.TaskForTeleSales;
import com.tranthanhqueanh.midtest.connectors.AccountConnector;

import java.util.List;

// Adapter for displaying TaskForTeleSales objects in a ListView.
public class TaskAdapter extends ArrayAdapter<TaskForTeleSales> {
    private Activity context;
    private int resource;
    private AccountConnector accountConnector;

    public TaskAdapter(@NonNull Activity context, int resource, @NonNull List<TaskForTeleSales> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.accountConnector = new AccountConnector(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Use a ViewHolder pattern for better performance
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(this.resource, parent, false);
            holder = new ViewHolder();
            holder.txtTaskTitle = convertView.findViewById(R.id.txtTaskTitle);
            holder.txtDateAssigned = convertView.findViewById(R.id.txtDateAssigned);
            holder.txtAssignedTo = convertView.findViewById(R.id.txtAssignedTo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TaskForTeleSales task = getItem(position);

        if (task != null) {
            holder.txtTaskTitle.setText(task.getTaskTitle());
            holder.txtDateAssigned.setText("Assigned: " + task.getDateAssigned());

            // Get the employee's username
            Account employee = accountConnector.getAccountById(task.getAccountId());
            if (employee != null) {
                holder.txtAssignedTo.setText("To: " + employee.getUsername());
            } else {
                holder.txtAssignedTo.setText("To: Unknown Employee");
            }

            // Conditional Styling: If IsCompleted is 1 (True), set background to green
            if (task.getIsCompleted() == 1) {
                convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.task_completed_green));
            } else {
                // Reset to default background if not completed (important for recycled views)
                convertView.setBackgroundColor(Color.TRANSPARENT); // Or a specific default color
            }
        }

        return convertView;
    }

    // ViewHolder for efficient view recycling
    static class ViewHolder {
        TextView txtTaskTitle;
        TextView txtDateAssigned;
        TextView txtAssignedTo;
    }
}
