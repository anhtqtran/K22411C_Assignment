package com.tranduythanh.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.tranduythanh.k22411csampleproject.R;
import com.tranduythanh.models.PaymentMethod;

public class PaymentMethodAdapter extends ArrayAdapter<PaymentMethod> {
    Activity context;
    int resource;
    Typeface typeface;

    public PaymentMethodAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        typeface = Typeface.createFromAsset(context.getAssets(), "font/TMC-Ong Do.TTF");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(this.resource, parent, false);
        TextView txtPaymentMethodName = item.findViewById(R.id.txtPaymentMethodName);
        TextView txtPaymentMethodDescription = item.findViewById(R.id.txtPaymentMethodDescription);

        PaymentMethod pm = getItem(position);
        if (pm != null) {
            txtPaymentMethodName.setText(pm.getName());
            txtPaymentMethodDescription.setText(pm.getDescription());
            txtPaymentMethodName.setTypeface(typeface);
        }

        return item;
    }
}