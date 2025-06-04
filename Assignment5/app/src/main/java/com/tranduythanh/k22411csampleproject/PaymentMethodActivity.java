package com.tranduythanh.k22411csampleproject;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.tranduythanh.adapters.PaymentMethodAdapter;
import com.tranduythanh.connectors.PaymentMethodConnector;
import com.tranduythanh.connectors.SQLiteConnector;
import com.tranduythanh.models.ListPaymentMethod;

public class PaymentMethodActivity extends AppCompatActivity {
    ListView lvPaymentMethod;
    PaymentMethodAdapter adapter;
    PaymentMethodConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        try {
            setContentView(R.layout.activity_paymentmethod);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            addViews();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tải giao diện: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void addViews() {
        try {
            lvPaymentMethod = findViewById(R.id.lvPaymentMethod);
            adapter = new PaymentMethodAdapter(this, R.layout.item_paymentmethod);
            connector = new PaymentMethodConnector();

            SQLiteConnector sqLiteConnector = new SQLiteConnector(this);
            sqLiteConnector.openDatabase();
            ListPaymentMethod lpm = connector.getAllPaymentMethods(sqLiteConnector.getDatabase());

            if (lpm.getPaymentMethods().isEmpty()) {
                Toast.makeText(this, "Không có phương thức thanh toán nào trong cơ sở dữ liệu", Toast.LENGTH_LONG).show();
            }

            adapter.addAll(lpm.getPaymentMethods());
            lvPaymentMethod.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}