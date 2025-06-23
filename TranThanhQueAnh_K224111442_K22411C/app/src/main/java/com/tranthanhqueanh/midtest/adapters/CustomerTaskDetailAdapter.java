package com.tranthanhqueanh.midtest.adapters;

import android.app.Activity;
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
import com.tranthanhqueanh.midtest.models.TaskForTeleSalesDetails; // This now holds customer info

import java.util.List;

// Adapter for displaying TaskForTeleSalesDetails (which includes customer info) in a ListView.
public class CustomerTaskDetailAdapter extends ArrayAdapter<TaskForTeleSalesDetails> {
    private Activity context;
    private int resource;

    public CustomerTaskDetailAdapter(@NonNull Activity context, int resource, @NonNull List<TaskForTeleSalesDetails> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(this.resource, parent, false);
            holder = new ViewHolder();
            holder.txtCustomerName = convertView.findViewById(R.id.txtCustomerName);
            holder.txtCustomerPhone = convertView.findViewById(R.id.txtCustomerPhone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TaskForTeleSalesDetails detail = getItem(position);

        if (detail != null && detail.getCustomer() != null) {
            holder.txtCustomerName.setText(detail.getCustomer().getName());
            holder.txtCustomerPhone.setText(detail.getCustomer().getPhone());

            // Conditional Styling: If IsCalled is 0 (False), set background to yellow
            if (detail.getIsCalled() == 0) {
                convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.customer_not_called_yellow));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT); // Default background
            }
        }

        return convertView;
    }

    static class ViewHolder {
        TextView txtCustomerName;
        TextView txtCustomerPhone;
    }
}
