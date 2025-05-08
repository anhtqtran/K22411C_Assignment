package com.tranthanhqueanh.k22411csampleproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tranthanhqueanh.models.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private ArrayList<Employee> employees;

    public EmployeeAdapter(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.name.setText(employee.getName());
        holder.email.setText(employee.getEmail());
        holder.phone.setText(employee.getPhone());
        holder.image.setImageResource(employee.getImage());  // Set the image
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, phone;
        ImageView image;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.employeeName);
            email = itemView.findViewById(R.id.employeeEmail);
            phone = itemView.findViewById(R.id.employeePhone);
            image = itemView.findViewById(R.id.employeeImage);
        }
    }
}