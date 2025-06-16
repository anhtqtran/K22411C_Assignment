package com.tranduythanh.k22411csampleproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranduythanh.models.TelephonyInfor;

public class SendSmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_sms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public  void sendSms(TelephonyInfor ti, String content)
    {
        final SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage( ti.getPhone(), null, content, null, null);
        Toast.makeText(SendSmsActivity.this, "Đã gửi tin nhắn tới "+ti.getPhone(),
                Toast.LENGTH_LONG).show();
    }
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    public  void sendSmsPendingIntent(TelephonyInfor ti, String content)
    {
        //lấy mặc định SmsManager
        final SmsManager sms = SmsManager.getDefault();
        Intent msgSent = new Intent("ACTION_MSG_SENT");
//Khai báo pendingintent để kiểm tra kết quả
        final PendingIntent pendingMsgSent =
                PendingIntent.getBroadcast(this, 0, msgSent, PendingIntent.FLAG_IMMUTABLE);
        registerReceiver(new BroadcastReceiver() {
            @SuppressLint("UnspecifiedRegisterReceiverFlag")
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                String msg="Send OK";
                if (result != Activity.RESULT_OK) {
                    msg="Send failed";
                }
                Toast.makeText(SendSmsActivity.this, msg,
                        Toast.LENGTH_LONG).show();
            }
        }, new IntentFilter("ACTION_MSG_SENT"));
//Gọi hàm gửi tin nhắn đi
        sms.sendTextMessage(ti.getPhone(), null, content,
                pendingMsgSent, null);
    }
}