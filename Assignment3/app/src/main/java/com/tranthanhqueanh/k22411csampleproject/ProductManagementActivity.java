package com.tranthanhqueanh.k22411csampleproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranthanhqueanh.connectors.CategoryConnector;
import com.tranthanhqueanh.connectors.ProductConnector;
import com.tranthanhqueanh.models.Category;
import com.tranthanhqueanh.models.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManagementActivity extends AppCompatActivity {

    ListView lvProduct;
    ProductAdapter adapter;
    ProductConnector connector;
    CategoryConnector categoryConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_management);
        setSupportActionBar(findViewById(R.id.toolbar));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvents();
    }

    private void addEvents() {
        lvProduct.setOnItemLongClickListener((parent, view, i, id) -> {
            Product selected = adapter.getItem(i);
            adapter.remove(selected); // Remove from adapter
            adapter.notifyDataSetChanged(); // Refresh ListView
            return true; // Consume event
        });
    }

    private void addViews() {
        lvProduct = findViewById(R.id.lvProduct);
        connector = new ProductConnector();
        categoryConnector = new CategoryConnector();
        adapter = new ProductAdapter(this, connector.get_all_products());
        lvProduct.setAdapter(adapter);
    }

    private class ProductAdapter extends ArrayAdapter<Product> {
        private final Map<Integer, DisplayConfig> displayConfig;

        public ProductAdapter(Context context, List<Product> products) {
            super(context, 0, products);
            displayConfig = new HashMap<>();
            displayConfig.put(R.id.tvProductName, new DisplayConfig(
                    R.string.product_name_label,
                    product -> new Object[]{product.getId(), product.getName()}
            ));
            displayConfig.put(R.id.tvProductPrice, new DisplayConfig(
                    R.string.product_price_label,
                    product -> new Object[]{product.getPrice()}
            ));
            displayConfig.put(R.id.tvProductCategory, new DisplayConfig(
                    R.string.product_category_label,
                    product -> {
                        String categoryName = "Unknown";
                        for (Category category : categoryConnector.get_all_categories()) {
                            if (category.getId() == product.getCate_id()) {
                                categoryName = category.getName();
                                break;
                            }
                        }
                        return new Object[]{categoryName};
                    }
            ));
            displayConfig.put(R.id.tvProductDescription, new DisplayConfig(
                    R.string.product_description_label,
                    product -> new Object[]{product.getDescription()}
            ));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_product, parent, false);
            }
            Product product = getItem(position);
            for (Map.Entry<Integer, DisplayConfig> entry : displayConfig.entrySet()) {
                TextView textView = convertView.findViewById(entry.getKey());
                DisplayConfig config = entry.getValue();
                Object[] values = config.valueProvider.apply(product);
                String formattedText = getContext().getString(config.stringResId, values);
                textView.setText(formattedText);
            }

            return convertView;
        }

        private class DisplayConfig {
            final int stringResId;
            final java.util.function.Function<Product, Object[]> valueProvider;

            DisplayConfig(int stringResId, java.util.function.Function<Product, Object[]> valueProvider) {
                this.stringResId = stringResId;
                this.valueProvider = valueProvider;
            }
        }
    }
}