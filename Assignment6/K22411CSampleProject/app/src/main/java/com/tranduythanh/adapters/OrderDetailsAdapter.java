package com.tranduythanh.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tranduythanh.k22411csampleproject.R;
import com.tranduythanh.models.OrderDetails;

public class OrderDetailsAdapter extends ArrayAdapter<OrderDetails> {

    private final Activity context;
    private final int resource;

    public OrderDetailsAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.txtProductId = convertView.findViewById(R.id.txtProductId);
            holder.txtQuantity = convertView.findViewById(R.id.txtQuantity);
            holder.txtPrice = convertView.findViewById(R.id.txtPrice);
            holder.txtDiscount = convertView.findViewById(R.id.txtDiscount);
            holder.txtVAT = convertView.findViewById(R.id.txtVAT);
            holder.txtTotalValue = convertView.findViewById(R.id.txtTotalValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OrderDetails od = getItem(position);
        if (od != null) {
            holder.txtProductId.setText(String.valueOf(od.getProductId()));
            holder.txtQuantity.setText(String.valueOf(od.getQuantity()));
            holder.txtPrice.setText(String.format("%,.2f VNĐ", od.getPrice()));
            holder.txtDiscount.setText(String.format("%.2f%%", od.getDiscount()));
            holder.txtVAT.setText(String.format("%.2f%%", od.getVAT()));
            holder.txtTotalValue.setText(String.format("%,.2f VNĐ", od.getTotalValue()));
        }

        // Alternate background colors
        convertView.setBackgroundResource(position % 2 == 0 ? R.drawable.item_background : R.drawable.item_background);

        return convertView;
    }

    private static class ViewHolder {
        TextView txtProductId;
        TextView txtQuantity;
        TextView txtPrice;
        TextView txtDiscount;
        TextView txtVAT;
        TextView txtTotalValue;
    }
}