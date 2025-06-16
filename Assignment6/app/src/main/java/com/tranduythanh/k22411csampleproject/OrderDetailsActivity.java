package com.tranduythanh.k22411csampleproject;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranduythanh.adapters.OrderDetailsAdapter;
import com.tranduythanh.connectors.OrderDetailsConnector;
import com.tranduythanh.connectors.SQLiteConnector;
import com.tranduythanh.models.OrderDetails;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity {

    ListView lvOrderDetails;
    OrderDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
    }

    private void addViews() {
        lvOrderDetails = findViewById(R.id.lvOrderDetails);
        adapter = new OrderDetailsAdapter(this, R.layout.item_orderdetails);
        lvOrderDetails.setAdapter(adapter);

        // Get OrderId from Intent
        int orderId = getIntent().getIntExtra("ORDER_ID", -1);
        if (orderId != -1) {
            SQLiteConnector connector = new SQLiteConnector(this);
            OrderDetailsConnector odc = new OrderDetailsConnector();
            ArrayList<OrderDetails> dataset = odc.getOrderDetailsByOrderId(connector.openDatabase(), orderId);
            adapter.addAll(dataset);
        }
    }
}