package com.tranthanhqueanh.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.tranthanhqueanh.myapplication.R;
import com.tranthanhqueanh.myapplication.models.Products;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private Context context;
    private List<Products> productList;

    public ProductListAdapter(Context context, List<Products> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products product = productList.get(position);
        holder.textViewProductName.setText(product.getProductName());
        holder.textViewProductCode.setText(product.getProductCode());
        holder.textViewUnitPrice.setText(String.valueOf(product.getUnitPrice()));
        Glide.with(context)
                .load(product.getImageLink())
                .placeholder(R.drawable.placeholder) // Placeholder image
                .into(holder.imageViewProduct);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduct;
        TextView textViewProductName, textViewProductCode, textViewUnitPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imgProduct);
            textViewProductName = itemView.findViewById(R.id.txtProductName);
            textViewProductCode = itemView.findViewById(R.id.txtProductCode);
            textViewUnitPrice = itemView.findViewById(R.id.txtUnitPrice);
        }
    }
}